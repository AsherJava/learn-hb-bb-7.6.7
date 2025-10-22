/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.gcreport.common.action;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GcActionGatherI18NResource
implements I18NResource {
    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u5408\u5e76\u62a5\u8868\u5de5\u5177\u6761\u6309\u94ae\u6536\u96c6";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        SpringContextUtils.getBeans(AbstractGcActionItem.class).stream().forEach(gcActionItem -> resourceObjects.add(new I18NResourceItem(gcActionItem.getCode(), gcActionItem.getTitle())));
        return resourceObjects;
    }
}

