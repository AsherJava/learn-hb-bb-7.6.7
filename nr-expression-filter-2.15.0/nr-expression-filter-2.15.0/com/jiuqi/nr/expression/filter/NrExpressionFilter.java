/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMainDimFilter
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.filter.IEntityDataFilter
 */
package com.jiuqi.nr.expression.filter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import com.jiuqi.nr.expression.filter.exception.EntityFilterException;
import com.jiuqi.nr.expression.filter.parse.EntityFormulaParser;
import com.jiuqi.nr.expression.filter.parse.FilterExecuteContext;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NrExpressionFilter<T extends AbstractMap<String, Object>, E extends AbstractMap<String, Object>> {
    private static final Logger logs = LoggerFactory.getLogger(NrExpressionFilter.class);
    @Autowired
    private EntityFormulaParser formulaParser;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    protected void filterData(E param, List<T> dataList) {
        FilterExecuteContext ctx;
        IEntityDataFilter entityDataFilter = null;
        Object filter = ((AbstractMap)param).get("NR_EXPRESSION_FILTER");
        if (filter instanceof IEntityDataFilter) {
            entityDataFilter = (IEntityDataFilter)filter;
        }
        if (entityDataFilter == null) {
            return;
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = entityDataFilter.getContext();
        try {
            if (context == null) {
                context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(null);
            }
            context.setCurrentEntityId(entityDataFilter.getEntityId());
            context.setDefaultGroupName(this.getCategoryName(param));
            ctx = new FilterExecuteContext((ExecutorContext)context);
            entityDataFilter.putCache("EXPRESSION_CACHE_CONTEXT", ctx);
        }
        catch (Exception e) {
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append(this.getTypeName()).append(this.getCategoryName(param)).append(":").append("\u6784\u9020\u516c\u5f0f\u4e0a\u4e0b\u6587\u51fa\u73b0\u5f02\u5e38\uff1a").append(entityDataFilter.getExpression()).append("\u3002");
            throw new EntityFilterException(msgBuilder.toString(), e);
        }
        IExpression expression = this.formulaParser.parseEntityFormula(ctx, entityDataFilter.getExpression(), this.getCategoryName(param));
        if (expression == null) {
            this.filterData(param, entityDataFilter, dataList);
        } else {
            this.executeJudge(param, dataList, entityDataFilter, expression);
        }
    }

    private void executeJudge(E param, List<T> dataList, IEntityDataFilter entityDataFilter, IExpression expression) {
        Iterator<T> it = dataList.iterator();
        while (it.hasNext()) {
            FilterExecuteContext<AbstractMap> ctx;
            AbstractMap data = (AbstractMap)it.next();
            com.jiuqi.nr.entity.engine.executors.ExecutorContext context = entityDataFilter.getContext();
            try {
                if (context != null) {
                    Object ctxCache = entityDataFilter.getCache("EXPRESSION_CACHE_CONTEXT");
                    if (ctxCache == null) {
                        ctx = new FilterExecuteContext<AbstractMap>((AbstractMap)param, data, (ExecutorContext)context);
                        entityDataFilter.putCache("EXPRESSION_CACHE_CONTEXT", ctx);
                    } else {
                        ctx = (FilterExecuteContext<AbstractMap>)((Object)ctxCache);
                        ctx.refreshData((AbstractMap)param, data);
                    }
                } else {
                    ctx = new FilterExecuteContext<AbstractMap>((AbstractMap)param, data);
                }
            }
            catch (Exception e) {
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append(this.getTypeName()).append(this.getCategoryName(param)).append(":").append(this.getKey(data)).append("\u6784\u9020\u516c\u5f0f\u4e0a\u4e0b\u6587\u51fa\u73b0\u5f02\u5e38\uff1a").append(entityDataFilter.getExpression()).append("\u3002");
                throw new EntityFilterException(msgBuilder.toString(), e);
            }
            boolean judge = false;
            try {
                judge = expression.judge(ctx);
            }
            catch (SyntaxException e) {
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append(this.getTypeName()).append(this.getCategoryName(param)).append(":").append(this.getKey(data)).append("\u8fc7\u6ee4\u516c\u5f0f\u6267\u884c\u5931\u8d25\uff1a").append(entityDataFilter.getExpression()).append("\u3002");
                logs.error(msgBuilder.toString(), e);
            }
            if (judge) continue;
            it.remove();
        }
    }

    private void filterData(E param, IEntityDataFilter entityDataFilter, List<T> dataList) {
        HashSet keySet;
        Set codeSet = dataList.stream().map(this::getKey).collect(Collectors.toSet());
        DimensionValueSet masterKey = entityDataFilter.getMasterKey();
        String dimensionName = entityDataFilter.getDimensionName();
        ArrayList keys = new ArrayList(codeSet);
        DimensionValueSet filterDimension = new DimensionValueSet();
        if (masterKey != null) {
            filterDimension.assign(masterKey);
        }
        HashSet<String> specialKeys = new HashSet<String>();
        if (dimensionName != null) {
            DimensionValueSet varDimensionValueSet = entityDataFilter.getContext().getVarDimensionValueSet();
            List<String> varDimValues = this.getDimensionValues(varDimensionValueSet, dimensionName.toString());
            List<String> masterDimValues = this.getDimensionValues(filterDimension, dimensionName.toString());
            specialKeys.addAll(varDimValues);
            specialKeys.addAll(masterDimValues);
            specialKeys.removeIf(keys::contains);
            keys.addAll(specialKeys);
            filterDimension.setValue(dimensionName.toString(), keys);
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext = entityDataFilter.getContext();
        executorContext.setDefaultGroupName(this.getCategoryName(param));
        this.setCacheData(executorContext, param, dataList);
        IMainDimFilter mainDimFilter = this.dataAccessProvider.newMainDimFilter();
        try {
            List keyList = mainDimFilter.filterByCondition((ExecutorContext)executorContext, filterDimension, entityDataFilter.getExpression());
            keySet = keyList == null ? new HashSet() : new HashSet(keyList);
        }
        catch (Exception e) {
            logs.error(String.format("\u5b9e\u4f53\u67e5\u8be2\u6267\u884c\u516c\u5f0f\u8fc7\u6ee4\u65f6\u53d1\u751f\u9519\u8bef: %s", entityDataFilter.getExpression()), e);
            return;
        }
        keySet.removeAll(specialKeys);
        dataList.removeIf(next -> !keySet.contains(this.getKey(next)));
    }

    private List<String> getDimensionValues(DimensionValueSet dimensionValueSet, String dimensioName) {
        ArrayList<String> keys = new ArrayList<String>();
        if (dimensionValueSet == null) {
            return keys;
        }
        Object value = dimensionValueSet.getValue(dimensioName);
        if (value instanceof String) {
            keys.add(value.toString());
        } else if (value instanceof List) {
            keys.addAll((Collection)value);
        }
        return keys;
    }

    protected void setCacheData(com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext, E param, List<T> dataList) {
        String filterData = "FILTER-DATA-LIST";
        String filterParam = "FILTER-PARAM";
        VariableManager variableManager = executorContext.getVariableManager();
        Variable dataVariable = variableManager.find(filterData);
        if (dataVariable != null) {
            dataVariable.setVarValue(dataList);
        } else {
            variableManager.add(new Variable(filterData, "\u6570\u636e", 0, dataList));
        }
        Variable paramVariable = variableManager.find(filterParam);
        if (paramVariable != null) {
            paramVariable.setVarValue(param);
        } else {
            variableManager.add(new Variable(filterParam, "\u53c2\u6570", 0, param));
        }
    }

    abstract String getCategoryName(E var1);

    abstract String getKey(T var1);

    abstract String getTypeName();
}

