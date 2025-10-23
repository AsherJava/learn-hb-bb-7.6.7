/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.execute.events;

import com.jiuqi.nr.workflow2.service.execute.events.StartProcessInstancePosEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {
    @EventListener
    public void handleMyCustomEvent(StartProcessInstancePosEvent event) {
        System.out.println("\u63a5\u6536\u5230\u4e8b\u4ef6: " + event.getSource());
    }
}

