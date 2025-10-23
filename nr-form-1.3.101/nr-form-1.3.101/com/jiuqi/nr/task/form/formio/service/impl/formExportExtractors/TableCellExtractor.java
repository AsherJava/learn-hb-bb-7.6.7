/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.task.form.common.FormExportVtEnum;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.AbstractFormExportCellExtractor;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.grid2.GridCellData;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TableCellExtractor
extends AbstractFormExportCellExtractor {
    public static final FormExportVtEnum VIEW_TYPE = FormExportVtEnum.TABLE;
    private NRDesignTimeController nrDesignTimeController;

    @Override
    public void setCellStyle(GridCellData gridCellData, XSSFCell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
        this.setDefaultStyle(gridCellData, cell, sheet, workbook);
    }

    @Override
    public void setCellText(String showText, DesignDataLinkDefine dataLinkDefine, DesignFieldDefine fieldDefine, Cell cell) {
        if (fieldDefine != null) {
            DesignTableDefine tableDefine;
            try {
                this.nrDesignTimeController = (NRDesignTimeController)BeanUtil.getBean(NRDesignTimeController.class);
                tableDefine = this.nrDesignTimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
            }
            catch (JQException e) {
                throw new RuntimeException(e);
            }
            if (tableDefine != null) {
                showText = tableDefine.getCode() + "[" + fieldDefine.getCode() + "]";
            }
            cell.setShowText(showText);
        }
        if (dataLinkDefine != null && DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)dataLinkDefine.getType())) {
            cell.setShowText(dataLinkDefine.getLinkExpression());
        }
    }
}

