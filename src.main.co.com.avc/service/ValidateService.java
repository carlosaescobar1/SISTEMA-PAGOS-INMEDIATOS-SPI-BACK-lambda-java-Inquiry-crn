package co.com.avc.service;

import co.com.ath.constants.ConstantsEnum;
import co.com.ath.constants.ResponseServiceEnum;
import co.com.ath.model.Headers;
import co.com.ath.model.InputRqAccount;
import co.com.ath.model.InputRqCust;
import co.com.ath.model.InputRqKeyType;
import co.com.ath.util.exception.ServiceException;
import io.micronaut.context.ApplicationContext;
import io.micronaut.validation.validator.Validator;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * ValidateService
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
 * Clase encargada de realizar las validaciones en los respectivos DTO y entities
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class ValidateService {

    /**
     * Método para validar Headers objeto que contiene los headers
     * de la petición de consulta. .
     * <p>
     * Recibe los parámetros en la clase Handler método redirect.
     *
     * @param headers modelo que representa los headers
     *                petición de consulta.
     */
    public void validateRequest(Headers headers) {

        validateConstraintViolations(headers);
        //validateBankId(headers.getCompanyId());

    }

    /**
     * Método para validar InputRqCust objeto de entrada para la
     * solicitud de consulta.
     * <p>
     * Recibe los parámetros en la clase Handler método redirect.
     *
     * @param inputRqCust modelo que representa el request de la
     *                    petición de creación en línea.
     * @param headers     modelo que representa los headers
     *                    petición de consulta.
     */
    public void validateRequest(InputRqCust inputRqCust, Headers headers) {

        validateConstraintViolations(inputRqCust);
        validateBankId(headers.getCompanyId(), inputRqCust.getBankId());

    }

    /**
     * Método para validar InputRqAccount objeto de entrada para la
     * solicitud de consulta.
     * <p>
     * Recibe los parámetros en la clase Handler método redirect.
     *
     * @param inputRqAccount modelo que representa el request de la
     *                       petición de creación en línea.
     * @param headers        modelo que representa los headers
     *                       petición de consulta.
     */
    public void validateRequest(InputRqAccount inputRqAccount, Headers headers) {

        validateConstraintViolations(inputRqAccount);
        validateBankId(headers.getCompanyId(), inputRqAccount.getBankId());

    }

    /**
     * Método para validar InputRqKeyType objeto de entrada para la
     * solicitud de consulta.
     * <p>
     * Recibe los parámetros en la clase Handler método redirect.
     *
     * @param inputRqKeyType modelo que representa el request de la
     *                       petición de creación en línea.
     * @param headers        modelo que representa los headers
     *                       petición de consulta.
     */
    public void validateRequest(InputRqKeyType inputRqKeyType, Headers headers) {

        validateConstraintViolations(inputRqKeyType);
        validateBankId(headers.getCompanyId(), inputRqKeyType.getBankId());

    }


    /**
     * Método que realizar las validaciones
     *
     * @param requestToValidate Objeto al que se le realizara validaciones
     */
    public void validateConstraintViolations(Object requestToValidate) {

        List<String> validationErrors = new ArrayList<>();
        StringJoiner invalidObjects = new StringJoiner(",");

        Validator validator = ApplicationContext.run().getBean(Validator.class);

        Set<ConstraintViolation<Object>> violations = validator.validate(requestToValidate);

        if (violations.isEmpty()) {
            return;
        }

        for (ConstraintViolation<Object> violation : violations) {
            String error = "Propiedad: " + violation.getPropertyPath() + ","
                    + " Valor inválido: " + violation.getInvalidValue()
                    + ", Mensaje: " + violation.getMessage();

            invalidObjects.add(violation.getPropertyPath().toString());

            validationErrors.add(error);

        }

        String message = "Error al realizar la consulta, el campo " + invalidObjects.toString().split(",")[0]
                + " contiene información inválida.";
        validateErrors(message, validationErrors);
    }

    /**
     * Método que valída si la petición proviene del banco dueño de la lambda.
     *
     * @param companyId identificador único del banco enviado en los headers de la
     *                  solicitud.
     * @param bankId    identificador único del banco enviado en los params de la
     *                  solicitud.
     */
    private void validateBankId(String companyId, String bankId) {

        if (companyId == null || bankId == null) {
            throw new ServiceException(ResponseServiceEnum.ERROR_BANK_ID.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getStatusDesc(),
                    ResponseServiceEnum.ERROR_BANK_ID.getStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getAdditionalStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getAdditionalStatusDesc());
        }

        if (!companyId.equalsIgnoreCase(bankId)
                || !companyId.equalsIgnoreCase(ConstantsEnum.BANK_ID.getValue())
                || !bankId.equalsIgnoreCase(ConstantsEnum.BANK_ID.getValue())) {

            throw new ServiceException(ResponseServiceEnum.ERROR_BANK_ID.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getStatusDesc(),
                    ResponseServiceEnum.ERROR_BANK_ID.getStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getAdditionalStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getAdditionalStatusDesc());
        }
    }

    /**
     * Método que valída si la petición proviene del banco dueño de la lambda.
     *
     * @param companyId identificador único del banco enviado en los headers de la
     *                  solicitud.
     */
    private void validateBankId(String companyId) {

        if (!companyId.equalsIgnoreCase(ConstantsEnum.BANK_ID.getValue())) {
            throw new ServiceException(ResponseServiceEnum.ERROR_BANK_ID.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getStatusDesc(),
                    ResponseServiceEnum.ERROR_BANK_ID.getStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getAdditionalStatusCode(),
                    ResponseServiceEnum.ERROR_BANK_ID.getAdditionalStatusDesc());
        }
    }

    /**
     * Método que construye el mensaje de error en caso de alguna violación de validaciones
     *
     * @param message          mensaje de entrada
     * @param validationErrors mapa de strings con las variables con violaciones
     */
    private void validateErrors(String message, List<String> validationErrors) {
        if (validationErrors.isEmpty()) {
            log.info("Request valido");
        } else {
            log.error("Request invalido " + validationErrors);
            throw new ServiceException(ResponseServiceEnum.ERROR_REQUEST_VALIDATION.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_REQUEST_VALIDATION.getStatusDesc(),
                    ResponseServiceEnum.ERROR_REQUEST_VALIDATION.getStatusCode(),
                    ResponseServiceEnum.ERROR_REQUEST_VALIDATION.getAdditionalStatusCode(),
                    message);

        }
    }

}
