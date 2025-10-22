/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import java.util.concurrent.Future;

public interface GcOnekeyMergeTaskService {
    public ReturnObject doTask(GcActionParamsVO var1, TaskTypeEnum var2);

    public Future<Boolean> doTaskAsync(GcActionParamsVO var1, TaskTypeEnum var2);
}

