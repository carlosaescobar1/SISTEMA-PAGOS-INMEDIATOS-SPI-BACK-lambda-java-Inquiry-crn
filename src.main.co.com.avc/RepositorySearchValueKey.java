package co.com.avc.repository;

import co.com.avc.commons.util.ATHException;
import co.com.avc.commons.util.constants.MessagesEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.model.InputRqAccount;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchTemplateResponse;
import org.opensearch.client.opensearch.core.search.Hit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

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
 * Clase para la búsqueda de las llaves en opensearch por medio de la informacion
 * de una llave en especifico
 *
 * @version 1.0
 * @autor Kevin A. Smok Garcia
 */
@Slf4j
public class RepositorySearchValueKey {

    /**
     * Objeto para almacenar los errores durante el procesamiento
     */
    StringWriter errors = new StringWriter();

    /**
     * Metodo que se encarga de de comunicarse por medio de OpensearchClient y realziar
     * una busqueda por medio de un template de busqueda para traer la llave especificada
     *
     * @param client         Cliente de Opensearch encargado de realizar la busqueda
     * @param inputRqAccount Objeto con la informacion de entrada mapeada
     * @return Listado de Hits con el resultado de la consulta
     */
    public List<Hit<HashMap>> RepositorySearchValueKey(OpenSearchClient client, InputRqInquiry inputRqInquiry) {

        SearchTemplateResponse<HashMap> searchResponse = null;
        try {
            searchResponse = client.searchTemplate(s -> s
                            .index(ConstantsEnum.INDEX_IK.getValue())
                            .id(ConstantsEnum.SEARCH_TEMPLATE_KEY_ACCOUNT.getValue())
                            .params("valueKey", JsonData.of(InputRqInquiry.getValueKey()))

                    ,
                    HashMap.class);

            return searchResponse.hits().hits();

        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error(ConstantsEnum.ERROR_CONNECTION.getValue() + errors.toString());
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }


    }

}
