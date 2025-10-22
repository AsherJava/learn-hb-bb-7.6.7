/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EntityCheckConfigData
implements IConfigModel {
    private boolean entityCheckEnable;
    private List<ConfigInfo> configInfos;

    public List<ConfigInfo> getConfigInfos() {
        return this.configInfos;
    }

    public void setConfigInfos(List<ConfigInfo> configInfos) {
        this.configInfos = configInfos;
    }

    public boolean getEntityCheckEnable() {
        return this.entityCheckEnable;
    }

    public void setEnable(boolean enable) {
        this.entityCheckEnable = enable;
    }
}

