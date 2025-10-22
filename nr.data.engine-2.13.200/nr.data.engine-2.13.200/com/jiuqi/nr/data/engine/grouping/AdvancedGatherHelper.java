/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.BitMaskAndNullValue
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.query.DataQueryBuilder
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.DataRowReader
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.FieldsInfoImpl
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.DataRowReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.grouping.EnumRowItem;
import com.jiuqi.nr.data.engine.grouping.GradeEnumTree;
import com.jiuqi.nr.data.engine.grouping.GroupTreeRow;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvancedGatherHelper {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedGatherHelper.class);
    private final ExecutorContext context;
    private final ReadonlyTableImpl tableImpl;
    private final DefinitionsCache cache;
    private final DimensionValueSet masterKeys;
    private final List<FieldGatherType> gatherTypes;
    private final List<Integer> groupKeys;
    private final HashMap<Integer, BitMaskAndNullValue> grpByColsInGroupingId;
    private final FieldsInfoImpl fieldsInfoImpl;
    private List<DataRowImpl> dataRows;
    private int currentLevel = 0;
    private int adjustLevel = 0;
    private boolean isNewRow;
    private boolean removeSelf;
    private boolean needRemoveSelf;
    private List<GroupTreeRow> groupTreeRows = null;
    private HashMap<String, IEntityRow> entityCache = null;
    private HashMap<Integer, String> groupColDims = null;
    private final QueryParam queryParam;
    private final HashMap<Integer, Boolean> setTypes;

    public AdvancedGatherHelper(ExecutorContext context, ReadonlyTableImpl tableImpl, DefinitionsCache cache, DimensionValueSet masterKeys, List<Integer> groupColumnIndexes, List<FieldGatherType> gatherTypes, QueryParam queryParam, HashMap<Integer, Boolean> setTypes) {
        this.context = context;
        this.tableImpl = tableImpl;
        this.cache = cache;
        this.masterKeys = masterKeys;
        this.gatherTypes = gatherTypes;
        this.groupKeys = new ArrayList<Integer>(groupColumnIndexes);
        this.grpByColsInGroupingId = tableImpl.getGrpByColsEffectiveInGroupingId();
        this.fieldsInfoImpl = tableImpl.fieldsInfoImpl;
        this.dataRows = new ArrayList<DataRowImpl>(tableImpl.getAllDataRows());
        this.queryParam = queryParam;
        this.setTypes = setTypes;
    }

    public void doPeriodLevelGather(Integer periodLevelIndex, List<Integer> periodLevels) throws DataTypeException {
        DataRowImpl dataRowImpl;
        String periodDim;
        FieldDefine periodField = periodLevelIndex < 0 ? null : this.fieldsInfoImpl.getFieldDefine(periodLevelIndex.intValue());
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        ColumnModelDefine column = dataDefinitionsCache.getColumnModel(periodField);
        String string = periodDim = periodField == null ? "" : dataDefinitionsCache.getDimensionName(column);
        if (StringUtils.isEmpty((String)periodDim) || !periodDim.equals("DATATIME")) {
            return;
        }
        BitMaskAndNullValue periodNullValue = this.grpByColsInGroupingId.get(periodLevelIndex);
        ArrayList<GroupTreeRow> periodTreeRows = new ArrayList<GroupTreeRow>();
        ArrayList<DataRowImpl> appendRows = new ArrayList<DataRowImpl>();
        HashSet<Integer> avgFields = new HashSet<Integer>();
        this.groupTreeRows = this.getGroupTreeRows(periodNullValue, periodTreeRows);
        if (periodTreeRows.size() >= 1 && periodLevels != null && periodLevels.size() > 0) {
            this.insertVirtualPeriodTreeRows(periodLevelIndex, periodTreeRows, avgFields, appendRows, periodLevels);
            if (avgFields.size() > 0 && appendRows.size() > 0) {
                this.calcAvgGatherResult(this.groupTreeRows, avgFields, appendRows);
            }
        }
        int maxLevel = this.grpByColsInGroupingId.size() + this.adjustLevel;
        ArrayList<DataRowImpl> dataRowImpls = new ArrayList<DataRowImpl>(this.dataRows.size());
        AdvancedGatherHelper.getDataRows(this.groupTreeRows, dataRowImpls, maxLevel);
        if (this.dataRows.size() > 0 && this.groupKeys.get(0) == periodLevelIndex && this.masterKeys.hasValue("DATATIME") && !(dataRowImpl = this.dataRows.get(0)).isVirtualRow()) {
            this.dataRows.remove(0);
            if (this.groupTreeRows.size() == 1) {
                this.groupTreeRows = this.groupTreeRows.get(0).getChildRows();
                for (int index = this.groupTreeRows.size() - 1; index >= 0; --index) {
                    GroupTreeRow groupTreeRow = this.groupTreeRows.get(index);
                    groupTreeRow.setParentRow(null);
                }
            }
        }
        this.dataRows = dataRowImpls;
        this.tableImpl.setAllDataRows(dataRowImpls);
    }

    public void doEntityLevelGather(String parentEntity, Integer entityLevelIndex, EntityViewDefine dwViewDefine, List<Integer> entityLevels, ReloadTreeInfo reloadTreeInfo) throws Exception {
        DataRowImpl firstRow;
        FieldDefine entityField = entityLevelIndex < 0 ? null : this.fieldsInfoImpl.getFieldDefine(entityLevelIndex.intValue());
        ColumnModelDefine entityColumn = this.cache.getDataModelDefinitionsCache().getColumnModel(entityField);
        String fieldDim = entityField == null ? "" : this.cache.getDataModelDefinitionsCache().getDimensionName(entityColumn);
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        String entityId = dwViewDefine.getEntityId();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        TableModelDefine tableModel = entityMetaService.getTableModel(entityId);
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(tableModel.getCode());
        if (StringUtils.isEmpty((String)fieldDim) || tableRunInfo.getDimensions().size() != 1 || !tableRunInfo.getDimensions().contains(fieldDim) || this.dataRows.size() <= 0) {
            return;
        }
        String currentPeriod = this.getCurrentPeriod();
        if (StringUtils.isEmpty((String)currentPeriod)) {
            return;
        }
        List<DataRowImpl> dataRowImpls = this.doEntityLevelGatherOnePeriod(parentEntity, entityLevelIndex, dwViewDefine, entityLevels, currentPeriod, fieldDim, reloadTreeInfo);
        if (dataRowImpls == null) {
            return;
        }
        if (dataRowImpls.size() > 0 && this.groupKeys.get(0) == entityLevelIndex && (entityLevels == null || entityLevels.size() <= 0 || entityLevels.contains(1)) && this.dataRows.get(0).getGroupingFlag() >= 0 && this.dataRows.size() > 1 && !(firstRow = dataRowImpls.get(0)).isVirtualRow()) {
            dataRowImpls.remove(0);
            if (this.groupTreeRows.size() == 1) {
                this.groupTreeRows = this.groupTreeRows.get(0).getChildRows();
                for (int index = this.groupTreeRows.size() - 1; index >= 0; --index) {
                    GroupTreeRow groupTreeRow = this.groupTreeRows.get(index);
                    groupTreeRow.setParentRow(null);
                }
            }
        }
        this.dataRows = dataRowImpls;
        this.tableImpl.setAllDataRows(dataRowImpls);
    }

    public void fixTotalFieldValue(DataRegTotalInfo dataRegTotalInfo) throws Exception {
        List gradeTotalItems = dataRegTotalInfo.getGradeTotalItems();
        if (gradeTotalItems == null || gradeTotalItems.size() <= 0) {
            return;
        }
        HashMap<String, GradeEnumTree> gradeEnumTrees = new HashMap<String, GradeEnumTree>();
        for (GradeTotalItem gradeTotalItem : gradeTotalItems) {
            int cubeCount;
            ArrayList<Integer> gradeLevels;
            GradeLinkItem linkDefine = gradeTotalItem.getGradeLink();
            EntityViewDefine entityViewDefine = linkDefine.getEntityView();
            if (entityViewDefine == null) continue;
            String entityViewId = entityViewDefine.getEntityId();
            GradeEnumTree enumTree = null;
            if (!gradeEnumTrees.containsKey(entityViewId)) {
                enumTree = this.getEnumTree(entityViewDefine);
                gradeEnumTrees.put(entityViewId, enumTree);
            } else {
                enumTree = (GradeEnumTree)gradeEnumTrees.get(entityViewId);
            }
            if (enumTree == null || (gradeLevels = new ArrayList<Integer>(enumTree.getGradeLevels())).size() <= 1 || gradeTotalItem.getGradeSetting().size() > 0 && !gradeLevels.containsAll(gradeTotalItem.getGradeSetting())) continue;
            if (gradeTotalItem.getGradeSetting().size() > 0) {
                gradeLevels.retainAll(gradeTotalItem.getGradeSetting());
            }
            if (gradeLevels.size() == 1 && ((Integer)gradeLevels.get(0)).intValue() == enumTree.getTreeDepth()) continue;
            int enumColumnIndex = gradeTotalItem.getColumnIndex();
            BitMaskAndNullValue enumNullValue = this.grpByColsInGroupingId.get(enumColumnIndex);
            ArrayList<GroupTreeRow> enumGroupTreeRows = new ArrayList<GroupTreeRow>();
            this.currentLevel = 0;
            this.adjustLevel = 0;
            if (this.groupTreeRows == null) {
                cubeCount = this.grpByColsInGroupingId.size();
                this.groupTreeRows = this.getGroupTreeRows(enumNullValue, enumGroupTreeRows);
            } else {
                this.getEnumGroupTreeRows(this.groupTreeRows, enumNullValue, enumGroupTreeRows);
                cubeCount = (int)(Math.log(this.adjustLevel + 1) / Math.log(2.0));
            }
            int maxLevel = gradeLevels.size() + this.currentLevel - 1;
            this.removeSelf = false;
            HashSet<Integer> avgFields = new HashSet<Integer>();
            ArrayList<DataRowImpl> appendRows = new ArrayList<DataRowImpl>();
            ArrayList<GroupTreeRow> mergeGroupRows = new ArrayList<GroupTreeRow>();
            this.insertVirtualEnumGroupTreeRow(enumColumnIndex, gradeLevels, enumGroupTreeRows, enumTree, maxLevel, avgFields, appendRows, mergeGroupRows);
            if (this.removeSelf && mergeGroupRows.size() > 0) {
                this.mergeGroupTreeRows(mergeGroupRows, enumColumnIndex, avgFields, appendRows);
            }
            if (avgFields.size() > 0 && appendRows.size() > 0) {
                this.calcAvgGatherResult(this.groupTreeRows, avgFields, appendRows);
            }
            maxLevel = cubeCount + maxLevel - this.currentLevel;
            this.sortEnumCode(enumGroupTreeRows, enumColumnIndex, enumTree);
            ArrayList<DataRowImpl> dataRowImpls = new ArrayList<DataRowImpl>();
            AdvancedGatherHelper.getDataRows(this.groupTreeRows, dataRowImpls, maxLevel);
            this.dataRows = dataRowImpls;
            this.tableImpl.setAllDataRows(dataRowImpls);
        }
    }

    private void sortEnumCode(List<GroupTreeRow> enumGroupTreeRows, int enumColumnIndex, GradeEnumTree enumTree) {
        HashSet<GroupTreeRow> excuteRows = new HashSet<GroupTreeRow>();
        for (GroupTreeRow groupTreeRow : enumGroupTreeRows) {
            GroupTreeRow parentRow = groupTreeRow.getParentRow();
            if (parentRow == null || excuteRows.contains(parentRow)) continue;
            excuteRows.add(parentRow);
            List<GroupTreeRow> childRows = parentRow.getChildRows();
            if (childRows.size() <= 1) continue;
            HashMap<String, EnumRowItem> enumRowItems = enumTree.getEnumRowItems();
            List<GroupTreeRow> sortRows = childRows.stream().sorted((left, right) -> {
                Object leftCode = left.getCurrentRow().internalGetValue(enumColumnIndex);
                Object rightCode = right.getCurrentRow().internalGetValue(enumColumnIndex);
                if (leftCode == null) {
                    return rightCode == null ? 0 : -1;
                }
                if (rightCode == null) {
                    return 1;
                }
                Object leftOrder = null;
                if (enumRowItems.containsKey(leftCode.toString())) {
                    leftOrder = ((EnumRowItem)enumRowItems.get(leftCode.toString())).getOrder();
                }
                Object rightOrder = null;
                if (enumRowItems.containsKey(rightCode.toString())) {
                    rightOrder = ((EnumRowItem)enumRowItems.get(rightCode.toString())).getOrder();
                }
                if (leftOrder == null) {
                    return rightOrder == null ? 0 : -1;
                }
                if (rightOrder == null) {
                    return 1;
                }
                return leftOrder.toString().compareTo(rightOrder.toString());
            }).collect(Collectors.toList());
            parentRow.setChildRows(sortRows);
        }
    }

    private void insertVirtualPeriodTreeRows(Integer periodLevelIndex, List<GroupTreeRow> periodTreeRows, HashSet<Integer> avgFields, List<DataRowImpl> appendRows, List<Integer> periodLevels) throws DataTypeException {
        int periodType;
        this.adjustLevel = 0;
        Object periodValue = periodTreeRows.get(0).getCurrentRow().internalGetValue(periodLevelIndex.intValue());
        if (periodValue == null || StringUtils.isEmpty((String)periodValue.toString())) {
            return;
        }
        try {
            PeriodWrapper periodWrapper = new PeriodWrapper(periodValue.toString());
            periodType = periodWrapper.getType();
        }
        catch (Exception e) {
            return;
        }
        List parentLevels = PeriodWrapper.getPeriodParentLevels((int)periodType);
        ArrayList intersectLevels = new ArrayList(parentLevels);
        intersectLevels.retainAll(periodLevels);
        Collections.reverse(intersectLevels);
        if (intersectLevels.size() <= 0) {
            return;
        }
        if (this.groupColDims == null) {
            this.groupColDims = this.getGroupColDims();
        }
        HashMap<String, GroupTreeRow> virtualRows = new HashMap<String, GroupTreeRow>();
        ArrayList<GroupTreeRow> selfRows = new ArrayList<GroupTreeRow>();
        for (GroupTreeRow periodTreeRow : periodTreeRows) {
            PeriodWrapper periodWrapper;
            int currentLevel = 0;
            DataRowImpl currentRow = periodTreeRow.getCurrentRow();
            periodValue = currentRow.internalGetValue(periodLevelIndex.intValue());
            if (periodValue == null || StringUtils.isEmpty((String)periodValue.toString())) continue;
            try {
                periodWrapper = new PeriodWrapper(periodValue.toString());
                periodType = periodWrapper.getType();
            }
            catch (Exception e) {
                continue;
            }
            parentLevels = PeriodWrapper.getPeriodParentLevels((int)periodType);
            intersectLevels = new ArrayList(parentLevels);
            intersectLevels.retainAll(periodLevels);
            Collections.reverse(intersectLevels);
            if (intersectLevels.size() <= 0) continue;
            if (!intersectLevels.contains(periodType)) {
                selfRows.add(periodTreeRow);
            }
            for (Integer parentLevel : intersectLevels) {
                if (parentLevel == periodType) continue;
                GregorianCalendar calendar = PeriodUtil.period2Calendar((PeriodWrapper)periodWrapper);
                PeriodWrapper parentWrapper = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)parentLevel, (int)0);
                String parentPeriod = parentWrapper.toString();
                DimensionValueSet valueSet = this.getParentRowKeys(currentRow);
                valueSet.setValue("DATATIME", (Object)parentPeriod);
                int groupingLevel = currentRow.getGroupingLevel();
                String rowKeys = valueSet.toString().toUpperCase();
                GroupTreeRow appendTreeRow = this.getAppendRow(periodLevelIndex, appendRows, valueSet, virtualRows, currentRow, parentPeriod, periodTreeRow, groupingLevel, rowKeys);
                this.calcGatherResult(appendTreeRow.getCurrentRow(), currentRow, this.isNewRow, avgFields);
                ++currentLevel;
            }
            if (currentLevel <= this.adjustLevel) continue;
            this.adjustLevel = currentLevel;
        }
        ArrayList<GroupTreeRow> mergeRows = new ArrayList<GroupTreeRow>();
        for (GroupTreeRow groupTreeRow : selfRows) {
            GroupTreeRow parentRow = groupTreeRow.getParentRow();
            this.removeSelfRow(groupTreeRow, parentRow);
            if (mergeRows.contains(parentRow)) continue;
            mergeRows.add(parentRow);
        }
        if (mergeRows.size() > 0) {
            this.mergeGroupTreeRows(mergeRows, periodLevelIndex, avgFields, appendRows);
        }
    }

    private DimensionValueSet getParentRowKeys(DataRowImpl currentRow) {
        DimensionValueSet valueSet = new DimensionValueSet();
        for (Map.Entry<Integer, BitMaskAndNullValue> bitMaskAndNullValue : this.grpByColsInGroupingId.entrySet()) {
            String dimName = this.groupColDims.get(bitMaskAndNullValue.getKey());
            Object dimValue = currentRow.internalGetValue(bitMaskAndNullValue.getKey().intValue());
            if (dimValue == null || StringUtils.isEmpty((String)dimValue.toString())) continue;
            valueSet.setValue(dimName, (Object)dimValue.toString());
        }
        valueSet.setValue("GroupingDeep", (Object)((currentRow.getGroupingFlag() + 1) / 2));
        return valueSet;
    }

    private DimensionValueSet getVisualEnumRowValueSet(int enumColIndex, String showText, DataRowImpl currentRow, GroupTreeRow groupTreeRow) {
        GroupTreeRow parentRow;
        boolean hasParentRow;
        DimensionValueSet valueSet = new DimensionValueSet();
        if (this.groupColDims == null) {
            this.groupColDims = this.getGroupColDims();
        }
        boolean bl = hasParentRow = (parentRow = groupTreeRow.getParentRow()) != null && parentRow.getCurrentRow() != null;
        if (hasParentRow) {
            valueSet.combine(parentRow.getCurrentRow().getRowKeys());
        }
        valueSet.setValue("GROUP_KEY", (Object)showText);
        valueSet.setValue("GroupingDeep", (Object)((currentRow.getGroupingFlag() + 1) / 2));
        return valueSet;
    }

    private List<DataRowImpl> doEntityLevelGatherOnePeriod(String parentEntity, Integer entityLevelIndex, EntityViewDefine dwViewDefine, List<Integer> entityLevels, String currentPeriod, String entityDim, ReloadTreeInfo reloadTreeInfo) throws Exception {
        int cubeCount;
        if (this.entityCache == null) {
            this.entityCache = this.getEntityCache(dwViewDefine, currentPeriod, parentEntity, reloadTreeInfo);
        }
        if (this.entityCache.size() <= 0) {
            return null;
        }
        IEntityRow parentRow = this.entityCache.get(parentEntity);
        List<String> parentKeys = Arrays.asList(parentRow.getParentsEntityKeyDataPath());
        BitMaskAndNullValue entityNullValue = this.grpByColsInGroupingId.get(entityLevelIndex);
        ArrayList<GroupTreeRow> entityGroupTreeRows = new ArrayList<GroupTreeRow>();
        this.currentLevel = 0;
        this.adjustLevel = 0;
        if (this.groupTreeRows == null) {
            cubeCount = this.grpByColsInGroupingId.size();
            this.groupTreeRows = this.getGroupTreeRows(entityNullValue, entityGroupTreeRows);
        } else {
            this.getEnumGroupTreeRows(this.groupTreeRows, entityNullValue, entityGroupTreeRows);
            cubeCount = (int)(Math.log(this.adjustLevel + 1) / Math.log(2.0));
        }
        ArrayList<DataRowImpl> currentLevelRows = new ArrayList<DataRowImpl>();
        for (GroupTreeRow entityGroupTreeRow : entityGroupTreeRows) {
            currentLevelRows.add(entityGroupTreeRow.getCurrentRow());
        }
        int maxLevel = this.getMaxGroupLevel(entityLevelIndex, currentLevelRows, parentKeys, this.currentLevel + 1);
        HashSet<Integer> avgFields = new HashSet<Integer>();
        ArrayList<DataRowImpl> appendRows = new ArrayList<DataRowImpl>();
        this.insertVirtualGroupTreeRow(entityDim, entityLevelIndex, entityGroupTreeRows, cubeCount, parentKeys, maxLevel, avgFields, appendRows);
        int entityFlag = (int)Math.pow(2.0, this.groupKeys.size() - this.groupKeys.indexOf(entityLevelIndex) - 1) - 1;
        if (entityLevels != null && entityLevels.size() > 0) {
            ArrayList<GroupTreeRow> nullTreeRows = new ArrayList<GroupTreeRow>();
            this.recurseAddRows(this.groupTreeRows, entityFlag, entityLevels, 0, nullTreeRows);
            ArrayList<GroupTreeRow> mergeRows = new ArrayList<GroupTreeRow>();
            for (GroupTreeRow groupTreeRow : nullTreeRows) {
                GroupTreeRow parentTreeRow = groupTreeRow.getParentRow();
                this.removeSelfRow(groupTreeRow, parentTreeRow);
                if (mergeRows.contains(parentTreeRow)) continue;
                mergeRows.add(parentTreeRow);
            }
            if (mergeRows.size() > 0) {
                this.mergeGroupTreeRows(mergeRows, entityLevelIndex, avgFields, appendRows);
            }
        }
        maxLevel = cubeCount + maxLevel - this.currentLevel;
        ArrayList<DataRowImpl> dataRowImpls = new ArrayList<DataRowImpl>();
        AdvancedGatherHelper.getDataRows(this.groupTreeRows, dataRowImpls, maxLevel);
        return dataRowImpls;
    }

    private void mergeGroupTreeRows(List<GroupTreeRow> mergeGroupRows, int enumColumnIndex, HashSet<Integer> avgFields, List<DataRowImpl> appendRows) throws DataTypeException {
        List<GroupTreeRow> childRows;
        int curIndex = this.groupKeys.indexOf(enumColumnIndex);
        if (curIndex < 0 || curIndex == this.groupKeys.size() - 1) {
            return;
        }
        int curFlag = (int)Math.pow(2.0, this.groupKeys.size() - 1 - curIndex) - 1;
        ArrayList<GroupTreeRow> calcRows = new ArrayList<GroupTreeRow>();
        for (GroupTreeRow mergeTreeRow : mergeGroupRows) {
            Object enumValue = mergeTreeRow.getCurrentRow().internalGetValue(enumColumnIndex);
            childRows = mergeTreeRow.getChildRows();
            if (childRows.size() <= 0) continue;
            if (childRows.size() == 1) {
                if (childRows.get(0).getCurrentRow().getGroupingFlag() >= curFlag) continue;
                childRows.get(0).getCurrentRow().setValue(enumColumnIndex, enumValue);
                continue;
            }
            HashMap<Integer, HashMap> rowsCache = new HashMap<Integer, HashMap>();
            for (GroupTreeRow groupTreeRow : childRows) {
                List<GroupTreeRow> sameKeyRows;
                String groupValue;
                HashMap sameLevelRows;
                DataRowImpl curRow = groupTreeRow.getCurrentRow();
                int groupingFlag = curRow.getGroupingFlag();
                if (groupingFlag < 0) continue;
                if (!rowsCache.containsKey(groupingFlag)) {
                    sameLevelRows = new HashMap();
                    rowsCache.put(groupingFlag, sameLevelRows);
                } else {
                    sameLevelRows = (HashMap)rowsCache.get(groupingFlag);
                }
                int curLevel = (int)(Math.log(groupingFlag + 1) / Math.log(2.0));
                int groupColumn = this.groupKeys.get(this.groupKeys.size() - curLevel - 1);
                Object curValue = curRow.internalGetValue(groupColumn);
                String string = groupValue = curValue == null ? "" : curValue.toString().toUpperCase();
                if (!sameLevelRows.containsKey(groupValue)) {
                    sameKeyRows = new ArrayList();
                    sameLevelRows.put(groupValue, sameKeyRows);
                } else {
                    sameKeyRows = (List)sameLevelRows.get(groupValue);
                }
                sameKeyRows.add(groupTreeRow);
            }
            for (Map.Entry entry : rowsCache.entrySet()) {
                for (Map.Entry rowPair : ((HashMap)entry.getValue()).entrySet()) {
                    List sameKeyRows = (List)rowPair.getValue();
                    GroupTreeRow firstRow = (GroupTreeRow)sameKeyRows.get(0);
                    if (firstRow.getCurrentRow().getGroupingFlag() < curFlag) {
                        firstRow.getCurrentRow().setValue(enumColumnIndex, enumValue);
                    }
                    if (sameKeyRows.size() <= 1) continue;
                    for (int index = 1; index < sameKeyRows.size(); ++index) {
                        GroupTreeRow mergeRow = (GroupTreeRow)sameKeyRows.get(index);
                        this.calcGatherResult(firstRow.getCurrentRow(), mergeRow.getCurrentRow(), false, avgFields);
                        List<GroupTreeRow> mergeChildRows = mergeRow.getChildRows();
                        for (int j = mergeChildRows.size() - 1; j >= 0; --j) {
                            GroupTreeRow mergeChildRow = mergeChildRows.get(j);
                            mergeChildRow.setAnotherParentRow(firstRow);
                        }
                        mergeRow.setAnotherParentRow(null);
                    }
                    if (avgFields.size() > 0) {
                        appendRows.add(firstRow.getCurrentRow());
                    }
                    calcRows.add(firstRow);
                }
            }
        }
        if (calcRows.size() > 0) {
            ArrayList<GroupTreeRow> treeRows = new ArrayList<GroupTreeRow>();
            block6: for (GroupTreeRow calcRow : calcRows) {
                childRows = calcRow.getChildRows();
                if (childRows.size() <= 0) continue;
                for (GroupTreeRow childRow : childRows) {
                    if (childRow.getCurrentRow().getGroupingFlag() < 0) continue;
                    treeRows.add(calcRow);
                    continue block6;
                }
            }
            if (treeRows.size() > 0) {
                this.mergeGroupTreeRows(treeRows, enumColumnIndex, avgFields, appendRows);
            }
        }
    }

    private void removeSelfRow(GroupTreeRow enumGroupTreeRow, GroupTreeRow parentRow) {
        List<GroupTreeRow> childRows = enumGroupTreeRow.getChildRows();
        if (childRows.size() > 0) {
            for (int index = childRows.size() - 1; index >= 0; --index) {
                GroupTreeRow childRow = childRows.get(index);
                childRow.setAnotherParentRow(parentRow);
            }
        }
        enumGroupTreeRow.setAnotherParentRow(null);
    }

    private void getEnumGroupTreeRows(List<GroupTreeRow> groupTreeRows, BitMaskAndNullValue enumNullValue, List<GroupTreeRow> enumGroupTreeRows) {
        for (GroupTreeRow groupTreeRow : groupTreeRows) {
            DataRowImpl currentRow = groupTreeRow.getCurrentRow();
            int groupingLevel = currentRow.getGroupingLevel();
            if (groupingLevel > this.adjustLevel) {
                this.adjustLevel = groupingLevel;
            }
            if ((currentRow.getGroupingFlag() & enumNullValue.getBitMask()) <= 0 && currentRow.getGroupingFlag() + 1 == enumNullValue.getBitMask()) {
                int curLevel;
                this.currentLevel = curLevel = (int)(Math.log(currentRow.getGroupingFlag() + 1) / Math.log(2.0));
                enumGroupTreeRows.add(groupTreeRow);
            }
            if (groupTreeRow.getChildRows().size() <= 0) continue;
            this.getEnumGroupTreeRows(groupTreeRow.getChildRows(), enumNullValue, enumGroupTreeRows);
        }
    }

    public static List<GroupTreeRow> getGroupTreeRows(List<DataRowImpl> dataRowImpls, HashMap<Integer, BitMaskAndNullValue> grpByColsInGroupingId) {
        int cubeCount;
        List sameFlagRows;
        ArrayList<GroupTreeRow> groupTreeRows = new ArrayList<GroupTreeRow>();
        HashMap<Integer, HashMap> groupRowsDict = new HashMap<Integer, HashMap>();
        HashMap<Integer, ArrayList> rowsDictByGroupingFlag = new HashMap<Integer, ArrayList>();
        for (DataRowImpl dataRowImpl : dataRowImpls) {
            int groupingFlag = dataRowImpl.getGroupingFlag();
            if (rowsDictByGroupingFlag.containsKey(groupingFlag)) {
                sameFlagRows = (ArrayList)rowsDictByGroupingFlag.get(groupingFlag);
            } else {
                sameFlagRows = new ArrayList();
                rowsDictByGroupingFlag.put(groupingFlag, (ArrayList)sameFlagRows);
            }
            ((ArrayList)sameFlagRows).add(dataRowImpl);
        }
        for (int index = cubeCount = grpByColsInGroupingId.size(); index >= -1; --index) {
            HashMap parentRows;
            HashMap sameLevelRows;
            int currentFlag;
            int n = currentFlag = index < 0 ? -1 : (int)Math.pow(2.0, index) - 1;
            if (!rowsDictByGroupingFlag.containsKey(currentFlag)) continue;
            sameFlagRows = (List)rowsDictByGroupingFlag.get(currentFlag);
            int parentFlag = (int)Math.pow(2.0, index + 1) - 1;
            if (!groupRowsDict.containsKey(currentFlag)) {
                sameLevelRows = new HashMap();
                groupRowsDict.put(currentFlag, sameLevelRows);
            } else {
                sameLevelRows = (HashMap)groupRowsDict.get(currentFlag);
            }
            if (!groupRowsDict.containsKey(parentFlag)) {
                parentRows = new HashMap();
                groupRowsDict.put(parentFlag, parentRows);
            } else {
                parentRows = (HashMap)groupRowsDict.get(parentFlag);
            }
            for (DataRowImpl sameFlagRow : sameFlagRows) {
                String groupKey;
                GroupTreeRow parentRow = null;
                StringBuilder groupKeys = new StringBuilder();
                StringBuilder parentKeys = new StringBuilder();
                for (Map.Entry<Integer, BitMaskAndNullValue> bitMaskAndNullValue : grpByColsInGroupingId.entrySet()) {
                    String colText;
                    Object colValue = sameFlagRow.internalGetValue(bitMaskAndNullValue.getKey().intValue());
                    String string = colText = colValue == null ? null : StringUtils.trim((String)colValue.toString());
                    if (colText == null) {
                        colText = "";
                    }
                    groupKeys.append(colText);
                    groupKeys.append("#^$");
                    if ((parentFlag & bitMaskAndNullValue.getValue().getBitMask()) <= 0) {
                        parentKeys.append(colText);
                        parentKeys.append("#^$");
                        continue;
                    }
                    parentKeys.append("#^$");
                }
                String dimKey = parentKeys.toString().toUpperCase();
                if (parentRows.containsKey(dimKey)) {
                    parentRow = (GroupTreeRow)parentRows.get(dimKey);
                }
                GroupTreeRow groupTreeRow = new GroupTreeRow(sameFlagRow, parentRow);
                if (parentRow == null) {
                    groupTreeRows.add(groupTreeRow);
                }
                if (sameLevelRows.containsKey(groupKey = groupKeys.toString().toUpperCase())) continue;
                sameLevelRows.put(groupKey, groupTreeRow);
            }
        }
        return groupTreeRows;
    }

    private List<GroupTreeRow> getGroupTreeRows(BitMaskAndNullValue enumNullValue, List<GroupTreeRow> enumGroupTreeRows) {
        int cubeCount;
        List sameFlagRows;
        this.currentLevel = 0;
        ArrayList<GroupTreeRow> groupTreeRows = new ArrayList<GroupTreeRow>();
        HashMap<Integer, HashMap> groupRowsDict = new HashMap<Integer, HashMap>();
        HashMap<Integer, ArrayList> rowsDictByGroupingFlag = new HashMap<Integer, ArrayList>();
        if (this.groupColDims == null) {
            this.groupColDims = this.getGroupColDims();
        }
        for (DataRowImpl dataRowImpl : this.dataRows) {
            int groupingFlag = dataRowImpl.getGroupingFlag();
            if (rowsDictByGroupingFlag.containsKey(groupingFlag)) {
                sameFlagRows = (ArrayList)rowsDictByGroupingFlag.get(groupingFlag);
            } else {
                sameFlagRows = new ArrayList();
                rowsDictByGroupingFlag.put(groupingFlag, (ArrayList)sameFlagRows);
            }
            ((ArrayList)sameFlagRows).add(dataRowImpl);
        }
        for (int index = cubeCount = this.grpByColsInGroupingId.size(); index >= -1; --index) {
            HashMap parentRows;
            HashMap sameLevelRows;
            int currentFlag;
            int n = currentFlag = index < 0 ? -1 : (int)Math.pow(2.0, index) - 1;
            if (!rowsDictByGroupingFlag.containsKey(currentFlag)) continue;
            sameFlagRows = (List)rowsDictByGroupingFlag.get(currentFlag);
            int parentFlag = (int)Math.pow(2.0, index + 1) - 1;
            if (!groupRowsDict.containsKey(currentFlag)) {
                sameLevelRows = new HashMap();
                groupRowsDict.put(currentFlag, sameLevelRows);
            } else {
                sameLevelRows = (HashMap)groupRowsDict.get(currentFlag);
            }
            if (!groupRowsDict.containsKey(parentFlag)) {
                parentRows = new HashMap();
                groupRowsDict.put(parentFlag, parentRows);
            } else {
                parentRows = (HashMap)groupRowsDict.get(parentFlag);
            }
            for (DataRowImpl sameFlagRow : sameFlagRows) {
                String groupKey;
                GroupTreeRow parentRow = null;
                DimensionValueSet groupKeys = new DimensionValueSet();
                DimensionValueSet parentKeys = new DimensionValueSet();
                for (Map.Entry<Integer, BitMaskAndNullValue> bitMaskAndNullValue : this.grpByColsInGroupingId.entrySet()) {
                    String colText;
                    String dimName = this.groupColDims.get(bitMaskAndNullValue.getKey());
                    Object colValue = sameFlagRow.internalGetValue(bitMaskAndNullValue.getKey().intValue());
                    String string = colText = colValue == null ? null : StringUtils.trim((String)colValue.toString());
                    if (colText == null) continue;
                    groupKeys.setValue(dimName, (Object)colText);
                    if ((parentFlag & bitMaskAndNullValue.getValue().getBitMask()) > 0) continue;
                    parentKeys.setValue(dimName, (Object)colText);
                }
                String dimKey = parentKeys.toString().toUpperCase();
                if (parentRows.containsKey(dimKey)) {
                    parentRow = (GroupTreeRow)parentRows.get(dimKey);
                }
                GroupTreeRow groupTreeRow = new GroupTreeRow(sameFlagRow, parentRow);
                if (parentRow == null) {
                    groupTreeRows.add(groupTreeRow);
                }
                if (!sameLevelRows.containsKey(groupKey = groupKeys.toString().toUpperCase())) {
                    sameLevelRows.put(groupKey, groupTreeRow);
                }
                if ((sameFlagRow.getGroupingFlag() & enumNullValue.getBitMask()) > 0 || sameFlagRow.getGroupingFlag() + 1 != enumNullValue.getBitMask()) continue;
                this.currentLevel = index;
                enumGroupTreeRows.add(groupTreeRow);
            }
        }
        return groupTreeRows;
    }

    private HashMap<Integer, String> getGroupColDims() {
        HashMap<Integer, String> colDims = new HashMap<Integer, String>();
        for (Map.Entry<Integer, BitMaskAndNullValue> bitMaskAndNullValue : this.grpByColsInGroupingId.entrySet()) {
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(bitMaskAndNullValue.getKey().intValue());
            DataModelDefinitionsCache modelCache = this.cache.getDataModelDefinitionsCache();
            ColumnModelDefine column = modelCache.getColumnModel(fieldDefine);
            String dimName = modelCache.getDimensionName(column);
            TableModelDefine tableModel = modelCache.getTableModel(column);
            dimName = StringUtils.isEmpty((String)dimName) ? tableModel.getName() + "_" + column.getName() : dimName;
            colDims.put(bitMaskAndNullValue.getKey(), dimName);
        }
        return colDims;
    }

    public static void getDataRows(List<GroupTreeRow> groupTreeRows, List<DataRowImpl> dataRowImpls, int maxLevel) {
        if (groupTreeRows.size() <= 0) {
            return;
        }
        int grpLevel = (int)Math.pow(2.0, maxLevel) - 1;
        for (GroupTreeRow groupTreeRow : groupTreeRows) {
            if (groupTreeRow.getCurrentRow().getGroupingFlag() >= 0) {
                groupTreeRow.getCurrentRow().setGroupingLevel(grpLevel);
                int deep = (grpLevel + 1) / 2;
                groupTreeRow.getCurrentRow().setGroupTreeDeep(deep);
                groupTreeRow.getCurrentRow().getRowKeys().setValue("GroupingDeep", (Object)deep);
            }
            dataRowImpls.add(groupTreeRow.getCurrentRow());
            if (groupTreeRow.getChildRows().size() <= 0) continue;
            AdvancedGatherHelper.getDataRows(groupTreeRow.getChildRows(), dataRowImpls, maxLevel - 1);
        }
    }

    private void insertVirtualGroupTreeRow(String entityDim, Integer entityLevelIndex, List<GroupTreeRow> entityGroupTreeRows, int cubeCount, List<String> parentKeys, int maxLevel, HashSet<Integer> avgFields, List<DataRowImpl> appendRows) throws DataTypeException {
        HashMap<String, GroupTreeRow> virtualRows = new HashMap<String, GroupTreeRow>();
        if (this.groupColDims == null) {
            this.groupColDims = this.getGroupColDims();
        }
        maxLevel = maxLevel + cubeCount - 1;
        int parentCount = parentKeys.size();
        for (int index = 0; index < maxLevel; ++index) {
            int calcLevel = (int)Math.pow(2.0, index) - 1;
            for (int jIndex = entityGroupTreeRows.size() - 1; jIndex >= 0; --jIndex) {
                IEntityRow entityRow;
                String parentEntityKey;
                String entityKeyData;
                GroupTreeRow entityGroupTreeRow = entityGroupTreeRows.get(jIndex);
                DataRowImpl currentRow = entityGroupTreeRow.getCurrentRow();
                if (currentRow.getGroupingLevel() != calcLevel) continue;
                Object entityValue = currentRow.internalGetValue(entityLevelIndex.intValue());
                if (entityValue == null && currentRow.getRowKeys().hasValue(entityDim)) {
                    entityValue = currentRow.getRowKeys().getValue(entityDim);
                }
                if (entityValue == null || !this.entityCache.containsKey(entityKeyData = entityValue.toString()) || StringUtils.isEmpty((String)(parentEntityKey = (entityRow = this.entityCache.get(entityKeyData)).getParentEntityKey())) || !this.entityCache.containsKey(parentEntityKey)) continue;
                IEntityRow parentRow = this.entityCache.get(parentEntityKey);
                DimensionValueSet valueSet = this.getParentRowKeys(currentRow);
                valueSet.setValue(entityDim, (Object)parentEntityKey);
                int diffLevel = maxLevel - (parentRow.getParentsEntityKeyDataPath().length - parentCount);
                int entityLevel = (int)Math.pow(2.0, diffLevel) - 1;
                String rowKeys = valueSet.toString().toUpperCase();
                GroupTreeRow appendTreeRow = this.getAppendRow(entityLevelIndex, appendRows, valueSet, virtualRows, currentRow, parentEntityKey, entityGroupTreeRow, entityLevel, rowKeys);
                this.calcGatherResult(appendTreeRow.getCurrentRow(), currentRow, this.isNewRow, avgFields);
                entityGroupTreeRows.remove(jIndex);
                if (!this.isNewRow) continue;
                entityGroupTreeRows.add(appendTreeRow);
            }
            this.calcAvgGatherResult(this.groupTreeRows, avgFields, appendRows);
        }
    }

    private void calcGatherResult(DataRowImpl appendRow, DataRowImpl currentRow, Boolean isNewRow, HashSet<Integer> avgFields) throws DataTypeException {
        for (int fieldIndex = 0; fieldIndex < this.fieldsInfoImpl.getFieldCount(); ++fieldIndex) {
            BigDecimal sumData;
            BigDecimal apprendValue;
            BigDecimal currentNumber;
            boolean noGather;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(fieldIndex);
            FieldGatherType gatherType = this.gatherTypes.get(fieldIndex);
            if (fieldDefine != null && gatherType == FieldGatherType.FIELD_GATHER_NONE) {
                gatherType = fieldDefine.getGatherType();
            }
            boolean bl = noGather = this.setTypes != null && this.setTypes.containsKey(fieldIndex) && this.setTypes.get(fieldIndex) != false;
            if (fieldDefine != null && fieldDefine.getCode().equals("BIZKEYORDER") || gatherType == FieldGatherType.FIELD_GATHER_NONE || noGather) continue;
            FieldType fieldType = this.fieldsInfoImpl.getDataType(fieldIndex);
            AbstractData currentValue = currentRow.getValue(fieldIndex);
            if (currentValue.isNull) continue;
            if (gatherType == FieldGatherType.FIELD_GATHER_COUNT) {
                currentNumber = currentValue.getAsCurrency();
                apprendValue = appendRow.getValue(fieldIndex).getAsCurrency();
                if (apprendValue == null) {
                    apprendValue = new BigDecimal(0.0);
                }
                sumData = apprendValue.add(currentNumber);
                appendRow.directSet(fieldIndex, (Object)sumData);
                continue;
            }
            if (fieldType != FieldType.FIELD_TYPE_INTEGER && fieldType != FieldType.FIELD_TYPE_FLOAT && fieldType != FieldType.FIELD_TYPE_DECIMAL) continue;
            currentNumber = currentValue.getAsCurrency();
            apprendValue = appendRow.getValue(fieldIndex).getAsCurrency();
            if (apprendValue == null) {
                apprendValue = new BigDecimal(0.0);
            }
            if (gatherType == FieldGatherType.FIELD_GATHER_SUM || gatherType == FieldGatherType.FIELD_GATHER_COUNT) {
                sumData = apprendValue.add(currentNumber);
                appendRow.directSet(fieldIndex, (Object)sumData);
                continue;
            }
            if (gatherType == FieldGatherType.FIELD_GATHER_MIN || gatherType == FieldGatherType.FIELD_GATHER_MAX) {
                if (isNewRow.booleanValue()) {
                    appendRow.directSet(fieldIndex, (Object)currentNumber);
                    continue;
                }
                if (apprendValue.compareTo(currentNumber) < 0 && gatherType == FieldGatherType.FIELD_GATHER_MAX) {
                    appendRow.directSet(fieldIndex, (Object)currentNumber);
                }
                if (apprendValue.compareTo(currentNumber) <= 0 || gatherType != FieldGatherType.FIELD_GATHER_MIN) continue;
                appendRow.directSet(fieldIndex, (Object)currentNumber);
                continue;
            }
            if (gatherType != FieldGatherType.FIELD_GATHER_AVG) continue;
            avgFields.add(fieldIndex);
        }
    }

    private GroupTreeRow getAppendRow(int enumColumnIndex, List<DataRowImpl> appendRows, DimensionValueSet valueSet, HashMap<String, GroupTreeRow> virtualRows, DataRowImpl currentRow, String showText, GroupTreeRow groupTreeRow, int rowLevel, String rowKeys) throws DataTypeException {
        GroupTreeRow appendTreeRow;
        DataRowImpl appendRow;
        this.isNewRow = false;
        if (!virtualRows.containsKey(rowKeys)) {
            this.isNewRow = true;
            appendRow = new DataRowImpl(this.tableImpl, valueSet, new ArrayList());
            int colCount = currentRow.getRowDatas().size();
            for (int colIndex = 0; colIndex < colCount; ++colIndex) {
                appendRow.getRowDatas().add(null);
            }
            appendRow.setGroupingFlag(currentRow.getGroupingFlag());
            appendRow.setGroupTreeDeep(currentRow.getGroupTreeDeep());
            for (Map.Entry<Integer, BitMaskAndNullValue> bitMaskAndNullValue : this.grpByColsInGroupingId.entrySet()) {
                if (bitMaskAndNullValue.getKey() == enumColumnIndex) {
                    appendRow.directSet(bitMaskAndNullValue.getKey().intValue(), (Object)showText);
                    continue;
                }
                Object fieldValue = currentRow.internalGetValue(bitMaskAndNullValue.getKey().intValue());
                if (fieldValue == null) continue;
                appendRow.directSet(bitMaskAndNullValue.getKey().intValue(), fieldValue);
            }
            appendRow.setVirtualRow(true);
            appendTreeRow = new GroupTreeRow(appendRow, groupTreeRow.getParentRow());
            GroupTreeRow parentRow = groupTreeRow.getParentRow();
            if (parentRow == null && this.groupTreeRows.size() > 0) {
                this.adjustParentRow(groupTreeRow, appendTreeRow);
            }
            groupTreeRow.setAnotherParentRow(appendTreeRow);
            virtualRows.put(rowKeys, appendTreeRow);
            appendRows.add(appendRow);
        } else {
            appendTreeRow = virtualRows.get(rowKeys);
            GroupTreeRow parentRow = groupTreeRow.getParentRow();
            if (parentRow == null && this.groupTreeRows.size() > 0) {
                this.adjustParentRow(groupTreeRow, appendTreeRow);
            }
            groupTreeRow.setAnotherParentRow(appendTreeRow);
            appendRow = appendTreeRow.getCurrentRow();
        }
        if (appendRow.getGroupingLevel() < rowLevel) {
            appendRow.setGroupingLevel(rowLevel);
        }
        return appendTreeRow;
    }

    private void adjustParentRow(GroupTreeRow groupTreeRow, GroupTreeRow appendTreeRow) {
        int indexOf = this.groupTreeRows.indexOf(groupTreeRow);
        int appendIndexOf = this.groupTreeRows.indexOf(appendTreeRow);
        if (indexOf >= 0) {
            if (appendIndexOf < 0) {
                this.groupTreeRows.set(indexOf, appendTreeRow);
            } else {
                this.groupTreeRows.remove(indexOf);
            }
        }
    }

    private void recurseAddRows(List<GroupTreeRow> groupTreeRows, int entityFlag, List<Integer> entityLevels, int curLevel, List<GroupTreeRow> nullTreeRows) {
        for (GroupTreeRow groupTreeRow : groupTreeRows) {
            DataRowImpl currentRow = groupTreeRow.getCurrentRow();
            if (currentRow.getGroupingFlag() != entityFlag) {
                if (groupTreeRow.getChildRows().size() <= 0) continue;
                this.recurseAddRows(groupTreeRow.getChildRows(), entityFlag, entityLevels, curLevel, nullTreeRows);
                continue;
            }
            if (entityLevels.contains(++curLevel)) {
                if (groupTreeRow.getChildRows().size() > 0) {
                    this.recurseAddRows(groupTreeRow.getChildRows(), entityFlag, entityLevels, curLevel, nullTreeRows);
                }
                --curLevel;
                continue;
            }
            nullTreeRows.add(groupTreeRow);
            if (groupTreeRow.getChildRows().size() > 0) {
                this.recurseAddRows(groupTreeRow.getChildRows(), entityFlag, entityLevels, curLevel, nullTreeRows);
            }
            --curLevel;
        }
    }

    private int getMaxGroupLevel(Integer entityLevelIndex, List<DataRowImpl> currentLevelRows, List<String> parentEntityPath, int currentLevel) throws DataTypeException {
        int maxLevel = currentLevel;
        for (DataRowImpl currentRow : currentLevelRows) {
            String entityKeyData;
            Object entityValue = currentRow.internalGetValue(entityLevelIndex.intValue());
            if (entityValue == null || !this.entityCache.containsKey(entityKeyData = entityValue.toString())) continue;
            IEntityRow entityRow = this.entityCache.get(entityKeyData);
            List<String> parentKeys = Arrays.asList(entityRow.getParentsEntityKeyDataPath());
            ArrayList<String> exceptKeys = new ArrayList<String>(parentKeys);
            exceptKeys.removeAll(parentEntityPath);
            for (String exceptKey : exceptKeys) {
                if (!this.entityCache.containsKey(exceptKey)) continue;
                IEntityRow exceptEntity = this.entityCache.get(exceptKey);
                int diffLevel = currentLevel + parentKeys.size() - exceptEntity.getParentsEntityKeyDataPath().length - 1;
                if (diffLevel <= maxLevel) continue;
                maxLevel = diffLevel;
            }
        }
        return maxLevel;
    }

    private HashMap<String, IEntityRow> getEntityCache(EntityViewDefine dwViewDefine, String currentPeriod, String parentEntity, ReloadTreeInfo reloadTreeInfo) throws Exception {
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanProvider.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(dwViewDefine);
        DimensionValueSet masterKeys = new DimensionValueSet();
        entityQuery.setMasterKeys(masterKeys);
        IEntityTable entityTable = entityQuery.executeReader((IContext)this.context);
        HashMap<String, IEntityRow> entityCache = new HashMap<String, IEntityRow>();
        IEntityRow parentRow = entityTable.findByEntityKey(parentEntity);
        List childRows = entityTable.getAllChildRows(parentEntity);
        if (parentRow == null || childRows == null || childRows.size() <= 0) {
            return new HashMap<String, IEntityRow>();
        }
        entityCache.put(parentEntity, parentRow);
        for (IEntityRow childRow : childRows) {
            entityCache.put(childRow.getEntityKeyData(), childRow);
        }
        return entityCache;
    }

    private String getCurrentPeriod() {
        DimensionValueSet rowKeys;
        String currentPeriod;
        DimensionValueSet masterKeys = this.tableImpl.getMasterKeys();
        String string = currentPeriod = masterKeys.hasValue("DATATIME") ? masterKeys.getValue("DATATIME").toString() : null;
        if (!StringUtils.isEmpty((String)currentPeriod)) {
            return currentPeriod;
        }
        if (this.dataRows.size() > 0 && (rowKeys = this.dataRows.get(0).getRowKeys()).hasValue("DATATIME")) {
            return rowKeys.getValue("DATATIME").toString();
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    private GradeEnumTree getEnumTree(EntityViewDefine entityViewDefine) throws Exception {
        void var10_14;
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanProvider.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        DimensionValueSet masterKeys = new DimensionValueSet();
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setEntityView(entityViewDefine);
        IEntityTable entityTable = entityQuery.executeReader((IContext)this.context);
        List entityRows = entityTable.getAllRows();
        int treeDepth = 1;
        HashMap<String, EnumRowItem> enumRowItems = new HashMap<String, EnumRowItem>();
        for (IEntityRow iEntityRow : entityRows) {
            EnumRowItem enumRowItem = new EnumRowItem(iEntityRow.getEntityKeyData(), iEntityRow.getTitle(), iEntityRow.getParentEntityKey());
            enumRowItem.setLeaf(true);
            enumRowItem.setOrder(iEntityRow.getEntityOrder());
            if (enumRowItems.containsKey(iEntityRow.getEntityKeyData())) continue;
            enumRowItems.put(iEntityRow.getEntityKeyData(), enumRowItem);
        }
        for (Map.Entry entry : enumRowItems.entrySet()) {
            String parentCode = ((EnumRowItem)entry.getValue()).getParentCode();
            if (StringUtils.isEmpty((String)parentCode)) continue;
            if (!enumRowItems.containsKey(parentCode)) {
                ((EnumRowItem)entry.getValue()).setParentCode("");
                continue;
            }
            ArrayList<String> parentPath = new ArrayList<String>();
            this.getParentPath(enumRowItems, parentCode, parentPath);
            Collections.reverse(parentPath);
            ((EnumRowItem)entry.getValue()).setParentPath(parentPath);
            int pathDepth = parentPath.size() + 1;
            if (pathDepth <= treeDepth) continue;
            treeDepth = pathDepth;
        }
        ArrayList<Integer> gradeLevels = new ArrayList<Integer>();
        boolean bl = true;
        while (var10_14 <= treeDepth) {
            gradeLevels.add((int)var10_14);
            ++var10_14;
        }
        GradeEnumTree gradeEnumTree = new GradeEnumTree();
        gradeEnumTree.setEntityViewId(entityViewDefine.getEntityId());
        gradeEnumTree.setTreeDepth(treeDepth);
        gradeEnumTree.setEnumRowItems(enumRowItems);
        gradeEnumTree.setGradeLevels(gradeLevels);
        return gradeEnumTree;
    }

    private void getParentPath(HashMap<String, EnumRowItem> enumRowItems, String parentCode, List<String> parentPath) {
        if (parentPath.contains(parentCode)) {
            return;
        }
        parentPath.add(parentCode);
        if (StringUtils.isEmpty((String)parentCode) || !enumRowItems.containsKey(parentCode)) {
            return;
        }
        EnumRowItem enumRowItem = enumRowItems.get(parentCode);
        enumRowItem.setLeaf(false);
        String newParentCode = enumRowItem.getParentCode();
        if (StringUtils.isEmpty((String)newParentCode) || !enumRowItems.containsKey(newParentCode)) {
            return;
        }
        this.getParentPath(enumRowItems, newParentCode, parentPath);
    }

    private void insertVirtualEnumGroupTreeRow(int enumColumnIndex, List<Integer> gradeLevels, List<GroupTreeRow> enumGroupTreeRows, GradeEnumTree enumTree, int maxLevel, HashSet<Integer> avgFields, List<DataRowImpl> appendRows, List<GroupTreeRow> mergeGroupRows) throws DataTypeException {
        HashMap<String, GroupTreeRow> virtualRows = new HashMap<String, GroupTreeRow>();
        HashMap<String, EnumRowItem> enumCache = enumTree.getEnumRowItems();
        HashMap<Integer, Object> noLeafRowsByDepth = new HashMap<Integer, Object>();
        for (GroupTreeRow enumGroupTreeRow : enumGroupTreeRows) {
            String enumText;
            DataRowImpl currentRow = enumGroupTreeRow.getCurrentRow();
            Object enumValue = currentRow.internalGetValue(enumColumnIndex);
            String string = enumText = enumValue == null ? "" : enumValue.toString();
            if (!enumCache.containsKey(enumText)) continue;
            EnumRowItem enumRowItem = enumCache.get(enumText);
            List<String> parentKeys = enumRowItem.getParentPath();
            if (!enumRowItem.isLeaf()) {
                Object noLeafRows;
                int pathCount;
                int n = pathCount = parentKeys == null ? 0 : parentKeys.size();
                if (!noLeafRowsByDepth.containsKey(pathCount)) {
                    noLeafRows = new ArrayList();
                    noLeafRowsByDepth.put(pathCount, noLeafRows);
                } else {
                    noLeafRows = (ArrayList)noLeafRowsByDepth.get(pathCount);
                }
                ((ArrayList)noLeafRows).add(enumGroupTreeRow);
                continue;
            }
            this.needRemoveSelf = true;
            ArrayList<String> exceptKeys = this.getExceptKeys(parentKeys, gradeLevels);
            for (String exceptKey : exceptKeys) {
                if (!enumCache.containsKey(exceptKey)) continue;
                EnumRowItem enumItem = enumCache.get(exceptKey);
                this.appendEnumRow(enumColumnIndex, maxLevel, avgFields, appendRows, enumItem, currentRow, virtualRows, enumGroupTreeRow);
            }
            if (!this.needRemoveSelf) continue;
            this.removeSelf = true;
            GroupTreeRow parentRow = enumGroupTreeRow.getParentRow();
            this.removeSelfRow(enumGroupTreeRow, parentRow);
            if (mergeGroupRows.contains(parentRow)) continue;
            mergeGroupRows.add(parentRow);
        }
        ArrayList pathKeys = new ArrayList(noLeafRowsByDepth.keySet());
        Collections.sort(pathKeys);
        for (int pathIndex = pathKeys.size() - 1; pathIndex >= 0; --pathIndex) {
            int pathKey = (Integer)pathKeys.get(pathIndex);
            List noLeafRows = (List)noLeafRowsByDepth.get(pathKey);
            for (GroupTreeRow groupTreeRow : noLeafRows) {
                DataRowImpl currentRow = groupTreeRow.getCurrentRow();
                Object enumValue = currentRow.internalGetValue(enumColumnIndex);
                String enumCode = enumValue == null ? "" : enumValue.toString();
                if (!enumCache.containsKey(enumCode)) continue;
                EnumRowItem enumItem = enumCache.get(enumCode);
                ArrayList<String> parentKeys = enumItem.getParentPath() == null ? new ArrayList<String>() : new ArrayList<String>(enumItem.getParentPath());
                parentKeys.add(enumCode);
                ArrayList<String> exceptKeys = this.getExceptKeys(parentKeys, gradeLevels);
                String selfCode = enumCode + "_000";
                exceptKeys.add(selfCode);
                if (!enumCache.containsKey(selfCode)) {
                    EnumRowItem enumRowItem = this.getSelfEnumRowItem(selfCode, enumCode, enumItem, parentKeys, enumCode);
                    enumCache.put(selfCode, enumRowItem);
                }
                GroupTreeRow treeRow = groupTreeRow;
                for (String exceptKey : exceptKeys) {
                    List<GroupTreeRow> childRows;
                    boolean canRemove;
                    if (!enumCache.containsKey(exceptKey)) continue;
                    enumItem = enumCache.get(exceptKey);
                    GroupTreeRow appendTreeRow = this.appendEnumRow(enumColumnIndex, maxLevel, avgFields, appendRows, enumItem, currentRow, virtualRows, groupTreeRow);
                    if (!Objects.equals(exceptKey, selfCode)) continue;
                    treeRow = appendTreeRow;
                    GroupTreeRow parentRow = groupTreeRow.getParentRow();
                    this.removeSelfRow(groupTreeRow, appendTreeRow);
                    if (!mergeGroupRows.contains(parentRow)) {
                        mergeGroupRows.add(parentRow);
                    }
                    if (parentRow.getParentRow() == null || !(canRemove = (childRows = parentRow.getParentRow().getChildRows()).remove(parentRow))) continue;
                    childRows.add(0, parentRow);
                }
                if (!this.needRemoveSelf) continue;
                this.removeSelf = true;
                GroupTreeRow parentRow = treeRow.getParentRow();
                this.removeSelfRow(treeRow, parentRow);
                if (mergeGroupRows.contains(parentRow)) continue;
                mergeGroupRows.add(parentRow);
            }
        }
    }

    private ArrayList<String> getExceptKeys(List<String> parentKeys, List<Integer> gradeLevels) {
        ArrayList<String> exceptKeys = new ArrayList<String>();
        if (parentKeys == null || parentKeys.size() <= 0) {
            this.needRemoveSelf = false;
            return exceptKeys;
        }
        for (int gradeLevel : gradeLevels) {
            if (parentKeys.size() + 1 > gradeLevel) {
                exceptKeys.add(parentKeys.get(gradeLevel - 1));
                continue;
            }
            if (parentKeys.size() + 1 > gradeLevel) continue;
            this.needRemoveSelf = false;
        }
        return exceptKeys;
    }

    private EnumRowItem getSelfEnumRowItem(String selfCode, String enumCode, EnumRowItem enumItem, ArrayList<String> parentKeys, String originalCode) {
        EnumRowItem selfItem = new EnumRowItem(selfCode, enumItem.getTitle() + "_\u672c\u7ea7", enumCode);
        selfItem.setParentPath(parentKeys);
        selfItem.setOriginalCode(originalCode);
        return selfItem;
    }

    private GroupTreeRow appendEnumRow(int enumColumnIndex, int maxLevel, HashSet<Integer> avgFields, List<DataRowImpl> appendRows, EnumRowItem enumItem, DataRowImpl currentRow, HashMap<String, GroupTreeRow> virtualRows, GroupTreeRow enumGroupTreeRow) throws DataTypeException {
        boolean isSelf;
        int diffLevel = maxLevel - (enumItem.getParentPath() == null ? 0 : enumItem.getParentPath().size());
        int enumLevel = (int)Math.pow(2.0, diffLevel) - 1;
        if (this.groupColDims == null) {
            this.groupColDims = this.getGroupColDims();
        }
        String enumCode = (isSelf = StringUtils.isNotEmpty((String)enumItem.getOriginalCode())) ? enumItem.getOriginalCode() : enumItem.getCode();
        DimensionValueSet valueSet = this.getVisualEnumRowValueSet(enumColumnIndex, enumCode, currentRow, enumGroupTreeRow);
        if (isSelf) {
            valueSet.setValue("SelfFlag", (Object)1);
        }
        String rowKeys = valueSet.toString().toUpperCase();
        this.isNewRow = false;
        GroupTreeRow appendRow = this.getAppendRow(enumColumnIndex, appendRows, valueSet, virtualRows, currentRow, enumCode, enumGroupTreeRow, enumLevel, rowKeys);
        if (isSelf) {
            appendRow.getCurrentRow().setBaseRow(true);
            valueSet.clearValue("SelfFlag");
        }
        this.calcGatherResult(appendRow.getCurrentRow(), currentRow, this.isNewRow, avgFields);
        return appendRow;
    }

    private void calcAvgGatherResult(List<GroupTreeRow> groupTreeRows, HashSet<Integer> avgFields, List<DataRowImpl> appendRows) throws DataTypeException {
        HashMap<String, DataRowImpl> rowImplsCache = new HashMap<String, DataRowImpl>();
        HashMap<String, List<DataRowImpl>> childRowsCache = new HashMap<String, List<DataRowImpl>>();
        this.getTreeRowsCache(groupTreeRows, rowImplsCache, childRowsCache);
        for (DataRowImpl dataRowImpl : appendRows) {
            String rowKeys = dataRowImpl.getRowKeys().toString();
            if (!rowImplsCache.containsKey(rowKeys)) continue;
            DataRowImpl currentRow = rowImplsCache.get(rowKeys);
            List<DataRowImpl> allChildRows = this.getAllChildRows(childRowsCache, rowKeys);
            for (Integer avgField : avgFields) {
                BigDecimal sumData = new BigDecimal(0);
                int count = 0;
                for (DataRowImpl childRow : allChildRows) {
                    AbstractData childValue = childRow.getValue(avgField.intValue());
                    if (childValue.isNull) continue;
                    sumData = sumData.add(childValue.getAsCurrency());
                    ++count;
                }
                if (count <= 0) continue;
                currentRow.setValue(avgField.intValue(), (Object)sumData.divide(new BigDecimal(count), 5));
            }
        }
    }

    private List<DataRowImpl> getChildRows(HashMap<String, List<DataRowImpl>> childRowsCache, String rowKeys) {
        if (childRowsCache.containsKey(rowKeys)) {
            return childRowsCache.get(rowKeys);
        }
        return new ArrayList<DataRowImpl>();
    }

    private List<DataRowImpl> getAllChildRows(HashMap<String, List<DataRowImpl>> childRowsCache, String rowKeys) {
        List<DataRowImpl> childRows = this.getChildRows(childRowsCache, rowKeys);
        HashMap<String, List<DataRowImpl>> found = new HashMap<String, List<DataRowImpl>>();
        if (childRows.size() <= 0) {
            return childRows;
        }
        Stack<DataRowImpl> foundList = new Stack<DataRowImpl>();
        for (int index = childRows.size() - 1; index >= 0; --index) {
            foundList.push(childRows.get(index));
            found.put(childRows.get(index).getRowKeys().toString(), childRows);
        }
        ArrayList<DataRowImpl> resultRows = new ArrayList<DataRowImpl>();
        while (foundList.size() > 0) {
            DataRowImpl childRow = (DataRowImpl)foundList.pop();
            resultRows.add(childRow);
            childRows = this.getChildRows(childRowsCache, childRow.getRowKeys().toString());
            if (childRows == null || childRows.size() <= 0) continue;
            for (int index = childRows.size() - 1; index >= 0; --index) {
                if (found.containsKey(childRow.getRowKeys().toString())) continue;
                foundList.push(childRows.get(index));
                found.put(childRow.getRowKeys().toString(), childRows);
            }
        }
        return resultRows;
    }

    private void getTreeRowsCache(List<GroupTreeRow> groupTreeRows, HashMap<String, DataRowImpl> rowImplsCache, HashMap<String, List<DataRowImpl>> childRowsCache) {
        for (GroupTreeRow groupTreeRow : groupTreeRows) {
            GroupTreeRow parentRow;
            String rowKeys = groupTreeRow.getCurrentRow().getRowKeys().toString();
            if (!rowImplsCache.containsKey(rowKeys)) {
                rowImplsCache.put(rowKeys, groupTreeRow.getCurrentRow());
            }
            if ((parentRow = groupTreeRow.getParentRow()) != null) {
                List<Object> childRows;
                String parentKeys = parentRow.getCurrentRow().getRowKeys().toString();
                if (!childRowsCache.containsKey(parentKeys)) {
                    childRows = new ArrayList();
                    childRowsCache.put(parentKeys, childRows);
                } else {
                    childRows = childRowsCache.get(parentKeys);
                }
                childRows.add(groupTreeRow.getCurrentRow());
            }
            if (groupTreeRow.getChildRows().size() <= 0) continue;
            this.getTreeRowsCache(groupTreeRow.getChildRows(), rowImplsCache, childRowsCache);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void mergeGroupResult() throws DataTypeException {
        if (this.dataRows == null || this.dataRows.size() <= 0) {
            return;
        }
        ArrayList<DataRowImpl> dataRowImpls = new ArrayList<DataRowImpl>(this.dataRows.size());
        for (DataRowImpl dataRowImpl : this.dataRows) {
            if (dataRowImpl.getGroupingFlag() == 0 && (dataRowImpl.getRowKeys() == null || dataRowImpl.getRowKeys().isAllNull())) continue;
            dataRowImpls.add(dataRowImpl);
        }
        this.dataRows = dataRowImpls;
        HashMap<Integer, String> dimCache = new HashMap<Integer, String>();
        for (Map.Entry<Integer, BitMaskAndNullValue> entry : this.grpByColsInGroupingId.entrySet()) {
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(entry.getKey().intValue());
            if (fieldDefine != null) {
                DataModelDefinitionsCache dataModelCache = this.cache.getDataModelDefinitionsCache();
                ColumnModelDefine column = dataModelCache.getColumnModel(fieldDefine);
                TableModelDefine tableModel = dataModelCache.getTableModel(column);
                String dimName = tableModel.getName().concat("_").concat(column.getName());
                dimCache.put(entry.getKey(), dimName);
                continue;
            }
            String dimName = "EXP_".concat(String.valueOf(entry.getKey()));
            dimCache.put(entry.getKey(), dimName);
        }
        HashMap<Integer, HashMap> hashMap = new HashMap<Integer, HashMap>();
        for (DataRowImpl dataRowImpl : this.dataRows) {
            void var10_16;
            HashMap rowImpls;
            int groupingFlag = dataRowImpl.getGroupingFlag();
            if (!hashMap.containsKey(groupingFlag)) {
                rowImpls = new HashMap();
                hashMap.put(groupingFlag, rowImpls);
            } else {
                rowImpls = (HashMap)hashMap.get(groupingFlag);
            }
            DimensionValueSet rowKeys = new DimensionValueSet();
            for (Map.Entry entry : this.grpByColsInGroupingId.entrySet()) {
                String dimName = (String)dimCache.get(entry.getKey());
                rowKeys.setValue(dimName, dataRowImpl.internalGetValue(((Integer)entry.getKey()).intValue()));
            }
            String rowKeyData = rowKeys.toString().toUpperCase();
            if (!rowImpls.containsKey(rowKeyData)) {
                LinkedList linkedList = new LinkedList();
                rowImpls.put(rowKeyData, linkedList);
            } else {
                List list = (List)rowImpls.get(rowKeyData);
            }
            var10_16.add(dataRowImpl);
        }
        LinkedList<DataRowImpl> linkedList = new LinkedList<DataRowImpl>();
        ArrayList groupingFlags = new ArrayList();
        groupingFlags.addAll(hashMap.keySet());
        Collections.sort(groupingFlags, new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (Integer groupingFlag : groupingFlags) {
            HashMap rowImpls = (HashMap)hashMap.get(groupingFlag);
            for (Map.Entry entry : rowImpls.entrySet()) {
                List currentDetailRows;
                HashMap detailRows;
                Map.Entry<Integer, BitMaskAndNullValue> grpColumn22;
                DataRowImpl currentLevelRow;
                List currentLevelRows = (List)entry.getValue();
                if (currentLevelRows.size() <= 0 || (currentLevelRow = (DataRowImpl)currentLevelRows.get(0)).getGroupingFlag() < 0) continue;
                DataRowImpl appendRow = new DataRowImpl(this.tableImpl, currentLevelRow.getRowKeys(), new ArrayList());
                int colCount = currentLevelRow.getRowDatas().size();
                for (int colIndex = 0; colIndex < colCount; ++colIndex) {
                    appendRow.getRowDatas().add(null);
                }
                appendRow.setGroupingFlag(groupingFlag.intValue());
                appendRow.setGroupingLevel(currentLevelRow.getGroupingLevel());
                for (Map.Entry<Integer, BitMaskAndNullValue> grpColumn22 : this.grpByColsInGroupingId.entrySet()) {
                    AbstractData fieldValue = currentLevelRow.getValue(grpColumn22.getKey().intValue());
                    if (AbstractData.isNull((AbstractData)fieldValue)) continue;
                    appendRow.internalSet(((Integer)grpColumn22.getKey()).intValue(), fieldValue.getAsObject());
                }
                linkedList.add(appendRow);
                HashSet<Integer> avgFields = new HashSet<Integer>();
                grpColumn22 = currentLevelRows.iterator();
                while (grpColumn22.hasNext()) {
                    DataRowImpl dataRowImpl = (DataRowImpl)grpColumn22.next();
                    this.calcGatherResult(appendRow, dataRowImpl, false, avgFields);
                }
                if (currentLevelRow.getGroupingFlag() != 0 || !hashMap.containsKey(-1) || (detailRows = (HashMap)hashMap.get(-1)) == null || !detailRows.containsKey(entry.getKey()) || (currentDetailRows = (List)detailRows.get(entry.getKey())) == null || currentDetailRows.size() <= 0) continue;
                linkedList.addAll(currentDetailRows);
            }
        }
        this.dataRows = linkedList;
        this.tableImpl.setAllDataRows(linkedList);
    }

    public void reCalcExpFields() throws ParseException, InterpretException {
        if (this.dataRows == null || this.dataRows.size() <= 0) {
            return;
        }
        FieldsInfoImpl fieldsInfoImpl = this.tableImpl.fieldsInfoImpl;
        QueryContext queryContext = new QueryContext(this.context, this.queryParam, null);
        DataQueryBuilder queryBuilder = this.tableImpl.getDataQueryBuilder();
        IQueryFieldDataReader dataReader = this.getDataReader(queryContext);
        HashSet<Integer> colIndexs = new HashSet<Integer>();
        for (int fieldIndex = 0; fieldIndex < fieldsInfoImpl.getFieldCount(); ++fieldIndex) {
            boolean noGather;
            FieldType dataType;
            FieldDefine fieldDefine = fieldsInfoImpl.getFieldDefine(fieldIndex);
            if (fieldDefine != null || (dataType = fieldsInfoImpl.getDataType(fieldIndex)) != FieldType.FIELD_TYPE_INTEGER && dataType != FieldType.FIELD_TYPE_DECIMAL && dataType != FieldType.FIELD_TYPE_FLOAT) continue;
            boolean bl = noGather = this.setTypes != null && this.setTypes.containsKey(fieldIndex) && this.setTypes.get(fieldIndex) != false;
            if (!noGather) continue;
            IASTNode currentNode = queryBuilder.getChild(fieldIndex);
            for (DataRowImpl dataRow : this.dataRows) {
                if (dataRow.getGroupingFlag() < 0) continue;
                dataReader.setDataSet((Object)dataRow);
                Object value = this.nodeEvaluate(queryContext, currentNode);
                if (value == null) continue;
                dataRow.directSet(fieldIndex, value);
                colIndexs.add(fieldIndex);
            }
        }
        if (colIndexs.size() > 0) {
            HashSet noneGatherCols = this.tableImpl.getNoneGatherCols();
            for (Integer colIndex : colIndexs) {
                noneGatherCols.remove(colIndex);
            }
        }
        this.tableImpl.setAllDataRows(this.dataRows);
    }

    private IQueryFieldDataReader getDataReader(QueryContext queryContext) throws ParseException {
        IQueryFieldDataReader dataReader = queryContext.getDataReader();
        if (dataReader == null || !(dataReader instanceof DataRowReader)) {
            dataReader = new DataRowReader(queryContext);
            queryContext.setDataReader(dataReader);
            List queryFields = this.tableImpl.queryfields;
            Integer index = 0;
            while (index < queryFields.size()) {
                QueryField queryField = (QueryField)queryFields.get(index);
                if (queryField != null && dataReader.findIndex(queryField) < 0) {
                    dataReader.putIndex(queryContext.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, index.intValue());
                }
                Integer n = index;
                Integer n2 = index = Integer.valueOf(index + 1);
            }
        }
        return dataReader;
    }

    protected Object nodeEvaluate(QueryContext qContext, IASTNode node) throws InterpretException {
        Object value = null;
        try {
            value = node.evaluate((IContext)qContext);
        }
        catch (SyntaxException e) {
            if (!e.isCanIgnore()) {
                logger.error(node.interpret((IContext)qContext, Language.FORMULA, (Object)qContext.getExeContext().getFormulaShowInfo()), e);
            }
        }
        catch (Exception e) {
            logger.error(node.interpret((IContext)qContext, Language.FORMULA, (Object)qContext.getExeContext().getFormulaShowInfo()), e);
        }
        return value;
    }

    public void reCalcExpressions(List<IExpression> expressions) throws ParseException, InterpretException {
        if (expressions == null || expressions.size() <= 0 || this.dataRows == null || this.dataRows.size() <= 0) {
            return;
        }
        QueryContext qContext = new QueryContext(this.context, this.queryParam, null);
        IQueryFieldDataReader dataReader = this.getDataReader(qContext);
        HashSet<Integer> colIndexs = new HashSet<Integer>();
        for (DataRowImpl dataRow : this.dataRows) {
            if (dataRow.getGroupingFlag() < 0) continue;
            dataReader.setDataSet((Object)dataRow);
            for (IExpression iExpression : expressions) {
                try {
                    iExpression.execute((IContext)qContext);
                }
                catch (SyntaxException e) {
                    if (e.isCanIgnore()) continue;
                    logger.error(iExpression.interpret((IContext)qContext, Language.FORMULA, (Object)qContext.getExeContext().getFormulaShowInfo()), e);
                }
                catch (Exception e) {
                    logger.error(iExpression.interpret((IContext)qContext, Language.FORMULA, (Object)qContext.getExeContext().getFormulaShowInfo()), e);
                }
            }
            Map rowValuesMap = qContext.getRowValuesMap();
            if (rowValuesMap.size() <= 0) continue;
            for (Map.Entry rowValue : rowValuesMap.entrySet()) {
                int fieldIndex = dataReader.findIndex((QueryField)rowValue.getKey());
                if (fieldIndex < 0) continue;
                dataRow.directSet(fieldIndex, rowValue.getValue());
                colIndexs.add(fieldIndex);
            }
            rowValuesMap.clear();
        }
        if (colIndexs.size() > 0) {
            HashSet noneGatherCols = this.tableImpl.getNoneGatherCols();
            for (Integer colIndex : colIndexs) {
                noneGatherCols.remove(colIndex);
            }
        }
        this.tableImpl.setAllDataRows(this.dataRows);
    }
}

