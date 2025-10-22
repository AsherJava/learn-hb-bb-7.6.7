/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ReportFormTreeWithCheckerUnionAll
implements IReportFormTreeProvider {
    protected IFormQueryHelper formQueryHelper;
    protected IReportFormTreeProvider baseProvider;
    protected List<IFormCheckExecutor> checkExecutors;

    public ReportFormTreeWithCheckerUnionAll(IFormQueryHelper formQueryHelper, IReportFormTreeProvider baseProvider, List<IReportFormChecker> formCheckers) {
        this.formQueryHelper = formQueryHelper;
        this.baseProvider = baseProvider;
        this.init(formCheckers);
    }

    @Override
    public List<FormGroupDefine> getGroups(String formSchemeKey) {
        return this.baseProvider.getGroups(formSchemeKey);
    }

    @Override
    public List<FormDefine> getForms(String groupKey) {
        List<FormDefine> forms = this.baseProvider.getForms(groupKey);
        HashSet unionAll = new HashSet();
        for (IFormCheckExecutor executor : this.checkExecutors) {
            List<FormDefine> checkForms = executor.checkFormList(forms);
            if (checkForms == null || checkForms.isEmpty()) continue;
            unionAll.addAll(checkForms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
        }
        return forms.stream().filter(form -> unionAll.contains(form.getKey())).collect(Collectors.toList());
    }

    private void init(List<IReportFormChecker> formCheckers) {
        this.checkExecutors = new ArrayList<IFormCheckExecutor>();
        for (IReportFormChecker checker : formCheckers) {
            this.checkExecutors.add(checker.getExecutor(this.formQueryHelper));
        }
    }
}

