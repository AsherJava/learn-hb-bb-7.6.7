/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.controller;

public interface IViewDeployController {
    @Deprecated
    public void deployFormDefine(String var1) throws Exception;

    default public void deployTask(String taskKey) throws Exception {
        this.deployTask(taskKey, true);
    }

    public void deployTask(String var1, boolean var2) throws Exception;

    public void deployTaskToDes(String var1) throws Exception;

    public void deployFormulaScheme(String var1, boolean var2) throws Exception;

    public void deployFormulaScheme(String var1, String var2) throws Exception;

    public void deployPrintScheme(String var1, boolean var2) throws Exception;

    public void deployPrintTemplate(String var1) throws Exception;

    public void deployFormulaConditions(String var1, String ... var2);

    public void deployTaskLinkByFormScheme(String var1);
}

