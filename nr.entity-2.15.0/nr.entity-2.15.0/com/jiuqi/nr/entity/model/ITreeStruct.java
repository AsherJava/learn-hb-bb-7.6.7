/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.model;

import java.io.Serializable;

public interface ITreeStruct
extends Serializable {
    public String getLevelCode();

    public boolean isFixedSize();

    public Integer getStructType();

    public Integer getCodeSize();
}

