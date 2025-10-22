/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.chart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URICheckUtils {
    public static Boolean checkURI(String uri) {
        if (!uri.startsWith("http")) {
            return false;
        }
        String regexProtocol = "file:///|gopher://|ftp://|ftps://|jar://|mailto://ssh2://|telent://|expect://";
        Pattern pPro = Pattern.compile(regexProtocol);
        Matcher mPro = pPro.matcher(uri);
        int notAllowCount = 0;
        while (mPro.find()) {
            ++notAllowCount;
        }
        if (notAllowCount > 0) {
            return false;
        }
        String regex = "http://|https://";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        int httpCount = 0;
        while (matcher.find()) {
            ++httpCount;
        }
        if (httpCount > 1) {
            return false;
        }
        return true;
    }
}

