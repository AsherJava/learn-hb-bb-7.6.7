/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.entity.adapter.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractDataQueryHelper<T extends AbstractMap> {
    private static final Logger log = LoggerFactory.getLogger(AbstractDataQueryHelper.class);

    protected void afterQuery(List<T> rows, IEntityQueryParam query) {
        this.sortRows(rows, query);
    }

    private void sortRows(List<T> rows, IEntityQueryParam query) {
        if (!query.isSortedByQuery()) {
            return;
        }
        if (!CollectionUtils.isEmpty(query.getMasterKey())) {
            Map<String, AbstractMap> keyMap = rows.stream().collect(Collectors.toMap(this::getKey, b -> b, (b1, b2) -> b2));
            rows.clear();
            Iterator<String> iterator = query.getMasterKey().iterator();
            while (iterator.hasNext()) {
                AbstractMap row = keyMap.get(iterator.next());
                if (row == null) continue;
                rows.add(row);
            }
        }
    }

    protected abstract String getKey(T var1);

    protected abstract String getDimensionName(String var1);

    private ExecutorContext getContext(IContext context) {
        ExecutorContext executorContext = null;
        if (context instanceof ExecutorContext) {
            executorContext = (ExecutorContext)context;
        } else if (context instanceof com.jiuqi.nr.entity.engine.executors.ExecutorContext) {
            com.jiuqi.nr.entity.engine.executors.ExecutorContext myContext = (com.jiuqi.nr.entity.engine.executors.ExecutorContext)context;
            executorContext = new ExecutorContext(myContext.getRuntimeController());
            executorContext.setEnv(myContext.getEnv());
            executorContext.setPeriodView(myContext.getPeriodView());
            executorContext.setVarDimensionValueSet(myContext.getVarDimensionValueSet());
            executorContext.setJQReportModel(myContext.isJQReportModel());
            executorContext.setPeriodProvider(myContext.getPeriodAdapter());
            executorContext.setRecordDataChange(myContext.isRecordDataChange());
            executorContext.setDefaultGroupName(myContext.getDefaultGroupName());
            executorContext.setUseDnaSql(myContext.isUseDnaSql());
            executorContext.setFormulaShowInfo(myContext.getFormulaShowInfo());
        }
        return executorContext;
    }
}

