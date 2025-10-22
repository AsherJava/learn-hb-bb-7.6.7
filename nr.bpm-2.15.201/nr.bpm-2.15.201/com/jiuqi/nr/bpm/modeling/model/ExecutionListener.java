/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class ExecutionListener
extends ActivitiExtension {
    protected ExecutionListener() {
        super("executionListener");
    }

    public void setEvent(String event) {
        Assert.notNull((Object)event, "'event' must not be null.");
        super.setProperty("event", event);
    }

    public void setClass(Class<?> clazz) {
        Assert.notNull(clazz, "'strategyType' must not be null.");
        super.setProperty("class", clazz.getName());
    }

    public static class ExecutionEndListener
    extends ExecutionListener {
        private static final Logger logger = LoggerFactory.getLogger(ExecutionEndListener.class);

        public ExecutionEndListener() {
            this.setEvent("end");
            try {
                Class<?> clazz = Class.forName("com.jiuqi.nr.bpm.impl.activiti6.ActivitiExecutionListener");
                this.setClass(clazz);
            }
            catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

