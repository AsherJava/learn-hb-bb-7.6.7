/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon;

import com.jiuqi.nr.itreebase.nodeicon.ImageType;
import java.io.Serializable;

public interface IconSource
extends Serializable {
    public String getKey();

    public ImageType getType();

    public String getSourceId();

    public byte[] getContent();

    default public String getImgSrc() {
        return null;
    }
}

