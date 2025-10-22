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

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface EntityViewDefine
extends Serializable {
    public String getRowFilterExpression();

    public boolean getFilterRowByAuthority();

    default public boolean isIgnorePermissions() {
        return !this.getFilterRowByAuthority();
    }

    public String getEntityId();
}

