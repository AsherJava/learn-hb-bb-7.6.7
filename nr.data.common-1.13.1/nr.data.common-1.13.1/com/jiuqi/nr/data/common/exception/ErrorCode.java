/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.exception;

import java.io.Serializable;

public interface ErrorCode
extends Serializable {
    public String bizName();

    public String getCode();

    public String getDescription();
}

