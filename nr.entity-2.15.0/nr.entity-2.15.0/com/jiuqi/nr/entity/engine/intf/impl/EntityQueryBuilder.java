/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.definitions.TableRunInfo;
import com.jiuqi.nr.entity.engine.exception.EntityEngineException;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryInfo;
import com.jiuqi.nr.entity.engine.filter.EntityDataFilter;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.impl.CommonQueryImpl;
import com.jiuqi.nr.entity.engine.intf.impl.DataLoader;
import com.jiuqi.nr.entity.engine.intf.impl.EntityQueryImpl;
import com.jiuqi.nr.entity.engine.intf.impl.EntityTableImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ModifyTableImpl;
import com.jiuqi.nr.entity.engine.intf.impl.QueryEntityTableImpl;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.FieldsInfoImpl;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityQueryBuilder {
    private final QueryContext queryContext;
    private EntityQueryParam entityQueryParam;
    private IEntityAdapter entityAdapter;
    private DimensionValueSet masterKeys;
    private String dimensionName;
    private DataLoader dataLoader;
    private QueryInfo queryInfo;

    public EntityQueryBuilder(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    public IEntityTable getReaderTable() {
        if (this.entityQueryParam.isDbMode()) {
            return new QueryEntityTableImpl(this.queryContext, this.queryInfo, this.dataLoader);
        }
        return new EntityTableImpl(this.queryContext, this.queryInfo);
    }

    public QueryEntityTableImpl getBuildTable() {
        return new QueryEntityTableImpl(this.queryContext, this.queryInfo, this.dataLoader);
    }

    public ModifyTableImpl getUpdateTable() {
        return new ModifyTableImpl(this.queryContext, this.queryInfo, this.dataLoader);
    }

    public EntityQueryBuilder buildQueryParam(EntityQueryImpl entityQuery) throws JQException {
        this.buildParam(entityQuery);
        this.entityQueryParam.setQueryStop(entityQuery.getStopModel());
        this.buildReferCondition(entityQuery);
        this.buildQueryInfo(entityQuery.getEntityView().getEntityId());
        return this;
    }

    public EntityQueryBuilder buildUpdateParam(CommonQueryImpl entityQuery) throws JQException {
        this.buildParam(entityQuery);
        this.buildQueryInfo(entityQuery.getEntityView().getEntityId());
        return this;
    }

    public void buildParam(CommonQueryImpl entityQuery) {
        String filter;
        EntityViewDefine entityView = entityQuery.getEntityView();
        DimensionValueSet masterKeys = entityQuery.getMasterKeys();
        if (masterKeys == null) {
            masterKeys = new DimensionValueSet();
        }
        this.masterKeys = masterKeys;
        this.entityQueryParam = new EntityQueryParam();
        this.entityQueryParam.setEntityId(EntityUtils.getId((String)entityView.getEntityId()));
        if (!entityQuery.isIgnoreViewFilter()) {
            filter = this.buildFilterAndExpression(entityQuery.getRowFilter(), entityView.getRowFilterExpression());
            filter = this.buildFilterAndExpression(filter, entityQuery.getExpression());
        } else {
            filter = this.buildFilterAndExpression(entityQuery.getRowFilter(), entityQuery.getExpression());
        }
        if (StringUtils.hasText(filter)) {
            EntityDataFilter dataFilter = this.buildDataFilter(entityQuery, masterKeys, filter);
            this.entityQueryParam.setFilter(dataFilter);
        }
        this.entityQueryParam.setVersionDate(entityQuery.getQueryVersionDate());
        this.entityQueryParam.setMasterKey(this.getMasterKeys(masterKeys));
        AuthorityType authType = entityQuery.getAuthorityOperations();
        if (entityView.getFilterRowByAuthority()) {
            switch (authType) {
                case None: {
                    this.entityQueryParam.setIgnoreAuth(true);
                    break;
                }
                case Read: {
                    this.entityQueryParam.setHasReadAuth(true);
                    break;
                }
                case Modify: {
                    this.entityQueryParam.setHasWriteAuth(true);
                    break;
                }
            }
        } else {
            this.entityQueryParam.setIgnoreAuth(true);
        }
        this.entityQueryParam.setOrderField(entityQuery.getOrderAttribute());
        this.entityQueryParam.setSortedByQuery(entityQuery.isSortedByQuery());
        ExecutorContext executorContext = entityQuery.getExecutorContext();
        this.entityQueryParam.setContext(executorContext);
        if (null != executorContext) {
            this.entityQueryParam.setExt(executorContext.getQueryParam());
        }
        this.entityQueryParam.setLazy(entityQuery.isLazyQuery());
        this.entityQueryParam.setMarkLeaf(entityQuery.isMarkLeaf());
        this.entityQueryParam.setMaskingData(entityQuery.isMaskingData());
        this.entityQueryParam.setDbMode(this.entityAdapter.isDBMode(EntityUtils.getId((String)entityView.getEntityId()), entityQuery.getQueryVersionDate()));
        this.entityQueryParam.setQueryContext(this.queryContext);
    }

    private EntityDataFilter buildDataFilter(CommonQueryImpl entityQuery, DimensionValueSet masterKeys, String filter) {
        EntityDataFilter dataFilter = new EntityDataFilter(filter, entityQuery.getEntityView().getEntityId());
        if (StringUtils.hasText(filter)) {
            dataFilter.setMasterKey(masterKeys, this.dimensionName);
        }
        dataFilter.buildFilterEnv(entityQuery.getExecutorContext(), this.queryContext);
        return dataFilter;
    }

    private void buildReferCondition(EntityQueryImpl entityQuery) {
        this.buildReferExpression(entityQuery);
        this.buildReferKeys(entityQuery);
        this.buildIsolation(entityQuery.getIsolationCondition());
    }

    private void buildIsolation(String isolationCondition) {
        this.entityQueryParam.setIsolationCondition(isolationCondition);
        IEntityDataFilter filter = this.entityQueryParam.getFilter();
        if (filter != null) {
            filter.putCache("ISOLATECONDITION", isolationCondition);
        }
    }

    private void buildReferKeys(EntityQueryImpl entityQuery) {
        if (CollectionUtils.isEmpty(entityQuery.getReferFilter())) {
            return;
        }
        List<String> masterKey = this.entityQueryParam.getMasterKey();
        if (CollectionUtils.isEmpty(masterKey)) {
            masterKey.addAll(entityQuery.getReferFilter());
        }
        LinkedHashSet<String> filterSet = new LinkedHashSet<String>(masterKey);
        this.entityQueryParam.setMasterKey(new ArrayList<String>(filterSet));
    }

    private void buildReferExpression(EntityQueryImpl entityQuery) {
        EntityDataFilter dataFilter;
        List<String> referExpression = entityQuery.getReferExpression();
        if (CollectionUtils.isEmpty(referExpression)) {
            return;
        }
        StringBuffer expression = new StringBuffer();
        IEntityDataFilter entityDataFilter = this.entityQueryParam.getFilter();
        if (entityDataFilter != null) {
            expression.append("(").append(entityDataFilter.getExpression()).append(")");
        }
        for (String filter : referExpression) {
            if (expression.length() > 0) {
                expression.append(" and ");
            }
            expression.append("(").append(filter).append(")");
        }
        if (expression.length() == 0) {
            return;
        }
        if (entityDataFilter == null) {
            dataFilter = this.buildDataFilter(entityQuery, this.masterKeys, expression.toString());
            this.entityQueryParam.setFilter(dataFilter);
        } else {
            dataFilter = (EntityDataFilter)entityDataFilter;
            dataFilter.setExpression(expression.toString());
        }
    }

    public String buildFilterAndExpression(String filter, String expression) {
        StringBuffer filterAndExpression = new StringBuffer();
        if (StringUtils.hasText(filter)) {
            if (!StringUtils.hasText(expression)) {
                return filter;
            }
            filterAndExpression.append("(").append(filter).append(")");
        }
        if (StringUtils.hasText(expression)) {
            if (StringUtils.hasText(filter)) {
                if (expression.equals(filter)) {
                    return filter;
                }
                filterAndExpression.append(" and ");
            }
            if (filterAndExpression.length() == 0) {
                filterAndExpression.append(expression);
            } else {
                filterAndExpression.append("(").append(expression).append(")");
            }
        }
        return filterAndExpression.toString();
    }

    public EntityQueryBuilder buildRuntimeEnv(String entityId, DimensionValueSet dimensionValueSet) {
        AdapterService adapterService = this.queryContext.getQueryService().getAdapterService();
        this.entityAdapter = adapterService.getEntityAdapter(entityId);
        this.dataLoader = new DataLoader(dimensionValueSet, this.entityAdapter);
        this.dimensionName = this.entityAdapter.getDimensionName(EntityUtils.getId((String)entityId));
        return this;
    }

    public void buildQueryInfo(String entityId) throws JQException {
        TableModelDefine tableModelDefine = this.entityAdapter.getTableByEntityId(EntityUtils.getId((String)entityId));
        if (tableModelDefine == null) {
            throw new JQException((ErrorEnum)EntityEngineException.ADAPTER_QUERY_ERROR, String.format("\u672a\u67e5\u8be2\u5230[%s]\u5bf9\u5e94\u7684\u5b9e\u4f53\u5973\u5a32\u5efa\u6a21", entityId));
        }
        IEntityModel entityModel = this.entityAdapter.getEntityModel(EntityUtils.getId((String)entityId));
        TableRunInfo tableRunInfo = new TableRunInfo();
        tableRunInfo.setTableName(tableModelDefine.getName());
        tableRunInfo.setTableModelDefine(tableModelDefine);
        tableRunInfo.setEntityModel(entityModel);
        tableRunInfo.setDimensionName(this.dimensionName);
        tableRunInfo.setFieldsInfo(this.initFieldsInfo(entityModel));
        this.queryInfo = new QueryInfo();
        this.queryInfo.setMasterKeys(this.masterKeys);
        this.queryInfo.setTableRunInfo(tableRunInfo);
        this.queryInfo.setQueryParam(this.entityQueryParam);
    }

    private FieldsInfoImpl initFieldsInfo(IEntityModel entityModel) {
        Iterator<IEntityAttribute> attributeIterator = entityModel.getAttributes();
        FieldsInfoImpl fieldsInfo = new FieldsInfoImpl();
        while (attributeIterator.hasNext()) {
            IEntityAttribute next = attributeIterator.next();
            fieldsInfo.setupField(next.getCode(), next);
        }
        return fieldsInfo;
    }

    private List<String> getMasterKeys(DimensionValueSet dimensionValueSet) {
        ArrayList<String> keys = new ArrayList<String>(dimensionValueSet.size());
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String name = dimensionValueSet.getName(i);
            Object value = dimensionValueSet.getValue(i);
            if (!this.dimensionName.equals(name) || value == null) continue;
            if (value instanceof String) {
                if (value.toString().contains(";")) {
                    String[] dimKeys = value.toString().split(";");
                    keys.addAll(Arrays.asList(dimKeys));
                    continue;
                }
                if (!StringUtils.hasText(value.toString())) continue;
                keys.add(value.toString());
                continue;
            }
            keys.addAll((List)value);
        }
        return keys;
    }

    public IEntityAdapter getEntityAdapter() {
        return this.entityAdapter;
    }

    public EntityQueryParam getEntityQueryParam() {
        return this.entityQueryParam;
    }

    public DataLoader getDataLoader() {
        return this.dataLoader;
    }
}

