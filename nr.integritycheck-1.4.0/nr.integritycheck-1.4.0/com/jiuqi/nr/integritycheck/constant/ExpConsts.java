/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.constant;

import java.util.Arrays;
import java.util.List;

public class ExpConsts {
    public static final String EXP_PATH_NAME = "ICRErrDes";
    public static final String EXP_VERSION = "1.0";
    public static final String EXP_JSON_FILENAME_SUFFIX = ".json";
    public static final String EXP_JSON_FILENAME = "PARAMINFO.json";
    public static final String EXP_CSV_FILENAME_SUFFIX = ".csv";
    public static final String EXP_CSV_FILENAME = "ERRORDESDATA.csv";
    public static final String EXP_ZIP_FILENAME = "ERRORDESDATA.zip";
    public static final char CSV_FILE_DELIMITER = ',';
    public static final List<String> EXP_CSV_HEADER_CODES_OLD = Arrays.asList("formKey", "formCode", "formTitle", "description", "createTime", "creator", "updateTime", "updater");
    public static final List<String> EXP_CSV_HEADER_CODES = Arrays.asList("FORMKEY", "DESCRIPTION", "CREATETIME", "CREATOR", "UPDATETIME", "UPDATER");
}

