/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.MD5Util;
import java.util.UUID;
import org.springframework.util.StringUtils;

public class DataTypeUtil {
    public static final UUID UUID_EMPTY = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static UUID getUuidByString(String str) {
        if (!StringUtils.hasText(str)) {
            return UUID_EMPTY;
        }
        String md5 = MD5Util.encrypt(str);
        StringBuilder sb = new StringBuilder();
        sb.append(md5.substring(0, 8)).append("-");
        sb.append(md5.substring(8, 12)).append("-");
        sb.append(md5.substring(12, 16)).append("-");
        sb.append(md5.substring(16, 20)).append("-");
        sb.append(md5.substring(20));
        return UUID.fromString(sb.toString());
    }

    public static String replaceScriptTag(String str) {
        if (str != null && (str.contains("<") || str.contains(">"))) {
            return str.replace("<", "\uff1c").replace(">", "\uff1e");
        }
        return str;
    }
}

