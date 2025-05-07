/**
 * Clase en package constants
 * <p>
 * Implementa los enum
 */
package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * StatusDirectyEnum
 * <p>
 * Enum que contiene los posibles tipos de directorios que
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

@Getter
@AllArgsConstructor
public enum StatusDirectoryEnum {

    /**
     * Indicate the status directory on AVAL
     */
    STATUS_AVAL_DIR("DIRAVAL"),

    /**
     * Indicate the status directory on FEDERATE
     */
    STATUS_FEDERATE_DIR("DIFE"),

    /**
     * Indicate the status directory on CENTRALIZED
     */
    STATUS_CENTRALIZED_DIR("DICE"),

    /**
     * Indicate the status directory parameter
     */
    STATUS_DIRECTORY("statusDirectory"),

    ;

    private final String value;
}