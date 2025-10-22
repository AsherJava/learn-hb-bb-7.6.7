/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.rest.RestClient
 *  com.jiuqi.bi.rest.bean.RestResult
 *  com.jiuqi.bi.rest.exception.RestException
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.bi.rest.RestClient;
import com.jiuqi.bi.rest.bean.RestResult;
import com.jiuqi.bi.rest.exception.RestException;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.etl.common.ETLTask;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.service.EtlTaskStateCallback;
import com.jiuqi.nr.etl.utils.ArrayOfTask;
import com.jiuqi.nr.etl.utils.ExecuteServiceWithAuth;
import com.jiuqi.nr.etl.utils.ExecuteServiceWithAuthPortType;
import com.jiuqi.nr.etl.utils.Task;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import javax.xml.namespace.QName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETLTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ETLTaskExecutor.class);
    private boolean calAfterFetch = false;
    private EtlInfo etlInfo;
    private IBatchCalculateService batchCalculateService;

    public ETLTaskExecutor() {
    }

    public ETLTaskExecutor(boolean calAfterFetch, EtlInfo etlInfo, IBatchCalculateService batchCalculateService) {
        this.calAfterFetch = calAfterFetch;
        this.etlInfo = etlInfo;
        this.batchCalculateService = batchCalculateService;
    }

    @Deprecated
    public List<Task> getAllTaskss(String url, String userName, String passWord, Logger logger) {
        try {
            ExecuteServiceWithAuth ser = new ExecuteServiceWithAuth(new URL(url), new QName("http://service.etl.jiuqi.com", "ExecuteServiceWithAuth"));
            ExecuteServiceWithAuthPortType port = ser.getExecuteServiceWithAuthHttpPort();
            ArrayOfTask tasks = port.getAllTask(userName, passWord);
            if (tasks != null) {
                return tasks.getTask();
            }
            return new ArrayList<Task>();
        }
        catch (Exception e) {
            logger.error("\u8fde\u63a5ETL\u4ea7\u751f\u9519\u8bef\uff1a" + e.getMessage());
            return new ArrayList<Task>();
        }
    }

    @Deprecated
    public Task findTaskByName(String taskName, String url, String userName, String passWord, Logger logger) {
        try {
            ExecuteServiceWithAuth ser = new ExecuteServiceWithAuth(new URL(url), new QName("http://service.etl.jiuqi.com", "ExecuteServiceWithAuth"));
            ExecuteServiceWithAuthPortType port = ser.getExecuteServiceWithAuthHttpPort();
            ArrayOfTask tasks = port.findTaskByName(taskName, userName, passWord);
            if (tasks != null) {
                List<Task> taskList = tasks.getTask();
                return taskList == null ? null : taskList.get(0);
            }
        }
        catch (Exception e) {
            logger.error("\u8fde\u63a5ETL\u4ea7\u751f\u9519\u8bef\uff1a" + e.getMessage());
            return null;
        }
        return null;
    }

    public EtlExecuteInfo execute(String taskid, String param, String url, String userName, String passWord, Logger logger) {
        return this.execute(taskid, param, url, userName, passWord, (int status) -> logger.info("ETL \u6b63\u5728\u6267\u884c\uff0c\u72b6\u6001{}", (Object)status));
    }

    public EtlExecuteInfo execute(String taskid, String param, String url, String userName, String passWord, EtlTaskStateCallback callback) {
        EtlExecuteInfo resule = new EtlExecuteInfo();
        RestClient restClient = ETLTaskExecutor.getClient(url, userName, passWord);
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put("paramsStr", param);
            String exeUri = "/core/remote/execution/" + taskid;
            RestResult exeResult = restClient.post(exeUri, params);
            if (exeResult.getStatusCode() == 200) {
                String data = exeResult.getDataAsString();
                JSONObject json = new JSONObject(data);
                String instanceGuid = json.getString("instanceGuid");
                resule.setResult(data);
                resule.setDetailMessage(exeResult.getStatusText());
                resule.setStatusCode(exeResult.getStatusCode());
                HashMap stateParams = new HashMap();
                String exeStateUri = "/core/remote/" + instanceGuid + "/state";
                RestResult stateResult = restClient.get(exeStateUri, stateParams);
                if (stateResult.getStatusCode() == 200) {
                    String stateData = stateResult.getDataAsString();
                    JSONObject stateJson = new JSONObject(stateData);
                    int calCount = 0;
                    int status = stateJson.getInt("state");
                    callback.processTaskState(status);
                    while (status == 0) {
                        long nanos = 2000000000L;
                        if (++calCount > 100) {
                            nanos = 10000000000L;
                        } else if (calCount > 50) {
                            nanos = 6000000000L;
                        } else if (calCount > 10) {
                            nanos = 4000000000L;
                        }
                        LockSupport.parkNanos(nanos);
                        status = new JSONObject(restClient.get(exeStateUri, stateParams).getDataAsString()).getInt("state");
                        callback.processTaskState(status);
                    }
                    if (status == 1) {
                        logger.info("ETL\u8c03\u7528\u6267\u884c\u6210\u529f\uff01");
                        if (this.calAfterFetch) {
                            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                            batchCalculateInfo.setDimensionSet(this.etlInfo.getDimensionSet());
                            batchCalculateInfo.setFormSchemeKey(this.etlInfo.getFormSchemeKey());
                            batchCalculateInfo.setFormulaSchemeKey(this.etlInfo.getFormulaSchemeKey());
                            batchCalculateInfo.setTaskKey(this.etlInfo.getTaskKey());
                            batchCalculateInfo.setVariableMap(this.etlInfo.getVariableMap());
                            this.batchCalculateService.batchCalculateForm(batchCalculateInfo, null);
                        }
                    } else {
                        logger.info("ETL\u8c03\u7528\u6267\u884c\u5931\u8d25\uff01");
                        resule.setErrorMessage("ETL\u8c03\u7528\u6267\u884c\u5931\u8d25");
                    }
                    return resule;
                }
                String stateData = stateResult.getDataAsString();
                JSONObject stateJson = new JSONObject(stateData);
                resule.setDetailMessage(stateResult.getStatusText());
                resule.setErrorMessage(stateJson.getString("error_msg"));
                resule.setStatusCode(stateResult.getStatusCode());
                return resule;
            }
            logger.error("ETL\u8c03\u7528\u6267\u884c\u5931\u8d25\uff01");
            String data = exeResult.getDataAsString();
            JSONObject json = new JSONObject(data);
            resule.setResult(data);
            resule.setDetailMessage(exeResult.getStatusText());
            resule.setErrorMessage(json.getString("error_msg"));
            resule.setStatusCode(exeResult.getStatusCode());
            return resule;
        }
        catch (Exception e) {
            logger.error("\u8fde\u63a5ETL\u4ea7\u751f\u9519\u8bef\uff1a" + e.getMessage());
            return resule;
        }
    }

    public static RestClient getClient(String url, String userName, String passWord) {
        String urltemp = url + "/api/";
        RestClient restClient = new RestClient(urltemp);
        boolean b = false;
        try {
            logger.info("\u767b\u5f55\u7528\u6237" + userName);
            String appid = "0000014C10F1C2C128DB3835918E0081";
            String devid = "B327-C214-1D31-A2B8";
            restClient.login(appid, devid, userName, passWord);
            return restClient;
        }
        catch (RestException e1) {
            logger.error(e1.getMessage(), e1);
            if (!b) {
                logger.error("\u767b\u5f55\u5931\u8d25");
            }
            return null;
        }
    }

    public List<ETLTask> getAllTask(String url, String userName, String passWord) {
        ArrayList<ETLTask> tasks = new ArrayList<ETLTask>();
        ETLTask task = null;
        RestClient restClient = ETLTaskExecutor.getClient(url, userName, passWord);
        HashMap params = new HashMap();
        try {
            String allTaskUri = "/core/remote/task";
            RestResult allTaskResult = restClient.get(allTaskUri, params);
            if (allTaskResult.getStatusCode() == 200) {
                JSONArray array = new JSONArray(allTaskResult.getDataAsString());
                for (int i = 0; i < array.length(); ++i) {
                    Object taskDesc;
                    Object taskName;
                    JSONObject json = array.getJSONObject(i);
                    task = new ETLTask();
                    Object taskGuid = json.get("taskGuid");
                    if (taskGuid != null) {
                        task.setTaskGuid(taskGuid.toString());
                    }
                    if ((taskName = json.get("taskName")) != null) {
                        task.setTaskName(taskName.toString());
                    }
                    if ((taskDesc = json.get("taskDescription")) != null) {
                        task.setTaskDescription(taskDesc.toString());
                    }
                    tasks.add(task);
                }
                return tasks;
            }
            JSONObject json = new JSONObject(allTaskResult.getDataAsString());
            logger.error("\u9519\u8bef\u7801\uff1a" + json.getInt("error_code"));
            logger.error("\u9519\u8bef\u4fe1\u606f\uff1a" + json.getString("error_msg"));
        }
        catch (RestException e1) {
            logger.error("\u83b7\u53d6\u6240\u6709ETL\u4efb\u52a1:\u8c03\u7528get\u65b9\u6cd5\u5931\u8d25");
            logger.error(e1.getMessage(), e1);
        }
        catch (JSONException e1) {
            logger.error("\u83b7\u53d6\u6240\u6709ETL\u4efb\u52a1:\u89e3\u6790\u8fd4\u56de\u503c\u5931\u8d25");
            logger.error(e1.getMessage(), e1);
        }
        return new ArrayList<ETLTask>();
    }

    public ETLTask findTaskByName(String taskName, String url, String userName, String passWord) {
        ETLTask etlTask = new ETLTask();
        RestClient restClient = ETLTaskExecutor.getClient(url, userName, passWord);
        HashMap params = new HashMap();
        try {
            String taskUri = "/core/remote/task/" + taskName;
            RestResult taskResult = restClient.get(taskUri, params);
            if (taskResult.getStatusCode() == 200) {
                JSONArray array = new JSONArray(taskResult.getDataAsString());
                for (int i = 0; i < array.length(); ++i) {
                    JSONObject json = array.getJSONObject(i);
                    Object taskGuid = json.get("taskGuid");
                    if (taskGuid != null) {
                        etlTask.setTaskGuid(taskGuid.toString());
                    }
                    etlTask.setTaskName(taskName);
                    Object taskDesc = json.get("taskDescription");
                    if (taskDesc == null) continue;
                    etlTask.setTaskDescription(taskDesc.toString());
                }
                return etlTask;
            }
            JSONObject json = new JSONObject(taskResult.getDataAsString());
            logger.error("\u9519\u8bef\u7801\uff1a" + json.getInt("error_code"));
            logger.error("\u9519\u8bef\u4fe1\u606f\uff1a" + json.getString("error_msg"));
        }
        catch (RestException e1) {
            logger.error("\u67e5\u627e\u6307\u5b9a\u6807\u8bc6\u7684ETL\u4efb\u52a1:\u8c03\u7528get\u65b9\u6cd5\u5931\u8d25");
            logger.error(e1.getMessage(), e1);
        }
        catch (JSONException e1) {
            logger.error("\u67e5\u627e\u6307\u5b9a\u6807\u8bc6\u7684ETL\u4efb\u52a1:\u89e3\u6790\u8fd4\u56de\u503c\u5931\u8d25");
            logger.error(e1.getMessage(), e1);
        }
        return null;
    }

    public EtlExecuteInfo execute(String taskGuid, String param, String url, String userName, String passWord) {
        EtlExecuteInfo etlExecuteInfo = new EtlExecuteInfo();
        RestClient restClient = ETLTaskExecutor.getClient(url, userName, passWord);
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put("paramsStr", param.toString());
            String exeUri = "/core/remote/execution/" + taskGuid;
            RestResult exeResult = restClient.post(exeUri, params);
            if (exeResult.getStatusCode() == 200) {
                String data = exeResult.getDataAsString();
                JSONObject json = new JSONObject(data);
                String instanceGuid = json.getString("instanceGuid");
                etlExecuteInfo.setResult(data);
                etlExecuteInfo.setDetailMessage(exeResult.getStatusText());
                etlExecuteInfo.setErrorMessage(json.getString("error_msg"));
                etlExecuteInfo.setStatusCode(exeResult.getStatusCode());
                return etlExecuteInfo;
            }
            if (exeResult.getStatusCode() == 403) {
                String data = exeResult.getDataAsString();
                JSONObject json = new JSONObject(data);
                etlExecuteInfo.setResult(data);
                etlExecuteInfo.setDetailMessage(exeResult.getStatusText());
                etlExecuteInfo.setErrorMessage(json.getString("error_msg"));
                etlExecuteInfo.setStatusCode(exeResult.getStatusCode());
                return etlExecuteInfo;
            }
            if (exeResult.getStatusCode() == 404) {
                String data = exeResult.getDataAsString();
                JSONObject json = new JSONObject(data);
                etlExecuteInfo.setResult(data);
                etlExecuteInfo.setDetailMessage(exeResult.getStatusText());
                etlExecuteInfo.setErrorMessage(json.getString("error_msg"));
                etlExecuteInfo.setStatusCode(exeResult.getStatusCode());
                return etlExecuteInfo;
            }
            String data = exeResult.getDataAsString();
            JSONObject json = new JSONObject(data);
            logger.error("\u9519\u8bef\u7801\uff1a" + json.getInt("error_code"));
            logger.error("\u9519\u8bef\u4fe1\u606f\uff1a" + json.getString("error_msg"));
            etlExecuteInfo.setResult(data);
            etlExecuteInfo.setDetailMessage(exeResult.getStatusText());
            etlExecuteInfo.setErrorMessage(json.getString("error_msg"));
            etlExecuteInfo.setStatusCode(exeResult.getStatusCode());
            return etlExecuteInfo;
        }
        catch (RestException e1) {
            logger.error("ETL\u670d\u52a1\u6267\u884c:\u8c03\u7528post\u65b9\u6cd5\u5931\u8d25");
            logger.error(e1.getMessage(), e1);
        }
        catch (JSONException e1) {
            logger.error("ETL\u670d\u52a1\u6267\u884c:\u89e3\u6790\u8fd4\u56de\u503c\u5931\u8d25");
            logger.error(e1.getMessage(), e1);
        }
        return null;
    }

    public boolean testLink(String url, String userName, String passWord) {
        RestClient restClient = ETLTaskExecutor.getClient(url, userName, passWord);
        return restClient != null;
    }
}

