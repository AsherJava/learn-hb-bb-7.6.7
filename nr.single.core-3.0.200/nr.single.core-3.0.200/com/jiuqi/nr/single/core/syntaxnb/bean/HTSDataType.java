/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;

public class HTSDataType
extends BaseCellDataType {
    private String exp;
    private int year;
    private int month;
    private int day;
    private CommonDataType value;

    public String getExp() {
        return this.exp;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public CommonDataType getValue() {
        return this.value;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setValue(CommonDataType value) {
        this.value = value;
    }
}

