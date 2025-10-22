/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.itreebase.collection;

import com.jiuqi.bi.util.StringUtils;
import java.util.List;

public interface IFilterStringListSortParam {
    public static final String UP_MOVE = "before";
    public static final String DOWN_MOVE = "after";

    public String getOperate();

    public String getBeforeNode();

    public String getAfterNode();

    public List<String> getRange();

    public static boolean isValidParam(IFilterStringListSortParam param) {
        List<String> range = param.getRange();
        return null != range && range.size() == 2 && StringUtils.isNotEmpty((String)param.getOperate());
    }
}

