/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.nros.service.IPortalService
 */
package com.jiuqi.nr.definition.paramlanguage.aop;

import com.jiuqi.nvwa.framework.nros.service.IPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageCheckCache {
    @Autowired
    private IPortalService portalService;
    private static final String LANGUAGECONFIG = "languages";

    public boolean checkEnableMultiLanguage() {
        return true;
    }
}

