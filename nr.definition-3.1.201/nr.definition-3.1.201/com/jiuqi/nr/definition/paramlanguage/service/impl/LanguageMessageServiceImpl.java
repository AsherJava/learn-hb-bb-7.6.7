/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.language.ILanguageType
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 */
package com.jiuqi.nr.definition.paramlanguage.service.impl;

import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.paramlanguage.service.LanguageMessageService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LanguageMessageServiceImpl
implements LanguageMessageService {
    @Override
    public int getLanguageTypeByMessage(String message) {
        ILanguageType valueOfName = LanguageType.valueOfCode((String)message);
        return null == valueOfName ? 0 : valueOfName.getValue();
    }

    @Override
    public List<String> getAllLanguageType() {
        ArrayList<String> allType = new ArrayList<String>();
        List allValues = LanguageType.allValues();
        for (ILanguageType iLanguageType : allValues) {
            if (iLanguageType.isDefault()) continue;
            allType.add(iLanguageType.getKey());
        }
        return allType;
    }
}

