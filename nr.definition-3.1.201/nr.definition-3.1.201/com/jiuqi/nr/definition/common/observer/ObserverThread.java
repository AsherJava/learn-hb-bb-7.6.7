/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common.observer;

import com.jiuqi.nr.definition.common.observer.Observer;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ObserverThread
implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ObserverThread.class);
    private Observer observer;
    private UUID taskID;

    public ObserverThread(Observer observer, UUID taskID) {
        this.observer = observer;
        this.taskID = taskID;
    }

    @Override
    public void run() {
        try {
            this.observer.excute(this.taskID);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

