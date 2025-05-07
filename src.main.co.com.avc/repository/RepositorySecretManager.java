package co.com.ath.repository;

import co.com.ath.commons.util.SecretManagerUtil;
import co.com.ath.constants.GestionEnum;
import co.com.ath.model.SecretManagerDto;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RepositorySecretManager {


    private final String arnSecret;


    public SecretManagerDto getSecrets(){

        SecretManagerDto secretManagerDto = new SecretManagerDto();

        JsonObject secretValues = SecretManagerUtil.getSecret(arnSecret);

        secretManagerDto.setPort(Integer.parseInt(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_PORT.getValue())));

        secretManagerDto.setKeyword(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_KEY.getValue()));

        secretManagerDto.setUsername(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_USER.getValue()));

        secretManagerDto.setSchema(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_SCHEMA.getValue()));

        secretManagerDto.setHostname(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_HOST.getValue()));


        return secretManagerDto;
    }

}
