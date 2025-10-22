/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import java.util.List;

public interface IDataChangeListener {
    public void onDataChange(UpdateDataSet var1);

    default public void onDataChange(IMonitor monitor, UpdateDataSet updateDatas) {
        this.onDataChange(updateDatas);
    }

    public void beforeDelete(List<DimensionValueSet> var1, IFieldsInfo var2);

    public void beforeUpdate(List<IDataRow> var1);

    @Deprecated
    default public boolean beforeDeleteAll(DimensionValueSet deleteKeys, IFieldsInfo fieldsInfo) {
        return true;
    }

    default public boolean beforeDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys, IFieldsInfo fieldsInfo) {
        this.beforeDeleteAll(deleteKeys, fieldsInfo);
        return true;
    }

    default public void onCommitException(Exception ex, List<InsertRowEvent> insertRowEvents, List<UpdateRowEvent> updateRowEvents, List<DeleteRowEvent> deleteRowEvents, List<DeleteAllRowEvent> deleteAllRowEvents) {
    }

    default public void afterDelete(List<DimensionValueSet> delRowKeys, IFieldsInfo fieldsInfo) {
    }

    default public void afterUpdate(List<IDataRow> updateRows) {
    }

    default public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys, IFieldsInfo fieldsInfo) {
    }

    default public void afterDeleteAll(DimensionValueSet masterKeys, IFieldsInfo fieldsInfo, IASTNode filterNode) {
    }

    default public boolean supportDeleteAllWithFilter() {
        return false;
    }

    public void finish();
}

