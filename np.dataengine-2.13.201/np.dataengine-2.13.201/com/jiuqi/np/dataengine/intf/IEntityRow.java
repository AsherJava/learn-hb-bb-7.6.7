/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IEntityItem;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

@Deprecated
public interface IEntityRow
extends IEntityItem,
IDataRow {
    public String[] getParentsEntityKeyDataPath();

    public void buildRow();

    public String getParentsTitlePath(String var1);

    public boolean isParentViewRow();

    public Integer getEntityRowType();

    public AbstractData getValue(String var1) throws RuntimeException, DataTypeException;

    public String getAsString(String var1) throws RuntimeException, DataTypeException;

    @Override
    @Deprecated
    public AbstractData getValue(FieldDefine var1) throws RuntimeException, DataTypeException;

    @Override
    @Deprecated
    public IFieldsInfo getFieldsInfo();

    @Override
    @Deprecated
    public Object getKeyValue(FieldDefine var1);

    @Override
    @Deprecated
    public String getAsString(FieldDefine var1) throws RuntimeException, DataTypeException;

    public int getFieldIndex(String var1);

    public List<String> getFieldsCode();
}

