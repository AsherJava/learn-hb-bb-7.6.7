/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.writeback;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.ReportLog;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.IReportParser;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportCellNode;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportRegionNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.RestrictedFieldNode;
import com.jiuqi.bi.quickreport.engine.result.WritebackMapInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.writeback.IWritebackContext;
import com.jiuqi.bi.quickreport.writeback.IWritebackExecutor;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFactoryManager;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SheetWriter {
    protected String userID;
    protected ReportContext reportContext;
    protected IReportParser parser;
    protected WritebackModel model;
    protected EngineWorkbook workbook;
    protected IWritebackContext writeContext;
    protected MemoryDataSet<?> dataSet;

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ReportContext getReportContext() {
        return this.reportContext;
    }

    public void setReportContext(ReportContext reportContext) {
        this.reportContext = reportContext;
    }

    public IReportParser getParser() {
        return this.parser;
    }

    public void setParser(IReportParser parser) {
        this.parser = parser;
    }

    public WritebackModel getModel() {
        return this.model;
    }

    public void setModel(WritebackModel model) {
        this.model = model;
    }

    public EngineWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(EngineWorkbook workbook) {
        this.workbook = workbook;
    }

    public abstract int execute(boolean var1) throws ReportWritebackException;

    public void writeback(MemoryDataSet<?> dataSet) throws ReportWritebackException {
        this.printDataSet(dataSet);
        try {
            IWritebackExecutor executor = WritebackFactoryManager.createExecutror(this.model.getInterfaceType(), this.model.getTableInfo().getName());
            executor.write(this.writeContext, dataSet);
        }
        catch (WritebackException e) {
            throw new ReportWritebackException("\u6267\u884c\u56de\u5199\u8868[" + this.model.getSheetName() + "]\u51fa\u9519", e);
        }
    }

    public MemoryDataSet<?> getDataSet() {
        return this.dataSet;
    }

    protected IReportExpression parseCell(int col, int row, TableField field) throws ReportWritebackException {
        String cellData = this.model.getGridData().getCellData(col, row);
        if (StringUtils.isEmpty((String)cellData)) {
            return null;
        }
        if (cellData.startsWith("=")) {
            return this.parseCellExpression(col, row, field);
        }
        return this.parseCellValue(col, row, field);
    }

    private IReportExpression parseCellExpression(int col, int row, TableField field) throws ReportWritebackException {
        String formula = this.model.getGridData().getCellData(col, row).substring(1);
        try {
            return this.parser.parseWriteback(formula);
        }
        catch (ReportExpressionException e) {
            throw new ReportWritebackException("\u89e3\u6790[" + this.model.getSheetName() + "!" + Position.toString((int)col, (int)row) + "]\u56de\u5199\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private IReportExpression parseCellValue(int col, int row, TableField field) throws ReportWritebackException {
        Object value;
        GridCell cell = this.model.getGridData().getCell(col, row);
        switch (field.getDataType()) {
            case STRING: {
                value = cell.getString();
                break;
            }
            case DOUBLE: 
            case INTEGER: {
                value = cell.getFloat();
                break;
            }
            case BOOLEAN: {
                value = cell.getBoolean();
                break;
            }
            case DATETIME: {
                Date date = cell.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                value = cal;
                break;
            }
            default: {
                throw new ReportWritebackException("\u672a\u652f\u6301\u7684\u56de\u5199\u5b57\u6bb5\u7c7b\u578b\uff1a" + field.getDataType());
            }
        }
        DataNode data = new DataNode(null, DataType.translateToSyntaxType((DataType)field.getDataType()), value);
        return new ReportExpression((IASTNode)data);
    }

    private void printDataSet(MemoryDataSet<?> dataSet) {
        if (this.model.isPrintToConsole()) {
            try {
                System.out.println("\u6b63\u5728\u6267\u884c" + this.model.getSheetName() + "\u7684\u6570\u636e\u56de\u5199\uff0c\u5199\u5165\u76ee\u6807\u8868\uff1a" + this.model.getTableInfo().getName());
                OutputStreamWriter writer = new OutputStreamWriter(System.out);
                dataSet.saveToCSV((Writer)writer);
                ((Writer)writer).flush();
            }
            catch (IOException e) {
                ReportLog.openLogger().error("\u8f93\u51fa\u8c03\u8bd5\u4fe1\u606f\u51fa\u9519\uff01", (Throwable)e);
            }
        }
    }

    protected abstract void getMappings(List<WritebackMapInfo> var1) throws ReportWritebackException;

    protected Set<String> getRefFields(IReportExpression expression) throws ReportWritebackException {
        HashSet<String> refFields = new HashSet<String>();
        for (IASTNode node : expression) {
            this.getRefFields(node, refFields);
        }
        return refFields;
    }

    private void getRefFields(IASTNode node, Set<String> refFields) throws ReportWritebackException {
        if (node instanceof DSFieldNode) {
            DSFieldNode fieldNode = (DSFieldNode)node;
            refFields.add(fieldNode.getDataSet().getName() + "." + fieldNode.getField().getName());
        } else if (node instanceof RestrictedFieldNode) {
            RestrictedFieldNode fieldNode = (RestrictedFieldNode)node;
            refFields.add(fieldNode.getDataSet().getName() + "." + fieldNode.getField().getName());
        } else if (node instanceof ReportCellNode) {
            ReportCellNode cellNode = (ReportCellNode)node;
            CellBindingInfo cellInfo = cellNode.getCellInfo();
            if (cellInfo != null && cellInfo.getValue() != null) {
                Set<String> nextFields = this.getRefFields(cellInfo.getValue());
                refFields.addAll(nextFields);
            }
        } else if (node instanceof ReportRegionNode) {
            ReportWorksheet workSheet;
            ReportRegionNode regionNode = (ReportRegionNode)node;
            try {
                workSheet = (ReportWorksheet)this.workbook.find(this.reportContext, regionNode.sheetName());
            }
            catch (CellExcpetion e) {
                throw new ReportWritebackException(e);
            }
            GridData grid = workSheet.getGridData();
            Region region = regionNode.calcRegion();
            for (int col = region.left(); col <= region.right(); ++col) {
                for (int row = region.top(); row <= region.bottom(); ++row) {
                    CellBindingInfo cellInfo = (CellBindingInfo)grid.getObj(col, row);
                    if (cellInfo == null || cellInfo.getValue() == null) continue;
                    Set<String> nextFields = this.getRefFields(cellInfo.getValue());
                    refFields.addAll(nextFields);
                }
            }
        }
    }

    protected void getWritebackFormulas(String targetName, IReportExpression expression, List<WritebackMapInfo.Formula> formulas) throws ReportWritebackException {
        IReportExpression rawExpr = this.getRawExpression(expression);
        if (rawExpr == null) {
            return;
        }
        formulas.add(this.createFormula(targetName, expression));
        for (IASTNode node : expression) {
            this.getWritebackFormulas(node, formulas);
        }
    }

    private void getWritebackFormulas(IASTNode node, List<WritebackMapInfo.Formula> formulas) throws ReportWritebackException {
        if (node instanceof ReportCellNode) {
            ReportCellNode cellNode = (ReportCellNode)node;
            CellBindingInfo cellInfo = cellNode.getCellInfo();
            if (cellInfo != null && cellInfo.getValue() != null) {
                this.getWritebackFormulas(cellInfo.getPosition().toString(), cellInfo.getValue(), formulas);
            }
        } else if (node instanceof ReportRegionNode) {
            ReportWorksheet workSheet;
            ReportRegionNode regionNode = (ReportRegionNode)node;
            try {
                workSheet = (ReportWorksheet)this.workbook.find(this.reportContext, regionNode.sheetName());
            }
            catch (CellExcpetion e) {
                throw new ReportWritebackException(e);
            }
            GridData grid = workSheet.getGridData();
            Region region = regionNode.calcRegion();
            for (int col = region.left(); col <= region.right(); ++col) {
                for (int row = region.top(); row <= region.bottom(); ++row) {
                    CellBindingInfo cellInfo = (CellBindingInfo)grid.getObj(col, row);
                    if (cellInfo == null || cellInfo.getValue() == null) continue;
                    this.getWritebackFormulas(cellInfo.getPosition().toString(), cellInfo.getValue(), formulas);
                }
            }
        }
    }

    private IReportExpression getRawExpression(IReportExpression expression) {
        while (expression != null && expression.getRootNode() instanceof ReportCellNode) {
            ReportCellNode cellNode = (ReportCellNode)expression.getRootNode();
            CellBindingInfo cellInfo = cellNode.getCellInfo();
            if (cellInfo == null) {
                return null;
            }
            expression = cellInfo.getValue();
        }
        return expression;
    }

    private WritebackMapInfo.Formula createFormula(String targetName, IReportExpression expression) throws ReportWritebackException {
        String explan;
        String expr;
        try {
            expr = targetName + " = " + expression.toFormula(this.reportContext);
            explan = targetName + " = " + expression.toExplain(this.reportContext);
        }
        catch (ReportExpressionException e) {
            throw new ReportWritebackException(e);
        }
        return new WritebackMapInfo.Formula(expr, explan);
    }
}

