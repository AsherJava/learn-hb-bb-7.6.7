/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class SumResultSaver {
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;

    public void doSave(List<SumCell> cells, DimensionValueSet dimensions, ResultSet rs) throws Exception {
        IDataQuery query = this.dataAccessProvider.newDataQuery();
        HashMap<String, FieldDefine> fieldMap = new HashMap<String, FieldDefine>();
        for (SumCell cell : cells) {
            FieldDefine destField = cell.getDestField();
            query.addColumn(destField);
            fieldMap.put(cell.getAlias(), destField);
        }
        query.setMasterKeys(dimensions);
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        IDataUpdator updator = query.openForUpdate(executorContext, true);
        ResultSetMetaData metaData = rs.getMetaData();
        while (rs.next()) {
            IDataRow row = updator.addInsertedRow(new DimensionValueSet(dimensions));
            for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                FieldDefine field = (FieldDefine)fieldMap.get(columnName);
                if (field == null) continue;
                row.setValue(field, value);
            }
        }
        updator.commitChanges();
    }
}

