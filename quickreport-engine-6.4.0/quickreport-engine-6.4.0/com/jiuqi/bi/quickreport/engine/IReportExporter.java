/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.watermark.IWatermarkInjector
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.office.excel.watermark.IWatermarkInjector;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import java.io.OutputStream;
import java.util.Map;

public interface IReportExporter {
    public String getFormat();

    public void setFormat(String var1);

    public Map<String, Integer> getPageNums();

    default public void setPageNum(int pageNum) {
        this.getPageNums().clear();
        this.getPageNums().put("@DEFAULT", pageNum);
    }

    public boolean isAllSheets();

    public void setAllSheets(boolean var1);

    public void setWatermarkInjector(IWatermarkInjector var1);

    public void export(OutputStream var1) throws ReportEngineException;

    public void export(OutputStream var1, int var2) throws ReportEngineException;
}

