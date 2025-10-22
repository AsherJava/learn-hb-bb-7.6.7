/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.api;

import com.jiuqi.nr.data.checkdes.facade.obj.FieldMappingObj;
import com.jiuqi.nr.data.checkdes.facade.obj.FormulaMappingObj;

public interface ICKDParamMapping {
    public String getMDCode(String var1);

    public String getPeriod(String var1);

    public String getEntityId(String var1);

    public String getEntityData(String var1, String var2);

    public FormulaMappingObj getFormula(FormulaMappingObj var1);

    public FieldMappingObj getField(FieldMappingObj var1);
}

