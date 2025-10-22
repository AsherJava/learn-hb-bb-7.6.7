/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 */
package com.jiuqi.nr.unit.uselector.web.service;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeInfo;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeTableData;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterTemplateInfo;
import com.jiuqi.nr.unit.uselector.filter.scheme.IFilterConditionMenuItem;
import java.util.List;

public interface IFilterSchemeService {
    public FilterSchemeTableData loadFilterSchemes(String var1);

    public FilterSchemeTableData saveFilterScheme(String var1, FilterSchemeInfo var2);

    public FilterTemplateInfo loadFilterTemplate(String var1, String var2);

    public FilterSchemeTableData saveFilterTemplate(String var1, FilterTemplateInfo var2);

    public FilterSchemeTableData removeFilterScheme(String var1, String var2);

    public FilterSchemeTableData copyFilterScheme(String var1, String var2);

    public List<IFilterConditionMenuItem> loadFilterConditionItem(String var1);

    public List<FilterSchemeInfo> getFilterSchemeInfos(IUnitTreeContext var1);
}

