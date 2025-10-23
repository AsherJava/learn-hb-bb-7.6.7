/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.PageSize
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.print.service.IExcelPrintSettingService;
import com.jiuqi.nr.print.web.vo.ExcelPrintSettingVO;
import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelPrintSettingServiceImpl
implements IExcelPrintSettingService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;

    @Override
    public ExcelPrintSettingVO get(String printSchemeKey, String formKey) {
        ExcelPrintSettingVO vo = null;
        DesignFormDefine formDefine = this.designTimeViewController.getForm(formKey);
        DesignPrintSettingDefine define = this.designTimePrintController.getPrintSettingDefine(printSchemeKey, formKey);
        if (null == define) {
            vo = new ExcelPrintSettingVO();
            vo.setPrintSchemeKey(printSchemeKey);
            vo.setFormKey(formKey);
        } else {
            vo = new ExcelPrintSettingVO(define);
        }
        HashMap<Integer, String> pageSizes = new HashMap<Integer, String>();
        for (PageSize pageSize : PageSize.values()) {
            pageSizes.put(pageSize.getValue(), pageSize.getTitle());
        }
        vo.setPageSizes(pageSizes);
        byte[] data = formDefine.getBinaryData();
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])data);
        this.initGrid(grid2Data);
        CellBook cellBook = CellBookInit.init();
        CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)grid2Data, (CellBook)cellBook, (String)formKey);
        vo.setCellBook(cellBook);
        return vo;
    }

    private void initGrid(Grid2Data gridData) {
        for (int c = 0; c < gridData.getColumnCount(); ++c) {
            for (int r = 0; r < gridData.getRowCount(); ++r) {
                GridCellData gridCellData = gridData.getGridCellData(c, r);
                gridCellData.setEditable(false);
            }
        }
        if (!gridData.isRowHidden(0) || !gridData.isColumnHidden(0)) {
            gridData.setRowHidden(0, true);
            gridData.setColumnHidden(0, true);
        }
    }

    @Override
    public void save(ExcelPrintSettingVO printSettingVO) {
        DesignPrintSettingDefine define = this.designTimePrintController.getPrintSettingDefine(printSettingVO.getPrintSchemeKey(), printSettingVO.getFormKey());
        if (null == define) {
            define = this.designTimePrintController.initPrintSetting();
            printSettingVO.value2Define(define);
            this.designTimePrintController.insertPrintSettingDefine(define);
        } else {
            printSettingVO.value2Define(define);
            this.designTimePrintController.updatePrintSettingDefine(define);
        }
    }

    @Override
    public ExcelPrintSettingVO reset(String printSchemeKey, String formKey) {
        this.designTimePrintController.deletePrintSettingDefine(printSchemeKey, formKey);
        return this.get(printSchemeKey, formKey);
    }

    @Override
    public void delete(String printSchemeKey) {
        this.designTimePrintController.deletePrintSettingDefine(printSchemeKey);
    }
}

