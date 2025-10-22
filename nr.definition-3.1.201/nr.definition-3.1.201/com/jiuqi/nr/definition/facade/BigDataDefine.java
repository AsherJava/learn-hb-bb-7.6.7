/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import java.io.Serializable;
import java.util.Date;

public interface BigDataDefine
extends Serializable {
    public String getKey();

    public String getCode();

    public int getLang();

    public byte[] getData();

    public String getVersion();

    public Date getUpdateTime();
}

