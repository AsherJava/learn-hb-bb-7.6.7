/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.jsontype.NamedType
 */
package com.jiuqi.va.biz.impl.model;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.model.ModelDefineBuilder;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component(value="VAMODELMANAGERIMPL")
@Lazy(value=false)
public class ModelManagerImpl
extends NamedManagerImpl<ModelType>
implements ModelManager {
    @Override
    public List<ModelType> getModelList(String metaType) {
        return this.stream().filter(o -> o.getMetaType().equals(metaType)).sorted(Comparator.comparing(ModelType::getTitle).reversed()).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        ModelDefineBuilder.setSubtypes(this.stream().map(o -> new NamedType(o.getModelDefineClass(), o.getName())).collect(Collectors.toList()));
    }

    @Override
    public List<ModelType> getModelList(String metaType, String bizModule) {
        return this.stream().filter(o -> o.getMetaType().equals(metaType) && (o.getBizModule() == null || o.getBizModule().equals(bizModule))).sorted(Comparator.comparing(ModelType::getTitle).reversed()).collect(Collectors.toList());
    }
}

