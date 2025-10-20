/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.DateCellProperty
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.logging.DummyLogger
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.Cells
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.cell.RegionNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.logging.DummyLogger;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.quickreport.engine.context.ParseContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.selection.CellSelection;
import com.jiuqi.bi.quickreport.engine.interaction.SortingCell;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.DependenceAnalyzer;
import com.jiuqi.bi.quickreport.engine.parser.ErrorExpression;
import com.jiuqi.bi.quickreport.engine.parser.FilterBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.IReportParser;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.ReportParser;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_ExpandingFunction;
import com.jiuqi.bi.quickreport.engine.style.CellStyleProcessor;
import com.jiuqi.bi.quickreport.engine.style.DataBarProcessor;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.engine.style.IconProcessor;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.quickreport.engine.workbook.WorkbookException;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.CellStyle;
import com.jiuqi.bi.quickreport.model.DataBarStyle;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.FilterInfo;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.quickreport.model.HyperlinkType;
import com.jiuqi.bi.quickreport.model.IconStyle;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.StyleRegion;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.cell.RegionNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WorkbookParser {
    private IContext context;
    private IReportParser parser;
    private int options;
    private CellSelection traceCells;
    private ILogger log;
    private Map<Position, SortingCell> sortInfos;
    private ReportWorkbook workbook;
    private PageMode pageMode;
    private Map<SheetPosition, String> errors;

    public WorkbookParser(int options, IContext context) {
        this.options = options;
        this.context = context;
        this.sortInfos = new HashMap<Position, SortingCell>();
        this.parser = new ReportParser(context);
        this.traceCells = new CellSelection();
        this.errors = new TreeMap<SheetPosition, String>();
        this.pageMode = PageMode.NONE;
    }

    @Deprecated
    public WorkbookParser(int options) {
        this(options, new ParseContext());
    }

    private boolean isCheckMode() {
        return (this.options & 8) != 0;
    }

    public IReportParser getParser() {
        return this.parser;
    }

    public ReportWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(ReportWorkbook workbook) {
        this.workbook = workbook;
        this.parser.setWorkbook(workbook);
    }

    public PageMode getPageMode() {
        return this.pageMode;
    }

    public void setPageMode(PageMode pageMode) {
        this.pageMode = pageMode;
    }

    public Map<Position, SortingCell> getSortInfos() {
        return this.sortInfos;
    }

    public void setLogger(ILogger log) {
        this.log = log;
    }

    public ILogger getLogger() {
        if (this.log == null) {
            this.log = new DummyLogger();
        }
        return this.log;
    }

    public void parse(QuickReport report) throws ReportExpressionException {
        this.resetBingdings(report);
        this.parseProperties(report);
        this.parseSheets(report);
        this.analyseDepends();
    }

    private void resetBingdings(QuickReport report) {
        for (WorksheetModel worksheet : report.getWorksheets()) {
            for (CellMap cellMap : worksheet.getCellMaps()) {
                worksheet.getGriddata().setObj(cellMap.getPosition().col(), cellMap.getPosition().row(), null);
            }
        }
    }

    private void parseProperties(QuickReport report) throws ReportExpressionException {
        if (this.context instanceof ReportContext) {
            String traceCellFilters;
            ReportContext rptContext = (ReportContext)this.context;
            String pinyinFilters = report.getProperties().getProperty("SYS_PINYIN_ORDERING");
            rptContext.getPinYinOrdering().parse(rptContext, pinyinFilters);
            if (!rptContext.getPinYinOrdering().isEmpty()) {
                this.log.debug("\u542f\u7528\u62fc\u97f3\u6392\u5e8f\uff1a" + rptContext.getPinYinOrdering());
            }
            if ((traceCellFilters = rptContext.getConfig().get("tracing.cells")) == null) {
                traceCellFilters = rptContext.getReport().getProperties().getProperty("tracing.cells");
            }
            this.traceCells.parse(rptContext, traceCellFilters);
            if (!this.traceCells.isEmpty()) {
                this.log.debug("\u542f\u7528\u5355\u5143\u683c\u6570\u636e\u8ddf\u8e2a\uff1a" + this.traceCells);
            }
        }
    }

    private void parseSheets(QuickReport report) throws ReportExpressionException {
        for (WorksheetModel sheet : report.getWorksheets()) {
            ReportWorksheet worksheet;
            try {
                worksheet = (ReportWorksheet)this.workbook.find(this.context, sheet.getName());
            }
            catch (CellExcpetion e) {
                throw new ReportExpressionException(e);
            }
            if (worksheet == null) {
                throw new ReportExpressionException("\u5b9a\u4f4d\u9875\u7b7e\u4e0d\u5b58\u5728\uff1a" + sheet.getName());
            }
            this.parseSheet(report, sheet, worksheet);
        }
    }

    private void parseSheet(QuickReport report, WorksheetModel sheet, ReportWorksheet worksheet) throws ReportExpressionException {
        boolean isPrimary = sheet.getName().equalsIgnoreCase(report.getPrimarySheetName());
        this.workbook.setActiveWorksheet(sheet.getName());
        this.parseSheetCells(sheet, worksheet, isPrimary);
        this.parseSheetSytles(sheet, worksheet);
        this.parseSheetFilters(sheet, worksheet);
    }

    private void parseSheetCells(WorksheetModel sheet, ReportWorksheet worksheet, boolean isPrimarySheet) throws ReportExpressionException {
        for (CellMap cellMap : sheet.getCellMaps()) {
            CellBindingInfo bindingInfo = this.parseCell(sheet, cellMap, worksheet, isPrimarySheet);
            this.buildCellLinks(bindingInfo);
        }
    }

    private CellBindingInfo parseCell(WorksheetModel sheet, CellMap cellMap, ReportWorksheet worksheet, boolean isPrimarySheet) throws ReportExpressionException {
        CellBindingInfo bindingInfo;
        try {
            bindingInfo = worksheet.openCellBinding(cellMap);
        }
        catch (WorkbookException e) {
            throw new ReportExpressionException(e);
        }
        this.parseCellValue(sheet, cellMap, bindingInfo);
        this.parseCellDisplay(sheet, cellMap, bindingInfo);
        this.parseCellComment(sheet, cellMap, bindingInfo);
        this.parseCellFilter(sheet, cellMap, bindingInfo);
        this.parseCellLink(sheet, cellMap, bindingInfo);
        this.parseCellOrder(sheet, cellMap, bindingInfo);
        if (isPrimarySheet) {
            this.parseExtCellOrder(sheet, cellMap, bindingInfo);
        }
        this.analyseCellFormat(sheet, cellMap, bindingInfo);
        this.validateCell(sheet, cellMap, bindingInfo);
        bindingInfo.setTraceable(this.traceCells.contains(bindingInfo.getPosition()));
        return bindingInfo;
    }

    private void parseCellValue(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        if (StringUtils.isEmpty((String)cellMap.getValue())) {
            throw new ReportExpressionException("\u5355\u5143\u683c\u6620\u5c04\u7684\u53d6\u503c\u8868\u8fbe\u5f0f\u4e3a\u7a7a\uff1a" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()));
        }
        try {
            IReportExpression expr = this.parser.parseEval(cellMap.getValue());
            bindingInfo.setValue(expr);
        }
        catch (ReportExpressionException e) {
            this.errors.put(bindingInfo.getPosition(), e.getMessage());
            if (this.isCheckMode()) {
                bindingInfo.setValue(new ErrorExpression(cellMap.getValue(), e.getMessage()));
            }
            throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u53d6\u503c\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private void parseCellDisplay(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        if (StringUtils.isEmpty((String)cellMap.getDisplay())) {
            bindingInfo.setDisplay(null);
            return;
        }
        try {
            IReportExpression expr = this.parser.parseEval(cellMap.getDisplay());
            bindingInfo.setDisplay(expr);
        }
        catch (ReportExpressionException e) {
            this.errors.put(bindingInfo.getPosition(), e.getMessage());
            if (this.isCheckMode()) {
                bindingInfo.setDisplay(new ErrorExpression(cellMap.getDisplay(), e.getMessage()));
            }
            throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u663e\u793a\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        if (bindingInfo.getDisplay().hasCellRef()) {
            if (this.isCheckMode()) {
                bindingInfo.setDisplay(new ErrorExpression(cellMap.getDisplay(), "\u5355\u5143\u683c\u663e\u793a\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u6570\u636e\u3002"));
            } else {
                throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u663e\u793a\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c\u663e\u793a\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u6570\u636e\u3002");
            }
        }
    }

    private void parseCellComment(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        if (StringUtils.isEmpty((String)cellMap.getComment())) {
            bindingInfo.setComment(null);
            return;
        }
        try {
            IReportExpression expr = this.parser.parseEval(cellMap.getComment());
            bindingInfo.setComment(expr);
        }
        catch (ReportExpressionException e) {
            this.errors.put(bindingInfo.getPosition(), e.getMessage());
            if (this.isCheckMode()) {
                bindingInfo.setComment(new ErrorExpression(cellMap.getComment(), e.getMessage()));
            }
            throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u6279\u6ce8\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        if (bindingInfo.getComment().hasCellRef()) {
            if (this.isCheckMode()) {
                bindingInfo.setDisplay(new ErrorExpression(cellMap.getDisplay(), "\u5355\u5143\u683c\u6279\u6ce8\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u6570\u636e\u3002"));
            } else {
                throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u6279\u6ce8\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c\u6279\u6ce8\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u6570\u636e\u3002");
            }
        }
    }

    private void parseCellFilter(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        if (StringUtils.isEmpty((String)cellMap.getFilter())) {
            bindingInfo.setFilter(null);
            return;
        }
        try {
            IReportExpression expr = this.parser.parseCond(cellMap.getFilter());
            bindingInfo.setFilter(expr);
        }
        catch (ReportExpressionException e) {
            this.errors.put(bindingInfo.getPosition(), e.getMessage());
            if (this.isCheckMode()) {
                bindingInfo.setDisplay(new ErrorExpression(cellMap.getFilter(), e.getMessage()));
            }
            throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void parseCellLink(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        if (cellMap.getHyperlink().getType() == HyperlinkType.NONE || StringUtils.isEmpty((String)cellMap.getHyperlink().getFilter())) {
            bindingInfo.setHyperlinkFilter(null);
            return;
        }
        try {
            bindingInfo.setHyperlinkFilter(this.parser.parseCond(cellMap.getHyperlink().getFilter()));
        }
        catch (ReportExpressionException e) {
            this.errors.put(bindingInfo.getPosition(), e.getMessage());
            if (this.isCheckMode()) {
                bindingInfo.setHyperlinkFilter(new ErrorExpression(cellMap.getHyperlink().getFilter(), e.getMessage()));
            }
            throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u8d85\u94fe\u63a5\u9002\u5e94\u6761\u4ef6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void parseCellOrder(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        if (cellMap.getExpandMode() == ExpandMode.NONE || cellMap.getOrderMode() == OrderMode.NONE || StringUtils.isEmpty((String)cellMap.getOrderValue())) {
            bindingInfo.setOrder(null);
            return;
        }
        try {
            bindingInfo.setOrder(this.parser.parseEval(cellMap.getOrderValue()));
        }
        catch (ReportExpressionException e) {
            this.errors.put(bindingInfo.getPosition(), e.getMessage());
            if (this.isCheckMode()) {
                bindingInfo.setOrder(new ErrorExpression(cellMap.getOrderValue(), e.getMessage()));
            }
            throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u6392\u5e8f\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        if (bindingInfo.getOrder().hasCellRef()) {
            if (this.isCheckMode()) {
                bindingInfo.setOrder(new ErrorExpression(cellMap.getOrderValue(), "\u5355\u5143\u683c\u6392\u5e8f\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u7684\u6570\u636e\u3002"));
            } else {
                throw new ReportExpressionException("\u89e3\u6790" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\u5355\u5143\u683c\u6392\u5e8f\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c\u6392\u5e8f\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u6570\u636e\u3002");
            }
        }
    }

    private void parseExtCellOrder(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        IReportExpression orderExpr;
        if (cellMap.getExpandMode() != ExpandMode.ROWEXPANDING) {
            return;
        }
        SortingCell orderInfo = this.sortInfos.get(cellMap.getPosition());
        if (orderInfo == null) {
            return;
        }
        try {
            orderExpr = this.parser.parseEval(orderInfo.expression);
        }
        catch (ReportExpressionException e) {
            throw new ReportExpressionException("\u89e3\u6790\u91cd\u7f6e\u7684\u5355\u5143\u683c\uff08" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\uff09\u6392\u5e8f\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + orderInfo.expression, e);
        }
        if (orderExpr.hasCellRef()) {
            throw new ReportExpressionException("\u89e3\u6790\u91cd\u7f6e\u7684\u5355\u5143\u683c\uff08" + Cells.toPosition((String)sheet.getName(), (Position)cellMap.getPosition()) + "\uff09\u6392\u5e8f\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c\u6392\u5e8f\u8868\u8fbe\u5f0f\u7981\u6b62\u5f15\u7528\u5355\u5143\u683c\u6570\u636e\uff1a" + orderInfo.expression);
        }
        bindingInfo.setOrder(orderExpr);
        bindingInfo.setOrderMode(orderInfo.mode);
        this.getLogger().debug("\u5355\u5143\u683c[" + bindingInfo.getPosition() + "]\u91cd\u7f6e\u6392\u5e8f\u8bbe\u7f6e\uff1a" + (Object)((Object)orderInfo.mode) + " - " + orderInfo.expression);
    }

    private void analyseCellFormat(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        GridCell cell = sheet.getGriddata().getCell(cellMap.getPosition().col(), cellMap.getPosition().row());
        switch (cell.getType()) {
            case 0: 
            case 1: {
                if (bindingInfo.getDisplay() == null) {
                    bindingInfo.setFormat(bindingInfo.getValue().getFormat(this.context));
                    break;
                }
                bindingInfo.setFormat(bindingInfo.getDisplay().getFormat(this.context));
                break;
            }
            case 5: {
                IReportExpression expr = bindingInfo.getDisplay() == null ? bindingInfo.getValue() : bindingInfo.getDisplay();
                bindingInfo.setFormat(expr.getFormat(this.context));
                if (bindingInfo.getFormat() != null || expr.getDataType(this.context) != 2) break;
                DateCellProperty dateCell = new DateCellProperty(cell);
                if (dateCell.isTimePattern()) {
                    bindingInfo.setFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                    break;
                }
                bindingInfo.setFormat(new SimpleDateFormat("yyyy-MM-dd"));
                break;
            }
        }
    }

    private void validateCell(WorksheetModel sheet, CellMap cellMap, CellBindingInfo bindingInfo) throws ReportExpressionException {
        FunctionNode expandingFunc;
        if (this.pageMode == PageMode.GRIDDATA && cellMap.getHierarchyMode() == HierarchyMode.TIERED && !this.isCheckMode()) {
            throw new ReportExpressionException("\u9875\u9762\u5206\u9875\u6a21\u5f0f\u4e0b\uff0c\u65e0\u6cd5\u8bbe\u7f6e\u5355\u5143\u683c[" + bindingInfo.getPosition() + "]\u4f7f\u7528\u5206\u7ea7\u663e\u793a");
        }
        if (bindingInfo.isMaster()) {
            expandingFunc = this.findExpandingFunction(bindingInfo.getValue());
            if (expandingFunc != null) {
                throw new ReportExpressionException("\u4e3b\u63a7\u5355\u5143\u683c[" + bindingInfo.getPosition() + "]\u53d6\u503c\u516c\u5f0f\u9519\u8bef\uff0c\u51fd\u6570" + expandingFunc.getDefine().name() + "()\u53ea\u5141\u8bb8\u5728\u4e3b\u63a7\u5355\u5143\u683c\u7684\u663e\u793a\u516c\u5f0f\u6216\u975e\u4e3b\u63a7\u5355\u5143\u683c\u4e2d\u4f7f\u7528");
            }
            expandingFunc = this.findExpandingFunction(bindingInfo.getOrder());
            if (expandingFunc != null) {
                throw new ReportExpressionException("\u4e3b\u63a7\u5355\u5143\u683c[" + bindingInfo.getPosition() + "]\u6392\u5e8f\u516c\u5f0f\u9519\u8bef\uff0c\u51fd\u6570" + expandingFunc.getDefine().name() + "()\u4e0d\u5141\u8bb8\u5728\u6392\u5e8f\u516c\u5f0f\u4e2d\u4f7f\u7528");
            }
        }
        if ((expandingFunc = this.findExpandingFunction(bindingInfo.getFilter())) != null) {
            throw new ReportExpressionException("\u5355\u5143\u683c[" + bindingInfo.getPosition() + "]\u8fc7\u6ee4\u516c\u5f0f\u9519\u8bef\uff0c\u51fd\u6570" + expandingFunc.getDefine().name() + "()\u4e0d\u5141\u8bb8\u5728\u8fc7\u6ee4\u516c\u5f0f\u4e2d\u4f7f\u7528");
        }
    }

    private FunctionNode findExpandingFunction(IReportExpression expression) {
        if (expression == null) {
            return null;
        }
        for (IASTNode node : expression) {
            if (!FunctionNode.isFunction((IASTNode)node, (Class[])new Class[]{Q_ExpandingFunction.class})) continue;
            return (FunctionNode)node;
        }
        return null;
    }

    public void buildCellLinks(CellBindingInfo bindingInfo) throws ReportExpressionException {
        this.clearCellLinks(bindingInfo.getPosition().getSheetName(), bindingInfo.getPosition().getPosition());
        if (bindingInfo.getValue() == null || bindingInfo.getValue().getRootNode() == null) {
            return;
        }
        IASTNode rootNode = bindingInfo.getValue().getRootNode();
        for (IASTNode n : rootNode) {
            if (n instanceof CellNode) {
                this.linkCell((CellNode)n, bindingInfo);
                continue;
            }
            if (!(n instanceof RegionNode)) continue;
            this.linkCells((RegionNode)n, bindingInfo);
        }
    }

    public void clearCellLinks(String sheetName, Position position) throws ReportExpressionException {
        ReportWorksheet workSheet;
        try {
            workSheet = (ReportWorksheet)this.workbook.find(this.context, sheetName);
        }
        catch (CellExcpetion e) {
            throw new ReportExpressionException(e);
        }
        CellBindingInfo bindingInfo = (CellBindingInfo)workSheet.getGridData().getObj(position.col(), position.row());
        if (bindingInfo == null) {
            return;
        }
        for (CellBindingInfo prev : bindingInfo.getDepends()) {
            prev.getAffects().remove(bindingInfo);
        }
        bindingInfo.getDepends().clear();
    }

    private void linkCell(CellNode prev, CellBindingInfo bindingInfo) throws ReportExpressionException {
        CellBindingInfo prevInfo;
        ReportWorksheet worksheet;
        try {
            worksheet = (ReportWorksheet)this.workbook.find(this.context, prev.sheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportExpressionException(bindingInfo.getPosition().toString() + "\u53d6\u503c\u8868\u8fbe\u5f0f\u5f15\u7528\u4e86\u4e0d\u5b58\u5728\u7684\u5355\u5143\u683c\u4f4d\u7f6e\uff1a" + prev.toString());
        }
        try {
            prevInfo = worksheet.openCellBinding(prev.getCellPosition());
        }
        catch (WorkbookException e) {
            throw new ReportExpressionException(e);
        }
        CellBindingInfo.link(prevInfo, bindingInfo);
    }

    private void linkCells(RegionNode prev, CellBindingInfo bindingInfo) throws ReportExpressionException {
        ReportWorksheet worksheet;
        try {
            worksheet = (ReportWorksheet)this.workbook.find(this.context, prev.sheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportExpressionException(bindingInfo.getPosition().toString() + "\u53d6\u503c\u8868\u8fbe\u5f0f\u5f15\u7528\u4e86\u4e0d\u5b58\u5728\u7684\u5355\u5143\u683c\u4f4d\u7f6e\uff1a" + prev.toString());
        }
        Region region = prev.getCellRegion();
        int left = region.left() == -1 ? 1 : region.left();
        int right = region.right() == -1 ? worksheet.getGridData().getColCount() - 1 : region.right();
        int top = region.top() == -1 ? 1 : region.top();
        int bottom = region.bottom() == -1 ? worksheet.getGridData().getRowCount() - 1 : region.bottom();
        for (int col = left; col <= right; ++col) {
            for (int row = top; row <= bottom; ++row) {
                CellBindingInfo prevCell;
                try {
                    prevCell = worksheet.openCellBinding(Position.valueOf((int)col, (int)row));
                }
                catch (WorkbookException e) {
                    throw new ReportExpressionException(e);
                }
                CellBindingInfo.link(prevCell, bindingInfo);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void parseSheetSytles(WorksheetModel sheet, ReportWorksheet worksheet) throws ReportExpressionException {
        if ((this.options & 4) != 0) {
            return;
        }
        this.parser.beginAction(1);
        try {
            for (StyleRegion styleRegion : sheet.getStyleRegions()) {
                IStyleProcessor processor = this.createStyleProcessor(worksheet, styleRegion);
                if (processor == null) continue;
                this.bindStyleProcessor(worksheet, styleRegion, processor);
            }
        }
        finally {
            this.parser.endAction(1);
        }
    }

    private IStyleProcessor createStyleProcessor(ReportWorksheet worksheet, StyleRegion styleRegion) throws ReportExpressionException {
        IStyleProcessor processor = null;
        if (styleRegion.isEnableDataBar()) {
            processor = this.createDataBarProcessor(worksheet, styleRegion, processor);
        }
        if (styleRegion.isEnableIconStyle()) {
            processor = this.createIconProcessor(worksheet, styleRegion, processor);
        }
        if (styleRegion.isEnableCellStyle()) {
            processor = this.createCellStyleProcessor(worksheet, styleRegion, processor);
        }
        return processor;
    }

    private IStyleProcessor createDataBarProcessor(ReportWorksheet worksheet, StyleRegion styleRegion, IStyleProcessor curProcessor) throws ReportExpressionException {
        if (styleRegion.getDataBarMode() == null) {
            return curProcessor;
        }
        DataBarProcessor processor = new DataBarProcessor(curProcessor, styleRegion);
        for (DataBarStyle barStyle : styleRegion.getDataBarStyles()) {
            IReportExpression filter;
            try {
                filter = StringUtils.isEmpty((String)barStyle.getCondition()) ? null : this.parser.parseCond(barStyle.getCondition());
            }
            catch (ReportExpressionException e) {
                throw new ReportExpressionException("\u89e3\u6790\u533a\u57df[" + worksheet.name() + "!" + styleRegion.getRegion() + "]\u7684\u6570\u636e\u6761\u6837\u5f0f\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
            processor.add(filter, barStyle);
        }
        return processor;
    }

    private IStyleProcessor createCellStyleProcessor(ReportWorksheet worksheet, StyleRegion styleRegion, IStyleProcessor curProcessor) throws ReportExpressionException {
        if (styleRegion.getCellStyles().isEmpty()) {
            return curProcessor;
        }
        CellStyleProcessor processor = new CellStyleProcessor(curProcessor);
        for (CellStyle cellStyle : styleRegion.getCellStyles()) {
            IReportExpression filter;
            try {
                filter = this.parser.parseCond(cellStyle.getCondition());
            }
            catch (ReportExpressionException e) {
                throw new ReportExpressionException("\u89e3\u6790\u533a\u57df[" + worksheet.name() + "!" + styleRegion.getRegion() + "]\u7684\u5355\u5143\u683c\u6837\u5f0f\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
            processor.add(filter, cellStyle);
        }
        return processor;
    }

    private IStyleProcessor createIconProcessor(ReportWorksheet worksheet, StyleRegion styleRegion, IStyleProcessor curProcessor) throws ReportExpressionException {
        if (styleRegion.getIconStyles().isEmpty()) {
            return curProcessor;
        }
        IconProcessor processor = new IconProcessor(curProcessor);
        for (IconStyle iconStyle : styleRegion.getIconStyles()) {
            IReportExpression filter;
            try {
                filter = this.parser.parseCond(iconStyle.getCondition());
            }
            catch (ReportExpressionException e) {
                throw new ReportExpressionException("\u89e3\u6790\u533a\u57df[" + worksheet.name() + "!" + styleRegion.getRegion() + "]\u7684\u56fe\u6807\u6837\u5f0f\u8868\u8fbe\u5f0f\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
            processor.add(filter, iconStyle);
        }
        return processor;
    }

    private void bindStyleProcessor(ReportWorksheet worksheet, StyleRegion styleRegion, IStyleProcessor processor) {
        GridData grid = worksheet.getGridData();
        Region region = styleRegion.getRegion();
        for (int col = region.left(); col <= region.right(); ++col) {
            for (int row = region.top(); row <= region.bottom(); ++row) {
                CellField cf = grid.expandCell(col, row);
                if (cf.left != col || cf.top != row) continue;
                CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj(col, row);
                if (bindingInfo == null) {
                    bindingInfo = new CellBindingInfo(new SheetPosition(worksheet.name(), col, row), null);
                    grid.setObj(col, row, (Object)bindingInfo);
                }
                bindingInfo.setStyleProcessor(processor);
            }
        }
    }

    private void parseSheetFilters(WorksheetModel sheet, ReportWorksheet worksheet) throws ReportExpressionException {
        this.parseColFilters(sheet, worksheet);
        this.parseRowFilters(sheet, worksheet);
    }

    private void parseColFilters(WorksheetModel sheet, ReportWorksheet worksheet) throws ReportExpressionException {
        for (FilterInfo colFilter : sheet.getColFilters()) {
            IReportExpression expr;
            if (StringUtils.isEmpty((String)colFilter.getFormula()) || colFilter.getPosition() < 1 || colFilter.getPosition() >= worksheet.getGridData().getColCount()) continue;
            try {
                expr = this.parser.parseCond(colFilter.getFormula());
            }
            catch (ReportExpressionException e) {
                throw new ReportExpressionException("\u89e3\u6790\u9875\u7b7e\u201d" + sheet.getName() + "\u201c\u4e2d" + Position.nameOfCol((int)colFilter.getPosition()) + "\u5217\u53e3\u5f84\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
            FilterBindingInfo bindingInfo = worksheet.openColFilter(colFilter.getPosition());
            bindingInfo.filter = colFilter.getFormula();
            bindingInfo.expression = expr;
        }
    }

    private void parseRowFilters(WorksheetModel sheet, ReportWorksheet worksheet) throws ReportExpressionException {
        for (FilterInfo rowFilter : sheet.getRowFilters()) {
            IReportExpression expr;
            if (StringUtils.isEmpty((String)rowFilter.getFormula()) || rowFilter.getPosition() < 1 || rowFilter.getPosition() >= worksheet.getGridData().getRowCount()) continue;
            try {
                expr = this.parser.parseCond(rowFilter.getFormula());
            }
            catch (ReportExpressionException e) {
                throw new ReportExpressionException("\u89e3\u6790\u9875\u7b7e\u201d" + sheet.getName() + "\u201c\u4e2d" + rowFilter.getPosition() + "\u884c\u53e3\u5f84\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
            FilterBindingInfo bindingInfo = worksheet.openRowFilter(rowFilter.getPosition());
            bindingInfo.filter = rowFilter.getFormula();
            bindingInfo.expression = expr;
        }
    }

    private void analyseDepends() throws ReportExpressionException {
        DependenceAnalyzer analyzer = new DependenceAnalyzer();
        analyzer.setCheckMode(this.isCheckMode());
        analyzer.setWorkbook(this.workbook);
        analyzer.execute();
        this.errors.putAll(analyzer.getErrors());
    }

    public void checkDepends(CellBindingInfo cell) throws ReportExpressionException {
        DependenceAnalyzer analyzer = new DependenceAnalyzer();
        analyzer.setCheckMode(true);
        analyzer.setWorkbook(this.workbook);
        analyzer.execute(cell);
    }

    public Map<SheetPosition, String> getErrors() {
        return this.errors;
    }
}

