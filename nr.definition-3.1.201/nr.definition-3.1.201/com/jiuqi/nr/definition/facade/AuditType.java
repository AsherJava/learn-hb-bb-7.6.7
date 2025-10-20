/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.nr.definition.facade;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface AuditType
extends Serializable {
    public Integer getCode();

    public String getOrder();

    public String getTitle();

    public String getIcon();

    public String getColor();

    public String getBackGroundColor();

    public String getFontColor();

    public String getGridColor();

    public void setCode(Integer var1);

    public void setOrder(String var1);

    public void setTitle(String var1);

    public void setIcon(String var1);

    public void setColor(String var1);

    public void setBackGroundColor(String var1);

    public void setFontColor(String var1);

    public void setGridColor(String var1);
}

