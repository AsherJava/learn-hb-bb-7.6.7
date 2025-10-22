/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.rest.RestClient
 *  com.jiuqi.bi.rest.exception.RestException
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpException
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.ClientProtocolException
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.utils.URIBuilder
 *  org.apache.http.message.BasicHeader
 *  org.apache.http.message.BasicNameValuePair
 *  org.apache.http.util.EntityUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.bi.rest.RestClient;
import com.jiuqi.bi.rest.exception.RestException;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.NrdlTask;
import com.jiuqi.nr.etl.service.EtlTaskStateCallback;
import com.jiuqi.nr.etl.service.internal.EtlAsyncTaskErrorException;
import com.jiuqi.nr.etl.service.internal.SSLClient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NrdlTaskExecutor {
    private static final int CONN_TIME_OUT = 10000;
    private static final int CONN_SO_TIME_OUT = 60000;
    private static final Logger logger = LoggerFactory.getLogger(NrdlTaskExecutor.class);
    private static String appid = "000001399FCE11FEA057EFDF592FE408";
    private static String devid = "92B4-0A18-E330-86D8";
    private static String token;
    private static String host;
    private boolean calAfterFetch = false;
    private EtlInfo etlInfo;
    private IBatchCalculateService batchCalculateService;

    public NrdlTaskExecutor() {
    }

    public NrdlTaskExecutor(boolean calAfterFetch, EtlInfo etlInfo, IBatchCalculateService batchCalculateService) {
        this.calAfterFetch = calAfterFetch;
        this.etlInfo = etlInfo;
        this.batchCalculateService = batchCalculateService;
    }

    public static RestClient getClient(String url, String userName, String passWord) {
        String urltemp = url + "/api/";
        RestClient restClient = new RestClient(urltemp);
        boolean b = false;
        try {
            logger.info("\u767b\u5f55\u7528\u6237" + userName);
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

    public boolean testLink(String url, String userName, String passWord) {
        token = null;
        try {
            NrdlTaskExecutor.login(url, userName, passWord);
        }
        catch (RuntimeException e) {
            return false;
        }
        return token != null;
    }

    public List<NrdlTask> getAllTask(String url, String userName, String passWord) {
        ArrayList<NrdlTask> taskList = new ArrayList<NrdlTask>();
        NrdlTaskExecutor.login(url, userName, passWord);
        String taskString = NrdlTaskExecutor.doGet("/api/jobs/remote/list/com.jiuqi.bi.dataintegration", null);
        JSONArray jsonArray = new JSONArray(taskString);
        for (int i = 0; i < jsonArray.length(); ++i) {
            Object isFolder;
            Object taskTitle;
            JSONObject json = jsonArray.getJSONObject(i);
            NrdlTask task = new NrdlTask();
            Object taskGuid = json.get("guid");
            if (taskGuid != null) {
                task.setTaskGuid(taskGuid.toString());
            }
            if ((taskTitle = json.get("title")) != null) {
                task.setTaskName(taskTitle.toString());
            }
            if ((isFolder = json.get("isFolder")) != null) {
                task.setIsFolder(isFolder.equals(true));
            }
            if (task.getIsFolder()) {
                List<NrdlTask> childTasks = this.getTaskByFolderGuid(url, task.getTaskGuid());
                for (NrdlTask nrdlTask : childTasks) {
                    taskList.add(nrdlTask);
                }
                continue;
            }
            taskList.add(task);
        }
        return taskList;
    }

    public List<NrdlTask> getTaskByFolderGuid(String url, String folderGuid) {
        ArrayList<NrdlTask> taskList = new ArrayList<NrdlTask>();
        HashMap<String, String> paras = new HashMap<String, String>();
        paras.put("folderGuid", folderGuid);
        String taskString = NrdlTaskExecutor.doGet("/api/jobs/remote/list/com.jiuqi.bi.dataintegration", paras);
        JSONArray jsonArray = new JSONArray(taskString);
        for (int i = 0; i < jsonArray.length(); ++i) {
            Object isFolder;
            Object taskTitle;
            JSONObject json = jsonArray.getJSONObject(i);
            NrdlTask task = new NrdlTask();
            Object taskGuid = json.get("guid");
            if (taskGuid != null) {
                task.setTaskGuid(taskGuid.toString());
            }
            if ((taskTitle = json.get("title")) != null) {
                task.setTaskName(taskTitle.toString());
            }
            Object parentGuid = json.get("parentGuid");
            if (taskTitle != null) {
                task.setParentGuid(parentGuid.toString());
            }
            if ((isFolder = json.get("isFolder")) != null) {
                task.setIsFolder(isFolder.equals(true));
            }
            if (task.getIsFolder()) {
                List<NrdlTask> childTasks = this.getTaskByFolderGuid(url, task.getTaskGuid());
                for (NrdlTask nrdlTask : childTasks) {
                    taskList.add(nrdlTask);
                }
                continue;
            }
            taskList.add(task);
        }
        return taskList;
    }

    public EtlExecuteInfo execute(String taskGuid, String param, String url, String userName, String passWord) {
        EtlExecuteInfo etlExecuteInfo = new EtlExecuteInfo();
        NrdlTaskExecutor.login(url, userName, passWord);
        try {
            HashMap<String, String> paras = new HashMap<String, String>();
            paras.put("params", "{}");
            String res = NrdlTaskExecutor.doPost("/api/jobs/remote/exec/com.jiuqi.bi.dataintegration/" + taskGuid, null, paras);
            JSONObject jsonObject = new JSONObject(res);
            if (jsonObject.has("error_code")) {
                etlExecuteInfo.setStatusCode(jsonObject.getInt("error_code"));
                etlExecuteInfo.setErrorMessage(jsonObject.getString("error_msg"));
                logger.error("\u9519\u8bef\u7801\uff1a" + jsonObject.getInt("error_code"));
                logger.error("\u9519\u8bef\u4fe1\u606f\uff1a" + jsonObject.getString("error_msg"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("\u6570\u636e\u96c6\u6210\u4efb\u52a1\u6267\u884c\u6210\u529f");
        return null;
    }

    public EtlExecuteInfo execute(String taskGuid, String param, String url, String userName, String passWord, Logger logger) {
        return this.execute(taskGuid, param, url, userName, passWord, (int status) -> logger.info("ETL \u6b63\u5728\u6267\u884c\uff0c\u72b6\u6001{}", (Object)status));
    }

    public static String doPost(String url, Map<String, String> queryParas, Map<String, String> boydParas) {
        SSLClient sSLClient;
        URL hostUrl = null;
        try {
            hostUrl = new URL(host);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String postUrl = null;
        try {
            postUrl = new URL(hostUrl, hostUrl.getFile() + url).toExternalForm();
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(postUrl);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            HtmlUtils.validateQueryStringPollution((String)token);
            HtmlUtils.validateQueryStringPollution((String)appid);
            HtmlUtils.validateQueryStringPollution((String)devid);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        uriBuilder.addParameter("token", token);
        uriBuilder.addParameter("src", appid);
        uriBuilder.addParameter("devid", devid);
        if (queryParas != null) {
            for (Map.Entry<String, String> entry : queryParas.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        ArrayList<BasicNameValuePair> formBody = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : boydParas.entrySet()) {
            formBody.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        Object var7_13 = null;
        try {
            sSLClient = new SSLClient();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        try {
            sSLClient.getParams().setParameter("http.connection.timeout", (Object)10000);
            sSLClient.getParams().setParameter("http.socket.timeout", (Object)60000);
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formBody, "UTF-8");
            httpPost.setEntity((HttpEntity)entity);
            httpPost.setHeader((Header)new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            httpPost.setHeader((Header)new BasicHeader("Accept", "text/plain;charset=utf-8"));
            HttpResponse response = sSLClient.execute((HttpUriRequest)httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String resultMsg = EntityUtils.toString((HttpEntity)response.getEntity(), (String)"UTF-8");
            switch (statusCode) {
                case 400: {
                    throw new HttpException("\u9519\u8bef\u8bf7\u6c42\uff1a" + resultMsg);
                }
                case 401: {
                    throw new HttpException("\u672a\u6388\u6743\uff1a" + resultMsg);
                }
                case 403: {
                    throw new HttpException("\u7981\u6b62\uff1a" + resultMsg);
                }
                case 404: {
                    throw new HttpException("\u672a\u627e\u5230\uff1a" + resultMsg);
                }
                case 500: {
                    throw new HttpException("\u670d\u52a1\u5668\u5185\u90e8\u9519\u8bef\uff1a" + resultMsg);
                }
            }
            if (statusCode >= 400) {
                throw new HttpException("\u5176\u4ed6\u9519\u8bef[" + statusCode + "]:" + resultMsg);
            }
            String string = resultMsg;
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
        catch (HttpException httpException) {
            throw new RuntimeException(httpException);
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new RuntimeException(uRISyntaxException);
        }
        catch (ClientProtocolException clientProtocolException) {
            throw new RuntimeException(clientProtocolException);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        finally {
            sSLClient.getConnectionManager().shutdown();
        }
    }

    public static String doGet(String url, Map<String, String> paras) {
        URL hostUrl = null;
        try {
            hostUrl = new URL(host);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String loginUrl = null;
        try {
            loginUrl = new URL(hostUrl, hostUrl.getFile() + url).toExternalForm();
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(loginUrl);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            HtmlUtils.validateQueryStringPollution((String)token);
            HtmlUtils.validateQueryStringPollution((String)appid);
            HtmlUtils.validateQueryStringPollution((String)devid);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        uriBuilder.addParameter("token", token);
        uriBuilder.addParameter("src", appid);
        uriBuilder.addParameter("devid", devid);
        if (paras != null) {
            for (String key : paras.keySet()) {
                uriBuilder.addParameter(key, paras.get(key));
            }
        }
        SSLClient client = null;
        try {
            client = new SSLClient();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            String resultMsg;
            client.getParams().setParameter("http.connection.timeout", (Object)10000);
            client.getParams().setParameter("http.socket.timeout", (Object)60000);
            String string = resultMsg = NrdlTaskExecutor.getHttp(uriBuilder, (HttpClient)client);
            return string;
        }
        catch (HttpException e) {
            throw new RuntimeException(e);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            client.getConnectionManager().shutdown();
        }
    }

    public static void login(String url, String userName, String password) {
        URL hostUrl = null;
        host = url;
        try {
            hostUrl = new URL(url);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String loginUrl = null;
        try {
            loginUrl = new URL(hostUrl, hostUrl.getFile() + "/api/auth/login/" + userName).toExternalForm();
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(loginUrl);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        uriBuilder.addParameter("pwd", password);
        uriBuilder.addParameter("src", appid);
        uriBuilder.addParameter("devid", devid);
        SSLClient client = null;
        try {
            client = new SSLClient();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            client.getParams().setParameter("http.connection.timeout", (Object)10000);
            client.getParams().setParameter("http.socket.timeout", (Object)60000);
            String resultMsg = NrdlTaskExecutor.getHttp(uriBuilder, (HttpClient)client);
            JSONObject loginJson = new JSONObject(resultMsg);
            token = loginJson.getString("token");
        }
        catch (HttpException e) {
            throw new RuntimeException(e);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            client.getConnectionManager().shutdown();
        }
    }

    private static String getHttp(URIBuilder uriBuilder, HttpClient client) throws URISyntaxException, IOException, HttpException {
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader((Header)new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        httpGet.setHeader((Header)new BasicHeader("Accept", "text/plain;charset=utf-8"));
        HttpResponse response = client.execute((HttpUriRequest)httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        String resultMsg = EntityUtils.toString((HttpEntity)response.getEntity(), (String)"UTF-8");
        switch (statusCode) {
            case 400: {
                throw new HttpException("\u9519\u8bef\u8bf7\u6c42\uff1a" + resultMsg);
            }
            case 401: {
                throw new HttpException("\u672a\u6388\u6743\uff1a" + resultMsg);
            }
            case 403: {
                throw new HttpException("\u7981\u6b62\uff1a" + resultMsg);
            }
            case 404: {
                throw new HttpException("\u672a\u627e\u5230\uff1a" + resultMsg);
            }
            case 500: {
                throw new HttpException("\u670d\u52a1\u5668\u5185\u90e8\u9519\u8bef\uff1a" + resultMsg);
            }
        }
        if (statusCode >= 400) {
            throw new HttpException("\u5176\u4ed6\u9519\u8bef[" + statusCode + "]:" + resultMsg);
        }
        return resultMsg;
    }

    public EtlExecuteInfo execute(String taskid, String param, String url, String userName, String passWord, EtlTaskStateCallback callback) {
        EtlExecuteInfo resule = new EtlExecuteInfo();
        String instanceGuid = null;
        NrdlTaskExecutor.login(url, userName, passWord);
        try {
            HashMap<String, String> paras = new HashMap<String, String>();
            paras.put("params", param);
            instanceGuid = NrdlTaskExecutor.doPost("/api/jobs/remote/exec/com.jiuqi.bi.dataintegration/" + taskid, null, paras).replace("\"", "");
            instanceGuid = instanceGuid.replace("instanceGuid:", "").replace("{", "").replace("}", "");
        }
        catch (Exception e) {
            e.printStackTrace();
            String[] errorMsg = new String[]{"\u6570\u636e\u96c6\u6210\u8c03\u7528\u5931\u8d25"};
            throw new EtlAsyncTaskErrorException("500", errorMsg);
        }
        String msg = null;
        try {
            msg = NrdlTaskExecutor.doGet("/api/jobs/remote/status/com.jiuqi.bi.dataintegration/" + instanceGuid, null);
        }
        catch (Exception e) {
            logger.error("\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25");
            String[] errorMsg = new String[]{"\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25"};
            throw new EtlAsyncTaskErrorException("500", errorMsg);
        }
        JSONObject json = new JSONObject(msg);
        if (json.has("error_code")) {
            logger.info("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25\uff01");
            resule.setErrorMessage(json.getString("error_msg"));
            resule.setStatusCode(500);
            return resule;
        }
        int calCount = 0;
        int status = json.getInt("state");
        while (status == 0 || status == -1) {
            long nanos = 2000000000L;
            if (++calCount > 100) {
                nanos = 10000000000L;
            } else if (calCount > 50) {
                nanos = 6000000000L;
            } else if (calCount > 10) {
                nanos = 4000000000L;
            }
            LockSupport.parkNanos(nanos);
            status = (Integer)new JSONObject(NrdlTaskExecutor.doGet("/api/jobs/remote/status/com.jiuqi.bi.dataintegration/" + instanceGuid, null)).get("state");
            callback.processTaskState(status);
        }
        if (status == -2) {
            logger.info("\u6570\u636e\u96c6\u6210\u4efb\u52a1\u6b63\u5728\u53d6\u6d88\uff01");
            resule.setStatusCode(-2);
            return resule;
        }
        callback.processTaskState(status);
        if (status == 1) {
            logger.info("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u6210\u529f\uff01");
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
            logger.info("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25\uff01");
            resule.setErrorMessage("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25");
        }
        json = new JSONObject(NrdlTaskExecutor.doGet("/api/jobs/remote/status/com.jiuqi.bi.dataintegration/" + instanceGuid, null));
        resule.setStatusCode(json.getInt("result"));
        resule.setResult(msg);
        return resule;
    }
}

