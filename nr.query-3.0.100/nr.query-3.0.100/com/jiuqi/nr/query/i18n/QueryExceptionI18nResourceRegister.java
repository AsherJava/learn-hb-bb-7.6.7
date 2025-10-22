/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.query.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QueryExceptionI18nResourceRegister
implements I18NResource {
    private static final long serialVersionUID = -8253502389972008674L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u67e5\u8be2\u6a21\u5757/\u5f02\u5e38\u4fe1\u606f\u6536\u96c6";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            resourceObjects.add(new I18NResourceItem("Exception_500", "\u6a21\u7248\u9519\u8bef\u4fe1\u606f"));
        }
        return resourceObjects;
    }
}

