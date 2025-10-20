/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package com.jiuqi.nr.analysisreport.helper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenerateContext {
    private List<Map<String, String>> unitMapList;
    private String calendarCode;
    private ReportBaseVO.PeriodDim periodDim;
    private List<Map<String, String>> periodMapList;
    private JsonObject template;
    private String fullContent;
    private Map<String, String> variableValueMap = new ConcurrentHashMap<String, String>();
    private String qcyProjectId;
    private Boolean isScientific;

    public List<Map<String, String>> getUnitMapList() {
        if (this.unitMapList == null) {
            this.unitMapList = new ArrayList<Map<String, String>>();
        }
        return this.unitMapList;
    }

    public void setPeriodCode(String periodCode) {
        this.calendarCode = periodCode;
    }

    public String getPeriodCode() {
        return this.calendarCode;
    }

    public ReportBaseVO.PeriodDim getPeriodDim() {
        return this.periodDim;
    }

    public void setPeriodDim(ReportBaseVO.PeriodDim periodDim) {
        this.periodDim = periodDim;
    }

    public List<Map<String, String>> getPeriodMapList() {
        if (this.periodMapList == null) {
            this.periodMapList = new ArrayList<Map<String, String>>();
        }
        return this.periodMapList;
    }

    public void setPeriodMapList(List<Map<String, String>> periodMapList) {
        this.periodMapList = periodMapList;
    }

    public void setTemplate(JsonObject template) {
        this.template = template;
    }

    public void setTemplate(String template) {
        Gson gson = new Gson();
        this.template = (JsonObject)gson.fromJson(template, JsonObject.class);
    }

    public void setPrintData(JsonObject printData) {
        JsonElement element = printData.has("template") ? printData.get("template") : printData.get("info");
        this.template = element.getAsJsonObject();
    }

    public void setPrintData(String printData) {
        Gson gson = new Gson();
        JsonObject print = (JsonObject)gson.fromJson(printData, JsonObject.class);
        JsonElement element = print.has("template") ? print.get("template") : print.get("info");
        this.template = element.getAsJsonObject();
    }

    public JsonObject getTemplate() {
        return this.template;
    }

    public Integer getOrientation() {
        if (this.template != null) {
            return !this.template.has("orientation") ? 0 : this.template.get("orientation").getAsInt();
        }
        return null;
    }

    public String getPaperType() {
        if (this.template != null) {
            return this.template.get("paperType").getAsString();
        }
        return null;
    }

    public String getPaperWidth() {
        if (this.template != null) {
            return this.template.get("paperWidth").getAsString();
        }
        return null;
    }

    public String getPaperHeight() {
        if (this.template != null) {
            return this.template.get("paperHeight").getAsString();
        }
        return null;
    }

    public String getMarginTop() {
        if (this.template != null) {
            return this.template.get("marginTop").getAsString();
        }
        return null;
    }

    public String getMarginBottom() {
        if (this.template != null) {
            return this.template.get("marginBottom").getAsString();
        }
        return null;
    }

    public String getMarginLeft() {
        if (this.template != null) {
            return this.template.get("marginLeft").getAsString();
        }
        return null;
    }

    public String getQcyProjectId() {
        return this.qcyProjectId;
    }

    public void setQcyProjectId(String qcyProjectId) {
        this.qcyProjectId = qcyProjectId;
    }

    public Boolean getIsScientific() {
        return this.isScientific;
    }

    public void setIsScientific(Boolean isScientific) {
        this.isScientific = isScientific;
    }

    public String getFullContent() {
        return this.fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    public Map<String, String> getVariableValueMap() {
        return this.variableValueMap;
    }

    public void setVariableValueMap(Map<String, String> variableValueMap) {
        this.variableValueMap = variableValueMap;
    }
}

