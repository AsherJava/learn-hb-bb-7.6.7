/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Map;
import org.springframework.lang.Nullable;

public class CheckQueryContext {
    private CheckResultQueryParam checkResultQueryParam;
    private FormSchemeDefine formSchemeDefine;
    private TableModelDefine ckrTable;
    private DimensionChanger ckrDimChanger;
    private TableModelDefine ckdTable;
    private DimensionChanger ckdDimChanger;
    private DimensionValueSet masterKey;
    private Map<String, ColInfo> colInfoMap;
    private ExecutorContext executorContext;
    private EntityData dwEntity;
    private Map<String, IDimDataLoader> entityLoaderMap;

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public CheckResultQueryParam getCheckResultQueryParam() {
        return this.checkResultQueryParam;
    }

    public void setCheckResultQueryParam(CheckResultQueryParam checkResultQueryParam) {
        this.checkResultQueryParam = checkResultQueryParam;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public TableModelDefine getCkrTable() {
        return this.ckrTable;
    }

    public void setCkrTable(TableModelDefine ckrTable) {
        this.ckrTable = ckrTable;
    }

    public DimensionChanger getCkrDimChanger() {
        return this.ckrDimChanger;
    }

    public void setCkrDimChanger(DimensionChanger ckrDimChanger) {
        this.ckrDimChanger = ckrDimChanger;
    }

    public TableModelDefine getCkdTable() {
        return this.ckdTable;
    }

    public void setCkdTable(TableModelDefine ckdTable) {
        this.ckdTable = ckdTable;
    }

    public DimensionChanger getCkdDimChanger() {
        return this.ckdDimChanger;
    }

    public void setCkdDimChanger(DimensionChanger ckdDimChanger) {
        this.ckdDimChanger = ckdDimChanger;
    }

    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionValueSet masterKey) {
        this.masterKey = masterKey;
    }

    @Nullable
    public Map<String, ColInfo> getColInfoMap() {
        return this.colInfoMap;
    }

    public void setColInfoMap(Map<String, ColInfo> colInfoMap) {
        this.colInfoMap = colInfoMap;
    }

    public EntityData getDwEntity() {
        return this.dwEntity;
    }

    public void setDwEntity(EntityData dwEntity) {
        this.dwEntity = dwEntity;
    }

    public Map<String, IDimDataLoader> getEntityLoaderMap() {
        return this.entityLoaderMap;
    }

    public void setEntityLoaderMap(Map<String, IDimDataLoader> entityLoaderMap) {
        this.entityLoaderMap = entityLoaderMap;
    }

    private static class ColInfo {
        private ColumnModelDefine columnModelDefine;
        private Integer queryIndex;

        private ColInfo() {
        }

        public ColumnModelDefine getColumnModelDefine() {
            return this.columnModelDefine;
        }

        public void setColumnModelDefine(ColumnModelDefine columnModelDefine) {
            this.columnModelDefine = columnModelDefine;
        }

        public Integer getQueryIndex() {
            return this.queryIndex;
        }

        public void setQueryIndex(Integer queryIndex) {
            this.queryIndex = queryIndex;
        }
    }
}

