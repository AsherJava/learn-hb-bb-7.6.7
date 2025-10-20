/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataProcessorIntf;
import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.CurrencyCellPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.NumberCellProperty;
import java.io.Serializable;

public final class CurrencyCellProperty
extends NumberCellProperty
implements CurrencyCellPropertyIntf,
CellDataProcessorIntf,
Serializable {
    private static final long serialVersionUID = 5187243397643190493L;
    public static final String[] CURRENCY_UNIT_NAMES = new String[]{"", "CNY", "USD", "EUR", "JPY", "HKD", "GBP", "CHF", "SUR", "CAD"};
    public static final String[] CURRENCY_UNIT_TITLES = new String[]{"", "\u4eba\u6c11\u5e01\u5143", "\u7f8e\u5143", "\u6b27\u5143", "\u65e5\u5143", "\u6e2f\u5143", "\u82f1\u9551", "\u745e\u58eb\u6cd5\u90ce", "\u4fc4\u7f57\u65af\u5362\u5e03", "\u52a0\u62ff\u5927\u5143"};
    public static final String[] CURRENCY_UNIT_SIGNS = new String[]{"", "\uffe5", "\uff04", "EUR", "J.\uffe5", "HK\uff04", "\uffe1", "SF.", "Rbs.", "Can.\uff04"};

    public CurrencyCellProperty(GridCell cell) {
        this(new CellDataProperty(cell));
    }

    public CurrencyCellProperty(CellDataPropertyIntf dataProperty) {
        super(dataProperty);
    }

    @Override
    public int getUnitIndex() {
        byte dataFormat = this.cellDataProperty.getDataFormat();
        return dataFormat & 0xF;
    }

    @Override
    public void setUnitIndex(int value) {
        if (value < 0 || value >= 10) {
            throw new IllegalArgumentException("\u8d27\u5e01\u7b26\u53f7\u88ab\u9519\u8bef\u8bbe\u7f6e,\u53ea\u80fd\u57280-9\u4e4b\u95f4!");
        }
        this.cellDataProperty.setDataFormat((byte)value);
    }

    @Override
    public int getUnitShowType() {
        return (this.cellDataProperty.getDataProperty() & 0x300) >> 8;
    }

    @Override
    public void setUnitShowType(int value) {
        if (value < 0 || value > 3) {
            throw new IllegalArgumentException("\u663e\u793a\u7c7b\u578b\u88ab\u9519\u8bef\u8bbe\u7f6e,\u5e94\u8be5\u88ab\u8bbe\u7f6e\u52300-3\u4e4b\u95f4!");
        }
        this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFFCFF | (value &= 3) << 8));
    }

    @Override
    public String getUnitTitle() {
        return CURRENCY_UNIT_TITLES[this.getUnitIndex()];
    }

    @Override
    public void setUnitTitle(String value) {
        int i = 0;
        boolean found = false;
        while (!found) {
            if (value.equals(CURRENCY_UNIT_TITLES[i])) {
                found = true;
                break;
            }
            ++i;
        }
        if (!found) {
            throw new IllegalArgumentException("\u6ca1\u6709\u8fd9\u6837\u7684\u8d27\u5e01\u5355\u4f4d!");
        }
        this.setUnitIndex(i);
    }

    @Override
    public boolean getShowCurrencyBox() {
        if (this.cellDataProperty.getDataType() == 3) {
            return (this.cellDataProperty.getDataProperty() & 0x400) != 0;
        }
        return false;
    }

    @Override
    public void setShowCurrencyBox(boolean value) {
        if (this.cellDataProperty.getDataType() == 3) {
            if (value) {
                this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x400));
            } else {
                this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFFBFF));
            }
        }
    }

    public String getUnitString() {
        return CURRENCY_UNIT_SIGNS[this.getUnitIndex()];
    }

    @Override
    public String getShowText(String storeText) {
        String showText = super.getShowText(storeText);
        int unitIndex = this.getUnitIndex();
        if (unitIndex >= 0) {
            int unitShowType = this.getUnitShowType();
            switch (unitShowType) {
                case 0: {
                    showText = CURRENCY_UNIT_SIGNS[unitIndex] + " " + showText;
                    break;
                }
                case 1: {
                    showText = CURRENCY_UNIT_NAMES[unitIndex] + " " + showText;
                    break;
                }
                case 2: {
                    showText = showText + " " + CURRENCY_UNIT_NAMES[unitIndex];
                    break;
                }
                case 3: {
                    showText = CURRENCY_UNIT_TITLES[unitIndex] + " " + showText;
                }
            }
        }
        return showText;
    }

    @Override
    public String parseShowText(String showText) {
        String number = null;
        int unitIndex = this.getUnitIndex();
        int unitShowType = this.getUnitShowType();
        String unitAdd = null;
        switch (unitShowType) {
            case 0: {
                unitAdd = CURRENCY_UNIT_SIGNS[unitIndex];
                if (showText.startsWith(unitAdd)) {
                    number = showText.substring(unitAdd.length());
                    break;
                }
                throw new IllegalArgumentException("\u5f00\u5934\u7684\u8d27\u5e01\u7b26\u53f7\u548c\u5b9a\u4e49\u7684\u8d27\u5e01\u7b26\u53f7\u4e0d\u7b26\uff01");
            }
            case 1: {
                unitAdd = CURRENCY_UNIT_NAMES[unitIndex];
                if (showText.startsWith(unitAdd)) {
                    number = showText.substring(unitAdd.length());
                    break;
                }
                throw new IllegalArgumentException("\u5f00\u5934\u7684\u8d27\u5e01\u82f1\u6587\u7f29\u5199\u548c\u5b9a\u4e49\u7684\u8d27\u5e01\u7b26\u53f7\u4e0d\u7b26\uff01");
            }
            case 2: {
                unitAdd = CURRENCY_UNIT_NAMES[unitIndex];
                if (showText.endsWith(unitAdd)) {
                    number = showText.substring(0, showText.length() - unitAdd.length());
                    break;
                }
                throw new IllegalArgumentException("\u5c3e\u90e8\u7684\u8d27\u5e01\u82f1\u6587\u7f29\u5199\u548c\u5b9a\u4e49\u7684\u8d27\u5e01\u7b26\u53f7\u4e0d\u7b26\uff01");
            }
            case 3: {
                unitAdd = CURRENCY_UNIT_TITLES[unitIndex];
                if (showText.endsWith(unitAdd)) {
                    number = showText.substring(0, showText.length() - unitAdd.length());
                    break;
                }
                throw new IllegalArgumentException("\u5c3e\u90e8\u7684\u8d27\u5e01\u540d\u79f0\u548c\u5b9a\u4e49\u7684\u8d27\u5e01\u7b26\u53f7\u4e0d\u7b26\uff01");
            }
            default: {
                number = showText;
            }
        }
        return super.parseShowText(number);
    }
}

