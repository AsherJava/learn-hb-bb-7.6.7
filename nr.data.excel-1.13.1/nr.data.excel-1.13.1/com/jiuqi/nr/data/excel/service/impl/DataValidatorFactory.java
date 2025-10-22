/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.nr.data.excel.service.impl.EmptyNotZeroFormValidator;
import com.jiuqi.nr.data.excel.service.impl.EmptyZeroFormValidator;
import com.jiuqi.nr.data.excel.service.impl.ZeroNotEmptyFormValidator;
import com.jiuqi.nr.data.excel.service.internal.IRegionDataValidator;

public class DataValidatorFactory {
    private DataValidatorFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static IRegionDataValidator getZeroNotEmptyFormValidator(boolean autoFillIsNullTable) {
        return new ZeroNotEmptyFormValidator(autoFillIsNullTable);
    }

    public static IRegionDataValidator getEmptyZeroFormValidator(boolean autoFillIsNullTable) {
        return new EmptyZeroFormValidator(autoFillIsNullTable);
    }

    public static IRegionDataValidator getEmptyNotZeroFormValidator(boolean autoFillIsNullTable) {
        return new EmptyNotZeroFormValidator(autoFillIsNullTable);
    }
}

