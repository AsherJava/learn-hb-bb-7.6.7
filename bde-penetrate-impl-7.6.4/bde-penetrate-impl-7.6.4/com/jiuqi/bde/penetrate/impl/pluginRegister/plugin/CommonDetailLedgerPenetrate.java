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
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CommonDetailLedgerPenetrate
implements ApenetrationPlugin {
    private static final List<ComputationModelEnum> list = Arrays.stream(ComputationModelEnum.values()).filter(cmEnum -> !cmEnum.getCode().equals(ComputationModelEnum.CUSTOMFETCH.getCode())).collect(Collectors.toList());

    @Override
    public String getAppName() {
        return "bde-penetrate-plugin-common";
    }

    @Override
    public String getPluginName() {
        return "COMMON_BALANCE_DETAIL_LEDGER";
    }

    @Override
    public PenetrateLevelEnum getLevel() {
        return PenetrateLevelEnum.DETAIL_LEDGER;
    }

    @Override
    public List<ComputationModelEnum> getBizModel() {
        return list;
    }
}

