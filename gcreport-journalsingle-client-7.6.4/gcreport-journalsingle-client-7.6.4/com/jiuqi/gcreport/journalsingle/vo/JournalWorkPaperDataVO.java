/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.gcreport.common.util.MapToPropertyJsonSerializer
 */
package com.jiuqi.gcreport.journalsingle.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.common.util.MapToPropertyJsonSerializer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalWorkPaperDataVO {
    private String subjectCode;
    private String subjectTitle;
    private String beforeZbValue;
    private String afterCalcValue;
    private String afterZbValue;
    @JsonSerialize(using=MapToPropertyJsonSerializer.class)
    private Map<String, BigDecimal> tzValue = new HashMap<String, BigDecimal>();
    private String parentId;
    private String beforZbId;
    private String beforeFormId;
    private String colAndRowLinkNum;
    private List<JournalWorkPaperDataVO> children;

    public List<JournalWorkPaperDataVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<JournalWorkPaperDataVO>();
        }
        return this.children;
    }

    public void setChildren(List<JournalWorkPaperDataVO> children) {
        this.children = children;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getBeforeZbValue() {
        return this.beforeZbValue;
    }

    public void setBeforeZbValue(String beforeZbValue) {
        this.beforeZbValue = beforeZbValue;
    }

    public String getAfterCalcValue() {
        return this.afterCalcValue;
    }

    public void setAfterCalcValue(String afterCalcValue) {
        this.afterCalcValue = afterCalcValue;
    }

    public String getAfterZbValue() {
        return this.afterZbValue;
    }

    public void setAfterZbValue(String afterZbValue) {
        this.afterZbValue = afterZbValue;
    }

    public Map<String, BigDecimal> getTzValue() {
        return this.tzValue;
    }

    public void setTzValue(Map<String, BigDecimal> tzValue) {
        this.tzValue = tzValue;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBeforZbId() {
        return this.beforZbId;
    }

    public void setBeforZbId(String beforZbId) {
        this.beforZbId = beforZbId;
    }

    public String getBeforeFormId() {
        return this.beforeFormId;
    }

    public void setBeforeFormId(String beforeFormId) {
        this.beforeFormId = beforeFormId;
    }

    public String getColAndRowLinkNum() {
        return this.colAndRowLinkNum;
    }

    public void setColAndRowLinkNum(String colAndRowLinkNum) {
        this.colAndRowLinkNum = colAndRowLinkNum;
    }
}

