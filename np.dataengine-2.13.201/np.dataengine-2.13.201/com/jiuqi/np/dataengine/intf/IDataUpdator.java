/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import java.util.List;

public interface IDataUpdator {
    public IFieldsInfo getFieldsInfo();

    public DimensionValueSet getMasterKeys();

    public DimensionSet getMasterDimensions();

    public DimensionSet getRowDimensions();

    public IDataRow addInsertedRow() throws DataTypeException;

    public IDataRow addInsertedRow(DimensionValueSet var1) throws IncorrectQueryException;

    public IDataRow addDeletedRow(DimensionValueSet var1) throws IncorrectQueryException;

    public IDataRow addModifiedRow(DimensionValueSet var1) throws IncorrectQueryException;

    public void deleteAll() throws Exception;

    public void deleteAll(boolean var1) throws Exception;

    public boolean commitChanges() throws IncorrectQueryException, Exception;

    public boolean commitChanges(boolean var1) throws IncorrectQueryException, Exception;

    public boolean commitChanges(boolean var1, boolean var2) throws IncorrectQueryException, Exception;

    public void setDataChangeMonitor(IMonitor var1);

    public void needCheckDuplicateKeys(boolean var1);

    public void setOnlySaveData(boolean var1);

    public void resetDeleteAllExistingData();

    public void resetRows();

    public int getInsertCount();

    public void setValidExpression(List<IParsedExpression> var1);
}

