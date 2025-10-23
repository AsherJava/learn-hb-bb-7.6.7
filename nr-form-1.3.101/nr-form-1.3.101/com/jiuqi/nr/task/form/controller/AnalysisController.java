/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.common.ExceptionEnum;
import com.jiuqi.nr.task.form.dto.AnalysisNode;
import com.jiuqi.nr.task.form.form.vo.FormVO;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.service.IAnalysisService;
import com.jiuqi.nr.task.form.vo.AnalysisInsertVo;
import com.jiuqi.nr.task.form.vo.ResultVo;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/form-manage/analysis"})
@Api(tags={"\u5206\u6790\u8868\u6269\u5c55"})
public class AnalysisController {
    @Autowired
    private IAnalysisService analysisService;

    @ApiOperation(value="\u5f02\u6b65\u67e5\u8be2\u5206\u6790\u8868\u6839\u6811\u578b")
    @GetMapping(value={"/tree-root"})
    public List<UITreeNode<AnalysisNode>> getRoot() {
        List<UITreeNode<AnalysisNode>> rootTree = this.analysisService.getRootTree();
        return rootTree;
    }

    @ApiOperation(value="\u540c\u6b65\u67e5\u8be2\u5206\u6790\u8868\u6839\u6811\u578b")
    @GetMapping(value={"/all-tree-root"})
    public List<UITreeNode<AnalysisNode>> getAllRoot() {
        List<UITreeNode<AnalysisNode>> rootTree = this.analysisService.getAllChildrenTree();
        return rootTree;
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u6790\u8868\u5b50\u6811\u578b")
    @GetMapping(value={"/tree-children/{parent}"})
    public List<UITreeNode<AnalysisNode>> getChildren(@PathVariable String parent) throws JQException {
        try {
            List<UITreeNode<AnalysisNode>> result = this.analysisService.getChildrenTree(parent, false);
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_008, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u6790\u8868")
    @PostMapping(value={"/search"})
    public List<AnalysisNode> search(@RequestBody FormVO formVo) throws JQException {
        ArrayList<AnalysisNode> search = new ArrayList();
        try {
            search = this.analysisService.search(formVo.getTitle());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_004, e.getMessage());
        }
        return search;
    }

    @ApiOperation(value="\u5b9a\u4f4d\u5206\u6790\u8868\u6811\u578b")
    @GetMapping(value={"/tree-location/{key}"})
    public List<UITreeNode<AnalysisNode>> location(@PathVariable String key) throws JQException {
        try {
            List<UITreeNode<AnalysisNode>> result = this.analysisService.locationTree(key);
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_008, e.getMessage());
        }
    }

    @ApiOperation(value="\u65b0\u589e\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868")
    @PostMapping(value={"/insert-analysis-form"})
    @TaskLog(operation="\u65b0\u589e\u5206\u6790\u8868")
    public ResultVo insertAnalysisForm(@RequestBody AnalysisInsertVo analysisInsertVo) throws JQException {
        try {
            return this.analysisService.insertForm(analysisInsertVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u6279\u91cf\u65b0\u589e\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868")
    @PostMapping(value={"/batch-insert-analysis-form"})
    @TaskLog(operation="\u6279\u91cf\u65b0\u589e\u5206\u6790\u8868")
    public List<ResultVo> batchInsertAnalysisForm(@RequestBody @SFDecrypt List<AnalysisInsertVo> analysisInsertVos) throws JQException {
        try {
            return this.analysisService.batchInsertForm(analysisInsertVos);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868")
    @PostMapping(value={"/update-analysis-form"})
    @TaskLog(operation="\u66f4\u65b0\u5206\u6790\u8868")
    public void updateAnalysisForm(@RequestBody @SFDecrypt FormVO formVo) throws JQException {
        try {
            this.analysisService.updateForm(formVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_006, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u8868\u6837")
    @GetMapping(value={"/style/{key}"})
    public FormStyleDTO getStyle(@PathVariable String key) throws JQException {
        try {
            FormStyleDTO style = this.analysisService.getStyle(key);
            return style;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_007, e.getMessage());
        }
    }
}

