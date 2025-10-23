/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.api.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.exception.EntityException;
import com.jiuqi.nr.task.api.service.entity.IEntityTreeQueryService;
import com.jiuqi.nr.task.api.service.entity.vo.EntityDataQueryVO;
import com.jiuqi.nr.task.api.service.entity.vo.EntityTreeNode;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@ApiOperation(value="\u5b9e\u4f53\u6811\u5f62\u63a5\u53e3")
@RequestMapping(value={"/api/v2/task/entity-tree"})
public class EntityTreeController {
    @Autowired
    private IEntityTreeQueryService treeService;

    @ApiOperation(value="\u521d\u59cb\u5316\u5b9e\u4f53\u6570\u636e\u6811\u5f62")
    @PostMapping(value={"/tree-init"})
    public List<UITreeNode<EntityTreeNode>> init(@RequestBody EntityDataQueryVO queryVO) throws JQException {
        try {
            return this.treeService.treeInit(queryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.TREE_INIT, e.getMessage());
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u5b50\u8282\u70b9")
    @PostMapping(value={"/children-node"})
    public List<UITreeNode<EntityTreeNode>> loadChildren(@RequestBody EntityDataQueryVO queryVO) throws JQException {
        try {
            return this.treeService.loadChildren(queryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.TREE_LOAD_CHILDREN, e.getMessage());
        }
    }

    @ApiOperation(value="\u6811\u7684\u641c\u7d22")
    @PostMapping(value={"/search-node"})
    public List<EntityTreeNode> search(@RequestBody EntityDataQueryVO queryVO) throws JQException {
        ArrayList res = new ArrayList();
        try {
            return this.treeService.treeSearch(queryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.TREE_SEARCH, e.getMessage());
        }
    }

    @ApiOperation(value="\u6811\u7684\u5b9a\u4f4d")
    @PostMapping(value={"/location-node"})
    public List<UITreeNode<EntityTreeNode>> locate(@RequestBody EntityDataQueryVO queryVO) throws JQException {
        try {
            return this.treeService.treeLocate(queryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.TREE_LOCATE, e.getMessage());
        }
    }
}

