/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.ProgressInfos;
import com.jiuqi.nr.designer.web.facade.TaskObj;
import com.jiuqi.nr.designer.web.facade.simple.SimpleFormGroupObj;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import com.jiuqi.nr.designer.web.treebean.PrintSchemeObject;
import java.util.List;
import java.util.Map;

public class PreloadData {
    @JsonProperty(value="ActivedFormId")
    private String activedFormId;
    @JsonProperty(value="ActivedGroupId")
    private String activedGroupId;
    @JsonProperty(value="ActivedSchemeId")
    private String activedSchemeId;
    @JsonProperty(value="ActivedFormulaId")
    private String activedFormulaId;
    @JsonProperty(value="TaskObj")
    private TaskObj taskObj;
    @JsonProperty(value="FormSchemes")
    private Map<String, FormSchemeObj> formSchemes;
    @JsonProperty(value="SimpleFormGroupObjs")
    private List<SimpleFormGroupObj> simpleFormGroupObjs;
    @JsonProperty(value="CurrentFormGroupObj")
    private FormGroupObject currentFormGroupObj;
    @JsonProperty(value="CurrentFormObj")
    private FormObj currentFormObj;
    @JsonProperty(value="FormulaSchemes")
    private Map<String, FormulaSchemeObject> formulaSchemes;
    @JsonProperty(value="PrintSchemes")
    private Map<String, PrintSchemeObject> printSchemes;
    @JsonProperty(value="ProgressInfos")
    private ProgressInfos progressInfos;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="IsPublishTask")
    private boolean isPublishTask;

    public TaskObj getTaskObj() {
        return this.taskObj;
    }

    public void setTaskObj(TaskObj taskObj) {
        this.taskObj = taskObj;
    }

    public String getActivedFormId() {
        return this.activedFormId;
    }

    public void setActivedFormId(String activedFormId) {
        this.activedFormId = activedFormId;
    }

    public String getActivedGroupId() {
        return this.activedGroupId;
    }

    public void setActivedGroupId(String activedGroupId) {
        this.activedGroupId = activedGroupId;
    }

    public String getActivedSchemeId() {
        return this.activedSchemeId;
    }

    public void setActivedSchemeId(String activedSchemeId) {
        this.activedSchemeId = activedSchemeId;
    }

    public String getActivedFormulaId() {
        return this.activedFormulaId;
    }

    public void setActivedFormulaId(String activedFormulaId) {
        this.activedFormulaId = activedFormulaId;
    }

    public Map<String, FormSchemeObj> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(Map<String, FormSchemeObj> formSchemes) {
        this.formSchemes = formSchemes;
    }

    public List<SimpleFormGroupObj> getSimpleFormGroupObjs() {
        return this.simpleFormGroupObjs;
    }

    public void setSimpleFormGroupObjs(List<SimpleFormGroupObj> simpleFormGroupObjs) {
        this.simpleFormGroupObjs = simpleFormGroupObjs;
    }

    public FormGroupObject getCurrentFormGroupObj() {
        return this.currentFormGroupObj;
    }

    public void setCurrentFormGroupObj(FormGroupObject currentFormGroupObj) {
        this.currentFormGroupObj = currentFormGroupObj;
    }

    public FormObj getCurrentFormObj() {
        return this.currentFormObj;
    }

    public void setCurrentFormObj(FormObj currentFormObj) {
        this.currentFormObj = currentFormObj;
    }

    public Map<String, FormulaSchemeObject> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public void setFormulaSchemes(Map<String, FormulaSchemeObject> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public Map<String, PrintSchemeObject> getPrintSchemes() {
        return this.printSchemes;
    }

    public void setPrintSchemes(Map<String, PrintSchemeObject> printSchemes) {
        this.printSchemes = printSchemes;
    }

    public ProgressInfos getProgressInfos() {
        return this.progressInfos;
    }

    public void setProgressInfos(ProgressInfos progressInfos) {
        this.progressInfos = progressInfos;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public boolean getIsPublishTask() {
        return this.isPublishTask;
    }

    public void setIsPublishTask(boolean publishTask) {
        this.isPublishTask = publishTask;
    }
}

