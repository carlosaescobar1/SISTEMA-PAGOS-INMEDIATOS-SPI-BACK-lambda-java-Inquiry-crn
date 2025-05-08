package co.com.avc.service;

import co.com.ath.model.Headers;
import co.com.avc.cornerconn.models.inquiries.KeysResponse;

public interface IRedInquiryAcc {

    KeysResponse redAccountService(Headers headers);

}
