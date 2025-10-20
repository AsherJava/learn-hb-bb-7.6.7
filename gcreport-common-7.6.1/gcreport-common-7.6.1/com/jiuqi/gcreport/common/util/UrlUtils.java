/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  org.apache.commons.codec.binary.Base64
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ObjectUtils;

public class UrlUtils {
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String data) throws Exception {
        try {
            String newData = Base64.encodeBase64String((byte[])data.getBytes(CHARSET));
            return URLEncoder.encode(newData, CHARSET);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desEncrypt(String data) throws Exception {
        try {
            byte[] encrypted1 = URLDecoder.decode(data, CHARSET).getBytes(CHARSET);
            return new String(Base64.decodeBase64((byte[])encrypted1), CHARSET);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getIp() {
        String realIp = DistributionManager.getInstance().getIp();
        if (!ObjectUtils.isEmpty(realIp)) {
            return realIp;
        }
        return "";
    }
}

