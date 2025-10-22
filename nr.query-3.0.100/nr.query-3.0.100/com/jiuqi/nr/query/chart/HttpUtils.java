/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.apache.http.client.utils.URIBuilder
 *  org.json.JSONObject
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package com.jiuqi.nr.query.chart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.query.chart.URICheckUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String splitchart = ";";

    public static JSONObject doRequest(String url, String param, String method) {
        HttpURLConnection httpURLConnection = null;
        String res = "";
        JSONObject jsonResult = new JSONObject();
        HttpStatus status = HttpStatus.OK;
        try {
            Throwable throwable;
            URL realUrl = new URL(url);
            URI uri = new URI(url);
            URIBuilder uriBuilder = new URIBuilder(uri);
            String uriStr = uriBuilder.toString();
            Boolean valued = URICheckUtils.checkURI(uriStr);
            if (!valued.booleanValue()) {
                throw new IllegalArgumentException("URL\u975e\u6cd5\uff0c\u8bf7\u68c0\u67e5\uff01");
            }
            realUrl = new URL(uriStr);
            httpURLConnection = (HttpURLConnection)realUrl.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.setReadTimeout(10000000);
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            if (HttpMethod.POST.name().equals(method)) {
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpURLConnection.setDoOutput(true);
            }
            httpURLConnection.connect();
            if (param != null) {
                try {
                    throwable = null;
                    try (DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());){
                        out.write(param.getBytes("UTF-8"));
                        out.flush();
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            try {
                throwable = null;
                try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                     BufferedReader in = new BufferedReader(inputStreamReader);){
                    if (httpURLConnection.getResponseCode() == 200) {
                        StringBuffer content = new StringBuffer();
                        String tempStr = null;
                        while ((tempStr = in.readLine()) != null) {
                            content.append(tempStr);
                        }
                        res = content.toString();
                        jsonResult.put("status", status.value());
                        jsonResult.put("res", (Object)res);
                        logger.info(res);
                    } else {
                        jsonResult.put("status", httpURLConnection.getResponseCode());
                    }
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        catch (Exception e) {
            jsonResult.put("status", (Object)HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
            jsonResult.put("errorMsg", (Object)e.getMessage());
            logger.error("\u53d1\u9001\u8bf7\u6c42\u5f02\u5e38!url ={}", (Object)url);
            logger.error(e.getMessage(), e);
        }
        return jsonResult;
    }

    public static String doRequestForString(String url, String param, String method) {
        HttpURLConnection httpURLConnection = null;
        String res = "";
        HttpStatus status = HttpStatus.OK;
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            URL realUrl = new URL(url);
            URI uri = new URI(url);
            URIBuilder uriBuilder = new URIBuilder(uri);
            String uriStr = uriBuilder.toString();
            Boolean valued = URICheckUtils.checkURI(uriStr);
            if (!valued.booleanValue()) {
                throw new IllegalArgumentException("URL\u975e\u6cd5\uff0c\u8bf7\u68c0\u67e5\uff01");
            }
            realUrl = new URL(uriStr);
            httpURLConnection = (HttpURLConnection)realUrl.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.setReadTimeout(10000000);
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            if (HttpMethod.POST.name().equals(method)) {
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpURLConnection.setDoOutput(true);
            }
            httpURLConnection.connect();
            if (param != null) {
                try (DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());){
                    out.write(param.getBytes("UTF-8"));
                    out.flush();
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            int responseCode = httpURLConnection.getResponseCode();
            switch (responseCode) {
                case 200: {
                    StringBuffer content = new StringBuffer();
                    String tempStr = null;
                    try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                         BufferedReader in = new BufferedReader(inputStreamReader);){
                        while ((tempStr = in.readLine()) != null) {
                            content.append(tempStr);
                        }
                        res = content.toString();
                        logger.info(res);
                    }
                    catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                    return res;
                }
            }
            Object conn = null;
            byte[] buff = new byte[1024];
            int len = 0;
            is = httpURLConnection.getErrorStream();
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            String responseMessage = os.toString("UTF-8");
            JSONObject error = new JSONObject(responseMessage);
            responseMessage = String.valueOf(error.get("error_msg"));
            String errorMsg = "\u8bf7\u6c42BI\u670d\u52a1[".concat(url).concat("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0[".concat(responseMessage).concat("]"));
            logger.error("BI\u5206\u6790\u8868\u5bfc\u51fa\u5f02\u5e38\uff0curl\uff1a" + errorMsg);
            return errorMsg;
        }
        catch (Exception e) {
            logger.error("\u53d1\u9001\u8bf7\u6c42\u5f02\u5e38!url ={}", (Object)url);
            logger.error(e.getMessage(), e);
            return res;
        }
    }

    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            Object t = MAPPER.readValue(jsonData, beanType);
            return (T)t;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, new Class[]{beanType});
        try {
            List list = (List)MAPPER.readValue(jsonData, javaType);
            return list;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String getCurrentId(String groupId) {
        String[] curGroupId = groupId.split(splitchart);
        int length = curGroupId.length;
        if (length == 0) {
            return "";
        }
        return curGroupId[length - 1];
    }
}

