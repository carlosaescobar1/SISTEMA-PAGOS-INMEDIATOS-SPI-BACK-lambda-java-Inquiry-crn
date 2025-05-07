package co.com.ath.model.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(ParamVaultTimeOut.class)
public class ParamVaultTimeOut {

    private int achInquiriesTimeOut;
    private int redInquiriesTimeOut;

}
