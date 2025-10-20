/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.impl.utils.IDataRefChcker;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDataRefChcker
implements IDataRefChcker {
    @Autowired
    protected DataSchemeService schemeService;
    private static final String SCHEME_NAME_TMPL = "%1$s|%2$s";

    protected String formatSchemeName(Map<String, String> schemeNameMap, String schemeCode) {
        if (!StringUtils.isEmpty((String)schemeNameMap.get(schemeCode))) {
            return String.format(SCHEME_NAME_TMPL, schemeCode, schemeNameMap.get(schemeCode));
        }
        return schemeCode;
    }
}

