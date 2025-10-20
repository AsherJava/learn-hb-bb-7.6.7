/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.value.EnumValue
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.biz.intf.value.EnumValue;

public enum BillState implements EnumValue
{
    SAVED(0, "\u5df2\u4fdd\u5b58"),
    TEMPORARY(1, "\u5df2\u6682\u5b58"),
    CHECKED(2, "\u5df2\u786e\u8ba4"),
    DELETED(4, "\u5df2\u5e9f\u6b62"),
    COMMITTED(8, "\u5df2\u63d0\u4ea4"),
    AUDITING(24, "\u5ba1\u6279\u4e2d"),
    SENDBACK(33, "\u9a73\u56de\u53ef\u63d0\u4ea4"),
    REJECT(68, "\u9a73\u56de\u4e0d\u53ef\u63d0\u4ea4"),
    AUDITPASSEDCANEDIT(129, "\u5ba1\u6279\u901a\u8fc7\u53ef\u4fee\u6539"),
    AUDITPASSED(130, "\u5ba1\u6279\u901a\u8fc7"),
    PARTPASSED(258, "\u90e8\u5206\u901a\u8fc7");

    private int value;
    private String title;

    private BillState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

