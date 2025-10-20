/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.penetrate.impl.pluginRegister;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.pluginRegister.PenetrateLevelEnum;
import java.util.List;

public interface ApenetrationPlugin {
    default public String getProdLine() {
        return "@bde";
    }

    public String getAppName();

    public String getPluginName();

    public PenetrateLevelEnum getLevel();

    public List<ComputationModelEnum> getBizModel();

    default public String getDataSchemeCode() {
        return null;
    }

    default public List<String> getAccountSoftwareType() {
        return null;
    }
}

