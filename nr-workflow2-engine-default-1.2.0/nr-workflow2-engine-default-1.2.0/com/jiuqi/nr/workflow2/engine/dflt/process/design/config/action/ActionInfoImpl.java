/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo;

public class ActionInfoImpl
implements ActionInfo {
    private String buttonName;
    private String stateName;

    @Override
    public String getButtonName() {
        return this.buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    @Override
    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}

