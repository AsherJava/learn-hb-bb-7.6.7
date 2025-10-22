/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.unit.treecommon.i18n.unittree;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeI18nResource
implements I18NResource {
    static final String NAME = "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u5355\u4f4d\u6811\u56fd\u9645\u5316";
    public static final String NAME_SPACE = "nr_dataentry_unit_tree";

    public String name() {
        return NAME;
    }

    public String getNameSpace() {
        return NAME_SPACE;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resources = new ArrayList<I18NResourceItem>();
        if (StringUtils.isEmpty((String)parentId)) {
            for (UnitTreeI18nKeys i18nKey : UnitTreeI18nKeys.values()) {
                resources.add(new I18NResourceItem(i18nKey.key, i18nKey.title));
            }
        }
        return resources;
    }
}

