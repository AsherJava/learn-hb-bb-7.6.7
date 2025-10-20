/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print.domain;

import com.jiuqi.va.query.print.domain.TablePrintDrawCellContext;
import com.jiuqi.va.query.print.domain.TablePrintDrawContext;

public final class QueryPrintThreadLocal {
    private static final ThreadLocal<TablePrintDrawContext> TABLE_PRINT_LOCAL = new ThreadLocal();
    private static final ThreadLocal<TablePrintDrawCellContext> TABLE_CELL_THREAD_LOCAL = new ThreadLocal();

    private QueryPrintThreadLocal() {
    }

    public static void setTablePrintLocal(TablePrintDrawContext tablePrintDrawContext) {
        TABLE_PRINT_LOCAL.set(tablePrintDrawContext);
    }

    public static TablePrintDrawContext getTablePrintDrawContext() {
        return TABLE_PRINT_LOCAL.get();
    }

    public static void removeTablePrintLocal() {
        TABLE_PRINT_LOCAL.remove();
    }

    public static void setTableCellThreadLocal(TablePrintDrawCellContext tablePrintDrawCellContext) {
        TABLE_CELL_THREAD_LOCAL.set(tablePrintDrawCellContext);
    }

    public static TablePrintDrawCellContext getTablePrintDrawCellContext() {
        return TABLE_CELL_THREAD_LOCAL.get();
    }

    public static void removeTablePrintCellContextLocal() {
        TABLE_CELL_THREAD_LOCAL.remove();
    }
}

