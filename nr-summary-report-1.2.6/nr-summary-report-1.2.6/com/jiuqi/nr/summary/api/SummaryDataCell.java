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
import com.jiuqi.nr.summary.api.BasicGetter;
import com.jiuqi.nr.summary.api.Ordered;

public interface SummaryDataCell
extends BasicGetter,
Ordered {
    public String getReportKey();

    public int getX();

    public int getY();

    public int getRowNum();

    public int getColNum();

    public String getExpression();

    public String getExpressionTitle();

    public String getReferDataFieldKey();

    public DataFieldGatherType getGatherType();

    public String getFieldName();

    public String getFieldTitle();

    public String getDataTableKey();

    public DataFieldType getFieldType();

    public int getPrecision();

    public int getDecimal();
}

