/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.task.form.formio.common;

import com.jiuqi.nr.datascheme.api.type.DataFieldType;

public enum ImportCellType {
    STRING(DataFieldType.STRING, "\u5b57\u7b26"),
    INTEGER(-1, "\u6574\u6570"),
    DATE(DataFieldType.DATE, "\u65e5\u671f"),
    DATE_TIME(DataFieldType.DATE_TIME, "\u65e5\u671f\u65f6\u95f4"),
    BIG_DECIMAL(DataFieldType.BIGDECIMAL, "\u6570\u503c"),
    FORM_STYLE(-1, "\u8868\u6837");

    private final int value;
    private final String title;

    private ImportCellType(DataFieldType value, String title) {
        this.value = value.getValue();
        this.title = title;
    }

    private ImportCellType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static ImportCellType valueOf(int value) {
        for (ImportCellType type : ImportCellType.values()) {
            if (type.getValue() != value) continue;
            return type;
        }
        return null;
    }

    public static ImportCellType getByFormulaType(int type) {
        switch (type) {
            case 2: {
                return DATE;
            }
            case 3: {
                return INTEGER;
            }
            case 6: {
                return STRING;
            }
            case 10: {
                return BIG_DECIMAL;
            }
        }
        return FORM_STYLE;
    }
}

