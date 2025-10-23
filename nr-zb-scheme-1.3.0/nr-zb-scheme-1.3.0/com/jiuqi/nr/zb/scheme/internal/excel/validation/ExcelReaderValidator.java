/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel.validation;

import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;

@FunctionalInterface
public interface ExcelReaderValidator {
    public <T> void validate(T var1, IExcelRowWrapper var2);

    default public <T> void afterValidate(T data) {
    }
}

