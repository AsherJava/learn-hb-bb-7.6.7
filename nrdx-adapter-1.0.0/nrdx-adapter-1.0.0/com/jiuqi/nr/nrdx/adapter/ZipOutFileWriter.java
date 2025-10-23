/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.helper.ZipOutputStreamHelper
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.nrdx.adapter;

import com.jiuqi.bi.transfer.engine.helper.ZipOutputStreamHelper;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import com.jiuqi.nr.nrdx.adapter.Const;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipOutFileWriter
implements FileWriter {
    private final Logger log = LoggerFactory.getLogger(ZipOutFileWriter.class);
    private final ZipOutputStreamHelper outputHelper;

    public ZipOutFileWriter(ZipOutputStreamHelper outputHelper) {
        this.outputHelper = outputHelper;
    }

    public void addBytes(String path, byte[] data) throws IOException {
        path = Const.handleDir(path);
        FileEntry fileEntry = Const.validateAndSplitPath(path);
        this.outputHelper.addFile(fileEntry.getFilePath(), fileEntry.getFileName(), data);
    }

    public void addFile(String path, File file) throws IOException {
        path = Const.handleDir(path);
        FileEntry fileEntry = Const.validateAndSplitPath(path);
        try (FileInputStream fis = new FileInputStream(file);){
            this.outputHelper.addFile(fileEntry.getFilePath(), fileEntry.getFileName(), (InputStream)fis);
        }
    }

    public void addStream(String path, InputStream fis) throws IOException {
        path = Const.handleDir(path);
        FileEntry fileEntry = Const.validateAndSplitPath(path);
        this.outputHelper.addFile(fileEntry.getFilePath(), fileEntry.getFileName(), fis);
    }

    public OutputStream createFile(String path) throws IOException {
        path = Const.handleDir(path);
        FileEntry fileEntry = Const.validateAndSplitPath(path);
        return this.outputHelper.addFile(fileEntry.getFilePath(), fileEntry.getFileName());
    }
}

