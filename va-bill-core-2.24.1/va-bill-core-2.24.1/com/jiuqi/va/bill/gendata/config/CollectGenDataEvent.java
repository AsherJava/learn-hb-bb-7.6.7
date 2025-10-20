/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.gendata.config;

import com.jiuqi.va.bill.gendata.intf.GenDataEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CollectGenDataEvent
implements ApplicationContextAware {
    private List<GenDataEvent> genDataEventList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.genDataEventList = new ArrayList<GenDataEvent>();
        Map<String, GenDataEvent> events = applicationContext.getBeansOfType(GenDataEvent.class);
        events.forEach((k, v) -> this.genDataEventList.add((GenDataEvent)v));
    }

    public List<GenDataEvent> get() {
        if (this.genDataEventList == null) {
            this.genDataEventList = new ArrayList<GenDataEvent>();
        }
        return this.genDataEventList;
    }

    public int size() {
        if (this.genDataEventList == null) {
            this.genDataEventList = new ArrayList<GenDataEvent>();
        }
        return this.genDataEventList.size();
    }
}

