/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.event;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public abstract class ParamChangeEvent
extends ApplicationEvent {
    private final ChangeType type;
    private final List<String> keys;

    public ParamChangeEvent(ChangeType type, List<String> keys) {
        super(keys);
        this.type = type;
        this.keys = keys;
    }

    public ChangeType getType() {
        return this.type;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    @Override
    public List<String> getSource() {
        return this.getKeys();
    }

    public static class ChangeParam<T> {
        private final T newValue;
        private final T oldValue;

        public ChangeParam(T newValue, T oldValue) {
            this.newValue = newValue;
            this.oldValue = oldValue;
        }

        public T getNewValue() {
            return this.newValue;
        }

        public T getOldValue() {
            return this.oldValue;
        }

        public T getValue() {
            if (null != this.newValue) {
                return this.newValue;
            }
            return this.oldValue;
        }
    }

    public static enum ChangeType {
        ADD,
        DELETE,
        UPDATE;

    }
}

