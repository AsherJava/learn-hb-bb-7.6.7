/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.zbselector.service;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Map;

public interface IFieldSelectFormFilterService {
    public boolean filterForm(Map<String, String> var1, String var2);

    public boolean filterField(Map<String, String> var1, FormDefine var2, DataLinkDefine var3);
}

