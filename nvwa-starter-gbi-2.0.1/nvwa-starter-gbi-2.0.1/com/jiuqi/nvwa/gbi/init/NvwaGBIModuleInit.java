/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactoryManager
 *  com.jiuqi.nvwa.gbi.adapter.IGPTAdapter
 *  com.jiuqi.nvwa.gbi.core.engine.prompt.IPromptTemplateService
 *  com.jiuqi.nvwa.gbi.core.plugin.AgentPluginFactoryManager
 *  com.jiuqi.nvwa.gbi.core.plugin.IAgentPluginFactory
 *  com.jiuqi.nvwa.gbi.dataset.model.GBIDSDataProviderFactory
 *  com.jiuqi.nvwa.gbi.dataset.model.GBIDSModelFactory
 *  com.jiuqi.nvwa.gbi.framework.AgentSemanticGraphManager
 *  com.jiuqi.nvwa.gbi.framework.config.IGBIModuleLauncher
 *  com.jiuqi.nvwa.gbi.framework.intellimodel.IntelligentModelFactoryManager
 *  com.jiuqi.nvwa.gbi.framework.service.IGPTService
 *  com.jiuqi.nvwa.gbi.framework.util.GBIResourceUtils
 *  com.jiuqi.nvwa.gbi.parameter.AgentParameterDataSourceFactory
 *  com.jiuqi.nvwa.gbi.parameter.AgentParameterDataSourceManager
 *  com.jiuqi.nvwa.gbi.resource.AgentResourceManager
 *  com.jiuqi.nvwa.gbi.resource.IAgentResourceProvider
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nvwa.gbi.init;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.nvwa.gbi.adapter.IGPTAdapter;
import com.jiuqi.nvwa.gbi.core.engine.prompt.IPromptTemplateService;
import com.jiuqi.nvwa.gbi.core.plugin.AgentPluginFactoryManager;
import com.jiuqi.nvwa.gbi.core.plugin.IAgentPluginFactory;
import com.jiuqi.nvwa.gbi.dataset.model.GBIDSDataProviderFactory;
import com.jiuqi.nvwa.gbi.dataset.model.GBIDSModelFactory;
import com.jiuqi.nvwa.gbi.framework.AgentSemanticGraphManager;
import com.jiuqi.nvwa.gbi.framework.config.IGBIModuleLauncher;
import com.jiuqi.nvwa.gbi.framework.intellimodel.IntelligentModelFactoryManager;
import com.jiuqi.nvwa.gbi.framework.service.IGPTService;
import com.jiuqi.nvwa.gbi.framework.util.GBIResourceUtils;
import com.jiuqi.nvwa.gbi.parameter.AgentParameterDataSourceFactory;
import com.jiuqi.nvwa.gbi.parameter.AgentParameterDataSourceManager;
import com.jiuqi.nvwa.gbi.resource.AgentResourceManager;
import com.jiuqi.nvwa.gbi.resource.IAgentResourceProvider;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaGBIModuleInit
implements ModuleInitiator {
    @Autowired(required=false)
    private List<IAgentResourceProvider> agentResourceProviders;
    @Autowired(required=false)
    private List<AgentParameterDataSourceFactory> agentParameterDataSources;
    @Autowired(required=false)
    private List<IAgentPluginFactory> agentPluginFactories;
    @Autowired
    private IGPTAdapter gptAdapter;
    @Autowired
    private IGPTService gptService;
    @Autowired(required=false)
    private IGBIModuleLauncher gbiModuleLauncher;
    @Autowired(required=false)
    private IntelligentModelFactoryManager intelligentModelFactoryManager;
    @Autowired
    private IPromptTemplateService templateService;

    public void init(ServletContext context) throws Exception {
        if (this.agentPluginFactories != null) {
            this.agentPluginFactories.forEach(arg_0 -> ((AgentPluginFactoryManager)AgentPluginFactoryManager.getInstance()).registerFactory(arg_0));
        }
        if (this.agentParameterDataSources != null) {
            this.agentParameterDataSources.forEach(arg_0 -> ((AgentParameterDataSourceManager)AgentParameterDataSourceManager.getInstance()).registerFactory(arg_0));
        }
        if (this.agentResourceProviders != null) {
            this.agentResourceProviders.forEach(arg_0 -> ((AgentResourceManager)AgentResourceManager.getInstance()).registerFactory(arg_0));
        }
        if (this.intelligentModelFactoryManager != null) {
            this.intelligentModelFactoryManager.registerAgentActions();
        }
        if (this.gbiModuleLauncher != null) {
            this.gbiModuleLauncher.launch();
        }
        DSModelFactoryManager.getInstance().registerFactory((DSModelFactory)new GBIDSModelFactory());
        DSModelFactoryManager.getInstance().registerDataProviderFactory((DSDataProviderFactory)new GBIDSDataProviderFactory());
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.gptService.initEnvironment(GBIResourceUtils.DEFAULT_SCHEME_ID);
        this.gptService.reload(GBIResourceUtils.DEFAULT_SCHEME_ID);
        this.templateService.refresh();
        AgentSemanticGraphManager.getInstance().getGraph(false);
    }
}

