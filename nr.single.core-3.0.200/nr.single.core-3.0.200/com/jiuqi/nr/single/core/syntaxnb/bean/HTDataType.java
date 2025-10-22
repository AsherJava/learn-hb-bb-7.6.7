/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;

public class HTDataType
extends BaseCellDataType {
    private String exp;
    private String condition;
    private String statMode;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private CommonDataType value;

    public String getExp() {
        return this.exp;
    }

    public String getCondition() {
        return this.condition;
    }

    public String getStatMode() {
        return this.statMode;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public int getStartMonth() {
        return this.startMonth;
    }

    public int getStartDay() {
        return this.startDay;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public int getEndMonth() {
        return this.endMonth;
    }

    public int getEndDay() {
        return this.endDay;
    }

    public CommonDataType getValue() {
        return this.value;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setStatMode(String statMode) {
        this.statMode = statMode;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public void setValue(CommonDataType value) {
        this.value = value;
    }
}

