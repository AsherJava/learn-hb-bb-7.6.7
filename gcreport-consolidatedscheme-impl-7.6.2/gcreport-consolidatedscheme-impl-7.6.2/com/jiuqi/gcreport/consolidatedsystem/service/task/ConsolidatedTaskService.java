/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.service.task;

import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

public interface ConsolidatedTaskService {
    public List<ConsolidatedTaskVO> getConsolidatedTasks(String var1);

    public void bindConsolidatedTask(ConsolidatedTaskVO var1);

    public void unbindConsolidatedTask(String[] var1);

    public ConsolidatedSystemEO getConsolidatedSystemBySchemeId(String var1, @NotNull String var2);

    public String getConsolidatedSystemIdBySchemeId(String var1, @NotNull String var2);

    public String getSystemIdByTaskIdAndPeriodStr(String var1, @NotNull String var2);

    public Set<String> getRelateTaskIdsByTaskId(String var1, @NotNull String var2);

    public ConsolidatedTaskVO getTaskBySchemeId(String var1, @NotNull String var2);

    public boolean isCorporate(String var1, @NotNull String var2, String var3);

    public List<String> getAllBoundTasks();

    public List<ConsolidatedTaskVO> getAllBoundTaskVOs();

    public void unbindBySystemId(String var1);

    public void bindConsolidatedTask(List<ConsolidatedTaskVO> var1);

    public List<ConsolidatedTaskVO> getConsolidatedTaskByTaskId(String var1);

    public List<String> getRelevancySystemsBySchemeIds(List<String> var1);

    public void exchangeSort(String var1, int var2);

    public List<ConsolidatedTaskVO> getAllDataCollectorScheme();

    public List<FormSchemeDefine> listBoundSchemeVos(String var1) throws Exception;

    public List<String> getRelevancySystemsInputSchemeIds(List<String> var1);

    public ConsolidatedTaskVO getTaskByTaskKeyAndPeriodStr(String var1, @NotNull String var2);

    public List<ConsolidatedTaskVO> listConsolidatedTaskBySystemIdAndPeriod(String var1, String var2);

    public boolean isEntryScheme(String var1, @NotNull String var2);

    public boolean managementCanCalc(String var1, @NotNull String var2, String var3);

    public String getSystemIdBySchemeId(String var1, String var2);

    public String getSystemIdByTaskId(String var1, @NotNull String var2);

    public List<ConsolidatedTaskVO> getTaskVOByTaskKey(String var1);

    public List<ConsolidatedTaskVO> getConsolidatedTasksBySchemeId(String var1);

    public Set<String> getRelevancySchemeKeys(List<String> var1);

    public List<String> listOrgTypeByTaskId(String var1);
}

