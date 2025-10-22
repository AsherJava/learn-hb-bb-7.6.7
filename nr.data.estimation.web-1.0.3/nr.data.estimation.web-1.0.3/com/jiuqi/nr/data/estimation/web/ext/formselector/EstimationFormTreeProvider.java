/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.form.selector.context.IFormQueryHelper
 *  com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider
 */
package com.jiuqi.nr.data.estimation.web.ext.formselector;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import java.util.List;
import java.util.stream.Collectors;

public class EstimationFormTreeProvider
implements IReportFormTreeProvider {
    private IFormQueryHelper helper;
    protected List<String> filterList;

    public EstimationFormTreeProvider(IFormQueryHelper helper, List<String> filterList) {
        this.helper = helper;
        this.filterList = filterList;
    }

    public List<FormGroupDefine> getGroups(String formSchemeKey) {
        List formGroups = this.helper.queryRootGroupsByFormScheme(formSchemeKey);
        return formGroups.stream().filter(group -> !this.getForms(group.getKey()).isEmpty()).collect(Collectors.toList());
    }

    public List<FormDefine> getForms(String groupKey) {
        List allFormsInGroup = this.helper.getAllFormsInGroupWithoutOrder(groupKey);
        return allFormsInGroup.stream().filter(e -> this.filterList.contains(e.getKey())).collect(Collectors.toList());
    }
}

