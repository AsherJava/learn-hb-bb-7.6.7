/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Version
 */
package com.jiuqi.nr.tds.impl;

import com.jiuqi.bi.util.Version;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.FileChecker;
import com.jiuqi.nr.tds.FileIOException;
import com.jiuqi.nr.tds.PackageData;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.api.DataTableReader;
import com.jiuqi.nr.tds.api.TdStoreFactory;
import com.jiuqi.nr.tds.bdf.BlockFileReader;
import com.jiuqi.nr.tds.csv.CsvFileReader;
import com.jiuqi.nr.tds.csv.TdsCsvFileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TableReader
implements DataTableReader {
    private DataTableReader dataTableReader;

    @Override
    public void open(File file, String tableName) throws IOException {
        block28: {
            if (!FileChecker.isCompressedFileByHeader(file)) {
                this.dataTableReader = new CsvFileReader();
                this.dataTableReader.open(file, tableName);
                return;
            }
            try (ZipFile zipFile = new ZipFile(file);){
                ZipEntry entry = zipFile.getEntry("package.json");
                if (entry == null) {
                    throw new FileIOException("\u5f53\u524d\u6587\u4ef6\u975eTDS\u6587\u4ef6,\u8bf7\u68c0\u67e5\u6587\u4ef6\u662f\u5426\u6b63\u786e");
                }
                try (InputStream inputStream = zipFile.getInputStream(entry);){
                    PackageData packageData = (PackageData)Costs.MAPPER.readValue(inputStream, PackageData.class);
                    String versionStr = packageData.getVersion();
                    Version version = new Version(versionStr);
                    if (TdStoreFactory.VERSION_0_0_1.equals((Object)version)) {
                        this.dataTableReader = new TdsCsvFileReader();
                        break block28;
                    }
                    if (TdStoreFactory.VERSION_1_0_0.equals((Object)version)) {
                        this.dataTableReader = new BlockFileReader();
                        break block28;
                    }
                    throw new FileIOException("\u5f53\u524d\u670d\u52a1\u65e0\u6cd5\u63a5\u5378TDS\u6587\u4ef6\u7248\u672c\uff1a" + versionStr);
                }
            }
        }
        this.dataTableReader.open(file, tableName);
    }

    @Override
    public List<TdColumn> columns() {
        return this.dataTableReader.columns();
    }

    @Override
    public boolean hasNext() {
        return this.dataTableReader.hasNext();
    }

    @Override
    public TdRowData next() {
        return this.dataTableReader.next();
    }

    @Override
    public void close() throws IOException {
        if (this.dataTableReader != null) {
            this.dataTableReader.close();
        }
    }

    @Override
    public void destroy() {
        this.dataTableReader.destroy();
    }
}

