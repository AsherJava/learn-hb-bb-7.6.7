/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.oauth2.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlEncodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(UrlEncodeUtil.class);

    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    public static String decode(String url) {
        try {
            return URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }
}

