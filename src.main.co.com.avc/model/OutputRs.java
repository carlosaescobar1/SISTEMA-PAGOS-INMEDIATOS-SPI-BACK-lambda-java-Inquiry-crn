package co.com.avc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AcctInfoEntity
 *
 * Desarrollo AVC - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Creado el : 7 de mayo 2025
 *
 * Autor: Jonhatan G Romero
 *
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 *
 * Este software es confidencial y es propiedad de AVC, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 *
 * Clase que representa la información de la cuenta asociada a la llave del usuario.
 *
 * @author Jonhatan G Romero
 * @version 1.0
 * @since  1.0
 */
@Setter
@Getter
@Introspected
@SerdeImport(OutputRs.class)
public class OutputRs {
    /**
     * Informacion del de cliente
     */
    @JsonProperty("PersonInfo")
    private PersonDto personInfoDto;

    /**
     * Informacion bancaria de la cuenta

    @JsonProperty("ResBGetAccountRelationships")
    private List<ResBGetAccountRelationshipsDto> resBGetAccountRelationshipsDto;*/
    /**
     * Informacion de la llave
     */
    @JsonProperty("KeyDto")
    private KeyDto keyDto;
    /**
     * Informacion del método de pago
     */
    @JsonProperty("PaymentMethodDto")
    private PaymentMethodDto paymentMethodDto;
}
