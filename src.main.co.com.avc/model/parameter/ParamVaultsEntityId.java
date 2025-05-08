package co.com.avc.model.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(ParamVaultsEntityId.class)
public class ParamVaultsEntityId {

    private String redEntityId;
    private String achEntityId;

}
