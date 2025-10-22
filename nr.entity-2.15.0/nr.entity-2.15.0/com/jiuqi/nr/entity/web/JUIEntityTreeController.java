/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.entity.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.service.EntityTreeService;
import com.jiuqi.nr.entity.component.tree.vo.JUITreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v2/entity-tree"})
@Api(tags={"\u5b9e\u4f53\u6811\u5f62\u7ec4\u4ef6"})
public class JUIEntityTreeController {
    @Autowired
    private EntityTreeService entityTreeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private final Function<List<ITree<TreeNode>>, List<JUITreeNode>> convert = t -> t.stream().map(JUITreeNode::new).collect(Collectors.toList());

    @PostMapping(value={"/tree-init"})
    public List<JUITreeNode> initTree(@RequestBody TreeParam treeParam) {
        List<ITree<TreeNode>> iTrees = this.entityTreeService.initTree(treeParam);
        this.resetTitle(iTrees);
        return this.convert.apply(iTrees);
    }

    private void resetTitle(List<ITree<TreeNode>> itree) {
        itree.forEach(e -> e.setTitle(((TreeNode)e.getData()).getTitle()));
        for (ITree<TreeNode> nodeITree : itree) {
            List children = nodeITree.getChildren();
            if (CollectionUtils.isEmpty(children)) continue;
            this.resetTitle(children);
        }
    }

    @PostMapping(value={"/children-node"})
    public List<JUITreeNode> queryChildren(@RequestBody TreeParam treeParam) {
        List<ITree<TreeNode>> childrenNodes = this.entityTreeService.getChildrenNodes(treeParam);
        this.resetTitle(childrenNodes);
        return this.convert.apply(childrenNodes);
    }

    @PostMapping(value={"/search-node"})
    public List<JUITreeNode> searchNodes(@RequestBody TreeParam treeParam) {
        return this.entityTreeService.searchNodes(treeParam).stream().map(e -> {
            ITree tree = new ITree((INode)e);
            JUITreeNode juiTreeNode = new JUITreeNode((ITree<TreeNode>)tree);
            juiTreeNode.setLeaf(true);
            juiTreeNode.setTitle(e.getTitle());
            return juiTreeNode;
        }).collect(Collectors.toList());
    }

    @PostMapping(value={"/location-node"})
    public List<JUITreeNode> locationNode(@RequestBody TreeParam treeParam) {
        List<ITree<TreeNode>> iTrees = this.entityTreeService.locationTreeNode(treeParam);
        this.resetTitle(iTrees);
        return this.convert.apply(iTrees);
    }

    @GetMapping(value={"/query/{entityId}"})
    public IEntityDefine queryEntity(@PathVariable String entityId) throws JQException {
        return this.entityMetaService.queryEntity(entityId);
    }

    @PostMapping(value={"/allChildren"})
    public List<String> queryAllChildren(@RequestBody TreeParam treeParam) throws JQException {
        return this.entityTreeService.getAllChildrenNodes(treeParam);
    }
}

