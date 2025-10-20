/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.masterdata.intf;

import com.jiuqi.budget.masterdata.intf.MasterDataVersion;
import java.time.LocalDateTime;

public class MasterDataVersionData
implements MasterDataVersion {
    private String title;
    private String id;
    private LocalDateTime validTime;
    private LocalDateTime inValidTime;

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getValidTime() {
        return this.validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    @Override
    public LocalDateTime getInValidTime() {
        return this.inValidTime;
    }

    public void setInValidTime(LocalDateTime inValidTime) {
        this.inValidTime = inValidTime;
    }
}

