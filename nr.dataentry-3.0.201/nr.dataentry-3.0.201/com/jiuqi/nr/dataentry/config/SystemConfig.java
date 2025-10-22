/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
    @Value(value="${jiuqi.nvwa.export.excel.format:xls}")
    public String excelFormat;

    public String getExcelFormat() {
        return this.excelFormat;
    }

    public void setExcelFormat(String excelFormat) {
        this.excelFormat = excelFormat;
    }
}

