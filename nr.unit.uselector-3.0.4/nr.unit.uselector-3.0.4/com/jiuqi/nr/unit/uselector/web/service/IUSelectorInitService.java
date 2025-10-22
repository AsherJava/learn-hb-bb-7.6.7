/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 */
package com.jiuqi.nr.unit.uselector.web.service;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.nr.unit.uselector.web.response.ContextInfo;
import com.jiuqi.nr.unit.uselector.web.response.IMenuItem;
import com.jiuqi.nr.unit.uselector.web.response.QuickMenuInfo;
import java.util.List;

public interface IUSelectorInitService {
    public ContextInfo loadContext(UnitTreeContextData var1);

    public QuickMenuInfo getQuickSelectionMenus(String var1);

    public List<IMenuItem> loadFilterSchemeMenus(String var1);
}

