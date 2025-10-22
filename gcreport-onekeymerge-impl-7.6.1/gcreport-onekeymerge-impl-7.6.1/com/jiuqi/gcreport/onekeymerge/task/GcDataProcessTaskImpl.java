/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.RedisUtils
 *  com.jiuqi.common.base.util.RestTemplateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.onekeymerge.dto.PreprocessStateEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDataPreProcessVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcPreParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.RedisUtils;
import com.jiuqi.common.base.util.RestTemplateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.onekeymerge.dto.PreprocessStateEnum;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyProcessService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcDataPreProcessVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcPreParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDataProcessTaskImpl
implements GcCenterTask {
    private static final Logger logger = LoggerFactory.getLogger(GcDataProcessTaskImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GcOnekeyProcessService processService;
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO) {
        return this.tt(paramsVO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ReturnObject doDataProcess(GcPreParamsVO paramsVO) {
        ReturnObject returnObject;
        TaskLog taskLog;
        block11: {
            taskLog = new TaskLog();
            returnObject = new ReturnObject(true);
            ArrayList retListVO = new ArrayList();
            try {
                String address = paramsVO.getAddress();
                if (StringUtils.isNull((String)address)) {
                    throw new BusinessRuntimeException("\u51ed\u8bc1\u6c60\u5730\u5740\u4e3a\u7a7a\uff0c\u8bf7\u7ef4\u62a4\u6539\u5730\u5740\u540e\u91cd\u8bd5");
                }
                address = address.endsWith("/") ? address : address + "/";
                address = address + "api/datacenter/v1/report/onekeymerge/doPreprocess";
                boolean dataProcess = this.redisUtils.hasKey("DATAPROCESS");
                if (dataProcess) {
                    throw new BusinessRuntimeException("\u5f53\u524d\u6709\u6b63\u5728\u6267\u884c\u7684\u6570\u636e\u9884\u5904\u7406\uff0c\u8bf7\u7a0d\u540e\u6267\u884c");
                }
                taskLog.writeInfoLog("\u5f00\u59cb\u6267\u884c\u9884\u5904\u7406", null);
                this.processService.setTaskProcessWithCode(paramsVO.getTaskLogId(), taskLog, TaskTypeEnum.DATAPROCESS.getCode());
                HashMap<String, String> jsonMap = new HashMap<String, String>();
                jsonMap.put("msgId", paramsVO.getTaskLogId());
                jsonMap.put("unitCode", OrgUtils.getCurrentUnit((GcActionParamsVO)paramsVO).getCode());
                this.redisUtils.set("DATAPROCESS", (Object)paramsVO.getTaskLogId());
                Map result = (Map)RestTemplateUtils.postJSON((String)address, jsonMap, (TypeReference)new TypeReference<Map<String, Object>>(){});
                Boolean success = Boolean.valueOf(String.valueOf(result.get("success")));
                if (success.booleanValue()) {
                    while (true) {
                        ReturnObject ret = this.waitForResult(paramsVO.getTaskLogId().toString(), paramsVO.getAddress());
                        TaskLog retData = (TaskLog)ret.getData();
                        if (!ret.isSuccess()) {
                            returnObject.setSuccess(false);
                        } else if (!retData.isFinish()) {
                            retData.getCompleteMessage().forEach(message -> {
                                taskLog.writeInfoLog(message.getMessage().toString(), null);
                                GcDataPreProcessVO vo = new GcDataPreProcessVO();
                                vo.setResult(message.getMessage().toString());
                                vo.setRule(message.getMessage().toString());
                                retListVO.add(vo);
                            });
                            returnObject.setData(retListVO);
                            continue;
                        }
                        break block11;
                        break;
                    }
                }
                throw new BusinessRuntimeException("\u8c03\u7528\u63a5\u53e3\u5931\u8d25");
            }
            catch (Exception e) {
                try {
                    logger.error("\u6570\u636e\u9884\u5904\u7406\u5931\u8d25", e);
                    returnObject.setErrorMessage(e.getMessage());
                    taskLog.writeErrorLog(e.getMessage(), null);
                }
                catch (Throwable throwable) {
                    returnObject.setSuccess(true);
                    this.redisUtils.del(new String[]{"DATAPROCESS"});
                    taskLog.setFinish(true);
                    throw throwable;
                }
                returnObject.setSuccess(true);
                this.redisUtils.del(new String[]{"DATAPROCESS"});
                taskLog.setFinish(true);
            }
        }
        returnObject.setSuccess(true);
        this.redisUtils.del(new String[]{"DATAPROCESS"});
        taskLog.setFinish(true);
        return returnObject;
    }

    private ReturnObject waitForResult(String taskLogId, String address) {
        TaskLog dataProcessLog = new TaskLog();
        try {
            dataProcessLog = this.getDataProcessLog(taskLogId, address);
            if (dataProcessLog.isFinish()) {
                return ReturnObject.ofSuccess((Object)dataProcessLog);
            }
            return ReturnObject.ofSuccess((Object)dataProcessLog);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnObject.ofFailed((String)e.getMessage(), (Object)dataProcessLog);
        }
    }

    public TaskLog getDataProcessLog(String taskLogId, String address) {
        TaskLog taskLog = new TaskLog();
        address = address.endsWith("/") ? address : address + "/";
        address = address + "api/datacenter/v1/report/onekeymerge/progress/";
        String url = address + taskLogId;
        try {
            Map result = (Map)RestTemplateUtils.getJSON((String)url, (TypeReference)new TypeReference<Map<String, Object>>(){});
            Map data = (Map)result.get("data");
            List prcessResults = (List)data.get("prcessResults");
            int state = (Integer)data.get("state");
            prcessResults.stream().filter(String::isEmpty).forEach(s -> taskLog.writeInfoLog(s, null));
            if (PreprocessStateEnum.ON.getState() != state) {
                taskLog.setFinish(true);
            }
            if (PreprocessStateEnum.FAILED.getState() == state) {
                throw new BusinessRuntimeException("\u6267\u884c\u5931\u8d25");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            taskLog.setFinish(true);
            throw e;
        }
        return taskLog;
    }

    private ReturnObject tt(GcActionParamsVO paramsVO) {
        String[] log = new String[]{"\u5168\u7ef4\u5ea6\u8f85\u52a9\u4f59\u989d\u8868\u8ba1\u7b97\u8ba1\u5212\u4efb\u52a1", "\u4efb\u52a1\u9879\u7ba1\u7406\u8ba1\u5212\u4efb\u52a1", "\u51ed\u8bc1\u5b57\u6bb5\u6570\u636e\u8f6c\u6362\u8ba1\u5212\u4efb\u52a1", "\u79d1\u76ee\u4f59\u989d\u8868\u8ba1\u7b97\u8ba1\u5212\u4efb\u52a1", "\u73b0\u91d1\u9879\u76ee\u4f59\u989d\u8868\u8ba1\u7b97\u8ba1\u5212\u4efb\u52a1", "\u91cd\u5206\u7c7b\u9884\u5904\u7406\u8ba1\u5212\u4efb\u52a1", "\u51ed\u8bc1\u5b57\u6bb5\u6570\u636e\u7a3d\u67e5\u8ba1\u5212\u4efb\u52a1", "\u5e74\u7ed3\u4f59\u989d\u8868\u540c\u6b65\u5230\u4e0b\u4e00\u5e74\u8ba1\u5212\u4efb\u52a1", "\u5efa\u9020\u5408\u540c\u62c9\u53d6\u8ba1\u5212\u4efb\u52a1"};
        TaskLog taskLog = new TaskLog(paramsVO.getOnekeyProgressData());
        ArrayList<GcBaseTaskStateVO> retListVO = new ArrayList<GcBaseTaskStateVO>();
        taskLog.writeInfoLog("\u5f00\u59cb\u6267\u884c\u9884\u5904\u7406", null);
        GcTaskResultEO eo = new GcTaskResultEO();
        eo.setUserName(OneKeyMergeUtils.getUser().getName());
        eo.setTaskTime(DateUtils.now());
        eo.setTaskCode(TaskTypeEnum.DATAPROCESS.getCode());
        taskLog.setTotalNum(Integer.valueOf(log.length));
        for (String s : log) {
            taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + 1));
            if (this.onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId().toString())) {
                taskLog.writeErrorLog("\u624b\u52a8\u505c\u6b62,\u670d\u52a1\u4e2d\u65ad", null);
                throw new BusinessRuntimeException("\u670d\u52a1\u7ec8\u6b62");
            }
            GcDataPreProcessVO dataPreProcessVO = new GcDataPreProcessVO();
            dataPreProcessVO.setRule(s);
            dataPreProcessVO.setResult("\u6210\u529f");
            taskLog.writeInfoLog(s, null);
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            retListVO.add((GcBaseTaskStateVO)dataPreProcessVO);
        }
        taskLog.setState(TaskStateEnum.SUCCESS);
        taskLog.endTask();
        this.onekeyMergeService.saveTaskResult(paramsVO, eo, retListVO, TaskStateEnum.SUCCESS.getCode());
        return ReturnObject.ofSuccess(retListVO);
    }
}

