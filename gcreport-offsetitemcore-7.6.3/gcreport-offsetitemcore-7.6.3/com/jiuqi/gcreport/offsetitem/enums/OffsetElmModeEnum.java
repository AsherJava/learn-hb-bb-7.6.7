/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.offsetitem.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.i18n.LocaleContextHolder;

public enum OffsetElmModeEnum {
    AUTO_ITEM(0, "\u81ea\u52a8"),
    MANUAL_ITEM(1, "\u624b\u52a8"),
    INPUT_ITEM(2, "\u8f93\u5165"),
    JOURNAL_ITEM(3, "\u65e5\u8bb0\u8d26"),
    WRITE_OFF_ITEM(4, "\u7ba1\u7406\u51b2\u9500"),
    MANAGE_INPUT_ITEM(5, "\u7ba1\u7406\u8f93\u5165"),
    BATCH_MANUAL_ITEM(6, "\u6279\u91cf\u624b\u52a8"),
    RY_INPUT_ITEM(7, "\u4efb\u610f\u8f93\u5165"),
    INIT_ITEM(9, "\u5e74\u521d\u521d\u59cb\u5316");

    private final int value;
    private final String title;
    private final Map<String, Map<String, String>> localeKey2localeTitle = new ConcurrentHashMap<String, Map<String, String>>();

    private OffsetElmModeEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static String getElmModeTitle(Integer value) {
        for (Map.Entry<Integer, String> entry : OffsetElmModeEnum.getElemodeMap().entrySet()) {
            if (!value.equals(entry.getKey())) continue;
            return entry.getValue();
        }
        return "\u65e0\u6b64\u7c7b\u578b";
    }

    public static Integer codeOfTitle(String title) {
        for (OffsetElmModeEnum value : OffsetElmModeEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getValue();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static OffsetElmModeEnum value(Integer elmModeValue) {
        for (OffsetElmModeEnum elmModeEnum : OffsetElmModeEnum.values()) {
            if (!Integer.valueOf(elmModeEnum.getValue()).equals(elmModeValue)) continue;
            return elmModeEnum;
        }
        return null;
    }

    public static Map<Integer, String> getElemodeMap() {
        OffsetElmModeEnum[] values;
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (OffsetElmModeEnum value : values = OffsetElmModeEnum.values()) {
            map.put(value.getValue(), value.getTitle());
        }
        return map;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        String localeKey = "gc.calculate.adjustingentry.showoffset.elmModeEnum" + this.value;
        NpContext context = NpContextHolder.getContext();
        Locale locale = null != context ? context.getLocale() : LocaleContextHolder.getLocale();
        if (((Map)this.localeKey2localeTitle.getOrDefault(locale.getCountry(), new HashMap())).containsKey(localeKey)) {
            return this.localeKey2localeTitle.get(locale.getCountry()).get(localeKey);
        }
        String title = GcI18nUtil.getMessage((String)localeKey);
        if (StringUtils.isEmpty((String)title)) {
            title = this.title;
        }
        Map country2title = this.localeKey2localeTitle.getOrDefault(locale.getCountry(), new HashMap());
        country2title.put(localeKey, title);
        this.localeKey2localeTitle.put(locale.getCountry(), country2title);
        return title;
    }
}

