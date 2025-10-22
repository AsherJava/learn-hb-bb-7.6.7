/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy.extend;

import java.util.function.Consumer;

public interface IParamDeployExtendService {
    default public void beforeDeploy(String formSchemeKey, Consumer<String> progress) {
    }

    default public void afterDeploy(String formSchemeKey, Consumer<String> progress) {
    }

    default public void beforeRollback(String formSchemeKey, Consumer<String> progress) {
    }

    default public void afterRollback(String formSchemeKey, Consumer<String> progress) {
    }
}

