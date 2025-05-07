package co.com.avc.repository;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.constants.MessagesEnum;
import co.com.ath.constants.ConstantsEnum;
import co.com.ath.model.SecretManagerDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;

import java.io.PrintWriter;
import java.io.StringWriter;


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
 * Clase que permite la conexión hacia opensearch
 *
 * @version 1.0
 * @autor Kevin A. Smok Garcia
 */
@Slf4j
public class RepositoryHttpRequest {

    private final SecretManagerDto secretManagerDto;

    /**
     * Encargado de manejar mensajes de error
     */
    private StringWriter errors;

    public RepositoryHttpRequest(SecretManagerDto secretManagerDto) {
        this.secretManagerDto = secretManagerDto;
    }

    /**
     * Creación del cliente y abre la conexión hacia OpenSearch.
     *
     * @return conexión hacia opensearch
     */
    public OpenSearchClient createHttpRequest() {

        try {
            log.info("Inicia el proceso de conexión con Opensearch");
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(secretManagerDto.getUsername(), secretManagerDto.getKeyword()));
            RestClient builder = RestClient.builder(
                            new HttpHost(secretManagerDto.getHostname(), secretManagerDto.getPort(), secretManagerDto.getSchema()))
                            .
                    setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    }).build();
            OpenSearchTransport transport = new RestClientTransport(builder, new JacksonJsonpMapper());
            return new OpenSearchClient(transport);
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error(ConstantsEnum.ERROR_CONNECTION.getValue() + errors.toString());
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
    }
}
