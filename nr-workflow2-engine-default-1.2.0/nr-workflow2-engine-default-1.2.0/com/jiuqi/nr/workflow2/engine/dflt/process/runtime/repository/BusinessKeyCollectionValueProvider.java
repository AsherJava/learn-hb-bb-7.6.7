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
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class BusinessKeyCollectionValueProvider
implements Closeable {
    private final ITempTableManager tempTableManager;
    private final IBusinessKeyCollection bizKeyCollection;
    private Map<String, Set<String>> dim2Values = new HashMap<String, Set<String>>(4);
    private Map<String, ITempTable> dim2TempTables;

    public BusinessKeyCollectionValueProvider(IBusinessKeyCollection bizKeyCollection, ITempTableManager tempTableManager) {
        this.bizKeyCollection = bizKeyCollection;
        this.tempTableManager = tempTableManager;
    }

    public Collection<String> getDimensionValues(String dimensionName) {
        Set<String> dimValues = this.dim2Values.get(dimensionName);
        if (dimValues == null) {
            dimValues = new HashSet<String>();
            for (IBusinessObject bizObject : this.bizKeyCollection.getBusinessObjects()) {
                dimValues.add(bizObject.getDimensions().getValue(dimensionName).toString());
            }
            this.dim2Values.put(dimensionName, dimValues);
        }
        return dimValues;
    }

    public Collection<String> getFormValues() {
        Set<String> formKeys = this.dim2Values.get("IST_FORMKEY");
        if (formKeys == null) {
            formKeys = new HashSet<String>();
            for (IBusinessObject bizObject : this.bizKeyCollection.getBusinessObjects()) {
                if (!(bizObject instanceof IFormObject)) continue;
                formKeys.add(((IFormObject)bizObject).getFormKey());
            }
            this.dim2Values.put("IST_FORMKEY", formKeys);
        }
        return formKeys;
    }

    public Collection<String> getFormGroupValues() {
        Set<String> formGroupKeys = this.dim2Values.get("IST_FORMGROUPKEY");
        if (formGroupKeys == null) {
            formGroupKeys = new HashSet<String>();
            for (IBusinessObject bizObject : this.bizKeyCollection.getBusinessObjects()) {
                if (!(bizObject instanceof IFormGroupObject)) continue;
                formGroupKeys.add(((IFormGroupObject)bizObject).getFormGroupKey());
            }
            this.dim2Values.put("IST_FORMGROUPKEY", formGroupKeys);
        }
        return formGroupKeys;
    }

    public boolean isDimensionNeedUsingTempTable(String dimensionName) {
        return this.isNeedTempTable(this.getDimensionValues(dimensionName).size());
    }

    public ITempTable getDimensionValueTable(String dimensionName) {
        ITempTable tempTable;
        if (this.dim2TempTables == null) {
            this.dim2TempTables = new HashMap<String, ITempTable>(2);
        }
        if ((tempTable = this.dim2TempTables.get(dimensionName)) == null) {
            try {
                tempTable = this.tempTableManager.getOneKeyTempTable();
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u4e34\u65f6\u8868\u5931\u8d25\u3002", (Throwable)e);
            }
            Collection<String> dimValues = this.getDimensionValues(dimensionName);
            ArrayList<Object[]> records = new ArrayList<Object[]>(dimValues.size());
            for (String dimValue : dimValues) {
                records.add(new Object[]{dimValue});
            }
            try {
                tempTable.insertRecords(records);
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u5199\u5165\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + tempTable.getTableName(), (Throwable)e);
            }
            this.dim2TempTables.put(dimensionName, tempTable);
        }
        return tempTable;
    }

    public boolean isFormNeedUsingTempTable() {
        return this.isNeedTempTable(this.getFormValues().size());
    }

    public ITempTable getFormValueTable() {
        ITempTable tempTable;
        if (this.dim2TempTables == null) {
            this.dim2TempTables = new HashMap<String, ITempTable>(2);
        }
        if ((tempTable = this.dim2TempTables.get("IST_FORMKEY")) == null) {
            try {
                tempTable = this.tempTableManager.getOneKeyTempTable();
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u4e34\u65f6\u8868\u5931\u8d25\u3002", (Throwable)e);
            }
            Collection<String> formKeys = this.getFormValues();
            ArrayList<Object[]> records = new ArrayList<Object[]>(formKeys.size());
            for (String formKey : formKeys) {
                records.add(new Object[]{formKey});
            }
            try {
                tempTable.insertRecords(records);
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u5199\u5165\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + tempTable.getTableName(), (Throwable)e);
            }
            this.dim2TempTables.put("IST_FORMKEY", tempTable);
        }
        return tempTable;
    }

    public boolean isFormGroupNeedUsingTempTable() {
        return this.isNeedTempTable(this.getFormGroupValues().size());
    }

    public ITempTable getFormGroupValueTable() {
        ITempTable tempTable;
        if (this.dim2TempTables == null) {
            this.dim2TempTables = new HashMap<String, ITempTable>(2);
        }
        if ((tempTable = this.dim2TempTables.get("IST_FORMGROUPKEY")) == null) {
            try {
                tempTable = this.tempTableManager.getOneKeyTempTable();
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u83b7\u53d6\u4e34\u65f6\u8868\u5931\u8d25\u3002", (Throwable)e);
            }
            Collection<String> formGroupKeys = this.getFormGroupValues();
            ArrayList<Object[]> records = new ArrayList<Object[]>(formGroupKeys.size());
            for (String formGroupKey : formGroupKeys) {
                records.add(new Object[]{formGroupKey});
            }
            try {
                tempTable.insertRecords(records);
            }
            catch (SQLException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u5199\u5165\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + tempTable.getTableName(), (Throwable)e);
            }
            this.dim2TempTables.put("IST_FORMGROUPKEY", tempTable);
        }
        return tempTable;
    }

    private boolean isNeedTempTable(int recordCount) {
        return recordCount > DataEngineUtil.getMaxInSize((IDatabase)DatabaseInstance.getDatabase());
    }

    @Override
    public void close() {
        if (this.dim2TempTables != null) {
            for (ITempTable tempTable : this.dim2TempTables.values()) {
                try {
                    tempTable.close();
                }
                catch (Exception e) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u5173\u95ed\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + tempTable.getTableName(), (Throwable)e);
                }
            }
        }
    }
}

