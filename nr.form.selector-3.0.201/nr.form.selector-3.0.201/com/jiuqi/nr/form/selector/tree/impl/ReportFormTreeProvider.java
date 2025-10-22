/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportFormTreeProvider
implements IReportFormTreeProvider {
    private IFormQueryHelper helper;
    private IFormCheckExecutor executor;
    private Map<String, List<FormDefine>> key2FormsMap = new HashMap<String, List<FormDefine>>();

    public ReportFormTreeProvider(IFormQueryHelper helper, IReportFormChecker formAuthChecker) {
        this.helper = helper;
        this.executor = formAuthChecker.getExecutor(helper);
    }

    @Override
    public List<FormGroupDefine> getGroups(String formSchemeKey) {
        List<FormGroupDefine> formGroups = this.helper.queryRootGroupsByFormScheme(formSchemeKey);
        formGroups = this.executor.checkGroupList(formGroups);
        return formGroups.stream().filter(group -> !this.getForms(group.getKey()).isEmpty()).collect(Collectors.toList());
    }

    @Override
    public List<FormDefine> getForms(String groupKey) {
        List<FormDefine> formList = this.key2FormsMap.get(groupKey);
        if (formList == null) {
            formList = this.executor.checkFormList(this.helper.getAllFormsInGroupWithoutOrder(groupKey));
            this.key2FormsMap.put(groupKey, formList);
        }
        return formList;
    }
}

