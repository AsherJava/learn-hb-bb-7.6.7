/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.pojo;

import com.jiuqi.nr.efdc.pojo.QueryEntity;
import java.sql.Timestamp;
import java.util.UUID;

public interface QueryObject {
    public UUID getId();

    public UUID getTaskKey();

    public UUID getFormSchemeKey();

    public String getPeriod();

    public UUID getMainDim();

    public String getAssistDim();

    public Timestamp getUpdatetime();

    public UUID getSolutionKey();

    public QueryEntity getEntitys();
}

