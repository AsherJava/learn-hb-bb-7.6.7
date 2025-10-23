/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.workflow2.todo.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition;
import com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoBaseContextImpl;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoFilterConditionContextImpl;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoTableDataContextImpl;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoTabsQuantityContextImpl;
import com.jiuqi.nr.workflow2.todo.service.TodoQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"nr/workflow2/todo"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u5f85\u529e\u4e8b\u9879\u670d\u52a1-\u5f85\u529e\u4e8b\u9879"})
public class TodoQueryController {
    @Resource
    private TodoQueryService todoListService;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5f85\u529e\u4e8b\u9879-\u9875\u7b7e\u4fe1\u606f\u67e5\u8be2")
    @PostMapping(value={"/tab-infos"})
    public TodoTabInfo getTodoTabs(@RequestBody @Valid TodoBaseContextImpl context) {
        return this.todoListService.getTodoTabInfos(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5f85\u529e\u4e8b\u9879-\u9875\u7b7e\u6570\u91cf\u67e5\u8be2")
    @PostMapping(value={"/tabs-quantity"})
    public Map<String, Integer> getTodoTabsQuantity(@RequestBody @Valid TodoTabsQuantityContextImpl context) {
        return this.todoListService.getTodoTabsQuantity(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5f85\u529e\u4e8b\u9879-\u7b5b\u9009\u6761\u4ef6\u67e5\u8be2")
    @PostMapping(value={"/filter-conditions"})
    public TodoFilterCondition getTodoFilterConditions(@RequestBody @Valid TodoFilterConditionContextImpl context) {
        return this.todoListService.getFilterConditions(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5f85\u529e\u4e8b\u9879\u670d\u52a1-\u8868\u683c\u6570\u636e\u67e5\u8be2")
    @PostMapping(value={"/table-data"})
    public TodoTableDataInfoImpl getTodoTableData(@RequestBody @Valid TodoTableDataContextImpl context) {
        return this.todoListService.getTodoTableData(context);
    }
}

