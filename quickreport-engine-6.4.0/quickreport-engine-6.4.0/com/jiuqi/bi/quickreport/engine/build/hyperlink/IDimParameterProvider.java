/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine.build.hyperlink;

import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.List;

public interface IDimParameterProvider {
    public String getDimName(IParameterEnv var1, ParameterModel var2) throws ReportBuildException;

    public List<ParameterModel> getDimParams(IParameterEnv var1, String var2) throws ReportBuildException;
}

