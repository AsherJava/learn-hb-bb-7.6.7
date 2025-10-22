/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.unit.treecommon.i18n.unittree;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@DependsOn(value={"i18nHelperSupport"})
@Component
public class UnitTreeBaseI18nHelper {
    @Qualifier(value="nr_dataentry_unit_tree")
    @Autowired
    private I18nHelper i18nHelper;

    public String getMessage(String i18nKey) {
        return this.i18nHelper.getMessage(i18nKey);
    }

    public String getMessage(String i18nKey, String defaultValue) {
        String message = this.getMessage(i18nKey);
        if (StringUtils.isEmpty((String)message)) {
            return defaultValue;
        }
        return message;
    }
}

