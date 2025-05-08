package co.com.avc.service;

import co.com.avc.model.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.opensearch.client.opensearch.OpenSearchClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

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
 *
 * Interfaz donde se establece los metodos de busqueda de llaves.
 *
 * @version 1.0
 * @autor Kevin A. Smok Garcia
 *
 */
public interface IServiceOpensearch {

    APIGatewayProxyResponseEvent keyCustSearch(OpenSearchClient client, InputRqCust inputRqCust);

    APIGatewayProxyResponseEvent keySearch(DynamoDbEnhancedClient client, InputRqKey inputRqKey, Headers headers, String nameTable);

    APIGatewayProxyResponseEvent keyTypeSearch(OpenSearchClient client, InputRqKeyType inputRqKeyType);

    APIGatewayProxyResponseEvent keyAccountSearch(OpenSearchClient client, InputRqAccount inputRqAccount);

}
