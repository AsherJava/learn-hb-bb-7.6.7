/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rewritesetting.dto;

public class RewriteFieldMappingDTO {
    private String type;
    private String zbField;
    private String offsetField;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZbField() {
        return this.zbField;
    }

    public void setZbField(String zbField) {
        this.zbField = zbField;
    }

    public String getOffsetField() {
        return this.offsetField;
    }

    public void setOffsetField(String offsetField) {
        this.offsetField = offsetField;
    }
}

