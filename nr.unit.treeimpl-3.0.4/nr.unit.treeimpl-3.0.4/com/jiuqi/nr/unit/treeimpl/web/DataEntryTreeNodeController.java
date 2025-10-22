/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.entity.engine.exception.EntityUpdateException
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.unit.treeimpl.web;

import com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.unit.treeimpl.web.request.LevelTreeExportParam;
import com.jiuqi.nr.unit.treeimpl.web.request.NodeDragParam;
import com.jiuqi.nr.unit.treeimpl.web.request.NodeModifyParam;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsCountInfo;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsInfo;
import com.jiuqi.nr.unit.treeimpl.web.service.IDataEntryTreeNodeService;
import com.jiuqi.nr.unit.treeimpl.web.service.impl.DataEntryTreeNodeDragService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/data-entry-unit-tree/node-data"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u5355\u4f4d\u6811\u8282\u70b9-API"})
public class DataEntryTreeNodeController {
    @Resource
    private IDataEntryTreeNodeService service;
    @Resource
    private DataEntryTreeNodeDragService dragService;

    @NRContextBuild
    @ApiOperation(value="\u5355\u4f4d\u6811-\u5bfc\u51fa\u7ea7\u6b21\u6811")
    @PostMapping(value={"/export-level-tree"})
    public void exportLevelTree(@Valid @RequestBody LevelTreeExportParam levelParam, HttpServletResponse response) {
        this.service.exportLevelTree(levelParam, response);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u7684\u6240\u5c5e\u6807\u8bb0")
    @PostMapping(value={"/inquery-node-tags"})
    public NodeTagsInfo inqueryNodeTags(@Valid @RequestBody NodeModifyParam param) {
        return this.service.inqueryNodeTags(param);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u7edf\u8ba1\u6807\u8bb0\u7684\u5355\u4f4d\u6570\u91cf")
    @PostMapping(value={"/count-tag-nodes"})
    public NodeTagsCountInfo conutTagNodes(@Valid @RequestBody RunTimeContextData contextData) {
        return this.service.countTagNodes(contextData);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u66f4\u65b0\u7ec8\u6b62\u586b\u62a5\u72b6\u6001")
    @PostMapping(value={"/save-terminal-state"})
    public Boolean saveTerminalState(@Valid @RequestBody NodeModifyParam param) {
        return this.service.saveTerminalState(param);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u62d6\u62fd\u6539\u53d8\u5355\u4f4d\u7ea7\u6b21\u548c\u8c03\u6574\u5355\u4f4d\u987a\u5e8f")
    @PostMapping(value={"/drag-node-change"})
    public Map<String, Object> dragNodeChange(@Valid @RequestBody NodeDragParam param) {
        try {
            return this.dragService.dragNodeChange(param);
        }
        catch (JQException | EntityUpdateException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            return new HashMap<String, Object>();
        }
    }
}

