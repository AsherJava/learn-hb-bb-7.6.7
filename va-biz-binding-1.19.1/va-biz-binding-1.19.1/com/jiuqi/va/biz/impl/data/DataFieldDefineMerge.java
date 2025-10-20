/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;

public class DataFieldDefineMerge {
    public static void merge(DataFieldDefineImpl target, DataFieldDefineImpl base) {
        if (target.getId() == null) {
            target.setId(base.getId());
        }
        if (target.getName() == null) {
            target.setName(base.getName());
        }
        if (target.getTitle() == null) {
            target.setTitle(base.getTitle());
        }
        if (base.getFieldType() != null) {
            target.setFieldType(base.getFieldType());
        }
        if (base.getFieldName() != null) {
            target.setFieldName(base.getFieldName());
        }
        if (base.getValueType() != null) {
            target.setValueType(base.getValueType());
        }
        if (base.getLength() > target.getLength()) {
            target.setLength(base.getLength());
        }
        if (base.getDigits() > target.getDigits()) {
            target.setDigits(base.getDigits());
        }
        if (base.isReadonly()) {
            target.setReadonly(base.isReadonly());
        }
        if (base.isRequired()) {
            target.setRequired(base.isRequired());
        }
        if (target.getRefTableName() == null) {
            target.setRefTableName(base.getRefTableName());
        }
        if (target.getRefTableType() == 0) {
            target.setRefTableType(base.getRefTableType());
        }
        if (base.isSolidified()) {
            target.setSolidified(base.isSolidified());
        }
    }
}

