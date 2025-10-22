/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 */
package nr.midstore2.data.work.internal.thread;

import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import java.util.LinkedList;
import java.util.Queue;

public class TableShareManager {
    private Queue<DETableModel> queue;

    public Queue<DETableModel> getQueue() {
        if (this.queue == null) {
            this.queue = new LinkedList<DETableModel>();
        }
        return this.queue;
    }

    public void setQueue(Queue<DETableModel> queue) {
        this.queue = queue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DETableModel getThreadFormsAsyn() {
        TableShareManager tableShareManager = this;
        synchronized (tableShareManager) {
            if (!this.getQueue().isEmpty()) {
                DETableModel item = this.getQueue().poll();
                return item;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isThreadQueueEmpty() {
        TableShareManager tableShareManager = this;
        synchronized (tableShareManager) {
            return this.getQueue().isEmpty();
        }
    }
}

