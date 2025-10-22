/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.controller;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.AssTaskFormSchemeInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckContext;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckResult;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpUnitInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckVersionObjectInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.PeriodSchemeInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.dto.EntityCheckUpDTO;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public interface IEntityCheckController {
    public String entityCheckUps(EntityCheckUpDTO var1, CheckConfigurationContent var2, Map<Integer, Map<String, EntityCheckUpRecord>> var3) throws Exception;

    public Map<Integer, Map<String, EntityCheckUpRecord>> getRecord(String var1, String var2, String var3, String var4, String var5, String var6, CheckConfigurationContent var7);

    public EntityCheckResult getEntityCheckResult(Map<Integer, Map<String, EntityCheckUpRecord>> var1, CheckConfigurationContent var2, String var3);

    public CheckConfigurationContent getCheckConfigurationContent(String var1, String var2, String var3, String var4);

    public String getContrastUnitScopKeyByCurrentScopKey(EntityCheckVersionObjectInfo var1, String var2, String var3, String var4, int var5, String var6, String var7) throws Exception;

    public String EntityCheckUp(String var1, String var2, String var3, String var4, String var5, boolean var6, String var7, String var8, String var9, JtableContext var10, String var11) throws Exception;

    public boolean insertEntityCheckUp(EntityCheckContext var1) throws Exception;

    public List<EntityCheckUpUnitInfo> queryEntityCheckUp(EntityCheckContext var1);

    public boolean updateBatchToFMDM(EntityCheckContext var1);

    public String GetAuditResult(AsyncTaskMonitor var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, JtableContext var9) throws Exception;

    public String getRelationTaskJsonStr(String var1) throws Exception;

    public String getRelationTaskToFromSchemeJsonStr(String var1, String var2, String var3) throws Exception;

    public int getRelationTaskCount(String var1);

    public String getRelationTaskTitle(String var1, String var2, String var3) throws Exception;

    public String eneityDataCheckResult(String var1);

    public String getMasterEntityKey(String var1);

    public int getCurrentUnitCount(String var1, String var2);

    public List<PeriodSchemeInfo> querySchemePeriodMapByTask(String var1) throws Exception;

    public AssTaskFormSchemeInfo querySchemePeriodMapByTaskAndFormSchemePeriod(String var1, String var2, String var3) throws Exception;

    public List<EnumStructure> getJSYYSlectData(String var1, String var2, String var3, String var4);
}

