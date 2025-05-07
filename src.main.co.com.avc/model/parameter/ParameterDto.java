package co.com.ath.model.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * ParameterDto
 * <p>
 * Desarrollo AVC - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
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
 * Clase que representa un objeto de transferencia de datos (DTO) para parámetros.
 * Esta clase encapsula la información relacionada con los parámetros utilizados en el sistema.
 * Los datos incluyen información de conexión, configuración y otros parámetros relevantes.
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Introspected
@SerdeImport(ParameterDto.class)
public class ParameterDto {

    /**
     * Mapea informacion de conexion a DynamoDB
     */
    private ParamDynamo dynamoParameters;

    /**
     * Mapeo para informacion de SNS
     */
    private ParamArnSns paramArnSns;

    /**
     * Region AWS
     */
    private String awsRegion;

    private String arnSecret;

    private ParamActiveVault paramActiveVault;

    private ParamVaultsEntityId paramVaultsEntityId;

    private ParamVaultTimeOut paramVaultTimeOut;

    private ParamOrigin paramOrigin;

    private int osQuerySize;

}
