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
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface FieldDefine
extends UniversalFieldDefine {
    public FieldValueType getValueType();

    public FieldGatherType getGatherType();

    public Boolean getAllowUndefinedCode();

    public Boolean getAllowMultipleSelect();

    public FormatProperties getFormatProperties();

    public Integer getSecretLevel();

    public String getMeasureUnit();

    public String getEntityKey();

    public String getAlias();
}

