package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SeverityEnum
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
public enum SeverityEnum {

    IFO("Info"),
    WARNING("Warning"),
    ERROR("Error")

    ;

    private final String value;

}
