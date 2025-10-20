/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 */
package com.jiuqi.va.bill.mq;

import com.jiuqi.va.join.api.domain.JoinDeclare;

public class BillAfterQueueDeclare
implements JoinDeclare {
    private String name;
    private String title;

    public BillAfterQueueDeclare() {
    }

    public BillAfterQueueDeclare(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

