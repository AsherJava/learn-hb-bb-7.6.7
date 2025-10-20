/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.commons.utils.JsonUtil;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileWriter
implements Closeable {
    private final ZipOutputStream outputStream;

    public ZipFileWriter(String archivePath) throws IOException {
        if (archivePath == null) {
            throw new IOException("File name can not be null.");
        }
        if (FileUtil.isFileNotEmpty(archivePath) || FileUtil.directoryExists(archivePath)) {
            throw new IOException(MessageFormatUtil.format("File name: {0}, already exists.", archivePath));
        }
        this.outputStream = new ZipOutputStream(FileUtil.getFileOutputStream(archivePath), StandardCharsets.UTF_8);
        this.outputStream.setMethod(8);
        this.outputStream.setLevel(9);
    }

    public void addEntry(String fileName, File file) throws IOException {
        if (file == null) {
            throw new IOException("File should exist.");
        }
        this.addEntry(fileName, Files.newInputStream(file.toPath(), new OpenOption[0]));
    }

    public void addEntry(String fileName, InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("Passed stream can not be null");
        }
        this.addEntryToZip(fileName, zos -> {
            int length;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) > 0) {
                zos.write(bytes, 0, length);
            }
        });
    }

    public void addJsonEntry(String fileName, Object objectToAdd) throws IOException {
        if (objectToAdd == null) {
            throw new IOException("Passed json object can not be null");
        }
        this.addEntryToZip(fileName, zos -> JsonUtil.serializeToStream(zos, objectToAdd));
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }

    private void addEntryToZip(String fileName, ZipWriter writer) throws IOException {
        if (fileName == null) {
            throw new IOException("File name should be unique.");
        }
        ZipEntry zipEntry = new ZipEntry(fileName);
        this.outputStream.putNextEntry(zipEntry);
        writer.write(this.outputStream);
    }

    @FunctionalInterface
    private static interface ZipWriter {
        public void write(ZipOutputStream var1) throws IOException;
    }
}

