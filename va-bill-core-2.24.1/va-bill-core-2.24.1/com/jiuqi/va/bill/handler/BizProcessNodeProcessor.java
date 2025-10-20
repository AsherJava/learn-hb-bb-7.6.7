/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 */
package com.jiuqi.va.bill.handler;

import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import java.util.List;

public interface BizProcessNodeProcessor {
    public String getSysCode();

    public void processed(List<ProcessNodeDO> var1);
}

