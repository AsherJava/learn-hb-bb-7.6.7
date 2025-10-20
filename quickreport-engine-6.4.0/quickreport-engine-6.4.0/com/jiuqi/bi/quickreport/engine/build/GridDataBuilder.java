/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 */
package com.jiuqi.bi.quickreport.engine.build;

import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.FixedArea;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.adjust.AlternateColorProcessor;
import com.jiuqi.bi.quickreport.engine.build.adjust.PagingBuilder;
import com.jiuqi.bi.quickreport.engine.build.adjust.WorkSheetAdjustor;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingFragment;
import com.jiuqi.bi.quickreport.engine.build.fragment.FixedFragment;
import com.jiuqi.bi.quickreport.engine.build.fragment.GridFragment;
import com.jiuqi.bi.quickreport.engine.build.handler.DefaultCellHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.HyperlinkCellHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.NullCellHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.ZeroCellHandler;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayer;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GridDataBuilder {
    private EngineWorkbook workbook;
    private List<CalcLayer> calcLayers = new ArrayList<CalcLayer>();
    private ReportContext context;
    private HyperlinkEnv hyperlinkEnv;
    private ICellContentHandler cellHandler;
    private boolean needMessageLink = true;
    private boolean needURLLink = true;
    private boolean needStyles = true;

    public EngineWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(EngineWorkbook workbook) {
        this.workbook = workbook;
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public List<CalcLayer> getCalcLayers() {
        return this.calcLayers;
    }

    public void setHyperlinkEnv(HyperlinkEnv hyperlinkEnv) {
        this.hyperlinkEnv = hyperlinkEnv;
    }

    public HyperlinkEnv getHyperlinkEnv() {
        return this.hyperlinkEnv;
    }

    public boolean needMessageLink() {
        return this.needMessageLink;
    }

    public void setNeedMessageLink(boolean needMessageLink) {
        this.needMessageLink = needMessageLink;
    }

    public boolean needURLLink() {
        return this.needURLLink;
    }

    public void setNeedURLLink(boolean needURLLink) {
        this.needURLLink = needURLLink;
    }

    public boolean needStyles() {
        return this.needStyles;
    }

    public void setNeedStyles(boolean needStyles) {
        this.needStyles = needStyles;
    }

    public void build() throws ReportBuildException {
        this.createCellHandler();
        this.execLayers();
        this.adjustSheets();
        this.buildPagingInfos();
    }

    private void createCellHandler() {
        this.cellHandler = new DefaultCellHandler(this.context.getLocale());
        switch (this.workbook.getReport().getNullConvertMode()) {
            case ASZERO: 
            case ASBLANK: {
                this.cellHandler = new NullCellHandler(this.cellHandler, this.workbook.getReport().getNullConvertMode());
                break;
            }
        }
        switch (this.workbook.getReport().getZeroConvertMode()) {
            case ASBLANK: 
            case ASNULL: {
                this.cellHandler = new ZeroCellHandler(this.cellHandler, this.workbook.getReport().getZeroConvertMode());
                break;
            }
        }
        if (this.needMessageLink || this.needURLLink) {
            this.cellHandler = new HyperlinkCellHandler(this.cellHandler, this.context, this.hyperlinkEnv).setNeedMessage(this.needMessageLink).setNeedURL(this.needURLLink);
        }
    }

    private void execLayers() throws ReportBuildException {
        this.hyperlinkEnv.beginBuiding(this.context);
        try {
            for (CalcLayer layer : this.calcLayers) {
                this.execLayer(layer);
            }
        }
        finally {
            this.hyperlinkEnv.endBuilding();
        }
    }

    private void execLayer(CalcLayer layer) throws ReportBuildException {
        for (GridArea area : layer.getAreas()) {
            try {
                this.workbook.setActiveWorksheet(area.getSheetName());
            }
            catch (ReportExpressionException e) {
                throw new ReportBuildException(e);
            }
            GridFragment fragment = this.createFragment(area);
            fragment.build();
        }
    }

    private GridFragment createFragment(GridArea area) throws ReportBuildException {
        GridFragment fragment;
        EngineWorksheet worksheet;
        try {
            worksheet = (EngineWorksheet)this.workbook.find(this.context, area.getSheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportBuildException(e);
        }
        if (area instanceof FixedArea) {
            fragment = new FixedFragment((FixedArea)area);
        } else if (area instanceof ExpandingArea) {
            fragment = new ExpandingFragment((ExpandingArea)area);
        } else {
            throw new ReportBuildException("\u672a\u77e5\u7684\u8868\u683c\u533a\u57df\u7c7b\u578b");
        }
        fragment.setContext(this.context);
        fragment.setWorksheet(worksheet);
        fragment.setCellHandler(this.cellHandler);
        return fragment;
    }

    private void adjustSheets() throws ReportBuildException {
        Map<String, List<GridArea>> sheetAreas = this.getSheetAreas();
        for (int i = 0; i < this.workbook.sheetSize(); ++i) {
            EngineWorksheet worksheet = (EngineWorksheet)this.workbook.getSheet(i);
            List<GridArea> areas = sheetAreas.get(worksheet.name().toUpperCase());
            this.adjustSheet(worksheet, areas);
            if (!this.needStyles || !this.workbook.getReport().getAlternateColor().isEnabled() || !worksheet.name().equalsIgnoreCase(this.workbook.getReport().getPrimarySheetName())) continue;
            this.alternateColor(worksheet);
        }
    }

    private Map<String, List<GridArea>> getSheetAreas() {
        HashMap<String, List<GridArea>> maps = new HashMap<String, List<GridArea>>();
        for (CalcLayer layer : this.calcLayers) {
            for (GridArea area : layer.getAreas()) {
                ArrayList<GridArea> areas = (ArrayList<GridArea>)maps.get(area.getSheetName().toUpperCase());
                if (areas == null) {
                    areas = new ArrayList<GridArea>();
                    maps.put(area.getSheetName().toUpperCase(), areas);
                }
                areas.add(area);
            }
        }
        return maps;
    }

    private void adjustSheet(EngineWorksheet worksheet, List<GridArea> areas) throws ReportBuildException {
        if (areas == null || areas.isEmpty()) {
            return;
        }
        WorkSheetAdjustor adjustor = new WorkSheetAdjustor();
        adjustor.setWorksheet(worksheet);
        adjustor.getAreas().addAll(areas);
        adjustor.adjust();
    }

    private void alternateColor(EngineWorksheet worksheet) throws ReportBuildException {
        new AlternateColorProcessor().config(this.workbook.getReport().getAlternateColor()).worksheet(worksheet).process();
    }

    private void buildPagingInfos() throws ReportBuildException {
        EngineWorksheet worksheet;
        if (this.context.getReport().getPageInfo().getPageMode() != PageMode.DATASET) {
            return;
        }
        PagingBuilder builder = new PagingBuilder(this.context);
        try {
            worksheet = (EngineWorksheet)this.workbook.find(this.context, this.context.getReport().getPrimarySheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportBuildException(e);
        }
        builder.setWorksheet(worksheet);
        this.calcLayers.forEach(layer -> layer.getAreas().stream().filter(area -> area.getSheetName().equalsIgnoreCase(this.context.getReport().getPrimarySheetName()) && area instanceof ExpandingArea).forEach(area -> builder.getAreas().add((ExpandingArea)area)));
        builder.build();
    }
}

