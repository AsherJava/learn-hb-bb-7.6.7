/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.data.excel.param.upload;

import com.jiuqi.np.graphics.Point;
import com.jiuqi.nr.data.excel.param.upload.ReportLinkDataCache;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFeature {
    private Map<Point, FeatureCode> features = new HashMap<Point, FeatureCode>();

    public ReportFeature(Grid2Data sampleData) {
        this(sampleData, null);
    }

    public ReportFeature(Grid2Data gridData, ReportLinkDataCache linkDataCache) {
        this.generateReportFeature(gridData, linkDataCache);
    }

    private void generateReportFeature(Grid2Data gridData, ReportLinkDataCache linkDataCache) {
        Grid2FieldList fieldList = gridData.merges();
        int colCount = gridData.getColumnCount();
        int rowCount = gridData.getRowCount();
        if (rowCount > 1 && colCount > 1) {
            for (int col = 1; col < colCount; ++col) {
                FeatureCode fc = new FeatureCode(1, col);
                if (gridData.getGridCellData(col, 1).isMerged()) {
                    fc.setMerge(true);
                }
                if (linkDataCache != null && linkDataCache.hasLinkData(1, col)) {
                    fc.setText("null");
                } else {
                    GridCellData cell = gridData.getGridCellData(col, 1);
                    if (fc.isMerge && null != fieldList) {
                        for (int i = 0; i < fieldList.count(); ++i) {
                            Grid2CellField field = fieldList.get(i);
                            if (col > field.right || col < field.left || 1 > field.bottom || 1 < field.top) continue;
                            cell = gridData.getGridCellData(field.left, field.top);
                            break;
                        }
                    }
                    fc.setText(cell.getShowText() == null ? "" : cell.getShowText());
                }
                this.features.put(new Point(1, col), fc);
            }
        }
    }

    public List<FeatureCode> getFeatures() {
        ArrayList<FeatureCode> featureCodeList = new ArrayList<FeatureCode>();
        featureCodeList.addAll(this.features.values());
        return featureCodeList;
    }

    public String getCellText(int row, int col) {
        Point p = new Point(row, col);
        FeatureCode featureCode = this.features.get(p);
        if (featureCode != null) {
            return featureCode.text;
        }
        return null;
    }

    public boolean isMergeCell(int row, int col) {
        Point p = new Point(row, col);
        FeatureCode featureCode = this.features.get(p);
        if (featureCode != null) {
            return featureCode.isMerge;
        }
        return false;
    }

    class FeatureCode {
        public int row;
        public int col;
        public String text;
        public boolean isMerge;

        public FeatureCode(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setMerge(boolean isMerge) {
            this.isMerge = isMerge;
        }
    }
}

