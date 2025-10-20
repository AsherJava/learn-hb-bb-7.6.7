/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.quickreport.engine.ReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.context.PagingUtils;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.result.PagingInfo;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class GridPageReportEngine
extends ReportEngine {
    private List<SheetData> resultSheets = new ArrayList<SheetData>();
    private SheetData primarySheet;
    private int curPageNum;

    public GridPageReportEngine(String userID, QuickReport report, IParameterEnv paramEnv) {
        super(userID, report, paramEnv);
    }

    @Override
    public void open(int options) throws ReportEngineException {
        super.open(options);
        EngineWorkbook workbook = this.execute();
        if ((options & 1) == 0) {
            this.cachePrimarySheet(workbook);
        } else {
            this.cacheAllSheets(workbook);
        }
        if (this.report.getPageInfo().getPageMode() == PageMode.GRIDDATA && this.report.getPageInfo().getRowCount() > 0) {
            this.primarySheet.getGridData().setRowCountPerPage(this.report.getPageInfo().getRowCount());
        }
        this.curPageNum = -1;
    }

    @Override
    protected void interactionChanged() throws ReportInteractionException {
        this.primarySheet = null;
        this.resultSheets.clear();
    }

    private void cachePrimarySheet(EngineWorkbook workbook) throws ReportEngineException {
        EngineWorksheet workSheet;
        try {
            workSheet = (EngineWorksheet)workbook.find(this.context, this.report.getPrimarySheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportEngineException(e);
        }
        if (workSheet == null) {
            throw new ReportEngineException("\u67e5\u627e\u4e3b\u9875\u7b7e\u4e0d\u5b58\u5728\uff1a" + this.report.getPrimarySheetName());
        }
        SheetData sheetData = this.toSheetData(workSheet);
        this.resultSheets.add(sheetData);
        this.primarySheet = sheetData;
    }

    private void cacheAllSheets(EngineWorkbook workbook) throws ReportEngineException {
        for (int i = 0; i < workbook.sheetSize(); ++i) {
            SheetData sheetData = this.toSheetData((EngineWorksheet)workbook.getSheet(i));
            this.resultSheets.add(sheetData);
            if (!sheetData.getSheetName().equalsIgnoreCase(this.report.getPrimarySheetName())) continue;
            this.primarySheet = sheetData;
        }
        if (this.primarySheet == null) {
            throw new ReportEngineException("\u62a5\u8868\u672a\u6307\u5b9a\u4e3b\u9875\u7b7e\u6216\u6307\u5b9a\u7684\u4e3b\u9875\u7b7e\u4e0d\u5b58\u5728\u3002");
        }
    }

    private synchronized void checkOpened() throws ReportEngineException {
        if (this.primarySheet == null) {
            this.open(this.options);
        }
    }

    @Override
    public SheetData getPrimarySheet() throws ReportEngineException {
        this.checkOpened();
        return this.primarySheet;
    }

    @Override
    public List<SheetData> getAllSheets() throws ReportEngineException {
        if ((this.options & 1) == 0) {
            throw new ReportEngineException("\u5f15\u64ce\u6253\u5f00\u65f6\u672a\u6307\u5b9aMULTISHEETS\u9009\u9879\uff0c\u65e0\u6cd5\u83b7\u53d6\u6240\u6709\u9875\u7b7e\u6570\u636e\u3002");
        }
        this.checkOpened();
        return this.resultSheets;
    }

    @Override
    public int getPageCount() throws ReportEngineException {
        this.checkOpened();
        return this.primarySheet.getGridData().getPageCount();
    }

    @Override
    public List<PagingInfo> getPagingInfos() throws ReportEngineException {
        if (this.report.getPageInfo().getPageMode() == PageMode.NONE) {
            return Collections.emptyList();
        }
        PagingInfo pagingInfo = this.createPagingInfo(this.curPageNum);
        return Arrays.asList(pagingInfo);
    }

    private PagingInfo createPagingInfo(int pageNum) throws ReportEngineException {
        PagingInfo pagingInfo = new PagingInfo("@GLOBAL");
        pagingInfo.setPageCount(this.getPageCount());
        pagingInfo.setPageNum(pageNum);
        pagingInfo.setPageSize(this.report.getPageInfo().getRowCount());
        return pagingInfo;
    }

    @Override
    public SheetData getPagedPrimarySheet(int pageNum) throws ReportEngineException {
        this.checkOpened();
        this.curPageNum = pageNum;
        if (pageNum <= 0) {
            return this.primarySheet;
        }
        SheetData sheetData = this.primarySheet.getPagedData(pageNum);
        if (this.report.getPageInfo().getPageMode() == PageMode.GRIDDATA) {
            sheetData.getPagingInfos().add(this.createPagingInfo(pageNum));
        }
        return sheetData;
    }

    @Override
    public SheetData getPagedPrimarySheet(Map<String, Integer> pageNums) throws ReportEngineException {
        int pageNum = PagingUtils.numOfPage(pageNums);
        return pageNum > 0 ? this.getPagedPrimarySheet(pageNum) : this.getPrimarySheet();
    }

    @Override
    public List<SheetData> getPagedAllSheets(int pageNum) throws ReportEngineException {
        this.checkOpened();
        this.curPageNum = pageNum;
        ArrayList<SheetData> sheets = new ArrayList<SheetData>(this.resultSheets.size());
        for (SheetData sheet : this.resultSheets) {
            if (sheet == this.primarySheet && pageNum >= 1) {
                sheets.add(this.getPagedPrimarySheet(pageNum));
                continue;
            }
            sheets.add(sheet);
        }
        return sheets;
    }

    @Override
    public List<SheetData> getPagedAllSheets(Map<String, Integer> pageNums) throws ReportEngineException {
        int pageNum = PagingUtils.numOfPage(pageNums);
        return pageNum > 0 ? this.getPagedAllSheets(pageNum) : this.getAllSheets();
    }

    @Override
    public void flush() {
        super.flush();
        this.primarySheet = null;
        this.resultSheets.clear();
    }
}

