/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.common;

import java.io.File;

public class Constants {
    public static final String FILE_PATH_SEP = File.separator;
    public static final String TEMP_DIR_PATH = System.getProperty("java.io.tmpdir") + FILE_PATH_SEP + "JQ" + FILE_PATH_SEP + "CKD";
    public static final String CKD_EXPORT_FILE_NAME = "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u6587\u4ef6";
    public static final String CSV_FILE_SUFFIX = ".csv";
    public static final char CSV_SEPARATOR = '\t';
    public static final String CSV_ENTITY_HEADER_PREFIX = "E_";
    public static final String YEAR_MONTH_DAY_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String ASYNC_TASK_IMP_CKD = "ASYNC_TASK_IMP_CKD";
    public static final String BETWEEN_FORM_CODE = "00000000-0000-0000-0000-000000000000";
    public static final String BJ_FORM_CODE = "FORMULA-MAPPING-BETWEEN";
    public static final String BETWEEN_FORM_KEY = "00000000-0000-0000-0000-000000000000";
    public static final String BETWEEN_FORM_TITLE = "\u8868\u95f4";
    public static final String CSV_HEADER_FMLSCHEMETITLE = "FMLSCHEMETITLE";
    public static final String CSV_HEADER_FMLSCHEMEKEY = "FMLSCHEMEKEY";
    public static final String CSV_HEADER_FORMCODE = "FORMCODE";
    public static final String CSV_HEADER_FORMKEY = "FORMKEY";
    public static final String CSV_HEADER_FORMULACODE = "FORMULACODE";
    public static final String CSV_HEADER_FMLEXPKEY = "FMLEXPKEY";
    public static final String CSV_HEADER_GLOBROW = "GLOBROW";
    public static final String CSV_HEADER_GLOBCOL = "GLOBCOL";
    public static final String CSV_HEADER_DIMSTR = "DIMSTR";
    public static final String CSV_HEADER_USERID = "USERID";
    public static final String CSV_HEADER_USERNICKNAME = "USERNICKNAME";
    public static final String CSV_HEADER_UPDATETIME = "UPDATETIME";
    public static final String CSV_HEADER_CONTENT = "CONTENT";
}

