/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbselector.service;

import com.jiuqi.nr.zbselector.define.FormGroupData;
import com.jiuqi.nr.zbselector.define.SelectedFieldDefine;
import java.util.List;

public interface IFieldSelectorExtend {
    public List<SelectedFieldDefine> getDefaultFields(String var1, String var2);

    public List<FormGroupData> getGroupAndForms(String var1);
}

