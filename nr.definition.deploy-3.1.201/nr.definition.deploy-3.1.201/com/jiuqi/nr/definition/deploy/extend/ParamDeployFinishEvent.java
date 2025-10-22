/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.definition.deploy.extend;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.Serializable;
import org.springframework.context.ApplicationEvent;

public class ParamDeployFinishEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    public final EventSource source;

    public ParamDeployFinishEvent(EventSource source) {
        super(source);
        this.source = source;
    }

    @Override
    public EventSource getSource() {
        return this.source;
    }

    public static class EventSource
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final FormSchemeDefine formScheme;

        public EventSource(FormSchemeDefine formScheme) {
            this.formScheme = formScheme;
        }

        public FormSchemeDefine getFormScheme() {
            return this.formScheme;
        }
    }
}

