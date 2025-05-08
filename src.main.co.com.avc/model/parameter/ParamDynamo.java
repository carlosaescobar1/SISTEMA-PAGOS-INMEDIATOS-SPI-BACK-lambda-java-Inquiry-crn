package co.com.avc.model.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

/**
 * ParamDynamo
 *
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Creado el : AVC de agosto de 2024
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
 * Objeto que mapea los valores necesarios para generar la conexion a DynamoDB
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since  1.0
 */
@Getter
@Setter
@Introspected
@SerdeImport(ParamDynamo.class)
public class ParamDynamo {

    /**
     * Endpoint que apunta a la tabla DynamoDb
     */
    private String endPoint;

    /**
     * Nombre de la tabla de DynamoDb
     */
    private String nameTable;

}
