/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.bean;

import java.io.Serializable;

public class RepairParam
implements Serializable {
    private String dataSchemeKey;
    private boolean repairOrg;
    private int threadNumber;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isRepairOrg() {
        return this.repairOrg;
    }

    public void setRepairOrg(boolean repairOrg) {
        this.repairOrg = repairOrg;
    }

    public int getThreadNumber() {
        return this.threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
}

