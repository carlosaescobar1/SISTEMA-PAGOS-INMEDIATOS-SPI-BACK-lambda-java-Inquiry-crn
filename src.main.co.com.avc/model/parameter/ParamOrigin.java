package co.com.avc.model.parameter;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Introspected
@SerdeImport(ParamOrigin.class)
public class ParamOrigin {

    private String mobileBanking;
    private String virtualBanking;
    private String offices;

}
