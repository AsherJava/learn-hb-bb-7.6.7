/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.common.param.ReadOnlyContext
 *  com.jiuqi.nr.data.common.service.IRegionDataLinkReadOnlyService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.common.param.ReadOnlyContext;
import com.jiuqi.nr.data.common.service.IRegionDataLinkReadOnlyService;
import com.jiuqi.nr.data.excel.param.upload.ReportLinkDataCache;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableStyleReadOnlyServiceImpl
implements IRegionDataLinkReadOnlyService {
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public Set<String> getReadOnlyDataLinks(ReadOnlyContext readOnlyContext) {
        HashSet<String> readOnlyDataLinks = new HashSet<String>();
        String formKey = readOnlyContext.getFormKey();
        if (!StringUtils.isEmpty((String)formKey)) {
            ReportLinkDataCache linkDataCache = new ReportLinkDataCache();
            linkDataCache.init(this.runTimeViewController.getAllLinksInRegion(readOnlyContext.getRegionKey()));
            Grid2Data grid2Data = this.getGridData(formKey);
            for (int row = 0; row < grid2Data.getRowCount(); ++row) {
                for (int col = 0; col < grid2Data.getColumnCount(); ++col) {
                    DataLinkDefine linkdata;
                    boolean isCellIsEdit;
                    GridCellData gridCellData = grid2Data.getGridCellData(col, row);
                    boolean bl = isCellIsEdit = gridCellData == null || gridCellData.getCellStyleData().isEditable();
                    if (isCellIsEdit || (linkdata = linkDataCache.getLinkData(row, col)) == null) continue;
                    readOnlyDataLinks.add(linkdata.getKey());
                }
            }
        }
        return readOnlyDataLinks;
    }

    public boolean isAllOrgShare(ReadOnlyContext readOnlyContext) {
        return true;
    }

    public Grid2Data getGridData(String formKey) {
        BigDataDefine formDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runTimeViewController.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }
}

