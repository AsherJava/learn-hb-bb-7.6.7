/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.service.IRuntimeDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeDataSchemeTreeServiceImpl
implements IRuntimeDataSchemeTreeService {
    @Autowired
    protected IDataSchemeTreeService<RuntimeDataSchemeNode> treeService;
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected IPeriodEntityAdapter periodEntityAdapter;
    protected static final String[] DIM_PUB = new String[]{"DIM_PUB", "DIM_PUB", "\u516c\u5171\u7ef4\u5ea6\u5206\u7ec4"};

    @Override
    public List<ITree<RuntimeDataSchemeNode>> getRootTree(String scheme, int interestType, NodeFilter filter) {
        boolean contDim = false;
        if ((interestType & NodeType.DIM.getValue()) != 0) {
            interestType ^= NodeType.DIM.getValue();
            contDim = true;
        }
        List rootTree = this.treeService.getRootTree(scheme, interestType, filter);
        if (contDim) {
            return this.reBuildTree(rootTree, interestType, filter);
        }
        return rootTree;
    }

    @Override
    public List<ITree<RuntimeDataSchemeNode>> getChildTree(RuntimeDataSchemeNode parent, int interestType, NodeFilter filter) {
        return this.treeService.getChildTree((INode)parent, interestType, filter);
    }

    @Override
    public List<ITree<RuntimeDataSchemeNode>> getSpecifiedTree(RuntimeDataSchemeNode node, String schemeKey, int interestType, NodeFilter filter) {
        boolean contDim = false;
        if ((interestType & NodeType.DIM.getValue()) != 0) {
            interestType ^= NodeType.DIM.getValue();
            contDim = true;
        }
        List specifiedTree = this.treeService.getSpecifiedTree((INode)node, schemeKey, interestType, filter);
        if (contDim) {
            return this.reBuildTree(specifiedTree, interestType, filter);
        }
        return specifiedTree;
    }

    private List<ITree<RuntimeDataSchemeNode>> reBuildTree(List<ITree<RuntimeDataSchemeNode>> rootTree, int interestType, NodeFilter filter) {
        ITree temp;
        LinkedList<ITree<RuntimeDataSchemeNode>> queue = new LinkedList<ITree<RuntimeDataSchemeNode>>(rootTree);
        while ((temp = (ITree)queue.poll()) != null) {
            RuntimeDataSchemeNode data = (RuntimeDataSchemeNode)temp.getData();
            if (data == null) continue;
            if (data.getType() == NodeType.SCHEME.getValue()) {
                ArrayList<ITree<RuntimeDataSchemeNode>> children = temp.getChildren();
                if (children == null) {
                    children = new ArrayList<ITree<RuntimeDataSchemeNode>>();
                    temp.setChildren(children);
                }
                List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(temp.getKey());
                this.dimNode(dataSchemeDimension, children, interestType, filter);
            }
            if (temp.getChildren() == null) continue;
            queue.addAll(temp.getChildren());
        }
        return rootTree;
    }

    public void dimNode(List<DataDimension> dims, List<ITree<RuntimeDataSchemeNode>> value, int interestType, NodeFilter filter) {
        DataDimension unit = null;
        ArrayList<DataDimension> scopes = new ArrayList<DataDimension>();
        ArrayList<DataDimension> otherDims = new ArrayList<DataDimension>();
        for (DataDimension dim : dims) {
            DimensionType dimensionType = dim.getDimensionType();
            if (DimensionType.UNIT == dimensionType) {
                unit = dim;
                continue;
            }
            if (DimensionType.UNIT_SCOPE == dimensionType) {
                scopes.add(dim);
                continue;
            }
            otherDims.add(dim);
        }
        if (unit == null) {
            return;
        }
        String dataSchemeKey = unit.getDataSchemeKey();
        RuntimeDataSchemeNodeDTO groupNode = new RuntimeDataSchemeNodeDTO(DIM_PUB[0], DIM_PUB[1], DIM_PUB[2], NodeType.GROUP.getValue(), dataSchemeKey);
        ITree group = new ITree((INode)groupNode);
        group.setIcons(NodeIconGetter.getIconByType(NodeType.GROUP));
        value.add(0, (ITree<RuntimeDataSchemeNode>)group);
        ArrayList<ITree<RuntimeDataSchemeNode>> children = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        group.setChildren(children);
        if (scopes.isEmpty()) {
            this.addNode(filter, unit, dataSchemeKey, children, interestType);
        } else {
            for (DataDimension scope : scopes) {
                this.addNode(filter, scope, dataSchemeKey, children, interestType);
            }
        }
        this.buildMdInfo(value, (ITree<RuntimeDataSchemeNode>)group, children);
        for (DataDimension otherDim : otherDims) {
            this.addNode(filter, otherDim, dataSchemeKey, children, interestType);
        }
    }

    private void addNode(NodeFilter filter, DataDimension unit, String dataSchemeKey, List<ITree<RuntimeDataSchemeNode>> children, int interestType) {
        ITree<RuntimeDataSchemeNode> tree = this.buildUnit(dataSchemeKey, unit, interestType);
        if (tree != null) {
            boolean test = true;
            if (filter != null) {
                test = filter.test((Object)tree.getData());
            }
            if (test) {
                children.add(tree);
            }
        }
    }

    private void buildMdInfo(List<ITree<RuntimeDataSchemeNode>> value, ITree<RuntimeDataSchemeNode> group, List<ITree<RuntimeDataSchemeNode>> children) {
        Iterator<ITree<RuntimeDataSchemeNode>> iterator = value.iterator();
        while (iterator.hasNext()) {
            ITree<RuntimeDataSchemeNode> next = iterator.next();
            if (NodeType.MD_INFO.getValue() != ((RuntimeDataSchemeNode)next.getData()).getType()) continue;
            children.add(next);
            group.setExpanded(group.isExpanded() || next.isSelected());
            iterator.remove();
        }
    }

    private ITree<RuntimeDataSchemeNode> buildUnit(String dataSchemeKey, DataDimension unit, int interestType) {
        String title;
        String code;
        String dimKey = unit.getDimKey();
        DimensionType dimensionType = unit.getDimensionType();
        if (dimensionType == DimensionType.PERIOD) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(unit.getDimKey());
            if (periodEntity == null) {
                return null;
            }
            code = periodEntity.getCode();
            title = periodEntity.getTitle();
        } else {
            IEntityDefine define = this.entityMetaService.queryEntity(dimKey);
            if (define == null) {
                return null;
            }
            code = define.getCode();
            title = define.getTitle();
        }
        RuntimeDataSchemeNodeDTO dimNode0 = new RuntimeDataSchemeNodeDTO(dataSchemeKey + ":" + dimKey, code, title, NodeType.DIM.getValue(), dataSchemeKey);
        dimNode0.setData(unit);
        ITree dimNode = new ITree((INode)dimNode0);
        dimNode.setIcons(NodeIconGetter.getIconByType(dimNode0.getType()));
        dimNode.setLeaf((interestType & NodeType.ENTITY_ATTRIBUTE.getValue()) == 0);
        return dimNode;
    }
}

