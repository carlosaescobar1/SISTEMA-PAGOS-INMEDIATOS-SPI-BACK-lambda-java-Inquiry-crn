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
@SerdeImport(PaymentMethodEntity.class)
@DynamoDBDocument
public class PaymentMethodEntity {

    /*Tipo de llave.*/
    @DynamoDBAttribute
    private String description;

    /*Valor de la llave.*/
    @DynamoDBAttribute
    private String accountNumber;

    public static final TableSchema<PaymentMethodEntity> SCHEMA = TableSchema.builder(PaymentMethodEntity.class)
            .newItemSupplier(PaymentMethodEntity::new)
            .addAttribute(String.class, a -> a.name("description")
                    .getter(PaymentMethodEntity::getKeyType)
                    .setter(PaymentMethodEntity::setKeyType))
            .addAttribute(String.class, a -> a.name("accountNumber")
                    .getter(PaymentMethodEntity::getKeyId)
                    .setter(PaymentMethodEntity::setKeyId))
            .build();
}
