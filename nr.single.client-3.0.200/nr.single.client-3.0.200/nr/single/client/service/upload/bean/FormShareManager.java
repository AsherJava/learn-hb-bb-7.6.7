/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package nr.single.client.service.upload.bean;

import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import nr.single.client.service.upload.bean.FormShareItem;

public class FormShareManager {
    private Queue<FormShareItem> queue;

    public Queue<FormShareItem> getQueue() {
        if (this.queue == null) {
            this.queue = new LinkedList<FormShareItem>();
        }
        return this.queue;
    }

    public void setQueue(Queue<FormShareItem> queue) {
        this.queue = queue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<FormDefine> getThreadFormsAsyn() {
        FormShareManager formShareManager = this;
        synchronized (formShareManager) {
            ArrayList<FormDefine> list = new ArrayList<FormDefine>();
            if (!this.getQueue().isEmpty()) {
                FormShareItem item = this.getQueue().poll();
                for (FormDefine form : item.getForms().values()) {
                    list.add(form);
                }
            }
            return list;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isThreadQueueEmpty() {
        FormShareManager formShareManager = this;
        synchronized (formShareManager) {
            return this.getQueue().isEmpty();
        }
    }
}

