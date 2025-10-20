/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.result.service.FetchResultService
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq;

import com.jiuqi.bde.base.result.service.FetchResultService;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcSyncEtlFetchVO;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FetchDataFormDTO;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FixedFetchResultHandler;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FloatFetchResultHandler;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchDataExecuteService;
import com.jiuqi.gcreport.bde.fetch.impl.syncfetch.service.GcSyncEtlFetchService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialAutoFetchDataHandler
implements ITaskHandler {
    @Autowired
    private GcSyncEtlFetchService etlFetchService;
    @Autowired
    private GcFetchDataExecuteService executeService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private FetchResultService resultService;
    private Logger logger = LoggerFactory.getLogger(FinancialAutoFetchDataHandler.class);

    public String getName() {
        return RequestSourceTypeEnum.FINANCIAL_AUTO_FETCH.getCode();
    }

    public String getTitle() {
        return RequestSourceTypeEnum.FINANCIAL_AUTO_FETCH.getTitle();
    }

    public String getPreTask() {
        return null;
    }

    public Map<String, String> getHandleParams(String preParam) {
        FetchInitTaskDTO fetchInitTask = (FetchInitTaskDTO)JsonUtils.readValue((String)preParam, FetchInitTaskDTO.class);
        FetchDataFormDTO fetchDataForm = null;
        HashMap<String, String> params = new HashMap<String, String>(fetchInitTask.getFetchForms().size());
        for (FetchFormDTO fetchForm : fetchInitTask.getFetchForms()) {
            fetchDataForm = (FetchDataFormDTO)BeanConvertUtil.convert((Object)fetchInitTask, FetchDataFormDTO.class, (String[])new String[0]);
            fetchDataForm.setFormId(fetchForm.getId());
            fetchDataForm.setFormCode(fetchForm.getFormCode());
            fetchDataForm.setFormTitle(fetchForm.getFormTitle());
            fetchDataForm.setFetchRegions(fetchForm.getFetchRegions());
            fetchDataForm.setFetchFormCt(fetchInitTask.getFetchForms().size());
            params.put(JsonUtils.writeValueAsString((Object)fetchDataForm), fetchForm.getId());
        }
        return params;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        TaskHandleResult result = new TaskHandleResult();
        result.setPreParam(param);
        result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
        try {
            Object ids;
            FetchDataFormDTO fetchForm = (FetchDataFormDTO)JsonUtils.readValue((String)param, FetchDataFormDTO.class);
            BdeCommonUtil.initNpUser((String)fetchForm.getUserName());
            FetchTaskUtil.buildNrCtxByOrgType((String)fetchForm.getRpUnitType());
            TaskHandleMsg handleMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
            String runnerId = handleMsg.getRunnerId();
            String instanceId = handleMsg.getInstanceId();
            String taskItemId = handleMsg.getTaskItemId();
            GcSyncEtlFetchVO gcSyncEtlFetchVO = new GcSyncEtlFetchVO();
            gcSyncEtlFetchVO.setFetchTaskId(runnerId);
            gcSyncEtlFetchVO.setFetchForm(fetchForm);
            this.etlFetchService.execute(gcSyncEtlFetchVO);
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            TaskHandleMsg taskHandleMsg = null;
            ArrayList fetchCtxList = CollectionUtils.newArrayList();
            ArrayList instanceIds = new ArrayList();
            int routeNum = BdeCommonUtil.getRouteNum();
            for (FetchRegionDTO fetchRegion : fetchForm.getFetchRegions()) {
                FetchRequestDTO context = this.executeService.buildFetchContext(fetchForm, fetchRegion);
                context.setRouteNum(Integer.valueOf(routeNum));
                context.setRequestRunnerId(runnerId);
                context.setRequestInstcId(instanceId);
                context.setRequestTaskId(taskItemId);
                context.setRequestSourceType(RequestSourceTypeEnum.NR_FETCH.getCode());
                taskHandleMsg = new TaskHandleMsg(FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode(), fetchRegion.getId(), instanceId, taskItemId, JsonUtils.writeValueAsString((Object)context), 0, runnerId);
                ids = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.startSubTask(FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode(), taskHandleMsg));
                fetchCtxList.add(context);
                instanceIds.addAll(ids);
            }
            TaskCountQueryDTO countQueryDTO = new TaskCountQueryDTO();
            countQueryDTO.setRunnerId(runnerId);
            countQueryDTO.setInstanceIdList(instanceIds);
            countQueryDTO.setDataHandleStates((Collection)CollectionUtils.newArrayList((Object[])new DataHandleState[]{DataHandleState.EXECUTING, DataHandleState.UNEXECUTE}));
            long timeOutTime = this.getTimeOutTimeMillis();
            while (true) {
                if (timeOutTime != 0L && System.currentTimeMillis() >= timeOutTime) {
                    result.setSuccess(Boolean.valueOf(false));
                    result.appendLog("\u53d6\u6570\u4efb\u52a1\u8d85\u65f6\uff01");
                    ids = result;
                    return ids;
                }
                Integer unExecuteCount = (Integer)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.countTask(countQueryDTO));
                if (unExecuteCount == null || unExecuteCount == 0) break;
                try {
                    Thread.sleep(BdeCommonUtil.getTaskSleepTime());
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new BusinessRuntimeException("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25");
                }
            }
            countQueryDTO = new TaskCountQueryDTO();
            countQueryDTO.setRunnerId(runnerId);
            countQueryDTO.setInstanceIdList(instanceIds);
            countQueryDTO.setDataHandleStates((Collection)CollectionUtils.newArrayList((Object[])new DataHandleState[]{DataHandleState.FAILURE, DataHandleState.CANCELED}));
            Integer failedCount = (Integer)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.countTask(countQueryDTO));
            if (failedCount > 0) {
                result.setSuccess(Boolean.valueOf(false));
                result.appendLog("\u53d6\u6570\u5931\u8d25");
                TaskHandleResult taskHandleResult = result;
                return taskHandleResult;
            }
            for (FetchRequestDTO fetchRequestDTO : fetchCtxList) {
                try {
                    fetchRequestDTO.setOrgMapping(fetchForm.getOrgMapping());
                    FetchResultDTO fetchResult = this.resultService.getFetchResult(fetchRequestDTO);
                    if (fetchRequestDTO.getFloatSetting() == null && fetchResult != null && fetchResult.getFixedResults() != null) {
                        new FixedFetchResultHandler(fetchRequestDTO, fetchResult).save();
                        continue;
                    }
                    new FloatFetchResultHandler(fetchRequestDTO, fetchResult).save();
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u56de\u5199\u62a5\u8868\u6570\u636e\u51fa\u73b0\u9519\u8bef\uff1a" + e.getMessage(), (Throwable)e);
                }
            }
            result.setSuccess(Boolean.valueOf(true));
            TaskHandleResult taskHandleResult = result;
            return taskHandleResult;
        }
        catch (Exception e) {
            this.logger.error("\u62a5\u8868\u53d6\u6570\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
            result.setSuccess(Boolean.valueOf(false));
            result.appendLog(e.getMessage());
            TaskHandleResult taskHandleResult = result;
            return taskHandleResult;
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    private long getTimeOutTimeMillis() {
        Integer timeOutOptionValue = ConverterUtils.getAsInteger((Object)BdeSystemOptionTool.getOptionValue((String)"BDE_TIMEOUT_TIME"));
        if (timeOutOptionValue == null || timeOutOptionValue <= 0) {
            return 0L;
        }
        return System.currentTimeMillis() + (long)(timeOutOptionValue * 1000);
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public String getModule() {
        return "GC";
    }

    public IDimType getDimType() {
        return FetchDimType.FORM;
    }

    public boolean sendPostTaskMsgWhileHandleTask() {
        return true;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public String getSpecialQueueFlag() {
        return null;
    }
}

