/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.List;

public class FillDataSumExcutor
implements IFormCheckExecutor {
    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        forms.removeIf(form -> !form.getIsGather());
        return forms;
    }
}

