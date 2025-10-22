/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.np.definition.facade;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Date;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface GroupLink
extends Serializable {
    public String getGroupKey();

    public void setGroupKey(String var1);

    public String getObjectKey();

    public void setObjectKey(String var1);

    public String getLevel();

    public void setLevel(String var1);

    public Date getUpdateTime();

    public void setUpdateTime(Date var1);
}

