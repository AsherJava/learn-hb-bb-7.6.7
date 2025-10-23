/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextExpendParam {
    private String dimensionName;
    private List<String> formGroupLists = new ArrayList<String>();
    private UserInfoParam userInfoParam;
    private WorkFlowType workFlowType;
    private Map<String, EntityInfoParam> units = new HashMap<String, EntityInfoParam>();
    private Map<String, List<DimensionValueSet>> notNeedImportFormMaps = new HashMap<String, List<DimensionValueSet>>();
    private List<String> needImportForm = new ArrayList<String>();
    private List<String> notNeedImportUnit = new ArrayList<String>();
    private List<String> noExistUnit = new ArrayList<String>();
    boolean addUnitUpload = false;
    private String filePath;
    private String dw;
    private String entityCode;
    private boolean isNrd;
    private List<String> srcDimCodes;
    private List<Variable> variables;
    private boolean notImportFMDMEntity;
    Set<String> exportData;
    boolean allEntity;
    boolean hasWorkFlowData;
    ImpMode mode;
    private DimensionValueSet dimensionValueSetWithAllDim;

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public List<String> getFormGroupLists() {
        return this.formGroupLists;
    }

    public void setFormGroupLists(List<String> formGroupLists) {
        this.formGroupLists = formGroupLists;
    }

    public UserInfoParam getUserInfoParam() {
        return this.userInfoParam;
    }

    public void setUserInfoParam(UserInfoParam userInfoParam) {
        this.userInfoParam = userInfoParam;
    }

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public Map<String, EntityInfoParam> getUnits() {
        return this.units;
    }

    public void setUnits(Map<String, EntityInfoParam> units) {
        this.units = units;
    }

    public Map<String, List<DimensionValueSet>> getNotNeedImportFormMaps() {
        return this.notNeedImportFormMaps;
    }

    public void setNotNeedImportFormMaps(Map<String, List<DimensionValueSet>> notNeedImportFormMaps) {
        this.notNeedImportFormMaps = notNeedImportFormMaps;
    }

    public List<String> getNeedImportForm() {
        return this.needImportForm;
    }

    public void setNeedImportForm(List<String> needImportForm) {
        this.needImportForm = needImportForm;
    }

    public List<String> getNoExistUnit() {
        return this.noExistUnit;
    }

    public void setNoExistUnit(List<String> noExistUnit) {
        this.noExistUnit = noExistUnit;
    }

    public boolean isAddUnitUpload() {
        return this.addUnitUpload;
    }

    public void setAddUnitUpload(boolean addUnitUpload) {
        this.addUnitUpload = addUnitUpload;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public boolean isNrd() {
        return this.isNrd;
    }

    public boolean getIsNrd() {
        return this.isNrd;
    }

    public void setNrd(boolean nrd) {
        this.isNrd = nrd;
    }

    public List<String> getSrcDimCodes() {
        return this.srcDimCodes;
    }

    public void setSrcDimCodes(List<String> srcDimCodes) {
        this.srcDimCodes = srcDimCodes;
    }

    public List<String> getNotNeedImportUnit() {
        return this.notNeedImportUnit;
    }

    public void setNotNeedImportUnit(List<String> notNeedImportUnit) {
        this.notNeedImportUnit = notNeedImportUnit;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public Set<String> getExportData() {
        return this.exportData;
    }

    public void setExportData(Set<String> exportData) {
        this.exportData = exportData;
    }

    public boolean isNotImportFMDMEntity() {
        return this.notImportFMDMEntity;
    }

    public boolean getNotImportFMDMEntity() {
        return this.notImportFMDMEntity;
    }

    public void setNotImportFMDMEntity(boolean notImportFMDMEntity) {
        this.notImportFMDMEntity = notImportFMDMEntity;
    }

    public boolean isAllEntity() {
        return this.allEntity;
    }

    public boolean getAllEntity() {
        return this.allEntity;
    }

    public void setAllEntity(boolean allEntity) {
        this.allEntity = allEntity;
    }

    public boolean isHasWorkFlowData() {
        return this.hasWorkFlowData;
    }

    public boolean getHasWorkFlowData() {
        return this.hasWorkFlowData;
    }

    public void setHasWorkFlowData(boolean hasWorkFlowData) {
        this.hasWorkFlowData = hasWorkFlowData;
    }

    public ImpMode getMode() {
        return this.mode;
    }

    public void setMode(ImpMode mode) {
        this.mode = mode;
    }

    public DimensionValueSet getDimensionValueSetWithAllDim() {
        return this.dimensionValueSetWithAllDim;
    }

    public void setDimensionValueSetWithAllDim(DimensionValueSet dimensionValueSetWithAllDim) {
        this.dimensionValueSetWithAllDim = dimensionValueSetWithAllDim;
    }
}

