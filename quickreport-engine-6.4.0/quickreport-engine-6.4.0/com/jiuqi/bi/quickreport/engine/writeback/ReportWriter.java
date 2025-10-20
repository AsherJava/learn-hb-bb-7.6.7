/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine.writeback;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.IReportParser;
import com.jiuqi.bi.quickreport.engine.parser.ReportParser;
import com.jiuqi.bi.quickreport.engine.result.WritebackData;
import com.jiuqi.bi.quickreport.engine.result.WritebackMapInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.SheetWriter;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.ExpandingSheetWriter;
import com.jiuqi.bi.quickreport.engine.writeback.fixed.FixedSheetWriter;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackWorkbook;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReportWriter {
    private String userID;
    private QuickReport report;
    private ReportContext context;
    private EngineWorkbook workbook;
    private IParameterEnv paramEnv;
    private int options;
    private Map<String, Integer> infos = new HashMap<String, Integer>();
    private WritebackData data = new WritebackData();

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public QuickReport getReport() {
        return this.report;
    }

    public void setReport(QuickReport report) {
        this.report = report;
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public EngineWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(EngineWorkbook workbook) {
        this.workbook = workbook;
    }

    public IParameterEnv getParamEnv() {
        return this.paramEnv;
    }

    public void setParamEnv(IParameterEnv paramEnv) {
        this.paramEnv = paramEnv;
    }

    public int getOptions() {
        return this.options;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public void execute() throws ReportWritebackException {
        IReportParser parser = this.createParser();
        for (WritebackModel writeSheet : this.report.getWritebackSheets()) {
            this.writeSheet(parser, writeSheet);
        }
    }

    public void writeback(WritebackData data) throws ReportWritebackException {
        IReportParser parser = this.createParser();
        for (WritebackModel writeSheet : this.report.getWritebackSheets()) {
            MemoryDataSet<?> dataSet = data.get(writeSheet.getSheetName());
            if (dataSet == null) continue;
            SheetWriter writer = this.createSheetWriter(parser, writeSheet);
            writer.writeback(dataSet);
            this.infos.put(writeSheet.getSheetName(), dataSet.size());
        }
    }

    private IReportParser createParser() {
        ReportParser parser = new ReportParser(this.context);
        parser.setParamEnv(this.paramEnv);
        parser.setWorkbook(new WritebackWorkbook(this.workbook, this.report));
        return parser;
    }

    private void writeSheet(IReportParser parser, WritebackModel writeSheet) throws ReportWritebackException {
        SheetWriter writer = this.createSheetWriter(parser, writeSheet);
        int recSize = writer.execute((this.options & 0x10) == 0);
        if ((this.options & 0x100) != 0) {
            this.data.put(writeSheet.getSheetName(), writer.getDataSet());
        }
        this.infos.put(writeSheet.getSheetName(), recSize);
    }

    private SheetWriter createSheetWriter(IReportParser parser, WritebackModel writeSheet) throws ReportWritebackException {
        SheetWriter writer;
        switch (writeSheet.getTableType()) {
            case FIXED: {
                writer = new FixedSheetWriter(writeSheet);
                break;
            }
            case EXPAND: {
                writer = new ExpandingSheetWriter(writeSheet);
                break;
            }
            default: {
                throw new ReportWritebackException("\u9875\u7b7e[" + writeSheet.getSheetName() + "]\u7684\u56de\u5199\u7c7b\u578b\u4e0d\u652f\u6301\uff1a" + (Object)((Object)writeSheet.getTableType()));
            }
        }
        writer.setUserID(this.userID);
        writer.setModel(writeSheet);
        writer.setWorkbook(this.workbook);
        writer.setParser(parser);
        writer.setReportContext(this.context);
        return writer;
    }

    public Map<String, Integer> getInfos() {
        return this.infos;
    }

    public WritebackData getData() {
        return this.data;
    }

    public List<WritebackMapInfo> getMappings() throws ReportWritebackException {
        ArrayList<WritebackMapInfo> mappings = new ArrayList<WritebackMapInfo>();
        IReportParser parser = this.createParser();
        for (WritebackModel writeSheet : this.report.getWritebackSheets()) {
            SheetWriter writer = this.createSheetWriter(parser, writeSheet);
            writer.getMappings(mappings);
        }
        return mappings;
    }
}

