/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.utils;

public enum EmptyStringEnum {
    ZERO(0, ""),
    ONE(1, "    "),
    TWO(2, "      "),
    THREE(3, "        "),
    FOUR(4, "          "),
    FIVE(5, "              "),
    SIX(6, "                        ");

    private Integer value = 0;
    private String format = "";

    private EmptyStringEnum(Integer value, String format) {
        this.value = value;
        this.format = format;
    }

    public Integer val() {
        return this.value;
    }

    public static String getEmptyString(Integer value, String code) {
        if (value == null) {
            return EmptyStringEnum.TWO.format + code;
        }
        switch (value) {
            case 0: {
                return EmptyStringEnum.ZERO.format + code;
            }
            case 1: {
                return EmptyStringEnum.ONE.format + code;
            }
            case 2: {
                return EmptyStringEnum.TWO.format + code;
            }
            case 3: {
                return EmptyStringEnum.THREE.format + code;
            }
            case 4: {
                return EmptyStringEnum.FOUR.format + code;
            }
            case 5: {
                return EmptyStringEnum.FIVE.format + code;
            }
            case 6: {
                return EmptyStringEnum.SIX.format + code;
            }
        }
        return EmptyStringEnum.ZERO.format + code;
    }
}

