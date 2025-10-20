/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataProcessorIntf;
import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.chinese.ChineseDouble;
import java.io.Serializable;
import java.math.BigDecimal;

public class NumberCellProperty
implements NumberCellPropertyIntf,
CellDataProcessorIntf,
Serializable {
    private static final long serialVersionUID = -6947795237215037918L;
    protected CellDataPropertyIntf cellDataProperty;
    private static final BigDecimal FLOOR_DELTA = new BigDecimal("0.5");
    private static final BigDecimal BIG_ZERO = new BigDecimal("0");
    private static final BigDecimal BIG_100 = new BigDecimal("100");

    public NumberCellProperty(CellDataPropertyIntf dataProperty) {
        this.cellDataProperty = dataProperty;
    }

    public NumberCellProperty(GridCell cell) {
        this(new CellDataProperty(cell));
    }

    public CellDataPropertyIntf getCellDataProperty() {
        return this.cellDataProperty;
    }

    @Override
    public int getDecimal() {
        return this.cellDataProperty.getDataFlag();
    }

    @Override
    public void setDecimal(int value) {
        if (value <= 15) {
            if (value < 0) {
                value = 0;
            }
        } else {
            throw new IllegalArgumentException("\u9519\u8bef\u8bbe\u7f6e\u4e86\u6570\u5b57\u7684\u5c0f\u6570\u4f4d\u6570\uff0c\u5e94\u8be5\u57280-15\u4e4b\u95f4\uff01");
        }
        this.cellDataProperty.setDataFlag((byte)value);
    }

    @Override
    public int getPrecision() {
        return this.cellDataProperty.getDataProperty() & 0x1F;
    }

    @Override
    public void setPrecision(int value) {
        if (value < 0 || value > 32) {
            throw new IllegalArgumentException("\u9519\u8bef\u8bbe\u7f6e\u4e86\u6570\u5b57\u7684\u7cbe\u5ea6\uff0c\u5e94\u8be5\u57280-32\u4e4b\u95f4\uff01");
        }
        int data = value & 0x1F;
        this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFFFE0 | data));
    }

    @Override
    public boolean getThoundsMark() {
        return (this.cellDataProperty.getDataProperty() & 0x8000) != 0;
    }

    @Override
    public void setThoundsMarks(boolean value) {
        if (value) {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x8000));
        } else {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFF7FFF));
        }
    }

    @Override
    public boolean getBracketNegative() {
        return (this.cellDataProperty.getDataProperty() & 0x4000) != 0;
    }

    @Override
    public void setBracketNegative(boolean value) {
        if (value) {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x4000));
        } else {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFBFFF));
        }
    }

    @Override
    public boolean getWarningNegative() {
        return (this.cellDataProperty.getDataProperty() & 0x2000) != 0;
    }

    @Override
    public void setWarningNegative(boolean value) {
        if (value) {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x2000));
        } else {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFDFFF));
        }
    }

    @Override
    public boolean getChineseNumber() {
        return (this.cellDataProperty.getDataProperty() & 0x1000) != 0;
    }

    @Override
    public void setChineseNumber(boolean value) {
        if (value) {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x1000));
        } else {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFEFFF));
        }
    }

    @Override
    public boolean getBigChineseChar() {
        return (this.cellDataProperty.getDataProperty() & 0x800) != 0;
    }

    @Override
    public void setBigChineseChar(boolean value) {
        if (value) {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x800));
        } else {
            this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFF7FF));
        }
    }

    @Override
    public boolean getIsPercent() {
        if (this.cellDataProperty.getDataType() == 2) {
            return (this.cellDataProperty.getDataProperty() & 0x400) != 0;
        }
        return false;
    }

    @Override
    public void setIsPercent(boolean value) {
        if (this.cellDataProperty.getDataType() == 2) {
            if (value) {
                this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() | 0x400));
            } else {
                this.cellDataProperty.setDataProPerty((short)(this.cellDataProperty.getDataProperty() & 0xFFFFFBFF));
            }
        }
    }

    @Override
    public String getEditText(String storeText) {
        return storeText;
    }

    @Override
    public String getShowText(String storeText) {
        if (storeText != null) {
            String showText;
            if (StringUtils.isEmpty(storeText)) {
                showText = "";
            } else {
                int decimal;
                BigDecimal value;
                String tempText = storeText;
                tempText = StringUtils.eliminate(tempText, ',');
                try {
                    value = new BigDecimal(tempText);
                }
                catch (NumberFormatException e) {
                    return storeText;
                }
                boolean isNegtive = value.compareTo(BIG_ZERO) < 0;
                boolean isPercent = this.getIsPercent();
                boolean showThoundsMark = this.getThoundsMark();
                if (isNegtive) {
                    value = value.abs();
                }
                if (isPercent) {
                    value = value.multiply(BIG_100);
                }
                if ((decimal = this.getDecimal()) == 15) {
                    decimal = 0;
                }
                if (!this.getChineseNumber()) {
                    showText = NumberCellProperty.formatNumberString(value, decimal, this.getThoundsMark());
                    if (isPercent) {
                        showText = showText + "%";
                    }
                    if (isNegtive) {
                        showText = this.getBracketNegative() ? "(" + showText + ")" : "-" + showText;
                    }
                } else {
                    value = NumberCellProperty.trimDouble(value, decimal);
                    if (isPercent) {
                        ChineseDouble cnDouble = new ChineseDouble(value.divide(BIG_100, 4).doubleValue());
                        showText = cnDouble.getPercentForm(decimal, showThoundsMark, this.getBigChineseChar());
                    } else {
                        ChineseDouble cnDouble = new ChineseDouble(value.doubleValue());
                        showText = cnDouble.chineseValue(this.getDecimal(), showThoundsMark, this.getBigChineseChar());
                    }
                    if (isNegtive) {
                        showText = "\uff08\u8d1f\uff09" + showText;
                    }
                }
            }
            return showText;
        }
        throw new IllegalArgumentException("\u4e0d\u80fd\u89e3\u6790\u6307\u5411null\u7684\u5b57\u7b26\u4e32\u4e3a\u6570\u5b57\uff01");
    }

    @Override
    public String parseEditText(String editText) {
        BigDecimal value = new BigDecimal(editText);
        return NumberCellProperty.formatNumberString(value, this.getDecimal(), false);
    }

    @Override
    public String parseShowText(String showText) {
        String value;
        if (StringUtils.isEmpty(showText)) {
            return showText;
        }
        if (!this.getChineseNumber()) {
            value = showText;
        } else {
            try {
                value = ChineseDouble.valueOf(showText);
            }
            catch (Exception ex1) {
                throw new IllegalArgumentException("\u4e2d\u6587\u6570\u503c\u6709\u9519\u8bef\uff0c\u89e3\u6790\u5931\u8d25");
            }
        }
        BigDecimal dbValue = new BigDecimal(value);
        return NumberCellProperty.formatNumberString(dbValue, this.getDecimal(), this.getThoundsMark());
    }

    private static BigDecimal trimDouble(BigDecimal source, int frcLength) {
        if (frcLength < 0) {
            frcLength = 0;
        }
        int multiplier = (int)Math.exp(Math.log(10.0) * (double)frcLength);
        BigDecimal factor = new BigDecimal(multiplier);
        BigDecimal value = source.multiply(factor);
        value = new BigDecimal(value.add(FLOOR_DELTA).toBigInteger());
        return value.divide(factor, frcLength, 4);
    }

    private static String formatNumberString(BigDecimal value, int decimal, boolean thoundsMark) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        if (thoundsMark) {
            df.setGroupingSize(3);
            df.setGroupingUsed(true);
        }
        if (decimal >= 0) {
            df.setMaximumFractionDigits(decimal);
            df.setMinimumFractionDigits(decimal);
        }
        return df.format(value);
    }
}

