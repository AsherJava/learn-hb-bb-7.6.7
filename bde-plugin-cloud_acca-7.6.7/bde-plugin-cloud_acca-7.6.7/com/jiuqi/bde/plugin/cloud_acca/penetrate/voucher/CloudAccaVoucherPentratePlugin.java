/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.penetrate.impl.pluginRegister.ApenetrationPlugin
 *  com.jiuqi.bde.penetrate.impl.pluginRegister.PenetrateLevelEnum
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.voucher;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.pluginRegister.ApenetrationPlugin;
import com.jiuqi.bde.penetrate.impl.pluginRegister.PenetrateLevelEnum;
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CloudAccaVoucherPentratePlugin
implements ApenetrationPlugin {
    public String getAppName() {
        return "bde-penetrate-plugin-egas";
    }

    public String getPluginName() {
        return "CLOUDACCA_BALANCE_VOUCHER";
    }

    public PenetrateLevelEnum getLevel() {
        return PenetrateLevelEnum.VOUCHER;
    }

    public List<ComputationModelEnum> getBizModel() {
        LinkedList<ComputationModelEnum> bizModelEnumList = new LinkedList<ComputationModelEnum>();
        bizModelEnumList.add(ComputationModelEnum.BALANCE);
        bizModelEnumList.add(ComputationModelEnum.ASSBALANCE);
        return bizModelEnumList;
    }

    public List<String> getAccountSoftwareType() {
        ArrayList<String> typeList = new ArrayList<String>();
        BdeCloudAccaPluginType cloudAccaPluginType = new BdeCloudAccaPluginType();
        typeList.add(cloudAccaPluginType.getSymbol());
        return typeList;
    }
}

