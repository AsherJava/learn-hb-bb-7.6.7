/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.ent.bamboocloud.bim;

import java.util.Arrays;
import java.util.List;

public class BimConsts {
    public static final String BASE_URI_PREFIX = "/api/gcreport/v1/bamboocloud";
    public static final String USER_SYNC_NOT_SUPPORTED = "\u6682\u672a\u5f00\u653e\u7528\u6237\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002";
    public static final String ORG_SYNC_NOT_SUPPORTED = "\u6682\u672a\u5f00\u653e\u7ec4\u7ec7\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002";

    private BimConsts() {
    }

    public static class SupportAlgorithm {
        public static final List<String> TRANS_ALGORITHM = Arrays.asList("AES", "SM4");
        public static final List<String> VERIFY_ALGORITHM = Arrays.asList("MD5", "SM3");

        public static boolean isTransAlgorithmSupport(String algorithm) {
            return TRANS_ALGORITHM.contains(algorithm);
        }

        public static boolean isVerifyAlgorithmSupport(String algorithm) {
            return VERIFY_ALGORITHM.contains(algorithm);
        }
    }

    public static class OpenFunction {
        public static final String ALL = "all";
        public static final String USER_ONLY = "user";
        public static final String ORG_ONLY = "org";

        private OpenFunction() {
        }
    }

    public static class Log {
        public static final String MOUDLE = "\u5408\u5e76-\u7af9\u4e91\u540c\u6b65";
        public static final String LOG_TEMPLATE_ADD_USER = "\u65b0\u589e-\u7528\u6237 %s \u6210\u529f";
        public static final String LOG_TEMPLATE_MOD_USER = "\u4fee\u6539-\u7528\u6237 %s \u6210\u529f";
        public static final String LOG_TEMPLATE_DEL_USER = "\u5220\u9664-\u7528\u6237 %s \u6210\u529f";
        public static final String LOG_TEMPLATE_ADD_ORG = "\u65b0\u589e-\u7ec4\u7ec7 %s \u6210\u529f";
        public static final String LOG_TEMPLATE_MOD_ORG = "\u4fee\u6539-\u7ec4\u7ec7 %s \u6210\u529f";
        public static final String LOG_TEMPLATE_DEL_ORG = "\u5220\u9664-\u7ec4\u7ec7 %s \u6210\u529f";

        private Log() {
        }
    }

    public static class Response {
        public static final String SUCCESS_CODE = "0";
        public static final String SUCCESS_MSG = "success";
        public static final String ERROR_CODE = "500";

        private Response() {
        }
    }

    public static class Org {
        public static final String CODE = "CODE";
        public static final String NAME = "NAME";
        public static final String SHORT_NAME = "SHORT_NAME";
        public static final String PARENT_CODE = "PARENT_CODE";
        public static final String CURRENCY_CODE = "CURRENCY_CODE";
        public static final String CREDIT_CODE = "CREDIT_CODE";
        public static final String CREATETIME = "CREATETIME";

        private Org() {
        }
    }

    public static class User {
        public static final String CODE = "CODE";
        public static final String NAME = "NAME";
        public static final String NICK_NAME = "NICK_NAME";
        public static final String PHONE = "PHONE";
        public static final String EMAIL = "EMAIL";
        public static final String CREATE_TIME = "CREATE_TIME";
        public static final String UPDATE_TIME = "UPDATE_TIME";
        public static final String ENABLE = "ENABLE";
        public static final String ORGCODE = "ORGCODE";
        public static final String ID_NUMBER = "ID_NUMBER";
        public static final String PASSWORD = "password";
        public static final String SECURITY_LEVEL = "SECURITY_LEVEL";

        private User() {
        }
    }

    public static class BimParams {
        public static final String BIM_REQUEST_ID = "bimRequestId";
        public static final String BIM_REMOTE_USER = "bimRemoteUser";
        public static final String BIM_REMOTE_PWD = "bimRemotePwd";
        public static final String BIM_ENABLE = "__ENABLE__";
        public static final String BIM_UID = "bimUid";
        public static final String BIM_ORG_ID = "bimOrgId";
        public static final String BIM_SIGNATURE = "signature";

        private BimParams() {
        }
    }
}

