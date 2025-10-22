/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FormAuthCheckExecutorOfBatch
implements IFormCheckExecutor {
    private IFormQueryHelper formQueryHelper;
    private DefinitionAuthorityProvider authorityProvider;

    public FormAuthCheckExecutorOfBatch(IFormQueryHelper formQueryHelper, DefinitionAuthorityProvider authorityProvider) {
        this.formQueryHelper = formQueryHelper;
        this.authorityProvider = authorityProvider;
    }

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        if (!forms.isEmpty()) {
            Set hasAuthFormKeys = this.authorityProvider.canReadForms(forms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
            return forms.stream().filter(e -> hasAuthFormKeys.contains(e.getKey())).collect(Collectors.toList());
        }
        return new ArrayList<FormDefine>();
    }
}

