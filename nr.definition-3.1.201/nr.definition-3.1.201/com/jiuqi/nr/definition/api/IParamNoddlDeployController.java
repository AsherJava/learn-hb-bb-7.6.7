/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.api;

import java.util.List;

public interface IParamNoddlDeployController {
    public boolean enableNoDDL();

    public List<String> preDeploy(String var1);

    public void unPreDeploy(String var1);

    public void deploy(String var1);

    public int getDDLStatus(String var1);
}

