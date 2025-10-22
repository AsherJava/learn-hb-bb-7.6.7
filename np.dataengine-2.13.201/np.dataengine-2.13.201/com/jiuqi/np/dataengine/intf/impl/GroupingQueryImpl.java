/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.sql.SummarizingMethod
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.GroupingTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.query.AdvancedGatherHelper;
import com.jiuqi.np.dataengine.query.FilledEnumLinkHelper;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.old.AdvancedGatherHelper_old;
import com.jiuqi.np.dataengine.query.old.GroupingQueryBuilder;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.sql.SummarizingMethod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GroupingQueryImpl
extends CommonQueryImpl
implements IGroupingQuery {
    private SummarizingMethod summarizingMethod;
    private List<Integer> groupColumns = new ArrayList<Integer>();
    private boolean wantDetail;
    private boolean sortGroupingAndDetailRows;
    private ArrayList<FieldGatherType> gatherTypes = new ArrayList();
    private HashMap<Integer, Boolean> setTypes = new HashMap();
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
    private boolean excuteCustomGather = true;
    private List<IExpression> expressions;
    private boolean hidenRow = false;
    private List<FieldDefine> enumFields;
    private List<List<String>> enumObjects;
    private boolean oldQueryModule = false;
    private boolean groupRowCalc = true;

    @Override
    public void setSummarizingMethod(SummarizingMethod summarizingMethod) {
        this.summarizingMethod = summarizingMethod;
    }

    public SummarizingMethod getSummarizingMethod() {
        return this.summarizingMethod;
    }

    @Override
    public void addGroupColumn(int columnIndex) {
        this.groupColumns.add(columnIndex);
    }

    @Override
    public int addGroupColumn(FieldDefine fieldDefine) {
        int columnIndex = this.addColumn(fieldDefine);
        this.groupColumns.add(columnIndex);
        return columnIndex;
    }

    @Override
    public void setWantDetail(boolean value) {
        this.wantDetail = value;
    }

    public boolean getWantDetail() {
        return this.wantDetail;
    }

    @Override
    public void setSortGroupingAndDetailRows(boolean value) {
        this.sortGroupingAndDetailRows = value;
    }

    public boolean getSortGroupingAndDetailRows() {
        return this.sortGroupingAndDetailRows;
    }

    @Override
    public FieldGatherType getGatherType(int columnIndex) {
        this.assureGatherTypesCount();
        return this.gatherTypes.get(columnIndex);
    }

    @Override
    public void setGatherType(int columnIndex, FieldGatherType gatherType) {
        this.assureGatherTypesCount();
        this.gatherTypes.set(columnIndex, gatherType);
        this.setTypes.put(columnIndex, gatherType == FieldGatherType.FIELD_GATHER_NONE);
    }

    /*
     * Loose catch block
     */
    @Override
    public IGroupingTable executeReader(ExecutorContext eContext) throws Exception {
        if (this.oldQueryModule) {
            return this.executeOldQuery(eContext);
        }
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_GROUP);
        this.assureGatherTypesCount();
        try {
            try (TempResource tempResource = new TempResource();){
                QueryContext queryContext = this.getQueryContext(eContext, tempResource, true);
                this.adjustQueryVersionDate(queryContext);
                com.jiuqi.np.dataengine.query.GroupingQueryBuilder builder = new com.jiuqi.np.dataengine.query.GroupingQueryBuilder();
                this.initSummarizingMethod();
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                builder.buildGroupingQuery(queryContext, this);
                builder.runQuery(queryContext, this.rowsPerPage, this.pageIndex * this.rowsPerPage);
                GroupingTableImpl result = (GroupingTableImpl)builder.getResultTable();
                if (!queryContext.isEnableNrdb()) {
                    FilledEnumLinkHelper.filledGroupingDataByEnumLinks(result, this.wantDetail, this.groupColumns, this.enumFields, this.enumObjects);
                    this.initBaseRow(result);
                    if (this.excuteCustomGather) {
                        this.executeAdvancedGather(eContext, result, false);
                    }
                    if (this.hidenRow && this.sortGroupingAndDetailRows) {
                        this.executeHidenOneDetailRow(result);
                    }
                }
                this.updateTableTotalCount(result, this.rowsPerPage);
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
    private IGroupingTable executeOldQuery(ExecutorContext eContext) throws Exception {
        this.ininMonitor(DataEngineConsts.DataEngineRunType.QUERY_GROUP);
        this.assureGatherTypesCount();
        try {
            try (TempResource tempResource = new TempResource();){
                QueryContext queryContext = this.getQueryContext(eContext, tempResource, true);
                queryContext.setOldQueryModule(this.oldQueryModule);
                queryContext.setQueryModule(true);
                this.adjustQueryVersionDate(queryContext);
                GroupingQueryBuilder builder = new GroupingQueryBuilder();
                this.initSummarizingMethod();
                builder.setQueryParam(this.queryParam);
                builder.setMainTableName(this.tableName);
                builder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
                builder.buildGroupingQuery(queryContext, this);
                builder.runQuery(queryContext, this.rowsPerPage, this.pageIndex * this.rowsPerPage);
                com.jiuqi.np.dataengine.query.old.GroupingTableImpl result = (com.jiuqi.np.dataengine.query.old.GroupingTableImpl)builder.getResultTable();
                if (this.rowsPerPage < 0) {
                    result.setTotalCount(result.getCount());
                }
                com.jiuqi.np.dataengine.query.old.GroupingTableImpl groupingTableImpl = result;
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

    private void initBaseRow(GroupingTableImpl result) {
        List<DataRowImpl> dataRowImpls = result.getAllDataRows();
        for (DataRowImpl dataRowImpl : dataRowImpls) {
            if (dataRowImpl.getGroupingFlag() < 0) continue;
            dataRowImpl.setBaseRow(true);
        }
    }

    private void executeHidenOneDetailRow(GroupingTableImpl result) {
        ArrayList dataRowImpls = result.dataRows;
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
                detailRow.setParentLevel(dataRowImpl.getGroupingLevel());
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
        if (result.dataRows.size() > 1 && result.dataRows.get(0).getGroupingFlag() >= 0 && (this.groupColumns.size() == 0 || !this.allowEntityLevelGather || this.allowEntityLevelGather && this.groupColumns.get(0) != this.entityLevelIndex || this.allowEntityLevelGather && this.entityLevels != null && this.entityLevels.size() > 0 && !this.entityLevels.contains(1))) {
            DataRowImpl dataRowImpl = result.dataRows.get(0);
            if (dataRowImpl.isVirtualRow()) {
                return;
            }
            result.dataRows.remove(0);
        }
    }

    private void removeRootGatherRow_old(com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl result) {
        if (result.dataRows.size() > 1 && result.dataRows.get(0).getGroupingFlag() >= 0 && (this.groupColumns.size() == 0 || !this.allowEntityLevelGather || this.allowEntityLevelGather && this.groupColumns.get(0) != this.entityLevelIndex || this.allowEntityLevelGather && this.entityLevels != null && this.entityLevels.size() > 0 && !this.entityLevels.contains(1))) {
            com.jiuqi.np.dataengine.query.old.DataRowImpl dataRowImpl = result.dataRows.get(0);
            if (dataRowImpl.isVirtualRow()) {
                return;
            }
            result.dataRows.remove(0);
        }
    }

    private void updateTableTotalCount(ReadonlyTableImpl result, int rowsPerPage) {
        if (rowsPerPage < 0) {
            result.setTotalCount(result.getCount());
        }
    }

    private boolean needRemoveRootRow(ReadonlyTableImpl result) {
        DataRowImpl dataRowImpl2;
        ArrayList<DataRowImpl> dataRows = result.dataRows;
        if (dataRows.size() <= 0) {
            return false;
        }
        int rootFlag = dataRows.get(0).getGroupingFlag();
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
        dataRowImpl2 = dataRows.get(0);
        if (dataRowImpl2.isVirtualRow()) {
            return false;
        }
        dataRows.remove(0);
        return true;
    }

    private boolean needRemoveRootRow_old(com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl result) {
        com.jiuqi.np.dataengine.query.old.DataRowImpl dataRowImpl2;
        ArrayList<com.jiuqi.np.dataengine.query.old.DataRowImpl> dataRows = result.dataRows;
        if (dataRows.size() <= 0) {
            return false;
        }
        int rootFlag = dataRows.get(0).getGroupingFlag();
        if (rootFlag <= 0) {
            return false;
        }
        HashMap<Integer, Integer> rowFlags = new HashMap<Integer, Integer>();
        for (com.jiuqi.np.dataengine.query.old.DataRowImpl dataRowImpl2 : dataRows) {
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
        dataRowImpl2 = dataRows.get(0);
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

    public void executeAdvancedGather_old(ExecutorContext context, com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl tableImpl, boolean mergeRows) throws Exception {
        boolean hasRemove;
        AdvancedGatherHelper_old helper = new AdvancedGatherHelper_old(context, tableImpl, context.getCache(), this.masterKeys, this.groupColumns, this.gatherTypes, this.queryParam, this.setTypes);
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
        if (!(hasRemove = this.needRemoveRootRow_old(tableImpl)) && !this.hasRootGatherRow) {
            this.removeRootGatherRow_old(tableImpl);
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

    @Override
    public void setDataRegTotalInfo(DataRegTotalInfo dataRegTotalInfo) {
        this.dataRegTotalInfo = dataRegTotalInfo;
    }

    @Override
    public void setEntityLevelGather(String parentEntity, int entityColumnIndex, EntityViewDefine viewDefine, List<Integer> entityLevels, ReloadTreeInfo reloadTreeInfo) {
        this.allowEntityLevelGather = true;
        this.parentEntity = parentEntity;
        this.entityLevelIndex = entityColumnIndex;
        this.dwViewDefine = viewDefine;
        this.entityLevels = entityLevels;
        this.reloadTreeInfo = reloadTreeInfo;
    }

    @Override
    public void setPeriodLevelGather(int periodColumnIndex, List<Integer> periodLevels) {
        this.allowPeriodLevelGather = true;
        this.periodLevelIndex = periodColumnIndex;
        this.periodLevels = periodLevels;
    }

    public HashMap<Integer, Boolean> getSetTypes() {
        return this.setTypes;
    }

    @Override
    public void setHasRootGatherRow(boolean value) {
        this.hasRootGatherRow = value;
    }

    public boolean getHasRootGatherRow() {
        return this.hasRootGatherRow;
    }

    @Override
    public void setQueryModule(boolean queryModule) {
        this.queryModule = queryModule;
    }

    public boolean isQueryModule() {
        return this.queryModule;
    }

    public void setExcuteCustomGather(boolean value) {
        this.excuteCustomGather = value;
    }

    @Override
    public void setCalcExpressions(List<IExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void setHidenOneDetailRow(boolean hidenRow) {
        this.hidenRow = hidenRow;
    }

    @Override
    public void setFilledEnumLinks(List<FieldDefine> enumFields, List<List<String>> enumObjects) {
        this.enumFields = enumFields;
        this.enumObjects = enumObjects;
    }

    @Override
    public void setGroupRowCalc(boolean needCalc) {
        this.groupRowCalc = needCalc;
    }

    @Override
    public void setOldQueryModule(boolean oldQueryModule) {
        this.oldQueryModule = oldQueryModule;
    }
}

