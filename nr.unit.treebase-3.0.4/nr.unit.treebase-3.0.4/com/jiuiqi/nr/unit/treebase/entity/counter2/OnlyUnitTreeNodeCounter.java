/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityAssist
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.counter2.AllChildAndNotChaENodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.DefaultUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.LeafAndNotChaNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.OnlyLeafNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.counter2.OptimizeUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityAssist;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Map;

public class OnlyUnitTreeNodeCounter
implements IUnitTreeNodeCounter {
    protected IUnitTreeContext context;
    protected UnitTreeNodeCountPloy nodeCountPloy;
    protected IUnitTreeContextWrapper contextWrapper;
    protected IUnitTreeEntityDataQuery entityDataQuery;

    public OnlyUnitTreeNodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IUnitTreeContextWrapper contextWrapper, UnitTreeNodeCountPloy nodeCountPloy) {
        this.context = context;
        this.nodeCountPloy = nodeCountPloy;
        this.contextWrapper = contextWrapper;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public Map<String, Integer> getRootNodeCountMap() {
        return this.getNodeCounter(this.context, this.entityDataQuery, this.contextWrapper, this.nodeCountPloy).getRootNodeCountMap();
    }

    @Override
    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData parent) {
        return this.getNodeCounter(this.context, this.entityDataQuery, this.contextWrapper, this.nodeCountPloy).getChildNodeCountMap(parent);
    }

    @Override
    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData locateNode) {
        return this.getNodeCounter(this.context, this.entityDataQuery, this.contextWrapper, this.nodeCountPloy).getTreeNodeCountMap(locateNode);
    }

    protected IUnitTreeNodeCounter getNodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IUnitTreeContextWrapper contextWrapper, UnitTreeNodeCountPloy nodeCountPloy) {
        IEntityAssist entityAssist = (IEntityAssist)SpringBeanUtils.getBean(IEntityAssist.class);
        TaskDefine taskDefine = context.getTaskDefine();
        IEntityRefer referBBLXEntity = contextWrapper.getBBLXEntityRefer(context.getEntityDefine());
        if (taskDefine != null && entityAssist.isDBModel(context.getEntityDefine().getId(), context.getPeriod(), taskDefine.getDateTime())) {
            return new OptimizeUnitTreeNodeCounter(context, entityDataQuery);
        }
        switch (nodeCountPloy) {
            case COUNT_OF_LEAF: {
                return new OnlyLeafNodeCounter(context, entityDataQuery);
            }
            case COUNT_OF_LEAF_AND_NOT_CHA_E: {
                return referBBLXEntity != null ? new LeafAndNotChaNodeCounter(context, entityDataQuery, referBBLXEntity) : new OnlyLeafNodeCounter(context, entityDataQuery);
            }
            case COUNT_OF_ALL_CHILD_AND_NOT_CHA_E: {
                return referBBLXEntity != null ? new AllChildAndNotChaENodeCounter(context, entityDataQuery, referBBLXEntity) : new DefaultUnitTreeNodeCounter(context, entityDataQuery);
            }
        }
        return new DefaultUnitTreeNodeCounter(context, entityDataQuery);
    }
}

