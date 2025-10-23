/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
    public static void unzip(String zipFile, String targetDir, Charset charset) throws IOException {
        Path targetPath = Paths.get(targetDir, new String[0]);
        if (!Files.exists(targetPath, new LinkOption[0])) {
            Files.createDirectories(targetPath, new FileAttribute[0]);
        } else if (!Files.isDirectory(targetPath, new LinkOption[0])) {
            throw new IllegalArgumentException("\u76ee\u6807\u8def\u5f84\u4e0d\u662f\u76ee\u5f55: " + targetDir);
        }
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(zipFile, new String[0]), new OpenOption[0]), charset);){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path entryPath = targetPath.resolve(entry.getName());
                if (!entryPath.normalize().startsWith(targetPath)) {
                    throw new SecurityException("\u975e\u6cd5ZIP\u6761\u76ee\u8def\u5f84: " + entry.getName());
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath, new FileAttribute[0]);
                } else {
                    Files.createDirectories(entryPath.getParent(), new FileAttribute[0]);
                    Files.copy(zis, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zis.closeEntry();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ZipExtractor.unzip("archive.zip", "unzip_output", StandardCharsets.UTF_8);
            System.out.println(" \u89e3\u538b\u5b8c\u6210\u81f3: " + Paths.get("unzip_output", new String[0]).toAbsolutePath());
        }
        catch (Exception e) {
            System.err.println(" \u89e3\u538b\u5931\u8d25: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

