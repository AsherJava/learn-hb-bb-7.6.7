/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.OrgDataMgrUiMenuExtend
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.extend.OrgDataMgrUiMenuExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrgDataMenuUtil {
    private static Map<String, OrgDataMgrUiMenuExtend> menuExtendMap;
    private static List<OrgDataMgrUiMenuExtend> menuExtends;

    public static List<OrgDataMgrUiMenuExtend> getMenuExtends() {
        if (menuExtends == null) {
            OrgDataMenuUtil.initMenuExtends();
        }
        return menuExtends;
    }

    public static void initMenuExtends() {
        if (menuExtendMap == null) {
            menuExtendMap = ApplicationContextRegister.getBeansOfType(OrgDataMgrUiMenuExtend.class);
        }
        menuExtends = new ArrayList<OrgDataMgrUiMenuExtend>();
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

