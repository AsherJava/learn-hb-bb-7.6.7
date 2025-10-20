/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.task;

import com.jiuqi.va.domain.menu.MenuDO;
import java.math.BigDecimal;
import java.util.List;

public interface MenuRegisterTask {
    public List<MenuDO> getMenus();

    default public MenuDO initMenu(String name, String title, String parentName, Integer biztype, String url, String perms, long ordernum) {
        MenuDO menu = new MenuDO();
        menu.setName(name);
        menu.setTitle(title);
        menu.setParentname(parentName);
        menu.setBiztype(biztype);
        menu.setUrl(url);
        menu.setUrltype(0);
        menu.setPerms(perms);
        menu.setOrdernum(new BigDecimal(ordernum));
        return menu;
    }
}

