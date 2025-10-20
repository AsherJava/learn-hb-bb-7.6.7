/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MulCheckConfiguration {
    private boolean openMulCheckBeforeCheck;
    private boolean showMulCheckSwitch;

    @Value(value="${jiuqi.nr.task.openMulCheck:false}")
    public void setOpenMulCheckBeforeCheck(boolean openMulCheckBeforeCheck) {
        this.openMulCheckBeforeCheck = openMulCheckBeforeCheck;
    }

    @Value(value="${jiuqi.nr.workflow.mul-check.enable:false}")
    public void setShowMulCheckSwitch(boolean showMulCheckSwitch) {
        this.showMulCheckSwitch = showMulCheckSwitch;
    }

    public boolean isOpenMulCheckBeforeCheck() {
        return this.openMulCheckBeforeCheck || this.showMulCheckSwitch;
    }
}

