/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.bus;

public final class BusEvent {
    private String busEventType;

    public BusEvent(String busEventType) {
        this.busEventType = busEventType;
    }

    public String getBusEventType() {
        return this.busEventType;
    }

    public void setBusEventType(String busEventType) {
        this.busEventType = busEventType;
    }
}

