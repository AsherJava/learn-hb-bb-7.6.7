/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 */
package com.jiuqi.va.biz.impl.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.impl.value.TypedContainerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionEventProcessor;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.GlobalActionEventProcessor;
import com.jiuqi.va.biz.intf.meta.MetaInfo;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class ModelDefineImpl
implements ModelDefine {
    private MetaInfo metaInfo;
    private String name;
    private String modelType;
    private List<PluginDefine> plugins = new ArrayList<PluginDefine>();
    private transient TypedContainerImpl<PluginDefine> pluginContainer;
    private transient List<ActionEventProcessor> actionEventProcessorList;
    private transient List<GlobalActionEventProcessor> globalActionEventProcessorList;
    private transient boolean isInit;

    @Override
    public void addActionEventProcessor(ActionEventProcessor processor) {
        if (this.actionEventProcessorList == null) {
            this.actionEventProcessorList = new ArrayList<ActionEventProcessor>();
        }
        this.actionEventProcessorList.add(processor);
    }

    public void initGlobalActionEventProcessor(List<GlobalActionEventProcessor> globalActionEventProcessorList) {
        this.globalActionEventProcessorList = globalActionEventProcessorList;
    }

    @Override
    public void removeActionEventProcessor(ActionEventProcessor processor) {
        if (this.actionEventProcessorList == null || !this.actionEventProcessorList.remove(processor)) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.loadsubtabledatafailed"));
        }
        if (this.actionEventProcessorList.size() == 0) {
            this.actionEventProcessorList = null;
        }
    }

    boolean beforeAction(Model model, Action action, ActionRequest request, ActionResponse response) {
        if (this.actionEventProcessorList != null) {
            for (ActionEventProcessor actionEventProcessor : this.actionEventProcessorList) {
                if (actionEventProcessor.beforeAction(model, action, request, response)) continue;
                return false;
            }
        }
        if (this.globalActionEventProcessorList != null) {
            for (GlobalActionEventProcessor globalActionEventProcessor : this.globalActionEventProcessorList) {
                if (globalActionEventProcessor.beforeAction(model, action, request, response)) continue;
                return false;
            }
        }
        return true;
    }

    void invokeAction(Model model, Action action, ActionRequest request, ActionResponse response) {
        if (this.actionEventProcessorList != null) {
            for (ActionEventProcessor actionEventProcessor : this.actionEventProcessorList) {
                actionEventProcessor.invokeAction(model, action, request, response);
            }
        }
        if (this.globalActionEventProcessorList != null) {
            for (GlobalActionEventProcessor globalActionEventProcessor : this.globalActionEventProcessorList) {
                globalActionEventProcessor.invokeAction(model, action, request, response);
            }
        }
    }

    void afterAction(Model model, Action action, ActionRequest request, ActionResponse response) {
        if (this.actionEventProcessorList != null) {
            for (ActionEventProcessor actionEventProcessor : this.actionEventProcessorList) {
                actionEventProcessor.afterAction(model, action, request, response);
            }
        }
        if (this.globalActionEventProcessorList != null) {
            for (GlobalActionEventProcessor globalActionEventProcessor : this.globalActionEventProcessorList) {
                globalActionEventProcessor.afterAction(model, action, request, response);
            }
        }
    }

    void finalAction(Model model, Action action, ActionRequest request, ActionResponse response) {
        if (this.actionEventProcessorList != null) {
            for (ActionEventProcessor actionEventProcessor : this.actionEventProcessorList) {
                actionEventProcessor.finalAction(model, action, request, response);
            }
        }
        if (this.globalActionEventProcessorList != null) {
            for (GlobalActionEventProcessor globalActionEventProcessor : this.globalActionEventProcessorList) {
                globalActionEventProcessor.finalAction(model, action, request, response);
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getModelType() {
        return this.modelType;
    }

    void setModelType(String modelType) {
        this.modelType = modelType;
    }

    @Override
    public TypedContainer<PluginDefine> getPlugins() {
        if (this.pluginContainer == null) {
            this.pluginContainer = new TypedContainerImpl<PluginDefine>(this.plugins);
        }
        return this.pluginContainer;
    }

    void addPlugin(PluginDefine pluginDefine) {
        this.plugins.add(pluginDefine);
        this.pluginContainer = null;
    }

    @Override
    public MetaInfo getMetaInfo() {
        return this.metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    @Override
    public boolean isInit() {
        return this.isInit;
    }

    public void setInit(boolean init) {
        this.isInit = init;
    }
}

