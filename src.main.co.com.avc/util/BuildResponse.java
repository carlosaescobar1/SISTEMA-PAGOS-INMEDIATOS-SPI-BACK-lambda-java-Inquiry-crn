package co.com.avc.util;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.constants.SeverityEnum;
import co.com.avc.model.AdditionalStatus;
import co.com.avc.model.MsgRsHdr;
import co.com.avc.model.ResBGetAccountRelationshipsError;
import co.com.avc.model.Status;
import co.com.avc.util.exception.ServiceException;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * BuildResponse
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
 * Clase que realiza la construccion de la respuesta de la lambda
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class BuildResponse {

    /**
     * Headers que se envian con la respuesta
     */
    //private static final Map<String, String> headers = new HashMap<>();

    /*static {
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Methods", "GET,OPTIONS,POST,PUT,DELETE");

    }*/

    /**
     * Construccion de la respuesta en caso de error tecnico
     *
     * @param ex    Excepcion disparada
     * @param rqUid RqId de la accion
     * @return APIGatewayProxyResponseEvent
     */
    public APIGatewayProxyResponseEvent buildTecError(ATHException ex, String rqUid) {


        ResBGetAccountRelationshipsError resBPostAccountRelationships = new ResBGetAccountRelationshipsError();
        MsgRsHdr msgRsHdr = new MsgRsHdr();

        headers.put("X-RqUID", rqUid != null ? rqUid : "");
        headers.put("X-ApprovalId", "0");
        headers.put("x-original-http-status-code", String.valueOf(ex.getHttpCode()));

        Status status = new Status();
        status.setStatusDesc(ex.getMessage());
        status.setServerStatusCode(ex.getErrorCode());
        status.setSeverity(ResponseServiceEnum.ERROR_DB_VALIDATION.getSeverity());

        status.setStatusCode(ex.getHttpCode());

        if (ex.getErrorCode().equals(ResponseServiceEnum.ERROR_DB_VALIDATION.getServerStatusCode())) {
            log.info("Ingresa al if");
            AdditionalStatus additionalStatus = new AdditionalStatus();
            additionalStatus.setStatusCode(Integer.parseInt(ex.getErrorCode()));
            additionalStatus.setServerStatusCode(String.valueOf(ex.getHttpCode()));
            additionalStatus.setSeverity(ResponseServiceEnum.ERROR_REQUEST_VALIDATION.getSeverity());
            additionalStatus.setStatusDesc(ResponseServiceEnum.ERROR_DB_VALIDATION.getAdditionalStatusDesc());

            status.setAdditionalStatus(additionalStatus);
        }

        msgRsHdr.setStatus(status);
        resBPostAccountRelationships.setMsgRsHdr(msgRsHdr);

        log.info("Respuesta : " + Util.object2String(resBPostAccountRelationships));

        return new APIGatewayProxyResponseEvent()
                .withBody(Util.object2String(resBPostAccountRelationships))
                .withStatusCode(ex.getHttpCode())
                .withHeaders(headers);
    }

    /**
     * Construccion de la respuesta para un error generico
     *
     * @param rqUid RqId de la accion
     * @return APIGatewayProxyResponseEvent
     */
    public APIGatewayProxyResponseEvent buildErrorDefault(String rqUid) {

        ResBGetAccountRelationshipsError resBPostAccountRelationships = new ResBGetAccountRelationshipsError();
        MsgRsHdr msgRsHdr = new MsgRsHdr();

        headers.put("X-RqUID", rqUid != null ? rqUid : "");
        headers.put("X-ApprovalId", "0");
        headers.put("x-original-http-status-code", ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode());

        Status status = new Status();
        status.setStatusDesc(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc());
        status.setServerStatusCode(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode());
        status.setSeverity("ERROR");
        status.setStatusCode(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());


        msgRsHdr.setStatus(status);
        resBPostAccountRelationships.setMsgRsHdr(msgRsHdr);

        log.info("Respuesta : " + Util.object2String(resBPostAccountRelationships));

        return new APIGatewayProxyResponseEvent()
                .withBody(Util.object2String(resBPostAccountRelationships))
                .withStatusCode(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode())
                .withHeaders(headers);
    }

    /**
     * COnstruccion de respuesta en caso de error por violaciones en validaciones
     *
     * @param ex    Excepcion que se disparo
     * @param rqUid RqId de la accion
     * @return APIGatewayProxyResponseEvent
     */
    public APIGatewayProxyResponseEvent buildServiceError(ServiceException ex, String rqUid) {

        ResBGetAccountRelationshipsError resBPostAccountRelationships = new ResBGetAccountRelationshipsError();
        MsgRsHdr msgRsHdr = new MsgRsHdr();

        headers.put("X-RqUID", rqUid != null ? rqUid : "");
        headers.put("X-ApprovalId", "0");
        headers.put("x-original-http-status-code", String.valueOf(ex.getHttpCode()));

        Status status = new Status();
        status.setStatusDesc(ex.getMessage());
        status.setServerStatusCode(String.valueOf(ex.getHttpCode()));
        status.setSeverity("ERROR");
        status.setStatusCode((ex.getHttpCode()));

        AdditionalStatus additionalStatus = new AdditionalStatus();

        additionalStatus.setStatusCode(ex.getAdditionalStatusCode());
        additionalStatus.setServerStatusCode(ex.getErrorCode());
        additionalStatus.setSeverity(SeverityEnum.ERROR.getValue());
        additionalStatus.setStatusDesc(ex.getAdditionalInfo());

        status.setAdditionalStatus(additionalStatus);

        msgRsHdr.setStatus(status);
        resBPostAccountRelationships.setMsgRsHdr(msgRsHdr);

        log.info("Respuesta : " + Util.object2String(resBPostAccountRelationships));

        return new APIGatewayProxyResponseEvent()
                .withBody(Util.object2String(resBPostAccountRelationships))
                .withStatusCode(ex.getHttpCode())
                .withHeaders(headers);
    }

}

