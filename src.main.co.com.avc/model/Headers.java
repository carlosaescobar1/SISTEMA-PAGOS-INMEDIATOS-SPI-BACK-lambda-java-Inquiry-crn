package co.com.ath.model;

import co.com.ath.commons.util.validations.ValidDate;
import co.com.ath.commons.util.validations.ValueOfEnum;
import co.com.ath.constants.ChannelEnum;
import co.com.ath.constants.CustIdentTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Headers
 * <p>
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Creado el : 28 de agosto de 2024
 * <p>
 * Autor: Kevin A. Smok Garcia
 * <p>
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase que representa la informacion en los Headers
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@Introspected
@SerdeImport(Headers.class)
public class Headers {

    /**
     * Identificador de la peticion
     */
    @NotNull
    @JsonProperty("X-RqUID")
    @Size(min = 1, max = 36)
    @Pattern(regexp = ("^\\d+$"))
    private String rqUID;

    /**
     * Canal de origen
     */
    @NotNull
    @ValueOfEnum(enumClass = ChannelEnum.class)
    @JsonProperty("X-Channel")
    private String channel;

    /**
     * Id de la entidad bancaria de origen
     */
    @JsonProperty("X-CompanyId")
    private String companyId;

    /**
     * Tipo de documento de la entidad bancaria de origen
     */
    @NotNull
    @Pattern(regexp = ("(?i)^NIT$"))
    @JsonProperty("X-GovIssueIdentType")
    private String govIssueIdentType;

    /**
     * NIT de la entidad origen de la transacción
     */
    @NotNull
    @Size(max = 9)
    @Pattern(regexp = ("^\\d+$"))
    @JsonProperty("X-IdentSerialNum")
    private String identSerialNum;

    /**
     * Dirección IP
     */
    @Pattern(regexp = "^[\\d+\\.\\:A-Za-z]+$")
    @JsonProperty("X-IPAddr")
    private String iPAddr;

    /**
     * Fecha de la transacción
     */
    @NotNull
    @ValidDate(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("X-ClientDt")
    private String clientDt;

    /**
     * Tipo de identificación del usuario que se autenticó
     */
    @ValueOfEnum(enumClass = CustIdentTypeEnum.class)
    @JsonProperty("X-CustIdentType")
    private String custIdentType;

    /**
     * Número de identificación del usuario que se autenticó
     */
    @Size(max = 18)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @JsonProperty("X-CustIdentNum")
    private String custIdentNum;

    /**
     * Tipo de llave
     */
    @Pattern(regexp = ("[1-5]"))
    @JsonProperty("X-RefType")
    private String refType;

}
