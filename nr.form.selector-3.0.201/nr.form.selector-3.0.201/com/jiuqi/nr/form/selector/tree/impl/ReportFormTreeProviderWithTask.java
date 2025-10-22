/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportFormTreeProviderWithTask
implements IReportFormTreeProvider {
    private IFormQueryHelper helper;
    private DefinitionAuthorityProvider authorityProvider;
    private Map<String, List<FormDefine>> group2FormsMap = new HashMap<String, List<FormDefine>>();

    public ReportFormTreeProviderWithTask(IFormQueryHelper helper, DefinitionAuthorityProvider authorityProvider) {
        this.helper = helper;
        this.authorityProvider = authorityProvider;
    }

    @Override
    public List<FormGroupDefine> getGroups(String formSchemeKey) {
        List<FormGroupDefine> formGroups = this.helper.queryRootGroupsByFormScheme(formSchemeKey);
        return formGroups.stream().filter(group -> !this.getForms(group.getKey()).isEmpty()).collect(Collectors.toList());
    }

    @Override
    public List<FormDefine> getForms(String groupKey) {
        List<Object> formList = this.group2FormsMap.get(groupKey);
        if (formList == null) {
            formList = this.helper.getAllFormsInGroupWithoutOrder(groupKey);
            if (formList != null) {
                Set hasAuthFormKeys = this.authorityProvider.canReadForms(formList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
                formList = formList.stream().filter(form -> hasAuthFormKeys.contains(form.getKey())).collect(Collectors.toList());
            } else {
                formList = new ArrayList<FormDefine>();
            }
            formList = formList.stream().filter(form -> !form.getFormType().equals((Object)FormType.FORM_TYPE_FMDM) && !form.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)).collect(Collectors.toList());
            this.group2FormsMap.put(groupKey, formList);
        }
        return formList;
    }
}

