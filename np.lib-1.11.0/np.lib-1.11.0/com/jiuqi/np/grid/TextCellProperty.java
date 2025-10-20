/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid;

import com.jiuqi.np.grid.CellDataProcessorIntf;
import com.jiuqi.np.grid.CellDataProperty;
import com.jiuqi.np.grid.CellDataPropertyIntf;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.TextCellPropertyIntf;

public class TextCellProperty
implements TextCellPropertyIntf,
CellDataProcessorIntf {
    protected CellDataPropertyIntf cellDataProperty;

    public TextCellProperty(GridCell cell) {
        this(new CellDataProperty(cell));
    }

    public TextCellProperty(CellDataPropertyIntf dataProperty) {
        this.cellDataProperty = dataProperty;
    }

    public CellDataPropertyIntf getCellDataProperty() {
        return this.cellDataProperty;
    }

    @Override
    public boolean getMultiLine() {
        return (this.cellDataProperty.getDataFlag() & 1) != 0;
    }

    @Override
    public void setMultiLine(boolean value) {
        if (value) {
            this.cellDataProperty.setDataFlag((byte)(this.cellDataProperty.getDataFlag() | 1));
        } else {
            this.cellDataProperty.setDataFlag((byte)(this.cellDataProperty.getDataFlag() & 0xFFFFFFFE));
        }
    }

    @Override
    public boolean getAllowMultiLine() {
        return (this.cellDataProperty.getDataFlag() & 2) != 0;
    }

    @Override
    public void setAllowMultiLine(boolean value) {
        if (value) {
            this.cellDataProperty.setDataFlag((byte)(this.cellDataProperty.getDataFlag() | 2));
        } else {
            this.cellDataProperty.setDataFlag((byte)(this.cellDataProperty.getDataFlag() & 0xFFFFFFFD));
        }
    }

    @Override
    public int getCharCase() {
        return this.cellDataProperty.getDataFlag() >> 2;
    }

    @Override
    public void setCharCase(int value) {
        if (value < 0 || value >= 3) {
            throw new IllegalArgumentException("\u9519\u8bef\u8bbe\u7f6e\u4e86\u6587\u672c\u5927\u5c0f\u5199\u60c5\u51b5,\u53ea\u80fd\u57280-2\u4e4b\u95f4\uff01");
        }
        int data = (value & 3) << 2;
        this.cellDataProperty.setDataFlag((byte)(this.cellDataProperty.getDataFlag() & 0xFFFFFFF3 | data));
    }

    @Override
    public int getMaxLength() {
        return this.cellDataProperty.getDataProperty();
    }

    @Override
    public void setMaxLength(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("\u4e0d\u80fd\u8bbe\u7f6e\u6587\u672c\u7684\u6700\u5927\u957f\u5ea6\u4e3a\u8d1f\u6570\uff01");
        }
        this.cellDataProperty.setDataProPerty((short)value);
    }

    @Override
    public String getEditText(String storeText) {
        return storeText;
    }

    @Override
    public String getShowText(String storeText) {
        return storeText;
    }

    @Override
    public String parseEditText(String editText) {
        if (editText != null) {
            if (editText == "") {
                return "";
            }
            String storeText = null;
            int charCase = this.getCharCase();
            switch (charCase) {
                case 0: {
                    storeText = editText;
                    break;
                }
                case 2: {
                    storeText = editText.toLowerCase();
                    break;
                }
                case 1: {
                    storeText = editText.toUpperCase();
                    break;
                }
                default: {
                    storeText = editText;
                }
            }
            int textLength = storeText.length();
            int maxLength = this.getMaxLength();
            if (maxLength != 0 && textLength > maxLength) {
                storeText = storeText.substring(0, maxLength);
            }
            return storeText;
        }
        throw new IllegalArgumentException("\u4e0d\u80fd\u89e3\u6790\u6307\u5411null\u7684\u5b57\u7b26\u4e32\uff01");
    }

    @Override
    public String parseShowText(String showText) {
        return this.parseEditText(showText);
    }
}

