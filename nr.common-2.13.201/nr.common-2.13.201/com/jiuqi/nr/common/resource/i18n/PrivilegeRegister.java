/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.common.resource.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PrivilegeRegister
implements I18NResource {
    private static final long serialVersionUID = -8788540704404448170L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u6743\u9650\u7ba1\u7406/\u6743\u9650\u9879\u6807\u9898";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (!StringUtils.hasLength(parentId)) {
            resourceObjects.add(new I18NResourceItem("read", "\u8bbf\u95ee"));
            resourceObjects.add(new I18NResourceItem("write", "\u7f16\u8f91"));
            resourceObjects.add(new I18NResourceItem("input", "\u6570\u636e\u5199"));
        }
        return resourceObjects;
    }
}

