/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 */
package com.jiuqi.nr.table.io.ncell;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nr.table.io.DataWriter;
import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.ncell.NCellWriteOptions;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import java.io.IOException;
import java.util.ArrayList;

public class NCellWriter
implements DataWriter<NCellWriteOptions> {
    private CellBook cellBook = new CellBook();

    @Override
    public void write(DataFrame<?> df, Destination dest) throws IOException {
        this.write(df, NCellWriteOptions.builder(dest).build());
    }

    @Override
    public void write(DataFrame<?> df, NCellWriteOptions options) {
        CellSheet sheet = this.cellBook.createSheet(options.getName(), options.getName(), df.rowSize(), df.columnSize());
        Index rows = df.rows();
        Index columns = df.columns();
        ArrayList<Object> rowLevels = new ArrayList<Object>(rows.levels());
        ArrayList<Object> colLevels = new ArrayList<Object>(columns.levels());
        for (int r = 0; r < df.rowSize(); ++r) {
            for (int c = 0; c < df.columnSize(); ++c) {
                Cell cell = sheet.getCell(r, c);
            }
        }
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }
}

