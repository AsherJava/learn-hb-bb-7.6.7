/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineMerge;
import com.jiuqi.va.biz.intf.meta.MetaInfo;
import com.jiuqi.va.biz.intf.model.Declare;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.TypedContainer;

public abstract class ModelDefineDeclare<T extends ModelDefineImpl, H extends DeclareHost<? super T>>
implements ModelDefine,
Declare<T, H>,
DeclareHost<PluginDefine> {
    private H host;
    private T impl;

    public ModelDefineDeclare(H host, Class<T> defineClass) {
        this.host = host;
        try {
            this.impl = (ModelDefineImpl)defineClass.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new ModelException(e);
        }
    }

    @Override
    public String getName() {
        return ((ModelDefineImpl)this.impl).getName();
    }

    public ModelDefineDeclare<T, H> setName(String name) {
        ((ModelDefineImpl)this.impl).setName(name);
        return this;
    }

    @Override
    public String getModelType() {
        return ((ModelDefineImpl)this.impl).getModelType();
    }

    public ModelDefineDeclare<T, H> setModelType(String modelType) {
        ((ModelDefineImpl)this.impl).setModelType(modelType);
        return this;
    }

    @Override
    public TypedContainer<PluginDefine> getPlugins() {
        return ((ModelDefineImpl)this.impl).getPlugins();
    }

    @Override
    public MetaInfo getMetaInfo() {
        return ((ModelDefineImpl)this.impl).getMetaInfo();
    }

    @Override
    public void accept(PluginDefine declareImpl) {
        PluginDefine target = ((ModelDefineImpl)this.impl).getPlugins().find(declareImpl.getType());
        if (target == null) {
            ((ModelDefineImpl)this.impl).addPlugin(declareImpl);
        } else {
            PluginDefineMerge.merge(target, declareImpl);
        }
    }

    @Override
    public H endDeclare() {
        if (this.host != null && this.impl != null) {
            this.host.accept(this.impl);
            this.host = null;
            this.impl = null;
        }
        return this.host;
    }
}

