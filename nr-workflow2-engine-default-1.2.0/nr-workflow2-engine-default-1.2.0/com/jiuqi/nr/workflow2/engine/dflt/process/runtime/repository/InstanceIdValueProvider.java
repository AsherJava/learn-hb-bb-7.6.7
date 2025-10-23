/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

class InstanceIdValueProvider
implements Closeable {
    private final ITempTableManager tempTableManager;
    private final Collection<String> instanceIds;
    private ITempTable tempTable;

    public InstanceIdValueProvider(Collection<String> instanceIds, ITempTableManager tempTableManager) {
        this.instanceIds = instanceIds;
        this.tempTableManager = tempTableManager;
    }

    public boolean isNeedUsingTempTable() {
        return this.instanceIds.size() > DataEngineUtil.getMaxInSize((IDatabase)DatabaseInstance.getDatabase());
    }

    public Collection<String> getInstanceIds() {
        return this.instanceIds;
    }

    public ITempTable getTempTable() {
        if (this.tempTable == null) {
            try {
                this.tempTable = this.tempTableManager.getOneKeyTempTable();
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u4e34\u65f6\u8868\u5931\u8d25\u3002", (Throwable)e);
            }
            ArrayList<Object[]> records = new ArrayList<Object[]>(this.instanceIds.size());
            for (String instanceId : this.instanceIds) {
                records.add(new Object[]{instanceId});
            }
            try {
                this.tempTable.insertRecords(records);
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u5199\u5165\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + this.tempTable.getTableName(), (Throwable)e);
            }
        }
        return this.tempTable;
    }

    @Override
    public void close() {
        if (this.tempTable != null) {
            try {
                this.tempTable.close();
            }
            catch (Exception e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u5173\u95ed\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + this.tempTable.getTableName(), (Throwable)e);
            }
        }
    }
}

