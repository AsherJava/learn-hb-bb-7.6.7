/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.Filter
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.jtable.params.base.FormData
 */
package com.jiuqi.nr.dataentry.filter;

import com.jiuqi.np.util.Filter;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.jtable.params.base.FormData;
import java.util.Set;

public class FormFilter
implements Filter<FormData> {
    private Set<String> lockedFormKeys;
    private Set<String> conditionResult;
    private DefinitionAuthorityProvider authorityProvider;
    private boolean writeAuth = false;

    public void setWriteAuth(DefinitionAuthorityProvider authorityProvider, boolean writeAuth) {
        this.authorityProvider = authorityProvider;
        this.writeAuth = writeAuth;
    }

    public void setCondition(Set<String> conditionResult) {
        this.conditionResult = conditionResult;
    }

    public void setLocked(Set<String> lockedFormKeys) {
        this.lockedFormKeys = lockedFormKeys;
    }

    public boolean accept(FormData form) {
        boolean canWriteForm;
        if (this.conditionResult != null && this.conditionResult.contains(form.getKey())) {
            return false;
        }
        return this.authorityProvider == null || (canWriteForm = this.authorityProvider.canWriteForm(form.getKey())) || !this.writeAuth;
    }
}

