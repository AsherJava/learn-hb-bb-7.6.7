/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.efdc.extract;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.IExtractRequest;
import java.util.List;

public interface IExtractRequestFactory {
    public IExtractRequest createReqeust(FormulaSchemeDefine var1, FormDefine var2);

    public IExtractRequest createReqeust(List<String> var1, String var2, FormDefine var3);

    public String getType();
}

