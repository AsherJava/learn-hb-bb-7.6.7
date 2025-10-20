/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.context.selection;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.selection.AllFilter;
import com.jiuqi.bi.quickreport.engine.context.selection.CellFilter;
import com.jiuqi.bi.quickreport.engine.context.selection.IPositionFilter;
import com.jiuqi.bi.quickreport.engine.context.selection.RegionFilter;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.ArrayList;
import java.util.List;

public class CellSelection {
    private List<IPositionFilter> filters = new ArrayList<IPositionFilter>();

    public boolean contains(SheetPosition position) {
        return this.filters.stream().anyMatch(f -> f.enabled(position));
    }

    public boolean isEmpty() {
        return this.filters.isEmpty();
    }

    public void parse(ReportContext context, String config) throws ReportExpressionException {
        this.filters.clear();
        if (config == null || config.isEmpty()) {
            return;
        }
        for (String item : config.split("[,;]")) {
            String filter;
            if (item == null || (filter = item.trim()).isEmpty()) continue;
            if ("ALL".equalsIgnoreCase(filter)) {
                this.filters.clear();
                this.filters.add(new AllFilter());
                break;
            }
            try {
                this.parseFilter(context, filter);
            }
            catch (NumberFormatException e) {
                throw new ReportExpressionException("\u5355\u5143\u683c\u8303\u56f4\u6307\u5b9a\u9519\u8bef\uff1a" + filter, e);
            }
        }
    }

    private void parseFilter(ReportContext context, String filter) {
        String content;
        String sheetName;
        int p = filter.indexOf(33);
        if (p >= 0) {
            sheetName = this.getSheetName(context, filter.substring(0, p));
            content = filter.substring(p + 1);
        } else {
            sheetName = this.getSheetName(context, null);
            content = filter;
        }
        if (content.contains(":")) {
            Region region = Region.createRegion((String)content);
            this.filters.add(new RegionFilter(sheetName, region));
        } else {
            Position position = Position.valueOf((String)content);
            this.filters.add(new CellFilter(sheetName, position));
        }
    }

    private String getSheetName(ReportContext context, String sheetName) {
        if (sheetName == null || sheetName.isEmpty()) {
            return context.getReport().getPrimarySheetName();
        }
        if (context.getReport().getWorksheets().stream().anyMatch(sheet -> sheetName.equalsIgnoreCase(sheet.getName()))) {
            return sheetName;
        }
        return null;
    }

    public String toString() {
        return this.filters.toString();
    }
}

