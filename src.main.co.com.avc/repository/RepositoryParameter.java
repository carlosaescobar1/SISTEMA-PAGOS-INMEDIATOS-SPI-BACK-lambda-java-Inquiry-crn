package co.com.avc.repository;

import co.com.avc.commons.util.ParameterStoreUtil;
import co.com.avc.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.model.parameter.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


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
 * Clase que permite traer y mapear los parameterStore y secretos necesarios.
 *
 * @version 1.0
 * @autor Kevin A. Smok Garcia
 */
@Slf4j
public class RepositoryParameter {

    /**
     * Método donde se realiza el mapeo de la respuesta de la consulta a opensearch
     *
     * @return valores mapeados
     */
    public ParameterDto getParameters() {

        ParameterDto parameterDto = new ParameterDto();

        Map<String, String> generalParameter = ParameterStoreUtil.getParameters(ConstantsEnum.PARAMETER_GENERAL_PATH_URL.getValue());

        parameterDto.setArnSecret(generalParameter.get(ConstantsEnum.VAR_OPENSEARCH.getValue()));

        parameterDto.setDynamoParameters((ParamDynamo)
                Util.string2object(generalParameter.get(ConstantsEnum.VAR_PARAM_JSON_DYNAMO.getValue()),
                        ParamDynamo.class));

        parameterDto.setAwsRegion(generalParameter.get(ConstantsEnum.VAR_PARAM_AWS_REGION.getValue()));

        parameterDto.setParamArnSns((ParamArnSns)
                Util.string2object(generalParameter.get(ConstantsEnum.VAR_PARAM_JSON_ARN_SNS.getValue()),
                        ParamArnSns.class));

        parameterDto.setParamVaultTimeOut((ParamVaultTimeOut)
                Util.string2object(generalParameter.get(ConstantsEnum.PARAM_JSON_VAULT_TIME_OUT.getValue()),
                        ParamVaultTimeOut.class));

        parameterDto.setParamActiveVault((ParamActiveVault)
                Util.string2object(generalParameter.get(ConstantsEnum.PARAM_JSON_VAULTS.getValue()),
                        ParamActiveVault.class));

        parameterDto.setParamVaultsEntityId((ParamVaultsEntityId)
                Util.string2object(generalParameter.get(ConstantsEnum.PARAM_ENTITY_ID.getValue()),
                        ParamVaultsEntityId.class));

        parameterDto.setParamOrigin((ParamOrigin)
                Util.string2object(generalParameter.get(ConstantsEnum.PARAM_JSON_ORIGIN.getValue()),
                        ParamOrigin.class));

        parameterDto.setOsQuerySize(Integer.parseInt(generalParameter.get(ConstantsEnum.PARAM_QUERY_SIZE.getValue())));

        log.info("Parametros: {}", Util.object2String(parameterDto));

        return parameterDto;
    }
}
