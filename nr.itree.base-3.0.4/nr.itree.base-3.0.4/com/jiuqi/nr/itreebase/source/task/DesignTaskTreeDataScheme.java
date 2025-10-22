/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.itreebase.source.task;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.impl.TreeNodeProviderImpl;
import com.jiuqi.nr.itreebase.source.task.DesignTaskNodeDataSource;
import com.jiuqi.nr.itreebase.source.task.TaskTreeNodeBuilder;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DesignTaskTreeDataScheme
implements ITreeDataSource {
    public static final String SOURCE_ID = "nr.design.task.define.tree.selector";
    @Resource
    private IDesignTimeViewController designTimeViewController;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;

    @Override
    public String getSourceId() {
        return SOURCE_ID;
    }

    @Override
    public INodeDataSource getNodeDataSource(ITreeContext context) {
        return new DesignTaskNodeDataSource(this.designTimeViewController, this.authorityProvider);
    }

    @Override
    public ITreeNodeProvider getTreeNodeProvider(ITreeContext context) {
        INodeDataSource dataProvider = this.getNodeDataSource(context);
        INodeDataBuilder nodeBuilder = this.getNodeBuilder(context, dataProvider);
        return new TreeNodeProviderImpl(dataProvider, nodeBuilder, this.getActionNode(context));
    }

    @Override
    public INodeDataBuilder getNodeBuilder(ITreeContext context, INodeDataSource dataSource) {
        return new TaskTreeNodeBuilder(dataSource);
    }

    private IBaseNodeData getActionNode(ITreeContext context) {
        List allTaskDefines;
        IBaseNodeData actionNode = context.getActionNode();
        if (actionNode == null && (allTaskDefines = this.designTimeViewController.listAllTask()) != null && !allTaskDefines.isEmpty()) {
            for (DesignTaskDefine task : allTaskDefines) {
                if (!this.authorityProvider.canReadTask(task.getKey())) continue;
                BaseNodeDataImpl actionNodeImpl = new BaseNodeDataImpl();
                actionNodeImpl.setKey(task.getKey());
                actionNodeImpl.setTitle(task.getTitle());
                actionNodeImpl.setCode(task.getTaskCode());
                return actionNodeImpl;
            }
        }
        return actionNode;
    }
}

