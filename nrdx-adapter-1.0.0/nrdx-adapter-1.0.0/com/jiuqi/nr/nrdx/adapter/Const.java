/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 */
package com.jiuqi.nr.nrdx.adapter;

import com.jiuqi.nr.data.common.service.dto.FileEntry;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Const {
    public static final String F_ID = "TASK_DATA";
    public static final String F_TITLE = "\u62a5\u8868\u4efb\u52a1\u53c2\u6570\u548c\u6570\u636e";
    public static final String NR = "NR";
    public static final String NRDX = "NRDX";
    public static final String NRD = "NRD";
    public static final String TRANSFER_MODULE_TITLE = "\u62a5\u8868";
    public static final String TRANSFER_MODULE_ID = "NR";
    public static final String E_PARAM = "e_args";
    public static final String HANDLE_TYPE = "handle_type";
    public static final String A_PARAM = "a_args";
    public static final String I_PARAM = "i_args";
    public static final String I_RESULT = "i_detail";
    public static final String I_WFUPLOAD_RESULT = "i_wfu_result";
    public static final String BUCKET_NAME = "TEMP";
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir") + File.separator + "JQ" + File.separator + "NRDX" + File.separator;
    private static final Logger log = LoggerFactory.getLogger(Const.class);

    public static FileEntry validateAndSplitPath(String fullPath) throws IOException {
        try {
            String filename;
            int lastSeparator = fullPath.lastIndexOf(47);
            String path = lastSeparator != -1 ? fullPath.substring(0, lastSeparator + 1) : "";
            String string = filename = lastSeparator != -1 ? fullPath.substring(lastSeparator + 1) : fullPath;
            if (log.isDebugEnabled()) {
                log.debug("\u539f\u59cb\u8def\u5f84: {}", (Object)fullPath);
                log.debug("\u8def\u5f84\u90e8\u5206: {}", (Object)path);
                log.debug("\u6587\u4ef6\u540d\u90e8\u5206: {}", (Object)filename);
            }
            Const.validatePath(path);
            Const.validateFilename(filename);
            if (path.equals("/")) {
                path = "";
            }
            return new FileEntry(filename, path);
        }
        catch (IllegalArgumentException e) {
            throw new IOException("\u8def\u5f84\u548c\u6587\u4ef6\u540d\u4e0d\u7b26\u5408\u89c4\u8303", e);
        }
    }

    public static void validatePath(String path) {
        if (!path.isEmpty()) {
            try {
                Paths.get(path, new String[0]);
            }
            catch (InvalidPathException e) {
                throw new IllegalArgumentException("\u65e0\u6548\u8def\u5f84: " + e.getMessage());
            }
            Pattern illegalChars = Pattern.compile("[\\\\:*?\"<> |]");
            if (illegalChars.matcher(path).find()) {
                throw new IllegalArgumentException("\u8def\u5f84\u5305\u542b\u975e\u6cd5\u5b57\u7b26: " + path);
            }
        }
    }

    public static void validateFilename(String filename) {
        String[] reservedWords;
        if (filename.isEmpty()) {
            throw new IllegalArgumentException("\u6587\u4ef6\u540d\u4e3a\u7a7a");
        }
        for (String word : reservedWords = new String[]{"CON", "PRN", "AUX", "NUL", "COM1", "LPT1"}) {
            if (!filename.equalsIgnoreCase(word)) continue;
            throw new IllegalArgumentException("\u6587\u4ef6\u540d\u662f\u7cfb\u7edf\u4fdd\u7559\u5b57: " + filename);
        }
        Pattern illegalChars = Pattern.compile("[\\\\/:*?\"<> |]");
        if (illegalChars.matcher(filename).find()) {
            throw new IllegalArgumentException("\u6587\u4ef6\u540d\u5305\u542b\u975e\u6cd5\u5b57\u7b26: " + filename);
        }
        if (filename.endsWith(".") || filename.endsWith("  ")) {
            throw new IllegalArgumentException("\u6587\u4ef6\u540d\u4e0d\u80fd\u4ee5\u7a7a\u683c\u6216\u70b9\u7ed3\u5c3e: " + filename);
        }
        if (filename.length() > 255) {
            throw new IllegalArgumentException("\u6587\u4ef6\u540d\u8fc7\u957f\uff08\u8d85\u8fc7255\u5b57\u7b26\uff09: " + filename);
        }
    }

    public static String handleDir(String dir) {
        if (!dir.startsWith("/")) {
            dir = "/" + dir;
        }
        return dir;
    }
}

