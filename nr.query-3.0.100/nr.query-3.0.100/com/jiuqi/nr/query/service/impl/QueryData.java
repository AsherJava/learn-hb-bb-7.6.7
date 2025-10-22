/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.common.summary.SummaryScheme
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.engine.validation.CompareType
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpression
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.engine.validation.CompareType;
import com.jiuqi.nr.data.engine.validation.DataValidationExpression;
import com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datawarning.dao.IDataWarningDao;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningProperties;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryFilterDefine;
import com.jiuqi.nr.query.block.QueryGridExtension;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.QuerySortType;
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.SummarySchemeUtils;
import com.jiuqi.nr.query.service.GridType;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.QueryGridDefination;
import com.jiuqi.nr.query.service.impl.DataNoneQueryException;
import com.jiuqi.nr.query.service.impl.PeriodHelper;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class QueryData {
    private static final Logger logger = LoggerFactory.getLogger(QueryData.class);
    IRunTimeViewController viewController;
    IDataAccessProvider dataAccessProvider;
    QueryHelper helper;
    QueryEntityUtil queryEntityUtil;
    DataModelService dataModelService;
    DataEngineAdapter dataEngineAdapter;
    IDataAssist dataAssist;
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    IEntityMetaService ientitymetaservice;
    IPeriodEntityAdapter periodEntityAdapter;
    IColumnModelFinder columnModelFinder;
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    Map<String, Integer> fieldIndex = new LinkedHashMap<String, Integer>();
    Map<String, Integer> showedFieldIndex = new LinkedHashMap<String, Integer>();
    Map<String, Integer> selectFieldIndex = new LinkedHashMap<String, Integer>();
    IGroupingTable dataTable;
    boolean isWantDetail;
    boolean hasFormulaWithPeriodShift;
    boolean isSimpleQuery;
    QueryBlockDefine block;
    boolean isLoadedData;
    public DimensionValueSet masterKeys;
    Map<String, QuerySelectField> masterOrGroupingFields = new LinkedHashMap<String, QuerySelectField>();
    Map<String, QuerySelectField> allDimFields = new LinkedHashMap<String, QuerySelectField>();
    Map<String, FieldDefine> allFieldDefines = new LinkedHashMap<String, FieldDefine>();
    Map<String, String> refFields = new LinkedHashMap<String, String>();
    List<QuerySelectField> selectedFields;
    List<QuerySelectField> numberFields = new ArrayList<QuerySelectField>();
    int allQueryFieldCount = 0;
    List<QueryDimensionDefine> dimensions;
    Map<String, QueryDimensionDefine> conditionDimensions = new LinkedHashMap<String, QueryDimensionDefine>();
    Map<String, QueryDimensionDefine> rowDimensions = new LinkedHashMap<String, QueryDimensionDefine>();
    Map<String, QueryDimensionDefine> colDimensions = new LinkedHashMap<String, QueryDimensionDefine>();
    QueryDimensionDefine fieldDimension;
    QueryDimensionDefine periodDimension;
    QueryDimensionDefine firstEntityDimension;
    boolean isFilterMode = false;
    GridType gridType;
    public boolean hasSortedField;
    boolean isAutoGather;
    boolean isShowGather;
    Map<String, List<DataLinkDefine>> allLinkesInRegion = new LinkedHashMap<String, List<DataLinkDefine>>();
    Map<String, DataLinkDefine> fieldMapLink;
    String blockCondition = null;
    List<QuerySelectField> showedFields = new ArrayList<QuerySelectField>();
    ArrayList<GradeTotalItem> gardeTotalItems;
    boolean hasSumDimension;
    IDataWarningDao warnDao;
    List<String> customExpress;
    List<String> periods;
    public IEntityTable customPeriodTable;
    public List<Integer> customFieldIndex;

    public QueryData() {
        try {
            this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
            this.ientitymetaservice = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            this.iRuntimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
            this.viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            this.dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            this.viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.warnDao = (IDataWarningDao)BeanUtil.getBean(IDataWarningDao.class);
            this.queryEntityUtil = (QueryEntityUtil)BeanUtil.getBean(QueryEntityUtil.class);
            this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
            this.dataEngineAdapter = (DataEngineAdapter)BeanUtil.getBean(DataEngineAdapter.class);
            this.columnModelFinder = (IColumnModelFinder)BeanUtil.getBean(IColumnModelFinder.class);
        }
        catch (Exception e) {
            logger.error("querydata \u521d\u59cb\u5316\u5f02\u5e38", e);
        }
    }

    public Map<String, Integer> getFieldIndex() throws DataNoneQueryException {
        if (this.isLoadedData) {
            return this.fieldIndex;
        }
        throw new DataNoneQueryException();
    }

    void getSelectFieldsInBlock(QueryBlockDefine block) {
        Optional<QueryDimensionDefine> fieldDim;
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            this.selectedFields = fd.getSelectFields();
        }
    }

    boolean autoGatherCheck() {
        try {
            TaskDefine taskDefine;
            TaskGatherType taskGatherType;
            QuerySelectField field;
            String taskId;
            if (!this.isWantDetail) {
                return false;
            }
            if (this.numberFields.size() > 0 && (taskId = (field = this.numberFields.get(0)).getTaskId()) != null && (taskGatherType = (taskDefine = this.viewController.queryTaskDefine(taskId)).getTaskGatherType()) == TaskGatherType.TASK_GATHER_AUTO && this.allQueryFieldCount == this.numberFields.size()) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    private void loadField(IGroupingQuery groupQuery, String entityFieldKey) {
        try {
            this.showedFields = new ArrayList<QuerySelectField>();
            boolean isDetailQuery = this.gridType == GridType.DETAIL;
            String fieldCondition = "";
            if (this.selectedFields == null || this.selectedFields.size() == 0) {
                this.selectedFields = new ArrayList<QuerySelectField>();
                return;
            }
            ArrayList<QuerySelectField> masterSelectFIelds = new ArrayList<QuerySelectField>();
            int index = 0;
            LinkedHashMap<String, String> tableKeys = new LinkedHashMap<String, String>();
            for (QuerySelectField querySelectField : this.selectedFields) {
                int colindex;
                boolean isMaster = Boolean.parseBoolean(querySelectField.getIsMaster());
                boolean isMasterOrGroupingField = isMaster || querySelectField.getIsGroupField();
                FieldDefine field = null;
                ColumnModelDefine columnModelDefine = null;
                String tableKey = null;
                FieldDefine refField = null;
                if (!querySelectField.getCustom()) {
                    field = this.dataDefinitionRuntimeController.queryFieldDefine(querySelectField.getCode());
                    if (field == null) {
                        columnModelDefine = this.dataModelService.getColumnModelDefineByID(querySelectField.getCode());
                        field = this.columnModelFinder.findFieldDefine(columnModelDefine);
                    }
                    refField = QueryHelper.getReferentField(querySelectField.getCode());
                    if (isMaster) {
                        String tableName = querySelectField.getTableName();
                        String tableKey1 = querySelectField.getTableKey();
                        Optional<QuerySelectField> first = this.selectedFields.stream().filter(e -> !Boolean.parseBoolean(e.getIsMaster())).findFirst();
                        if (first.isPresent() && (tableName = first.get().getDataSheet()) == null && first.get().getCustom() && querySelectField.getDataSheet() != null) {
                            tableName = querySelectField.getDataSheet();
                        }
                        if (tableName != null) {
                            TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(tableKey1);
                            if (tableDefine != null) {
                                field = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(querySelectField.getFileExtension(), tableDefine.getKey());
                                if ("DW".equals(querySelectField.getFileExtension()) && field == null) {
                                    field = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", tableDefine.getKey());
                                }
                                refField = QueryHelper.getReferentField(field.getKey());
                            } else {
                                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableKey1);
                                columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), querySelectField.getFileExtension());
                                if (columnModelDefine != null) {
                                    field = this.columnModelFinder.findFieldDefine(columnModelDefine);
                                }
                                if ("DW".equals(querySelectField.getFileExtension()) && columnModelDefine == null) {
                                    field = this.columnModelFinder.findFieldDefine(this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "MDCODE"));
                                }
                                refField = QueryHelper.getReferentField(field.getKey());
                            }
                        }
                    }
                    tableKey = field.getOwnerTableKey();
                }
                String sfCode = querySelectField.getCode();
                if (this.isAutoGather && sfCode.equals(entityFieldKey)) {
                    field = refField;
                }
                if (field != null) {
                    this.allFieldDefines.put(querySelectField.getCode(), field);
                    String dimName = null;
                    dimName = querySelectField.getDimensionName() != null ? querySelectField.getDimensionName() : this.dataAssist.getDimensionName(field);
                    this.allDimFields.put(dimName, querySelectField);
                    if (refField != null) {
                        this.refFields.put(refField.getCode(), sfCode);
                    }
                    if (isMasterOrGroupingField) {
                        this.masterOrGroupingFields.put(dimName, querySelectField);
                    }
                    if (!isMaster && !querySelectField.isHidden()) {
                        ++this.allQueryFieldCount;
                        if (QueryHelper.isNumField(field)) {
                            this.numberFields.add(querySelectField);
                        }
                    }
                }
                if (isMaster) {
                    masterSelectFIelds.add(querySelectField);
                } else {
                    String regionKey;
                    tableKeys.put(querySelectField.getTableName(), tableKey);
                    if (this.fieldMapLink == null) {
                        this.fieldMapLink = new LinkedHashMap<String, DataLinkDefine>();
                    }
                    if (!this.fieldMapLink.containsKey(sfCode) && (regionKey = querySelectField.getRegionKey()) != null) {
                        List<DataLinkDefine> linkDefines;
                        if (this.allLinkesInRegion.containsKey(regionKey)) {
                            linkDefines = this.allLinkesInRegion.get(regionKey);
                        } else {
                            linkDefines = this.helper.getAllLinkDefinesInRegion(regionKey);
                            this.allLinkesInRegion.put(regionKey, linkDefines);
                        }
                        for (DataLinkDefine linkDefine : linkDefines) {
                            if (!linkDefine.getLinkExpression().equals(sfCode)) continue;
                            this.fieldMapLink.put(sfCode, linkDefine);
                        }
                    }
                }
                if (!querySelectField.isHidden()) {
                    this.showedFields.add(querySelectField);
                    this.showedFieldIndex.put(sfCode, index);
                    if (this.block.getIsDataSet()) {
                        this.selectFieldIndex.put(sfCode, index);
                        ++index;
                    }
                }
                if (!this.block.getIsDataSet()) {
                    this.selectFieldIndex.put(sfCode, index);
                    ++index;
                }
                if (!this.hasFormulaWithPeriodShift) {
                    this.hasFormulaWithPeriodShift = querySelectField.getStatisticsFields() != null && !querySelectField.getStatisticsFields().isEmpty();
                }
                boolean fieldSorted = false;
                QueryItemSortDefine sortDefine = querySelectField.getSort();
                if (sortDefine != null) {
                    fieldSorted = querySelectField.getIsSorted();
                    ArrayList<Object> fvs = sortDefine.getFilterValues();
                    if (fvs != null && fvs.size() > 0 && !this.hasSortedField) {
                        this.hasSortedField = true;
                    }
                }
                if ((querySelectField.isHidden() || isMasterOrGroupingField) && !isDetailQuery && field != null) {
                    if (querySelectField.getIsGroupField() && !this.isSimpleQuery) {
                        if (querySelectField.getIsMaster().equals("true")) {
                            String dimName = QueryHelper.getDimName(field);
                            Optional<QueryDimensionDefine> isHasCorrespondingDim = this.block.getBlockInfo().getQueryDimensions().stream().filter(dimsion -> dimsion.getLayoutType() != QueryLayoutType.LYT_CONDITION && dimsion.getDimensionType() == QueryDimensionType.QDT_ENTITY && dimName.equals(dimsion.getDimensionName())).findFirst();
                            if (!isHasCorrespondingDim.isPresent()) continue;
                            colindex = groupQuery.addGroupColumn(field);
                            if (this.isAutoGather && sfCode.equals(entityFieldKey)) {
                                this.fieldIndex.put(sfCode, colindex);
                                continue;
                            }
                            this.fieldIndex.put(field.getKey(), colindex);
                            continue;
                        }
                        int colindex3 = groupQuery.addGroupColumn(field);
                        if (this.isAutoGather && sfCode.equals(entityFieldKey)) {
                            this.fieldIndex.put(sfCode, colindex3);
                            continue;
                        }
                        this.fieldIndex.put(field.getKey(), colindex3);
                        continue;
                    }
                    if (entityFieldKey != null && sfCode.equals(entityFieldKey) && this.isAutoGather) {
                        int colindex2 = groupQuery.addGroupColumn(field);
                        this.fieldIndex.put(sfCode, colindex2);
                        continue;
                    }
                    int colindex2 = groupQuery.addColumn(field);
                    this.fieldIndex.put(field.getKey(), colindex2);
                    continue;
                }
                String dimensionName = querySelectField.getDimensionName();
                boolean isConditionField = false;
                if (dimensionName != null && querySelectField.isHidden()) {
                    isConditionField = this.dimensions.stream().anyMatch(dim -> dim.getLayoutType() == QueryLayoutType.LYT_CONDITION && dimensionName.equals(dim.getDimensionName()));
                }
                if (field != null && isConditionField) {
                    colindex = groupQuery.addColumn(field);
                    this.fieldIndex.put(field.getKey(), colindex);
                }
                if (!this.isFilterMode) {
                    this.isFilterMode = QueryHelper.checkIsFilterMode(querySelectField);
                }
                if (querySelectField.getCustom()) {
                    int colindex4 = groupQuery.addExpressionColumn(querySelectField.getCustomValue());
                    this.fieldIndex.put(querySelectField.getCustomValue(), colindex4);
                    groupQuery.setGatherType(colindex4, querySelectField.getGatherType());
                    continue;
                }
                SetColumnReturn returnObj = this.setColumn(groupQuery, querySelectField, fieldCondition, fieldSorted, refField);
                this.fieldIndex = this.setStaticticsColumn(groupQuery, querySelectField, this.fieldIndex);
                fieldCondition = returnObj.condition;
                this.fieldIndex = returnObj.columnIndex;
            }
            if (this.block.getBlockType() == QueryBlockType.QBT_CUSTOMENTRY) {
                String FIELDFLOATORDER = "FLOATORDER";
                for (String table : tableKeys.keySet()) {
                    TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine((String)tableKeys.get(table));
                    if (tableDefine != null) {
                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", tableDefine.getKey());
                        if (fieldDefine != null) {
                            int columnIndex = groupQuery.addColumn(fieldDefine);
                            this.fieldIndex.put(fieldDefine.getKey(), columnIndex);
                            groupQuery.setGatherType(columnIndex, FieldGatherType.FIELD_GATHER_NONE);
                            continue;
                        }
                        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableDefine.getKey(), "FLOATORDER");
                        if (columnModelDefine == null || (fieldDefine = this.columnModelFinder.findFieldDefine(columnModelDefine)) == null) continue;
                        int columnIndex = groupQuery.addColumn(fieldDefine);
                        this.fieldIndex.put(fieldDefine.getKey(), columnIndex);
                        groupQuery.setGatherType(columnIndex, FieldGatherType.FIELD_GATHER_NONE);
                        continue;
                    }
                    TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById((String)tableKeys.get(table));
                    if (tableModelDefine == null) continue;
                    FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", tableModelDefine.getID());
                    if (fieldDefine != null) {
                        int columnIndex = groupQuery.addColumn(fieldDefine);
                        this.fieldIndex.put(fieldDefine.getKey(), columnIndex);
                        groupQuery.setGatherType(columnIndex, FieldGatherType.FIELD_GATHER_NONE);
                        continue;
                    }
                    ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "FLOATORDER");
                    if (columnModelDefine == null || (fieldDefine = this.columnModelFinder.findFieldDefine(columnModelDefine)) == null) continue;
                    int columnIndex = groupQuery.addColumn(fieldDefine);
                    this.fieldIndex.put(fieldDefine.getKey(), columnIndex);
                    groupQuery.setGatherType(columnIndex, FieldGatherType.FIELD_GATHER_NONE);
                }
            }
            if (!this.hasSortedField && isDetailQuery) {
                for (QuerySelectField querySelectField : this.masterOrGroupingFields.values()) {
                    QueryItemSortDefine sortDefine;
                    if (querySelectField.getSort() == null) {
                        sortDefine = new QueryItemSortDefine();
                        sortDefine.setSortType(QuerySortType.SORT_ASC);
                        querySelectField.setSort(sortDefine);
                        continue;
                    }
                    sortDefine = querySelectField.getSort();
                    ArrayList<Object> fvs = sortDefine.getFilterValues();
                    if (fvs == null || fvs.size() <= 0) continue;
                    this.hasSortedField = true;
                }
            }
            if (!StringUtil.isNullOrEmpty((String)fieldCondition)) {
                this.blockCondition = StringUtil.isNullOrEmpty((String)this.blockCondition) ? fieldCondition : String.format("{1} OR {2}", this.blockCondition, fieldCondition);
            }
        }
        catch (Exception ex) {
            logger.error("", ex);
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        ConcurrentHashMap seen = new ConcurrentHashMap();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }

    private SetColumnReturn setColumn(IGroupingQuery groupQuery, QuerySelectField sf, String fieldCondition, boolean fieldSort, FieldDefine refField) {
        try {
            TableDefine tableDefine;
            SetColumnReturn returnObj = new SetColumnReturn();
            int colIndex = 0;
            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(sf.getCode());
            if (Boolean.parseBoolean(sf.getIsMaster()) && (tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(sf.getTableKey())) != null) {
                field = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(sf.getFileExtension(), tableDefine.getKey());
                if ("DW".equals(sf.getFileExtension()) && field == null) {
                    field = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", tableDefine.getKey());
                }
            }
            if (sf.getCode() == null && sf.getTitle() != null) {
                int index = groupQuery.addExpressionColumn(sf.getTitle());
                this.fieldIndex.put(sf.getTitle(), index);
                returnObj.condition = fieldCondition;
                returnObj.columnIndex = this.fieldIndex;
                return returnObj;
            }
            if (field != null) {
                boolean isMaster = Boolean.parseBoolean(sf.getIsMaster());
                if (!this.isWantDetail && sf.getIsGroupField()) {
                    colIndex = groupQuery.addGroupColumn(field);
                    if (isMaster) {
                        this.fieldIndex.put(sf.getCode(), colIndex);
                    } else {
                        this.fieldIndex.put(field.getKey(), colIndex);
                    }
                } else if (!sf.isHidden() || isMaster) {
                    if (isMaster) {
                        colIndex = groupQuery.addGroupColumn(field);
                        this.fieldIndex.put(sf.getCode(), colIndex);
                    } else {
                        colIndex = groupQuery.addColumn(field);
                        this.fieldIndex.put(field.getKey(), colIndex);
                    }
                }
                if (this.isShowGather) {
                    if (!sf.isHidden()) {
                        if (sf.getGatherType() != null) {
                            groupQuery.setGatherType(colIndex, sf.getGatherType());
                        } else {
                            groupQuery.setGatherType(colIndex, field.getGatherType());
                        }
                    }
                } else if (!sf.isHidden()) {
                    groupQuery.setGatherType(colIndex, field.getGatherType());
                }
                if (!(!sf.getIsSorted() || fieldSort && Boolean.parseBoolean(sf.getIsMaster()))) {
                    QueryItemSortDefine sortDefine = sf.getSort();
                    if (!Boolean.parseBoolean(sf.getIsMaster()) && sortDefine.getSortType() != null) {
                        groupQuery.addOrderByItem(field, sortDefine.getSortType() == QuerySortType.SORT_DESC);
                    }
                    fieldCondition = this.getFilterConditions(fieldCondition, sortDefine, field);
                    ArrayList<Object> fvs = sortDefine.getFilterValues();
                    if (fvs != null && fvs.size() > 0) {
                        if (Boolean.parseBoolean(sf.getIsMaster())) {
                            String dimName = sf.getDimensionName();
                            if (sf.getDimensionName() == null) {
                                dimName = this.dataAssist.getDimensionName(field);
                            }
                            DimensionValueSet masterFilerSet = new DimensionValueSet();
                            masterFilerSet.setValue(dimName, fvs);
                            groupQuery.setMasterKeys(masterFilerSet);
                        } else {
                            groupQuery.setColumnFilterValueList(colIndex, fvs);
                        }
                    }
                }
            }
            returnObj.condition = fieldCondition;
            returnObj.columnIndex = this.fieldIndex;
            return returnObj;
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5206\u7ec4\u67e5\u8be2\u589e\u52a0\u5217\u5f02\u5e38", (String)ex.getMessage());
            return null;
        }
    }

    private String getFilterConditions(String condition, QueryItemSortDefine sortDefine, FieldDefine field) {
        List<QueryFilterDefine> filters;
        List deployInfoByDataFieldKeys = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
        if (deployInfoByDataFieldKeys.size() > 0) {
            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        }
        if ((filters = sortDefine.getFilterCondition()) == null) {
            return condition;
        }
        String itemCondition = "";
        boolean isConditionNull = StringUtil.isNullOrEmpty((String)condition);
        int i = 0;
        for (QueryFilterDefine filter : filters) {
            if (filter.getSymbol() == null || filter.getValue() == null) continue;
            String expression = "";
            if (QueryHelper.isNumField(field)) {
                DataValidationExpression exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.helper.getSymbol(filter.getSymbol()), (Object)filter.getValue());
                expression = exp.toFormula();
            } else {
                StringBuilder fieldStr = new StringBuilder();
                fieldStr.append(deployInfoByColumnKey.getTableName()).append("[").append(deployInfoByColumnKey.getFieldName()).append("]");
                String fieldCondition = fieldStr.toString();
                switch (filter.getSymbol().toString()) {
                    case "Start": {
                        expression = fieldCondition + " like '" + filter.getValue() + "%' ";
                        break;
                    }
                    case "NotStart": {
                        expression = fieldCondition + " not like '" + filter.getValue() + "%' ";
                        break;
                    }
                    case "End": {
                        expression = fieldCondition + " like '%" + filter.getValue() + "'";
                        break;
                    }
                    case "NotEnd": {
                        expression = fieldCondition + " not like '%" + filter.getValue() + "' ";
                        break;
                    }
                    case "Contain": {
                        expression = fieldCondition + " like '%" + filter.getValue() + "%'";
                        break;
                    }
                    case "NotContain": {
                        expression = fieldCondition + " not like '%" + filter.getValue() + "%' ";
                    }
                }
            }
            itemCondition = i > 0 ? itemCondition + String.format(" %s %s%s", sortDefine.getFilterRelation(), expression, isConditionNull ? "" : ")") : (isConditionNull ? itemCondition + expression : itemCondition + String.format(" AND (%s", expression));
            ++i;
        }
        if (StringUtil.isNullOrEmpty((String)condition)) {
            return itemCondition;
        }
        condition = condition + String.format(" OR (%s)", itemCondition);
        return condition;
    }

    Map<String, Integer> setStaticticsColumn(IGroupingQuery groupQuery, QuerySelectField sf, Map<String, Integer> fieldIndex) {
        List<QueryStatisticsItem> statisticsFields = sf.getStatisticsFields();
        if (statisticsFields != null && statisticsFields.size() > 0) {
            for (int i = 0; i < statisticsFields.size(); ++i) {
                QueryStatisticsItem item = statisticsFields.get(i);
                int colindex = groupQuery.addExpressionColumn(item.getFormulaExpression());
                if (fieldIndex.containsKey(item.getFormulaExpression())) continue;
                fieldIndex.put(item.getFormulaExpression(), colindex);
            }
        }
        return fieldIndex;
    }

    private void loadDimension(IGroupingQuery groupQuery) {
        try {
            QuerySelectField sf;
            String firstDimenKey = this.block.getQueryMasterKeys().split(";")[0];
            this.gardeTotalItems = new ArrayList();
            int entityDimCount = (int)this.dimensions.stream().filter(d -> d != null && (d.getDimensionType() == QueryDimensionType.QDT_ENTITY || d.getDimensionType() == QueryDimensionType.QDT_DICTIONARY) && d.getLayoutType() != QueryLayoutType.LYT_CONDITION).count();
            for (int i = 0; i < this.dimensions.size(); ++i) {
                String[] structs;
                boolean isTreeStruct;
                QueryDimensionDefine dimsion = this.dimensions.get(i);
                if (dimsion == null || dimsion.getDimensionType() == QueryDimensionType.QDT_FIELD || dimsion.isHidden() || dimsion.getDimensionType() == QueryDimensionType.QDT_CUSTOM || dimsion.getDimensionType() == QueryDimensionType.QDT_GRIDFIELD) continue;
                boolean isNotCondition = dimsion.getLayoutType() != QueryLayoutType.LYT_CONDITION;
                EntityViewDefine entityView = QueryHelper.getEntityView(dimsion.getViewId());
                String dimensionName = "";
                boolean isEntity = dimsion.getDimensionType() == QueryDimensionType.QDT_ENTITY;
                TableModelDefine entityTableDefine = null;
                String treeStruct = "";
                if (entityView != null && entityView.getEntityId() != null) {
                    boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId());
                    if (periodView) {
                        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityView.getEntityId());
                        dimensionName = periodEntity.getDimensionName();
                    } else {
                        TableModelDefine tableModel = this.ientitymetaservice.getTableModel(entityView.getEntityId());
                        dimensionName = tableModel.getTitle();
                    }
                    entityTableDefine = this.queryEntityUtil.getEntityTablelDefineByView(entityView.getEntityId());
                    treeStruct = this.queryEntityUtil.getDicTreeStructByView(entityView.getEntityId());
                }
                if (isEntity && !dimsion.isHidden()) {
                    if (this.firstEntityDimension == null && dimsion.getViewId().equals(firstDimenKey)) {
                        this.firstEntityDimension = dimsion;
                    }
                    if (dimsion.isPeriodDim() && this.periodDimension == null) {
                        this.periodDimension = dimsion;
                    }
                }
                if (entityDimCount >= 2 && isNotCondition && (isEntity || dimsion.getDimensionType() == QueryDimensionType.QDT_DICTIONARY)) {
                    QuerySelectField masterSef = this.masterOrGroupingFields.get(dimensionName);
                    if (!this.isWantDetail) {
                        QueryItemSortDefine sortDefine;
                        ArrayList<Object> fvs;
                        FieldDefine field = this.allFieldDefines.get(masterSef.getCode());
                        Integer colindex = null;
                        if (masterSef.getIsGroupField()) {
                            if (!this.fieldIndex.containsKey(field.getKey())) {
                                colindex = groupQuery.addGroupColumn(field);
                                this.fieldIndex.put(masterSef.getCode(), colindex);
                            }
                        } else {
                            colindex = groupQuery.addColumn(field);
                            this.fieldIndex.put(masterSef.getCode(), colindex);
                        }
                        this.fieldIndex = this.setStaticticsColumn(groupQuery, masterSef, this.fieldIndex);
                        if (masterSef.getIsSorted() && (fvs = (sortDefine = masterSef.getSort()).getFilterValues()) != null && fvs.size() > 0) {
                            if (Boolean.parseBoolean(masterSef.getIsMaster())) {
                                String dimName = masterSef.getDimensionName();
                                if (masterSef.getDimensionName() == null) {
                                    dimName = this.dataAssist.getDimensionName(field);
                                }
                                DimensionValueSet masterFilerSet = new DimensionValueSet();
                                masterFilerSet.setValue(dimName, fvs);
                                groupQuery.setMasterKeys(masterFilerSet);
                            } else if (colindex != null) {
                                groupQuery.setColumnFilterValueList(colindex.intValue(), fvs);
                            }
                        }
                    }
                }
                if (!this.hasSumDimension && isNotCondition && dimsion.isShowSum()) {
                    this.hasSumDimension = true;
                }
                boolean bl = isTreeStruct = !StringUtils.isEmpty((String)treeStruct);
                if (!isTreeStruct || !isNotCondition || (structs = treeStruct.split(",|;")).length <= 1) continue;
                try {
                    ArrayList<Integer> levels = new ArrayList<Integer>();
                    for (int l = 1; l <= structs.length; ++l) {
                        levels.add(l);
                    }
                    DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(entityTableDefine.getName());
                    ColumnModelDefine columnModelDefine = dimensionChanger.getColumn(dimensionName);
                    String field = this.refFields.get(columnModelDefine.getCode());
                    GradeLinkItem linkItem = new GradeLinkItem();
                    linkItem.setEntityView(entityView);
                    linkItem.setKey(this.fieldMapLink.get(field).getKey());
                    linkItem.setLinkExpression(field);
                    GradeTotalItem item = new GradeTotalItem(linkItem, this.fieldIndex.get(field).intValue(), levels);
                    this.gardeTotalItems.add(item);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (this.firstEntityDimension != null && (sf = this.masterOrGroupingFields.get(this.firstEntityDimension.getDimensionName())) != null && sf.isHidden()) {
                FieldDefine field = this.allFieldDefines.get(sf.getCode());
                int colindex = 0;
                if (!this.isSimpleQuery) {
                    if (!this.fieldIndex.containsKey(field.getKey())) {
                        colindex = groupQuery.addGroupColumn(field);
                    }
                } else {
                    colindex = groupQuery.addColumn(field);
                }
                if (!this.fieldIndex.containsKey(field.getKey())) {
                    if (Boolean.parseBoolean(sf.getIsMaster())) {
                        this.fieldIndex.put(sf.getCode(), colindex);
                    } else {
                        this.fieldIndex.put(field.getKey(), colindex);
                    }
                }
            }
        }
        catch (Exception ex) {
            logger.error("", ex);
        }
    }

    private void loadWarning() {
        String key = "'" + this.block.getModelID() + "'-'" + this.block.getId() + "'";
        List<DataWarningDefine> warList = this.warnDao.QueryDataWarnigs(key);
        this.customExpress = new ArrayList<String>();
        for (DataWarningDefine warn : warList) {
            String express = this.getCustomWarnFormula(warn);
            if (!"".equals(express)) {
                this.customExpress.add(express);
                continue;
            }
            DataWarningProperties preWarn = warn.getProperty();
            if (!"\u516c\u5f0f\u6761\u4ef6".equals(preWarn.getPreFieldLabel())) continue;
            this.customExpress.add(preWarn.getFieldFormulaInput());
        }
    }

    private String handlingFormula(String curExpression, String condition, String value, String expression) {
        String keyWord = "if ";
        String addExpression = "";
        if (!StringUtil.isNullOrEmpty((String)condition)) {
            addExpression = " and " + condition;
        }
        if (!StringUtil.isNullOrEmpty((String)expression)) {
            keyWord = " else if ";
        }
        expression = expression + keyWord + curExpression + addExpression + " then " + value;
        return expression;
    }

    public String getCustomWarnFormula(DataWarningDefine preWarn) {
        String expression = "";
        DataWarningProperties warnProperty = preWarn.getProperty();
        try {
            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(preWarn.getFieldCode());
            block1 : switch (preWarn.getWarnType()) {
                case CELL: {
                    if (field == null && "\u6307\u6807\u6570\u503c".equals(warnProperty.getPreFieldLabel())) {
                        switch (warnProperty.getFieldCompareValue()) {
                            case BETWEEN: {
                                expression = preWarn.getFieldCode() + ">=" + warnProperty.getFieldCompareInput() + " and " + preWarn.getFieldCode() + "<=" + warnProperty.getFieldCompareInputTwo();
                                break block1;
                            }
                            case NOTBETWEEN: {
                                expression = preWarn.getFieldCode() + "<=" + warnProperty.getFieldCompareInput() + " or " + preWarn.getFieldCode() + ">=" + warnProperty.getFieldCompareInputTwo();
                                break block1;
                            }
                        }
                        expression = preWarn.getFieldCode() + this.helper.getSymbol(warnProperty.getFieldCompareValue()).getSign() + warnProperty.getFieldCompareInput();
                        break;
                    }
                    if (field != null || !"\u516c\u5f0f\u6761\u4ef6".equals(warnProperty.getPreFieldLabel())) break;
                    expression = warnProperty.getFieldFormulaInput();
                    break;
                }
                case ICON: {
                    if (field == null && "\u6307\u6807\u6570\u503c".equals(warnProperty.getPreFieldLabel())) {
                        List<String> compareList = warnProperty.getFieldIconCompareList();
                        List<Integer> valueList = warnProperty.getIconInputList();
                        int index = compareList.size();
                        for (int i = 0; i < index; ++i) {
                            String curValue;
                            FilterSymbols current;
                            String curExpression = "";
                            if (i == 0) {
                                current = FilterSymbols.valueOf(compareList.get(i));
                                curValue = String.valueOf(valueList.get(i));
                                curExpression = preWarn.getFieldCode() + this.helper.getSymbol(current).getSign() + curValue;
                                expression = this.handlingFormula(curExpression, "", Integer.toString(i), expression);
                                continue;
                            }
                            current = FilterSymbols.valueOf(compareList.get(i));
                            curValue = String.valueOf(valueList.get(i));
                            curExpression = preWarn.getFieldCode() + this.helper.getSymbol(current).getSign() + curValue;
                            FilterSymbols parent = FilterSymbols.getAntonym(compareList.get(i - 1));
                            String parentValue = String.valueOf(valueList.get(i - 1));
                            String preExpression = preWarn.getFieldCode() + this.helper.getSymbol(parent).getSign() + parentValue;
                            expression = this.handlingFormula(curExpression, preExpression, Integer.toString(i), expression);
                            if (i + 1 != index) continue;
                            FilterSymbols lastSymbols = FilterSymbols.getAntonym(compareList.get(i));
                            String lasterValue = String.valueOf(valueList.get(i));
                            String lastExpression = preWarn.getFieldCode() + this.helper.getSymbol(lastSymbols).getSign() + lasterValue;
                            expression = this.handlingFormula(lastExpression, "", Integer.toString(i + 1), expression);
                        }
                        break;
                    }
                    if (field != null || !"\u516c\u5f0f\u6761\u4ef6".equals(warnProperty.getPreFieldLabel())) break;
                    expression = warnProperty.getFieldFormulaInput();
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        return expression;
    }

    public IGroupingTable getData(QueryBlockDefine block, GridType gridType, boolean isSimpleQuery) {
        this.block = block;
        PeriodHelper phelper = new PeriodHelper(block);
        this.periods = phelper.getPeriodsForQuery();
        return this.getData(block, gridType, isSimpleQuery, this.periods);
    }

    boolean isQueryDetail() {
        return this.block.isShowDetail() && (this.isSimpleQuery || this.gridType == GridType.DETAIL);
    }

    public IGroupingTable getData(QueryBlockDefine block, GridType gridType, boolean isSimpleQuery, List<String> periods) {
        this.helper = new QueryHelper();
        this.periods = periods;
        this.block = block;
        this.isSimpleQuery = isSimpleQuery;
        this.gridType = gridType;
        this.isWantDetail = this.isQueryDetail();
        this.dataAssist = this.dataAccessProvider.newDataAssist(new ExecutorContext(this.dataDefinitionRuntimeController));
        IGroupingQuery groupQuery = this.dataAccessProvider.newGroupingQuery(null);
        groupQuery.setOldQueryModule(true);
        groupQuery.setWantDetail(this.isWantDetail);
        QueryGridExtension gridExtension = new QueryGridExtension(block.getBlockExtension());
        this.isShowGather = gridExtension.isShowGather();
        this.getSelectFieldsInBlock(block);
        this.isAutoGather = this.helper.autoGatherCheck(this.selectedFields, this.isWantDetail);
        this.dimensions = block.getQueryDimensions();
        String entityFieldKey = null;
        if (this.isAutoGather) {
            block2: for (Map.Entry<String, Object> entry : block.getQueryMasters().entrySet()) {
                Object value;
                Object v;
                if (!(entry.getValue() instanceof LinkedHashMap) || (v = ((LinkedHashMap)(value = (LinkedHashMap)entry.getValue())).get("bizFields")) == null || !(v instanceof LinkedHashMap)) continue;
                LinkedHashMap fields = (LinkedHashMap)v;
                for (Map.Entry objectEntry : fields.entrySet()) {
                    LinkedHashMap val;
                    Object o = objectEntry.getValue();
                    if (!(o instanceof LinkedHashMap) || !((val = (LinkedHashMap)o).get("Order") instanceof Integer) || (Integer)val.get("Order") != 0) continue;
                    entityFieldKey = (String)objectEntry.getKey();
                    continue block2;
                }
            }
        }
        this.loadField(groupQuery, entityFieldKey);
        ReloadTreeInfo reloadTreeInfo = null;
        SummaryScheme sumScheme = null;
        sumScheme = gridExtension.getSumSchemeObject();
        if (sumScheme != null) {
            reloadTreeInfo = new SummarySchemeUtils().toReloadTreeInfo(sumScheme);
        }
        this.loadWarning();
        this.customFieldIndex = new ArrayList<Integer>();
        if (this.customExpress != null && this.customExpress.size() > 0) {
            for (String string : this.customExpress) {
                int colindex = groupQuery.addExpressionColumn(string);
                this.fieldIndex.put(string, colindex);
                this.customFieldIndex.add(colindex);
            }
        }
        this.loadDimension(groupQuery);
        DataRegTotalInfo regTotalInfo = new DataRegTotalInfo(this.gardeTotalItems);
        for (GradeTotalItem item : this.gardeTotalItems) {
            if (this.fieldIndex.containsValue(item.getColumnIndex())) continue;
            groupQuery.addGroupColumn(item.getColumnIndex().intValue());
        }
        groupQuery.setDataRegTotalInfo(regTotalInfo);
        groupQuery.setWantDetail(block.isShowDetail() || this.isWantDetail);
        this.blockCondition = this.helper.setConditions(groupQuery, block, this.blockCondition, this.conditionDimensions, this.allDimFields, this.fieldIndex, periods, reloadTreeInfo, this.isAutoGather);
        if (!StringUtil.isNullOrEmpty((String)this.blockCondition)) {
            groupQuery.setRowFilter(this.blockCondition);
        }
        if (this.isWantDetail && this.isAutoGather && this.firstEntityDimension != null && !CollectionUtils.isEmpty(this.firstEntityDimension.getSelectItems())) {
            EntityViewDefine entityViewDefine = QueryHelper.getEntityView(this.firstEntityDimension.getViewId());
            QuerySelectItem querySelectItem = this.firstEntityDimension.getSelectItems().get(0);
            String entity = querySelectItem.getCode();
            if (this.firstEntityDimension.getSelectItems().size() > 1) {
                List<QuerySelectItem> selectItems = this.firstEntityDimension.getSelectItems();
                IEntityTable entityTable = QueryHelper.getEntityTable(entityViewDefine, reloadTreeInfo);
                String sumEntity = null;
                int maxDepthByEntityKey = 0;
                for (QuerySelectItem selectItem : selectItems) {
                    int depth = entityTable.getMaxDepthByEntityKey(selectItem.getCode());
                    if (depth <= maxDepthByEntityKey) continue;
                    sumEntity = selectItem.getCode();
                    maxDepthByEntityKey = depth;
                }
                entity = sumEntity;
            }
            int index = 0;
            QuerySelectField masterField = this.masterOrGroupingFields.get(this.firstEntityDimension.getDimensionName());
            index = this.fieldIndex.get(masterField.getCode());
            groupQuery.setEntityLevelGather(entity, index, entityViewDefine, null, reloadTreeInfo);
        }
        if (this.hasSortedField && GridType.DETAIL.equals((Object)this.gridType)) {
            groupQuery.setSortGroupingAndDetailRows(false);
        } else {
            groupQuery.setSortGroupingAndDetailRows(true);
        }
        if ((this.gridType != GridType.DETAIL || this.hasSumDimension) && block.getBlockType() != QueryBlockType.QBT_CUSTOMENTRY) {
            groupQuery.setSummarizingMethod(SummarizingMethod.RollUp);
        }
        this.isLoadedData = true;
        try {
            if (this.block.getIsDataSet()) {
                block.getQueryDimensions().get(0).setSelectFields(this.showedFields);
                this.fieldIndex = this.showedFieldIndex;
            } else {
                this.dataTable = this.hasFormulaWithPeriodShift ? this.helper.getMultiPeriodTable(groupQuery, block, this.periods) : this.helper.getDataTable(groupQuery, block, this.isQueryDetail(), false);
            }
        }
        catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
        this.masterKeys = groupQuery.getMasterKeys();
        return this.dataTable;
    }

    public QueryGridDefination.RowDataType checkRowData(IDataRow row) {
        if (row == null) {
            return QueryGridDefination.RowDataType.ALLNULL;
        }
        IFieldsInfo fields = row.getFieldsInfo();
        int zeroColumn = 0;
        int nullColumn = 0;
        int dataColumn = 0;
        for (int i = 0; i < fields.getFieldCount(); ++i) {
            try {
                boolean isNullGroupValue;
                if (this.customFieldIndex.contains(i)) continue;
                FieldDefine field = fields.getFieldDefine(i);
                FieldType fieldType = fields.getDataType(i);
                if (field != null) {
                    Integer index;
                    QuerySelectField item;
                    String key = field.getKey();
                    fieldType = field.getType();
                    if (!this.fieldIndex.containsKey(key) || "true".equals((item = this.selectedFields.get(index = this.selectFieldIndex.get(key))).getIsMaster())) continue;
                }
                AbstractData value = row.getValue(i);
                boolean bl = isNullGroupValue = row.getGroupingFlag() >= 0 && "\u2014\u2014".equals(value.getAsObject());
                if (value == null || isNullGroupValue) {
                    ++nullColumn;
                    continue;
                }
                switch (fieldType) {
                    case FIELD_TYPE_FLOAT: 
                    case FIELD_TYPE_DECIMAL: {
                        double fv = value.getAsFloat();
                        if (fv > -1.0E-6 && fv < 1.0E-6) {
                            ++zeroColumn;
                            break;
                        }
                        ++dataColumn;
                        break;
                    }
                    case FIELD_TYPE_INTEGER: {
                        int iv = value.getAsInt();
                        if (iv > -1 && iv < 1) {
                            ++zeroColumn;
                            break;
                        }
                        ++dataColumn;
                        break;
                    }
                    default: {
                        String val = value.getAsString();
                        if (StringUtil.isNullOrEmpty((String)val) || val.equals("\u2014\u2014")) {
                            ++nullColumn;
                            break;
                        }
                        ++dataColumn;
                    }
                }
                continue;
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        if (dataColumn > 0) {
            return QueryGridDefination.RowDataType.DATAS;
        }
        if (zeroColumn > 0) {
            return QueryGridDefination.RowDataType.ALLZERO;
        }
        return QueryGridDefination.RowDataType.ALLNULL;
    }

    class SetColumnReturn {
        public String condition;
        public Map<String, Integer> columnIndex = new LinkedHashMap<String, Integer>();

        SetColumnReturn() {
        }
    }
}

