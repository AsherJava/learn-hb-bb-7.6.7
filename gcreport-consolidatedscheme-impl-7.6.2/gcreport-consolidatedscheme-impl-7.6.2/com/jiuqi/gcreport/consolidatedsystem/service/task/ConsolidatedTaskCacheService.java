/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.service.task;

import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface ConsolidatedTaskCacheService {
    @Deprecated
    public ConsolidatedTaskVO getTaskBySchemeId(String var1, @NotNull String var2);

    public ConsolidatedTaskVO getTaskByTaskId(String var1, @NotNull String var2);

    public String getSystemIdByTaskId(String var1, @NotNull String var2);

    public List<ConsolidatedTaskVO> listConsolidatedTaskBySystemIdAndPeriod(String var1, String var2);
}

