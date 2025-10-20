/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.billcore.dto;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.List;

public class GcBillGroupDTO {
    private DefaultTableEntity master;
    private List<DefaultTableEntity> items;

    public GcBillGroupDTO(DefaultTableEntity master, List<DefaultTableEntity> items) {
        this.master = master;
        this.items = items;
    }

    public DefaultTableEntity getMaster() {
        return this.master;
    }

    public void setMaster(DefaultTableEntity master) {
        this.master = master;
    }

    public List<DefaultTableEntity> getItems() {
        return this.items;
    }

    public void setItems(List<DefaultTableEntity> items) {
        this.items = items;
    }
}

