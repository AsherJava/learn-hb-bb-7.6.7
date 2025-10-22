/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import java.util.Date;

public interface DesignUniversalFieldDefine
extends IMetaItem,
UniversalFieldDefine {
    public void setCode(String var1);

    public void setDescription(String var1);

    public void setType(FieldType var1);

    public void setSize(int var1);

    public void setNullable(boolean var1);

    public void setTemporary(boolean var1);

    public void setNeedSynchronize(boolean var1);

    public void setFractionDigits(int var1);

    public void setValueType(FieldValueType var1);

    public void setDefaultValue(String var1);

    public void setDBDefaultValue(String var1);

    public void setVerification(String var1);

    public void setCalculation(String var1);

    public void setOwnerTableKey(String var1);

    public void setReferFieldKey(String var1);

    public void setTableName(String var1);

    public void setFieldName(String var1);

    public void setUpdateTime(Date var1);

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setDbNullAble(boolean var1);

    public void setUseAuthority(boolean var1);
}

