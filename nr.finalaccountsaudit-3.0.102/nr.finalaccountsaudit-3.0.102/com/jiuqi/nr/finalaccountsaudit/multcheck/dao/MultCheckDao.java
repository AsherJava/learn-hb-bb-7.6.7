/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.dao;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.LastCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import java.util.List;
import java.util.Map;

public interface MultCheckDao {
    public boolean insertMultCheckResult(String var1, MultCheckItem var2, OneKeyCheckInfo var3, AsyncTaskMonitor var4);

    public List<MultCheckResultItem> getCheckItemResults(String var1, String var2);

    public LastCheckInfo lastCheckResults(String var1);

    public boolean deleteCheckResult(String var1, String var2, List<String> var3);

    public List<Map<String, Object>> getCheckItemList(String var1);
}

