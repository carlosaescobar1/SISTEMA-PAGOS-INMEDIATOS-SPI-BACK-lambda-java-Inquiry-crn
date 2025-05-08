package co.com.avc.util;

import co.com.avc.constants.BankIdEnum;

import java.util.Arrays;

public class BankNameUtil {

    public static String bankNameSelector(String bankIdRq){

        return Arrays.stream(BankIdEnum.values())
                .filter(bankId -> bankId.getBankId().equals(bankIdRq))
                .map(BankIdEnum::getBankName)
                .findFirst()
                .orElse(null);

    }

}
