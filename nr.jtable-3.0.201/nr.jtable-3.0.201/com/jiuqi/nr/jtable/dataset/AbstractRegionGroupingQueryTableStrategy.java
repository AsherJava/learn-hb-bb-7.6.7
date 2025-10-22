/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService
 *  com.jiuqi.nr.definition.common.Consts
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.definition.common.Consts;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.impl.MeasureFieldValueProcessor;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.SortingMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRegionGroupingQueryTableStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRegionGroupingQueryTableStrategy.class);
    protected IJtableDataEngineService jtableDataEngineService;
    protected IGroupingQuery groupingQuery;
    protected ExecutorContext context;
    protected AbstractRegionRelationEvn regionRelationEvn;
    protected GroupingRelationEvn groupingRelationEvn;
    protected RegionQueryInfo regionQueryInfo;
    protected List<String> cells = new ArrayList<String>();
    protected StringBuffer filterBuf = new StringBuffer();
    protected Map<String, Integer> gradeCellIndex = new HashMap<String, Integer>();
    protected List<String> groupingLinks = new ArrayList<String>();
    public boolean needBalance = false;
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    protected DataFieldDeployInfoService deployInfoService;

    public AbstractRegionGroupingQueryTableStrategy(IGroupingQuery groupingQuery, AbstractRegionRelationEvn regionRelationEvn, GroupingRelationEvn groupingRelationEvn, RegionQueryInfo regionQueryInfo) {
        this.jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        JtableContext jtableContext = new JtableContext(regionQueryInfo.getContext());
        this.groupingQuery = groupingQuery;
        this.context = this.jtableDataEngineService.getExecutorContext(jtableContext);
        if (jtableContext.getMeasureMap() != null && !jtableContext.getMeasureMap().isEmpty() && this.context.getEnv() instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment environment = (ReportFmlExecEnvironment)this.context.getEnv();
            MeasureFieldValueProcessor measureProcessor = new MeasureFieldValueProcessor(regionRelationEvn, jtableContext);
            if (measureProcessor.getMultiplier(null) != 1.0) {
                this.needBalance = true;
            }
            environment.setFieldValueProcessor((IFieldValueProcessor)measureProcessor);
        }
        this.regionRelationEvn = regionRelationEvn;
        this.groupingRelationEvn = groupingRelationEvn;
        this.regionQueryInfo = regionQueryInfo;
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.deployInfoService = (DataFieldDeployInfoService)SpringBeanUtils.getBean(DataFieldDeployInfoService.class);
        this.initDataQuery();
    }

    public IReadonlyTable getRegionQueryTable() {
        IGroupingTable readonlyTable = null;
        try {
            if (this.regionRelationEvn.isPaginate()) {
                this.addPageInfo();
            }
            readonlyTable = this.groupingQuery.executeReader(this.context);
        }
        catch (Exception e) {
            String contextStr = DimensionValueSetUtil.getContextStr(this.regionQueryInfo.getContext(), this.regionQueryInfo.getRegionKey(), null);
            String errorStr = "\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + contextStr + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage();
            logger.error(errorStr, e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + contextStr});
        }
        return readonlyTable;
    }

    public IndexItem getRowIndex(DimensionValueSet locatDimensionValueSet) {
        JtableContext jtableContext = this.regionQueryInfo.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        IDataQuery floatOrderDataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, this.regionRelationEvn.getRegionData().getKey());
        floatOrderDataQuery.setMasterKeys(dimensionValueSet);
        FieldData floatOrderField = this.regionRelationEvn.getFloatOrderFields().get(0);
        this.jtableDataEngineService.addQueryColumn((ICommonQuery)floatOrderDataQuery, floatOrderField.getFieldKey());
        this.jtableDataEngineService.addOrderByItem((ICommonQuery)floatOrderDataQuery, floatOrderField.getFieldKey(), false);
        try {
            return floatOrderDataQuery.queryRowIndex(locatDimensionValueSet, this.context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    protected void initDataQuery() {
        this.setGroupingSeting();
        this.addRegionFilter();
        this.addQueryColumn();
        this.addGroupingColumn();
        this.addOrderColumn();
        if (this.filterBuf.length() > 0) {
            this.groupingQuery.setRowFilter(this.filterBuf.toString());
        }
    }

    protected void addPageInfo() {
    }

    protected abstract void setGroupingSeting();

    protected abstract void addRegionFilter();

    protected abstract void addQueryColumn();

    protected void addPeriodColumn() {
    }

    protected void addNormalColumn() {
        int columnIndex;
        Map<String, String> dataLinkFormulaMap = this.regionRelationEvn.getDataLinkFormulaMap();
        for (String string : dataLinkFormulaMap.keySet()) {
            this.cells.add(string);
            int columnIndex2 = this.groupingQuery.addExpressionColumn(dataLinkFormulaMap.get(string));
            this.regionRelationEvn.putCellIndex(string, columnIndex2);
        }
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        for (String dataLinkKey : dataLinkFieldMap.keySet()) {
            if (dataLinkFormulaMap.containsKey(dataLinkKey)) continue;
            this.cells.add(dataLinkKey);
            FieldData field = dataLinkFieldMap.get(dataLinkKey);
            columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, field.getFieldKey());
            this.regionRelationEvn.putCellIndex(dataLinkKey, columnIndex);
            if (this.regionQueryInfo.getRestructureInfo().getGrade() != null && this.regionQueryInfo.getRestructureInfo().getGrade().getGradeCells() != null && !this.regionQueryInfo.getRestructureInfo().getGrade().getGradeCells().isEmpty()) {
                for (GradeCellInfo gradeCellInfo : this.regionQueryInfo.getRestructureInfo().getGrade().getGradeCells()) {
                    if (!gradeCellInfo.getZbid().contains(field.getFieldKey())) continue;
                    this.gradeCellIndex.put(dataLinkKey, columnIndex);
                    this.groupingLinks.add(dataLinkKey);
                }
            }
            this.addColumnFilter(dataLinkKey, columnIndex);
        }
        Map<String, String> map = this.regionRelationEvn.getFieldBalanceFormulaMap();
        if (!this.regionRelationEvn.isCommitData() && this.needBalance) {
            for (String balanceFieldKey : map.keySet()) {
                columnIndex = this.groupingQuery.addExpressionColumn(map.get(balanceFieldKey));
                this.regionRelationEvn.putCellIndex(balanceFieldKey, columnIndex);
            }
        }
    }

    protected void addColumnFilter(String dataLinkKey, int columnIndex) {
        if (this.regionQueryInfo != null && this.regionQueryInfo.getFilterInfo().getCellQuerys() != null) {
            List<CellQueryInfo> cellQueryInfos = this.regionQueryInfo.getFilterInfo().getCellQuerys();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                if (!dataLinkKey.equals(cellQueryInfo.getCellKey())) continue;
                SortingMethod sortingMe = new SortingMethod();
                StringBuffer cellFilterBuf = new StringBuffer();
                FieldData fieldData = this.regionRelationEvn.getFieldByDataLink(dataLinkKey);
                fieldData.setDataLinkKey(dataLinkKey);
                cellFilterBuf = sortingMe.sortingMethod(cellQueryInfo, fieldData, (ICommonQuery)this.groupingQuery, columnIndex, this.regionQueryInfo.getContext());
                if (StringUtils.isNotEmpty((String)cellQueryInfo.getShortcuts()) && cellQueryInfo.getShortcuts().contains(SortingMethod.topTen)) {
                    this.regionRelationEvn.setPaginate(false);
                }
                if (this.filterBuf.length() == 0) {
                    this.filterBuf.append(cellFilterBuf);
                    break;
                }
                if (this.filterBuf.length() == 0 || cellFilterBuf.length() == 0) break;
                this.filterBuf.append(" AND " + cellFilterBuf);
                break;
            }
        }
    }

    protected void addGroupingColumn() {
        LinkedHashMap<String, GradeCellInfo> gradeEntityCellMap = new LinkedHashMap<String, GradeCellInfo>();
        RegionGradeInfo grade = this.regionQueryInfo.getRestructureInfo().getGrade();
        for (GradeCellInfo gradeCell : grade.getGradeCells()) {
            FieldGatherType fieldGatherType;
            String dataLinkKey;
            if (!UUIDUtils.isUUID((String)gradeCell.getZbid()) || !this.gradeCellIndex.containsKey(dataLinkKey = this.regionRelationEvn.getDataLinkKeyByFiled(gradeCell.getZbid()))) continue;
            int columnIndex = this.gradeCellIndex.get(dataLinkKey);
            LinkData dataLink = this.regionRelationEvn.getDataLinkByKey(dataLinkKey);
            gradeEntityCellMap.put(dataLinkKey, gradeCell);
            this.groupingQuery.addGroupColumn(columnIndex);
            if (gradeCell.getGatherType() == dataLink.getZbgather() || (fieldGatherType = FieldGatherType.forValue((int)gradeCell.getGatherType())) == null) continue;
            this.groupingQuery.setGatherType(columnIndex, FieldGatherType.forValue((int)gradeCell.getGatherType()));
        }
        if (gradeEntityCellMap.size() > 0 || !grade.getLevels().isEmpty()) {
            this.regionRelationEvn.setEntityGrade(true);
            this.jtableDataEngineService.setDataRegTotalInfo(this.groupingQuery, gradeEntityCellMap, this.gradeCellIndex, grade.getLevels());
        }
    }

    public AbstractData expressionEvaluat(String defaultVaule) {
        AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(defaultVaule, this.regionQueryInfo.getContext(), this.context.getVarDimensionValueSet());
        return expressionEvaluat;
    }

    protected abstract void addOrderColumn();

    public List<String> getCells() {
        return this.cells;
    }

    public List<String> getGroupingLinks() {
        return this.groupingLinks;
    }

    public RegionQueryInfo getRegionQueryInfo() {
        return this.regionQueryInfo;
    }

    protected List<IExpression> getExpressions() {
        ArrayList<String> linkCodes = new ArrayList<String>();
        HashSet<String> tableMap = new HashSet<String>();
        for (Map.Entry<String, LinkData> linkData : this.regionRelationEvn.getDataLinkMap().entrySet()) {
            FieldData fieldData = this.regionRelationEvn.getFieldByDataLink(linkData.getKey());
            if (fieldData == null || fieldData.getFieldGather() != FieldGatherType.FIELD_GATHER_NONE.getValue()) continue;
            linkCodes.add(linkData.getValue().getUniqueCode());
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(fieldData.getOwnerTableKey());
            tableMap.add(dataTable.getCode());
        }
        if (linkCodes.size() <= 0) {
            return null;
        }
        List<IParsedExpression> parsedExpressions = this.jtableDataEngineService.getExpressionsByLinks(linkCodes, this.regionQueryInfo.getContext().getFormulaSchemeKey(), this.regionQueryInfo.getContext().getFormKey(), DataEngineConsts.FormulaType.CALCULATE, Consts.WRITE_ENUM);
        if (parsedExpressions == null || parsedExpressions.size() <= 0) {
            return null;
        }
        for (int index = parsedExpressions.size() - 1; index >= 0; --index) {
            IParsedExpression iParsedExpression = parsedExpressions.get(index);
            QueryFields queryFields = iParsedExpression.getQueryFields();
            int fieldCount = queryFields.getCount();
            boolean needRemove = false;
            for (int fieldIndex = 0; fieldIndex < fieldCount; ++fieldIndex) {
                QueryField queryField = queryFields.getItem(fieldIndex);
                if (tableMap.contains(queryField.getTableName())) continue;
                needRemove = true;
                break;
            }
            if (!needRemove) continue;
            parsedExpressions.remove(index);
        }
        return parsedExpressions.stream().map(t -> t.getRealExpression()).collect(Collectors.toList());
    }

    public void setEnumFilledQuery(List<FieldDefine> enumFields, List<List<String>> allEntityData) {
        this.groupingQuery.setFilledEnumLinks(enumFields, allEntityData);
    }
}

