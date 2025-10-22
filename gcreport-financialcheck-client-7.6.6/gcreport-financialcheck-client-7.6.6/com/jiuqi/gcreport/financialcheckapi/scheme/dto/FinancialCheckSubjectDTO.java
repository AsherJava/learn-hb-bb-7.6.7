/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.dto;

import java.util.Map;

public class FinancialCheckSubjectDTO {
    private String subject;
    private Map<String, Object> dimension;

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, Object> getDimension() {
        return this.dimension;
    }

    public void setDimension(Map<String, Object> dimension) {
        this.dimension = dimension;
    }
}

