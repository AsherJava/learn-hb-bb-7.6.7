/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.service.impl;

import com.jiuqi.nr.configuration.event.SystemOptionEvent;
import com.jiuqi.nr.configuration.event.bean.EventBO;
import com.jiuqi.nr.configuration.service.ISystemOptionListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class SystemOptionListenerServiceImpl
implements ISystemOptionListenerService {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void publish(EventBO eventBo) {
        this.applicationContext.publishEvent(new SystemOptionEvent((Object)this, eventBo));
    }

    @Override
    public void publish(String key, Object value) {
        this.publish(new EventBO(key, value));
    }

    @Override
    public void publish(String key, Object value, Object oldValue) {
        this.publish(new EventBO(key, value, oldValue));
    }

    @Override
    public void publish(String key, String taskKey, String formSchemeKey, Object value, Object oldValue) {
        this.publish(new EventBO(key, value, oldValue, taskKey, formSchemeKey));
    }
}

