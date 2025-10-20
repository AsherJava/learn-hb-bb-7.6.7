/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnEnum
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.bde.penetrate.impl.i18n;

import com.jiuqi.bde.common.constant.ColumnEnum;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BdeColumnI18NResource
implements I18NResource {
    private static final long serialVersionUID = 3804010821344821000L;

    public String name() {
        return "BDE/\u900f\u89c6\u5217";
    }

    public String getNameSpace() {
        return "BDE";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        for (ColumnEnum columnEnum : ColumnEnum.values()) {
            resourceObjects.add(new I18NResourceItem(columnEnum.getCode(), columnEnum.getTitle()));
        }
        return resourceObjects;
    }
}

