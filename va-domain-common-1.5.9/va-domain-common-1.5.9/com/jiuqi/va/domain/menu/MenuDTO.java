/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.domain.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.domain.menu.MenuDO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MenuDTO
extends MenuDO {
    private static final long serialVersionUID = 1L;
    private String searchKey;
    private String username;
    private List<MenuDO> menus;

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MenuDO> getMenus() {
        return this.menus;
    }

    public void setMenus(List<MenuDO> menus) {
        this.menus = menus;
    }
}

