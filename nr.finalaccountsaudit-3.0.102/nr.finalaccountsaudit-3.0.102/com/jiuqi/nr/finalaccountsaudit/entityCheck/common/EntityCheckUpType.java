/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public enum EntityCheckUpType {
    INCREASE("\u65b0\u589e", 1),
    DECREASE("\u51cf\u5c11", 2),
    SAME_SN_CODE("\u53ef\u80fd\u6709\u8bef\u7684\u5355\u4f4d--\u4e0a\u5e74\u4ee3\u7801\u91cd\u7801", 3),
    NULL_SN_CODE("\u53ef\u80fd\u6709\u8bef\u7684\u5355\u4f4d--\u4e0a\u5e74\u4ee3\u7801\u4e3a\u7a7a", 4),
    SN_CODE_NON_EXIST("\u53ef\u80fd\u6709\u8bef\u7684\u5355\u4f4d--\u4e0a\u5e74\u4ee3\u7801\u4e0d\u5b58\u5728", 5),
    ZJ_SAME_ENTITY_NAME("\u53ef\u80fd\u6709\u8bef\u7684\u5355\u4f4d--\u589e\u51cf\u540c\u540d", 6),
    ZJ_SAME_ENTITY_CODE("\u53ef\u80fd\u6709\u8bef\u7684\u5355\u4f4d--\u589e\u51cf\u540c\u7801", 7),
    INCREASE_HAVE_SNDM("\u53ef\u80fd\u6709\u8bef\u7684\u5355\u4f4d--\u65b0\u62a5\u56e0\u7d20\u53ef\u80fd\u6709\u8bef", 8);

    private String name;
    private int index;

    private EntityCheckUpType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (EntityCheckUpType c : EntityCheckUpType.values()) {
            if (c.getIndex() != index) continue;
            return c.name;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }
}

