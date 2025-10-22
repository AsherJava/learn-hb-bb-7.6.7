/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.setting;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;

public interface IFieldsInfo {
    public int getFieldCount();

    public FieldType getDataType(int var1);

    public FieldDefine getFieldDefine(int var1);

    public int indexOf(String var1);

    public int indexOf(FieldDefine var1);

    public void setupField(int var1, FieldDefine var2);

    public void setupField(int var1, FieldType var2);

    public void reset();

    public void appendFields(int var1);
}

