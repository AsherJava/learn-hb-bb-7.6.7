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
import com.jiuqi.nr.dataentry.service.IPiercePluginI18nExtService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PiercePluginI18nResourceRegister
implements I18NResource {
    @Autowired(required=false)
    private List<IPiercePluginI18nExtService> iPiercePluginI18nExtServices;

    public String name() {
        return "\u65b0\u62a5\u8868/\u7a7f\u900f\u63d2\u4ef6\u6309\u94ae";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            resourceObjects.add(new I18NResourceItem("dataTrack", "\u6570\u636e\u8ffd\u8e2a"));
            resourceObjects.add(new I18NResourceItem("efdcPiercePlugin", "EFDC\u7a7f\u900f"));
            if (this.iPiercePluginI18nExtServices != null) {
                for (IPiercePluginI18nExtService iPiercePluginI18nExtService : this.iPiercePluginI18nExtServices) {
                    List<I18NResourceItem> resource = iPiercePluginI18nExtService.getResource();
                    if (resource.size() <= 0) continue;
                    resourceObjects.addAll(resource);
                }
            }
        }
        return resourceObjects;
    }

    public String getNameSpace() {
        return "nr";
    }
}

