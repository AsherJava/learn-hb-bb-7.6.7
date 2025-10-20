/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo
 */
package com.jiuqi.bde.penetrate.impl.core.intf;

import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateBaseDataInfoProvider;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DefaultPenetrateBaseDataInfoProvider
implements IPenetrateBaseDataInfoProvider {
    @Override
    public String getPluginType() {
        return "DEFAULT_BASEDATAINFOPROVIDER";
    }

    @Override
    public Map<String, Map<String, String>> provideBaseDataInfo(PenetrateFetchSettingInfo fetchSettingInfo) {
        return new HashMap<String, Map<String, String>>();
    }
}

