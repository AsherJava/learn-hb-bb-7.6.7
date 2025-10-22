/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common.observer;

import com.jiuqi.nr.definition.common.observer.Observer;
import com.jiuqi.nr.definition.common.observer.ObserverAnno;
import com.jiuqi.nr.definition.common.observer.TaskObserverable;
import java.util.Collection;
import java.util.Map;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ObserverScanner
implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(ObserverAnno.class);
            if (beans == null || beans.size() == 0) {
                return;
            }
            Collection<Object> observers = beans.values();
            TaskObserverable observerable = TaskObserverable.getInstance();
            for (Object obj : observers) {
                if (!(obj instanceof Observer)) continue;
                observerable.addObserver((Observer)obj);
            }
        }
    }
}

