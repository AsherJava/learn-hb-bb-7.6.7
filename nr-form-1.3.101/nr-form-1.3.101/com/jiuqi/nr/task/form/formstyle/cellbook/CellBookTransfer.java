/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.converter.ICellBookGrid2DataConverterProvider
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.task.form.formstyle.cellbook;

import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nr.task.form.formstyle.cellbook.transfer.SheetTransferProvider;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.converter.ICellBookGrid2DataConverterProvider;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class CellBookTransfer {
    final SheetTransferProvider sheetTransferProvider = new SheetTransferProvider();

    public Grid2Data transfer(CellBook cellBook) {
        Grid2Data grid2Data = new Grid2Data();
        List sheets = cellBook.getSheets();
        if (CollectionUtils.isEmpty(sheets)) {
            return grid2Data;
        }
        CellSheet sheet = (CellSheet)sheets.get(0);
        CellBookGrid2dataConverter.cellBookToGrid2Data((CellSheet)sheet, (Grid2Data)grid2Data, (ICellBookGrid2DataConverterProvider)this.sheetTransferProvider);
        return grid2Data;
    }

    public CellBook transfer(Grid2Data grid2Data, String sheetCode) {
        boolean hiddenSerialNumberHeader = false;
        if (!grid2Data.isRowHidden(0) || !grid2Data.isColumnHidden(0)) {
            grid2Data.setRowHidden(0, true);
            grid2Data.setColumnHidden(0, true);
        } else {
            hiddenSerialNumberHeader = true;
        }
        CellBook cellBook = CellBookInit.init();
        CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)grid2Data, (CellBook)cellBook, (ICellBookGrid2DataConverterProvider)this.sheetTransferProvider, (String)sheetCode);
        ((CellSheet)cellBook.getSheets().get(0)).getOptions().setHiddenSerialNumberHeader(hiddenSerialNumberHeader);
        return cellBook;
    }
}

