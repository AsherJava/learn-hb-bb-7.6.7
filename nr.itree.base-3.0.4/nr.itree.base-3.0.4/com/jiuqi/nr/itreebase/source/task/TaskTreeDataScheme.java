/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.itreebase.source.task;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeDataSource;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.impl.TreeNodeProviderImpl;
import com.jiuqi.nr.itreebase.source.task.TaskNodeDataSource;
import com.jiuqi.nr.itreebase.source.task.TaskTreeNodeBuilder;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class TaskTreeDataScheme
implements ITreeDataSource {
    public static final String SOURCE_ID = "nr.task.define.tree.selector";
    @Resource
    private IRunTimeViewController rtViewService;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;

    @Override
    public String getSourceId() {
        return SOURCE_ID;
    }

    @Override
    public INodeDataSource getNodeDataSource(ITreeContext context) {
        return new TaskNodeDataSource(this.rtViewService, this.authorityProvider, context);
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
        List taskDefines;
        Object fliterNoWorkflowObj;
        IBaseNodeData actionNode = context.getActionNode();
        boolean fliterNoWorkflow = false;
        JSONObject customVariable = context.getCustomVariable();
        if (customVariable != null && !customVariable.isEmpty() && (fliterNoWorkflowObj = customVariable.get("fliterNoWorkflow")) != null && fliterNoWorkflowObj instanceof Boolean) {
            fliterNoWorkflow = (Boolean)fliterNoWorkflowObj;
        }
        if (actionNode == null && context.isSelectFirstNode() && (taskDefines = this.rtViewService.listAllTask()) != null && !taskDefines.isEmpty()) {
            for (TaskDefine task : taskDefines) {
                TaskFlowsDefine flowsSetting;
                if (fliterNoWorkflow && (flowsSetting = task.getFlowsSetting()) != null && flowsSetting.getFlowsType().equals((Object)FlowsType.NOSTARTUP) || !this.authorityProvider.canReadTask(task.getKey())) continue;
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

