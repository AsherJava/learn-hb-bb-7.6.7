/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=5)
@Component
public class EmptyTreeDataReadWriteAccessImpl
implements IReadWriteAccess {
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public String getName() {
        return "emptyTreeData";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) {
        boolean emptyTreeData = (Boolean)item.getParams();
        if (emptyTreeData) {
            String message = "\u5355\u4f4d\u6570\u636e\u4e3a\u7a7a\u4e0d\u53ef\u67e5\u770b";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("UNIT_NULL_DATA_CANNOT_READ"))) {
                message = this.i18nHelper.getMessage("UNIT_NULL_DATA_CANNOT_READ");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) {
        return this.readable(item, context);
    }

    @Override
    public Boolean IsBreak() {
        return true;
    }
}

