/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.IReportExporter;
import com.jiuqi.bi.quickreport.engine.IReportInteraction;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.result.PagingInfo;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.quickreport.engine.result.WritebackData;
import com.jiuqi.bi.quickreport.engine.result.WritebackMapInfo;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkEnv;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IReportEngine {
    public void initParamEnv() throws ReportEngineException;

    public String getLanguage();

    public void setLanguage(String var1);

    public void open(int var1) throws ReportEngineException;

    @Deprecated
    public IParameterEnv getParamEnv();

    public SheetData getPrimarySheet() throws ReportEngineException;

    public List<SheetData> getAllSheets() throws ReportEngineException;

    public int getPageCount() throws ReportEngineException;

    public List<PagingInfo> getPagingInfos() throws ReportEngineException;

    public SheetData getPagedPrimarySheet(int var1) throws ReportEngineException;

    public SheetData getPagedPrimarySheet(Map<String, Integer> var1) throws ReportEngineException;

    public List<SheetData> getPagedAllSheets(int var1) throws ReportEngineException;

    public List<SheetData> getPagedAllSheets(Map<String, Integer> var1) throws ReportEngineException;

    @Deprecated
    public IHyperlinkEnv getHyperlinkEnv();

    @Deprecated
    public void exportPrimarySheet(OutputStream var1) throws ReportEngineException;

    @Deprecated
    public void exportAllSheets(OutputStream var1) throws ReportEngineException;

    @Deprecated
    public void exportPagedPrimarySheet(OutputStream var1, int var2) throws ReportEngineException;

    @Deprecated
    public void exportPagedPrimarySheet(OutputStream var1, Map<String, Integer> var2) throws ReportEngineException;

    @Deprecated
    public void exportPagedAllSheets(OutputStream var1, int var2) throws ReportEngineException;

    @Deprecated
    public void exportPagedAllSheets(OutputStream var1, Map<String, Integer> var2) throws ReportEngineException;

    public IReportExporter createExporter() throws ReportEngineException;

    public Map<String, Integer> getWritebackInfos();

    public WritebackData getWritebackData();

    public List<WritebackMapInfo> getWritebackMappings() throws ReportEngineException;

    public void writeback(WritebackData var1) throws ReportEngineException;

    public void flush();

    public IReportInteraction getInteraction();

    public void setListener(IReportListener var1);

    public Map<String, String> getConfig();
}

