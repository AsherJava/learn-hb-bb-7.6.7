/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.summary.model.caliber.CaliberApplyType;
import com.jiuqi.nr.summary.model.caliber.CaliberFloatInfo;
import com.jiuqi.nr.summary.model.caliber.CaliberInfo;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.GuestCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.report.FilterInfo;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class SummaryReportModelHelper {
    private final SummaryReportModel reportModel;

    public SummaryReportModelHelper(SummaryReportModel reportModel) {
        this.reportModel = reportModel;
    }

    public String getReportName() {
        return this.reportModel.getName();
    }

    public String getReportTitle() {
        return this.reportModel.getTitle();
    }

    public List<SummaryFloatRegion> getFloatRegions() {
        List<SummaryFloatRegion> floatRegions = this.reportModel.getConfig().getRegions();
        return floatRegions;
    }

    public GridData getGridData() {
        return this.reportModel.getGridData();
    }

    public SummaryReportModel getReportModel() {
        return this.reportModel;
    }

    public List<DataCell> getDataCells() {
        return this.reportModel.getDataCells();
    }

    public String generateFloatTableCode(SummaryFloatRegion floatRegion) {
        if (floatRegion == null) {
            return null;
        }
        String tableName = floatRegion.getTableName();
        return StringUtils.hasLength(tableName) ? tableName : "SR" + this.reportModel.getName() + "_F" + floatRegion.getPosition();
    }

    public String generateFloatTableTitle(SummaryFloatRegion floatRegion) {
        if (floatRegion == null) {
            return null;
        }
        String floatTableCode = this.generateFloatTableCode(floatRegion);
        int findex = floatTableCode.indexOf("_F");
        String floatRowNumber = floatTableCode.substring(findex + 2);
        return this.reportModel.getTitle() + "\u6d6e\u52a8\u533a\u57df" + floatRowNumber;
    }

    public String generateFixTableCode() {
        return "SR" + this.reportModel.getName();
    }

    public String generateFixTableTitle() {
        return this.reportModel.getTitle() + "\u56fa\u5b9a\u533a\u57df";
    }

    public boolean hasFixCell() {
        List<DataCell> dataCells = this.reportModel.getDataCells();
        List<SummaryFloatRegion> regions = this.reportModel.getConfig().getRegions();
        List floatPostions = regions.stream().map(SummaryFloatRegion::getPosition).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(dataCells)) {
            for (DataCell dataCell : dataCells) {
                if (floatPostions.contains(dataCell.getX())) continue;
                return true;
            }
        }
        return false;
    }

    public List<DataCell> getFixDataCells() {
        List<DataCell> fixDataCells = this.reportModel.getDataCells();
        List<SummaryFloatRegion> floatRegions = this.reportModel.getConfig().getRegions();
        if (floatRegions != null && !floatRegions.isEmpty()) {
            List floatRegionNumbers = this.reportModel.getConfig().getRegions().stream().map(SummaryFloatRegion::getPosition).collect(Collectors.toList());
            fixDataCells = fixDataCells.stream().filter(cell -> !floatRegionNumbers.contains(cell.getX())).collect(Collectors.toList());
        }
        return fixDataCells;
    }

    public List<DataCell> getFloatDataCells(SummaryFloatRegion region) {
        List<DataCell> floatDataCells = new ArrayList<DataCell>();
        List<DataCell> dataCells = this.reportModel.getDataCells();
        if (dataCells != null) {
            floatDataCells = dataCells.stream().filter(cell -> cell.getX() == region.getPosition()).collect(Collectors.toList());
        }
        return floatDataCells;
    }

    public List<MainCell> getAllMainCells() {
        return this.reportModel.getConfig().getMainCells();
    }

    public List<MainCell> getMainCellsByRegion(SummaryFloatRegion region) {
        List<MainCell> allMainCells;
        List<MainCell> regionMainCells = new ArrayList<MainCell>();
        if (region != null && (allMainCells = this.reportModel.getConfig().getMainCells()) != null) {
            regionMainCells = allMainCells.stream().filter(cell -> cell.getX() == region.getPosition()).collect(Collectors.toList());
        }
        return regionMainCells;
    }

    public String generateMainCellFieldCode(String tableName, MainCell mainCell) {
        List<CaliberInfo> caliberInfos = mainCell.getCaliberInfos();
        for (CaliberInfo caliberInfo : caliberInfos) {
            CaliberFloatInfo floatInfo = caliberInfo.getFloatInfo();
            if (!caliberInfo.getApplyType().equals((Object)CaliberApplyType.FLOAT)) continue;
            return tableName + "_" + floatInfo.getDisplayField().split("\\.")[0];
        }
        return null;
    }

    public String getMainCellFieldTitle(MainCell mainCell) {
        List<CaliberInfo> caliberInfos = mainCell.getCaliberInfos();
        for (CaliberInfo caliberInfo : caliberInfos) {
            if (!caliberInfo.getApplyType().equals((Object)CaliberApplyType.FLOAT)) continue;
            return caliberInfo.getTitle();
        }
        return null;
    }

    public List<MainCell> getMainCellsByRow(Integer row) {
        List<MainCell> allMainCells;
        List<MainCell> regionMainCells = new ArrayList<MainCell>();
        if (row >= 0 && (allMainCells = this.reportModel.getConfig().getMainCells()) != null) {
            regionMainCells = allMainCells.stream().filter(cell -> cell.getX() == row.intValue()).collect(Collectors.toList());
        }
        return regionMainCells;
    }

    public List<GuestCell> getGuestCellByCol(Integer col) {
        List<GuestCell> allGuestCell;
        List<GuestCell> guestCells = new ArrayList<GuestCell>();
        if (col >= 0 && (allGuestCell = this.reportModel.getConfig().getGuestCells()) != null) {
            guestCells = allGuestCell.stream().filter(cell -> cell.getY() == col.intValue()).collect(Collectors.toList());
        }
        return guestCells;
    }

    public List<CaliberInfo> getCaliberInfoByRow(Integer row) {
        return this._getCaliberInfoByRow(row, CaliberApplyType.FILTER);
    }

    public List<CaliberInfo> getFloatCaliberInfoByRow(Integer row) {
        return this._getCaliberInfoByRow(row, CaliberApplyType.FLOAT);
    }

    private List<CaliberInfo> _getCaliberInfoByRow(Integer row, CaliberApplyType type) {
        ArrayList<CaliberInfo> result = new ArrayList<CaliberInfo>();
        List<MainCell> mainCells = this.getMainCellsByRow(row);
        mainCells.forEach(mainCell -> {
            List<CaliberInfo> caliberInfos = mainCell.getCaliberInfos();
            for (CaliberInfo info : caliberInfos) {
                if (!info.getApplyType().equals((Object)type)) continue;
                result.add(info);
            }
        });
        return result;
    }

    public List<CaliberInfo> getCaliberInfoByCol(Integer col) {
        ArrayList<CaliberInfo> result = new ArrayList<CaliberInfo>();
        List<GuestCell> guestCells = this.getGuestCellByCol(col);
        guestCells.forEach(guestCell -> {
            List<CaliberInfo> caliberInfos = guestCell.getCaliberInfos();
            for (CaliberInfo info : caliberInfos) {
                if (!info.getApplyType().equals((Object)CaliberApplyType.FILTER)) continue;
                result.add(info);
            }
        });
        return result;
    }

    public String getReportFilter() {
        return this.reportModel.getConfig().getFilter();
    }

    public FilterInfo getFilterInfoByRow(Integer row) {
        List<FilterInfo> rowFilters = this.reportModel.getConfig().getRowFilters();
        if (CollectionUtils.isEmpty(rowFilters)) {
            return null;
        }
        for (FilterInfo info : rowFilters) {
            if (info.getPosition() != row.intValue()) continue;
            return info;
        }
        return null;
    }

    public FilterInfo getFilterInfoByCol(Integer col) {
        List<FilterInfo> colFilters = this.reportModel.getConfig().getColFilters();
        if (CollectionUtils.isEmpty(colFilters)) {
            return null;
        }
        for (FilterInfo info : colFilters) {
            if (info.getPosition() != col.intValue()) continue;
            return info;
        }
        return null;
    }

    public String getDataCellKey(DataCell dataCell) {
        return dataCell.getKey();
    }

    public String getMainCellKey(MainCell mainCell) {
        String key = mainCell.getX() + "_" + mainCell.getY();
        return key;
    }
}

