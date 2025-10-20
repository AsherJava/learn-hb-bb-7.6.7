/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 */
package com.jiuqi.va.organization.task;

import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaOrgMenuRegisterTask
implements MenuRegisterTask {
    public List<MenuDO> getMenus() {
        ArrayList<MenuDO> menus = new ArrayList<MenuDO>();
        menus.add(this.initMenu("VaOrganizationGroup", "\u7ec4\u7ec7\u53ca\u804c\u5458", "-", 0, null, null, 1L));
        menus.add(this.initMenu("VaOrgCategoryMgr", "\u7ec4\u7ec7\u7c7b\u578b\u7ba1\u7406", "VaOrganizationGroup", 1, "/org/category", "vaOrg:category:mgr", 1L));
        menus.add(this.initMenu("VaOrgDataMgr", "\u673a\u6784\u7ba1\u7406", "VaOrganizationGroup", 1, "/organization", null, 2L));
        menus.add(this.initMenu("VaOrgDataAdd", "\u65b0\u5efa", "VaOrgDataMgr", 2, null, "vaOrg:data:add", 1L));
        menus.add(this.initMenu("VaOrgDataUpdate", "\u4fee\u6539", "VaOrgDataMgr", 2, null, "vaOrg:data:update", 2L));
        menus.add(this.initMenu("VaOrgDataRemove", "\u5220\u9664", "VaOrgDataMgr", 2, null, "vaOrg:data:remove", 3L));
        menus.add(this.initMenu("VaOrgDataVersion", "\u7248\u672c\u7ba1\u7406", "VaOrgDataMgr", 2, null, "vaOrg:version:mgr", 4L));
        return menus;
    }
}

