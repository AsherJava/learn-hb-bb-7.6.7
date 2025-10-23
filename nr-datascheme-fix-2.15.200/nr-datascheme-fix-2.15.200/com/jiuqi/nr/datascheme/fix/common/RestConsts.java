/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.common;

public class RestConsts {
    public static final String REST_PREFIX = "api/v1/datascheme/";
    public static final String REST_API_SCHEME_FIX_TAGS = "\u6570\u636e\u65b9\u6848\uff1a\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5931\u8d25\u4fee\u590d";
    public static final String REST_API_SCHEME_FIX_CHECK = "\u53d1\u5e03\u5931\u8d25\u4fee\u590d\u68c0\u67e5";
    public static final String REST_API_SCHEME_FIX_CHECK_URL = "scheme/fix-check/{key}";
    public static final String REST_API_SCHEME_DOFIX = "\u6267\u884c\u4fee\u590d\u64cd\u4f5c";
    public static final String REST_API_SCHEME_DOFIX_URL = "scheme/dofix";
    public static final String REST_API_SCHEME_SHOW_FIXRESULT = "\u662f\u5426\u663e\u793a\u67e5\u770b\u5386\u53f2\u4fee\u590d\u7ed3\u679c\u6309\u94ae";
    public static final String REST_API_SCHEME_SHOW_FIXRESULT_URL = "scheme/show-fix-result/{key}";
    public static final String REST_API_SCHEME_FIX_RESULT = "\u67e5\u770b\u5386\u53f2\u4fee\u590d\u7ed3\u679c";
    public static final String REST_API_SCHEME_FIX_RESULT_URL = "scheme/fix-logs/{key}";
    public static final String REST_API_SCHEME_DROP_NEW_TABLE = "\u5220\u9664\u5907\u4efd\u8868";
    public static final String REST_API_SCHEME_DROP_NEW_TABLE_URL = "scheme/droptable";
    public static final String REST_API_SCHEME_DROP_ALL_NEW_TABLE = "\u5168\u90e8\u5220\u9664\u5907\u4efd\u8868";
    public static final String REST_API_SCHEME_DROP_ALL_NEW_TABLE_URL = "scheme/droptableall/{key}";
    public static final String REST_API_SCHEME_FIX_PROCESS = "\u83b7\u53d6\u4fee\u590d\u8fdb\u5ea6";
    public static final String REST_API_SCHEME_FIX_PROCESS_URL = "scheme/fixprogress/{key}";
    public static final String REST_API_TABLE_INDEX_FIX_TAGS = "\u6570\u636e\u65b9\u6848\uff1a\u7d22\u5f15\u4fee\u590d";
    public static final String REST_API_TABLE_INDEX_CHECK_TAGS = "\u7d22\u5f15\u68c0\u67e5";
    public static final String REST_API_TABLE_INDEX_CHECK_URL = "fix/table/index/check/{dataSchemeKey}";
    public static final String REST_API_TABLE_INDEX_REBUILD_TAGS = "\u7d22\u5f15\u91cd\u5efa";
    public static final String REST_API_TABLE_INDEX_REBUILD_URL = "fix/table/index/rebuild/{dataSchemeKey}/{errorType}";
}

