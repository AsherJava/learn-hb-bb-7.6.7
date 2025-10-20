/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.webserviceclient.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.webserviceclient.utils.WebserviceClientUtil;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataIntegrationExecuter {
    private static Logger logger = LoggerFactory.getLogger(DataIntegrationExecuter.class);
    private static final String API_JOBS_REMOTE_EXEC_DATAINTEGRATION = "/api/jobs/remote/exec/com.jiuqi.bi.dataintegration/";
    private static final String API_JOBS_REMOTE_STATUS = "/api/jobs/remote/status/com.jiuqi.bi.dataintegration/";
    private static final String API_JOBS_REMOTE_LOGS = "/api/jobs/remote/logs/com.jiuqi.bi.dataintegration/";

    public void executeEtlTask(AsyncTaskMonitor monitor, DataIntegrationtkExecuteParams etlTaskExecuteParams, List<String> executeMessages) {
        NrdlTaskExecutor.login((String)etlTaskExecuteParams.getUrl(), (String)etlTaskExecuteParams.getUserName(), (String)etlTaskExecuteParams.getPw());
        String instanceGuid = this.getInstanceGuid(etlTaskExecuteParams.getEtlTaskKey(), etlTaskExecuteParams.getParam().toString());
        try {
            long timeStampPoint = new Date().getTime();
            double[] progress = new double[]{0.15, 0.0};
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
                execStatus = (Integer)new JSONObject(NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_STATUS + instanceGuid), null)).get("state");
                this.modityMoniterProcess(progress, execStatus);
                timeStampPoint = this.checkTaskLogInfo(monitor, progress, timeStampPoint, instanceGuid, executeMessages);
            }
            if (execStatus == -2) {
                this.checkEtlExecuteResult(execStatus);
            } else {
                JSONObject json = new JSONObject(NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_STATUS + instanceGuid), null));
                this.checkEtlExecuteResult(json.getInt("result"));
            }
            this.checkTaskLogInfo(monitor, progress, timeStampPoint, instanceGuid, executeMessages);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    public long checkTaskLogInfo(AsyncTaskMonitor monitor, double[] progress, long timeStampPoint, String instanceGuid, List<String> executeMessages) {
        List<Map> newTaskLogs = this.getTaskLogDetails(timeStampPoint, instanceGuid);
        if (CollectionUtils.isEmpty(newTaskLogs)) {
            return timeStampPoint;
        }
        timeStampPoint = (Long)newTaskLogs.get(newTaskLogs.size() - 1).get("timestamp");
        String errorMsg = "";
        for (Map taskLogDetail : newTaskLogs) {
            String level = (String)taskLogDetail.get("level");
            String message = (String)taskLogDetail.get("message");
            if ("\u9519\u8bef".equals(level) && StringUtils.isEmpty((String)errorMsg)) {
                errorMsg = message;
            } else {
                int logBeginIndex = message.indexOf("@@");
                if (logBeginIndex == -1) continue;
                if (message.length() > logBeginIndex + 2) {
                    message = message.substring(logBeginIndex + 2);
                }
            }
            String detail = "\u3010" + level + "\u3011 " + message;
            WebserviceClientUtil.modifyAsyncTaskState(monitor, progress[0], detail, executeMessages);
        }
        executeMessages.add(errorMsg);
        if (!StringUtils.isEmpty((String)errorMsg)) {
            logger.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38" + errorMsg);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u5f02\u5e38" + errorMsg);
        }
        return timeStampPoint;
    }

    private List<Map> getTaskLogDetails(long timeStampPoint, String instanceGuid) {
        String etlTaskLogs = NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_LOGS + instanceGuid + "?requireType=afterAll&requireCount=5&timeStampPoint=" + timeStampPoint), null);
        if (etlTaskLogs.contains("error_code")) {
            throw new BusinessRuntimeException("\u6267\u884c\u65e5\u5fd7\u67e5\u8be2\u8c03\u7528\u6267\u884c\u5931\u8d25");
        }
        return JSONUtil.parseArray((String)etlTaskLogs, Map.class);
    }

    public int getTaskExecStatus(String instanceGuid) {
        try {
            String msg = NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_STATUS + instanceGuid), null);
            JSONObject json = new JSONObject(msg);
            if (json.has("error_code")) {
                throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25");
            }
            return json.getInt("state");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25", (Throwable)e);
        }
    }

    private String getInstanceGuid(String taskid, String param) {
        try {
            HashMap<String, String> paras = new HashMap<String, String>();
            paras.put("params", param);
            String instanceGuid = NrdlTaskExecutor.doPost((String)(API_JOBS_REMOTE_EXEC_DATAINTEGRATION + taskid), null, paras).replace("\"", "");
            return instanceGuid.replace("instanceGuid:", "").replace("{", "").replace("}", "");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
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

    private void modityMoniterProcess(double[] progress, int status) {
        if (status != 0) {
            return;
        }
        double step = 0.05;
        if (progress[0] >= 0.98) {
            progress[0] = 0.99;
            step = 0.0;
        } else if (progress[0] >= 0.8) {
            progress[1] = progress[1] + 1.0;
            step = 0.0;
            if (progress[1] % 3.0 == 0.0) {
                step = 0.01;
            }
        } else if (progress[0] >= 0.5) {
            step = 0.01;
        } else if (progress[0] >= 0.3) {
            step = 0.02;
        }
        progress[0] = progress[0] + step;
    }
}

