/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 */
package com.jiuqi.dc.integration.execute.impl.runner;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO;
import com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO;
import com.jiuqi.dc.integration.execute.impl.service.ConvertService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PlanTaskRunner(id="AE94B960B4C14A81BB09EC27C86F14E2", name="convertRunner", title="\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408", group="\u4e00\u672c\u8d26/\u6570\u636e\u6574\u5408", settingPage="dataIntegrationConfig", order=1)
@Component(value="convertRunner")
public class ConvertRunner
extends Runner {
    @Autowired
    private ConvertService executeService;
    @Autowired
    private TaskLogService logService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    public boolean excute(String runnerParameter) {
        Date startTime = DateUtils.now();
        String instanceId = UUIDUtils.newUUIDStr();
        if (StringUtils.isEmpty((String)runnerParameter)) {
            this.appendLog(String.format("\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408taskId = %1$s\n", instanceId));
            String log = "\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408\u6267\u884c\u5931\u8d25\uff0c\u6570\u636e\u6574\u5408\u9ad8\u7ea7\u53c2\u6570\u672a\u914d\u7f6e";
            this.appendLog(log);
            this.logService.insertFailureTaskAndItemLogs(instanceId, "\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408", log, startTime);
            return false;
        }
        ConvertExecuteDTO executeParam = (ConvertExecuteDTO)JsonUtils.readValue((String)runnerParameter, ConvertExecuteDTO.class);
        if (CollectionUtils.isEmpty((Collection)executeParam.getBaseDataRefs()) && CollectionUtils.isEmpty((Collection)executeParam.getBizDataRefs())) {
            this.appendLog(String.format("\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408taskId = %1$s\n", instanceId));
            String log = "\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408\u6267\u884c\u5931\u8d25\uff0c\u57fa\u7840\u6570\u636e\u8303\u56f4\u4e0e\u4e1a\u52a1\u6570\u636e\u8303\u56f4\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a";
            this.appendLog(log);
            this.appendLog("\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408\u6267\u884c\u5931\u8d25\uff0c\u57fa\u7840\u6570\u636e\u8303\u56f4\u4e0e\u4e1a\u52a1\u6570\u636e\u8303\u56f4\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a");
            this.logService.insertFailureTaskAndItemLogs(instanceId, "\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408", log, startTime);
            return false;
        }
        if (executeParam.getDataScheme() == null) {
            String log = "\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408\u6267\u884c\u5931\u8d25\uff0c\u6570\u636e\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a";
            this.appendLog(log);
            this.logService.insertFailureTaskAndItemLogs(instanceId, "\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408", log, startTime);
            return false;
        }
        Map<String, String> convertMap = this.executeService.convert(executeParam, true);
        if (!StringUtils.isEmpty((String)convertMap.get("BaseDataConvert"))) {
            this.appendLog(String.format("\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408-\u57fa\u7840\u6570\u636e\u65b9\u6848taskId = %1$s\n", convertMap.get("BaseDataConvert")));
        }
        if (!StringUtils.isEmpty((String)convertMap.get("BizVoucherConvert"))) {
            this.appendLog(String.format("\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408-\u51ed\u8bc1\u6570\u636e\u65b9\u6848taskId = %1$s\n", convertMap.get("BizVoucherConvert")));
        }
        if (!StringUtils.isEmpty((String)convertMap.get("BizBalanceInitConvert"))) {
            this.appendLog(String.format("\u3010\u6570\u636e\u6574\u5408\u3011\u6570\u636e\u6574\u5408-\u5e74\u521d\u6570\u636e\u65b9\u6848taskId = %1$s\n", convertMap.get("BizBalanceInitConvert")));
        }
        StringBuilder bizDataScheme = new StringBuilder();
        StringBuilder baseDataScheme = new StringBuilder();
        if (!CollectionUtils.isEmpty((Collection)executeParam.getBaseDataRefs())) {
            for (ConvertRefDefineDTO refDefine : executeParam.getBaseDataRefs()) {
                if (baseDataScheme.length() > 0) {
                    baseDataScheme.append(",");
                }
                baseDataScheme.append(refDefine.getName());
            }
        }
        if (!CollectionUtils.isEmpty((Collection)executeParam.getBizDataRefs())) {
            for (ConvertRefDefineDTO refDefine : executeParam.getBizDataRefs()) {
                if (bizDataScheme.length() > 0) {
                    bizDataScheme.append(",");
                }
                bizDataScheme.append(refDefine.getName());
            }
        }
        this.appendLog(String.format("\u672c\u6b21\u6267\u884c\u6570\u636e\u65b9\u6848\u3010%1$s\u3011\n\u4e1a\u52a1\u6570\u636e\u65b9\u6848\u3010%2$s\u3011\n\u57fa\u7840\u6570\u636e\u65b9\u6848\u3010%3$s\u3011", executeParam.getDataScheme().getName(), bizDataScheme, baseDataScheme));
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        for (String logId : convertMap.values()) {
            taskHandlerClient.waitTaskFinished(logId);
        }
        return true;
    }
}

