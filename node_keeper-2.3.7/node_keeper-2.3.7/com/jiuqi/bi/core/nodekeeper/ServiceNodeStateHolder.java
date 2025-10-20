/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.nodekeeper;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceNodeStateHolder {
    private ServiceNodeState state = ServiceNodeState.STOP;
    private static final ServiceNodeStateHolder instance = new ServiceNodeStateHolder();
    private static final List<StateChangeCallBack> callbacks = new CopyOnWriteArrayList<StateChangeCallBack>();

    private ServiceNodeStateHolder() {
    }

    public static ServiceNodeStateHolder getInstance() {
        return instance;
    }

    public static void setState(ServiceNodeState state) {
        ServiceNodeStateHolder.instance.state = state;
        for (StateChangeCallBack callback : callbacks) {
            callback.change(state);
        }
    }

    public static ServiceNodeState getState() {
        return ServiceNodeStateHolder.instance.state;
    }

    public static void addStateChangeCallback(StateChangeCallBack changeCallBack) {
        callbacks.add(changeCallBack);
    }

    public static interface StateChangeCallBack {
        public void change(ServiceNodeState var1);
    }
}

