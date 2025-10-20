/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import java.util.List;

public interface IAssistProvider<AcctAssist extends IAcctAssist> {
    public String getPluginType();

    public List<AcctAssist> listAssist(String var1);
}

