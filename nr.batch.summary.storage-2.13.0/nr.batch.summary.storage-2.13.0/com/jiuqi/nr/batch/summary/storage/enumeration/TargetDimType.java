/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.enumeration;

public enum TargetDimType {
    BASE_DATA("type_of_base_data_group_key", "LB8RTOF1", "\u57fa\u7840\u6570\u636e", 1),
    CALIBRE("type_of_calibre_group_key", "LB8RTOF2", "\u53e3\u5f84", 2),
    CONDITION("type_of_custom_condition_node_key", "LB8RTOF3", "\u81ea\u5b9a\u4e49\u5206\u7c7b\u6c47\u603b\u6761\u4ef6", 3);

    public final String key;
    public final String code;
    public final String name;
    public final int value;

    private TargetDimType(String key, String code, String name, int value) {
        this.key = key;
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public static TargetDimType valueOf(Integer value) {
        if (value != null) {
            for (TargetDimType t : TargetDimType.values()) {
                if (t.value != value) continue;
                return t;
            }
        }
        return null;
    }
}

