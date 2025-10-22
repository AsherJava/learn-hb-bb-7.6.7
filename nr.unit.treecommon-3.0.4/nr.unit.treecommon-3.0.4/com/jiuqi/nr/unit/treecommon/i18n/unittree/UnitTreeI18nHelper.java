/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.unit.treecommon.i18n.unittree;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeBaseI18nHelper;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeI18nHelper {
    private UnitTreeBaseI18nHelper i18nHelper;

    public String getMessage(String i18nKey) {
        if (this.i18nHelper == null) {
            this.i18nHelper = (UnitTreeBaseI18nHelper)SpringBeanUtils.getBean(UnitTreeBaseI18nHelper.class);
        }
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

