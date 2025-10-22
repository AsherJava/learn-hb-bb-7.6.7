/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.simple.SimpleFormGroupObj;
import com.jiuqi.nr.designer.web.rest.vo.OrderVO;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class FormGroupController {
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private IDesignTimeViewController designTimeViewService;
    @Autowired
    private IDesignRestService designRestService;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u5206\u7ec4\u8be6\u7ec6\u4fe1\u606f")
    @GetMapping(value={"/form-group/{task-key}/{form-group-key}"})
    public FormGroupObject query(@PathVariable(value="task-key") String taskId, @PathVariable(value="form-group-key") String formGroupKey) throws JQException {
        try {
            DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskId);
            DesignFormGroupDefine formGroupDefine = this.nrDesignTimeController.queryFormGroup(formGroupKey);
            FormGroupObject formGroupObject = this.initParamObjPropertyUtil.transFormGroupObject(taskDefine, formGroupDefine);
            return formGroupObject;
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_175, (Throwable)e);
        }
    }

    @PostMapping(value={"stepSaveFormGroup"})
    @ApiOperation(value="\u4fdd\u5b58\u62a5\u8868\u5206\u7ec4")
    public FormGroupObject stepSaveFormGroup(@RequestBody @SFDecrypt FormGroupObject formGroupObject) throws JQException {
        String logTitle = "\u64cd\u4f5c\u62a5\u8868\u5206\u7ec4";
        String formGroupTitle = "\u672a\u77e5";
        try {
            formGroupTitle = formGroupObject.getTitle();
            logTitle = formGroupObject.isIsDeleted() ? "\u5220\u9664\u62a5\u8868\u5206\u7ec4" : (formGroupObject.isIsNew() ? "\u65b0\u589e\u62a5\u8868\u5206\u7ec4" : "\u4fee\u6539\u62a5\u8868\u5206\u7ec4");
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(formGroupObject.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.stepSaveService.saveFormGroup(formGroupObject);
            NrDesignLogHelper.log(logTitle, formGroupTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return formGroupObject;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formGroupTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formGroupTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_121, (Throwable)e);
        }
    }

    @PostMapping(value={"saveFormGroupOrder"})
    @ApiOperation(value="\u4fdd\u5b58\u62a5\u8868\u5206\u7ec4\u7684order")
    public void saveFormGroup(@RequestBody OrderVO order) throws JQException {
        try {
            String[] formKeys = order.getKeys();
            for (int i = 0; i < formKeys.length; ++i) {
                this.stepSaveService.saveChangeFormGroupOrder(formKeys[i], order.getOrders() != null && order.getOrders().length > i ? order.getOrders()[i] : OrderGenerator.newOrder());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_152, (Throwable)e);
        }
    }

    @GetMapping(value={"delete-form-group/{groupKey}"})
    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u5206\u7ec4")
    public void deleteFormGroup(@PathVariable(value="groupKey") String groupKey) throws JQException {
        String logTitle = "\u5220\u9664\u62a5\u8868\u5206\u7ec4";
        String groupTitle = "\u672a\u77e5";
        String groupCode = "\u672a\u77e5";
        try {
            DesignFormGroupDefine groupDefine = this.designTimeViewService.queryFormGroup(groupKey);
            groupTitle = groupDefine.getTitle();
            groupCode = groupDefine.getCode();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(groupDefine.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            boolean sameServeCode = this.serveCodeService.isSameServeCode(groupDefine.getOwnerLevelAndId());
            if (!sameServeCode) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_173);
            }
            this.iDesignFormCopyService.deleteCopyFormInfosbyGroup(groupKey);
            this.designTimeViewService.deleteFormGroup(groupKey, true);
            NrDesignLogHelper.log(logTitle, groupCode + "|" + groupTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, groupCode + "|" + groupTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, groupCode + "|" + groupTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_153, (Throwable)e);
        }
    }

    @GetMapping(value={"delete-form-group/without_data/{groupKey}"})
    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u5206\u7ec4\u4e0d\u5220\u9664\u6570\u636e")
    public void deleteFormGroupWithoutData(@PathVariable(value="groupKey") String groupKey) throws JQException {
        String logTitle = "\u5220\u9664\u62a5\u8868\u5206\u7ec4";
        String groupTitle = "\u672a\u77e5";
        String groupCode = "\u672a\u77e5";
        try {
            DesignFormGroupDefine groupDefine = this.designTimeViewService.queryFormGroup(groupKey);
            groupTitle = groupDefine.getTitle();
            groupCode = groupDefine.getCode();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(groupDefine.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            boolean sameServeCode = this.serveCodeService.isSameServeCode(groupDefine.getOwnerLevelAndId());
            if (!sameServeCode) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_173);
            }
            this.designTimeViewService.deleteFormGroup(groupKey, false);
            NrDesignLogHelper.log(logTitle, groupCode + "|" + groupTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, groupCode + "|" + groupTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, groupCode + "|" + groupTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_153, (Throwable)e);
        }
    }

    @GetMapping(value={"/simple-form-group-tree/{form-scheme-key}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u5206\u7ec4\u548c\u62a5\u8868\u7b80\u5316\u6811\u5f62")
    public List<SimpleFormGroupObj> simpleFormGroupTree(@PathVariable(value="form-scheme-key") String formSchemeKey) throws JQException {
        return this.designRestService.simpleFormGroupTree(formSchemeKey);
    }
}

