/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.build.adjust;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.AlternateColor;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AlternateColorProcessor {
    private AlternateColor alternateColor;
    private EngineWorksheet worksheet;
    private static final String ERR_RANGE = "\u4ea4\u66ff\u8272\u533a\u57df\u8303\u56f4\u6307\u5b9a\u9519\u8bef\uff1a";

    public AlternateColorProcessor config(AlternateColor alternateColor) {
        this.alternateColor = alternateColor;
        return this;
    }

    public AlternateColorProcessor worksheet(EngineWorksheet worksheet) {
        this.worksheet = worksheet;
        return this;
    }

    public void process() throws ReportBuildException {
        if (!this.alternateColor.isEnabled()) {
            return;
        }
        List<Region> regions = this.parseRegions();
        if (regions.isEmpty()) {
            return;
        }
        List<RGBA> colors = this.parseColors();
        if (colors.isEmpty()) {
            return;
        }
        for (Region region : regions) {
            if (region.isRowMode()) {
                this.colorRows(region, colors);
                continue;
            }
            if (!region.isColMode()) continue;
            this.colorCols(region, colors);
        }
    }

    private List<Region> parseRegions() throws ReportBuildException {
        if (this.alternateColor.getRange() == null || this.alternateColor.getRange().isEmpty()) {
            return Collections.emptyList();
        }
        String[] ranges = this.alternateColor.getRange().split(",;");
        ArrayList<Region> regions = new ArrayList<Region>(ranges.length);
        for (String range : ranges) {
            if (range == null || (range = range.trim()).isEmpty()) continue;
            Region region = this.parseRegion(range);
            regions.add(region);
        }
        return regions;
    }

    private Region parseRegion(String range) throws ReportBuildException {
        try {
            if (range.contains(":")) {
                Region region = Region.createRegion((String)range);
                if (!region.isValidate() || !region.isColMode() && !region.isRowMode()) {
                    throw new ReportBuildException(ERR_RANGE + range);
                }
                return region;
            }
            Position pos = Position.valueOf((String)range);
            if (!pos.isValidate() || !pos.isEntireCol() && !pos.isEntireRow()) {
                throw new ReportBuildException(ERR_RANGE + range);
            }
            return new Region(pos, pos);
        }
        catch (NumberFormatException e) {
            throw new ReportBuildException(ERR_RANGE + range, e);
        }
    }

    private List<RGBA> parseColors() throws ReportBuildException {
        ArrayList<RGBA> colors = new ArrayList<RGBA>(this.alternateColor.getColors().size());
        for (String color : this.alternateColor.getColors()) {
            RGBA rgba;
            try {
                rgba = RGBA.parse(color);
            }
            catch (NumberFormatException e) {
                throw new ReportBuildException("\u6307\u5b9a\u4ea4\u66ff\u8272\u683c\u5f0f\u9519\u8bef\uff1a" + color, e);
            }
            colors.add(rgba);
        }
        return colors;
    }

    private void colorRows(Region region, List<RGBA> colors) throws ReportBuildException {
        ResultGridData resultGrid = this.worksheet.getResultGrid();
        int head = resultGrid.locateNewRow(region.top());
        int tail = resultGrid.locateLastRow(region.bottom());
        RGBAIterator i = new RGBAIterator(colors);
        for (int row = head; row <= tail; ++row) {
            RGBA color = (RGBA)i.next();
            for (int col = 1; col < resultGrid.getGridData().getColCount(); ++col) {
                if (!resultGrid.getGridData().isValidCell(col, row)) continue;
                this.colorCell(resultGrid.getGridData(), col, row, color);
            }
        }
    }

    private void colorCols(Region region, List<RGBA> colors) throws ReportBuildException {
        ResultGridData resultGrid = this.worksheet.getResultGrid();
        int head = resultGrid.locateNewCol(region.left());
        int tail = resultGrid.locateLastCol(region.right());
        RGBAIterator i = new RGBAIterator(colors);
        for (int col = head; col <= tail; ++col) {
            RGBA color = (RGBA)i.next();
            for (int row = 1; row < resultGrid.getGridData().getRowCount(); ++row) {
                if (!resultGrid.getGridData().isValidCell(col, row)) continue;
                this.colorCell(resultGrid.getGridData(), col, row, color);
            }
        }
    }

    private void colorCell(GridData grid, int col, int row, RGBA color) {
        CellValue value = (CellValue)grid.getObj(col, row);
        if (value != null && value.styleValue != null && value.styleValue.isBackColorChanged()) {
            return;
        }
        GridCell cell = grid.getCell(col, row);
        cell.setBackColor(color.color);
        cell.setBackAlpha(color.alpha);
        cell.setBackStyle(1);
        grid.setCell(cell);
    }

    private static final class RGBAIterator
    implements Iterator<RGBA> {
        private int index;
        private List<RGBA> colors;

        public RGBAIterator(List<RGBA> colors) {
            this.colors = colors;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public RGBA next() {
            RGBA color = this.colors.get(this.index);
            ++this.index;
            if (this.index >= this.colors.size()) {
                this.index = 0;
            }
            return color;
        }
    }

    private static class RGBA {
        public final int color;
        public final int alpha;

        public RGBA(int color, int alpha) {
            this.color = color;
            this.alpha = alpha;
        }

        public static RGBA parse(String text) throws NumberFormatException {
            String upperText = text.trim().toUpperCase();
            if (upperText.length() <= 4) {
                throw new NumberFormatException();
            }
            if (!upperText.startsWith("0X")) {
                throw new NumberFormatException("\u989c\u8272\u503c\u8868\u8fbe\u5f0f\u672a\u4ee5'0x'\u5f00\u5934");
            }
            int color = Integer.parseInt(upperText.substring(2, upperText.length() - 2), 16);
            int alpha = Integer.parseInt(upperText.substring(upperText.length() - 2), 16);
            return new RGBA(color, alpha);
        }
    }
}

