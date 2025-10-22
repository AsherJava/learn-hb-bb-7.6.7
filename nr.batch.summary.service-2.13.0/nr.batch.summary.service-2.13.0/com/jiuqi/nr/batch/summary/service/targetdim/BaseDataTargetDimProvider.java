/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDataTargetDimProvider
implements TargetDimProvider {
    private IEntityRefer entityRefer;
    private IEntityDefine entityDefine;
    private SummaryScheme summaryScheme;
    private TargetDimProviderFactoryImpl wrapper;
    private TargetRangeUnitProvider rangeUnitProvider;

    public BaseDataTargetDimProvider(TargetDimProviderFactoryImpl wrapper, TargetRangeUnitProvider rangeUnitProvider, SummaryScheme summaryScheme, IEntityRefer entityRefer, IEntityDefine entityDefine) {
        this.wrapper = wrapper;
        this.entityRefer = entityRefer;
        this.entityDefine = entityDefine;
        this.summaryScheme = summaryScheme;
        this.rangeUnitProvider = rangeUnitProvider;
    }

    @Override
    public boolean isEmptyTargetDim(String period) {
        return this.getTargetDims(period).isEmpty();
    }

    @Override
    public List<String> getTargetDims(String period) {
        IEntityQuery query = this.wrapper.newEntityQuery(this.entityRefer.getReferEntityId());
        ExecutorContext queryContext = this.wrapper.newEntityQueryContext();
        query.setMasterKeys(new DimensionValueSet());
        IEntityTable tableData = this.wrapper.executeWithReader(query, (IContext)queryContext);
        List allRows = tableData.getAllRows();
        if (allRows != null) {
            return allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }

    @Override
    public List<String> getEntityRowKeys(String period, String targetDimKey) {
        List<String> dimEntities = this.getEntityRow(period, targetDimKey).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        return this.rangeUnitProvider.retainAll(period, dimEntities);
    }

    public List<IEntityRow> getEntityRow(String period, String targetDimKey) {
        String rowFilter = String.format("%s[%s] in {%s}", this.entityDefine.getCode(), this.entityRefer.getOwnField(), "'" + targetDimKey + "'");
        IEntityQuery query = this.wrapper.newEntityQuery(this.summaryScheme.getTask(), period);
        query.setExpression(rowFilter);
        ExecutorContext queryContext = this.wrapper.newEntityQueryContext(this.summaryScheme.getTask(), period);
        queryContext.setVarDimensionValueSet(query.getMasterKeys());
        return this.wrapper.executeWithReader(query, (IContext)queryContext).getAllRows();
    }
}

