/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.task.form.common.FormExportVtEnum;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.AbstractFormExportCellExtractor;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.grid2.GridCellData;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DefaultCellExtractor
extends AbstractFormExportCellExtractor {
    public static final FormExportVtEnum VIEW_TYPE = FormExportVtEnum.DEFAULT;

    @Override
    public void setCellStyle(GridCellData gridCellData, XSSFCell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
        this.setDefaultStyle(gridCellData, cell, sheet, workbook);
    }

    @Override
    public void setCellText(String showText, DesignDataLinkDefine dataLinkDefine, DesignFieldDefine fieldDefine, Cell cell) {
        cell.setShowText(showText);
    }
}

