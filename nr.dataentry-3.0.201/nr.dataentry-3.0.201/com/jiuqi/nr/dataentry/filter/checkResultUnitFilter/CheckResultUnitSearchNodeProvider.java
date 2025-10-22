/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
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
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 */
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
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
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CheckResultUnitSearchNodeProvider
implements ISearchNodeProvider {
    protected int totalSize;
    private IUnitTreeEntityRowProvider entityRowProvider;
    private IUnitTreeContext context;
    private ArrayList<String> units;

    public CheckResultUnitSearchNodeProvider(IUnitTreeEntityRowProvider entityRowProvider, IUnitTreeContext context, DimensionCollectionUtil dimensionCollectionUtil, ICheckResultService checkResultService) {
        this.entityRowProvider = entityRowProvider;
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

    public int getTotalSize() {
        return this.totalSize;
    }

    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        List<IEntityRow> matchRows = this.matchEntityRows(keywords);
        this.totalSize = matchRows.size();
        return matchRows.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
    }

    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        List<IEntityRow> matchRows = this.matchEntityRows(keywords);
        this.totalSize = matchRows.size();
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, this.totalSize);
        List<IEntityRow> pageRows = matchRows.subList(fromIndex, toIndex);
        return pageRows.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
    }

    protected List<IEntityRow> matchEntityRows(String[] keywords) {
        List allRows = this.entityRowProvider.getAllRows();
        ArrayList<IEntityRow> matchRows = new ArrayList<IEntityRow>();
        for (IEntityRow row : allRows) {
            if (!this.checkEntityRow(row, keywords)) continue;
            matchRows.add(row);
        }
        return this.filter(matchRows);
    }

    protected boolean checkEntityRow(IEntityRow row, String[] keywords) {
        List<String> strings = Arrays.asList(keywords);
        return this.matchCode(row, strings) || this.matchTitle(row, strings);
    }

    protected boolean matchCode(IEntityRow row, List<String> matchCodes) {
        for (String matchCode : matchCodes) {
            if (!row.getCode().toLowerCase().contains(matchCode.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected boolean matchTitle(IEntityRow row, List<String> matchTitles) {
        for (String matchTitle : matchTitles) {
            if (!row.getTitle().toLowerCase().contains(matchTitle.toLowerCase())) continue;
            return true;
        }
        return false;
    }

    protected IBaseNodeData madeSearchNodeData(IEntityRow row) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.putKey(row.getEntityKeyData());
        impl.putCode(row.getCode());
        impl.putTitle(row.getTitle());
        impl.setPath(this.entityRowProvider.getNodePath((IBaseNodeData)impl));
        return impl;
    }
}

