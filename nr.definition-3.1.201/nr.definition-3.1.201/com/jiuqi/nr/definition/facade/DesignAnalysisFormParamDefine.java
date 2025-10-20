/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.AnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.analysis.ColAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;
import java.util.List;

public interface DesignAnalysisFormParamDefine
extends AnalysisFormParamDefine {
    public void setFloatListSettings(List<FloatListConfig> var1);

    public void setHostCount(int var1);

    public void setGuestCount(int var1);

    public void setNumEnable(int var1);

    public void setFirstDimensionFloat(boolean var1);

    public void setShowAllChild(boolean var1);

    public void setConditionFormula(String var1);

    public void setLineCalibers(List<LineCaliber> var1);

    public void setColAttribute(List<ColAttribute> var1);

    public void setDimensionConfig(DimensionConfig var1);

    public void setAutoAnalysis(boolean var1);

    public void setFunctionName(String var1);

    public void setFunctionCondition(String var1);

    public void setNoColCbrLinks(List<String> var1);

    public void setNoRowCbrLinks(List<String> var1);
}

