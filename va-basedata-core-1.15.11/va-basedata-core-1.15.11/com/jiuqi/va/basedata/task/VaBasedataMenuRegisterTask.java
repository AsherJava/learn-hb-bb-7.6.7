/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.basedata.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaBasedataMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaBasedataGroup", "\u57fa\u7840\u7ba1\u7406", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaBasedataEnumMain", "\u679a\u4e3e\u6570\u636e", "VaBasedataGroup", 1, "/enumdata", null, 1L));
        menus.add(this.initMenu("VaBasedataEnumMgr", "\u7ba1\u7406", "VaBasedataEnumMain", 2, null, "vaBasedata:enumData:mgr", 1L));
        menus.add(this.initMenu("VaBasedataBaseMain", "\u57fa\u7840\u6570\u636e", "VaBasedataGroup", 1, "/basedata", "vaBasedata:define:mgr", 2L));
        menus.add(this.initMenu("VaBasedataBaseMgr", "\u6267\u884c", "VaBasedataBaseMain", 2, null, "vaBasedata:baseData:mgr", 1L));
        menus.add(this.initMenu("VaBasedataBaseQuery", "\u8d8a\u6743\u67e5\u8be2", "VaBasedataBaseMain", 2, null, "vaBasedata:unauthorized.query", 2L));
        return menus;
    }
}

