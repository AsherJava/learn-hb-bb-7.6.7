/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.event;

import com.jiuqi.nr.attachment.event.FileDeleteSource;
import org.springframework.context.ApplicationEvent;

public class FileDeleteEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1255232209438546005L;

    public FileDeleteEvent(Object source) {
        super(source);
    }

    @Override
    public FileDeleteSource getSource() {
        return (FileDeleteSource)this.source;
    }
}

