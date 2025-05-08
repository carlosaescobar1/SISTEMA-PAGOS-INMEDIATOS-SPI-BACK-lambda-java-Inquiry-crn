package co.com.ath.util.exception;

import co.com.ath.commons.util.ATHException;
import lombok.Getter;
import lombok.Setter;

/**
 * ServiceException
 *
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Creado el : 28 de agosto de 2024
 *
 * Autor: Kevin A. Smok Garcia
 *
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 *
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 *
 * Excepcion personaizada para errores ligados a violaciones en las validaciones de DTO
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since  1.0
 */
@Getter
@Setter
public class ServiceException extends ATHException {

    private final int additionalStatusCode;

    private final String additionalInfo;

    public ServiceException(String errorCode, String errorMessage, int httpCode,
                            int additionalStatusCode, String additionalInfo) {
        super(errorCode, errorMessage, httpCode);
        this.additionalInfo = additionalInfo;
        this.additionalStatusCode = additionalStatusCode;
    }

}
