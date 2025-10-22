/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import java.io.Serializable;
import java.util.Date;

public interface TaskGroupLinkDefine
extends Serializable {
    public String getGroupKey();

    public String getTaskKey();

    public String getOrder();

    public Date getUpdateTime();
}

