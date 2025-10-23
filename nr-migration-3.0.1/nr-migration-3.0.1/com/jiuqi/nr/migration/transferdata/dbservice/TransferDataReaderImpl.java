/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.fielddatacrud.spi.IDataReader
 */
package com.jiuqi.nr.migration.transferdata.dbservice;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import java.util.ArrayList;
import java.util.List;

public class TransferDataReaderImpl
implements IDataReader {
    private final List<IRowData> rowDatas = new ArrayList<IRowData>();

    public List<IRowData> getRowDatas() {
        return this.rowDatas;
    }

    public void start(List<IMetaData> metas, long totalCount) {
    }

    public void readRow(IRowData rowData) {
        this.rowDatas.add(rowData);
    }

    public void finish() {
    }
}

