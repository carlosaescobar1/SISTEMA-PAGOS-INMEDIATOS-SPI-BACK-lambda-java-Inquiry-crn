/**
 * Clase en package constants
 * <p>
 * Implementa los enum
 */
package co.com.avc.constants;

/**
 * ConstantsEnum
 * <p>
 * Enum que contiene las constantes de parametros, errores, secrets
 * podrán ser utilizados en el directorio Aval para consulta.
 * <p>
 * Desarrollo AVC - SPBVI
 * <p>
 * Creado él: 07 de Mayo de 2025
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
public enum ConstantsEnum {
    /**
     * Comentario mostrado al presentarse errores en conexiones
     */
    ERROR_CONNECTION("No se esta generando la conexion con OpenSearch Correctamente "),

    /**
     * Comentario mostrado al presentarse errores en conexiones
     */
    ERROR_NOT_FOUND("No se encuentra la llave buscada "),

    /**
     * Constante de indice de llaves
     */
    INDEX_IK("index_key"),
    /**
     * Raiz de los parametros para esta lambda
     */
    VAR_LAMBDA("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/"),
    /**
     * Constante para traer parametro con el ARN del Secreto de Opensearch
     */
    VAR_OPENSEARCH("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/arnSecretOpensearch"),

    /**
     * Constante para traer los ARN de los SNS de Opensearch
     */
    VAR_PARAM_JSON_ARN_SNS("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/jsonArnSnsBAVV"),

    /**
     * Constante para traer la informacion general de las lambdas de BAVV
     */
    PARAMETER_GENERAL_PATH_URL("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/"),

    /**
     * Constante para traer informacion de DynamoDB
     */
    VAR_PARAM_JSON_DYNAMO("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/jsonDynamoDirAval"),

    /**
     * Constante para traer informacion de la region AWS
     */
    VAR_PARAM_AWS_REGION("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/awsRegion"),

    /**
     * Ruta del parámetro que contendrá el entity ID asignado por las camaras al banco
     * que procesa la solicitud
     */
    PARAM_ENTITY_ID("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/vaultsEntityId"),

    /**
     * Ruta del parámetro que contendrá los tiempos de espera configurados
     * para la conexión a los servicios de las cámaras.
     */
    PARAM_JSON_VAULT_TIME_OUT("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/vaultServicesTimeOut"),

    /**
     * Ruta del parámetro del jsonActiveVault
     */
    PARAM_JSON_VAULTS("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/jsonActiveVault"),

    /**
     * Ruta del parámetro que contendrá las variables necesarias para establecer el origin dentro de
     * las peticiones realizadas hacia la camara redeban.
     */
    PARAM_JSON_ORIGIN("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/jsonOrigin"),

    /**
     * tamaño maximo de datos que trae la consulta de opensearch
     */
    PARAM_QUERY_SIZE("/SPI/AWUE1ATHSPI-LAMBDA-BAVV/osQuerySize"),

    /**
     * Constante de error en Handler
     */
    ERROR("Error: "),

    /**
     * Constante con el valor por defecto que se seteara
     * al conectarse a directorio federado ACH
     */
    ACH_DEFAULT_VALUE(""),

    /**
     * Constante con el valor por defecto que se seteara
     * al conectarse a directorio federado Redeban
     */
    RED_DEFAULT_VALUE(""),

    /**
     * Constante para traer el parámetro con el valor para el template de busqueda por informacion de cliente de opensearch
     */
    SEARCH_TEMPLATE_KEY_CUST("TemplateKeyCust"),
    SEARCH_TEMPLATE_KEY_ACCOUNT("TemplateKeyAccount"),
    SEARCH_TEMPLATE_KEY_TYPE("TemplateKeyType"),

    APPLICATION_JSON("application/json"),

    KEY_ID_START_WITH("@"),

    INQUIRY_VAULT_FLAG("0"),

    /**
     * Constante con un ID único del banco dueño de la lambda
     */
    BANK_ID("0052");

    private final String value;

    ConstantsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
