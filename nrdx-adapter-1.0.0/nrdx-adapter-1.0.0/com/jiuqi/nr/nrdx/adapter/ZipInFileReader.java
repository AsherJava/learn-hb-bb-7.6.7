/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.entity.RecorderListResult
 *  com.jiuqi.bi.transfer.engine.helper.ZipInputStreamHelper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.adapter;

import com.jiuqi.bi.transfer.engine.entity.RecorderListResult;
import com.jiuqi.bi.transfer.engine.helper.ZipInputStreamHelper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import com.jiuqi.nr.nrdx.adapter.Const;
import com.jiuqi.nr.tds.Costs;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ZipInFileReader
implements FileFinder {
    private final ZipInputStreamHelper inputStreamHelper;

    public ZipInFileReader(ZipInputStreamHelper inputStreamHelper) {
        this.inputStreamHelper = inputStreamHelper;
    }

    public List<FileEntry> listFiles(String path) throws IOException {
        try {
            List list = this.inputStreamHelper.list(path);
            ArrayList<FileEntry> result = new ArrayList<FileEntry>();
            for (RecorderListResult file : list) {
                FileEntry fileEntry = new FileEntry();
                if (path.endsWith("/")) {
                    fileEntry.setFilePath(path + file.getName());
                } else {
                    fileEntry.setFilePath(path + "/" + file.getName());
                }
                fileEntry.setFileName(file.getName());
                fileEntry.setDirectory(file.isDirectory());
                result.add(fileEntry);
            }
            return result;
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }

    public InputStream getFileInputStream(String path) throws IOException {
        path = Const.handleDir(path);
        FileEntry fileEntry = Const.validateAndSplitPath(path);
        try {
            return this.inputStreamHelper.readFileStream(fileEntry.getFilePath(), fileEntry.getFileName());
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public File getFile(String path) throws IOException {
        LocalTime hour = LocalTime.now().withMinute(0).withSecond(0).withNano(0);
        String hourStr = hour.format(Costs.FORMATTER);
        String tempPath = Costs.ROOTPATH + Costs.FILE_SEPARATOR + "NRDX" + Costs.FILE_SEPARATOR + LocalDate.now() + Costs.FILE_SEPARATOR + hourStr + Costs.FILE_SEPARATOR + OrderGenerator.newOrder() + Costs.FILE_SEPARATOR;
        FileEntry fileEntry = Const.validateAndSplitPath(Const.handleDir(path));
        File file = new File(tempPath + fileEntry.getFileName());
        Costs.createPathIfNotExists((Path)file.getParentFile().toPath());
        try (InputStream fileInputStream = this.getFileInputStream(path);){
            Files.copy(fileInputStream, file.toPath(), new CopyOption[0]);
            File file2 = file;
            return file2;
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }

    public byte[] getFileBytes(String path) throws IOException {
        path = Const.handleDir(path);
        FileEntry fileEntry = Const.validateAndSplitPath(path);
        try {
            return this.inputStreamHelper.readFile(fileEntry.getFilePath(), fileEntry.getFileName());
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}

