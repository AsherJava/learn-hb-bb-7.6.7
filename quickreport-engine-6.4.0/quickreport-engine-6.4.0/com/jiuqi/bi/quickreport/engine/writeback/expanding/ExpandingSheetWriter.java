/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.writeback.expanding;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.result.WritebackMapInfo;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.SheetWriter;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.DataRowReader;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.ExpandingWritebackContext;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.ExpandingWritebackInfo;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.syntax.cell.Position;
import java.util.ArrayList;
import java.util.List;

public final class ExpandingSheetWriter
extends SheetWriter {
    private static final int ROW_FIELDNAME = 2;
    private static final int ROW_DATASTART = 3;

    public ExpandingSheetWriter(WritebackModel model) {
        this.writeContext = new ExpandingWritebackContext(model, this.userID);
    }

    @Override
    public int execute(boolean ignoreWrite) throws ReportWritebackException {
        MemoryDataSet<ExpandingWritebackInfo> dataSet = this.createDataSet();
        this.expandDataSet(dataSet);
        if (!ignoreWrite) {
            this.writeback(dataSet);
        }
        this.dataSet = dataSet;
        return dataSet.size();
    }

    private MemoryDataSet<ExpandingWritebackInfo> createDataSet() throws ReportWritebackException {
        MemoryDataSet dataSet = new MemoryDataSet();
        for (int col = 1; col < this.model.getGridData().getColCount(); ++col) {
            Column<ExpandingWritebackInfo> column = this.createColumn(col);
            dataSet.getMetadata().addColumn(column);
        }
        return dataSet;
    }

    private Column<ExpandingWritebackInfo> createColumn(int col) throws ReportWritebackException {
        TableField field = this.openTableField(col);
        boolean isKey = this.model.isKey(field.getName());
        ExpandingWritebackInfo info = new ExpandingWritebackInfo(col, field, isKey);
        return new Column(field.getName(), field.getDataType().value(), field.getTitle(), (Object)info);
    }

    private TableField openTableField(int col) throws ReportWritebackException {
        String fieldName = this.model.getGridData().getCellData(col, 2);
        TableField field = this.model.findField(fieldName);
        if (field == null) {
            throw new ReportWritebackException("\u5206\u6790\u56de\u5199\u8868\u51fa\u9519\uff0c'" + this.model.getSheetName() + "!" + Position.toString((int)col, (int)2) + "'\u5bf9\u5e94\u7684\u5b57\u6bb5[" + fieldName + "]\u4e0d\u5b58\u5728\u3002");
        }
        return field;
    }

    private void expandDataSet(MemoryDataSet<ExpandingWritebackInfo> dataSet) throws ReportWritebackException {
        for (int row = 3; row < this.model.getGridData().getRowCount(); ++row) {
            if (this.isEmptyRow(row, dataSet)) continue;
            List<IReportExpression> expressions = this.parseRow(row, dataSet);
            this.expandDataRow(expressions, row, dataSet);
        }
    }

    private boolean isEmptyRow(int row, MemoryDataSet<ExpandingWritebackInfo> dataSet) {
        for (int col = 1; col <= dataSet.getMetadata().getColumnCount(); ++col) {
            String data = this.model.getGridData().getCellData(col, row);
            if (data == null || data.trim().length() == 0) continue;
            return false;
        }
        return true;
    }

    private List<IReportExpression> parseRow(int row, MemoryDataSet<ExpandingWritebackInfo> dataSet) throws ReportWritebackException {
        ArrayList<IReportExpression> expressions = new ArrayList<IReportExpression>();
        for (int col = 1; col < this.model.getGridData().getColCount(); ++col) {
            Column column = dataSet.getMetadata().getColumn(col - 1);
            IReportExpression expression = this.parseCell(col, row, ((ExpandingWritebackInfo)column.getInfo()).getField());
            if (expression == null && ((ExpandingWritebackInfo)column.getInfo()).isKey()) {
                throw new ReportWritebackException("\u4e3b\u952e\u5b57\u6bb5[" + column.getName() + "]\u7684\u53d6\u503c\u516c\u5f0f[" + this.model.getSheetName() + "!" + Position.toString((int)col, (int)row) + "]\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
            }
            expressions.add(expression);
        }
        return expressions;
    }

    private void expandDataRow(List<IReportExpression> expressions, int row, MemoryDataSet<ExpandingWritebackInfo> dataSet) throws ReportWritebackException {
        DataRowReader reader = new DataRowReader(this.model.getSheetName(), row, dataSet);
        reader.setContext(this.reportContext);
        reader.setWriteContext((ExpandingWritebackContext)this.writeContext);
        reader.getExpressions().addAll(expressions);
        reader.read();
    }

    @Override
    protected void getMappings(List<WritebackMapInfo> mappings) throws ReportWritebackException {
        GridData grid = this.model.getGridData();
        for (int col = 1; col < grid.getColCount(); ++col) {
            TableField targetField = this.openTableField(col);
            WritebackMapInfo mapping = new WritebackMapInfo();
            mapping.setTargetFieldName(this.model.getTableInfo().getName() + "." + targetField.getName());
            for (int row = 3; row < grid.getRowCount(); ++row) {
                IReportExpression expression = this.parseCell(col, row, targetField);
                if (expression == null) continue;
                mapping.getSrcFieldNames().addAll(this.getRefFields(expression));
                this.getWritebackFormulas(targetField.getName(), expression, mapping.getFormulas());
            }
            mappings.add(mapping);
        }
    }
}

