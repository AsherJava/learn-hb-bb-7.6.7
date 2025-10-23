/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ExcelProperty {
    public String sheetName() default "";

    public String sheetSuffix() default "";
}

