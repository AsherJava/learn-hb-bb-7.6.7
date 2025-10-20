/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.event;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import java.util.List;

public class FormSchemePeriodChangeEvent
extends ParamChangeEvent {
    public FormSchemePeriodChangeEvent(ParamChangeEvent.ChangeType type, List<String> keys) {
        super(type, keys);
    }
}

