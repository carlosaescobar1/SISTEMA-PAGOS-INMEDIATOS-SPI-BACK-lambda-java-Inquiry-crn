package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResponseServiceEnum
 * <p>
 * Enum que contiene los posibles tipos de respuesta que
 * podrán ser registrados en el directorio Aval.
 * <p>
 * Desarrollo AVC - SPBVI
 * <p>
 * Creado él: 07 de Mayo de 2025
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 **/
@AllArgsConstructor
@Getter
public enum ResponseServiceEnum {


    /**
     * Error entregado como respuesta a una validacion
     */
    ERROR_REQUEST_VALIDATION(400, "400", "Error",
            "La solicitud contiene parámetros inválidos o faltan parámetros requeridos.",
            9, "VALIDATIONS"),


    /**
     * Error entregado como respuesta a un metodo no permitido
     */
    ERROR_NOT_ALLOW_METHOD(405, "405", "Error",
            "Método no permitido.",
            405,
            "VALIDATIONS"),

    /**
     * Error cuando no se encuentra ninguna llave en la consulta
     */
    ERROR_DB_VALIDATION(206,"206",  "Warning",
            "Llave no existe.", 8,
            "La llave que se intenta consultar no existe."),

    /**
     * Error cuando no se encuentra ninguna llave en la consulta
     */
    ERROR_DB_VALIDATION_CLIENT(206, "206",  "Warning",
            "Cliente sin llaves asociadas.",
            8,
            "El cliente que intenta consultar no tiene llaves asociadas."),


    /**
     * Error entregado como respuesta a un error interno
     */
    ERROR_TEC_EXCEPTION(500, "500", "Error",
            "Error interno del servidor.",
            500, "CREATION"),

    /**
     * Error entregado como respuesta a un problema con DynamoDB
     */
    ERROR_TEC_EXCEPTION_DYNAMO(500, "500", "Error",
            "Error al intentar operacion sobre DynamoDB.",
            50,"CREATION"),

    /**
     * Constante de error en caso de que se presente un fallo al intentar operación sobre DynamoDB.
     */
    ERROR_TEC_EXCEPTION_VAULT_CONN(500, "500", "Error",
            "Error al intentar operación sobre directorio federado",
            500,
            "CONSULTA"),

    /**
     * Constante de error cuando la solicitud no proviene del banco dueño de la lambda.
     */
    ERROR_BANK_ID(206, "206", "Warning",
            "Operación rechazada.",
            11,
            "No se puede realizar la operación solicitada sobre la llave indicada."),

    /**
     * Constante de error cuando la solicitud no proviene del cliente dueño de la llave.
     */
    ERROR_CLIENT_ID(206,  "206","Warning",
            "Operación rechazada.",
            11,
            "No se puede realizar la operación solicitada sobre la llave indicada.");

    private final Integer statusCode;
    private final String serverStatusCode;
    private final String severity;
    private final String statusDesc;
    private final Integer additionalStatusCode;
    private final String additionalStatusDesc;

}
