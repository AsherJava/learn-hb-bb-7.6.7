/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.ruler.CheckFieldLength;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckFieldLengthBeforeTempSave
extends CheckFieldLength {
    private Set<String> triggerTypes;

    @Override
    public String getName() {
        return "BillCheckFieldLengthBeforeTempSave";
    }

    @Override
    public Set<String> getTriggerTypes() {
        if (this.triggerTypes == null) {
            this.triggerTypes = Stream.of("before-bill-temp-save").collect(Collectors.toSet());
        }
        return this.triggerTypes;
    }
}

