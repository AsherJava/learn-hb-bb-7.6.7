/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.definition.i18n;

import com.jiuqi.np.i18n.helper.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class AuditTypeI18nHelper {
    @Autowired
    @Qualifier(value="audit_type")
    private I18nHelper i18nHelper;

    public String getMessage(String code) {
        return this.i18nHelper.getMessage(code);
    }
}

