/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim;

import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.common.base.util.StringUtils;

public class CustomFetchExecuteUtil {
    public static final String FN_SQL_TEMPLATE = "SELECT %1$S FROM %2$s T WHERE 1=1 %3$s %4$s";
    public static final String FN_CT_FIELD = "FETCH_EXE_CT_FIELD";
    public static final String EMPTY_VAL = "";
    public static final String FN_TREE_NODE_ROOT_KEY = "root";
    public static final String FN_TREE_NODE_ROOT_VAL = "-";

    public static String getFieldDefineInfo(ExecuteSettingVO setting) {
        if (setting == null) {
            return EMPTY_VAL;
        }
        StringBuffer info = new StringBuffer();
        if (!StringUtils.isEmpty((String)setting.getFieldDefineTitle())) {
            info.append("\u6307\u6807\uff1a").append("\u3010").append(setting.getFieldDefineTitle()).append("\u3011");
        }
        return info.toString();
    }
}

