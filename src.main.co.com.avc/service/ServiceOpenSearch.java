package co.com.avc.service;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.constants.StatusDirectoryEnum;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.mapper.DynamoToResponseMapper;
import co.com.avc.mapper.HitsToResponseMapper;
import co.com.avc.mapper.RedToResponseMapper;
import co.com.avc.model.*;
import co.com.avc.cornerconn.constants.KeyTypeEnum;
import co.com.avc.cornerconn.models.inquiries.KeyResponse;
import co.com.avc.cornerconn.models.inquiries.KeysResponse;
import co.com.avc.repository.RepositoryDynamo;
import co.com.avc.repository.RepositorySearchAccount;
import co.com.avc.repository.RepositorySearchCust;
import co.com.avc.repository.RepositorySearchKeyType;
import co.com.avc.util.exception.ServiceException;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.search.Hit;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.util.HashMap;
import java.util.List;

import static co.com.ath.constants.StatusDirectoryEnum.STATUS_DIRECTORY;

/**
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Creado el : 28 de agosto de 2024
 * <p>
 * Autor: Kevin A. Smok Garcia
 * <p>
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase encargada de implementar los repositorios de busqueda para realizar dos tipos de busqueda,
 * una por medio de la informacion del cliente y otra por medio de la informacion de llave,
 * esta informacion llega por medio de path parameters
 *
 * @version 1.0
 * @autor Kevin A. Smok Garcia
 */
@Slf4j
public class ServiceOpenSearch implements IServiceOpensearch {

    /**
     * Instancia de RepositorySearchCust
     */
    RepositorySearchCust repositorySearchCust = new RepositorySearchCust();

    /**
     * Instancia de RepositorySearchAccount
     */
    RepositorySearchAccount repositorySearchAccount = new RepositorySearchAccount();

    /**
     * Instancia de RepositorySearchKeyType
     */
    RepositorySearchKeyType repositorySearchKeyType = new RepositorySearchKeyType();

    /**
     * Instancia de RepositoryDynamo
     */
    RepositoryDynamo repositoryDynamo = new RepositoryDynamo();

    /**
     * Instancia de mapper DynamoToResponseMapper
     */
    DynamoToResponseMapper dynamoToResponseMapper = new DynamoToResponseMapper();

    /**
     * Instancia de RedToResponseMapper
     */
    RedToResponseMapper redToResponseMapper = new RedToResponseMapper();

    /**
     * Instancia del mappe HitsToResponseMapper
     */
    HitsToResponseMapper hitsToResponseMapper = new HitsToResponseMapper();

    /**
     * Instancia de OutputRs
     */
    OutputRs outputRs = new OutputRs();

    /**
     * Instancia de KeysResponse
     */
    KeysResponse keysResponse = new KeysResponse();


    private final IRedInquiryKey redInquiryKey;

    private final IRedInquiryAcc redInquiryAccount;

    private final int querySize;


    public ServiceOpenSearch(IRedInquiryKey redInquiryKey, int querySize, IRedInquiryAcc redInquiryAccount) {
        this.redInquiryKey = redInquiryKey;
        this.querySize = querySize;
        this.redInquiryAccount = redInquiryAccount;
    }

    /**
     * Metodo que recibe la informacion del cliente por medio de path paramethers
     * y realiza la busqueda de las llaves relacionadas a ese cliente
     *
     * @param client      Cliente de Opensearch encargado de realizar la busqueda
     * @param inputRqCust Objeto con la informacion de entrada mapeada
     * @return Retorna un OutPutRs con la informacion de la consulta
     */
    @Override
    public APIGatewayProxyResponseEvent keyCustSearch(OpenSearchClient client, InputRqCust inputRqCust) {
        log.info("Comienza la consulta por informacion del cliente");

        if (!inputRqCust.getCustIdentNum()
                .equalsIgnoreCase(inputRqCust.getHeaders().getCustIdentNum())
                || !inputRqCust.getCustIdentType()
                .equalsIgnoreCase(inputRqCust.getHeaders().getCustIdentType())) {

            log.info("Llave encontrada no pertenece al cliente solicitante.");
            throw new ServiceException(ResponseServiceEnum.ERROR_CLIENT_ID.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getStatusDesc(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getAdditionalStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getAdditionalStatusDesc());
        }

        List<Hit<HashMap>> hits = repositorySearchCust.searchKeyCust(client, inputRqCust, querySize);

        boolean hasNotStatusDir = hits.stream()
                .anyMatch(hit -> !hit.source().containsKey(STATUS_DIRECTORY.getValue()));

        hits.forEach(hit -> log.info("Hits: {}", Util.object2String(hit.source())));

        if (hasNotStatusDir) {
            keysResponse = redInquiryAccount.redAccountService(inputRqCust.getHeaders());
        } else {
            keysResponse = null;
        }
        return hitsToResponseMapper.mapHitsToResponse(hits, inputRqCust.getHeaders(), keysResponse);
    }

    /**
     * Metodo que recibe la informacion de la llave por medio de path paramethers
     * y realiza la busqueda de dicha llave
     *
     * @param inputRqKey Objeto con la informacion de entrada mapeada
     * @return un OutPutRs con la informacion de la consulta
     */
    public APIGatewayProxyResponseEvent keySearch(DynamoDbEnhancedClient client, InputRqKey inputRqKey,
                                                  Headers headers, String nameTable) {

        log.info("Comienza la consulta por informacion de la llave");

        DynamoSpiEntity dynamoSpiEntity = null;

        String refType = inputRqKey.getHeaders().getRefType() != null ?
                inputRqKey.getHeaders().getRefType() :
                KeyTypeEnum.KEY_ALPHANUMERIC.getAthValue();
        String idSk;
        if (refType != null) {

            log.info("Inicia proceso de busqueda en DynamoDb");
            if (inputRqKey.getRefId().toUpperCase().startsWith(ConstantsEnum.KEY_ID_START_WITH.getValue())) {
                idSk = refType + inputRqKey.getRefId().toUpperCase();
            } else {
                idSk = inputRqKey.getRefId().toUpperCase();
            }

            dynamoSpiEntity = repositoryDynamo.load(idSk, idSk, client, nameTable);

        }

        if (dynamoSpiEntity == null || dynamoSpiEntity.getStatusDirectory() == null) {

            log.info("Inicia proceso de busqueda en camara receptora");

            if (dynamoSpiEntity == null) {
                KeyResponse keyResponse = redInquiryKey.processRedInquiryService(
                        inputRqKey.getHeaders().getRefType(),
                        inputRqKey.getRefId(),
                        headers);

                String statusDir = null;

                if (!keyResponse.getKey().isDice()) {
                    statusDir = StatusDirectoryEnum.STATUS_FEDERATE_DIR.getValue();
                } else if (keyResponse.getKey().isDice()) {
                    statusDir = StatusDirectoryEnum.STATUS_CENTRALIZED_DIR.getValue();
                }
                return redToResponseMapper.mapRedToResponse(keyResponse, inputRqKey.getHeaders(), statusDir);
            } else if (dynamoSpiEntity.getStatusDirectory() == null) {

                return redInquiryKey.processRedInquiryServiceApi(inputRqKey.getHeaders().getRefType(),
                        inputRqKey.getRefId(),
                        headers, dynamoSpiEntity);
            }
        }

        return dynamoToResponseMapper.dynamoToResponse(dynamoSpiEntity, inputRqKey.getHeaders(), dynamoSpiEntity.getStatusDirectory());

    }

    /**
     * Metodo que recibe la informacion del cliente por medio de path paramethers
     * y realiza la busqueda de las llaves relacionadas a ese cliente
     *
     * @param client         Cliente de Opensearch encargado de realizar la busqueda
     * @param inputRqAccount Objeto con la informacion de entrada mapeada
     * @return Retorna un OutPutRs con la informacion de la consulta
     */
    @Override
    public APIGatewayProxyResponseEvent keyAccountSearch(OpenSearchClient client, InputRqAccount inputRqAccount) {
        log.info("Comienza la consulta por informacion del cliente");

        if (!inputRqAccount.getCustIdentNum()
                .equalsIgnoreCase(inputRqAccount.getHeaders().getCustIdentNum())
                || !inputRqAccount.getCustIdentType()
                .equalsIgnoreCase(inputRqAccount.getHeaders().getCustIdentType())) {
            log.info("Llave encontrada no pertenece al cliente solicitante.");
            throw new ServiceException(ResponseServiceEnum.ERROR_CLIENT_ID.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getStatusDesc(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getAdditionalStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getAdditionalStatusDesc());
        }

        log.info("Comienza consulta por cuenta");
        List<Hit<HashMap>> hits = repositorySearchAccount.searchKeyAccount(client, inputRqAccount);

        return hitsToResponseMapper.mapHitsToResponse(hits, inputRqAccount.getHeaders(), null);

    }

    /**
     * Metodo que recibe la informacion del cliente por medio de path paramethers
     * y realiza la busqueda de las llaves relacionadas a ese cliente
     *
     * @param client         Cliente de Opensearch encargado de realizar la busqueda
     * @param inputRqKeyType Objeto con la informacion de entrada mapeada
     * @return Retorna un OutPutRs con la informacion de la consulta
     */
    @Override
    public APIGatewayProxyResponseEvent keyTypeSearch(OpenSearchClient client, InputRqKeyType inputRqKeyType) {
        log.info("Comienza la consulta por informacion del cliente");

        if (!inputRqKeyType.getCustIdentNum()
                .equalsIgnoreCase(inputRqKeyType.getHeaders().getCustIdentNum())
                || !inputRqKeyType.getCustIdentType()
                .equalsIgnoreCase(inputRqKeyType.getHeaders().getCustIdentType())) {
            log.info("Llave encontrada no pertenece al cliente solicitante.");
            throw new ServiceException(ResponseServiceEnum.ERROR_CLIENT_ID.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getStatusDesc(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getAdditionalStatusCode(),
                    ResponseServiceEnum.ERROR_CLIENT_ID.getAdditionalStatusDesc());
        }

        List<Hit<HashMap>> hits = repositorySearchKeyType.searchKeyType(client, inputRqKeyType);

        return hitsToResponseMapper.mapHitsToResponse(hits, inputRqKeyType.getHeaders(), null);

    }
}
