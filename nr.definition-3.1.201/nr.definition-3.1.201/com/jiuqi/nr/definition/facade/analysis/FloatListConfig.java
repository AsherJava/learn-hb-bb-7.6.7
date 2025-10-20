/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.analysis;

import java.util.List;

public interface FloatListConfig {
    public String getDataRegionKey();

    public void setDataRegionKey(String var1);

    public String getListCondition();

    public void setListCondition(String var1);

    public String getListFilter();

    public void setListFilter(String var1);

    public String getSortFields();

    public void setSortFields(String var1);

    public String getSortFlags();

    public void setSortFlags(String var1);

    public int getMaxRowCount();

    public void setMaxRowCount(int var1);

    public String getClassifyFields();

    public void setClassifyFields(String var1);

    public String getClassifyWidths();

    public void setClassifyWidths(String var1);

    public boolean getClassifySumOnly();

    public void setClassifySumOnly(boolean var1);

    public List<String> getSumExpressions();

    public void setSumExpressions(List<String> var1);
}

