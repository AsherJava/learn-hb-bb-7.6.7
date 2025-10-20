/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.bde.common.i18n.util;

import com.jiuqi.bde.common.constant.ColumnEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
@Lazy
public class BdeI18nHelper {
    @Autowired
    @Qualifier(value="BDE")
    private I18nHelper i18nHelper;

    public String getMessage(String key) {
        return this.i18nHelper.getMessage(key);
    }

    public String getMessageByColumnCode(String key) {
        String message = this.i18nHelper.getMessage(key);
        if (!StringUtils.isEmpty((String)message)) {
            return message;
        }
        ColumnEnum enumByCode = ColumnEnum.getEnumByCode(key);
        return enumByCode == null ? key : enumByCode.getTitle();
    }

    public String getMessage(String key, String defaultMessage) {
        String message = this.i18nHelper.getMessage(key);
        return StringUtils.isEmpty((String)message) ? defaultMessage : message;
    }
}

