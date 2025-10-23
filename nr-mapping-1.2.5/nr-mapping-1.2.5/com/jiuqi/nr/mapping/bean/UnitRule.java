/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.bean;

import com.jiuqi.nr.mapping.common.UnitRuleType;

public class UnitRule {
    private String key;
    private String mrKey;
    private UnitRuleType type;
    private String express;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMrKey() {
        return this.mrKey;
    }

    public void setMrKey(String mrKey) {
        this.mrKey = mrKey;
    }

    public UnitRuleType getType() {
        return this.type;
    }

    public void setType(UnitRuleType type) {
        this.type = type;
    }

    public String getExpress() {
        return this.express;
    }

    public void setExpress(String express) {
        this.express = express;
    }
}

