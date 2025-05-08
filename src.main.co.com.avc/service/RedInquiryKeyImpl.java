package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.*;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.mapper.DynamoToResponseMapper;
import co.com.avc.mapper.RedInquiryMapper;
import co.com.avc.mapper.RedToResponseMapper;
import co.com.avc.model.Headers;
import co.com.avc.cornerconn.models.HttpResponseWrapper;
import co.com.avc.cornerconn.models.inquiries.KeysResponse;
import co.com.avc.cornerconn.models.inquiries.MsgInformationResponseInquiry;
import co.com.avc.cornerconn.service.inquiries.IRedInquiriesAccountService;
import co.com.avc.cornerconn.service.inquiries.RedInquiriesAccountServiceImpl;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import co.com.avc.util.exception.ServiceException;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class RedInquiryKeyImpl implements IRedInquiryKey {

    private final IRedInquiriesAccountService redInquiriesAccountService = new RedInquiriesAccountServiceImpl();
    private final RedInquiryMapper redInquiryMapper;

    private final TimeLineUtil timeLineUtil;

    private final int serviceTimeOut;

    private final VaultSelectorUtil vaultSelectorUtil;


    @Override
    public KeyResponse processRedInquiryService(
            String keyType, String keyId,
            Headers headers
    ) {

        try {

            timeLineUtil.sendLogFedRq(headers, keyType, keyId);

            return processInquiryServiceRs(redInquiriesAccountService.keyInquiry(
                    keyId,
                    redInquiryMapper.mapRedHeadersRq(headers),
                    vaultSelectorUtil.vaultSelector(VaultNameEnum.REDEBAN_PERSON_V3.getValue()).getUrlInquiryKeyVault(),
                    serviceTimeOut)
            );

        } catch (IOException conExp) {

            log.error("Error al consumir servicio Camara Redeban Directory: {}", conExp.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusCode());

        } finally {
            timeLineUtil.sendLogFedRs(headers, keyType, keyId);
        }

    }


    private KeyResponse processInquiryServiceRs(HttpResponseWrapper inquiryRs) {

        log.info("Codigo de respuesta solicitud Redeban Directory: {} ", inquiryRs.getStatusCode());

        log.info("Respuesta redeban sin mapear: {}", inquiryRs.getResponseBody());

        if (inquiryRs.getStatusCode() == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                && inquiryRs.getResponseBody().contains("Key")) {

            KeyResponse keyResponse = (KeyResponse) Util.string2object(inquiryRs.getResponseBody(), KeyResponse.class);

            log.info("Respuesta del servicio Redeban Directory exitosa: {}",
                    Util.object2String(keyResponse));

            return keyResponse;

        } else {

            MsgInformationResponseInquiry msgInformationResponse = (MsgInformationResponseInquiry) Util.string2object(
                    inquiryRs.getResponseBody(),
                    MsgInformationResponseInquiry.class
            );

            log.info("Respuesta del servicio Redeban Directory no exitosa: {}",
                    Util.object2String(msgInformationResponse));

            if (msgInformationResponse.getMessageInformation()
                    .getMsgCode().equalsIgnoreCase(ResponseCodeEnum.RED_PERSON_NOT_FOUND_STATUS_CODE.getValue())) {

                throw new ServiceException(ResponseServiceEnum.ERROR_DB_VALIDATION.getServerStatusCode(),
                        ResponseServiceEnum.ERROR_DB_VALIDATION.getStatusDesc(),
                        ResponseServiceEnum.ERROR_DB_VALIDATION.getStatusCode(),
                        ResponseServiceEnum.ERROR_DB_VALIDATION.getAdditionalStatusCode(),
                        ResponseServiceEnum.ERROR_DB_VALIDATION.getAdditionalStatusDesc());
            }

            throw new ServiceException(msgInformationResponse.getMessageInformation().getMsgCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusDesc(),
                    inquiryRs.getStatusCode(),
                    inquiryRs.getStatusCode(),
                    msgInformationResponse.getMessageInformation().getMsgDescription());

        }

    }

    @Override
    public APIGatewayProxyResponseEvent processRedInquiryServiceApi(String keyType, String keyId, Headers headers, DynamoSpiEntity dynamoSpiEntity) {

        timeLineUtil.sendLogFedRq(headers, keyType, keyId);

        try {

            timeLineUtil.sendLogFedRq(headers, keyType, keyId);

            return processInquiryServiceRsApi(redInquiriesAccountService.keyInquiry(
                    keyId,
                    redInquiryMapper.mapRedHeadersRq(headers),
                    vaultSelectorUtil.vaultSelector(VaultNameEnum.REDEBAN_PERSON_V3.getValue()).getUrlInquiryKeyVault(),
                    serviceTimeOut), headers, dynamoSpiEntity);

        } catch (IOException conExp) {

            log.error("Error al consumir servicio Camara Redeban Directory: {}", conExp.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusCode());

        } finally {
            timeLineUtil.sendLogFedRs(headers, keyType, keyId);
        }
    }

    private APIGatewayProxyResponseEvent processInquiryServiceRsApi(HttpResponseWrapper inquiryRs, Headers headers, DynamoSpiEntity dynamoSpiEntity) {

        log.info("Codigo de respuesta solicitud Redeban Directory: {} ", inquiryRs.getStatusCode());

        log.info("Respuesta redeban sin mapear: {}", inquiryRs.getResponseBody());

        RedToResponseMapper redToResponseMapper = new RedToResponseMapper();
        DynamoToResponseMapper dynamoToResponseMapper = new DynamoToResponseMapper();

        if (inquiryRs.getStatusCode() == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                && inquiryRs.getResponseBody().contains("Key")) {

            KeyResponse keyResponse = (KeyResponse) Util.string2object(inquiryRs.getResponseBody(), KeyResponse.class);

            log.info("Respuesta del servicio Redeban Directory exitosa: {}",
                    Util.object2String(keyResponse));

            if (!keyResponse.getKey().isDice()) {
                return redToResponseMapper.mapRedToResponse(keyResponse, headers, StatusDirectoryEnum.STATUS_FEDERATE_DIR.getValue());
            }
            else if (keyResponse.getKey().isDice()) {
                return redToResponseMapper.mapRedToResponse(keyResponse, headers, StatusDirectoryEnum.STATUS_CENTRALIZED_DIR.getValue());
            }
        }
        return dynamoToResponseMapper.dynamoToResponse(dynamoSpiEntity, headers, StatusDirectoryEnum.STATUS_AVAL_DIR.getValue());

    }
}
