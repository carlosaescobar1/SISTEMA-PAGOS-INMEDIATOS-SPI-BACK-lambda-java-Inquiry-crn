package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * AcctTypeEnum
 * <p>
 * Enum que contiene los posibles tipos de bancos  que
 * podrán ser registrados en el directorio Aval.
 * <p>
 * Desarrollo AVC - SPBVI
 * <p>
 * Creado él: 07 de Mayo de 2025
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
@AllArgsConstructor
@Getter
public enum BankIdEnum {

    BANCO_AV_VILLAS("0052", "Banco AVVillas"),
    BANCO_DE_BOGOTA("0001", "Banco de Bogota"),
    BANCO_DE_OCCIDENTE("0023", "Banco de Occidente"),
    BANCO_POPULAR("0002", "Banco Popular"),
    DALE("0097", "Dale")

    ;

    private final String bankId;
    private final String bankName;

}
