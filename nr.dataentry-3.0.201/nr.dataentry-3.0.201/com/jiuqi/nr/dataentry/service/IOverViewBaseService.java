/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public interface IOverViewBaseService {
    public List<IEntityRow> filterAuthByEntity(JtableContext var1, List<IEntityRow> var2, String var3, WorkFlowType var4);

    public List<String> filterAuth(JtableContext var1, List<String> var2, String var3, EntityViewData var4, WorkFlowType var5);

    public List<String> getAuthFormGroup(JtableContext var1);

    public Map<String, String> getFilterForm(WorkFlowType var1, String var2, String var3, JtableContext var4);

    public Map<String, String> getTitleMap(String var1, String var2, boolean var3);

    public Map<String, String> getActionTitleMap(String var1, String var2, boolean var3);
}

