/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.itreebase.context.ITreeContext
 *  com.jiuqi.nr.itreebase.source.INodeDataBuilder
 *  com.jiuqi.nr.itreebase.source.INodeDataSource
 *  com.jiuqi.nr.itreebase.source.ITreeDataSource
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.gather.gzw.web.tree.selector;

import com.jiuqi.nr.batch.gather.gzw.web.tree.selector.TaskTreeDataGZWSource;
import com.jiuqi.nr.batch.gather.gzw.web.tree.selector.TaskTreeNodeGZWBuilder;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeDataSource;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TaskTreeSelectorGZWProvider
implements ITreeDataSource {
    public static final String SOURCE_ID = "nr.batch.gather.gzw.task.tree.selector";
    @Resource
    private IRunTimeViewController rtViewService;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;

    public String getSourceId() {
        return SOURCE_ID;
    }

    public INodeDataSource getNodeDataSource(ITreeContext context) {
        return new TaskTreeDataGZWSource(this.rtViewService, this.dataSchemeService, this.authorityProvider);
    }

    public INodeDataBuilder getNodeBuilder(ITreeContext context, INodeDataSource dataSource) {
        return new TaskTreeNodeGZWBuilder(dataSource);
    }
}

