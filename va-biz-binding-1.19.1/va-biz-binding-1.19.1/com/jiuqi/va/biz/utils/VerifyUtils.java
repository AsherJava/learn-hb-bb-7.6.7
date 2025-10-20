/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 */
package com.jiuqi.va.biz.utils;

import com.jiuqi.va.biz.utils.E;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VerifyUtils {
    public static final int AUTH_ALL = 15;
    public static final int AUTH_AUTO = 0;
    public static final int AUTH_VIEW = 1;
    public static final int AUTH_EDIT = 2;
    public static final int VERIFY_ERROR = -1;
    public static final int VERIFY_AUTH = -2;
    public static final int VERIFY_TIMEOUT = -3;

    public static List<String> genVerifyCode(BillVerifyDTO dto) {
        return dto.getBillCodes().stream().map(o -> VerifyUtils.genVerifyCode(o, dto.getAuth())).collect(Collectors.toList());
    }

    public static Map<String, String> genVerifyCodeForUsers(BillVerifyDTO dto) {
        HashMap<String, String> verifyCodeMap = new HashMap<String, String>();
        String billcode = dto.getBillCode();
        List userIds = dto.getUserIds();
        for (String userId : userIds) {
            String verifycode = VerifyUtils.genVerifyCode(billcode, dto.getAuth(), userId);
            verifyCodeMap.put(userId, verifycode);
        }
        return verifyCodeMap;
    }

    public static String genVerifyCode(String billCode, int auth) {
        return VerifyUtils.genVerifyCode(billCode, auth, String.valueOf(Env.getUserId()));
    }

    private static String genVerifyCode(String billCode, int auth, String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(billCode);
        sb.append(",");
        sb.append(userId);
        sb.append(',');
        sb.append(auth);
        return E.e(sb.toString());
    }

    public static int doVerify(String billCode, int auth, String verifyCode) {
        if (Utils.isEmpty(verifyCode)) {
            return -1;
        }
        String s = E.d(verifyCode);
        if (Utils.isEmpty(s)) {
            return -1;
        }
        String[] sl = s.split(",");
        if (sl.length < 3) {
            return -1;
        }
        if (!sl[0].equals(billCode)) {
            return -1;
        }
        if (!"@OPENAPI".equals(sl[1]) && !Env.getUserId().equals(sl[1])) {
            return -1;
        }
        int verifyAuth = Integer.parseInt(sl[2]);
        if ((auth & verifyAuth) != auth) {
            return -2;
        }
        return verifyAuth;
    }
}

