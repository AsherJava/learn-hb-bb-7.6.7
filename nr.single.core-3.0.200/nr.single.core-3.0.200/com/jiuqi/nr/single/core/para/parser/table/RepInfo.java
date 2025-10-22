/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.nr.single.core.para.impl.FloatRegionImpl;
import com.jiuqi.nr.single.core.para.impl.ReportDataImpl;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.parser.print.ColRowPair;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepInfo {
    private String code;
    private String title;
    private String subTitle;
    private String moneyUnit;
    private String filter;
    private String subNo;
    private boolean isGather;
    private int defColWidth;
    private int defRowHeight;
    private int colCount;
    private int rowCount;
    private int[] colWidthArr;
    private int[] rowHeightArr;
    private String[][] titleArr;
    private boolean[] colHidden;
    private boolean[] rowHidden;
    private int headCol;
    private int headRow;
    private long[] headerColData;
    private long[] headerRowData;
    private long[][] dataArr;
    private long[][] dataExArr;
    private ReportDataImpl reportData;
    private boolean isFMDM = false;
    private Map<String, List<String>> fmZBMap;
    private FieldDefs defs;
    private ReportTableType tableType = ReportTableType.forValue(0);
    private Map<String, ZBInfo> linkZBMap;
    private boolean iniFieldMap = false;
    private boolean privateCalcView;

    public Map<String, ZBInfo> getLinkZBMap() {
        if (null == this.linkZBMap) {
            this.linkZBMap = new HashMap<String, ZBInfo>();
        }
        return this.linkZBMap;
    }

    public void setLinkZBMap(Map<String, ZBInfo> linkZBMap) {
        this.linkZBMap = linkZBMap;
    }

    public final ReportTableType getTableType() {
        return this.tableType;
    }

    public final boolean isFloatTable() {
        return this.tableType == ReportTableType.RTT_ROWFLOATTABLE || this.tableType == ReportTableType.RTT_COLFLOATTALBE;
    }

    public final boolean isRowFloatTable() {
        return this.tableType == ReportTableType.RTT_ROWFLOATTABLE;
    }

    public final boolean isColFloatTable() {
        return this.tableType == ReportTableType.RTT_COLFLOATTALBE;
    }

    public final String getTableTypeCode() {
        String result = "";
        if (ReportTableType.RTT_FIXTABLE == this.tableType) {
            result = "X";
        } else if (ReportTableType.RTT_ROWFLOATTABLE == this.tableType) {
            result = "R";
        } else if (ReportTableType.RTT_WORDTABLE == this.tableType) {
            result = "W";
        } else if (ReportTableType.RTT_BLOBTABLE == this.tableType) {
            result = "F";
        } else if (ReportTableType.RTT_COLFLOATTALBE == this.tableType) {
            result = "C";
        }
        return result;
    }

    public final void setTableType(ReportTableType value) {
        this.tableType = value;
    }

    public final void setTableType(String value) {
        this.tableType = ReportTableType.RTT_FIXTABLE;
        if ("X".equalsIgnoreCase(value)) {
            this.tableType = ReportTableType.RTT_FIXTABLE;
        } else if ("R".equalsIgnoreCase(value)) {
            this.tableType = ReportTableType.RTT_ROWFLOATTABLE;
        } else if ("W".equalsIgnoreCase(value)) {
            this.tableType = ReportTableType.RTT_WORDTABLE;
        } else if ("C".equalsIgnoreCase(value)) {
            this.tableType = ReportTableType.RTT_COLFLOATTALBE;
        } else if ("F".equalsIgnoreCase(value)) {
            this.tableType = ReportTableType.RTT_BLOBTABLE;
        }
    }

    public final ReportFormImpl convertReportFormImpl() {
        ReportFormImpl reportFormImpl = new ReportFormImpl();
        return reportFormImpl;
    }

    public final String getCode() {
        return this.code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getSubTitle() {
        return this.subTitle;
    }

    public final void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public final String getMoneyUnit() {
        return this.moneyUnit;
    }

    public final void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }

    public final String getFilter() {
        return this.filter;
    }

    public final boolean getCalcView() {
        return this.privateCalcView;
    }

    public final void setCalcView(boolean value) {
        this.privateCalcView = value;
    }

    public final void setFilter(String filter) {
        this.filter = filter;
    }

    public final void setGather(boolean value) {
        this.isGather = value;
    }

    public final boolean getGather() {
        return this.isGather;
    }

    public final String getSubNo() {
        return this.subNo;
    }

    public final void setSubNo(String subNo) {
        this.subNo = subNo;
    }

    public final int getDefColWidth() {
        return this.defColWidth;
    }

    public final void setDefColWidth(int defColWidth) {
        this.defColWidth = defColWidth;
    }

    public final int getDefRowHeight() {
        return this.defRowHeight;
    }

    public final void setDefRowHeight(int defRowHeight) {
        this.defRowHeight = defRowHeight;
    }

    public final int getColCount() {
        if (this.getTableType() == ReportTableType.RTT_BLOBTABLE && this.colCount == 0) {
            return 3;
        }
        return this.colCount;
    }

    public final void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public final int getRowCount() {
        if (this.getTableType() == ReportTableType.RTT_BLOBTABLE && this.rowCount == 0) {
            return 2;
        }
        return this.rowCount;
    }

    public final void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public final int[] getColWidthArr() {
        return this.colWidthArr;
    }

    public final void setColWidthArr(int[] colWidthArr) {
        this.colWidthArr = colWidthArr;
    }

    public final int[] getRowHeightArr() {
        return this.rowHeightArr;
    }

    public final void setRowHeightArr(int[] rowHeightArr) {
        this.rowHeightArr = rowHeightArr;
    }

    public final String[][] getTitleArr() {
        return this.titleArr;
    }

    public final void setTitleArr(String[][] titleArr) {
        this.titleArr = titleArr;
    }

    public final boolean[] getColHidden() {
        return this.colHidden;
    }

    public final void setColHidden(boolean[] colHidden) {
        this.colHidden = colHidden;
    }

    public long[] getHeaderColData() {
        return this.headerColData;
    }

    public void setHeaderColData(long[] headerColData) {
        this.headerColData = headerColData;
    }

    public long[] getHeaderRowData() {
        return this.headerRowData;
    }

    public void setHeaderRowData(long[] headerRowData) {
        this.headerRowData = headerRowData;
    }

    public final long[][] getDataArr() {
        return this.dataArr;
    }

    public final void setDataArr(long[][] dataArr) {
        this.dataArr = dataArr;
    }

    public final long[][] getDataExArr() {
        return this.dataExArr;
    }

    public final void setDataExArr(long[][] dataExArr) {
        this.dataExArr = dataExArr;
    }

    public final boolean[] getRowHidden() {
        return this.rowHidden;
    }

    public final void setRowHidden(boolean[] rowHidden) {
        this.rowHidden = rowHidden;
    }

    public final ReportDataImpl getReportData() {
        return this.reportData;
    }

    public final void setReportData(ReportDataImpl reportData) {
        this.reportData = reportData;
    }

    public final boolean isFMDM() {
        return this.isFMDM;
    }

    public final void setFMDM(boolean isFMDM) {
        this.isFMDM = isFMDM;
    }

    public final Map<String, List<String>> getFmZBMap() {
        if (this.fmZBMap == null) {
            this.fmZBMap = new HashMap<String, List<String>>();
        }
        return this.fmZBMap;
    }

    public final void setFmZBMap(Map<String, List<String>> fmZBMap) {
        this.fmZBMap = fmZBMap;
    }

    public final int getHeadCol() {
        return this.headCol;
    }

    public final void setHeadCol(int headCol) {
        this.headCol = headCol;
    }

    public final int getHeadRow() {
        return this.headRow;
    }

    public final void setHeadRow(int headRow) {
        this.headRow = headRow;
    }

    public void addDefs(FieldDefs defs) {
        this.defs = defs;
    }

    public final FieldDefs getDefs() {
        return this.defs;
    }

    public final void getMaxColAndRow(ColRowPair maxColRow) {
        maxColRow.setCol(0);
        maxColRow.setRow(0);
        List<ZBInfo> zbList = this.defs.getZbs();
        this.getMaxColAndRowBySub(zbList, maxColRow);
        for (FieldDefs childDef : this.defs.getSubMbs()) {
            if (childDef == null) continue;
            zbList = childDef.getZbs();
            this.getMaxColAndRowBySub(zbList, maxColRow);
        }
    }

    private final void getMaxColAndRowBySub(List<ZBInfo> zbList, ColRowPair maxColRow) {
        Integer maxCol = maxColRow.getCol();
        Integer maxRow = maxColRow.getRow();
        for (ZBInfo zbInfo : zbList) {
            if (zbInfo.getNumPos() == null) continue;
            if (maxCol < zbInfo.getNumPos()[0]) {
                maxCol = zbInfo.getNumPos()[0];
            }
            if (maxRow >= zbInfo.getNumPos()[1]) continue;
            maxRow = zbInfo.getNumPos()[1];
        }
        maxColRow.setCol(maxCol);
        maxColRow.setRow(maxRow);
    }

    public final List<ZBInfo> getHiddenZbInfos() {
        ArrayList<ZBInfo> result = new ArrayList<ZBInfo>();
        List<ZBInfo> zbList = this.defs.getZbs();
        this.getHiddenZbInfosBySub(zbList, result);
        for (FieldDefs childDef : this.defs.getSubMbs()) {
            if (childDef == null) continue;
            zbList = childDef.getZbs();
            this.getHiddenZbInfosBySub(zbList, result);
        }
        return result;
    }

    private final void getHiddenZbInfosBySub(List<ZBInfo> zbList, List<ZBInfo> list) {
        for (ZBInfo zbInfo : zbList) {
            if (zbInfo.getNumPos() == null || zbInfo.getNumPos()[0] != 0 || zbInfo.getNumPos()[1] != 0 || zbInfo.getGridPos()[0] != 0) continue;
            list.add(zbInfo);
        }
    }

    public final ZBInfo findZBInfoByFieldCode(String fieldCode) {
        ZBInfo zb = null;
        if (this.defs.getAllZbInfosPair().containsKey(fieldCode)) {
            zb = this.defs.getAllZbInfosPair().get(fieldCode);
        } else if (this.defs.getSubMbs() != null) {
            for (FieldDefs floatDefs : this.defs.getSubMbs()) {
                if (!floatDefs.getAllZbInfosPair().containsKey(fieldCode)) continue;
                zb = floatDefs.getAllZbInfosPair().get(fieldCode);
            }
        }
        return zb;
    }

    public final ZBInfo findZBInfoByColRowNum(int colNum, int rowNum) {
        ZBInfo zb = null;
        String aCode = String.valueOf(colNum) + "_" + String.valueOf(rowNum);
        if (this.defs.getZbInfosPairByCRNum().containsKey(aCode)) {
            zb = this.defs.getZbInfosPairByCRNum().get(aCode);
        } else if (this.defs.getSubMbs() != null) {
            for (FieldDefs floatDefs : this.defs.getSubMbs()) {
                if (!floatDefs.getZbInfosPairByCRNum().containsKey(aCode)) continue;
                zb = floatDefs.getZbInfosPairByCRNum().get(aCode);
            }
        }
        return zb;
    }

    public final Grid2Data getApplyGridData() {
        Grid2Data gridData = Grid2Data.bytesToGrid((byte[])this.reportData.getGridBytes());
        List<FieldDefs> floatRegionList = this.defs.getSubMbs();
        if (floatRegionList.isEmpty()) {
            return gridData;
        }
        FieldDefs fieldRegion = null;
        for (int i = floatRegionList.size() - 1; i >= 0; --i) {
            fieldRegion = floatRegionList.get(i);
            FloatRegionImpl regionImp = fieldRegion.getRegionInfo().getMapArea();
            if (regionImp.getFloatRangeStartNo() != regionImp.getFloatRangeEndNo()) continue;
            gridData.insertRows(regionImp.getFloatRangeStartNo() + 1, 5);
        }
        return gridData;
    }

    public final boolean isFloatZB(String fieldName) {
        List<String> fixZBList = this.defs.getZbNameList();
        if (fixZBList.contains(fieldName)) {
            return false;
        }
        List<FieldDefs> floatDefsList = this.defs.getSubMbs();
        for (FieldDefs floatDefs : floatDefsList) {
            List<String> zblist = floatDefs.getZbNameList();
            if (!zblist.contains(fieldName)) continue;
            return true;
        }
        return false;
    }

    public boolean isIniFieldMap() {
        return this.iniFieldMap;
    }

    public void setIniFieldMap(boolean iniFieldMap) {
        this.iniFieldMap = iniFieldMap;
    }
}

