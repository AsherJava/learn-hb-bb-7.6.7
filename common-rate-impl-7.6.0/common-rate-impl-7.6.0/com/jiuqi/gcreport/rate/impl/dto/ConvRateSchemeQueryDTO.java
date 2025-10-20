/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.rate.impl.dto;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class ConvRateSchemeQueryDTO
extends TenantDO {
    private static final long serialVersionUID = -1248465789460374412L;
    private List<String> subjectCodes;
    private String rowDataId;
    private String subjectCode;

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String getRowDataId() {
        return this.rowDataId;
    }

    public void setRowDataId(String rowDataId) {
        this.rowDataId = rowDataId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}

