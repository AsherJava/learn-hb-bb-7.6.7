/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk.pojo;

public final class DingtalkConsts {
    public static final String CERITY_TYPE = "DINGTALK_ENT_INTERNAL_APP_LOGON";
    public static final String CERITY_TITLE = "\u9489\u9489\u4f01\u4e1a\u5185\u90e8\u5e94\u7528\u5355\u70b9";
    public static final String LOGIN_URL_TEMPLATE = "https://login.dingtalk.com/oauth2/auth?redirect_uri={redirect_uri}&response_type=code&client_id={client_id}&state={state}&scope=openid&prompt=consent";
    public static final String ACCESS_TOKEN_URL_TEMPLATE = "https://api.dingtalk.com/v1.0/oauth2/userAccessToken";
    public static final String USER_INFO_URL_TEMPLATE = "https://api.dingtalk.com/v1.0/contact/users/{unionId}";
    public static final String ACCESS_TOKEN_HEADER = "x-acs-dingtalk-access-token";
    public static final String OPEN_LINK_TEMPLATE = "https://applink.dingtalk.com/page/link?url={url}";
    public static final String DEFAULT_TENANT = "__default_tenant__";

    private DingtalkConsts() {
    }

    public final class SupportUserFiled {
        public static final String TELEPHONE = "telephone";
        public static final String EMAIL = "email";

        private SupportUserFiled() {
        }
    }
}

