/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 */
package com.jiuqi.nr.data.excel.param.upload;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.excel.param.upload.ReportLinkDataCache;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import java.util.ArrayList;

public class RegionMatchInfo {
    private final DataRegionDefine region;
    private int preFixRectangle;
    private int matchStart;
    private int matchEnd;
    private int matchStartCol;
    private int matchEndCol;
    private String featureText;
    private int toFeatrueDistance;
    private int toEndDistance;
    private String errorInfo;

    public RegionMatchInfo(DataRegionDefine region, Grid2Data sampleGD, ReportLinkDataCache linkDataCache, int preAreaEnd) {
        this.region = region;
        if (DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() == this.region.getRegionKind().getValue()) {
            this.preFixRectangle = region.getRegionLeft() - preAreaEnd - 1;
            this.featureText = this.generateFeatureCol(sampleGD, linkDataCache, preAreaEnd);
        } else {
            this.preFixRectangle = region.getRegionTop() - preAreaEnd - 1;
            this.featureText = this.generateFeature(sampleGD, linkDataCache, preAreaEnd);
        }
    }

    private String generateFeature(Grid2Data sampleGD, ReportLinkDataCache linkDataCache, int preAreaEndRow) {
        ArrayList<RowSample> sampleRowFeatures = new ArrayList<RowSample>();
        for (int row = preAreaEndRow + 1; row <= this.region.getRegionTop(); ++row) {
            int col;
            StringBuilder rowSampleStr = new StringBuilder();
            for (col = 1; col < sampleGD.getColumnCount() && !linkDataCache.hasLinkData(row, col); ++col) {
                String txt;
                if (sampleGD.getGridCellData(col, row).isMerged()) {
                    Point firstMergeCellPoint = sampleGD.getGridCellData(col, row).getMergeInfo();
                    if (firstMergeCellPoint != null) continue;
                    txt = this.getCellText(sampleGD, row, col);
                    rowSampleStr.append(txt);
                    continue;
                }
                txt = this.getCellText(sampleGD, row, col);
                rowSampleStr.append(txt);
            }
            if (rowSampleStr.toString().trim().length() <= 0) continue;
            sampleRowFeatures.add(new RowSample(row, rowSampleStr.toString(), col));
        }
        String credibleFeature = null;
        int reliability = -1;
        int credibleRowIndex = -1;
        for (RowSample rowSample : sampleRowFeatures) {
            if (reliability >= rowSample.cellCount) continue;
            reliability = rowSample.cellCount;
            credibleFeature = rowSample.sampleText;
            credibleRowIndex = rowSample.rowIndex;
        }
        if (reliability == -1) {
            this.errorInfo = String.format("\u4e0d\u652f\u6301\u7684\u6d6e\u52a8\u533a\u57df\u8868\u6837\uff1a\u6d6e\u52a8\u533a\u57df%s[%s]\u4e0d\u80fd\u751f\u6210\u7279\u5f81\u4fe1\u606f\u3002", this.region.getKey(), this.region.getTitle());
        } else {
            this.toFeatrueDistance = this.region.getRegionTop() - credibleRowIndex;
        }
        return credibleFeature;
    }

    private String generateFeatureCol(Grid2Data sampleGD, ReportLinkDataCache linkDataCache, int preAreaEndCol) {
        ArrayList<ColSample> sampleColFeatures = new ArrayList<ColSample>();
        for (int col = preAreaEndCol + 1; col <= this.region.getRegionLeft(); ++col) {
            int row;
            StringBuilder colSampleStr = new StringBuilder();
            for (row = 1; row < sampleGD.getRowCount() && !linkDataCache.hasLinkData(row, col); ++row) {
                String txt;
                if (sampleGD.getGridCellData(col, row).isMerged()) {
                    Point firstMergeCellPoint = sampleGD.getGridCellData(col, row).getMergeInfo();
                    if (firstMergeCellPoint != null) continue;
                    txt = this.getCellText(sampleGD, row, col);
                    colSampleStr.append(txt);
                    continue;
                }
                txt = this.getCellText(sampleGD, row, col);
                colSampleStr.append(txt);
            }
            if (colSampleStr.toString().trim().length() <= 0) continue;
            sampleColFeatures.add(new ColSample(col, colSampleStr.toString(), row));
        }
        String credibleFeature = null;
        int reliability = -1;
        int credibleColIndex = -1;
        for (ColSample colSample : sampleColFeatures) {
            if (reliability >= colSample.cellCount) continue;
            reliability = colSample.cellCount;
            credibleFeature = colSample.sampleText;
            credibleColIndex = colSample.colIndex;
        }
        if (reliability == -1) {
            this.errorInfo = String.format("\u4e0d\u652f\u6301\u7684\u6d6e\u52a8\u533a\u57df\u8868\u6837\uff1a\u6d6e\u52a8\u533a\u57df%s[%s]\u4e0d\u80fd\u751f\u6210\u7279\u5f81\u4fe1\u606f\u3002", this.region.getKey(), this.region.getTitle());
        } else {
            this.toFeatrueDistance = this.region.getRegionLeft() - credibleColIndex;
        }
        return credibleFeature;
    }

    private String getCellText(Grid2Data gridData, int row, int col) {
        GridCellData cell;
        if (gridData != null && StringUtils.isNotEmpty((String)(cell = gridData.getGridCellData(col, row)).getShowText())) {
            return cell.getShowText();
        }
        return "";
    }

    public DataRegionDefine getRegion() {
        return this.region;
    }

    public String getFeatureText() {
        return this.featureText;
    }

    public int getMatchStart() {
        return this.matchStart;
    }

    public void setMatchStart(int matchStart) {
        this.matchStart = matchStart;
    }

    public int getMatchEnd() {
        return this.matchEnd;
    }

    public void setMatchEnd(int matchEnd) {
        this.matchEnd = matchEnd;
    }

    public int getPreFixRectangle() {
        return this.preFixRectangle;
    }

    public int getFeatureDistance() {
        return this.toFeatrueDistance;
    }

    public void setEndDistance(int endDistance) {
        this.toEndDistance = endDistance;
    }

    public int getEndDistance() {
        return this.toEndDistance;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public int getMatchStartCol() {
        return this.matchStartCol;
    }

    public void setMatchStartCol(int matchStartCol) {
        this.matchStartCol = matchStartCol;
    }

    public int getMatchEndCol() {
        return this.matchEndCol;
    }

    public void setMatchEndCol(int matchEndCol) {
        this.matchEndCol = matchEndCol;
    }

    private class ColSample {
        final String sampleText;
        final int cellCount;
        final int colIndex;

        ColSample(int colIndex, String sampleText, int cellCount) {
            this.sampleText = sampleText;
            this.cellCount = cellCount;
            this.colIndex = colIndex;
        }
    }

    private class RowSample {
        final String sampleText;
        final int cellCount;
        final int rowIndex;

        RowSample(int rowIndex, String sampleText, int cellCount) {
            this.sampleText = sampleText;
            this.cellCount = cellCount;
            this.rowIndex = rowIndex;
        }
    }
}

