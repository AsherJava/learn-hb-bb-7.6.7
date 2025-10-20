/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckDTO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyLink;
import java.util.ArrayList;
import java.util.List;

public class RegionSurveyParam {
    private String dataSchemeId;
    private String taskId;
    private String formId;
    private String regionId;
    private String schemeId;
    private String hisData;
    private int zbTypeFlag;
    private String questionType;
    private String valueName;
    private List<SurveyLink> links = new ArrayList<SurveyLink>();
    private List<ResponseSurveyCheckDTO> items;
    private String fuzzyKey;
    private String zbKey;

    public String getZbKey() {
        return this.zbKey;
    }

    public void setZbKey(String zbKey) {
        this.zbKey = zbKey;
    }

    public String getFuzzyKey() {
        return this.fuzzyKey;
    }

    public void setFuzzyKey(String fuzzyKey) {
        this.fuzzyKey = fuzzyKey;
    }

    public List<ResponseSurveyCheckDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ResponseSurveyCheckDTO> items) {
        this.items = items;
    }

    public List<SurveyLink> getLinks() {
        return this.links;
    }

    public void setLinks(List<SurveyLink> links) {
        this.links = links;
    }

    public String getValueName() {
        return this.valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public int getZbTypeFlag() {
        return this.zbTypeFlag;
    }

    public void setZbTypeFlag(int zbTypeFlag) {
        this.zbTypeFlag = zbTypeFlag;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getHisData() {
        return this.hisData;
    }

    public void setHisData(String hisData) {
        this.hisData = hisData;
    }

    public String getDataSchemeId() {
        return this.dataSchemeId;
    }

    public void setDataSchemeId(String dataSchemeId) {
        this.dataSchemeId = dataSchemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}

