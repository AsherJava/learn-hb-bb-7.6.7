/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=5)
@Component
public class DataVersionReadWriteAccessImpl
implements IReadWriteAccess {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public String getName() {
        return "dataVersion";
    }

    @Override
    public boolean isEnable(JtableContext context) {
        String dataVersionObj = this.taskOptionController.getValue(context.getTaskKey(), "DATA_VERSION");
        return "1".equals(dataVersionObj);
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        boolean isVersionData;
        JSONObject versionData;
        JSONObject json = new JSONObject((Map)((HashMap)item.getParams()));
        if (json.has("versionData") && (versionData = json.getJSONObject("versionData")).has("isVersionData") && versionData.has("isSysVersion") && (isVersionData = versionData.getBoolean("isVersionData"))) {
            String message = "\u5feb\u7167\u6570\u636e\uff0c\u65e0\u6cd5\u4fee\u6539";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("VERSION_DATA_CANNOT_MODIFIED"))) {
                message = this.i18nHelper.getMessage("VERSION_DATA_CANNOT_MODIFIED");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }
}

