/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.extend.OrgCategoryMgrUiMenuExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrgCategoryMenuUtil {
    private static Map<String, OrgCategoryMgrUiMenuExtend> menuExtendMap;
    private static List<OrgCategoryMgrUiMenuExtend> menuExtends;

    public static List<OrgCategoryMgrUiMenuExtend> getMenuExtends() {
        if (menuExtends == null) {
            OrgCategoryMenuUtil.initMenuExtends();
        }
        return menuExtends;
    }

    public static void initMenuExtends() {
        if (menuExtendMap == null) {
            menuExtendMap = ApplicationContextRegister.getBeansOfType(OrgCategoryMgrUiMenuExtend.class);
        }
        menuExtends = new ArrayList<OrgCategoryMgrUiMenuExtend>();
        if (menuExtendMap != null && !menuExtendMap.isEmpty()) {
            menuExtends.addAll(menuExtendMap.values());
            Collections.sort(menuExtends, (o1, o2) -> {
                if (o1.getOrderNum() < o2.getOrderNum()) {
                    return -1;
                }
                if (o1.getOrderNum() > o2.getOrderNum()) {
                    return 1;
                }
                return 0;
            });
        }
    }
}

