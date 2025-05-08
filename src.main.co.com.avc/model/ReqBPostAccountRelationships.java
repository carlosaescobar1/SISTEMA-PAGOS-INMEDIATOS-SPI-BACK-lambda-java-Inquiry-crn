package co.com.avc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Introspected
@SerdeImport(ReqBPostAccountRelationships.class)
public class ReqBPostAccountRelationships {

    /**
     * Información de la cuenta
     */
    @NotNull
    @Valid
    @JsonProperty("XferInfo")
    private XferInfo xferInfo;

}
