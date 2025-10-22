/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.io.MemStream2
 *  com.jiuqi.nvwa.grid2.io.ReadMemStream2
 *  com.jiuqi.nvwa.grid2.io.Stream2
 */
package com.jiuqi.nr.splittable.util;

import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.io.MemStream2;
import com.jiuqi.nvwa.grid2.io.ReadMemStream2;
import com.jiuqi.nvwa.grid2.io.Stream2;

public class GridCellDataUtil {
    public static GridCellData cellDataClone(GridCellData oldCellData) {
        GridCellData newCellData = new GridCellData(oldCellData.getColIndex(), oldCellData.getRowIndex());
        MemStream2 store = new MemStream2();
        ReadMemStream2 stream = new ReadMemStream2();
        newCellData.copyCellData(oldCellData);
        try {
            oldCellData.saveToStream((Stream2)store, oldCellData.getRowIndex(), oldCellData.getColIndex());
            byte[] bytes = store.getBytes();
            stream.writeBuffer(bytes, 0, bytes.length);
            stream.setPosition(0L);
            newCellData.loadFromStream((Stream2)stream, oldCellData.getRowIndex(), oldCellData.getColIndex());
        }
        catch (Exception exception) {
            // empty catch block
        }
        newCellData.setMergeInfo(oldCellData.getMergeInfo());
        return newCellData;
    }
}

