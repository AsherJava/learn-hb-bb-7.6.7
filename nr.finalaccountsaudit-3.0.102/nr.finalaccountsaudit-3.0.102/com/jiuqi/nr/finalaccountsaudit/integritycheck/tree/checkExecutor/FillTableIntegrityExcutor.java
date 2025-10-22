/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.tree.checkExecutor;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.List;

public class FillTableIntegrityExcutor
implements IFormCheckExecutor {
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        forms.removeIf(form -> form.getFormType() != FormType.FORM_TYPE_FIX && form.getFormType() != FormType.FORM_TYPE_FLOAT);
        return forms;
    }
}

