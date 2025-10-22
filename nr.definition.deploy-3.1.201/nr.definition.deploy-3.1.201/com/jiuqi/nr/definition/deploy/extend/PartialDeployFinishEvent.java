/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.extend;

import com.jiuqi.nr.definition.common.ParamResourceType;
import java.io.Serializable;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class PartialDeployFinishEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final EventSource source;

    public PartialDeployFinishEvent(EventSource source) {
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
        private final ParamResourceType type;
        private final String schemeKey;
        private final List<String> sourceKeys;

        public EventSource(ParamResourceType type, String schemeKey, List<String> sourceKeys) {
            this.type = type;
            this.schemeKey = schemeKey;
            this.sourceKeys = sourceKeys;
        }

        public ParamResourceType getType() {
            return this.type;
        }

        public String getSchemeKey() {
            return this.schemeKey;
        }

        public List<String> getSourceKeys() {
            return this.sourceKeys;
        }
    }
}

