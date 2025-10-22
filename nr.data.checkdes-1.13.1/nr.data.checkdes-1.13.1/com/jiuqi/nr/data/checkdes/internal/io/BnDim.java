/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.io;

import org.springframework.util.StringUtils;

public class BnDim {
    public static final String BIZ_KEY_DIM = "ID";
    public static final String ENTITY_SEPARATOR = "*";
    private boolean bizKey;
    private String dataTableCode;
    private String dataFieldCode;
    private String refEntityId;
    private String dimValue;

    public BnDim(boolean bizKey, String dataTableCode, String dataFieldCode, String refEntityId, String dimValue) {
        this.bizKey = bizKey;
        this.dataTableCode = dataTableCode;
        this.dataFieldCode = dataFieldCode;
        this.refEntityId = refEntityId;
        this.dimValue = dimValue;
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

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public boolean isBizKey() {
        return this.bizKey;
    }

    public void setBizKey(boolean bizKey) {
        this.bizKey = bizKey;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        if (this.bizKey) {
            s.append(BIZ_KEY_DIM);
        } else {
            s.append(this.dataTableCode).append("[").append(this.dataFieldCode);
            if (StringUtils.hasText(this.refEntityId)) {
                s.append(ENTITY_SEPARATOR).append(this.refEntityId);
            }
            s.append("]");
        }
        s.append(":").append(this.dimValue);
        return s.toString();
    }
}

