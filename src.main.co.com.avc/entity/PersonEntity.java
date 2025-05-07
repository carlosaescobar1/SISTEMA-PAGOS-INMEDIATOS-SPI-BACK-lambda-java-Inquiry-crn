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
@SerdeImport(PersonEntity.class)
@DynamoDBDocument
public class PersonEntity {

    /*Primer nombre del cliente.*/
    @DynamoDBAttribute
    private String firstName;

    /*Segundo nombre del cliente.*/
    @DynamoDBAttribute
    private String secondName;

    /*Primer apellido del cliente.*/
    @DynamoDBAttribute
    private String firstSurName;

    /*Segundo apellido del cliente.*/
    @DynamoDBAttribute
    private String secondSurName;

    /*Tipo de persona.*/
    @DynamoDBAttribute
    private String typePerson;

    /*Nombre legal del cliente*/
    @DynamoDBAttribute
    private String documentType;

    /*Objeto que guarda la información de la identificación del cliente.*/
    @DynamoDBAttribute
    private String documentNumber;

    public static final TableSchema<PersonEntity> SCHEMA =
            TableSchema.builder(PersonEntity.class)
                    .newItemSupplier(PersonEntity::new)
                    .addAttribute(String.class, a -> a.name("firstName")
                            .getter(PersonEntity::getFirstName)
                            .setter(PersonEntity::setFirstName))
                    .addAttribute(String.class, a -> a.name("secondName")
                            .getter(PersonEntity::getSecondName)
                            .setter(PersonEntity::setSecondName))
                    .addAttribute(String.class, a -> a.name("firstSurName")
                            .getter(PersonEntity::getFirstSurName)
                            .setter(PersonEntity::setFirstSurName))
                    .addAttribute(String.class, a -> a.name("secondSurName")
                            .getter(PersonEntity::getSecondSurName)
                            .setter(PersonEntity::setSecondSurName))
                    .addAttribute(String.class, a -> a.name("typePerson")
                            .getter(PersonEntity::getTypePerson)
                            .setter(PersonEntity::setTypePerson))
                    .addAttribute(String.class, a -> a.name("documentType")
                            .getter(PersonEntity::getDocumentType)
                            .setter(PersonEntity::setDocumentType))
                    .build();

}
