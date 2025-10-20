/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.biz.utils;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.Date;

public class Env {
    private static boolean DEBUG = false;
    private static final ThreadLocal<String> localTenant = new ThreadLocal();
    private static byte[] p = Utils.md5Bytes(new byte[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97});

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        Env.DEBUG = DEBUG;
    }

    public static void setTenant(String tenant) {
        if (tenant != null && localTenant.get() != null) {
            throw new RuntimeException();
        }
        if (tenant == null && localTenant.get() == null) {
            throw new RuntimeException();
        }
        localTenant.set(tenant);
    }

    public static String getTenantName() {
        String tenantName = localTenant.get();
        if (tenantName != null) {
            return tenantName;
        }
        return ShiroUtil.getTenantName();
    }

    public static String getUserId() {
        UserLoginDTO user;
        try {
            user = ShiroUtil.getUser();
        }
        catch (Exception e) {
            user = null;
        }
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public static String getUserCode() {
        UserLoginDTO user;
        try {
            user = ShiroUtil.getUser();
        }
        catch (Exception e) {
            user = null;
        }
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    public static String getUnitCode() {
        UserLoginDTO user;
        try {
            user = ShiroUtil.getUser();
        }
        catch (Exception e) {
            user = null;
        }
        if (user != null) {
            return user.getLoginUnit();
        }
        return null;
    }

    public static Date getBizDate() {
        UserLoginDTO user;
        try {
            user = ShiroUtil.getUser();
        }
        catch (Exception e) {
            user = null;
        }
        if (user != null) {
            return user.getLoginDate();
        }
        return Utils.currentDate();
    }

    public static byte[] getP() {
        return p;
    }

    public static void setP(byte[] p) {
        Env.p = p;
    }
}

