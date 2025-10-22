/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import java.util.List;

public interface IMonitor {
    public void start();

    public boolean isCancel();

    default public void canceling(String msg) {
    }

    default public void canceled(String msg, Object data) {
    }

    public void error(FormulaCheckEventImpl var1);

    public void error(CheckExpression var1, QueryContext var2) throws SyntaxException, DataTypeException;

    public void message(String var1, Object var2);

    public void error(String var1, Object var2);

    public void debug(String var1, DataEngineConsts.DebugLogType var2);

    public void onProgress(double var1);

    public void finish();

    public void exception(Exception var1);

    public void onDataChange(UpdateDataSet var1);

    public void beforeDelete(List<DimensionValueSet> var1);

    public void beforeUpdate(List<IDataRow> var1);

    @Deprecated
    default public boolean beforeDeleteAll(DimensionValueSet deleteKeys) {
        return true;
    }

    default public boolean beforeDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
        this.beforeDeleteAll(deleteKeys);
        return true;
    }

    default public void onCommitException(Exception ex, List<InsertRowEvent> insertRowEvents, List<UpdateRowEvent> updateRowEvents, List<DeleteRowEvent> deleteRowEvents, List<DeleteAllRowEvent> deleteAllRowEvents) {
    }

    default public void afterDelete(List<DimensionValueSet> delRowKeys) {
    }

    default public void afterUpdate(List<IDataRow> updateRows) {
    }

    @Deprecated
    default public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
    }

    default public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys, IASTNode filterNode) {
    }

    default public IDatabase getDatabase() {
        return null;
    }

    default public boolean isDebug() {
        return false;
    }
}

