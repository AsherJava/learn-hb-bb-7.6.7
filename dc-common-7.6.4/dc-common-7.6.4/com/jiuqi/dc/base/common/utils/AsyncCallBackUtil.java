/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.common.intf.ICallBack;
import com.jiuqi.dc.base.common.intf.ICallBackService;

public class AsyncCallBackUtil {
    public static void asyncCall(ICallBack action) {
        ICallBackService callBackService = (ICallBackService)SpringContextUtils.getBean(ICallBackService.class);
        callBackService.asyncCallBack(action);
    }
}

