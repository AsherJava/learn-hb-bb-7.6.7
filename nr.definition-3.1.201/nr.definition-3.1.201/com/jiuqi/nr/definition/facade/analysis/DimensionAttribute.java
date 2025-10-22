/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.definition.facade.analysis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.definition.common.DimensionRange;
import com.jiuqi.nr.definition.common.PeriodRangeType;
import java.util.List;

public interface DimensionAttribute {
    public String getLinkEntityKey();

    public void setLinkEntityKey(String var1);

    public String getPeriodRange();

    public void setPeriodRange(String var1);

    public int getPeriodType();

    public void setPeriodType(int var1);

    public boolean isShowLinkEntity();

    public void setShowLinkEntity(boolean var1);

    public boolean isShowOnTree();

    public void setShowOnTree(boolean var1);

    public DimensionRange getUnitRange();

    public void setUnitRange(DimensionRange var1);

    @JsonIgnore
    public String getFromPeriod();

    @JsonIgnore
    public String getToPeriod();

    public List<String> getUnitKeys();

    public void setUnitKeys(List<String> var1);

    public List<String> getUnitCodes();

    public void setUnitCodes(List<String> var1);

    public String getCondition();

    public void setCondition(String var1);

    public String getUnitTitles();

    public void setUnitTitles(String var1);

    public PeriodRangeType getPeriodRangeType();

    public void setPeriodRangeType(PeriodRangeType var1);
}

