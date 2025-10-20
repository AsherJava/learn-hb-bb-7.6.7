/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.common.constant.ParamTypeEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.common.constant.ParamTypeEnum;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;

public class FilterRuleUtils {
    private static final String FN_TMPL_END_SCOPE = "%1$sZZ";

    public static FilterRule getFilterRule(String setting) {
        if (StringUtils.isEmpty((String)setting)) {
            setting = "";
        }
        if (setting.contains(":")) {
            String[] settingArr = setting.split(":");
            if (settingArr.length != 2) {
                throw new BusinessRuntimeException(String.format("\u89c4\u5219\u914d\u7f6e\u9519\u8bef,\u3010%1$s\u3011\u4e0d\u6ee1\u8db3\u8303\u56f4\u89c4\u5219\u8bbe\u7f6e\u683c\u5f0f", setting));
            }
            return new FilterRule(ParamTypeEnum.RANGE, settingArr);
        }
        if (setting.contains(",")) {
            String[] settingArr = setting.split(",");
            return new FilterRule(ParamTypeEnum.MULTIVALUED, settingArr);
        }
        return new FilterRule(ParamTypeEnum.SINGLE, new String[]{setting});
    }

    public static boolean matchVague(FilterRule filterRule, String dataCode) {
        switch (filterRule.getParamTypeEnum()) {
            case SINGLE: {
                return dataCode.startsWith(filterRule.getParamValues()[0]);
            }
            case MULTIVALUED: {
                for (String settingCode : filterRule.getParamValues()) {
                    if (!dataCode.startsWith(settingCode)) continue;
                    return true;
                }
                return false;
            }
            case RANGE: {
                String end = String.format(FN_TMPL_END_SCOPE, filterRule.getParamValues()[1]);
                return dataCode.compareTo(filterRule.getParamValues()[0]) >= 0 && dataCode.compareTo(end) <= 0;
            }
        }
        return false;
    }

    public static boolean matchEqual(FilterRule filterRule, String dataCode) {
        switch (filterRule.getParamTypeEnum()) {
            case SINGLE: {
                return dataCode.equals(filterRule.getParamValues()[0]);
            }
            case MULTIVALUED: {
                for (String settingCode : filterRule.getParamValues()) {
                    if (!dataCode.equals(settingCode)) continue;
                    return true;
                }
                return false;
            }
            case RANGE: {
                String end = String.format(FN_TMPL_END_SCOPE, filterRule.getParamValues()[1]);
                return dataCode.compareTo(filterRule.getParamValues()[0]) >= 0 && dataCode.compareTo(end) <= 0;
            }
        }
        return false;
    }
}

