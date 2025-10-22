/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.service.IDataAccessItemBaseService;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AccessItemServiceCollector
implements ApplicationListener<ContextRefreshedEvent> {
    private final Map<IDataAccessItemService, Integer> accessItemServices = new HashMap<IDataAccessItemService, Integer>();
    private final Map<IDataExtendAccessItemService, Integer> extendAccessItemServices = new HashMap<IDataExtendAccessItemService, Integer>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.initAccessItemBaseList(event);
    }

    public void initAccessItemBaseList(ContextRefreshedEvent event) {
        Map<String, IDataAccessItemBaseService> beans = event.getApplicationContext().getBeansOfType(IDataAccessItemBaseService.class);
        Collection<IDataAccessItemBaseService> allAccessBeans = beans.values();
        if (CollectionUtils.isEmpty(allAccessBeans)) {
            return;
        }
        for (IDataAccessItemBaseService baseService : allAccessBeans) {
            int typeOrder;
            int order = baseService.getOrder();
            AccessLevel accessLevel = baseService.getLevel();
            int n = typeOrder = accessLevel == AccessLevel.FORM ? 10000 : 0;
            if (baseService instanceof IDataAccessItemService) {
                this.accessItemServices.put((IDataAccessItemService)baseService, order + typeOrder);
            }
            if (!(baseService instanceof IDataExtendAccessItemService)) continue;
            this.extendAccessItemServices.put((IDataExtendAccessItemService)baseService, order + typeOrder);
        }
    }

    public List<IDataAccessItemService> getAccessBaseServices() {
        ArrayList<IDataAccessItemService> accessServices = new ArrayList<IDataAccessItemService>();
        if (!this.accessItemServices.isEmpty()) {
            this.accessItemServices.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> accessServices.add((IDataAccessItemService)x.getKey()));
        }
        return accessServices;
    }

    public List<IDataExtendAccessItemService> getAccessBaseExtendServices() {
        ArrayList<IDataExtendAccessItemService> accessServices = new ArrayList<IDataExtendAccessItemService>();
        if (!this.extendAccessItemServices.isEmpty()) {
            this.extendAccessItemServices.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> accessServices.add((IDataExtendAccessItemService)x.getKey()));
        }
        return accessServices;
    }
}

