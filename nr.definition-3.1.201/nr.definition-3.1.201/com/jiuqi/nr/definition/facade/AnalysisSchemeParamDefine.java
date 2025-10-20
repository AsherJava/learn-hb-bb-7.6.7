/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import java.util.List;

public interface AnalysisSchemeParamDefine {
    public String getSrcTaskKey();

    public String getSrcFormSchemeKey();

    public List<DimensionInfo> getSrcDims();

    public String getCondition();

    public boolean isAutoAnalysis();

    public String getFunctionName();

    public String getFunctionCondition();
}

