/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.gather.ActionType
 */
package com.jiuqi.nr.data.estimation.web.enumeration;

import com.jiuqi.nr.dataentry.gather.ActionType;

public enum DataEntryToolBarMenus {
    estimationGroupMenu("estimation-group-menu", "\u6d4b\u7b97\uff08what-if\uff09", "#icon16_DH_A_NW_gongnengfenzushouqi", "\u6d4b\u7b97", ActionType.GROUP),
    newEstimationMenu("new-estimation-scheme", "\u65b0\u7684\u6d4b\u7b97\u65b9\u6848", "#icon16_GJ_A_NW_fuzhi", "\u65b0\u5efa\u4e00\u4e2a\u6d4b\u7b97\u65b9\u6848", ActionType.BUTTON),
    oldEstimationMenu("old-estimation-scheme", "\u4e0a\u4e00\u6b21\u7684\u6d4b\u7b97\u65b9\u6848", "#icon16_GJ_A_NW_fuzhi", "\u7ee7\u7eed\u4e0a\u4e00\u6b21\u6d4b\u7b97", ActionType.BUTTON);

    public String code;
    public String title;
    public String icon;
    public String desc;
    public ActionType type;

    private DataEntryToolBarMenus(String code, String title, String icon, String desc, ActionType type) {
        this.code = code;
        this.title = title;
        this.icon = icon;
        this.desc = desc;
        this.type = type;
    }
}

