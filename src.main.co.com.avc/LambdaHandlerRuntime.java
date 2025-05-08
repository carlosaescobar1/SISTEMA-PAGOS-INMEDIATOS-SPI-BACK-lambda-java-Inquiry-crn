/**
 * Clase en la raiz del proyecto co.com.ath
 *
 * Implementa la clase LambdaHandlerRuntime
 */
package co.com.avc;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.net.MalformedURLException;

/**
 * LambdaHandlerRuntime
 * <p>
 * Desarrollo AVC - SPBVI
 * <p>
 * Creado él: 08 de mayo 2025
 *
 * @author Jonhatan G. Romero
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de AVC, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
@Slf4j
public class LambdaHandlerRuntime extends AbstractMicronautLambdaRuntime<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent, APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{

    public static void main(String[] args) throws MalformedURLException {
        log.info("🟢✅ Ingreso a LambdaHandlerRuntime main()");
        new LambdaHandlerRuntime().run(args);
    }

    /**
     * Método encargado de invocar el LambdaHandler
     * @param args
     * @return
     */
    @Override
    protected RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createRequestHandler(String... args) {
        log.info("🟢✅ Creando instancia de LambdaHandler desde LambdaHandlerRuntime");
        return new LambdaHandler();
    }
}