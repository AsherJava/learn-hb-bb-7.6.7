/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.IntValue
 */
package com.jiuqi.np.period;

import com.jiuqi.bi.util.IntValue;
import com.jiuqi.np.period.DateAider;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.util.LogHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeriodWrapper
implements Cloneable,
Comparable,
Serializable {
    private static final long serialVersionUID = -992473302791838842L;
    protected int year;
    protected int type;
    protected int period;
    protected String periodString;
    protected PeriodLanguage language = PeriodLanguage.Chinese;

    public PeriodWrapper() {
        this.periodString = "1900N0001";
        this.year = 2000;
        this.type = 1;
        this.period = 1;
    }

    public PeriodWrapper(int year, int type, int period) {
        this.periodString = "1900N0001";
        this.setAll(year, type, period);
    }

    public PeriodWrapper(String periodString) {
        try {
            this.parseString(periodString);
        }
        catch (IllegalArgumentException e) {
            this.periodString = periodString;
            this.type = 8;
        }
    }

    public PeriodWrapper(PeriodWrapper another) {
        this.periodString = another.periodString;
        this.year = another.year;
        this.type = another.type;
        this.period = another.period;
    }

    public void assign(PeriodWrapper another) {
        this.periodString = another.periodString;
        this.year = another.year;
        this.type = another.type;
        this.period = another.period;
    }

    public int getYear() {
        return this.year;
    }

    public int getType() {
        return this.type;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setYear(int value) {
        if (value < 0 || value > 9999) {
            throw new IllegalArgumentException("\u5e74\u4efd\u65e0\u6548\uff1a" + value);
        }
        this.year = value;
        if (this.type == 6 && this.period > this.getHighBound()) {
            this.period = this.getHighBound();
        }
        this.periodString = PeriodWrapper.makePeriodString(this.year, this.type, this.period);
    }

    public void setType(int value) {
        if (value < 1 || value > 8) {
            throw new IllegalArgumentException("\u65f6\u671f\u7c7b\u578b\u65e0\u6548\uff1a" + value);
        }
        this.type = value;
        if (this.type != 4 && this.period > this.getHighBound()) {
            this.period = this.getHighBound();
        }
        this.periodString = PeriodWrapper.makePeriodString(this.year, this.type, this.period);
    }

    public void setPeriod(int value) {
        if (this.type != 4 && (value < 0 || value > this.getHighBound())) {
            throw new IllegalArgumentException("\u65f6\u671f\u65e0\u6548\uff1a" + value);
        }
        this.period = value;
        this.periodString = PeriodWrapper.makePeriodString(this.year, this.type, this.period);
    }

    public void setAll(int year, int type, int period) {
        if (year < 0 || year > 9999) {
            throw new IllegalArgumentException("\u5e74\u4efd\u65e0\u6548\uff1a" + year);
        }
        if (type < 1 || type > 8) {
            throw new IllegalArgumentException("\u65f6\u671f\u7c7b\u578b\u65e0\u6548\uff1a" + type);
        }
        this.year = year;
        this.type = type;
        if (type != 4 && (period < 0 || period > this.getHighBound())) {
            this.parseString(this.periodString);
            throw new IllegalArgumentException("\u65f6\u671f\u65e0\u6548\uff1a" + period);
        }
        this.period = period;
        this.periodString = PeriodWrapper.makePeriodString(year, type, period);
    }

    public String toString() {
        return this.periodString;
    }

    public void parseString(String periodString) throws IllegalArgumentException {
        try {
            this.year = Integer.parseInt(periodString.substring(0, 4));
            this.type = PeriodConsts.codeToType(periodString.charAt(4));
            this.period = Integer.parseInt(periodString.substring(5));
            if (this.year < 0 || this.year > 9999) {
                throw new IllegalArgumentException("\u5e74\u4efd\u65e0\u6548\uff1a" + periodString);
            }
            if (this.type < 1 || this.type > 8) {
                throw new IllegalArgumentException("\u65f6\u671f\u7c7b\u578b\u65e0\u6548\uff1a" + periodString);
            }
            if (this.type != 4 && (this.period < 0 || this.period > this.getHighBound())) {
                throw new IllegalArgumentException("\u65f6\u671f\u65e0\u6548\uff1a" + periodString);
            }
        }
        catch (IllegalArgumentException ex) {
            this.parseString(this.periodString);
            throw ex;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("\u65f6\u671f\u4ee3\u7801\u683c\u5f0f\u975e\u6cd5\uff1a" + periodString);
        }
        this.periodString = periodString;
    }

    public void setLanguage(PeriodLanguage l) {
        this.language = l;
    }

    public PeriodLanguage getLanguage() {
        return this.language;
    }

    @Deprecated
    public String toTitleString() {
        return new DefaultPeriodAdapter().getPeriodTitle(this);
    }

    @Deprecated
    public void parseTitleString(String titleString) throws IllegalArgumentException {
        try {
            int index = -1;
            for (int i = 0; i < titleString.length(); ++i) {
                char ch = titleString.charAt(i);
                if (ch >= '0' && ch <= '9') continue;
                index = i;
                break;
            }
            this.year = Integer.parseInt(titleString.substring(0, index));
            if (PeriodConsts.titleToType(titleString.substring(index, index + 1)) != 1) {
                throw new IllegalArgumentException("\u627e\u4e0d\u5230\u5e74\u5ea6\u6807\u8bc6\uff1a" + titleString);
            }
            ++index;
            IntValue tailStart = new IntValue(0);
            IntValue periodType = new IntValue(0);
            if (!PeriodConsts.parsePeriodEndingTail(titleString, tailStart, periodType)) {
                throw new IllegalArgumentException("\u65e0\u6cd5\u5224\u65ad\u65f6\u671f\u7c7b\u578b\uff1a" + titleString);
            }
            this.type = periodType.value;
            int tail = tailStart.value;
            if (index >= tail) {
                this.period = 1;
            } else {
                this.period = 0;
                block4: while (index <= tail) {
                    char ch = titleString.charAt(index);
                    if (ch >= '0' && ch <= '9') {
                        for (int stop = index + 1; stop <= tail; ++stop) {
                            String mt;
                            ch = titleString.charAt(stop);
                            if (ch >= '0' && ch <= '9') continue;
                            String ps = titleString.substring(index, stop);
                            this.period = Integer.parseInt(ps);
                            if (this.type != 6 || !(mt = PeriodConsts.typeToTitle(4)).equals(titleString.substring(stop, stop + mt.length()))) break block4;
                            int day = Integer.parseInt(titleString.substring(stop + mt.length(), tail));
                            this.period = DateAider.calcDayInYear(this.year, this.period, day);
                            break block4;
                        }
                        break;
                    }
                    ++index;
                }
                if (this.period <= 0 || this.period > this.getHighBound()) {
                    if (this.type == 2 && (titleString.charAt(tail - 1) == '\u4e0a' || titleString.charAt(tail - 1) == '\u4e0b')) {
                        this.period = titleString.charAt(tail - 1) == '\u4e0a' ? 1 : 2;
                    } else {
                        throw new IllegalArgumentException("\u65e0\u6cd5\u5224\u65ad\u65f6\u671f\uff1a" + titleString);
                    }
                }
            }
            this.periodString = PeriodWrapper.makePeriodString(this.year, this.type, this.period);
        }
        catch (IllegalArgumentException ex) {
            throw ex;
        }
        catch (Exception ex) {
            this.parseString(this.periodString);
            throw new IllegalArgumentException("\u65f6\u671f\u63cf\u8ff0\u683c\u5f0f\u975e\u6cd5\uff1a" + titleString);
        }
    }

    public int hashCode() {
        return this.periodString.hashCode();
    }

    public boolean equals(Object another) {
        if (another instanceof PeriodWrapper) {
            return this.periodString.equals(((PeriodWrapper)another).periodString);
        }
        return false;
    }

    public int compareTo(Object o) {
        if (o instanceof PeriodWrapper) {
            return this.periodString.compareTo(((PeriodWrapper)o).periodString);
        }
        return this.hashCode() - o.hashCode();
    }

    public Object clone() {
        return new PeriodWrapper(this);
    }

    @Deprecated
    public boolean priorYear() {
        return this.modifyYear(-1);
    }

    @Deprecated
    public boolean nextYear() {
        return this.modifyYear(1);
    }

    @Deprecated
    public boolean modifyYear(int modifyCount) {
        return new DefaultPeriodAdapter().modifyYear(this, modifyCount);
    }

    @Deprecated
    public boolean priorPeriod() {
        return this.modifyPeriod(-1);
    }

    @Deprecated
    public boolean nextPeriod() {
        return this.modifyPeriod(1);
    }

    @Deprecated
    public boolean modifyPeriod(int modifyCount) {
        return new DefaultPeriodAdapter().modifyPeriod(this, modifyCount);
    }

    private int getHighBound() {
        int result = PeriodConsts.PERIOD_TYPE_HIGH_BOUNDs[this.type];
        return result;
    }

    public static String makePeriodString(int yearNumber, int periodType, int periodIndex) {
        StringBuilder buffer = new StringBuilder();
        buffer.setLength(0);
        PeriodWrapper.bufferAppendInt4(buffer, yearNumber);
        buffer.append((char)PeriodConsts.typeToCode(periodType));
        PeriodWrapper.bufferAppendInt4(buffer, periodIndex);
        return buffer.toString();
    }

    private static final void bufferAppendInt4(StringBuilder buffer, int value) {
        String sValue = Integer.toString(value);
        if (sValue.length() == 4) {
            buffer.append(sValue);
        } else {
            int count = 4 - sValue.length();
            if (count > 0) {
                while (count-- > 0) {
                    buffer.append('0');
                }
            } else {
                sValue = sValue.substring(-count);
            }
            buffer.append(sValue);
        }
    }

    public static void main(String[] args) {
        PeriodWrapper dp = new PeriodWrapper(2006, 3, 4);
        LogHelper.info(dp.toString());
        LogHelper.info(dp.toTitleString());
        dp.setAll(2006, 6, 97);
        LogHelper.info(dp.toTitleString());
        dp.parseTitleString(dp.toTitleString());
        LogHelper.info(dp.toString());
        dp.setAll(2007, 5, 2);
        LogHelper.info(dp.toTitleString());
        dp.parseTitleString(dp.toTitleString());
        LogHelper.info(dp.toString());
        dp.setAll(2007, 2, 2);
        LogHelper.info(dp.toTitleString());
        dp.parseTitleString(dp.toTitleString());
        LogHelper.info(dp.toString());
        dp.setAll(2007, 1, 1);
        LogHelper.info(dp.toTitleString());
        dp.parseTitleString(dp.toTitleString());
        LogHelper.info(dp.toString());
        dp.setAll(2012, 8, 2);
        LogHelper.info(dp.toTitleString());
        dp.parseTitleString(dp.toTitleString());
        LogHelper.info(dp.toString());
    }

    public static List<Integer> getPeriodParentLevels(int periodType) {
        ArrayList<Integer> parentLevels = new ArrayList<Integer>();
        switch (periodType) {
            case 6: {
                parentLevels.add(6);
                parentLevels.add(7);
                parentLevels.add(5);
                parentLevels.add(4);
                parentLevels.add(3);
                parentLevels.add(2);
                parentLevels.add(1);
                return parentLevels;
            }
            case 5: {
                parentLevels.add(5);
                parentLevels.add(4);
                parentLevels.add(3);
                parentLevels.add(2);
                parentLevels.add(1);
                return parentLevels;
            }
            case 4: {
                parentLevels.add(4);
                parentLevels.add(3);
                parentLevels.add(2);
                parentLevels.add(1);
                return parentLevels;
            }
            case 3: {
                parentLevels.add(3);
                parentLevels.add(2);
                parentLevels.add(1);
                return parentLevels;
            }
            case 2: {
                parentLevels.add(2);
                parentLevels.add(1);
                return parentLevels;
            }
        }
        return parentLevels;
    }
}

