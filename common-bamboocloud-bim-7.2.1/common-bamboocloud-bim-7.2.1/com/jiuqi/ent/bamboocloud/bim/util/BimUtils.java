/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.banboocloud.Codec.BamboocloudFacade
 *  javax.servlet.http.HttpServletRequest
 */
package com.jiuqi.ent.bamboocloud.bim.util;

import com.banboocloud.Codec.BamboocloudFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class BimUtils {
    private static final Logger logger = LoggerFactory.getLogger(BimUtils.class);

    public static String transEncode(String plainText, String key, String algorithm) {
        return BamboocloudFacade.encrypt((String)plainText, (String)key, (String)algorithm);
    }

    public static String transDecode(String ciphertext, String key, String algorithm) {
        Assert.hasText(key, "\u540c\u6b65\u5bc6\u94a5key\u672a\u914d\u7f6e\uff0c\u8bf7\u8054\u7cfb\u4e1a\u52a1\u65b9\u914d\u7f6e");
        return BamboocloudFacade.decrypt((String)ciphertext, (String)key, (String)algorithm);
    }

    public static boolean verifySignature(Map<String, Object> params, String algorithm) {
        String verifyStr = BimUtils.getVerifyPlaintext(params);
        String signature = params.get("signature").toString();
        logger.debug("\u7b7e\u540d\u4e32 --:{}  \u5f85\u9a8c\u8bc1\u7b7e\u540d\u4e32 --:{}", (Object)signature, (Object)verifyStr);
        Boolean verify = BamboocloudFacade.verify((String)signature, (String)verifyStr, (String)algorithm);
        if (verify != null) {
            return verify;
        }
        return false;
    }

    private static String getVerifyPlaintext(Map<String, Object> params) {
        TreeMap<String, Object> verifyMap = new TreeMap<String, Object>(params);
        StringBuilder sb = new StringBuilder();
        for (String key : verifyMap.keySet()) {
            if ("signature".equals(key)) continue;
            sb.append(key).append("=").append(verifyMap.get(key)).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getParamsSignature(Map<String, Object> params, String algorithm) {
        return BamboocloudFacade.signature((String)BimUtils.getVerifyPlaintext(params), (String)algorithm);
    }

    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = request.getReader();){
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        }
        catch (IOException e) {
            logger.error("request\u4e2d\u8bfb\u53d6\u8bf7\u6c42\u4f53\u62a5\u9519", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}

