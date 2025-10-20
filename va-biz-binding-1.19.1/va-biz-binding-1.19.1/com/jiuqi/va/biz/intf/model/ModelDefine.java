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
import com.jiuqi.va.biz.intf.action.ActionEventProcessor;
import com.jiuqi.va.biz.intf.meta.MetaInfo;
import com.jiuqi.va.biz.intf.model.ModelDefineBuilder;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.intf.value.TypedContainer;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="modelType", visible=true, include=JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonTypeResolver(value=ModelDefineBuilder.class)
public interface ModelDefine
extends NamedElement {
    public MetaInfo getMetaInfo();

    public String getModelType();

    public TypedContainer<PluginDefine> getPlugins();

    default public void addActionEventProcessor(ActionEventProcessor processor) {
    }

    default public void removeActionEventProcessor(ActionEventProcessor processor) {
    }

    default public boolean isInit() {
        return false;
    }
}

