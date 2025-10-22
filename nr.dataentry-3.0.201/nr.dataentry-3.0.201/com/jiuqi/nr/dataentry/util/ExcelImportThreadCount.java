/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.util;

import org.springframework.beans.factory.annotation.Value;

public class ExcelImportThreadCount {
    @Value(value="${jiuqi.nr.export.thread-count:10}")
    private int exportThreadCount;
    @Value(value="${jiuqi.nr.print.thread-count:10}")
    private int printThreadCount;

    public int getExportThreadCount() {
        return this.exportThreadCount;
    }

    public int getPrintThreadCount() {
        return this.printThreadCount;
    }
}

