/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.data.common.param.EntityValue
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.data.text;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.data.common.param.EntityValue;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.List;

public class FieldDataSaveParam {
    private ImpMode mode;
    private DimensionCollection masterKey;
    private List<Variable> variables;
    private ResouceType authMode = ResouceType.FORM;
    private EntityValue entityValue;
    private boolean allIllegalDataImport = true;
    private FilterDim filterDim;
    private CompletionDim completionDim;
    private String filterCondition;
    List<DataField> dataFieldsInAimRegion;

    public EntityValue getEntityValue() {
        return this.entityValue;
    }

    public void setEntityValue(EntityValue entityValue) {
        this.entityValue = entityValue;
    }

    public ImpMode getMode() {
        return this.mode;
    }

    public void setMode(ImpMode mode) {
        this.mode = mode;
    }

    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public ResouceType getAuthMode() {
        return this.authMode;
    }

    public void setAuthMode(ResouceType authMode) {
        this.authMode = authMode;
    }

    public boolean isAllIllegalDataImport() {
        return this.allIllegalDataImport;
    }

    public void setAllIllegalDataImport(boolean allIllegalDataImport) {
        this.allIllegalDataImport = allIllegalDataImport;
    }

    public FilterDim getFilterDim() {
        return this.filterDim;
    }

    public void setFilterDim(FilterDim filterDim) {
        this.filterDim = filterDim;
    }

    public CompletionDim getCompletionDim() {
        return this.completionDim;
    }

    public void setCompletionDim(CompletionDim completionDim) {
        this.completionDim = completionDim;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public List<DataField> getDataFieldsInAimRegion() {
        return this.dataFieldsInAimRegion;
    }

    public void setDataFieldsInAimRegion(List<DataField> dataFieldsInAimRegion) {
        this.dataFieldsInAimRegion = dataFieldsInAimRegion;
    }
}

