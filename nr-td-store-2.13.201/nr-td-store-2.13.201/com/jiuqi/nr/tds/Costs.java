/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.tds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.tds.FileIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.format.DateTimeFormatter;

public class Costs {
    public static final String ROOTPATH = System.getProperty("java.io.tmpdir");
    public static final String FILE_SEPARATOR = File.separator;
    public static final String TEMP_DIR = ROOTPATH + FILE_SEPARATOR + "tds" + FILE_SEPARATOR;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static void createPathIfNotExists(Path path) {
        try {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        catch (InvalidPathException e) {
            throw new FileIOException(" \u975e\u6cd5\u8def\u5f84\u683c\u5f0f: " + path, e);
        }
        catch (IOException e) {
            throw new FileIOException(" \u521b\u5efa\u8def\u5f84\u5931\u8d25: " + path, e);
        }
    }
}

