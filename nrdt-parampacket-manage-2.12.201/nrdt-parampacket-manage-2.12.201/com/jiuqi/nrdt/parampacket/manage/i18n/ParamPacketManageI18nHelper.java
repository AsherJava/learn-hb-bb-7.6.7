/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nrdt.parampacket.manage.i18n;

import com.jiuqi.np.i18n.helper.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ParamPacketManageI18nHelper {
    @Qualifier(value="nrdt_parampacketmanage")
    @Autowired
    private I18nHelper i18nHelper;

    public String getMessage(String i18nKey) {
        return this.i18nHelper.getMessage(i18nKey);
    }

    public String getMessage(String i18nKey, String defaultValue) {
        String message = this.getMessage(i18nKey);
        if (!StringUtils.hasLength(message)) {
            return defaultValue;
        }
        return message;
    }
}

