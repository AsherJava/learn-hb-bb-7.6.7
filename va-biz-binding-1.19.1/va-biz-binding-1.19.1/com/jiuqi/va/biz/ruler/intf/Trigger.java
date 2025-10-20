/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import java.util.UUID;
import java.util.stream.Stream;

public interface Trigger {
    public String getTriggerType();

    public String getTriggerTitle();

    public Stream<UUID> getFormulaList();
}

