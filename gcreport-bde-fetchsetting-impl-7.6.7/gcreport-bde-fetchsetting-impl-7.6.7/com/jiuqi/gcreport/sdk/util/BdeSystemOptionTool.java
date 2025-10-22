/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.sdk.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;

public class BdeSystemOptionTool {
    public static String getOptionValue(String optionKey) {
        INvwaSystemOptionService service = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        if (service != null) {
            return service.findValueById(optionKey);
        }
        return null;
    }
}

