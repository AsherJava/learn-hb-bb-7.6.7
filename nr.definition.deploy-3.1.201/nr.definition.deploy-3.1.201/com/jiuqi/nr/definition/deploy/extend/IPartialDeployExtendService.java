/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.extend;

import com.jiuqi.nr.definition.common.ParamResourceType;
import java.util.List;

public interface IPartialDeployExtendService {
    public ParamResourceType getType();

    default public void beforeDeploy(String schemeKey, List<String> sourceKeys) {
    }

    default public void afterDeploy(String schemeKey, List<String> sourceKeys) {
    }

    default public void beforeRollback(String schemeKey, List<String> sourceKeys) {
    }

    default public void afterRollback(String schemeKey, List<String> sourceKeys) {
    }
}

