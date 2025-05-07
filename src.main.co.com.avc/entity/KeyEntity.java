package co.com.avc.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

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
@Getter
@Setter
@ToString
@Introspected
@ReflectiveAccess
@SerdeImport(KeyEntity.class)
@DynamoDBDocument
public class KeyEntity {

    /*Tipo de llave.*/
    @DynamoDBAttribute
    private String keyType;

    /*Valor de la llave.*/
    @DynamoDBAttribute
    private String valueKey;

    public static final TableSchema<KeyEntity> SCHEMA = TableSchema.builder(KeyEntity.class)
            .newItemSupplier(KeyEntity::new)
            .addAttribute(String.class, a -> a.name("keyType")
                    .getter(KeyEntity::getKeyType)
                    .setter(KeyEntity::setKeyType))
            .addAttribute(String.class, a -> a.name("valueKey")
                    .getter(KeyEntity::getKeyId)
                    .setter(KeyEntity::setKeyId))
            .build();
}
