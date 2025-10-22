/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.OperateRowEventHandler;
import com.jiuqi.np.dataengine.event.RevokeRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.OperateRowException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class OperateRowEventListener
implements InitializingBean {
    @Autowired(required=false)
    private List<OperateRowEventHandler> handlers;

    public void beforeAllDelete(DeleteAllRowEvent deleteAllRowEvent) {
        if (deleteAllRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.beforeAllDelete(deleteAllRowEvent);
            if (!deleteAllRowEvent.isBreak()) continue;
            break;
        }
    }

    public void beforeDelete(DeleteRowEvent deleteRowEvent) {
        if (deleteRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.beforeDelete(deleteRowEvent);
            if (!deleteRowEvent.isBreak()) continue;
            break;
        }
    }

    public void afterDelete(DeleteRowEvent deleteRowEvent) throws OperateRowException {
        if (deleteRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.afterDelete(deleteRowEvent);
        }
    }

    public void beforeUpdate(UpdateRowEvent updateRowEvent) {
        if (updateRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.beforeUpdate(updateRowEvent);
        }
    }

    public void afterUpdate(UpdateRowEvent updateRowEvent) throws OperateRowException {
        if (updateRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.afterUpdate(updateRowEvent);
        }
    }

    public void beforeInsert(InsertRowEvent insertRowEvent) {
        if (insertRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.beforeInsert(insertRowEvent);
        }
    }

    public void afterInsert(InsertRowEvent insertRowEvent) throws OperateRowException {
        if (insertRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.afterInsert(insertRowEvent);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.handlers == null) {
            this.handlers = new ArrayList<OperateRowEventHandler>();
        }
    }

    public boolean hasListerner() {
        return this.handlers != null && this.handlers.size() > 0;
    }

    public void afterRevoke(RevokeRowEvent revokeRowEvent) {
        if (revokeRowEvent == null) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.afterRevoke(revokeRowEvent);
        }
    }

    public void exceptionHandler(Exception ex, InsertRowEvent insertRowEvent, UpdateRowEvent updateRowEvent, DeleteRowEvent deleteRowEvent, DeleteAllRowEvent deleteAllRowEvent) {
        if (this.handlers == null || this.handlers.isEmpty()) {
            return;
        }
        for (OperateRowEventHandler handler : this.handlers) {
            handler.exceptionHandler(ex, insertRowEvent, updateRowEvent, deleteRowEvent, deleteAllRowEvent);
        }
    }
}

