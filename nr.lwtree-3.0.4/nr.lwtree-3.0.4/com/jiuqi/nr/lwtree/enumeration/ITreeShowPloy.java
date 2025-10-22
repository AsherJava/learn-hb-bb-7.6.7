/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.lwtree.enumeration;

public enum ITreeShowPloy {
    DEFAULT_TREE(0),
    SUMMARY_TREE(1),
    FILTER_TREE(2),
    RANGE_WITH_ROOTS(3),
    RANGE_WITH_TREE(4),
    RANGE_WITH_LIST(5),
    WITH_OUT_LEAF_NODE(6);

    public int value;

    private ITreeShowPloy(int value) {
        this.value = value;
    }

    public static ITreeShowPloy valueOf(int value) {
        for (ITreeShowPloy v : ITreeShowPloy.values()) {
            if (v.value != value) continue;
            return v;
        }
        return DEFAULT_TREE;
    }
}

