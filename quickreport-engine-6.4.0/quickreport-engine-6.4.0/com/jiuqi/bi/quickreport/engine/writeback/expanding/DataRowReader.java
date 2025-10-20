/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.data.ArrayData
 */
package com.jiuqi.bi.quickreport.engine.writeback.expanding;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.ExpandingWritebackContext;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.ExpandingWritebackInfo;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackCellNode;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackRegionNode;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackRestrictions;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.data.ArrayData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class DataRowReader {
    private final String sheetName;
    private final int row;
    private ReportContext context;
    private List<IReportExpression> expressions;
    private ExpandingWritebackContext writeContext;
    private MemoryDataSet<ExpandingWritebackInfo> dataSet;
    private MemoryDataSet<ExpandingRegion> colValues;
    private MemoryDataSet<ExpandingRegion> rowValues;
    private ExpandingArea area;

    public DataRowReader(String sheetName, int row, MemoryDataSet<ExpandingWritebackInfo> dataSet) {
        this.sheetName = sheetName;
        this.row = row;
        this.dataSet = dataSet;
        this.colValues = new MemoryDataSet();
        this.rowValues = new MemoryDataSet();
        this.expressions = new ArrayList<IReportExpression>();
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public List<IReportExpression> getExpressions() {
        return this.expressions;
    }

    public ExpandingWritebackContext getWriteContext() {
        return this.writeContext;
    }

    public void setWriteContext(ExpandingWritebackContext context) {
        this.writeContext = context;
    }

    public void read() throws ReportWritebackException {
        List<WritebackCellNode> masterCells = this.analyse();
        this.buildFrames(masterCells);
        this.expandData();
    }

    private List<WritebackCellNode> analyse() throws ReportWritebackException {
        ArrayList<WritebackCellNode> masterCells = new ArrayList<WritebackCellNode>();
        this.area = null;
        for (int i = 0; i < this.expressions.size(); ++i) {
            IReportExpression expression = this.expressions.get(i);
            if (expression == null) continue;
            this.analyse(masterCells, expression, i);
        }
        return masterCells;
    }

    private void analyse(List<WritebackCellNode> masterCells, IReportExpression expression, int index) throws ReportWritebackException {
        for (IASTNode node : expression) {
            GridArea curArea;
            if (node instanceof WritebackCellNode) {
                WritebackCellNode cellNode = (WritebackCellNode)node;
                if (cellNode.isMasterCell()) {
                    masterCells.add(cellNode);
                }
                curArea = cellNode.getCellArea();
            } else {
                if (!(node instanceof WritebackRegionNode)) continue;
                WritebackRegionNode regionNode = (WritebackRegionNode)node;
                try {
                    curArea = regionNode.getRefExpandingArea();
                }
                catch (SyntaxException e) {
                    throw new ReportWritebackException("\u56de\u5199\u8868\u53d6\u6570\u9519\u8bef\uff0c" + this.sheetName + "!" + Position.toString((int)(index + 1), (int)this.row) + "\u5355\u5143\u683c\u516c\u5f0f\u9519\u8bef\uff0c" + e.getMessage(), e);
                }
            }
            if (!(curArea instanceof ExpandingArea)) continue;
            if (this.area != null && curArea != this.area) {
                throw new ReportWritebackException("\u56de\u5199\u8868\u53d6\u6570\u9519\u8bef\uff0c" + this.sheetName + "!" + Position.toString((int)(index + 1), (int)this.row) + "\u5355\u5143\u683c\u5f15\u7528\u4e86\u4e0d\u540c\u6d6e\u52a8\u533a\u57df\u7684\u5355\u5143\u683c\uff1a" + node);
            }
            this.area = (ExpandingArea)curArea;
        }
    }

    private void buildFrames(List<WritebackCellNode> masterCells) throws ReportWritebackException {
        if (masterCells.isEmpty()) {
            return;
        }
        Map<CellBindingInfo, ExpandingRegion> regionMaps = this.area.buildRegionMaps();
        Map<ExpandingRegion, Integer> regionPositions = this.locateCells(masterCells, regionMaps);
        this.expandAxis(this.rowValues, this.area.getRowAxis().getData(), regionPositions);
        this.expandAxis(this.colValues, this.area.getColAxis().getData(), regionPositions);
    }

    private Map<ExpandingRegion, Integer> locateCells(List<WritebackCellNode> masterCells, Map<CellBindingInfo, ExpandingRegion> regionMaps) throws ReportWritebackException {
        HashMap<ExpandingRegion, Integer> regionPositions = new HashMap<ExpandingRegion, Integer>(masterCells.size());
        for (WritebackCellNode cell : masterCells) {
            CellBindingInfo bindingInfo = cell.getCellInfo();
            ExpandingRegion region = regionMaps.get(bindingInfo);
            Integer pos = (Integer)regionPositions.get(region);
            if (pos == null) {
                pos = region.getExpandMode() == ExpandMode.ROWEXPANDING ? Integer.valueOf(this.addToAxis(this.rowValues, region)) : Integer.valueOf(this.addToAxis(this.colValues, region));
                regionPositions.put(region, pos);
            }
            cell.setAxisIndex(pos);
        }
        return regionPositions;
    }

    private int addToAxis(MemoryDataSet<ExpandingRegion> axisValues, ExpandingRegion region) throws ReportWritebackException {
        if (region.isStatic()) {
            throw new ReportWritebackException("\u9650\u5b9a\u5206\u6790\u9519\u8bef\uff0c\u5e38\u91cf\u5355\u5143\u683c\u4e0d\u80fd\u53c2\u4e0e\u9650\u5b9a\u3002");
        }
        DSField field = region.getKeyField().getField();
        Column column = new Column(field.getName(), 0, field.getTitle(), (Object)region);
        axisValues.getMetadata().addColumn(column);
        return column.getIndex();
    }

    private void expandAxis(MemoryDataSet<ExpandingRegion> axisValues, AxisDataNode data, Map<ExpandingRegion, Integer> regionPositions) throws ReportWritebackException {
        if (axisValues.getMetadata().size() == 0 || data.getChildren().isEmpty()) {
            return;
        }
        BuildingRow buildingRow = new BuildingRow(axisValues);
        this.expandNode(buildingRow, data, regionPositions);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void expandNode(BuildingRow buildingRow, AxisDataNode data, Map<ExpandingRegion, Integer> regionPositions) throws ReportWritebackException {
        Integer pos = regionPositions.get(data.getRegion());
        if (pos == null) {
            for (AxisDataNode sub : data.getChildren()) {
                this.expandNode(buildingRow, sub, regionPositions);
            }
        } else {
            buildingRow.setValue(pos, data);
            try {
                if (buildingRow.isReady()) {
                    buildingRow.complete();
                    return;
                }
                for (AxisDataNode sub : data.getChildren()) {
                    this.expandNode(buildingRow, sub, regionPositions);
                }
            }
            finally {
                buildingRow.clearValue(pos);
            }
        }
    }

    private void expandData() throws ReportWritebackException {
        if (this.colValues.getMetadata().size() == 0 && this.rowValues.getMetadata().size() == 0) {
            this.readDataRow();
        } else if (this.colValues.getMetadata().size() == 0) {
            this.readRowExpanding();
        } else if (this.rowValues.getMetadata().size() == 0) {
            this.readColExpanding();
        } else {
            this.readDualExpanding();
        }
    }

    private void readDataRow() throws ReportWritebackException {
        Object[] buffer = this.evalRow();
        if (buffer != null) {
            this.appendRow(buffer);
        }
    }

    private Object[] evalRow() throws ReportWritebackException {
        Object[] buffer = new Object[this.expressions.size()];
        boolean hasData = false;
        for (int i = 0; i < this.expressions.size(); ++i) {
            Object value;
            IReportExpression expr = this.expressions.get(i);
            Column column = this.dataSet.getMetadata().getColumn(i);
            try {
                value = expr == null ? null : expr.evaluate(this.context);
            }
            catch (ReportExpressionException e) {
                throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868\u51fa\u9519\uff0c\u5355\u5143\u683c[" + this.sheetName + "!" + Position.toString((int)(i + 1), (int)this.row) + "]\u8ba1\u7b97\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
            if (value == null && ((ExpandingWritebackInfo)column.getInfo()).isKey()) {
                throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868\u51fa\u9519\uff0c\u4e3b\u952e\u5355\u5143\u683c[" + this.sheetName + "!" + Position.toString((int)(i + 1), (int)this.row) + "]\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
            }
            if (value instanceof ArrayData) {
                throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868\u51fa\u9519\uff0c\u5355\u5143\u683c[" + this.sheetName + "!" + Position.toString((int)(i + 1), (int)this.row) + "]\u8ba1\u7b97\u8fd4\u56de\u4e86\u591a\u503c\u6570\u636e\uff0c\u65e0\u6cd5\u5904\u7406\u3002");
            }
            try {
                buffer[i] = ((ExpandingWritebackInfo)column.getInfo()).getValidator().validate(value);
            }
            catch (NumberFormatException e) {
                throw new ReportWritebackException("\u6267\u884c\u5355\u5143\u683c[" + this.sheetName + "!" + Position.toString((int)(i + 1), (int)this.row) + "]\u56de\u5199\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
            if (buffer[i] == null) continue;
            hasData = true;
        }
        return hasData ? buffer : null;
    }

    private void appendRow(Object[] buffer) throws ReportWritebackException {
        try {
            this.dataSet.add(buffer);
        }
        catch (DataSetException e) {
            throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868\u51fa\u9519[" + this.sheetName + ", \u884c " + this.row + "]" + e.getMessage(), e);
        }
        this.writeContext.getRowMaps().add(this.row);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readRowExpanding() throws ReportWritebackException {
        WritebackRestrictions writeRestrictions = new WritebackRestrictions(this.area);
        this.context.setTag(writeRestrictions);
        try {
            for (DataRow row : this.rowValues) {
                writeRestrictions.setRowRestrictions((AxisDataNode[])row.getBuffer());
                this.readDataRow();
            }
        }
        finally {
            this.context.setTag(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readColExpanding() throws ReportWritebackException {
        WritebackRestrictions writeRestrictions = new WritebackRestrictions(this.area);
        this.context.setTag(writeRestrictions);
        try {
            for (DataRow row : this.colValues) {
                writeRestrictions.setColRestrictions((AxisDataNode[])row.getBuffer());
                this.readDataRow();
            }
        }
        finally {
            this.context.setTag(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readDualExpanding() throws ReportWritebackException {
        WritebackRestrictions writeRestrictions = new WritebackRestrictions(this.area);
        this.context.setTag(writeRestrictions);
        try {
            for (DataRow row : this.rowValues) {
                writeRestrictions.setRowRestrictions((AxisDataNode[])row.getBuffer());
                for (DataRow col : this.colValues) {
                    writeRestrictions.setColRestrictions((AxisDataNode[])col.getBuffer());
                    this.readDataRow();
                }
            }
        }
        finally {
            this.context.setTag(null);
        }
    }

    private static final class BuildingRow {
        private MemoryDataSet<?> dataSet;
        private AxisDataNode[] buffer;
        private int count;

        public BuildingRow(MemoryDataSet<?> dataSet) {
            this.dataSet = dataSet;
            this.buffer = new AxisDataNode[dataSet.getMetadata().size()];
            this.count = 0;
        }

        public void setValue(int index, AxisDataNode value) {
            this.buffer[index] = value;
            ++this.count;
        }

        public void clearValue(int index) {
            this.buffer[index] = null;
            --this.count;
        }

        public boolean isReady() {
            return this.count == this.buffer.length;
        }

        public void complete() throws ReportWritebackException {
            if (this.isReady()) {
                try {
                    this.dataSet.add((Object[])this.buffer.clone());
                }
                catch (DataSetException e) {
                    throw new ReportWritebackException(e);
                }
            }
        }
    }
}

