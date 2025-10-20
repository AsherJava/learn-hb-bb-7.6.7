/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.event.DeleteAllRowEvent
 *  com.jiuqi.np.dataengine.event.DeleteRowEvent
 *  com.jiuqi.np.dataengine.event.InsertRowEvent
 *  com.jiuqi.np.dataengine.event.OperateRowEventHandler
 *  com.jiuqi.np.dataengine.event.RevokeRowEvent
 *  com.jiuqi.np.dataengine.event.UpdateRowEvent
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.OperateRowException
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.query;

import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.OperateRowEventHandler;
import com.jiuqi.np.dataengine.event.RevokeRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.OperateRowException;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;

public class GcDataRowEventHandler
implements OperateRowEventHandler {
    @Autowired
    private DataModelService dataModelService;

    private TableModelDefine checkTableNoOrg(String tableKey) throws Exception {
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineById(tableKey);
        if (tableDefine.getName().startsWith("MD_ORG")) {
            Object[] args = new String[]{"MD_ORG"};
            throw new ExecuteException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.notchangemdorgdatamsg", (Object[])args));
        }
        if (tableDefine.getName().startsWith("MD_")) {
            Object[] args = new String[]{"MD_"};
            throw new ExecuteException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.notchangemdorgdatamsg", (Object[])args));
        }
        return tableDefine;
    }

    public void beforeDelete(DeleteRowEvent deleteRowEvent) {
        if (deleteRowEvent.getDeleteRows() == null || deleteRowEvent.getDeleteRows().isEmpty()) {
            return;
        }
        try {
            this.checkTableNoOrg(deleteRowEvent.getTableKey());
        }
        catch (Exception e) {
            deleteRowEvent.setBreak(e.getMessage());
            e.printStackTrace();
        }
    }

    public void beforeAllDelete(DeleteAllRowEvent deleteAllRowEvent) {
        try {
            this.checkTableNoOrg(deleteAllRowEvent.getTableKey());
        }
        catch (Exception e) {
            deleteAllRowEvent.setBreak(e.getMessage());
            e.printStackTrace();
        }
    }

    public void beforeUpdate(UpdateRowEvent updateRowEvent) {
        if (updateRowEvent.getUpdateRows() == null || updateRowEvent.getUpdateRows().size() <= 0 || updateRowEvent.isOnlySaveData()) {
            return;
        }
        try {
            this.checkTableNoOrg(updateRowEvent.getTableKey());
        }
        catch (Exception e) {
            updateRowEvent.setBreak(e.getMessage());
            e.printStackTrace();
        }
    }

    public void beforeInsert(InsertRowEvent insertRowEvent) {
        if (insertRowEvent.getInsertRows() == null || insertRowEvent.getInsertRows().isEmpty()) {
            return;
        }
        try {
            this.checkTableNoOrg(insertRowEvent.getTableKey());
        }
        catch (Exception e) {
            insertRowEvent.setBreak(e.getMessage());
            e.printStackTrace();
        }
    }

    public void afterInsert(InsertRowEvent insertRowEvent) throws OperateRowException {
    }

    public void afterRevoke(RevokeRowEvent revokeRowEvent) {
    }

    public void afterUpdate(UpdateRowEvent updateRowEvent) throws OperateRowException {
    }

    public void afterDelete(DeleteRowEvent deleteRowEvent) throws OperateRowException {
    }
}

