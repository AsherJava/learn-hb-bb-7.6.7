/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

public class TableConsts {
    public static final String NR_STATE_XXX_FORMLOCK = "NR_STATE_%s_FORMLOCK";
    public static final String NR_STATE_XXX_FORMLOCK_TITLE = "\u9501\u5b9a\u72b6\u6001\u8868";
    public static final String FORMLOCK_FL_ID = "FL_ID";
    public static final String FORMLOCK_FL_DW = "FL_DW";
    public static final String FORMLOCK_FL_PERIOD = "FL_PERIOD";
    public static final String FORMLOCK_FL_SCENE1 = "FL_SCENE1";
    public static final String FORMLOCK_FL_FORMSCHEMEKEY = "FL_FORMSCHEMEKEY";
    public static final String FORMLOCK_FL_FORMKEY = "FL_FORMKEY";
    public static final String FORMLOCK_FL_USER = "FL_USER";
    public static final String FORMLOCK_FL_ISLOCK = "FL_ISLOCK";
    public static final String FORMLOCK_FL_UPDATETIME = "FL_UPDATETIME";
    public static final String NR_STATE_XXX_FORMLOCK_HIS = "NR_STATE_%s_FORMLOCK_HIS";
    public static final String NR_STATE_XXX_FORMLOCK_HIS_TITLE = "\u9501\u5b9a\u5386\u53f2\u8868";
    public static final String FORMLOCK_FLH_ID = "FLH_ID";
    public static final String FORMLOCK_FLH_FORMSCHEME = "FLH_FORMSCHEME";
    public static final String FORMLOCK_FLH_FORM = "FLH_FORM";
    public static final String FORMLOCK_FLH_USER = "FLH_USER";
    public static final String FORMLOCK_FLH_OPER = "FLH_OPER";
    public static final String FORMLOCK_FLH_OPERTIME = "FLH_OPERTIME";
    @Deprecated
    public static final String NR_WORKFLOW_XXX = "NR_WORKFLOW_%s";
    public static final String NR_WORKFLOW_XXX_TITLE = "\u4e0a\u62a5\u72b6\u6001\u8868";
    public static final String WORKFLOW_WL_ID = "WL_ID";
    public static final String WORKFLOW_WL_DW = "WL_DW";
    public static final String WORKFLOW_WL_PERIOD = "WL_PERIOD";
    public static final String WORKFLOW_WL_FORMSCHEMEKEY = "WL_FORMSCHEMEKEY";
    public static final String WORKFLOW_WL_FORMKEY = "WL_FORMKEY";
    public static final String WORKFLOW_WL_STATE = "WL_STATE";
    public static final String WORKFLOW_WL_UPDATETIME = "WL_UPDATETIME";
    public static final String NR_DATAPUBLISH_XXX = "NR_DATAPUBLISH_%s";
    public static final String NR_DATAPUBLISH_XXX_TITLE = "\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868";
    public static final String DATAPUBLISH_DP_ID = "DP_ID";
    public static final String DATAPUBLISH_DP_DW = "DP_DW";
    public static final String DATAPUBLISH_DP_PERIOD = "DP_PERIOD";
    public static final String DATAPUBLISH_DP_SCENE1 = "DP_SCENE1";
    public static final String DATAPUBLISH_DP_FORMSCHEMEKEY = "DP_FORMSCHEMEKEY";
    public static final String DATAPUBLISH_DP_FORMKEY = "DP_FORMKEY";
    public static final String DATAPUBLISH_DP_USER = "DP_USER";
    public static final String DATAPUBLISH_DP_ISPUBLISH = "DP_ISPUBLISH";
    public static final String DATAPUBLISH_DP_UPDATETIME = "DP_UPDATETIME";
    public static final int ROWLIMITCOUNT = 200000;
    public static final String NR_UNITSTATE_XXX = "NR_UNITSTATE_%s";
    public static final String NR_UNITSTATE_XXX_TITLE = "\u7ec8\u6b62\u586b\u62a5\u72b6\u6001\u8868";
    public static final String UNITSTATE_US_ID = "US_ID";
    public static final String UNITSTATE_US_DW = "US_DW";
    public static final String UNITSTATE_US_PERIOD = "US_PERIOD";
    public static final String UNITSTATE_US_FORMSCHEMEKEY = "US_FORMSCHEMEKEY";
    public static final String UNITSTATE_US_FORMKEY = "US_FORMKEY";
    public static final String UNITSTATE_US_USER = "US_USER";
    public static final String UNITSTATE_US_STATE = "US_STATE";
    public static final String UNITSTATE_US_UPDATETIME = "US_UPDATETIME";
    public static final String NR_SECRET_XXX = "NR_SECRETLEVEL_%s";
    public static final String NR_SECRET_XXX_TITLE = "\u5bc6\u7ea7\u72b6\u6001\u8868";
    public static final String SECRET_ID = "SL_ID";
    public static final String SECRET_DW = "SL_DW";
    public static final String SECRET_PERIOD = "SL_PERIOD";
    public static final String SECRET_FORMSCHEMEKEY = "SL_FORMSCHEMEKEY";
    public static final String SECRET_FORMKEY = "SL_FORMKEY";
    public static final String SECRET_LEVEL = "SL_LEVEL";
    public static final String SECRET_USER = "SL_USER";
    public static final String SECRET_UPDATETIME = "SL_UPDATETIME";
    public static final String DW_FIELD = "MDCODE";
    public static final String PERIOD_FIELD = "PERIOD";
    public static final String FORMSCHEMEKEY = "FORMSCHEMEKEY";

    public static String getSysTableName(String tablePrefex, String schemeCode) {
        return String.format(tablePrefex, schemeCode);
    }

    public static String getSysTableTitle(String titleSuffix, String schemeCode) {
        return String.format("\u6570\u636e\u65b9\u6848\u3010%s\u3011%s", schemeCode, titleSuffix);
    }
}

