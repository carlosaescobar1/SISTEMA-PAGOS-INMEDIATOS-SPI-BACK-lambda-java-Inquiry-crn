package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.constants.ResponseStatusCodeEnum;
import co.com.avc.constants.VaultNameEnum;
import co.com.avc.mapper.RedInquiryMapper;
import co.com.avc.model.Headers;
import co.com.ath.redebanconn.model.HttpResponseWrapper;
import co.com.ath.redebanconn.model.inquiries.KeysResponse;
import co.com.ath.redebanconn.model.inquiries.MsgInformationResponseInquiry;
import co.com.ath.redebanconn.service.inquiries.RedIdInquiriesImpl;
import co.com.ath.util.TimeLineUtil;
import co.com.ath.util.VaultSelectorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class RedInquiryAccImpl implements IRedInquiryAcc {

    private final RedIdInquiriesImpl redIdInquiries = new RedIdInquiriesImpl();
    private final RedInquiryMapper redInquiryMapper;

    private final TimeLineUtil timeLineUtil;

    private final int serviceTimeOut;

    private final VaultSelectorUtil vaultSelectorUtil;

    @Override
    public KeysResponse redAccountService(  Headers headers) {

        try {

            HttpResponseWrapper httpResponseWrapper = redIdInquiries.identInquiry(headers.getCustIdentNum(), redInquiryMapper.mapRedHeadersRq(headers),
                    vaultSelectorUtil.vaultSelector(VaultNameEnum.REDEBAN_PERSON_V3.getValue()).getUrlInquiryAccVault(), serviceTimeOut);

            return processRedAccountService(httpResponseWrapper);

        } catch (IOException conExp) {

            log.error("Error al consumir servicio Camara Redeban Directory: {}", conExp.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_VAULT_CONN.getStatusCode());

        }

    }


    private KeysResponse processRedAccountService(HttpResponseWrapper inquiryRs) {

        log.info("Codigo de respuesta solicitud Redeban Directory: {} ", inquiryRs.getStatusCode());

        log.info("Respuesta redeban sin mapear: {}", inquiryRs.getResponseBody());

        if (inquiryRs.getStatusCode() == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                && inquiryRs.getResponseBody().contains("Key")) {

            KeysResponse keysResponse = (KeysResponse) Util.string2object(inquiryRs.getResponseBody(), KeysResponse.class);

            log.info("Respuesta del servicio Redeban Directory exitosa: {}",
                    Util.object2String(keysResponse));

            return keysResponse;

        } else {

            MsgInformationResponseInquiry msgInformationResponse = (MsgInformationResponseInquiry) Util.string2object(
                    inquiryRs.getResponseBody(),
                    MsgInformationResponseInquiry.class);

            log.info("Respuesta del servicio Redeban Directory no exitosa: {}",
                    Util.object2String(msgInformationResponse));

            return null;
        }
    }

}
