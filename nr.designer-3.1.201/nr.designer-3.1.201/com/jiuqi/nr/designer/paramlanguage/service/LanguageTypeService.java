/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.language.ILanguageType
 */
package com.jiuqi.nr.designer.paramlanguage.service;

import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.designer.paramlanguage.vo.LanguageTypeObject;
import java.util.List;

public interface LanguageTypeService {
    public ILanguageType query(String var1);

    public List<LanguageTypeObject> queryAll();

    public String queryDefaultLanguage();
}

