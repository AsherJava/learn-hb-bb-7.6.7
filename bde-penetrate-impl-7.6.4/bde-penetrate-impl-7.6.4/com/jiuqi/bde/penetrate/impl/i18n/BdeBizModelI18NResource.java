/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.bde.penetrate.impl.i18n;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BdeBizModelI18NResource
implements I18NResource {
    private static final long serialVersionUID = 5755783235021897L;

    public String name() {
        return "BDE/\u4e1a\u52a1\u6a21\u578b";
    }

    public String getNameSpace() {
        return "BDE";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        for (ComputationModelEnum bizModelEnum : ComputationModelEnum.values()) {
            resourceObjects.add(new I18NResourceItem(bizModelEnum.getCode(), bizModelEnum.getName()));
        }
        return resourceObjects;
    }
}

