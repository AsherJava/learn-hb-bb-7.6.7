/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyTaskPoolEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;

public interface MergeTaskExecutor {
    public String getTaskType();

    public MergeTaskEO createTask(GcActionParamsVO var1);

    public Object buildAsyncTaskParam(GcActionParamsVO var1, String var2);

    public String publishTask(GcActionParamsVO var1, Object var2, OneKeyTaskPoolEnum var3);
}

