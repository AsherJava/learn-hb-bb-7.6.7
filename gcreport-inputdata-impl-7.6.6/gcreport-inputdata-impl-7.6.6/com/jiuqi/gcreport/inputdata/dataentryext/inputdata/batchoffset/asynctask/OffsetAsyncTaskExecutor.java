/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.offset.OffsetParam
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.batchoffset.asynctask;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataAdvanceService;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.AutoOffsetHelper;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.offset.OffsetParam;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OffsetAsyncTaskExecutor
implements NpAsyncTaskExecutor {
    private InputDataService inputDataService;
    private InputDataAdvanceService inputDataAdvanceService;
    private ConsolidatedTaskCacheService consolidatedTaskCacheService;
    private IRunTimeViewController iRunTimeViewController;

    public OffsetAsyncTaskExecutor(@Lazy InputDataService inputDataService, @Lazy InputDataAdvanceService inputDataAdvanceService, @Lazy ConsolidatedTaskCacheService consolidatedTaskCacheService, @Lazy IRunTimeViewController iRunTimeViewController) {
        this.inputDataService = inputDataService;
        this.inputDataAdvanceService = inputDataAdvanceService;
        this.consolidatedTaskCacheService = consolidatedTaskCacheService;
        this.iRunTimeViewController = iRunTimeViewController;
    }

    public void execute(Object args, AsyncTaskMonitor monitor) {
        if (!(args instanceof OffsetParam)) {
            return;
        }
        try {
            monitor.progressAndMessage(0.1, GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.startprogressmsg"));
            OffsetParam offsetParam = (OffsetParam)args;
            String taskId = offsetParam.getDataEntryContext().getTaskKey();
            Map<String, String> dimensions = InputDataConver.getDimFieldValueMap(offsetParam.getDataEntryContext().getDimensionSet(), taskId);
            String systemId = this.consolidatedTaskCacheService.getSystemIdByTaskId(taskId, dimensions.get("DATATIME"));
            if (StringUtils.isEmpty((String)systemId)) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
            }
            List<InputDataEO> inputItems = this.listInputDataItem(offsetParam, dimensions, ReportOffsetStateEnum.NOTOFFSET);
            AutoOffsetHelper autoOffsetHelper = new AutoOffsetHelper();
            monitor.progressAndMessage(0.2, GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.mappingruleprogressmsg"));
            autoOffsetHelper.mappingRuleByInputDataItems(inputItems);
            monitor.progressAndMessage(0.2, GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.canceloffsetprogressmsg"));
            List<InputDataEO> inputItemOffsetItems = this.listInputDataItem(offsetParam, dimensions, ReportOffsetStateEnum.OFFSET);
            autoOffsetHelper.cancelInputOffset(inputItemOffsetItems);
            monitor.progressAndMessage(0.4, GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.offsetprogressmsg"));
            List<InputDataEO> inputItemNoOffsetItems = this.listInputDataItem(offsetParam, dimensions, ReportOffsetStateEnum.NOTOFFSET);
            Map<String, Set<String>> offsetedOrgAndItemIdMapping = this.inputDataAdvanceService.autoBatchOffset(inputItemNoOffsetItems, null);
            Set<String> offsetItems = offsetedOrgAndItemIdMapping.get(dimensions.get("MDCODE"));
            int offsetNum = offsetItems == null ? 0 : offsetItems.size();
            Object[] messageArgs = new Object[]{offsetNum};
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
            String message = String.format("\u4efb\u52a1\uff1a%1s\uff1b\u65f6\u671f\uff1a%2s\uff1b\u672c\u65b9\u5355\u4f4d:%3s\uff1b", taskDefine.getTitle(), dimensions.get("DATATIME"), dimensions.get("MDCODE"));
            String title = String.format("\u81ea\u52a8\u62b5\u9500--\u4efb\u52a1%1s-\u65f6\u671f%2s-\u5355\u4f4d%3s", taskDefine.getTitle(), dimensions.get("DATATIME"), dimensions.get("MDCODE"));
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)title, (String)message);
            monitor.finish("", (Object)GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.finishprogressmsg", (Object[])messageArgs));
        }
        catch (Exception e) {
            monitor.error(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.exceptionmsg"), (Throwable)e);
            e.printStackTrace();
        }
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.OFFSET.getName();
    }

    private List<InputDataEO> listInputDataItem(OffsetParam offsetParam, Map<String, String> dimensions, ReportOffsetStateEnum offsetStateEnum) {
        List<Object> inputItems = new ArrayList();
        String taskId = offsetParam.getDataEntryContext().getTaskKey();
        if (offsetParam.getAllFormOffset()) {
            inputItems = this.inputDataService.queryByTaskAndDimensions(taskId, dimensions);
        } else {
            int maxBatchFormNum = 20;
            inputItems = offsetParam.getFormIds().size() > 20 ? this.inputDataService.queryByTaskAndDimensions(taskId, dimensions) : new OffsetHelper(offsetParam.getDataEntryContext()).getAllInputItems(offsetParam.getFormIds(), offsetStateEnum, taskId);
        }
        inputItems = inputItems.stream().filter(inputData -> offsetStateEnum.getValue().equals(inputData.getOffsetState())).collect(Collectors.toList());
        return inputItems;
    }

    static class OffsetHelper {
        private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
        private DataEntryContext dataEntryContext;
        private IDataAccessProvider dataAccessProvider;
        private IRunTimeViewController runTimeViewController;
        private List<FieldDefine> inputDataColumnFieldDefines;
        private Set<String> inputDataFieldIds;
        private InputDataNameProvider inputDataNameProvider;

        OffsetHelper(DataEntryContext dataEntryContext) {
            String inputDataTableKey;
            this.dataEntryContext = dataEntryContext;
            this.dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
            this.runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
            this.inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
            this.inputDataColumnFieldDefines = new ArrayList<FieldDefine>();
            try {
                inputDataTableKey = this.inputDataNameProvider.getDataTableKeyByTaskId(dataEntryContext.getTaskKey());
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notinputdatatablemsg"), (Throwable)e);
            }
            try {
                this.inputDataFieldIds = this.dataDefinitionRuntimeController.getAllFieldsInTable(inputDataTableKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notinputdatafieldsmsg"), (Throwable)e);
            }
            try {
                this.inputDataColumnFieldDefines.add(this.dataDefinitionRuntimeController.queryFieldByCodeInTable("BIZKEYORDER", inputDataTableKey));
                this.inputDataColumnFieldDefines.add(this.dataDefinitionRuntimeController.queryFieldByCodeInTable("OFFSETSTATE", inputDataTableKey));
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notbizkeyorderoroffsetstatefieldmsg"), (Throwable)e);
            }
        }

        private List<InputDataEO> getAllInputItems(Collection<String> formIds, ReportOffsetStateEnum offsetStateEnum, String taskId) {
            List<String> allInputItemIds = formIds.stream().map(formId -> this.getInputItemsByForm((String)formId, offsetStateEnum)).flatMap(Collection::stream).collect(Collectors.toList());
            return ((InputDataService)SpringContextUtils.getBean(InputDataService.class)).queryByIds(allInputItemIds, taskId);
        }

        private List<String> getInputItemsByForm(String formId, ReportOffsetStateEnum offsetStateEnum) {
            List<DataRegionDefine> dataRegionDefines = this.getInputDataDataRegion(formId);
            if (CollectionUtils.isEmpty(dataRegionDefines)) {
                return Collections.emptyList();
            }
            ArrayList<String> inputDataItemIds = new ArrayList<String>();
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                List<String> inputItemIdByRegionDefine = this.getInputDataItemByRegionDefine(dataRegionDefine, offsetStateEnum);
                if (CollectionUtils.isEmpty(inputItemIdByRegionDefine)) continue;
                inputDataItemIds.addAll(inputItemIdByRegionDefine);
            }
            return inputDataItemIds;
        }

        private List<String> getInputDataItemByRegionDefine(DataRegionDefine dataRegionDefine, ReportOffsetStateEnum offsetStateEnum) {
            IDataTable dataTable;
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.setUseDnaSql(false);
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(this.dataEntryContext.getFormSchemeKey());
            queryEnvironment.setRegionKey(dataRegionDefine.getKey());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.setRowFilter(dataRegionDefine.getFilterCondition());
            dataQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet((Map)this.dataEntryContext.getDimensionSet()));
            for (FieldDefine fieldDefine : this.inputDataColumnFieldDefines) {
                dataQuery.addColumn(fieldDefine);
            }
            try {
                dataTable = dataQuery.executeQuery(context);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.queryinputdatas.exceptionmsg"), (Throwable)e);
            }
            int count = dataTable.getCount();
            if (count == 0) {
                return Collections.emptyList();
            }
            ArrayList<String> inputItemIds = new ArrayList<String>();
            for (int i = 0; i < count; ++i) {
                IDataRow dataRow = dataTable.getItem(i);
                AbstractData offsetState = dataRow.getValue(this.inputDataColumnFieldDefines.get(1));
                if (!offsetStateEnum.getValue().equals(offsetState.getAsString())) continue;
                inputItemIds.add(String.valueOf(dataRow.getRowKeys().getValue("RECORDKEY")));
            }
            return inputItemIds;
        }

        private List<DataRegionDefine> getInputDataDataRegion(String formId) {
            List allRegionsInForms = this.runTimeViewController.getAllRegionsInForm(formId);
            List<DataRegionDefine> currDataRegionDefines = allRegionsInForms.stream().filter(dataRegionDefine -> dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && this.inputDataFieldIds.containsAll(this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()))).collect(Collectors.toList());
            return currDataRegionDefines;
        }
    }
}

