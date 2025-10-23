/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.service.entity;

import com.jiuqi.nr.task.api.service.entity.vo.EntityDataQueryVO;
import com.jiuqi.nr.task.api.service.entity.vo.EntityTreeNode;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.List;

public interface IEntityTreeQueryService {
    public List<UITreeNode<EntityTreeNode>> treeInit(EntityDataQueryVO var1);

    public List<UITreeNode<EntityTreeNode>> loadChildren(EntityDataQueryVO var1);

    public List<EntityTreeNode> treeSearch(EntityDataQueryVO var1);

    public List<UITreeNode<EntityTreeNode>> treeLocate(EntityDataQueryVO var1);
}

