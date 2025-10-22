/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.data.bean.CheckResultNode
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datacheck.entitytree;

import com.jiuqi.nr.datacheck.entitytree.service.ITreeCheckService;
import com.jiuqi.nr.datacheck.entitytree.vo.TreeCheckPM;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import nr.single.data.bean.CheckResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datacheck/tree-check"})
public class TreeCheckController {
    @Autowired
    private ITreeCheckService treeCheckService;

    @PostMapping(value={"/result/get"})
    @ApiOperation(value="\u67e5\u8be2\u6811\u5f62\u7ed3\u6784\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f", notes="\u67e5\u8be2\u6811\u5f62\u7ed3\u6784\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    public List<CheckResultNode> queryEntityTreeCheckResult(@RequestBody TreeCheckPM treeCheckPM) {
        return this.treeCheckService.queryRunCheckResult(treeCheckPM);
    }

    @PostMapping(value={"/result/export"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void queryEntityTreeCheckResultExport(HttpServletResponse response, @RequestBody TreeCheckPM treeCheckPM) {
        this.treeCheckService.exportRunCheckResult(response, treeCheckPM);
    }
}

