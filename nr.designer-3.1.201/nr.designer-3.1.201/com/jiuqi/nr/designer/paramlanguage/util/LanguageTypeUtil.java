/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.language.ILanguageType
 */
package com.jiuqi.nr.designer.paramlanguage.util;

import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.designer.paramlanguage.vo.LanguageTypeObject;

public class LanguageTypeUtil {
    public static LanguageTypeObject languageTypeToObj(ILanguageType languageType) {
        LanguageTypeObject languageTypeObj = new LanguageTypeObject();
        languageTypeObj.setType(languageType.getKey());
        languageTypeObj.setLanguage(languageType.getLanguage());
        languageTypeObj.setDefault(languageType.isDefault());
        return languageTypeObj;
    }
}

