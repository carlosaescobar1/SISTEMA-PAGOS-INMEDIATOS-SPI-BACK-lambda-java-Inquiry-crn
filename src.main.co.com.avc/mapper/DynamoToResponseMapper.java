package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.ath.commons.util.constants.MessagesEnum;
import co.com.avc.constants.ConsentEnum;
import co.com.avc.constants.PersonTypeEnum;
import co.com.avc.constants.StatusDirectoryEnum;
import co.com.avc.entity.CustInfEntity;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.model.*;
import co.com.avc.util.BankNameUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DynamoToResponseMapper
 * <p>
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
 * Clase que realiza el mapeo de la consulta en DynamoDb al objeto de repsuesta OuPutRs
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class DynamoToResponseMapper {

    /**
     * Metodo que mapea la informacion que llega desde una consulta de Opensearch al OutputRs
     *
     * @param dynamoSpiEntity
     * @return OutputRs
     */
    public APIGatewayProxyResponseEvent dynamoToResponse(DynamoSpiEntity dynamoSpiEntity, String statusDirect) {

         /**
         * Instancia de OutputRs
         */
        OutputRs outputRs = new OutputRs();

        /**
         * Instancia de PersonInfoDto
         */
        PersonInfoDto personInfoDto = new PersonInfoDto();

        /**
         * Instancia de PersonNameDto
         */
        PersonNameDto personNameDto = new PersonNameDto();

        /**
         * Lista de ResBGetAccountRelationshipsDto
         */
        List<ResBGetAccountRelationshipsDto> resBGetList = new ArrayList<>();

        /**
         * Instancia de ResBGetAccountRelationshipsDto
         */
        ResBGetAccountRelationshipsDto resBGetAccountRelationshipsDto = new ResBGetAccountRelationshipsDto();

        /**
         * Lista de RefInfoDto
         */
        List<RefInfoDto> refInfoDtoList = new ArrayList<>();

        /**
         * Instancia de CardAcctIdFromDto;
         */
        CardAcctIdFromDto cardAcctIdFromDto = new CardAcctIdFromDto();

        /**
         * Instancia  de CardAcctIdDto
         */
        CardAcctIdDto cardAcctIdDto = new CardAcctIdDto();

        /**
         * Instancia de BankInfoDto
         */
        BankInfoDto bankInfoDto = new BankInfoDto();

        /**
         * Instancia de BankAcctStatusDto
         */
        BankAcctStatusDto bankAcctStatusDto = new BankAcctStatusDto();


        personInfoDto.setPersonName(personNameDtoMapper(dynamoSpiEntity.getCustInf(), dynamoSpiEntity.getCustType()));
        personInfoDto.setCustIdentType(dynamoSpiEntity.getCustInf().getCustIdent().getCustIdentType());
        personInfoDto.setCustIdentNum(dynamoSpiEntity.getCustInf().getCustIdent().getCustIdentNum());
        personInfoDto.setPersonaType(dynamoSpiEntity.getCustType());

        outputRs.setPersonInfoDto(personInfoDto);

        refInfoDto1.setRefType(dynamoSpiEntity.getKey().getKeyType());
        refInfoDto1.setRefId(dynamoSpiEntity.getKey().getKeyId());

        refInfoDto2.setRefType("1");
        if (dynamoSpiEntity.getSignerAlias() == null) {
            refInfoDto2.setRefId("");
        } else {
            refInfoDto2.setRefId(dynamoSpiEntity.getSignerAlias());
        }

        refInfoDto3.setRefType("1");
        //Mas adelante se debe cambiar el siguiente valor por la camara una ves se usen
        refInfoDto3.setRefId(dynamoSpiEntity.getVaultNameRec());

        refInfoDtoList.add(refInfoDto1);
        refInfoDtoList.add(refInfoDto2);
        refInfoDtoList.add(refInfoDto3);

        bankInfoDto.setBankId(dynamoSpiEntity.getAcctInfo().getBankId());
        bankInfoDto.setName(BankNameUtil.bankNameSelector(dynamoSpiEntity.getAcctInfo().getBankId()));

        cardAcctIdDto.setBankInfo(bankInfoDto);
        cardAcctIdDto.setAcctId(dynamoSpiEntity.getAcctInfo().getAcctId());
        cardAcctIdDto.setAcctType(dynamoSpiEntity.getAcctInfo().getAcctType());
        cardAcctIdDto.setPreferredIndicator(dynamoSpiEntity.getPreferredIndicator());

        cardAcctIdFromDto.setCardAcctId(cardAcctIdDto);
        xferInfoDto.setCardAcctIdFrom(cardAcctIdFromDto);

        bankAcctStatusDto.setStatusDesc(dynamoSpiEntity.getStatusKey());
        bankAcctStatusDto.setEffDt(dynamoSpiEntity.getEffDtCreate());


        if (dynamoSpiEntity.getEffDtModify() != null && !dynamoSpiEntity.getEffDtModify().isEmpty()) {
            bankAcctStatusDto.setEffDt(dynamoSpiEntity.getEffDtModify());
        }

        if (dynamoSpiEntity.getEffDtConsent() != null && !dynamoSpiEntity.getEffDtConsent().isEmpty()) {
            bankAcctStatusDto.setEffDtConsent(dynamoSpiEntity.getEffDtConsent());
            bankAcctStatusDto.setConsent(dynamoSpiEntity.getConsent());
        }else if (dynamoSpiEntity.getEffDtConsent() == null){
            bankAcctStatusDto.setConsent(ConsentEnum.N.getValue());
        }
        resBGetAccountRelationshipsDto.setRefInfoDtos(refInfoDtoList);
        resBGetAccountRelationshipsDto.setXferInfoDto(xferInfoDto);
        resBGetAccountRelationshipsDto.setBankAcctStatusDto(bankAcctStatusDto);
        resBGetAccountRelationshipsDto.setStatusDirectory(statusDirect);
        resBGetList.add(resBGetAccountRelationshipsDto);

        outputRs.setResBGetAccountRelationshipsDto(resBGetList);


        headersRs.put("X-RqUID", headers.getRqUID());
        headersRs.put("X-ApprovalId", UUID.randomUUID().toString());
        headersRs.put("x-original-http-status-code", String.valueOf(MessagesEnum.SUCCESS_RESPONSE.getHttpCode()));
        headersRs.put("Content-Type", "application/json");
        headersRs.put("X-Custom-Header", "application/json");
        headersRs.put("Access-Control-Allow-Origin", "*");
        headersRs.put("Access-Control-Allow-Headers", "*");
        headersRs.put("Access-Control-Allow-Methods", "GET,OPTIONS,POST,PUT,DELETE");

        log.info("Respuesta de la consulta : " + Util.object2String(outputRs));

        return new APIGatewayProxyResponseEvent()
                .withBody(Util.object2String(outputRs))
                .withStatusCode(MessagesEnum.SUCCESS_RESPONSE.getHttpCode())
                .withHeaders(headersRs);
    }

    private PersonNameDto personNameDtoMapper(CustInfEntity custInfEntity, String personType) {

        PersonNameDto nameDto = new PersonNameDto();

        if (personType.equalsIgnoreCase(PersonTypeEnum.PN.getValue())) {

            String fullName = Stream.of(
                            custInfEntity.getCustFirstName(),
                            custInfEntity.getCustSecondName(),
                            custInfEntity.getCustFirstLastName(),
                            custInfEntity.getCustSecondLastName()
                    )
                    .filter(name -> name != null && !name.isEmpty())
                    .collect(Collectors.joining(" "));

            nameDto.setFirstName(fullName);

        } else {
            nameDto.setLegalName(custInfEntity.getCustLegalName());
        }

        return nameDto;

    }

}
