/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.dataentry.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.dataentry.attachment.enums.ToolbarEnum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class I18nAttachmentBtnInfo
implements I18NResource {
    private static final long serialVersionUID = 8179947294409091893L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u9644\u4ef6\u7ba1\u7406";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        I18NResourceItem item = new I18NResourceItem(ToolbarEnum.CHOOSEFROMFILEPOOL.getCode(), ToolbarEnum.CHOOSEFROMFILEPOOL.getTitle());
        resourceObjects.add(item);
        return resourceObjects;
    }
}

