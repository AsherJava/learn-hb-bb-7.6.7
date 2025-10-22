/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.treegroup;

public interface GroupInfo {
    public String getDimKey();

    public String getDimFieldCode();

    public String getRelateEntityId();

    public String getRelateFieldCode();

    public boolean isHasSecondLevelGroup();

    public boolean isFromDim();
}

