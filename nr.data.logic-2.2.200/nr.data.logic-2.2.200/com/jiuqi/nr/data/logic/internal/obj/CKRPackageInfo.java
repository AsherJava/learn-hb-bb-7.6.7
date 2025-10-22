/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import java.util.Map;

public class CKRPackageInfo {
    private QueryContext queryContext;
    private FormulaShowInfo formulaShowInfo;
    private Map<String, IDimDataLoader> entityDataLoaderMap;
    private Map<String, String> colDimNameMap;
    private Map<Integer, Boolean> checkTypes;
    private QueryCondition queryCondition;
    private String formSchemeKey;
    private CKDValCollectorCache ckdValCollectorCache;

    public CKRPackageInfo() {
    }

    public CKRPackageInfo(QueryContext queryContext, FormulaShowInfo formulaShowInfo, Map<String, IDimDataLoader> entityDataLoaderMap, Map<String, String> colDimNameMap, Map<Integer, Boolean> checkTypes, QueryCondition queryCondition, String formSchemeKey, CKDValCollectorCache ckdValCollectorCache) {
        this.queryContext = queryContext;
        this.formulaShowInfo = formulaShowInfo;
        this.entityDataLoaderMap = entityDataLoaderMap;
        this.colDimNameMap = colDimNameMap;
        this.checkTypes = checkTypes;
        this.queryCondition = queryCondition;
        this.formSchemeKey = formSchemeKey;
        this.ckdValCollectorCache = ckdValCollectorCache;
    }

    public QueryContext getQueryContext() {
        return this.queryContext;
    }

    public FormulaShowInfo getFormulaShowInfo() {
        return this.formulaShowInfo;
    }

    public Map<String, IDimDataLoader> getEntityDataLoaderMap() {
        return this.entityDataLoaderMap;
    }

    public Map<String, String> getColDimNameMap() {
        return this.colDimNameMap;
    }

    public Map<Integer, Boolean> getCheckTypes() {
        return this.checkTypes;
    }

    public QueryCondition getQueryCondition() {
        return this.queryCondition;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public CKDValCollectorCache getCkdValCollectorCache() {
        return this.ckdValCollectorCache;
    }

    public void setQueryContext(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    public void setFormulaShowInfo(FormulaShowInfo formulaShowInfo) {
        this.formulaShowInfo = formulaShowInfo;
    }

    public void setEntityDataLoaderMap(Map<String, IDimDataLoader> entityDataLoaderMap) {
        this.entityDataLoaderMap = entityDataLoaderMap;
    }

    public void setColDimNameMap(Map<String, String> colDimNameMap) {
        this.colDimNameMap = colDimNameMap;
    }

    public void setCheckTypes(Map<Integer, Boolean> checkTypes) {
        this.checkTypes = checkTypes;
    }

    public void setQueryCondition(QueryCondition queryCondition) {
        this.queryCondition = queryCondition;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setCkdValCollectorCache(CKDValCollectorCache ckdValCollectorCache) {
        this.ckdValCollectorCache = ckdValCollectorCache;
    }
}

