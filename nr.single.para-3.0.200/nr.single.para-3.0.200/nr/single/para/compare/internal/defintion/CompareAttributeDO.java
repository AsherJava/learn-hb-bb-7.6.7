/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package nr.single.para.compare.internal.defintion;

import com.jiuqi.nvwa.definition.common.ColumnModelType;
import nr.single.para.compare.definition.ICompareAttribute;
import nr.single.para.compare.definition.common.CompareDataType;

public class CompareAttributeDO
implements ICompareAttribute {
    private static final long serialVersionUID = 1L;
    private String key;
    private String code;
    private String title;
    private CompareDataType compareType;
    private ColumnModelType columnType;
    private int precision;
    private int decimal;
    private boolean nullAble = true;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public CompareDataType getCompareType() {
        return this.compareType;
    }

    @Override
    public ColumnModelType getColumnType() {
        return this.getColumnType();
    }

    @Override
    public int getPrecision() {
        return this.precision;
    }

    @Override
    public int getDecimal() {
        return this.decimal;
    }

    @Override
    public boolean isNullAble() {
        return this.nullAble;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompareType(CompareDataType compareType) {
        this.compareType = compareType;
    }

    public void setColumnType(ColumnModelType columnType) {
        this.columnType = columnType;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }
}

