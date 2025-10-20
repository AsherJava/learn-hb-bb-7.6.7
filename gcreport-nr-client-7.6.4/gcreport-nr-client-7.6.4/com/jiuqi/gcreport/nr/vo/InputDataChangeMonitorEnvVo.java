/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.vo;

public class InputDataChangeMonitorEnvVo {
    public static final String VARNAME_INPUTDATA_CHANGEMONITOR_ENV_VO = "INPUTDATA_CHANGEMONITOR_ENV_VO";
    private boolean isCalcField = true;
    private boolean isRealTimeOffSet = true;

    public InputDataChangeMonitorEnvVo() {
    }

    public InputDataChangeMonitorEnvVo(boolean isCalcField, boolean isRealTimeOffSet) {
        this.isCalcField = isCalcField;
        this.isRealTimeOffSet = isRealTimeOffSet;
    }

    public static InputDataChangeMonitorEnvVo defaultEnv() {
        return new InputDataChangeMonitorEnvVo(true, true);
    }

    public boolean isCalcField() {
        return this.isCalcField;
    }

    public void setCalcField(boolean calcField) {
        this.isCalcField = calcField;
    }

    public boolean isRealTimeOffSet() {
        return this.isRealTimeOffSet;
    }

    public void setRealTimeOffSet(boolean realTimeOffSet) {
        this.isRealTimeOffSet = realTimeOffSet;
    }
}

