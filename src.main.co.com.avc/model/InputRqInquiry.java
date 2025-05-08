package co.com.avc.model;

import co.com.ath.commons.util.validations.ValueOfEnum;
import co.com.avc.constants.CustIdentTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
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
@SerdeImport(InputRqInquiry.class)
public class InputRqInquiry {

    /**
     * valor de la llave
     */
    @NotNull
    @JsonProperty("ValueKey")
    @Size(max = 100)
    private String valueKey;

}
