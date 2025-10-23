/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.task.form.common.FormExportVtEnum;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.AbstractFormExportCellExtractor;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.HashMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GatherTypeCellExtractor
extends AbstractFormExportCellExtractor {
    public static final FormExportVtEnum VIEW_TYPE = FormExportVtEnum.GATHER_TYPE;
    public static final HashMap<Integer, String> gatherTypeMap = new HashMap();

    @Override
    public void setCellStyle(GridCellData gridCellData, XSSFCell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
        this.setDefaultStyle(gridCellData, cell, sheet, workbook);
    }

    @Override
    public void setCellText(String showText, DesignDataLinkDefine dataLinkDefine, DesignFieldDefine fieldDefine, Cell cell) {
        if (fieldDefine != null) {
            cell.setShowText(gatherTypeMap.get(fieldDefine.getGatherType().getValue()));
        }
        if (dataLinkDefine != null && DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)dataLinkDefine.getType())) {
            cell.setShowText("\u4e0d\u6c47\u603b");
        }
    }

    static {
        gatherTypeMap.put(0, "\u4e0d\u6c47\u603b");
        gatherTypeMap.put(1, "\u7d2f\u52a0\u6c47\u603b");
        gatherTypeMap.put(2, "\u8ba1\u6570\u6c47\u603b");
        gatherTypeMap.put(3, "\u5e73\u5747\u503c\u6c47\u603b");
        gatherTypeMap.put(4, "\u6700\u5c0f\u503c\u6c47\u603b");
        gatherTypeMap.put(5, "\u6700\u5927\u503c\u6c47\u603b");
        gatherTypeMap.put(6, "\u91cd\u7b97\u6307\u6807\u8ba1\u7b97\u8868\u8fbe\u5f0f");
        gatherTypeMap.put(7, "\u53bb\u91cd\u8ba1\u6570\u6c47\u603b");
        gatherTypeMap.put(8, "\u7f57\u5217\u6c47\u603b");
        gatherTypeMap.put(9, "\u53bb\u91cd\u7f57\u5217\u6c47\u603b");
        gatherTypeMap.put(10, "\u672b\u7ea7\u7f57\u5217\u6c47\u603b");
    }
}

