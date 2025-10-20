/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.bean;

abstract class BaseDataFormat {
    public static final String DATE_REG = "yyyy-MM-dd";
    public static final String DATE_TIME_REG = "yyyy-MM-dd HH:mm:ss";

    BaseDataFormat() {
    }

    public abstract String getFormatDate(Object var1);
}

