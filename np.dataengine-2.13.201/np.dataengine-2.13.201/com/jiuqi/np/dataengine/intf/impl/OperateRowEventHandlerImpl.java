/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.OperateRowEventHandler;
import com.jiuqi.np.dataengine.event.RevokeRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.OperateRowException;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class OperateRowEventHandlerImpl
implements OperateRowEventHandler {
    @Override
    public void beforeDelete(DeleteRowEvent deleteRowEvent) {
    }

    @Override
    public void afterDelete(DeleteRowEvent deleteRowEvent) throws OperateRowException {
    }

    @Override
    public void beforeAllDelete(DeleteAllRowEvent deleteAllRowEvent) {
    }

    @Override
    public void beforeUpdate(UpdateRowEvent updateRowEvent) {
    }

    @Override
    public void afterUpdate(UpdateRowEvent updateRowEvent) throws OperateRowException {
    }

    @Override
    public void beforeInsert(InsertRowEvent insertRowEvent) {
    }

    @Override
    public void afterInsert(InsertRowEvent insertRowEvent) throws OperateRowException {
    }

    @Override
    public void afterRevoke(RevokeRowEvent revokeRowEvent) {
    }
}

