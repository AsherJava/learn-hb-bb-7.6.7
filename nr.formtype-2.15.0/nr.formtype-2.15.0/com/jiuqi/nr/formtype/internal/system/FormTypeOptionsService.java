/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.formtype.internal.system;

import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FormTypeOptionsService {
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    public boolean enableNrFormTypeMgr() {
        String value = this.systemOptionService.get("form_type_option_id", "formtype_option_gzwms");
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return !"0".equals(value);
    }
}

