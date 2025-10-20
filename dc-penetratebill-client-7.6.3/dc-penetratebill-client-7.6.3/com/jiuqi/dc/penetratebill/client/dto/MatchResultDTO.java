/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.client.dto;

public class MatchResultDTO {
    private boolean match;
    private String schemeId;
    private String billNoField;

    public MatchResultDTO() {
    }

    public MatchResultDTO(boolean match, String schemeId, String billNoField) {
        this.match = match;
        this.schemeId = schemeId;
        this.billNoField = billNoField;
    }

    public boolean isMatch() {
        return this.match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getBillNoField() {
        return this.billNoField;
    }

    public void setBillNoField(String billNoField) {
        this.billNoField = billNoField;
    }
}

