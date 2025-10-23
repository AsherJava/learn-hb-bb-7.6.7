/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.fielddatacrud.RegionPO
 *  com.jiuqi.nr.fielddatacrud.SaveRes
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 */
package com.jiuqi.nr.sbdata.carry.util;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.sbdata.carry.bean.DataFileCheckInfo;
import com.jiuqi.nr.sbdata.carry.bean.DataTableCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.FormCarryResult;
import com.jiuqi.nr.sbdata.carry.exception.TzCarryDownException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TzCarryUtils {
    public static IParamDataProvider getParamDataProvider(String taskKey, String formSchemeKey, String formKey) {
        HashSet<String> formKeys = new HashSet<String>();
        formKeys.add(formKey);
        return TzCarryUtils.getParamDataProvider(taskKey, formSchemeKey, formKeys);
    }

    public static IParamDataProvider getParamDataProvider(String taskKey, String formSchemeKey, Set<String> formKeys) {
        return () -> (dataTableCode, dataFieldCodes) -> {
            HashSet<RegionPO> rs = new HashSet<RegionPO>();
            for (String formKey : formKeys) {
                RegionPO regionPO = new RegionPO();
                regionPO.setTaskKey(taskKey);
                regionPO.setFormSchemeKey(formSchemeKey);
                regionPO.setFormKey(formKey);
                rs.add(regionPO);
            }
            return rs;
        };
    }

    public static IParamDataProvider getParamDataProvider(String taskKey) {
        return () -> (dataTableCode, dataFieldCodes) -> {
            HashSet<RegionPO> rs = new HashSet<RegionPO>();
            RegionPO regionPO = new RegionPO();
            regionPO.setTaskKey(taskKey);
            rs.add(regionPO);
            return rs;
        };
    }

    public static IParamDataProvider getParamDataProvider(String taskKey, String formSchemeKey) {
        return () -> (dataTableCode, dataFieldCodes) -> {
            HashSet<RegionPO> rs = new HashSet<RegionPO>();
            RegionPO regionPO = new RegionPO();
            regionPO.setTaskKey(taskKey);
            regionPO.setFormSchemeKey(formSchemeKey);
            rs.add(regionPO);
            return rs;
        };
    }

    public static Map<String, Boolean> getAsyncTaskInfo(AsyncTaskManager asyncTaskManager, Set<String> executeAsyncTasks) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        for (String asyncTaskId : executeAsyncTasks) {
            TaskState taskState = asyncTaskManager.queryTaskState(asyncTaskId);
            if (taskState == TaskState.ERROR || taskState == TaskState.FINISHED || taskState == TaskState.OVERTIME) {
                result.put(asyncTaskId, true);
                continue;
            }
            result.put(asyncTaskId, false);
        }
        return result;
    }

    public static void publishSubJob(JobContext jobContext, AbstractRealTimeJob job, int parallelNumber, Set<String> allAsyncTaskId, Set<String> executingTasks, Set<String> executedTasks, AsyncTaskManager asyncTaskManager, Object param, AsyncTaskMonitor monitor, int formSize) throws JobExecutionException {
        double splitSize = 0.8 / (double)formSize;
        while (true) {
            if (executingTasks.size() < parallelNumber) break;
            Map<String, Boolean> info = TzCarryUtils.getAsyncTaskInfo(asyncTaskManager, executingTasks);
            for (Map.Entry<String, Boolean> infoEntry : info.entrySet()) {
                if (!infoEntry.getValue().booleanValue() || executedTasks.contains(infoEntry.getKey())) continue;
                executedTasks.add(infoEntry.getKey());
                executingTasks.remove(infoEntry.getKey());
                monitor.progressAndMessage(0.1 + splitSize * (double)executedTasks.size(), null);
                jobContext.getDefaultLogger().info("======\u62a5\u8868\u6267\u884c\u8fdb\u5ea6\uff1a" + executedTasks.size() + "/" + formSize + "======");
            }
            if (executingTasks.size() != parallelNumber) continue;
            jobContext.waitForSubJob(5);
        }
        Map jobArgs = job.getParams();
        jobArgs.put("NR_ARGS", SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        job.setTitle("\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c-\u5b50\u4efb\u52a1");
        String asyncTaskID = jobContext.executeRealTimeSubJob(job);
        allAsyncTaskId.add(asyncTaskID);
        executingTasks.add(asyncTaskID);
    }

    public static void updateProgressAndLog(JobContext jobContext, AsyncTaskManager asyncTaskManager, AsyncTaskMonitor asyncTaskMonitor, Set<String> allAsyncTasks, Set<String> executedTasks, int formSize) throws JobExecutionException {
        Set<String> unfinishedTasks = allAsyncTasks.stream().filter(a -> !executedTasks.contains(a)).collect(Collectors.toSet());
        double splitSize = 0.8 / (double)formSize;
        while (true) {
            Map<String, Boolean> asyncTaskInfo = TzCarryUtils.getAsyncTaskInfo(asyncTaskManager, unfinishedTasks);
            for (Map.Entry<String, Boolean> entry : asyncTaskInfo.entrySet()) {
                if (!entry.getValue().booleanValue() || executedTasks.contains(entry.getKey())) continue;
                executedTasks.add(entry.getKey());
                asyncTaskMonitor.progressAndMessage(0.1 + splitSize * (double)executedTasks.size(), null);
                jobContext.getDefaultLogger().info("======\u62a5\u8868\u6267\u884c\u8fdb\u5ea6\uff1a" + executedTasks.size() + "/" + formSize + "======");
            }
            if (executedTasks.size() == formSize) break;
            jobContext.waitForSubJob(10);
        }
    }

    public static DataFileCheckInfo checkDataField(List<DataField> sourceFields, List<DataField> destFields) {
        DataFileCheckInfo dataFileCheckInfo = new DataFileCheckInfo();
        for (int i = 0; i < sourceFields.size(); ++i) {
            DataField sourceField = sourceFields.get(i);
            DataField destField = destFields.get(i);
            if (destField == null) {
                throw new TzCarryDownException("\u672a\u627e\u5230\u6307\u6807" + sourceField.getCode() + "\u7684\u6620\u5c04\u6307\u6807");
            }
            TzCarryUtils.compareDataFields(dataFileCheckInfo, sourceField, destField);
        }
        return dataFileCheckInfo;
    }

    public static void compareDataFields(DataFileCheckInfo dataFileCheckInfo, DataField sourceDataField, DataField destDataField) {
        if (!TzCarryUtils.compareBaseObj(sourceDataField.getDataFieldType(), destDataField.getDataFieldType())) {
            dataFileCheckInfo.getType().add(sourceDataField.getCode());
            return;
        }
        if (TzCarryUtils.compareLengthOrPrecision(sourceDataField.getPrecision(), destDataField.getPrecision())) {
            dataFileCheckInfo.getLength().add(sourceDataField.getCode());
        }
        if (TzCarryUtils.compareLengthOrPrecision(sourceDataField.getDecimal(), destDataField.getDecimal())) {
            dataFileCheckInfo.getPrecision().add(sourceDataField.getCode());
        }
        if (TzCarryUtils.compareNullAbleOrCustomCode(sourceDataField.isNullable(), destDataField.isNullable())) {
            dataFileCheckInfo.getNullAble().add(sourceDataField.getCode());
        }
        if (StringUtils.hasLength(sourceDataField.getRefDataEntityKey()) && StringUtils.hasLength(destDataField.getRefDataEntityKey()) && TzCarryUtils.compareNullAbleOrCustomCode(sourceDataField.isAllowUndefinedCode(), destDataField.isAllowUndefinedCode())) {
            dataFileCheckInfo.getCustomCode().add(sourceDataField.getCode());
        }
    }

    private static boolean compareNullAbleOrCustomCode(boolean source, boolean dest) {
        return source && !dest;
    }

    private static boolean compareLengthOrPrecision(Integer source, Integer dest) {
        if (source == null) {
            source = 0;
        }
        if (dest == null) {
            dest = 0;
        }
        return source > dest;
    }

    private static boolean compareBaseObj(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (null == obj1 || null == obj2) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static void convertResult(DataTableCarryResult result, SaveRes saveRes, List<Object> errorRowData) {
        Collection failDw;
        result.setNoAuthDw(new HashSet<String>(saveRes.getNoPermissionDw()));
        if (errorRowData != null && !errorRowData.isEmpty()) {
            result.setErrorRowData(errorRowData);
            result.setSuccess(false);
        }
        if (!CollectionUtils.isEmpty(failDw = saveRes.getFailDw())) {
            result.setSuccess(false);
            ReturnRes failMessage = saveRes.getFailMessage((String)failDw.stream().findAny().get());
            result.setErrorMessage(failMessage.getMessage());
        }
    }

    public static DataTableCarryResult mergeResult(List<DataTableCarryResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return new DataTableCarryResult();
        }
        DataTableCarryResult successResult = null;
        for (DataTableCarryResult result : results) {
            if (!result.isSuccess()) {
                return result;
            }
            if (CollectionUtils.isEmpty(result.getNoAuthDw())) continue;
            successResult = result;
        }
        if (successResult != null) {
            return successResult;
        }
        return results.get(0);
    }

    public static void waitComplete(JobContext jobContext) throws JobExecutionException {
        boolean isAllFinish = true;
        while (isAllFinish) {
            isAllFinish = jobContext.waitForSubJob(5);
        }
    }

    public static List<FormCarryResult> getAllResultAndLog(JobContext jobContext, AsyncTaskManager asyncTaskManager, Set<String> ids) {
        Map results = asyncTaskManager.queryDetailStrings(new ArrayList<String>(ids));
        Collection values = results.values();
        ArrayList<FormCarryResult> allResults = new ArrayList<FormCarryResult>();
        try {
            for (String value : values) {
                allResults.add((FormCarryResult)JsonUtil.toObject((String)value, FormCarryResult.class));
            }
        }
        catch (Exception e) {
            throw new TzCarryDownException("\u7ed3\u679c\u8f6c\u6362\u5931\u8d25\uff01");
        }
        Logger logger = jobContext.getDefaultLogger();
        for (FormCarryResult result : allResults) {
            TzCarryUtils.jobLogger(logger, result);
        }
        return allResults;
    }

    private static void jobLogger(Logger logger, FormCarryResult result) {
        logger.info("\u8868\u5355" + result.getFormCode() + "\u7ed3\u8f6c" + (result.isSuccess() ? "\u6210\u529f" : "\u5931\u8d25"));
        logger.info("======================================================");
        if (result.isSuccess()) {
            if (!result.getNoAuthDw().isEmpty()) {
                logger.info("\u65e0\u6743\u9650\u8df3\u8fc7\u5355\u4f4d\uff1a" + String.join((CharSequence)"\uff0c", result.getNoAuthDw()));
            }
        } else if (result.getDataFileCheckInfo() != null && !result.getDataFileCheckInfo().isCheckSuccess()) {
            DataFileCheckInfo dataFileCheckInfo = result.getDataFileCheckInfo();
            logger.info("\u5931\u8d25\u539f\u56e0\uff1a\u6307\u6807\u68c0\u67e5\u4e0d\u901a\u8fc7");
            if (!CollectionUtils.isEmpty(dataFileCheckInfo.getType())) {
                logger.info("\u6765\u6e90\u6307\u6807\u4e0e\u76ee\u6807\u6307\u6807\u7c7b\u578b\u4e0d\u5339\u914d");
                logger.info(String.join((CharSequence)"\uff0c", dataFileCheckInfo.getType()));
            }
            if (!CollectionUtils.isEmpty(dataFileCheckInfo.getLength())) {
                logger.info("\u6765\u6e90\u6307\u6807\u957f\u5ea6\u5927\u4e8e\u76ee\u6807\u6307\u6807\u957f\u5ea6");
                logger.info(String.join((CharSequence)"\uff0c", dataFileCheckInfo.getLength()));
            }
            if (!CollectionUtils.isEmpty(dataFileCheckInfo.getPrecision())) {
                logger.info("\u6765\u6e90\u6307\u6807\u7cbe\u5ea6\u5927\u4e8e\u76ee\u6807\u6307\u6807\u7cbe\u5ea6");
                logger.info(String.join((CharSequence)"\uff0c", dataFileCheckInfo.getPrecision()));
            }
            if (!CollectionUtils.isEmpty(dataFileCheckInfo.getNullAble())) {
                logger.info("\u6765\u6e90\u6307\u6807\u5141\u8bb8\u4e3a\u7a7a\uff0c\u76ee\u6807\u6307\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
                logger.info(String.join((CharSequence)"\uff0c", dataFileCheckInfo.getNullAble()));
            }
            if (!CollectionUtils.isEmpty(dataFileCheckInfo.getCustomCode())) {
                logger.info("\u6765\u6e90\u6307\u6807\u5141\u8bb8\u672a\u5b9a\u4e49\u7f16\u7801\uff0c\u76ee\u6807\u6307\u6807\u4e0d\u5141\u8bb8\u672a\u5b9a\u4e49\u7f16\u7801");
                logger.info(String.join((CharSequence)"\uff0c", dataFileCheckInfo.getCustomCode()));
            }
        } else if (!CollectionUtils.isEmpty(result.getErrorRowData())) {
            if (StringUtils.hasLength(result.getErrorMessage())) {
                logger.info("\u5931\u8d25\u539f\u56e0: \u6570\u636e\u9519\u8bef," + result.getErrorMessage());
            } else {
                logger.info("\u5931\u8d25\u539f\u56e0: \u6570\u636e\u9519\u8bef");
            }
            List<Object> errorRowData = result.getErrorRowData();
            ArrayList<String> error = new ArrayList<String>();
            for (Object o : errorRowData) {
                if (o == null) {
                    error.add("null");
                    continue;
                }
                error.add(o.toString());
            }
            logger.info("\u9519\u8bef\u6570\u636e\u884c\uff1a" + String.join((CharSequence)"\uff0c", error));
        } else if (StringUtils.hasLength(result.getErrorMessage())) {
            logger.info("\u5931\u8d25\u539f\u56e0: " + result.getErrorMessage());
        }
    }

    public static boolean getContainFail(List<FormCarryResult> results) {
        boolean containFail = false;
        for (FormCarryResult result : results) {
            if (result.isSuccess()) continue;
            containFail = true;
            break;
        }
        return containFail;
    }

    public static Map<String, List<DataField>> getDataTable2DataField(RuntimeViewController runtimeView, IRuntimeDataSchemeService runtimeDataSchemeService, String regionKey) {
        List allLinksInRegion = runtimeView.getAllLinksInRegion(regionKey);
        List fieldKeys = allLinksInRegion.stream().filter(a -> a.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD)).map(DataLinkDefine::getLinkExpression).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return new HashMap<String, List<DataField>>();
        }
        List dataFields = runtimeDataSchemeService.getDataFields(fieldKeys);
        return dataFields.stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
    }
}

