/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.controller.vo.FormTreeParam;
import com.jiuqi.nr.task.form.controller.vo.FormUITreeNode;
import com.jiuqi.nr.task.form.form.exception.FormException;
import com.jiuqi.nr.task.form.service.IFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/form-tree-designer"})
@Api(tags={"\u8868\u5355\u8bbe\u8ba1"})
public class FormTreeController {
    public static final String FORM_DESIGN_TREE_URL = "/api/v1/form-tree-designer";
    @Autowired
    IFormService formService;

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u62a5\u8868\u6839\u6811\u578b")
    @PostMapping(value={"/get/root/tree"})
    public List<UITreeNode<FormUITreeNode>> getFormTreeRoot(@RequestBody FormTreeParam formTreeParam) throws JQException {
        try {
            List<UITreeNode<FormUITreeNode>> formTreeRoot = this.formService.getFormTreeRoot(formTreeParam);
            return formTreeRoot;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_TREE_ERROE_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u5206\u7ec4\u67e5\u8be2\u62a5\u8868\u5b50\u6811\u578b")
    @PostMapping(value={"/get/child/tree"})
    public List<UITreeNode<FormUITreeNode>> getFormChildTree(@RequestBody FormTreeParam formTreeParam) throws JQException {
        try {
            List<UITreeNode<FormUITreeNode>> formTree = this.formService.getFormChildTree(formTreeParam);
            return formTree;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_TREE_ERROE_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u62a5\u8868\u6839\u6811\u578b")
    @PostMapping(value={"/location/tree"})
    public List<UITreeNode<FormUITreeNode>> locationFormTree(@RequestBody FormTreeParam formTreeParam) throws JQException {
        try {
            List<UITreeNode<FormUITreeNode>> formTree = this.formService.locationFormTree(formTreeParam);
            return formTree;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_TREE_ERROE_001, e.getMessage());
        }
    }
}

