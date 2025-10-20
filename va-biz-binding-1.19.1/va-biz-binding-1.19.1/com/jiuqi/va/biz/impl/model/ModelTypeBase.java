/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.model.PluginType;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.impl.ComputedPropImpl;
import com.jiuqi.va.biz.ruler.impl.RulerExecutor;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.view.intf.ExternalViewManager;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ModelTypeBase
implements ModelType {
    private PluginManager pluginManager;
    @Autowired
    private ExternalViewManager externalViewManager;

    public Class<? extends ModelDefineImpl> getModelDefineClass() {
        return ModelDefineImpl.class;
    }

    public Class<? extends ModelImpl> getModelClass() {
        return ModelImpl.class;
    }

    @Override
    public String[] getDependPlugins() {
        return null;
    }

    @Override
    public String getBizModule() {
        return null;
    }

    private PluginManager getPluginManager() {
        if (this.pluginManager == null) {
            this.pluginManager = (PluginManager)ApplicationContextRegister.getBean(PluginManager.class);
        }
        return this.pluginManager;
    }

    @Override
    public final void initModelDefine(ModelDefine modelDefine, String defineName) {
        ModelDefineImpl modelDefineImpl = (ModelDefineImpl)modelDefine;
        modelDefineImpl.setName(defineName);
        modelDefineImpl.setModelType(this.getName());
        boolean[] initCompPlugin = new boolean[]{false};
        TypedContainer<PluginDefine> plugins = modelDefine.getPlugins();
        if (plugins.size() == 0) {
            modelDefineImpl.setInit(true);
        }
        plugins.stream().forEach(o -> {
            String type = o.getType();
            PluginType pluginType = (PluginType)this.getPluginManager().get(type);
            if ("computedProp".equals(type) && initCompPlugin[0]) {
                return;
            }
            if ("ruler".equals(type)) {
                PluginDefine computeProps = modelDefine.getPlugins().find("computedProp");
                if (computeProps != null) {
                    PluginType computedProp = (PluginType)this.getPluginManager().get("computedProp");
                    computedProp.initPluginDefine(computeProps, modelDefine);
                } else {
                    ComputedPropDefineImpl computedPropDefine = new ComputedPropDefineImpl();
                    computedPropDefine.setType("computedProp");
                    ((ModelDefineImpl)modelDefine).addPlugin(computedPropDefine);
                }
                initCompPlugin[0] = true;
            }
            pluginType.initPluginDefine((PluginDefine)o, modelDefine);
            this.declarePlugin(pluginType, modelDefineImpl);
        });
        String[] depends = this.getDependPlugins();
        if (depends != null) {
            Stream.of(depends).forEach(o -> this.loadPluginDefine(modelDefineImpl, (String)o));
        }
        this.ensureModelDefine(modelDefineImpl);
        modelDefine.getPlugins().stream().forEach(o -> {
            PluginType pluginType = (PluginType)this.getPluginManager().get(o.getType());
            pluginType.pluginDefineLoaded((PluginDefine)o, modelDefine);
        });
    }

    @Override
    public void initModelDefine(ModelDefine modelDefine, String defineName, String externalViewName) {
        ModelDefineImpl modelDefineImpl = (ModelDefineImpl)modelDefine;
        modelDefineImpl.setName(defineName);
        modelDefineImpl.setModelType(this.getName());
        boolean[] initCompPlugin = new boolean[]{false};
        TypedContainer<PluginDefine> plugins = modelDefine.getPlugins();
        if (plugins.size() == 0) {
            modelDefineImpl.setInit(true);
        }
        modelDefine.getPlugins().stream().forEach(o -> {
            String type = o.getType();
            PluginType pluginType = (PluginType)this.getPluginManager().get(type);
            if ("computedProp".equals(type) && initCompPlugin[0]) {
                return;
            }
            if ("ruler".equals(type)) {
                PluginDefine computeProps = modelDefine.getPlugins().find("computedProp");
                if (computeProps != null) {
                    PluginType computedProp = (PluginType)this.getPluginManager().get("computedProp");
                    computedProp.initPluginDefine(computeProps, modelDefine);
                } else {
                    ComputedPropDefineImpl computedPropDefine = new ComputedPropDefineImpl();
                    computedPropDefine.setType("computedProp");
                    ((ModelDefineImpl)modelDefine).addPlugin(computedPropDefine);
                }
                initCompPlugin[0] = true;
            }
            pluginType.initPluginDefine((PluginDefine)o, modelDefine, externalViewName);
            this.declarePlugin(pluginType, modelDefineImpl);
        });
        String[] depends = this.getDependPlugins();
        if (depends != null) {
            Stream.of(depends).forEach(o -> this.loadPluginDefine(modelDefineImpl, (String)o));
        }
        this.ensureModelDefine(modelDefineImpl);
        modelDefine.getPlugins().stream().forEach(o -> {
            PluginType pluginType = (PluginType)this.getPluginManager().get(o.getType());
            pluginType.pluginDefineLoaded((PluginDefine)o, modelDefine);
        });
    }

    protected void ensureModelDefine(ModelDefineImpl modelDefine) {
    }

    protected void declarePlugin(PluginType pluginType, ModelDefineImpl modelDefine) {
    }

    protected final void loadPluginDefine(ModelDefineImpl modelDefine, String name) {
        PluginDefine pluginDefine = modelDefine.getPlugins().find(name);
        if (pluginDefine == null) {
            PluginType pluginType = (PluginType)this.getPluginManager().get(name);
            String[] depends = pluginType.getDependPlugins();
            if (depends != null) {
                Arrays.asList(depends).forEach(o -> this.loadPluginDefine(modelDefine, (String)o));
            }
            try {
                pluginDefine = pluginType.getPluginDefineClass().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new ModelException(e);
            }
            modelDefine.addPlugin(pluginDefine);
            pluginType.initPluginDefine(pluginDefine, modelDefine);
            this.declarePlugin(pluginType, modelDefine);
        }
    }

    @Override
    public final void initModel(Model model, ModelContext context, ModelDefine modelDefine) {
        ModelImpl modelImpl = (ModelImpl)model;
        modelImpl.setContext(context);
        modelImpl.setDefine(modelDefine);
        modelDefine.getPlugins().stream().forEach(o -> {
            Plugin plugin;
            PluginType pluginType = (PluginType)this.getPluginManager().get(o.getType());
            try {
                plugin = pluginType.getPluginClass().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new ModelException(e);
            }
            pluginType.initPlugin(plugin, (PluginDefine)o, modelImpl);
            modelImpl.addPlugin(plugin);
        });
        RulerImpl ruler = modelImpl.getPlugins().find(RulerImpl.class);
        ComputedPropImpl computedProp = modelImpl.getPlugins().find(ComputedPropImpl.class);
        if (ruler != null && computedProp != null) {
            RulerExecutor rulerExecutor = new RulerExecutor(ruler, computedProp);
            ruler.setRulerExecutor(rulerExecutor);
        }
        this.ensureModel((ModelImpl)model);
    }

    protected void ensureModel(ModelImpl model) {
    }
}

