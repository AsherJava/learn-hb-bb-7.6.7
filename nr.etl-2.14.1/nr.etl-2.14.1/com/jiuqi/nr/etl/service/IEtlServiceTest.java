/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.service;

import com.jiuqi.nr.etl.common.ETLTask;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import java.util.List;

public interface IEtlServiceTest {
    public List<ETLTask> getAllTaskTest(String var1, String var2, String var3);

    public ETLTask findTaskByNameTest(String var1, String var2, String var3, String var4);

    public EtlExecuteInfo executeTest(String var1, String var2, String var3, String var4, String var5);
}

