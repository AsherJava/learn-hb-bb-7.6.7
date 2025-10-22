/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public interface IDataRow {
    public IFieldsInfo getFieldsInfo();

    public DimensionValueSet getMasterKeys();

    public DimensionSet getMasterDimensions();

    public DimensionSet getRowDimensions();

    public DimensionValueSet getRowKeys();

    @Deprecated
    public int getGroupingFlag();

    public String getRecKey();

    public Object getKeyValue(FieldDefine var1);

    public AbstractData getValue(int var1) throws DataTypeException;

    default public Object getValueObject(int fieldIndex) {
        return null;
    }

    public AbstractData getValue(FieldDefine var1) throws RuntimeException, DataTypeException;

    public String getAsString(int var1) throws DataTypeException;

    public String getAsString(FieldDefine var1) throws RuntimeException, DataTypeException;

    public void setValue(int var1, Object var2);

    public void setValue(FieldDefine var1, Object var2);

    public void setAsString(int var1, String var2);

    public void setAsString(FieldDefine var1, String var2);

    public void validateAll() throws Exception;

    public void validateAll(boolean var1) throws Exception;

    public boolean validateExpression(List<IParsedExpression> var1) throws ExpressionValidateException;

    public boolean judge(IExpression var1, QueryContext var2);

    @Deprecated
    public int getGroupingLevel();

    @Deprecated
    public int getParentLevel();

    public boolean isFilledRow();

    public void setRecordKey(String var1);

    default public int getGroupTreeDeep() {
        return -1;
    }
}

