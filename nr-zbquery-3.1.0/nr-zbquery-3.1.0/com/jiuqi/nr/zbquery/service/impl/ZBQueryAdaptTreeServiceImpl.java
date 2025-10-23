/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTableAdaptNode;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeAdaptNode;
import com.jiuqi.nr.zbquery.extend.IZBQueryAdaptTreeProvider;
import com.jiuqi.nr.zbquery.rest.param.AdaptTreeRequestParam;
import com.jiuqi.nr.zbquery.rest.vo.ResourceTreeAdaptNodeVo;
import com.jiuqi.nr.zbquery.service.ZBQueryAdaptTreeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class ZBQueryAdaptTreeServiceImpl
implements ZBQueryAdaptTreeService {
    private static final String NO_PROVIDER = "\u672a\u6536\u96c6\u5230IZBQueryAdaptTreeProvider\u7684\u5b9e\u73b0!";
    @Autowired
    private List<IZBQueryAdaptTreeProvider> adaptTreeProviders;
    private Map<String, IZBQueryAdaptTreeProvider> adaptTreeProviderMap;

    @Override
    public List<ITree<ResourceTreeAdaptNodeVo>> initTree(AdaptTreeRequestParam param) {
        Assert.notEmpty(this.adaptTreeProviders, NO_PROVIDER);
        ArrayList<ITree<ResourceTreeAdaptNodeVo>> adaptTreeNodeList = new ArrayList<ITree<ResourceTreeAdaptNodeVo>>();
        for (IZBQueryAdaptTreeProvider provider : this.adaptTreeProviders) {
            adaptTreeNodeList.addAll(this.wrapAdaptTreeNode(provider, provider.getRoots(param.getParam())));
        }
        return adaptTreeNodeList;
    }

    @Override
    public List<ITree<ResourceTreeAdaptNodeVo>> getChildren(AdaptTreeRequestParam param) {
        Assert.notEmpty(this.adaptTreeProviders, NO_PROVIDER);
        IZBQueryAdaptTreeProvider provider = this.getAdaptTreeProvider(param.getTreeType());
        return this.wrapAdaptTreeNode(provider, provider.getChildren(param.getParam()));
    }

    @Override
    public List<ResourceTableAdaptNode> getResourceFields(AdaptTreeRequestParam param) {
        Assert.notEmpty(this.adaptTreeProviders, NO_PROVIDER);
        return this.getAdaptTreeProvider(param.getTreeType()).getFields(param.getParam());
    }

    private List<ITree<ResourceTreeAdaptNodeVo>> wrapAdaptTreeNode(IZBQueryAdaptTreeProvider provider, List<ITree<ResourceTreeAdaptNode>> adaptNodes) {
        ArrayList<ITree<ResourceTreeAdaptNodeVo>> wrapAdaptTreeNodes = new ArrayList<ITree<ResourceTreeAdaptNodeVo>>();
        if (!CollectionUtils.isEmpty(adaptNodes)) {
            adaptNodes.forEach(node -> wrapAdaptTreeNodes.add(this.transferTreeNodeVo(provider, (ITree<ResourceTreeAdaptNode>)node)));
        }
        return wrapAdaptTreeNodes;
    }

    private ITree<ResourceTreeAdaptNodeVo> transferTreeNodeVo(IZBQueryAdaptTreeProvider provider, ITree<ResourceTreeAdaptNode> adaptNode) {
        ITree transferAdaptNode = new ITree((INode)new ResourceTreeAdaptNodeVo(provider.getTreeType(), (ResourceTreeAdaptNode)adaptNode.getData()));
        transferAdaptNode.setLeaf(adaptNode.isLeaf());
        transferAdaptNode.setIcons(adaptNode.getIcons());
        List children = adaptNode.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(c -> transferAdaptNode.appendChild(this.transferTreeNodeVo(provider, (ITree<ResourceTreeAdaptNode>)c)));
        }
        return transferAdaptNode;
    }

    private IZBQueryAdaptTreeProvider getAdaptTreeProvider(String treeType) {
        this.checkProviderMapLoaded();
        return this.adaptTreeProviderMap.get(treeType);
    }

    private void checkProviderMapLoaded() {
        if (this.adaptTreeProviderMap == null) {
            this.adaptTreeProviderMap = new ConcurrentHashMap<String, IZBQueryAdaptTreeProvider>();
            for (IZBQueryAdaptTreeProvider provider : this.adaptTreeProviders) {
                this.adaptTreeProviderMap.put(provider.getTreeType(), provider);
            }
        }
    }
}

