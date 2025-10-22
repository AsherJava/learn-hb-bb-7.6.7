/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.param.impl;

import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import com.jiuqi.nr.entity.engine.setting.OrderAttribute;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EntityQueryParam
implements IEntityQueryParam {
    private IEntityDataFilter filter;
    private Date versionDate;
    private String entityId;
    private List<String> masterKey;
    private List<String> codes;
    private boolean ignoreAuth;
    private boolean hasReadAuth;
    private boolean hasWriteAuth;
    private Integer limit;
    private Integer offSet;
    private ExecutorContext context;
    private List<OrderAttribute> orderField;
    private String isolationCondition;
    private boolean sortedByQuery;
    private boolean lazy;
    private boolean markLeaf;
    private boolean maskingData;
    private QueryContext queryContext;
    private boolean dbMode;
    private Integer queryStop;
    private Map<String, Object> ext;

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public Date getVersionDate() {
        return this.versionDate;
    }

    public void setFilter(IEntityDataFilter filter) {
        this.filter = filter;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    @Override
    public boolean isIgnoreAuth() {
        return this.ignoreAuth;
    }

    @Override
    public boolean isHasReadAuth() {
        return this.hasReadAuth;
    }

    @Override
    public boolean isHasWriteAuth() {
        return this.hasWriteAuth;
    }

    @Override
    public IEntityDataFilter getFilter() {
        return this.filter;
    }

    @Override
    public List<String> getMasterKey() {
        return this.masterKey;
    }

    @Override
    public List<String> getCodes() {
        return this.codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    @Override
    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public Integer getOffSet() {
        return this.offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }

    public void setIgnoreAuth(boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }

    public void setHasReadAuth(boolean hasReadAuth) {
        this.hasReadAuth = hasReadAuth;
    }

    public void setHasWriteAuth(boolean hasWriteAuth) {
        this.hasWriteAuth = hasWriteAuth;
    }

    public void setMasterKey(List<String> masterKey) {
        this.masterKey = masterKey;
    }

    @Override
    public List<OrderAttribute> getOrderField() {
        return this.orderField;
    }

    public void setOrderField(List<OrderAttribute> orderField) {
        this.orderField = orderField;
    }

    @Override
    public String getIsolationCondition() {
        return this.isolationCondition;
    }

    public void setIsolationCondition(String isolationCondition) {
        this.isolationCondition = isolationCondition;
    }

    @Override
    public boolean isSortedByQuery() {
        return this.sortedByQuery;
    }

    public void setSortedByQuery(boolean sortedByQuery) {
        this.sortedByQuery = sortedByQuery;
    }

    @Override
    public ExecutorContext getContext() {
        return this.context;
    }

    public void setContext(ExecutorContext context) {
        this.context = context;
    }

    @Override
    public boolean isLazy() {
        return this.lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    @Override
    public boolean isMarkLeaf() {
        return this.markLeaf;
    }

    public void setMarkLeaf(boolean markLeaf) {
        this.markLeaf = markLeaf;
    }

    @Override
    public boolean isMaskingData() {
        return this.maskingData;
    }

    public void setMaskingData(boolean maskingData) {
        this.maskingData = maskingData;
    }

    @Override
    public Map<String, Object> getExt() {
        return this.ext;
    }

    @Override
    public boolean isDbMode() {
        return this.dbMode;
    }

    public void setDbMode(boolean dbMode) {
        this.dbMode = dbMode;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    @Override
    public QueryContext getQueryContext() {
        return this.queryContext;
    }

    public void setQueryContext(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    @Override
    public Integer getQueryStop() {
        return this.queryStop;
    }

    public void setQueryStop(Integer queryStop) {
        this.queryStop = queryStop;
    }
}

