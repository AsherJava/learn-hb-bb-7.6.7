/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.efdc.extract.impl;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.IExtractRequest;
import com.jiuqi.nr.efdc.extract.IExtractRequestFactory;
import com.jiuqi.nr.efdc.extract.impl.EFDCExtractRequestImpl;
import java.util.List;

public class EFDCExtractRequestFactory
implements IExtractRequestFactory {
    public static final String TYPE = "NR_EFDC";

    @Override
    public IExtractRequest createReqeust(FormulaSchemeDefine formulaScheme, FormDefine form) {
        return new EFDCExtractRequestImpl(formulaScheme, form);
    }

    @Override
    public IExtractRequest createReqeust(List<String> formulas, String formSchemeKey, FormDefine form) {
        return new EFDCExtractRequestImpl(formulas, formSchemeKey, form);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}

