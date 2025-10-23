/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.domain;

import java.util.Calendar;

public interface MessageSampleDO {
    public String getId();

    public String getType();

    public String getActionCode();

    public String getTitle();

    public String getSubject();

    public String getContent();

    public Calendar getCreateTime();
}

