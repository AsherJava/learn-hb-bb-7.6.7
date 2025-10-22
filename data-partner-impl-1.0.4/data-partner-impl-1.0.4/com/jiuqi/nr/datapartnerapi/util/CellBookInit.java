/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.constant.CellBorderStyle
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.datapartnerapi.util;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.model.CellBook;

public class CellBookInit {
    public static CellBook init() {
        CellBook cellBook = new CellBook();
        cellBook.getBookStyle().getDefaultStyle().setRightBorderStyle(CellBorderStyle.THIN);
        cellBook.getBookStyle().getDefaultStyle().setRightBorderColor("D1D1D1");
        cellBook.getBookStyle().getDefaultStyle().setBottomBorderStyle(CellBorderStyle.THIN);
        cellBook.getBookStyle().getDefaultStyle().setBottomBorderColor("D1D1D1");
        return cellBook;
    }
}

