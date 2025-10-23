/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.summary.api;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.summary.api.BasicSetter;
import com.jiuqi.nr.summary.api.OrderSetter;
import com.jiuqi.nr.summary.api.SummaryDataCell;

public interface DesignSummaryDataCell
extends SummaryDataCell,
BasicSetter,
OrderSetter {
    public void setReportKey(String var1);

    public void setX(int var1);

    public void setY(int var1);

    public void setRowNum(int var1);

    public void setColNum(int var1);

    public void setExpression(String var1);

    public void setExpressionTitle(String var1);

    public void setReferDataFieldKey(String var1);

    public void setGatherType(DataFieldGatherType var1);

    public void setFieldName(String var1);

    public void setFieldTitle(String var1);

    public void setDataTableKey(String var1);

    public void setFieldType(DataFieldType var1);

    public void setPrecision(int var1);

    public void setDecimal(int var1);
}

