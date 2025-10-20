/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams
 *  com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.bde.fetch.impl.etl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.etl.service.BdeEtlExecuteService;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams;
import com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BdeEtlExecuteServiceImpl
implements BdeEtlExecuteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdeEtlExecuteServiceImpl.class);
    public static final String FN_TMPL_LOG = "\u3010%1$s\u3011 %2$s %3$s";

    @Override
    public String doExecute(DataIntegrationtkExecuteParams etlTaskExecuteParams) {
        LOGGER.debug("\u5f00\u59cb\u8fdb\u884cETL\u4efb\u52a1\u6570\u636e\u52a0\u5de5");
        ArrayList executeMessages = CollectionUtils.newArrayList();
        NrdlTaskExecutor.login((String)etlTaskExecuteParams.getUrl(), (String)etlTaskExecuteParams.getUserName(), (String)etlTaskExecuteParams.getPw());
        String instanceGuid = this.getInstanceGuid(etlTaskExecuteParams.getEtlTaskKey(), etlTaskExecuteParams.getParam().toString());
        try {
            long timeStampPoint = System.currentTimeMillis();
            int execStatus = this.getTaskExecStatus(instanceGuid);
            int calCount = 0;
            while (execStatus == 0 || execStatus == -1) {
                long nanos = 2000000000L;
                if (++calCount > 100) {
                    nanos = 10000000000L;
                } else if (calCount > 50) {
                    nanos = 6000000000L;
                } else if (calCount > 10) {
                    nanos = 4000000000L;
                }
                LockSupport.parkNanos(nanos);
                execStatus = (Integer)new JSONObject(NrdlTaskExecutor.doGet((String)("/api/jobs/remote/status/com.jiuqi.bi.dataintegration/" + instanceGuid), null)).get("state");
            }
            if (execStatus == -2) {
                this.checkEtlExecuteResult(execStatus);
            } else {
                JSONObject json = new JSONObject(NrdlTaskExecutor.doGet((String)("/api/jobs/remote/status/com.jiuqi.bi.dataintegration/" + instanceGuid), null));
                this.checkEtlExecuteResult(json.getInt("result"));
            }
            this.checkTaskLogInfo(timeStampPoint, instanceGuid, executeMessages);
            LOGGER.debug("ETL\u6d41\u7a0b\u65e5\u5fd7 " + executeMessages);
            return String.join((CharSequence)"\r\n", executeMessages);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("ETL\u6d41\u7a0b\u9519\u8bef\u65e5\u5fd7 " + executeMessages);
            throw new BusinessRuntimeException(String.join((CharSequence)"\r\n", executeMessages), (Throwable)e);
        }
    }

    public long checkTaskLogInfo(long timeStampPoint, String instanceGuid, List<String> executeMessages) {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38: {}", (Object)e.getMessage(), (Object)e);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u5f02\u5e38: " + e.getMessage(), (Throwable)e);
        }
        List<LinkedHashMap<String, Object>> newTaskLogs = this.getTaskLogDetails(timeStampPoint, instanceGuid);
        if (CollectionUtils.isEmpty(newTaskLogs)) {
            return timeStampPoint;
        }
        timeStampPoint = (Long)newTaskLogs.get(newTaskLogs.size() - 1).get("timestamp");
        String errorMsg = "";
        for (LinkedHashMap<String, Object> taskLogDetail : newTaskLogs) {
            String level = (String)taskLogDetail.get("level");
            executeMessages.add(BdeEtlExecuteServiceImpl.parseErrorMsg(taskLogDetail));
            if (!"\u9519\u8bef".equals(level) || !StringUtils.isEmpty((String)errorMsg)) continue;
            errorMsg = (String)taskLogDetail.get("message");
        }
        if (!StringUtils.isEmpty((String)errorMsg)) {
            LOGGER.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38" + errorMsg);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u5f02\u5e38" + errorMsg);
        }
        return timeStampPoint;
    }

    public static String parseErrorMsg(LinkedHashMap<String, Object> taskLogDetail) {
        return String.format(FN_TMPL_LOG, (String)taskLogDetail.get("level"), (String)taskLogDetail.get("dateTime"), (String)taskLogDetail.get("message"));
    }

    public int getTaskExecStatus(String instanceGuid) {
        try {
            String msg = NrdlTaskExecutor.doGet((String)("/api/jobs/remote/status/com.jiuqi.bi.dataintegration/" + instanceGuid), null);
            JSONObject json = new JSONObject(msg);
            if (json.has("error_code")) {
                throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25");
            }
            return json.getInt("state");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25", (Throwable)e);
        }
    }

    private List<LinkedHashMap<String, Object>> getTaskLogDetails(long timeStampPoint, String instanceGuid) {
        String etlTaskLogs = NrdlTaskExecutor.doGet((String)("/api/jobs/remote/logs/com.jiuqi.bi.dataintegration/" + instanceGuid + "?requireType=afterAll&requireCount=5&timeStampPoint=" + timeStampPoint), null);
        if (etlTaskLogs.contains("error_code")) {
            throw new BusinessRuntimeException("\u6267\u884c\u65e5\u5fd7\u67e5\u8be2\u8c03\u7528\u6267\u884c\u5931\u8d25");
        }
        return (List)JsonUtils.readValue((String)etlTaskLogs, (TypeReference)new TypeReference<List<LinkedHashMap<String, Object>>>(){});
    }

    private String getInstanceGuid(String taskid, String param) {
        try {
            HashMap<String, String> paras = new HashMap<String, String>();
            paras.put("params", param);
            String instanceGuid = NrdlTaskExecutor.doPost((String)("/api/jobs/remote/exec/com.jiuqi.bi.dataintegration/" + taskid), null, paras).replace("\"", "");
            return instanceGuid.replace("instanceGuid:", "").replace("{", "").replace("}", "");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u8c03\u7528\u5931\u8d25", (Throwable)e);
        }
    }

    public void checkEtlExecuteResult(int statusCode) {
        if (statusCode != 100) {
            String errorMsg = "\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25";
            switch (statusCode) {
                case 500: {
                    errorMsg = "\u4efb\u52a1\u53c2\u6570\u9519\u8bef\u6216\u670d\u52a1\u5668\u5f02\u5e38";
                    break;
                }
                case -9999: {
                    errorMsg = "\u672a\u8ba4\u8bc1\u901a\u8fc7";
                    break;
                }
                case -100: {
                    errorMsg = "\u4efb\u52a1\u8fd0\u884c\u5931\u8d25";
                    break;
                }
                case 1: {
                    errorMsg = "\u4efb\u52a1\u672a\u5b8c\u6210";
                    break;
                }
                case 2: {
                    errorMsg = "\u4efb\u52a1\u5df2\u53d6\u6d88";
                    break;
                }
                case 3: {
                    errorMsg = "\u4efb\u52a1\u5df2\u7ec8\u6b62";
                    break;
                }
                case 4: {
                    errorMsg = "\u4efb\u52a1\u5f02\u5e38";
                    break;
                }
                case -2: {
                    errorMsg = "\u4efb\u52a1\u6b63\u5728\u53d6\u6d88";
                    break;
                }
            }
            throw new BusinessRuntimeException(errorMsg);
        }
    }
}

