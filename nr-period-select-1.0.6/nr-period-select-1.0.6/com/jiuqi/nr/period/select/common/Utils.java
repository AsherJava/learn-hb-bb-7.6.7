/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.common;

import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.KeyType;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.vo.ParamObj;

public class Utils {
    public static KeyType getKeyType(ParamObj paramObj) {
        if (StringUtils.isNotEmpty((String)paramObj.getDataScheme())) {
            return KeyType.DATASCHEME;
        }
        if (StringUtils.isNotEmpty((String)paramObj.getTaskId())) {
            return KeyType.TASK;
        }
        if (StringUtils.isNotEmpty((String)paramObj.getFormScheme())) {
            return KeyType.FORMSCHEME;
        }
        return null;
    }

    public static boolean isDefaultTitle(IPeriodRow iPeriodRow) {
        return iPeriodRow.getTitle().equals(PeriodUtils.getDateStrFromPeriod((String)iPeriodRow.getCode()));
    }

    public static void setTitle(IPeriodRow iPeriodRow, Page page, String showTitle) {
        if (!Utils.isDefaultTitle(iPeriodRow)) {
            page.setTitle(iPeriodRow.getTitle());
        } else {
            page.setTitle(showTitle);
        }
    }
}

