/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine;

public class ReportEngineOption {
    public static final int MULTISHEETS = 1;
    public static final int DISABLE_EXCEL_FORMULAS = 2;
    public static final int IGNORE_STYLES = 4;
    public static final int CHECK_MODE = 8;
    public static final int WRITEBACK = 16;
    public static final int DISABLE_MESSAGE_LINK = 32;
    public static final int DISABLE_INTERACTION = 64;
    public static final int ENABLE_RESTRICTION = 128;
    public static final int WRITEBACK_SNAPSHOT = 256;
    public static final int DISABLE_URL_LINK = 512;
    public static final String CONFIG_EXCEL_FORMAT = "excel.format";
    public static final String TRACE_CELLS = "tracing.cells";

    private ReportEngineOption() {
    }
}

