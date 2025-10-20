/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.param;

import com.jiuqi.nr.designer.web.rest.param.SurveyQuestionPM;
import java.util.List;

public class SurveyZBTreePM {
    private String dataSchemeId;
    private int zbTypeFlag;
    private String questionType;
    private SurveyQuestionPM questionParam;
    private int nodeType;
    private String currentNodeKey;
    private String valueName;
    private String zbKey;
    private String fuzzyKey;
    private List<List<String>> disabledZbs;

    public String getDataSchemeId() {
        return this.dataSchemeId;
    }

    public void setDataSchemeId(String dataSchemeId) {
        this.dataSchemeId = dataSchemeId;
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

    public SurveyQuestionPM getQuestionParam() {
        return this.questionParam;
    }

    public void setQuestionParam(SurveyQuestionPM questionParam) {
        this.questionParam = questionParam;
    }

    public int getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getCurrentNodeKey() {
        return this.currentNodeKey;
    }

    public void setCurrentNodeKey(String currentNodeKey) {
        this.currentNodeKey = currentNodeKey;
    }

    public String getValueName() {
        return this.valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

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

    public List<List<String>> getDisabledZbs() {
        return this.disabledZbs;
    }

    public void setDisabledZbs(List<List<String>> disabledZbs) {
        this.disabledZbs = disabledZbs;
    }
}

