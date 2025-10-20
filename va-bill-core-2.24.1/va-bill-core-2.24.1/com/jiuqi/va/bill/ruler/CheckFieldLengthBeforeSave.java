/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.ruler.CheckFieldLength;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckFieldLengthBeforeSave
extends CheckFieldLength {
    private Set<String> triggerTypes;

    @Override
    public String getName() {
        return "BillCheckFieldLengthBeforeSave";
    }

    @Override
    public Set<String> getTriggerTypes() {
        if (this.triggerTypes == null) {
            this.triggerTypes = Stream.of("before-save").collect(Collectors.toSet());
        }
        return this.triggerTypes;
    }
}

