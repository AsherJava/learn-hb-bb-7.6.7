/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.common;

import java.io.File;

public class ExtConstants {
    public static final String EXT_TXT = ".txt";
    public static final String EXT_JSON = ".json";
    public static final String EXT_CSV = ".csv";
    public static final String LINE = "\r\n";
    public static final String PRE_REGION_TOP = "_F";
    public static final String ROWDATAS = "_ROWDATAS";
    public static final String ROOTPATH = System.getProperty("java.io.tmpdir");
    public static final String FILE_SEPARATOR = File.separator;
    public static final String EXPORTDIR = ROOTPATH + FILE_SEPARATOR + ".nr" + FILE_SEPARATOR + "AppData" + FILE_SEPARATOR + "export";
    public static final String UPLOADDIR = ROOTPATH + FILE_SEPARATOR + ".nr" + FILE_SEPARATOR + "AppData" + FILE_SEPARATOR + "upload";
    public static final String SEPARATOR = "/";
    public static final String EXPORTTXTDATAS = "ExportTxtDatas.zip";
    public static final String EXPORTJSONDATAS = "ExportJsonDatas.zip";
    public static final String TXTEXPORTSERVICEIMPL = "TxtExportServiceImpl";
    public static final String CSVEXPORTSERVICEIMPL = "CsvExportServiceImpl";
    public static final String JSONEXPORTSERVICEIMPL = "JsonExportServiceImpl";
    public static final String TXTFILEIMPORTSERVICEIMPL = "TxtFileImportServiceImpl";
    public static final String JSONFILEIMPORTSERVICEIMPL = "JsonFileImportServiceImpl";
    public static final String CSVFILEIMPORTSERVICEIMPL = "CSVFileImportServiceImpl";
    public static final int PAGESIZE = 20000;
    public static final int MAX_JSON_ROWS = 10000;
    public static final int MAX_FIELDS = 950;
    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";
    public static final String COA = ",";
    public static final String DOT = ".";
    public static final String SEMICOLON = ";";
    public static final String COA_ZH = "\uff0c";
    public static final String UNDERLINE = "_";
    public static final String BIZKEYORDER = "BIZKEYORDER";
    public static final String BIZKEYORDER_ID = "RECORDKEY";
    public static final String VERSIONID = "VERSIONID";
    public static final String FLOATORDER = "FLOATORDER";
    public static final String ADJUST = "ADJUST";
    public static final String ATTAMENT_DIRECTORY = "Attament/";
    public static final String ATTAMENT = "Attament";
    public static final String XOR = "|";
    public static final int ONE = 1;
    public static final String ZIP = "zip";
    public static final int BUFFER_SIZE = 1024;
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final int FLOATIMPOPT_APPEND = 0;
    public static final int FLOATIMPOPT_UPDATE = 1;
    public static final int FLOATIMPOPT_DEL_INSERT = 2;
    public static final String CODE = "CODE";
    public static final int ENTITYIMPOPT_APPEND = 0;
    public static final int ENTITYIMPOPT_UPDATE = 1;
    public static final int ENTITYIMPOPT_DEL_INSERT = 2;
    public static final String VALIDTIME = "VALIDTIME";
    public static final String INVALIDTIME = "INVALIDTIME";
    public static final String ORDINAL = "ORDINAL";
    public static final String UNIT_INEXISTENCE = "unit_inexistence";
    public static final String UNIT_NOACCESS = "unit_noaccess";
    public static final String UNIT_SUCCESS = "unit_success";
    public static final String REGION_NOSUCCESS = "region_nosuccess";
    public static final String SEPARATOR_CODE = "SEPARATOR_CODE";
    public static final String SEPARATOR_ONE = " ";
    public static final String SEPARATOR_TWO = "_";
    public static final String SEPARATOR_THREE = "&";
    public static final String ERROR_DATA_KEY = "error_data";
    public static final String SKIP_DATA_KEY = "skip_data";
    public static final String LOG_INFO = "log_info";
    public static final String CUR_PERIODSTR = "[CUR_PERIODSTR]";
}

