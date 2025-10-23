/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.zbquery.model.ConditionOperation;

public class ConditionExpGenerator {
    public static String generate(ConditionOperation condType, int dataType, String dsName, String dsField, Double magnitudeValue, Object ... condValues) {
        String fieldExp = dsName + "." + dsField;
        return ConditionExpGenerator.generate(condType, dataType, fieldExp, magnitudeValue, condValues);
    }

    public static String generate(ConditionOperation condType, int dataType, String fieldExp, Double magnitudeValue, Object ... condValues) {
        if (dataType == 5 || dataType == 3 || dataType == 10) {
            return ConditionExpGenerator.generateByNumber(condType, fieldExp, magnitudeValue, condValues);
        }
        if (dataType == 6) {
            return ConditionExpGenerator.generateByString(condType, fieldExp, condValues);
        }
        if (dataType == 2) {
            return ConditionExpGenerator.generateByDate(condType, fieldExp, "yyyy-MM-dd", condValues);
        }
        if (dataType == 1) {
            return ConditionExpGenerator.generateByBoolean(condType, fieldExp, condValues);
        }
        if (dataType == DataFieldType.DATE_TIME.getValue()) {
            return ConditionExpGenerator.generateByDate(condType, fieldExp, "yyyy-MM-dd HH:mm:ss", condValues);
        }
        throw new RuntimeException(String.format("%s\u6761\u4ef6\u8868\u8fbe\u5f0f\u751f\u6210\u5931\u8d25\uff1a\u672a\u77e5\u6570\u636e\u7c7b\u578b[%d]", fieldExp, dataType));
    }

    private static String generateByNumber(ConditionOperation condType, String fieldExp, Double magnitudeValue, Object ... condValues) {
        switch (condType) {
            case EQUAL: {
                return String.format(magnitudeValue == null ? "%s=%s" : "%s=%s*" + magnitudeValue, fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0]);
            }
            case NOTEQUAL: {
                return String.format("%s<>%s", fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0]);
            }
            case GREATER: {
                return String.format("%s>%s", fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0]);
            }
            case GREATEREQUAL: {
                return String.format("%s>=%s", fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0]);
            }
            case LESSER: {
                return String.format("%s<%s", fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0]);
            }
            case LESSEQUAL: {
                return String.format("%s<=%s", fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0]);
            }
            case BETWEEN: {
                return String.format("%s>=%s AND %s<=%s", fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[0], fieldExp, ConditionExpGenerator.isNull(condValues) ? null : condValues[1]);
            }
        }
        throw new RuntimeException("\u6761\u4ef6\u8868\u8fbe\u5f0f\u751f\u6210\u5931\u8d25\uff1a\u6570\u503c\u7c7b\u578b\u6682\u4e0d\u652f\u6301\u81ea\u5b9a\u4e49\u6761\u4ef6\u7c7b\u578b[" + condType.name() + "]");
    }

    private static String generateByString(ConditionOperation condType, String fieldExp, Object ... condValues) {
        switch (condType) {
            case EQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s=null", fieldExp) : String.format("%s=\"%s\"", fieldExp, condValues[0]);
            }
            case NOTEQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<>null", fieldExp) : String.format("%s<>\"%s\"", fieldExp, condValues[0]);
            }
            case GREATER: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s>null", fieldExp) : String.format("%s>\"%s\"", fieldExp, condValues[0]);
            }
            case GREATEREQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s>=null", fieldExp) : String.format("%s>=\"%s\"", fieldExp, condValues[0]);
            }
            case LESSER: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<null", fieldExp) : String.format("%s<\"%s\"", fieldExp, condValues[0]);
            }
            case LESSEQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<=null", fieldExp) : String.format("%s<=\"%s\"", fieldExp, condValues[0]);
            }
            case STARTWITH: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Left(%s, %d)=null", fieldExp, 0) : String.format("Left(%s, %d)=\"%s\"", fieldExp, condValues[0].toString().length(), condValues[0]);
            }
            case STARTWITHOUT: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Left(%s, %d)<>null", fieldExp, 0) : String.format("Left(%s, %d)<>\"%s\"", fieldExp, condValues[0].toString().length(), condValues[0]);
            }
            case ENDWITH: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Right(%s, %d)=null", fieldExp, 0) : String.format("Right(%s, %d)=\"%s\"", fieldExp, condValues[0].toString().length(), condValues[0]);
            }
            case ENDWITHOUT: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Right(%s, %d)<>null", fieldExp, 0) : String.format("Right(%s, %d)<>\"%s\"", fieldExp, condValues[0].toString().length(), condValues[0]);
            }
            case CONTAIN: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s=null", fieldExp) : String.format("Pos(%s, \"%s\")>=1", fieldExp, condValues[0]);
            }
            case NOTCONTAIN: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<>null", fieldExp) : String.format("Pos(%s, \"%s\")=0", fieldExp, condValues[0]);
            }
        }
        throw new RuntimeException("\u6761\u4ef6\u8868\u8fbe\u5f0f\u751f\u6210\u5931\u8d25\uff1a\u5b57\u7b26\u7c7b\u578b\u6682\u4e0d\u652f\u6301\u81ea\u5b9a\u4e49\u6761\u4ef6\u7c7b\u578b[" + condType.name() + "]");
    }

    private static String generateByDate(ConditionOperation condType, String fieldExp, String format, Object ... condValues) {
        switch (condType) {
            case EQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s=null", fieldExp) : String.format("%s=DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format);
            }
            case NOTEQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<>null", fieldExp) : String.format("%s<>DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format);
            }
            case GREATER: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s>null", fieldExp) : String.format("%s>DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format);
            }
            case GREATEREQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s>=null", fieldExp) : String.format("%s>=DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format);
            }
            case LESSER: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<null", fieldExp) : String.format("%s<DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format);
            }
            case LESSEQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("%s<=null", fieldExp) : String.format("%s<=DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format);
            }
            case BETWEEN: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Str(%s)=null", fieldExp) : String.format("%s>=DateValue(\"%s\", \"%s\") AND %s<=DateValue(\"%s\", \"%s\")", fieldExp, condValues[0], format, fieldExp, condValues[1], format);
            }
        }
        throw new RuntimeException("\u6761\u4ef6\u8868\u8fbe\u5f0f\u751f\u6210\u5931\u8d25\uff1a\u65e5\u671f\u7c7b\u578b\u6682\u4e0d\u652f\u6301\u81ea\u5b9a\u4e49\u6761\u4ef6\u7c7b\u578b[" + condType.name() + "]");
    }

    private static String generateByBoolean(ConditionOperation condType, String fieldExp, Object ... condValues) {
        switch (condType) {
            case EQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Str(%s)=null", fieldExp) : String.format("Str(%s)=\"%s\"", fieldExp, condValues[0]);
            }
            case NOTEQUAL: {
                return ConditionExpGenerator.isNull(condValues) ? String.format("Str(%s)=null", fieldExp) : String.format("Str(%s)<>\"%s\"", fieldExp, condValues[0]);
            }
        }
        throw new RuntimeException("\u6761\u4ef6\u8868\u8fbe\u5f0f\u751f\u6210\u5931\u8d25\uff1a\u5e03\u5c14\u7c7b\u578b\u6682\u4e0d\u652f\u6301\u81ea\u5b9a\u4e49\u6761\u4ef6\u7c7b\u578b[" + condType.name() + "]");
    }

    private static boolean isNull(Object ... values) {
        return values == null || values.length == 0;
    }
}

