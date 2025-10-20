/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.webserviceclient.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.webserviceclient.utils.TrustAllTrustManager;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.period.PeriodWrapper;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebserviceClientUtil {
    private static Logger logger = LoggerFactory.getLogger(WebserviceClientUtil.class);

    public static void initWSClientParam(WebserviceClientParam wsClientParam) {
        GcBaseDataService baseDataService = (GcBaseDataService)SpringContextUtils.getBean(GcBaseDataService.class);
        GcBaseData baseData = baseDataService.queryBasedataByCode("MD_WS_CLIENT", wsClientParam.getWsClientBaseDataCode());
        if (baseData == null) {
            throw new BusinessRuntimeException("\u672a\u67e5\u8be2\u5230\u57fa\u7840\u6570\u636e\u3010MD_WS_CLIENT\u3011");
        }
        Object token = baseData.getFieldVal("TOKEN");
        wsClientParam.setToken(token == null ? "" : String.valueOf(token));
        String url = (String)baseData.getFieldVal("URL");
        wsClientParam.setUrl(url);
        String paramsXmlText = (String)baseData.getFieldVal("PARAMSXMLTEXT");
        wsClientParam.setParamsXmlText(paramsXmlText);
        Object zjkTableName = baseData.getFieldVal("ZJKTABLENAME");
        wsClientParam.setZjkTableName(zjkTableName == null ? "" : String.valueOf(zjkTableName));
        Object userName = baseData.getFieldVal("USERNAME");
        wsClientParam.setUserName(userName == null ? "" : String.valueOf(userName));
        Object password = baseData.getFieldVal("PWD");
        wsClientParam.setPassword(password == null ? "" : String.valueOf(password));
    }

    public static PeriodWrapper getCurrentPeriod(int periodType) {
        Calendar date = Calendar.getInstance();
        int year = date.get(1);
        int month = date.get(2);
        int week = date.get(3);
        int day = date.get(6);
        int dayOfMonth = date.get(5);
        date.get(7);
        int acctYear = year;
        int acctPriod = 1;
        if (1 == periodType) {
            acctPriod = 1;
        } else if (2 == periodType) {
            acctPriod = (month + 1) / 7 + 1;
        } else if (3 == periodType) {
            acctPriod = (month + 1) / 4 + 1;
        } else if (4 == periodType) {
            acctPriod = month + 1;
        } else if (5 == periodType) {
            acctPriod = month * 3 + (dayOfMonth - 1) / 10 + 1;
        } else if (6 == periodType) {
            acctPriod = day;
        } else if (7 == periodType) {
            acctPriod = week;
        } else if (8 == periodType) {
            // empty if block
        }
        return new PeriodWrapper(acctYear, periodType, acctPriod);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean sendRequestToWS(String xmlText, WebserviceClientParam clientParam, List<String> logs) {
        OutputStream outputStream = null;
        BufferedReader responseBuffer = null;
        HttpURLConnection httpConnection = null;
        HostnameVerifier hv = WebserviceClientUtil.getHostnameVerifier();
        try {
            String line;
            logger.info("\u6570\u636e\u4f20\u8f93\u8ba1\u5212xml\uff1a{}", (Object)xmlText);
            logger.info("\u6570\u636e\u4f20\u8f93\u8ba1\u5212url\uff1a{}", (Object)clientParam.getUrl());
            TrustManager[] trustAllCerts = new TrustManager[]{new TrustAllTrustManager()};
            SSLContext sc = SSLContext.getInstance("SSL");
            SSLSessionContext sslsc = sc.getServerSessionContext();
            sslsc.setSessionTimeout(0);
            sc.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            URL restServiceURL = new URL(clientParam.getUrl());
            httpConnection = (HttpURLConnection)restServiceURL.openConnection();
            httpConnection.setConnectTimeout(30000);
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            httpConnection.setRequestProperty("SOAPAction", "http://sap.com/xi/WebService/soap1.1");
            httpConnection.setUseCaches(false);
            if (!StringUtils.isEmpty((String)clientParam.getUserName()) && !StringUtils.isEmpty((String)clientParam.getPassword())) {
                String userPassword = clientParam.getUserName() + ":" + clientParam.getPassword();
                httpConnection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPassword.getBytes()));
            }
            outputStream = httpConnection.getOutputStream();
            outputStream.write(xmlText.getBytes());
            outputStream.flush();
            responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "utf-8"));
            StringBuffer output = new StringBuffer();
            while ((line = responseBuffer.readLine()) != null) {
                output.append(line);
            }
            logger.info("\u6570\u636e\u4f20\u8f93\u8ba1\u5212\u8fd4\u56de\u7ed3\u679c\uff1a{}", (Object)output);
            WebserviceClientUtil.close(outputStream);
        }
        catch (Exception e) {
            String msg = "\u6570\u636e\u4f20\u8f93\u8bf7\u6c42\u53d1\u9001\u5931\u8d25\uff1a" + e.getMessage();
            logger.error(msg, e);
            logs.add(msg);
            boolean bl = false;
            return bl;
        }
        finally {
            WebserviceClientUtil.close(outputStream);
            WebserviceClientUtil.close(responseBuffer);
            httpConnection.disconnect();
        }
        WebserviceClientUtil.close(responseBuffer);
        httpConnection.disconnect();
        return true;
    }

    private static HostnameVerifier getHostnameVerifier() {
        return (urlHostName, session) -> true;
    }

    private static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void createAsyncTask(AsyncTaskMonitor asyncTaskMonitor, String title, List<String> logs) {
        if (asyncTaskMonitor == null) {
            return;
        }
        logs.add(title);
        asyncTaskMonitor.progressAndMessage(0.0, title);
        ((AsyncTaskDao)SpringContextUtils.getBean(AsyncTaskDao.class)).updateResultAndDetail(TaskState.PROCESSING, asyncTaskMonitor.getTaskId(), title, logs);
    }

    public static void modifyAsyncTaskState(AsyncTaskMonitor asyncTaskMonitor, double progress, String result, List<String> logs) {
        if (asyncTaskMonitor == null) {
            return;
        }
        logs.add(result);
        asyncTaskMonitor.progressAndMessage(progress, result);
    }
}

