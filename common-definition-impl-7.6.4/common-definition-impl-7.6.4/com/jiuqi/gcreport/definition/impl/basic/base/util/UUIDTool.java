/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.definition.impl.basic.base.util;

import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.UUID;
import org.springframework.util.StringUtils;

public class UUIDTool {
    private static final int h2b_A_10 = 55;
    private static final int h2b_a_10 = 87;
    private static final UUID EMPTYUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static UUID emptyUUID() {
        return EMPTYUUID;
    }

    public static boolean isEmpty(UUID uuid) {
        return uuid == null || EMPTYUUID.equals(uuid);
    }

    public static UUID newUUID() {
        return UUID.randomUUID();
    }

    public static String newUUIDString36() {
        return UUIDTool.toString36(UUID.randomUUID());
    }

    public static UUID fromObject(Object id) {
        if (id == null || "".equals(id)) {
            return null;
        }
        if (id instanceof UUID) {
            return (UUID)id;
        }
        if (id instanceof byte[]) {
            return UUIDTool.fromByte((byte[])id);
        }
        if (id instanceof String) {
            return UUIDTool.fromString(String.valueOf(id));
        }
        if (id instanceof Integer) {
            return new UUID(0L, ((Integer)id).intValue());
        }
        return null;
    }

    public static String toString36(UUID id) {
        return id.toString();
    }

    public static String toString(UUID id) {
        return id.toString().toUpperCase().replaceAll("-", "");
    }

    public static UUID fromByte(byte[] data) {
        int i;
        if (data.length != 16) {
            throw new IllegalArgumentException("Invalid UUID byte[]");
        }
        long msb = 0L;
        long lsb = 0L;
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    public static UUID fromString(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        if (id.length() == 32) {
            return UUIDTool.tryParse(id, false);
        }
        if (id.length() == 36) {
            try {
                return UUID.fromString(id);
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static UUID paserString(String id) {
        UUID uuid = UUIDTool.fromString(id);
        if (uuid == null) {
            Object[] i18Args = new String[]{id};
            throw new IllegalArgumentException(GcI18nUtil.getMessage((String)"ent.definetion.util.uuid.str.error", (Object[])i18Args));
        }
        return uuid;
    }

    public static String[] toString(UUID[] ids) {
        if (ids == null) {
            return null;
        }
        String[] result = new String[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            result[i] = ids[i].toString();
        }
        return result;
    }

    public static UUID valueOf(String str) {
        return UUIDTool.tryParse(str, true);
    }

    public static UUID valueOf(long mostSigBits, long leastSigBits) {
        if (mostSigBits == 0L && leastSigBits == 0L) {
            return EMPTYUUID;
        }
        return new UUID(mostSigBits, leastSigBits);
    }

    private static UUID tryParse(String str, boolean throwException) {
        block5: {
            if (str == null) {
                return null;
            }
            int strl = str.length();
            if (strl == 32) {
                try {
                    return UUIDTool.valueOf(UUIDTool.hexToLong(str, 0), UUIDTool.hexToLong(str, 16));
                }
                catch (RuntimeException e) {
                    if (!throwException) break block5;
                    throw e;
                }
            }
        }
        if (throwException) {
            Object[] i18Args = new String[]{str};
            throw new RuntimeException(GcI18nUtil.getMessage((String)"ent.definetion.util.uuid.str.error", (Object[])i18Args));
        }
        return null;
    }

    private static long hexToLong(String str, int start) {
        long temp = UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        temp = temp << 4 | (long)UUIDTool.parseChar(str, start++);
        return temp << 4 | (long)UUIDTool.parseChar(str, start++);
    }

    private static int parseChar(String s, int offset) throws RuntimeException, StringIndexOutOfBoundsException {
        char c = s.charAt(offset);
        if (c >= '0') {
            if (c <= '9') {
                return c - 48;
            }
            if (c >= 'A') {
                if (c <= 'F') {
                    return c - 55;
                }
                if (c >= 'a' && c <= 'f') {
                    return c - 87;
                }
            }
        }
        Object[] i18Args = new Object[]{s, offset, Character.valueOf(c)};
        throw new RuntimeException(GcI18nUtil.getMessage((String)"ent.definetion.util.uuid.str.offset.error", (Object[])i18Args));
    }
}

