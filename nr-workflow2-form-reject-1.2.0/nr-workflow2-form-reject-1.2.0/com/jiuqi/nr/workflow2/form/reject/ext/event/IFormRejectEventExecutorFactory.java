/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 */
package com.jiuqi.nr.workflow2.form.reject.ext.event;

import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEvent;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class IFormRejectEventExecutorFactory
implements InitializingBean {
    @Autowired
    private List<IFormRejectEvent> executors;
    private static IFormRejectEventExecutorFactory INSTANCE;

    public static IFormRejectEvent getFormRejectEvent(String eventId) {
        return IFormRejectEventExecutorFactory.INSTANCE.executors.stream().filter(e -> e.getEventId().equals(eventId)).findAny().orElse(null);
    }

    public static List<IFormRejectEvent> getAllEnableEvents(IProcessExecutePara args) {
        return IFormRejectEventExecutorFactory.INSTANCE.executors.stream().filter(e -> e.isEnabled(args)).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }
}

