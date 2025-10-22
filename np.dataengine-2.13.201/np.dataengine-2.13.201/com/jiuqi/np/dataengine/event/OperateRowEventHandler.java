/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.RevokeRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.OperateRowException;

public interface OperateRowEventHandler {
    public void beforeDelete(DeleteRowEvent var1);

    public void afterDelete(DeleteRowEvent var1) throws OperateRowException;

    public void beforeAllDelete(DeleteAllRowEvent var1);

    public void beforeUpdate(UpdateRowEvent var1);

    public void afterUpdate(UpdateRowEvent var1) throws OperateRowException;

    public void beforeInsert(InsertRowEvent var1);

    public void afterInsert(InsertRowEvent var1) throws OperateRowException;

    public void afterRevoke(RevokeRowEvent var1);

    default public void exceptionHandler(Exception ex, InsertRowEvent insertRowEvent, UpdateRowEvent updateRowEvent, DeleteRowEvent deleteRowEvent, DeleteAllRowEvent deleteAllRowEvent) {
    }
}

