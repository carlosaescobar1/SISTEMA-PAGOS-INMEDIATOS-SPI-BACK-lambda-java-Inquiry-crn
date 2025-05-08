package co.com.avc;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.mapper.HeadersMapper;
import co.com.avc.mapper.RedInquiryMapper;
import co.com.avc.mapper.TimeLineMapper;
import co.com.avc.model.*;
import co.com.avc.model.parameter.ParameterDto;
import co.com.ath.opensearch.logs.service.OpensearchLogService;
import co.com.avc.repository.RepositoryDynamoBuilder;
import co.com.avc.repository.RepositoryHttpRequest;
import co.com.avc.repository.RepositoryParameter;
import co.com.avc.repository.RepositorySecretManager;
import co.com.avc.service.*;
import co.com.avc.util.BuildResponse;
import co.com.avc.util.OriginSelectorUtil;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import co.com.avc.util.exception.ServiceException;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;

import java.io.PrintWriter;
import java.io.StringWriter;

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
 * Lambda que se activa por Api Gateway y realiza una consulta, esta consulta puede realizarse
 * por medio de la informacion del cliente o por la informacion de la llave.
 *
 * @version 1.0
 * @autor Kevin A. Smok Garcia
 */
@Slf4j
@Introspected
public class LambdaHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    /**
     * Instancia de RepositoryParameter
     */
    RepositoryParameter repositoryParameter = new RepositoryParameter();

    /**
     * Realiza llamado de los parametros
     */
    ParameterDto parameterDto = repositoryParameter.getParameters();

    RepositorySecretManager repositorySecretManager = new RepositorySecretManager(
            parameterDto.getArnSecret()
    );

    SecretManagerDto secretManagerDto = repositorySecretManager.getSecrets();

    /**
     * Instancia de RepositoryDynamoBuilder
     */
    RepositoryDynamoBuilder repositoryDynamoBuilder = new RepositoryDynamoBuilder(parameterDto.getAwsRegion());


    /**
     * Objeto para realizar solicitudes HTTP al repositorio de usuarios
     */
    RepositoryHttpRequest httpRequest = new RepositoryHttpRequest(secretManagerDto);

    /**
     * Cliente OpenSearch para interactuar con el servicio OpenSearch
     * realiza llamado al método de conexión a la base de datos de
     * openSearch, enviando los parámetros necesarios para dicha conexión.
     */
    OpenSearchClient client = httpRequest.createHttpRequest();

    /**
     * Instancia encarga de realizar el mapeo de los objetos que posteriormente se almacenaran en el
     * * indíce "index_timeline" de la base de datos Open search.
     */
    TimeLineMapper timeLineMapper = new TimeLineMapper();

    /**
     * Instancia de la clase encargada de enviar mensajes SNS que serán almacenados en la base de datos
     * Open search.
     */
    OpensearchLogService opensearchLogService = new OpensearchLogService();

    /**
     * Instancia de la clase encarga de gestionar el envío de logs al indíce "index_timeline" de
     * la base de datos Open search.
     */
    TimeLineUtil timeLineUtil = new TimeLineUtil(opensearchLogService, timeLineMapper,
            parameterDto.getParamArnSns().getArnSnsOpenSearch());

    VaultSelectorUtil vaultSelectorUtil = new VaultSelectorUtil(parameterDto.getParamActiveVault());

    OriginSelectorUtil originSelectorUtil = new OriginSelectorUtil(parameterDto.getParamOrigin());

    RedInquiryMapper redInquiryMapper = new RedInquiryMapper(parameterDto.getParamVaultsEntityId().getRedEntityId(), originSelectorUtil);

    IRedInquiryKey redInquiryKey = new RedInquiryKeyImpl(
            redInquiryMapper,
            timeLineUtil,
            parameterDto.getParamVaultTimeOut().getRedInquiriesTimeOut(),
            vaultSelectorUtil
    );

    IRedInquiryAcc redInquiryAcc = new RedInquiryAccImpl(
            redInquiryMapper,
            timeLineUtil,
            parameterDto.getParamVaultTimeOut().getRedInquiriesTimeOut(),
            vaultSelectorUtil
    );

    /**
     * Se crea instancia de ServiceOpenSearch para llamar al método
     * del servicio.
     */
    ServiceOpenSearch serviceOpenSearch = new ServiceOpenSearch(redInquiryKey, parameterDto.getOsQuerySize(),redInquiryAcc);

    /**
     * Se crea instancia de ValidateService para realizar validaciones
     */
    ValidateService validateService = new ValidateService();

    /**
     * Objeto para almacenar los errores durante el procesamiento
     */
    StringWriter errors = new StringWriter();

    /**
     * Dto para mapear la informacion de entrada para consulta por cliente
     */
    InputRqCust inputRqCust = new InputRqCust();

    /**
     * Dto para mapear la informacion de entrada para consulta por llave
     */
    InputRqKey inputRqKey = new InputRqKey();

    /**
     * Dto para mapear la informacion de entrada para consulta por llave
     */
    InputRqKeyType inputRqKeyType = new InputRqKeyType();

    /**
     * Dto para mapear la informacion de entrada para consulta por llave
     */
    InputRqAccount inputRqAccount = new InputRqAccount();

    /**
     * Instancia de BuildResponse
     */
    BuildResponse buildResponse = new BuildResponse();

    /**
     * RqUid que llega desde los request
     */
    String rqUid;

    /**
     * Instancia de Headers
     */
    Headers headers = new Headers();

    /**
     * Instancia para mapear headers
     */
    HeadersMapper headersMapper = new HeadersMapper();

    public LambdaHandler() {
    }
    /**
     * Este método procesa las solicitudes de API Gateway,
     * Hace llamado a método encargado del procesamiento de las llaves
     *
     * @param input   La entrada de la función Lambda.
     * @return Retorna un APIGatewayProxyResponseEvent que sera enviado a Api Gateway.
     */
    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        log.info("🟢✅ [INICIO] Ejecución LambdaHandler - Solicitud recibida");
        try {
            return redirect(input);
        } catch (ServiceException e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("🔴❌ [ServiceException] Error en ejecución: {}", errors);
            timeLineUtil.sendLogRs(headers);
            return buildResponse.buildServiceError(e, rqUid);
        } catch (ATHException e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("🔴❌ [ATHException] Error técnico durante ejecución: {}", errors);
            timeLineUtil.sendLogRs(headers);
            return buildResponse.buildTecError(e, rqUid);
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("🔴❌ [Exception] Error no controlado: {}", errors);
            timeLineUtil.sendLogRs(headers);
            return buildResponse.buildErrorDefault(rqUid);
        }
    }
    /**
     * Metodo encargado filtrar la informacion y seleccionar la consulta que se realizara.
     * Se puede buscar por medio de la informacion del cliente o por medio de la informacion
     * de la llave dependiendo del path del que pro
     *
     * @param input Objeto APIGatewayProxyRequestEvent de entrada
     * @return APIGatewayProxyResponseEvent Retorna un objeto de respuest para ApiGateway con la niformacion
     * de la consulta
     */
    public APIGatewayProxyResponseEvent redirect(APIGatewayProxyRequestEvent input) {
        log.info("🟢✅ [REDIRECT] Inicio de procesamiento de la solicitud");

        APIGatewayProxyResponseEvent responseEvent;

        //headers = headersMapper.heardersMapper(input.getHeaders());

        if (input.getHttpMethod().equalsIgnoreCase("GET")) {
            timeLineUtil.sendLogRq(headers,
                    input.getPathParameters().get("RefType") != null ? input.getPathParameters().get("RefType") : "",
                    input.getPathParameters().get("RefId") != null ? input.getPathParameters().get("RefId") : "",
                    input.getPathParameters().get("BankId") != null ? input.getPathParameters().get("BankId") : null);
        } else {
            timeLineUtil.sendLogRq(headers, "", "", headers.getCompanyId() != null ? headers.getCompanyId() : null);
        }

        log.info("🟢✅ Headers recibidos: {}", input.getHeaders());
        log.info("🟢✅ Path parameters: {}", input.getPathParameters());
        log.info("🟢✅ Query strings: {}", input.getQueryStringParameters());

        rqUid = input.getHeaders().get("X-RqUID") != null ? input.getHeaders().get("X-RqUID") : "";
        validateService.validateRequest(headers);

        if (input.getHttpMethod().equalsIgnoreCase("GET")) {

            if (input.getPath().contains("KeyInquiry") && input.getPathParameters().get("RefId") != null) {
                inputRqKey.setRefId(input.getPathParameters().get("RefId"));
                inputRqKey.setHeaders(headers);

                validateService.validateConstraintViolations(inputRqKey);
                responseEvent = serviceOpenSearch.keySearch(repositoryDynamoBuilder.getClient(), inputRqKey, headers, parameterDto.getDynamoParameters().getNameTable());

                timeLineUtil.sendLogRs(headers, inputRqKey.getHeaders().getRefType(), inputRqKey.getRefId());
                log.info("🟢✅ Consulta por RefId ejecutada exitosamente");
                return responseEvent;
            } else if (input.getPath().contains("IdentInquiry")) {
                inputRqCust.setCustIdentType(input.getPathParameters().get("CustIdentType").toUpperCase());
                inputRqCust.setCustIdentNum(input.getPathParameters().get("CustIdentNum"));
                inputRqCust.setBankId(input.getPathParameters().get("BankId"));
                inputRqCust.setHeaders(headers);

                validateService.validateRequest(inputRqCust, headers);
                responseEvent = serviceOpenSearch.keyCustSearch(client, inputRqCust);
                timeLineUtil.sendLogRs(headers, inputRqCust.getBankId());
                log.info("🟢✅ Consulta por identificación ejecutada exitosamente");
                return responseEvent;
            } else if (input.getPath().contains("KeyInquiry")) {
                inputRqKeyType.setCustIdentType(headers.getCustIdentType());
                inputRqKeyType.setCustIdentNum(headers.getCustIdentNum());
                inputRqKeyType.setBankId(headers.getCompanyId());
                inputRqKeyType.setRefType(input.getPathParameters().get("RefType"));
                inputRqKeyType.setHeaders(headers);

                validateService.validateRequest(inputRqKeyType, headers);
                responseEvent = serviceOpenSearch.keyTypeSearch(client, inputRqKeyType);
                timeLineUtil.sendLogRs(headers, inputRqKeyType.getBankId());
                log.info("🟢✅ Consulta por tipo de llave ejecutada exitosamente");
                return responseEvent;
            } else {
                log.warn("🔴❌ El Path de la petición no corresponde a ningún servicio disponible");
                return null;
            }
        } else if (input.getHttpMethod().equalsIgnoreCase("POST")) {
            inputRqAccount.setReqBPostAccountRelationships((ReqBPostAccountRelationships) Util.string2object(input.getBody(), ReqBPostAccountRelationships.class));
            inputRqAccount.setHeaders(headers);
            inputRqAccount.setCustIdentType(headers.getCustIdentType());
            inputRqAccount.setCustIdentNum(headers.getCustIdentNum());
            inputRqAccount.setBankId(headers.getCompanyId());

            validateService.validateRequest(inputRqAccount, headers);
            responseEvent = serviceOpenSearch.keyAccountSearch(client, inputRqAccount);
            timeLineUtil.sendLogRs(headers, inputRqAccount.getBankId());
            log.info("🟢✅ Consulta por cuenta ejecutada exitosamente");
            return responseEvent;

        } else {
            log.warn("🔴❌ Método HTTP no soportado");
            return null;
        }
    }
}


