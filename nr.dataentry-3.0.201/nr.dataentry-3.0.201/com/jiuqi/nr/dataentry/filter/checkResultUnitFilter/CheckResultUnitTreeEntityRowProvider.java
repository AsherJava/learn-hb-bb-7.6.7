/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 */
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CheckResultUnitTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IUnitTreeEntityDataQuery entityDataQuery;
    protected IUnitTreeContext context;
    protected IEntityTable dataTable;
    protected IUnitTreeEntityRowCounter rowCounter;
    protected ArrayList<String> units;

    public CheckResultUnitTreeEntityRowProvider(IUnitTreeContext context, DimensionCollectionUtil dimensionCollectionUtil, ICheckResultService checkResultService, IUnitTreeEntityDataQuery entityDataQuery) {
        this.entityDataQuery = entityDataQuery;
        this.context = context;
        this.units = new ArrayList();
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setPagerInfo(null);
        checkResultQueryParam.setGroupType(GroupType.unit);
        checkResultQueryParam.setBatchId((String)context.getCustomVariable().get("checkAsyncTaskKey"));
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName(context.getPeriodEntity().getDimensionName());
        dimensionValue.setValue(context.getPeriod());
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        dimensionSet.put(context.getPeriodEntity().getDimensionName(), dimensionValue);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (String key : dimensionSet.keySet()) {
            String name = ((DimensionValue)dimensionSet.get(key)).getName();
            String value = ((DimensionValue)dimensionSet.get(key)).getValue();
            builder.setValue(name, new Object[]{value});
        }
        DimensionCollection dimensionCollection = builder.getCollection();
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        List formulaSchemeList = FormulaUtil.getFormulaSchemeList((String)context.getFormScheme().getKey(), (String)((String)context.getCustomVariable().get("formulaSchemeKeys"))).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        checkResultQueryParam.setFormulaSchemeKeys(formulaSchemeList);
        CheckResultGroup checkResultGroup = checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
        for (CheckResultGroupData groupData : checkResultGroup.getGroupData()) {
            this.units.add(groupData.getCode());
        }
    }

    private List<IEntityRow> filter(List<IEntityRow> source) {
        ArrayList<IEntityRow> target = new ArrayList<IEntityRow>();
        for (IEntityRow iEntityRow : source) {
            if (!this.units.contains(iEntityRow.getCode())) continue;
            target.add(iEntityRow);
        }
        return target;
    }

    public List<IEntityRow> getRootRows() {
        return this.getAllRows();
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        return new ArrayList<IEntityRow>();
    }

    public IEntityTable getStructTreeRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        return this.dataTable;
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        return new String[]{rowData.getKey()};
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        IEntityRow targetRow = null;
        if (rowData != null && StringUtils.isNotEmpty((String)rowData.getKey())) {
            if (this.dataTable != null) {
                targetRow = this.dataTable.findByEntityKey(rowData.getKey());
            }
            if (targetRow == null) {
                this.dataTable = this.entityDataQuery.makeIEntityTable(this.context, Collections.singletonList(rowData.getKey()));
                targetRow = this.dataTable.findByEntityKey(rowData.getKey());
            }
        }
        return targetRow;
    }

    public List<IEntityRow> getAllRows() {
        this.dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List source = this.dataTable.getAllRows();
        return this.filter(source);
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        return true;
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        return 0;
    }

    public int getAllChildCount(IBaseNodeData parent) {
        return 0;
    }
}

