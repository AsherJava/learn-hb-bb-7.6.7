/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import java.util.List;

public interface IFormCheckExecutor {
    default public List<FormGroupDefine> checkGroupList(List<FormGroupDefine> formGroups) {
        return formGroups;
    }

    public List<FormDefine> checkFormList(List<FormDefine> var1);
}

