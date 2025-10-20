/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelConsts {
    public static final String FN_ID = "ID";
    public static final String FT_ID = "\u6807\u8bc6";
    public static final String FN_VER = "VER";
    public static final String FT_VER = "\u7248\u672c";
    public static final String FN_MASTERID = "MASTERID";
    public static final String FT_MASTERID = "\u4e3b\u8868";
    public static final String FN_GROUPID = "GROUPID";
    public static final String FT_GROUPID = "\u5206\u7ec4";
    public static final String FN_PARENTID = "PARENTID";
    public static final String FT_PARENTID = "\u4e0a\u7ea7";
    public static final String FN_ORDINAL = "ORDINAL";
    public static final String FT_ORDINAL = "\u6392\u5e8f";
    public static final String FN_TEMPSTEP = "TEMPSTEP";
    public static final String FT_TEMPSTEP = "\u5411\u5bfc\u5236\u5355\u6682\u5b58\u9875\u7d22\u5f15";
    public static final String FN_BILLCODE = "BILLCODE";
    public static final String FN_BILLSTATE = "BILLSTATE";
    public static final String SYNC_TYPE = "type";
    public static final String SYNC_MODEL = "model";
    public static final String SYNC_PARAMS = "params";
    public static final String MODEL_ID = "id";
    public static final String MODEL_DEFINE = "define";
    public static final String MODEL_DATA = "data";
    public static final String FRONT_INSERT_DATA = "insert";
    public static final String FRONT_UPDATE_DATA = "update";
    public static final String FRONT_DELETE_DATA = "delete";
    public static final String MODEL_STATE = "state";
    public static final String[] ACTION_PARAM_DEFINE = new String[0];
    public static final String[] ACTION_PARAM_EDIT = new String[]{"data", "state"};
    public static final Set<String> FIXED_FIELDS = Stream.of("ID", "MASTERID", "GROUPID").collect(Collectors.toSet());
    public static final Set<String> INC_FIXED_FIELDS = new HashSet<String>(Arrays.asList("ID", "MASTERID", "GROUPID", "VER", "ORDINAL", "TEMPSTEP"));
    public static final String BASEDATE_DIM_UNITCODE = "UNITCODE";
    public static final String BASEDATE_DIM_BIZDATE = "BIZDATE";
    public static final String BASEDATE_DIM_LEAFFLAG = "LEAFFLAG";
    public static final String BASEDATE_SEARCH_SHAREFORCECHECK = "shareForceCheck";
    public static final String BASEDATE_SEARCH_SHAREUNITCODES = "shareUnitcodes";
    public static final String X_DETAIL_FILTER_DATA_ID = "X--detailFilterDataId";
    public static final int X_DETAIL_FILTER_DATA_ID_ORDINAL = 0;
    public static final int X_DETAIL_FILTER_DATA_ID_APPEND = 1;
    public static final int X_DETAIL_FILTER_DATA_ID_DELETE = 2;
    public static final String X_COMPUTE_DATETIME_FIELDS = "X--computeDateTimeFields";
}

