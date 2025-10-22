/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.nros.service.IPortalService
 */
package com.jiuqi.nr.datascheme.i18n.language;

import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageTypeDao;
import com.jiuqi.nvwa.framework.nros.service.IPortalService;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(value=false)
public class LanguageTypeService {
    private static LanguageTypeDao languageTypeDao;
    private static IPortalService portalService;
    private static final String LANGUAGECONFIG = "languages";

    public LanguageTypeService(LanguageTypeDao languageTypeDao, IPortalService portalService) {
        LanguageTypeService.languageTypeDao = languageTypeDao;
        LanguageTypeService.portalService = portalService;
    }

    public static boolean enableMultiLanguage() {
        return true;
    }

    public static List<? extends ILanguageType> getAll() {
        return languageTypeDao.queryAll();
    }
}

