/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common.observer;

import com.jiuqi.nr.definition.common.observer.Observer;
import com.jiuqi.nr.definition.common.observer.ObserverAnno;
import com.jiuqi.nr.definition.common.observer.ObserverThread;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ObserverAnno
class TaskObserverable {
    private List<Observer> list;

    public static final TaskObserverable getInstance() {
        return ObserverIniter.INSTANCE;
    }

    private TaskObserverable() {
    }

    public void addObserver(Observer observer) {
        if (this.list == null) {
            this.list = new ArrayList<Observer>();
        }
        this.list.add(observer);
    }

    public void notify(UUID taskID) throws Exception {
        if (this.list == null || this.list.isEmpty()) {
            return;
        }
        for (Observer observer : this.list) {
            if (observer.isAsyn()) {
                ObserverThread thread = new ObserverThread(observer, taskID);
                Thread t = new Thread(thread);
                t.start();
                continue;
            }
            observer.excute(taskID);
        }
    }

    private static class ObserverIniter {
        private static final TaskObserverable INSTANCE = new TaskObserverable();

        private ObserverIniter() {
        }
    }
}

