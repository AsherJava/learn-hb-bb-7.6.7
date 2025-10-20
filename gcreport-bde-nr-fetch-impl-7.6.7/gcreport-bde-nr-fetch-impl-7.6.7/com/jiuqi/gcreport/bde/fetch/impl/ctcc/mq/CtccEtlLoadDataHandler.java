/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.certify.BdeRequestCertifyConfig
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil
 *  com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.etl.common.EtlServeEntity
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf.EtlBatchFetchMessage;
import com.jiuqi.gcreport.bde.fetch.impl.etl.service.BdeEtlExecuteService;
import com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CtccEtlLoadDataHandler
implements ITaskHandler {
    public static final String FN_TASK_NAME = "CTCC_ETL_LOAD_DATA";
    @Autowired
    private BdeRequestCertifyConfig certifyConfig;
    @Autowired
    private BdeEtlExecuteService etlExecuteService;
    private static final String FN_DIMCODE_TMPL = "\u4efb\u52a1\uff1a\u3010%1$s|%2$s\u3011,\u62a5\u8868:\u3010%3$s|%4$s\u3011";

    public String getName() {
        return FN_TASK_NAME;
    }

    public String getTitle() {
        return "\u7535\u4fe1-\u8c03\u7528ETL\u52a0\u8f7d\u6570\u636e";
    }

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        List messageList = (List)JsonUtils.readValue((String)preParam, (TypeReference)new TypeReference<List<EtlBatchFetchMessage>>(){});
        HashMap<String, String> params = new HashMap<String, String>();
        for (EtlBatchFetchMessage message : messageList) {
            params.put(JsonUtils.writeValueAsString((Object)message), this.formatDimCode(message));
        }
        return params;
    }

    private String formatDimCode(EtlBatchFetchMessage message) {
        return String.format(FN_DIMCODE_TMPL, message.getTaskCode(), message.getTaskTitle(), message.getFormCode(), message.getFormTitle());
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        EtlBatchFetchMessage message = (EtlBatchFetchMessage)JsonUtils.readValue((String)param, EtlBatchFetchMessage.class);
        DataIntegrationtkExecuteParams etlTaskExecuteParams = this.initEtlTaskExecuteParams(message);
        monitor.progressAndLog(0.2, "\u5f00\u542f\u8c03\u7528ETL\u5904\u7406\u6570\u636e");
        try {
            String result = this.etlExecuteService.doExecute(etlTaskExecuteParams);
            monitor.progressAndLog(0.9, "\u8c03\u7528ETL\u5904\u7406\u6570\u636e\u5b8c\u6210");
            TaskHandleResult handleResult = new TaskHandleResult();
            handleResult.setSuccess(Boolean.valueOf(true));
            handleResult.setPreParam(param);
            handleResult.appendLog(result);
            return handleResult;
        }
        catch (BusinessRuntimeException e) {
            TaskHandleResult handleResult = new TaskHandleResult();
            handleResult.setSuccess(Boolean.valueOf(false));
            handleResult.appendLog(e.getMessage());
            handleResult.setPreParam(param);
            return handleResult;
        }
    }

    private DataIntegrationtkExecuteParams initEtlTaskExecuteParams(EtlBatchFetchMessage message) {
        DataIntegrationtkExecuteParams etlTaskExecuteParams = new DataIntegrationtkExecuteParams();
        EtlServeEntity serveEntity = DataIntegrationUtil.getEtlServeEntity();
        etlTaskExecuteParams.setUrl(serveEntity.getAddress());
        etlTaskExecuteParams.setUserName(serveEntity.getUserName());
        etlTaskExecuteParams.setPw(DataIntegrationUtil.getPassword((EtlServeEntity)serveEntity));
        etlTaskExecuteParams.setEtlTaskKey(message.getEtlTaskId());
        JSONObject etlTaskParam = new JSONObject();
        etlTaskParam.put("TASKKEY", (Object)message.getTaskKey());
        etlTaskParam.put("SCHEMEKEY", (Object)message.getSchemeKey());
        etlTaskParam.put("DATATIME", (Object)message.getDataTime());
        if (!StringUtils.isEmpty((String)message.getCurrency())) {
            etlTaskParam.put("CURRENCY", (Object)message.getCurrency());
        }
        if (!StringUtils.isEmpty((String)message.getOrgType())) {
            etlTaskParam.put("ORGTYPE", (Object)message.getOrgType());
        }
        etlTaskParam.put("UNITCODE", (Object)String.join((CharSequence)";", message.getUnitCodes()));
        etlTaskParam.put("FORMKEY", (Object)message.getFormKey());
        etlTaskParam.put("FORMCODE", (Object)message.getFormCode());
        etlTaskParam.put("USER", (Object)NpContextHolder.getContext().getUserName());
        etlTaskExecuteParams.setParam(etlTaskParam);
        return etlTaskExecuteParams;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public String getModule() {
        CtccEtlLoadDataHandler ctccEtlLoadDataHandler = this;
        return ctccEtlLoadDataHandler.certifyConfig.getModuleName();
    }

    public IDimType getDimType() {
        return new IDimType(){

            public String getTitle() {
                return "\u4efb\u52a1\u3001\u62a5\u8868\u4fe1\u606f";
            }

            public String getName() {
                return "TASK_FORM";
            }
        };
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public String getSpecialQueueFlag() {
        return "CTCC_ETL_LOADDATA";
    }
}

