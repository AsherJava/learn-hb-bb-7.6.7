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
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;

public class FormulaSchemeResoucre
implements Identifiable {
    private final FormSchemeDefine formScheme;
    private final String resId;

    public FormulaSchemeResoucre(FormulaSchemeDefine formulaScheme, FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
        this.resId = ResourceType.FORMULA_SCHEME.toResourceId(formulaScheme.getKey());
    }

    public String getId() {
        return this.resId;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }
}

