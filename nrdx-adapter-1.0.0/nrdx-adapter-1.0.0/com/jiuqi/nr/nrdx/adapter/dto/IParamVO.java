/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.io.tsd.dto.IParam
 */
package com.jiuqi.nr.nrdx.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.io.tsd.dto.IParam;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class IParamVO
implements INRContext {
    private String key;
    private String taskKey;
    private String formSchemeKey;
    private String mappingKey;
    private List<String> unitKeys;
    private List<String> importTypes;
    private String periodValue;
    private List<DimensionValue> filterDim;
    private List<DimensionValue> completionDim;
    private boolean emptyTableCover = true;
    private boolean otherFormDataDelete;
    private String contextEntityId;
    private String contextFilterExpression;
    private String recKey;
    private String dwSchemeKey;
    private boolean doUpload;
    private boolean allowForceUpload;
    private String uploadDes;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public List<String> getUnitKeys() {
        return this.unitKeys;
    }

    public void setUnitKeys(List<String> unitKeys) {
        this.unitKeys = unitKeys;
    }

    public List<String> getImportTypes() {
        return this.importTypes;
    }

    public void setImportTypes(List<String> importTypes) {
        this.importTypes = importTypes;
    }

    public List<DimensionValue> getFilterDim() {
        return this.filterDim;
    }

    public void setFilterDim(List<DimensionValue> filterDim) {
        this.filterDim = filterDim;
    }

    public List<DimensionValue> getCompletionDim() {
        return this.completionDim;
    }

    public void setCompletionDim(List<DimensionValue> completionDim) {
        this.completionDim = completionDim;
    }

    public boolean isEmptyTableCover() {
        return this.emptyTableCover;
    }

    public void setEmptyTableCover(boolean emptyTableCover) {
        this.emptyTableCover = emptyTableCover;
    }

    public boolean isOtherFormDataDelete() {
        return this.otherFormDataDelete;
    }

    public void setOtherFormDataDelete(boolean otherFormDataDelete) {
        this.otherFormDataDelete = otherFormDataDelete;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public boolean isDoUpload() {
        return this.doUpload;
    }

    public void setDoUpload(boolean doUpload) {
        this.doUpload = doUpload;
    }

    public boolean isAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(boolean allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

    public String getUploadDes() {
        return this.uploadDes;
    }

    public void setUploadDes(String uploadDes) {
        this.uploadDes = uploadDes;
    }

    public IParam toIParam() {
        IParam iParam = new IParam();
        iParam.setRecKey(this.recKey);
        iParam.setTaskKey(this.taskKey);
        iParam.setFormSchemeKey(this.formSchemeKey);
        iParam.setMappingKey(this.mappingKey);
        iParam.setUnitKeys(this.unitKeys);
        iParam.setImportTypes(this.importTypes);
        iParam.setFilterDim(this.filterDim);
        iParam.setCompletionDim(this.completionDim);
        iParam.setEmptyTableCover(this.emptyTableCover);
        iParam.setOtherFormDataDelete(this.otherFormDataDelete);
        iParam.setDwSchemeKey(this.dwSchemeKey);
        return iParam;
    }
}

