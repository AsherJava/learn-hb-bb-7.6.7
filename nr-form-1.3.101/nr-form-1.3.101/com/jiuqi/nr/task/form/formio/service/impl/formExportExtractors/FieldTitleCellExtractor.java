/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.form.common.FormExportVtEnum;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.AbstractFormExportCellExtractor;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.grid2.GridCellData;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FieldTitleCellExtractor
extends AbstractFormExportCellExtractor {
    public static final FormExportVtEnum VIEW_TYPE = FormExportVtEnum.TITLE;
    private IDesignTimeViewController designTimeViewController;
    private IEntityMetaService entityMetaService;

    public FieldTitleCellExtractor(IDesignTimeViewController designTimeViewController, IEntityMetaService entityMetaService) {
        this.designTimeViewController = designTimeViewController;
        this.entityMetaService = entityMetaService;
    }

    @Override
    public void setCellStyle(GridCellData gridCellData, XSSFCell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
        this.setDefaultStyle(gridCellData, cell, sheet, workbook);
    }

    @Override
    public void setCellText(String showText, DesignDataLinkDefine dataLinkDefine, DesignFieldDefine fieldDefine, Cell cell) {
        if (fieldDefine != null) {
            cell.setShowText(fieldDefine.getTitle());
        }
        if (dataLinkDefine != null && DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)dataLinkDefine.getType())) {
            DesignDataRegionDefine dataRegion = this.designTimeViewController.getDataRegion(dataLinkDefine.getRegionKey());
            IEntityModel model = this.getModel(dataRegion.getFormKey(), this.designTimeViewController, this.entityMetaService);
            IEntityAttribute attribute = model.getAttribute(dataLinkDefine.getLinkExpression());
            String name = attribute.getTitle();
            cell.setShowText(name);
        }
    }
}

