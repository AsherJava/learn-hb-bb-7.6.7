/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.temptable.ITempTable
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.common.temptable.ITempTable;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class TempTableReturnInfo
implements Closeable {
    List<String> sourceColName;
    List<String> targetColName;
    ITempTable tempTable;

    public TempTableReturnInfo(ITempTable tempTable, List<String> sourceColName, List<String> targetColName) {
        this.sourceColName = sourceColName;
        this.targetColName = targetColName;
        this.tempTable = tempTable;
    }

    public List<String> getSourceColName() {
        return this.sourceColName;
    }

    public List<String> getTargetColName() {
        return this.targetColName;
    }

    public ITempTable getTempTable() {
        return this.tempTable;
    }

    @Override
    public void close() throws IOException {
        if (this.tempTable != null) {
            this.tempTable.close();
        }
    }
}

