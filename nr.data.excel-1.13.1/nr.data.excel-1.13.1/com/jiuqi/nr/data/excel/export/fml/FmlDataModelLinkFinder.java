/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportColumnLinkFinder
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.data.excel.export.fml.Fml;
import com.jiuqi.nr.data.excel.export.fml.FmlContext;
import com.jiuqi.nr.data.excel.export.fml.FmlNode;
import com.jiuqi.nr.data.excel.export.grid.GridArea;
import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportColumnLinkFinder;
import java.util.List;

public class FmlDataModelLinkFinder
extends ReportColumnLinkFinder {
    private Fml fml;
    private final FmlContext fmlContext;

    public FmlDataModelLinkFinder(IRunTimeViewController controller, String formSchemeKey, Fml fml, FmlContext fmlContext) {
        super(controller, formSchemeKey);
        this.fml = fml;
        this.fmlContext = fmlContext;
    }

    public void setFml(Fml fml) {
        this.fml = fml;
    }

    public Region getCellRegion(ExecutorContext context, DataModelLinkColumn column) {
        int regionRight;
        int regionLeft;
        int regionBottom;
        int regionTop;
        if (column == null || column.getReportInfo() == null || StringUtils.isEmpty((String)column.getReportInfo().getReportKey())) {
            return super.getCellRegion(context, column);
        }
        String dataLinkCode = column.getDataLinkCode();
        String formKey = column.getReportInfo().getReportKey();
        DataLinkDefine dataLinkDefine = this.fmlContext.getRunTimeViewController().queryDataLinkDefineByUniquecode(formKey, dataLinkCode);
        if (dataLinkDefine == null) {
            return super.getCellRegion(context, column);
        }
        String dataLinkKey = dataLinkDefine.getKey();
        FmlNode fmlNodeByDataLink = this.fml.getFmlNodeByDataLink(dataLinkKey);
        if (fmlNodeByDataLink == null) {
            return super.getCellRegion(context, column);
        }
        DataRegionKind dataRegionKind = fmlNodeByDataLink.getDataRegionKind();
        if (dataRegionKind == DataRegionKind.DATA_REGION_SIMPLE) {
            return super.getCellRegion(context, column);
        }
        GridAreaInfo gridAreaInfo = this.fmlContext.getGridAreaInfo(formKey);
        List<Integer> regionIndex = gridAreaInfo.getRegionAreaIndexMap().get(fmlNodeByDataLink.getRegionKey());
        if (regionIndex == null || regionIndex.size() != 1) {
            return super.getCellRegion(context, column);
        }
        Integer index = regionIndex.get(0);
        GridArea gridArea = gridAreaInfo.getGridAreaList().get(index);
        if (gridArea.getRowSpan() > 1) {
            return super.getCellRegion(context, column);
        }
        int posX = fmlNodeByDataLink.getPosX();
        int posY = fmlNodeByDataLink.getPosY();
        if (dataRegionKind == DataRegionKind.DATA_REGION_COLUMN_LIST) {
            int rowAppend = gridArea.getTop() - gridArea.getOriginalTop();
            regionBottom = regionTop = posY + rowAppend;
            regionLeft = gridArea.getLeft();
            regionRight = gridArea.getRight();
        } else {
            regionTop = gridArea.getTop();
            regionBottom = gridArea.getBottom();
            regionRight = regionLeft = posX;
        }
        return new Region(regionLeft, regionTop, regionRight, regionBottom);
    }
}

