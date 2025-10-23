/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.nrdx.data.api;

import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestFinder
implements FileFinder {
    private ZipInputStream zipInputStream;
    private String path;

    public ZipInputStream getZipInputStream() {
        return this.zipInputStream;
    }

    public void setZipInputStream(ZipInputStream zipInputStream) {
        this.zipInputStream = zipInputStream;
        try {
            String basePath = "DATA/com.jiuqi.nr/TASK_DATA";
            this.path = TestFinder.extractToTempDir(zipInputStream) + "/" + basePath;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FileEntry> listFiles(String path) throws IOException {
        ArrayList<FileEntry> fileEntries = new ArrayList<FileEntry>();
        for (File file : new File(this.path + "/" + path).listFiles()) {
            FileEntry fileEntry = new FileEntry();
            fileEntry.setFilePath(file.getParent());
            fileEntry.setFileName(file.getName());
            fileEntry.setDirectory(file.isDirectory());
            fileEntries.add(fileEntry);
        }
        return fileEntries;
    }

    public InputStream getFileInputStream(String path) throws IOException {
        File file = new File(this.path + "/" + path);
        return new FileInputStream(file);
    }

    public File getFile(String path) throws IOException {
        File file = new File(this.path + "/" + path);
        return file;
    }

    public byte[] getFileBytes(String path) throws IOException {
        File file = new File(this.path + "/" + path);
        return Files.readAllBytes(file.toPath());
    }

    public static String extractToTempDir(ZipInputStream zipInput) throws IOException {
        ZipEntry entry;
        Path tempDir = Files.createTempDirectory("unzip_", new FileAttribute[0]);
        while ((entry = zipInput.getNextEntry()) != null) {
            Path entryPath = tempDir.resolve(entry.getName());
            if (!entryPath.normalize().startsWith(tempDir.normalize())) {
                throw new IOException("\u975e\u6cd5\u8def\u5f84: " + entry.getName());
            }
            if (entry.isDirectory()) {
                Files.createDirectories(entryPath, new FileAttribute[0]);
            } else {
                Files.createDirectories(entryPath.getParent(), new FileAttribute[0]);
                try (OutputStream fos = Files.newOutputStream(entryPath, new OpenOption[0]);){
                    int len;
                    byte[] buffer = new byte[8192];
                    while ((len = zipInput.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
            zipInput.closeEntry();
        }
        System.out.println(" \u89e3\u538b\u5b8c\u6210\uff0c\u76ee\u5f55: " + tempDir.toAbsolutePath());
        return tempDir.toAbsolutePath().toString();
    }
}

