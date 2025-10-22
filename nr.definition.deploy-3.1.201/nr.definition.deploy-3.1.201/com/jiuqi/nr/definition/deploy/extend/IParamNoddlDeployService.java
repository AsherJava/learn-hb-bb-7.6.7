/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy.extend;

import java.util.List;

public interface IParamNoddlDeployService {
    public List<String> preDeploy(NoddlDeployArgs var1);

    public void deploy(NoddlDeployArgs var1);

    public static class NoddlDeployArgs {
        public final String taskKey;
        public final String formSchemeKey;
        public final String dataSchemeKey;

        public NoddlDeployArgs(String taskKey, String formSchemeKey, String dataSchemeKey) {
            this.taskKey = taskKey;
            this.formSchemeKey = formSchemeKey;
            this.dataSchemeKey = dataSchemeKey;
        }
    }
}

