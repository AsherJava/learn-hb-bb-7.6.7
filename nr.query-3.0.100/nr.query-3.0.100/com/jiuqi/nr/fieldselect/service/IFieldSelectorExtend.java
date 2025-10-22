/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fieldselect.service;

import com.jiuqi.nr.fieldselect.define.FormGroupData;
import com.jiuqi.nr.fieldselect.define.SelectedFieldDefine;
import java.util.List;

public interface IFieldSelectorExtend {
    public List<SelectedFieldDefine> getDefaultFields(String var1, String var2);

    public List<FormGroupData> getGroupAndForms(String var1);
}

