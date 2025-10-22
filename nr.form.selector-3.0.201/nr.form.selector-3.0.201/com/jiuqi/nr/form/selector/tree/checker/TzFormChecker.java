/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.form.selector.tree.checker;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TzFormChecker
implements IReportFormChecker,
IFormCheckExecutor {
    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        return forms.stream().filter(a -> a.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)).collect(Collectors.toList());
    }

    @Override
    public String getCheckerId() {
        return "sb-form-checker";
    }

    @Override
    public String getShowText() {
        return "\u53f0\u8d26\u8868\u5c55\u793a";
    }

    @Override
    public List<IReportFormChecker> getGroupChecker() {
        return Collections.emptyList();
    }

    @Override
    public IFormCheckExecutor getExecutor(IFormQueryContext context) {
        return this;
    }
}

