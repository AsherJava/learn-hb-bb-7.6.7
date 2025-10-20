/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.Matrix
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.cell.RegionNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.cell;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.Matrix;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.area.RestrictedCell;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.QParserHelper;
import com.jiuqi.bi.quickreport.engine.parser.cell.IExpandable;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportCellNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.cell.RegionNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportRegionNode
extends RegionNode
implements IExpandable {
    private static final long serialVersionUID = 1096617875822272605L;
    private boolean rowExpanding;
    private boolean colExpanding;

    public ReportRegionNode(Token token, IWorksheet workSheet, Region region, int options) {
        super(token, workSheet, region, options);
    }

    @Override
    public boolean isRowExpanding() {
        return this.rowExpanding;
    }

    @Override
    public boolean isColExpanding() {
        return this.colExpanding;
    }

    @Override
    public void setRowExpanding(boolean rowExpanding) {
        this.rowExpanding = rowExpanding;
    }

    @Override
    public void setColExpanding(boolean colExpanding) {
        this.colExpanding = colExpanding;
    }

    public Region calcRegion() {
        int right;
        int left;
        int bottom;
        int top;
        if (!this.region.isColMode() && !this.region.isRowMode()) {
            return this.region;
        }
        GridData grid = ((ReportWorksheet)this.workSheet).getGridData();
        if (this.region.isColMode()) {
            top = 1;
            bottom = grid.getRowCount() - 1;
        } else {
            top = this.region.top();
            bottom = this.region.bottom();
        }
        if (this.region.isRowMode()) {
            left = 1;
            right = grid.getColCount() - 1;
        } else {
            left = this.region.left();
            right = this.region.right();
        }
        return new Region(left, top, right, bottom);
    }

    public final Object evaluate(IContext context) throws SyntaxException {
        return this.getRegionValue((ReportContext)context, false);
    }

    public final ArrayData evaluateRaw(IContext context) throws SyntaxException {
        return this.getRegionValue((ReportContext)context, true);
    }

    public ArrayData evaluateRaw(IContext context, int colSize, int rowSize) throws SyntaxException {
        ArrayData arr = this.evaluateRaw(context);
        return arr == null ? null : arr.fit(colSize, rowSize);
    }

    private ArrayData getRegionValue(ReportContext context, boolean isRaw) throws SyntaxException {
        ExpandingArea expandingArea = this.getRefExpandingArea();
        if (expandingArea == null) {
            return this.getFixedRegionValue(context, isRaw);
        }
        return this.getExpandedRegionValue(context, expandingArea, isRaw);
    }

    public ExpandingArea getRefExpandingArea() throws SyntaxException {
        ExpandingArea area = null;
        GridData grid = ((EngineWorksheet)this.workSheet).getGridData();
        for (int col = this.region.left(); col <= this.region.right(); ++col) {
            for (int row = this.region.top(); row <= this.region.bottom(); ++row) {
                CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj(col, row);
                if (bindingInfo == null || !(bindingInfo.getOwnerArea() instanceof ExpandingArea)) continue;
                if (area == null) {
                    area = (ExpandingArea)bindingInfo.getOwnerArea();
                    continue;
                }
                if (area == bindingInfo.getOwnerArea()) continue;
                throw new SyntaxException("\u533a\u57df" + this + "\u5305\u542b\u591a\u4e2a\u65e0\u5173\u7684\u6d6e\u52a8\u533a\u57df\uff0c\u65e0\u6cd5\u83b7\u53d6\u6570\u636e\u3002");
            }
        }
        return area;
    }

    private ArrayData getFixedRegionValue(ReportContext context, boolean isRaw) throws SyntaxException {
        ArrayList<List<Object>> values = new ArrayList<List<Object>>();
        int baseType = this.readFixedValues(values, isRaw);
        return this.toArrayValue(values, baseType, isRaw);
    }

    private int readFixedValues(List<List<Object>> values, boolean isRaw) throws SyntaxException {
        int bottom;
        int right;
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        try {
            int left = this.region.left() == -1 ? 1 : resultGrid.locateNewCol(this.region.left());
            right = this.region.right() == -1 ? resultGrid.getGridData().getColCount() - 1 : resultGrid.locateNewCol(this.region.right());
            int top = this.region.top() == -1 ? 1 : resultGrid.locateNewRow(this.region.top());
            bottom = this.region.bottom() == -1 ? resultGrid.getGridData().getRowCount() - 1 : resultGrid.locateNewRow(this.region.bottom());
        }
        catch (ReportBuildException e) {
            throw new SyntaxException("\u5b9a\u4f4d\u533a\u57df\u8303\u56f4\u51fa\u9519\uff1a" + this, (Throwable)e);
        }
        boolean hasNumber = false;
        boolean hasText = false;
        for (int row = top; row <= bottom; ++row) {
            ArrayList<Object> record = new ArrayList<Object>();
            for (int col = left; col <= right; ++col) {
                int cellDataType = this.readCellValue(record, resultGrid.getGridData(), col, row, isRaw);
                if (cellDataType == 3) {
                    hasNumber = true;
                    continue;
                }
                if (cellDataType != 6) continue;
                hasText = true;
            }
            values.add(record);
        }
        if (hasNumber || !hasText) {
            return 3;
        }
        return 6;
    }

    private int readCellValue(List<Object> record, GridData grid, int col, int row, boolean isRaw) {
        CellField field = grid.expandCell(col, row);
        if (field.left != col || field.top != row) {
            record.add(null);
            return 0;
        }
        GridCell cell = grid.getCellEx(col, row);
        CellValue cellValue = (CellValue)grid.getObj(cell.getColNum(), cell.getRowNum());
        if (cellValue == null) {
            return this.readCellValue(record, cell);
        }
        return this.readCellValue(record, cell, cellValue);
    }

    private int readCellValue(List<Object> record, GridCell cell, CellValue cellValue) {
        record.add(cellValue.value);
        if (cellValue.value == null) {
            switch (cell.getType()) {
                case 2: 
                case 3: 
                case 4: {
                    return 3;
                }
                case 1: {
                    return 6;
                }
            }
            return 0;
        }
        if (cellValue.value instanceof Number) {
            return 3;
        }
        if (cellValue.value instanceof String) {
            return 6;
        }
        return 0;
    }

    private int readCellValue(List<Object> record, GridCell cell) {
        switch (cell.getType()) {
            case 2: 
            case 3: 
            case 4: {
                if (StringUtils.isEmpty((String)cell.getCellData())) {
                    record.add(null);
                } else {
                    record.add(cell.getFloat());
                }
                return 3;
            }
            case 1: {
                record.add(cell.getCellData());
                return 6;
            }
            case 5: {
                if (StringUtils.isEmpty((String)cell.getCellData())) {
                    record.add(null);
                } else {
                    Date date = cell.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    record.add(cal);
                }
                return 0;
            }
        }
        if (StringUtils.isEmpty((String)cell.getCellData())) {
            record.add(null);
            return 0;
        }
        try {
            double v = Double.parseDouble(cell.getCellData());
            record.add(v);
            return 3;
        }
        catch (NumberFormatException e) {
            record.add(cell.getCellData());
            return 6;
        }
    }

    private boolean needMergedCell(GridData grid, CellField field, int col, int row) {
        if (this.rowExpanding && this.colExpanding) {
            return true;
        }
        if (this.rowExpanding) {
            return col == field.left;
        }
        if (this.colExpanding) {
            return row == field.top;
        }
        return false;
    }

    private ArrayData getExpandedRegionValue(ReportContext context, ExpandingArea refArea, boolean isRaw) throws SyntaxException {
        if (this.isRestrictMode(context, refArea)) {
            return this.getRestrictedExpandedValues(context, refArea, isRaw);
        }
        return this.getAllExpandedValues(context, refArea, isRaw);
    }

    protected boolean isRestrictMode(ReportContext context, ExpandingArea refArea) {
        return context.getCurrentCell().getBindingInfo().getOwnerArea() == refArea;
    }

    private ArrayData getRestrictedExpandedValues(ReportContext context, ExpandingArea refArea, boolean isRaw) throws SyntaxException {
        List<AxisDataNode> restrictions = this.calcRegionRestrictions(context, refArea);
        ArrayList<List<Object>> values = new ArrayList<List<Object>>();
        int baseType = this.readExpandedValues(values, isRaw, restrictions, refArea);
        return this.toArrayValue(values, baseType, isRaw);
    }

    protected List<AxisDataNode> calcRegionRestrictions(ReportContext context, ExpandingArea refArea) throws SyntaxException {
        HashSet<ExpandingRegion> colRestrictions = new HashSet<ExpandingRegion>();
        HashSet<ExpandingRegion> rowRestrictions = new HashSet<ExpandingRegion>();
        this.getRawRestrictions(colRestrictions, rowRestrictions, refArea);
        ArrayList<ExpandingRegion> allRestrictions = new ArrayList<ExpandingRegion>();
        RestrictedCell evalCell = refArea.getRestrictionMap().get(context.getCurrentCell().getBindingInfo());
        for (ExpandingRegion restriction : evalCell.getRowRestrictions()) {
            if (!rowRestrictions.contains(restriction)) continue;
            allRestrictions.add(restriction);
        }
        for (ExpandingRegion restriction : evalCell.getColRestrictions()) {
            if (!colRestrictions.contains(restriction)) continue;
            allRestrictions.add(restriction);
        }
        ArrayList<AxisDataNode> restrictions = new ArrayList<AxisDataNode>();
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        Position evalPos = context.getCurrentCell().getPosition();
        ExpandingCalcCell evalCalcCell = (ExpandingCalcCell)resultGrid.getGridData().getObj(evalPos.col(), evalPos.row());
        for (ExpandingRegion region : allRestrictions) {
            AxisDataNode data = ReportCellNode.findAxisData(evalCalcCell.getRestrictions(), region);
            restrictions.add(data);
        }
        return restrictions;
    }

    private void getRawRestrictions(Set<ExpandingRegion> colRestrictions, Set<ExpandingRegion> rowRestrictions, ExpandingArea refArea) throws SyntaxException {
        GridData rawGrid = ((EngineWorksheet)this.workSheet).getGridData();
        List<ExpandingRegion> colList = null;
        List<ExpandingRegion> rowList = null;
        for (int row = this.region.top(); row <= this.region.bottom(); ++row) {
            for (int col = this.region.left(); col <= this.region.right(); ++col) {
                CellBindingInfo bindingInfo = (CellBindingInfo)rawGrid.getObj(col, row);
                if (bindingInfo == null) continue;
                RestrictedCell refCell = refArea.getRestrictionMap().get(bindingInfo);
                if (colList == null || this.containRestrictions(refCell.getColRestrictions(), colList)) {
                    colList = refCell.getColRestrictions();
                } else if (!this.containRestrictions(colList, refCell.getColRestrictions())) {
                    throw new SyntaxException(this.getToken(), "\u65e0\u6cd5\u5904\u7406\u6d6e\u52a8\u680f\u9650\u5b9a\uff0c\u53ef\u80fd\u662f\u5305\u542b\u5e76\u5217\u6d6e\u52a8\u680f\u7b49\u539f\u56e0\u5bfc\u81f4\u8be5\u95ee\u9898\u3002");
                }
                if (rowList == null || this.containRestrictions(refCell.getRowRestrictions(), rowList)) {
                    rowList = refCell.getRowRestrictions();
                    continue;
                }
                if (this.containRestrictions(rowList, refCell.getRowRestrictions())) continue;
                throw new SyntaxException(this.getToken(), "\u65e0\u6cd5\u5904\u7406\u6d6e\u52a8\u884c\u9650\u5b9a\uff0c\u53ef\u80fd\u662f\u5305\u542b\u4e86\u5e76\u5217\u6d6e\u52a8\u884c\u7b49\u539f\u56e0\u5bfc\u81f4\u8be5\u95ee\u9898\u3002");
            }
        }
        if (colList != null) {
            colRestrictions.addAll(colList);
        }
        if (rowList != null) {
            rowRestrictions.addAll(rowList);
        }
    }

    private boolean containRestrictions(List<ExpandingRegion> restrictions1, List<ExpandingRegion> restrictions2) {
        if (restrictions1.size() < restrictions2.size()) {
            return false;
        }
        for (int i = 0; i < restrictions2.size(); ++i) {
            if (restrictions1.get(i) == restrictions2.get(i)) continue;
            return false;
        }
        return true;
    }

    private int readExpandedValues(List<List<Object>> values, boolean isRaw, List<AxisDataNode> restrictions, ExpandingArea refArea) {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        GridData grid = resultGrid.getGridData();
        List<Integer> cols = resultGrid.getExpandedCols(this.region.left(), this.region.right());
        List<Integer> rows = resultGrid.getExpandedRows(this.region.top(), this.region.bottom());
        Matrix flags = new Matrix();
        flags.setSize(cols.size(), rows.size());
        boolean hasNumber = false;
        boolean hasText = false;
        for (int row : rows) {
            ArrayList<Object> record = new ArrayList<Object>();
            for (int col : cols) {
                int baseDataType;
                GridCell cell = this.fetchCell(grid, col, row, isRaw);
                if (cell == null) {
                    record.add(null);
                    continue;
                }
                CellValue cellValue = (CellValue)grid.getObj(cell.getColNum(), cell.getRowNum());
                if (cellValue == null) {
                    baseDataType = this.readCellValue(record, cell);
                } else {
                    flags.setElement(record.size(), values.size(), (Object)(restrictions == null || ReportCellNode.contains(restrictions, cellValue._restrictions) ? 1 : 0));
                    baseDataType = this.readCellValue(record, cell, cellValue);
                }
                if (baseDataType == 3) {
                    hasNumber = true;
                    continue;
                }
                if (baseDataType != 6) continue;
                hasText = true;
            }
            values.add(record);
        }
        if (restrictions != null) {
            this.ignoreValues(values, (Matrix<Boolean>)flags, refArea);
        }
        if (hasNumber || !hasText) {
            return 3;
        }
        return 6;
    }

    private GridCell fetchCell(GridData grid, int col, int row, boolean isRaw) {
        CellField field = grid.expandCell(col, row);
        if (field.left == col && field.top == row) {
            return grid.getCellEx(col, row);
        }
        if (isRaw && this.needMergedCell(grid, field, col, row)) {
            return grid.getCellEx(field.left, field.top);
        }
        return null;
    }

    private void ignoreValues(List<List<Object>> values, Matrix<Boolean> flags, ExpandingArea refArea) {
        if (refArea.getRowAxis().isEmpty()) {
            this.ignoreCols(values, flags);
            this.ignoreRows(values, flags);
        } else {
            this.ignoreRows(values, flags);
            this.ignoreCols(values, flags);
        }
    }

    private void ignoreCols(List<List<Object>> values, Matrix<Boolean> flags) {
        for (int col = flags.getXCount() - 1; col >= 0; --col) {
            if (!this.isEmptyCol(flags, col)) continue;
            for (List<Object> record : values) {
                record.remove(col);
            }
            flags.xDelete(col);
        }
    }

    private void ignoreRows(List<List<Object>> values, Matrix<Boolean> flags) {
        for (int row = flags.getYCount() - 1; row >= 0; --row) {
            if (!this.isEmptyRow(flags, row)) continue;
            values.remove(row);
            flags.yDelete(row);
        }
    }

    private boolean isEmptyCol(Matrix<Boolean> flags, int col) {
        for (int row = 0; row < flags.getYCount(); ++row) {
            Boolean flag = (Boolean)flags.getElement(col, row);
            if (flag == null || flag.booleanValue()) continue;
            return true;
        }
        return false;
    }

    private boolean isEmptyRow(Matrix<Boolean> flags, int row) {
        for (int col = 0; col < flags.getXCount(); ++col) {
            Boolean flag = (Boolean)flags.getElement(col, row);
            if (flag == null || flag.booleanValue()) continue;
            return true;
        }
        return false;
    }

    private ArrayData getAllExpandedValues(ReportContext context, ExpandingArea refArea, boolean isRaw) throws SyntaxException {
        ArrayList<List<Object>> values = new ArrayList<List<Object>>();
        int baseType = this.readExpandedValues(values, isRaw, null, refArea);
        return this.toArrayValue(values, baseType, isRaw);
    }

    private ArrayData toArrayValue(List<List<Object>> values, int baseType, boolean isRaw) {
        if (isRaw) {
            return this.toRawArray(values);
        }
        if (baseType == 3) {
            return this.toNumberArray(values);
        }
        return this.toStringArray(values);
    }

    private ArrayData toRawArray(List<List<Object>> values) {
        int colSize = this.sizeOfCol(values);
        if (colSize == 0) {
            return null;
        }
        ArrayData arr = new ArrayData(0, colSize, values.size());
        for (int row = 0; row < values.size(); ++row) {
            List<Object> record = values.get(row);
            for (int col = 0; col < record.size(); ++col) {
                Object value = record.get(col);
                arr.set(col, row, value);
            }
        }
        return arr;
    }

    private ArrayData toNumberArray(List<List<Object>> values) {
        int colSize = this.sizeOfCol(values);
        if (colSize == 0) {
            return null;
        }
        ArrayData arr = new ArrayData(3, colSize, values.size());
        for (int row = 0; row < values.size(); ++row) {
            List<Object> record = values.get(row);
            for (int col = 0; col < record.size(); ++col) {
                Object value = record.get(col);
                if (value instanceof Number) {
                    arr.set(col, row, value);
                    continue;
                }
                if (!(value instanceof String)) continue;
                try {
                    double d = Double.parseDouble(value.toString());
                    arr.set(col, row, (Object)d);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        }
        return arr;
    }

    private ArrayData toStringArray(List<List<Object>> values) {
        int colSize = this.sizeOfCol(values);
        if (colSize == 0) {
            return null;
        }
        ArrayData arr = new ArrayData(6, colSize, values.size());
        for (int row = 0; row < values.size(); ++row) {
            List<Object> record = values.get(row);
            for (int col = 0; col < record.size(); ++col) {
                Object value = record.get(col);
                if (value instanceof Calendar) {
                    String dateStr = DataType.formatValue((int)2, (Object)value);
                    arr.set(col, row, (Object)dateStr);
                    continue;
                }
                if (value == null) continue;
                arr.set(col, row, (Object)value.toString());
            }
        }
        return arr;
    }

    private int sizeOfCol(List<List<Object>> values) {
        int colSize = 0;
        for (List<Object> record : values) {
            if (record.size() <= colSize) continue;
            colSize = record.size();
        }
        return colSize;
    }

    public final int getFirstRow(IContext context) throws SyntaxException {
        ExpandingArea refArea = this.getRefExpandingArea();
        GridArea evalArea = ((ReportContext)context).getCurrentCell().getBindingInfo().getOwnerArea();
        if (refArea == null || refArea != evalArea) {
            return this.locateFirstRow(context);
        }
        return this.locateRestrictedFirstPos((ReportContext)context, refArea, new RowLocator());
    }

    private int locateFirstRow(IContext context) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        try {
            return resultGrid.locateNewRow(this.region.top());
        }
        catch (ReportBuildException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public final int getFirstCol(IContext context) throws SyntaxException {
        ExpandingArea refArea = this.getRefExpandingArea();
        GridArea evalArea = ((ReportContext)context).getCurrentCell().getBindingInfo().getOwnerArea();
        if (refArea == null || refArea != evalArea) {
            return this.locateFirstCol(context);
        }
        return this.locateRestrictedFirstPos((ReportContext)context, refArea, new ColLocator());
    }

    private int locateFirstCol(IContext context) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        try {
            return resultGrid.locateNewCol(this.region.left());
        }
        catch (ReportBuildException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    private int locateRestrictedFirstPos(ReportContext context, ExpandingArea refArea, IPositionLocator colLocator) throws SyntaxException {
        List<AxisDataNode> restrictions = this.calcRegionRestrictions(context, refArea);
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        List<Integer> cols = resultGrid.getExpandedCols(this.region.left(), this.region.right());
        List<Integer> rows = resultGrid.getExpandedRows(this.region.top(), this.region.bottom());
        return colLocator.scanFirstPos(restrictions, resultGrid, cols, rows);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (info instanceof DSFormulaInfo) {
            QParserHelper.evalInterpret(context, (IASTNode)this, buffer);
        } else {
            super.toFormula(context, buffer, info);
        }
    }

    private final class ColLocator
    implements IPositionLocator {
        private ColLocator() {
        }

        @Override
        public int scanFirstPos(List<AxisDataNode> restrictions, ResultGridData resultGrid, List<Integer> cols, List<Integer> rows) throws SyntaxException {
            for (int col : cols) {
                for (int row : rows) {
                    Object obj = resultGrid.getGridData().getObj(col, row);
                    if (!(obj instanceof CellValue)) continue;
                    CellValue cellValue = (CellValue)obj;
                    if (!ReportCellNode.contains(cellValue._restrictions, restrictions)) continue;
                    return col;
                }
            }
            throw new SyntaxException("\u65e0\u6cd5\u5b9a\u4f4d\u5355\u5143\u683c\u4f4d\u7f6e\uff1a" + this);
        }
    }

    private final class RowLocator
    implements IPositionLocator {
        private RowLocator() {
        }

        @Override
        public int scanFirstPos(List<AxisDataNode> restrictions, ResultGridData resultGrid, List<Integer> cols, List<Integer> rows) throws SyntaxException {
            for (int row : rows) {
                for (int col : cols) {
                    Object obj = resultGrid.getGridData().getObj(col, row);
                    if (!(obj instanceof CellValue)) continue;
                    CellValue cellValue = (CellValue)obj;
                    if (!ReportCellNode.contains(cellValue._restrictions, restrictions)) continue;
                    return row;
                }
            }
            throw new SyntaxException("\u65e0\u6cd5\u5b9a\u4f4d\u5355\u5143\u683c\u4f4d\u7f6e\uff1a" + this);
        }
    }

    private static interface IPositionLocator {
        public int scanFirstPos(List<AxisDataNode> var1, ResultGridData var2, List<Integer> var3, List<Integer> var4) throws SyntaxException;
    }
}

