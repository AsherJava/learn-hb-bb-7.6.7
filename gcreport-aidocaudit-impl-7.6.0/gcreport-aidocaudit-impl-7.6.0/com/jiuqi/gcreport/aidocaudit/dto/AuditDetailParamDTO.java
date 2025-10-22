/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

public class AuditDetailParamDTO {
    private String id;
    private int resultType;

    public AuditDetailParamDTO(String id, int resultType) {
        this.id = id;
        this.resultType = resultType;
    }

    public AuditDetailParamDTO() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }
}

