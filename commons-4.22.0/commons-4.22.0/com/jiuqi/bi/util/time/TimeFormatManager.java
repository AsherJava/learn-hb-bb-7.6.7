/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.IFormatProvider;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimeFormatManager {
    private TimeFormatManager() {
    }

    public static void registerProvider(IFormatProvider provider) {
        FormatCache.formats.put(provider.name().toUpperCase(), provider);
    }

    public static void unregisterProvider(String providerName) {
        FormatCache.formats.remove(providerName.toUpperCase());
    }

    public static IFormatProvider.Parser createParser(TimeFieldInfo field, String name, String param, Locale locale) throws TimeCalcException {
        IFormatProvider provider = (IFormatProvider)FormatCache.formats.get(name.toUpperCase());
        if (provider == null) {
            throw new TimeCalcException("\u81ea\u5b9a\u4e49\u65f6\u671f\u683c\u5f0f\u63d0\u4f9b\u5668\u4e0d\u5b58\u5728\uff1a" + field.getFormat());
        }
        IFormatProvider.Parser parser = provider.createParser(field, param, locale);
        if (parser == null) {
            throw new TimeCalcException("\u81ea\u5b9a\u4e49\u65f6\u671f\u683c\u5f0f\u4e0d\u652f\u6301\u89e3\u6790\u64cd\u4f5c\uff1a" + field.getFormat());
        }
        return parser;
    }

    public static IFormatProvider.Formatter createFormatter(TimeFieldInfo field, String name, String param, Locale locale) throws TimeCalcException {
        IFormatProvider provider = (IFormatProvider)FormatCache.formats.get(name.toUpperCase());
        if (provider == null) {
            throw new TimeCalcException("\u81ea\u5b9a\u4e49\u65f6\u671f\u683c\u5f0f\u63d0\u4f9b\u5668\u4e0d\u5b58\u5728\uff1a" + field.getFormat());
        }
        IFormatProvider.Formatter formatter = provider.createFormatter(field, param, locale);
        if (formatter == null) {
            throw new TimeCalcException("\u81ea\u5b9a\u4e49\u65f6\u671f\u683c\u5f0f\u4e0d\u652f\u6301\u683c\u5f0f\u5316\u64cd\u4f5c\uff1a" + field.getFormat());
        }
        return formatter;
    }

    public static String[] parsePattern(String pattern) {
        String param;
        String name;
        if (pattern == null || !pattern.startsWith("${") || !pattern.endsWith("}")) {
            return null;
        }
        int p = pattern.indexOf(58, 2);
        if (p < 0) {
            name = pattern.substring(2, pattern.length() - 3);
            param = null;
        } else {
            name = pattern.substring(2, p);
            param = pattern.substring(p + 1, pattern.length() - 1);
        }
        return new String[]{name, param};
    }

    public static IFormatProvider.Parser tryCreateParser(TimeFieldInfo field, Locale locale) throws TimeCalcException {
        String[] pattern = TimeFormatManager.parsePattern(field.getFormat());
        if (pattern == null) {
            return null;
        }
        return TimeFormatManager.createParser(field, pattern[0], pattern[1], locale);
    }

    public static IFormatProvider.Formatter tryCreateFormatter(TimeFieldInfo field, Locale locale) throws TimeCalcException {
        String[] pattern = TimeFormatManager.parsePattern(field.getFormat());
        if (pattern == null) {
            return null;
        }
        return TimeFormatManager.createFormatter(field, pattern[0], pattern[1], locale);
    }

    private static final class FormatCache {
        private static final Map<String, IFormatProvider> formats = new ConcurrentHashMap<String, IFormatProvider>();

        private FormatCache() {
        }
    }
}

