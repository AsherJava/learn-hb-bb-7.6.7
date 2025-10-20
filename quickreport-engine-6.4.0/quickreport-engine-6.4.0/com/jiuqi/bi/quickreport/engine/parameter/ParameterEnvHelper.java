/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine.parameter;

import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.parameter.BufferedParameterEnv;

public class ParameterEnvHelper {
    private ParameterEnvHelper() {
    }

    public static IParameterEnv unwrap(com.jiuqi.nvwa.framework.parameter.IParameterEnv env) {
        while (env instanceof BufferedParameterEnv) {
            env = ((BufferedParameterEnv)env).getEnv();
        }
        if (env instanceof EnhancedParameterEnvAdapter) {
            return ((EnhancedParameterEnvAdapter)env).getOriginalParameterEnv();
        }
        return null;
    }
}

