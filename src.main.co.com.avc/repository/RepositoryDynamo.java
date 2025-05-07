package co.com.avc.repository;

import co.com.ath.commons.util.ATHException;
import co.com.avc.commons.util.Util;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.entity.DynamoSpiEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.io.PrintWriter;
import java.io.StringWriter;

import static co.com.avc.entity.DynamoSpiEntity.TABLE_SCHEMA_DYNAMO_SPI;

/**
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
 * Clase de realizar la busqueda de llaves en DynamoDb
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class RepositoryDynamo {

    /**
     * Método que busca un objeto de tipo DynamoSpiEntity desde DynamoDB.
     * <p>
     * Recibe el ID y la clave secundaria (SK) del objeto a cargar.
     * <p>
     * Recibe los parámetros en el método migrateKeys clase MigrationKeyServiceImpl
     * y método updateAccountInformation clase UpdateKeyServiceImpl
     *
     * @param id ID del objeto a buscar
     * @param sk Clave secundaria (SK) del objeto a buscar
     * @return El objeto cargado de tipo DynamoSpiEntity
     */
    public DynamoSpiEntity load(String valueKey, DynamoDbEnhancedClient client, String nameTable) {
        log.info("Inicia bÚsqueda valor de llave en DynamoDB");
        log.info("id" + valueKey);
        try {
            var table = client.table(nameTable, TABLE_SCHEMA_DYNAMO_SPI);

            DynamoSpiEntity dynamoSpiEntity = table.getItem(Key.builder().partitionValue(valueKey).build());

            if(dynamoSpiEntity == null) {
                return null;
            }

            log.info("Objeto recuperado: " + Util.object2String(dynamoSpiEntity));
            return dynamoSpiEntity;
        } catch (Exception e) {
            log.error("Error en la búsqueda por valor de llave en DynamoDB: " + e.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusCode());
        }
    }

}
