/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.enumeration;

public enum UnitTreeNodeCountPloy {
    COUNT_OF_ALL_CHILD,
    COUNT_OF_LEAF,
    COUNT_OF_LEAF_AND_NOT_CHA_E,
    COUNT_OF_ALL_CHILD_AND_NOT_CHA_E;


    public static UnitTreeNodeCountPloy translatePloy(boolean isCountOfChaE, boolean isCountOfLeaves) {
        if (isCountOfChaE && isCountOfLeaves) {
            return COUNT_OF_LEAF;
        }
        if (isCountOfChaE) {
            return COUNT_OF_ALL_CHILD;
        }
        if (isCountOfLeaves) {
            return COUNT_OF_LEAF_AND_NOT_CHA_E;
        }
        return COUNT_OF_ALL_CHILD_AND_NOT_CHA_E;
    }
}

