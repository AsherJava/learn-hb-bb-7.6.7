/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.auth;

import java.util.Arrays;
import java.util.List;

public class DataSchemeAuthConsts {
    public static final String INHERIT_PATH_PROVIDER_ID = "datascheme_inherit_path_provider";
    public static final String RESOURCE_CATEGORY_ID = "datascheme-auth-resource-category";
    public static final String RESOURCE_CATEGORY_TITLE = "\u6570\u636e\u65b9\u6848";
    public static final String RESOURCE_CATEGORY_GROUPTITLE = "\u62a5\u8868";
    public static final String PRIVILEGE_READ_ID = "datascheme_auth_resource_read";
    public static final String PRIVILEGE_READ_NAME = "read";
    public static final String PRIVILEGE_READ_TITLE = "\u8bbf\u95ee";
    public static final String PRIVILEGE_WRITE_ID = "datascheme_auth_resource_write";
    public static final String PRIVILEGE_WRITE_NAME = "write";
    public static final String PRIVILEGE_WRITE_TITLE = "\u7f16\u8f91";
    protected static final List<String> ALL_PRIVILEGE_ID = Arrays.asList("22222222-1111-1111-1111-222222222222", "datascheme_auth_resource_read", "datascheme_auth_resource_write");

    private DataSchemeAuthConsts() {
    }
}

