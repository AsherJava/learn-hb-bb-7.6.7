/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.para;

import com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable;

public interface IProcessRunPara {
    public String getTaskKey();

    public String getPeriod();

    public IProcessCustomVariable getCustomVariable();
}

