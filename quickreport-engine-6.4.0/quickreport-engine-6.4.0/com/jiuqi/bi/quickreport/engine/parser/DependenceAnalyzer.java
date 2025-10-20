/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.ReportLog;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

final class DependenceAnalyzer {
    private ReportWorkbook workbook;
    private boolean checkMode;
    private List<CellBindingInfo> bindingInfos = new LinkedList<CellBindingInfo>();
    private int currentOrder;
    private Map<SheetPosition, String> errors = new TreeMap<SheetPosition, String>();
    private static final int MAX_ROUTE_PRINT = 1000;
    private static final int MAX_ROUTE_DISPLAY = 100;

    public ReportWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(ReportWorkbook workbook) {
        this.workbook = workbook;
    }

    public boolean isCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(boolean checkMode) {
        this.checkMode = checkMode;
    }

    public void execute() throws ReportExpressionException {
        if (this.collectInfos()) {
            this.analyse();
        } else {
            this.complete();
        }
    }

    private void analyse() throws ReportExpressionException {
        while (!this.bindingInfos.isEmpty()) {
            if (this.scanCells() != 0 || this.bindingInfos.isEmpty()) continue;
            this.checkDeadRoute();
        }
    }

    private boolean collectInfos() {
        boolean depended = false;
        for (int i = 0; i < this.workbook.sheetSize(); ++i) {
            ReportWorksheet worksheet = this.workbook.getSheet(i);
            GridData grid = worksheet.getGridData();
            for (int row = 1; row < grid.getRowCount(); ++row) {
                for (int col = 1; col < grid.getColCount(); ++col) {
                    CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj(col, row);
                    if (bindingInfo == null) continue;
                    if (!bindingInfo.getDepends().isEmpty()) {
                        bindingInfo._setIndegree(bindingInfo.getDepends().size());
                        depended = true;
                    }
                    this.bindingInfos.add(bindingInfo);
                }
            }
        }
        return depended;
    }

    private int scanCells() {
        int count = 0;
        ++this.currentOrder;
        Iterator<CellBindingInfo> i = this.bindingInfos.iterator();
        while (i.hasNext()) {
            CellBindingInfo bindingInfo = i.next();
            if (bindingInfo._getIndegree() != 0) continue;
            this.markCompleted(bindingInfo);
            i.remove();
            ++count;
        }
        return count;
    }

    private void markCompleted(CellBindingInfo bindingInfo) {
        bindingInfo.setToplogicOrder(this.currentOrder);
        for (CellBindingInfo next : bindingInfo.getAffects()) {
            next._setIndegree(next._getIndegree() - 1);
        }
    }

    private void checkDeadRoute() throws ReportExpressionException {
        ArrayDeque<CellBindingInfo> route = new ArrayDeque<CellBindingInfo>();
        if (!this.scanRoute(route, this.bindingInfos.get(0))) {
            ReportLog.openLogger().error("\u5206\u6790\u5355\u5143\u683c\u5faa\u73af\u8def\u5f84\u65f6\u51fa\u73b0\u9519\u8bef\uff0c\u65e0\u6cd5\u5b8c\u6210\u8def\u5f84\u5206\u6790\uff1a" + DependenceAnalyzer.toString(this.bindingInfos, 1000));
            throw new ReportExpressionException("\u5206\u6790\u5355\u5143\u683c\u5faa\u73af\u8def\u5f84\u65f6\u51fa\u73b0\u9519\u8bef\uff0c\u65e0\u6cd5\u5b8c\u6210\u8def\u5f84\u5206\u6790\uff1a" + DependenceAnalyzer.toString(this.bindingInfos, 1000));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean scanRoute(Deque<CellBindingInfo> route, CellBindingInfo current) throws ReportExpressionException {
        if (route.contains(current)) {
            String message = "\u68c0\u6d4b\u5230\u5faa\u73af\u4f9d\u8d56\u7684\u5355\u5143\u683c\u5f15\u7528" + DependenceAnalyzer.toString(route, 1000) + "\u3002";
            this.errors.put(current.getPosition(), message);
            if (this.checkMode) {
                this.breakRoute(current, route);
                return true;
            }
            throw new ReportExpressionException(message);
        }
        route.push(current);
        try {
            for (CellBindingInfo next : current.getAffects()) {
                if (next._getIndegree() == 0) continue;
                if (!this.scanRoute(route, next)) continue;
                boolean bl = true;
                return bl;
            }
        }
        finally {
            route.pop();
        }
        return false;
    }

    private void breakRoute(CellBindingInfo current, Deque<CellBindingInfo> route) {
        ++this.currentOrder;
        this.markCompleted(current);
        current._setIndegree(0);
        this.bindingInfos.remove(current);
        current.setErrorMessage("\u5355\u5143\u8ba1\u7b97\u5b58\u5728\u5faa\u73af\u7684\u4f9d\u8d56\u5173\u7cfb\uff1a" + DependenceAnalyzer.toString(route, 100));
    }

    private void complete() {
        ++this.currentOrder;
        this.bindingInfos.forEach(bi -> bi.setToplogicOrder(this.currentOrder));
    }

    public static String toString(Collection<CellBindingInfo> cells, int maxSize) {
        StringBuilder buffer = new StringBuilder("[");
        int count = 0;
        for (CellBindingInfo cell : cells) {
            if (count > 0) {
                buffer.append(", ");
            }
            if (++count >= maxSize) {
                buffer.append("...");
                break;
            }
            buffer.append(cell.getPosition());
        }
        buffer.append(']');
        return buffer.toString();
    }

    public Map<SheetPosition, String> getErrors() {
        return this.errors;
    }

    public void execute(CellBindingInfo cell) throws ReportExpressionException {
        this.collectInfos(cell);
        this.analyse();
    }

    private void collectInfos(CellBindingInfo cell) {
        Set<CellBindingInfo> cells = this.getAffectedCells(cell);
        this.initCellInfos(cells);
    }

    private Set<CellBindingInfo> getAffectedCells(CellBindingInfo cell) {
        HashSet<CellBindingInfo> cells = new HashSet<CellBindingInfo>();
        ArrayDeque<CellBindingInfo> stack = new ArrayDeque<CellBindingInfo>();
        stack.push(cell);
        while (!stack.isEmpty()) {
            CellBindingInfo current = (CellBindingInfo)stack.pop();
            if (!cells.add(current)) continue;
            for (CellBindingInfo next : current.getAffects()) {
                stack.push(next);
            }
        }
        return cells;
    }

    private void initCellInfos(Set<CellBindingInfo> cells) {
        this.bindingInfos.addAll(cells);
        for (CellBindingInfo cell : this.bindingInfos) {
            cell._setIndegree(0);
            for (CellBindingInfo prev : cell.getDepends()) {
                if (!cells.contains(prev)) continue;
                cell._setIndegree(cell._getIndegree() + 1);
            }
            cell.setErrorMessage(null);
        }
    }
}

