/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.openapi.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaOpenApiMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaSystemSettingGroup", "\u7cfb\u7edf\u914d\u7f6e", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaIntfMgrGroup", "\u63a5\u53e3\u7ba1\u7406", "VaSystemSettingGroup", 0, null, null, 1L));
        menus.add(this.initMenu("VaOpenApiAuthMgr", "\u63a5\u53e3\u6388\u6743\u7ba1\u7406", "VaIntfMgrGroup", 1, "/openApi", "vaOpenApi:auth:mgr", 0L));
        return menus;
    }
}

