/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.memdb.api.DBIterator
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.bdf.BlockDataFile
 */
package com.jiuqi.nr.tds.bdf;

import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.FileChecker;
import com.jiuqi.nr.tds.FileIOException;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.bdf.BlockFileColumn;
import com.jiuqi.nr.tds.bdf.BlockFileModel;
import com.jiuqi.nr.tds.bdf.DbRowData;
import com.jiuqi.nr.tds.impl.BaseReader;
import com.jiuqi.nvwa.memdb.api.DBIterator;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.bdf.BlockDataFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockFileReader
extends BaseReader {
    private BlockDataFile blockDataFile;
    private DBIterator itr;
    private Map<String, Integer> name2Index;
    private BlockFileModel blockFileModel;

    @Override
    public void open(File file, String tableName) throws IOException {
        if (FileChecker.isCompressedFileByHeader(file)) {
            super.open(file, tableName);
            this.blockFileModel = (BlockFileModel)Costs.MAPPER.readValue(new File(this.tempDir + "model.json"), BlockFileModel.class);
            this.blockDataFile = new BlockDataFile(new File(this.tempDir), tableName, true);
            this.blockDataFile.open();
            this.itr = this.blockDataFile.iterator();
            this.name2Index = new HashMap<String, Integer>();
            int index = 0;
            for (BlockFileColumn column : this.blockFileModel.getColumns()) {
                this.name2Index.put(column.getName(), index++);
            }
        } else {
            throw new FileIOException("\u5f53\u524d\u6587\u4ef6\u975eTDS\u6587\u4ef6,\u8bf7\u68c0\u67e5\u6587\u4ef6\u662f\u5426\u6b63\u786e");
        }
    }

    @Override
    public List<TdColumn> columns() {
        List<BlockFileColumn> bfColumns = this.blockFileModel.getColumns();
        return new ArrayList<TdColumn>(bfColumns);
    }

    @Override
    public boolean hasNext() {
        if (this.itr == null) {
            return false;
        }
        return this.itr.hasNext();
    }

    @Override
    public TdRowData next() {
        if (this.itr == null) {
            return null;
        }
        DBRecord next = (DBRecord)this.itr.next();
        return new DbRowData(next, this.blockFileModel, this.name2Index);
    }

    @Override
    public void close() throws IOException {
        this.itr = null;
        if (this.blockDataFile != null) {
            this.blockDataFile.close();
        }
    }
}

