/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.query.old.DataRowImpl
 *  com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.DataentryFlowService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.common.summary.SummaryScheme
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.ITreeStruct
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.state.common.StateConst
 *  com.jiuqi.nr.state.pojo.StateEntites
 *  com.jiuqi.nr.state.service.IStateSevice
 *  com.jiuqi.nr.state.serviceImpl.StateServiceImpl
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.old.DataRowImpl;
import com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.DataentryFlowService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.datawarning.dao.IDataWarningDao;
import com.jiuqi.nr.datawarning.dao.impl.DataWarningDaoImpl;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningProperties;
import com.jiuqi.nr.datawarning.service.IDataWarningExecutor;
import com.jiuqi.nr.datawarning.service.IDataWarningTable;
import com.jiuqi.nr.datawarning.serviceimpl.DataWarningExecutor;
import com.jiuqi.nr.datawarning.serviceimpl.DataWarningTable;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.ITreeStruct;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectHelper;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.block.DimensionPageLoadInfo;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryDirection;
import com.jiuqi.nr.query.block.QueryFieldPosition;
import com.jiuqi.nr.query.block.QueryGridExtension;
import com.jiuqi.nr.query.block.QueryGridPage;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.SummarySchemeUtils;
import com.jiuqi.nr.query.defines.DimensionItemType;
import com.jiuqi.nr.query.defines.QueryDimItem;
import com.jiuqi.nr.query.querymodal.QueryType;
import com.jiuqi.nr.query.service.GridType;
import com.jiuqi.nr.query.service.QueryCacheManager;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.PeriodHelper;
import com.jiuqi.nr.query.service.impl.QueryData;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.state.pojo.StateEntites;
import com.jiuqi.nr.state.service.IStateSevice;
import com.jiuqi.nr.state.serviceImpl.StateServiceImpl;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.netty.util.internal.StringUtil;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

public class QueryGridDefination {
    private final String CACHENAME = "QueryBlockPage";
    private NedisCacheManager cacheManager = DefaultCacheProvider.getCacheManager();
    private static final Logger logger = LoggerFactory.getLogger(QueryGridDefination.class);
    public QueryGridPage pageData;
    public boolean isPaging;
    public QueryBlockDefine block;
    public List<QueryDimensionDefine> rowDimensions;
    public List<QueryDimensionDefine> colDimensions;
    public List<QueryDimensionDefine> gridFieldDimensions;
    QueryDimensionDefine fieldDimension = null;
    public Map<String, QueryDimensionDefine> conditonDimensions;
    public List<QuerySelectField> showedFields;
    public List<String> hasNtAuthFields;
    public List<String> hasNtWriteAuthFields;
    public Map<String, Integer> showedFieldsIndex;
    public Map<String, FieldDefine> fieldDefines;
    public Map<String, FieldDefine> statictisFields;
    public Map<String, String> fieldDimNames;
    public List<QueryDimItem> rows;
    public List<QueryDimItem> cols;
    public IDataRow totalRow;
    public Map<String, Boolean> dimNameMasterMap;
    Map<String, Map<String, Map<Integer, Integer>>> dimStrucCurrentIndex;
    public IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    public DataModelService dataModelService;
    public IColumnModelFinder columnModelFinder;
    IRunTimeViewController viewController;
    public static IDataAccessProvider dataAccessProvider;
    public static IDataAssist dataAssist;
    private static QueryEntityUtil queryEntityUtil;
    public IGroupingTable dataTable;
    public Map<String, Integer> fieldIndex;
    public Map<String, List<String>> statictisFieldValues;
    public Map<String, Map<String, String>> duplicateFieldValues;
    public Map<String, List<Object>> fieldCodeAndValue;
    public Map<String, List<Object>> fieldCodeAndValueWarnUse;
    public Map<String, QuerySelectField> simpleFieldMap;
    int detailsCount = 0;
    int statisticCount;
    public QueryHelper helper;
    private PeriodHelper periodHelper;
    Map<String, Integer> pageIndex;
    public QueryCacheManager queryCache;
    public IDataWarningExecutor warExecutor;
    IEntityAuthorityService entityAuthorityProvider;
    @Autowired
    public IDataWarningDao warnDao;
    public IDataWarningTable warnTable;
    public boolean isExport;
    public GridType gridType;
    PeriodType ptype;
    boolean isRowSidePaging;
    int structRowCount = 0;
    boolean isQueryDetail;
    Map<String, EntityCache> entityDimensionData;
    boolean hasTreeStructDimension;
    final String DIMENSION_DIRECTION_ROW = "ROW";
    final String DIMENSION_DIRECTION_COL = "COL";
    int rowLastDimensionIndex = 0;
    int colLastDimensionIndex = 0;
    public Map<String, DataLinkDefine> fieldMapLink;
    public Map<String, IEntityTable> enumFieldTable;
    public IPeriodProvider customPeriodTable;
    public boolean isFieldInCol;
    int fieldDimensionIndex;
    QueryMode mode = QueryMode.DATAQUERY;
    QueryDirection fieldDirection = QueryDirection.COLDIRECTION;
    public boolean isTreeLoad = true;
    boolean hasMore = false;
    public boolean isSinglePeriod = false;
    public boolean isCustomInput = false;
    public boolean mixShowDetail = false;
    public boolean isHistoryData = false;
    public boolean isdataPenetration = false;
    public int nullEntityRowSize = 1;
    public String curPeriod = "";
    public Map<DimensionValueSet, Integer> customEntryInsertRows = new HashMap<DimensionValueSet, Integer>();
    public Map<DimensionValueSet, List<DimensionValueSet>> insertRowsBiz = new HashMap<DimensionValueSet, List<DimensionValueSet>>();
    public boolean isFloat = true;
    public List<FieldDefine> floatOrderField = new ArrayList<FieldDefine>();
    public Map<String, String> floatOrderMap = new HashMap<String, String>();
    public boolean hasMoreRegion = false;
    EntityViewDefine entityViewDefine = null;
    String dimName = null;
    HashMap<String, Boolean> authEntities = new HashMap();
    Date queryVersionStartDate = null;
    Date queryVersionEndDate = null;
    private IEntityMetaService entityMetaService;
    private IPeriodEntityAdapter periodEntityAdapter;
    boolean onlyLoadForm;
    boolean loopEnd;
    boolean childHasDetailRow;
    String firstViewId;
    public Map<String, QueryDimensionDefine> dimensionLinks;
    public int maxItemCount = 50;
    int totalCount = 0;
    Map<Integer, Boolean> needCheck = new HashMap<Integer, Boolean>();
    public DimensionPageLoadInfo pageInfo = new DimensionPageLoadInfo();
    public boolean lastItemNotEnd = false;
    public int lastItemIndex = 0;
    public String lastItemTitle;
    public boolean isTreeStruct = false;
    private String userKey;
    private String blockKey;
    public Map<String, QuerySelectField> statisticsFieldMap;
    private QueryDimItem curFirstDimItem;
    boolean hasFormulaWithPeriodShift;
    public boolean isSimpleQuery = false;
    QueryDimensionDefine firstDimension = null;
    DimensionValueSet queryMasterKey = null;
    public List<IDataRow> detailRows = new ArrayList<IDataRow>();
    public int MaxSortItemCount = 100;
    public boolean isAutoGather = false;
    public Map<String, Integer> selectFieldIndex = new HashMap<String, Integer>();
    private ExecutorContext executorContext;
    private QueryContext qContext;
    private ReportFormulaParser parser;
    QueryData qdata;
    public String decimal = null;
    public Integer decimalVal = null;
    public boolean useEntityAuth = true;
    public Map<String, Boolean> EntityAuthWrite = new HashMap<String, Boolean>();
    Map<String, Map<Integer, String>> nextLoadItem = new LinkedHashMap<String, Map<Integer, String>>();
    public String periodDimName = "DATATIME";
    List<String> periods = new ArrayList<String>();
    List<String> customExpress;
    int startDepth = 0;
    ReloadTreeInfo reloadTreeInfo = null;
    private IStateSevice stateSevice;
    private DataentryFlowService dataentryFlowService;
    Map<String, DimensionValueSet> dimVMap = new HashMap<String, DimensionValueSet>();
    List<Integer> customFieldIndex = new ArrayList<Integer>();
    HashMap<DimensionValueSet, Map<DimensionValueSet, StateConst>> stateCache = null;
    public List<String> Masterkeys;
    boolean hasMasterDimension;

    public boolean checkEntityAuth(DimensionValueSet dimValue) {
        try {
            String entityKey = dimValue.getValue(this.dimName).toString();
            if (this.authEntities.containsKey(entityKey)) {
                return this.authEntities.get(entityKey) == false;
            }
            boolean canWriteEntity = this.entityAuthorityProvider.canWriteEntity(this.entityViewDefine.getEntityId(), entityKey, this.queryVersionStartDate, this.queryVersionEndDate);
            this.authEntities.put(entityKey, canWriteEntity);
            return !canWriteEntity;
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53\u6743\u9650\u68c0\u67e5\u5f02\u5e38! " + e.getMessage());
            return false;
        }
    }

    public QueryGridDefination(QueryBlockDefine block, boolean isExport, boolean onlyLoadForm) throws CloneNotSupportedException {
        this.block = block;
        this.isExport = isExport;
        this.mode = QueryMode.DATAQUERY;
        this.onlyLoadForm = onlyLoadForm;
        try {
            this.init();
        }
        catch (Exception e) {
            logger.error("QueryGridDefination \u521d\u59cb\u5316\u5f02\u5e38:" + e.getMessage());
        }
    }

    public QueryGridDefination(QueryBlockDefine block, boolean isExport) {
        this.isExport = isExport;
        this.mode = QueryMode.SIMPLEQUERY;
        this.block = block;
        try {
            this.init();
        }
        catch (Exception e) {
            logger.error("QueryGridDefination \u521d\u59cb\u5316\u5f02\u5e38:" + e.getMessage());
        }
    }

    private QueryDimensionDefine getFirstDimension(QueryDirection direction, List<QueryDimensionDefine> dimensions) {
        EntityViewDefine entityView;
        QueryDimensionDefine firstDimension;
        if (this.fieldDirection == direction && this.fieldDimensionIndex == 0) {
            firstDimension = dimensions.get(1);
            this.startDepth = 1;
        } else {
            firstDimension = dimensions.get(0);
        }
        if (firstDimension != null && firstDimension.getDimensionName() == null && (entityView = QueryHelper.getEntityView(firstDimension.getViewId())) != null) {
            String dimName = QueryHelper.getDimName(entityView);
            firstDimension.setDimensionName(dimName);
        }
        return firstDimension;
    }

    private Map<Integer, List<String>> getDepthPageInfo() {
        Map<Object, Object> depthPageInfo = new LinkedHashMap();
        depthPageInfo = this.pageInfo.dimensionRows.get(this.pageInfo.firstDimensionID);
        if (depthPageInfo == null) {
            depthPageInfo = new LinkedHashMap();
        }
        return depthPageInfo;
    }

    private int getNullEntityRowSizeByFirstDim() {
        List<Object> dimensions = new ArrayList();
        if (this.gridType == GridType.ROW) {
            dimensions = this.rowDimensions;
        } else if (this.gridType == GridType.COL) {
            dimensions = this.colDimensions;
        }
        ArrayList<Integer> sizeList = new ArrayList<Integer>();
        if (dimensions.size() > 1) {
            for (int i = 1; i < dimensions.size(); ++i) {
                QueryDimensionDefine dimension = (QueryDimensionDefine)dimensions.get(i);
                if (QueryDimensionType.QDT_FIELD == dimension.getDimensionType()) continue;
                if ("DATATIME".equals(dimension.getDimensionName())) {
                    sizeList.add(this.periods.size());
                    continue;
                }
                EntityViewDefine entityView = QueryHelper.getEntityView(dimension.getViewId());
                DimensionValueSet dimValueSet = new DimensionValueSet();
                List<Object> rowsStr = new ArrayList();
                QueryDimensionDefine conditionDim = null;
                if (this.conditonDimensions != null) {
                    conditionDim = this.conditonDimensions.get(this.firstDimension.getViewId());
                }
                if (conditionDim != null && TableKind.TABLE_KIND_ENTITY_PERIOD != TableKind.valueOf((String)conditionDim.getTableKind())) {
                    rowsStr = this.getEntityDataRows(dimension);
                }
                if (!rowsStr.isEmpty()) {
                    dimValueSet.setValue(dimension.getDimensionName(), rowsStr);
                }
                if (this.periods != null && this.periods.size() > 0) {
                    dimValueSet.setValue(this.periodDimName, (Object)this.periods.get(this.periods.size() - 1));
                }
                IEntityTable entityTable = QueryHelper.getEntityTableOnce(entityView, dimValueSet.size() == 0 ? null : dimValueSet);
                int size = entityTable.getTotalCount();
                sizeList.add(size);
            }
            int size = (Integer)sizeList.stream().reduce((x, y) -> x * y).get();
            return size;
        }
        return 1;
    }

    private List<String> setHasDataEntitys(IEntityTable entityTable, List<IEntityRow> rows, int depth, boolean isShowNull, IEntityRow parentRow) {
        Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
        List<Object> depthList = new ArrayList();
        depthList = depthPageInfo.containsKey(depth) ? depthPageInfo.get(depth) : new ArrayList();
        boolean belongThisItem = true;
        if (parentRow != null && depthList.size() == 1) {
            IEntityRow row = entityTable.findByEntityKey((String)depthList.get(0));
            belongThisItem = row.getParentEntityKey().equals(parentRow.getEntityKeyData());
        }
        boolean startToCheck = this.needCheck.containsKey(depth) ? this.needCheck.get(depth) : false;
        int index = 0;
        for (IEntityRow row : rows) {
            List childEntity;
            ++index;
            if (startToCheck && !depthList.contains(row.getEntityKeyData()) && belongThisItem) continue;
            String dimName = this.pageInfo.dimensionName;
            DimensionValueSet tempValueSet = new DimensionValueSet();
            tempValueSet.setValue(dimName, (Object)row.getEntityKeyData());
            DimensionValueSet entityDim = new DimensionValueSet();
            entityDim.setValue(dimName, (Object)row.getEntityKeyData());
            if (this.dimNameMasterMap.get(dimName) == null) {
                logger.error("\u7ef4\u5ea6\u540d\u9519\u8bef  \u9519\u8bef\u4fe1\u606f\uff1a\u672a\u627e\u5230\u5339\u914d\u7684\u7ef4\u5ea6\u540d" + dimName, (Object)this.dimNameMasterMap);
            }
            List dataRows = this.dimNameMasterMap != null && this.dimNameMasterMap.get(dimName) != null && this.dimNameMasterMap.get(dimName) != false ? this.dataTable.findFuzzyRows(entityDim) : this.dataTable.findDetailRowsByGroupKeyByFirstDimension(entityDim, dimName);
            IDataRow groupingRow = this.dataTable.findGroupingRow(entityDim);
            boolean parentAdd = false;
            if (dataRows.size() > 0 || isShowNull || groupingRow != null) {
                if (dataRows.size() > 0 || groupingRow != null) {
                    this.pageInfo.hasDataItems.put(depth, row.getParentEntityKey());
                    this.pageInfo.hasDataItems.put(depth + 1, row.getEntityKeyData());
                }
                parentAdd = true;
                int startRecord = 0;
                if (this.pageInfo.recordIndex.containsKey(row.getEntityKeyData())) {
                    int lastEnd;
                    startRecord = lastEnd = this.pageInfo.recordIndex.get(row.getEntityKeyData()).intValue();
                }
                dataRows.removeIf(this::notShowNullOrZeroRow);
                int size = dataRows.size();
                int end = this.maxItemCount - (size - startRecord);
                boolean showThisAllNull = false;
                boolean showOtherAllNull = false;
                if (this.block.isShowDetail()) {
                    if (this.gridType != GridType.COL) {
                        if (isShowNull && !this.pageInfo.recordIndex.containsKey(row.getEntityKeyData())) {
                            this.pageInfo.recordIndex.put(row.getEntityKeyData(), 0);
                            if (this.isSimpleQuery && this.block.isShowNullRow() && !this.isSinglePeriod) {
                                this.maxItemCount = size == 0 ? --this.maxItemCount : end;
                            } else if (this.nullEntityRowSize > this.maxItemCount) {
                                showThisAllNull = true;
                                this.maxItemCount = 0;
                            } else {
                                this.maxItemCount -= this.nullEntityRowSize;
                                showOtherAllNull = true;
                            }
                            this.setPassedRows(depth, row);
                        }
                        if (!this.isSimpleQuery || !this.block.isShowNullRow() || this.isSinglePeriod) {
                            if (end < -100 && !showThisAllNull && !showOtherAllNull) {
                                end = startRecord == 0 ? this.maxItemCount : startRecord + this.maxItemCount;
                                this.pageInfo.recordIndex.put(row.getEntityKeyData(), end);
                                if (!this.pageInfo.recordIndex.containsKey(row.getParentEntityKey())) {
                                    this.pageInfo.recordIndex.put(row.getParentEntityKey(), 0);
                                    this.setPassedRows(depth - 1, parentRow);
                                }
                                this.setPassedRows(depth, row);
                                this.pageInfo.recordStart.put(row.getEntityKeyData(), startRecord);
                                this.hasMore = true;
                                this.maxItemCount = 0;
                            } else {
                                if (!showOtherAllNull) {
                                    this.maxItemCount = end;
                                }
                                if (this.pageInfo.recordStart.containsKey(row.getEntityKeyData())) {
                                    if (startRecord == size) {
                                        this.pageInfo.recordStart.remove(row.getEntityKeyData());
                                    } else {
                                        this.pageInfo.recordStart.put(row.getEntityKeyData(), startRecord);
                                    }
                                }
                                if (row.getParentEntityKey() != null && !this.pageInfo.recordIndex.containsKey(row.getParentEntityKey())) {
                                    this.pageInfo.recordIndex.put(row.getParentEntityKey(), 0);
                                    if (parentRow != null) {
                                        this.setPassedRows(depth - 1, parentRow);
                                    }
                                }
                                this.pageInfo.recordIndex.put(row.getEntityKeyData(), size);
                                this.setPassedRows(depth, row);
                                if (showThisAllNull) {
                                    this.maxItemCount = 0;
                                }
                            }
                        }
                    } else {
                        this.maxItemCount = end;
                        if (isShowNull && !this.pageInfo.recordIndex.containsKey(row.getEntityKeyData())) {
                            this.pageInfo.recordIndex.put(row.getEntityKeyData(), 0);
                            this.setPassedRows(depth, row);
                        }
                        this.pageInfo.recordIndex.put(row.getEntityKeyData(), size);
                        this.setPassedRows(depth, row);
                    }
                } else if (!this.pageInfo.recordIndex.containsKey(row.getEntityKeyData())) {
                    if (row.getParentEntityKey() != null && !this.pageInfo.recordIndex.containsKey(row.getParentEntityKey())) {
                        this.pageInfo.recordIndex.put(row.getParentEntityKey(), 0);
                        this.setPassedRows(depth - 1, parentRow);
                    }
                    this.hasMore = true;
                    this.pageInfo.recordIndex.put(row.getEntityKeyData(), size);
                    this.setPassedRows(depth, row);
                    if (isShowNull && !this.hasWriteSub(depth, row.getEntityKeyData())) {
                        --this.maxItemCount;
                    }
                }
                if (!depthList.contains(row.getEntityKeyData())) {
                    depthList.add(row.getEntityKeyData());
                }
                if (this.pageInfo.lastPassedRows.containsKey(depth)) {
                    String item;
                    List<String> keys = this.pageInfo.lastPassedRows.get(depth);
                    if ((depthList.size() == 1 || !this.block.isShowDetail()) && keys.contains(item = (String)depthList.get(0)) && item.equals(row.getEntityKeyData()) && size - startRecord == 0 && entityTable.getChildRows(row.getEntityKeyData()).size() == 0) {
                        depthList.remove(row.getEntityKeyData());
                    }
                }
                if (this.maxItemCount <= 0) {
                    this.pageInfo.hasDataItems.put(depth + 1, row.getEntityKeyData());
                    depthPageInfo.put(depth, depthList);
                    this.checkHasMore(entityTable, rows, index);
                    break;
                }
            }
            if (startToCheck) {
                if (!this.pageInfo.hasDataItems.containsValue(row.getEntityKeyData())) {
                    depthList.remove(row.getEntityKeyData());
                }
                startToCheck = false;
                this.needCheck.put(depth, false);
            }
            if ((childEntity = entityTable.getChildRows(row.getEntityKeyData())).size() > 0) {
                List<String> childDepthList = this.setHasDataEntitys(entityTable, childEntity, depth + 1, isShowNull, row);
                if (!parentAdd && childDepthList.size() > 0) {
                    depthList.add(row.getEntityKeyData());
                    this.pageInfo.hasDataItems.put(depth, row.getEntityKeyData());
                }
                if (this.maxItemCount <= 0) {
                    this.pageInfo.hasDataItems.put(depth + 1, row.getEntityKeyData());
                    if (parentRow == null) {
                        this.checkHasMore(entityTable, rows, index);
                        if (this.hasMore || this.block.isShowNullRow()) break;
                        this.block.setEnd(true);
                        break;
                    }
                    if (this.hasMore) break;
                    this.checkHasMore(entityTable, rows, index);
                    break;
                }
            }
            if (parentRow != null || index != rows.size()) continue;
            this.block.setEnd(true);
        }
        depthPageInfo = this.getDepthPageInfo();
        depthPageInfo.put(depth, depthList);
        this.pageInfo.dimensionRows.put(this.pageInfo.firstDimensionID, depthPageInfo);
        return depthList;
    }

    private boolean notShowNullOrZeroRow(IDataRow row1) {
        RowDataType rowDataType = this.checkRowData(row1);
        boolean notShowNull = !this.block.isShowNullRow() && rowDataType == RowDataType.ALLNULL;
        boolean notShowZero = !this.block.isShowZeroRow() && rowDataType == RowDataType.ALLZERO;
        return notShowNull || notShowZero;
    }

    private boolean hasWriteSub(int depth, String row) {
        if (this.pageInfo.lastPassedRows.containsKey(depth)) {
            List<String> keys = this.pageInfo.lastPassedRows.get(depth);
            boolean hasWrite = keys.contains(row);
            return hasWrite;
        }
        return false;
    }

    private void checkHasMore(IEntityTable entityTable, List<IEntityRow> rows, int index) {
        List<IEntityRow> others = rows.subList(index, rows.size());
        for (IEntityRow entityRow : others) {
            List childRows;
            if (!this.hasDetailRowCheck(entityRow, childRows = entityTable.getChildRows(entityRow.getEntityKeyData()), entityTable, null)) continue;
            this.hasMore = true;
            break;
        }
    }

    private void setPassedRows(int depth, IEntityRow row) {
        List<String> passRows;
        if (depth < 0) {
            return;
        }
        if (!this.pageInfo.passedRows.containsKey(depth)) {
            this.pageInfo.passedRows.put(depth, new ArrayList());
        }
        if (!(passRows = this.pageInfo.passedRows.get(depth)).contains(row.getEntityKeyData())) {
            passRows.add(row.getEntityKeyData());
        }
    }

    private void initFirstDimension() {
        if (this.gridType == GridType.ROW) {
            this.firstDimension = this.getFirstDimension(QueryDirection.ROWDIRECTION, this.rowDimensions);
        } else if (this.gridType == GridType.COL) {
            this.firstDimension = this.getFirstDimension(QueryDirection.COLDIRECTION, this.colDimensions);
        }
    }

    private void getFirstDimensionPageItems() {
        IEntityTable entityTable;
        if (this.firstDimension == null) {
            return;
        }
        if (this.pageInfo.firstDimensionID == null) {
            this.pageInfo.firstDimensionID = this.firstDimension.getViewId();
            this.pageInfo.dimensionName = this.firstDimension.getDimensionName();
        }
        String dimName = this.pageInfo.dimensionName;
        QueryDimensionDefine conditionDim = null;
        if (this.conditonDimensions != null) {
            conditionDim = this.conditonDimensions.get(this.firstDimension.getViewId());
        }
        EntityViewDefine entityView = QueryHelper.getEntityView(this.firstDimension.getViewId());
        DimensionValueSet dimValueSet = new DimensionValueSet();
        List<Object> rowsStr = new ArrayList();
        if (conditionDim != null && TableKind.TABLE_KIND_ENTITY_PERIOD != TableKind.valueOf((String)conditionDim.getTableKind())) {
            rowsStr = this.getEntityDataRows(this.firstDimension);
        }
        if (rowsStr.size() > 0) {
            dimValueSet.setValue(this.firstDimension.getDimensionName(), rowsStr);
        }
        if (this.periods != null && this.periods.size() > 0) {
            dimValueSet.setValue("DATATIME", (Object)this.periods.get(this.periods.size() - 1));
        }
        if ((entityTable = QueryHelper.getEntityTable(entityView, (DimensionValueSet)(dimValueSet.size() > 0 ? dimValueSet : null))) != null) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityView.getEntityId());
            ITreeStruct treeStruct = entityDefine.getTreeStruct();
            List rows = entityTable.getRootRows();
            boolean isShowNullRow = this.block.isShowNullRow();
            if (isShowNullRow) {
                this.nullEntityRowSize = this.getNullEntityRowSizeByFirstDim();
            }
            if (this.mode == QueryMode.SIMPLEQUERY && !this.isSinglePeriod && this.isSimpleQuery && this.block.isShowNullRow()) {
                if (this.dataTable.getTotalCount() > 15 || isShowNullRow) {
                    int depth = this.startDepth;
                    Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
                    for (Map.Entry<Integer, List<String>> entry : depthPageInfo.entrySet()) {
                        if (entry.getValue().size() != 1) continue;
                        this.needCheck.put(entry.getKey(), true);
                    }
                    this.setHasDataEntitys(entityTable, rows, depth, isShowNullRow, null);
                } else {
                    try {
                        Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
                        ArrayList<String> depthList = new ArrayList<String>();
                        LinkedHashMap entityRows = new LinkedHashMap();
                        for (int rowindex = 0; rowindex < this.dataTable.getTotalCount(); ++rowindex) {
                            IEntityRow entityRow;
                            IDataRow dataRow = this.dataTable.getItem(rowindex);
                            AbstractData data = dataRow.getValue(this.statictisFields.get(dimName));
                            if (entityRows.containsKey(data.getAsString()) || data.isNull || (entityRow = entityTable.findByEntityKey(data.getAsString())) == null || depthList.contains(data.getAsString())) continue;
                            depthList.add(data.getAsString());
                            String[] parents = entityRow.getParentsEntityKeyDataPath();
                            if (parents == null || parents.length <= 0) continue;
                            for (String pkey : parents) {
                                if (depthList.contains(pkey)) continue;
                                depthList.add(pkey);
                            }
                        }
                        depthPageInfo.put(-1, depthList);
                        this.pageInfo.dimensionRows.put(this.pageInfo.firstDimensionID, depthPageInfo);
                        this.block.setEnd(true);
                    }
                    catch (Exception ex) {
                        LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u7b2c\u4e00\u7ef4\u5ea6\u9879\u5206\u9875\u7ec4\u7ec7\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
                    }
                }
                this.totalCount = 500 - this.maxItemCount;
                return;
            }
            if (treeStruct != null && !StringUtils.isEmpty((String)treeStruct.getLevelCode()) || this.mode == QueryMode.SIMPLEQUERY) {
                if (this.pageInfo.strucNode == null || !this.isPaging) {
                    Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
                    List<Object> depthList = new ArrayList();
                    depthList = depthPageInfo.containsKey(-1) ? depthPageInfo.get(-1) : new ArrayList();
                    for (IEntityRow row : rows) {
                        List childRows;
                        if (this.hasDetailRowCheck(row, childRows = entityTable.getChildRows(row.getEntityKeyData()), entityTable, null)) {
                            depthList.add(row.getEntityKeyData());
                            continue;
                        }
                        if (!this.block.isShowNullRow()) continue;
                        depthList.add(row.getEntityKeyData());
                    }
                    depthPageInfo.put(-1, depthList);
                    this.pageInfo.dimensionRows.put(this.pageInfo.firstDimensionID, depthPageInfo);
                } else if (this.pageInfo.strucNode.length() > 0) {
                    Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
                    ArrayList<String> depthList = new ArrayList<String>();
                    DimensionValueSet dimNodeSet = new DimensionValueSet();
                    if (this.block.getBlockInfo().getNodeDimSet() != null) {
                        dimNodeSet.parseString(this.block.getBlockInfo().getNodeDimSet());
                    }
                    this.pageInfo.dimNodeSet = dimNodeSet;
                    String expandDimName = dimName;
                    for (int i = 0; i < dimNodeSet.size(); ++i) {
                        String name = dimNodeSet.getName(i);
                        String value = dimNodeSet.getValue(name).toString();
                        if (!this.pageInfo.strucNode.equals(value)) continue;
                        expandDimName = name;
                        break;
                    }
                    this.pageInfo.expandDimName = expandDimName;
                    depthList.add(this.pageInfo.strucNode);
                    depthPageInfo.put(this.pageInfo.parentDepth, depthList);
                    this.pageInfo.dimensionRows.put(this.pageInfo.firstDimensionID, depthPageInfo);
                }
                if (this.isSimpleQuery) {
                    this.setHasDataEntitys(entityTable, rows, this.startDepth, this.block.isShowNullRow(), null);
                } else {
                    this.block.setEnd(true);
                }
            } else if (this.dataTable.getTotalCount() > 15 || isShowNullRow) {
                int depth = this.startDepth;
                Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
                for (Map.Entry<Integer, List<String>> entry : depthPageInfo.entrySet()) {
                    if (entry.getValue().size() != 1) continue;
                    this.needCheck.put(entry.getKey(), true);
                }
                this.setHasDataEntitys(entityTable, rows, depth, isShowNullRow, null);
            } else {
                try {
                    Map<Integer, List<String>> depthPageInfo = this.getDepthPageInfo();
                    ArrayList<String> depthList = new ArrayList<String>();
                    LinkedHashMap entityRows = new LinkedHashMap();
                    for (int rowindex = 0; rowindex < this.dataTable.getTotalCount(); ++rowindex) {
                        IEntityRow entityRow;
                        IDataRow dataRow = this.dataTable.getItem(rowindex);
                        AbstractData data = dataRow.getValue(this.statictisFields.get(dimName));
                        if (entityRows.containsKey(data.getAsString()) || data.isNull || (entityRow = entityTable.findByEntityKey(data.getAsString())) == null || depthList.contains(data.getAsString())) continue;
                        depthList.add(data.getAsString());
                        String[] parents = entityRow.getParentsEntityKeyDataPath();
                        if (parents == null || parents.length <= 0) continue;
                        for (String pkey : parents) {
                            if (depthList.contains(pkey)) continue;
                            depthList.add(pkey);
                        }
                    }
                    depthPageInfo.put(-1, depthList);
                    this.pageInfo.dimensionRows.put(this.pageInfo.firstDimensionID, depthPageInfo);
                    this.block.setEnd(true);
                }
                catch (Exception ex) {
                    LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u7b2c\u4e00\u7ef4\u5ea6\u9879\u5206\u9875\u7ec4\u7ec7\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
                }
            }
        } else {
            this.block.setEnd(true);
        }
        this.totalCount = 500 - this.maxItemCount;
    }

    private void orderDetailRowsWithFistDim() {
        String[] masterKeys = this.block.getMasterKeyValue("masterKeys").split(";");
        if (masterKeys.length == 0) {
            return;
        }
        QueryDimensionDefine orderDim = null;
        String firstMasterKey = masterKeys[0];
        for (QueryDimensionDefine dimension : this.block.getQueryDimensions()) {
            if (dimension.getDimensionType() == QueryDimensionType.QDT_FIELD || dimension.getViewId() == null || !dimension.getViewId().equals(firstMasterKey)) continue;
            orderDim = dimension;
            break;
        }
        if (orderDim == null) {
            return;
        }
        String firstDimName = orderDim.getDimensionName();
        if (this.queryMasterKey != null && this.queryMasterKey.hasValue(firstDimName)) {
            List dimValues = (List)this.queryMasterKey.getValue(firstDimName);
            for (String key : dimValues) {
                DimensionValueSet rowKey = new DimensionValueSet();
                rowKey.setValue(firstDimName, (Object)key);
                List rows = this.dataTable.findFuzzyRows(rowKey);
                for (IDataRow row : rows) {
                    RowDataType valType = this.checkRowData(row);
                    if (!this.block.isShowNullRow() && valType == RowDataType.ALLNULL || !this.block.isShowZeroRow() && valType == RowDataType.ALLZERO) continue;
                    this.detailRows.add(row);
                }
            }
        }
    }

    private void init() {
        block37: {
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
            this.columnModelFinder = (IColumnModelFinder)BeanUtil.getBean(IColumnModelFinder.class);
            dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            dataAssist = dataAccessProvider.newDataAssist(new ExecutorContext(this.dataDefinitionRuntimeController));
            this.viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
            this.stateSevice = (IStateSevice)BeanUtil.getBean(StateServiceImpl.class);
            this.entityAuthorityProvider = (IEntityAuthorityService)BeanUtil.getBean(IEntityAuthorityService.class);
            queryEntityUtil = (QueryEntityUtil)BeanUtil.getBean(QueryEntityUtil.class);
            this.qdata = new QueryData();
            this.statictisFieldValues = new LinkedHashMap<String, List<String>>();
            this.helper = new QueryHelper();
            this.periodHelper = new PeriodHelper(this.block);
            this.warnDao = (IDataWarningDao)BeanUtil.getBean(DataWarningDaoImpl.class);
            this.warExecutor = new DataWarningExecutor();
            this.warnTable = new DataWarningTable();
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u7ed3\u6784\u521d\u59cb\u5316", (String)"\u521b\u5efa\u5e2e\u52a9\u7c7b\u5bf9\u8c61");
            try {
                this.executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                this.qContext = new QueryContext(this.executorContext, null);
                this.parser = dataAssist.createFormulaParser(false);
            }
            catch (ParseException ex) {
                logger.error(ex.getMessage(), ex);
            }
            QueryGridExtension gridExtension = new QueryGridExtension(this.block.getBlockExtension());
            String penetrationType = gridExtension.getPenetrationType();
            if (null != penetrationType) {
                if ("historicalDataQuery".equals(gridExtension.getPenetrationType())) {
                    this.isHistoryData = true;
                } else if ("dataPenetration".equals(gridExtension.getPenetrationType())) {
                    this.isdataPenetration = true;
                }
                if (!StringUtils.isEmpty((String)gridExtension.getDecimal())) {
                    this.decimal = gridExtension.getDecimal();
                    this.decimalVal = Integer.parseInt(this.decimal);
                }
            }
            boolean bl = this.isCustomInput = this.block.getBlockType() == QueryBlockType.QBT_CUSTOMENTRY;
            if (this.isCustomInput || this.isHistoryData) {
                this.mixShowDetail = true;
            }
            if (this.mixShowDetail) {
                this.block.getBlockInfo().setShowDetail(true);
                boolean peroidInCol = this.block.getQueryDimensions().stream().anyMatch(dim -> dim.isPeriodDim() && dim.getLayoutType() == QueryLayoutType.LYT_COL);
                if (peroidInCol) {
                    this.block.getBlockInfo().setFieldPosition(QueryFieldPosition.UP);
                }
                this.isQueryDetail = true;
            }
            this.dimStrucCurrentIndex = new LinkedHashMap<String, Map<String, Map<Integer, Integer>>>();
            this.ptype = PeriodType.valueOf((String)this.block.getPeriodTypeInCache());
            Optional<QueryDimensionDefine> periodOptional = this.block.getQueryDimensions().stream().filter(dim -> dim.isPeriodDim() && dim.getLayoutType() == QueryLayoutType.LYT_CONDITION).findFirst();
            try {
                if (periodOptional.isPresent() && this.mixShowDetail) {
                    QueryDimensionDefine periodCondition = periodOptional.get();
                    List<QuerySelectItem> selectItems = periodCondition.getSelectItems();
                    for (QuerySelectItem querySelectItem : selectItems) {
                        int period = QueryHelper.strToPeriod(querySelectItem.getCode());
                        if (period == this.ptype.type()) continue;
                        int type = QueryHelper.maxperiodType(period, this.ptype.type());
                        this.ptype = PeriodType.fromType((int)type);
                        String periodStr = querySelectItem.getCode();
                        char p2 = periodStr.charAt(4);
                        char c = PeriodUtil.convertType2Str((int)type).charAt(0);
                        String concat = periodStr.substring(0, 4).concat(String.valueOf(c)).concat("001");
                        if (p2 == c) continue;
                        querySelectItem.setCode(concat);
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u65f6\u671f\u7c7b\u578b\u6bd4\u8f83\u5f02\u5e38! " + e.getMessage());
            }
            this.statictisFields = new LinkedHashMap<String, FieldDefine>();
            this.fieldMapLink = new LinkedHashMap<String, DataLinkDefine>();
            this.enumFieldTable = new LinkedHashMap<String, IEntityTable>();
            this.gridFieldDimensions = new ArrayList<QueryDimensionDefine>();
            this.statisticsFieldMap = new HashMap<String, QuerySelectField>();
            this.simpleFieldMap = new HashMap<String, QuerySelectField>();
            this.gridType = GridType.DETAIL;
            this.setMasterKeys();
            this.setDimensions();
            this.setShowFields();
            this.setMasterDimension();
            SummaryScheme sumScheme = null;
            sumScheme = gridExtension.getSumSchemeObject();
            if (sumScheme != null) {
                this.reloadTreeInfo = new SummarySchemeUtils().toReloadTreeInfo(sumScheme);
            }
            this.queryCache = (QueryCacheManager)BeanUtil.getBean(QueryCacheManager.class);
            this.fieldIndex = new LinkedHashMap<String, Integer>();
            this.duplicateFieldValues = new LinkedHashMap<String, Map<String, String>>();
            this.fieldCodeAndValue = new LinkedHashMap<String, List<Object>>();
            this.fieldCodeAndValueWarnUse = new LinkedHashMap<String, List<Object>>();
            this.dimensionLinks = new HashMap<String, QueryDimensionDefine>();
            this.block.getQueryDimensions().forEach(dim -> {
                if (dim != null && dim.getLinkes() != null && dim.getDimensionName() != null) {
                    this.dimensionLinks.put(dim.getDimensionName(), (QueryDimensionDefine)dim);
                }
            });
            this.initFirstDimension();
            this.periods = this.periodHelper.getPeriodsForQuery();
            if (!this.onlyLoadForm) {
                try {
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
                    boolean bl2 = this.mode == QueryMode.SIMPLEQUERY;
                    this.dataTable = this.qdata.getData(this.block, this.gridType, bl2, this.periods);
                    this.fieldIndex = this.qdata.getFieldIndex();
                    boolean hasFieldSorted = this.qdata.hasSortedField;
                    this.customFieldIndex = this.qdata.customFieldIndex;
                    if (this.dataTable != null) {
                        this.isSinglePeriod = this.block.getQueryDimensions().stream().anyMatch(p -> p.getLayoutType().equals((Object)QueryLayoutType.LYT_ROW));
                        if (this.block.isShowNullRow() && !this.isSinglePeriod) {
                            this.nullRowDisplay();
                        }
                        int totalCount = this.dataTable.getTotalCount();
                        if (this.gridType == GridType.COL) {
                            this.block.getBlockInfo().setTotalCount(totalCount * this.showedFields.size());
                        } else if (this.gridType == GridType.ROW || this.gridType == GridType.DETAIL) {
                            this.block.getBlockInfo().setTotalCount(totalCount);
                            if (this.gridType == GridType.DETAIL && !hasFieldSorted) {
                                this.orderDetailRowsWithFistDim();
                            }
                        }
                        if (this.gridType == GridType.ROW && bl2 && totalCount > 0 && this.detailRows.size() == 0) {
                            int detailCount = 0;
                            for (int j = 0; j < totalCount; ++j) {
                                IDataRow next;
                                IDataRow dateRow = this.dataTable.getItem(j);
                                if (!this.isAutoGather && dateRow.getGroupingFlag() >= 0) continue;
                                if (this.isAutoGather && dateRow.getGroupingFlag() >= 0 && (next = this.dataTable.getItem(j + 1)).getGroupingFlag() < 0) {
                                    ++detailCount;
                                    ++j;
                                    continue;
                                }
                                RowDataType valType = this.checkRowData(dateRow);
                                if (!this.block.isShowNullRow() && valType == RowDataType.ALLNULL || !this.block.isShowZeroRow() && valType == RowDataType.ALLZERO) continue;
                                ++detailCount;
                            }
                            this.block.getBlockInfo().setTotalCount(this.block.getBlockInfo().isShowSum() ? detailCount + 1 : detailCount);
                        }
                        if (totalCount > 0) {
                            this.totalRow = this.dataTable.getItem(0);
                        }
                        this.initGridFieldFormDataTable(warList);
                        this.handlerWarningList(warList);
                        if (this.gridType == GridType.RAC) {
                            this.setDimVMap2();
                        }
                    }
                    String[] masterKeys = this.block.getMasterKeyValue("masterKeys").split(";");
                    Optional<QueryDimensionDefine> firstDim = this.block.getQueryDimensions().stream().filter(p -> p.getDimensionType() != QueryDimensionType.QDT_FIELD && p.getViewId() != null && p.getViewId().equals(masterKeys[0])).findFirst();
                    if (firstDim.isPresent()) {
                        for (String viewId : masterKeys) {
                            this.entityViewDefine = QueryHelper.getEntityView(viewId);
                            if (queryEntityUtil.getEntityTablelKindByView(viewId) == TableKind.TABLE_KIND_ENTITY_PERIOD) continue;
                            this.dimName = dataAssist.getDimensionName(this.entityViewDefine);
                            break;
                        }
                    }
                    this.queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
                    this.queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
                    if (CollectionUtils.isEmpty(this.periods)) break block37;
                    String period1 = this.periods.get(0);
                    String period2 = this.periods.get(this.periods.size() - 1);
                    Date[] dates = new Date[2];
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        PeriodWrapper periodWrapper1 = new PeriodWrapper(period1);
                        PeriodWrapper periodWrapper2 = new PeriodWrapper(period2);
                        String[] timesArr = PeriodUtil.getTimesArr((PeriodWrapper)periodWrapper1, (PeriodWrapper)periodWrapper2);
                        if (timesArr != null) {
                            dates[0] = simpleDateFormat.parse(timesArr[0]);
                            dates[1] = simpleDateFormat.parse(timesArr[1]);
                            this.queryVersionStartDate = dates[0];
                            this.queryVersionEndDate = dates[1];
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
                catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            } else {
                this.block.setEnd(true);
            }
        }
        this.reloadDimData();
    }

    public void nullRowDisplay() {
        String[] masterKeys = this.block.getMasterKeyValue("masterKeys").split(";");
        if (masterKeys.length == 0) {
            return;
        }
        QueryDimensionDefine orderDim = null;
        String firstMasterKey = masterKeys[0];
        for (QueryDimensionDefine dimension : this.block.getQueryDimensions()) {
            if (dimension.getDimensionType() == QueryDimensionType.QDT_FIELD || !dimension.getViewId().equals(firstMasterKey)) continue;
            orderDim = dimension;
            break;
        }
        if (orderDim == null) {
            return;
        }
        if (!this.isSinglePeriod && this.block.getBlockInfo().isShowNullRow() && this.rowDimensions == null) {
            this.rowDimensions = new ArrayList<QueryDimensionDefine>();
            QueryDimensionDefine dimx = orderDim.clone();
            dimx.setLayoutType(QueryLayoutType.LYT_ROW);
            this.rowDimensions.add(dimx);
            if (this.firstDimension == null) {
                this.firstDimension = dimx;
            }
            return;
        }
    }

    public void reloadDimData() {
        try {
            Map.Entry tailByReflection;
            this.stateCache = null;
            this.isPaging = this.block.isPaging();
            if (this.gridType == GridType.RAC) {
                this.isPaging = false;
            }
            if (this.mode == QueryMode.SIMPLEQUERY) {
                this.isSimpleQuery = true;
            }
            this.isSinglePeriod = this.block.getQueryDimensions().stream().anyMatch(p -> p.getLayoutType().equals((Object)QueryLayoutType.LYT_ROW));
            if (this.isPaging && this.dataTable != null && (this.gridType != GridType.DETAIL || !this.isSinglePeriod && this.block.isShowNullRow()) && this.gridType != GridType.RAC) {
                Object tempc;
                this.blockKey = this.block.getId();
                this.userKey = QueryHelper.getCacheKey(NpContextHolder.getContext().getTenant(), NpContextHolder.getContext().getUserId());
                if (QueryType.NEXTPAGE == this.block.getQueryType() && (tempc = this.queryCache.getCache(this.userKey, this.blockKey, "PAGEINFO")) != null) {
                    this.pageInfo = (DimensionPageLoadInfo)tempc;
                }
                this.pageInfo.parentDepth = this.block.getBlockInfo().getDepth();
                this.pageInfo.strucNode = this.block.getBlockInfo().getDimValue();
                if (this.pageInfo.dimensionRows.size() > 0) {
                    this.pageInfo.isFirstPage = false;
                    LinkedHashMap<Integer, List<String>> map = new LinkedHashMap<Integer, List<String>>();
                    for (Map.Entry<Integer, List<String>> entry : this.pageInfo.passedRows.entrySet()) {
                        ArrayList list = new ArrayList();
                        list.addAll(entry.getValue());
                        map.put(entry.getKey(), list);
                    }
                    this.pageInfo.lastPassedRows = map;
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    if (this.pageInfo.lastDimValueSetStr != null) {
                        dimensionValueSet.parseString(this.pageInfo.lastDimValueSetStr);
                        this.pageInfo.lastDimValueSet = dimensionValueSet;
                    }
                }
                this.getFirstDimensionPageItems();
            }
            if (!this.isPaging) {
                this.getFirstDimensionPageItems();
            }
            loadItemParams params = new loadItemParams();
            params.depth = 0;
            params.isFirstLoad = true;
            params.isRememberLoadIndex = true;
            params.dimensionIndex = 0;
            if (this.isCustomInput) {
                String FIELDFLOATORDER = "FLOATORDER";
                List<QuerySelectField> selectFieldsInBlock = QueryHelper.getSelectFieldsInBlock(this.block);
                List tables = selectFieldsInBlock.stream().filter(item -> !Boolean.parseBoolean(item.getIsMaster())).map(QuerySelectField::getTableKey).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<String>(Comparator.comparing(item -> item))), ArrayList::new));
                if (tables.size() > 1) {
                    this.hasMoreRegion = true;
                }
                for (String string : tables) {
                    Optional<QuerySelectField> first;
                    TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(string);
                    if (tableDefine != null) {
                        Optional<QuerySelectField> first2;
                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(FIELDFLOATORDER, tableDefine.getKey());
                        if (fieldDefine != null && this.floatOrderField.contains(fieldDefine)) {
                            this.floatOrderField.add(fieldDefine);
                        } else {
                            ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableDefine.getKey(), FIELDFLOATORDER);
                            if (columnModelDefine != null && (fieldDefine = this.columnModelFinder.findFieldDefine(columnModelDefine)) != null && this.floatOrderField.contains(fieldDefine)) {
                                this.floatOrderField.add(fieldDefine);
                            }
                        }
                        if (!this.hasMoreRegion || !(first2 = selectFieldsInBlock.stream().filter(i -> table.equals(i.getTableName())).findFirst()).isPresent()) continue;
                        QuerySelectField querySelectField = first2.get();
                        this.floatOrderMap.put(querySelectField.getRegionKey(), fieldDefine != null ? fieldDefine.getKey() : null);
                        continue;
                    }
                    TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(string);
                    if (tableModelDefine == null) continue;
                    FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(FIELDFLOATORDER, tableModelDefine.getID());
                    if (fieldDefine != null && this.floatOrderField.contains(fieldDefine)) {
                        this.floatOrderField.add(fieldDefine);
                    } else {
                        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), FIELDFLOATORDER);
                        if (columnModelDefine != null && (fieldDefine = this.columnModelFinder.findFieldDefine(columnModelDefine)) != null && this.floatOrderField.contains(fieldDefine)) {
                            this.floatOrderField.add(fieldDefine);
                        }
                    }
                    if (!this.hasMoreRegion || !(first = selectFieldsInBlock.stream().filter(i -> table.equals(i.getTableName())).findFirst()).isPresent()) continue;
                    QuerySelectField querySelectField = first.get();
                    this.floatOrderMap.put(querySelectField.getRegionKey(), fieldDefine != null ? fieldDefine.getKey() : null);
                }
                if (this.block.getBlockInfo().getCustomEntryInsertRows() != null) {
                    DimensionValueSet dimensionValueSet;
                    String dimValue;
                    for (Map.Entry entry : this.block.getBlockInfo().getCustomEntryInsertRows().entrySet()) {
                        Integer n = (Integer)entry.getValue();
                        dimValue = (String)entry.getKey();
                        dimensionValueSet = new DimensionValueSet();
                        dimensionValueSet.parseString(dimValue);
                        this.customEntryInsertRows.put(dimensionValueSet, n);
                    }
                    Map<String, List<String>> insertRowBizKey = this.block.getBlockInfo().getInsertRowBizKey();
                    if (insertRowBizKey != null) {
                        for (Map.Entry entry : insertRowBizKey.entrySet()) {
                            dimValue = (String)entry.getKey();
                            dimensionValueSet = new DimensionValueSet();
                            dimensionValueSet.parseString(dimValue);
                            List values = (List)entry.getValue();
                            ArrayList<DimensionValueSet> bizs = new ArrayList<DimensionValueSet>();
                            for (String value : values) {
                                DimensionValueSet bizSet = new DimensionValueSet();
                                bizSet.parseString(value);
                                bizs.add(bizSet);
                            }
                            this.insertRowsBiz.put(dimensionValueSet, bizs);
                        }
                    }
                }
            }
            if (!this.isSinglePeriod && this.block.getBlockInfo().isShowNullRow() && this.rowDimensions == null && this.isSimpleQuery) {
                this.rowDimensions = new ArrayList<QueryDimensionDefine>();
                if (this.firstDimension != null) {
                    QueryDimensionDefine dimx = this.firstDimension.clone();
                    this.rowDimensions.add(dimx);
                }
            }
            if (this.rowDimensions != null) {
                params.isPagingLoad = (this.gridType == GridType.RAC || this.gridType == GridType.ROW) && this.block.getPageDirection() == 0 && !this.isExport;
                this.structRowCount = 0;
                params.dimSize = this.rowDimensions.size();
                params.lasteDimensionIndex = this.rowLastDimensionIndex;
                if (this.block.getBlockInfo().isTranspose()) {
                    params.loadKey = this.DIMENSION_DIRECTION_COL;
                    this.cols = this.initStructTree(this.rowDimensions, params, null).getChildItems();
                } else {
                    params.loadKey = this.DIMENSION_DIRECTION_ROW;
                    this.rows = this.initStructTree(this.rowDimensions, params, null).getChildItems();
                }
            }
            this.structRowCount = 0;
            if (this.colDimensions != null) {
                params.isPagingLoad = (this.gridType == GridType.RAC || this.gridType == GridType.COL) && this.block.getPageDirection() == 0 && !this.isExport;
                params.dimSize = this.colDimensions.size();
                params.lasteDimensionIndex = this.colLastDimensionIndex;
                if (this.block.getBlockInfo().isTranspose()) {
                    params.loadKey = this.DIMENSION_DIRECTION_ROW;
                    this.rows = this.initStructTree(this.colDimensions, params, null).getChildItems();
                } else {
                    params.loadKey = this.DIMENSION_DIRECTION_COL;
                    this.cols = this.initStructTree(this.colDimensions, params, null).getChildItems();
                }
            }
            if ((tailByReflection = this.getTailByReflection((LinkedHashMap)this.pageInfo.recordIndex)) != null) {
                String lastItem;
                this.pageInfo.lastItem = lastItem = (String)tailByReflection.getKey();
                int endDepth = Integer.MAX_VALUE;
                for (Map.Entry<Integer, String> entry : this.pageInfo.hasDataItems.entrySet()) {
                    if (entry.getValue() == null || !entry.getValue().equals(lastItem)) continue;
                    endDepth = entry.getKey();
                    break;
                }
                Map<Integer, List<String>> map = this.pageInfo.dimensionRows.get(this.pageInfo.firstDimensionID);
                for (Integer n : map.keySet()) {
                    if (n <= endDepth - 1) continue;
                    map.get(n).removeAll((Collection)map.get(n));
                }
            }
            this.queryCache.setItem(this.userKey, this.blockKey, "PAGEINFO", this.pageInfo);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public boolean checkUploaded(DimensionValueSet dimensionValueSet) {
        try {
            QueryGridExtension gridExtension;
            String penetrationType;
            boolean isdataPenetration;
            if (this.stateCache == null) {
                this.stateCache = new HashMap(50);
            }
            boolean bl = isdataPenetration = null != (penetrationType = (gridExtension = new QueryGridExtension(this.block.getBlockExtension())).getPenetrationType()) && "dataPenetration".equals(gridExtension.getPenetrationType());
            if (dimensionValueSet == null || dimensionValueSet.size() == 0) {
                return true;
            }
            if (this.block.getBlockInfo().getBlockType() != QueryBlockType.QBT_CUSTOMENTRY && !isdataPenetration && !this.isHistoryData) {
                return true;
            }
            String formSchemeKey = this.block.getBlockInfo().getFormSchemeKey();
            if (StringUtil.isNullOrEmpty((String)formSchemeKey)) {
                return true;
            }
            StateEntites stateEntites = new StateEntites();
            stateEntites.setDims(dimensionValueSet);
            stateEntites.setFormSchemeKey(formSchemeKey);
            stateEntites.setUserId(NpContextHolder.getContext().getUserId());
            Map<DimensionValueSet, StateConst> stateInfo = null;
            if (this.stateCache.containsKey(dimensionValueSet)) {
                stateInfo = this.stateCache.get(dimensionValueSet);
            } else {
                stateInfo = this.stateSevice.getStateInfo(stateEntites);
                this.stateCache.put(dimensionValueSet, stateInfo);
            }
            if (null != stateInfo) {
                for (Map.Entry<DimensionValueSet, StateConst> entry : stateInfo.entrySet()) {
                    StateConst state = entry.getValue();
                    if (!state.equals((Object)StateConst.ENDFILL) && !state.equals((Object)StateConst.ENDFILLICON)) continue;
                    return true;
                }
            }
            DataEntryParam dataEntryParam = new DataEntryParam();
            dataEntryParam.setFormSchemeKey(formSchemeKey);
            dataEntryParam.setDim(dimensionValueSet);
            ActionStateBean state = this.dataentryFlowService.queryUnitState(dataEntryParam);
            return !UploadState.ORIGINAL.toString().equals(state.getCode()) && !UploadState.ORIGINAL_UPLOAD.toString().equals(state.getCode()) && !UploadState.ORIGINAL_SUBMIT.toString().equals(state.getCode()) && !UploadState.REJECTED.toString().equals(state.getCode()) && !UploadState.RETURNED.toString().equals(state.getCode());
        }
        catch (Exception ex) {
            LogHelper.debug((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u68c0\u67e5\u4e0a\u62a5\u72b6\u6001\u5f02\u5e38", (String)("\u5f02\u5e38\u4fe1\u606f\uff1a" + ex.getMessage()));
            return false;
        }
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
            logger.error(e.getMessage(), e);
        }
        return expression;
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

    public void handlerWarningList(List<DataWarningDefine> warList) {
        ArrayList<FieldDefine> fieldList = new ArrayList<FieldDefine>();
        for (String fieldCode : this.fieldDefines.keySet()) {
            FieldDefine field = this.fieldDefines.get(fieldCode);
            if (!warList.stream().anyMatch(a -> fieldCode.equals(a.getFieldCode()))) continue;
            fieldList.add(field);
        }
        this.warExecutor.setShowedFieldsIndex(this.showedFieldsIndex);
        this.warExecutor.setField(fieldList);
        this.warExecutor.setFieldValues(this.fieldCodeAndValueWarnUse);
        this.warExecutor.setWarnigItems(warList);
        this.warnTable = this.warExecutor.execute(this.dataTable, this.fieldIndex);
    }

    public <K, V> Map.Entry<K, V> getTailByReflection(LinkedHashMap<K, V> map) {
        Field tail = null;
        try {
            tail = map.getClass().getDeclaredField("tail");
            ReflectionUtils.makeAccessible(tail);
            return (Map.Entry)tail.get(map);
        }
        catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
        }
        catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private void setDimVMap2() {
        if (this.dataTable == null) {
            return;
        }
        try {
            List<QueryDimensionDefine> dimensions = this.block.getBlockInfo().getQueryDimensions();
            ArrayList<String> rowDimNames = new ArrayList<String>();
            ArrayList<String> colDimNames = new ArrayList<String>();
            int index = 0;
            for (QueryDimensionDefine dimension : dimensions) {
                if (dimension.getDimensionType() == QueryDimensionType.QDT_FIELD || dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION) continue;
                if (dimension.getLayoutType() == QueryLayoutType.LYT_ROW) {
                    rowDimNames.add(dimension.getDimensionName());
                    ++index;
                }
                if (dimension.getLayoutType() != QueryLayoutType.LYT_COL) continue;
                colDimNames.add(dimension.getDimensionName());
                ++index;
            }
            List dimVList = this.dataTable.getGroupingDimensionValues();
            for (DimensionValueSet dimV : dimVList) {
                String[] dimVStr;
                if (dimV == null) continue;
                List dataRow = this.dataTable.findDetailRowsByGroupKey(dimV);
                dataRow.removeIf(row -> this.checkRowData((IDataRow)row) == RowDataType.ALLNULL);
                if (dataRow == null || dataRow.isEmpty() || (dimVStr = dimV.toString().split(",")).length != index) continue;
                String rowCombDimV = "";
                String colCombDimV = "";
                for (int i = 0; i < dimVStr.length; ++i) {
                    String colCombName;
                    ArrayList<String> nameStr;
                    String vStr = dimVStr[i].trim();
                    if (!this.dimVMap.containsKey(vStr)) {
                        this.dimVMap.put(vStr, dimV);
                    }
                    if (rowDimNames.contains((nameStr = new ArrayList<String>(Arrays.asList(vStr.split("=")))).get(0))) {
                        rowCombDimV = rowCombDimV + vStr + ", ";
                    }
                    if (colDimNames.contains(nameStr.get(0))) {
                        colCombDimV = colCombDimV + vStr + ", ";
                    }
                    if (i != dimVStr.length - 1) continue;
                    String rowCombName = rowCombDimV.isEmpty() ? "" : rowCombDimV.substring(0, rowCombDimV.length() - 2);
                    String string = colCombName = colCombDimV.isEmpty() ? "" : colCombDimV.substring(0, colCombDimV.length() - 2);
                    if (!rowCombName.isEmpty() && !this.dimVMap.containsKey(rowCombName)) {
                        this.dimVMap.put(rowCombName, dimV);
                    }
                    if (colCombName.isEmpty() || this.dimVMap.containsKey(colCombName)) continue;
                    this.dimVMap.put(colCombName, dimV);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u4ea4\u53c9\u8868\u521d\u59cb\u5316\u660e\u7ec6\u6570\u636e\u884c\u51fa\u9519: " + e.getMessage());
        }
    }

    private String handlerRowValue(QuerySelectField field, AbstractData value, IDataRow data) throws Exception {
        String showText = null;
        String fieldCode = field.getCode();
        String strValue = value.getAsString();
        String dimCode = this.fieldDimNames.get(fieldCode);
        if (Boolean.parseBoolean(field.getIsMaster()) || this.enumFieldTable.containsKey(dimCode)) {
            IEntityTable table = this.enumFieldTable.get(dimCode != null ? dimCode : fieldCode);
            IEntityTable iEntityTable = table = table != null ? table : this.enumFieldTable.get(fieldCode);
            if (!"DATATIME".equals(dimCode)) {
                if (!value.isNull) {
                    if (value.getAsString() == "\u2014\u2014") {
                        showText = value.getAsString();
                    } else {
                        FieldDefine fieldDef = this.fieldDefines.get(fieldCode);
                        if (table != null && !StringUtils.isEmpty((String)fieldDef.getEntityKey())) {
                            IEntityRow entity = table.findByEntityKey(strValue);
                            IEntityRow iEntityRow = entity = entity == null ? table.findByCode(strValue) : entity;
                            if (entity != null) {
                                DataLinkDefine linkDefine = this.fieldMapLink.get(fieldCode);
                                String rowCaption = "";
                                if (linkDefine != null && !StringUtils.isEmpty((String)linkDefine.getCaptionFieldsString())) {
                                    String[] fields = linkDefine.getCaptionFieldsString().split(";");
                                    for (int i = 0; i < fields.length; ++i) {
                                        String fieldKey = fields[i];
                                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                                        if (fieldDefine == null) continue;
                                        com.jiuqi.nr.entity.engine.data.AbstractData dataValue = entity.getValue(fieldDefine.getCode());
                                        if (rowCaption.length() > 0) {
                                            rowCaption = rowCaption + "|";
                                        }
                                        rowCaption = rowCaption + dataValue.getAsString();
                                    }
                                } else if (entity != null) {
                                    rowCaption = entity.getTitle();
                                }
                                if (entity != null) {
                                    showText = rowCaption;
                                }
                            } else {
                                showText = strValue;
                            }
                        } else {
                            showText = strValue;
                        }
                    }
                }
            } else if (value.isNull) {
                Object code = data.getRowKeys().getValue(dimCode);
                if (code != null) {
                    PeriodWrapper pw = PeriodUtil.getPeriodWrapper((String)code.toString());
                    showText = strValue = pw.toTitleString();
                }
            } else if (value.getAsString() == "\u2014\u2014") {
                showText = " ";
            } else {
                String valueStr = value.getAsString();
                PeriodWrapper pw = PeriodUtil.getPeriodWrapper((String)valueStr);
                showText = strValue = pw.toTitleString();
            }
        } else if (!value.isNull) {
            String val = value.getAsString();
            int digits = 2;
            FieldDefine fieldDefine = null;
            if (this.fieldDefines.containsKey(fieldCode)) {
                fieldDefine = this.fieldDefines.get(fieldCode);
                digits = fieldDefine.getFractionDigits();
            }
            if (this.decimalVal != null) {
                digits = this.decimalVal;
            }
            if ("\u2014\u2014".equals(val)) {
                showText = null;
            } else {
                String valStr = value.getAsString();
                showText = fieldDefine != null ? QueryGridDefination.getValue(fieldDefine, valStr, digits) : QueryGridDefination.getValue(field, valStr, digits);
            }
        } else {
            showText = "";
            showText = "";
        }
        return showText;
    }

    public static String getValue(QuerySelectField curent, String valStr, int digits) {
        if (valStr == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)valStr)) {
            return valStr;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + QueryHelper.getDigits(digits));
        switch (curent.getFiledType()) {
            case FIELD_TYPE_DECIMAL: {
                BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                BigDecimal deciValue = decimalValue.setScale(digits, 4);
                return decimalFormat.format(deciValue);
            }
            case FIELD_TYPE_FLOAT: {
                double floatValue = Convert.toDouble((String)valStr);
                Double dbVal = Round.callFunction((Number)floatValue, (int)digits);
                return decimalFormat.format(dbVal);
            }
            case FIELD_TYPE_INTEGER: {
                Integer iVal = Convert.toInt((String)valStr);
                return decimalFormat.format(iVal);
            }
        }
        return valStr;
    }

    public static String getValue(FieldDefine curent, String valStr, int digits) {
        if (valStr == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)valStr)) {
            return valStr;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + QueryHelper.getDigits(digits));
        switch (curent.getType()) {
            case FIELD_TYPE_DECIMAL: {
                BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                BigDecimal deciValue = decimalValue.setScale(digits, 4);
                return decimalFormat.format(deciValue);
            }
            case FIELD_TYPE_FLOAT: {
                double floatValue = Convert.toDouble((String)valStr);
                Double dbVal = Round.callFunction((Number)floatValue, (int)digits);
                return decimalFormat.format(dbVal);
            }
            case FIELD_TYPE_INTEGER: {
                Integer iVal = Convert.toInt((String)valStr);
                return decimalFormat.format(iVal);
            }
        }
        return valStr;
    }

    private void initGridFieldFormDataTable(List<DataWarningDefine> warnList) {
        try {
            if (this.gridFieldDimensions == null) {
                return;
            }
            boolean endLoop = false;
            for (int i = 0; i < this.dataTable.getTotalCount() && !endLoop; ++i) {
                IDataRow row = this.dataTable.getItem(i);
                ++this.detailsCount;
                RowDataType rowDataType = this.checkRowData(row);
                if (!this.block.isShowNullRow() && rowDataType == RowDataType.ALLNULL && warnList.isEmpty() || !this.block.isShowZeroRow() && rowDataType == RowDataType.ALLZERO && warnList.isEmpty()) continue;
                Map<Object, Object> fieldValues = new LinkedHashMap();
                List<Object> valueList = new ArrayList();
                List<Object> warnValueList = new ArrayList();
                for (int f = 0; f < this.showedFields.size(); ++f) {
                    Object value;
                    int dataIndex;
                    QuerySelectField selectField = this.showedFields.get(f);
                    String fieldKey = selectField.getCode();
                    FieldDefine item = null;
                    if (Boolean.parseBoolean(selectField.getIsMaster())) {
                        String tableName = selectField.getTableName();
                        Optional<QuerySelectField> first = this.showedFields.stream().filter(e -> !Boolean.parseBoolean(e.getIsMaster())).findFirst();
                        if (first.isPresent() && (tableName = first.get().getTableName()) == null && first.get().getCustom() && selectField.getDataSheet() != null) {
                            tableName = selectField.getDataSheet();
                        }
                        TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(selectField.getTableKey());
                        item = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(selectField.getFileExtension(), tableDefine.getKey());
                        if ("DW".equals(selectField.getFileExtension()) && item == null) {
                            item = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", tableDefine.getKey());
                        }
                    } else {
                        item = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                    }
                    String dimName = this.fieldDimNames.get(fieldKey);
                    boolean isPeriod = "DATATIME".equals(dimName);
                    fieldValues = this.duplicateFieldValues.containsKey(fieldKey) ? this.duplicateFieldValues.get(fieldKey) : new LinkedHashMap();
                    int n = dataIndex = this.fieldIndex.containsKey(fieldKey) ? this.fieldIndex.get(fieldKey) : -1;
                    if (dataIndex < 0 || dataIndex >= this.fieldIndex.size()) continue;
                    if (valueList.size() <= this.MaxSortItemCount) {
                        if (this.block.isShowZeroRow() || rowDataType != RowDataType.ALLZERO) {
                            valueList = this.fieldCodeAndValue.containsKey(fieldKey) ? this.fieldCodeAndValue.get(fieldKey) : new ArrayList();
                        }
                    } else if (warnList.isEmpty()) {
                        endLoop = true;
                        continue;
                    }
                    warnValueList = this.fieldCodeAndValueWarnUse.containsKey(fieldKey) ? this.fieldCodeAndValueWarnUse.get(fieldKey) : new ArrayList();
                    AbstractData val = row.getValue(dataIndex);
                    String showText = this.handlerRowValue(selectField, val, row);
                    String warnShowText = val.getAsString();
                    if (row.getGroupingFlag() < 0) {
                        if (item != null && QueryHelper.isNumField(item) || selectField.getCustom() && QueryHelper.isNumField(selectField)) {
                            value = 0.0;
                            if (showText != "" && showText != null) {
                                try {
                                    value = Double.valueOf(showText);
                                }
                                catch (Exception ex) {
                                    logger.debug(ex.getMessage());
                                    value = val.getAsCurrency().doubleValue();
                                }
                            }
                            if (this.block.isShowZeroRow() || rowDataType != RowDataType.ALLZERO) {
                                valueList.add(value);
                            }
                            warnValueList.add(value);
                        } else {
                            if (this.block.isShowZeroRow() || rowDataType != RowDataType.ALLZERO) {
                                valueList.add(showText);
                            }
                            warnValueList.add(warnShowText);
                        }
                    }
                    if (valueList.size() <= this.MaxSortItemCount && (this.block.isShowZeroRow() || rowDataType != RowDataType.ALLZERO)) {
                        this.fieldCodeAndValue.put(fieldKey, valueList);
                    }
                    this.fieldCodeAndValueWarnUse.put(fieldKey, warnValueList);
                    if (val.isNull && !isPeriod || fieldValues.containsValue(showText) || showText == "" || showText == null || row.getGroupingFlag() >= 0) continue;
                    value = val.getAsString();
                    if (val.dataType == 1) {
                        Object object = value = val.getAsBool() ? "1" : "0";
                    }
                    if (isPeriod) {
                        value = ((DataRowImpl)row).getKeyValue(dimName).toString();
                    }
                    fieldValues.put(value, showText);
                    this.duplicateFieldValues.put(fieldKey, fieldValues);
                }
                for (int j = 0; j < this.gridFieldDimensions.size(); ++j) {
                    QueryDimensionDefine fieldDimension = this.gridFieldDimensions.get(j);
                    List<Object> values = new ArrayList();
                    String dimName = this.fieldDimNames.get(fieldDimension.getViewId());
                    if (this.statictisFieldValues.containsKey(dimName)) {
                        values = this.statictisFieldValues.get(dimName);
                    }
                    if (this.statictisFields.containsKey(dimName)) {
                        FieldDefine field = this.statictisFields.get(dimName);
                        int dataIndex = this.fieldIndex.get(field.getKey());
                        String val = row.getAsString(dataIndex);
                        if (val == null || values.contains(val)) continue;
                        values.add(val);
                    }
                    this.statictisFieldValues.put(dimName, values);
                }
            }
            this.statisticCount = this.dataTable.getTotalCount() - this.detailsCount;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void getFieldDimension() {
        Optional<QueryDimensionDefine> dimenOptional = this.block.getQueryDimensions().stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst();
        if (dimenOptional.isPresent()) {
            this.fieldDimension = dimenOptional.get();
        }
    }

    private void setFieldDimension() {
        if (this.fieldDimension != null) {
            if (this.fieldDimension.getLayoutType() == QueryLayoutType.LYT_ROW || this.block.getBlockInfo().getQueryDirection() == QueryDirection.ROWDIRECTION) {
                if (this.rowDimensions == null) {
                    this.rowDimensions = new ArrayList<QueryDimensionDefine>();
                }
                this.rowDimensions.add(this.fieldDimension);
                this.fieldDimensionIndex = this.rowDimensions.size() - 1;
                this.fieldDirection = QueryDirection.ROWDIRECTION;
            } else {
                if (this.colDimensions == null) {
                    this.colDimensions = new ArrayList<QueryDimensionDefine>();
                }
                this.colDimensions.add(this.fieldDimension);
                this.fieldDimensionIndex = this.colDimensions.size() - 1;
                this.fieldDirection = QueryDirection.COLDIRECTION;
            }
        }
    }

    public void setDimensions() {
        boolean isFieldInCol;
        boolean rowHasMasterDim = false;
        this.getFieldDimension();
        boolean bl = isFieldInCol = this.fieldDimension.getLayoutType() == QueryLayoutType.LYT_COL;
        if (this.mode == QueryMode.SIMPLEQUERY && isFieldInCol) {
            Optional<QueryDimensionDefine> dimenOptional = this.block.getQueryDimensions().stream().filter(idx -> (idx.getDimensionType() == QueryDimensionType.QDT_ENTITY || idx.getDimensionType() == QueryDimensionType.QDT_DICTIONARY) && idx.getLayoutType() == QueryLayoutType.LYT_ROW).findFirst();
            Optional<QueryDimensionDefine> dimConOptional = this.block.getQueryDimensions().stream().filter(idx -> (idx.getDimensionType() == QueryDimensionType.QDT_ENTITY || idx.getDimensionType() == QueryDimensionType.QDT_DICTIONARY) && idx.getLayoutType() == QueryLayoutType.LYT_CONDITION && idx.getTableKind() != null && TableKind.TABLE_KIND_ENTITY == TableKind.valueOf((String)idx.getTableKind()) && !idx.getSelectItems().isEmpty()).findFirst();
            if (dimenOptional.isPresent()) {
                this.gridType = GridType.ROW;
                QueryDimensionDefine rowdimension = dimenOptional.get();
                this.hasMasterDimension = this.Masterkeys.contains(rowdimension.getViewId());
                if (this.rowDimensions == null) {
                    this.rowDimensions = new ArrayList<QueryDimensionDefine>();
                }
                this.rowDimensions.add(rowdimension);
            }
            if (this.colDimensions == null) {
                this.colDimensions = new ArrayList<QueryDimensionDefine>();
            }
            this.colDimensions.add(this.fieldDimension);
            this.isQueryDetail = true;
            if (dimConOptional.isPresent()) {
                if (this.conditonDimensions == null) {
                    this.conditonDimensions = new LinkedHashMap<String, QueryDimensionDefine>();
                }
                this.conditonDimensions.put(dimConOptional.get().getViewId(), dimConOptional.get());
            }
            return;
        }
        if (this.block.getFieldPosition() == QueryFieldPosition.UP) {
            this.setFieldDimension();
        }
        for (QueryDimensionDefine dimension : this.block.getQueryDimensions()) {
            if (dimension == null) continue;
            if (dimension.isHidden()) {
                if (dimension.getLayoutType() != QueryLayoutType.LYT_CONDITION || this.block.getBlockType() != QueryBlockType.QBT_CUSTOMENTRY || !dimension.isPeriodDim()) continue;
                this.curPeriod = dimension.getSelectItems().get(0).getCode();
                continue;
            }
            if (dimension.getDimensionType() == QueryDimensionType.QDT_FIELD) continue;
            this.hasMasterDimension = this.Masterkeys.contains(dimension.getViewId());
            if (dimension.getLayoutType() == QueryLayoutType.LYT_ROW) {
                if (this.rowDimensions == null) {
                    this.rowDimensions = new ArrayList<QueryDimensionDefine>();
                }
                this.gridType = this.gridType != null && this.gridType == GridType.COL || this.gridType == GridType.RAC ? GridType.RAC : GridType.ROW;
                this.rowDimensions.add(dimension);
                if (!rowHasMasterDim) {
                    rowHasMasterDim = this.Masterkeys.contains(dimension.getViewId());
                }
                if (dimension.getDimensionType() == QueryDimensionType.QDT_GRIDFIELD) {
                    this.gridFieldDimensions.add(dimension);
                    this.rowLastDimensionIndex = this.rowDimensions.size() - 1;
                    continue;
                }
                this.rowLastDimensionIndex = this.rowDimensions.size() - 1;
                continue;
            }
            if (dimension.getLayoutType() == QueryLayoutType.LYT_COL) {
                if (this.colDimensions == null) {
                    this.colDimensions = new ArrayList<QueryDimensionDefine>();
                }
                this.colDimensions.add(dimension);
                this.gridType = this.gridType != null && this.gridType == GridType.ROW || this.gridType == GridType.RAC ? GridType.RAC : GridType.COL;
                if (dimension.getDimensionType() == QueryDimensionType.QDT_GRIDFIELD) {
                    this.gridFieldDimensions.add(dimension);
                    continue;
                }
                this.colLastDimensionIndex = this.colDimensions.size() - 1;
                continue;
            }
            if (dimension.getLayoutType() != QueryLayoutType.LYT_CONDITION) continue;
            if (this.conditonDimensions == null) {
                this.conditonDimensions = new LinkedHashMap<String, QueryDimensionDefine>();
            }
            this.conditonDimensions.put(dimension.getViewId(), dimension);
        }
        if (this.block.getFieldPosition() == QueryFieldPosition.DOWN) {
            this.setFieldDimension();
        }
        this.isQueryDetail = this.gridType != GridType.RAC && this.block.isShowDetail();
    }

    private void setMasterKeys() {
        this.Masterkeys = Arrays.asList(this.block.getQueryMasterKeys().split(";"));
    }

    void setMasterDimension() {
        for (int i = 0; i < this.Masterkeys.size(); ++i) {
            IPeriodProvider periodProvider;
            String dimName;
            FieldDefine field;
            String viewId = this.Masterkeys.get(i);
            EntityViewDefine entityView = QueryHelper.getEntityView(viewId);
            if (entityView == null || (field = this.statictisFields.get(dimName = QueryHelper.getDimName(entityView))) == null) continue;
            IEntityTable entityTable = null;
            if (queryEntityUtil.getEntityTablelKindByView(viewId) != TableKind.TABLE_KIND_ENTITY_PERIOD) {
                entityTable = QueryHelper.getEntityTable(entityView);
                this.enumFieldTable.put(dimName, entityTable);
                continue;
            }
            this.periodDimName = dimName;
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(viewId);
            this.customPeriodTable = periodProvider = this.periodEntityAdapter.getPeriodProvider(entityView.getEntityId());
        }
    }

    private void setShowFields() {
        try {
            List<QuerySelectField> selectedFields = QueryHelper.getSelectFieldsInBlock(this.block);
            this.isAutoGather = this.helper.autoGatherCheck(selectedFields, this.isQueryDetail);
            this.showedFieldsIndex = new LinkedHashMap<String, Integer>();
            this.showedFields = new ArrayList<QuerySelectField>();
            this.hasNtAuthFields = new ArrayList<String>();
            this.hasNtWriteAuthFields = new ArrayList<String>();
            this.fieldDimNames = new LinkedHashMap<String, String>();
            this.fieldDefines = new LinkedHashMap<String, FieldDefine>();
            FieldSelectHelper fieldHelper = new FieldSelectHelper();
            this.dimNameMasterMap = new HashMap<String, Boolean>();
            int index = 0;
            LinkedHashMap<String, List<DataLinkDefine>> allLinkesInRegion = new LinkedHashMap<String, List<DataLinkDefine>>();
            for (QuerySelectField field : selectedFields) {
                List<DataLinkDefine> linkDefines;
                String regionKey;
                if (!this.hasFormulaWithPeriodShift) {
                    this.hasFormulaWithPeriodShift = field.getStatisticsFields() != null && !field.getStatisticsFields().isEmpty();
                }
                String code = field.getCode();
                FieldDefine fieldDefineOrigin = null;
                ColumnModelDefine columnModelDefine = null;
                String fieldKey = null;
                try {
                    if (field.getTableName() != null && field.getDataSheet() != null) {
                        TableDefine tableDefine = null;
                        TableModelDefine tableModelDefine = null;
                        tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(field.getTableKey());
                        if (tableDefine != null) {
                            fieldDefineOrigin = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(field.getFileExtension(), tableDefine.getKey());
                            if ("DW".equals(field.getFileExtension()) && fieldDefineOrigin == null && (fieldDefineOrigin = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("MDCODE", tableDefine.getKey())) != null) {
                                fieldKey = fieldDefineOrigin.getKey();
                            }
                        } else {
                            tableModelDefine = this.dataModelService.getTableModelDefineById(field.getTableKey());
                            columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), field.getFileExtension());
                            if ("DW".equals(field.getFileExtension()) && columnModelDefine == null && (columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "MDCODE")) != null) {
                                fieldKey = columnModelDefine.getID();
                            }
                        }
                    } else {
                        fieldKey = code;
                    }
                }
                catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
                if (!field.getCustom()) {
                    if (!fieldHelper.checkFieldAuth(fieldKey)) {
                        this.hasNtAuthFields.add(code);
                    }
                    if (!fieldHelper.checkFieldWriteAuth(fieldKey) || "true".equals(field.getIsMaster())) {
                        this.hasNtWriteAuthFields.add(code);
                    }
                }
                if (field.getCustom() && !this.block.getIsDataSet() && !field.isHidden()) {
                    if (!this.hasFormulaWithPeriodShift) {
                        try {
                            IExpression expression = this.parser.parseEval(field.getCode(), (IContext)this.qContext);
                            if (expression != null) {
                                QueryFields qfield = ExpressionUtils.getQueryFields((IASTNode)expression);
                                qfield.forEach(field1 -> {
                                    PeriodModifier modifier = field1.getPeriodModifier();
                                    if (modifier != null) {
                                        this.hasFormulaWithPeriodShift = true;
                                    }
                                });
                            }
                        }
                        catch (Exception expression) {
                            // empty catch block
                        }
                    }
                    this.showedFields.add(field);
                    this.showedFieldsIndex.put(code, index);
                    ++index;
                    continue;
                }
                if (field.getRegionKey() != null && !"".equals(field.getRegionKey()) && DataRegionKind.DATA_REGION_SIMPLE == field.getRegionKind() && field.getIsMergeSameCell()) {
                    this.simpleFieldMap.put(field.getCode(), field);
                    if (field.getStatisticsFields() != null && !field.getStatisticsFields().isEmpty()) {
                        for (QueryStatisticsItem sf : field.getStatisticsFields()) {
                            QuerySelectField statisticeField = new QuerySelectField();
                            statisticeField.setTitle(this.getStatisticsFieldName(sf.getBuiltIn()));
                            statisticeField.setCode(sf.getFormulaExpression());
                            statisticeField.setIsMaster(field.getIsMaster());
                            this.simpleFieldMap.put(statisticeField.getCode(), statisticeField);
                        }
                    }
                }
                FieldDefine fieldDefine = null;
                ColumnModelDefine columnModel = null;
                String dimName = null;
                if (!field.getCustom()) {
                    if (Boolean.parseBoolean(field.getIsMaster()) && field.getTableName() != null && field.getDataSheet() != null) {
                        Object tableName = field.getTableName();
                        Optional<QuerySelectField> first = this.block.getQueryDimensions().get(0).getSelectFields().stream().filter(e -> !Boolean.parseBoolean(e.getIsMaster())).findFirst();
                        if (first.isPresent() && (tableName = first.get().getDataSheet()) == null && first.get().getCustom() && field.getDataSheet() != null) {
                            tableName = field.getDataSheet();
                        }
                        if (tableName != null) {
                            TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(field.getTableKey());
                            if (tableDefine != null) {
                                fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(field.getFileExtension(), tableDefine.getKey());
                                if (fieldDefine == null && (columnModel = this.dataModelService.getColumnModelDefineByCode(tableDefine.getKey(), field.getFileExtension())) != null) {
                                    fieldDefine = this.columnModelFinder.findFieldDefine(columnModel);
                                }
                                if ("DW".equals(field.getFileExtension()) && fieldDefine == null) {
                                    columnModel = this.dataModelService.getColumnModelDefineByCode(tableDefine.getKey(), "MDCODE");
                                    fieldDefine = this.columnModelFinder.findFieldDefine(columnModel);
                                }
                            } else {
                                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(field.getTableKey());
                                if (tableModelDefine != null) {
                                    fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(field.getFileExtension(), tableModelDefine.getID());
                                    if (fieldDefine == null && (columnModel = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), field.getFileExtension())) != null) {
                                        fieldDefine = this.columnModelFinder.findFieldDefine(columnModel);
                                    }
                                    if ("DW".equals(field.getFileExtension()) && fieldDefine == null) {
                                        columnModel = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "MDCODE");
                                        fieldDefine = this.columnModelFinder.findFieldDefine(columnModel);
                                    }
                                }
                            }
                        } else {
                            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(code);
                            if (fieldDefine == null && (columnModel = this.dataModelService.getColumnModelDefineByID(code)) != null) {
                                fieldDefine = this.columnModelFinder.findFieldDefine(columnModel);
                            }
                        }
                    } else {
                        fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(code);
                        if (fieldDefine == null && (columnModel = this.dataModelService.getColumnModelDefineByID(code)) != null) {
                            fieldDefine = this.columnModelFinder.findFieldDefine(columnModel);
                        }
                    }
                    dimName = dataAssist.getDimensionName(fieldDefine);
                }
                if (!StringUtils.isEmpty(dimName)) {
                    if (field.getStatisticsFields() != null && !field.getStatisticsFields().isEmpty()) {
                        for (QueryStatisticsItem statField : field.getStatisticsFields()) {
                            this.fieldDimNames.put(statField.getFormulaExpression(), dimName);
                        }
                    }
                    this.fieldDimNames.put(code, dimName);
                    if (!this.dimNameMasterMap.containsKey(dimName)) {
                        this.dimNameMasterMap.put(dimName, Boolean.parseBoolean(field.getIsMaster()));
                    }
                    if (!this.statictisFields.containsKey(dimName)) {
                        this.statictisFields.put(dimName, fieldDefine);
                    }
                } else {
                    this.fieldDimNames.put(code, code);
                }
                this.fieldDefines.put(code, fieldDefine);
                if (!field.isHidden()) {
                    this.showedFields.add(field);
                    this.showedFieldsIndex.put(code, index);
                    ++index;
                }
                if (this.fieldMapLink.containsKey(code) || (regionKey = field.getRegionKey()) == null) continue;
                if (allLinkesInRegion.containsKey(regionKey)) {
                    linkDefines = (List<DataLinkDefine>)allLinkesInRegion.get(regionKey);
                } else {
                    linkDefines = this.helper.getAllLinkDefinesInRegion(regionKey);
                    allLinkesInRegion.put(regionKey, linkDefines);
                }
                for (DataLinkDefine linkDefine : linkDefines) {
                    IEntityTable et;
                    FieldDefine fieldDefine1;
                    if (!linkDefine.getLinkExpression().equals(code)) continue;
                    this.fieldMapLink.put(code, linkDefine);
                    if (linkDefine.getLinkExpression() == null || linkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || (fieldDefine1 = this.dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression())).getEntityKey() == null || (et = QueryHelper.getEntityTable(fieldDefine1.getEntityKey())) == null) continue;
                    this.enumFieldTable.put(dimName, et);
                }
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private List<QueryDimItem> getFieldItems(int depth) {
        ArrayList<QueryDimItem> items = new ArrayList<QueryDimItem>();
        for (int j = 0; j < this.showedFields.size(); ++j) {
            QuerySelectField field = this.showedFields.get(j);
            QueryDimItem item = QueryDimItem.newItem(field.getTitle(), field.getCode(), depth, 0, DimensionItemType.FIELD);
            List<QueryStatisticsItem> staticsFields = field.getStatisticsFields();
            if (staticsFields != null && staticsFields.size() > 0) {
                item.setItemType(DimensionItemType.FIELDTEXT);
                ArrayList<QueryDimItem> childItems = new ArrayList<QueryDimItem>();
                QueryDimItem itemOwn = QueryDimItem.newItem("\u672c\u671f", field.getCode(), depth, 0, DimensionItemType.FIELD);
                itemOwn.setDataIndex(this.fieldIndex.get(field.getCode()));
                childItems.add(itemOwn);
                for (int k = 0; k < staticsFields.size(); ++k) {
                    QueryStatisticsItem staticField = staticsFields.get(k);
                    QueryDimItem staticItem = QueryDimItem.newItem(this.getStatisticsFieldName(staticField.getBuiltIn()), staticField.getFormulaExpression(), 0, 0, DimensionItemType.FIELD);
                    staticItem.setDataIndex(this.fieldIndex.get(staticField.getFormulaExpression()));
                    childItems.add(staticItem);
                }
                item.setChildItems(childItems);
            } else {
                item.setDataIndex(this.fieldIndex.get(field.getCode()));
            }
            items.add(item);
        }
        return items;
    }

    private QueryDimItem createSubTotalItem(QueryDimItem parentItem, int depth, QueryDimensionDefine dimension) {
        List<Object> childItems = new ArrayList<QueryDimItem>();
        try {
            QueryDirection direction;
            QueryDimItem sumItem = parentItem.clone();
            sumItem.setShowTitle("\u5c0f\u8ba1");
            sumItem.setIsSubTotalItem(true);
            sumItem.setItemType(DimensionItemType.SUBTOTALROW);
            sumItem.setChildItems(null);
            sumItem.setDetailRows(null);
            QueryDirection queryDirection = direction = dimension.getLayoutType() == QueryLayoutType.LYT_ROW ? QueryDirection.ROWDIRECTION : QueryDirection.COLDIRECTION;
            if (this.block.getFieldPosition() == QueryFieldPosition.DOWN && (this.fieldDimension.getLayoutType() == dimension.getLayoutType() || this.block.getBlockInfo().getQueryDirection() == direction)) {
                sumItem.setChildItems(this.getFieldItems(depth));
                sumItem.setChildItemhasField(true);
            }
            List<QueryDimItem> pc = parentItem.getChildItems();
            if (dimension.isPreposeSum()) {
                childItems.add(sumItem);
                if (pc != null) {
                    childItems.addAll(pc);
                }
            } else {
                childItems = pc;
                if (childItems == null) {
                    childItems = new ArrayList();
                }
                childItems.add(sumItem);
            }
            parentItem.setChildItems(childItems);
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u751f\u6210\u5c0f\u8ba1\u7ef4\u5ea6\u9879\u65f6\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
        }
        return parentItem;
    }

    private List<String> getPageItems(String viewId, int depth) {
        Map<Object, Object> pageItems = new LinkedHashMap();
        if (viewId.equals(this.pageInfo.firstDimensionID)) {
            pageItems = this.pageInfo.dimensionRows.get(viewId);
        }
        List allItems = null;
        if (pageItems != null) {
            allItems = pageItems.size() == 1 && pageItems.containsKey(-1) ? (List)pageItems.get(-1) : (List)pageItems.get(depth);
        }
        if (allItems == null) {
            allItems = new ArrayList();
        }
        return allItems;
    }

    private List<String> getStructPageItems(String viewId, int depth) {
        Map<Object, Object> pageItems = new LinkedHashMap();
        if (viewId.equals(this.pageInfo.firstDimensionID) && !this.pageInfo.dimensionRows.isEmpty()) {
            pageItems = this.pageInfo.dimensionRows.get(viewId);
        }
        List allItems = null;
        if (pageItems.size() == 1 && pageItems.containsKey(-1)) {
            allItems = (List)pageItems.get(-1);
        }
        if (this.isSimpleQuery) {
            allItems = (ArrayList)pageItems.get(depth);
        }
        if (allItems == null) {
            allItems = new ArrayList();
        }
        return allItems;
    }

    private void setPageLastItem(String viewId, int depth, List<String> items) {
        Map<Integer, List<String>> pageItems = new LinkedHashMap<Integer, List<String>>();
        if (viewId.equals(this.pageInfo.firstDimensionID)) {
            pageItems = this.pageInfo.dimensionRows.get(viewId);
        }
        pageItems.put(depth, items);
        this.pageInfo.dimensionRows.put(viewId, pageItems);
        this.pageInfo.parentDepth = depth;
    }

    public String getStatisticsFieldName(String builtIn) {
        String name = null;
        switch (builtIn) {
            case "YOY": {
                name = "\u540c\u6bd4";
                break;
            }
            case "MOM": {
                name = "\u73af\u6bd4";
                break;
            }
            case "PREPERIOD": {
                name = "\u4e0a\u671f\u6570";
                break;
            }
            case "PREYEAR": {
                name = "\u4e0a\u5e74\u540c\u671f";
                break;
            }
        }
        return name;
    }

    /*
     * Unable to fully structure code
     */
    private QueryDimItem initStructTree(List<QueryDimensionDefine> dimensions, loadItemParams params, QueryDimItem parentItem) {
        if (parentItem == null) {
            parentItem = new QueryDimItem();
            parentItem.setIsNotTreeStruct(false);
        }
        items = new ArrayList<QueryDimItem>();
        dimensionRecordsCount = 0;
        if (dimensions == null || dimensions.size() == 0) {
            return parentItem;
        }
        loadOtherDimension = params.dimensionIndex < params.dimSize - 1;
        try {
            isLastDimension = params.getIsLasteDimension();
            dataDim = params.parentDimValue == null ? new DimensionValueSet() : new DimensionValueSet(params.parentDimValue);
            dimension = dimensions.get(params.dimensionIndex);
            dimType = dimension.getDimensionType();
            masterKeys = this.block.getQueryMasterKeys();
            viewId = dimension.getViewId();
            isMaster = masterKeys.indexOf(viewId + ";") >= 0;
            childHasField = false;
            v0 = direction = dimension.getLayoutType() == QueryLayoutType.LYT_ROW ? QueryDirection.ROWDIRECTION : QueryDirection.COLDIRECTION;
            if (this.block.getFieldPosition() == QueryFieldPosition.DOWN && (this.fieldDimension.getLayoutType() == dimension.getLayoutType() || this.block.getBlockInfo().getQueryDirection() == direction) && params.dimensionIndex + 1 == this.fieldDimensionIndex) {
                childHasField = true;
            }
            switch (1.$SwitchMap$com$jiuqi$nr$query$block$QueryDimensionType[dimType.ordinal()]) {
                case 1: {
                    if (!this.isFieldInCol) {
                        this.isFieldInCol = params.loadKey == this.DIMENSION_DIRECTION_COL;
                    }
                    for (j = 0; j < this.showedFields.size(); ++j) {
                        field = this.showedFields.get(j);
                        fieldCode = field.getCode();
                        isMasterField = Boolean.parseBoolean(field.getIsMaster());
                        item = QueryDimItem.newItem(field.getTitle(), field.getCode(), params.depth, 0, DimensionItemType.FIELD);
                        item.setDimensionName(this.fieldDimNames.get(fieldCode));
                        staticsFields = field.getStatisticsFields();
                        if (staticsFields != null && staticsFields.size() > 0) {
                            item.setItemType(DimensionItemType.FIELD);
                            childItems = new ArrayList<QueryDimItem>();
                            itemOwn = QueryDimItem.newItem("\u672c\u671f", fieldCode, params.depth, 0, DimensionItemType.FIELD);
                            itemOwn.setDataIndex(this.fieldIndex.get(fieldCode));
                            item.setIsMaster(isMasterField);
                            if (loadOtherDimension) {
                                temp = params.clone();
                                ++temp.dimensionIndex;
                                temp.depth = this.hasTreeStructDimension ? ++temp.depth : 0;
                                itemOwn = this.loadChildItem(dimensions, temp, itemOwn);
                            }
                            childItems.add(itemOwn);
                            this.statisticsFieldMap.put(fieldCode, field);
                            for (k = 0; k < staticsFields.size(); ++k) {
                                staticField = staticsFields.get(k);
                                staticItem = QueryDimItem.newItem(staticField.getTitle(), staticField.getFormulaExpression(), 0, 0, DimensionItemType.FIELD);
                                staticItem.setDataIndex(this.fieldIndex.get(staticField.getFormulaExpression()));
                                staticItem.setIsMaster(isMasterField);
                                this.statisticsFieldMap.put(staticItem.getEditTitle(), field);
                                if (loadOtherDimension) {
                                    temp = params.clone();
                                    ++temp.dimensionIndex;
                                    temp.depth = this.hasTreeStructDimension ? ++temp.depth : 0;
                                    staticItem = this.loadChildItem(dimensions, temp, staticItem);
                                }
                                if (this.hasNtAuthFields.contains(fieldCode)) {
                                    this.hasNtAuthFields.add(staticItem.getEditTitle());
                                }
                                childItems.add(staticItem);
                            }
                            item.setChildItems(childItems);
                        } else {
                            if (this.fieldIndex.containsKey(fieldCode)) {
                                item.setDataIndex(this.fieldIndex.get(fieldCode));
                            }
                            item.setIsMaster(isMasterField);
                            if (loadOtherDimension) {
                                temp = params.clone();
                                ++temp.dimensionIndex;
                                temp.depth = this.hasTreeStructDimension ? ++temp.depth : 0;
                                item = this.loadChildItem(dimensions, temp, item);
                            }
                        }
                        items.add(item);
                    }
                    parentItem.setChildItems(items);
                    return parentItem;
                }
                case 2: 
                case 3: {
                    entityTable = null;
                    conditionDim = null;
                    if (this.conditonDimensions != null) {
                        conditionDim = this.conditonDimensions.get(dimension.getViewId());
                    }
                    if (this.entityDimensionData == null || !this.entityDimensionData.containsKey(dimension.getViewId())) {
                        if (this.entityDimensionData == null) {
                            this.entityDimensionData = new LinkedHashMap<String, EntityCache>();
                        }
                        entityView = QueryHelper.getEntityView(dimension.getViewId());
                        dimValueSet = new DimensionValueSet();
                        rowsStr = null;
                        if (conditionDim != null && TableKind.TABLE_KIND_ENTITY_PERIOD != TableKind.valueOf((String)conditionDim.getTableKind()) && (rowsStr = this.getEntityDataRows(dimension)).isEmpty() && conditionDim.getSelectItems().isEmpty()) {
                            rowsStr = null;
                        }
                        if (rowsStr != null) {
                            dimValueSet.setValue(dimension.getDimensionName(), rowsStr);
                        }
                        if (this.periods != null && this.periods.size() > 0) {
                            dimValueSet.setValue(this.periodDimName, (Object)this.periods.get(this.periods.size() - 1));
                        }
                        entityTable = QueryHelper.getEntityTable(entityView, dimValueSet.size() == 0 ? null : dimValueSet);
                        cache = new EntityCache();
                        cache.entityTable = entityTable;
                        cache.entityView = entityView;
                        this.entityDimensionData.put(dimension.getViewId(), cache);
                    } else {
                        cache = this.entityDimensionData.get(dimension.getViewId());
                        entityTable = cache.entityTable;
                        entityView = cache.entityView;
                    }
                    periodView = this.periodEntityAdapter.isPeriodEntity(dimension.getViewId());
                    dimName = QueryHelper.getDimName(entityView);
                    if (!this.enumFieldTable.containsKey(dimName)) {
                        this.enumFieldTable.put(dimName, entityTable);
                    }
                    entityParams = new InitCacheParams();
                    entityParams.dimName = dimName;
                    entityParams.depth = params.depth;
                    entityParams.isRememberLoadIndex = params.isRememberLoadIndex;
                    entityParams.needChild = true;
                    entityParams.isShowSum = dimension.isShowSum();
                    entityParams.isSumRowInFront = dimension.isPreposeSum();
                    entityParams.isShowTotalSum = this.block.isShowSum();
                    entityParams.isMaster = isMaster;
                    if (this.gridType == GridType.RAC && !this.mixShowDetail) {
                        entityParams.isShowSum = false;
                        if (dimension.isShowSum() && dimension.isPreposeSum()) {
                            sumItem = new QueryDimItem();
                            sumItem.setShowTitle("\u5c0f\u8ba1");
                            sumItem.setIsSubTotalItem(true);
                            sumItem.setItemType(DimensionItemType.SUBTOTALROW);
                            sumItem.setDimensionName(dimName);
                            sumItem.setChildItems(null);
                            sumItem.setDetailRows(null);
                            sumItem.setChildItemhasField(childHasField);
                            sumItem.setDepth(params.depth);
                            items.add(sumItem);
                        }
                    }
                    if (!periodView) ** GOTO lbl186
                    periods = QueryHelper.getPeriodByDim(this.block, dimension, conditionDim, this.ptype, this.block.getTaskDefStartPeriod(), this.block.getTaskDefEndPeriod(), this.customPeriodTable);
                    for (String period : periods.keySet()) {
                        periodItem = QueryDimItem.newItem(periods.get(period), period, entityParams.depth, 0, DimensionItemType.FIELD);
                        periodItem.setIsMaster(isMaster);
                        periodItem.setDimensionName(dimName);
                        periodItem.setItemType(DimensionItemType.PERIODENTITY);
                        periodItem = this.setDataRows(dataDim, dimName, period, periodItem, isLastDimension, parentItem);
                        if (periodItem.getDetailRows() != null && periodItem.getDetailRows().size() > 0) {
                            parentItem.setChildHasDetailRow(true);
                        }
                        if ((periodItem.getDetailRows() == null || periodItem.getDetailRows().size() == 0) && this.block.isShowNullRow()) {
                            periodItem.setChildDataSize(1);
                            periodItem.setOwnDataSize(1);
                            rows = new ArrayList<IDataRow>();
                            nullRow = new DataRowImpl((ReadonlyTableImpl)this.dataTable, null, null);
                            rows.add((IDataRow)nullRow);
                            periodItem.setDetailRows(rows);
                        }
                        periodItem.setIsShowSubTotal(entityParams.isShowSum);
                        v1 = hasWriteSubTotal = this.pageInfo.lastDimValueSet != null && this.pageInfo.lastDimValueSet.isSubsetOf(dataDim) != false || this.pageInfo.recordStart.containsKey(parentItem.getEditTitle()) != false && this.pageInfo.recordStart.get(parentItem.getEditTitle()) != 0;
                        if (hasWriteSubTotal) {
                            periodItem.setIsShowSubTotal(false);
                            if (this.pageInfo.lastDimensionName != null) {
                                lastdim = new DimensionValueSet();
                                lastdim.parseString(this.pageInfo.lastDimensionName);
                                periodDim = new DimensionValueSet();
                                periodDim.parseString(periodItem.getDimensionValueSet());
                                if (lastdim.isSubsetOf(periodDim) && this.pageInfo.lastDimensionRecord.get(this.pageInfo.lastDimensionName) < periodItem.totalSize || periodItem.isFinish) {
                                    entityDimName = parentItem.getEditTitle();
                                    total = this.pageInfo.recordIndex.get(entityDimName) - this.pageInfo.recordStart.get(entityDimName);
                                    if (periodItem.isFirst) {
                                        periodItem.setIsShowSubTotal(true);
                                    }
                                }
                            }
                            if (this.pageInfo.lastDimensionName != null || periodItem.isFinish) {
                                this.lastItemNotEnd = true;
                            }
                            this.lastItemIndex = periodItem.itemPageStart;
                        }
                        periodItem.setIsSubTotalInFront(entityParams.isSumRowInFront);
                        if (loadOtherDimension) {
                            temp = params.clone();
                            ++temp.dimensionIndex;
                            temp.depth = this.hasTreeStructDimension ? ++temp.depth : 0;
                            periodItem = this.loadChildItem(dimensions, temp, periodItem);
                        }
                        if (dimension.isShowSum() && this.gridType != GridType.RAC) {
                            depth = this.hasTreeStructDimension != false ? params.depth + 1 : 0;
                            this.createSubTotalItem(periodItem, depth, dimension);
                        }
                        periodItem.setChildItemhasField(childHasField);
                        items.add(periodItem);
                        if (!periodItem.isPageEnd() && !this.onlyLoadForm) continue;
                        ** GOTO lbl244
                    }
                    ** GOTO lbl244
lbl186:
                    // 1 sources

                    entityDefine = this.entityMetaService.queryEntity(entityView.getEntityId());
                    treeStruct = null;
                    if (entityDefine.getTreeStruct() != null) {
                        treeStruct = entityDefine.getTreeStruct().getLevelCode();
                    }
                    isNotTreeStruct = StringUtils.isEmpty(treeStruct);
                    if (dimension.getLayoutType() == QueryLayoutType.LYT_COL && !isNotTreeStruct || this.gridType == GridType.RAC) {
                        isNotTreeStruct = true;
                    }
                    if (this.pageInfo.firstDimensionID != null) {
                        cache = this.entityDimensionData.get(this.pageInfo.firstDimensionID);
                        if (cache != null) {
                            struct = QueryGridDefination.queryEntityUtil.getDicTreeStructByView(cache.entityView.getEntityId());
                            isNotTreeStruct = StringUtils.isEmpty((String)struct);
                            treeStructByView = QueryGridDefination.queryEntityUtil.getDicTreeStructByView(cache.entityView.getEntityId());
                            isNotTreeStruct = StringUtils.isEmpty((String)treeStructByView);
                        } else {
                            isNotTreeStruct = true;
                        }
                    }
                    if (dimension.getLayoutType() == QueryLayoutType.LYT_COL && !isNotTreeStruct || this.gridType == GridType.RAC || this.isExport) {
                        isNotTreeStruct = true;
                    }
                    if (this.mode == QueryMode.SIMPLEQUERY) {
                        isNotTreeStruct = false;
                    }
                    isTree = isNotTreeStruct;
                    if (isNotTreeStruct) {
                        isTree = false;
                    } else if (!this.hasTreeStructDimension) {
                        this.hasTreeStructDimension = true;
                    }
                    entityParams.isTree = isTree;
                    entityParams.isNotTreeStruct = isNotTreeStruct;
                    entityParams.isMaster = isMaster;
                    entityParams.itemType = dimType == QueryDimensionType.QDT_ENTITY ? DimensionItemType.MASTERENTITY : DimensionItemType.ENTITY;
                    rootRows = entityTable.getRootRows();
                    if (!isNotTreeStruct) {
                        if (!StringUtils.isEmpty((String)this.pageInfo.strucNode) && this.pageInfo.parentDepth != 0) {
                            params.depth = this.pageInfo.parentDepth;
                        }
                        pageItems = this.getStructPageItems(dimension.getViewId(), params.depth);
                        if (this.pageInfo.expandDimName != null && this.pageInfo.dimNodeSet != null && this.pageInfo.dimNodeSet.hasValue(dimName)) {
                            expandDim = this.pageInfo.dimNodeSet.getValue(dimension.getDimensionName()).toString();
                            pageItems.add(expandDim);
                        }
                        if (pageItems.size() == 0 && this.pageInfo.dimensionName != null && !dimension.getDimensionName().equals(this.pageInfo.dimensionName) || !this.isPaging) {
                            rows = entityTable.getRootRows();
                            pageItems = rows.stream().map((Function<IEntityRow, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getEntityKeyData(), (Lcom/jiuqi/nr/entity/engine/intf/IEntityRow;)Ljava/lang/String;)()).collect(Collectors.toList());
                            entityParams.needChild = true;
                        }
                        this.isTreeStruct = true;
                        if (parentItem != null && !StringUtils.isEmpty((String)parentItem.getEditTitle())) {
                            this.isTreeStruct = parentItem.getIsNotTreeStruct() == false;
                            entityParams.isNotTreeStruct = parentItem.getIsNotTreeStruct();
                        }
                        if (!this.isTreeLoad) {
                            pageItems = this.getPageItems(dimension.getViewId(), params.depth);
                        }
                        chileHasDetailRow = this.isTreeLoad == false || this.isSimpleQuery != false ? this.loadEntityItems(dimensions, rootRows, items, entityTable, entityParams, params, parentItem) : this.loadStrucEntityItems(dimensions, pageItems, items, entityTable, entityParams, params, parentItem);
                        if (this.childHasDetailRow) {
                            parentItem.setChildHasDetailRow(chileHasDetailRow);
                        }
                    } else {
                        rows = entityTable.getRootRows();
                        entityParams.needChild = true;
                        chileHasDetailRow = this.loadEntityItems(dimensions, rows, items, entityTable, entityParams, params, parentItem);
                        if (this.childHasDetailRow) {
                            parentItem.setChildHasDetailRow(chileHasDetailRow);
                        }
                    }
lbl244:
                    // 6 sources

                    if (this.gridType == GridType.RAC && dimension.isShowSum() && !dimension.isPreposeSum()) {
                        sumItem = new QueryDimItem();
                        sumItem.setShowTitle("\u5c0f\u8ba1");
                        sumItem.setIsSubTotalItem(true);
                        sumItem.setItemType(DimensionItemType.SUBTOTALROW);
                        sumItem.setDimensionName(dimName);
                        sumItem.setChildItems(null);
                        sumItem.setDetailRows(null);
                        sumItem.setDepth(params.depth);
                        sumItem.setChildItemhasField(childHasField);
                        items.add(sumItem);
                    }
                    parentItem.setChildItems(items);
                    return parentItem;
                }
                case 4: {
                    fieldCode = dimension.getViewId();
                    if (this.onlyLoadForm) {
                        parentItem.setChildItems(items);
                        return parentItem;
                    }
                    pageItems = this.getStructPageItems(dimension.getViewId(), params.depth);
                    dimensionName = dimension.getDimensionName();
                    if (this.pageInfo.expandDimName != null && this.pageInfo.dimNodeSet != null && this.pageInfo.dimNodeSet.hasValue(dimensionName)) {
                        expandDim = this.pageInfo.dimNodeSet.getValue(dimension.getDimensionName()).toString();
                        pageItems.add(expandDim);
                    }
                    isNotTreeStruct = true;
                    if (this.pageInfo.firstDimensionID != null && this.entityDimensionData != null && (cache = this.entityDimensionData.get(this.pageInfo.firstDimensionID)) != null) {
                        struct = QueryGridDefination.queryEntityUtil.getDicTreeStructByView(cache.entityView.getEntityId());
                        isNotTreeStruct = StringUtils.isEmpty((String)struct);
                        parentItem.setIsNotTreeStruct(isNotTreeStruct);
                    }
                    if (this.fieldDefines.containsKey(fieldCode)) {
                        field = this.fieldDefines.get(fieldCode);
                        fieldDimName = QueryGridDefination.dataAssist.getDimensionName(field);
                        if (this.statictisFieldValues.containsKey(fieldDimName)) {
                            if (this.gridType == GridType.RAC && dimension.isShowSum() && dimension.isPreposeSum()) {
                                sumItem = new QueryDimItem();
                                sumItem.setDimensionName(dimension.getDimensionName());
                                sumItem.setShowTitle("\u5c0f\u8ba1");
                                sumItem.setIsSubTotalItem(true);
                                sumItem.setItemType(DimensionItemType.SUBTOTALROW);
                                sumItem.setChildItems(null);
                                sumItem.setDetailRows(null);
                                sumItem.setDepth(params.depth);
                                sumItem.setChildItemhasField(childHasField);
                                items.add(sumItem);
                            }
                            values = this.statictisFieldValues.get(fieldDimName);
                            if (pageItems != null && !pageItems.isEmpty()) {
                                values = pageItems;
                            }
                            for (k = 0; k < values.size(); ++k) {
                                show = val = values.get(k);
                                if (this.enumFieldTable.containsKey(fieldCode) && (et = this.enumFieldTable.get(fieldCode)) != null && (row = et.findByCode(val)) != null) {
                                    show = row.getTitle();
                                }
                                dataItem = QueryDimItem.newItem(show, val, params.depth, 0, DimensionItemType.ENTITY);
                                dataItem.setIsMaster(isMaster);
                                dataItem.setDimensionName(fieldDimName);
                                dataItem = this.setDataRows(dataDim, fieldDimName, val, dataItem, isLastDimension, parentItem);
                                if (loadOtherDimension) {
                                    temp = params.clone();
                                    ++temp.dimensionIndex;
                                    temp.depth = this.hasTreeStructDimension ? ++temp.depth : 0;
                                    dataItem = this.loadChildItem(dimensions, temp, dataItem);
                                }
                                if (dataItem.getDetailRows().size() > 0 || dataItem.getOwnDataSize() > 0 && dataItem.getStaticticsRow() != null) {
                                    parentItem.setChildHasDetailRow(true);
                                    dataItem.setIsShowSubTotal(dimension.isShowSum());
                                    dataItem.setIsSubTotalInFront(dimension.isPreposeSum());
                                }
                                dataItem = this.setChildHasField(dataItem, dimension, params.dimensionIndex + 1 == this.fieldDimensionIndex);
                                dataItem.setIsNotTreeStruct(isNotTreeStruct);
                                if (dimension.isShowSum() && this.gridType != GridType.RAC) {
                                    depth = this.hasTreeStructDimension != false ? params.depth + 1 : 0;
                                    this.createSubTotalItem(dataItem, depth, dimension);
                                }
                                items.add(dataItem);
                            }
                        }
                        if (this.gridType == GridType.RAC && dimension.isShowSum() && !dimension.isPreposeSum()) {
                            sumItem = new QueryDimItem();
                            sumItem.setShowTitle("\u5c0f\u8ba1");
                            sumItem.setIsSubTotalItem(true);
                            sumItem.setItemType(DimensionItemType.SUBTOTALROW);
                            sumItem.setChildItems(null);
                            sumItem.setDetailRows(null);
                            sumItem.setDepth(params.depth);
                            sumItem.setChildItemhasField(childHasField);
                            items.add(sumItem);
                        }
                    }
                    parentItem.setChildItems(items);
                    return parentItem;
                }
            }
        }
        catch (Exception ex) {
            QueryGridDefination.logger.error(ex.getMessage());
        }
        parentItem.setChildItems(items);
        return parentItem;
    }

    private List<String> getEntityDataRows(QueryDimensionDefine dimension) {
        List<String> rowsStr;
        block8: {
            block7: {
                QueryDimensionDefine conditionDim = null;
                IEntityTable entityTable = null;
                if (this.conditonDimensions != null) {
                    conditionDim = this.conditonDimensions.get(dimension.getViewId());
                }
                boolean hasCondition = conditionDim != null && conditionDim.getSelectItems().size() > 0;
                EntityViewDefine entityView = QueryHelper.getEntityView(dimension.getViewId());
                rowsStr = new ArrayList<String>();
                if (!hasCondition) break block7;
                if (dimension.getSelectItems() != null && !dimension.getSelectItems().isEmpty()) {
                    rowsStr = this.helper.getEntityRows2(dimension, conditionDim);
                } else {
                    if (this.periods != null && this.periods.size() > 0) {
                        DimensionValueSet dimV = new DimensionValueSet();
                        dimV.setValue("DATATIME", (Object)this.periods.get(this.periods.size() - 1));
                        entityTable = QueryHelper.getEntityTable(entityView, dimV, this.reloadTreeInfo);
                    } else {
                        entityTable = QueryHelper.getEntityTable(entityView, this.reloadTreeInfo);
                    }
                    List<IEntityRow> entityRows = this.helper.getEntityRows(conditionDim, entityTable);
                    for (IEntityRow r : entityRows) {
                        if (null == r) continue;
                        rowsStr.add(r.getEntityKeyData());
                    }
                }
                break block8;
            }
            List<QuerySelectItem> dimItems = dimension.getSelectItems();
            if (dimItems == null || dimItems.size() <= 0) break block8;
            for (QuerySelectItem item : dimItems) {
                rowsStr.add(item.getCode());
            }
        }
        return rowsStr;
    }

    private QueryDimItem setChildHasField(QueryDimItem item, QueryDimensionDefine dimension, boolean nextDimisField) {
        if (this.block.getFieldPosition() == QueryFieldPosition.DOWN && this.fieldDimension.getLayoutType() == dimension.getLayoutType() && nextDimisField) {
            item.setChildItemhasField(true);
        }
        return item;
    }

    private QueryDimItem loadChildItem(List<QueryDimensionDefine> dimensions, loadItemParams params, QueryDimItem itemOwn) {
        itemOwn = this.initStructTree(dimensions, params, itemOwn);
        return itemOwn;
    }

    private QueryDimItem setDataRows(DimensionValueSet dataDim, String dimName, String value, QueryDimItem item, boolean isLastDimension, QueryDimItem parent) {
        boolean mixHasData;
        boolean noParent;
        if (this.onlyLoadForm || this.dataTable == null) {
            return item;
        }
        if (!StringUtils.isEmpty((String)dimName)) {
            dataDim.setValue(dimName, (Object)value);
        }
        boolean bl = noParent = parent == null || StringUtils.isEmpty((String)parent.getEditTitle());
        if (!noParent) {
            DimensionValueSet parentSet = new DimensionValueSet();
            if (parent.getDimensionValueSet() != null) {
                parentSet.parseString(parent.getDimensionValueSet());
            }
            if (parentSet.size() > dataDim.size() || !dataDim.hasValue(parent.getDimensionName())) {
                parentSet.combine(dataDim);
                dataDim = parentSet;
            }
        }
        item.setDimensionValueSet(dataDim.toString());
        ArrayList<IDataRow> detailRows = null;
        if (this.gridType != GridType.RAC) {
            boolean allMaster = true;
            for (int i = 0; i < dataDim.size(); ++i) {
                Boolean isMaster = this.dimNameMasterMap.get(dataDim.getName(i));
                if (isMaster.booleanValue()) continue;
                allMaster = false;
                break;
            }
            if (allMaster) {
                IDataRow groupingRow;
                detailRows = this.dataTable.findFuzzyRows(dataDim);
                if (this.isQueryDetail && CollectionUtils.isEmpty(detailRows) && (groupingRow = this.dataTable.findGroupingRow(dataDim)) != null) {
                    detailRows.add(groupingRow);
                }
            } else if (dataDim.size() == 1 || this.pageInfo.dimensionName != null && this.pageInfo.dimensionName.equals(item.getDimensionName())) {
                String dimensionName = this.pageInfo.dimensionName == null ? dataDim.getName(0) : this.pageInfo.dimensionName;
                detailRows = this.dataTable.findDetailRowsByGroupKeyByFirstDimension(dataDim, dimensionName);
            } else {
                detailRows = this.dataTable.findDetailRowsByGroupKey(dataDim);
            }
        } else {
            if (this.dimVMap.containsKey(dataDim.toString())) {
                dataDim = this.dimVMap.get(dataDim.toString());
            }
            if (item.getIsMaster()) {
                detailRows = this.dataTable.findFuzzyRows(dataDim);
                if (this.isQueryDetail && CollectionUtils.isEmpty(detailRows)) {
                    IDataRow groupingRow = this.dataTable.findGroupingRow(dataDim);
                    detailRows.add(groupingRow);
                }
            } else {
                detailRows = this.dataTable.findDetailRowsByGroupKey(dataDim);
            }
        }
        String itemTitle = item.getEditTitle();
        int itemDepth = item.getDepth();
        if (this.pageInfo.dimensionName != null && !item.getDimensionName().equals(this.pageInfo.dimensionName)) {
            itemTitle = dataDim.getValue(this.pageInfo.dimensionName).toString();
            int n = itemDepth = this.curFirstDimItem != null ? this.curFirstDimItem.getDepth() : item.getDepth();
        }
        if (detailRows.size() > 0) {
            boolean isPaging = this.block.isPaging();
            if (this.gridType != GridType.RAC) {
                detailRows.removeIf(this::notShowNullOrZeroRow);
            }
            item.totalSize = detailRows.size();
            if (isPaging && parent != null && !StringUtils.isEmpty((String)parent.getEditTitle())) {
                if (this.pageInfo.lastItem != null && itemTitle != null && itemTitle.equals(this.pageInfo.lastItem) && !this.pageInfo.recordStart.containsKey(itemTitle)) {
                    return item;
                }
                if (parent.isPageEnd()) {
                    this.setGroupingRow(dataDim, item);
                    return item;
                }
            }
            if (this.isTreeStruct && isPaging && this.isTreeLoad && this.mode != QueryMode.SIMPLEQUERY) {
                boolean expandFirstLeval;
                boolean bl2 = expandFirstLeval = noParent && item.getDepth() == 0;
                if (!StringUtils.isEmpty((String)this.pageInfo.strucNode) && !expandFirstLeval) {
                    if (detailRows.size() != 1 && !dataDim.equals((Object)this.pageInfo.dimNodeSet)) {
                        item.setOwnDataSize(detailRows.size());
                        this.setGroupingRow(dataDim, item);
                        return item;
                    }
                } else if (StringUtils.isEmpty((String)this.pageInfo.strucNode) && !noParent && item.getDepth() != 0) {
                    item.setOwnDataSize(detailRows.size());
                    if (detailRows.size() == 1) {
                        item.setDetailRows(detailRows);
                    }
                    this.setGroupingRow(dataDim, item);
                    return item;
                }
            } else if (isPaging && !this.pageInfo.recordIndex.containsKey(itemTitle) && this.gridType != GridType.RAC && !this.getDepthPageInfo().containsKey(-1) && detailRows.isEmpty()) {
                return item;
            }
            if (this.pageInfo.recordIndex.containsKey(itemTitle)) {
                boolean hasWrite = false;
                if (this.pageInfo.lastPassedRows.containsKey(itemDepth)) {
                    List<String> keys = this.pageInfo.lastPassedRows.get(itemDepth);
                    hasWrite = keys.contains(itemTitle);
                }
                if (!this.pageInfo.isFirstPage.booleanValue() && hasWrite && !this.pageInfo.recordStart.containsKey(itemTitle)) {
                    item.setHasWriteTotal(true);
                    return item;
                }
            }
            List<Object> tempList = new ArrayList();
            int end = detailRows.size();
            int size = detailRows.size();
            int start = 0;
            if (isPaging && this.pageInfo.recordIndex.containsKey(itemTitle) && isLastDimension) {
                end = this.pageInfo.recordIndex.get(itemTitle);
            }
            if (isPaging && isLastDimension && this.pageInfo.recordStart.containsKey(itemTitle)) {
                end = 0;
                int total = this.pageInfo.recordIndex.get(itemTitle) - this.pageInfo.recordStart.get(itemTitle);
                Integer recordStart = this.pageInfo.recordStart.get(itemTitle);
                start = 0;
                if (this.pageInfo.lastDimensionName == null) {
                    if (this.pageInfo.lastDimensionRecord.containsKey(item.getEditTitle()) && this.pageInfo.lastDimensionRecord.get(item.getDimensionValueSet()) == size) {
                        start = 0;
                        end = 0;
                    } else {
                        item.isFirst = true;
                        if (size < total - this.pageInfo.lastItemCount) {
                            int totalSize;
                            this.pageInfo.lastItemCount += size;
                            this.pageInfo.lastDimensionRecord.put(item.getDimensionValueSet(), this.pageInfo.lastItemCount);
                            end = this.pageInfo.lastItemCount;
                            if (end == item.totalSize) {
                                this.pageInfo.lastDimensionName = null;
                                this.pageInfo.lastItemCount = 0;
                                this.pageInfo.recordStart.put(itemTitle, recordStart + size);
                            }
                            int sum = this.pageInfo.lastDimensionRecord.values().stream().mapToInt(e -> e == null ? 0 : e).sum();
                            item.isFinish = true;
                            this.pageInfo.lastItemCount = 0;
                            int n = totalSize = noParent ? item.totalSize : parent.totalSize;
                            if (item.getDimensionName().equals(this.pageInfo.dimensionName)) {
                                totalSize = item.totalSize;
                            } else {
                                int n2 = totalSize = parent.getDimensionName().equals(this.pageInfo.dimensionName) ? parent.totalSize : item.totalSize;
                                if (this.curFirstDimItem != null) {
                                    totalSize = this.curFirstDimItem.totalSize;
                                }
                            }
                            if (sum == totalSize) {
                                this.pageInfo.lastDimensionRecord = new LinkedHashMap<String, Integer>();
                            }
                        } else {
                            this.pageInfo.lastDimensionName = item.getDimensionValueSet();
                            end = total - this.pageInfo.lastItemCount;
                            if (end == 0) {
                                this.pageInfo.lastDimensionName = null;
                                this.pageInfo.lastDimensionRecord = new LinkedHashMap<String, Integer>();
                                this.pageInfo.lastItemCount = 0;
                            }
                            this.pageInfo.lastItemCount += end;
                            this.pageInfo.lastDimensionRecord.put(item.getDimensionValueSet(), end);
                            this.pageInfo.recordStart.put(itemTitle, recordStart + end);
                            if (end == total) {
                                if (noParent) {
                                    item.setPageEnd(true);
                                } else {
                                    parent.setPageEnd(true);
                                    if (end == size) {
                                        this.pageInfo.lastDimensionName = null;
                                        this.pageInfo.lastItemCount = 0;
                                    }
                                }
                            }
                        }
                    }
                } else if (this.pageInfo.lastDimensionName.equals(item.getDimensionValueSet())) {
                    Integer count = this.pageInfo.lastDimensionRecord.get(this.pageInfo.lastDimensionName);
                    start = count;
                    if (start + total <= size) {
                        this.pageInfo.lastItemCount += total;
                        end = this.pageInfo.lastItemCount;
                        this.pageInfo.lastDimensionRecord.put(this.pageInfo.lastDimensionName, end);
                    } else {
                        end = size;
                        int deltaSize = size - start;
                        count = count + deltaSize;
                        this.pageInfo.lastItemCount += deltaSize;
                        this.pageInfo.lastDimensionRecord.put(this.pageInfo.lastDimensionName, size - start);
                        this.pageInfo.recordStart.put(itemTitle, recordStart + deltaSize);
                        this.pageInfo.lastDimensionName = null;
                    }
                    if (end == size) {
                        int totalSize;
                        this.pageInfo.lastDimensionName = null;
                        if (end - start == total) {
                            if (start != 0) {
                                this.lastItemNotEnd = true;
                                item.setHasWriteTotal(true);
                            }
                            if (noParent) {
                                item.setPageEnd(true);
                            } else {
                                parent.setPageEnd(true);
                            }
                        }
                        int sum = this.pageInfo.lastDimensionRecord.values().stream().mapToInt(e -> e == null ? 0 : e).sum();
                        item.isFinish = true;
                        this.pageInfo.lastItemCount = 0;
                        int n = totalSize = noParent ? item.totalSize : parent.totalSize;
                        if (sum == totalSize) {
                            this.pageInfo.lastDimensionRecord = new LinkedHashMap<String, Integer>();
                        }
                    }
                } else {
                    start = 0;
                    end = 0;
                }
            }
            if (end == start && !this.isTreeStruct) {
                detailRows = new ArrayList<IDataRow>();
            }
            if (end <= detailRows.size()) {
                if (this.pageInfo.recordStart.containsKey(itemTitle)) {
                    if (this.mode == QueryMode.SIMPLEQUERY && start > 0) {
                        item.setHasWriteTotal(true);
                        parent.setHasWriteTotal(true);
                    }
                    item.itemPageStart = start;
                    try {
                        tempList = detailRows.subList(start, end);
                    }
                    catch (Exception e2) {
                        tempList = new ArrayList();
                    }
                } else {
                    tempList = detailRows;
                }
            } else {
                tempList = detailRows;
            }
            if (isLastDimension) {
                item.setDetailRows(tempList);
                item.setOwnDataSize(tempList.size());
                if (start == 0 && end != 0) {
                    item.isFirst = true;
                }
                if (tempList.size() > 0) {
                    this.pageInfo.lastDimValueSetStr = dataDim.toString();
                }
                if (start == 0 && end == 0 && parent != null && size == parent.totalSize) {
                    parent.setChildDataSize(0);
                }
            } else {
                item.setChildDataSize(tempList.size());
            }
        }
        if (!this.block.isShowDetail() && this.pageInfo.recordIndex.containsKey(itemTitle)) {
            boolean hasWrite = false;
            if (this.pageInfo.lastPassedRows.containsKey(itemDepth)) {
                List<String> keys = this.pageInfo.lastPassedRows.get(itemDepth);
                hasWrite = keys.contains(itemTitle);
            }
            if (!this.pageInfo.isFirstPage.booleanValue() && hasWrite && !this.pageInfo.recordStart.containsKey(itemTitle)) {
                item.setHasWriteTotal(true);
                return item;
            }
        }
        this.setGroupingRow(dataDim, item);
        item.setLastDimension(isLastDimension);
        if (!this.block.isShowDetail()) {
            ArrayList<Object> rows;
            if (item.getStaticticsRow() != null) {
                item.setChildDataSize(1);
                item.setOwnDataSize(1);
                rows = new ArrayList<IDataRow>();
                rows.add(item.getStaticticsRow());
                item.setDetailRows(rows);
            } else if (this.block.isShowNullRow() && item.getItemType() != DimensionItemType.PERIODENTITY) {
                item.setChildDataSize(1);
                item.setOwnDataSize(1);
                rows = new ArrayList();
                rows.add(item.getStaticticsRow());
                item.setDetailRows(rows);
            }
        }
        boolean flag = item.getStaticticsRow() == null && !this.isSimpleQuery || item.getDetailRows() == null && item.getDetailRows().size() == 0;
        boolean bl3 = mixHasData = this.gridType == GridType.RAC && item.getStaticticsRow() != null;
        if (this.block.isShowNullRow() && item.getItemType() != DimensionItemType.PERIODENTITY && flag || mixHasData) {
            item.setChildDataSize(1);
            item.setOwnDataSize(1);
            if (item.getDetailRows() == null || item.getDetailRows().size() == 0) {
                ArrayList<IDataRow> rows = new ArrayList<IDataRow>();
                rows.add(item.getStaticticsRow());
                item.setDetailRows(rows);
            }
            if (isLastDimension && this.block.isShowNullRow() && (item.getDetailRows() == null || item.getDetailRows().size() == 0)) {
                ArrayList<IDataRow> rows = new ArrayList<IDataRow>();
                DataRowImpl nullRow = new DataRowImpl((ReadonlyTableImpl)this.dataTable, null, null);
                rows.add((IDataRow)nullRow);
                item.setDetailRows(rows);
            }
        }
        return item;
    }

    private void setGroupingRow(DimensionValueSet dataDim, QueryDimItem item) {
        if (this.mode != QueryMode.SIMPLEQUERY) {
            IDataRow groupingRow = this.dataTable.findGroupingRow(dataDim);
            if (groupingRow != null) {
                item.setStaticticsRow(groupingRow);
                if (!this.block.isShowDetail() && this.checkRowData(groupingRow) == RowDataType.ALLNULL && !this.block.isShowNullRow()) {
                    item.setStaticticsRow(null);
                }
            } else if (item.getIsShowSubTotal()) {
                item.setStaticticsRow((IDataRow)new DataRowImpl((ReadonlyTableImpl)this.dataTable, dataDim, null));
            }
        }
    }

    private boolean loadStrucEntityItems(List<QueryDimensionDefine> dimensions, List<String> rows, List<QueryDimItem> items, IEntityTable entityTable, InitCacheParams params, loadItemParams itemParam, QueryDimItem parentItem) {
        boolean hasDetailRow = false;
        boolean isPaging = this.block.isPaging();
        int start = 0;
        try {
            DimensionValueSet dataDim = new DimensionValueSet(itemParam.parentDimValue);
            boolean loadOtherDimension = itemParam.dimensionIndex < dimensions.size() - 1;
            QueryDimensionDefine dimension = dimensions.get(itemParam.dimensionIndex);
            String dimensionName = dimension.getDimensionName();
            ArrayList<String> pageItems = new ArrayList<String>();
            pageItems.addAll(rows);
            int depth = this.pageInfo.parentDepth + 1;
            if (parentItem != null && !StringUtils.isEmpty((String)parentItem.getEditTitle())) {
                depth = parentItem.getDepth() + 1;
            }
            if (this.pageInfo.parentDepth != 0) {
                params.depth = depth;
            }
            for (int i = start; i < pageItems.size(); ++i) {
                IEntityRow erow = entityTable.findByEntityKey((String)pageItems.get(i));
                if (rows.size() == 0) break;
                if (!pageItems.contains(erow.getEntityKeyData())) continue;
                rows.remove(erow.getEntityKeyData());
                List childs = entityTable.getChildRows(erow.getEntityKeyData());
                QueryDimItem entityItem = this.getEntityItem(dimensions, erow, dataDim, params, itemParam, loadOtherDimension, parentItem, dimensionName);
                entityItem.setChildHasDetailRow(childs.size() > 0);
                if (this.onlyLoadForm) {
                    items.add(entityItem);
                    if (!hasDetailRow) {
                        hasDetailRow = entityItem.getChildHasDetailRow() || entityItem.getDetailRows() != null && entityItem.getDetailRows().size() > 0;
                    }
                    return hasDetailRow;
                }
                if (params.needChild) {
                    List childRows = entityTable.getChildRows(erow.getEntityKeyData());
                    if (childRows.size() > 0) {
                        boolean isFirstTreeLevel;
                        List<String> rowList = childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                        List<QueryDimItem> childItems = entityItem.getChildItems();
                        if (childItems == null) {
                            childItems = new ArrayList<QueryDimItem>();
                        }
                        InitCacheParams childParams = params.clone();
                        ++childParams.depth;
                        boolean chileHasDetailRow = false;
                        IDataRow dataRow = this.dataTable.findGroupingRow(erow.getRowKeys());
                        if (dataRow != null && this.checkRowData(dataRow) != RowDataType.ALLNULL && entityItem.getOwnDataSize() != 1) {
                            chileHasDetailRow = true;
                        }
                        if (!chileHasDetailRow) {
                            chileHasDetailRow = !this.block.isShowDetail() && !entityItem.getIsNotTreeStruct() ? this.hasDetailRowCheck((IEntityRow)childRows.get(0), childRows, entityTable, dataDim) : this.hasDetailRowCheck(erow, childRows, entityTable, dataDim);
                            entityItem.hasChildItem = this.hasDetailRowCheck((IEntityRow)childRows.get(0), childRows, entityTable, dataDim);
                        }
                        if (this.block.isShowNullRow()) {
                            chileHasDetailRow = true;
                            if (this.pageInfo.dimensionName != null && this.pageInfo.dimensionName.equals(entityItem.getDimensionName())) {
                                entityItem.hasChildItem = true;
                            }
                        }
                        boolean bl = isFirstTreeLevel = parentItem.getDimensionName() == null && entityItem.getDepth() == 0;
                        if (isPaging) {
                            childParams.needChild = false;
                            entityItem.setChildHasDetailRow(chileHasDetailRow);
                            if (isFirstTreeLevel) {
                                childParams.needChild = true;
                                entityItem.hasChildItem = true;
                            }
                            if (this.mode == QueryMode.SIMPLEQUERY) {
                                childParams.needChild = true;
                            }
                        }
                        entityItem.setChildDataSize(chileHasDetailRow ? childs.size() : 0);
                        if (this.isTreeStruct && !StringUtils.isEmpty((String)this.pageInfo.strucNode) || !isPaging || isFirstTreeLevel || this.isSimpleQuery) {
                            if (isPaging && !isFirstTreeLevel && !this.isSimpleQuery) {
                                childParams.needChild = entityItem.getEditTitle().equals(this.pageInfo.strucNode);
                            }
                            if (entityItem.getEditTitle().equals(this.pageInfo.strucNode) || !isPaging || isFirstTreeLevel || this.isSimpleQuery) {
                                childParams.depth = params.depth + 1;
                                this.loadStrucEntityItems(dimensions, rowList, childItems, entityTable, childParams, itemParam, entityItem);
                            }
                        }
                        entityItem.setChildHasDetailRow(chileHasDetailRow);
                        entityItem.setChildItems(childItems);
                        if (chileHasDetailRow || entityItem.getOwnDataSize() > 0) {
                            items.add(entityItem);
                        }
                    } else {
                        entityItem = this.setChildHasField(entityItem, dimension, itemParam.dimensionIndex + 1 == this.fieldDimensionIndex);
                        if (this.block.isShowNullRow() && this.pageInfo.dimensionName != null && this.pageInfo.dimensionName.equals(entityItem.getDimensionName())) {
                            entityItem.hasChildItem = true;
                            entityItem.setChildHasDetailRow(true);
                        }
                        items.add(entityItem);
                    }
                    if (!hasDetailRow) {
                        hasDetailRow = entityItem.getChildHasDetailRow() || entityItem.getChildDataSize() > 0;
                    }
                } else {
                    entityItem.setChildHasDetailRow(false);
                    items.add(entityItem);
                }
                if (dimension.isShowSum() && this.gridType != GridType.RAC) {
                    depth = this.hasTreeStructDimension ? params.depth + 1 : 0;
                    this.createSubTotalItem(entityItem, depth, dimension);
                }
                this.setPageLastItem(dimension.getViewId(), params.depth, pageItems);
            }
            return hasDetailRow;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private boolean loadEntityItems(List<QueryDimensionDefine> dimensions, List<IEntityRow> rows, List<QueryDimItem> items, IEntityTable entityTable, InitCacheParams params, loadItemParams itemParam, QueryDimItem parentItem) {
        boolean hasDetailRow = false;
        int start = 0;
        try {
            DimensionValueSet dataDim = new DimensionValueSet(itemParam.parentDimValue);
            boolean loadOtherDimension = itemParam.dimensionIndex < dimensions.size() - 1;
            QueryDimensionDefine dimension = dimensions.get(itemParam.dimensionIndex);
            List<String> pageItems = this.getPageItems(dimension.getViewId(), params.depth);
            if (pageItems.size() == 0 && this.dimNameMasterMap.containsKey(dimension.getDimensionName()) && !this.dimNameMasterMap.get(dimension.getDimensionName()).booleanValue() || this.pageInfo.dimensionName != null && !this.pageInfo.dimensionName.equals(dimension.getDimensionName())) {
                pageItems = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            String dimensionName = dimension.getDimensionName();
            ArrayList<String> lastItem = new ArrayList<String>();
            for (int i = start; i < rows.size(); ++i) {
                IEntityRow erow = rows.get(i);
                if (this.isPaging) {
                    int index;
                    String[] parents;
                    List<String> path;
                    IEntityRow last;
                    if (pageItems.size() == 0 && !this.onlyLoadForm) break;
                    if (!pageItems.contains(erow.getEntityKeyData()) && !this.onlyLoadForm) continue;
                    if (pageItems.size() == 0) {
                        pageItems.remove(erow.getEntityKeyData());
                        lastItem.add(erow.getEntityKeyData());
                    }
                    boolean hasWrite = false;
                    if (this.pageInfo.lastPassedRows.containsKey(params.depth)) {
                        List<String> keys = this.pageInfo.lastPassedRows.get(params.depth);
                        hasWrite = keys.contains(erow.getEntityKeyData());
                    }
                    if (this.block.isShowNullRow() && hasWrite && !this.pageInfo.recordStart.containsKey(erow.getEntityKeyData()) && this.pageInfo.lastItem != null && (last = entityTable.findByEntityKey(this.pageInfo.lastItem)) != null && (last.getParentEntityKey() != null ? !(path = Arrays.asList(parents = last.getParentsEntityKeyDataPath())).contains(erow.getEntityKeyData()) && !this.pageInfo.lastItem.equals(erow.getEntityKeyData()) : rows.contains(last) && i < (index = rows.indexOf(last)))) continue;
                }
                QueryDimItem entityItem = this.getEntityItem(dimensions, erow, dataDim, params, itemParam, loadOtherDimension, parentItem, dimensionName);
                if (this.onlyLoadForm) {
                    items.add(entityItem);
                    if (!hasDetailRow) {
                        hasDetailRow = entityItem.getChildHasDetailRow() || entityItem.getDetailRows() != null && entityItem.getDetailRows().size() > 0;
                    }
                    entityItem = this.setChildHasField(entityItem, dimension, itemParam.dimensionIndex + 1 == this.fieldDimensionIndex);
                    return hasDetailRow;
                }
                if (params.needChild) {
                    List childRows = entityTable.getChildRows(erow.getEntityKeyData());
                    if (childRows.size() > 0) {
                        if (params.isNotTreeStruct || this.mode == QueryMode.SIMPLEQUERY) {
                            boolean hasDetailRowCheck = this.hasDetailRowCheck(erow, childRows, entityTable, dataDim);
                            hasDetailRowCheck = hasDetailRowCheck && (entityItem.getChildDataSize() > 0 || entityItem.getOwnDataSize() > 0 || entityItem.getStaticticsRow() != null);
                            boolean showNull = this.block.isShowNullRow();
                            if (!this.hasWrite(entityItem) && (showNull || hasDetailRowCheck || entityItem.getChildDataSize() > 0 || entityItem.getOwnDataSize() > 0) || this.gridType == GridType.RAC) {
                                items.add(entityItem);
                            }
                            InitCacheParams childParams = params.clone();
                            ++childParams.depth;
                            boolean chileHasDetailRow = this.loadEntityItems(dimensions, childRows, items, entityTable, childParams, itemParam, entityItem);
                            entityItem.setChildHasDetailRow(chileHasDetailRow);
                        } else {
                            List<QueryDimItem> childItems = entityItem.getChildItems();
                            if (childItems == null) {
                                childItems = new ArrayList<QueryDimItem>();
                            }
                            InitCacheParams childParams = params.clone();
                            ++childParams.depth;
                            boolean chileHasDetailRow = this.loadEntityItems(dimensions, childRows, childItems, entityTable, childParams, itemParam, entityItem);
                            entityItem.setChildHasDetailRow(chileHasDetailRow);
                            entityItem.setChildItems(childItems);
                            items.add(entityItem);
                        }
                        entityItem = this.setChildHasField(entityItem, dimension, itemParam.dimensionIndex + 1 == this.fieldDimensionIndex);
                    } else if (!this.hasWrite(entityItem = this.setChildHasField(entityItem, dimension, itemParam.dimensionIndex + 1 == this.fieldDimensionIndex))) {
                        items.add(entityItem);
                    }
                    if (!hasDetailRow) {
                        hasDetailRow = entityItem.getChildHasDetailRow() || entityItem.getDetailRows().size() > 0;
                    }
                } else {
                    items.add(entityItem);
                }
                if (dimension.isShowSum() && this.gridType != GridType.RAC) {
                    int depth = this.hasTreeStructDimension ? params.depth + 1 : 0;
                    this.createSubTotalItem(entityItem, depth, dimension);
                }
                if (lastItem.size() <= 0) continue;
                this.setPageLastItem(dimension.getViewId(), params.depth, lastItem);
            }
            return hasDetailRow;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private boolean hasWrite(QueryDimItem item) {
        if (this.pageInfo.recordIndex.containsKey(item.getEditTitle())) {
            boolean hasWrite = false;
            int itemDetailDataSize = this.pageInfo.recordIndex.get(item.getEditTitle());
            if (this.pageInfo.lastPassedRows.containsKey(item.getDepth())) {
                List<String> keys = this.pageInfo.lastPassedRows.get(item.getDepth());
                hasWrite = keys.contains(item.getEditTitle());
            }
            if (this.block.isShowNullRow() && this.isSimpleQuery && !this.isSinglePeriod) {
                if (itemDetailDataSize == 0 && hasWrite) {
                    return true;
                }
                if (this.pageInfo.recordStart.containsKey(item.getEditTitle()) && hasWrite) {
                    return false;
                }
            }
            if (!this.pageInfo.isFirstPage.booleanValue() && hasWrite && this.pageInfo.recordStart.size() == 0) {
                return true;
            }
            if (item.hasWriteTotal() && hasWrite && this.block.isShowNullRow()) {
                return true;
            }
        }
        return false;
    }

    private QueryDimItem getEntityItem(List<QueryDimensionDefine> dimensions, IEntityRow erow, DimensionValueSet dataDim, InitCacheParams params, loadItemParams itemParam, boolean loadOtherDimension, QueryDimItem parentItem, String dimensionName) {
        QueryDimItem entityItem = QueryDimItem.newItem(erow.getTitle(), erow.getEntityKeyData(), params.depth, 0, DimensionItemType.FIELD);
        try {
            boolean hasWriteSubTotal;
            DimensionValueSet entityDim = new DimensionValueSet();
            entityDim.setValue(dimensionName, (Object)erow.getEntityKeyData());
            dataDim.combine(entityDim);
            entityItem.setDimensionValueSet(dataDim.toString());
            entityItem.setDimensionName(params.dimName);
            entityItem.setIsMaster(params.isMaster);
            entityItem.setIsShowSubTotal(params.isShowSum);
            entityItem = this.setDataRows(dataDim, "", "", entityItem, itemParam.getIsLasteDimension(), parentItem);
            entityItem.setIsTree(params.isTree);
            entityItem.setIsNotTreeStruct(params.isNotTreeStruct && this.mode != QueryMode.SIMPLEQUERY);
            boolean bl = hasWriteSubTotal = this.pageInfo.lastDimValueSet != null && this.pageInfo.lastDimValueSet.isSubsetOf(dataDim);
            if (hasWriteSubTotal) {
                entityItem.setIsShowSubTotal(false);
                if (this.pageInfo.lastDimensionName != null) {
                    this.lastItemNotEnd = true;
                }
                if (entityItem.isFirst) {
                    entityItem.setIsShowSubTotal(true);
                }
            }
            entityItem.setIsSubTotalInFront(params.isSumRowInFront);
            entityItem.setItemType(params.itemType);
            int depth = params.depth;
            if (parentItem.getDimensionName() != null) {
                depth = parentItem.getDepth() + 1;
            }
            entityItem.setDepth(depth);
            if (this.pageInfo.dimensionName != null && this.pageInfo.dimensionName.equals(entityItem.getDimensionName())) {
                this.curFirstDimItem = entityItem;
            }
            if ((itemParam.dimensionIndex < itemParam.lasteDimensionIndex && !entityItem.getIsNotTreeStruct() && StringUtils.isEmpty((String)this.pageInfo.strucNode) || this.pageInfo.dimNodeSet != null && !this.pageInfo.dimNodeSet.isSubsetOf(dataDim)) && this.isPaging && this.isTreeLoad) {
                loadOtherDimension = false;
                if (parentItem.getDimensionName() == null && entityItem.getDepth() == 0 || this.mode == QueryMode.SIMPLEQUERY) {
                    loadOtherDimension = true;
                }
                if (this.pageInfo.strucNode == null && entityItem.getDepth() != 0 || dimensions.size() == 1 || itemParam.lasteDimensionIndex < itemParam.dimensionIndex + 1) {
                    loadOtherDimension = false;
                }
            }
            if (loadOtherDimension) {
                loadItemParams temp = itemParam.clone();
                ++temp.dimensionIndex;
                temp.depth = this.hasTreeStructDimension ? ++temp.depth : 0;
                temp.parentDimValue = dataDim;
                entityItem = this.loadChildItem(dimensions, temp, entityItem);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u521b\u5efa\u7ef4\u5ea6\u9879\u65f6\u5f02\u5e38", (String)("\u7ef4\u5ea6\u4fe1\u606f" + erow.getTitle() + ";\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
        }
        return entityItem;
    }

    public void reloadDataTable() {
        List<Object> returnValue = this.helper.initGroupQuery(this.block, this.fieldIndex, this.isQueryDetail, this.isExport, this.conditonDimensions, this.fieldMapLink, this.gridType != GridType.DETAIL, this.periods, this.customExpress, false);
        IGroupingQuery groupQuery = (IGroupingQuery)returnValue.get(0);
        this.fieldIndex = (Map)returnValue.get(1);
        this.dataTable = this.helper.getDataTable(groupQuery, this.block, this.isQueryDetail, this.isExport);
        this.reloadDimData(this.rows, (IReadonlyTable)this.dataTable, null);
    }

    private void reloadDimData(List<QueryDimItem> items, IReadonlyTable dataTable, DimensionValueSet parentDimValue) {
        DimensionValueSet itemSet = parentDimValue;
        if (parentDimValue == null) {
            itemSet = new DimensionValueSet();
        } else {
            itemSet.combine(parentDimValue);
        }
        for (int i = 0; i < items.size(); ++i) {
            boolean isLastDimension = i == items.size() - 1;
            QueryDimItem item = items.get(i);
            itemSet.setValue(item.getDimensionName(), (Object)item.getEditTitle());
            item = this.setDataRows(itemSet, item.getDimensionName(), item.getEditTitle(), item, isLastDimension, null);
            if (item.getChildItems().size() > 0) {
                this.reloadDimData(item.getChildItems(), dataTable, itemSet);
            }
            items.set(i, item);
        }
    }

    public void reloadFieldItem(int changeType, QueryBlockDefine blockDefine) {
    }

    public Map<Integer, Double> getSubTotalRow(DimensionValueSet dimValue, List<Integer> fieldIndex, QueryDimItem subtotalItem, List<QueryDimItem> items) {
        LinkedHashMap<Integer, Double> subTotalValues = new LinkedHashMap<Integer, Double>();
        try {
            if (subtotalItem.getChildItemhasField()) {
                subTotalValues.put(0, 0.0);
                double total = 0.0;
                for (QueryDimItem item : items) {
                    if (item.getShowTitle() == subtotalItem.getEditTitle()) continue;
                    IDataRow row = item.getStaticticsRow();
                    if (row == null) {
                        DimensionValueSet curDimValue = new DimensionValueSet(dimValue);
                        curDimValue.setValue(item.getDimensionName(), (Object)item.getEditTitle());
                        row = this.dataTable.findGroupingRow(curDimValue);
                        if (row == null) continue;
                    }
                    for (Integer index : fieldIndex) {
                        AbstractData value = row.getValue(index.intValue());
                        if (value == null || value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                        total += value.getAsFloat();
                    }
                }
                subTotalValues.put(0, total);
            } else {
                double total = 0.0;
                subTotalValues.put(0, total);
                for (QueryDimItem item : items) {
                    DimensionValueSet curDimValue = new DimensionValueSet(dimValue);
                    curDimValue.combine(item.getDimValueSet());
                    ArrayList<QueryDimItem> childItems = new ArrayList();
                    if (!item.getChildItemhasField()) {
                        childItems = item.getChildItems();
                    }
                    if (childItems.size() > 0) {
                        return this.getSubTotalRow(curDimValue, fieldIndex, subtotalItem, childItems);
                    }
                    IDataRow row = this.dataTable.findGroupingRow(curDimValue);
                    if (row == null) continue;
                    for (Integer index : fieldIndex) {
                        if (subTotalValues.containsKey(index)) {
                            total = (Double)subTotalValues.get(index);
                        }
                        AbstractData value = row.getValue(index.intValue());
                        if (value.dataType == 10 || value.dataType == 3 || value.dataType == 5) {
                            total += value.getAsFloat();
                        }
                        subTotalValues.put(index, total);
                    }
                }
            }
        }
        catch (Exception ex) {
            logger.error("\u4ea4\u53c9\u8868\u8ba1\u7b97\u5c0f\u8ba1\u6570\u5f02\u5e38\uff1a" + ex.getMessage());
        }
        return subTotalValues;
    }

    public Map<Integer, Double> getSubTotalRowNew(DimensionValueSet dimValue, List<Integer> fieldIndex, QueryDimItem subtotalItem, List<QueryDimItem> items, boolean dimIsInCol, QuerySelectField preField) {
        LinkedHashMap<Integer, Double> subTotalValues;
        block30: {
            subTotalValues = new LinkedHashMap<Integer, Double>();
            DecimalFormat df = new DecimalFormat("0.0#");
            try {
                if (subtotalItem.getChildItemhasField()) {
                    subTotalValues.put(0, 0.0);
                    Double total = 0.0;
                    int depth = 0;
                    if (dimIsInCol) {
                        for (QueryDimItem item : items) {
                            if (item.getShowTitle() == subtotalItem.getEditTitle()) continue;
                            IDataRow row = item.getStaticticsRow();
                            if (row == null) {
                                DimensionValueSet curDimValue = new DimensionValueSet(dimValue);
                                curDimValue.setValue(item.getDimensionName(), (Object)item.getEditTitle());
                                row = this.dataTable.findGroupingRow(curDimValue);
                                if (row == null) continue;
                            }
                            if (depth != item.getDepth()) continue;
                            for (Integer index : fieldIndex) {
                                AbstractData value = row.getValue(index.intValue());
                                if (value == null || value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                                total = total + value.getAsFloat();
                                total = Double.parseDouble(df.format(total));
                            }
                        }
                    } else {
                        DimensionValueSet curDimValue = new DimensionValueSet(dimValue);
                        IDataRow row = this.dataTable.findGroupingRow(curDimValue);
                        if (row == null) {
                            return subTotalValues;
                        }
                        for (Integer index : fieldIndex) {
                            AbstractData value;
                            if (subTotalValues.containsKey(index)) {
                                total = (Double)subTotalValues.get(index);
                            }
                            if ((value = row.getValue(index.intValue())) == null || value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                            total = total + value.getAsFloat();
                            total = Double.parseDouble(df.format(total));
                        }
                    }
                    subTotalValues.put(0, total);
                    break block30;
                }
                subTotalValues.put(0, 0.0);
                if (dimIsInCol) {
                    for (QueryDimItem item : items) {
                        AbstractData value;
                        DimensionValueSet curDimValue = new DimensionValueSet(dimValue);
                        curDimValue.combine(item.getDimValueSet());
                        ArrayList<QueryDimItem> childItems = new ArrayList();
                        if (!item.getChildItemhasField()) {
                            childItems = item.getChildItems();
                        }
                        if (childItems.size() > 0) {
                            return this.getSubTotalRow(curDimValue, fieldIndex, subtotalItem, childItems);
                        }
                        IDataRow row = this.dataTable.findGroupingRow(curDimValue);
                        if (row == null) continue;
                        if (preField == null) {
                            double total = 0.0;
                            for (Integer index : fieldIndex) {
                                if (subTotalValues.containsKey(index)) {
                                    total = (Double)subTotalValues.get(index);
                                }
                                value = row.getValue(index.intValue());
                                if (value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                                total += value.getAsFloat();
                                total = Double.parseDouble(df.format(total));
                            }
                            subTotalValues.put(0, total);
                            continue;
                        }
                        for (Integer index : fieldIndex) {
                            double total = 0.0;
                            if (subTotalValues.containsKey(index)) {
                                total = (Double)subTotalValues.get(index);
                            }
                            value = row.getValue(index.intValue());
                            if (value.dataType == 10 || value.dataType == 3 || value.dataType == 5) {
                                total += value.getAsFloat();
                                total = Double.parseDouble(df.format(total));
                            }
                            subTotalValues.put(index, total);
                        }
                    }
                } else {
                    DimensionValueSet curDimValue = new DimensionValueSet(dimValue);
                    IDataRow row = this.dataTable.findGroupingRow(curDimValue);
                    if (row == null) {
                        return subTotalValues;
                    }
                    if (preField == null) {
                        double total = 0.0;
                        for (Integer index : fieldIndex) {
                            AbstractData value = row.getValue(index.intValue());
                            if (value.dataType == 10 || value.dataType == 3 || value.dataType == 5) {
                                total += value.getAsFloat();
                                total = Double.parseDouble(df.format(total));
                            }
                            subTotalValues.put(index, total);
                        }
                    } else {
                        int curFieldIndex = this.fieldIndex.get(preField.getCode());
                        for (Integer index : fieldIndex) {
                            double total = 0.0;
                            if (index != curFieldIndex) continue;
                            if (subTotalValues.containsKey(index)) {
                                total = (Double)subTotalValues.get(index);
                            }
                            AbstractData value = row.getValue(index.intValue());
                            if (value.dataType == 10 || value.dataType == 3 || value.dataType == 5) {
                                total += value.getAsFloat();
                                total = Double.parseDouble(df.format(total));
                            }
                            subTotalValues.put(index, total);
                        }
                    }
                }
            }
            catch (Exception ex) {
                logger.error("\u4ea4\u53c9\u8868\u8ba1\u7b97\u5c0f\u8ba1\u6570\u5f02\u5e38\uff1a" + ex.getMessage());
            }
        }
        return subTotalValues;
    }

    private Double countTotal(double curCount, QueryDimItem item, List<Integer> fieldIndex) {
        try {
            IDataRow row = item.getStaticticsRow();
            if (row == null) {
                return 0.0;
            }
            for (Integer index : fieldIndex) {
                AbstractData value = row.getValue(index.intValue());
                if (value.dataType != 10 && value.dataType != 3 && value.dataType != 5) continue;
                curCount += value.getAsFloat();
            }
        }
        catch (Exception ex) {
            logger.error("\u4ea4\u53c9\u8868\u8ba1\u7b97\u5c0f\u8ba1\u6570\u5f02\u5e38\uff1a" + ex.getMessage());
        }
        return curCount;
    }

    public boolean hasDetailRowCheck(IEntityRow entityRow, List<IEntityRow> childRows, IEntityTable entityTable, DimensionValueSet dataDim) {
        boolean isMaster = true;
        String dimName = null;
        DimensionValueSet entityDim = new DimensionValueSet();
        entityDim.setValue(this.dimName, (Object)entityRow.getEntityKeyData());
        if (dataDim != null && dataDim.getName(0) != null) {
            dimName = dataDim.getName(0);
            isMaster = this.dimNameMasterMap.get(dataDim.getName(0));
        } else {
            for (int i = 0; i < entityDim.size() && (isMaster = this.dimNameMasterMap.get(dimName = entityDim.getName(i)).booleanValue()); ++i) {
            }
        }
        DimensionValueSet dims = new DimensionValueSet(dataDim);
        dims.combine(entityDim);
        IDataRow groupingRow = this.dataTable.findGroupingRow(dims);
        if (groupingRow != null && this.checkRowData(groupingRow) != RowDataType.ALLNULL) {
            return true;
        }
        List rows = isMaster ? this.dataTable.findFuzzyRows(entityDim) : this.dataTable.findDetailRowsByGroupKeyByFirstDimension(entityDim, dimName);
        rows.removeIf(this::notShowNullOrZeroRow);
        if (!CollectionUtils.isEmpty(rows)) {
            return true;
        }
        for (IEntityRow row : childRows) {
            boolean hasRow;
            dims = new DimensionValueSet(dataDim);
            dims.combine(entityDim);
            if (row == null) continue;
            IDataRow dataRow = this.dataTable.findGroupingRow(dims);
            if (dataRow != null && this.checkRowData(dataRow) != RowDataType.ALLNULL) {
                return true;
            }
            List rows2 = null;
            if (dimName.equals(this.pageInfo.dimensionName)) {
                rows2 = this.dataTable.findDetailRowsByGroupKeyByFirstDimension(dims, dimName);
            } else {
                List list = rows2 = isMaster ? this.dataTable.findFuzzyRows(dims) : this.dataTable.findDetailRowsByGroupKey(dims);
            }
            if (!this.block.isShowNullRow()) {
                rows2.removeIf(row1 -> this.checkRowData((IDataRow)row1) == RowDataType.ALLNULL);
            }
            if (!CollectionUtils.isEmpty(rows2)) {
                return true;
            }
            List chlids = entityTable.getChildRows(row.getEntityKeyData());
            if (chlids.size() <= 0 || !(hasRow = this.hasDetailRowCheck(row, chlids, entityTable, dataDim))) continue;
            return true;
        }
        return false;
    }

    public RowDataType checkRowData(IDataRow row) {
        if (row == null) {
            return RowDataType.ALLNULL;
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
                    if (!this.showedFieldsIndex.containsKey(key) || "true".equals((item = this.showedFields.get(index = this.showedFieldsIndex.get(key))).getIsMaster())) continue;
                }
                AbstractData value = row.getValue(i);
                boolean bl = isNullGroupValue = row.getGroupingFlag() >= 0 && "\u2014\u2014".equals(value.getAsObject());
                if (value == null || isNullGroupValue) {
                    ++nullColumn;
                    continue;
                }
                switch (fieldType) {
                    case FIELD_TYPE_DECIMAL: 
                    case FIELD_TYPE_FLOAT: {
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
                logger.error(ex.getMessage());
            }
        }
        if (dataColumn > 0) {
            return RowDataType.DATAS;
        }
        if (zeroColumn > 0) {
            return RowDataType.ALLZERO;
        }
        return RowDataType.ALLNULL;
    }

    public static enum RowDataType {
        ALLNULL,
        ALLZERO,
        DATAS;

    }

    class loadItemParams
    implements Cloneable {
        public boolean isPagingLoad;
        public boolean isRememberLoadIndex;
        public boolean isFirstLoad;
        public int dimensionIndex;
        public DimensionValueSet parentDimValue;
        public int depth;
        public int dimSize;
        public int lasteDimensionIndex;
        public String loadKey;

        loadItemParams() {
        }

        public boolean getIsLasteDimension() {
            return this.dimensionIndex == this.lasteDimensionIndex;
        }

        protected loadItemParams clone() throws CloneNotSupportedException {
            return (loadItemParams)super.clone();
        }
    }

    class EntityCache {
        public IEntityTable entityTable;
        public EntityViewDefine entityView;
        public DimensionValueSet dataTime;

        EntityCache() {
        }
    }

    class InitCacheParams
    implements Cloneable {
        public int depth;
        public boolean needChild;
        public String dimName;
        public Map<Integer, Integer> nextIndex;
        public boolean isRememberLoadIndex;
        public boolean isTree;
        public boolean isNotTreeStruct;
        public boolean isMaster;
        public DimensionItemType itemType;
        public boolean isShowSum;
        public boolean isSumRowInFront;
        public boolean isShowTotalSum;

        InitCacheParams() {
        }

        protected InitCacheParams clone() throws CloneNotSupportedException {
            return (InitCacheParams)super.clone();
        }
    }

    static enum QueryMode {
        SIMPLEQUERY,
        DATAQUERY;

    }
}

