/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ChineseNumberFormat
 *  com.jiuqi.bi.util.IntValue
 */
package com.jiuqi.np.period;

import com.jiuqi.bi.util.ChineseNumberFormat;
import com.jiuqi.bi.util.IntValue;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.period.ReservedModifierManager;
import com.jiuqi.np.period.text.StringHelper;
import java.io.Serializable;

public class PeriodModifier
implements Comparable,
Serializable {
    private static final long serialVersionUID = 4148329494085343887L;
    public static final int FLAG_RELATIVE = 0;
    public static final int FLAG_ABSOLUTE = 1;
    int yearFlag = 0;
    int yearModifier = 0;
    int periodFlag = 0;
    int periodType = 0;
    int periodModifier = 0;
    int destPeriodType = -1;

    public static PeriodModifier parse(String modifierString) {
        PeriodModifier m = new PeriodModifier();
        m.parsePeriodModifier(modifierString);
        return m;
    }

    public boolean isEmpty() {
        return this.yearFlag == 0 && this.yearModifier == 0 && this.periodFlag == 0 && this.periodModifier == 0 && this.periodType == 0;
    }

    public boolean isRelative() {
        return this.yearFlag == 0 || this.periodFlag == 0;
    }

    public int compareTo(Object another) {
        if (another instanceof PeriodModifier) {
            return this.compareTo((PeriodModifier)another);
        }
        return this.hashCode() - another.hashCode();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.periodFlag;
        result = 31 * result + this.periodModifier;
        result = 31 * result + this.periodType;
        result = 31 * result + this.yearFlag;
        result = 31 * result + this.yearModifier;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PeriodModifier other = (PeriodModifier)obj;
        if (this.periodFlag != other.periodFlag) {
            return false;
        }
        if (this.periodModifier != other.periodModifier) {
            return false;
        }
        if (this.periodType != other.periodType) {
            return false;
        }
        if (this.yearFlag != other.yearFlag) {
            return false;
        }
        return this.yearModifier == other.yearModifier;
    }

    public int compareTo(PeriodModifier another) {
        if (another == null) {
            return 1;
        }
        int result = this.yearFlag - another.yearFlag;
        if (result == 0) {
            result = this.yearModifier - another.yearModifier;
        }
        if (result == 0) {
            result = this.periodFlag - another.periodFlag;
        }
        if (result == 0) {
            result = this.periodType - another.periodType;
        }
        if (result == 0) {
            result = this.periodModifier - another.periodModifier;
        }
        return result;
    }

    public String modify(String periodString) {
        PeriodWrapper period = new PeriodWrapper(periodString);
        if (!this.modify(period)) {
            return "1900N0001";
        }
        return period.toString();
    }

    public boolean modify(PeriodWrapper periodWrapper) {
        boolean result = true;
        if (this.yearFlag == 0) {
            result = periodWrapper.modifyYear(this.yearModifier);
        } else {
            periodWrapper.setYear(this.yearModifier);
        }
        this.tryCorrectType(periodWrapper, this.destPeriodType);
        if (result) {
            if (this.periodFlag == 1) {
                periodWrapper.setAll(periodWrapper.getYear(), this.periodType, this.periodModifier);
            } else if (this.periodModifier != 0) {
                if (this.periodType != 0 && this.periodType != periodWrapper.getType()) {
                    PeriodType tt;
                    PeriodType mt = PeriodType.fromType(this.periodType);
                    if (!mt.canModify(tt = PeriodType.fromType(periodWrapper.getType()))) {
                        return false;
                    }
                    result = mt.modify(periodWrapper, this.periodModifier);
                } else {
                    result = periodWrapper.modifyPeriod(this.periodModifier);
                }
            } else if (this.periodType == 1) {
                periodWrapper.setAll(periodWrapper.getYear(), 1, 1);
            }
        }
        return result;
    }

    public void tryCorrectType(PeriodWrapper periodWrapper, int destPeriodType) {
        if (destPeriodType >= 0 && destPeriodType != periodWrapper.getType()) {
            PeriodType dType = PeriodType.fromType(destPeriodType);
            PeriodType sType = PeriodType.fromType(periodWrapper.getType());
            PeriodWrapper destPW = dType.fromCalendar(sType.toCalendar(periodWrapper));
            periodWrapper.assign(destPW);
        }
    }

    private String combineModifier(PeriodModifier another) {
        if (another.yearFlag == 1 || another.yearModifier != 0) {
            if (this.yearFlag == 1 || this.yearModifier != 0) {
                return "\u5e74\u5ea6\u591a\u6b21\u8bbe\u7f6e";
            }
            this.yearFlag = another.yearFlag;
            this.yearModifier = another.yearModifier;
        }
        if (another.periodFlag == 1 || another.periodModifier != 0) {
            if (this.periodFlag == 1 || this.periodModifier != 0) {
                return "\u671f\u95f4\u591a\u6b21\u8bbe\u7f6e";
            }
            this.periodFlag = another.periodFlag;
            this.periodModifier = another.periodModifier;
        }
        if (another.periodType != 0) {
            if (this.periodType != 0 && this.periodType != another.periodType) {
                return "\u65f6\u671f\u7c7b\u578b\u8bbe\u7f6e\u51b2\u7a81";
            }
            this.periodType = another.periodType;
        }
        return null;
    }

    public String combineAbsolutePeriod(PeriodWrapper periodWrapper) {
        if (this.yearFlag == 1 || this.yearModifier != 0) {
            return "\u5e74\u5ea6\u591a\u6b21\u8bbe\u7f6e";
        }
        this.yearFlag = 1;
        this.yearModifier = periodWrapper.getYear();
        if (this.periodFlag == 1 || this.periodModifier != 0) {
            return "\u671f\u95f4\u591a\u6b21\u8bbe\u7f6e";
        }
        if (this.periodType == 0 || this.periodType != periodWrapper.getType()) {
            return "\u65f6\u671f\u7c7b\u578b\u8bbe\u7f6e\u51b2\u7a81";
        }
        this.periodFlag = 1;
        this.periodModifier = periodWrapper.getPeriod();
        this.periodType = periodWrapper.getType();
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String parsePeriodModifier(String modifierString) {
        IntValue type = new IntValue(0);
        IntValue tail = new IntValue(0);
        if (ReservedModifierManager.MODIFIERs.containsKey(modifierString)) {
            PeriodModifier modifier = (PeriodModifier)ReservedModifierManager.MODIFIERs.get(modifierString);
            return this.combineModifier(modifier);
        }
        if (!PeriodConsts.parsePeriodEndingTail(modifierString, tail, type)) return modifierString;
        if (tail.value == 0) {
            if (this.periodType != 0) return "\u65f6\u671f\u7c7b\u578b\u6307\u5b9a\u591a\u4f59";
            this.periodType = type.value;
            return null;
        }
        int flag = 1;
        int modifier = -1;
        String number = modifierString.substring(0, tail.value);
        char leadChar = number.charAt(0);
        if (leadChar == '+' || leadChar == '-' || leadChar == '\u4e0a' || leadChar == '\u4e0b' || leadChar == '\u524d' || leadChar == '\u540e') {
            flag = 0;
            number = number.substring(1);
            try {
                modifier = Integer.parseInt(number);
            }
            catch (NumberFormatException e1) {
                try {
                    ChineseNumberFormat format = new ChineseNumberFormat(false, 0);
                    modifier = format.parse(number).intValue();
                }
                catch (Exception e2) {
                    return "\u671f\u6570\u683c\u5f0f\u201c" + number + "\u201d\u65e0\u6cd5\u89e3\u6790";
                }
            }
            if (leadChar == '-' || leadChar == '\u4e0a' || leadChar == '\u524d') {
                modifier = -modifier;
            }
        } else if (StringHelper.isNumberString(number)) {
            modifier = Integer.parseInt(number);
        } else {
            try {
                ChineseNumberFormat format = new ChineseNumberFormat(false, 0);
                modifier = format.parse(number).intValue();
            }
            catch (Exception e2) {
                try {
                    ChineseNumberFormat format = new ChineseNumberFormat(false, 0);
                    modifier = format.parse(number).intValue();
                }
                catch (Exception e3) {
                    PeriodWrapper periodWrapper = new PeriodWrapper();
                    try {
                        periodWrapper.parseTitleString(modifierString);
                        return this.combineAbsolutePeriod(periodWrapper);
                    }
                    catch (IllegalArgumentException e4) {
                        try {
                            periodWrapper.parseString(modifierString);
                            return this.combineAbsolutePeriod(periodWrapper);
                        }
                        catch (IllegalArgumentException e5) {
                            return "\u65f6\u671f\u9650\u5b9a\u65e0\u6cd5\u89e3\u6790";
                        }
                    }
                }
            }
        }
        if (type.value == 1) {
            if (this.yearFlag == 1 || this.yearModifier != 0) {
                return "\u5e74\u5ea6\u591a\u6b21\u8bbe\u7f6e";
            }
            this.yearFlag = flag;
            this.yearModifier = modifier;
            return null;
        } else {
            if (this.periodFlag == 1 || this.periodModifier != 0) {
                return "\u671f\u95f4\u591a\u6b21\u8bbe\u7f6e";
            }
            this.periodFlag = flag;
            this.periodModifier = modifier;
            if (type.value == 0) return null;
            if (this.periodType != 0 && this.periodType != type.value) {
                return "\u65f6\u671f\u7c7b\u578b\u8bbe\u7f6e\u51b2\u7a81";
            }
            this.periodType = type.value;
        }
        return null;
    }

    public void union(PeriodModifier another) {
        if (this.yearFlag == another.yearFlag) {
            this.yearModifier = this.yearFlag == 1 ? another.yearModifier : (this.yearModifier += another.yearModifier);
        } else {
            this.yearFlag = 1;
            this.yearModifier += another.yearModifier;
        }
        if (this.periodType != another.periodType && another.periodType != 0) {
            this.periodType = another.periodType;
            this.periodFlag = another.periodFlag;
            this.periodModifier = another.periodModifier;
        } else if (this.periodFlag == another.periodFlag) {
            this.periodModifier = this.periodFlag == 1 ? another.periodModifier : (this.periodModifier += another.periodModifier);
        } else {
            this.periodFlag = 1;
            this.periodModifier += another.periodModifier;
        }
        if (another.getPeriodType() != 0 && another.periodModifier == 0 && another.yearModifier == 0) {
            this.destPeriodType = another.getPeriodType();
        }
    }

    public String toString() {
        boolean needComma = false;
        StringBuffer result = new StringBuffer(64);
        if (this.yearFlag == 1 || this.yearModifier != 0) {
            if (this.yearFlag == 0 && this.yearModifier > 0) {
                result.append('+');
            }
            result.append(this.yearModifier);
            result.append('N');
            needComma = true;
        }
        if (this.periodFlag == 1 || this.periodModifier != 0) {
            if (needComma) {
                result.append(',');
            }
            if (this.periodFlag == 0 && this.periodModifier > 0) {
                result.append('+');
            }
            result.append(this.periodModifier);
            if (this.periodType != 0) {
                result.append((char)PeriodConsts.typeToCode(this.periodType));
            }
        } else if (this.periodType != 0) {
            if (needComma) {
                result.append(',');
            }
            result.append((char)PeriodConsts.typeToCode(this.periodType));
        }
        return result.toString();
    }

    public String toTitle() {
        String title;
        StringBuffer result = new StringBuffer(64);
        if (this.yearFlag == 1 || this.yearModifier != 0) {
            String yearStr = this.yearModifier + "N";
            if (this.yearFlag == 0 && this.yearModifier > 0) {
                yearStr = "+" + yearStr;
            }
            if ((title = ReservedModifierManager.MODIFIER_TITILS.get(yearStr)) != null) {
                result.append(title);
            } else if (this.yearFlag == 1) {
                result.append(this.yearModifier).append("\u5e74");
            } else {
                result.append(yearStr);
            }
        }
        StringBuffer periodStr = new StringBuffer();
        if (this.periodFlag == 1 || this.periodModifier != 0) {
            if (this.periodFlag == 0 && this.periodModifier > 0) {
                periodStr.append('+');
            }
            periodStr.append(this.periodModifier);
            if (this.periodType != 0) {
                periodStr.append((char)PeriodConsts.typeToCode(this.periodType));
            }
        } else if (this.periodType != 0) {
            periodStr.append((char)PeriodConsts.typeToCode(this.periodType));
        }
        if ((title = ReservedModifierManager.MODIFIER_TITILS.get(periodStr.toString())) != null) {
            result.append(title);
        } else if (this.yearFlag == 1) {
            result.append("\u7b2c").append(this.periodModifier).append("\u671f");
        } else {
            result.append(periodStr.toString());
        }
        return result.toString();
    }

    public int getYearFlag() {
        return this.yearFlag;
    }

    public int getYearModifier() {
        return this.yearModifier;
    }

    public int getPeriodFlag() {
        return this.periodFlag;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public int getPeriodModifier() {
        return this.periodModifier;
    }

    public void setYearFlag(int yearFlag) {
        this.yearFlag = yearFlag;
    }

    public void setYearModifier(int yearModifier) {
        this.yearModifier = yearModifier;
    }

    public void setPeriodFlag(int periodFlag) {
        this.periodFlag = periodFlag;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public void setPeriodModifier(int periodModifier) {
        this.periodModifier = periodModifier;
    }

    public static void main(String[] args) {
        PeriodWrapper pw = new PeriodWrapper("2022N0001");
        PeriodModifier modifier = PeriodModifier.parse("-0N");
        PeriodModifier another = PeriodModifier.parse("Y");
        modifier.union(another);
        modifier.modify(pw);
        System.out.println(pw);
    }
}

