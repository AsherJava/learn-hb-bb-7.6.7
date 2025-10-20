/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.build.adjust;

import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingAxis;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.cache.DSPagingInfo;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.DSPageInfo;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PagingBuilder {
    private ReportContext context;
    private EngineWorksheet worksheet;
    private List<ExpandingArea> areas;

    public PagingBuilder(ReportContext context) {
        this.context = context;
        this.areas = new ArrayList<ExpandingArea>();
    }

    public void setWorksheet(EngineWorksheet worksheet) {
        this.worksheet = worksheet;
    }

    public List<ExpandingArea> getAreas() {
        return this.areas;
    }

    public void analyse() throws ReportBuildException {
        HashMap<String, ExpandingArea> pageInfos = new HashMap<String, ExpandingArea>();
        for (ExpandingArea area : this.areas) {
            DSPageInfo pageInfo = this.findPageInfo(area);
            if (pageInfo == null) continue;
            pageInfos.putIfAbsent(pageInfo.getDatasetName(), area);
        }
        this.context.validatePagingInfos(pageInfos.keySet());
    }

    private DSPageInfo findPageInfo(ExpandingArea area) {
        DSPageInfo pageInfo = this.findAxisPageInfo(area.getRowAxis());
        if (pageInfo != null) {
            return pageInfo;
        }
        return this.findAxisPageInfo(area.getColAxis());
    }

    private DSPageInfo findAxisPageInfo(ExpandingAxis axis) {
        if (axis.isEmpty()) {
            return null;
        }
        for (DSFieldNode primaryField : axis.getPrimaryFields()) {
            String dsName = primaryField.getDataSet().getName();
            Optional<DSPageInfo> pageInfo = this.context.getReport().getPageInfo().getDSPageInfos().stream().filter(pi -> pi.getDatasetName().equalsIgnoreCase(dsName)).findAny();
            if (!pageInfo.isPresent()) continue;
            return pageInfo.get();
        }
        return null;
    }

    public void build() throws ReportBuildException {
        this.areas.sort((a1, a2) -> a1.getRegion().leftTop().compareTo(a2.getRegion().leftTop()));
        for (ExpandingArea area : this.areas) {
            this.build(area);
        }
    }

    private void build(ExpandingArea area) throws ReportBuildException {
        DSPagingInfo pagingInfo = this.findPagingInfo(area.getRowAxis());
        if (pagingInfo == null) {
            pagingInfo = this.findPagingInfo(area.getColAxis());
        }
        if (pagingInfo == null) {
            return;
        }
        Region region = this.worksheet.getResultGrid().locateNewRegion(area.getRegion());
        pagingInfo.getRegions().add(region);
    }

    private DSPagingInfo findPagingInfo(ExpandingAxis axis) throws ReportBuildException {
        for (DSFieldNode primaryField : axis.getPrimaryFields()) {
            DSPagingInfo pagingInfo;
            try {
                pagingInfo = this.context.findPagingInfo(primaryField.getDataSet().getName());
            }
            catch (ReportContextException e) {
                throw new ReportBuildException(e);
            }
            if (pagingInfo == null) continue;
            return pagingInfo;
        }
        return null;
    }
}

