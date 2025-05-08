package co.com.avc.service;

import co.com.ath.entity.DynamoSpiEntity;
import co.com.ath.model.Headers;
import co.com.avc.cornerconn.models.inquiries.KeysResponse;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public interface IRedInquiryKey {

    KeyResponse processRedInquiryService(
            String keyType, String keyId,
            Headers headers
    );

    APIGatewayProxyResponseEvent processRedInquiryServiceApi(
            String keyType, String keyId,
            Headers headers, DynamoSpiEntity dynamoSpiEntity) ;

}
