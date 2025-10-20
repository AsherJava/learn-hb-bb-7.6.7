/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.data.ArrayData
 */
package com.jiuqi.bi.quickreport.engine.writeback.fixed;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.result.WritebackMapInfo;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.SheetWriter;
import com.jiuqi.bi.quickreport.engine.writeback.fixed.FixedWritebackContext;
import com.jiuqi.bi.quickreport.engine.writeback.fixed.FixedWritebackInfo;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.data.ArrayData;
import java.util.List;

public final class FixedSheetWriter
extends SheetWriter {
    private static final int IDX_FIELDNAME = 2;
    private static final int IDX_EXPRESSION = 3;

    public FixedSheetWriter(WritebackModel model) {
        this.writeContext = new FixedWritebackContext(model, this.userID);
    }

    @Override
    public int execute(boolean ignoreWrite) throws ReportWritebackException {
        MemoryDataSet<FixedWritebackInfo> dataSet = this.createDataSet();
        this.fillDataSet(dataSet);
        if (!ignoreWrite) {
            this.writeback(dataSet);
        }
        this.dataSet = dataSet;
        return dataSet.size();
    }

    private MemoryDataSet<FixedWritebackInfo> createDataSet() throws ReportWritebackException {
        MemoryDataSet dataSet = new MemoryDataSet();
        GridData grid = this.model.getGridData();
        for (int row = 2; row < grid.getRowCount(); ++row) {
            Column<FixedWritebackInfo> column = this.createColumn(row);
            dataSet.getMetadata().addColumn(column);
        }
        return dataSet;
    }

    private Column<FixedWritebackInfo> createColumn(int row) throws ReportWritebackException {
        TableField field = this.openTableField(row);
        IReportExpression expression = this.parseCell(3, row, field);
        boolean isKey = this.model.isKey(field.getName());
        if (isKey && expression == null) {
            throw new ReportWritebackException("\u56de\u5199\u5b57\u6bb5[" + field.getName() + "]\u4e3a\u4e3b\u952e\u5b57\u6bb5\uff0c\u5fc5\u987b\u6307\u5b9a\u56de\u5199\u516c\u5f0f\uff1a" + this.model.getSheetName() + "!" + Position.toString((int)3, (int)row));
        }
        FixedWritebackInfo info = new FixedWritebackInfo(Position.valueOf((int)3, (int)row), expression, isKey, field);
        return new Column(field.getName(), field.getDataType().value(), field.getTitle(), (Object)info);
    }

    private TableField openTableField(int row) throws ReportWritebackException {
        String fieldName = this.model.getGridData().getCellData(2, row);
        TableField field = this.model.findField(fieldName);
        if (field == null) {
            throw new ReportWritebackException("\u5206\u6790\u56de\u5199\u8868\u51fa\u9519\uff0c" + this.model.getSheetName() + "!" + Position.toString((int)2, (int)row) + "\u5bf9\u5e94\u7684\u5b57\u6bb5\u4e0d\u5b58\u5728\u3002");
        }
        return field;
    }

    private void fillDataSet(MemoryDataSet<FixedWritebackInfo> dataSet) throws ReportWritebackException {
        boolean hasData = false;
        Object[] buffer = new Object[dataSet.getMetadata().size()];
        for (int col = 0; col < dataSet.getMetadata().size(); ++col) {
            Object value;
            Column column = dataSet.getMetadata().getColumn(col);
            buffer[col] = value = this.evalValue((Column<FixedWritebackInfo>)column);
            if (value == null) continue;
            hasData = true;
        }
        if (hasData) {
            try {
                dataSet.add(buffer);
            }
            catch (DataSetException e) {
                throw new ReportWritebackException("\u5904\u7406\u56de\u5199\u8868[" + this.model.getSheetName() + "]\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
        }
    }

    private Object evalValue(Column<FixedWritebackInfo> column) throws ReportWritebackException {
        Object value = null;
        if (((FixedWritebackInfo)column.getInfo()).getExpression() != null) {
            try {
                value = ((FixedWritebackInfo)column.getInfo()).getExpression().evaluate(this.parser.getContext());
            }
            catch (ReportExpressionException e) {
                throw new ReportWritebackException("\u8ba1\u7b97\u56de\u5199\u5355\u5143\u683c[" + this.model.getSheetName() + "!" + ((FixedWritebackInfo)column.getInfo()).getPosition() + "]\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
        }
        if (value == null && ((FixedWritebackInfo)column.getInfo()).isKey()) {
            throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868\u51fa\u9519\uff0c\u4e3b\u952e\u5b57\u6bb5[" + this.model.getSheetName() + "!" + ((FixedWritebackInfo)column.getInfo()).getPosition() + "]\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (value instanceof ArrayData) {
            throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868\u51fa\u9519\uff0c\u5b57\u6bb5[" + this.model.getSheetName() + "!" + ((FixedWritebackInfo)column.getInfo()).getPosition() + "]\u8ba1\u7b97\u8fd4\u56de\u591a\u503c\u7ed3\u679c\u3002");
        }
        try {
            return ((FixedWritebackInfo)column.getInfo()).getValidator().validate(value);
        }
        catch (NumberFormatException e) {
            throw new ReportWritebackException("\u6267\u884c\u5b57\u6bb5[" + this.model.getSheetName() + "!" + ((FixedWritebackInfo)column.getInfo()).getPosition() + "]\u56de\u5199\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    @Override
    protected void getMappings(List<WritebackMapInfo> mappings) throws ReportWritebackException {
        GridData grid = this.model.getGridData();
        for (int row = 2; row < grid.getRowCount(); ++row) {
            TableField targetField = this.openTableField(row);
            IReportExpression expression = this.parseCell(3, row, targetField);
            if (expression == null) continue;
            WritebackMapInfo mapping = new WritebackMapInfo();
            mapping.setTargetFieldName(this.model.getTableInfo().getName() + "." + targetField.getName());
            mapping.getSrcFieldNames().addAll(this.getRefFields(expression));
            this.getWritebackFormulas(targetField.getName(), expression, mapping.getFormulas());
            mappings.add(mapping);
        }
    }
}

