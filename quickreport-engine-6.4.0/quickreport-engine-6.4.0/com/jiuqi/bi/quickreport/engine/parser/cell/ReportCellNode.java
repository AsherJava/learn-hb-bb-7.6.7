/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.ArrayData$ItemIterator
 *  com.jiuqi.bi.syntax.grid.GridHelper
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.cell;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridData;
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
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackRestrictions;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.grid.GridHelper;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class ReportCellNode
extends CellNode
implements IExpandable {
    private static final long serialVersionUID = -2421644117902322272L;
    private boolean rowExpanding;
    private boolean colExpanding;

    public ReportCellNode(Token token, IWorksheet workSheet, Position position, int options) {
        super(token, workSheet, position, options);
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

    public final int getType(IContext context) throws SyntaxException {
        GridData grid = ((ReportWorksheet)this.workSheet).getGridData();
        if (this.position.isValidate()) {
            return GridHelper.getCellType((GridData)grid, (int)this.position.col(), (int)this.position.row());
        }
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ReportContext rptContext = (ReportContext)context;
        switch (this.getReadingMode(rptContext)) {
            case FIXED: {
                return this.getFixedCellValue(rptContext);
            }
            case ALL: {
                return this.getAllExpandedValues(rptContext);
            }
            case OFFSET: {
                return this.getOffsetValue(rptContext);
            }
            case RESTRICTED: {
                return this.getRestrictedValues(rptContext);
            }
        }
        throw new SyntaxException(this.getToken(), "\u7a0b\u5e8f\u9519\u8bef\uff0c\u65e0\u6cd5\u5224\u65ad\u5355\u5143\u683c\u503c\u7684\u8bfb\u53d6\u65b9\u5f0f\u3002");
    }

    protected ReadingMode getReadingMode(ReportContext context) {
        if (this.isFixedCell()) {
            return ReadingMode.FIXED;
        }
        if (this.isSameArea(context)) {
            if (this.isOffsetMode(context)) {
                return ReadingMode.OFFSET;
            }
            return ReadingMode.RESTRICTED;
        }
        return ReadingMode.ALL;
    }

    private boolean isFixedCell() {
        return !(this.getCellArea() instanceof ExpandingArea);
    }

    public CellBindingInfo getCellInfo() {
        EngineWorksheet sheet = (EngineWorksheet)this.workSheet;
        return (CellBindingInfo)sheet.getGridData().getObj(this.position.col(), this.position.row());
    }

    public GridArea getCellArea() {
        CellBindingInfo bindingInfo = this.getCellInfo();
        return bindingInfo == null ? null : bindingInfo.getOwnerArea();
    }

    public boolean isMasterCell() {
        CellBindingInfo bindingInfo = this.getCellInfo();
        return bindingInfo != null && bindingInfo.isMaster();
    }

    private Object getFixedCellValue(ReportContext context) throws SyntaxException {
        Position newPos;
        EngineWorksheet sheet = (EngineWorksheet)this.workSheet;
        try {
            newPos = sheet.getResultGrid().locateNewPostion(this.position);
        }
        catch (ReportBuildException e) {
            throw new SyntaxException((Throwable)e);
        }
        return ReportCellNode.readCellValue(sheet.getResultGrid().getGridData(), newPos.col(), newPos.row());
    }

    private boolean isSameArea(ReportContext context) {
        GridArea refArea;
        if (context.getCurrentCell() == null) {
            return false;
        }
        GridArea evalArea = context.getCurrentCell().getArea();
        return evalArea == (refArea = this.getCellArea());
    }

    private boolean isOffsetMode(ReportContext context) {
        RestrictedCell evalCell = this.getEvalCell(context);
        RestrictedCell refCell = this.getRefCell(context);
        return evalCell.isSameRestrictions(refCell);
    }

    private RestrictedCell getEvalCell(ReportContext context) {
        CellBindingInfo evalBindingInfo = context.getCurrentCell().getBindingInfo();
        ExpandingArea expandingArea = (ExpandingArea)evalBindingInfo.getOwnerArea();
        return expandingArea.getRestrictionMap().get(evalBindingInfo);
    }

    private RestrictedCell getRefCell(ReportContext context) {
        CellBindingInfo refBindingInfo = this.getCellInfo();
        CellBindingInfo evalBindingInfo = context.getCurrentCell().getBindingInfo();
        ExpandingArea expandingArea = (ExpandingArea)evalBindingInfo.getOwnerArea();
        return expandingArea.getRestrictionMap().get(refBindingInfo);
    }

    private Object getOffsetValue(ReportContext context) {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        Position rawPos = context.getCurrentCell().getBindingInfo().getPosition().getPosition();
        Position newPos = context.getCurrentCell().getPosition();
        int col = this.position.col() + newPos.col() - rawPos.col();
        int row = this.position.row() + newPos.row() - rawPos.row();
        return ReportCellNode.readCellValue(resultGrid.getGridData(), col, row);
    }

    private Object getRestrictedValues(ReportContext context) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        List<AxisDataNode> restrictions = this.calcCellRestrictions(context, resultGrid);
        List<Position> cellPositions = this.getRestrictedCellPositions(context, restrictions, resultGrid);
        return ReportCellNode.readArrayValues(resultGrid.getGridData(), cellPositions);
    }

    protected List<AxisDataNode> calcCellRestrictions(ReportContext context, ResultGridData resultGrid) throws SyntaxException {
        RestrictedCell evalCell = this.getEvalCell(context);
        RestrictedCell refCell = this.getRefCell(context);
        Position evalPos = context.getCurrentCell().getPosition();
        ExpandingCalcCell evalCalcCell = (ExpandingCalcCell)resultGrid.getGridData().getObj(evalPos.col(), evalPos.row());
        ArrayList<AxisDataNode> restrictions = new ArrayList<AxisDataNode>();
        this.getCellRestrictions(restrictions, evalCalcCell.getRestrictions(), evalCell.getRowRestrictions(), refCell.getRowRestrictions());
        this.getCellRestrictions(restrictions, evalCalcCell.getRestrictions(), evalCell.getColRestrictions(), refCell.getColRestrictions());
        return restrictions;
    }

    private void getCellRestrictions(List<AxisDataNode> restrictions, List<AxisDataNode> allRestrictions, List<ExpandingRegion> evalRegions, List<ExpandingRegion> refRegions) throws SyntaxException {
        ExpandingRegion refRegion;
        ExpandingRegion evalRegion;
        for (int i = 0; i < Math.min(evalRegions.size(), refRegions.size()) && (evalRegion = evalRegions.get(i)) == (refRegion = refRegions.get(i)); ++i) {
            AxisDataNode data = ReportCellNode.findAxisData(allRestrictions, evalRegion);
            restrictions.add(data);
        }
    }

    static AxisDataNode findAxisData(List<AxisDataNode> allDatas, ExpandingRegion region) throws SyntaxException {
        for (AxisDataNode data : allDatas) {
            if (data.getRegion() != region) continue;
            return data;
        }
        throw new SyntaxException("\u65e0\u6cd5\u5b9a\u4f4d\u9650\u5b9a\u6761\u4ef6\u7684\u503c\uff1a" + (Object)((Object)region.getField()));
    }

    private List<Position> getRestrictedCellPositions(ReportContext context, List<AxisDataNode> restrictions, ResultGridData resultGrid) {
        ArrayList<Position> positions = new ArrayList<Position>();
        List<Integer> cols = resultGrid.getExpandedCols(this.position.col());
        List<Integer> rows = resultGrid.getExpandedRows(this.position.row());
        boolean writebackMode = context.getTag() instanceof WritebackRestrictions;
        for (int row : rows) {
            for (int col : cols) {
                Object obj = resultGrid.getGridData().getObj(col, row);
                if (!(obj instanceof CellValue)) continue;
                CellValue cellValue = (CellValue)obj;
                if ((writebackMode || !ReportCellNode.contains(cellValue._restrictions, restrictions)) && (!writebackMode || !ReportCellNode.contains(restrictions, cellValue._restrictions))) continue;
                positions.add(Position.valueOf((int)col, (int)row));
            }
        }
        return positions;
    }

    static boolean contains(List<AxisDataNode> restrictions1, List<AxisDataNode> restrictions2) {
        if (restrictions1 == null || restrictions1.isEmpty()) {
            return restrictions2 == null || restrictions2.isEmpty();
        }
        if (restrictions2 == null || restrictions2.isEmpty()) {
            return true;
        }
        return restrictions1 == restrictions2 || restrictions1.containsAll(restrictions2);
    }

    private static Object readArrayValues(GridData grid, List<Position> cells) {
        switch (cells.size()) {
            case 0: {
                return null;
            }
            case 1: {
                return ReportCellNode.readCellValue(grid, cells.get(0).col(), cells.get(0).row());
            }
        }
        ArrayList<Object> values = new ArrayList<Object>(cells.size());
        boolean isNumber = ReportCellNode.readValues(grid, cells, values);
        ArrayData arr = new ArrayData(isNumber ? 3 : 6, cells.size());
        ReportCellNode.fillValues(arr, values, isNumber);
        return arr;
    }

    private static boolean readValues(GridData grid, List<Position> cells, List<Object> values) {
        boolean isNumber = true;
        for (Position p : cells) {
            Object value = ReportCellNode.readCellValue(grid, p.col(), p.row());
            values.add(value);
            if (value == null || value instanceof Number) continue;
            isNumber = false;
        }
        return isNumber;
    }

    private Object getAllExpandedValues(ReportContext context) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        List<Integer> cols = resultGrid.getExpandedCols(this.position.col());
        List<Integer> rows = resultGrid.getExpandedRows(this.position.row());
        if (cols.isEmpty() || rows.isEmpty()) {
            throw new SyntaxException("\u5b9a\u4f4d\u6d6e\u52a8\u5355\u5143\u683c\u5931\u8d25\uff1a" + this.toString());
        }
        return this.readArrayValues(resultGrid.getGridData(), cols, rows);
    }

    private Object readArrayValues(GridData grid, List<Integer> cols, List<Integer> rows) {
        ArrayList<Object> values = new ArrayList<Object>();
        boolean isNumber = this.readValues(grid, cols, rows, values);
        if (cols.size() == 1 && rows.size() == 1) {
            return values.get(0);
        }
        ArrayData arr = cols.size() == 1 ? new ArrayData(isNumber ? 3 : 6, 1, rows.size()) : (rows.size() == 1 ? new ArrayData(isNumber ? 3 : 6, cols.size()) : new ArrayData(isNumber ? 3 : 6, cols.size(), rows.size()));
        ReportCellNode.fillValues(arr, values, isNumber);
        return arr;
    }

    private static void fillValues(ArrayData arr, List<Object> values, boolean isNumber) {
        ArrayData.ItemIterator itr = arr.itemIterator();
        if (isNumber) {
            for (Object value : values) {
                itr.next();
                itr.set(value);
            }
        } else {
            for (Object value : values) {
                itr.next();
                itr.set((Object)DataType.formatValue((int)0, (Object)value));
            }
        }
    }

    private boolean readValues(GridData grid, List<Integer> cols, List<Integer> rows, List<Object> values) {
        boolean isNumber = true;
        for (int row : rows) {
            for (int col : cols) {
                Object value = this.readExpandValue(grid, col, row);
                values.add(value);
                if (value == null || value instanceof Number) continue;
                isNumber = false;
            }
        }
        return isNumber;
    }

    private Object readExpandValue(GridData grid, int col, int row) {
        CellField field = grid.expandCell(col, row);
        if (field.left != col || field.top != row) {
            if (this.rowExpanding && this.colExpanding) {
                return ReportCellNode.readCellValue(grid, field.left, field.top);
            }
            if (this.rowExpanding && field.left == col) {
                return ReportCellNode.readCellValue(grid, col, field.top);
            }
            if (this.colExpanding && field.top == row) {
                return ReportCellNode.readCellValue(grid, field.left, row);
            }
        }
        return ReportCellNode.readCellValue(grid, col, row);
    }

    private static Object readCellValue(GridData grid, int col, int row) {
        CellValue cellValue = (CellValue)grid.getObj(col, row);
        if (cellValue == null) {
            return GridHelper.readCellValue((GridData)grid, (int)col, (int)row);
        }
        return cellValue.value;
    }

    public final int getFirstRow(IContext context) throws SyntaxException {
        if (this.isFixedCell() || !this.isSameArea((ReportContext)context)) {
            return this.locateFirstRow(context);
        }
        return this.locateRestrictedFirstPos((ReportContext)context, new RowLocator());
    }

    private int locateFirstRow(IContext context) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        try {
            return resultGrid.locateNewRow(this.position.row());
        }
        catch (ReportBuildException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    private int locateRestrictedFirstPos(ReportContext context, IPositionLocator locator) throws SyntaxException {
        if (this.isOffsetMode(context)) {
            return locator.offsetFirstPos(context, ((EngineWorksheet)this.workSheet).getResultGrid());
        }
        return this.getRestrictedFirstPos(context, locator);
    }

    private int getRestrictedFirstPos(ReportContext context, IPositionLocator locator) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        List<AxisDataNode> restrictions = this.calcCellRestrictions(context, resultGrid);
        return locator.scanFirstPos(restrictions, resultGrid);
    }

    public final int getFirstCol(IContext context) throws SyntaxException {
        if (this.isFixedCell() || !this.isSameArea((ReportContext)context)) {
            return this.locateFirstCol(context);
        }
        return this.locateRestrictedFirstPos((ReportContext)context, new ColLocator());
    }

    private int locateFirstCol(IContext context) throws SyntaxException {
        ResultGridData resultGrid = ((EngineWorksheet)this.workSheet).getResultGrid();
        try {
            return resultGrid.locateNewCol(this.position.col());
        }
        catch (ReportBuildException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (info instanceof DSFormulaInfo) {
            QParserHelper.evalInterpret(context, (IASTNode)this, buffer);
        } else {
            super.toFormula(context, buffer, info);
        }
    }

    protected static enum ReadingMode {
        FIXED,
        OFFSET,
        RESTRICTED,
        ALL;

    }

    private final class ColLocator
    implements IPositionLocator {
        private ColLocator() {
        }

        @Override
        public int offsetFirstPos(ReportContext context, ResultGridData resultGrid) throws SyntaxException {
            Position rawPos = context.getCurrentCell().getBindingInfo().getPosition().getPosition();
            Position newPos = context.getCurrentCell().getPosition();
            return ReportCellNode.this.position.col() + newPos.col() - rawPos.col();
        }

        @Override
        public int scanFirstPos(List<AxisDataNode> restrictions, ResultGridData resultGrid) throws SyntaxException {
            List<Integer> cols = resultGrid.getExpandedCols(ReportCellNode.this.position.col());
            List<Integer> rows = resultGrid.getExpandedRows(ReportCellNode.this.position.row());
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
        public int offsetFirstPos(ReportContext context, ResultGridData resultGrid) throws SyntaxException {
            Position rawPos = context.getCurrentCell().getBindingInfo().getPosition().getPosition();
            Position newPos = context.getCurrentCell().getPosition();
            return ReportCellNode.this.position.row() + newPos.row() - rawPos.row();
        }

        @Override
        public int scanFirstPos(List<AxisDataNode> restrictions, ResultGridData resultGrid) throws SyntaxException {
            List<Integer> cols = resultGrid.getExpandedCols(ReportCellNode.this.position.col());
            List<Integer> rows = resultGrid.getExpandedRows(ReportCellNode.this.position.row());
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
        public int offsetFirstPos(ReportContext var1, ResultGridData var2) throws SyntaxException;

        public int scanFirstPos(List<AxisDataNode> var1, ResultGridData var2) throws SyntaxException;
    }
}

