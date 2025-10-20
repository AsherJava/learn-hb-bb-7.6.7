/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.quickreport.engine.ReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.cache.DSPagingInfo;
import com.jiuqi.bi.quickreport.engine.result.PagingInfo;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DataSetPageReportEngine
extends ReportEngine {
    public DataSetPageReportEngine(String userID, QuickReport report, IParameterEnv paramEnv) {
        super(userID, report, paramEnv);
    }

    @Override
    public SheetData getPrimarySheet() throws ReportEngineException {
        return this.getPagedPrimarySheet(0);
    }

    @Override
    public List<SheetData> getAllSheets() throws ReportEngineException {
        return this.getPagedAllSheets(0);
    }

    @Override
    public int getPageCount() throws ReportEngineException {
        Map<String, DSPagingInfo> pagingInfos;
        try {
            pagingInfos = this.context.getPagingInfos();
        }
        catch (ReportContextException e) {
            throw new ReportBuildException(e);
        }
        if (pagingInfos.isEmpty()) {
            return 1;
        }
        if (pagingInfos.size() == 1) {
            DSPagingInfo pagingInfo = pagingInfos.values().iterator().next();
            return pagingInfo.getPageCount();
        }
        throw new ReportBuildException("\u5206\u6790\u8868\u542f\u7528\u4e86\u591a\u6570\u636e\u96c6\u5206\u9875\uff0c\u65e0\u6cd5\u83b7\u53d6\u5168\u5c40\u5206\u9875\u6570\u91cf");
    }

    @Override
    public List<PagingInfo> getPagingInfos() throws ReportEngineException {
        Map<String, DSPagingInfo> dsPagingInfos;
        try {
            dsPagingInfos = this.context.getPagingInfos();
        }
        catch (ReportContextException e) {
            throw new ReportBuildException(e);
        }
        ArrayList<PagingInfo> pagingInfos = new ArrayList<PagingInfo>();
        dsPagingInfos.values().forEach(dsPi -> dsPi.getRegions().forEach(r -> {
            PagingInfo pi = new PagingInfo(dsPi.getId());
            pi.setPageCount(dsPi.getPageCount());
            pi.setPageNum(dsPi.getPageNum());
            pi.setPageSize(dsPi.getPageSize());
            pi.setRegion((Region)r);
            pagingInfos.add(pi);
        }));
        return pagingInfos;
    }

    @Override
    public SheetData getPagedPrimarySheet(int pageNum) throws ReportEngineException {
        return this.getPagedPrimarySheet(Collections.singletonMap("@DEFAULT", pageNum));
    }

    @Override
    public SheetData getPagedPrimarySheet(Map<String, Integer> pageNums) throws ReportEngineException {
        this.context.setPageNums(pageNums);
        EngineWorkbook workbook = this.execute();
        return this.getPrimaryData(workbook);
    }

    private SheetData getPrimaryData(EngineWorkbook workbook) throws ReportEngineException {
        EngineWorksheet worksheet;
        try {
            worksheet = (EngineWorksheet)workbook.find(this.context, this.report.getPrimarySheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportEngineException(e);
        }
        if (worksheet == null) {
            throw new ReportEngineException("\u67e5\u627e\u4e3b\u9875\u7b7e\u4e0d\u5b58\u5728\uff1a" + this.report.getPrimarySheetName());
        }
        SheetData sheetData = this.toSheetData(worksheet);
        this.fillPagingInfos(sheetData);
        return sheetData;
    }

    @Override
    public List<SheetData> getPagedAllSheets(int pageNum) throws ReportEngineException {
        return this.getPagedAllSheets(Collections.singletonMap("@DEFAULT", pageNum));
    }

    @Override
    public List<SheetData> getPagedAllSheets(Map<String, Integer> pageNums) throws ReportEngineException {
        this.context.setPageNums(pageNums);
        EngineWorkbook workbook = this.execute();
        return this.getAllDatas(workbook);
    }

    private List<SheetData> getAllDatas(EngineWorkbook workbook) throws ReportEngineException {
        ArrayList<SheetData> sheets = new ArrayList<SheetData>(this.report.getWorksheets().size());
        for (int i = 0; i < workbook.sheetSize(); ++i) {
            EngineWorksheet worksheet = (EngineWorksheet)workbook.getSheet(i);
            SheetData sheetData = this.toSheetData(worksheet);
            if (worksheet.name().equalsIgnoreCase(this.report.getPrimarySheetName())) {
                this.fillPagingInfos(sheetData);
            }
            sheets.add(sheetData);
        }
        return sheets;
    }

    private void fillPagingInfos(SheetData sheetData) throws ReportEngineException {
        Map<String, DSPagingInfo> pagingInfos;
        try {
            pagingInfos = this.context.getPagingInfos();
        }
        catch (ReportContextException e) {
            throw new ReportEngineException(e);
        }
        pagingInfos.values().stream().filter(pi -> pi.getPageNum() > 0 && !pi.getRegions().isEmpty()).forEach(pi -> pi.getRegions().forEach(r -> {
            PagingInfo pagingInfo = new PagingInfo(pi.getId());
            pagingInfo.setPageCount(pi.getPageCount());
            pagingInfo.setPageNum(pi.getPageNum());
            pagingInfo.setPageSize(pi.getPageSize());
            pagingInfo.setRegion((Region)r);
            sheetData.getPagingInfos().add(pagingInfo);
        }));
    }
}

