/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.jsontype.NamedType
 */
package com.jiuqi.va.biz.impl.model;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.PluginDefineBuilder;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.model.PluginType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class PluginManagerImpl
extends NamedManagerImpl<PluginType>
implements PluginManager {
    @Override
    public List<PluginType> getPluginList(Class<? extends Model> modelClass) {
        return this.stream().filter(plugin -> plugin.getDependModel().isAssignableFrom(modelClass)).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        PluginDefineBuilder.setSubtypes(this.stream().map(o -> new NamedType(o.getPluginDefineClass(), o.getName())).collect(Collectors.toList()));
    }

    @Override
    public Set<String> getDependPlugins(String pluginName) {
        HashSet<String> plugins = new HashSet<String>();
        this.collectDependPlugins(pluginName, plugins);
        return plugins;
    }

    private void collectDependPlugins(String pluginName, Set<String> plugins) {
        PluginType pluginType = (PluginType)this.get(pluginName);
        String[] depends = pluginType.getDependPlugins();
        if (depends != null) {
            List<String> dependList = Arrays.asList(depends);
            plugins.addAll(dependList);
            Arrays.asList(depends).forEach(o -> this.collectDependPlugins((String)o, plugins));
        }
    }
}

