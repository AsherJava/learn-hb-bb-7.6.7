/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EProcessRangeDimType {
    ALL,
    RANGE,
    ONE;


    @JsonCreator
    public static EProcessRangeDimType fromString(String value) {
        switch (value) {
            case "ALL": {
                return ALL;
            }
            case "RANGE": {
                return RANGE;
            }
            case "ONE": {
                return ONE;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}

