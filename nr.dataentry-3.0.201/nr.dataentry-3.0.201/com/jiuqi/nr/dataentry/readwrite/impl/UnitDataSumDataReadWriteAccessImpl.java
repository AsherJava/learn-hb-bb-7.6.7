/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UnitDataSumDataReadWriteAccessImpl
implements IReadWriteAccess {
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public String getName() {
        return "unitDataSumData";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        Boolean isDataSum = (Boolean)item.getParams();
        if (isDataSum.booleanValue()) {
            String message = "\u6c47\u603b\u6570\u636e\uff0c\u4e0d\u53ef\u5199";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("SUM_DATA_CANNOT_WRITTEN"))) {
                message = this.i18nHelper.getMessage("SUM_DATA_CANNOT_WRITTEN");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public Boolean IsBreak() {
        return true;
    }
}

