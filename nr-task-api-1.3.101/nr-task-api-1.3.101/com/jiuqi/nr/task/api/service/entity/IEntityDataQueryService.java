/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.service.entity;

import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;
import com.jiuqi.nr.task.api.service.entity.dto.EntityQueryDTO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.List;

public interface IEntityDataQueryService {
    public List<UITreeNode<EntityDataDTO>> initEntityTree(String var1);

    public List<UITreeNode<EntityDataDTO>> loadChildren(EntityQueryDTO var1);

    public List<EntityDataDTO> searchEntityData(EntityQueryDTO var1);

    public List<UITreeNode<EntityDataDTO>> locationEntityDataTree(EntityQueryDTO var1);

    public List<EntityDataDTO> query(EntityQueryDTO var1);
}

