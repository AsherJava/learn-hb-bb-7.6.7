/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.bizmeta.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaBizMetaMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaBizmodelGroup", "\u4e1a\u52a1\u6a21\u578b", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaBizMeta", "\u5143\u6570\u636e\u7ba1\u7406", "VaBizmodelGroup", 1, "/meta/all", null, 1L));
        menus.add(this.initMenu("VaBill", "\u5355\u636e\u7ba1\u7406", "VaBizmodelGroup", 1, "/meta/bill", null, 2L));
        menus.add(this.initMenu("VaBillList", "\u5355\u636e\u5217\u8868\u7ba1\u7406", "VaBizmodelGroup", 1, "/meta/billlist", null, 3L));
        menus.add(this.initMenu("VaBillcode", "\u5355\u636e\u7f16\u53f7\u7ba1\u7406", "VaBizmodelGroup", 1, "/billcode", null, 4L));
        return menus;
    }
}

