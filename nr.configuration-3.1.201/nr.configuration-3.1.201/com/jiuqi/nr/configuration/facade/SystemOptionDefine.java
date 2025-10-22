/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.facade;

import com.jiuqi.nr.configuration.facade.SystemOptionBase;

@Deprecated
public interface SystemOptionDefine
extends SystemOptionBase {
    public String getTaskKey();

    public String getFormSchemeKey();

    public Object getValue();
}

