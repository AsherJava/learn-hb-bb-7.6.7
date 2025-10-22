/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.util;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class BatchExportConsts {
    public static final String SEPARATOR = File.separator;
    public static final String ROOT_LOCATION = System.getProperty(FilenameUtils.normalize("java.io.tmpdir"));
    public static final String EXPORTDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "export";
    public static final String UPLOADDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "upload";
    public static final String EXPORT_UNICODE = "GBK";
    public static final String BATCH_TO_ZIP = "zip";
    public static final String BATCH_TO_MUILTISHEET = "batchToMutiSheet";
    public static final String CURRENT_FORM = "currentForm";
    public static final String CURRENT_ALL_FORM = "currentAllForm";
    public static final Integer SHEET_LENGTH = 31;
    public static final String SHEET_NAME = "(\u9875\u540d\u6620\u5c04\u8868)";
    public static final String ERROR_SHEET_NAME = "(\u9519\u8bef\u4fe1\u606f\u6620\u5c04\u8868)";
    public static final String COMPANY_SEPARATOR = "_";
    public static final String FORMAT_SEPARATOR = "|";
    public static final String SEPARATOR_ONE = " ";
    public static final String SEPARATOR_TWO = "_";
    public static final String SEPARATOR_THREE = "&";
    public static final String HIDDEN_SHEET_NAME = "HIDDENSHEETNAME";
}

