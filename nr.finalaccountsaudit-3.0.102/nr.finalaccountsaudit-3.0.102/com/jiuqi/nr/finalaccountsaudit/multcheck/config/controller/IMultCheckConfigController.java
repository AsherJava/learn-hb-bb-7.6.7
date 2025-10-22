/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.controller;

import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.TaskShortInfo;
import java.util.List;

public interface IMultCheckConfigController {
    public List<TaskShortInfo> getRelationTaskAndCurrentTask(String var1, String var2) throws Exception;

    public int getPeriodTypeByScheme(String var1);
}

