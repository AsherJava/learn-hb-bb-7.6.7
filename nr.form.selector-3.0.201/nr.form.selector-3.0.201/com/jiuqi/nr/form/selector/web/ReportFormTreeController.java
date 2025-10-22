/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.form.selector.web;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.form.selector.context.FormTreeContextImpl;
import com.jiuqi.nr.form.selector.service.CheckerService;
import com.jiuqi.nr.form.selector.service.ReportFormTreeService;
import com.jiuqi.nr.form.selector.tree.IReportFormChecker;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/report-forms/tree"})
@Api(tags={"\u8868\u5355\u9009\u62e9\u5668-\u62a5\u8868\u6811-API"})
public class ReportFormTreeController {
    private static final Logger logger = LoggerFactory.getLogger(ReportFormTreeController.class);
    @Resource
    private ReportFormTreeService service;
    @Resource
    private CheckerService checkerService;

    @NRContextBuild
    @ApiOperation(value="\u521d\u59cb\u5316\u62a5\u8868\u6811")
    @PostMapping(value={"/form-tree-init"})
    @ResponseBody
    public IReturnObject<String> formTreeInit(@RequestBody FormTreeContextImpl context) {
        return null;
    }

    @NRContextBuild
    @ApiOperation(value="\u52a0\u8f7d-\u62a5\u8868\u6811")
    @PostMapping(value={"/loading-tree-data"})
    @ResponseBody
    public IReturnObject<List<ITree<IReportTreeNode>>> loadTreeData(@Valid @RequestBody FormTreeContextImpl context) {
        IReturnObject instance;
        List<ITree<IReportTreeNode>> rpTree = null;
        try {
            rpTree = this.service.getReportTree(context);
            instance = IReturnObject.getSuccessInstance(rpTree);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObject.getErrorInstance((String)("\u52a0\u8f7d-\u62a5\u8868\u6811\u5f02\u5e38\uff1a" + e.getMessage()), rpTree);
        }
        return instance;
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u6ce8\u518c\u7684\u7b5b\u9009\u5668")
    @GetMapping(value={"/loading-tree-data"})
    public IReturnObject<List<IReportFormChecker>> getAllChecker() {
        IReturnObject instance;
        List<IReportFormChecker> checkers = null;
        try {
            checkers = this.checkerService.getAllFormCheckerList();
            instance = IReturnObject.getSuccessInstance(checkers);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObject.getErrorInstance((String)("\u83b7\u53d6\u7b5b\u9009\u5668\u5f02\u5e38\uff1a" + e.getMessage()), checkers);
        }
        return instance;
    }
}

