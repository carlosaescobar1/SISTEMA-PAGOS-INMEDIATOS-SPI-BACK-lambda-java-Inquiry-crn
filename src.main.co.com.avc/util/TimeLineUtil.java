package co.com.avc.util;


import co.com.ath.commons.util.Util;
import co.com.avc.constants.TimeLineEnum;
import co.com.avc.mapper.TimeLineMapper;
import co.com.avc.model.Headers;
import co.com.avc.opensearch.logs.constants.ActionConstants;
import co.com.avc.opensearch.logs.constants.TimeStampEnum;
import co.com.avc.opensearch.logs.service.OpensearchLogService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

;

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
 * Clase encargada de enviar a la mensajería SNS los registros de
 * logs que serán almacenados en Open search.
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class TimeLineUtil {

    /**
     * Dependencia inyectada por medio del constructor usada
     * para el envío de los a traves de mensajería SNS
     */
    OpensearchLogService opensearchLogService;

    /**
     * Dependencia inyectada por medio del constructor usada
     * para el mapeo de los mensajes que serán enviados al
     * índice index_timeline en open search.
     */
    TimeLineMapper timeLineMapper;

    /**
     * Atributo seteado a traves del constructor que contiene
     * el ARN del SNS vinculado al guardado de registros en
     * Open Search.
     */
    String arnSnsOpenSearch;

    /**
     * Metodo que envia logs de de inicio del proceso
     *
     * @param headers Headers de entrada
     * @param keyType Tipo de llave
     * @param keyId   Id de la lalve
     */
    public void sendLogRq(Headers headers, String keyType, String keyId, String bankId) {

        opensearchLogService.sendSNSOpenSearchLogs(timeLineMapper.keySearchToTimeLine(headers, keyType, keyId, bankId, TimeLineEnum.LOG_INI),
                arnSnsOpenSearch,
                ActionConstants.QUERY, TimeStampEnum.CUST_INQUIRIES_REQUEST);

    }


    public String isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            LocalDate.parse(date, formatter);
            return date;
        } catch (DateTimeParseException e) {
            return Util.createDate();
        }
    }

    /**
     * Metodo que envia logs de finalizacion del proceso
     *
     * @param headers Headers de entrada
     */
    public void sendLogRs(Headers headers) {

        opensearchLogService.sendSNSOpenSearchLogs(timeLineMapper.custSearchToTimeLine(headers, TimeLineEnum.LOG_FINAL),
                arnSnsOpenSearch,
                ActionConstants.QUERY, TimeStampEnum.CUST_INQUIRIES_RESPONSE);

    }


    /**
     * Metodo que envia logs de finalizacion del proceso
     *
     * @param headers Headers de entrada
     */
    public void sendLogRs(Headers headers, String bankId) {

        opensearchLogService.sendSNSOpenSearchLogs(timeLineMapper.custSearchToTimeLine(headers, bankId, TimeLineEnum.LOG_FINAL),
                arnSnsOpenSearch,
                ActionConstants.QUERY, TimeStampEnum.CUST_INQUIRIES_RESPONSE);

    }

    /**
     * Metodo que envia logs de finalizacion del proceso
     *
     * @param headers Headers de entrada
     * @param keyType Tipo de llave
     * @param keyId   Id de la llave
     */
    public void sendLogRs(Headers headers, String keyType, String keyId) {

        opensearchLogService.sendSNSOpenSearchLogs(timeLineMapper.keySearchToTimeLine(headers, keyType, keyId, TimeLineEnum.LOG_FINAL),
                arnSnsOpenSearch,
                ActionConstants.QUERY, TimeStampEnum.CUST_INQUIRIES_RESPONSE);

    }

    /**
     * Método que envía al SNS el registro inicial del proceso de
     * creación en línea (Petición de usuario para la creación de una
     * llave)
     * <p>
     * Obtiene los parámetros en la clase MigrationKeyServiceImpl
     * método migrateKeys.
     */
    public void sendLogFedRq(Headers headers, String keyType, String keyId) {

        opensearchLogService.sendSNSOpenSearchLogs(timeLineMapper.keySearchToTimeLine(
                        headers, keyType, keyId, TimeLineEnum.LOG_INI),
                arnSnsOpenSearch,
                ActionConstants.QUERY,
                TimeStampEnum.FED_INQUIRIES_REQUEST);

    }

    /**
     * Método que envía al SNS el registro final del proceso de
     * creación en línea (Respuesta al usuario para la creación de una
     * llave)
     * <p>
     * Obtiene los parámetros en la clase Handler
     * método redirect.
     */
    public void sendLogFedRs(Headers headers, String keyType, String keyId) {

        opensearchLogService.sendSNSOpenSearchLogs(timeLineMapper.keySearchToTimeLine(
                        headers, keyType, keyId, TimeLineEnum.LOG_INI),
                arnSnsOpenSearch,
                ActionConstants.QUERY,
                TimeStampEnum.FED_INQUIRIES_RESPONSE);

    }

}
