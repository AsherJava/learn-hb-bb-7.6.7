/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import java.util.Map;

public class DeployFixCheckExResult {
    private DeployExType exType;
    private Map<String, String> tmKeyAndLtName;

    public DeployFixCheckExResult() {
    }

    public DeployFixCheckExResult(DeployExType exType, Map<String, String> tmKeyAndLtName) {
        this.exType = exType;
        this.tmKeyAndLtName = tmKeyAndLtName;
    }

    public DeployExType getExType() {
        return this.exType;
    }

    public void setExType(DeployExType exType) {
        this.exType = exType;
    }

    public Map<String, String> getTmKeyAndLtName() {
        return this.tmKeyAndLtName;
    }

    public void setTmKeyAndLtName(Map<String, String> tmKeyAndLtName) {
        this.tmKeyAndLtName = tmKeyAndLtName;
    }
}

