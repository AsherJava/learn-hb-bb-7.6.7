/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.customExcelBatchImport.common;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class CustomExcelBatchImportConsts {
    public static final String SEPARATOR_ONE = " ";
    public static final String SEPARATOR_TWO = "_";
    public static final String SEPARATOR_THREE = "&";
    public static final String SPLIT_CHAR = ";";
    public static final String CUSTOM_EXCEL = "CUSTOM_EXCEL";
    public static final String CUSTOM_EXCEL_BATCH_IMPORT_FILE_AREA = "CUSTOMAREA";
    public static final String CUSTOM_EXCEL_BATCH_IMPORT_ZIP_NAME = "\u81ea\u5b9a\u4e49\u5bfc\u5165excel\u6a21\u677f.zip";
    public static final String SEPARATOR = File.separator;
    public static final String ROOT_LOCATION = System.getProperty(FilenameUtils.normalize("java.io.tmpdir"));
    public static final String EXPORTDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "export";
    public static final String UPLOADDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "upload";
    public static final String CHECKWORK = "checkWork";
    public static final String ANALYSIS = "analysis";
}

