/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;

public interface IAssistProviderGather {
    public <AcctAssist extends IAcctAssist> IAssistProvider<AcctAssist> getByPluginType(String var1);
}

