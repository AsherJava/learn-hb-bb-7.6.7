/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.join.api.domain;

import com.jiuqi.va.join.api.domain.ListenerStatus;

public class ListenerChangeStatus {
    private String joinName;
    private ListenerStatus listenerStatus;

    public ListenerChangeStatus() {
    }

    public ListenerChangeStatus(String joinName, ListenerStatus listenerStatus) {
        this.joinName = joinName;
        this.listenerStatus = listenerStatus;
    }

    public String getJoinName() {
        return this.joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public ListenerStatus getListenerStatus() {
        return this.listenerStatus;
    }

    public void setListenerStatus(ListenerStatus listenerStatus) {
        this.listenerStatus = listenerStatus;
    }
}

