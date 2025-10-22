/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 *  com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.facade.FormCopyObj;
import com.jiuqi.nr.designer.web.rest.vo.TaskSchemeGroupTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.TaskTreeNode;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class TaskLinkController {
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private IDesignRestService restService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IDesignParamCheckService iDesignParamCheckService;

    @PostMapping(value={"saveTaskLink"})
    @ApiOperation(value="\u4fdd\u5b58\u5173\u8054\u4efb\u52a1")
    public ArrayList<TaskLinkObject> saveTaskLinkge(@RequestBody TaskLinkObject[] taskLinkObjects) throws JQException {
        try {
            this.stepSaveService.saveTaskLinkage(taskLinkObjects);
            return this.stepSaveService.getTaskLinkObjBySchemeKey(taskLinkObjects[0].getCurrentFormSchemeKey());
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_011, (Throwable)e);
        }
    }

    @PostMapping(value={"deleteTaskLink"})
    @ApiOperation(value="\u5220\u9664\u5173\u8054\u4efb\u52a1")
    public ArrayList<TaskLinkObject> deleteTaskLinkge(@RequestBody TaskLinkObject taskLinkObjects) throws JQException {
        try {
            TaskLinkObject[] taskLinkObjectsArray = new TaskLinkObject[]{taskLinkObjects};
            this.stepSaveService.saveTaskLinkage(taskLinkObjectsArray);
            return this.stepSaveService.getTaskLinkObjBySchemeKey(taskLinkObjects.getCurrentFormSchemeKey());
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_011, (Throwable)e);
        }
    }

    @GetMapping(value={"getTaskLinkgeBySchemeKey/{schemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1")
    public ArrayList<TaskLinkObject> getTaskLinkgeBySchemeKey(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.stepSaveService.getTaskLinkObjBySchemeKey(schemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_157, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6a21\u7cca\u67e5\u8be2\u5173\u8054\u4efb\u52a1\u6811")
    @RequestMapping(value={"associated-task-search/table"}, method={RequestMethod.GET})
    public List<TaskTreeNode> searchNode(@RequestParam String keyword) throws JQException {
        return this.restService.getSearchSchemeResult(keyword);
    }

    @ApiOperation(value="\u5173\u8054\u4efb\u52a1\u6811\u5f62\u5b9a\u4f4d\u8282\u70b9")
    @RequestMapping(value={"position/reload"}, method={RequestMethod.POST})
    public List<ITree<TaskTreeNode>> fieldTreeReloadData(@RequestBody TaskTreeNode node) throws JQException {
        return this.restService.reloadTaskAndSchemes(node);
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u590d\u5236\u4efb\u52a1\u65b9\u6848\u5206\u7ec4\u6811")
    @RequestMapping(value={"/task-scheme-group-tree"}, method={RequestMethod.GET})
    public List<ITree<TaskSchemeGroupTreeNode>> getGroupTree() throws JQException {
        return this.restService.getGroupTree();
    }

    @ApiOperation(value="\u6a21\u7cca\u67e5\u8be2\u4efb\u52a1\u65b9\u6848\u5206\u7ec4\u6811")
    @RequestMapping(value={"task-scheme-group-search/table"}, method={RequestMethod.GET})
    public List<TaskSchemeGroupTreeNode> searchGroupNode(@RequestParam String keyword) throws JQException {
        return this.restService.getSearchGroupResult(keyword);
    }

    @ApiOperation(value="\u4efb\u52a1\u65b9\u6848\u5206\u7ec4\u6811\u5b9a\u4f4d\u8282\u70b9")
    @RequestMapping(value={"task-scheme-group-position/reload"}, method={RequestMethod.POST})
    public List<ITree<TaskSchemeGroupTreeNode>> groupTreeReloadData(@RequestBody TaskSchemeGroupTreeNode node) throws JQException {
        return this.restService.reloadGroupTree(node);
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u590d\u5236\u4efb\u52a1\u65b9\u6848\u5206\u7ec4\u6811")
    @RequestMapping(value={"/formcopy-tree/{taskKey}"}, method={RequestMethod.GET})
    public List<TreeObj> getFormCopyTree(@PathVariable(value="taskKey") String taskKey) throws JQException {
        return this.restService.getFormCopyTree(taskKey);
    }

    @ApiOperation(value="\u590d\u5236\u62a5\u8868")
    @RequestMapping(value={"/copyForm"}, method={RequestMethod.POST})
    public FormCopyObj copyForm(@RequestBody FormCopyObj formCopyObj) throws Exception {
        if (!StringUtils.hasLength(formCopyObj.getCopyFormKey())) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, "\u672a\u8bbe\u7f6e\u590d\u5236\u62a5\u8868");
        }
        DesignFormDefine formDefine = this.iDesignTimeViewController.querySoftFormDefine(formCopyObj.getCopyFormKey());
        if (null == formDefine) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, "\u590d\u5236\u62a5\u8868\u4e0d\u5b58\u5728");
        }
        if (!StringUtils.hasLength(formCopyObj.getFormGroup())) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, "\u672a\u8bbe\u7f6e\u590d\u5236\u5206\u7ec4");
        }
        DesignFormGroupDefine designFormGroupDefine = this.iDesignTimeViewController.queryFormGroup(formCopyObj.getFormGroup());
        if (null == designFormGroupDefine) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, "\u590d\u5236\u5206\u7ec4\u4e0d\u5b58\u5728");
        }
        if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) {
            List forms = this.iDesignTimeViewController.getAllFormDefinesInFormSchemeWithoutBinaryData(designFormGroupDefine.getFormSchemeKey());
            for (DesignFormDefine def : forms) {
                if (FormType.FORM_TYPE_NEWFMDM.getValue() != def.getFormType().getValue()) continue;
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, "\u76ee\u6807\u62a5\u8868\u65b9\u6848\u5df2\u5b58\u5728\u5c01\u9762\u4ee3\u7801\u8868\uff01");
            }
        }
        DesignFormDefineImpl designFormDefine = new DesignFormDefineImpl();
        designFormDefine.setKey(UUIDUtils.getKey());
        designFormDefine.setFormCode(formCopyObj.getFormCode());
        designFormDefine.setTitle(formCopyObj.getFormTitle());
        designFormDefine.setFormScheme(designFormGroupDefine.getFormSchemeKey());
        try {
            this.iDesignParamCheckService.checkFormTitleAndCode((DesignFormDefine)designFormDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, e.getMessage());
        }
        try {
            return this.restService.copyForm(formCopyObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0105, "\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458", (Throwable)e);
        }
    }
}

