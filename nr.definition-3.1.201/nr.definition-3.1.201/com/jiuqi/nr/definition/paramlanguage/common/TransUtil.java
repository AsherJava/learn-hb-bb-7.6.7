/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.common;

import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;

public class TransUtil {
    public Integer transformLanguageResourceType(LanguageResourceType languageResourceType) {
        return languageResourceType.getValue();
    }

    public LanguageResourceType transformLanguageResourceType(Integer value) {
        return LanguageResourceType.valueOf(value);
    }
}

