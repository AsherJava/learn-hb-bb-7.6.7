/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nrdt.parampacket.manage.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nrdt.parampacket.manage.i18n.ParamPacketManageI18nKeys;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ParamPacketManageI18nRegisterResource
implements I18NResource {
    public static final String NAME = "\u5355\u673a\u7248/\u53c2\u6570\u5305\u7ba1\u7406";
    public static final String NAMESPACE = "nrdt_parampacketmanage";

    public String name() {
        return NAME;
    }

    public String getNameSpace() {
        return NAMESPACE;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resources = new ArrayList<I18NResourceItem>();
        if (StringUtils.isEmpty((String)parentId)) {
            for (ParamPacketManageI18nKeys i18nKey : ParamPacketManageI18nKeys.values()) {
                resources.add(new I18NResourceItem(i18nKey.key, i18nKey.title));
            }
        }
        return resources;
    }
}

