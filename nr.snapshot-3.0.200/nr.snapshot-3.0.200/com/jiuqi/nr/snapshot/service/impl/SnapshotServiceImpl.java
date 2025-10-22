/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.snapshot.consts.JudgeNameType;
import com.jiuqi.nr.snapshot.exception.SnapshotDuplicateNameException;
import com.jiuqi.nr.snapshot.exception.SnapshotException;
import com.jiuqi.nr.snapshot.exception.SnapshotNoPermissionsException;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotParam;
import com.jiuqi.nr.snapshot.input.CreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.CreateSnapshotInfo;
import com.jiuqi.nr.snapshot.input.JudgeNameContext;
import com.jiuqi.nr.snapshot.input.UpdateSnapshotInfo;
import com.jiuqi.nr.snapshot.message.DSDimensionInfo;
import com.jiuqi.nr.snapshot.message.DimInfo;
import com.jiuqi.nr.snapshot.message.Snapshot;
import com.jiuqi.nr.snapshot.message.SnapshotFileInfo;
import com.jiuqi.nr.snapshot.output.QueryDimResult;
import com.jiuqi.nr.snapshot.output.ResultInfo;
import com.jiuqi.nr.snapshot.output.SnapshotInfo;
import com.jiuqi.nr.snapshot.service.ISnapshotDimService;
import com.jiuqi.nr.snapshot.service.SnapshotFileService;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import com.jiuqi.nr.snapshot.utils.LogHellperUtil;
import com.jiuqi.nr.snapshot.utils.SnapshotFileTool;
import com.jiuqi.nr.snapshot.utils.SnapshotJoinProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotServiceImpl
implements SnapshotService {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotServiceImpl.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController definitionRunTimeViewController;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private SnapshotFileService snapshotFileService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private ISnapshotDimService snapshotDimService;
    private static final String SYS_ADMIN = "sys_user_admin";
    private static final String LOG_TITLE = "\u5feb\u7167";

    @Override
    public boolean judgeDuplicateNames(JudgeNameContext judgeNameContext) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(judgeNameContext.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        DimensionValueSet dimensionValueSet = judgeNameContext.getDimensionValueSet();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            if (StringUtils.isEmpty((String)judgeNameContext.getSnapshotId())) {
                this.judgeDuplicateNames(null, judgeNameContext.getTitle(), dataScheme, context, dimensionValueSet);
            } else {
                this.judgeDuplicateNames(judgeNameContext.getSnapshotId(), judgeNameContext.getTitle(), dataScheme, context, dimensionValueSet);
            }
            return true;
        }
        catch (SnapshotDuplicateNameException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public SnapshotInfo createSnapshot(CreateSnapshotContext createSnapshotContext, AsyncTaskMonitor monitor) throws SnapshotException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(createSnapshotContext.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        DimensionValueSet dimensionValueSet = createSnapshotContext.getDimensionCollection().combineWithoutVarDim();
        Object dwValue = dimensionValueSet.getValue(DWdimensionName);
        if (dwValue instanceof ArrayList) {
            ArrayList<String> dwValueList = new ArrayList<String>();
            for (Object o : (List)dwValue) {
                dwValueList.add((String)o);
            }
            logDimensionCollection.setDw(taskDefine.getDw(), dwValueList.toArray(new String[dwValueList.size()]));
        } else {
            String targetKey = String.valueOf(dwValue);
            logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        }
        String periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName));
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            this.judgeDuplicateNames(null, createSnapshotContext.getTitle(), dataScheme, context, createSnapshotContext.getDimensionCollection().combineWithoutVarDim());
            if (null != monitor) {
                monitor.progressAndMessage(0.1, "\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a10%");
            }
            SnapshotInfo snapshotInfo = new SnapshotInfo();
            snapshotInfo.setDimensionCollection(createSnapshotContext.getDimensionCollection());
            Snapshot snapshot = new Snapshot();
            snapshot.setTitle(createSnapshotContext.getTitle());
            snapshot.setDescribe(createSnapshotContext.getDescribe());
            snapshot.setFormSchemeKey(createSnapshotContext.getFormSchemeKey());
            snapshotInfo.setSnapshot(snapshot);
            String id = UUID.randomUUID().toString();
            this.insertSnapshotInfoTable(createSnapshotContext, monitor, snapshot, taskDefine, dataScheme, id, context);
            TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
            Map<String, String> dwSnapshotFile = this.createSnapshotFile(createSnapshotContext, monitor, taskDefine, dimensionChanger, dataScheme);
            this.insertSnapshotRelTable(createSnapshotContext, monitor, id, context, snapshotRelTable, dimensionChanger, dwSnapshotFile);
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u521b\u5efa\u6210\u529f", "\u521b\u5efa\u5feb\u7167\u6210\u529f");
            return snapshotInfo;
        }
        catch (SnapshotDuplicateNameException e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u521b\u5efa\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a" + e.getMessage());
            throw e;
        }
    }

    private void judgeDuplicateNames(String snapshotId, String title, DataScheme dataScheme, DataAccessContext context, DimensionValueSet dimensionValueSet) throws SnapshotDuplicateNameException {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        ColumnModelDefine snapshotIDColum = null;
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
            if (snapshotField.getCode().equals("TITLE")) {
                queryModel.getColumnFilters().put(snapshotField, title);
                continue;
            }
            if (!snapshotField.getCode().equals("ID")) continue;
            snapshotIDColum = snapshotField;
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        ArrayList<String> snapshotIDs = new ArrayList<String>();
        try {
            INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                Iterator snapshotID = iNvwaDataRows.getRow(i).getValue(snapshotIDColum);
                snapshotIDs.add((String)((Object)snapshotID));
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (StringUtils.isNotEmpty((String)snapshotId)) {
            snapshotIDs.remove(snapshotId);
        }
        if (!snapshotIDs.isEmpty()) {
            ArrayList<String> dimColumnNames = new ArrayList<String>();
            dimColumnNames.add("MDCODE");
            dimColumnNames.add("DATATIME");
            List reportDimension = this.runtimeDataSchemeService.getReportDimension(dataScheme.getKey());
            if (null != reportDimension && !reportDimension.isEmpty()) {
                for (DataDimension dataDimension : reportDimension) {
                    IEntityDefine entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                    if (null != entity) {
                        dimColumnNames.add(entity.getDimensionName());
                        continue;
                    }
                    if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
                    dimColumnNames.add("ADJUST");
                }
            }
            TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
            NvwaQueryModel relQueryModel = new NvwaQueryModel();
            List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
            for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
                relQueryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
                if (!snapshotRelField.getCode().equals("SNAPSHOTID")) continue;
                relQueryModel.getColumnFilters().put(snapshotRelField, snapshotIDs);
            }
            HashMap<String, List> dimColumnNameAndValues = new HashMap<String, List>();
            INvwaDataAccess relDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(relQueryModel);
            try {
                List columns = relQueryModel.getColumns();
                INvwaDataSet iNvwaDataRows = relDataAccess.executeQueryWithRowKey(context);
                for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                    for (NvwaQueryColumn column : columns) {
                        ColumnModelDefine columnModel = column.getColumnModel();
                        for (String dimColumnName : dimColumnNames) {
                            if (!dimColumnName.equals(columnModel.getCode())) continue;
                            Object dimValue = iNvwaDataRows.getRow(i).getValue(columnModel);
                            List dimValues = dimColumnNameAndValues.computeIfAbsent(dimColumnName, k -> new ArrayList());
                            dimValues.add((String)dimValue);
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            String dwDimensionName = dimensionChanger.getDimensionName("MDCODE");
            Object DWvalue = dimensionValueSet.getValue(dwDimensionName);
            ArrayList<String> inDws = null;
            if (DWvalue instanceof List) {
                inDws = (ArrayList<String>)DWvalue;
            } else {
                inDws = new ArrayList<String>();
                inDws.add((String)DWvalue);
            }
            for (String inDw : inDws) {
                List dwValues = (List)dimColumnNameAndValues.get("MDCODE");
                if (null == dwValues) continue;
                for (int i = 0; i < dwValues.size(); ++i) {
                    if (!inDw.equals(dwValues.get(i))) continue;
                    boolean samedimValue = true;
                    for (String dimColumnName : dimColumnNames) {
                        String dimValue;
                        String dimensionName = dimensionChanger.getDimensionName(dimColumnName);
                        String indimValue = (String)dimensionValueSet.getValue(dimensionName);
                        if (indimValue.equals(dimValue = (String)((List)dimColumnNameAndValues.get(dimColumnName)).get(i))) continue;
                        samedimValue = false;
                        break;
                    }
                    if (!samedimValue) continue;
                    throw new SnapshotDuplicateNameException("Duplicate name.");
                }
            }
        }
    }

    private void insertSnapshotInfoTable(CreateSnapshotContext createSnapshotContext, AsyncTaskMonitor monitor, Snapshot snapshot, TaskDefine taskDefine, DataScheme dataScheme, String id, DataAccessContext context) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List columns = queryModel.getColumns();
        snapshot.setSnapshotId(id);
        ContextUser operator = NpContextHolder.getContext().getUser();
        try {
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block25: for (int i = 0; i < columns.size(); ++i) {
                switch (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                    case "ID": {
                        iNvwaDataRow.setValue(i, (Object)id);
                        continue block25;
                    }
                    case "TITLE": {
                        iNvwaDataRow.setValue(i, (Object)createSnapshotContext.getTitle());
                        continue block25;
                    }
                    case "DESCRIBTION": {
                        iNvwaDataRow.setValue(i, (Object)createSnapshotContext.getDescribe());
                        continue block25;
                    }
                    case "CREATTIME": {
                        Date creatDate = new Date();
                        iNvwaDataRow.setValue(i, (Object)creatDate);
                        snapshot.setCreatTime(creatDate);
                        continue block25;
                    }
                    case "CREATUSERID": {
                        iNvwaDataRow.setValue(i, (Object)operator.getId());
                        continue block25;
                    }
                    case "CREATUSERNAME": {
                        iNvwaDataRow.setValue(i, (Object)operator.getFullname());
                        snapshot.setCreatUserName(operator.getFullname());
                        continue block25;
                    }
                    case "TASKKEY": {
                        iNvwaDataRow.setValue(i, (Object)taskDefine.getKey());
                        continue block25;
                    }
                    case "FORMSCHEMEKEY": {
                        iNvwaDataRow.setValue(i, (Object)createSnapshotContext.getFormSchemeKey());
                        continue block25;
                    }
                    case "ISAUTOCREATED": {
                        if (createSnapshotContext.getIsAutoCreate()) {
                            iNvwaDataRow.setValue(i, (Object)"1");
                            continue block25;
                        }
                        iNvwaDataRow.setValue(i, (Object)"0");
                    }
                }
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.3, "\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a30%");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    private Map<String, String> createSnapshotFile(CreateSnapshotContext createSnapshotContext, AsyncTaskMonitor monitor, TaskDefine taskDefine, DimensionChanger dimensionChanger, DataScheme dataScheme) {
        String dwDimensionName = dimensionChanger.getDimensionName("MDCODE");
        String dataTimeDimensionName = dimensionChanger.getDimensionName("DATATIME");
        List<String> formKeys = createSnapshotContext.getFormKeys();
        SnapshotFileTool snapshotFileTool = new SnapshotFileTool();
        HashMap<String, String> dwSnapshotFile = new HashMap<String, String>();
        List dimensionCombinations = createSnapshotContext.getDimensionCollection().getDimensionCombinations();
        File file = null;
        try {
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                List<String> dimList = new ArrayList<String>();
                List<String> dimNames = new ArrayList<String>();
                List<List<String>> dimDatass = new ArrayList<List<String>>();
                if (null != this.snapshotDimService) {
                    QueryDimResult queryDimResult = this.snapshotDimService.queryDims(createSnapshotContext.getDimensionCollection(), taskDefine.getKey());
                    if (null != queryDimResult && queryDimResult.isEnable()) {
                        List<DimInfo> dimInfos = queryDimResult.getDimInfos();
                        dimList = dimInfos.stream().map(DimInfo::getDimId).collect(Collectors.toList());
                        dimNames = dimInfos.stream().map(DimInfo::getDimName).collect(Collectors.toList());
                        dimDatass = dimInfos.stream().map(DimInfo::getDimDatas).collect(Collectors.toList());
                    } else {
                        this.queryDims(dataScheme, taskDefine, createSnapshotContext.getFormSchemeKey(), dimensionValueSet, dimList, dimNames, dimDatass, dwDimensionName, dataTimeDimensionName);
                    }
                } else {
                    this.queryDims(dataScheme, taskDefine, createSnapshotContext.getFormSchemeKey(), dimensionValueSet, dimList, dimNames, dimDatass, dwDimensionName, dataTimeDimensionName);
                }
                DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
                dimensionCollectionBuilder.setEntityValue(dwDimensionName, taskDefine.getDw(), new Object[]{dimensionValueSet.getValue(dwDimensionName)});
                dimensionCollectionBuilder.setEntityValue(dataTimeDimensionName, taskDefine.getDateTime(), new Object[]{dimensionValueSet.getValue(dataTimeDimensionName)});
                Object adJust = dimensionValueSet.getValue("ADJUST");
                if (null != adJust) {
                    dimensionCollectionBuilder.setEntityValue("ADJUST", "ADJUST", new Object[]{adJust});
                }
                for (int i = 0; i < dimList.size(); ++i) {
                    dimensionCollectionBuilder.setEntityValue(dimNames.get(i), dimList.get(i), new Object[]{dimDatass.get(i)});
                }
                DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
                String extZipFile = this.snapshotFileService.createSnapshotFile(dataScheme.getKey(), dimensionCollection, formKeys);
                file = new File(FilenameUtils.normalize(extZipFile));
                FileInputStream fileStream = new FileInputStream(file);
                Throwable throwable = null;
                try {
                    SnapshotFileInfo snapshotFileInfo = snapshotFileTool.upload(fileStream);
                    dwSnapshotFile.put(dimensionValueSet.getValue(dwDimensionName).toString(), snapshotFileInfo.getKey());
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (fileStream == null) continue;
                    if (throwable != null) {
                        try {
                            ((InputStream)fileStream).close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    ((InputStream)fileStream).close();
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            boolean delete;
            if (null != file && !(delete = file.delete())) {
                logger.error("\u5220\u9664\u521b\u5efa\u7684\u5feb\u7167\u6587\u4ef6\u5931\u8d25");
            }
            try {
                snapshotFileTool.close();
            }
            catch (ObjectStorageException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.8, "\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a80%");
        }
        return dwSnapshotFile;
    }

    private void queryDims(DataScheme dataScheme, TaskDefine taskDefine, String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> dimList, List<String> dimNames, List<List<String>> dimDatass, String dwDimensionName, String dataTimeDimensionName) throws Exception {
        List dataSchemeDimensions;
        EntityViewDefine mainDimension = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        List reportDimension = this.runtimeDataSchemeService.getReportDimension(dataScheme.getKey());
        List reportDimKeys = null;
        if (null != reportDimension && !reportDimension.isEmpty()) {
            reportDimKeys = reportDimension.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        }
        if (null != (dataSchemeDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.DIMENSION)) && !dataSchemeDimensions.isEmpty()) {
            for (DataDimension dataSchemeDimension : dataSchemeDimensions) {
                IEntityDefine entity = this.iEntityMetaService.queryEntity(dataSchemeDimension.getDimKey());
                if (null == entity) continue;
                if (null != reportDimKeys && reportDimKeys.contains(dataSchemeDimension.getDimKey())) {
                    dimList.add(dataSchemeDimension.getDimKey());
                    dimNames.add(entity.getDimensionName());
                    String reportDimValue = (String)dimensionValueSet.getValue(entity.getDimensionName());
                    dimDatass.add(Collections.singletonList(reportDimValue));
                    continue;
                }
                boolean isReferDim = false;
                List taskOrgLinkDefines = this.definitionRunTimeViewController.listTaskOrgLinkByTask(taskDefine.getKey());
                List entityRefer = this.iEntityMetaService.getEntityRefer(((TaskOrgLinkDefine)taskOrgLinkDefines.get(0)).getEntity());
                for (IEntityRefer iEntityRefer : entityRefer) {
                    if (!iEntityRefer.getReferEntityId().equals(dataSchemeDimension.getDimKey()) || !iEntityRefer.getOwnField().equals(dataSchemeDimension.getDimAttribute())) continue;
                    isReferDim = true;
                    break;
                }
                if (isReferDim) {
                    List<String> refDimDatas = this.queryRefDimDatas(formSchemeKey, dimensionValueSet, dwDimensionName, dataTimeDimensionName, mainDimension, dataSchemeDimension, taskOrgLinkDefines);
                    dimList.add(dataSchemeDimension.getDimKey());
                    dimNames.add(entity.getDimensionName());
                    dimDatass.add(refDimDatas);
                    continue;
                }
                List<String> allDimDatas = this.queryAllDimDatas(formSchemeKey, dimensionValueSet, dwDimensionName, dataTimeDimensionName, mainDimension, dataSchemeDimension);
                dimList.add(dataSchemeDimension.getDimKey());
                dimNames.add(entity.getDimensionName());
                dimDatass.add(allDimDatas);
            }
        }
    }

    @NotNull
    private List<String> queryRefDimDatas(String formSchemeKey, DimensionValueSet dimensionValueSet, String dwDimensionName, String dataTimeDimensionName, EntityViewDefine mainDimension, DataDimension dataSchemeDimension, List<TaskOrgLinkDefine> taskOrgLinkDefines) throws Exception {
        HashSet<String> dimDatas = new HashSet<String>();
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(taskOrgLinkDefine.getEntity());
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            DimensionValueSet masterKeys = new DimensionValueSet();
            masterKeys.setValue(dataTimeDimensionName, dimensionValueSet.getValue(dataTimeDimensionName));
            Object dwEntityData = dimensionValueSet.getValue(dwDimensionName);
            masterKeys.setValue(dwDimensionName, dwEntityData);
            entityQuery.setMasterKeys(masterKeys);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
            IEntityRow entityKey = iEntityTable.findByEntityKey((String)dwEntityData);
            if (null == entityKey) continue;
            AbstractData value = entityKey.getValue(dataSchemeDimension.getDimAttribute());
            if (null != value && !value.isNull) {
                String[] refDimValues = value.getAsString().split(";");
                dimDatas.addAll(Arrays.asList(refDimValues));
                continue;
            }
            List<String> allDimDatas = this.queryAllDimDatas(formSchemeKey, dimensionValueSet, dwDimensionName, dataTimeDimensionName, mainDimension, dataSchemeDimension);
            dimDatas.addAll(allDimDatas);
        }
        return new ArrayList<String>(dimDatas);
    }

    @NotNull
    private List<String> queryAllDimDatas(String formSchemeKey, DimensionValueSet dimensionValueSet, String dwDimensionName, String dataTimeDimensionName, EntityViewDefine mainDimension, DataDimension dataSchemeDimension) throws Exception {
        IDimensionFilter dimensionFilter = this.runTimeViewController.getDimensionFilter(formSchemeKey, dataSchemeDimension.getDimKey());
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dimensionFilter);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(dataTimeDimensionName, dimensionValueSet.getValue(dataTimeDimensionName));
        Object dwEntityData = dimensionValueSet.getValue(dwDimensionName);
        masterKeys.setValue(dwDimensionName, dwEntityData);
        entityQuery.setMasterKeys(masterKeys);
        if (!StringUtils.isEmpty((String)dataSchemeDimension.getDimAttribute())) {
            ReferRelation referRelation = new ReferRelation();
            referRelation.setViewDefine(mainDimension);
            if (dwEntityData instanceof List) {
                referRelation.setRange((List)dwEntityData);
            } else {
                ArrayList<String> dwEntityDataList = new ArrayList<String>(1);
                dwEntityDataList.add(dwEntityData.toString());
                referRelation.setRange(dwEntityDataList);
            }
            entityQuery.addReferRelation(referRelation);
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
        ArrayList<String> dimDatas = new ArrayList<String>();
        for (IEntityRow row : iEntityTable.getAllRows()) {
            dimDatas.add(row.getEntityKeyData());
        }
        return dimDatas;
    }

    private void insertSnapshotRelTable(CreateSnapshotContext createSnapshotContext, AsyncTaskMonitor monitor, String id, DataAccessContext context, TableModelDefine snapshotRelTable, DimensionChanger dimensionChanger, Map<String, String> dwSnapshotFile) {
        DimensionValueSet dimensionValueSet = createSnapshotContext.getDimensionCollection().combineWithoutVarDim();
        NvwaQueryModel relQueryModel = new NvwaQueryModel();
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            relQueryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
        }
        INvwaUpdatableDataAccess relUpdatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(relQueryModel);
        List relColumns = relQueryModel.getColumns();
        Set<String> dws = dwSnapshotFile.keySet();
        try {
            INvwaDataUpdator iNvwaDataUpdator = relUpdatableDataAccess.openForUpdate(context);
            for (String dw : dws) {
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                block14: for (int i = 0; i < relColumns.size(); ++i) {
                    switch (((NvwaQueryColumn)relColumns.get(i)).getColumnModel().getCode()) {
                        case "SNAPSHOTID": {
                            iNvwaDataRow.setValue(i, (Object)id);
                            continue block14;
                        }
                        case "SSFILEKEY": {
                            iNvwaDataRow.setValue(i, (Object)dwSnapshotFile.get(dw));
                            continue block14;
                        }
                        case "MDCODE": {
                            iNvwaDataRow.setValue(i, (Object)dw);
                            continue block14;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)relColumns.get(i)).getColumnModel());
                            iNvwaDataRow.setValue(i, dimensionValueSet.getValue(dimensionName));
                        }
                    }
                }
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.9, "\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a90%");
        }
    }

    @Override
    public void batchCreateSnapshot(BatchCreateSnapshotContext batchCreateSnapshotContext, AsyncTaskMonitor monitor) throws SnapshotException {
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "\u6279\u91cf\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a10%");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(batchCreateSnapshotContext.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        ArrayList<String> dwValueCatch = new ArrayList<String>();
        ArrayList<String> periodValueCatch = new ArrayList<String>();
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        try {
            ArrayList<String> snapshotFileKeys = new ArrayList<String>();
            TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
            HashMap<String, DSDimensionInfo> dataschemeDimCatch = new HashMap<String, DSDimensionInfo>();
            List<CreateSnapshotInfo> createSnapshotInfos = batchCreateSnapshotContext.getCreateSnapshotInfos();
            for (CreateSnapshotInfo createSnapshotInfo : createSnapshotInfos) {
                String periodCode;
                String snapshotFileKey = this.createSnapshotFile(createSnapshotInfo, taskDefine, dimensionChanger, dataScheme, dataschemeDimCatch);
                snapshotFileKeys.add(snapshotFileKey);
                DimensionValueSet dimensionValueSet = createSnapshotInfo.getDimensionCombination().toDimensionValueSet();
                String targetKey = String.valueOf(dimensionValueSet.getValue(DWdimensionName));
                if (!dwValueCatch.contains(targetKey)) {
                    dwValueCatch.add(targetKey);
                }
                if (periodValueCatch.contains(periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName)))) continue;
                periodValueCatch.add(periodCode);
            }
            if (!dwValueCatch.isEmpty()) {
                logDimensionCollection.setDw(taskDefine.getDw(), dwValueCatch.toArray(new String[dwValueCatch.size()]));
            }
            if (!periodValueCatch.isEmpty()) {
                logDimensionCollection.setPeriod(taskDefine.getDateTime(), (String)periodValueCatch.get(0));
            }
            if (null != monitor) {
                monitor.progressAndMessage(0.8, "\u6279\u91cf\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a80%");
            }
            this.batchInsertSnapshotTable(batchCreateSnapshotContext, snapshotFileKeys, dimensionChanger, dataScheme, logHellperUtil);
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u6279\u91cf\u521b\u5efa\u6210\u529f", "\u5feb\u7167\u6279\u91cf\u521b\u5efa\u6210\u529f");
            if (null != monitor) {
                monitor.progressAndMessage(0.9, "\u6279\u91cf\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a90%");
            }
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u6279\u91cf\u521b\u5efa\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a" + e.getMessage());
            throw e;
        }
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private String createSnapshotFile(CreateSnapshotInfo createSnapshotInfo, TaskDefine taskDefine, DimensionChanger dimensionChanger, DataScheme dataScheme, Map<String, DSDimensionInfo> dataschemeDimCatch) throws SnapshotException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public ResultInfo batchCreateSnapshotJudgeName(BatchCreateSnapshotParam param, AsyncTaskMonitor monitor) {
        Object filterDims;
        List<DimensionCombination> sameNameDims;
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "\u6279\u91cf\u521b\u5efa\u5feb\u7167\u603b\u8fdb\u5ea6\uff1a10%");
        }
        ResultInfo resultInfo = new ResultInfo();
        Object dimensionCombinations = param.getDimensionCollection().getDimensionCombinations();
        if (null != param.getJudgeNameType() && JudgeNameType.JUDGE_INTERRUPT.equals((Object)param.getJudgeNameType())) {
            sameNameDims = this.batchJudgeDuplicateName(param.getTitle(), param.getTaskKey(), param.getDimensionCollection());
            if (!sameNameDims.isEmpty()) {
                resultInfo.setSuccess(false);
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("\u6709\u91cd\u540d\u5feb\u7167\uff0c\u7ef4\u5ea6\u4e3a\uff1a");
                for (DimensionCombination dimensionCombination : sameNameDims) {
                    errorMessage.append(dimensionCombination.toDimensionValueSet().toString()).append(";");
                }
                resultInfo.setErrorMessage(errorMessage.toString());
                return resultInfo;
            }
        } else if (null != param.getJudgeNameType() && JudgeNameType.JUDGE_SKIP.equals((Object)param.getJudgeNameType()) && !(sameNameDims = this.batchJudgeDuplicateName(param.getTitle(), param.getTaskKey(), param.getDimensionCollection())).isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("\u6709\u91cd\u540d\u5feb\u7167\uff0c\u4ee5\u4e0b\u7ef4\u5ea6\u5df2\u8df3\u8fc7\uff1a");
            for (DimensionCombination dimensionCombination : sameNameDims) {
                message.append(dimensionCombination.toDimensionValueSet().toString()).append(";");
            }
            resultInfo.setMessage(message.toString());
            filterDims = new ArrayList();
            Iterator iterator = dimensionCombinations.iterator();
            while (iterator.hasNext()) {
                DimensionCombination dimensionCombination = (DimensionCombination)iterator.next();
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                boolean flag = true;
                for (DimensionCombination sameNameDim3 : sameNameDims) {
                    DimensionValueSet sameNameDimensionValueSet = sameNameDim3.toDimensionValueSet();
                    if (0 != dimensionValueSet.compareTo(sameNameDimensionValueSet)) continue;
                    flag = false;
                    break;
                }
                if (!flag) continue;
                filterDims.add(dimensionCombination);
            }
            dimensionCombinations = filterDims;
        }
        BatchCreateSnapshotContext batchCreateSnapshotContext = new BatchCreateSnapshotContext();
        batchCreateSnapshotContext.setTaskKey(param.getTaskKey());
        ArrayList<CreateSnapshotInfo> createSnapshotInfos = new ArrayList<CreateSnapshotInfo>();
        filterDims = dimensionCombinations.iterator();
        while (filterDims.hasNext()) {
            DimensionCombination dimensionCombination = (DimensionCombination)filterDims.next();
            CreateSnapshotInfo createSnapshotInfo = new CreateSnapshotInfo(param.getTitle(), param.getDescribe(), param.getFormSchemeKey(), dimensionCombination, param.getFormKeys(), false);
            createSnapshotInfos.add(createSnapshotInfo);
        }
        batchCreateSnapshotContext.setCreateSnapshotInfos(createSnapshotInfos);
        try {
            this.batchCreateSnapshot(batchCreateSnapshotContext, monitor);
            resultInfo.setSuccess(true);
        }
        catch (SnapshotException e) {
            resultInfo.setSuccess(false);
            resultInfo.setMessage(null);
            resultInfo.setErrorMessage("\u6279\u91cf\u521b\u5efa\u5feb\u7167\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage());
        }
        return resultInfo;
    }

    private List<DimensionCombination> batchJudgeDuplicateName(String title, String taskKey, DimensionCollection dimensionCollection) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        ColumnModelDefine snapshotIDColum = null;
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
            if (snapshotField.getCode().equals("TITLE")) {
                queryModel.getColumnFilters().put(snapshotField, title);
                continue;
            }
            if (!snapshotField.getCode().equals("ID")) continue;
            snapshotIDColum = snapshotField;
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        ArrayList<String> snapshotIDs = new ArrayList<String>();
        try {
            INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                Object snapshotID = iNvwaDataRows.getRow(i).getValue(snapshotIDColum);
                snapshotIDs.add((String)snapshotID);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<DimensionCombination> sameNameDims = new ArrayList<DimensionCombination>();
        if (!snapshotIDs.isEmpty()) {
            TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
            NvwaQueryModel relQueryModel = new NvwaQueryModel();
            List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
            ColumnModelDefine DWColum = null;
            ColumnModelDefine periodColum = null;
            for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
                relQueryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
                if (snapshotRelField.getCode().equals("SNAPSHOTID")) {
                    relQueryModel.getColumnFilters().put(snapshotRelField, snapshotIDs);
                    continue;
                }
                if (snapshotRelField.getCode().equals("MDCODE")) {
                    DWColum = snapshotRelField;
                    continue;
                }
                if (!snapshotRelField.getCode().equals("DATATIME")) continue;
                periodColum = snapshotRelField;
            }
            ArrayList<String> DWs = new ArrayList<String>();
            ArrayList<String> periods = new ArrayList<String>();
            INvwaDataAccess relDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(relQueryModel);
            try {
                INvwaDataSet iNvwaDataRows = relDataAccess.executeQueryWithRowKey(context);
                for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                    Object DWvalue = iNvwaDataRows.getRow(i).getValue(DWColum);
                    DWs.add((String)DWvalue);
                    Object periodValue = iNvwaDataRows.getRow(i).getValue(periodColum);
                    periods.add((String)periodValue);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            String dwDimensionName = dimensionChanger.getDimensionName("MDCODE");
            String periodDimensionName = dimensionChanger.getDimensionName("DATATIME");
            List dimensionCombinations = dimensionCollection.getDimensionCombinations();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                String inDw = (String)dimensionCombination.getValue(dwDimensionName);
                String inPeriod = (String)dimensionCombination.getValue(periodDimensionName);
                for (int i = 0; i < DWs.size(); ++i) {
                    if (!inDw.equals(DWs.get(i)) || !inPeriod.equals(periods.get(i))) continue;
                    sameNameDims.add(dimensionCombination);
                }
            }
        }
        return sameNameDims;
    }

    private void batchInsertSnapshotTable(BatchCreateSnapshotContext batchCreateSnapshotContext, List<String> snapshotFileKeys, DimensionChanger dimensionChanger, DataScheme dataScheme, LogHellperUtil logHellperUtil) throws SnapshotException {
        NvwaQueryModel snapshotAueryModel = new NvwaQueryModel();
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            snapshotAueryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
        }
        INvwaUpdatableDataAccess snapshotUpdatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(snapshotAueryModel);
        List snapshotColumns = snapshotAueryModel.getColumns();
        ContextUser operator = NpContextHolder.getContext().getUser();
        TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
        NvwaQueryModel relQueryModel = new NvwaQueryModel();
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            relQueryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
        }
        INvwaUpdatableDataAccess relUpdatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(relQueryModel);
        List relColumns = relQueryModel.getColumns();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        int index = 0;
        try {
            INvwaDataUpdator snapshotDataUpdator = snapshotUpdatableDataAccess.openForUpdate(context);
            INvwaDataUpdator relDataUpdator = relUpdatableDataAccess.openForUpdate(context);
            List<CreateSnapshotInfo> createSnapshotInfos = batchCreateSnapshotContext.getCreateSnapshotInfos();
            for (int i = 0; i < createSnapshotInfos.size(); ++i) {
                String id = UUID.randomUUID().toString();
                INvwaDataRow snapshotataRow = snapshotDataUpdator.addInsertRow();
                block27: for (int j = 0; j < snapshotColumns.size(); ++j) {
                    switch (((NvwaQueryColumn)snapshotColumns.get(j)).getColumnModel().getCode()) {
                        case "ID": {
                            snapshotataRow.setValue(j, (Object)id);
                            continue block27;
                        }
                        case "TITLE": {
                            snapshotataRow.setValue(j, (Object)createSnapshotInfos.get(i).getTitle());
                            continue block27;
                        }
                        case "DESCRIBTION": {
                            snapshotataRow.setValue(j, (Object)createSnapshotInfos.get(i).getDescribe());
                            continue block27;
                        }
                        case "CREATTIME": {
                            Date creatDate = new Date();
                            snapshotataRow.setValue(j, (Object)creatDate);
                            continue block27;
                        }
                        case "CREATUSERID": {
                            snapshotataRow.setValue(j, (Object)operator.getId());
                            continue block27;
                        }
                        case "CREATUSERNAME": {
                            snapshotataRow.setValue(j, (Object)operator.getFullname());
                            continue block27;
                        }
                        case "TASKKEY": {
                            snapshotataRow.setValue(j, (Object)batchCreateSnapshotContext.getTaskKey());
                            continue block27;
                        }
                        case "FORMSCHEMEKEY": {
                            snapshotataRow.setValue(j, (Object)createSnapshotInfos.get(i).getFormSchemeKey());
                            continue block27;
                        }
                        case "ISAUTOCREATED": {
                            if (createSnapshotInfos.get(i).isAutoCreate()) {
                                snapshotataRow.setValue(j, (Object)"1");
                                continue block27;
                            }
                            snapshotataRow.setValue(j, (Object)"0");
                        }
                    }
                }
                DimensionValueSet dimensionValueSet = createSnapshotInfos.get(i).getDimensionCombination().toDimensionValueSet();
                INvwaDataRow relataRow = relDataUpdator.addInsertRow();
                for (int j = 0; j < relColumns.size(); ++j) {
                    if (((NvwaQueryColumn)relColumns.get(j)).getColumnModel().getCode().equals("SNAPSHOTID")) {
                        relataRow.setValue(j, (Object)id);
                        continue;
                    }
                    if (((NvwaQueryColumn)relColumns.get(j)).getColumnModel().getCode().equals("SSFILEKEY")) {
                        relataRow.setValue(j, (Object)snapshotFileKeys.get(i));
                        continue;
                    }
                    String dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)relColumns.get(j)).getColumnModel());
                    relataRow.setValue(j, dimensionValueSet.getValue(dimensionName));
                }
                if (500 != ++index) continue;
                snapshotDataUpdator.commitChanges(context);
                relDataUpdator.commitChanges(context);
                index = 0;
            }
            if (index != 0) {
                snapshotDataUpdator.commitChanges(context);
                relDataUpdator.commitChanges(context);
            }
        }
        catch (Exception e) {
            logHellperUtil.error(batchCreateSnapshotContext.getTaskKey(), null, "\u5feb\u7167\u6279\u91cf\u521b\u5efa\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u63d2\u5165\u6570\u636e\u5e93\u5931\u8d25");
            throw new SnapshotException(e.getMessage());
        }
    }

    @Override
    public void deleteSnapshot(String taskKey, String snapshotId) throws SnapshotException {
        this.deleteSnapshot(taskKey, snapshotId, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteSnapshot(String taskKey, String snapshotId, boolean ignorePermission) throws SnapshotException {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        if (!ignorePermission) {
            this.judgeUser(snapshotId, dataScheme.getBizCode(), context);
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
            if (!snapshotField.getCode().equals("ID")) continue;
            queryModel.getColumnFilters().put(snapshotField, snapshotId);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            iNvwaDataRows.deleteAll();
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u5feb\u7167\u5220\u9664\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u5220\u9664\u5feb\u7167\u4fe1\u606f\u5931\u8d25");
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return;
        }
        ArrayList<String> SSFileKeys = new ArrayList<String>();
        NvwaQueryModel relQueryModel = new NvwaQueryModel();
        TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        ColumnModelDefine SSFileKeyColumnModel = null;
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            relQueryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
            if (snapshotRelField.getCode().equals("SNAPSHOTID")) {
                relQueryModel.getColumnFilters().put(snapshotRelField, snapshotId);
                continue;
            }
            if (!snapshotRelField.getCode().equals("SSFILEKEY")) continue;
            SSFileKeyColumnModel = snapshotRelField;
        }
        INvwaUpdatableDataAccess relUpdatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(relQueryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = relUpdatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                String fileKey = row.getValue(SSFileKeyColumnModel).toString();
                SSFileKeys.add(fileKey);
            }
            iNvwaDataRows.deleteAll();
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u5feb\u7167\u5220\u9664\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u5220\u9664\u5feb\u7167\u4fe1\u606f\u5931\u8d25");
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return;
        }
        SnapshotFileTool snapshotFileTool = new SnapshotFileTool();
        try {
            for (String ssFileKey : SSFileKeys) {
                snapshotFileTool.delete(ssFileKey);
            }
            logHellperUtil.info(taskDefine.getKey(), null, "\u5feb\u7167\u5220\u9664\u6210\u529f", "\u5220\u9664\u5feb\u7167\u6210\u529f");
        }
        catch (IOException e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u5feb\u7167\u5220\u9664\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u5220\u9664\u5feb\u7167\u6587\u4ef6\u5931\u8d25");
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            try {
                snapshotFileTool.close();
            }
            catch (ObjectStorageException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @Override
    public void updateSnapshot(UpdateSnapshotInfo updateSnapshotInfo) throws SnapshotException {
        this.updateSnapshot(updateSnapshotInfo, false);
    }

    @Override
    public void updateSnapshot(UpdateSnapshotInfo updateSnapshotInfo, boolean ignorePermission) throws SnapshotException {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(updateSnapshotInfo.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        if (!ignorePermission) {
            this.judgeUser(updateSnapshotInfo.getSnapshotId(), dataScheme.getBizCode(), context);
        }
        this.judgeDuplicateNames(updateSnapshotInfo.getSnapshotId(), updateSnapshotInfo.getTitle(), dataScheme, context, updateSnapshotInfo.getDimensionCombination().toDimensionValueSet());
        String DWdimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimensionName = periodAdapter.getPeriodDimensionName();
        DimensionValueSet dimensionValueSet = updateSnapshotInfo.getDimensionCombination().toDimensionValueSet();
        String targetKey = String.valueOf(dimensionValueSet.getValue(DWdimensionName));
        String periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, LOG_TITLE);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            switch (snapshotField.getCode()) {
                case "ID": {
                    queryModel.getColumnFilters().put(snapshotField, updateSnapshotInfo.getSnapshotId());
                    break;
                }
                case "TITLE": {
                    queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
                    break;
                }
                case "DESCRIBTION": {
                    queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
                }
            }
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List columns = queryModel.getColumns();
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                for (int j = 0; j < columns.size(); ++j) {
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("TITLE")) {
                        row.setValue(j, (Object)updateSnapshotInfo.getTitle());
                        continue;
                    }
                    row.setValue(j, (Object)updateSnapshotInfo.getDescribe());
                }
            }
            iNvwaDataRows.commitChanges(context);
            logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u66f4\u65b0\u6210\u529f", "\u5feb\u7167\u66f4\u65b0\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u5feb\u7167\u66f4\u65b0\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a" + e.getMessage());
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void judgeUser(String snapShotId, String dataSchemeBizCode, DataAccessContext context) throws SnapshotNoPermissionsException {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataSchemeBizCode);
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        ColumnModelDefine creatUserColum = null;
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
            if (snapshotField.getCode().equals("CREATUSERID")) {
                creatUserColum = snapshotField;
                continue;
            }
            if (!snapshotField.getCode().equals("ID")) continue;
            queryModel.getColumnFilters().put(snapshotField, snapShotId);
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        String userid = "";
        try {
            INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                Object userId = iNvwaDataRows.getRow(i).getValue(creatUserColum);
                userid = (String)userId;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ContextUser operator = NpContextHolder.getContext().getUser();
        if (!operator.getId().equals(SYS_ADMIN) && !operator.getId().equals(userid)) {
            throw new SnapshotNoPermissionsException("User does not have permissions.");
        }
    }

    @Override
    public List<SnapshotInfo> querySnapshot(DimensionCombination dimensionCombination, String taskKey) {
        ArrayList<SnapshotInfo> snapshotInfos = new ArrayList<SnapshotInfo>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        ColumnModelDefine snapshotColum = null;
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(snapshotRelTable.getName());
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
            String dimensionName = dimensionChanger.getDimensionName(snapshotRelField);
            if (StringUtils.isNotEmpty((String)dimensionName) && null != dimensionValueSet.getValue(dimensionName)) {
                queryModel.getColumnFilters().put(snapshotRelField, dimensionValueSet.getValue(dimensionName));
            }
            if (!snapshotRelField.getCode().equals("SNAPSHOTID")) continue;
            snapshotColum = snapshotRelField;
        }
        ColumnModelDefine creatTimeColum = null;
        TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
            if (snapshotField.getCode().equals("CREATTIME")) {
                creatTimeColum = snapshotField;
                continue;
            }
            if (!snapshotField.getCode().equals("TASKKEY")) continue;
            queryModel.getColumnFilters().put(snapshotField, taskKey);
        }
        queryModel.getOrderByItems().add(new OrderByItem(creatTimeColum, true));
        SnapshotJoinProvider provider = new SnapshotJoinProvider();
        context.setSqlJoinProvider((ISqlJoinProvider)provider);
        queryModel.setMainTableName(snapshotRelTable.getName());
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = queryModel.getColumns();
        LinkedHashMap<String, DimensionCollectionBuilder> snapshotIDMappingDim = new LinkedHashMap<String, DimensionCollectionBuilder>();
        LinkedHashMap<String, Snapshot> snapshotIDMappingSnapshot = new LinkedHashMap<String, Snapshot>();
        try {
            INvwaDataSet iNvwaDataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                Object value = row.getValue(snapshotColum);
                String snapshotID = (String)value;
                if (null == snapshotIDMappingDim.get(snapshotID)) {
                    DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
                    snapshotIDMappingDim.put(snapshotID, builder);
                }
                DimensionCollectionBuilder collectionBuilder = (DimensionCollectionBuilder)snapshotIDMappingDim.get(snapshotID);
                for (NvwaQueryColumn column : columns) {
                    String dimensionName = dimensionChanger.getDimensionName(column.getColumnModel());
                    if (!StringUtils.isNotEmpty((String)dimensionName) || null == dimensionValueSet.getValue(dimensionName)) continue;
                    collectionBuilder.setValue(dimensionName, new Object[]{(String)row.getValue(column.getColumnModel())});
                }
                if (null != snapshotIDMappingSnapshot.get(snapshotID)) continue;
                Snapshot snapshot = new Snapshot();
                snapshot.setSnapshotId(snapshotID);
                snapshotIDMappingSnapshot.put(snapshotID, snapshot);
                block22: for (int j = 0; j < columns.size(); ++j) {
                    switch (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                        case "TITLE": {
                            String title = (String)row.getValue(j);
                            snapshot.setTitle(title);
                            continue block22;
                        }
                        case "DESCRIBTION": {
                            String discribtion = (String)row.getValue(j);
                            snapshot.setDescribe(discribtion);
                            continue block22;
                        }
                        case "CREATTIME": {
                            Date creatTime = ((GregorianCalendar)row.getValue(j)).getTime();
                            snapshot.setCreatTime(creatTime);
                            continue block22;
                        }
                        case "CREATUSERNAME": {
                            String creatUserName = (String)row.getValue(j);
                            snapshot.setCreatUserName(creatUserName);
                            continue block22;
                        }
                        case "FORMSCHEMEKEY": {
                            String formShemeKey = (String)row.getValue(j);
                            snapshot.setFormSchemeKey(formShemeKey);
                            continue block22;
                        }
                        case "ISAUTOCREATED": {
                            String isAutoCreate = (String)row.getValue(j);
                            if ("1".equals(isAutoCreate)) {
                                snapshot.setAutoCreate(true);
                                continue block22;
                            }
                            snapshot.setAutoCreate(false);
                        }
                    }
                }
            }
            for (String snapshotId : snapshotIDMappingDim.keySet()) {
                SnapshotInfo snapshotInfo = new SnapshotInfo();
                snapshotInfo.setDimensionCollection(((DimensionCollectionBuilder)snapshotIDMappingDim.get(snapshotId)).getCollection());
                snapshotInfo.setSnapshot((Snapshot)snapshotIDMappingSnapshot.get(snapshotId));
                snapshotInfos.add(snapshotInfo);
            }
            return snapshotInfos;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return snapshotInfos;
        }
    }
}

