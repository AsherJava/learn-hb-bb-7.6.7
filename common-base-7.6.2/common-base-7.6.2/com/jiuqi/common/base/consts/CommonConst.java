/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.consts;

import com.jiuqi.common.base.enums.OAuthGrantTypeEnum;

public final class CommonConst {

    public static enum ProductIdentificationEnum {
        GCREPORT("gcreport", "\u5408\u5e76"),
        BDE("bde", "BDE"),
        DATACENTER("datacenter", "\u4e00\u672c\u8d26");

        private String code;
        private String title;

        private ProductIdentificationEnum(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static interface OAuthConst {
        public static final String GRANT_TYPE = "grant_type";

        public static interface OAuthPasswordGrantConst {
            public static final OAuthGrantTypeEnum GRANT_TYPE = OAuthGrantTypeEnum.PASSWORD;
            public static final String CLIENT_ID = "client_id";
            public static final String CLIENT_SECRET = "client_secret";
            public static final String USERNAME = "@username";
            public static final String PASSWORD = "password";
            public static final String SCOPE = "scope";
            public static final String REDIRECT_URI = "redirect_uri";
        }
    }

    public static interface JWTConst {
        public static final String TOKEN_JWT_USER_ID = "userid";
        public static final String TOKEN_JWT_USER_NAME = "username";
    }

    public static interface HttpHeaderConst {
        public static final String BEARER_TYPE = "Bearer";
        public static final String X_TENANT_ID = "tenant-id";
        public static final String X_CLIENT_TOKEN_USER = "X-Auth-Token-User";
        public static final String X_CLIENT_TOKEN = "X-Auth-Token";
        public static final String X_TOKEN = "token";
        public static final String X_TRACE_ID = "X-Trace-Id";
        public static final String X_Client_IP = "X-Client-IP";
        public static final String AUTHORIZATION = "Authorization";
    }

    public static interface DbConfigConst {
        @Deprecated
        public static final String BIZ_DATASOURCE_CODE = "jiuqi.gcreport.mdd.datasource";
        @Deprecated
        public static final String BIZREADONLY_DATASOURCE_CODE = "jiuqi.gcreport.mddreadonly.datasource";
        public static final String MDD_DATASOURCE_CODE = "jiuqi.gcreport.mdd.datasource";
        public static final String MDDREADONLY_DATASOURCE_CODE = "jiuqi.gcreport.mddreadonly.datasource";
        public static final String SYSTEM_DATASOURCE_DB_TYPE = "spring.datasource.dbType";
    }

    public static interface ServiceNameConst {
        public static final String GCREPORT_SERVICE_NAME = "${custom.service-name.gcreport:gcreport-service}";
        public static final String GCREPORT_SERVICE_URL = "${custom.service-url.gcreport:}";
        public static final String DATACENTER_SERVICE_NAME = "${custom.service-name.datacenter:datacenter-service}";
        public static final String DATACENTER_SERVICE_URL = "${custom.service-url.datacenter:}";
        public static final String MESSAGE_SERVICE_NAME = "${custom.service-name.msgcenter:msgcenter-service}";
        public static final String MESSAGE_SERVICE_URL = "${custom.service-url.msgcenter:}";
        public static final String AUTH_SERVICE_NAME = "${custom.service-name.auth:auth-service}";
        public static final String AUTH_SERVICE_URL = "${custom.service-url.auth:}";
        public static final String PLANTASK_SERVICE_NAME = "${custom.service-name.plantask:plantask-service}";
        public static final String PLANTASK_SERVICE_URL = "${custom.service-url.plantask:}";
        public static final String RELTXN_SERVICE_NAME = "${custom.service-name.reltxn:reltxn-service}";
        public static final String RELTXN_SERVICE_URL = "${custom.service-url.reltxn:}";
    }

    public static interface ApiBasePathConst {
        public static final String GCREPORT_API_BASE_PATH = "/api/gcreport/v1/";
        public static final String DATACENTER_API_BASE_PATH = "/api/datacenter/v1/";
        public static final String MESSAGE_CENTER_API_BASE_PATH = "/api/msgcenter/v1/";
        public static final String AUTH_API_BASE_PATH = "/api/auth/v1/";
        public static final String RELATED_TRX_API_BASE_PATH = "/api/relatedtrx/v1/";
    }
}

