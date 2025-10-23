/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.model.ValueConvertMode
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.util.StringUtils;

public class QuerySystemOptionUtils {
    private static final int DEFAULT_WIDTH_130 = 130;
    private static final int DEFAULT_WIDTH_270 = 270;
    private static final boolean QUERY_DETAIL_RECODE = true;
    private static final boolean BOLD_SUM_NODE = false;
    private static final boolean ENABLE_FAVORITES = false;
    private static final boolean ENABLE_QUICK_TOOLBAR = false;
    private static INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);

    public static int getMainDimensionValueWidth() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "COLUMN_WIDTH_MAIN_DIMENSION");
        if (StringUtils.hasLength(value)) {
            return Integer.parseInt(value);
        }
        return 270;
    }

    public static int getDimensionValueWidth() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "COLUMN_WIDTH_DIMENSION");
        if (StringUtils.hasLength(value)) {
            return Integer.parseInt(value);
        }
        return 130;
    }

    public static int getZbValueWidth() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "COLUMN_WIDTH_ZB");
        if (StringUtils.hasLength(value)) {
            return Integer.parseInt(value);
        }
        return 130;
    }

    public static boolean getQueryDetailRecord() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "QUERY_DETAIL_RECODE");
        if (StringUtils.hasLength(value)) {
            return "1".equals(value);
        }
        return true;
    }

    public static ValueConvertMode getNullDisplayMode() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "NULL_Display_MODE");
        if (StringUtils.hasLength(value)) {
            return ValueConvertMode.valueOf((String)value);
        }
        return ValueConvertMode.NONE;
    }

    public static ValueConvertMode getZeroDisplayMode() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "ZERO_Display_MODE");
        if (StringUtils.hasLength(value)) {
            return ValueConvertMode.valueOf((String)value);
        }
        return ValueConvertMode.NONE;
    }

    public static boolean isBoldSumNode() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "BOLD_SUM_NODE");
        if (StringUtils.hasLength(value)) {
            return "1".equals(value);
        }
        return false;
    }

    public static boolean isEnableFavorites() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "ENABLE_FAVORITES");
        if (StringUtils.hasLength(value)) {
            return "1".equals(value);
        }
        return false;
    }

    public static boolean isEnableQuickToolbar() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "ENABLE_QUICK_TOOLBAR");
        if (StringUtils.hasLength(value)) {
            return "1".equals(value);
        }
        return false;
    }

    public static int getSimpleExportValue() {
        String value = nvwaSystemOptionService.get("NrZbQuerySystemOption", "ZBQUERY_SIMPLEEXPORT_VALUE");
        if (StringUtils.hasLength(value)) {
            return Integer.parseInt(value);
        }
        return 1000;
    }
}

