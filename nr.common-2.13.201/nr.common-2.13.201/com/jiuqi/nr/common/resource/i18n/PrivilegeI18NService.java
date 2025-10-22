/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.common.resource.i18n;

import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.i18n.helper.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@DependsOn(value={"i18nHelperSupport"})
public class PrivilegeI18NService {
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    public String getTitleByPrivilege(Privilege privilege) {
        String message;
        if (privilege == null) {
            return null;
        }
        String name = privilege.getName();
        if (StringUtils.hasLength(name) && StringUtils.hasLength(message = this.i18nHelper.getMessage(name))) {
            return message;
        }
        return privilege.getTitle();
    }
}

