package co.com.ath.repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Creado el : 28 de agosto de 2024
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
 * <p>
 * Clase encargada de mapear la conexion con Dynamo
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class RepositoryDynamoBuilder {

    /**
     * Region de AWS en donde se alojan los servicios.
     */
    private String dynamoRegion;


    /**
     * Variable encargada de manejar mensajes de error para los catch
     */
    static StringWriter errors;

    /**
     * Variable constante de ERROR
     */
    static final String ERROR = "Error: ";

    public DynamoDbEnhancedClient getClient() {

        DynamoDbEnhancedClient enhancedClient;
        try {
            log.info("Entrando getClient");
            var dynamoDbClient = DynamoDbClient.builder().region(Region.of(dynamoRegion))
                    .credentialsProvider(
                            SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.getStringValue().isPresent()
                                    ? ContainerCredentialsProvider.builder().build()
                                    : EnvironmentVariableCredentialsProvider.create())
                    .build();

            enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();

            log.info("Saliendo getClient");
            return enhancedClient;
        } catch (Exception e) {
            errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(ERROR + errors);
            log.info("Entra a default");
            return null;
        }
    }
}
