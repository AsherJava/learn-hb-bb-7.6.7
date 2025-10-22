/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.entity.ext.dwdm;

import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class DWDMOptionQuery {
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    protected boolean enableOption() {
        String enable = this.systemOptionService.get("ORG_AUTH_EXT", "ORG_IDC_ENABLE");
        return StringUtils.hasText(enable) && "1".equals(enable);
    }

    protected String getOption() {
        return this.systemOptionService.get("ORG_AUTH_EXT", "ORG_IDC_LENGTH");
    }
}

