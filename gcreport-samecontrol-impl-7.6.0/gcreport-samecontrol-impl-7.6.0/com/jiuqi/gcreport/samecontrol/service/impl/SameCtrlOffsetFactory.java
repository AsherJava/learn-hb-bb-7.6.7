/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOffSetItemService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlBeginOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlEndOffSetItemServiceImpl;

public class SameCtrlOffsetFactory {
    private static final String BEGIN_OFFSETTAB = "beginOffsetTab";
    private static final String END_OFFSETPAGE = "endOffsetTab";

    public static SameCtrlOffSetItemService getSameCtrlOffsetItemService(String showTabType) {
        SameCtrlOffSetItemService offSetItemService = null;
        switch (showTabType) {
            case "beginOffsetTab": {
                offSetItemService = (SameCtrlOffSetItemService)SpringContextUtils.getBean(SameCtrlBeginOffSetItemServiceImpl.class);
                break;
            }
            case "endOffsetTab": {
                offSetItemService = (SameCtrlOffSetItemService)SpringContextUtils.getBean(SameCtrlEndOffSetItemServiceImpl.class);
                break;
            }
        }
        return offSetItemService;
    }
}

