/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.BaseDataMgrUiMenuExtend
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.common;

import com.jiuqi.va.extend.BaseDataMgrUiMenuExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BaseDataMenuUtil {
    private static Map<String, BaseDataMgrUiMenuExtend> menuExtendMap;
    private static List<BaseDataMgrUiMenuExtend> menuExtends;

    public static List<BaseDataMgrUiMenuExtend> getMenuExtends() {
        if (menuExtends == null) {
            BaseDataMenuUtil.initMenuExtends();
        }
        return menuExtends;
    }

    public static void initMenuExtends() {
        if (menuExtendMap == null) {
            menuExtendMap = ApplicationContextRegister.getBeansOfType(BaseDataMgrUiMenuExtend.class);
        }
        menuExtends = new ArrayList<BaseDataMgrUiMenuExtend>();
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

