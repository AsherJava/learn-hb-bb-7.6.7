/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.datamodel.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaBizmodelGroup", "\u4e1a\u52a1\u6a21\u578b", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaDatamodelMgr", "\u6570\u636e\u5efa\u6a21", "VaBizmodelGroup", 1, "/datamodel", "vaDatamodel:define:mgr", 1L));
        return menus;
    }
}

