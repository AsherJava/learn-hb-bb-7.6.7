/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.authz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthzUtil {
    public static final Logger log = LoggerFactory.getLogger(AuthzUtil.class);

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !AuthzUtil.isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen = AuthzUtil.length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (Character.isWhitespace(cs.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isValidAddress(String authzCenterAddress) {
        return !AuthzUtil.isBlank(authzCenterAddress) && authzCenterAddress.contains("http") && authzCenterAddress.contains(":");
    }

    public static String getFirstAddress(String authzCenterAddress) {
        List<String> allKmsAddress = AuthzUtil.getAllKmsAddress(authzCenterAddress);
        if (allKmsAddress.size() == 0) {
            return "";
        }
        return allKmsAddress.get(0);
    }

    public static List<String> getAllKmsAddress(String authzCenterAddress) {
        if (null == authzCenterAddress || "".equals(authzCenterAddress.trim())) {
            return new ArrayList<String>(0);
        }
        ArrayList<String> list = null;
        if (!authzCenterAddress.contains(",")) {
            list = new ArrayList<String>(1);
            list.add(authzCenterAddress.trim());
            return list;
        }
        String[] addressSplit = authzCenterAddress.split(",");
        list = new ArrayList(addressSplit.length);
        for (String address : addressSplit) {
            list.add(address.trim());
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String doGet(String httpUrl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        }
        catch (MalformedURLException e) {
            log.debug("doGet MalformedURLException\uff0c\u5165\u53c2\uff1a{}", (Object)httpUrl, (Object)e);
        }
        catch (IOException e) {
            log.debug("doGet IOException\uff0c\u5165\u53c2\uff1a{}", (Object)httpUrl, (Object)e);
        }
        finally {
            if (null != br) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    log.debug("br.close()IO\u5f02\u5e38", e);
                }
            }
            if (null != is) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    log.debug("is.close()IO\u5f02\u5e38", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private static String getString(int t) {
        String m = "";
        m = t > 0 ? (t < 10 ? "0" + t : t + "") : "00";
        return m;
    }

    public static String format(int t) {
        if (t < 60000) {
            return t % 60000 / 1000 + "\u79d2";
        }
        if (t >= 60000 && t < 3600000) {
            return AuthzUtil.getString(t % 3600000 / 60000) + ":" + AuthzUtil.getString(t % 60000 / 1000);
        }
        return AuthzUtil.getString(t / 3600000) + ":" + AuthzUtil.getString(t % 3600000 / 60000) + ":" + AuthzUtil.getString(t % 60000 / 1000);
    }
}

