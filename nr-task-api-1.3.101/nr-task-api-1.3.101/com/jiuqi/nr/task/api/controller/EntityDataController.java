/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.api.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.exception.EntityException;
import com.jiuqi.nr.task.api.service.entity.IEntityDataQueryService;
import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;
import com.jiuqi.nr.task.api.service.entity.vo.EntityDataQueryVO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@ApiOperation(value="\u5b9e\u4f53\u6570\u636e\u67e5\u8be2")
@RequestMapping(value={"/api/v2/task/entity-data"})
public class EntityDataController {
    @Autowired
    private IEntityDataQueryService entityDataService;

    @ApiOperation(value="\u521d\u59cb\u5316\u5b9e\u4f53\u6570\u636e\u6811\u5f62")
    @GetMapping(value={"/tree/init/{entityId}"})
    public List<UITreeNode<EntityDataDTO>> initEntityTree(@PathVariable String entityId) throws JQException {
        try {
            return this.entityDataService.initEntityTree(entityId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.QUERY, e.getMessage());
        }
    }

    @ApiOperation(value="\u6309\u7167\u6307\u5b9a\u6570\u636e\u5b9a\u4f4d\u5b9e\u4f53\u6570\u636e\u6811\u5f62")
    @PostMapping(value={"/tree/children"})
    public List<UITreeNode<EntityDataDTO>> loadChildren(@RequestBody EntityDataQueryVO entityDataQueryVO) throws JQException {
        try {
            return this.entityDataService.loadChildren(entityDataQueryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.QUERY, e.getMessage());
        }
    }

    @ApiOperation(value="\u641c\u7d22\u5b9e\u4f53\u6570\u636e")
    @PostMapping(value={"/tree/search"})
    public List<EntityDataDTO> searchEntityData(@RequestBody EntityDataQueryVO entityDataQueryVO) throws JQException {
        try {
            return this.entityDataService.searchEntityData(entityDataQueryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.QUERY, e.getMessage());
        }
    }

    @ApiOperation(value="\u6309\u7167\u6307\u5b9a\u6570\u636e\u5b9a\u4f4d\u5b9e\u4f53\u6570\u636e\u6811\u5f62")
    @PostMapping(value={"/tree/location"})
    public List<UITreeNode<EntityDataDTO>> locationEntityDataTree(@RequestBody EntityDataQueryVO entityDataQueryVO) throws JQException {
        try {
            return this.entityDataService.locationEntityDataTree(entityDataQueryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.QUERY, e.getMessage());
        }
    }

    @ApiOperation(value="\u6307\u5b9a\u4e3b\u952e\u96c6\u5408\u83b7\u53d6\u5b9e\u4f53\u6570\u636e\u96c6")
    @PostMapping(value={"/data/query"})
    public List<EntityDataDTO> queryEntityData(@RequestBody EntityDataQueryVO entityDataQueryVO) throws JQException {
        try {
            return this.entityDataService.query(entityDataQueryVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.QUERY, e.getMessage());
        }
    }
}

