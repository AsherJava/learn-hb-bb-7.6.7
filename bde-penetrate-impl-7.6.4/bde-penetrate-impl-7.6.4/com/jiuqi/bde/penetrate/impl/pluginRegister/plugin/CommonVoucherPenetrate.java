/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 */
package com.jiuqi.bde.penetrate.impl.pluginRegister.plugin;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.pluginRegister.ApenetrationPlugin;
import com.jiuqi.bde.penetrate.impl.pluginRegister.PenetrateLevelEnum;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommonVoucherPenetrate
implements ApenetrationPlugin {
    @Override
    public String getAppName() {
        return "bde-penetrate-plugin-common";
    }

    @Override
    public String getPluginName() {
        return "COMMON_BALANCE_VOUCHER";
    }

    @Override
    public PenetrateLevelEnum getLevel() {
        return PenetrateLevelEnum.VOUCHER;
    }

    @Override
    public List<ComputationModelEnum> getBizModel() {
        return Arrays.asList(ComputationModelEnum.values());
    }
}

