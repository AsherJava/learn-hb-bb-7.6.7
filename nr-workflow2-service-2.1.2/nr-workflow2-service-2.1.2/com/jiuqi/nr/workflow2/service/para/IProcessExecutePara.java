/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.para;

import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;

public interface IProcessExecutePara
extends IProcessRunPara {
    public String getTaskId();

    public String getUserTaskCode();

    public void setUserTaskCode(String var1);

    public String getActionCode();
}

