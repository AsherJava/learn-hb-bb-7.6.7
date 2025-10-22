/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.offsetitem.dto.PentrationDataJsonSerializer
 */
package com.jiuqi.gcreport.workingpaper.querytask.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.offsetitem.dto.PentrationDataJsonSerializer;
import java.math.BigDecimal;
import java.util.Map;

public class WorkingPaperSubjectQmsItemDTO {
    private String orgCode;
    private String subjectCode;
    private OrientEnum subjectOrient;
    private BigDecimal zbValue;
    @JsonSerialize(using=PentrationDataJsonSerializer.class)
    private Map<String, String> assistName2CodeMap;
    @JsonSerialize(using=PentrationDataJsonSerializer.class)
    private Map<String, String> assistName2ShowTitleMap;
    @JsonSerialize(using=PentrationDataJsonSerializer.class)
    private Map<String, String> otherColumnsValueMap;

    public static WorkingPaperSubjectQmsItemDTO empty() {
        WorkingPaperSubjectQmsItemDTO emptyItemDTO = new WorkingPaperSubjectQmsItemDTO();
        emptyItemDTO.zbValue = BigDecimal.ZERO;
        return emptyItemDTO;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public BigDecimal getZbValue() {
        return this.zbValue;
    }

    public void setZbValue(BigDecimal zbValue) {
        this.zbValue = zbValue;
    }

    public OrientEnum getSubjectOrient() {
        return this.subjectOrient;
    }

    public void setSubjectOrient(OrientEnum subjectOrient) {
        this.subjectOrient = subjectOrient;
    }

    public Map<String, String> getAssistName2CodeMap() {
        return this.assistName2CodeMap;
    }

    public void setAssistName2CodeMap(Map<String, String> assistName2CodeMap) {
        this.assistName2CodeMap = assistName2CodeMap;
    }

    public Map<String, String> getAssistName2ShowTitleMap() {
        return this.assistName2ShowTitleMap;
    }

    public void setAssistName2ShowTitleMap(Map<String, String> assistName2ShowTitleMap) {
        this.assistName2ShowTitleMap = assistName2ShowTitleMap;
    }

    public Map<String, String> getOtherColumnsValueMap() {
        return this.otherColumnsValueMap;
    }

    public void setOtherColumnsValueMap(Map<String, String> otherColumnsValueMap) {
        this.otherColumnsValueMap = otherColumnsValueMap;
    }
}

