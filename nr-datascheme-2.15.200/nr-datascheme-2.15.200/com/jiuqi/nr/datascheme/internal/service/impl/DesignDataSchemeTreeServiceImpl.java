/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
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
public class DesignDataSchemeTreeServiceImpl
implements IDesignDataSchemeTreeService {
    @Autowired
    protected IDataSchemeTreeService<DataSchemeNode> treeService;
    @Autowired
    protected IDesignDataSchemeService designDataSchemeService;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected IPeriodEntityAdapter periodEntityAdapter;
    protected static final String[] DIM_PUB = new String[]{"DIM_PUB", "DIM_PUB", "\u516c\u5171\u7ef4\u5ea6\u5206\u7ec4"};

    @Override
    public List<ITree<DataSchemeNode>> getRootTree(String scheme, int interestType, NodeFilter filter) {
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
    public List<ITree<DataSchemeNode>> getChildTree(DataSchemeNode parent, int interestType, NodeFilter filter) {
        return this.treeService.getChildTree((INode)parent, interestType, filter);
    }

    @Override
    public List<ITree<DataSchemeNode>> getSpecifiedTree(DataSchemeNode node, String schemeKey, int interestType, NodeFilter filter) {
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

    private List<ITree<DataSchemeNode>> reBuildTree(List<ITree<DataSchemeNode>> rootTree, int interestType, NodeFilter filter) {
        ITree temp;
        LinkedList<ITree<DataSchemeNode>> queue = new LinkedList<ITree<DataSchemeNode>>(rootTree);
        while ((temp = (ITree)queue.poll()) != null) {
            DataSchemeNode data = (DataSchemeNode)temp.getData();
            if (data == null) continue;
            if (data.getType() == NodeType.SCHEME.getValue()) {
                ArrayList<ITree<DataSchemeNode>> children = temp.getChildren();
                if (children == null) {
                    children = new ArrayList<ITree<DataSchemeNode>>();
                    temp.setChildren(children);
                }
                List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(temp.getKey());
                this.dimNode(dataSchemeDimension, children, filter, interestType);
            }
            if (temp.getChildren() == null) continue;
            queue.addAll(temp.getChildren());
        }
        return rootTree;
    }

    public void dimNode(List<DesignDataDimension> dims, List<ITree<DataSchemeNode>> value, NodeFilter filter, int interestType) {
        DesignDataDimension unit = null;
        ArrayList<DesignDataDimension> scopes = new ArrayList<DesignDataDimension>();
        ArrayList<DesignDataDimension> otherDims = new ArrayList<DesignDataDimension>();
        for (DesignDataDimension dim : dims) {
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
        DataSchemeNodeDTO groupNode = new DataSchemeNodeDTO(DIM_PUB[0], DIM_PUB[1], DIM_PUB[2], NodeType.GROUP.getValue(), dataSchemeKey);
        ITree group = new ITree((INode)groupNode);
        group.setIcons(NodeIconGetter.getIconByType(NodeType.GROUP));
        value.add(0, (ITree<DataSchemeNode>)group);
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        group.setChildren(children);
        if (scopes.isEmpty()) {
            this.addNode(filter, unit, dataSchemeKey, children, interestType);
        } else {
            for (DesignDataDimension scope : scopes) {
                this.addNode(filter, scope, dataSchemeKey, children, interestType);
            }
        }
        this.buildMdInfo(value, (ITree<DataSchemeNode>)group, children);
        for (DesignDataDimension otherDim : otherDims) {
            this.addNode(filter, otherDim, dataSchemeKey, children, interestType);
        }
    }

    private void addNode(NodeFilter filter, DesignDataDimension unit, String dataSchemeKey, List<ITree<DataSchemeNode>> children, int interestType) {
        ITree<DataSchemeNode> tree = this.buildUnit(dataSchemeKey, unit, interestType);
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

    private void buildMdInfo(List<ITree<DataSchemeNode>> value, ITree<DataSchemeNode> group, List<ITree<DataSchemeNode>> children) {
        Iterator<ITree<DataSchemeNode>> iterator = value.iterator();
        while (iterator.hasNext()) {
            ITree<DataSchemeNode> next = iterator.next();
            if (NodeType.MD_INFO.getValue() != ((DataSchemeNode)next.getData()).getType()) continue;
            children.add(next);
            group.setExpanded(group.isExpanded() || next.isSelected());
            iterator.remove();
        }
    }

    private ITree<DataSchemeNode> buildUnit(String dataSchemeKey, DesignDataDimension unit, int interestType) {
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
        DataSchemeNodeDTO dimNode0 = new DataSchemeNodeDTO(dataSchemeKey + ":" + dimKey, code, title, NodeType.DIM.getValue(), dataSchemeKey);
        dimNode0.setData(unit);
        ITree dimNode = new ITree((INode)dimNode0);
        dimNode.setIcons(NodeIconGetter.getIconByType(dimNode0.getType()));
        dimNode.setLeaf((interestType & NodeType.ENTITY_ATTRIBUTE.getValue()) == 0);
        return dimNode;
    }
}

