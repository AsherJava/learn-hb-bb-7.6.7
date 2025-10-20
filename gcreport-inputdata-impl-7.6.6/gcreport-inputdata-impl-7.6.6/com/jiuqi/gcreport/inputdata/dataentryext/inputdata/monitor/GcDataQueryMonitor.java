/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.event.DeleteAllRowEvent
 *  com.jiuqi.np.dataengine.event.DeleteRowEvent
 *  com.jiuqi.np.dataengine.event.InsertRowEvent
 *  com.jiuqi.np.dataengine.event.UpdateRowEvent
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.update.UpdateDataRecord
 *  com.jiuqi.np.dataengine.update.UpdateDataSet
 *  com.jiuqi.np.dataengine.update.UpdateDataTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.monitor;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckService;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.query.GcDataQueryImpl;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class GcDataQueryMonitor
extends AbstractMonitor {
    private GcDataQueryImpl gcDataQuery;
    private InputDataService inputDataService;
    private InputDataLockService inputDataLockService;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private Logger logger = LoggerFactory.getLogger(GcDataQueryMonitor.class);
    private String updateLockId;
    private String deleteLockId;
    private List<String> deleteAllLockIds;
    private InputDataCheckService inputDataCheckService;
    private boolean exceptionHappenOnBefore = false;

    public GcDataQueryMonitor(GcDataQueryImpl gcDataQuery) {
        this.gcDataQuery = gcDataQuery;
        this.inputDataService = (InputDataService)SpringContextUtils.getBean(InputDataService.class);
        this.inputDataLockService = (InputDataLockService)SpringContextUtils.getBean(InputDataLockService.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        this.inputDataCheckService = (InputDataCheckService)SpringContextUtils.getBean(InputDataCheckService.class);
    }

    public void onDataChange(UpdateDataSet updateDatas) {
        super.onDataChange(updateDatas);
        if (!this.gcDataQuery.getGcContext().isGcQuery()) {
            return;
        }
        try {
            this.afterInputDataChange(updateDatas);
        }
        catch (Exception e) {
            this.logger.error("\u5185\u90e8\u8868onDataChange\u4fdd\u5b58\u540e\u5904\u7406\u5f02\u5e38\u3002", e);
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString()), (String)("\u5185\u90e8\u8868\u6570\u636e\u4fdd\u5b58\u540e\u5904\u7406\u5f02\u5e38\uff1a" + e.getMessage()));
        }
    }

    private InputDataChangeMonitorEnvVo getInputDataChangeMonitorEnvVo() {
        Variable variable;
        ExecutorContext executorContext = this.gcDataQuery.getExecutorContext();
        if (executorContext != null && executorContext.getEnv() != null && executorContext.getEnv().getVariableManager() != null && (variable = executorContext.getEnv().getVariableManager().find("INPUTDATA_CHANGEMONITOR_ENV_VO")) != null) {
            try {
                Object monitorEnvVo = variable.getVarValue((IContext)executorContext);
                if (monitorEnvVo != null && monitorEnvVo instanceof InputDataChangeMonitorEnvVo) {
                    return (InputDataChangeMonitorEnvVo)monitorEnvVo;
                }
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.isinputdatachangemonitor"), (Throwable)e);
            }
        }
        return InputDataChangeMonitorEnvVo.defaultEnv();
    }

    private void afterInputDataChange(UpdateDataSet updateDatas) {
        this.logger.info("\u6570\u636e\u5f55\u5165 afterInputDataChange :{}", (Object)updateDatas.toString());
        GcDataEntryContext dataEntryContext = this.gcDataQuery.getGcContext();
        String tableName = ((InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class)).getTableNameByTaskId(dataEntryContext.getTaskKey());
        UpdateDataTable table = updateDatas.getTable(tableName);
        InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo = this.getInputDataChangeMonitorEnvVo();
        if (!CollectionUtils.isEmpty(this.gcDataQuery.getGcContext().getInitOrgList())) {
            this.afterInputDataChangeGroupByOrgId(table, dataEntryContext, inputDataChangeMonitorEnvVo);
        } else {
            List<InputDataEO> insertRecords = InputDataConver.converToInputItem(table.getInsertRecords(), dataEntryContext, inputDataChangeMonitorEnvVo);
            Map updateRecordsMap = table.getUpdateRecords();
            List updateRowIds = null;
            if (updateRecordsMap != null && updateRecordsMap.size() > 0) {
                updateRowIds = updateRecordsMap.values().stream().map(updateRecord -> {
                    String id = null;
                    Object value = updateRecord.getRowkeys().getValue("RECORDKEY");
                    if (value != null) {
                        id = value.toString();
                    }
                    return id;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            }
            this.inputDataService.afterSave(dataEntryContext, insertRecords, updateRowIds, this.updateLockId, inputDataChangeMonitorEnvVo);
        }
    }

    private void afterInputDataChangeGroupByOrgId(UpdateDataTable table, GcDataEntryContext dataEntryContext, InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo) {
        HashMap<String, Map<String, DimensionValue>> dimFieldValueGroupByOrgId = new HashMap<String, Map<String, DimensionValue>>();
        Map<String, List<InputDataEO>> insertRecordGroupByOrgId = InputDataConver.converToInputItemGroupByOrgIds(table.getInsertRecords(), dataEntryContext, inputDataChangeMonitorEnvVo, dimFieldValueGroupByOrgId);
        Map updateRecordsMap = table.getUpdateRecords();
        HashMap updateRowIdGroupByOrgId = new HashMap();
        if (updateRecordsMap != null && updateRecordsMap.size() > 0) {
            for (UpdateDataRecord updateDataRecord : updateRecordsMap.values()) {
                Object value = updateDataRecord.getRowkeys().getValue("RECORDKEY");
                if (value == null) continue;
                String unitCode = (String)updateDataRecord.getRowkeys().getValue("MD_ORG");
                String id = value.toString();
                if (updateRowIdGroupByOrgId.keySet().contains(unitCode)) {
                    ((List)updateRowIdGroupByOrgId.get(unitCode)).add(id);
                } else {
                    ArrayList<String> updateRowIds = new ArrayList<String>();
                    updateRowIds.add(id);
                    updateRowIdGroupByOrgId.put(unitCode, updateRowIds);
                }
                if (dimFieldValueGroupByOrgId.keySet().contains(unitCode)) continue;
                dimFieldValueGroupByOrgId.put(unitCode, DimensionValueSetUtil.getDimensionSet((DimensionValueSet)updateDataRecord.getRowkeys()));
            }
        }
        DataEntryContext context = new DataEntryContext();
        BeanUtils.copyProperties((Object)dataEntryContext, context);
        context.setDimensionStates(dataEntryContext.getDimensionStates());
        for (String unitCode : dimFieldValueGroupByOrgId.keySet()) {
            List<InputDataEO> insertRecords = insertRecordGroupByOrgId.get(unitCode);
            List updateRowIds = (List)updateRowIdGroupByOrgId.get(unitCode);
            Map dimensionValue = (Map)dimFieldValueGroupByOrgId.get(unitCode);
            context.setDimensionSet(dimensionValue);
            try {
                this.inputDataService.afterSave(context, insertRecords, updateRowIds, this.updateLockId, inputDataChangeMonitorEnvVo);
            }
            catch (Exception e) {
                this.logger.error(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.inputdataaftersaveexceptionmsg"), e);
            }
        }
    }

    public void beforeUpdate(List<IDataRow> updateRows) {
        super.beforeUpdate(updateRows);
        if (!this.gcDataQuery.getGcContext().isGcQuery() || CollectionUtils.isEmpty(updateRows)) {
            return;
        }
        try {
            FieldDefine offStateField;
            String taskId = this.gcDataQuery.getGcContext().getTask().getKey();
            String inputDataTableKey = null;
            try {
                inputDataTableKey = ((InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class)).getDataTableKeyByTaskId(taskId);
            }
            catch (Exception e) {
                this.logger.error("\u83b7\u53d6\u5185\u90e8\u8868\u5f55\u5165\u8868\u8868\u5b9a\u4e49\u5931\u8d25\u3002", e);
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notinputdatatableexceptionmsg"));
            }
            FieldDefine oppOrgFieldDefine = null;
            try {
                oppOrgFieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("OPPUNITID", inputDataTableKey);
            }
            catch (Exception e) {
                this.logger.error("\u83b7\u53d6\u5185\u90e8\u8868\u5f55\u5165\u8868{}\u5b57\u6bb5\u5b9a\u4e49\u5931\u8d25\u3002", (Object)"OPPUNITID", (Object)e);
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notinputdatafieldsexceptionmsg"));
            }
            ArrayList<InputDataEO> inputItems = new ArrayList<InputDataEO>();
            for (IDataRow dataRow : updateRows) {
                InputDataEO inputItem = new InputDataEO();
                inputItem.setId(dataRow.getRowKeys().getValue("RECORDKEY").toString());
                try {
                    inputItem.setMdOrg(dataRow.getRowKeys().getValue("MD_ORG").toString());
                }
                catch (DataTypeException e) {
                    this.logger.error("\u5185\u90e8\u8868\u4fee\u6539\u524d\uff0c\u83b7\u53d6{}\u5b57\u6bb5\u503c\u5931\u8d25\u3002", (Object)"MD_ORG", (Object)e);
                    throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.updaterowmdorgexceptionmsg"));
                }
                try {
                    inputItem.setOppUnitId(dataRow.getAsString(oppOrgFieldDefine));
                }
                catch (DataTypeException e) {
                    this.logger.error("\u5185\u90e8\u8868\u4fee\u6539\u524d\uff0c\u83b7\u53d6{}\u5b57\u6bb5\u503c\u5931\u8d25\u3002", (Object)"OPPUNITID", (Object)e);
                    throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.updaterowoppunitexceptionmsg"));
                }
                inputItems.add(inputItem);
            }
            this.logger.info("\u6570\u636e\u5f55\u5165\u4fee\u6539\u524dbeforeUpdate taskId:" + taskId + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId).toString().replace("=", ":") + " inputItemIds:" + ((Object)inputItems).toString());
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString()), (String)("\u5185\u90e8\u8868\u6570\u636e\u4fee\u6539\u524d\u64cd\u4f5c\uff1a taskId:" + taskId + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId).toString().replace("=", ":") + " inputItemIds:" + inputItems.get(0)));
            this.updateLockId = this.inputDataService.beforeUpdate(inputItems, taskId, InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId));
            try {
                offStateField = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("OFFSETSTATE", inputDataTableKey);
            }
            catch (Exception e) {
                this.logger.error("\u5185\u90e8\u8868\u4fee\u6539\u524d\uff0c\u83b7\u53d6{OFFSETSTATE}\u5b57\u6bb5\u503c\u5931\u8d25\u3002", e);
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.updaterowoffsetstateexceptionmsg"));
            }
            for (IDataRow dataRow : updateRows) {
                dataRow.setValue(offStateField, (Object)ReportOffsetStateEnum.NOTOFFSET.getValue());
            }
        }
        catch (Exception e) {
            this.exceptionHappenOnBefore = true;
            throw new RuntimeException(e);
        }
    }

    public void beforeDelete(List<DimensionValueSet> delRowKeys) {
        super.beforeDelete(delRowKeys);
        if (!this.gcDataQuery.getGcContext().isGcQuery() || CollectionUtils.isEmpty(delRowKeys)) {
            return;
        }
        try {
            this.checkDeleteMaster(this.gcDataQuery.getMasterKeys(), this.gcDataQuery.getGcContext().getTaskKey());
            this.beforeInputDataDelete(delRowKeys);
        }
        catch (Exception e) {
            this.exceptionHappenOnBefore = true;
            throw new RuntimeException(e);
        }
    }

    public boolean beforeDeleteAll(DimensionValueSet deleteKeys) {
        super.beforeDeleteAll(deleteKeys);
        if (!this.gcDataQuery.getGcContext().isGcQuery()) {
            return true;
        }
        this.deleteAllLockIds = new ArrayList<String>();
        try {
            IDataTable dataTable;
            FieldDefine timestampFieldDefine;
            String inputDataTableKey;
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.setUseDnaSql(false);
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(this.gcDataQuery.getGcContext().getFormScheme().getKey());
            queryEnvironment.setRegionKey(this.gcDataQuery.getGcContext().getRegionDefine().getKey());
            try {
                inputDataTableKey = ((InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class)).getDataTableKeyByTaskId(this.gcDataQuery.getGcContext().getTaskKey());
            }
            catch (Exception e) {
                Object[] args = new Object[]{this.gcDataQuery.getGcContext().getMainTable()};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.nottabledefinemsg", (Object[])args), (Throwable)e);
            }
            try {
                timestampFieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("RECORDTIMESTAMP", inputDataTableKey);
            }
            catch (Exception e) {
                Object[] args = new Object[]{this.gcDataQuery.getGcContext().getMainTable()};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.notfielddefinemsg", (Object[])args), (Throwable)e);
            }
            if (!CollectionUtils.isEmpty(this.gcDataQuery.getGcContext().getInitOrgList())) {
                return this.beforeDeleteAllGroupByOrgId(deleteKeys, dataAccessProvider, timestampFieldDefine, context, queryEnvironment);
            }
            IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.addColumn(timestampFieldDefine);
            dataQuery.setRowFilter(this.gcDataQuery.getRowFilter());
            dataQuery.setMasterKeys(this.gcDataQuery.getMasterKeys());
            this.checkDeleteCondition((GcDataQueryImpl)dataQuery);
            try {
                dataTable = dataQuery.executeQuery(context);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.queryinputdataexceptionmsg"), (Throwable)e);
            }
            int count = dataTable.getCount();
            if (count == 0) {
                return false;
            }
            ArrayList<String> deleteItemIds = new ArrayList<String>();
            for (int i = 0; i < count; ++i) {
                IDataRow dataRow = dataTable.getItem(i);
                Object idStr = dataRow.getRowKeys().getValue("RECORDKEY");
                deleteItemIds.add(idStr.toString());
            }
            if (CollectionUtils.isEmpty(deleteItemIds)) {
                return false;
            }
            String taskId = this.gcDataQuery.getGcContext().getTask().getKey();
            String deleteAllLockId = this.inputDataService.beforeDelete(deleteItemIds, taskId, InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId), "\u5220\u9664\u5168\u90e8\u524d");
            this.deleteAllLockIds.add(deleteAllLockId);
            this.logger.info("\u5408\u5e76-\u6570\u636e\u5f55\u5165  \u6574\u8868\u6e05\u9664  taskId:" + taskId + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId).toString().replace("=", ":") + " inputItemIds:" + (String)deleteItemIds.get(0));
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString()), (String)("\u5185\u90e8\u8868\u6570\u636e\u5220\u9664\u5168\u90e8\u524d\u64cd\u4f5c: taskId:" + taskId + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId).toString().replace("=", ":") + " inputItemIds:" + (String)deleteItemIds.get(0)));
            deleteKeys.setValue("RECORDKEY", deleteItemIds);
            return true;
        }
        catch (Exception e) {
            this.exceptionHappenOnBefore = true;
            this.logger.error("\u5408\u5e76-\u6570\u636e\u5f55\u5165  \u6574\u8868\u6e05\u9664 \u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString(), e);
            this.afterDeleteAll(this.deleteAllLockIds);
            return false;
        }
    }

    private boolean beforeDeleteAllGroupByOrgId(DimensionValueSet deleteKeys, IDataAccessProvider dataAccessProvider, FieldDefine timestampFieldDefine, ExecutorContext context, QueryEnvironment queryEnvironment) {
        IDataTable dataTable;
        HashMap deleteItemIdGroupByMdOrg = new HashMap();
        HashMap<String, DimensionValueSet> dimensionValueSetGroupByMdOrg = new HashMap<String, DimensionValueSet>();
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.addColumn(timestampFieldDefine);
        dataQuery.setRowFilter(this.gcDataQuery.getRowFilter());
        dataQuery.setMasterKeys(this.gcDataQuery.getMasterKeys());
        this.checkDeleteAllConditions((GcDataQueryImpl)dataQuery);
        try {
            dataTable = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.queryinputdataexceptionmsg"), (Throwable)e);
        }
        int count = dataTable.getCount();
        if (count == 0) {
            return false;
        }
        for (int i = 0; i < count; ++i) {
            IDataRow dataRow = dataTable.getItem(i);
            Object idStr = dataRow.getRowKeys().getValue("RECORDKEY");
            String unitCode = (String)dataRow.getRowKeys().getValue("MD_ORG");
            if (deleteItemIdGroupByMdOrg.keySet().contains(unitCode)) {
                ((List)deleteItemIdGroupByMdOrg.get(unitCode)).add(idStr.toString());
                continue;
            }
            ArrayList<String> deleteItemIds = new ArrayList<String>();
            deleteItemIds.add(idStr.toString());
            deleteItemIdGroupByMdOrg.put(unitCode, deleteItemIds);
            dimensionValueSetGroupByMdOrg.put(unitCode, dataRow.getRowKeys());
        }
        if (deleteItemIdGroupByMdOrg.isEmpty()) {
            return false;
        }
        ArrayList deleteItemIds = new ArrayList();
        String taskId = this.gcDataQuery.getGcContext().getTask().getKey();
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(taskId);
        for (String orgCode : deleteItemIdGroupByMdOrg.keySet()) {
            List itemIds = (List)deleteItemIdGroupByMdOrg.get(orgCode);
            try {
                String deleteAllLockId = this.inputDataService.beforeDelete(itemIds, taskId, InputDataConver.getDimFieldValueMapByRowKeys((DimensionValueSet)dimensionValueSetGroupByMdOrg.get(orgCode), tableName), "\u5220\u9664\u5168\u90e8\u524d\u591a\u7ef4\u503c");
                this.deleteAllLockIds.add(deleteAllLockId);
                deleteItemIds.addAll(itemIds);
            }
            catch (Exception e) {
                this.logger.error("\u5220\u9664\u5168\u90e8\u524d\u591a\u7ef4\u503c\u5931\u8d25\u3002", e);
            }
            this.logger.info("\u6570\u636e\u5f55\u5165\u5220\u9664\u6570\u636e\u4e3b\u4f53\u7ef4\u5ea6\u548c\u6761\u6570beforeDeleteAll  taskId:" + taskId + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMapByRowKeys((DimensionValueSet)dimensionValueSetGroupByMdOrg.get(orgCode), tableName).toString().replace("=", ":") + " inputItemIds:" + (String)itemIds.get(0));
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString()), (String)("\u5185\u90e8\u8868\u6570\u636e\u591a\u7ef4\u503c\u5220\u9664\u5168\u90e8\u524d\u64cd\u4f5c: taskId:" + taskId + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMapByRowKeys((DimensionValueSet)dimensionValueSetGroupByMdOrg.get(orgCode), tableName).toString().replace("=", ":") + " inputItemIds:" + (String)itemIds.get(0)));
        }
        deleteKeys.setValue("RECORDKEY", deleteItemIds);
        return true;
    }

    private void checkDeleteCondition(GcDataQueryImpl dataQuery) {
        this.checkDeleteMaster(dataQuery.getMasterKeys(), dataQuery.getGcContext().getTaskKey());
        if (dataQuery.getGcContext().getForm() == null || StringUtils.isEmpty(dataQuery.getGcContext().getForm().getKey()) || StringUtils.isEmpty(dataQuery.getGcContext().getFormKey())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notformkeymsg"));
        }
    }

    private void checkDeleteAllConditions(GcDataQueryImpl dataQuery) {
        if (dataQuery.getGcContext().getForm() == null || StringUtils.isEmpty(dataQuery.getGcContext().getForm().getKey()) || StringUtils.isEmpty(dataQuery.getGcContext().getFormKey())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notformkeymsg"));
        }
        for (GcOrgCacheVO orgCache : this.gcDataQuery.getGcContext().getInitOrgList()) {
            if (orgCache.getOrgKind() == GcOrgKindEnum.UNIONORG) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notunionorgchangedatamsg"));
            }
            if (orgCache.getOrgKind() != GcOrgKindEnum.DIFFERENCE) continue;
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notdifferencechangedatamsg"));
        }
    }

    private void checkDeleteMaster(DimensionValueSet masterKeys, String taskKey) {
        if (StringUtils.isEmpty(masterKeys.getValue("DATATIME"))) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.datatimenotemptymsg"));
        }
        if (StringUtils.isEmpty(masterKeys.getValue("MD_ORG"))) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.dwnotemptymsg"));
        }
        this.checkDeleteOrg(masterKeys, taskKey);
    }

    private void checkDeleteOrg(DimensionValueSet masterKeys, String taskKey) {
        String orgId = String.valueOf(masterKeys.getValue("MD_ORG"));
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskKey);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)this.gcDataQuery.getGcContext().getYearPeriod());
        GcOrgCacheVO org = tool.getOrgByCode(orgId);
        if (org.getOrgKind() == GcOrgKindEnum.UNIONORG) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notunionorgchangedatamsg"));
        }
        if (org.getOrgKind() == GcOrgKindEnum.DIFFERENCE) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.monitor.notdifferencechangedatamsg"));
        }
    }

    private void beforeInputDataDelete(List<DimensionValueSet> delRowKeys) {
        if (delRowKeys == null || delRowKeys.size() == 0) {
            return;
        }
        List<String> deleteRowIds = delRowKeys.stream().map(delRowKey -> {
            String id = null;
            Object value = delRowKey.getValue("RECORDKEY");
            if (value != null) {
                id = value.toString();
            }
            return id;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        String taskId = this.gcDataQuery.getGcContext().getTask().getKey();
        this.logger.info("\u6570\u636e\u5f55\u5165\u5220\u9664\u524d\u6570\u636e\u4e3b\u4f53\u7ef4\u5ea6beforeDelete: taskId:" + taskId + " \u5220\u9664\u6761\u6570:" + deleteRowIds.size() + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId).toString() + " inputItemIds:" + (String)deleteRowIds.get(0));
        LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString()), (String)("\u5185\u90e8\u8868\u6570\u636e\u6570\u636e\u5220\u9664\u524d\u64cd\u4f5c\uff1ataskId:" + taskId + " \u5220\u9664\u6761\u6570:" + deleteRowIds.size() + " \u4e3b\u4f53\u7ef4\u5ea6\u7684\u5b57\u6bb5\u6807\u8bc6\u548c\u5b57\u6bb5\u503c:" + InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId).toString().replace("=", ":") + " inputItemIds:" + (String)deleteRowIds.get(0)));
        this.deleteLockId = this.inputDataService.beforeDelete(deleteRowIds, taskId, InputDataConver.getDimFieldValueMap(this.gcDataQuery.getGcContext().getDimensionSet(), taskId), "\u5220\u9664\u524d");
    }

    public void onCommitException(Exception ex, List<InsertRowEvent> insertRowEvents, List<UpdateRowEvent> updateRowEvents, List<DeleteRowEvent> deleteRowEvents, List<DeleteAllRowEvent> deleteAllRowEvents) {
        if (!this.gcDataQuery.getGcContext().isGcQuery()) {
            return;
        }
        if (!this.exceptionHappenOnBefore) {
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u4efb\u52a1\uff1a" + this.gcDataQuery.getGcContext().getTask().getTitle() + "\uff0c\u65f6\u671f\uff1a" + this.gcDataQuery.getGcContext().getYearPeriod().toString()), (String)("\u5f15\u64ce\u843d\u5e93\u5f02\u5e38\uff1a" + ex.getMessage()));
            this.logger.error("\u6570\u636e\u5f55\u5165\u5f15\u64ce\u843d\u5e93\u5f02\u5e38", ex);
        }
        this.afterDelete(this.deleteLockId);
        this.afterUpdate(this.updateLockId);
        this.afterDeleteAll(this.deleteAllLockIds);
    }

    public void afterDelete(List<DimensionValueSet> delRowKeys) {
        super.afterDelete(delRowKeys);
        if (!this.gcDataQuery.getGcContext().isGcQuery()) {
            return;
        }
        try {
            this.afterDelete(this.deleteLockId);
        }
        catch (Exception e) {
            this.logger.error("\u5185\u90e8\u8868afterDelete\u540e\u5904\u7406\u5f02\u5e38\u3002", e);
        }
    }

    public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
        super.afterDeleteAll(masterKeys, deleteKeys);
        if (!this.gcDataQuery.getGcContext().isGcQuery()) {
            return;
        }
        try {
            this.afterDeleteAll(this.deleteAllLockIds);
        }
        catch (Exception e) {
            this.logger.error("\u5185\u90e8\u8868afterDeleteAll\u540e\u5904\u7406\u5f02\u5e38\u3002", e);
        }
    }

    private void afterDelete(String deleteLockId) {
        if (StringUtils.isEmpty(deleteLockId)) {
            return;
        }
        this.inputDataLockService.unlock(deleteLockId);
    }

    private void afterUpdate(String updateLockId) {
        if (ObjectUtils.isEmpty(updateLockId)) {
            return;
        }
        this.inputDataLockService.unlock(updateLockId);
    }

    private void afterDeleteAll(List<String> deleteAllLockIds) {
        if (CollectionUtils.isEmpty(deleteAllLockIds)) {
            return;
        }
        for (String deleteAllLockId : deleteAllLockIds) {
            try {
                this.inputDataLockService.unlock(deleteAllLockId);
            }
            catch (Exception e) {
                this.logger.error("\u5185\u90e8\u8868afterDeleteAll\u540e\u5904\u7406\u5f02\u5e38\u3002", e);
            }
        }
    }
}

