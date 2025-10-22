/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.unit.treecommon.i18n.uselector;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.definition.internal.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UnitSelectorI18nHelper {
    @Autowired
    @Qualifier(value="nr-unit-selector")
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

    public static String getMessaged(String i18nKey) {
        UnitSelectorI18nHelper helper = (UnitSelectorI18nHelper)BeanUtil.getBean(UnitSelectorI18nHelper.class);
        return helper.getMessage(i18nKey);
    }

    public static String getMessaged(String i18nKey, String defaultValue) {
        UnitSelectorI18nHelper helper = (UnitSelectorI18nHelper)BeanUtil.getBean(UnitSelectorI18nHelper.class);
        return helper.getMessage(i18nKey, defaultValue);
    }
}

