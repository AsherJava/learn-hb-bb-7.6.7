/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.security.authentication;

public interface SecurityConst {
    public static final String CURRENTUSER = "currentUser";
    public static final String USERNAME = "username";
    public static final String USERID = "userId";
    public static final String SESSION_TENANTID = "tenant-id";
    public static final String TENANT_Id = "tenantId";
    public static final String TENANT = "tenant";
    public static final String MESSAGE = "message";
    public static final String TOKEN = "token";
    public static final String X_AUTH_TOKEN = "x-auth-token";
    public static final String PASSWORD_CHECKED = "passwordCheck";
    public static final String PASSWORD_REG = "passwordReg";
    public static final String MAX_FAIL_OPTION = "MAX_FILED_LOGIN";
    public static final String USE_LOCK_OPTION = "LOCK";
    public static final String USED_LOCK = "1";
    public static final String PASSWORD_REGULAR = "PASSWORD_REGULAR";
    public static final String INIT_PASSWORD_REGULAR = "INIT_PASSWORD_REGULAR";
    public static final String USER_STATUS = "status";
    public static final int USER_NORMAL = 200;
    public static final int USER_LOCK = 203;
    public static final int USER_REFUSE = 204;
    public static final int USER_NEED_MODIFYPWD = 205;
}

