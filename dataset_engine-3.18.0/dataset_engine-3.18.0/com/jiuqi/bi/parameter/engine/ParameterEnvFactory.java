/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.ParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.List;

public class ParameterEnvFactory {
    public static IParameterEnv createParameterEnv(List<ParameterModel> parameterModels, String userGuid) throws ParameterException {
        return new ParameterEnv(parameterModels, userGuid);
    }

    public static IParameterEnv createParaEnvWithoutCascadeRelation(List<ParameterModel> parameterModels, String userGuid) throws ParameterException {
        return new ParameterEnv(parameterModels, userGuid, true);
    }

    public static IParameterEnv createParameterEnv(ParameterEngineEnv env) throws ParameterException {
        return new ParameterEnv(env);
    }
}

