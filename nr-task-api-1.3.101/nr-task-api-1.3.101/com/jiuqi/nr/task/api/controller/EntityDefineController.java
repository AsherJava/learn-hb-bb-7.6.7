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
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.api.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.exception.EntityException;
import com.jiuqi.nr.task.api.service.entity.IEntityDefineQueryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@ApiOperation(value="\u5b9e\u4f53\u5b9a\u4e49\u67e5\u8be2")
@RequestMapping(value={"/api/v2/task/entity-define"})
public class EntityDefineController {
    @Autowired
    private IEntityDefineQueryService entityDefineQueryService;

    @ApiOperation(value="\u521d\u59cb\u5316\u5b9e\u4f53\u6570\u636e\u6811\u5f62")
    @GetMapping(value={"/define/isolation/{entityId}"})
    public int isIsolation(@PathVariable String entityId) throws JQException {
        try {
            return this.entityDefineQueryService.isIsolation(entityId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)EntityException.QUERY, e.getMessage());
        }
    }
}

