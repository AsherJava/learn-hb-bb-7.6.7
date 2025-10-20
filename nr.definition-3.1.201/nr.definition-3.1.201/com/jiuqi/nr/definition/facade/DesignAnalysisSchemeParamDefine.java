/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import java.util.List;

public interface DesignAnalysisSchemeParamDefine
extends AnalysisSchemeParamDefine {
    public void setSrcTaskKey(String var1);

    public void setSrcFormSchemeKey(String var1);

    public void setSrcDims(List<DimensionInfo> var1);

    public void setCondition(String var1);

    public void setAutoAnalysis(boolean var1);

    public void setFunctionName(String var1);

    public void setFunctionCondition(String var1);
}

