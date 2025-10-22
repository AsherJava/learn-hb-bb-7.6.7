/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
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
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider
 *  com.jiuqi.nr.unit.uselector.source.impl.USelectorEntityRowProvider
 */
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
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
import com.jiuqi.nr.jtable.util.FormulaUtil;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.source.impl.USelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

public class CheckResultUnitSelectorEntityRowProvider
implements IUSelectorEntityRowProvider {
    protected IUnitTreeContext context;
    protected UnitTreeEntityDataQuery entityDataQuery;
    protected ArrayList<String> units;

    public CheckResultUnitSelectorEntityRowProvider(IUnitTreeContext context, UnitTreeEntityDataQuery entityDataQuery, DimensionCollectionUtil dimensionCollectionUtil, ICheckResultService checkResultService) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
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

    public int getTotalCount() {
        return this.units.size();
    }

    public String[] getParentsEntityKeyDataPath(String rowKey) {
        return this.entityDataQuery.makeIEntityTable(this.context).getParentsEntityKeyDataPath(rowKey);
    }

    public boolean hasParent(String rowKey) {
        IEntityRow entityRow = this.findEntityRow(rowKey);
        if (entityRow != null) {
            String[] path = entityRow.getParentsEntityKeyDataPath();
            return path != null && path.length != 0 && !path[0].equals(entityRow.getEntityKeyData());
        }
        return false;
    }

    public boolean isLeaf(String rowKey) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, Collections.singletonList(rowKey));
        return dataTable.getDirectChildCount(rowKey) == 0;
    }

    public IEntityRow findEntityRow(String rowKey) {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, Collections.singletonList(rowKey));
        return dataTable.findByEntityKey(rowKey);
    }

    public List<IEntityRow> getAllRows() {
        List sourceRows = this.entityDataQuery.makeIEntityTable(this.context).getAllRows();
        return this.filterIEntityRows(sourceRows);
    }

    public List<IEntityRow> getAllLeaveRows() {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List allRows = dataTable.getAllRows();
        List<IEntityRow> sourceRows = allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) == 0).collect(Collectors.toList());
        return this.filterIEntityRows(sourceRows);
    }

    public List<IEntityRow> getAllNonLeaveRows() {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List allRows = dataTable.getAllRows();
        List<IEntityRow> sourceRows = allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) > 0).collect(Collectors.toList());
        return this.filterIEntityRows(sourceRows);
    }

    public List<IEntityRow> getChildRows(List<String> parents) {
        return new ArrayList<IEntityRow>();
    }

    public List<IEntityRow> getChildRowsAndSelf(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> childRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> {
            childRows.add(dataTable.findByEntityKey(parentKey));
            childRows.addAll(dataTable.getChildRows(parentKey));
        });
        return this.filterIEntityRows(childRows);
    }

    public List<IEntityRow> getAllChildRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> allChildRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> allChildRows.addAll(dataTable.getAllChildRows(parentKey)));
        return this.filterIEntityRows(allChildRows);
    }

    public List<IEntityRow> getAllChildRowsAndSelf(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> allChildRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> {
            allChildRows.add(dataTable.findByEntityKey(parentKey));
            allChildRows.addAll(dataTable.getAllChildRows(parentKey));
        });
        return this.filterIEntityRows(allChildRows);
    }

    public List<IEntityRow> getAllChildLeaveRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        List allRows = dataTable.getAllRows();
        List<IEntityRow> sourceRows = allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) == 0).collect(Collectors.toList());
        return this.filterIEntityRows(sourceRows);
    }

    public List<IEntityRow> getAllChildNonLeaveRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        List allRows = dataTable.getAllRows();
        List<IEntityRow> sourceRows = allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) > 0).collect(Collectors.toList());
        return this.filterIEntityRows(sourceRows);
    }

    public List<IEntityRow> getAllParentRows(List<String> rowKeys) {
        List<IEntityRow> checkRows = this.getCheckRows(rowKeys);
        LinkedHashSet parentKeys = new LinkedHashSet();
        checkRows.forEach(row -> parentKeys.addAll(Arrays.asList(row.getParentsEntityKeyDataPath())));
        return this.getCheckRows(new ArrayList<String>(parentKeys));
    }

    public List<IEntityRow> getCheckRows(List<String> rowKeys) {
        return this.entityDataQuery.makeIEntityTable(this.context, rowKeys).getAllRows();
    }

    public List<IEntityRow> filterByFormulas(String ... expressions) {
        try {
            List sourceRows = this.entityDataQuery.makeIEntityTable(this.context, expressions).getAllRows();
            return this.filterIEntityRows(sourceRows);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(USelectorEntityRowProvider.class).error(e.getMessage(), e);
            return new ArrayList<IEntityRow>();
        }
    }

    public List<IEntityRow> filterByFormulas(IUnitTreeContext context, String ... expressions) {
        try {
            List sourceRows = this.entityDataQuery.makeIEntityTable(context, expressions).getAllRows();
            return this.filterIEntityRows(sourceRows);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(USelectorEntityRowProvider.class).error(e.getMessage(), e);
            return new ArrayList<IEntityRow>();
        }
    }

    public List<IEntityRow> getContinueNodeAndAllChildren(List<String> rangeNodeKeys) {
        if (rangeNodeKeys != null && !rangeNodeKeys.isEmpty()) {
            IEntityTable entityTable = this.entityDataQuery.makeRangeFullTreeData(this.context, rangeNodeKeys);
            return this.filterIEntityRows(entityTable.getAllRows());
        }
        return new ArrayList<IEntityRow>();
    }

    public List<IEntityRow> getContinueNode(List<String> rangeNodeKeys) {
        if (rangeNodeKeys != null && !rangeNodeKeys.isEmpty()) {
            IEntityTable entityTable = this.entityDataQuery.makeIEntityTable(this.context, rangeNodeKeys);
            return this.filterIEntityRows(entityTable.getAllRows());
        }
        return new ArrayList<IEntityRow>();
    }

    private List<IEntityRow> filterIEntityRows(List<IEntityRow> sourceRows) {
        ArrayList<IEntityRow> targetRows = new ArrayList<IEntityRow>();
        for (IEntityRow iEntityRow : sourceRows) {
            if (!this.units.contains(iEntityRow.getCode())) continue;
            targetRows.add(iEntityRow);
        }
        return targetRows;
    }
}

