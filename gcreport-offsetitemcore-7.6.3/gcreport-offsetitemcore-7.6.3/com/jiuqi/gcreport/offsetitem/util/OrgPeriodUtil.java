/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.offsetitem.util;

import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Map;
import org.springframework.util.StringUtils;

public class OrgPeriodUtil {
    public static String getQueryOrgPeriod(String defaultPeriod) {
        if (StringUtils.isEmpty(defaultPeriod)) {
            return "";
        }
        if (defaultPeriod.endsWith("00")) {
            if (defaultPeriod.contains("N")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "1";
            }
            if (defaultPeriod.contains("H")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "2";
            }
            if (defaultPeriod.contains("J")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "4";
            }
            if (defaultPeriod.contains("Y")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "12";
            }
            if (defaultPeriod.contains("X")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "36";
            }
            if (defaultPeriod.contains("Z")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "53";
            }
        }
        return defaultPeriod;
    }

    public static String getUnitTitle(Map<String, String> unitCode2TitleCache, String unitCode, GcOrgCenterService tool) {
        if (StringUtils.isEmpty(unitCode)) {
            return "";
        }
        String unitTitle = unitCode2TitleCache.get(unitCode);
        if (null == unitTitle) {
            GcOrgCacheVO cacheVO = tool.getOrgByCode(unitCode);
            unitTitle = cacheVO == null ? "" : cacheVO.getTitle();
            unitCode2TitleCache.put(unitCode, unitTitle);
        }
        return unitTitle;
    }
}

