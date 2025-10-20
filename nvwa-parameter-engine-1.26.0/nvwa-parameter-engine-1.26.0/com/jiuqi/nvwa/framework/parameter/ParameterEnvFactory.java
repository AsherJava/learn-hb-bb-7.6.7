/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.List;

public class ParameterEnvFactory {
    public static IParameterEnv createParameterEnv(List<ParameterModel> parameterModels, String userGuid) throws ParameterException {
        return new ParameterEnv(userGuid, parameterModels);
    }
}

