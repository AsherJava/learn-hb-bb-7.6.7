/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum OrderType {
    ORDER_ASC("0"),
    ORDER_DESC("1");

    private String name;

    private OrderType(String name) {
        this.name = name;
    }

    public static OrderType parse(String key) {
        switch (key) {
            case "1": {
                return ORDER_DESC;
            }
        }
        return ORDER_ASC;
    }

    public String getName() {
        return this.name;
    }
}

