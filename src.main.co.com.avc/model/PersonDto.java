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
@SerdeImport(PersonDto.class)
public class PersonDto {

    /**
     * Primer apellido del cliente
     */
    @JsonProperty("FirstName")
    private String firstName;

    /**
     * Segundo nombre del cliente
     */
    @JsonProperty("SecondName")
    private String secondName;


    /**
     * Primer apellido del cliente
     */
    @JsonProperty("FirstSurName")
    private String firstSurName;

    /**
     * Segundo nombre del cliente
     */
    @JsonProperty("SecondSurName")
    private String secondSurName;

    /**
     * Segundo nombre del cliente
     */
    @JsonProperty("TypePerson")
    private String typePerson;

    /**
     * Tipo de documento
     */
    @JsonProperty("DocumentType")
    private String documentType;

    /**
     * Nombre del Negocio
     */
    @JsonProperty("BusinessName")
    private String businessName;

    /**
     * Número de documento
     */
    @JsonProperty("DocumentNumber")
    private String documentNumber;

}
