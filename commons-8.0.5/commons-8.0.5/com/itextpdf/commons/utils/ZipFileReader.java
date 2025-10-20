/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import com.itextpdf.commons.utils.MessageFormatUtil;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipFileReader
implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipFileReader.class);
    private final ZipFile zipFile;
    private int thresholdSize = 1000000000;
    private int thresholdEntries = 10000;
    private double thresholdRatio = 10.0;

    public ZipFileReader(String archivePath) throws IOException {
        if (archivePath == null) {
            throw new IOException("File name can not be null.");
        }
        this.zipFile = new ZipFile(archivePath, StandardCharsets.UTF_8);
    }

    public Set<String> getFileNames() throws IOException {
        HashSet<String> fileNames = new HashSet<String>();
        Enumeration<? extends ZipEntry> entries = this.zipFile.entries();
        int totalSizeArchive = 0;
        int totalEntryArchive = 0;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            boolean zipBombSuspicious = false;
            try (BufferedInputStream in = new BufferedInputStream(this.zipFile.getInputStream(entry));){
                int nBytes;
                ++totalEntryArchive;
                byte[] buffer = new byte[2048];
                int totalSizeEntry = 0;
                while ((nBytes = ((InputStream)in).read(buffer)) > 0) {
                    totalSizeEntry += nBytes;
                    totalSizeArchive += nBytes;
                    double compressionRatio = (double)totalSizeEntry / (double)entry.getCompressedSize();
                    if (!(compressionRatio > this.thresholdRatio)) continue;
                    zipBombSuspicious = true;
                    break;
                }
                if (zipBombSuspicious) {
                    LOGGER.warn(MessageFormatUtil.format("Ratio between compressed and uncompressed data is highly suspicious, looks like a Zip Bomb Attack. Threshold ratio is {0}.", this.thresholdRatio));
                    break;
                }
                if (totalSizeArchive > this.thresholdSize) {
                    LOGGER.warn(MessageFormatUtil.format("The uncompressed data size is too much for the application resource capacity, looks like a Zip Bomb Attack. Threshold size is {0}.", this.thresholdSize));
                    break;
                }
                if (totalEntryArchive > this.thresholdEntries) {
                    LOGGER.warn(MessageFormatUtil.format("Too much entries in this archive, can lead to inodes exhaustion of the system, looks like a Zip Bomb Attack. Threshold number of file entries is {0}.", this.thresholdEntries));
                    break;
                }
            }
            if (entry.isDirectory()) continue;
            fileNames.add(entry.getName());
        }
        return fileNames;
    }

    public InputStream readFromZip(String fileName) throws IOException {
        if (fileName == null) {
            throw new IOException("File name can not be null.");
        }
        ZipEntry entry = this.zipFile.getEntry(fileName);
        if (entry == null || entry.isDirectory()) {
            throw new IOException(MessageFormatUtil.format("Zip entry not found for name: {0}", fileName));
        }
        return this.zipFile.getInputStream(entry);
    }

    public void setThresholdSize(int thresholdSize) {
        this.thresholdSize = thresholdSize;
    }

    public void setThresholdEntries(int thresholdEntries) {
        this.thresholdEntries = thresholdEntries;
    }

    public void setThresholdRatio(double thresholdRatio) {
        this.thresholdRatio = thresholdRatio;
    }

    @Override
    public void close() throws IOException {
        this.zipFile.close();
    }
}

