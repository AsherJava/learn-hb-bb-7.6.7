/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.jiuiqi.nr.unit.treebase.menu.IMenuContainer;
import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerType;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IMenuContainerHelper {
    private List<IMenuContainer> menuContainers;

    @Autowired(required=true)
    public IMenuContainerHelper(List<IMenuContainer> list) {
        if (null != list && !list.isEmpty()) {
            this.menuContainers = list.stream().sorted(Comparator.comparing(IMenuContainer::getOrdinary).reversed()).collect(Collectors.toList());
        }
    }

    public List<IMenuContainer> getMenuContainers(String name) {
        if (this.menuContainers != null && StringUtils.isNotEmpty((String)name)) {
            return this.menuContainers.stream().filter(c -> c.getContainerName().equals(name)).collect(Collectors.toList());
        }
        return new ArrayList<IMenuContainer>();
    }

    public List<IMenuContainer> getMenuContainers(String name, IMenuContainerType containerType) {
        if (this.menuContainers != null && StringUtils.isNotEmpty((String)name)) {
            return this.menuContainers.stream().filter(c -> c.getContainerName().equals(name) && c.getContainerType() == containerType).collect(Collectors.toList());
        }
        return new ArrayList<IMenuContainer>();
    }
}

