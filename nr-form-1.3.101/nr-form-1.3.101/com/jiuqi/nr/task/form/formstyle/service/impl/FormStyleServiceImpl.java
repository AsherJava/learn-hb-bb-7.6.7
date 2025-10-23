/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formstyle.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.formstyle.cellbook.CellBookTransfer;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.formstyle.service.IFormStyleService;
import com.jiuqi.nr.task.form.util.I18nNvwaToForm;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FormStyleServiceImpl
implements IFormStyleService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignBigDataService designBigDataService;

    @Override
    public void insertDefaultFormStyle(String formKey) {
        Assert.notNull((Object)formKey, "formKey\u4e0d\u80fd\u4e3a\u7a7a");
        this.designTimeViewController.insertFormStyle(formKey, this.createGrid2Data(19, 14, 28, 70));
    }

    @Override
    public void insertDefaultFMDMFormStyle(String formKey) {
        Assert.notNull((Object)formKey, "formKey\u4e0d\u80fd\u4e3a\u7a7a");
        this.designTimeViewController.insertFormStyle(formKey, this.createGrid2Data(21, 3, 28, 150));
    }

    @Override
    public void saveFormStyle(String formKey, FormStyleDTO formStyle) {
        if (Constants.DataStatus.MODIFY.equals((Object)formStyle.getStatus()) && Boolean.TRUE.equals(formStyle.isFormStyleChanged())) {
            CellBook cellBook = formStyle.getCellBook();
            CellBookTransfer transfer = new CellBookTransfer();
            Grid2Data grid2Data = transfer.transfer(cellBook);
            try {
                this.designBigDataService.updateBigDataDefine(formKey, "FORM_DATA", Grid2Data.gridToBytes((Grid2Data)grid2Data));
            }
            catch (Exception e) {
                throw new FormRuntimeException("\u66f4\u65b0\u8868\u6837\u5931\u8d25", e);
            }
        }
    }

    @Override
    public CellBook getFormStyle(String formKey, int language) {
        Grid2Data grid2Data = this.designTimeViewController.getFormStyle(formKey, language);
        if (I18nNvwaToForm.EN.getValue() == language && null == grid2Data) {
            grid2Data = this.designTimeViewController.getFormStyle(formKey, I18nNvwaToForm.CN.getValue());
        }
        CellBookTransfer transfer = new CellBookTransfer();
        return transfer.transfer(grid2Data, formKey);
    }

    private Grid2Data createGrid2Data(int row, int col, int rowHeight, int colWidth) {
        int i;
        Grid2Data grid2Data = new Grid2Data();
        grid2Data.setRowCount(row);
        grid2Data.setColumnCount(col);
        for (i = 0; i < row; ++i) {
            grid2Data.setRowHeight(i, rowHeight);
        }
        for (i = 1; i < col; ++i) {
            grid2Data.setColumnWidth(i, colWidth);
        }
        grid2Data.setColumnWidth(0, 36);
        for (int i2 = 0; i2 < row; ++i2) {
            for (int j = 0; j < col; ++j) {
                GridCellData cellData = grid2Data.getGridCellData(j, i2);
                cellData.setFontSize(14);
                cellData.setFontName("\u5b8b\u4f53");
                cellData.setForeGroundColor(Integer.parseInt("494949", 16));
            }
        }
        return grid2Data;
    }
}

