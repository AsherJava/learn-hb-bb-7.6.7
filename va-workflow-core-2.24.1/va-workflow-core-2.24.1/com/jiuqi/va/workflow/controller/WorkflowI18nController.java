/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.I18nPluginManager
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow"})
public class WorkflowI18nController {
    public static final String PLUGIN_NAME = "pluginName";
    public static final String DEFINE_NAME = "defineName";
    public static final String PROCESS_DESIGN_PLUGIN = "processDesignPlugin";
    private static final String VERSION = "version";
    @Autowired
    private I18nPluginManager i18nPluginManager;
    @Autowired
    private ModelDefineService modelDefineService;

    @PostMapping(value={"/i18n/plugins/resource"})
    public List<VaI18nResourceItem> getI18nPluginsResource(@RequestBody TenantDO param) {
        List i18nPlugins;
        I18nPlugin i18nPlugin;
        if (ObjectUtils.isEmpty(param.getExtInfo(PLUGIN_NAME)) || ObjectUtils.isEmpty(param.getExtInfo(DEFINE_NAME)) || ObjectUtils.isEmpty(param.getExtInfo(VERSION))) {
            return Collections.emptyList();
        }
        String defineName = (String)param.getExtInfo(DEFINE_NAME);
        String pluginName = (String)param.getExtInfo(PLUGIN_NAME);
        long version = Long.parseLong(String.valueOf(param.getExtInfo(VERSION)));
        ModelDefine define = this.modelDefineService.getDefine(defineName, Long.valueOf(version));
        PluginDefine pluginDefine = (PluginDefine)define.getPlugins().find(pluginName);
        ProcessDesignPluginDefine processDefine = null;
        if (pluginDefine instanceof ProcessDesignPluginDefine) {
            processDefine = (ProcessDesignPluginDefine)pluginDefine;
        }
        if (Objects.nonNull(i18nPlugin = (I18nPlugin)(i18nPlugins = Optional.ofNullable(this.i18nPluginManager.getI18nPluginList()).orElse(Collections.emptyList())).stream().filter(x -> pluginName.equalsIgnoreCase(x.getName())).findAny().orElse(null)) && Objects.nonNull(processDefine)) {
            return i18nPlugin.getI18nResource((PluginDefine)processDefine, null);
        }
        return Collections.emptyList();
    }
}

