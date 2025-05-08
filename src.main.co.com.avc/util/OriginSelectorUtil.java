package co.com.avc.util;

import co.com.avc.constants.ChannelEnum;
import co.com.avc.model.parameter.ParamOrigin;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@AllArgsConstructor
public class OriginSelectorUtil {

    private final ParamOrigin paramOrigin;

    private Map<String, Supplier<String>> channelMap;

    public OriginSelectorUtil(ParamOrigin paramOrigin) {
        this.paramOrigin = paramOrigin;
        initializeChannelMap();
    }

    private void initializeChannelMap() {
        channelMap = new HashMap<>();
        channelMap.put(ChannelEnum.MB.name(), paramOrigin::getMobileBanking);
        channelMap.put(ChannelEnum.PB.name(), paramOrigin::getVirtualBanking);
        channelMap.put(ChannelEnum.OFVV.name(), paramOrigin::getOffices);
    }

    public String originSelector(String channel) {
        Supplier<String> supplier = channelMap.get(channel.toUpperCase());
        return (supplier != null) ? supplier.get() : null; // O manejar el caso de no encontrado
    }

}
