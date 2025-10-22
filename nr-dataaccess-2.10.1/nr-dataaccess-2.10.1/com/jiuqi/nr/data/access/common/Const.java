/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Const {
    public static final String CANACCESS = "1";
    public static final String NOACCESS = "2";
    public static final String LOCK_CANCEL_CODE = "lock_cancel";
    public static final String TRUE = "1";
    public static final String FALSE = "0";
    public static final String DEFAULT_VALUE = "00000000-0000-0000-0000-000000000000";
    public static final UUID emptyID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String SPLIT_VALUE = ";";
    public static final String WORKFLOW_MSG = "\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91";
    public static final String WORKFLOW_SUBMIT_MSG = "\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91";
    public static final String WORKFLOW_UPLOAD_MSG = "\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91";
    public static final String WORKFLOW_CONFIRM_MSG = "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91";
    public static final String WORKFLOW_END_MSG = "\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f";
    public static final String WORKFLOW_UNSTART_MSG = "\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb";
    public static final String WORKFLOW_END_MSG_CODE = "UPLOAD_TIME_END";
    public static final String WORKFLOW_END_MSG_START_CODE = "UPLOAD_TIME_NOT_START";
    public static final String ENDUPLOAD_MSG = "\u8be5\u8282\u70b9\u7ec8\u6b62\u586b\u62a5\u4e0d\u53ef\u7f16\u8f91";
    public static final String FORMCOND_MSG = "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6";
    public static final String FORMEXP_MSG = "\u62a5\u8868\u6570\u636e\u53ea\u8bfb\u516c\u5f0f\u6210\u7acb\uff0c\u4e0d\u53ef\u7f16\u8f91";
    public static final String FORMLOCK_MSG = "\u62a5\u8868\u88ab\u9501\u5b9a\u4e0d\u53ef\u7f16\u8f91";
    public static final String SECRET_MSG = "\u7528\u6237\u5bc6\u7ea7\u4e0d\u80fd\u8bbf\u95ee\u62a5\u8868\u5bc6\u7ea7\uff0c\u62a5\u8868\u4e0d\u53ef\u89c1";
    public static final String UNITFORMACCESS_MSG = "\u8be5\u5355\u4f4d\u5bf9\u62a5\u8868\u6ca1\u6709\u6743\u9650\u4e0d\u53ef\u7f16\u8f91";
    public static final String USERFORMACCESS_MSG = "\u7528\u6237\u5bf9\u62a5\u8868\u6ca1\u6709\u7f16\u8f91\u6743\u9650";
    public static final String DAYAPUBLSH_READ_MSG = "\u65e0\u6743\u9650\u7528\u6237\u4e0d\u53ef\u67e5\u770b\u672a\u53d1\u5e03\u7684\u6570\u636e";
    public static final String DAYAPUBLSH_WRITE_MSG = "\u6570\u636e\u5df2\u53d1\u5e03\u4e0d\u53ef\u7f16\u8f91";
    public static final String DAYAPUBLSH_UNAUTH_MSG = "\u6ca1\u6709\u8bbf\u95ee\u672a\u53d1\u5e03\u6570\u636e\u6743\u9650\u4e0d\u53ef\u7f16\u8f91";
    public static final String WORKFLOW_ACCESS_NAME = "upload";
    public static final String FORM_CONDITION_ACCESS_NAME = "formCondition";
    public static final String IGNORE_ALL_ACCESS = "ALL";
    public static final String FORMLOCK = "FORM_LOCK";
    public static final String MULTIUSER_FORM_LOCK = "MULTIUSER_FORM_LOCK";
    public static final String FORCE_FORM_UNLOCK_ROLE = "FORCE_FORM_UNLOCK_ROLE";
    public static final String DW_FIELD = "MDCODE";
    public static final String PERIOD_FIELD = "PERIOD";
    public static final String IGNORE_WORKFLOW_STATE_ITEM = "IGNORE_WORKFLOW_STATE_ITEM";

    public static enum DataPublishStatus {
        UNAUTH_READ("10", "\u65e0\u6743\u9650\u7528\u6237\u4e0d\u53ef\u67e5\u770b\u672a\u53d1\u5e03\u7684\u6570\u636e"),
        PUBLISH_WRITE("11", "\u6570\u636e\u5df2\u53d1\u5e03\u4e0d\u53ef\u7f16\u8f91"),
        UNAUTH_WRITE("12", "\u6ca1\u6709\u8bbf\u95ee\u672a\u53d1\u5e03\u6570\u636e\u6743\u9650\u4e0d\u53ef\u7f16\u8f91"),
        DEFAULT("1", "");

        private String code;
        private String msg;

        private DataPublishStatus(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }

        public static String getMsgByCode(String code) {
            return Arrays.asList(DataPublishStatus.values()).stream().filter(e -> e.getCode().equals(code)).findAny().orElse(DEFAULT).getMsg();
        }

        public static List<String> getCodeLists() {
            return Arrays.asList(DataPublishStatus.values()).stream().map(DataPublishStatus::getCode).collect(Collectors.toList());
        }
    }
}

