/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.common;

public class NrFormCopyRestConsts {
    public static final String REST_PREFIX = "api/v1/designers/";
    public static final String REST_PREFIX_DESIGNER = "designer";
    public static final String MODULE_TITLE = "\u5efa\u6a21\u8bbe\u8ba1";
    public static final String REST_FORMCOPYANDSYNC = "\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757";
    public static final String REST_GET_TASKTREE = "\u83b7\u53d6\u4efb\u52a1\u548c\u5bf9\u5e94\u62a5\u8868\u65b9\u6848\u7684\u6811\u5f62";
    public static final String REST_GET_TASKTREE_URL = "/get-tasktree";
    public static final String REST_GET_LINKS_BY_SRC_AND_DES = "\u6839\u636e\u6765\u6e90\u62a5\u8868\u65b9\u6848\u548c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u6620\u5c04\u5173\u7cfb";
    public static final String REST_GET_LINKS_BY_SRC_AND_DES_URL = "/get-links/{srcFormSchemeKey}/{desFormSchemeKey}";
    public static final String REST_GET_FORMCOPY_INFO = "\u67e5\u8be2\u590d\u5236\u62a5\u8868\u7684\u4fe1\u606f";
    public static final String REST_GET_FORMCOPY_INFO_URL = "/get-formcopy_info";
    public static final String REST_DO_FORMCOPY = "\u6267\u884c\u62a5\u8868\u590d\u5236";
    public static final String REST_DO_FORMCOPY_URL = "/do-formcopy";
    public static final String REST_GET_FORMSYNC_INFO = "\u67e5\u8be2\u540c\u6b65\u4fe1\u606f";
    public static final String REST_GET_FORMSYNC_INFO_URL = "/get-formsync-info/{desFormSchemeKey}";
    public static final String REST_GET_FORMSYNC_RECORDS = "\u67e5\u8be2\u540c\u6b65\u8bb0\u5f55";
    public static final String REST_GET_FORMSYNC_RECORDS_URL = "/get-formsync-records/{desFormSchemeKey}";
    public static final String REST_DO_FORMSYNC = "\u62a5\u8868\u590d\u5236\u6267\u884c\u540c\u6b65";
    public static final String REST_DO_FORMSYNC_URL = "/do-formsync";
    public static final String REST_FORMSYNC_COVER_MESSAGE = "\u6267\u884c\u540c\u6b65\u8986\u76d6\u63d0\u793a";
    public static final String REST_FORMSYNC_COVER_MESSAGE_URL = "/formsync-cover-message";
    public static final String REST_HAS_FORMSYNC_INFO = "\u662f\u5426\u6709\u540c\u6b65\u4fe1\u606f";
    public static final String REST_HAS_FORMSYNC_INFO_URL = "/has-formsync-info/{formSchemeKey}";
    public static final String REST_PUSH_FORM_COPY_AND_SYNC = "\u63a8\u9001\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757";
    public static final String REST_GET_PUSH_FORM_SYNC_INFO = "\u67e5\u8be2\u63a8\u9001\u540c\u6b65\u4fe1\u606f";
    public static final String REST_GET_PUSH_FORM_SYNC_INFO_URL = "/get-push-formsync-info/{srcFormSchemeKey}";
    public static final String REST_DO_PUSH_FORM_SYNC = "\u62a5\u8868\u590d\u5236\u63a8\u9001\u540c\u6b65";
    public static final String REST_DO_PUSH_FORM_SYNC_URL = "/do-push-formsync";
    public static final String REST_GET_FORMSYNC_PUSH_RECORDS = "\u67e5\u8be2\u540c\u6b65\u63a8\u9001\u8bb0\u5f55";
    public static final String REST_GET_FORMSYNC_PUSH_RECORDS_URL = "/get-formsync-push-records/{srcFormSchemeKey}";
    public static final String REST_DO_PUSH_FORM_SYNC_GROUP_KEY = "\u67e5\u8be2\u62a5\u8868\u6240\u5c5e\u5206\u7ec4key";
    public static final String REST_DO_PUSH_FORM_SYNC_GROUP_KEY_URL = "/get-formsync-group-key/{desFormKey}";
    public static final String REST_GET_PUSH_FORMSCHEME_MESSAGE = "\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u548c\u6253\u5370\u65b9\u6848\u4fe1\u606f";
    public static final String REST_GET_PUSH_FORMSCHEME_MESSAGE_URL = "/get-formScheme-formula-print/{srcFormSchemeKey}";
    public static final String REST_GET_SCHEME_LINKS_BY_SRC_AND_DES = "\u6839\u636e\u6765\u6e90\u62a5\u8868\u65b9\u6848\u548c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u4e0d\u5e26\u6765\u6e90\u65b9\u6848\u4fe1\u606f\u7684\u6620\u5c04\u5173\u7cfb";
    public static final String REST_GET_SCHEME_LINKS_BY_SRC_AND_DES_URL = "/get-links-without-src";
    public static final String REST_GET_FORM_COPY_PUSH_CHECK = "\u62a5\u8868\u590d\u5236\u63a8\u9001\u67e5\u627e\u5404\u8981\u590d\u5236\u7684\u62a5\u8868\u5728\u54e5\u54e5\u76ee\u6807\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u7684\u68c0\u67e5\u7ed3\u679c";
    public static final String REST_GET_FORM_COPY_PUSH_CHECK_URL = "/get-form-copy-push-check";
    public static final String REST_GET_FORM_COPY_PUSH_CHECK_FORM_CODE = "\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u65b0\u589e\u62a5\u8868\uff0c\u68c0\u67e5code\u5728\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u662f\u5426\u5b58\u5728";
    public static final String REST_GET_FORM_COPY_PUSH_CHECK_FORM_CODE_URL = "/get-form-copy-push-check-form-code/{desFormSchemeKey}/{formCode}";
    public static final String REST_GET_FORM_COPY_PUSH_CHECK_FORMS_CODE = "\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6267\u884c\u590d\u5236\u3001\u63a8\u9001\uff0c\u6279\u91cf\u68c0\u67e5\u8981\u65b0\u589e\u7684\u62a5\u8868\uff0c\u5176codes\u5728\u65b0\u589e\u62a5\u8868\u6240\u5c5e\u7684\u62a5\u8868\u65b9\u6848\u4e0b\u5b58\u5728\u4e0d\u5b58\u5728";
    public static final String REST_GET_FORM_COPY_PUSH_CHECK_FORMS_CODE_URL = "/get-form-copy-push-check-form-codes";
    public static final String REST_GET_FORM_COPY_PUSH_DO = "\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6279\u91cf\u6267\u884c\u590d\u5236";
    public static final String REST_GET_FORM_COPY_PUSH_DO_URL = "/get-form-copy-push-do";
}

