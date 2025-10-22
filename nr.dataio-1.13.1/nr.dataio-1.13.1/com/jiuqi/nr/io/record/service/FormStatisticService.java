/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 */
package com.jiuqi.nr.io.record.service;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.io.record.bean.FormStatisticLog;
import java.util.List;

public interface FormStatisticService {
    public Page<FormStatisticLog> queryStatisticLogs(String var1, String var2, int var3, int var4);

    public void saveStatisticLogs(List<FormStatisticLog> var1);
}

