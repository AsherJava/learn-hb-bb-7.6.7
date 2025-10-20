/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.bill.bd.menu.domain;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillMdMapMenu
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaFiledMapMgrGroup", "\u751f\u6210\u5355\u636e\u6620\u5c04\u914d\u7f6e", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaApplyToRegMgr", "\u751f\u6210\u5355\u636e\u6620\u5c04", "VaFiledMapMgrGroup", 1, "/applyreg/config", "vaFiledMap:applytoreg:mgr", 1L));
        menus.add(this.initMenu("VaApplyToRegExceptionMgr", "\u751f\u6210\u5355\u636e\u6620\u5c04\u5f02\u5e38", "VaFiledMapMgrGroup", 1, "/applyregex/config", "vaFiledMap:applyregex:mgr", 1L));
        return menus;
    }
}

