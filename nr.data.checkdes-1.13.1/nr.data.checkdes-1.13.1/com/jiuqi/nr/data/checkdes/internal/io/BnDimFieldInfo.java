/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.io;

import org.springframework.util.StringUtils;

public class BnDimFieldInfo {
    private String dataFieldKey;
    private String dataTableCode;
    private String dataFieldCode;
    private String refEntityId;
    private String dimName;

    public BnDimFieldInfo(String dataFieldKey, String dataTableCode, String dataFieldCode, String refEntityId, String dimName) {
        this.dataFieldKey = dataFieldKey;
        this.dataTableCode = dataTableCode;
        this.dataFieldCode = dataFieldCode;
        this.refEntityId = refEntityId;
        this.dimName = dimName;
    }

    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }

    public String getDataFieldCode() {
        return this.dataFieldCode;
    }

    public void setDataFieldCode(String dataFieldCode) {
        this.dataFieldCode = dataFieldCode;
    }

    public String getRefEntityId() {
        return this.refEntityId;
    }

    public void setRefEntityId(String refEntityId) {
        this.refEntityId = refEntityId;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getExpDimKey() {
        StringBuilder s = new StringBuilder();
        s.append(this.dataTableCode).append("[").append(this.dataFieldCode);
        if (StringUtils.hasText(this.refEntityId)) {
            s.append("*").append(this.refEntityId);
        }
        s.append("]");
        return s.toString();
    }
}

