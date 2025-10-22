/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.jiuiqi.nr.unit.treebase.menu.IMenuContainerFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IMenuContainerFilterHelper {
    private Map<String, IMenuContainerFilter> filterMap = new HashMap<String, IMenuContainerFilter>();

    @Autowired(required=true)
    public IMenuContainerFilterHelper(List<IMenuContainerFilter> list) {
        if (null != list && !list.isEmpty()) {
            list.forEach(filter -> this.filterMap.put(filter.getContainerId(), (IMenuContainerFilter)filter));
        }
    }

    public IMenuContainerFilter getMenuContainerFilter(String containerId) {
        if (this.filterMap.containsKey(containerId)) {
            return this.filterMap.get(containerId);
        }
        return null;
    }
}

