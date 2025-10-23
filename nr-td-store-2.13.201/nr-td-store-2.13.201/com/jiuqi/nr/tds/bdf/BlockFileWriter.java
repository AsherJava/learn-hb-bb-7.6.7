/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.model.TableModel
 *  com.jiuqi.nvwa.memdb.api.record.ArrayRecord
 *  com.jiuqi.nvwa.memdb.bdf.BlockDataFile
 *  com.jiuqi.nvwa.memdb.bdf.field.BDFColumnInfo
 */
package com.jiuqi.nr.tds.bdf;

import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.FileIOException;
import com.jiuqi.nr.tds.PackageData;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.api.DataTableWriter;
import com.jiuqi.nr.tds.api.TdStoreFactory;
import com.jiuqi.nr.tds.bdf.BlockFileColumn;
import com.jiuqi.nr.tds.bdf.BlockFileModel;
import com.jiuqi.nr.tds.impl.BaseWriter;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.model.TableModel;
import com.jiuqi.nvwa.memdb.api.record.ArrayRecord;
import com.jiuqi.nvwa.memdb.bdf.BlockDataFile;
import com.jiuqi.nvwa.memdb.bdf.field.BDFColumnInfo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockFileWriter
extends BaseWriter {
    private final BlockDataFile blockDataFile;
    private static final Logger logger = LoggerFactory.getLogger(BlockFileWriter.class);

    public BlockFileWriter(File dataDir, TdModel tdModel) {
        super(dataDir, tdModel.getName());
        Costs.createPathIfNotExists(dataDir.toPath());
        BlockFileModel blockFileModel = new BlockFileModel(tdModel);
        try {
            File modelFile = new File(dataDir, "model.json");
            byte[] bytes = Costs.MAPPER.writeValueAsBytes((Object)blockFileModel);
            Files.write(modelFile.toPath(), bytes, new OpenOption[0]);
            File packageFile = new File(dataDir, "package.json");
            PackageData pkg = new PackageData();
            pkg.setVersion(TdStoreFactory.VERSION_1_0_0.toString());
            byte[] pkBytes = Costs.MAPPER.writeValueAsBytes((Object)pkg);
            Files.write(packageFile.toPath(), pkBytes, new OpenOption[0]);
        }
        catch (IOException e) {
            throw new FileIOException("\u751f\u6210\u6570\u636e\u6587\u4ef6\u5931\u8d25", e);
        }
        TableModel tableModel = new TableModel();
        tableModel.setName(tdModel.getName());
        for (BlockFileColumn column : blockFileModel.getColumns()) {
            tableModel.getColumns().add(column.toDBColumn());
        }
        this.blockDataFile = new BlockDataFile(dataDir, tableModel);
        this.blockDataFile.create();
    }

    @Override
    public DataTableWriter appendRow(TdRowData rowData) {
        ArrayRecord arrayRecord = ArrayRecord.of((Object[])rowData.toArray());
        this.blockDataFile.insert((DBRecord)arrayRecord);
        return this;
    }

    @Override
    public DataTableWriter appendRow(List<Object> rowData) {
        for (int i = 0; i < this.blockDataFile.getColumns().size(); ++i) {
            Object value;
            BDFColumnInfo bdfColumnInfo = (BDFColumnInfo)this.blockDataFile.getColumns().get(i);
            if (bdfColumnInfo.getDataType() != 2 || rowData.size() <= i || !((value = rowData.get(i)) instanceof Date)) continue;
            rowData.set(i, ((Date)value).getTime());
        }
        ArrayRecord arrayRecord = ArrayRecord.of(rowData);
        this.blockDataFile.insert((DBRecord)arrayRecord);
        return this;
    }

    @Override
    public void close() throws IOException {
        this.blockDataFile.close();
    }

    @Override
    public void flush() throws IOException {
        this.blockDataFile.flush();
    }
}

