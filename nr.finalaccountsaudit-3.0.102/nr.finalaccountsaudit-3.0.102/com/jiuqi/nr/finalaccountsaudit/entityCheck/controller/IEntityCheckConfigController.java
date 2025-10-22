/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.FuncExecResult
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.controller;

import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.SelectStructure;
import java.util.List;

public interface IEntityCheckConfigController {
    public FuncExecResult getDataEntryInit(String var1) throws Exception;

    public List<SelectStructure> getAssTasks(String var1, String var2);

    public List<SelectStructure> getAssFormSchemes(String var1, String var2, String var3);

    public Association getAssociation(String var1, String var2, String var3, String var4);
}

