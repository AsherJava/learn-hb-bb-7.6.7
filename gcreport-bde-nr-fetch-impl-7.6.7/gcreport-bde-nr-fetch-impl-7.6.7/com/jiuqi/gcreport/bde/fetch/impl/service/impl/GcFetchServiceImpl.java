/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTaskState
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO
 *  com.jiuqi.bde.log.fetch.dto.FetchLogDTO
 *  com.jiuqi.bde.log.fetch.service.FetchTaskLogService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.AsyncCallBackUtil
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.OutOfQueueException
 *  com.jiuqi.np.asynctask.exception.TaskExsitsException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundAsyncTaskException
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.fetch.impl.service.impl;

import com.jiuqi.bde.common.constant.FetchTaskState;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.dto.FetchLogDTO;
import com.jiuqi.bde.log.fetch.service.FetchTaskLogService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.AsyncCallBackUtil;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLog;
import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLogVO;
import com.jiuqi.gcreport.bde.fetch.impl.intf.GcFetchInfo;
import com.jiuqi.gcreport.bde.fetch.impl.intf.IBeforeFetchDataCheckerGather;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchDataExecuteService;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.OutOfQueueException;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundAsyncTaskException;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class GcFetchServiceImpl
implements GcFetchService {
    private Logger logger = LoggerFactory.getLogger(GcFetchServiceImpl.class);
    @Resource
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IBeforeFetchDataCheckerGather fetchDataCheckerGather;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private FetchTaskLogService fetchLogService;
    @Autowired
    private GcFetchDataExecuteService executeService;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public AsyncTaskInfo fetchData(@RequestBody EfdcInfo efdcInfo) {
        this.logger.debug("BDE\u53d6\u6570\u63a7\u5236\u5c42\u63a5\u6536\u5230\u7684\u8bf7\u6c42\u53c2\u6570\u3010{}\u3011", (Object)JsonUtils.writeValueAsString((Object)efdcInfo));
        this.fetchDataCheckerGather.getCheckerList().forEach(item -> item.doCheck(efdcInfo));
        String dimensionUniqueKey = FetchTaskUtil.newDimensionUniqueKey((EfdcInfo)efdcInfo);
        if (efdcInfo.getVariableMap() == null) {
            efdcInfo.setVariableMap(new HashMap());
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setUrl("/api/gcreport/v1/fetchData/query?asynTaskID=");
        try {
            boolean existExecutingTask = this.fetchLogService.existExecutingTask(dimensionUniqueKey);
            if (existExecutingTask) {
                asyncTaskInfo.setId(null);
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
                return asyncTaskInfo;
            }
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            FetchInitTaskDTO fetchInitTask = this.executeService.buildFetchInitTask(efdcInfo);
            TaskParamVO taskParam = new TaskParamVO();
            taskParam.setPreParam(JsonUtils.writeValueAsString((Object)fetchInitTask));
            taskParam.setExt_1(UUIDUtils.emptyHalfGUIDStr());
            taskParam.setExt_2(fetchInitTask.getUnitCode());
            taskParam.setExt_3(fetchInitTask.getUsername());
            taskParam.setExt_4(dimensionUniqueKey);
            taskParam.setExt_5(FetchTaskState.UNEXECUTE.getCode());
            String runnerId = (String)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.startTaskWithExtInfo(FetchTaskType.NR_FETCH.getCode(), taskParam));
            if (!StringUtils.hasText(runnerId)) {
                asyncTaskInfo.setId(null);
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setResult("\u6ca1\u6709\u627e\u5230\u9700\u8981\u53d6\u6570\u7684\u62a5\u8868\u3002");
                return asyncTaskInfo;
            }
            asyncTaskInfo.setId(runnerId);
            asyncTaskInfo.setState(TaskState.PROCESSING);
            AsyncCallBackUtil.asyncCall(() -> {
                BdeCommonUtil.initNpUser((String)fetchInitTask.getUserName());
                FetchTaskUtil.buildNrCtxByOrgType((String)fetchInitTask.getRpUnitType());
                this.fetchLogService.waitFetchTaskFinished(runnerId);
                this.fetchLogService.updateTaskFinished(runnerId);
                this.executeService.doCalculate(fetchInitTask);
                NpContextHolder.clearContext();
            });
        }
        catch (TaskExsitsException e) {
            asyncTaskInfo.setId(e.getTaskId());
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setResult("\u6267\u884c\u53d6\u6570\u4efb\u52a1\u51fa\u9519\uff0c\u8be6\u7ec6\u539f\u56e0" + e.getMessage());
        }
        catch (OutOfQueueException e) {
            asyncTaskInfo.setId(UUIDUtils.newHalfGUIDStr());
            asyncTaskInfo.setState(TaskState.OUTOFQUEUE);
            asyncTaskInfo.setResult("\u5f53\u524d\u6267\u884c{}\u4eba\u6570\u8fc7\u591a\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\uff01");
        }
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo queryFetchTask(String taskId) {
        AsyncTaskQueryInfo queryInfo = new AsyncTaskQueryInfo();
        queryInfo.setAsynTaskID(taskId);
        return this.queryFetchTaskInfo(queryInfo);
    }

    @Override
    public AsyncTaskInfo batchFetchData(EfdcInfo efdcInfo) {
        this.logger.debug("BDE\u6279\u91cf\u53d6\u6570\u63a7\u5236\u5c42\u63a5\u6536\u5230\u7684\u8bf7\u6c42\u53c2\u6570\u3010{}\u3011", (Object)JsonUtils.writeValueAsString((Object)efdcInfo));
        this.fetchDataCheckerGather.getCheckerList().forEach(item -> item.doBatchCheck(efdcInfo));
        String dimensionUniqueKey = FetchTaskUtil.newDimensionUniqueKey((EfdcInfo)efdcInfo);
        if (efdcInfo.getVariableMap() == null) {
            efdcInfo.setVariableMap(new HashMap());
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setUrl("/api/gcreport/v1/batchFetchData/query?asynTaskID=");
        try {
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            GcFetchInfo fetchInfo = new GcFetchInfo();
            fetchInfo.setEfdcInfo(efdcInfo);
            fetchInfo.setRpUnitType(this.getOrgType(efdcInfo));
            fetchInfo.setUserName(this.getUserName());
            TaskParamVO taskParam = new TaskParamVO();
            taskParam.setPreParam(JsonUtils.writeValueAsString((Object)fetchInfo));
            taskParam.setExt_1(UUIDUtils.emptyHalfGUIDStr());
            taskParam.setExt_2("#");
            taskParam.setExt_3(fetchInfo.getUserName());
            taskParam.setExt_4(dimensionUniqueKey);
            taskParam.setExt_5(FetchTaskState.UNEXECUTE.getCode());
            String runnerId = (String)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.startTaskWithExtInfo(FetchTaskType.NR_BATCH_FETCH.getCode(), taskParam));
            if (!StringUtils.hasText(runnerId)) {
                asyncTaskInfo.setId(null);
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setResult("\u6ca1\u6709\u627e\u5230\u9700\u8981\u53d6\u6570\u7684\u5355\u4f4d\u3002");
                return asyncTaskInfo;
            }
            asyncTaskInfo.setState(TaskState.PROCESSING);
            asyncTaskInfo.setId(runnerId);
        }
        catch (TaskExsitsException e) {
            asyncTaskInfo.setId(e.getTaskId());
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
        }
        catch (OutOfQueueException e) {
            asyncTaskInfo.setState(TaskState.OUTOFQUEUE);
            asyncTaskInfo.setId(UUIDUtils.newHalfGUIDStr());
            asyncTaskInfo.setResult("\u5f53\u524d\u6267\u884c{}\u4eba\u6570\u8fc7\u591a\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\uff01");
        }
        return asyncTaskInfo;
    }

    public String getUserName() {
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user == null) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u83b7\u53d6\u5230\u7528\u6237\u4fe1\u606f\uff0c\u8bf7\u91cd\u65b0\u767b\u5f55\u540e\u91cd\u8bd5");
        }
        return StringUtils.hasText(user.getName()) ? user.getName() : user.getFullname();
    }

    public String getOrgType(EfdcInfo efdcInfo) {
        return FetchTaskUtil.getOrgTypeByTaskAndCtx((String)efdcInfo.getTaskKey());
    }

    @Override
    public AsyncTaskInfo queryFetchTaskInfo(AsyncTaskQueryInfo asyncTaskQueryInfo) {
        try {
            if (!StringUtils.hasText(asyncTaskQueryInfo.getAsynTaskID())) {
                throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
            }
            String asynTaskID = asyncTaskQueryInfo.getAsynTaskID();
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(asynTaskID);
            asyncTaskInfo.setUrl("/api/gcreport/v1/fetchData/query?asynTaskID=");
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            Double handleProcess = (Double)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.getTaskProgress(asyncTaskQueryInfo.getAsynTaskID()));
            if (NumberUtils.compareDouble((double)0.0, (double)handleProcess)) {
                asyncTaskInfo.setProcess(Double.valueOf(0.1));
                asyncTaskInfo.setState(TaskState.PROCESSING);
                asyncTaskInfo.setResult("\u6b63\u5728\u6267\u884c\u53d6\u6570");
                asyncTaskInfo.setDetail((Object)"\u6b63\u5728\u6267\u884c\u53d6\u6570");
            } else if (!NumberUtils.compareDouble((double)1.0, (double)handleProcess)) {
                asyncTaskInfo.setProcess(Double.valueOf(this.calcProcess(handleProcess)));
                asyncTaskInfo.setState(TaskState.PROCESSING);
                asyncTaskInfo.setResult("\u6b63\u5728\u6267\u884c\u53d6\u6570");
                asyncTaskInfo.setDetail((Object)"\u6b63\u5728\u6267\u884c\u53d6\u6570");
            } else {
                FetchItemLogDTO fetchLog = this.fetchLogService.queryFailTask(asyncTaskQueryInfo.getAsynTaskID());
                if (fetchLog == null) {
                    asyncTaskInfo.setProcess(Double.valueOf(0.9));
                    asyncTaskInfo.setState(TaskState.PROCESSING);
                    asyncTaskInfo.setResult("\u6b63\u5728\u6267\u884c\u8fd0\u7b97");
                    return asyncTaskInfo;
                }
                if (!StringUtils.hasText(fetchLog.getLog())) {
                    asyncTaskInfo.setProcess(Double.valueOf(1.0));
                    asyncTaskInfo.setState(TaskState.FINISHED);
                    asyncTaskInfo.setResult(fetchLog.getLogDigest());
                    return asyncTaskInfo;
                }
                asyncTaskInfo.setProcess(Double.valueOf(1.0));
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setResult(StringUtils.hasText(fetchLog.getLogDigest()) ? fetchLog.getLogDigest() : "\u53d6\u6570\u5931\u8d25");
                asyncTaskInfo.setDetail((Object)fetchLog.getLog());
                return asyncTaskInfo;
            }
            return asyncTaskInfo;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u53d6\u6570\u8fdb\u5ea6\u51fa\u73b0\u9519\u8bef", (Throwable)e);
        }
    }

    @Override
    public AsyncTaskInfo queryBatchFetchTaskInfo(AsyncTaskQueryInfo asyncTaskQueryInfo) {
        try {
            if (!StringUtils.hasText(asyncTaskQueryInfo.getAsynTaskID())) {
                throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
            }
            String asynTaskID = asyncTaskQueryInfo.getAsynTaskID();
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(asynTaskID);
            asyncTaskInfo.setUrl("/api/gcreport/v1/batchFetchData/query?asynTaskID=");
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            Double handleProcess = (Double)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.getTaskProgress(asyncTaskQueryInfo.getAsynTaskID()));
            if (NumberUtils.compareDouble((double)0.0, (double)handleProcess)) {
                asyncTaskInfo.setProcess(Double.valueOf(0.05));
                asyncTaskInfo.setState(TaskState.PROCESSING);
                asyncTaskInfo.setResult("\u6b63\u5728\u6267\u884c\u53d6\u6570");
                asyncTaskInfo.setDetail((Object)"\u6b63\u5728\u6267\u884c\u53d6\u6570");
            } else if (!NumberUtils.compareDouble((double)1.0, (double)handleProcess)) {
                asyncTaskInfo.setProcess(Double.valueOf(this.calcProcess(handleProcess)));
                asyncTaskInfo.setState(TaskState.PROCESSING);
                asyncTaskInfo.setResult("\u6b63\u5728\u6267\u884c\u53d6\u6570");
                asyncTaskInfo.setDetail((Object)"\u6b63\u5728\u6267\u884c\u53d6\u6570");
            } else {
                asyncTaskInfo.setProcess(Double.valueOf(1.0));
                FetchLogDTO queryFailBatchTask = this.fetchLogService.queryFailBatchTask(asyncTaskQueryInfo.getAsynTaskID());
                int fetchSize = queryFailBatchTask.getFetchSize();
                BatchBdeFetchLogVO batchBdeFetchLogVO = new BatchBdeFetchLogVO();
                if (CollectionUtils.isEmpty((Collection)queryFailBatchTask.getFailedItemList())) {
                    batchBdeFetchLogVO.setSuccessNum(fetchSize);
                    batchBdeFetchLogVO.setFailedNum(0);
                    asyncTaskInfo.setState(TaskState.FINISHED);
                    asyncTaskInfo.setResult("\u53d6\u6570\u5b8c\u6210");
                    asyncTaskInfo.setDetail((Object)"\u53d6\u6570\u5b8c\u6210");
                    return asyncTaskInfo;
                }
                batchBdeFetchLogVO.setTaskNum(fetchSize);
                batchBdeFetchLogVO.setFailedNum(queryFailBatchTask.getFailedItemList().size());
                batchBdeFetchLogVO.setSuccessNum(fetchSize - queryFailBatchTask.getFailedItemList().size());
                HashMap<String, List<BatchBdeFetchLog>> batchBdeFetchLogsMap = new HashMap();
                ArrayList<BatchBdeFetchLog> batchBdeFetchLogs = new ArrayList<BatchBdeFetchLog>();
                BaseDataDTO baseDataDTO = new BaseDataDTO();
                baseDataDTO.setTableName("MD_CURRENCY");
                baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
                Map<String, String> currencyMap = this.baseDataClient.list(baseDataDTO).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (K1, K2) -> K1));
                BatchBdeFetchLog log = null;
                for (FetchItemLogDTO fetchLogDto : queryFailBatchTask.getFailedItemList()) {
                    String currencyName = "";
                    currencyName = !StringUtils.hasText(fetchLogDto.getCurrency()) ? fetchLogDto.getCurrency() : (StringUtils.hasText(currencyMap.get(fetchLogDto.getCurrency())) ? currencyMap.get(fetchLogDto.getCurrency()) : fetchLogDto.getCurrency());
                    log = (BatchBdeFetchLog)BeanConvertUtil.convert((Object)fetchLogDto, BatchBdeFetchLog.class, (String[])new String[0]);
                    log.setPeriod(fetchLogDto.getPeriodScheme());
                    log.setCurrency(currencyName);
                    log.setAsyncTaskId(fetchLogDto.getRunnerId());
                    batchBdeFetchLogs.add(log);
                }
                batchBdeFetchLogsMap = batchBdeFetchLogs.stream().collect(Collectors.groupingBy(BatchBdeFetchLog::getUnitName));
                batchBdeFetchLogVO.setBatchBdeFetchLogsMap(batchBdeFetchLogsMap);
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setResult("\u53d6\u6570\u5931\u8d25");
                asyncTaskInfo.setDetail((Object)JsonUtils.writeValueAsString((Object)batchBdeFetchLogVO));
            }
            return asyncTaskInfo;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u53d6\u6570\u8fdb\u5ea6\u51fa\u73b0\u9519\u8bef", (Throwable)e);
        }
    }

    private double calcProcess(Double handleProcess) {
        return NumberUtils.sum((double)0.1, (double)NumberUtils.mul((double)handleProcess, (double)0.8));
    }
}

