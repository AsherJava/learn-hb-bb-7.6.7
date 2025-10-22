/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 */
package com.jiuqi.nr.definition.auth.authz2;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class AllFormulaSchemeResoucre
implements Identifiable {
    private final String resId;
    private final FormSchemeDefine formScheme;

    public AllFormulaSchemeResoucre(FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
        this.resId = ResourceType.ALL_FORMULA_SCHEME.toResourceId(formScheme.getKey());
    }

    public String getId() {
        return this.resId;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }
}

