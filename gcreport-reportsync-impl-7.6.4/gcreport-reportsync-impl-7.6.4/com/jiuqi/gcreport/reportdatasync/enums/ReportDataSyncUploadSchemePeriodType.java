/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.enums;

public enum ReportDataSyncUploadSchemePeriodType {
    before,
    current,
    custom;


    public static ReportDataSyncUploadSchemePeriodType getPperiodTypeByName(String periodTypeValue) {
        for (ReportDataSyncUploadSchemePeriodType periodType : ReportDataSyncUploadSchemePeriodType.values()) {
            if (!periodType.name().equals(periodTypeValue)) continue;
            return periodType;
        }
        return null;
    }
}

