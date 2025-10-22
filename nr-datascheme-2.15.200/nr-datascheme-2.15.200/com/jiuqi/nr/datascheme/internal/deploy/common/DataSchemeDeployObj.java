/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataSchemeDeployObj {
    private DeployType state = DeployType.NONE;
    private DataScheme dataScheme;
    private DeployType dimState = DeployType.NONE;
    private Collection<DataGroup> addDataGroups;
    private Collection<DataGroup> updateDataGroups;
    private Collection<String> deleteDataGroupKeys;

    public DataSchemeDeployObj() {
        this.state = DeployType.NONE;
    }

    public DeployType getState() {
        return this.state;
    }

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public boolean needUpdateDataGroup() {
        int i = 0;
        if (null != this.addDataGroups) {
            i += this.addDataGroups.size();
        }
        if (null != this.updateDataGroups) {
            i += this.updateDataGroups.size();
        }
        if (null != this.deleteDataGroupKeys) {
            i += this.deleteDataGroupKeys.size();
        }
        return 0 != i;
    }

    public void setDataScheme(DataScheme dataScheme, DeployType state) {
        this.dataScheme = dataScheme;
        this.state = state;
    }

    public DeployType getDimState() {
        return this.dimState;
    }

    public void setDimState(DeployType dimState) {
        this.dimState = dimState;
    }

    public Collection<DataGroup> getAddDataGroups() {
        return this.addDataGroups;
    }

    public void addAddDataGroup(DataGroup addDataGroup) {
        if (null == this.addDataGroups) {
            this.addDataGroups = new ArrayList<DataGroup>();
        }
        this.addDataGroups.add(addDataGroup);
    }

    public void addAddDataGroup(List<? extends DataGroup> addDataGroups) {
        if (null == this.addDataGroups) {
            this.addDataGroups = new ArrayList<DataGroup>();
        }
        if (null != addDataGroups && !addDataGroups.isEmpty()) {
            this.addDataGroups.addAll(addDataGroups);
        }
    }

    public Collection<DataGroup> getUpdateDataGroups() {
        return this.updateDataGroups;
    }

    public void addUpdateDataGroup(DataGroup updateDataGroup) {
        if (null == this.updateDataGroups) {
            this.updateDataGroups = new ArrayList<DataGroup>();
        }
        this.updateDataGroups.add(updateDataGroup);
    }

    public Collection<String> getDeleteDataGroupKeys() {
        return this.deleteDataGroupKeys;
    }

    public void addDeleteDataGroupKey(String deleteDataGroupKey) {
        if (null == this.deleteDataGroupKeys) {
            this.deleteDataGroupKeys = new ArrayList<String>();
        }
        this.deleteDataGroupKeys.add(deleteDataGroupKey);
    }

    public void addDeleteDataGroupKey(Collection<String> deleteDataGroupKeys) {
        if (null == this.deleteDataGroupKeys) {
            this.deleteDataGroupKeys = new ArrayList<String>();
        }
        if (null != deleteDataGroupKeys && !deleteDataGroupKeys.isEmpty()) {
            this.deleteDataGroupKeys.addAll(deleteDataGroupKeys);
        }
    }

    public List<DesignCatalogModelDefine> getAddCatalogModels() {
        if (DeployType.ADD.equals((Object)this.state)) {
            return DataSchemeDeployHelper.createCatalogModel(this.dataScheme, this.addDataGroups);
        }
        return DataSchemeDeployHelper.createCatalogModel(this.addDataGroups);
    }

    public List<DesignCatalogModelDefine> getUpdateCatalogModels() {
        if (DeployType.UPDATE.equals((Object)this.state)) {
            return DataSchemeDeployHelper.createCatalogModel(this.dataScheme, this.updateDataGroups);
        }
        return DataSchemeDeployHelper.createCatalogModel(this.updateDataGroups);
    }

    public List<String> getDeleteCalalogModelKeys() {
        ArrayList<Object> result = null;
        result = null == this.deleteDataGroupKeys ? new ArrayList() : new ArrayList<String>(this.deleteDataGroupKeys);
        if (DeployType.DELETE.equals((Object)this.state)) {
            result.add(0, this.dataScheme.getKey());
        }
        return result;
    }
}

