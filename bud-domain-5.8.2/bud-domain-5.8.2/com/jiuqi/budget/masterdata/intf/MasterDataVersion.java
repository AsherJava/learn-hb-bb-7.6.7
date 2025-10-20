/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.masterdata.intf;

import java.time.LocalDateTime;

public interface MasterDataVersion {
    public String getTitle();

    public String getId();

    public LocalDateTime getValidTime();

    public LocalDateTime getInValidTime();
}

