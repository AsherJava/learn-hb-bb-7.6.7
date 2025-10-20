/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo
 */
package com.jiuqi.bde.penetrate.impl.model;

import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import java.util.Map;

public interface IPenetrateBaseDataInfoProvider {
    public static final String DEFAULT_KEY = "DEFAULT_BASEDATAINFOPROVIDER";

    public String getPluginType();

    public Map<String, Map<String, String>> provideBaseDataInfo(PenetrateFetchSettingInfo var1);
}

