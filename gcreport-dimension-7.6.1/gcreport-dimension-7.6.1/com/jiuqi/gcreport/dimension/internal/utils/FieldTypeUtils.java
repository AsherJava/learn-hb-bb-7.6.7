/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.utils;

import com.jiuqi.gcreport.dimension.vo.FieldTypeVO;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class FieldTypeUtils {
    public static List<FieldTypeVO> getEditFieldTypes() {
        EnumSet<FieldType> allFieldTypes = EnumSet.allOf(FieldType.class);
        return allFieldTypes.stream().map(fieldValueType -> new FieldTypeVO(fieldValueType.getNrValue(), fieldValueType.getTitle())).collect(Collectors.toList());
    }

    public static List<FieldTypeVO> getAddFieldTypes() {
        ArrayList<FieldTypeVO> fieldTypeVOS = new ArrayList<FieldTypeVO>();
        FieldTypeVO stringTypeVO = new FieldTypeVO(FieldType.FIELD_TYPE_STRING.getNrValue(), FieldType.FIELD_TYPE_STRING.getTitle());
        FieldTypeVO fdateTypeVO = new FieldTypeVO(FieldType.FIELD_TYPE_DATE.getNrValue(), FieldType.FIELD_TYPE_DATE.getTitle());
        FieldTypeVO numTypeVO = new FieldTypeVO(FieldType.FIELD_TYPE_DECIMAL.getNrValue(), FieldType.FIELD_TYPE_DECIMAL.getTitle());
        fieldTypeVOS.add(stringTypeVO);
        fieldTypeVOS.add(fdateTypeVO);
        fieldTypeVOS.add(numTypeVO);
        return fieldTypeVOS;
    }

    public static String getFieldTitle(int fieldType) {
        FieldType fieldTypeE = FieldType.getEnumByValue(fieldType);
        return fieldTypeE == null ? "" : fieldTypeE.getTitle();
    }

    public static enum FieldType {
        FIELD_TYPE_STRING(2, "\u5b57\u7b26\u578b", 6),
        FIELD_TYPE_DATE(5, "\u65e5\u671f\u7c7b\u578b", 5),
        FIELD_TYPE_DECIMAL(8, "\u6570\u503c\u578b", 10),
        FIELD_TYPE_INTEGER(3, "\u6574\u6570\u7c7b\u578b", 4),
        FIELD_TYPE_LOGIC(4, "\u5e03\u5c14\u7c7b\u578b", 1),
        FIELD_TYPE_BINARY(18, "\u4e8c\u8fdb\u5236\u7c7b\u578b", 37),
        FIELD_TYPE_PICTURE(17, "\u56fe\u7247\u7c7b\u578b", 35),
        FIELD_TYPE_TEXT(16, "\u6587\u672c\u7c7b\u578b", 34),
        FIELD_TYPE_TIME(19, "\u65f6\u95f4\u65e5\u671f\u7c7b\u578b", 8),
        FIELD_TYPE_TIME_STAMP(9, "\u65f6\u95f4\u6233", 2),
        FIELD_TYPE_UUID(7, "UUID\u7c7b\u578b", 33);

        private int nrValue;
        private String title;
        private int vaValue;

        private FieldType(int value, String title, int vaValue) {
            this.nrValue = value;
            this.title = title;
            this.vaValue = vaValue;
        }

        public int getNrValue() {
            return this.nrValue;
        }

        public String getTitle() {
            return this.title;
        }

        public static FieldType getEnumByValue(int fieldType) {
            for (FieldType fieldTypeEnum : FieldType.values()) {
                if (fieldTypeEnum.getNrValue() != fieldType) continue;
                return fieldTypeEnum;
            }
            return null;
        }

        public int getVaValue() {
            return this.vaValue;
        }

        public void setVaValue(int vaValue) {
            this.vaValue = vaValue;
        }
    }
}

