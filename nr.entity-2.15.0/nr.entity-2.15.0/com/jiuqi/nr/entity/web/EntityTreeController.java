/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
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
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.service.EntityTreeService;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/entity-tree"})
@Api(tags={"\u5b9e\u4f53\u6811\u5f62\u7ec4\u4ef6"})
public class EntityTreeController {
    @Autowired
    private EntityTreeService entityTreeService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @PostMapping(value={"/tree-init"})
    public List<ITree<TreeNode>> initTree(@RequestBody TreeParam treeParam) {
        return this.entityTreeService.initTree(treeParam);
    }

    @PostMapping(value={"/children-node"})
    public List<ITree<TreeNode>> queryChildren(@RequestBody TreeParam treeParam) {
        return this.entityTreeService.getChildrenNodes(treeParam);
    }

    @PostMapping(value={"/search-node"})
    public List<TreeNode> searchNodes(@RequestBody TreeParam treeParam) {
        return this.entityTreeService.searchNodes(treeParam);
    }

    @PostMapping(value={"/location-node"})
    public List<ITree<TreeNode>> locationNode(@RequestBody TreeParam treeParam) {
        return this.entityTreeService.locationTreeNode(treeParam);
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

