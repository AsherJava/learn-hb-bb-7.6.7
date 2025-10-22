/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeWithCheckerUnionAll;
import java.util.List;

public class ReportFormTreeWithCheckerRetainAll
extends ReportFormTreeWithCheckerUnionAll {
    public ReportFormTreeWithCheckerRetainAll(IFormQueryHelper formQueryHelper, IReportFormTreeProvider baseProvider, List<IReportFormChecker> formCheckers) {
        super(formQueryHelper, baseProvider, formCheckers);
    }

    @Override
    public List<FormDefine> getForms(String groupKey) {
        List<FormDefine> forms = this.baseProvider.getForms(groupKey);
        if (this.checkExecutors != null && !this.checkExecutors.isEmpty()) {
            for (IFormCheckExecutor executor : this.checkExecutors) {
                if (forms.isEmpty()) break;
                List<FormDefine> checkForms = executor.checkFormList(forms);
                forms.removeIf(form -> checkForms.stream().noneMatch(checkForm -> checkForm.getKey().equals(form.getKey())));
            }
        }
        return forms;
    }
}

