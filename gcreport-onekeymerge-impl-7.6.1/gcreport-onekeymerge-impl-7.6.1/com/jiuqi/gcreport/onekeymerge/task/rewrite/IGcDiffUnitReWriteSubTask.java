/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite;

import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.List;

public interface IGcDiffUnitReWriteSubTask {
    public String getName();

    public ReturnObject doTask(GcActionParamsVO var1, List<String> var2, List<String> var3, TaskLog var4);
}

