/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.common.Constant;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.intf.ICommonQuery;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.OrderAttribute;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class CommonQueryImpl
implements ICommonQuery {
    private DimensionValueSet masterKeys;
    private Date queryVersionDate = Constant.DATE_VERSION_INVALID_VALUE;
    private EntityViewDefine entityView;
    private String rowFilter;
    private AuthorityType authType = AuthorityType.None;
    private boolean ignoreViewFilter;
    private List<OrderAttribute> orderAttribute;
    private boolean sortedByQuery = true;
    private ExecutorContext executorContext;
    private String expression;
    private final QueryContext queryContext;
    private boolean lazyQuery;
    private boolean markLeaf;
    private boolean maskingData = false;

    public CommonQueryImpl(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionDate;
    }

    @Override
    public void setQueryVersionDate(Date queryVersionDate) {
        this.queryVersionDate = queryVersionDate;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u65f6\u671f\u53c2\u6570\u4e3a\uff1a{}", queryVersionDate);
    }

    public EntityViewDefine getEntityView() {
        return this.entityView;
    }

    @Override
    public void setEntityView(EntityViewDefine entityView) {
        this.entityView = entityView;
        this.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("\u8bbe\u7f6e\u89c6\u56fe\u53c2\u6570\uff0c\u5b9e\u4f53\u4e3a\uff1a{}, \u8fc7\u6ee4\u6761\u4ef6\u4e3a\uff1a{}, \u5f00\u542f\u6743\u9650\uff1a{}", entityView.getEntityId(), entityView.getRowFilterExpression(), entityView.getFilterRowByAuthority());
            }
        });
    }

    @Override
    public void setMasterKeys(DimensionValueSet masterKeys) {
        int size = masterKeys.size();
        HashMap<String, Object> listValue = new HashMap<String, Object>(size);
        int i = 0;
        while (i < size) {
            Object masterKeysValue = masterKeys.getValue(i);
            if (masterKeysValue instanceof List) {
                ArrayList temp = new ArrayList((List)masterKeysValue);
                listValue.put(masterKeys.getName(i), temp);
            }
            int finalI = i++;
            this.getQueryContext().getLogger().accept(e -> {
                if (e.isTraceEnabled()) {
                    e.trace("\u8bbe\u7f6e\u7ef4\u5ea6\u503c,key\uff1a{}\uff0cvalue:{}", masterKeys.getName(finalI), masterKeysValue);
                }
            });
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.assign(masterKeys);
        listValue.forEach((arg_0, arg_1) -> ((DimensionValueSet)dimensionValueSet).setValue(arg_0, arg_1));
        this.masterKeys = dimensionValueSet;
    }

    @Override
    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    @Override
    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u8fc7\u6ee4\u6761\u4ef6\u4e3a\uff1a{}", rowFilter);
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u6267\u884c\u516c\u5f0f\u4e3a\uff1a{}", expression);
    }

    public String getExpression() {
        return this.expression;
    }

    public AuthorityType getAuthorityOperations() {
        return this.authType;
    }

    @Override
    public void setAuthorityOperations(AuthorityType authType) {
        this.authType = authType;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u6743\u9650\u4e3a\uff1a{}", authType.name());
    }

    public QueryContext getQueryContext() {
        return this.queryContext;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    @Override
    public void setIgnoreViewFilter(boolean ignore) {
        this.ignoreViewFilter = ignore;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u5ffd\u7565\u7f3a\u9677\uff1a{}", ignore);
    }

    @Override
    public void addOrderAttribute(String attributeCode, OrderType orderType) {
        this.sorted(true);
        OrderAttribute attribute = new OrderAttribute();
        attribute.setAttributeCode(attributeCode);
        attribute.setType(orderType);
        this.orderAttribute.add(attribute);
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u6392\u5e8f\u5b57\u6bb5\uff1a{}, \u6392\u5e8f\u89c4\u5219\u4e3a\uff1a{}", new Object[]{attribute, orderType});
    }

    @Override
    public void sorted(boolean sorted) {
        if (sorted) {
            if (CollectionUtils.isEmpty(this.orderAttribute)) {
                this.orderAttribute = new ArrayList<OrderAttribute>(16);
            }
        } else {
            this.orderAttribute = null;
        }
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u67e5\u8be2\u7ed3\u679c\u6392\u5e8f\uff1a{}", sorted);
    }

    @Override
    public void sortedByQuery(boolean sorted) {
        this.sortedByQuery = sorted;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u67e5\u8be2\u7ed3\u679c\u6309\u67e5\u8be2\u8303\u56f4\u6392\u5e8f\uff1a{}", sorted);
    }

    public boolean isSortedByQuery() {
        return this.sortedByQuery;
    }

    public boolean isIgnoreViewFilter() {
        return this.ignoreViewFilter;
    }

    public List<OrderAttribute> getOrderAttribute() {
        return this.orderAttribute;
    }

    public boolean isLazyQuery() {
        return this.lazyQuery;
    }

    @Override
    public void lazyQuery() {
        this.lazyQuery = true;
        this.getQueryContext().getLogger().trace("\u8bbe\u7f6e\u61d2\u52a0\u8f7d\u6a21\u5f0f");
    }

    public boolean isMarkLeaf() {
        return this.markLeaf;
    }

    @Override
    public void markLeaf() {
        this.markLeaf = true;
        this.getQueryContext().getLogger().trace("\u6807\u8bb0\u53f6\u5b50\u8282\u70b9");
    }

    @Override
    public void maskedData() {
        this.maskingData = true;
    }

    public boolean isMaskingData() {
        return this.maskingData;
    }

    protected void setContext(IContext context) {
        DimensionValueSet varDimensionValueSet;
        ExecutorContext executorContext = this.executorContext = context instanceof ExecutorContext ? (ExecutorContext)context : new ExecutorContext(this.getQueryContext().getQueryService().getRuntimeController());
        if (context instanceof ExecutorContext && (varDimensionValueSet = this.executorContext.getVarDimensionValueSet()) == null) {
            this.executorContext.setVarDimensionValueSet(this.masterKeys);
        }
        this.executorContext.setCurrentEntityId(this.entityView.getEntityId());
    }

    protected void resolutionVersion(boolean query) {
        if (this.queryVersionDate != null && !this.queryVersionDate.equals(Constant.DATE_VERSION_INVALID_VALUE)) {
            return;
        }
        String viewKey = this.executorContext.getPeriodView();
        this.queryVersionDate = query ? this.getQueryContext().getQueryService().getVersionQueryService().getVersionDate(this.masterKeys, viewKey) : this.getQueryContext().getQueryService().getVersionQueryService().getVersionDate(this.masterKeys, viewKey, EntityUtils.getId((String)this.entityView.getEntityId()));
        this.getQueryContext().getLogger().trace("\u65f6\u671f\u89e3\u6790\u7ed3\u679c\uff1a{}", this.queryVersionDate);
    }
}

