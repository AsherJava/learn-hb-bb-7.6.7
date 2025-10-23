/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.dto;

import java.util.Map;

public class EditObject {
    private String taskId;
    private String schemeId;
    private String formGroupId;
    private String formId;
    private boolean showTask;
    private boolean showFormScheme;
    private boolean showMode;
    private String defaultShowMode;
    private String localFormId;
    private boolean showPeriod;
    private boolean formulaCheck;
    private boolean showWildcard;
    private boolean showButtons;
    private String runType;
    private Map<String, Object> params;
    private String editKey;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getFormGroupId() {
        return this.formGroupId;
    }

    public void setFormGroupId(String formGroupId) {
        this.formGroupId = formGroupId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public boolean isShowTask() {
        return this.showTask;
    }

    public void setShowTask(boolean showTask) {
        this.showTask = showTask;
    }

    public boolean isShowFormScheme() {
        return this.showFormScheme;
    }

    public void setShowFormScheme(boolean showFormScheme) {
        this.showFormScheme = showFormScheme;
    }

    public boolean isShowMode() {
        return this.showMode;
    }

    public void setShowMode(boolean showMode) {
        this.showMode = showMode;
    }

    public String getDefaultShowMode() {
        return this.defaultShowMode;
    }

    public void setDefaultShowMode(String defaultShowMode) {
        this.defaultShowMode = defaultShowMode;
    }

    public String getLocalFormId() {
        return this.localFormId;
    }

    public void setLocalFormId(String localFormId) {
        this.localFormId = localFormId;
    }

    public boolean isShowPeriod() {
        return this.showPeriod;
    }

    public void setShowPeriod(boolean showPeriod) {
        this.showPeriod = showPeriod;
    }

    public boolean isFormulaCheck() {
        return this.formulaCheck;
    }

    public void setFormulaCheck(boolean formulaCheck) {
        this.formulaCheck = formulaCheck;
    }

    public boolean isShowWildcard() {
        return this.showWildcard;
    }

    public void setShowWildcard(boolean showWildcard) {
        this.showWildcard = showWildcard;
    }

    public boolean isShowButtons() {
        return this.showButtons;
    }

    public void setShowButtons(boolean showButtons) {
        this.showButtons = showButtons;
    }

    public String getRunType() {
        return this.runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getEditKey() {
        return this.editKey;
    }

    public void setEditKey(String editKey) {
        this.editKey = editKey;
    }
}

