/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.IO.ProcessDataImportResult;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryTableModel {
    private static final Logger logger = LoggerFactory.getLogger(QueryTableModel.class);
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;

    public List<UploadStateNew> queryProcessStateDataList(FormSchemeDefine formScheme, DimensionValueSet dims, List<String> formOrGroupKeys) {
        ArrayList<UploadStateNew> processStateDataList = new ArrayList<UploadStateNew>();
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
        String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.getTableByCode(tableCode);
        if (table == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848:" + formScheme.getKey() + ";" + formScheme.getFormSchemeCode() + ";" + formScheme.getTitle() + "\u6240\u5bf9\u5e94\u7684\u5386\u53f2\u72b6\u6001\u8868\u4e0d\u5b58\u5728");
        }
        List<ColumnModelDefine> allColumnModels = this.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumnModels) {
            Object value;
            String dimensionName;
            if ("FORMID".equals(columnModelDefine.getCode()) && formOrGroupKeys != null && !formOrGroupKeys.isEmpty()) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formOrGroupKeys);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null && (value = dims.getValue(dimensionName)) != null && !"".equals(value)) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
                if (!"FORMID".equals(columnModelDefine.getCode())) {
                    OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                    nvwaQueryModel.getOrderByItems().add(orderByItem);
                }
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        List<ColumnModelDefine> dimensionColumns = this.getDimensionValueSetColumns(table, allColumnModels);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
        try {
            INvwaDataSet executeQuery = readOnlyDataAccess.executeQueryWithRowKey(context);
            for (INvwaDataRow iNvwaDataRow : executeQuery) {
                UploadStateNew uploadState = new UploadStateNew();
                ArrayKey rowKey = iNvwaDataRow.getRowKey();
                DimensionValueSet dimensionValue = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                uploadState.setEntities(dimensionValue);
                block20: for (int j = 0; j < allColumnModels.size(); ++j) {
                    ColumnModelDefine columnModel = allColumnModels.get(j);
                    Object value = iNvwaDataRow.getValue(columnModel);
                    if (value == null || "".equals(value)) continue;
                    switch (columnModel.getCode()) {
                        case "PREVEVENT": {
                            uploadState.setPreEvent(value.toString());
                            continue block20;
                        }
                        case "CURNODE": {
                            uploadState.setTaskId(value.toString());
                            continue block20;
                        }
                        case "FORMID": {
                            uploadState.setFormId(value.toString());
                            continue block20;
                        }
                        case "FORCE_STATE": {
                            uploadState.setForce(value.toString());
                            continue block20;
                        }
                        case "START_TIME": {
                            if (!(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setStartTime(time);
                            continue block20;
                        }
                        case "UPDATE_TIME": {
                            if (!(value instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)value;
                            Date time = gc.getTime();
                            uploadState.setUpdateTime(time);
                            continue block20;
                        }
                    }
                }
                processStateDataList.add(uploadState);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return processStateDataList;
    }

    public List<UploadRecordNew> queryProcessHistoryStates(FormSchemeDefine formScheme, DimensionValueSet dims, List<String> formOrGroupKeys) {
        ArrayList<UploadRecordNew> historyStateList = new ArrayList<UploadRecordNew>();
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
        String tableCode = "SYS_UP_HI_" + formScheme.getFormSchemeCode();
        TableModelDefine table = this.getTableByCode(tableCode);
        if (table == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848:" + formScheme.getKey() + ";" + formScheme.getFormSchemeCode() + ";" + formScheme.getTitle() + "\u6240\u5bf9\u5e94\u7684\u5386\u53f2\u72b6\u6001\u8868\u4e0d\u5b58\u5728");
        }
        List<ColumnModelDefine> allColumns = this.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableCode);
        for (ColumnModelDefine columnModelDefine : allColumns) {
            String dimensionName;
            if ("TIME_".equals(columnModelDefine.getCode())) {
                OrderByItem orderByItem = new OrderByItem(columnModelDefine, false);
                nvwaQueryModel.getOrderByItems().add(orderByItem);
            }
            if ("FORMID".equals(columnModelDefine.getCode()) && formOrGroupKeys != null && !formOrGroupKeys.isEmpty()) {
                nvwaQueryModel.getColumnFilters().put(columnModelDefine, formOrGroupKeys);
            }
            if ((dimensionName = dimensionChanger.getDimensionName(columnModelDefine)) != null) {
                Object value = dims.getValue(dimensionName);
                if (value != null && !"".equals(value)) {
                    nvwaQueryModel.getColumnFilters().put(columnModelDefine, value);
                }
                if (dwMainDimName.equals(dimensionName)) {
                    OrderByItem orderByItem = new OrderByItem(columnModelDefine, true);
                    nvwaQueryModel.getOrderByItems().add(orderByItem);
                }
            }
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
            INvwaDataSet executeQueryWithRowKey = readOnlyDataAccess.executeQueryWithRowKey(context);
            List<ColumnModelDefine> dimensionColumns = this.getDimensionValueSetColumns(table, allColumns);
            UploadRecordNew historyState = null;
            for (INvwaDataRow dataSet : executeQueryWithRowKey) {
                historyState = new UploadRecordNew();
                ArrayKey rowKey = dataSet.getRowKey();
                DimensionValueSet dimensionValueSet = dimensionChanger.getDimensionValueSet(rowKey, dimensionColumns);
                historyState.setEntities(dimensionValueSet);
                block20: for (int j = 0; j < allColumns.size(); ++j) {
                    ColumnModelDefine columnModelDefine = allColumns.get(j);
                    if (columnModelDefine == null) continue;
                    String value = "";
                    Object obj = dataSet.getValue(columnModelDefine);
                    if (obj != null) {
                        value = obj.toString();
                    }
                    switch (columnModelDefine.getCode()) {
                        case "FORMID": {
                            if (value == null) continue block20;
                            historyState.setFormKey(value.toString());
                            continue block20;
                        }
                        case "CMT": {
                            Object commet = dataSet.getValue(columnModelDefine);
                            if (commet == null) continue block20;
                            if (commet instanceof byte[]) {
                                byte[] bt = (byte[])commet;
                                String s = new String(bt);
                                historyState.setCmt(s);
                                continue block20;
                            }
                            historyState.setCmt(commet.toString());
                            continue block20;
                        }
                        case "TIME_": {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (obj == null || !(obj instanceof GregorianCalendar)) continue block20;
                            GregorianCalendar gc = (GregorianCalendar)obj;
                            Date time = gc.getTime();
                            String format = formatter.format(time);
                            historyState.setTime(format);
                            continue block20;
                        }
                        case "OPERATOR": {
                            if (StringUtils.isEmpty((String)value)) continue block20;
                            historyState.setOperator(value);
                            continue block20;
                        }
                        case "CUREVENT": {
                            historyState.setAction(value);
                            continue block20;
                        }
                        case "CURNODE": {
                            historyState.setTaskId(value);
                            continue block20;
                        }
                    }
                }
                historyStateList.add(historyState);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return historyStateList;
    }

    private TableModelDefine getTableByCode(String tableCode) {
        try {
            return this.dataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("query table %s error.", tableCode), e);
        }
    }

    private List<ColumnModelDefine> getAllFieldsInTable(String tableKey) {
        try {
            return this.dataModelService.getColumnModelDefinesByTable(tableKey);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("query fields %s error.", tableKey), e);
        }
    }

    private List<ColumnModelDefine> getDimensionValueSetColumns(TableModelDefine table, List<ColumnModelDefine> columns) {
        ArrayList<ColumnModelDefine> dimensionColumns = new ArrayList<ColumnModelDefine>();
        String bizKeys = table.getBizKeys();
        String[] bizKeyArray = bizKeys.split(";");
        for (int i = 0; i < bizKeyArray.length; ++i) {
            String bizKey = bizKeyArray[i];
            ColumnModelDefine columnModelDefine = columns.stream().filter(e -> e.getID().equals(bizKey)).findFirst().get();
            dimensionColumns.add(columnModelDefine);
        }
        return dimensionColumns;
    }

    public FormSchemeDefine getFormScheme(String taskCode, String period) {
        TaskDefine taskDefine;
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, (taskDefine = runTimeViewController.getTaskByCode(taskCode)).getKey());
        if (schemePeriodLinkDefine == null) {
            throw new RuntimeException("\u4efb\u52a1\u548c\u65f6\u671f\u4e0b\u6ca1\u6709\u62a5\u8868\u65b9\u6848");
        }
        return runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }

    public void batchInsertOrUpdateProcessData(FormSchemeDefine formScheme, List<UploadStateNew> uploadStateNewList, ProcessDataImportResult processDataImportResult, Map<String, String> sourceNodeIdToCodeMap, Map<String, String> targetNodeIdToCodeMap, boolean defaultWorkflow) {
        if (formScheme == null) {
            throw new BpmException(String.format("\u627e\u4e0d\u5230\u65b9\u6848--%s", formScheme.getKey()));
        }
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme.getFormSchemeCode());
        TableModelDefine table = this.getTableByCode(tableCode);
        if (table == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848:" + formScheme.getKey() + ";" + formScheme.getFormSchemeCode() + ";" + formScheme.getTitle() + "\u6240\u5bf9\u5e94\u7684\u72b6\u6001\u8868\u4e0d\u5b58\u5728");
        }
        List<ColumnModelDefine> allColumns = this.getAllFieldsInTable(table.getID());
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        for (ColumnModelDefine columnModelDefine : allColumns) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(nvwaQueryModel);
        try {
            INvwaUpdatableDataSet queryForUpdate = updatableDataAccess.executeQueryForUpdate(context);
            List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(table, allColumns);
            for (UploadStateNew uploadStateNew : uploadStateNewList) {
                DimensionValueSet dimensionValueSet = uploadStateNew.getEntities();
                dimensionValueSet.setValue("PROCESSKEY", (Object)this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                ArrayKey arrayKey = dimensionChanger.getArrayKey(dimensionValueSet, dimensionValueSetColumns);
                INvwaDataRow dataRow = queryForUpdate.findRow(arrayKey);
                if (dataRow == null) {
                    int j = 0;
                    dataRow = queryForUpdate.appendRow();
                    for (ColumnModelDefine fieldDefine : allColumns) {
                        Object value;
                        String dimensionName = dimensionChanger.getDimensionName(fieldDefine);
                        if (dimensionName != null && (value = dimensionValueSet.getValue(dimensionName)) != null) {
                            dataRow.setValue(j, value);
                        }
                        ++j;
                    }
                }
                int index = 0;
                for (ColumnModelDefine fieldDefine : allColumns) {
                    if (fieldDefine.getCode().equals("PREVEVENT")) {
                        dataRow.setValue(index, (Object)uploadStateNew.getPreEvent());
                    } else if (fieldDefine.getCode().equals("CURNODE")) {
                        if (defaultWorkflow) {
                            dataRow.setValue(index, (Object)uploadStateNew.getTaskId());
                        } else {
                            String targetTaskNodeID = this.getTargetTaskNodeID(uploadStateNew.getTaskId(), sourceNodeIdToCodeMap, targetNodeIdToCodeMap);
                            if (targetTaskNodeID != null) {
                                dataRow.setValue(index, (Object)targetTaskNodeID);
                            }
                        }
                    }
                    ++index;
                }
            }
            queryForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!defaultWorkflow) {
            this.updateActivitiInstance(formScheme.getKey(), uploadStateNewList, processDataImportResult, sourceNodeIdToCodeMap, targetNodeIdToCodeMap);
        }
    }

    public void batchInsertHisProcessData(FormSchemeDefine formScheme, List<UploadRecordNew> uploadRecordNewList, Map<String, String> sourceNodeIdToCodeMap, Map<String, String> targetNodeIdToCodeMap, boolean defaultWorkflow) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        if (formScheme == null) {
            return;
        }
        String tableName = TableConstant.getSysUploadRecordTableName(formScheme);
        TableModelDefine table = this.getTableByCode(tableName);
        if (table == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848:" + formScheme.getKey() + ";" + formScheme.getFormSchemeCode() + ";" + formScheme.getTitle() + "\u6240\u5bf9\u5e94\u7684\u5386\u53f2\u72b6\u6001\u8868\u4e0d\u5b58\u5728");
        }
        List<ColumnModelDefine> allColumns = this.getAllFieldsInTable(table.getID());
        this.deleteHistoryData(formScheme.getKey(), tableName, allColumns, uploadRecordNewList);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnDefine : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnDefine));
        }
        try {
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
            List<ColumnModelDefine> dimensionValueSetColumns = this.getDimensionValueSetColumns(table, allColumns);
            for (UploadRecordNew uploadRecordNew : uploadRecordNewList) {
                DimensionValueSet entities = uploadRecordNew.getEntities();
                entities.setValue("PROCESSKEY", (Object)this.nrParameterUtils.getProcessKey(formScheme.getKey()));
                ArrayKey arrayKey = dimensionChanger.getArrayKey(entities, dimensionValueSetColumns);
                INvwaDataRow dataRow = iNvwaDataRows.findRow(arrayKey);
                if (dataRow == null) {
                    int j = 0;
                    dataRow = iNvwaDataRows.appendRow();
                    for (ColumnModelDefine fieldDefine : allColumns) {
                        Object value;
                        String dimensionName = dimensionChanger.getDimensionName(fieldDefine);
                        if (dimensionName != null && (value = entities.getValue(dimensionName)) != null) {
                            dataRow.setValue(j, value);
                        }
                        ++j;
                    }
                }
                int index = 0;
                for (ColumnModelDefine column : allColumns) {
                    if (column.getCode().equals("CUREVENT")) {
                        dataRow.setValue(index, (Object)uploadRecordNew.getAction());
                    } else if (column.getCode().equals("CURNODE")) {
                        if (defaultWorkflow) {
                            dataRow.setValue(index, (Object)uploadRecordNew.getTaskId());
                        } else {
                            String targetTaskNodeID = this.getTargetTaskNodeID(uploadRecordNew.getTaskId(), sourceNodeIdToCodeMap, targetNodeIdToCodeMap);
                            if (targetTaskNodeID != null) {
                                dataRow.setValue(index, (Object)targetTaskNodeID);
                            }
                        }
                    } else if (column.getCode().equals("CMT")) {
                        dataRow.setValue(index, (Object)uploadRecordNew.getCmt());
                    } else if (column.getCode().equals("OPERATOR")) {
                        dataRow.setValue(index, (Object)uploadRecordNew.getOperator());
                    } else if (column.getCode().equals("OPERATIONID")) {
                        dataRow.setValue(index, (Object)uploadRecordNew.getOperationid());
                    } else if (column.getCode().equals("TIME_")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = uploadRecordNew.getTime();
                        if (time != null) {
                            Date date = sdf.parse(time);
                            dataRow.setValue(index, (Object)date);
                        }
                    } else if (column.getCode().equals("EXECUTE_ORDER")) {
                        dataRow.setValue(index, (Object)0);
                    } else if (column.getCode().equals("FORCE_STATE")) {
                        dataRow.setValue(index, (Object)0);
                    } else if (column.getCode().equals("BIZKEYORDER")) {
                        dataRow.setValue(index, (Object)UUID.randomUUID().toString());
                    }
                    ++index;
                }
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    public void deleteHistoryData(String formSchemeKey, String tableCode, List<ColumnModelDefine> allFieldsInTable, List<UploadRecordNew> uploadRecordNewList) {
        TableModelDefine table = this.nrParameterUtils.getTableByCode(tableCode);
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine columnModelDefine : allFieldsInTable) {
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        if (uploadRecordNewList != null && uploadRecordNewList.size() > 0) {
            for (UploadRecordNew uploadRecordNew : uploadRecordNewList) {
                DimensionValueSet entities = uploadRecordNew.getEntities();
                dims.add(entities);
            }
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(table, allFieldsInTable);
        for (ColumnModelDefine column : dimensionValueSetColumns) {
            Object value;
            String dimensionName;
            if ("PROCESSKEY".equals(column.getCode())) {
                nvwaQueryModel.getColumnFilters().put(column, this.nrParameterUtils.getProcessKey(formSchemeKey));
                continue;
            }
            if ("BIZKEYORDER".equals(column.getCode()) || (dimensionName = dimensionChanger.getDimensionName(column)) == null || null == (value = dimensionValueSet.getValue(dimensionName)) || "".equals(value)) continue;
            nvwaQueryModel.getColumnFilters().put(column, value);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(nvwaQueryModel);
        try {
            INvwaDataUpdator openForUpdate = updatableDataAccess.openForUpdate(context);
            openForUpdate.deleteAll();
            openForUpdate.commitChanges(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateActivitiInstance(String forschemeKey, List<UploadStateNew> uploadStateNewList, ProcessDataImportResult processDataImportResult, Map<String, String> sourceNodeIdToCodeMap, Map<String, String> targetNodeIdToCodeMap) {
        int errorNum = 0;
        try {
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(forschemeKey);
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(forschemeKey);
            WorkflowSettingDefine workflowSettingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(forschemeKey);
            StringBuffer sb = new StringBuffer();
            if (workflowSettingDefine != null && workflowSettingDefine.getId() != null) {
                String workflowId = workflowSettingDefine.getWorkflowId();
                for (UploadStateNew uploadStateNew : uploadStateNewList) {
                    DimensionValueSet entities = uploadStateNew.getEntities();
                    String unitKey = entities.getValue(dwMainDimName).toString();
                    String period = entities.getValue("DATATIME").toString();
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(forschemeKey, entities, uploadStateNew.getFormId(), uploadStateNew.getFormId());
                    List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
                    if (tasks == null || tasks.size() <= 0) continue;
                    Task task = tasks.get(0);
                    String targetTaskNodeID = this.getTargetTaskNodeID(uploadStateNew.getTaskId(), sourceNodeIdToCodeMap, targetNodeIdToCodeMap);
                    if (targetTaskNodeID != null) {
                        runTimeService.jumpToTargetNode(workflowId, task.getId(), targetTaskNodeID);
                        continue;
                    }
                    ++errorNum;
                    sb.append("\u65f6\u671f:" + period + "\u5355\u4f4d:" + unitKey + ",\u6e90\u670d\u52a1\u4e0a\u7684\u8282\u70b9\u4efb\u52a1ID:" + uploadStateNew.getTaskId() + ",\u5f53\u524d\u670d\u52a1\u6267\u884c\u4e2d\u7684\u8282\u70b9\u4efb\u52a1ID:" + task.getId() + ";\n");
                }
            }
            processDataImportResult.setInstanceJumpErrorInfos(sb.toString());
            processDataImportResult.setInstancJumpErrorCount(errorNum);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String getTargetTaskNodeID(String sourceTaskNodeId, Map<String, String> sourceNodeIdToCodeMap, Map<String, String> targetNodeIdToCodeMap) {
        String targetTaskNodeID = null;
        if (sourceNodeIdToCodeMap != null) {
            String code = sourceNodeIdToCodeMap.get(sourceTaskNodeId);
            if (targetNodeIdToCodeMap != null) {
                return targetNodeIdToCodeMap.get(code);
            }
        }
        return targetTaskNodeID;
    }
}

