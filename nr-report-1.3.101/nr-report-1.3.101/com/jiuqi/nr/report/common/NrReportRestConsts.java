/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.report.common;

public class NrReportRestConsts {
    public static final String REST_REPORT_PREFIX = "api/v1/report/template/";
    public static final String REST_TAG_PREFIX = "api/v1/report/tag/";
    public static final String REST_RPTT_TAG = "\u5206\u6790\u62a5\u544a\u6a21\u677f\u670d\u52a1";
    public static final String REST_RPTT_FILE_TAG = "\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0b\u8f7d\u670d\u52a1";
    public static final String REST_RPTT_GET_TAG = "\u67e5\u8be2\u5206\u6790\u62a5\u8868\u6a21\u677f";
    public static final String REST_RPTT_GET = "get/{key}";
    public static final String REST_RPTT_BATCH_GET_TAG = "\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u5206\u6790\u62a5\u8868\u6a21\u677f";
    public static final String REST_RPTT_BATCH_GET = "batch_get/{formSchemeKey}";
    public static final String REST_RPTT_ADD_TAG = "\u65b0\u589e\u5206\u6790\u62a5\u8868\u6a21\u677f";
    public static final String REST_RPTT_ADD = "add";
    public static final String REST_RPTT_UPDATE_TAG = "\u66f4\u65b0\u5206\u6790\u62a5\u8868\u6a21\u677f";
    public static final String REST_RPTT_UPDATE = "update";
    public static final String REST_RPTT_UPLOAD_TAG = "\u4e0a\u4f20\u5206\u6790\u62a5\u8868\u6a21\u677f\u6587\u4ef6";
    public static final String REST_RPTT_UPLOAD = "upload";
    public static final String REST_RPTT_DOWNLOAD_TAG = "\u4e0b\u8f7d\u5206\u6790\u62a5\u8868\u6a21\u677f\u9644\u4ef6";
    public static final String REST_RPTT_DOWNLOAD = "download/{fileKey}";
    public static final String REST_RPTT_DELETE_TAG = "\u5220\u9664\u5206\u6790\u62a5\u8868\u6a21\u677f";
    public static final String REST_RPTT_DELETE = "delete";
    public static final String REST_RPTT_NAME_CHECK_TAG = "\u540d\u79f0\u68c0\u67e5";
    public static final String REST_RPTT_NAME_CHECK = "check";
    public static final String REST_RPT_CUSTOM_TAG = "\u5206\u6790\u62a5\u544a\u6a21\u677f\u81ea\u5b9a\u4e49\u6807\u7b7e\u6a21\u5757";
    public static final String REST_QUERY_TAGS_BY_RPT = "\u67e5\u8be2\u62a5\u544a\u6a21\u677f\u4e0b\u7684\u6240\u6709\u81ea\u5b9a\u4e49\u6807\u7b7e";
    public static final String REST_QUERY_TAGS_BY_RPT_URL = "query-tags-by-rpt/{rptKey}/{fileKey}";
    public static final String REST_DEL_TAGS = "\u5220\u9664\u81ea\u5b9a\u4e49\u6807\u7b7e";
    public static final String REST_DEL_TAGS_URL = "del-tags";
    public static final String REST_BATCH_SAVE_TAG_INFO = "\u6279\u91cf\u4fdd\u5b58\u4fdd\u5b58\u81ea\u5b9a\u4e49\u6807\u7b7e\u7684\u4fe1\u606f";
    public static final String REST_BATCH_SAVE_TAG_INFO_URL = "batch_save-tag-info";
    public static final String REST_IMPORT_TAG_INFO = "\u5bfc\u5165\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e";
    public static final String REST_IMPORT_TAG_INFO_URL = "import-tag-info";
    public static final String REST_EXPORT_TAG_INFO = "\u5bfc\u51fa\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e";
    public static final String REST_EXPORT_TAG_INFO_URL = "export-tag-info";
    public static final String REST_QUERY_ENTITY_ATTRIBUTE = "\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u5c5e\u6027";
    public static final String REST_QUERY_ENTITY_ATTRIBUTE_URL = "query-entity-attribute/{taskKey}";
    public static final String REST_QUERY_ENTITY_ATTRIBUTE_TREE = "\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u5c5e\u6027\u6811\u578b";
    public static final String REST_QUERY_ENTITY_ATTRIBUTE_TREE_URL = "query-entity-attribute-tree/{taskKey}";
    public static final String REST_TAG_QUERY_FORM = "\u67e5\u8be2\u6807\u7b7e\u5173\u8054\u62a5\u8868\u6240\u9700\u6570\u636e";
    public static final String REST_TAG_QUERY_FORM_URL = "query-form-for-tag/{formSchemeKey}";
    public static final String REST_TAG_QUERY_FORM_TREE = "\u67e5\u8be2\u6807\u7b7e\u5173\u8054\u62a5\u8868\u6240\u9700\u6570\u636e\u6811\u578b";
    public static final String REST_TAG_QUERY_FORM_TREE_URL = "query-form-tree-for-tag/{formSchemeKey}";
    public static final String REST_TAG_QUERY_QUICK_REPORT_TREE = "\u67e5\u8be2\u6570\u636e\u5206\u6790\u4e0b\u7684\u5206\u6790\u8868\u6811\u5f62\u8282\u70b9\u6570\u636e";
    public static final String REST_TAG_QUERY_QUICK_REPORT_TREE_URL = "query-quick-report-child-tree/{parent}";
    public static final String REST_TAG_LOCATE_QUICK_REPORT_NODE = "\u83b7\u53d6\u5b9a\u4f4d\u5206\u6790\u8868\u8282\u70b9\u7684\u6811\u5f62\u6570\u636e";
    public static final String REST_TAG_LOCATE_QUICK_REPORT_NODE_URL = "locate-quick-report-node/{guid}";
    public static final String REST_TAG_ALL_QUICK_REPORT_NODE = "\u83b7\u53d6\u6240\u6709\u7684\u5206\u6790\u8868\u8282\u70b9";
    public static final String REST_TAG_ALL_QUICK_REPORT_NODE_URL = "all-quick-report-nodes";
    public static final String REST_TAG_QUERY_CHART_CHILD_TREE = "\u56fe\u8868\u6811\u578b\u7684\u4e0b\u7ea7";
    public static final String REST_TAG_QUERY_CHART_CHILD_TREE_URL = "query-chart-child-tree/{parent}/{type}";
    public static final String REST_TAG_LOCATE_CHART_TREE = "\u5b9a\u4f4d\u56fe\u8868\u6811";
    public static final String REST_TAG_LOCATE_CHART_TREE_URL = "locate-chart-node/{chartId}";
    public static final String REST_TAG_ALL_CHART_NODE = "\u83b7\u53d6\u6240\u6709\u7684\u56fe\u8868\u8282\u70b9";
    public static final String REST_TAG_ALL_CHART_NODE_URL = "all-chart-nodes";
}

