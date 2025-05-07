/**
 * Enum con los nombres de variables entorno
 */
package co.com.avc.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Gestion Enum
 * <p>
 * Enum que contiene los posibles tipos de datos de gestión que
 * podrán ser utilizados en el directorio Aval.
 * <p>
 * Desarrollo AVC - SPBVI
 * <p>
 * Creado él: 07 de Mayo de 2025
 *
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
 */
@AllArgsConstructor
@Getter
public enum GestionEnum {
    /**
     * Clave del secreto para acceder al valor del usuario
     */
    VAR_USER("username"),

    /**
     * Clave del secreto para acceder al valor de la contraseña
     */
    VAR_KEY("password"),

    /**
     * Clave del secreto para acceder al valor del host
     */
    VAR_HOST("host"),

    /**
     * Clave del secreto para acceder al valor del puerto
     */
    VAR_PORT("port"),

    /**
     * Clave del secreto para acceder al valor del esquema
     */
    VAR_SCHEMA("schema");

    private final String value;

}
