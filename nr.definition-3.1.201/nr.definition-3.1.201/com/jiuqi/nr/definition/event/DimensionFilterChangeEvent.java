/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.event;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import java.util.List;

public class DimensionFilterChangeEvent
extends ParamChangeEvent {
    public DimensionFilterChangeEvent(ParamChangeEvent.ChangeType type, List<String> tasks) {
        super(type, tasks);
    }
}

