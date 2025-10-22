/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.event;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import java.util.List;

public class TaskGroupLinkChangeEvent
extends ParamChangeEvent {
    public TaskGroupLinkChangeEvent(ParamChangeEvent.ChangeType type, List<String> keys) {
        super(type, keys);
    }
}

