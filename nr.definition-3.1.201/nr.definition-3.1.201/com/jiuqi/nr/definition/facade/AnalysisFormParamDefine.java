/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.analysis.ColAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;
import java.util.List;

public interface AnalysisFormParamDefine {
    public List<FloatListConfig> getFloatListSettings();

    public int getHostCount();

    public int getGuestCount();

    public int getNumEnable();

    public boolean getFirstDimensionFloat();

    public boolean getShowAllChild();

    public String getConditionFormula();

    public List<LineCaliber> getLineCalibers();

    public List<ColAttribute> getColAttribute();

    public DimensionConfig getDimensionConfig();

    public boolean isAutoAnalysis();

    public String getFunctionName();

    public String getFunctionCondition();

    public List<String> getNoColCbrLinks();

    public List<String> getNoRowCbrLinks();
}

