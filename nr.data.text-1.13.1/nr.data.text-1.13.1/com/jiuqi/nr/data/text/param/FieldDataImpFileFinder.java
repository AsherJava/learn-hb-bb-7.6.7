/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FieldDataImpFileFinder
implements FileFinder {
    private FileFinder finder;
    private String basePath;
    private String curFileName;
    private static final String atthachmentPath = "attachment";

    public FieldDataImpFileFinder(FileFinder finder, String basePath) {
        this.finder = finder;
        this.basePath = basePath;
    }

    public String getCurFileName() {
        return this.curFileName;
    }

    public void setCurFileName(String curFileName) {
        this.curFileName = curFileName;
    }

    public List<FileEntry> listFiles(String path) throws IOException {
        if (!StringUtils.hasLength(path)) {
            return this.finder.listFiles(this.basePath + "/" + path);
        }
        if (atthachmentPath.equals(path)) {
            List fileEntries = this.finder.listFiles(this.basePath + "/" + path);
            ArrayList<FileEntry> result = new ArrayList<FileEntry>();
            if (!CollectionUtils.isEmpty(fileEntries)) {
                for (FileEntry fileEntry : fileEntries) {
                    String fileName = fileEntry.getFileName();
                    if (fileName.endsWith(".csv") && !fileName.equals(this.curFileName + ".csv")) continue;
                    result.add(fileEntry);
                }
            }
            return result;
        }
        return this.finder.listFiles(this.basePath + "/" + path);
    }

    public InputStream getFileInputStream(String path) throws IOException {
        if (StringUtils.hasLength(path)) {
            return this.finder.getFileInputStream(this.pathHandle(path));
        }
        return this.finder.getFileInputStream(this.basePath + "/" + this.curFileName);
    }

    public File getFile(String path) throws IOException {
        if (StringUtils.hasLength(path)) {
            return this.finder.getFile(this.pathHandle(path));
        }
        return this.finder.getFile(this.basePath + "/" + this.curFileName);
    }

    public byte[] getFileBytes(String path) throws IOException {
        if (StringUtils.hasLength(path)) {
            return this.finder.getFileBytes(this.pathHandle(path));
        }
        return this.finder.getFileBytes(this.basePath + "/" + this.curFileName);
    }

    public String pathHandle(String path) {
        String aimPath = path;
        if (path.startsWith("/")) {
            aimPath = path.substring(1);
        }
        if (aimPath.startsWith(atthachmentPath)) {
            return this.basePath + "/" + aimPath;
        }
        return aimPath;
    }
}

