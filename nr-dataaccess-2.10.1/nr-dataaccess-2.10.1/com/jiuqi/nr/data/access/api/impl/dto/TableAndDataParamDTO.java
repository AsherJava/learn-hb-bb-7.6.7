/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.access.api.impl.dto;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;

public class TableAndDataParamDTO {
    private DataAccessContext dataContext;
    private String entityId;
    private TableModelDefine table;
    private String dwDimensionName;
    private String periodDimensionName;
    private List<ColumnModelDefine> keys = new ArrayList<ColumnModelDefine>();
    private List<String> dimNames = new ArrayList<String>();
    private int isLockColIndex = -1;
    private List<DimensionCombination> collections;
    private DimensionValueSet masterKey;

    public DataAccessContext getDataContext() {
        return this.dataContext;
    }

    public void setDataContext(DataAccessContext dataContext) {
        this.dataContext = dataContext;
    }

    public TableModelDefine getTable() {
        return this.table;
    }

    public void setTable(TableModelDefine table) {
        this.table = table;
    }

    public String getDwDimensionName() {
        return this.dwDimensionName;
    }

    public void setDwDimensionName(String dwDimensionName) {
        this.dwDimensionName = dwDimensionName;
    }

    public String getPeriodDimensionName() {
        return this.periodDimensionName;
    }

    public void setPeriodDimensionName(String periodDimensionName) {
        this.periodDimensionName = periodDimensionName;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<ColumnModelDefine> getKeys() {
        return this.keys;
    }

    public void setKeys(List<ColumnModelDefine> keys) {
        this.keys = keys;
    }

    public List<String> getDimNames() {
        return this.dimNames;
    }

    public void setDimNames(List<String> dimNames) {
        this.dimNames = dimNames;
    }

    public int getIsLockColIndex() {
        return this.isLockColIndex;
    }

    public void setIsLockColIndex(int isLockColIndex) {
        this.isLockColIndex = isLockColIndex;
    }

    public List<DimensionCombination> getCollections() {
        return this.collections;
    }

    public void setCollections(List<DimensionCombination> collections) {
        this.collections = collections;
    }

    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionValueSet masterKey) {
        this.masterKey = masterKey;
    }
}

