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
import org.springframework.stereotype.Component;

@Component
public class MeasureReadWriteAccessImpl
implements IReadWriteAccess {
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public String getName() {
        return "measure";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        Boolean measureAccess = (Boolean)item.getParams();
        if (!measureAccess.booleanValue()) {
            String message = "\u5f53\u524d\u91cf\u7eb2\u4e0d\u662f\u9ed8\u8ba4\uff0c\u4e0d\u53ef\u5199";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("NO_DEFAULT_MEASURE_NO_WRITTEN"))) {
                message = this.i18nHelper.getMessage("NO_DEFAULT_MEASURE_NO_WRITTEN");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }
}

