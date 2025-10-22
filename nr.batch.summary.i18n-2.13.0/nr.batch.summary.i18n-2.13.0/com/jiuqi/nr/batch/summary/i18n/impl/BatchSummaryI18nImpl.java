/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.i18n.impl;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.batch.summary.i18n.BatchSummaryI18n;
import com.jiuqi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BatchSummaryI18nImpl
implements BatchSummaryI18n {
    @Qualifier(value="nr_batch_summary")
    @Autowired
    private I18nHelper i18nHelper;

    @Override
    public String getMessage(String i18nKey) {
        return this.i18nHelper.getMessage(i18nKey);
    }

    @Override
    public String getMessage(String i18nKey, String defaultValue) {
        String message = this.getMessage(i18nKey);
        if (StringUtils.isEmpty((String)message)) {
            return defaultValue;
        }
        return message;
    }
}

