/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.databind.annotation.JsonTypeResolver
 */
package com.jiuqi.va.biz.intf.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.PluginDefineBuilder;
import com.jiuqi.va.biz.intf.value.TypedElement;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", visible=true, include=JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonTypeResolver(value=PluginDefineBuilder.class)
public interface PluginDefine
extends TypedElement {
    default public boolean isLocked() {
        return false;
    }

    default public void requireNotLocked() {
        if (this.isLocked()) {
            throw new ModelException("\u63d2\u4ef6\u5b9a\u4e49\u5df2\u9501\u5b9a\uff0c\u4e0d\u5141\u8bb8\u4fee\u6539");
        }
    }
}

