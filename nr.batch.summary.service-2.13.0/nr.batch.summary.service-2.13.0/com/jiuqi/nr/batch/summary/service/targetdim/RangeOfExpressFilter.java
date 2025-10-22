/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RangeOfExpressFilter
implements TargetRangeUnitProvider {
    private SummaryScheme summaryScheme;
    private TargetDimProviderFactoryImpl wrapper;

    public RangeOfExpressFilter(SummaryScheme summaryScheme, TargetDimProviderFactoryImpl wrapper) {
        this.wrapper = wrapper;
        this.summaryScheme = summaryScheme;
    }

    @Override
    public List<String> getRangeEntities(String period) {
        String expression = this.summaryScheme.getRangeUnit().getExpression();
        IEntityQuery query = this.wrapper.newEntityQuery(this.summaryScheme.getTask(), period);
        query.setExpression(expression);
        ExecutorContext executorContext = this.wrapper.newEntityQueryContext(this.summaryScheme.getTask(), period);
        List allRows = this.wrapper.executeWithReader(query, (IContext)executorContext).getAllRows();
        return allRows == null ? new ArrayList<String>() : allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }
}

