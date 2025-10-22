/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.common;

public class NRBqlConsts {
    public static final String CONNECTION_NAME = "@NR";
    public static final String DSV_TYPE = "nr.datascheme";
    public static final String DSV_PRIFIX = "NR_";
    public static final String TEMPTABLE_CACHE_KEY = "TempAssistantTableCache";
    public static final String ZBAUTH_CACHE_KEY = "zbAuthCache";
    public static final String BQL_OPTIONS_FULL_MASTER_DIM_MODE = "NR.fullMasterDimMode";
    public static final String BQL_OPTIONS_UNIT_FILTER = "NR.orgFilter";
    public static final String BQL_OPTIONS_FORM_SCHEME = "NR.reportScheme";
    public static final String BQL_OPTIONS_SUPER_UNIT_AUTH = "NR.superUnitAuthority";
    public static final String BQL_OPTIONS_GATHER_SCHEME_CODE = "batchGatherSchemeCode";
    public static final String BQL_OPTIONS_QUERY_STOP_FLAG = "QUERY_CONTEXT_STOP_FLAG";
    public static final String BQL_OPTIONS_START_PERIOD = "startTimeKey";
    public static final String BQL_OPTIONS_TREE_ROOT_ONLY = "TreeRootOnly";
    public static final String BQL_OPTIONS_END_PERIOD = "endTimeKey";
    public static final String BQL_OPTIONS_CALIBRE_FILTER = "calibreFilter";
    public static final String BQL_OPTIONS_DIM_DEFAULT_VALUE = "dimDefaultValue";
    public static final String BQL_OPTIONS_ADJUST_MAP = "adjustMap";
    public static final String BQL_OPTIONS_EXPAND_MODE = "expandMode";
    public static final String BQL_OPTIONS_EXPAND_BY_DIMENSIONS = "expandByDimensions";
    public static final String BQL_OPTIONS_DYNAMIC_EXPAND = "dynamicExpand";
    public static final String VAR_DATA_SCHEME = "NR.var.dataScheme";
    public static final String VAR_DIM_TYPE = "dimType";
    public static final String VAR_DIM_VALUE = "dimValue";
    public static final String PROP_DATATABLE_MAP = "NR.dataTableMap";
    public static final String PROP_DATATABLE_GROUP = "NR.dataTableGroup";
    public static final String ADJUST_PERIOD_CACHE_KEY = "adjustVersionPeriod";
    public static final String IGNORE_UNIT_CACHE_KEY = "ignoreUnitDim";
    public static final int GATHER_DIM_TYPE_BD = 1;
    public static final int GATHER_DIM_TYPE_CALIBRE = 2;
    public static final String DIM_VALUE_LIST_PREFIX = "DimValueList_";
    public static final String MAINDIM_TITLE_CACHE = "MainDimTitleCache";
    public static final String DATATABLE_PARAM_PREFIX = "P_";
    public static final String FIELD_VIRTUAL_BIZKEY = "VIRTUAL_BIZKEYORDER";
    public static final String QUERYOPTIONS_MAPPING_TABLE_PREFIX = "MappingMainDimTable_";
    public static final String QUERYOPTIONS_DIM_QUERY_INFOS = "DimQueryInfos";
    public static final String QUERYOPTIONS_MDINFO_TABLE = "MDInfo_Table";
    public static final String QUERYOPTIONS_PARENT_TABLE_RELATION = "parnetTableRelation";
    public static final String QUERYOPTIONS_DATATABLE_EXECUTOR = "DataTableQueryExecutor";
    public static final String BWB_VALUE = "PROVIDER_BASECURRENCY";
    public static final String REST_PREFIX = "api/v1/zbquery-engine/dsv/";
    public static final String TABLE_GROUP_DIM_GUID = "NR.dimTableGroup";
    public static final String TABLE_GROUP_DIM_TITLE = "\u7ef4\u5ea6\u8868";

    public static enum ExpandMode {
        DONOTHING("0", "\u4e0d\u5904\u7406"),
        SHOW_ALLNULL("1", "\u663e\u793a\u5168\u7a7a\u6570\u636e\u5355\u4f4d"),
        HIDE_ALLNULL("2", "\u4e0d\u663e\u793a\u5168\u7a7a\u6570\u636e\u5355\u4f4d"),
        HIDE_ALLZERO("3", "\u4e0d\u663e\u793a\u51680\u6570\u636e\u5355\u4f4d");

        private final String code;
        private final String title;

        private ExpandMode(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public ExpandMode getExpandMode(String code) {
            for (ExpandMode temp : ExpandMode.values()) {
                if (!temp.getCode().equals(code)) continue;
                return temp;
            }
            return null;
        }

        public String getTitle() {
            return this.title;
        }

        public String getCode() {
            return this.code;
        }
    }
}

