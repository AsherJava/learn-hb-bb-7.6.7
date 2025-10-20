/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.language.ILanguageType
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService
 */
package com.jiuqi.nr.designer.paramlanguage.service.impl;

import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.paramlanguage.util.LanguageTypeUtil;
import com.jiuqi.nr.designer.paramlanguage.vo.LanguageTypeObject;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageTypeServiceImpl
implements LanguageTypeService {
    @Autowired
    private DefaultLanguageService defaultLanguageService;

    @Override
    public ILanguageType query(String key) {
        return LanguageType.valueOfKey((String)key);
    }

    @Override
    public List<LanguageTypeObject> queryAll() {
        return LanguageType.allValues().stream().map(LanguageTypeUtil::languageTypeToObj).collect(Collectors.toList());
    }

    @Override
    public String queryDefaultLanguage() {
        return String.valueOf(this.defaultLanguageService.getDefaultLanguage());
    }
}

