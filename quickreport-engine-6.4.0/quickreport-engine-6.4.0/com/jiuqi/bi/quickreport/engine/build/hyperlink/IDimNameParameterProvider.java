/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine.build.hyperlink;

import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.List;

@Deprecated
public interface IDimNameParameterProvider {
    public String getDimName(ParameterModel var1);

    public List<ParameterModel> getParameters(IParameterEnv var1, String var2);
}

