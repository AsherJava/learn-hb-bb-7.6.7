/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package nr.single.para.compare.definition;

import com.jiuqi.nvwa.definition.common.ColumnModelType;
import nr.single.para.compare.definition.common.CompareDataType;

public interface ICompareAttribute {
    public String getKey();

    public String getCode();

    public String getTitle();

    public CompareDataType getCompareType();

    public ColumnModelType getColumnType();

    public int getPrecision();

    public int getDecimal();

    public boolean isNullAble();
}

