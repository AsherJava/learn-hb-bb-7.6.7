/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.apache.commons.collections4.map.HashedMap
 */
package com.jiuqi.bi.text;

import com.jiuqi.bi.util.StringUtils;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;

public class DateFormatTransfer {
    private final String pattern;
    private final Map<String, IPatternTransfer> map;

    private DateFormatTransfer(String pattern) {
        this.pattern = pattern;
        this.map = new HashedMap();
        this.map.put(PatternTransfer_EN.locale.getLanguage(), new PatternTransfer_EN());
        this.map.put(PatternTransfer_ZH.locale.getLanguage(), new PatternTransfer_ZH());
    }

    public static String transfer(String pattern, String lang) {
        Locale locale = Locale.getDefault();
        if (StringUtils.isNotEmpty((String)lang)) {
            locale = Locale.forLanguageTag(lang);
        }
        return DateFormatTransfer.transfer(pattern, locale);
    }

    public static String transfer(String pattern, Locale locale) {
        if (StringUtils.isEmpty((String)pattern)) {
            return pattern;
        }
        return new DateFormatTransfer(pattern).transfer(locale);
    }

    public static Locale getLocale4Date(Locale locale) {
        return new DateFormatTransfer(null)._getLocale4Date(locale);
    }

    private String transfer(Locale locale) {
        IPatternTransfer datePatternTransfer = this.map.get(locale.getLanguage());
        if (datePatternTransfer == null) {
            return this.transfer(Locale.ENGLISH);
        }
        return datePatternTransfer.transfer(this.pattern);
    }

    private Locale _getLocale4Date(Locale locale) {
        IPatternTransfer datePatternTransfer = this.map.get(locale.getLanguage());
        if (datePatternTransfer == null) {
            return DateFormatTransfer.getLocale4Date(Locale.ENGLISH);
        }
        return datePatternTransfer.getLocale4Date();
    }

    static class PatternTransfer_EN
    implements IPatternTransfer {
        static final Locale locale = Locale.ENGLISH;

        PatternTransfer_EN() {
        }

        @Override
        public Locale getLocale4Date() {
            return Locale.ENGLISH;
        }

        @Override
        public String transfer(String pattern) {
            pattern = pattern.replaceAll("\uff08[^\uff08\uff09]*\uff09", "");
            pattern = pattern.replaceAll("\\([^()]*\\)", "");
            StringBuilder buffer = new StringBuilder();
            if (this.hasChar(pattern, 'B')) {
                buffer.append("B ");
            }
            if (this.hasChar(pattern, 'Q')) {
                buffer.append("QQQ ");
            }
            if (this.hasChar(pattern, 'X')) {
                buffer.append("XXX ");
            }
            if (this.hasChar(pattern, 'w')) {
                buffer.append("'W'ww ");
            }
            if (this.hasChar(pattern, 'M')) {
                buffer.append("MMM ");
            }
            if (this.hasChar(pattern, 'd')) {
                buffer.append("dd ");
            }
            if (this.hasChar(pattern, 'y')) {
                buffer.append("yyyy ");
            }
            return buffer.toString().trim();
        }

        private boolean hasChar(String pattern, char ch) {
            return pattern.indexOf(ch) >= 0;
        }
    }

    static class PatternTransfer_ZH
    implements IPatternTransfer {
        static final Locale locale = Locale.CHINESE;

        PatternTransfer_ZH() {
        }

        @Override
        public Locale getLocale4Date() {
            return locale;
        }

        @Override
        public String transfer(String pattern) {
            return pattern;
        }
    }

    static interface IPatternTransfer {
        public Locale getLocale4Date();

        public String transfer(String var1);
    }
}

