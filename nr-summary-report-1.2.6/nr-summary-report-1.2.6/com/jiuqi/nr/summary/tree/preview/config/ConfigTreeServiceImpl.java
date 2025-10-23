/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.preview.config;

import com.jiuqi.nr.summary.tree.core.AbstractTreeService;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.preview.config.ConfigReportTreeNodeBuilder;
import com.jiuqi.nr.summary.tree.preview.config.ConfigSolutionGroupTreeNodeBuilder;
import com.jiuqi.nr.summary.tree.preview.config.ConfigSolutionTreeNodeBuilder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConfigTreeServiceImpl
extends AbstractTreeService {
    @Override
    public String getId() {
        return "preview_config_tree";
    }

    @Override
    public void registerCustomDataProviders(List<TreeNodeBuilder<?>> customDataProviderList) {
        customDataProviderList.add(new ConfigSolutionGroupTreeNodeBuilder());
        customDataProviderList.add(new ConfigSolutionTreeNodeBuilder());
        customDataProviderList.add(new ConfigReportTreeNodeBuilder());
    }
}

