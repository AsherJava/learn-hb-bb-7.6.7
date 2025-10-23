/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.web.facade.FixItem;
import java.util.Map;

public class FixParamVO
extends FixItem {
    private DeployExType exType;
    private String exDesc;
    private int fixParamValue;
    private Map<String, String> tmKeyAndLtName;

    public DeployExType getExType() {
        return this.exType;
    }

    public void setExType(DeployExType exType) {
        this.exType = exType;
    }

    public String getExDesc() {
        return this.exDesc;
    }

    public void setExDesc(String exDesc) {
        this.exDesc = exDesc;
    }

    public Map<String, String> getTmKeyAndLtName() {
        return this.tmKeyAndLtName;
    }

    public void setTmKeyAndLtName(Map<String, String> tmKeyAndLtName) {
        this.tmKeyAndLtName = tmKeyAndLtName;
    }

    public int getFixParamValue() {
        return this.fixParamValue;
    }

    public void setFixParamValue(int fixParamValue) {
        this.fixParamValue = fixParamValue;
    }
}

