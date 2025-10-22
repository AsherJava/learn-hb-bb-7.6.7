/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.GroupingTableImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.query.DataQueryBuilder
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.FieldsInfoImpl
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.sql.SummarizingMethod
 *  org.springframework.data.util.Pair
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.GroupingTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.data.engine.grouping.AdvancedGatherHelper;
import com.jiuqi.nr.data.engine.grouping.FilledEnumLinkHelper;
import com.jiuqi.nr.data.engine.grouping.GroupKey;
import com.jiuqi.nr.data.engine.grouping.GroupingQueryBuilder;
import com.jiuqi.nr.data.engine.grouping.multi.GroupRowTreeNode;
import com.jiuqi.nr.data.engine.grouping.multi.GroupingSortComparator;
import com.jiuqi.nr.data.engine.grouping.multi.IMultiGroupingColumn;
import com.jiuqi.nr.data.engine.grouping.multi.MultiGroupingRow;
import com.jiuqi.nr.data.engine.grouping.multi.StatGroupingColumn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

public class GroupingQueryImpl
extends CommonQueryImpl
implements IGroupingQuery {
    private static final Logger LOG = LoggerFactory.getLogger(GroupingQueryImpl.class);
    private SummarizingMethod summarizingMethod;
    private final List<Integer> groupColumns = new ArrayList<Integer>();
    private boolean wantDetail;
    private boolean sortGroupingAndDetailRows;
    private final ArrayList<FieldGatherType> gatherTypes = new ArrayList();
    private final HashMap<Integer, Boolean> setTypes = new HashMap();
    private DataRegTotalInfo dataRegTotalInfo;
    private boolean allowPeriodLevelGather;
    private Integer periodLevelIndex;
    private List<Integer> periodLevels;
    private boolean allowEntityLevelGather;
    private String parentEntity;
    private Integer entityLevelIndex;
    private List<Integer> entityLevels;
    private EntityViewDefine dwViewDefine;
    private ReloadTreeInfo reloadTreeInfo;
    private boolean hasRootGatherRow = true;
    private boolean queryModule = false;
    private boolean excuteCustomGather = true;
    private List<IExpression> expressions;
    private boolean hidenRow = false;
    private List<FieldDefine> enumFields;
    private List<List<String>> enumObjects;
    private boolean groupRowCalc = true;

    public void setSummarizingMethod(SummarizingMethod summarizingMethod) {
        this.summarizingMethod = summarizingMethod;
    }

    public SummarizingMethod getSummarizingMethod() {
        return this.summarizingMethod;
    }

    public void addGroupColumn(int columnIndex) {
        this.groupColumns.add(columnIndex);
    }

    public int addGroupColumn(FieldDefine fieldDefine) {
        int columnIndex = this.addColumn(fieldDefine);
        this.groupColumns.add(columnIndex);
        return columnIndex;
    }

    public void setWantDetail(boolean value) {
        this.wantDetail = value;
    }

    public boolean getWantDetail() {
        return this.wantDetail;
    }

    public void setSortGroupingAndDetailRows(boolean value) {
        this.sortGroupingAndDetailRows = value;
    }

    public boolean getSortGroupingAndDetailRows() {
        return this.sortGroupingAndDetailRows;
    }

    public FieldGatherType getGatherType(int columnIndex) {
        this.assureGatherTypesCount();
        return this.gatherTypes.get(columnIndex);
    }

    public void setGatherType(int columnIndex, FieldGatherType gatherType) {
        this.assureGatherTypesCount();
        this.gatherTypes.set(columnIndex, gatherType);
        this.setTypes.put(columnIndex, gatherType == FieldGatherType.FIELD_GATHER_NONE);
    }

    /*
     * Loose catch block
     */
    public IGroupingTable executeReader(ExecutorContext eContext) throws Exception {
        if (this.excuteCustomGather && this.dataRegTotalInfo != null && this.dataRegTotalInfo.isMultiGradeLevels()) {
            return this.executeMultiGradeReader(eContext);
        }
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_GROUP);
        try {
            try (TempResource tempResource = new TempResource();){
                this.assureGatherTypesCount();
                QueryContext queryContext = this.getQueryContext(eContext, tempResource, true);
                this.adjustQueryVersionDate(queryContext);
                GroupingQueryBuilder builder = new GroupingQueryBuilder();
                this.initSummarizingMethod();
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.buildGroupingQuery(queryContext, this);
                builder.runQuery(queryContext, this.rowsPerPage, this.pageIndex * this.rowsPerPage);
                GroupingTableImpl result = (GroupingTableImpl)builder.getResultTable();
                FilledEnumLinkHelper.filledGroupingDataByEnumLinks(result, this.wantDetail, this.groupColumns, this.enumFields, this.enumObjects);
                this.initBaseRow(result);
                if (this.excuteCustomGather) {
                    this.executeAdvancedGather(eContext, (ReadonlyTableImpl)result, false);
                }
                if (this.hidenRow && this.sortGroupingAndDetailRows) {
                    this.executeHidenOneDetailRow(result);
                }
                this.updateTableTotalCount((ReadonlyTableImpl)result, this.rowsPerPage);
                GroupingTableImpl groupingTableImpl = result;
                return groupingTableImpl;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    /*
     * Loose catch block
     */
    private IGroupingTable executeMultiGradeReader(ExecutorContext eContext) throws Exception {
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_GROUP);
        try {
            try (TempResource tempResource = new TempResource();){
                this.assureGatherTypesCount();
                QueryContext queryContext = this.getQueryContext(eContext, tempResource, true);
                this.adjustQueryVersionDate(queryContext);
                DataQueryBuilder builder = new DataQueryBuilder();
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.buildQuery(queryContext, (CommonQueryImpl)this, true);
                builder.runQuery(queryContext, this.rowsPerPage, this.pageIndex * this.rowsPerPage);
                ReadonlyTableImpl detailResult = builder.getResultTable();
                GroupingTableImpl result = this.executeMultiGradeGather(queryContext, detailResult);
                this.updateTableTotalCount((ReadonlyTableImpl)result, this.rowsPerPage);
                GroupingTableImpl groupingTableImpl = result;
                return groupingTableImpl;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    public GroupingTableImpl executeMultiGradeGather(QueryContext queryContext, ReadonlyTableImpl detailTable) {
        IFieldsInfo fieldsInfo = detailTable.getFieldsInfo();
        GroupingTableImpl groupTable = this.initGroupingTable(queryContext, fieldsInfo);
        GroupRowTreeNode rootRow = this.processGrouping(queryContext, detailTable, groupTable);
        this.processGroupRow(fieldsInfo, rootRow);
        this.setSortedResult(groupTable, rootRow);
        this.groupRowCalc(queryContext, groupTable);
        return groupTable;
    }

    private GroupingTableImpl initGroupingTable(QueryContext queryContext, IFieldsInfo fieldsInfo) {
        GroupingTableImpl result = new GroupingTableImpl(queryContext, this.masterKeys, fieldsInfo.getFieldCount());
        result.setNoneGatherCols(new ArrayList());
        result.setSupportTreeGroup(true);
        result.fieldsInfoImpl = (FieldsInfoImpl)fieldsInfo;
        return result;
    }

    private GroupRowTreeNode processGrouping(QueryContext queryContext, ReadonlyTableImpl detailResult, GroupingTableImpl groupTable) {
        List allDetailRows = detailResult.getAllDataRows();
        List<Integer> precisions = this.initPrecisions(this.dataRegTotalInfo.getPrecisions());
        this.dataRegTotalInfo.setPrecisions(precisions);
        MultiGroupingRow rootRow = this.createRootRow(queryContext, detailResult.getFieldsInfo(), groupTable);
        String rootGroupKey = rootRow.getRowKeys().getValue("GROUP_KEY").toString();
        GroupRowTreeNode root = new GroupRowTreeNode(rootRow, rootGroupKey);
        HashMap<String, GroupRowTreeNode> rowTreeNodeMap = new HashMap<String, GroupRowTreeNode>();
        int maxDeep = 0;
        for (DataRowImpl dataRow : allDetailRows) {
            if (this.getHasRootGatherRow()) {
                this.updateGroupingRow(detailResult.getFieldsInfo(), rootRow, dataRow);
            }
            root.childrenSizePlus();
            GroupRowTreeNode branchRoot = this.processRowGrouping(queryContext, detailResult.getFieldsInfo(), groupTable, rowTreeNodeMap, dataRow);
            root.addChild(branchRoot);
            maxDeep = Math.max(branchRoot.getDeep() + 1, maxDeep);
        }
        if (this.getHasRootGatherRow()) {
            rootRow.setGroupingFlag(maxDeep);
            rootRow.setGroupTreeDeep(maxDeep);
            rootRow.getRowKeys().setValue("GroupingDeep", (Object)maxDeep);
            rowTreeNodeMap.put(rootGroupKey, root);
        }
        return root;
    }

    private GroupRowTreeNode processRowGrouping(QueryContext queryContext, IFieldsInfo fieldsInfo, GroupingTableImpl groupTable, Map<String, GroupRowTreeNode> rowTreeNodeMap, DataRowImpl dataRow) {
        GroupKey group = this.getGroupKey(dataRow);
        GroupRowTreeNode preNode = null;
        int deep = 0;
        for (int lev = this.dataRegTotalInfo.getGradeLevels().size() - 1; lev >= 0; --lev) {
            Integer levelLength = (Integer)this.dataRegTotalInfo.getGradeLevels().get(lev);
            Pair<String, GradeLinkItem> levelKey = group.getLevelKey(levelLength);
            String groupKey = (String)levelKey.getFirst();
            GradeLinkItem keyEndWithLink = (GradeLinkItem)levelKey.getSecond();
            if (keyEndWithLink.getEntityView() != null && levelLength > group.length()) {
                ++deep;
                continue;
            }
            if (keyEndWithLink.getEntityView() != null && groupKey.charAt(groupKey.length() - 1) == '#') {
                ++deep;
                continue;
            }
            GroupRowTreeNode currNode = rowTreeNodeMap.get(groupKey);
            if (currNode == null) {
                MultiGroupingRow currRow = this.createGroupingRow(queryContext, fieldsInfo, groupTable, groupKey, deep);
                currNode = new GroupRowTreeNode(currRow, groupKey);
                rowTreeNodeMap.put(groupKey, currNode);
            }
            currNode.childrenSizePlus();
            if (preNode != null) {
                currNode.addChild(preNode);
                preNode = currNode;
            } else {
                preNode = currNode;
                preNode.addChild(new GroupRowTreeNode(dataRow, group.getGroupKeyValue()), true);
            }
            this.updateGroupingRow(fieldsInfo, currNode.getDataRow(), dataRow);
            ++deep;
        }
        return preNode;
    }

    private MultiGroupingRow createGroupingRow(QueryContext queryContext, IFieldsInfo fieldsInfo, GroupingTableImpl groupTable, String groupKey, int deep) {
        DimensionValueSet rowKey = new DimensionValueSet(queryContext.getMasterKeys());
        rowKey.setValue("GROUP_KEY", (Object)groupKey);
        IMultiGroupingColumn[] groupingColumns = this.getGroupColumn(fieldsInfo);
        MultiGroupingRow groupingRow = new MultiGroupingRow(groupingColumns, (ReadonlyTableImpl)groupTable, rowKey);
        groupingRow.setGroupingFlag(deep);
        groupingRow.setGroupTreeDeep(deep);
        groupingRow.getRowKeys().setValue("GroupingDeep", (Object)deep);
        return groupingRow;
    }

    private void updateGroupingRow(IFieldsInfo fieldsInfo, DataRowImpl groupingRow, DataRowImpl dataRow) {
        for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
            groupingRow.setValue(i, dataRow.getValue(i).getAsObject());
        }
    }

    private MultiGroupingRow createRootRow(QueryContext queryContext, IFieldsInfo fieldsInfo, GroupingTableImpl result) {
        DimensionValueSet rowKey = new DimensionValueSet(queryContext.getMasterKeys());
        rowKey.setValue("GROUP_KEY", (Object)"");
        IMultiGroupingColumn[] groupingColumns = this.getGroupColumn(fieldsInfo);
        return new MultiGroupingRow(groupingColumns, (ReadonlyTableImpl)result, rowKey);
    }

    private void processGroupRow(IFieldsInfo fieldsInfo, GroupRowTreeNode rootRow) {
        for (GroupRowTreeNode groupRowTreeNode : rootRow) {
            if (groupRowTreeNode.getDataRow().getGroupTreeDeep() < 0) continue;
            String groupKey = groupRowTreeNode.getGroupKey();
            DataRowImpl groupingRow = groupRowTreeNode.getDataRow();
            for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
                Object sumValue = null;
                FieldType dataType = fieldsInfo.getDataType(i);
                if (dataType == FieldType.FIELD_TYPE_DECIMAL || dataType == FieldType.FIELD_TYPE_INTEGER || dataType == FieldType.FIELD_TYPE_FLOAT || dataType == FieldType.FIELD_TYPE_STRING) {
                    sumValue = groupingRow.getValueObject(i);
                }
                groupingRow.getRowDatas().add(sumValue);
            }
            int startIndex = 0;
            int itemIndex = -1;
            for (GradeTotalItem item : this.dataRegTotalInfo.getGradeTotalItems()) {
                ++itemIndex;
                int colIndex = item.getColumnIndex();
                String value = (String)groupingRow.getValueObject(colIndex);
                if (value == null) continue;
                Integer precision = (Integer)this.dataRegTotalInfo.getPrecisions().get(itemIndex);
                if (precision != null && value.length() > precision) {
                    value = value.substring(0, precision);
                }
                int endIndex = startIndex + value.length();
                StringBuilder groupColumnValue = null;
                if (groupKey.length() > startIndex) {
                    groupColumnValue = new StringBuilder();
                    for (int i = startIndex; i < endIndex && i < groupKey.length(); ++i) {
                        char charCode = groupKey.charAt(i);
                        if ('#' == charCode) {
                            ++endIndex;
                            continue;
                        }
                        groupColumnValue.append(charCode);
                    }
                    if (item.isNeedEnd0() && groupColumnValue.length() < value.length()) {
                        int zeroCount = value.length() - groupColumnValue.length();
                        for (int i = 0; i < zeroCount; ++i) {
                            groupColumnValue.append("0");
                        }
                    }
                }
                value = null;
                if (groupColumnValue != null) {
                    value = groupColumnValue.toString();
                }
                startIndex = endIndex;
                groupingRow.getRowDatas().set(colIndex, value);
            }
        }
    }

    private void setSortedResult(GroupingTableImpl result, GroupRowTreeNode rootRow) {
        if (CollectionUtils.isEmpty(this.orderColumns)) {
            rootRow.sortTreeByGroupKey();
        } else {
            rootRow.sortTreeByComparator(new GroupingSortComparator(this.orderColumns));
        }
        ArrayList<DataRowImpl> allDataRows = new ArrayList<DataRowImpl>();
        rootRow.setHasRootRow(this.getHasRootGatherRow());
        if (this.wantDetail && this.hidenRow) {
            rootRow.removeSingleDetailNodes();
        } else if (!this.wantDetail) {
            rootRow.removeDetailRows();
        }
        for (GroupRowTreeNode groupRowTreeNode : rootRow) {
            allDataRows.add(groupRowTreeNode.getDataRow());
        }
        result.setAllDataRows(allDataRows);
    }

    private void groupRowCalc(QueryContext queryContext, GroupingTableImpl result) {
        if (this.groupRowCalc) {
            try {
                AdvancedGatherHelper helper = new AdvancedGatherHelper(queryContext.getExeContext(), (ReadonlyTableImpl)result, queryContext.getExeContext().getCache(), this.masterKeys, this.groupColumns, this.gatherTypes, this.queryParam, this.setTypes);
                helper.reCalcExpressions(this.expressions);
            }
            catch (InterpretException | ParseException e) {
                LOG.warn("\u884c\u5185\u516c\u5f0f\u8ba1\u7b97\u5931\u8d25", e);
            }
        }
    }

    private List<Integer> initPrecisions(List<Integer> precisions) {
        if (CollectionUtils.isEmpty(precisions)) {
            precisions = new ArrayList<Integer>();
            for (Object ignored : this.dataRegTotalInfo.getGradeTotalItems()) {
                precisions.add(null);
            }
        }
        for (int i = 0; i < this.dataRegTotalInfo.getGradeTotalItems().size() - precisions.size(); ++i) {
            precisions.add(null);
        }
        return precisions;
    }

    private IMultiGroupingColumn[] getGroupColumn(IFieldsInfo fieldsInfo) {
        IMultiGroupingColumn[] groupingColumns = new IMultiGroupingColumn[fieldsInfo.getFieldCount()];
        for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
            FieldDefine fieldDefine = fieldsInfo.getFieldDefine(i);
            if (fieldDefine != null) {
                FieldGatherType gatherType = fieldDefine.getGatherType();
                int statKind = DataTypesConvert.gatherTypeToStatKind((FieldGatherType)gatherType);
                groupingColumns[i] = new StatGroupingColumn(statKind, DataTypesConvert.fieldTypeToDataType((FieldType)fieldsInfo.getDataType(i)));
                continue;
            }
            groupingColumns[i] = new StatGroupingColumn(DataTypesConvert.fieldTypeToDataType((FieldType)fieldsInfo.getDataType(i)));
        }
        return groupingColumns;
    }

    private GroupKey getGroupKey(DataRowImpl dataRow) {
        GroupKey groupKey = new GroupKey();
        groupKey.setKeys(new ArrayList<String>());
        groupKey.setDataRegTotalInfo(this.dataRegTotalInfo);
        for (int i = 0; i < this.dataRegTotalInfo.getGradeTotalItems().size(); ++i) {
            GradeTotalItem gradeTotalItem = (GradeTotalItem)this.dataRegTotalInfo.getGradeTotalItems().get(i);
            String value = dataRow.getAsString(gradeTotalItem.getColumnIndex().intValue());
            if (StringUtils.isEmpty((String)value)) {
                value = "";
            }
            groupKey.getKeys().add(value);
        }
        return groupKey;
    }

    private void initBaseRow(GroupingTableImpl result) {
        List dataRowImpls = result.getAllDataRows();
        for (DataRowImpl dataRowImpl : dataRowImpls) {
            if (dataRowImpl.getGroupingFlag() < 0) continue;
            dataRowImpl.setBaseRow(true);
        }
    }

    private void executeHidenOneDetailRow(GroupingTableImpl result) {
        List dataRowImpls = result.getAllDataRows();
        if (dataRowImpls == null || dataRowImpls.size() <= 0) {
            return;
        }
        int detailCount = 0;
        DataRowImpl detailRow = null;
        for (int index = dataRowImpls.size() - 1; index >= 0; --index) {
            DataRowImpl dataRowImpl = (DataRowImpl)dataRowImpls.get(index);
            if (dataRowImpl.getGroupingFlag() < 0) {
                detailRow = dataRowImpl;
                ++detailCount;
                continue;
            }
            if (dataRowImpl.getGroupingFlag() >= 0 && detailCount == 1 && dataRowImpl.isBaseRow()) {
                detailRow.setParentLevel(Integer.valueOf(dataRowImpl.getGroupingLevel()));
                dataRowImpls.remove(index);
            }
            detailCount = 0;
            detailRow = null;
        }
    }

    private void initSummarizingMethod() {
        if ((this.allowEntityLevelGather || this.allowPeriodLevelGather || this.dataRegTotalInfo != null) && this.summarizingMethod != SummarizingMethod.RollUp) {
            this.summarizingMethod = SummarizingMethod.RollUp;
        }
    }

    private void removeRootGatherRow(ReadonlyTableImpl result) {
        if (result.getAllDataRows().size() > 1 && ((DataRowImpl)result.getAllDataRows().get(0)).getGroupingFlag() >= 0 && (this.groupColumns.size() == 0 || !this.allowEntityLevelGather || this.allowEntityLevelGather && this.groupColumns.get(0) != this.entityLevelIndex || this.allowEntityLevelGather && this.entityLevels != null && this.entityLevels.size() > 0 && !this.entityLevels.contains(1))) {
            DataRowImpl dataRowImpl = (DataRowImpl)result.getAllDataRows().get(0);
            if (dataRowImpl.isVirtualRow()) {
                return;
            }
            result.getAllDataRows().remove(0);
        }
    }

    private void updateTableTotalCount(ReadonlyTableImpl result, int rowsPerPage) {
        if (rowsPerPage < 0) {
            result.setTotalCount(result.getCount());
        }
    }

    private boolean needRemoveRootRow(ReadonlyTableImpl result) {
        DataRowImpl dataRowImpl2;
        List dataRows = result.getAllDataRows();
        if (dataRows.size() <= 0) {
            return false;
        }
        int rootFlag = ((DataRowImpl)dataRows.get(0)).getGroupingFlag();
        if (rootFlag <= 0) {
            return false;
        }
        HashMap<Integer, Integer> rowFlags = new HashMap<Integer, Integer>();
        for (DataRowImpl dataRowImpl2 : dataRows) {
            int rowFlag = dataRowImpl2.getGroupingFlag();
            if (rowFlag < 0) continue;
            if (!rowFlags.containsKey(rowFlag)) {
                rowFlags.put(rowFlag, 1);
                continue;
            }
            rowFlags.put(rowFlag, (Integer)rowFlags.get(rowFlag) + 1);
        }
        if ((Integer)rowFlags.get(rootFlag) > 1 || rowFlags.size() <= 1) {
            return false;
        }
        int childFlag = (rootFlag + 1) / 2 - 1;
        if (!rowFlags.containsKey(childFlag)) {
            ArrayList flagList = new ArrayList(rowFlags.keySet());
            Collections.sort(flagList);
            childFlag = (Integer)flagList.get(flagList.size() - 2);
            if (!rowFlags.containsKey(childFlag)) {
                return false;
            }
        }
        if ((Integer)rowFlags.get(childFlag) > 1) {
            return false;
        }
        dataRowImpl2 = (DataRowImpl)dataRows.get(0);
        if (dataRowImpl2.isVirtualRow()) {
            return false;
        }
        dataRows.remove(0);
        return true;
    }

    public void executeAdvancedGather(ExecutorContext context, ReadonlyTableImpl tableImpl, boolean mergeRows) throws Exception {
        boolean hasRemove;
        AdvancedGatherHelper helper = new AdvancedGatherHelper(context, tableImpl, context.getCache(), this.masterKeys, this.groupColumns, this.gatherTypes, this.queryParam, this.setTypes);
        if (mergeRows) {
            helper.mergeGroupResult();
        }
        if (this.allowPeriodLevelGather && this.groupColumns.contains(this.periodLevelIndex)) {
            helper.doPeriodLevelGather(this.periodLevelIndex, this.periodLevels);
        }
        if (this.allowEntityLevelGather && !StringUtils.isEmpty((String)this.parentEntity) && this.groupColumns.contains(this.entityLevelIndex) && this.dwViewDefine != null) {
            helper.doEntityLevelGather(this.parentEntity, this.entityLevelIndex, this.dwViewDefine, this.entityLevels, this.reloadTreeInfo);
        }
        if (this.dataRegTotalInfo != null) {
            helper.fixTotalFieldValue(this.dataRegTotalInfo);
        }
        helper.reCalcExpFields();
        if (this.expressions != null && this.expressions.size() > 0) {
            helper.reCalcExpressions(this.expressions);
        }
        if (!(hasRemove = this.needRemoveRootRow(tableImpl)) && !this.hasRootGatherRow) {
            this.removeRootGatherRow(tableImpl);
        }
    }

    private void assureGatherTypesCount() {
        if (this.gatherTypes.size() >= this.columns.size()) {
            return;
        }
        int c = this.columns.size();
        for (int i = this.gatherTypes.size(); i < c; ++i) {
            this.gatherTypes.add(FieldGatherType.FIELD_GATHER_NONE);
        }
    }

    public ArrayList<FieldGatherType> getGatherTypes() {
        return this.gatherTypes;
    }

    public List<Integer> getGroupColumns() {
        return this.groupColumns;
    }

    public void setDataRegTotalInfo(DataRegTotalInfo dataRegTotalInfo) {
        this.dataRegTotalInfo = dataRegTotalInfo;
    }

    public void setEntityLevelGather(String parentEntity, int entityColumnIndex, EntityViewDefine viewDefine, List<Integer> entityLevels, ReloadTreeInfo reloadTreeInfo) {
        this.allowEntityLevelGather = true;
        this.parentEntity = parentEntity;
        this.entityLevelIndex = entityColumnIndex;
        this.dwViewDefine = viewDefine;
        this.entityLevels = entityLevels;
        this.reloadTreeInfo = reloadTreeInfo;
    }

    public void setPeriodLevelGather(int periodColumnIndex, List<Integer> periodLevels) {
        this.allowPeriodLevelGather = true;
        this.periodLevelIndex = periodColumnIndex;
        this.periodLevels = periodLevels;
    }

    public HashMap<Integer, Boolean> getSetTypes() {
        return this.setTypes;
    }

    public void setHasRootGatherRow(boolean value) {
        this.hasRootGatherRow = value;
    }

    public boolean getHasRootGatherRow() {
        return this.hasRootGatherRow;
    }

    public void setQueryModule(boolean queryModule) {
        this.queryModule = queryModule;
    }

    public boolean isQueryModule() {
        return this.queryModule;
    }

    public void setExcuteCustomGather(boolean value) {
        this.excuteCustomGather = value;
    }

    public void setCalcExpressions(List<IExpression> expressions) {
        this.expressions = expressions;
    }

    public void setHidenOneDetailRow(boolean hidenRow) {
        this.hidenRow = hidenRow;
    }

    public void setFilledEnumLinks(List<FieldDefine> enumFields, List<List<String>> enumObjects) {
        this.enumFields = enumFields;
        this.enumObjects = enumObjects;
    }

    public void setGroupRowCalc(boolean needCalc) {
        this.groupRowCalc = needCalc;
    }
}

