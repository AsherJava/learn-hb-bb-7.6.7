/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.common;

import java.io.File;

public final class JioConst {
    public static final String PERIOD_SEPARATOR = ",";
    public static final String DOT = ",";
    public static final String MODULE_NAME = "JIO\u6587\u4ef6\u5bfc\u5165";
    public static final String MODULE_EXPORT = "JIO\u6570\u636e\u5bfc\u51fa";
    public static final String CONFIG_ROOT_TAG = "SCHEME_CONFIG";
    public static final String CONFIG_SYS_ZDM_STRUCT = "CONFIG_SYS_ZDM_STRUCT";
    public static final String CONFIG_SYS_ZDM_FIELD_LENGTH = "CONFIG_SYS_ZDM_FIELD_LENGTH";
    public static final String CONFIG_UNIT_FIELD = "CONFIG_UNIT_FIELD";
    public static final String CONFIG_VERSION_GROUPID = "CONFIG_VERSION_GROUPID";
    public static final String CONFIG_PERIOD_FIELD = "CONFIG_PERIOD_FIELD";
    public static final String CONFIG_BBLX_FIELD = "CONFIG_BBLX_FIELD";
    public static final String CONFIG_UNIT_NAME = "CONFIG_UNIT_NAME";
    public static final String CONFIG_SJUNIT_NAME = "CONFIG_SJUNIT_NAME";
    public static final String CONFIG_CONTAIN_BBLX = "CONFIG_CONTAIN_BBLX";
    public static final String CONFIG_ADD_THEME_NODE = "CONFIG_ADD_THEME_NODE";
    public static final String CONFIG_MODIFY_THEME_NODE = "CONFIG_MODIFY_THEME_NODE";
    public static final String CONFIG_ALLOW_FILT_THEME_NODE = "CONFIG_ALLOW_FILT_THEME_NODE";
    public static final String CONFIG_COMMIT_THEME_NODE = "CONFIG_COMMIT_THEME_NODE";
    public static final String CONFIG_IGNORE_THEME_NODE_STATE = "CONFIG_IGNORE_THEME_NODE_STATE";
    public static final String CONFIG_ALLOW_PERIOD_MAPPING = "CONFIG_ALLOW_PERIOD_MAPPING";
    public static final String CONFIG_FIX_YEAR = "CONFIG_FIX_YEAR";
    public static final String CONFIG_YEAR = "CONFIG_YEAR";
    public static final String CONFIG_GROUP_NAME = "CONFIG_GROUP_NAME";
    public static final String CONFIG_PTFILE_FLAG = "CONFIG_PTFILE_FLAG";
    public static final String CONFIG_SRBBLX_VAL = "CONFIG_SRBBLX_VAL";
    public static final String CONFIG_ZBMAP_FILENAME = "CONFIG_ZBMAP_FILENAME";
    public static final String CONFIG_DIM_START_PERIOD = "CONFIG_DIM_START_PERIOD";
    public static final String CONFIG_GROUP_GUID = "CONFIG_GROUP_GUID";
    public static final String CONFIG_PT_RPTLIST = "CONFIG_PT_RPTLIST";
    public static final String CONFIG_FILTER = "CONFIG_FILTER";
    public static final String CONFIG_FMDM_REPORT_NAME = "fmdmReportName";
    public static final String CONFIG_PERIOD_MAPPING_TAG = "CONFIG_PERIOD_MAPPING_TAG";
    public static final String CONFIG_PERIOD_MAPPING_ITEM = "ITEM";
    public static final String CONFIG_PERIOD_MAPPING_KEY = "KEY";
    public static final String CONFIG_PERIOD_MAPPING_VALUE = "VALUE";
    public static final String CONFIG_JQT_TITLE = "CONFIG_JQT_TITLE";
    public static final String CONFIG_JQT_RPTNME_TITLE = "CONFIG_JQT_RPTNME_TITLE";
    public static final String CONFIG_JQT_ZBNAME_TITLE = "CONFIG_JQT_ZBNAME_TITLE";
    public static final String CONFIG_JQT_RPTNME = "CONFIG_JQT_RPTNME";
    public static final String CONFIG_JQT_ZBNAME = "CONFIG_JQT_ZBNAME";
    public static final String CONFIG_JQT_ZBTYPE = "CONFIG_JQT_ZBTYPE";
    public static final String CONFIG_JQT_ZBDICMAL = "CONFIG_JQT_ZBDICMAL";
    public static final String CONFIG_JQT_ZBLENGTH = "CONFIG_JQT_ZBLENGTH";
    public static final String CONFIG_JQT_ZBFLOAT = "CONFIG_JQT_ZBFLOAT";
    public static final String CONFIG_ZBMAP_TITLE = "CONFIG_ZBMAP_TITLE";
    public static final String CONFIG_ZBMAP_RPTNME_TITLE = "CONFIG_ZBMAP_RPTNME_TITLE";
    public static final String CONFIG_ZBMAP_ZBNAME_TITLE = "CONFIG_ZBMAP_ZBNAME_TITLE";
    public static final String CONFIG_ZBMAP_RPTNME = "CONFIG_ZBMAP_RPTNME";
    public static final String CONFIG_ZBMAP_PTCODE = "CONFIG_ZBMAP_PTCODE";
    public static final String CONFIG_ZBMAP_DIM = "CONFIG_ZBMAP_DIM";
    public static final String CONFIG_ZBMAP_PERIOD = "CONFIG_ZBMAP_PERIOD";
    public static final String CONFIG_ZBMAP_NETCODE = "CONFIG_ZBMAP_NETCODE";
    public static final String CONFIG_ZBMAP_TABLENAME = "CONFIG_ZBMAP_TABLENAME";
    public static final String CONFIG_ZBMAP_GUID = "CONFIG_ZBMAP_GUID";
    public static final String CONFIG_IMPORT_ZDM_FORMULA = "CONFIG_IMPORT_ZDM_FORMULA";
    public static final String CONFIG_EXPORT_PTDWDM_FORMULA = "CONFIG_EXPORT_PTDWDM_FORMULA";
    public static final String CONFIG_EXPORT_BBLX_FORMULA = "CONFIG_EXPORT_BBLX_FORMULA";
    public static final String CONST_TRUE = "1";
    public static final String CONST_FALSE = "0";
    public static final String CONST_BINARY_TITLE = "GUID^BINARY";
    public static final String CONST_TXT_TITLE = "TXT^BINARY";
    public static final int CONST_ALLPARA = 0;
    public static final int CONST_ZBPARA = 1;
    public static final int CONST_INI = 0;
    public static final int CONST_XLS = 1;
    public static final String PARAMS_DIR_TARGET_TEMP = "PARAMS_DIR_TARGET_TEMP";
    public static final String PARAMS_DIR_SUCCESS_BAK = "PARAMS_DIR_SUCCESS_BAK";
    public static final String PARAMS_DIR_FAILURE_BAK = "PARAMS_DIR_FAILURE_BAK";
    public static final String PARAMS_SHOW_TIMMING_BUTTON = "PARAMS_SHOW_TIMMING_BUTTON";
    public static final String PARAMS_SHOW_SOLUTION_SELECTOR = "PARAMS_SHOW_SOLUTION_SELECTOR";
    public static final String PARAMS_DEFAULT_SOLUTION = "PARAMS_DEFAULT_SOLUTION";
    public static final String PARAMS_FORCE_PERIOD = "PARAMS_FORCE_PERIOD";
    public static final String PARAMS_SELECT_SOLUTION = "PARAMS_SELECT_SOLUTION";
    public static final String PARAMS_CLEAR_DATA_BEFORE_IMPORT = "PARAMS_CLEAR_DATA_BEFORE_IMPORT";
    public static final String PARAMS_ERROR_LIMIT = "PARAMS_ERROR_LIMIT";
    public static final String PARAMS_NOT_CLEANDATA = "PARAMS_NOT_CLEANDATA";
    public static final String PARAMS_CHECK_UNITNAME = "PARAMS_CHECK_UNITNAME";
    public static final String PARAMS_ERROR_INFO = "PARAMS_ERROR_INFO";
    public static final String PARAMS_PERIOD = "PARAMS_PERIOD";
    public static final String PARAMS_PT_PERIOD_LIST = "PARAMS_PT_PERIOD_LIST";
    public static final String PARAMS_PT_REPORT_LIST = "PARAMS_PT_REPORT_LIST";
    public static final String PARAMS_STRING_NUMBER = "PARAMS_STRING_NUMBER";
    public static final String PARAMS_POLICY_TYPE = "PARAMS_POLICY_TYPE";
    public static final String PARAMS_POLICY_TITLE = "PARAMS_POLICY_TITLE";
    public static final String PARAMS_SHOW_DATA_IMPORT_BTN = "PARAMS_SHOW_DATA_IMPORT_BTN";
    public static final String PARAMS_SHOW_PARA_IMPORT_BTN = "PARAMS_SHOW_PARA_IMPORT_BTN";
    public static final String PARAMS_MAN_MADE_CONFIGURED = "PARAMS_MAN_MADE_CONFIGURED";
    public static final String PARAMS_START_WORKFLOW = "PARAMS_START_WORKFLOW";
    public static final String PARAMS_SHOW_IMPORT_WAY = "PARAMS_SHOW_IMPORT_WAY";
    public static final String MAPPING_SCHEME_GUID = "MAPPING_SCHEME_GUID";
    public static final String PRECHECK_DATA = "problemMap";
    public static final String PRECHECK_RESULT_TYPE = "precheckResultType";
    public static final String PRECHECK_RESULT_READ_ONLY = "precheckResultReadOnly";
    public static final String PRECHECK_RESULT_UNIT_LIST = "precheckResultUnitList";
    public static final String PRECHECK_LOG = "PRECHECK_LOG";
    public static final String DEFAULT_DIR = File.separator + "work" + File.separator + "jioTemp";
    public static final String FILE_PATH = "filePath";
    public static final String JIO_PARSER = "jioParser";
    public static final String JIO_MAPPER = "jioMapper";
    public static final String FIELDNAME = "FIELDNAME";
    public static final String EXPORT_MODULE_NAME = "JIO\u6570\u636e\u5bfc\u51fa";
    public static final boolean SHOW_LOG_PAGE = true;
    public static final int ONE_DAYTIME = 86400000;
    public static final String FILE_DESC_IDENTY = "JQDataIndexFile";
    public static final String FILE_DESC_DISTIRBUTE_TIME = "2000.08";
    public static final String FILE_DESC_VERSION = "0010";
    public static final int FILE_DESC_INDEX_TYPE = 1;
    public static final int FILE_DESC_INDEX_COUNT = 1;
    public static final String TBT_IDX_DESC_INDEXNAME = "ZDM_Other";
    public static final String TBT_IDX_FIELD_DEF = "SYS_ZDM";
    public static final String TBT_IDX_DATATYPE = "C";
    public static final String PARAMS_DIR_TEMPFILE = "PARAMS_DIR_TEMPFILE";
    public static final String PARAMS_TIME_LIMIT = "PARAMS_TIME_LIMIT";
    public static final String PARAMS_SHOW_LOG = "PARAMS_SHOW_LOG";
    public static final String CUSTOM_ZBMAPPING_FILENAME = "MapNexus";
    public static final String CUSTOM_DWMAPPING_FILENAME = "MapCompanyList";
    public static final String PARAMS_VERSION_MODEL = "PARAMS_VERSION_MODEL";
    public static final String VERSION_MODEL_DEFAULT = "VersionDefaultModel";
    public static final String VERSION_MODEL_CZ = "VersionCZModel";
    public static final String CONFIG_FMDM_SNDM_CODE = "CONFIG_FMDM_SNDM_CODE";

    public static enum ImportMode {
        UPLOAD_FILE(0, "\u4e0a\u4f20\u6587\u4ef6", "\u8bf7\u9009\u62e9jio\u6587\u4ef6\uff08*.jio\uff09\uff1a"),
        SERVER_FILE(1, "\u670d\u52a1\u5668\u6587\u4ef6", "\u8bf7\u586b\u5199\u670d\u52a1\u5668jio\u6587\u4ef6\u8def\u5f84\uff08*.jio\uff09\uff1a"),
        SERVIER_DIR(2, "\u670d\u52a1\u5668\u76ee\u5f55", "\u8bf7\u586b\u5199\u670d\u52a1\u5668\u4efb\u52a1\u76ee\u5f55\uff1a");

        private int code;
        private String name;
        private String tips;

        private ImportMode(int code, String name, String tips) {
            this.code = code;
            this.name = name;
            this.tips = tips;
        }

        public int getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getTips() {
            return this.tips;
        }
    }

    public static enum SynState {
        ALL(-1, "\u5168\u90e8"),
        RUNNING(0, "\u6267\u884c\u4e2d"),
        WAITING(1, "\u7b49\u5f85"),
        SUCCESS(2, "\u6210\u529f"),
        FINISHWITHERROR(3, "\u5b8c\u6210\u4f46\u6709\u9519\u8bef"),
        FAILED(4, "\u5931\u8d25"),
        WARNING(5, "\u6210\u529f\u4f46\u6709\u8b66\u544a");

        private int value;
        private String title;

        private SynState(int value, String title) {
            this.value = value;
            this.title = title;
        }

        public int getValue() {
            return this.value;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

