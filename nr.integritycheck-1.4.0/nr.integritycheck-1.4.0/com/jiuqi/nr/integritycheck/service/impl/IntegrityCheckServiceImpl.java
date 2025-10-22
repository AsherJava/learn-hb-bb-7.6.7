/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult
 *  com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService
 *  com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper
 *  com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo
 *  com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.tag.management.entityimpl.TagDefine
 *  com.jiuqi.nr.tag.management.environment.BaseTagContextData
 *  com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData
 *  com.jiuqi.nr.tag.management.service.ITagManagementConfigService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaPageDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.integritycheck.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult;
import com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService;
import com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.asynctask.CanceledInfo;
import com.jiuqi.nr.integritycheck.common.AddTagParam;
import com.jiuqi.nr.integritycheck.common.CheckErrDesContext;
import com.jiuqi.nr.integritycheck.common.ErrorDesInfo;
import com.jiuqi.nr.integritycheck.common.ErrorDesShowInfo;
import com.jiuqi.nr.integritycheck.common.ErrorFormUnitInfo;
import com.jiuqi.nr.integritycheck.common.ExpErrDesInfo;
import com.jiuqi.nr.integritycheck.common.ExpErrDesParam;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckParam;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityDataInfo;
import com.jiuqi.nr.integritycheck.common.MapWrapper;
import com.jiuqi.nr.integritycheck.common.PageTableICRInfo;
import com.jiuqi.nr.integritycheck.common.QueryICRParam;
import com.jiuqi.nr.integritycheck.common.ResultInfo;
import com.jiuqi.nr.integritycheck.common.TableHeaderColumnInfo;
import com.jiuqi.nr.integritycheck.common.TableICRInfo;
import com.jiuqi.nr.integritycheck.dao.IntegrityCheckResDao;
import com.jiuqi.nr.integritycheck.errdescheck.IErrDesCheckService;
import com.jiuqi.nr.integritycheck.errdescheck.param.GetRuleGroupContext;
import com.jiuqi.nr.integritycheck.helper.FormOperationHelper;
import com.jiuqi.nr.integritycheck.helper.ICSplitTableHelper;
import com.jiuqi.nr.integritycheck.helper.NRDTSplitTableHelper;
import com.jiuqi.nr.integritycheck.listener.IntegrityCheckListener;
import com.jiuqi.nr.integritycheck.listener.param.CheckEvent;
import com.jiuqi.nr.integritycheck.message.DimAndFormMessage;
import com.jiuqi.nr.integritycheck.service.IIntegrityCheckService;
import com.jiuqi.nr.integritycheck.utils.ErrDesUtil;
import com.jiuqi.nr.integritycheck.utils.ICRSqlJoinProvider;
import com.jiuqi.nr.integritycheck.utils.TmpTableUtils;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData;
import com.jiuqi.nr.tag.management.service.ITagManagementConfigService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaPageDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IntegrityCheckServiceImpl
implements IIntegrityCheckService {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityCheckServiceImpl.class);
    @Autowired
    private FormOperationHelper fHelper;
    @Autowired
    private TmpTableUtils tempDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ICSplitTableHelper icSplitTableHelper;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired(required=false)
    private List<IntegrityCheckListener> listeners;
    @Autowired(required=false)
    private NRDTSplitTableHelper nrdtSplitTableHelper;
    @Autowired
    private IntegrityCheckResDao integrityCheckResDao;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IDataCheckCommonService dataCheckCommonService;
    @Autowired
    private IErrDesCheckService errDesCheckService;
    @Autowired
    private ContentCheckServiceFactory contentCheckServiceFactory;
    @Resource
    private ITagManagementConfigService tagManagementConfigService;
    @Value(value="${jiuqi.nr.icr.checkBatch:false}")
    private boolean icrCheckBatch;
    @Value(value="${jiuqi.nr.icr.checkBatchNum:1000}")
    private int icrCheckBatchNum;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public IntegrityCheckResInfo integrityCheck(IntegrityCheckParam param, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        IntegrityCheckResInfo integrityCheckResInfo;
        block36: {
            ITempTable tempTable;
            boolean enableNrdb;
            String tableName;
            IntegrityCheckResInfo integrityCheckResInfo2;
            block34: {
                IntegrityCheckResInfo integrityCheckResInfo3;
                block35: {
                    Object asyncDetial;
                    HashMap<String, Map<MapWrapper, String>> formUnitsZero;
                    List<String> formKeys;
                    String masterDimName;
                    Map<String, Object> entityTableMap;
                    block32: {
                        IntegrityCheckResInfo canceledInfo3;
                        block33: {
                            List dimensionCombinations;
                            String periodDimValue;
                            DimensionCollection dims;
                            block30: {
                                Object dimValue;
                                block31: {
                                    integrityCheckResInfo2 = new IntegrityCheckResInfo();
                                    if (null == asyncTaskMonitor) {
                                        return null;
                                    }
                                    dims = param.getDims();
                                    if (CollectionUtils.isEmpty(dims.getDimensionCombinations())) {
                                        return null;
                                    }
                                    String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
                                    if (StringUtils.hasText(contextEntityId)) {
                                        integrityCheckResInfo2.setContextEntityId(contextEntityId);
                                    }
                                    TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
                                    tableName = null;
                                    if (null != this.listeners) {
                                        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                                        tableName = this.icSplitTableHelper.getICRSplitTableName(dataScheme);
                                        CheckEvent event = new CheckEvent(param.getBatchId(), tableName);
                                        for (IntegrityCheckListener listener : this.listeners) {
                                            listener.beforeCheck(event);
                                        }
                                    }
                                    periodDimValue = (String)dims.combineDim().getValue("DATATIME");
                                    asyncTaskMonitor.progressAndMessage(0.01, "running");
                                    enableNrdb = this.nrdbHelper.isEnableNrdb();
                                    tempTable = null;
                                    entityTableMap = this.fHelper.getEntityTable(param.getFormSchemeKey(), periodDimValue, asyncTaskMonitor);
                                    EntityViewData masterEntityView = (EntityViewData)entityTableMap.get(this.fHelper.masterEntityViewKey);
                                    masterDimName = masterEntityView.getDimensionName();
                                    DataCheckDimInfo dataCheckDimInfo = this.dataCheckCommonService.queryDims(param.getTaskKey(), param.getFormSchemeKey(), param.getDims());
                                    integrityCheckResInfo2.setDimNameEntityIdExistCurrencyAttributeMap(dataCheckDimInfo.getDimNameEntityIdExistCurrencyAttributeMap());
                                    integrityCheckResInfo2.setDimNameTitleMap(dataCheckDimInfo.getDimNameTitleMap());
                                    integrityCheckResInfo2.setDimRange(dataCheckDimInfo.getDimRange());
                                    integrityCheckResInfo2.setDimNameIsShowMap(dataCheckDimInfo.getDimNameIsShowMap());
                                    HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dims);
                                    for (int i = 0; i < dimensionValueSet.size(); ++i) {
                                        String dimName = dimensionValueSet.getName(i);
                                        if (null != dataCheckDimInfo.getDimNameIsShowMap() && null != dataCheckDimInfo.getDimNameIsShowMap().get(dimName) && !((Boolean)dataCheckDimInfo.getDimNameIsShowMap().get(dimName)).booleanValue()) continue;
                                        dimValue = dimensionValueSet.getValue(i);
                                        DimensionValue dimensionValue = new DimensionValue();
                                        dimensionValue.setName(dimName);
                                        if (dimValue instanceof String) {
                                            dimensionValue.setValue((String)dimValue);
                                        } else if (dimValue instanceof List) {
                                            dimensionValue.setValue(String.join((CharSequence)";", (List)dimValue));
                                        }
                                        dimensionSet.put(dimName, dimensionValue);
                                    }
                                    integrityCheckResInfo2.setDimensionSet(dimensionSet);
                                    formKeys = this.getForms(param.getFormKeys(), param.getFormSchemeKey(), asyncTaskMonitor);
                                    param.setFormKeys(formKeys);
                                    integrityCheckResInfo2.setFormKeys(formKeys);
                                    if (!asyncTaskMonitor.isCancel()) break block30;
                                    CanceledInfo canceledInfo2 = new CanceledInfo();
                                    canceledInfo2.setFormNum(0);
                                    asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo2);
                                    dimValue = integrityCheckResInfo2;
                                    if (enableNrdb) break block31;
                                    this.dropTempTable(tempTable);
                                }
                                return dimValue;
                            }
                            if (!this.icrCheckBatch) {
                                dimensionCombinations = dims.getDimensionCombinations();
                                if (!enableNrdb) {
                                    tempTable = this.tempDao.createTempTableAndInsertData(dimensionCombinations);
                                    asyncTaskMonitor.progressAndMessage(0.15, "running");
                                    formUnitsZero = this.fHelper.queryFormIsEmptyZero(tempTable.getTableName(), param, masterDimName, periodDimValue, asyncTaskMonitor, 0.15, 0.8);
                                } else {
                                    formUnitsZero = this.fHelper.queryFormIsEmptyZero(param, dimensionCombinations, asyncTaskMonitor, 0.15, 0.8);
                                }
                            } else {
                                formUnitsZero = new HashMap();
                                dimensionCombinations = dims.getDimensionCombinations();
                                int batch = dimensionCombinations.size() / this.icrCheckBatchNum;
                                int remainder = dimensionCombinations.size() % this.icrCheckBatchNum;
                                if (!enableNrdb) {
                                    tempTable = this.tempDao.createTempTable(dimensionCombinations);
                                }
                                if (batch > 0) {
                                    double ratio = 0.0;
                                    double batchProgress = 0.8 / (double)(batch + 1);
                                    for (int i = 0; i < batch; ++i) {
                                        List<DimensionCombination> dimComsBatch = dimensionCombinations.subList(i * this.icrCheckBatchNum, (i + 1) * this.icrCheckBatchNum);
                                        this.executeCheck(param, asyncTaskMonitor, (ratio += batchProgress) - batchProgress + 0.15, ratio, enableNrdb, tempTable, masterDimName, periodDimValue, formUnitsZero, dimComsBatch);
                                    }
                                    if (remainder != 0) {
                                        List<DimensionCombination> dimComsBatch = dimensionCombinations.subList(batch * this.icrCheckBatchNum, dimensionCombinations.size());
                                        this.executeCheck(param, asyncTaskMonitor, 0.95 - batchProgress, 0.8, enableNrdb, tempTable, masterDimName, periodDimValue, formUnitsZero, dimComsBatch);
                                    }
                                } else {
                                    this.executeCheck(param, asyncTaskMonitor, 0.15, 0.8, enableNrdb, tempTable, masterDimName, periodDimValue, formUnitsZero, dimensionCombinations);
                                }
                            }
                            if (!asyncTaskMonitor.isCancel()) break block32;
                            asyncDetial = this.asyncTaskManager.queryDetail(asyncTaskMonitor.getTaskId());
                            if (null == asyncDetial) {
                                CanceledInfo canceledInfo3 = new CanceledInfo();
                                canceledInfo3.setFormNum(formKeys.size());
                                asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo3);
                            }
                            canceledInfo3 = integrityCheckResInfo2;
                            if (enableNrdb) break block33;
                            this.dropTempTable(tempTable);
                        }
                        return canceledInfo3;
                    }
                    asyncTaskMonitor.progressAndMessage(0.95, "running");
                    this.processCheckTableData(param, formUnitsZero, entityTableMap, masterDimName, integrityCheckResInfo2, asyncTaskMonitor);
                    if (!asyncTaskMonitor.isCancel()) break block34;
                    asyncDetial = this.asyncTaskManager.queryDetail(asyncTaskMonitor.getTaskId());
                    if (null == asyncDetial) {
                        CanceledInfo canceledInfo = new CanceledInfo();
                        canceledInfo.setFormNum(formKeys.size());
                        asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
                    }
                    integrityCheckResInfo3 = integrityCheckResInfo2;
                    if (enableNrdb) break block35;
                    this.dropTempTable(tempTable);
                }
                return integrityCheckResInfo3;
            }
            try {
                if (null != this.listeners) {
                    CheckEvent event = new CheckEvent(param.getBatchId(), tableName);
                    for (IntegrityCheckListener listener : this.listeners) {
                        listener.afterCheck(event);
                    }
                }
                integrityCheckResInfo = integrityCheckResInfo2;
                if (enableNrdb) break block36;
            }
            catch (Exception e) {
                try {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
                catch (Throwable throwable) {
                    if (!enableNrdb) {
                        this.dropTempTable(tempTable);
                    }
                    throw throwable;
                }
            }
            this.dropTempTable(tempTable);
        }
        return integrityCheckResInfo;
    }

    private void executeCheck(IntegrityCheckParam param, AsyncTaskMonitor asyncTaskMonitor, double curProgress, double ratio, boolean enableNrdb, ITempTable tempTable, String masterDimName, String periodDimValue, Map<String, Map<MapWrapper, String>> formUnitsZero, List<DimensionCombination> dimComsBatch) throws Exception {
        if (!enableNrdb) {
            this.tempDao.deleteTempTableData(tempTable);
            this.tempDao.insertTempTableData(tempTable, dimComsBatch);
            asyncTaskMonitor.progressAndMessage(0.15, "running");
            Map<String, Map<MapWrapper, String>> batchFormUnitsZero = this.fHelper.queryFormIsEmptyZero(tempTable.getTableName(), param, masterDimName, periodDimValue, asyncTaskMonitor, curProgress, ratio);
            this.mergeMaps(formUnitsZero, batchFormUnitsZero);
        } else {
            Map<String, Map<MapWrapper, String>> batchFormUnitsZero = this.fHelper.queryFormIsEmptyZero(param, dimComsBatch, asyncTaskMonitor, curProgress, ratio);
            this.mergeMaps(formUnitsZero, batchFormUnitsZero);
        }
    }

    private void mergeMaps(Map<String, Map<MapWrapper, String>> target, Map<String, Map<MapWrapper, String>> map) {
        for (Map.Entry<String, Map<MapWrapper, String>> entry : map.entrySet()) {
            target.merge(entry.getKey(), new HashMap<MapWrapper, String>(entry.getValue()), (oldValue, newValue) -> {
                oldValue.putAll(newValue);
                return oldValue;
            });
        }
    }

    private List<String> getForms(List<String> selectedForms, String formSchemeKey, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        List<String> allForms = this.fHelper.getFormsAllList(formSchemeKey, asyncTaskMonitor);
        List<String> forms = selectedForms.isEmpty() ? allForms : allForms.stream().filter(selectedForms::contains).collect(Collectors.toList());
        asyncTaskMonitor.progressAndMessage(0.1, "running");
        return forms;
    }

    private void processCheckTableData(IntegrityCheckParam param, Map<String, Map<MapWrapper, String>> formUnitsZero, Map<String, Object> entityTableMap, String masterDimName, IntegrityCheckResInfo integrityCheckResInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        ArrayList<MapWrapper> dimMapKey = new ArrayList<MapWrapper>();
        List dimensionCombinations = param.getDims().getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
            for (Object dimName : dimensionCombination.getNames()) {
                dimNameValueMap.put((String)dimName, (String)dimensionCombination.getValue((String)dimName));
            }
            dimMapKey.add(new MapWrapper(dimNameValueMap));
        }
        List<String> formKeys = param.getFormKeys();
        Map<MapWrapper, Map<String, TableICRInfo>> tableICRInfoMap = this.getSaveData(formUnitsZero, dimMapKey, formKeys, entityTableMap, masterDimName);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(param.getTaskKey(), param.getFormSchemeKey());
        IBatchAccessResult visitAccess = dataAccessService.getVisitAccess(param.getDims(), formKeys);
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
            for (String dimName : dimensionCombination.getNames()) {
                dimNameValueMap.put(dimName, (String)dimensionCombination.getValue(dimName));
            }
            MapWrapper mapWrapper = new MapWrapper(dimNameValueMap);
            Object value = dimensionCombination.getValue("MD_CURRENCY");
            if (null != value && !"CNY".equals(value.toString()) && !"RMB".equals(value.toString())) continue;
            for (String form : formKeys) {
                IAccessResult access = visitAccess.getAccess(dimensionCombination, form);
                if (access.haveAccess() || !tableICRInfoMap.containsKey(mapWrapper) || !tableICRInfoMap.get(mapWrapper).containsKey(form)) continue;
                TableICRInfo tableICRInfo = tableICRInfoMap.get(mapWrapper).get(form);
                tableICRInfo.setResult("--");
            }
        }
        if (tableICRInfoMap.isEmpty()) {
            return;
        }
        if (asyncTaskMonitor.isCancel()) {
            CanceledInfo canceledInfo = new CanceledInfo();
            canceledInfo.setFormNum(formKeys.size());
            asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
            return;
        }
        this.saveCheckResult(param, tableICRInfoMap, integrityCheckResInfo);
        asyncTaskMonitor.progressAndMessage(0.99, "running");
    }

    private Map<MapWrapper, Map<String, TableICRInfo>> getSaveData(Map<String, Map<MapWrapper, String>> formUnitstemp, List<MapWrapper> dimLists, List<String> selectedForms, Map<String, Object> entityTableMap, String masterDimName) {
        HashMap<MapWrapper, Map<String, TableICRInfo>> dwKeyTableICRMap = new HashMap<MapWrapper, Map<String, TableICRInfo>>();
        IEntityTable entityTable = (IEntityTable)entityTableMap.get("entityTable");
        EntityViewData masterEntityView = (EntityViewData)entityTableMap.get("masterEntityView");
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterEntityView.getKey());
        IEntityAttribute bblxField = entityModel.getBblxField();
        for (MapWrapper dimMapKey : dimLists) {
            boolean canAdd = false;
            for (String fromKey : selectedForms) {
                Map<String, TableICRInfo> formKeyTableICRInfoMap;
                TableICRInfo tableICRInfo2 = new TableICRInfo();
                tableICRInfo2.setDimNameValueMap(dimMapKey.getDimNameValueMap());
                tableICRInfo2.setFormKey(fromKey);
                if (dwKeyTableICRMap.containsKey(dimMapKey)) {
                    formKeyTableICRInfoMap = (Map)dwKeyTableICRMap.get(dimMapKey);
                    formKeyTableICRInfoMap.put(fromKey, tableICRInfo2);
                } else {
                    formKeyTableICRInfoMap = new HashMap();
                    formKeyTableICRInfoMap.put(fromKey, tableICRInfo2);
                    dwKeyTableICRMap.put(dimMapKey, formKeyTableICRInfoMap);
                }
                if (formUnitstemp.containsKey(fromKey) && formUnitstemp.get(fromKey).containsKey(dimMapKey)) {
                    String vaule = formUnitstemp.get(fromKey).get(dimMapKey);
                    canAdd = true;
                    tableICRInfo2.setResult(vaule);
                    continue;
                }
                tableICRInfo2.setResult("");
            }
            if (!canAdd) continue;
            String dmKey = dimMapKey.getDimNameValueMap().get(masterDimName);
            String bblx = "";
            if (null != entityTable) {
                IEntityRow entityRow = entityTable.findByEntityKey(dmKey);
                if (null == entityRow || null == bblxField) continue;
                if (!bblxField.getCode().isEmpty() && null != entityRow.getAsString(bblxField.getCode())) {
                    bblx = entityRow.getAsString(bblxField.getCode());
                }
            }
            Map formKeyTableICRInfoMap = (Map)dwKeyTableICRMap.get(dimMapKey);
            if (!"1".equals(bblx)) {
                formKeyTableICRInfoMap.forEach((formKey, tableICRInfo) -> tableICRInfo.setBblx(0));
                continue;
            }
            formKeyTableICRInfoMap.forEach((formKey, tableICRInfo) -> tableICRInfo.setBblx(1));
        }
        return dwKeyTableICRMap;
    }

    private void saveCheckResult(IntegrityCheckParam param, Map<MapWrapper, Map<String, TableICRInfo>> tableICRInfoMap, IntegrityCheckResInfo integrityCheckResInfo) throws Exception {
        TableModelDefine icrTable;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.icSplitTableHelper.getICRSplitTableName(dataScheme);
        if (null != this.nrdtSplitTableHelper) {
            tableName = this.nrdtSplitTableHelper.getSplitTableName(tableName, param.getBatchId());
        }
        if (null == (icrTable = this.dataModelService.getTableModelDefineByName(tableName))) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return;
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icrFields = this.dataModelService.getColumnModelDefinesByTable(icrTable.getID());
        for (ColumnModelDefine icrField : icrFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icrField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            List columns = queryModel.getColumns();
            int insertRowCount = 0;
            for (MapWrapper mapWrapper : tableICRInfoMap.keySet()) {
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                Map<String, String> dimNameValueMap = mapWrapper.getDimNameValueMap();
                dimNameValueMap.forEach((arg_0, arg_1) -> ((DimensionValueSet)dimensionValueSet).setValue(arg_0, arg_1));
                Map<String, TableICRInfo> formKeyTableICRInfoMap = tableICRInfoMap.get(mapWrapper);
                for (String formKey : formKeyTableICRInfoMap.keySet()) {
                    TableICRInfo tableICRInfo = formKeyTableICRInfoMap.get(formKey);
                    INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                    String id = UUID.randomUUID().toString();
                    int resultCode = 0;
                    String result = tableICRInfo.getResult();
                    if ("\u7a7a\u8868".equals(result)) {
                        resultCode = 1;
                    } else if ("\u96f6\u8868".equals(result)) {
                        resultCode = 2;
                    } else if ("--".equals(result)) {
                        resultCode = 3;
                    } else if (!StringUtils.hasText(result)) {
                        resultCode = 0;
                    }
                    String recid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey).toString();
                    for (int i = 0; i < columns.size(); ++i) {
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("ID")) {
                            iNvwaDataRow.setValue(i, (Object)id);
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("TASK_KEY")) {
                            iNvwaDataRow.setValue(i, (Object)param.getTaskKey());
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("BBLX")) {
                            iNvwaDataRow.setValue(i, (Object)tableICRInfo.getBblx());
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FORM_KEY")) {
                            iNvwaDataRow.setValue(i, (Object)tableICRInfo.getFormKey());
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("ICR_RESULT")) {
                            iNvwaDataRow.setValue(i, (Object)resultCode);
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("RECID")) {
                            iNvwaDataRow.setValue(i, (Object)recid);
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("BATCHID")) {
                            iNvwaDataRow.setValue(i, (Object)param.getBatchId());
                            continue;
                        }
                        String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(i)).getColumnModel());
                        if (null == dimensionValueSet.getValue(dimensionName)) continue;
                        iNvwaDataRow.setValue(i, dimensionValueSet.getValue(dimensionName));
                    }
                    if (++insertRowCount < 5000) continue;
                    iNvwaDataUpdator.commitChanges(context);
                    insertRowCount = 0;
                }
            }
            if (insertRowCount > 0) {
                iNvwaDataUpdator.commitChanges(context);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw e;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        this.integrityCheckResDao.insert(param.getBatchId(), taskDefine.getDataScheme(), objectMapper.writeValueAsString((Object)integrityCheckResInfo));
    }

    private void dropTempTable(ITempTable tempTable) throws Exception {
        if (tempTable != null) {
            try {
                this.tempDao.dropTempTable(tempTable);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public ResultInfo queryICResult(QueryICRParam param) throws Exception {
        IntegrityDataInfo integrityDataInfo = new IntegrityDataInfo();
        integrityDataInfo.setTaskKey(param.getTaskKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String masterDimName = entityDefine.getDimensionName();
        integrityDataInfo.setMasterDimName(masterDimName);
        String period = "";
        ArrayList<String> dwKeyList = new ArrayList<String>();
        DimensionCollection dimensionCollection = param.getDimensionCollection();
        for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
            dwKeyList.add((String)dimensionCombination.getValue(masterDimName));
            if (StringUtils.hasText(period)) continue;
            period = (String)dimensionCombination.getValue("DATATIME");
        }
        integrityDataInfo.setFmKeyList(param.getFormKeys());
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(param.getFormSchemeKey(), period);
        IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, param.getFormSchemeKey(), false);
        LinkedHashMap<String, String> dwCodeKey = new LinkedHashMap<String, String>();
        for (String dwKey : dwKeyList) {
            IEntityRow entityKey = entityTable.findByEntityKey(dwKey);
            dwCodeKey.put(entityKey.getCode(), dwKey);
        }
        integrityDataInfo.setDwCodeKey(dwCodeKey);
        ArrayList dimTitles = new ArrayList();
        LinkedHashMap<String, IEntityTable> dimNameTableMap = new LinkedHashMap<String, IEntityTable>();
        String finalPeriod = period;
        this.sceneDimQueryAndForEach(param.getTaskKey(), (sceneDimKey, sceneEntity) -> {
            try {
                dimTitles.add(sceneEntity.getTitle());
                IEntityQuery dimQuery = this.entityQueryHelper.getDimEntityQuery(sceneDimKey, finalPeriod);
                IEntityTable dimEntityTable = this.entityQueryHelper.buildEntityTable(dimQuery, param.getFormSchemeKey(), true);
                dimNameTableMap.put(sceneEntity.getDimensionName(), dimEntityTable);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
        StringBuilder headTitle = new StringBuilder();
        headTitle.append("\u5355\u4f4d\u4ee3\u7801").append(" | ").append("\u5355\u4f4d\u540d\u79f0");
        for (String dimTitle : dimTitles) {
            headTitle.append(" | ").append(dimTitle);
        }
        LinkedHashMap<String, String> formCodeKey = new LinkedHashMap<String, String>();
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList<String> columns = new ArrayList<String>();
        columns.add(headTitle.toString());
        columns.add("\u7f3a\u8868\u6570\u91cf");
        for (String key : param.getFormKeys()) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(key);
            if (formDefine != null) {
                columns.add(formDefine.getFormCode() + " | " + formDefine.getTitle());
                formCodeKey.put(formDefine.getFormCode(), formDefine.getKey());
                formKeys.add(formDefine.getKey());
                continue;
            }
            columns.add(key);
        }
        PageTableICRInfo pageTableICRInfo = this.pageQueryCheckResult(param);
        integrityDataInfo.setFormCodeKey(formCodeKey);
        integrityDataInfo.setHeaderList(columns);
        integrityDataInfo.setFormLackMap(pageTableICRInfo.getFormLackMap());
        integrityDataInfo.setPagerInfo(pageTableICRInfo.getPagerInfo());
        integrityDataInfo.setLackCount(pageTableICRInfo.getLackCount());
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setEntityId(taskDefine.getDw());
        resultInfo.setIntegrityDataInfo(integrityDataInfo);
        List<List<String>> rows = this.buildRowData(masterDimName, entityTable, dimNameTableMap, pageTableICRInfo, formKeys);
        integrityDataInfo.setRowDatas(rows);
        HashMap<String, ErrorDesShowInfo> tableICDInfos = new HashMap<String, ErrorDesShowInfo>();
        for (TableICRInfo tableICRInfo : pageTableICRInfo.getTableICRInfos()) {
            if (null == tableICRInfo.getErrorDesInfo() || !StringUtils.hasText(tableICRInfo.getErrorDesInfo().getDescription())) continue;
            String key = tableICRInfo.getDimNameValueMap().get(masterDimName) + tableICRInfo.getFormKey();
            ErrorDesShowInfo errorDesShowInfo = new ErrorDesShowInfo();
            errorDesShowInfo.setUpdater(tableICRInfo.getErrorDesInfo().getUpdater());
            errorDesShowInfo.setUpdateTime(tableICRInfo.getErrorDesInfo().getUpdateTime());
            errorDesShowInfo.setDescription(tableICRInfo.getErrorDesInfo().getDescription());
            tableICDInfos.put(key, errorDesShowInfo);
        }
        resultInfo.setTableICDInfos(tableICDInfos);
        resultInfo.setBatchId(param.getBatchId());
        return resultInfo;
    }

    private void sceneDimQueryAndForEach(String taskKey, BiConsumer<String, IEntityDefine> sceneEntityConsumer) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        String dimsStr = taskDefine.getDims();
        if (StringUtils.hasText(dimsStr)) {
            String[] dimArrays;
            List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
            HashMap<String, DataDimension> dataDimensionMap = new HashMap<String, DataDimension>();
            IEntityModel entityModel = null;
            for (DataDimension dataDimension : dataSchemeDimension) {
                dataDimensionMap.put(dataDimension.getDimKey(), dataDimension);
                if (DimensionType.UNIT != dataDimension.getDimensionType()) continue;
                entityModel = this.entityMetaService.getEntityModel(dataDimension.getDimKey());
            }
            for (String dimArray : dimArrays = dimsStr.split(";")) {
                IEntityAttribute attribute;
                String refField;
                IEntityDefine dimEntityDefine = this.entityMetaService.queryEntity(dimArray);
                if (null == dimEntityDefine) continue;
                String string = refField = null == dataDimensionMap.get(dimArray) ? "" : ((DataDimension)dataDimensionMap.get(dimArray)).getDimAttribute();
                if (null != entityModel && StringUtils.hasText(refField) && null != (attribute = entityModel.getAttribute(refField)) && !attribute.isMultival()) continue;
                sceneEntityConsumer.accept(dimArray, dimEntityDefine);
            }
        }
    }

    private List<List<String>> buildRowData(String masterDimName, IEntityTable unitEntityTable, LinkedHashMap<String, IEntityTable> dimNameTableMap, PageTableICRInfo pageTableICRInfo, List<String> formKeys) {
        ArrayList<List<String>> result = new ArrayList<List<String>>();
        List<TableICRInfo> tableICRInfos = pageTableICRInfo.getTableICRInfos();
        Map<String, Integer> unitLackFormMap = pageTableICRInfo.getUnitLackFormMap();
        Map tableICRInfoMap = tableICRInfos.stream().collect(Collectors.groupingBy(info -> info.getDimNameValueMap().get(masterDimName), LinkedHashMap::new, Collectors.toList()));
        int rowIndex = 1;
        for (String unitCode : tableICRInfoMap.keySet()) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(String.valueOf(rowIndex));
            row.add(unitCode);
            IEntityRow unitEntityRow = unitEntityTable.findByEntityKey(unitCode);
            row.add(unitEntityRow.getTitle());
            List unitICRInfos = (List)tableICRInfoMap.get(unitCode);
            Map<String, List<TableICRInfo>> formUnitICRInfos = unitICRInfos.stream().collect(Collectors.groupingBy(TableICRInfo::getFormKey));
            for (String dimKey : dimNameTableMap.keySet()) {
                IEntityTable dimEntityTable = dimNameTableMap.get(dimKey);
                IEntityRow dimEntityRow = dimEntityTable.findByEntityKey(((TableICRInfo)unitICRInfos.get(0)).getDimNameValueMap().get(dimKey));
                row.add(dimEntityRow.getTitle());
            }
            row.add(String.valueOf(unitLackFormMap.getOrDefault(unitCode, 0)));
            for (String formKey : formKeys) {
                row.add(formUnitICRInfos.get(formKey).get(0).getResult());
            }
            result.add(row);
            ++rowIndex;
        }
        return result;
    }

    @Override
    public PageTableICRInfo pageQueryCheckResult(QueryICRParam param) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
        Map<String, List<String>> dimNameValueMap = this.getDimNameValueMap(param);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String masterDimName = entityDefine.getDimensionName();
        boolean enableNrdb = this.nrdbHelper.isEnableNrdb();
        if (enableNrdb) {
            return this.queryCheckResultEnableNrdb(taskDefine.getDataScheme(), masterDimName, dimNameValueMap, param);
        }
        return this.queryCheckResultUnenableNrdb(taskDefine.getDataScheme(), masterDimName, dimNameValueMap, param);
    }

    @Override
    public List<ErrorFormUnitInfo> queryErrorFormUnit(QueryICRParam param) {
        ArrayList<ErrorFormUnitInfo> result = new ArrayList<ErrorFormUnitInfo>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        Map<String, List<String>> dimNameValueMap = this.getDimNameValueMap(param);
        QueryTableInfo queryTableInfo = this.getQueryTableInfo(param.getBatchId(), dataScheme);
        NvwaQueryModel unitSubQueryModel = new NvwaQueryModel();
        unitSubQueryModel.setMainTableName(queryTableInfo.icrTableName);
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(queryTableInfo.icrTableId);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(queryTableInfo.icrTableName);
        for (ColumnModelDefine icrField : columnModelDefines) {
            String dimensionName;
            String code;
            switch (code = icrField.getCode()) {
                case "RECID": 
                case "MDCODE": 
                case "ICR_RESULT": {
                    unitSubQueryModel.getColumns().add(new NvwaQueryColumn(icrField));
                    break;
                }
                case "BATCHID": {
                    unitSubQueryModel.getColumnFilters().put(icrField, param.getBatchId());
                    break;
                }
                case "FORM_KEY": {
                    unitSubQueryModel.getColumnFilters().put(icrField, param.getFormKeys());
                }
            }
            if (null == dimNameValueMap.get(dimensionName = dimensionChanger.getDimensionName(icrField))) continue;
            if ("ADJUST".equals(dimensionName)) {
                unitSubQueryModel.getColumnFilters().put(icrField, "0");
                continue;
            }
            unitSubQueryModel.getColumnFilters().put(icrField, dimNameValueMap.get(dimensionName));
        }
        unitSubQueryModel.setFilter("ICR_RESULT <> 0");
        int recideColIndex = IntStream.range(0, unitSubQueryModel.getColumns().size()).filter(i -> ((NvwaQueryColumn)unitSubQueryModel.getColumns().get(i)).getColumnModel().getCode().equals("RECID")).findFirst().orElse(-1);
        int unitCodeColIndex = IntStream.range(0, unitSubQueryModel.getColumns().size()).filter(i -> ((NvwaQueryColumn)unitSubQueryModel.getColumns().get(i)).getColumnModel().getCode().equals("MDCODE")).findFirst().orElse(-1);
        int resultColIndex = IntStream.range(0, unitSubQueryModel.getColumns().size()).filter(i -> ((NvwaQueryColumn)unitSubQueryModel.getColumns().get(i)).getColumnModel().getCode().equals("ICR_RESULT")).findFirst().orElse(-1);
        try {
            MemoryDataSet dataRows;
            HashMap recidRowMap = new HashMap();
            DataAccessContext subQueryContext = this.buildDataAccessContext(dataScheme, false);
            PageInfo pageInfo = param.getPageInfo();
            if (pageInfo == null) {
                INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(unitSubQueryModel);
                dataRows = dataAccess.executeQuery(subQueryContext);
            } else {
                INvwaPageDataAccess pageDataAccess = this.nvwaDataAccessProvider.createPageDataAccess(unitSubQueryModel);
                int pageBeginIndex = (pageInfo.getPageIndex() - 1) * pageInfo.getRowsPerPage();
                int pageEndIndex = pageBeginIndex + pageInfo.getRowsPerPage();
                dataRows = pageDataAccess.executeQuery(subQueryContext, pageBeginIndex, pageEndIndex);
            }
            dataRows.forEach(row -> recidRowMap.put(row.getString(recideColIndex), row));
            IEntityQuery query = this.entityQueryHelper.getEntityQuery(param.getFormSchemeKey(), dimNameValueMap.get("DATATIME").get(0));
            IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, param.getFormSchemeKey(), false);
            ColumnModelDefine errRecidField = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icdTableId, "RECID");
            ColumnModelDefine errorDescField = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icdTableId, "DESCRIPTION");
            DataAccessContext context = this.buildDataAccessContext(dataScheme, false);
            NvwaQueryModel errDesQueryModel = new NvwaQueryModel();
            errDesQueryModel.setMainTableName(queryTableInfo.icdTableName);
            errDesQueryModel.getColumns().add(new NvwaQueryColumn(errRecidField));
            errDesQueryModel.getColumns().add(new NvwaQueryColumn(errorDescField));
            errDesQueryModel.getColumnFilters().put(errRecidField, new ArrayList(recidRowMap.keySet()));
            INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(errDesQueryModel);
            MemoryDataSet errDescDataRows = dataAccess.executeQuery(context);
            HashMap errRecidDescMap = new HashMap();
            errDescDataRows.forEach(row -> errRecidDescMap.put(row.getString(0), row.getString(1)));
            for (String recid : recidRowMap.keySet()) {
                DataRow dataRow = (DataRow)recidRowMap.get(recid);
                ErrorFormUnitInfo errorFormUnitInfo = new ErrorFormUnitInfo();
                String unitCode = dataRow.getString(unitCodeColIndex);
                IEntityRow entityRow = entityTable.findByEntityKey(unitCode);
                errorFormUnitInfo.setUnitCode(unitCode);
                errorFormUnitInfo.setUnitTitle(entityRow.getTitle());
                int resultCode = dataRow.getInt(resultColIndex);
                if (0 == resultCode) {
                    errorFormUnitInfo.setErrorType("");
                } else if (1 == resultCode) {
                    errorFormUnitInfo.setErrorType("\u7a7a\u8868");
                } else if (2 == resultCode) {
                    errorFormUnitInfo.setErrorType("\u96f6\u8868");
                } else if (3 == resultCode) {
                    errorFormUnitInfo.setErrorType("--");
                }
                errorFormUnitInfo.setErrorDes((String)errRecidDescMap.get(recid));
                result.add(errorFormUnitInfo);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private Map<String, List<String>> getDimNameValueMap(QueryICRParam param) {
        HashMap<String, List<String>> dimNameValueMap = new HashMap<String, List<String>>();
        List dimensionCombinations = param.getDimensionCollection().getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            for (String dimName : dimensionCombination.getNames()) {
                String value;
                List dimValues = dimNameValueMap.computeIfAbsent(dimName, k -> new ArrayList());
                if (dimValues.contains(value = (String)dimensionCombination.getValue(dimName))) continue;
                dimValues.add(value);
            }
        }
        return dimNameValueMap;
    }

    public PageTableICRInfo queryCheckResultEnableNrdb(String dataSchemeKey, String masterDimName, Map<String, List<String>> dimNameValueMap, QueryICRParam param) {
        PageTableICRInfo pageTableICRInfo = new PageTableICRInfo();
        try {
            ArrayList<TableICRInfo> tableICRInfos = new ArrayList<TableICRInfo>();
            PagerInfo pagerInfo = new PagerInfo();
            pageTableICRInfo.setTableICRInfos(tableICRInfos);
            pageTableICRInfo.setPagerInfo(pagerInfo);
            String period = dimNameValueMap.get("DATATIME").get(0);
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            ArrayList<DimensionQueryCondition> dimQueryConds = new ArrayList<DimensionQueryCondition>();
            QueryTableInfo queryTableInfo = this.getQueryTableInfo(param.getBatchId(), dataScheme);
            NvwaQueryModel queryModel = this.buildPageQueryModel(param, queryTableInfo, dimNameValueMap, dimQueryConds, false);
            DataAccessContext context = this.buildDataAccessContext(dataScheme, false);
            TempPageQueryResult queryResult = this.pageQuery(param, queryTableInfo, period, context, queryModel, dimQueryConds);
            pageTableICRInfo.setLackCount(queryResult.lackCount);
            pagerInfo.setTotal(queryResult.totalCount);
            pageTableICRInfo.setFormLackMap(queryResult.formLackMap);
            HashMap<String, Integer> unitLackFormMap = new HashMap<String, Integer>();
            pageTableICRInfo.setUnitLackFormMap(unitLackFormMap);
            List columns = queryModel.getColumns();
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(queryTableInfo.icrTableName);
            ArrayList<String> recids = new ArrayList<String>();
            for (DataRow dataRow : queryResult.rows) {
                TableICRInfo tableICRInfo = new TableICRInfo();
                tableICRInfo.setBatchId(param.getBatchId());
                HashMap<String, String> tableICRInfoDims = new HashMap<String, String>();
                tableICRInfo.setDimNameValueMap(tableICRInfoDims);
                block15: for (int j = 0; j < columns.size(); ++j) {
                    switch (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                        case "RECID": {
                            String recid = dataRow.getString(j);
                            tableICRInfo.setRecId(recid);
                            recids.add(recid);
                            continue block15;
                        }
                        case "BBLX": {
                            int bblx = dataRow.getInt(j);
                            tableICRInfo.setBblx(bblx);
                            continue block15;
                        }
                        case "FORM_KEY": {
                            String formKey = dataRow.getString(j);
                            tableICRInfo.setFormKey(formKey);
                            continue block15;
                        }
                        case "ICR_RESULT": {
                            int resultCode = dataRow.getInt(j);
                            if (0 == resultCode) {
                                tableICRInfo.setResult("");
                                continue block15;
                            }
                            if (1 == resultCode) {
                                tableICRInfo.setResult("\u7a7a\u8868");
                                continue block15;
                            }
                            if (2 == resultCode) {
                                tableICRInfo.setResult("\u96f6\u8868");
                                continue block15;
                            }
                            if (3 != resultCode) continue block15;
                            tableICRInfo.setResult("--");
                            continue block15;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel());
                            if (!dimNameValueMap.containsKey(dimensionName)) continue block15;
                            String dimValue = dataRow.getString(j);
                            tableICRInfoDims.put(dimensionName, dimValue);
                        }
                    }
                }
                tableICRInfos.add(tableICRInfo);
            }
            tableICRInfos.stream().filter(info -> !info.getResult().equals("")).forEach(info -> {
                Map<String, String> dimValueMap = info.getDimNameValueMap();
                String unitCode = dimValueMap.get(masterDimName);
                int lackFormCount = unitLackFormMap.computeIfAbsent(unitCode, k -> 0);
                unitLackFormMap.put(unitCode, lackFormCount + 1);
            });
            List<ErrorDesInfo> errorDesInfos = this.queryErrorDesInfo(queryTableInfo.icdTableId, recids);
            block16: for (TableICRInfo tableICRInfo : tableICRInfos) {
                String recId = tableICRInfo.getRecId();
                for (ErrorDesInfo errorDesInfo : errorDesInfos) {
                    if (!recId.equals(errorDesInfo.getRecid())) continue;
                    tableICRInfo.setErrorDesInfo(errorDesInfo);
                    continue block16;
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        return pageTableICRInfo;
    }

    private List<ErrorDesInfo> queryErrorDesInfo(String icdTableId, List<String> recids) {
        ArrayList<ErrorDesInfo> result = new ArrayList<ErrorDesInfo>();
        if (!CollectionUtils.isEmpty(recids)) {
            try {
                NvwaQueryModel queryModel = new NvwaQueryModel();
                this.addICDQueryColumns(icdTableId, queryModel);
                ColumnModelDefine recidColumn = this.dataModelService.getColumnModelDefineByCode(icdTableId, "RECID");
                queryModel.getColumns().add(new NvwaQueryColumn(recidColumn));
                queryModel.getColumnFilters().put(recidColumn, recids);
                INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
                MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
                List columns = queryModel.getColumns();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < dataRows.size(); ++i) {
                    ErrorDesInfo errorDesInfo = new ErrorDesInfo();
                    DataRow item = dataRows.get(i);
                    for (int j = 0; j < columns.size(); ++j) {
                        if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("RECID")) {
                            String recid = item.getString(j);
                            errorDesInfo.setRecid(recid);
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("DESCRIPTION")) {
                            String des = item.getString(j);
                            errorDesInfo.setDescription(des);
                            continue;
                        }
                        if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("UPDATETIME")) {
                            Date updateTime = item.getDate(j).getTime();
                            errorDesInfo.setUpdateTime(formatter.format(updateTime));
                            continue;
                        }
                        if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("UPDATER")) continue;
                        String updater = item.getString(j);
                        errorDesInfo.setUpdater(updater);
                    }
                    result.add(errorDesInfo);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private QueryTableInfo getQueryTableInfo(String batchId, String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        return this.getQueryTableInfo(batchId, dataScheme);
    }

    private QueryTableInfo getQueryTableInfo(String batchId, DataScheme dataScheme) {
        TableModelDefine icrTable;
        QueryTableInfo queryTableInfo = new QueryTableInfo();
        String icrTableName = this.icSplitTableHelper.getICRSplitTableName(dataScheme);
        if (null != this.nrdtSplitTableHelper) {
            icrTableName = this.nrdtSplitTableHelper.getSplitTableName(icrTableName, batchId);
        }
        if (null == (icrTable = this.dataModelService.getTableModelDefineByName(icrTableName))) {
            throw new RuntimeException("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
        }
        String icdTableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
        TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(icdTableName);
        if (null == icdTable) {
            throw new RuntimeException("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
        }
        queryTableInfo.icrTableId = icrTable.getID();
        queryTableInfo.icrTableName = icrTableName;
        queryTableInfo.icdTableId = icdTable.getID();
        queryTableInfo.icdTableName = icdTableName;
        return queryTableInfo;
    }

    private NvwaQueryModel buildPageQueryModel(QueryICRParam param, QueryTableInfo queryTableInfo, Map<String, List<String>> dimNameValueMap, List<DimensionQueryCondition> dimQueryConds, boolean joinICD) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(queryTableInfo.icrTableName);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(queryTableInfo.icrTableName);
        List icrFields = this.dataModelService.getColumnModelDefinesByTable(queryTableInfo.icrTableId);
        for (ColumnModelDefine icrField : icrFields) {
            String dimensionName;
            queryModel.getColumns().add(new NvwaQueryColumn(icrField));
            if (icrField.getCode().equals("BATCHID")) {
                queryModel.getColumnFilters().put(icrField, param.getBatchId());
            }
            if (icrField.getCode().equals("FORM_KEY")) {
                queryModel.getColumnFilters().put(icrField, param.getFormKeys());
            }
            if (null == dimNameValueMap.get(dimensionName = dimensionChanger.getDimensionName(icrField))) continue;
            if ("ADJUST".equals(dimensionName)) {
                queryModel.getColumnFilters().put(icrField, "0");
                dimQueryConds.add(new DimensionQueryCondition(icrField, Collections.singletonList("0")));
                continue;
            }
            queryModel.getColumnFilters().put(icrField, dimNameValueMap.get(dimensionName));
            dimQueryConds.add(new DimensionQueryCondition(icrField, dimNameValueMap.get(dimensionName)));
        }
        if (joinICD) {
            this.addICDQueryColumns(queryTableInfo.icdTableId, queryModel);
        }
        return queryModel;
    }

    private void addICDQueryColumns(String icdTableId, NvwaQueryModel queryModel) {
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTableId);
        for (ColumnModelDefine icdField : icdFields) {
            switch (icdField.getCode()) {
                case "DESCRIPTION": 
                case "UPDATETIME": 
                case "UPDATER": {
                    queryModel.getColumns().add(new NvwaQueryColumn(icdField));
                }
            }
        }
    }

    private DataAccessContext buildDataAccessContext(DataScheme dataScheme, boolean canJoinICD) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        if (canJoinICD) {
            ICRSqlJoinProvider provider = new ICRSqlJoinProvider(dataScheme.getBizCode());
            context.setSqlJoinProvider((ISqlJoinProvider)provider);
        }
        return context;
    }

    public PageTableICRInfo queryCheckResultUnenableNrdb(String dataSchemeKey, String masterDimName, Map<String, List<String>> dimNameValueMap, QueryICRParam param) {
        PageTableICRInfo pageTableICRInfo = new PageTableICRInfo();
        try {
            ArrayList<TableICRInfo> tableICRInfos = new ArrayList<TableICRInfo>();
            PagerInfo pagerInfo = new PagerInfo();
            pageTableICRInfo.setTableICRInfos(tableICRInfos);
            pageTableICRInfo.setPagerInfo(pagerInfo);
            String period = dimNameValueMap.get("DATATIME").get(0);
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            ArrayList<DimensionQueryCondition> dimQueryConds = new ArrayList<DimensionQueryCondition>();
            QueryTableInfo queryTableInfo = this.getQueryTableInfo(param.getBatchId(), dataScheme);
            NvwaQueryModel queryModel = this.buildPageQueryModel(param, queryTableInfo, dimNameValueMap, dimQueryConds, true);
            DataAccessContext context = this.buildDataAccessContext(dataScheme, true);
            TempPageQueryResult queryResult = this.pageQuery(param, queryTableInfo, period, context, queryModel, dimQueryConds);
            pageTableICRInfo.setLackCount(queryResult.lackCount);
            pagerInfo.setTotal(queryResult.totalCount);
            pageTableICRInfo.setFormLackMap(queryResult.formLackMap);
            HashMap<String, Integer> unitLackFormMap = new HashMap<String, Integer>();
            pageTableICRInfo.setUnitLackFormMap(unitLackFormMap);
            List columns = queryModel.getColumns();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(queryTableInfo.icrTableName);
            for (DataRow dataRow : queryResult.rows) {
                TableICRInfo tableICRInfo = new TableICRInfo();
                tableICRInfo.setBatchId(param.getBatchId());
                HashMap<String, String> tableICRInfoDims = new HashMap<String, String>();
                tableICRInfo.setDimNameValueMap(tableICRInfoDims);
                ErrorDesInfo errorDesInfo = new ErrorDesInfo();
                block21: for (int j = 0; j < columns.size(); ++j) {
                    switch (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                        case "RECID": {
                            String recid = dataRow.getString(j);
                            tableICRInfo.setRecId(recid);
                            errorDesInfo.setRecid(recid);
                            continue block21;
                        }
                        case "BBLX": {
                            int bblx = dataRow.getInt(j);
                            tableICRInfo.setBblx(bblx);
                            continue block21;
                        }
                        case "FORM_KEY": {
                            String formKey = dataRow.getString(j);
                            tableICRInfo.setFormKey(formKey);
                            continue block21;
                        }
                        case "ICR_RESULT": {
                            int resultCode = dataRow.getInt(j);
                            if (0 == resultCode) {
                                tableICRInfo.setResult("");
                                continue block21;
                            }
                            if (1 == resultCode) {
                                tableICRInfo.setResult("\u7a7a\u8868");
                                continue block21;
                            }
                            if (2 == resultCode) {
                                tableICRInfo.setResult("\u96f6\u8868");
                                continue block21;
                            }
                            if (3 != resultCode) continue block21;
                            tableICRInfo.setResult("--");
                            continue block21;
                        }
                        case "DESCRIPTION": {
                            String des = dataRow.getString(j);
                            if (!StringUtils.hasText(des)) continue block21;
                            errorDesInfo.setDescription(des);
                            continue block21;
                        }
                        case "UPDATETIME": {
                            Calendar date = dataRow.getDate(j);
                            if (null == date) continue block21;
                            Date updateTime = date.getTime();
                            errorDesInfo.setUpdateTime(formatter.format(updateTime));
                            continue block21;
                        }
                        case "UPDATER": {
                            String updater = dataRow.getString(j);
                            if (!StringUtils.hasText(updater)) continue block21;
                            errorDesInfo.setUpdater(updater);
                            continue block21;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel());
                            if (!dimNameValueMap.containsKey(dimensionName)) continue block21;
                            String dimValue = dataRow.getString(j);
                            tableICRInfoDims.put(dimensionName, dimValue);
                        }
                    }
                }
                if (errorDesInfo.isNotNull()) {
                    tableICRInfo.setErrorDesInfo(errorDesInfo);
                }
                tableICRInfos.add(tableICRInfo);
            }
            tableICRInfos.stream().filter(info -> !info.getResult().equals("")).forEach(info -> {
                Map<String, String> dimValueMap = info.getDimNameValueMap();
                String unitCode = dimValueMap.get(masterDimName);
                int lackFormCount = unitLackFormMap.computeIfAbsent(unitCode, k -> 0);
                unitLackFormMap.put(unitCode, lackFormCount + 1);
            });
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        return pageTableICRInfo;
    }

    private TempPageQueryResult pageQuery(QueryICRParam param, QueryTableInfo queryTableInfo, String datatime, DataAccessContext context, NvwaQueryModel mainQueryModel, List<DimensionQueryCondition> dimQueryConds) throws Exception {
        TempPageQueryResult queryResult = new TempPageQueryResult();
        UnitPageQueryResult unitPageQueryResult = this.subQueryUnitCode(datatime, param, queryTableInfo, dimQueryConds);
        queryResult.totalCount = unitPageQueryResult.total;
        queryResult.lackCount = this.queryLackCount(datatime, param, queryTableInfo, dimQueryConds);
        queryResult.formLackMap = this.queryFormLackCount(datatime, param, queryTableInfo, dimQueryConds);
        ColumnModelDefine mdCodeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "MDCODE");
        mainQueryModel.getColumnFilters().put(mdCodeColumnDefine, unitPageQueryResult.unitCodes);
        INvwaDataAccess mainDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(mainQueryModel);
        queryResult.rows = mainDataAccess.executeQuery(context);
        return queryResult;
    }

    private UnitPageQueryResult subQueryUnitCode(String datatime, QueryICRParam param, QueryTableInfo queryTableInfo, List<DimensionQueryCondition> dimQueryConds) throws Exception {
        MemoryDataSet dataRows;
        UnitPageQueryResult result = new UnitPageQueryResult();
        PageInfo pageInfo = param.getPageInfo();
        ArrayList<String> mdCodes = new ArrayList<String>();
        NvwaQueryModel subQueryModel = new NvwaQueryModel();
        subQueryModel.setMainTableName(queryTableInfo.icrTableName);
        DataAccessContext subContext = new DataAccessContext(this.dataModelService);
        ColumnModelDefine mdCodeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "MDCODE");
        ColumnModelDefine batchIdColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "BATCHID");
        ColumnModelDefine datatimeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "DATATIME");
        subQueryModel.getColumns().add(new NvwaQueryColumn(mdCodeColumnDefine));
        subQueryModel.getColumnFilters().put(batchIdColumnDefine, param.getBatchId());
        subQueryModel.getColumnFilters().put(datatimeColumnDefine, datatime);
        if (CollectionUtils.isNotEmpty(dimQueryConds)) {
            for (DimensionQueryCondition cond : dimQueryConds) {
                subQueryModel.getColumnFilters().put(cond.columnModelDefine, cond.condValues.size() == 1 ? cond.condValues.get(0) : cond.condValues);
            }
        }
        subQueryModel.getGroupByColumns().add(0);
        subQueryModel.setFilter(this.getFilter(param));
        if (pageInfo == null) {
            INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(subQueryModel);
            dataRows = dataAccess.executeQuery(subContext);
        } else {
            INvwaPageDataAccess subPageDataAccess = this.nvwaDataAccessProvider.createPageDataAccess(subQueryModel);
            int pageBeginIndex = pageInfo.getPageIndex() * pageInfo.getRowsPerPage();
            int pageEndIndex = pageBeginIndex + pageInfo.getRowsPerPage();
            dataRows = subPageDataAccess.executeQuery(subContext, pageBeginIndex, pageEndIndex);
            result.total = subPageDataAccess.queryTotalCount(subContext);
        }
        for (int i = 0; i < dataRows.size(); ++i) {
            DataRow dataRow = dataRows.get(i);
            mdCodes.add(dataRow.getString(0));
        }
        result.unitCodes = mdCodes;
        return result;
    }

    private String getFilter(QueryICRParam param) {
        String filter = "";
        if (!param.isNeedDiffTable() || !param.isNeedZeroTable()) {
            StringBuilder filterBuf = new StringBuilder();
            if (!param.isNeedDiffTable()) {
                filterBuf.append("BBLX").append(" <> 1");
            }
            if (!param.isNeedZeroTable()) {
                filterBuf.append(" AND ").append("ICR_RESULT").append(" <> 2");
            }
            if ((filter = filterBuf.toString()).startsWith(" AND ")) {
                filter = filter.substring(5);
            }
        }
        return filter;
    }

    private Map<String, Integer> queryFormLackCount(String datatime, QueryICRParam param, QueryTableInfo queryTableInfo, List<DimensionQueryCondition> dimQueryConds) {
        String formLackCountQuerySql;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        HashMap<String, Object> args = new HashMap<String, Object>();
        if (CollectionUtils.isNotEmpty(dimQueryConds)) {
            StringBuilder dimQueryCondBuilder = new StringBuilder();
            for (int i = 0; i < dimQueryConds.size(); ++i) {
                DimensionQueryCondition cond = dimQueryConds.get(i);
                List<String> condValues = cond.condValues;
                if (!CollectionUtils.isNotEmpty(condValues)) continue;
                if (i > 0) {
                    dimQueryCondBuilder.append(" AND ");
                }
                String columnCode = cond.columnModelDefine.getCode();
                dimQueryCondBuilder.append(columnCode);
                if (condValues.size() > 1) {
                    dimQueryCondBuilder.append(" IN (:").append(columnCode).append(")");
                    args.put(columnCode, condValues);
                    continue;
                }
                dimQueryCondBuilder.append(" = :").append(columnCode);
                args.put(columnCode, condValues.get(0));
            }
            formLackCountQuerySql = "SELECT FORM_KEY,COUNT(ID) FROM " + queryTableInfo.icrTableName + " WHERE " + "BATCHID" + " = :batchId AND " + "ICR_RESULT" + " <> :normalType AND " + dimQueryCondBuilder + " AND " + "FORM_KEY" + " IN (:formKeys) GROUP BY " + "FORM_KEY";
            args.put("batchId", param.getBatchId());
            args.put("normalType", 0);
            args.put("formKeys", param.getFormKeys());
        } else {
            formLackCountQuerySql = "SELECT FORM_KEY,COUNT(ID) FROM " + queryTableInfo.icrTableName + " WHERE " + "BATCHID" + " = :batchId AND " + "DATATIME" + " = :period AND " + "ICR_RESULT" + " <> :normalType AND " + "FORM_KEY" + " IN (:formKeys) GROUP BY " + "FORM_KEY";
            args.put("batchId", param.getBatchId());
            args.put("period", datatime);
            args.put("normalType", 0);
            args.put("formKeys", param.getFormKeys());
        }
        HashMap<String, Integer> formLackMap = new HashMap<String, Integer>();
        namedParameterJdbcTemplate.query(formLackCountQuerySql, args, rs -> formLackMap.put(rs.getString(1), rs.getInt(2)));
        return formLackMap;
    }

    private int queryLackCount(String datatime, QueryICRParam param, QueryTableInfo queryTableInfo, List<DimensionQueryCondition> dimQueryConds) throws Exception {
        NvwaQueryModel lackCountQueryModel = new NvwaQueryModel();
        lackCountQueryModel.setMainTableName(queryTableInfo.icrTableName);
        ColumnModelDefine mdCodeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "MDCODE");
        ColumnModelDefine batchIdColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "BATCHID");
        ColumnModelDefine datatimeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "DATATIME");
        lackCountQueryModel.getColumns().add(new NvwaQueryColumn(mdCodeColumnDefine));
        lackCountQueryModel.getColumnFilters().put(batchIdColumnDefine, param.getBatchId());
        lackCountQueryModel.getColumnFilters().put(datatimeColumnDefine, datatime);
        if (CollectionUtils.isNotEmpty(dimQueryConds)) {
            for (DimensionQueryCondition cond : dimQueryConds) {
                lackCountQueryModel.getColumnFilters().put(cond.columnModelDefine, cond.condValues.size() == 1 ? cond.condValues.get(0) : cond.condValues);
            }
        }
        lackCountQueryModel.getGroupByColumns().add(0);
        StringBuilder lackFilterBuf = new StringBuilder();
        lackFilterBuf.append("ICR_RESULT").append("<> 0");
        if (!param.isNeedDiffTable()) {
            lackFilterBuf.append(" AND ").append("BBLX").append("<> 1");
        }
        if (!param.isNeedZeroTable()) {
            lackFilterBuf.append(" AND ").append("ICR_RESULT").append(" <> 2");
        }
        lackCountQueryModel.setFilter(lackFilterBuf.toString());
        INvwaDataAccess lackCountDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(lackCountQueryModel);
        MemoryDataSet lackDataRows = lackCountDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
        return lackDataRows.size();
    }

    @Override
    public ErrorDesInfo editErrorDes(String taskKey, DimensionCombination dimensionCombination, String formKey, String description) {
        SimpleDateFormat formatter;
        List icdFields;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
        TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == icdTable) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return null;
        }
        ErrorDesInfo errorDesInfo = new ErrorDesInfo();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        UUID uuid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey);
        String recid = uuid.toString();
        boolean recidIsExist = this.editICDTable(description, errorDesInfo, recid, icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID()), formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if (recidIsExist) {
            return errorDesInfo;
        }
        this.interICDTable(description, taskDefine, errorDesInfo, dimensionValueSet, recid, icdFields, formatter);
        return errorDesInfo;
    }

    private boolean editICDTable(String description, ErrorDesInfo errorDesInfo, String recid, List<ColumnModelDefine> icdFields, SimpleDateFormat formatter) {
        boolean recidIsExist = false;
        int desIndex = 0;
        int updateTimeIndex = 0;
        int updaterIndex = 0;
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (icdField.getCode().equals("RECID")) {
                queryModel.getColumnFilters().put(icdField, recid);
                continue;
            }
            if (icdField.getCode().equals("DESCRIPTION")) {
                desIndex = icdFields.indexOf(icdField);
                continue;
            }
            if (icdField.getCode().equals("UPDATETIME")) {
                updateTimeIndex = icdFields.indexOf(icdField);
                continue;
            }
            if (!icdField.getCode().equals("UPDATER")) continue;
            updaterIndex = icdFields.indexOf(icdField);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                recidIsExist = true;
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                row.setValue(desIndex, (Object)description);
                row.setValue(updateTimeIndex, (Object)new Date());
                row.setValue(updaterIndex, (Object)this.resolveCurrentUserName());
                errorDesInfo.setRecid(recid);
                errorDesInfo.setDescription(description);
                errorDesInfo.setUpdater(this.resolveCurrentUserName());
                errorDesInfo.setUpdateTime(formatter.format(new Date()));
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return recidIsExist;
    }

    private void interICDTable(String description, TaskDefine taskDefine, ErrorDesInfo errorDesInfo, DimensionValueSet dimensionValueSet, String recid, List<ColumnModelDefine> icdFields, SimpleDateFormat formatter) {
        ArrayList<String> dimNames = new ArrayList<String>();
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dataDimension : dataSchemeDimension) {
            if (!DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType())) continue;
            IEntityDefine entity = this.entityMetaService.queryEntity(dataDimension.getDimKey());
            if (null != entity) {
                dimNames.add(entity.getDimensionName());
                continue;
            }
            if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
            dimNames.add("ADJUST");
        }
        IEntityDefine entity = this.entityMetaService.queryEntity(taskDefine.getDw());
        String dwDimName = entity.getDimensionName();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            List columns = queryModel.getColumns();
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block4: for (int i = 0; i < columns.size(); ++i) {
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("RECID")) {
                    iNvwaDataRow.setValue(i, (Object)recid);
                    errorDesInfo.setRecid(recid);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("DESCRIPTION")) {
                    iNvwaDataRow.setValue(i, (Object)description);
                    errorDesInfo.setDescription(description);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATETIME")) {
                    iNvwaDataRow.setValue(i, (Object)new Date());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATOR")) {
                    iNvwaDataRow.setValue(i, (Object)this.resolveCurrentUserName());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("UPDATETIME")) {
                    iNvwaDataRow.setValue(i, (Object)new Date());
                    errorDesInfo.setUpdateTime(formatter.format(new Date()));
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("UPDATER")) {
                    iNvwaDataRow.setValue(i, (Object)this.resolveCurrentUserName());
                    errorDesInfo.setUpdater(this.resolveCurrentUserName());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("MDCODE")) {
                    iNvwaDataRow.setValue(i, (Object)dimensionValueSet.getValue(dwDimName).toString());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("DATATIME")) {
                    iNvwaDataRow.setValue(i, (Object)dimensionValueSet.getValue("DATATIME").toString());
                    continue;
                }
                for (String dimName : dimNames) {
                    if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals(dimName)) continue;
                    iNvwaDataRow.setValue(i, (Object)dimensionValueSet.getValue(dimName).toString());
                    continue block4;
                }
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void batchEditErrorDes(String taskKey, DimensionCollection dimensionCollection, List<String> formKeys, String description, String batchId) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
        TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == icdTable) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return;
        }
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        HashSet<String> recids = new HashSet<String>();
        HashMap<String, DimensionValueSet> recidDimMap = new HashMap<String, DimensionValueSet>();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            for (String formKey : formKeys) {
                UUID uuid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey);
                recids.add(uuid.toString());
                recidDimMap.put(uuid.toString(), dimensionValueSet);
            }
        }
        Set<String> emptyTableRecid = this.queryEmptyFormRecids(batchId, dataScheme);
        recids.retainAll(emptyTableRecid);
        Set<String> updateRecids = this.batchUpdateICDTable(description, icdFields, new ArrayList<String>(recids));
        if (!updateRecids.isEmpty()) {
            updateRecids.forEach(recids::remove);
        }
        if (!recids.isEmpty()) {
            this.batchInsertICDTable(description, taskDefine, new ArrayList<String>(recids), recidDimMap, icdFields);
        }
    }

    private Set<String> batchUpdateICDTable(String description, List<ColumnModelDefine> icdFields, List<String> recids) {
        HashSet<String> updateRecids = new HashSet<String>();
        int recidIndex = 0;
        int desIndex = 0;
        int updateTimeIndex = 0;
        int updaterIndex = 0;
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (icdField.getCode().equals("RECID")) {
                queryModel.getColumnFilters().put(icdField, recids);
                recidIndex = icdFields.indexOf(icdField);
                continue;
            }
            if (icdField.getCode().equals("DESCRIPTION")) {
                desIndex = icdFields.indexOf(icdField);
                continue;
            }
            if (icdField.getCode().equals("UPDATETIME")) {
                updateTimeIndex = icdFields.indexOf(icdField);
                continue;
            }
            if (!icdField.getCode().equals("UPDATER")) continue;
            updaterIndex = icdFields.indexOf(icdField);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            int updateRowCount = 0;
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                updateRecids.add(row.getValue(recidIndex).toString());
                row.setValue(desIndex, (Object)description);
                row.setValue(updateTimeIndex, (Object)new Date());
                row.setValue(updaterIndex, (Object)this.resolveCurrentUserName());
                if (++updateRowCount < 5000) continue;
                iNvwaDataRows.commitChanges(context);
                updateRowCount = 0;
            }
            if (updateRowCount > 0) {
                iNvwaDataRows.commitChanges(context);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return updateRecids;
    }

    @Nullable
    private Set<String> queryEmptyFormRecids(String batchId, DataScheme dataScheme) {
        TableModelDefine icrTable;
        String icrTableName = this.icSplitTableHelper.getICRSplitTableName(dataScheme);
        if (null != this.nrdtSplitTableHelper) {
            icrTableName = this.nrdtSplitTableHelper.getSplitTableName(icrTableName, batchId);
        }
        if (null == (icrTable = this.dataModelService.getTableModelDefineByName(icrTableName))) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return null;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icrFields = this.dataModelService.getColumnModelDefinesByTable(icrTable.getID());
        for (ColumnModelDefine icrField : icrFields) {
            if (icrField.getCode().equals("RECID")) {
                queryModel.getColumns().add(new NvwaQueryColumn(icrField));
                continue;
            }
            if (icrField.getCode().equals("ICR_RESULT")) {
                queryModel.getColumns().add(new NvwaQueryColumn(icrField));
                continue;
            }
            if (!icrField.getCode().equals("BATCHID")) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(icrField));
            queryModel.getColumnFilters().put(icrField, batchId);
        }
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            HashMap<String, Integer> recidResMap = new HashMap<String, Integer>();
            for (DataRow dataRow : dataRows) {
                String recid2 = "";
                int res2 = 0;
                for (int i = 0; i < columns.size(); ++i) {
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("RECID")) {
                        recid2 = dataRow.getString(i);
                        continue;
                    }
                    if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("ICR_RESULT")) continue;
                    res2 = dataRow.getInt(i);
                }
                recidResMap.put(recid2, res2);
            }
            HashSet<String> filterRecid = new HashSet<String>();
            recidResMap.forEach((recid, res) -> {
                if (null != res && res == 1) {
                    filterRecid.add((String)recid);
                }
            });
            return filterRecid;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    private void batchInsertICDTable(String description, TaskDefine taskDefine, List<String> recids, Map<String, DimensionValueSet> recidDimMap, List<ColumnModelDefine> icdFields) {
        ArrayList<String> dimNames = new ArrayList<String>();
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dataDimension : dataSchemeDimension) {
            if (!DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType())) continue;
            IEntityDefine entity = this.entityMetaService.queryEntity(dataDimension.getDimKey());
            if (null != entity) {
                dimNames.add(entity.getDimensionName());
                continue;
            }
            if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
            dimNames.add("ADJUST");
        }
        IEntityDefine entity = this.entityMetaService.queryEntity(taskDefine.getDw());
        String dwDimName = entity.getDimensionName();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            int insertRowCount = 0;
            List columns = queryModel.getColumns();
            for (String recid : recids) {
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                block5: for (int i = 0; i < columns.size(); ++i) {
                    DimensionValueSet dimensionValueSet;
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("RECID")) {
                        iNvwaDataRow.setValue(i, (Object)recid);
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("DESCRIPTION")) {
                        iNvwaDataRow.setValue(i, (Object)description);
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATETIME")) {
                        iNvwaDataRow.setValue(i, (Object)new Date());
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATOR")) {
                        iNvwaDataRow.setValue(i, (Object)this.resolveCurrentUserName());
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("UPDATETIME")) {
                        iNvwaDataRow.setValue(i, (Object)new Date());
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("UPDATER")) {
                        iNvwaDataRow.setValue(i, (Object)this.resolveCurrentUserName());
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("MDCODE")) {
                        dimensionValueSet = recidDimMap.get(recid);
                        iNvwaDataRow.setValue(i, (Object)dimensionValueSet.getValue(dwDimName).toString());
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("DATATIME")) {
                        dimensionValueSet = recidDimMap.get(recid);
                        iNvwaDataRow.setValue(i, (Object)dimensionValueSet.getValue("DATATIME").toString());
                        continue;
                    }
                    for (String dimName : dimNames) {
                        if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals(dimName)) continue;
                        DimensionValueSet dimensionValueSet2 = recidDimMap.get(recid);
                        iNvwaDataRow.setValue(i, (Object)dimensionValueSet2.getValue(dimName).toString());
                        continue block5;
                    }
                }
                if (++insertRowCount < 5000) continue;
                iNvwaDataUpdator.commitChanges(context);
                insertRowCount = 0;
            }
            if (insertRowCount > 0) {
                iNvwaDataUpdator.commitChanges(context);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public String checkErrorDes(CheckErrDesContext context) {
        GetRuleGroupContext getRuleGroupContext = new GetRuleGroupContext();
        getRuleGroupContext.setTaskKey(context.getTaskKey());
        getRuleGroupContext.setFormSchemeKey(context.getFormSchemeKey());
        String ruleGroup = this.errDesCheckService.getRuleGroup(getRuleGroupContext);
        ContentCheckByGroupService checkService = this.contentCheckServiceFactory.getCheckService();
        ContentCheckResult checkResult = checkService.check(context.getDescription(), ruleGroup);
        if (checkResult.getStatus()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (String message : checkResult.getMessages()) {
            result.append(message).append(";");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    @Override
    public void deleteErrorDes(String taskKey, DimensionCombination dimensionCombination, String formKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
        TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == icdTable) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return;
        }
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        String recid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey).toString();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (!icdField.getCode().equals("RECID")) continue;
            queryModel.getColumnFilters().put(icdField, recid);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            iNvwaDataUpdator.deleteAll();
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private String resolveCurrentUserName() {
        ContextUser operator = NpContextHolder.getContext().getUser();
        return operator == null ? null : operator.getName();
    }

    @Override
    public ErrorDesInfo queryErrorDes(String taskKey, DimensionCombination dimensionCombination, String formKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
        TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == icdTable) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return null;
        }
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        String recid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey).toString();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (!icdField.getCode().equals("RECID")) continue;
            queryModel.getColumnFilters().put(icdField, recid);
        }
        ErrorDesInfo errorDesInfo = new ErrorDesInfo();
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < dataRows.size(); ++i) {
                DataRow item = dataRows.get(i);
                for (int j = 0; j < columns.size(); ++j) {
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("RECID")) {
                        errorDesInfo.setRecid(item.getString(j));
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("DESCRIPTION")) {
                        errorDesInfo.setDescription(item.getString(j));
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("CREATETIME")) {
                        errorDesInfo.setCreateTime(formatter.format(item.getDate(j).getTime()));
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("CREATOR")) {
                        errorDesInfo.setCreator(item.getString(j));
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("UPDATETIME")) {
                        errorDesInfo.setUpdateTime(formatter.format(item.getDate(j).getTime()));
                        continue;
                    }
                    if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("UPDATER")) continue;
                    errorDesInfo.setUpdater(item.getString(j));
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return errorDesInfo;
    }

    @Override
    public List<ExpErrDesInfo> queryErrorDes(ExpErrDesParam param) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
        TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == icdTable) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return null;
        }
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskDefine.getKey(), formScheme.getKey());
        IBatchAccessResult batchAccessResult = dataAccessService.getReadAccess(param.getDims(), param.getFormKeys());
        HashMap<String, DimAndFormMessage> recidMap = new HashMap<String, DimAndFormMessage>();
        for (DimensionCombination dimensionCombination : param.getDims().getDimensionCombinations()) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            List accessFormKeys = batchAccessResult.getAccessForm(dimensionCombination);
            Iterator iterator = accessFormKeys.iterator();
            while (iterator.hasNext()) {
                String formKey = (String)iterator.next();
                String recid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey).toString();
                recidMap.put(recid, new DimAndFormMessage(dimensionValueSet, formKey));
            }
        }
        if (recidMap.isEmpty()) {
            return null;
        }
        ArrayList<ExpErrDesInfo> result = new ArrayList<ExpErrDesInfo>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (!icdField.getCode().equals("RECID")) continue;
            queryModel.getColumnFilters().put(icdField, new ArrayList(recidMap.keySet()));
        }
        INvwaDataAccess readOnlyDataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < dataRows.size(); ++i) {
                ExpErrDesInfo expErrDesInfo = new ExpErrDesInfo();
                DataRow item = dataRows.get(i);
                block22: for (int j = 0; j < columns.size(); ++j) {
                    switch (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                        case "RECID": {
                            String recid = item.getString(j);
                            DimensionValueSet dimensionValueSet = new DimensionValueSet(((DimAndFormMessage)recidMap.get(recid)).getDimensionValueSet());
                            expErrDesInfo.setDimensionValueSet(dimensionValueSet);
                            expErrDesInfo.setFormKey(((DimAndFormMessage)recidMap.get(recid)).getFormKey());
                            continue block22;
                        }
                        case "DESCRIPTION": {
                            expErrDesInfo.setDescription(item.getString(j));
                            continue block22;
                        }
                        case "CREATETIME": {
                            expErrDesInfo.setCreateTime(formatter.format(item.getDate(j).getTime()));
                            continue block22;
                        }
                        case "CREATOR": {
                            expErrDesInfo.setCreator(item.getString(j));
                            continue block22;
                        }
                        case "UPDATETIME": {
                            expErrDesInfo.setUpdateTime(formatter.format(item.getDate(j).getTime()));
                            continue block22;
                        }
                        case "UPDATER": {
                            expErrDesInfo.setUpdater(item.getString(j));
                        }
                    }
                }
                result.add(expErrDesInfo);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void addTags(AddTagParam param) {
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            QueryTableInfo queryTableInfo = this.getQueryTableInfo(param.getBatchId(), dataScheme);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            queryModel.setMainTableName(queryTableInfo.icrTableName);
            ColumnModelDefine mdCodeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "MDCODE");
            ColumnModelDefine batchIdColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "BATCHID");
            ColumnModelDefine datatimeColumnDefine = this.dataModelService.getColumnModelDefineByCode(queryTableInfo.icrTableId, "DATATIME");
            queryModel.getColumns().add(new NvwaQueryColumn(mdCodeColumnDefine));
            queryModel.getColumnFilters().put(batchIdColumnDefine, param.getBatchId());
            queryModel.getColumnFilters().put(datatimeColumnDefine, param.getPeriod());
            boolean needDiffTable = param.isDiffTable();
            boolean needZeroTable = param.isZeroTable();
            if (!needDiffTable || !needZeroTable) {
                String filter;
                StringBuilder filterBuf = new StringBuilder();
                if (!needDiffTable) {
                    filterBuf.append("BBLX").append(" <> 1");
                }
                if (!needZeroTable) {
                    filterBuf.append(" AND ").append("ICR_RESULT").append(" <> 2");
                }
                if ((filter = filterBuf.toString()).startsWith(" AND ")) {
                    filter = filter.substring(5);
                }
                queryModel.setFilter(filter);
            }
            queryModel.getGroupByColumns().add(0);
            DataAccessContext context = this.buildDataAccessContext(dataScheme, false);
            INvwaDataAccess dataAccess = this.nvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            MemoryDataSet dataRows = dataAccess.executeQuery(context);
            List unitCodes = dataRows.stream().map(row -> row.getString(0)).collect(Collectors.toList());
            BaseTagContextData tagDefineContext = new BaseTagContextData();
            tagDefineContext.setEntityId(param.getEntityId());
            TagDefine tagDefine = new TagDefine();
            tagDefine.setTitle(param.getTitle());
            tagDefine.setCategory(param.getCategory());
            tagDefine.setDescription(param.getDescription());
            tagDefine.setRangeModify(false);
            String tagKey = this.tagManagementConfigService.addTagDefinePurely(tagDefineContext, tagDefine);
            for (String entityDataKey : unitCodes) {
                TagAddMappingsContextData tagMappingContext = new TagAddMappingsContextData();
                tagMappingContext.setEntityData(entityDataKey);
                tagMappingContext.setTagKeys(Collections.singletonList(tagKey));
                this.tagManagementConfigService.addTagMappingPurely(tagMappingContext);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportCheckResult(QueryICRParam param, OutputStream outputStream) throws Exception {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);){
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            Map<String, List<String>> dimNameValueMap = this.getDimNameValueMap(param);
            String period = dimNameValueMap.get("DATATIME").get(0);
            QueryTableInfo queryTableInfo = this.getQueryTableInfo(param.getBatchId(), dataScheme);
            HashMap<Integer, Integer> columnMaxWidthMap = new HashMap<Integer, Integer>();
            CellStyle cellNormalStyle = this.createCellNormalStyle(workbook);
            SXSSFSheet sheet = workbook.createSheet("\u8868\u5b8c\u6574\u6027\u68c0\u67e5");
            this.handleHeaderColumn_exportCheckResult(period, param, queryTableInfo, sheet, cellNormalStyle, columnMaxWidthMap);
            this.queryAndHandleRows_exportCheckResult(period, param, queryTableInfo, sheet, cellNormalStyle, columnMaxWidthMap);
            this.setAutoColumnSize_exportCheckResult(sheet, columnMaxWidthMap);
            workbook.write(outputStream);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportErrorFormUnit(QueryICRParam param, OutputStream outputStream) throws Exception {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);){
            CellStyle cellNormalStyle = this.createCellNormalStyle(workbook);
            SXSSFSheet sxssfSheet = workbook.createSheet("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7f3a\u8868\u4fe1\u606f");
            HashMap<Integer, Integer> columnMaxWidthMap = new HashMap<Integer, Integer>();
            this.handleHeaderColumn_exportErrorFormUnit(sxssfSheet, cellNormalStyle, columnMaxWidthMap);
            this.queryAndHandleRows_exportErrorFormUnit(param, sxssfSheet, cellNormalStyle, columnMaxWidthMap);
            this.setAutoColumnSize_exportErrorFormUnit(sxssfSheet, columnMaxWidthMap);
            workbook.write(outputStream);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void handleHeaderColumn_exportErrorFormUnit(SXSSFSheet sxssfSheet, CellStyle cellNormalStyle, Map<Integer, Integer> columnMaxWidthMap) {
        ArrayList<TableHeaderColumnInfo> headerColumnInfos = new ArrayList<TableHeaderColumnInfo>();
        headerColumnInfos.add(new TableHeaderColumnInfo("\u5e8f\u53f7"));
        headerColumnInfos.add(new TableHeaderColumnInfo("\u7f3a\u8868\u540d\u79f0"));
        headerColumnInfos.add(new TableHeaderColumnInfo("\u7c7b\u578b"));
        headerColumnInfos.add(new TableHeaderColumnInfo("\u51fa\u9519\u8bf4\u660e"));
        int currentColumn = 0;
        for (TableHeaderColumnInfo columnInfo : headerColumnInfos) {
            currentColumn = this.processColumn(sxssfSheet, cellNormalStyle, columnInfo, 0, currentColumn, columnMaxWidthMap);
        }
    }

    private void queryAndHandleRows_exportErrorFormUnit(QueryICRParam param, SXSSFSheet sxssfSheet, CellStyle cellNormalStyle, Map<Integer, Integer> columnMaxWidthMap) {
        List<ErrorFormUnitInfo> errorFormUnitInfos = this.queryErrorFormUnit(param);
        for (int i = 0; i < errorFormUnitInfos.size(); ++i) {
            ErrorFormUnitInfo errorFormUnitInfo = errorFormUnitInfos.get(i);
            SXSSFRow row = sxssfSheet.createRow(1 + i);
            SXSSFCell rowNumCell = row.createCell(0);
            rowNumCell.setCellStyle(cellNormalStyle);
            rowNumCell.setCellValue(String.valueOf(i + 1));
            SXSSFCell unitInfoCell = row.createCell(1);
            unitInfoCell.setCellStyle(cellNormalStyle);
            unitInfoCell.setCellValue(errorFormUnitInfo.getUnitCode() + " | " + errorFormUnitInfo.getUnitTitle());
            this.calcColumnWidth(unitInfoCell, 1, columnMaxWidthMap);
            SXSSFCell errorTypeCell = row.createCell(2);
            errorTypeCell.setCellStyle(cellNormalStyle);
            errorTypeCell.setCellValue(errorFormUnitInfo.getErrorType());
            SXSSFCell errorDescCell = row.createCell(3);
            errorDescCell.setCellStyle(cellNormalStyle);
            errorDescCell.setCellValue(errorFormUnitInfo.getErrorDes());
            this.calcColumnWidth(errorDescCell, 3, columnMaxWidthMap);
        }
    }

    private void calcColumnWidth(SXSSFCell cell, int colNum, Map<Integer, Integer> columnMaxWidthMap) {
        DataFormatter dataFormatter = new DataFormatter();
        String cellValue = dataFormatter.formatCellValue(cell);
        int cellWidth = cellValue.getBytes(StandardCharsets.UTF_8).length;
        int maxCellWidth = Math.max(cellWidth, columnMaxWidthMap.getOrDefault(colNum, 0));
        columnMaxWidthMap.put(colNum, maxCellWidth);
    }

    private void setAutoColumnSize_exportErrorFormUnit(SXSSFSheet sxssfSheet, Map<Integer, Integer> columnMaxWidthMap) {
        columnMaxWidthMap.put(0, 10);
        columnMaxWidthMap.put(2, 20);
        sxssfSheet.trackAllColumnsForAutoSizing();
        for (int colNum : columnMaxWidthMap.keySet()) {
            sxssfSheet.setColumnWidth(colNum, columnMaxWidthMap.get(colNum) * 256 + 200);
        }
    }

    private void handleHeaderColumn_exportCheckResult(String period, QueryICRParam param, QueryTableInfo queryTableInfo, SXSSFSheet sxssfSheet, CellStyle cellNormalStyle, Map<Integer, Integer> columnMaxWidthMap) throws Exception {
        ArrayList<TableHeaderColumnInfo> headerColumnInfos = new ArrayList<TableHeaderColumnInfo>();
        int lackCount = this.queryLackCount(period, param, queryTableInfo, null);
        TableHeaderColumnInfo lackCountColumnInfo = new TableHeaderColumnInfo("\u7f3a\u8868\u5355\u4f4d" + lackCount).addChildCol(new TableHeaderColumnInfo("\u5e8f\u53f7", 2)).addChildCol(new TableHeaderColumnInfo("\u5355\u4f4d\u4ee3\u7801", 2)).addChildCol(new TableHeaderColumnInfo("\u5355\u4f4d\u540d\u79f0", 2));
        ArrayList dimTitles = new ArrayList();
        this.sceneDimQueryAndForEach(param.getTaskKey(), (sceneDimKey, sceneEntity) -> dimTitles.add(sceneEntity.getTitle()));
        if (CollectionUtils.isNotEmpty(dimTitles)) {
            dimTitles.forEach(dimTitle -> lackCountColumnInfo.addChildCol(new TableHeaderColumnInfo((String)dimTitle, 2)));
        }
        TableHeaderColumnInfo formCodeColumnInfo = new TableHeaderColumnInfo("\u6807\u8bc6").addChildCol(new TableHeaderColumnInfo("\u8868\u540d").addChildCol(new TableHeaderColumnInfo("\u8ba1\u6570")));
        headerColumnInfos.add(lackCountColumnInfo);
        headerColumnInfos.add(formCodeColumnInfo);
        Map<String, Integer> formLackCountMap = this.queryFormLackCount(period, param, queryTableInfo, null);
        param.getFormKeys().forEach(formKey -> {
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (formDefine != null) {
                TableHeaderColumnInfo formColumnInfo = new TableHeaderColumnInfo(formDefine.getFormCode()).addChildCol(new TableHeaderColumnInfo(formDefine.getTitle()).addChildCol(new TableHeaderColumnInfo(String.valueOf(formLackCountMap.get(formDefine.getKey())))));
                headerColumnInfos.add(formColumnInfo);
            }
        });
        int currentColumn = 0;
        for (TableHeaderColumnInfo columnInfo : headerColumnInfos) {
            currentColumn = this.processColumn(sxssfSheet, cellNormalStyle, columnInfo, 0, currentColumn, columnMaxWidthMap);
        }
    }

    private void queryAndHandleRows_exportCheckResult(String period, QueryICRParam param, QueryTableInfo queryTableInfo, SXSSFSheet sxssfSheet, CellStyle cellNormalStyle, Map<Integer, Integer> columnMaxWidthMap) throws Exception {
        String sql = "SELECT * FROM " + queryTableInfo.icrTableName + " WHERE " + "DATATIME" + " = :period AND " + "BATCHID" + " = :batchId ";
        if (!param.isNeedDiffTable()) {
            sql = sql + "AND BBLX <> 1 ";
        }
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        namedParameterJdbcTemplate.getJdbcTemplate().setFetchSize(1000);
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("period", period);
        args.put("batchId", param.getBatchId());
        ArrayList<String> sceneDimColumnCodes = new ArrayList<String>();
        HashMap<String, IEntityTable> sceneEntityTableMap = new HashMap<String, IEntityTable>();
        this.sceneDimQueryAndForEach(param.getTaskKey(), (dimKey, dimEntity) -> {
            IEntityTable dimEntityTable;
            sceneDimColumnCodes.add(dimEntity.getDimensionName());
            IEntityQuery dimQuery = this.entityQueryHelper.getDimEntityQuery(dimKey, period);
            try {
                dimEntityTable = this.entityQueryHelper.buildEntityTable(dimQuery, param.getFormSchemeKey(), true);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            sceneEntityTableMap.put(dimEntity.getDimensionName(), dimEntityTable);
        });
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(param.getFormSchemeKey(), period);
        IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, param.getFormSchemeKey(), false);
        namedParameterJdbcTemplate.query(sql, args, (RowCallbackHandler)new RowCallbackExportHandler(param, sceneDimColumnCodes, entityTable, sceneEntityTableMap, columnMaxWidthMap, sxssfSheet, cellNormalStyle));
    }

    private void setAutoColumnSize_exportCheckResult(SXSSFSheet sxssfSheet, Map<Integer, Integer> columnMaxWidthMap) {
        columnMaxWidthMap.put(0, 10);
        sxssfSheet.trackAllColumnsForAutoSizing();
        for (int colNum : columnMaxWidthMap.keySet()) {
            if (colNum == 1) {
                sxssfSheet.autoSizeColumn(1);
                continue;
            }
            sxssfSheet.setColumnWidth(colNum, columnMaxWidthMap.get(colNum) * 256 + 200);
        }
    }

    private CellStyle createCellNormalStyle(Workbook workbook) {
        CellStyle cellNormalStyle = workbook.createCellStyle();
        cellNormalStyle.setAlignment(HorizontalAlignment.CENTER);
        cellNormalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)14);
        cellNormalStyle.setFont(font);
        return cellNormalStyle;
    }

    private int processColumn(SXSSFSheet sheet, CellStyle cellNormalStyle, TableHeaderColumnInfo columnInfo, int currentRow, int currentColumn, Map<Integer, Integer> columnMaxWidthMap) {
        SXSSFRow row;
        int colSpan = columnInfo.calcRealColCount();
        int rowSpan = columnInfo.getRowSpan();
        if (rowSpan > 1 || colSpan > 1) {
            CellRangeAddress mergedRegion = new CellRangeAddress(currentRow, currentRow + rowSpan - 1, currentColumn, currentColumn + colSpan - 1);
            sheet.addMergedRegion(mergedRegion);
        }
        if ((row = sheet.getRow(currentRow)) == null) {
            row = sheet.createRow(currentRow);
        }
        String cellTitle = columnInfo.getTitle();
        Cell cell = row.createCell(currentColumn);
        cell.setCellValue(cellTitle);
        cell.setCellStyle(cellNormalStyle);
        DataFormatter dataFormatter = new DataFormatter();
        String cellValue = dataFormatter.formatCellValue(cell);
        int cellWidth = cellValue.getBytes(StandardCharsets.UTF_8).length;
        int maxCellWidth = Math.max(cellWidth, columnMaxWidthMap.getOrDefault(currentColumn, 0));
        columnMaxWidthMap.put(currentColumn, maxCellWidth);
        List<TableHeaderColumnInfo> childCols = columnInfo.getChildCols();
        if (!CollectionUtils.isEmpty(childCols)) {
            int nextRow = currentRow + 1;
            int childColumn = currentColumn;
            for (TableHeaderColumnInfo childCol : childCols) {
                childColumn = this.processColumn(sheet, cellNormalStyle, childCol, nextRow, childColumn, columnMaxWidthMap);
            }
        }
        return currentColumn + colSpan;
    }

    static class RowCallbackExportHandler
    implements RowCallbackHandler {
        private final QueryICRParam param;
        private final SXSSFSheet sxssfSheet;
        private final CellStyle cellNormalStyle;
        private final IEntityTable unitEntityTable;
        private final List<String> sceneColumnCodes;
        private final Map<String, IEntityTable> sceneEntityTableMap;
        private final Map<Integer, Integer> columnMaxWidthMap;
        private final Map<String, Integer> unitLackFormCountMap;
        private final Map<String, List<FormCheckResult>> formCheckResultMap;
        private int rowNum = 1;

        public RowCallbackExportHandler(QueryICRParam param, List<String> sceneColumnCodes, IEntityTable unitEntityTable, Map<String, IEntityTable> sceneEntityTableMap, Map<Integer, Integer> columnMaxWidthMap, SXSSFSheet sxssfSheet, CellStyle cellNormalStyle) {
            this.param = param;
            this.sxssfSheet = sxssfSheet;
            this.cellNormalStyle = cellNormalStyle;
            this.sceneColumnCodes = sceneColumnCodes;
            this.unitEntityTable = unitEntityTable;
            this.sceneEntityTableMap = sceneEntityTableMap;
            this.columnMaxWidthMap = columnMaxWidthMap;
            this.unitLackFormCountMap = new HashMap<String, Integer>();
            this.formCheckResultMap = new HashMap<String, List<FormCheckResult>>();
        }

        public void processRow(ResultSet rs) throws SQLException {
            String unitCode = rs.getString("MDCODE");
            String formKeyValue = rs.getString("FORM_KEY");
            int resultCode = rs.getInt("ICR_RESULT");
            FormCheckResult formCheckResult = new FormCheckResult();
            formCheckResult.formKey = formKeyValue;
            if (0 == resultCode) {
                formCheckResult.result = "";
            } else if (1 == resultCode) {
                formCheckResult.result = "\u7a7a\u8868";
                this.unitLackFormCountMap.put(unitCode, this.unitLackFormCountMap.getOrDefault(unitCode, 0) + 1);
            } else if (2 == resultCode) {
                formCheckResult.result = "\u96f6\u8868";
                this.unitLackFormCountMap.put(unitCode, this.unitLackFormCountMap.getOrDefault(unitCode, 0) + 1);
            } else if (3 == resultCode) {
                formCheckResult.result = "--";
            }
            if (CollectionUtils.isNotEmpty(this.sceneColumnCodes)) {
                for (String sceneColumnCode : this.sceneColumnCodes) {
                    String sceneValue = rs.getString(sceneColumnCode);
                    String sceneTitle = this.sceneEntityTableMap.get(sceneColumnCode).findByEntityKey(sceneValue).getTitle();
                    formCheckResult.addSceneValue(sceneTitle);
                }
            }
            this.formCheckResultMap.computeIfAbsent(unitCode, k -> new ArrayList()).add(formCheckResult);
            List<FormCheckResult> formCheckResults = this.formCheckResultMap.get(unitCode);
            if (formCheckResults.size() == this.param.getFormKeys().size()) {
                boolean isAllZero = true;
                HashMap<String, FormCheckResult> formCheckResultMap = new HashMap<String, FormCheckResult>();
                for (FormCheckResult oneFormResult : formCheckResults) {
                    formCheckResultMap.put(oneFormResult.formKey, oneFormResult);
                    isAllZero = isAllZero && "\u96f6\u8868".equals(oneFormResult.result);
                }
                if (!this.param.isNeedZeroTable() && isAllZero) {
                    return;
                }
                ArrayList<String> oneRow = new ArrayList<String>();
                oneRow.add(String.valueOf(this.rowNum));
                oneRow.add(unitCode);
                IEntityRow entityRow = this.unitEntityTable.findByEntityKey(unitCode);
                oneRow.add(entityRow.getTitle());
                oneRow.addAll(formCheckResults.get((int)0).sceneValues);
                oneRow.add(String.valueOf(this.unitLackFormCountMap.getOrDefault(unitCode, 0)));
                for (String formKey : this.param.getFormKeys()) {
                    oneRow.add(((FormCheckResult)formCheckResultMap.get((Object)formKey)).result);
                }
                SXSSFRow row = this.sxssfSheet.createRow(2 + this.rowNum);
                for (int i = 0; i < oneRow.size(); ++i) {
                    String cellTitle = (String)oneRow.get(i);
                    SXSSFCell cell = row.createCell(i);
                    cell.setCellValue(cellTitle);
                    cell.setCellStyle(this.cellNormalStyle);
                    DataFormatter dataFormatter = new DataFormatter();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    int cellWidth = cellValue.getBytes(StandardCharsets.UTF_8).length;
                    int maxCellWidth = Math.max(cellWidth, this.columnMaxWidthMap.getOrDefault(i, 0));
                    this.columnMaxWidthMap.put(i, maxCellWidth);
                }
                this.formCheckResultMap.remove(unitCode);
                this.unitLackFormCountMap.remove(unitCode);
                ++this.rowNum;
            }
        }
    }

    static class DimensionQueryCondition {
        ColumnModelDefine columnModelDefine;
        List<String> condValues;

        public DimensionQueryCondition(ColumnModelDefine columnModelDefine, List<String> condValues) {
            this.columnModelDefine = columnModelDefine;
            this.condValues = condValues;
        }
    }

    static class FormCheckResult {
        String formKey;
        String result;
        List<String> sceneValues = new ArrayList<String>();

        public String getFormKey() {
            return this.formKey;
        }

        public void addSceneValue(String sceneValue) {
            this.sceneValues.add(sceneValue);
        }
    }

    static class QueryTableInfo {
        String icrTableId;
        String icrTableName;
        String icdTableId;
        String icdTableName;

        QueryTableInfo() {
        }
    }

    static class TempPageQueryResult {
        MemoryDataSet<NvwaQueryColumn> rows;
        Map<String, Integer> formLackMap;
        int totalCount;
        int lackCount;

        TempPageQueryResult() {
        }
    }

    static class UnitPageQueryResult {
        List<String> unitCodes;
        private int total;

        UnitPageQueryResult() {
        }
    }
}

