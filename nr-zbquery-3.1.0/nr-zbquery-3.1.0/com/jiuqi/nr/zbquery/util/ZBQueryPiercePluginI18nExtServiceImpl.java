/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.nr.dataentry.service.IPiercePluginI18nExtService
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.dataentry.service.IPiercePluginI18nExtService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryPiercePluginI18nExtServiceImpl
implements IPiercePluginI18nExtService {
    public List<I18NResourceItem> getResource() {
        ArrayList<I18NResourceItem> items = new ArrayList<I18NResourceItem>();
        items.add(new I18NResourceItem("zbQueryPierce", "\u7a7f\u900f\u67e5\u8be2"));
        return items;
    }
}

