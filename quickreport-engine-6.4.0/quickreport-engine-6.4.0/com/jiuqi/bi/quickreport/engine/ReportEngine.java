/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelException
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.office.excel.SimpleExportor
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelException;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.office.excel.SimpleExportor;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.ReportLog;
import com.jiuqi.bi.quickreport.engine.IEngineListener;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.IReportExporter;
import com.jiuqi.bi.quickreport.engine.IReportInteraction;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.area.GridAreaAnalyzer;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.GridDataBuilder;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.folding.FoldingAnalyzer;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.bi.quickreport.engine.build.print.PrintSettingAdjustor;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.ReportDataSetProvider;
import com.jiuqi.bi.quickreport.engine.context.ReportNotFoundException;
import com.jiuqi.bi.quickreport.engine.export.ReportExporter;
import com.jiuqi.bi.quickreport.engine.interaction.InteractionAnalyzer;
import com.jiuqi.bi.quickreport.engine.interaction.InteractionFilterBuilder;
import com.jiuqi.bi.quickreport.engine.interaction.InteractionUtil;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteraction;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayer;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayerAnalyzer;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayerException;
import com.jiuqi.bi.quickreport.engine.parameter.BufferedParameterEnv;
import com.jiuqi.bi.quickreport.engine.parameter.ParameterEnvHelper;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.quickreport.engine.parser.WorkbookParser;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.result.FoldingInfo;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.quickreport.engine.result.WritebackData;
import com.jiuqi.bi.quickreport.engine.result.WritebackMapInfo;
import com.jiuqi.bi.quickreport.engine.script.ScriptRunner;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWriter;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkEnv;
import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import java.io.File;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ReportEngine
implements IReportEngine {
    protected String userID;
    protected QuickReport report;
    protected com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv;
    protected ReportContext context;
    protected ReportDataSetProvider datasetProvider;
    protected HyperlinkEnv hyperlinkEnv;
    protected Map<String, Integer> writebackInfos;
    protected WritebackData writebackData;
    protected int options;
    protected ILogger log;
    private final ScriptRunner engineScriptRunner;
    private final ReportInteraction interaction;
    private static final int MAX_ERROR_SIZE = 10;

    public ReportEngine(String userID, QuickReport report, com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) {
        this.userID = userID;
        this.report = report;
        this.paramEnv = paramEnv;
        this.context = new ReportContext(report);
        this.context.setUserID(userID);
        this.context.setParamEnv(paramEnv);
        this.datasetProvider = new ReportDataSetProvider(report);
        this.context.setDataSetProvider(this.datasetProvider);
        this.writebackInfos = new HashMap<String, Integer>();
        this.log = ReportLog.openLogger();
        this.engineScriptRunner = new ScriptRunner(report, this.log);
        this.interaction = new ReportInteraction(this);
    }

    @Override
    public void initParamEnv() throws ReportEngineException {
        for (DataSetInfo dataSetInfo : this.report.getRefDataSets()) {
            DSModel dsModel;
            String dsName = dataSetInfo.getId();
            try {
                dsModel = this.context.openDataSetModel(dsName);
            }
            catch (ReportNotFoundException notFound) {
                this.log.error("\u5f15\u7528\u6570\u636e\u96c6\u4e22\u5931\uff1a" + dsName, (Throwable)notFound);
                continue;
            }
            catch (ReportContextException e) {
                throw new ReportEngineException(e);
            }
            try {
                dsModel.prepareParameterEnv(this.paramEnv);
            }
            catch (DSModelException e) {
                throw new ReportEngineException(e);
            }
        }
    }

    @Override
    public String getLanguage() {
        return this.context.getLanguage();
    }

    @Override
    public void setLanguage(String language) {
        this.context.setLanguage(language);
    }

    @Override
    public void open(int options) throws ReportEngineException {
        this.options = options;
    }

    @Override
    @Deprecated
    public IParameterEnv getParamEnv() {
        return ParameterEnvHelper.unwrap(this.paramEnv);
    }

    public final EngineWorkbook execute() throws ReportEngineException {
        this.log.debug("\u5f00\u59cb\u6267\u884c\u62a5\u8868[" + this.report.getName() + " " + this.report.getTitle() + "]...");
        EngineListener listener = new EngineListener();
        if (!this.execute(listener)) {
            throw new ReportEngineException("\u62a5\u8868\u6267\u884c\u88ab\u7ec8\u6b62");
        }
        this.log.debug("\u62a5\u8868[" + this.report.getName() + "]\u5904\u7406\u5b8c\u6210\u3002");
        this.dumpGrid(listener.getWorkbook());
        return listener.getWorkbook();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized boolean execute(IEngineListener listener) throws ReportEngineException {
        EngineWorkbook workbook;
        block12: {
            block11: {
                List<GridArea> areas;
                block10: {
                    List<CalcLayer> calcLayers;
                    block9: {
                        block8: {
                            block7: {
                                boolean bl;
                                workbook = null;
                                try {
                                    this.beginExecute();
                                    if (listener.begin(this.context)) break block7;
                                    bl = false;
                                }
                                catch (Throwable throwable) {
                                    this.endExecute(workbook);
                                    throw throwable;
                                }
                                this.endExecute(workbook);
                                return bl;
                            }
                            workbook = this.parseReport();
                            if (listener.parse(workbook)) break block8;
                            boolean bl = false;
                            this.endExecute(workbook);
                            return bl;
                        }
                        areas = this.analyseArea(workbook);
                        calcLayers = this.analyseLayers(areas);
                        this.buildDataSetFilters();
                        if (listener.analyse(areas, calcLayers)) break block9;
                        boolean bl = false;
                        this.endExecute(workbook);
                        return bl;
                    }
                    this.buildGridData(workbook, calcLayers);
                    if (listener.build(workbook)) break block10;
                    boolean bl = false;
                    this.endExecute(workbook);
                    return bl;
                }
                this.prepareInteractions(workbook, areas);
                if (listener.interact(this.interaction)) break block11;
                boolean bl = false;
                this.endExecute(workbook);
                return bl;
            }
            this.writeback(workbook);
            if (listener.writeback(workbook)) break block12;
            boolean bl = false;
            this.endExecute(workbook);
            return bl;
        }
        this.endExecute(workbook);
        return listener.end(workbook);
    }

    private void beginExecute() throws ReportEngineException {
        this.context.getCurrentFilters().clear();
        this.context.setCurrentCell(null);
        this.hyperlinkEnv = new HyperlinkEnv();
        this.writebackInfos.clear();
        try {
            this.context.refresh();
        }
        catch (ReportContextException e) {
            throw new ReportEngineException(e);
        }
        this.engineScriptRunner.before(this.report, this.paramEnv, this.context);
    }

    private void endExecute(EngineWorkbook workbook) throws ReportEngineException {
        if (workbook != null) {
            try {
                EngineWorksheet worksheet = (EngineWorksheet)workbook.find(this.context, this.report.getPrimarySheetName());
                this.engineScriptRunner.after(this.report, this.paramEnv, worksheet.getResultGrid().getGridData());
            }
            catch (CellExcpetion e) {
                throw new ReportEngineException(e);
            }
        }
        try {
            this.context.snapshot();
        }
        catch (ReportContextException e) {
            throw new ReportEngineException(e);
        }
        this.context.getDataSetFilters().clear();
    }

    private EngineWorkbook parseReport() throws ReportExpressionException {
        this.log.debug("\u5f00\u59cb\u89e3\u6790\u62a5\u8868...");
        EngineWorkbook workbook = new EngineWorkbook(this.report);
        this.context.setWorkbook(workbook);
        WorkbookParser parser = new WorkbookParser(this.options, this.context);
        parser.setLogger(this.log);
        parser.setWorkbook(workbook);
        parser.setPageMode(this.report.getPageInfo().getPageMode());
        parser.getParser().setParamEnv(new BufferedParameterEnv(this.paramEnv));
        parser.getSortInfos().putAll(this.interaction.getCellSortInfos());
        parser.parse(this.report);
        if (!parser.getErrors().isEmpty()) {
            throw new ReportExpressionException(this.toErrorMessage(parser.getErrors()));
        }
        this.log.debug("\u62a5\u8868\u89e3\u6790\u5b8c\u6210\uff01");
        return workbook;
    }

    private String toErrorMessage(Map<SheetPosition, String> errors) {
        StringBuilder buffer = new StringBuilder("\u89e3\u6790\u62a5\u8868\u9047\u5230\u9519\u8bef\uff1a");
        int count = 0;
        for (Map.Entry<SheetPosition, String> e : errors.entrySet()) {
            buffer.append(StringUtils.LINE_SEPARATOR).append(e.getKey()).append('\uff1a').append(e.getValue());
            if (++count < 10 || errors.size() <= count) continue;
            buffer.append(StringUtils.LINE_SEPARATOR).append("\u5176\u5b83").append(errors.size() - count).append("\u9519\u8bef...");
            break;
        }
        return buffer.toString();
    }

    private List<GridArea> analyseArea(EngineWorkbook workbook) throws ReportAreaExpcetion {
        this.log.debug("\u5f00\u59cb\u5206\u6790\u62a5\u8868\u533a\u57df...");
        GridAreaAnalyzer analyzer = new GridAreaAnalyzer();
        analyzer.setContext(this.context);
        analyzer.setWorkbook(workbook);
        analyzer.analyse();
        this.log.debug("\u62a5\u8868\u533a\u57df\u5206\u6790\u5b8c\u6210\uff01");
        return analyzer.getAreas();
    }

    private List<CalcLayer> analyseLayers(List<GridArea> areas) throws CalcLayerException {
        this.log.debug("\u5f00\u59cb\u5206\u6790\u62a5\u8868\u8ba1\u7b97\u5c42\u6b21...");
        CalcLayerAnalyzer analyzer = new CalcLayerAnalyzer(areas);
        analyzer.analyse();
        this.log.debug("\u62a5\u8868\u8ba1\u7b97\u5c42\u6b21\u5206\u6790\u5b8c\u6210\uff01");
        return analyzer.getCalcLayers();
    }

    private void buildDataSetFilters() throws ReportInteractionException {
        if (this.interaction.getCellFilterInfos().isEmpty()) {
            return;
        }
        this.log.debug("\u5f00\u59cb\u5904\u7406\u8868\u5934\u8fc7\u6ee4\u6761\u4ef6...");
        InteractionFilterBuilder builder = new InteractionFilterBuilder(this.context, this.interaction.getCellFilterInfos());
        builder.build();
        this.log.debug("\u8868\u5934\u8fc7\u6ee4\u6761\u4ef6\u5904\u7406\u5b8c\u6210\uff01");
    }

    private void buildGridData(EngineWorkbook workbook, List<CalcLayer> calcLayers) throws ReportBuildException {
        this.log.debug("\u5f00\u59cb\u751f\u6210\u7ed3\u679c\u8868\u6837...");
        GridDataBuilder builder = new GridDataBuilder();
        builder.setContext(this.context);
        builder.setWorkbook(workbook);
        builder.getCalcLayers().addAll(calcLayers);
        builder.setHyperlinkEnv(this.hyperlinkEnv);
        builder.setNeedMessageLink((this.options & 0x20) == 0);
        builder.setNeedURLLink((this.options & 0x200) == 0);
        builder.build();
        this.log.debug("\u7ed3\u679c\u8868\u6837\u751f\u6210\u5b8c\u6210\u3002");
    }

    private void prepareInteractions(EngineWorkbook workbook, List<GridArea> areas) throws ReportInteractionException {
        if ((this.options & 0x40) != 0) {
            return;
        }
        this.log.debug("\u5f00\u59cb\u5904\u7406\u7528\u6237\u4ea4\u4e92\u64cd\u4f5c...");
        InteractionAnalyzer analyzer = new InteractionAnalyzer();
        analyzer.setContext(this.context);
        analyzer.setWorkbook(workbook);
        analyzer.getAreas().addAll(areas);
        analyzer.setInteraction(this.interaction);
        analyzer.analyse();
        this.log.debug("\u7528\u6237\u4ea4\u4e92\u64cd\u4f5c\u5904\u7406\u5b8c\u6210\uff0c\u751f\u6210" + this.interaction.getInteractiveInfos().size() + "\u4e2a\u4ea4\u4e92\u5355\u5143\u683c\u3002");
    }

    private void writeback(EngineWorkbook workbook) throws ReportWritebackException {
        if ((this.options & 0x110) == 0 || this.report.getWritebackSheets().isEmpty()) {
            return;
        }
        this.log.debug("\u5f00\u59cb\u56de\u5199\u6570\u636e...");
        ReportWriter writer = this.createReportWriter(workbook);
        writer.execute();
        this.writebackInfos.putAll(writer.getInfos());
        this.writebackData = writer.getData();
        this.log.debug("\u6570\u636e\u56de\u5199\u5b8c\u6210\uff01");
    }

    private ReportWriter createReportWriter(EngineWorkbook workbook) {
        ReportWriter writer = new ReportWriter();
        writer.setUserID(this.userID);
        writer.setReport(this.report);
        writer.setContext(this.context);
        writer.setParamEnv(this.paramEnv);
        writer.setWorkbook(workbook);
        writer.setOptions(this.options);
        return writer;
    }

    private void dumpGrid(EngineWorkbook workbook) {
        if (!this.report.getDebugConfig().isEnabled() || StringUtils.isEmpty((String)this.report.getDebugConfig().getDumpDir())) {
            return;
        }
        WorksheetModel sheetModel = this.report.getPrimarySheet();
        if (sheetModel == null) {
            return;
        }
        try {
            String rawFileName = this.report.getDebugConfig().getDumpDir() + File.separator + this.report.getName() + ".RAW.JQC";
            if (rawFileName.contains("..")) {
                throw new IllegalArgumentException("\u975e\u6cd5\u7684\u6587\u4ef6\u8def\u5f84");
            }
            sheetModel.getGriddata().saveToFile(rawFileName);
            EngineWorksheet primarySheet = (EngineWorksheet)workbook.find(this.context, this.report.getPrimarySheetName());
            String gridFileName = this.report.getDebugConfig().getDumpDir() + File.separator + this.report.getName() + ".RESULT.JQC";
            if (gridFileName.contains("..")) {
                throw new IllegalArgumentException("\u975e\u6cd5\u7684\u6587\u4ef6\u8def\u5f84");
            }
            primarySheet.getResultGrid().getGridData().saveToFile(gridFileName);
            String xlsxFileName = this.report.getDebugConfig().getDumpDir() + File.separator + this.report.getName() + ".RESULT.XLSX";
            if (xlsxFileName.contains("..")) {
                throw new IllegalArgumentException("\u975e\u6cd5\u7684\u6587\u4ef6\u8def\u5f84");
            }
            SimpleExportor exporter = new SimpleExportor(primarySheet.getResultGrid().getGridData(), true);
            exporter.export(xlsxFileName);
        }
        catch (Exception e) {
            this.log.error("\u5206\u6790\u8868\u8c03\u8bd5\u8f93\u51fa\u5931\u8d25\uff1a{}", new Object[]{this.report.getName(), e});
        }
    }

    protected final SheetData toSheetData(EngineWorksheet workSheet) throws ReportBuildException {
        FoldingAnalyzer foldingAnalyzer = new FoldingAnalyzer(workSheet);
        GridData grid = workSheet.getResultGrid().getGridData();
        grid.setOptions(grid.getOptions() ^ 0x10);
        for (int row = 1; row < grid.getRowCount(); ++row) {
            for (int col = 1; col < grid.getColCount(); ++col) {
                CellValue cellValue;
                try {
                    cellValue = (CellValue)grid.getObj(col, row);
                }
                catch (ClassCastException e) {
                    SheetPosition pos = new SheetPosition(workSheet.name(), Position.valueOf((int)col, (int)row));
                    throw new ReportBuildException("\u5355\u5143\u683c[" + pos + "]\u4fe1\u606f\u9519\u8bef\uff0c\u7a0b\u5e8f\u5185\u90e8\u903b\u8f91\u6709\u8bef\u3002", e);
                }
                if (cellValue == null) continue;
                CellResultInfo cellResult = new CellResultInfo(cellValue);
                if ((this.options & 0x80) != 0) {
                    InteractionUtil.initRestrictions(cellResult, cellValue);
                }
                FoldingInfo foldingInfo = foldingAnalyzer.scanFolding(col, row);
                cellResult.setFoldingInfo(foldingInfo);
                grid.setObj(col, row, (Object)cellResult);
            }
        }
        PrintSettingAdjustor adjustor = new PrintSettingAdjustor(workSheet.getResultGrid());
        PrintSetting printSetting = adjustor.adjust(workSheet.getSheetModel());
        boolean isPrimarySheet = StringUtils.equalsIgnoreCase((String)workSheet.name(), (String)this.report.getPrimarySheetName());
        if (isPrimarySheet && !this.report.getPrintTempletes().isEmpty()) {
            grid.getExtDatas().setData("print.colmaps", workSheet.getResultGrid().getColMaps());
            grid.getExtDatas().setData("print.rowmaps", workSheet.getResultGrid().getRowMaps());
        }
        SheetData sheetData = new SheetData(workSheet.name(), grid, printSetting);
        if (isPrimarySheet) {
            sheetData.getLinkInfos().addAll(this.hyperlinkEnv.toCellLinkInfos());
            sheetData.getLinkParams().addAll(this.hyperlinkEnv.toCellLinkParams());
        }
        return sheetData;
    }

    protected void interactionChanged() throws ReportInteractionException {
    }

    @Override
    @Deprecated
    public IHyperlinkEnv getHyperlinkEnv() {
        return this.hyperlinkEnv;
    }

    @Override
    public void exportPrimarySheet(OutputStream outStream) throws ReportEngineException {
        IReportExporter exporter = this.createExporter();
        exporter.export(outStream);
    }

    @Override
    public void exportAllSheets(OutputStream outStream) throws ReportEngineException {
        IReportExporter exporter = this.createExporter();
        exporter.setAllSheets(true);
        exporter.export(outStream);
    }

    @Override
    public void exportPagedPrimarySheet(OutputStream outStream, int pageNum) throws ReportEngineException {
        IReportExporter exporter = this.createExporter();
        exporter.setPageNum(pageNum);
        exporter.export(outStream);
    }

    @Override
    public void exportPagedPrimarySheet(OutputStream outStream, Map<String, Integer> pageNums) throws ReportEngineException {
        IReportExporter exporter = this.createExporter();
        if (pageNums != null) {
            exporter.getPageNums().putAll(pageNums);
        }
        exporter.export(outStream);
    }

    @Override
    public void exportPagedAllSheets(OutputStream outStream, int pageNum) throws ReportEngineException {
        IReportExporter exporter = this.createExporter();
        exporter.setAllSheets(true);
        exporter.setPageNum(pageNum);
        exporter.export(outStream);
    }

    @Override
    public void exportPagedAllSheets(OutputStream outStream, Map<String, Integer> pageNums) throws ReportEngineException {
        IReportExporter exporter = this.createExporter();
        exporter.setAllSheets(true);
        if (pageNums != null) {
            exporter.getPageNums().putAll(pageNums);
        }
        exporter.export(outStream);
    }

    @Override
    public IReportExporter createExporter() {
        return new ReportExporter(this);
    }

    @Override
    public Map<String, Integer> getWritebackInfos() {
        return this.writebackInfos;
    }

    @Override
    public WritebackData getWritebackData() {
        return this.writebackData;
    }

    @Override
    public List<WritebackMapInfo> getWritebackMappings() throws ReportEngineException {
        if (this.report.getWritebackSheets().isEmpty()) {
            return Collections.emptyList();
        }
        EngineWorkbook workbook = this.parseReport();
        ReportWriter writer = this.createReportWriter(workbook);
        return writer.getMappings();
    }

    @Override
    public void writeback(WritebackData data) throws ReportEngineException {
        EngineWorkbook workbook = this.parseReport();
        ReportWriter writer = this.createReportWriter(workbook);
        writer.writeback(data);
        this.writebackInfos.putAll(writer.getInfos());
    }

    @Override
    public void flush() {
        this.context.clearCache();
    }

    @Override
    public IReportInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    public void setListener(IReportListener listener) {
        this.context.setListener(listener);
        this.datasetProvider.setListener(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        return this.context.getConfig();
    }

    public ScriptRunner getScriptRunner() {
        return this.engineScriptRunner;
    }

    public QuickReport getReport() {
        return this.report;
    }

    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getParameterEnv() {
        return this.paramEnv;
    }

    public ILogger getLogger() {
        return this.log;
    }

    private static final class EngineListener
    implements IEngineListener {
        private EngineWorkbook workbook;

        private EngineListener() {
        }

        @Override
        public boolean end(EngineWorkbook workbook) throws ReportEngineException {
            this.workbook = workbook;
            return true;
        }

        public EngineWorkbook getWorkbook() {
            return this.workbook;
        }
    }
}

