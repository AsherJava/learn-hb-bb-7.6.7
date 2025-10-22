/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.designer.sync.IAction;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.vo.FormSaveObjectDeserializer;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using=FormSaveObjectDeserializer.class)
public class FormSaveObject {
    private boolean ifUpdateFormula;
    private String progressId;
    private FormObj formData;
    private Map<Integer, Grid2Data> gridMap;
    private String activedSchemeId;
    private List<IAction> syncActions;
    private List<FormulaObj> formulaObjs;

    public List<FormulaObj> getFormulaObjs() {
        return this.formulaObjs;
    }

    public void setFormulaObjs(List<FormulaObj> formulaObjs) {
        this.formulaObjs = formulaObjs;
    }

    public FormObj getFormData() {
        return this.formData;
    }

    public void setFormData(FormObj formData) {
        this.formData = formData;
    }

    public Map<Integer, Grid2Data> getGridMap() {
        return this.gridMap;
    }

    public void setGridMap(Map<Integer, Grid2Data> gridMap) {
        this.gridMap = gridMap;
    }

    public String getActivedSchemeId() {
        return this.activedSchemeId;
    }

    public void setActivedSchemeId(String activedSchemeId) {
        this.activedSchemeId = activedSchemeId;
    }

    public List<IAction> getSyncActions() {
        return this.syncActions;
    }

    public void setSyncActions(List<IAction> syncActions) {
        this.syncActions = syncActions;
    }

    public boolean isIfUpdateFormula() {
        return this.ifUpdateFormula;
    }

    public void setIfUpdateFormula(boolean ifUpdateFormula) {
        this.ifUpdateFormula = ifUpdateFormula;
    }

    public String getProgressId() {
        return this.progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }
}

