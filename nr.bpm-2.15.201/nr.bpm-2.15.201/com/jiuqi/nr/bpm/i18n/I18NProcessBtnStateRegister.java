/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.bpm.i18n.I18NProcessBtnState;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class I18NProcessBtnStateRegister
implements I18NResource {
    private static final long serialVersionUID = 2689653124182745877L;
    public static final String NAMESPACE = "process_btn";
    static final String NAME = "\u65b0\u62a5\u8868/\u4efb\u52a1\u8bbe\u8ba1/\u6d41\u7a0b\u6309\u94ae\u72b6\u6001";

    public String name() {
        return NAME;
    }

    public String getNameSpace() {
        return NAMESPACE;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        I18NProcessBtnState[] values = I18NProcessBtnState.values();
        if (null == parentId || StringUtils.isEmpty((String)parentId)) {
            for (I18NProcessBtnState value : values) {
                resourceObjects.add(new I18NResourceItem(value.getCode(), value.getTitle()));
            }
        }
        return resourceObjects;
    }
}

