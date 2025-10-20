/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf;

import com.jiuqi.common.base.util.StringUtils;

public class EtlLoadUnitKey {
    private String taskKey;
    private String schemeKey;
    private String dataTime;
    private String currency;
    private String unitCode;
    private String logicKey;

    public EtlLoadUnitKey(String taskKey, String schemeKey, String dataTime, String currency, String unitCode) {
        this.taskKey = taskKey;
        this.schemeKey = schemeKey;
        this.dataTime = dataTime;
        this.currency = StringUtils.isEmpty((String)currency) ? "#" : currency;
        this.unitCode = unitCode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public String getLogicKey() {
        if (this.logicKey == null) {
            this.logicKey = this.generateLogicKey();
        }
        return this.logicKey;
    }

    public boolean equals(Object obj) {
        if (obj instanceof EtlLoadUnitKey) {
            EtlLoadUnitKey key = (EtlLoadUnitKey)obj;
            return key.getLogicKey().equals(this.getLogicKey());
        }
        return false;
    }

    public int hashCode() {
        return this.getLogicKey().hashCode();
    }

    public String generateLogicKey() {
        StringBuffer logicKey = new StringBuffer();
        logicKey.append(this.taskKey).append("|");
        logicKey.append(this.schemeKey).append("|");
        logicKey.append(this.dataTime).append("|");
        logicKey.append(this.currency).append("|");
        logicKey.append(this.unitCode).append("|");
        return logicKey.toString();
    }
}

