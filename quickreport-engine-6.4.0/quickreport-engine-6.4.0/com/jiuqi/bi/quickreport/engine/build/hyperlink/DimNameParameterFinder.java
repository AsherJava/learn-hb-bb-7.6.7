/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine.build.hyperlink;

import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.IDimNameParameterProvider;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.IDimParameterProvider;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.List;

public class DimNameParameterFinder {
    private static IDimParameterProvider dimParameterProvider;
    private static IDimNameParameterProvider dimNameParameterProvider;

    public static void setDimParameterProvider(IDimParameterProvider provider) {
        dimParameterProvider = provider;
    }

    @Deprecated
    public static void setDimNameParameterProvider(IDimNameParameterProvider provider) {
        dimNameParameterProvider = provider;
    }

    public String getDimName(com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv, ParameterModel parameterModel) throws ReportBuildException {
        if (dimParameterProvider == null) {
            return null;
        }
        return dimParameterProvider.getDimName(paramEnv, parameterModel);
    }

    @Deprecated
    public String getDimName(com.jiuqi.bi.parameter.model.ParameterModel parameterModel) {
        if (dimNameParameterProvider == null) {
            return null;
        }
        return dimNameParameterProvider.getDimName(parameterModel);
    }

    public List<ParameterModel> getDimParams(com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv, String dimName) throws ReportBuildException {
        if (dimParameterProvider == null) {
            return new ArrayList<ParameterModel>(0);
        }
        return dimParameterProvider.getDimParams(paramEnv, dimName);
    }

    @Deprecated
    public List<com.jiuqi.bi.parameter.model.ParameterModel> getParameters(IParameterEnv paramEnv, String dimName) {
        if (dimNameParameterProvider == null) {
            return new ArrayList<com.jiuqi.bi.parameter.model.ParameterModel>(0);
        }
        return dimNameParameterProvider.getParameters(paramEnv, dimName);
    }
}

