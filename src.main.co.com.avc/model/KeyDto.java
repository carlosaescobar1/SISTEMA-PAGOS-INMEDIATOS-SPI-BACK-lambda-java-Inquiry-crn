package co.com.avc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

/**
 * PersonNameDto
 *
 * Desarrollo AVC - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Creado el : 7 de mayo 2025
 *
 * Autor: Jonhatan G. Romero
 *
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 *
 * Este software es confidencial y es propiedad de AVC, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 *
 * Clase que representa un objeto que contiene la informacion del cliente
 *
 * @author Jonhatan G. Romero
 * @version 1.0
 * @since  1.0
 */
@Getter
@Setter
@Introspected
@SerdeImport(KeyDto.class)
public class KeyDto {

    /**
    * Tipo de documento
    */
    @JsonProperty("KeyType")
    private String keyType;

    /**
     * Número de documento
     */
    @JsonProperty("ValueKey")
    private String valueKey;

}
