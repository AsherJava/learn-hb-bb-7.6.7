/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;

public interface GcCenterTask {
    public ReturnObject doTask(GcActionParamsVO var1);

    default public ReturnObject paramCheck(GcActionParamsVO paramsVO) {
        return new ReturnObject(true);
    }
}

