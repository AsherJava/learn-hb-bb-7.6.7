/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;

public class IParam {
    private String recKey;
    private String taskKey;
    private String formSchemeKey;
    private String mappingKey;
    private List<String> unitKeys;
    private List<String> importTypes;
    private List<DimensionValue> filterDim;
    private List<DimensionValue> completionDim;
    private boolean emptyTableCover;
    private boolean otherFormDataDelete;
    private String dwSchemeKey;

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
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

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }
}

