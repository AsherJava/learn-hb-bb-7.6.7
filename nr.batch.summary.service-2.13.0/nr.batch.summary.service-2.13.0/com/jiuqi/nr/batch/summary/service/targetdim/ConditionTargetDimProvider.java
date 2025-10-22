/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConditionTargetDimProvider
implements TargetDimProvider {
    protected SummaryScheme summaryScheme;
    protected TargetDimProviderFactoryImpl wrapper;
    private TargetRangeUnitProvider rangeUnitProvider;
    protected CustomConditionRowProvider conditionRowProvider;

    public ConditionTargetDimProvider(SummaryScheme summaryScheme, CustomConditionRowProvider conditionRowProvider, TargetRangeUnitProvider rangeUnitProvider, TargetDimProviderFactoryImpl wrapper) {
        this.wrapper = wrapper;
        this.summaryScheme = summaryScheme;
        this.rangeUnitProvider = rangeUnitProvider;
        this.conditionRowProvider = conditionRowProvider;
    }

    @Override
    public List<String> getTargetDims(String period) {
        return this.conditionRowProvider.getAllRows().stream().map(CustomCalibreRow::getCode).collect(Collectors.toList());
    }

    @Override
    public List<String> getEntityRowKeys(String period, String targetDimKey) {
        CustomConditionRow targetDimRow = this.conditionRowProvider.findRow(targetDimKey);
        ConditionValueType valueType = targetDimRow.getValue().getValueType();
        List<Object> dimEntities = new ArrayList();
        switch (valueType) {
            case UNITS: {
                dimEntities = targetDimRow.getValue().getCheckList();
                break;
            }
            case EXPRESSION: {
                dimEntities = this.getEntityRow(targetDimRow, period).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
        }
        return this.rangeUnitProvider.retainAll(period, dimEntities);
    }

    public List<IEntityRow> getEntityRow(CustomConditionRow targetDimRow, String period) {
        String expression = targetDimRow.getValue().getExpression();
        if (StringUtils.isNotEmpty((String)expression)) {
            IEntityQuery query = this.wrapper.newEntityQuery(this.summaryScheme.getTask(), period);
            query.setExpression(expression);
            ExecutorContext queryContext = this.wrapper.newEntityQueryContext(this.summaryScheme.getTask(), period);
            queryContext.setVarDimensionValueSet(query.getMasterKeys());
            return this.wrapper.executeWithReader(query, (IContext)queryContext).getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }
}

