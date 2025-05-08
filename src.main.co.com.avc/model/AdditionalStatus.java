package co.com.avc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AdditionalStatus
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
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since  1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Introspected
@SerdeImport(AdditionalStatus.class)
public class AdditionalStatus {

    @JsonProperty("StatusCode")
    private int statusCode;

    @JsonProperty("ServerStatusCode")
    private String serverStatusCode;

    @JsonProperty("Severity")
    private String severity;

    @JsonProperty("StatusDesc")
    private String statusDesc;

}