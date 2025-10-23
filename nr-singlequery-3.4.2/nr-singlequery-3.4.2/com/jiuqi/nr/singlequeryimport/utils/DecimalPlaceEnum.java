/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.utils;

public enum DecimalPlaceEnum {
    ZERO(0, "#,##0"),
    ONE(1, "#,##0.0"),
    TWO(2, "#,##0.00"),
    THREE(3, "#,##0.000"),
    FOUR(4, "#,##0.0000");

    private Integer value = 0;
    private String format = "#,##0.00";

    private DecimalPlaceEnum(Integer value, String format) {
        this.value = value;
        this.format = format;
    }

    public Integer val() {
        return this.value;
    }

    public static String getFormat(Integer value) {
        if (value == null) {
            return DecimalPlaceEnum.TWO.format;
        }
        switch (value) {
            case 0: {
                return DecimalPlaceEnum.ZERO.format;
            }
            case 1: {
                return DecimalPlaceEnum.ONE.format;
            }
            case 2: {
                return DecimalPlaceEnum.TWO.format;
            }
            case 3: {
                return DecimalPlaceEnum.THREE.format;
            }
            case 4: {
                return DecimalPlaceEnum.FOUR.format;
            }
        }
        return DecimalPlaceEnum.ZERO.format;
    }
}

