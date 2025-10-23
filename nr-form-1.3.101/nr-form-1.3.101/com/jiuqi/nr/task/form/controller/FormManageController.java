/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.task.api.aop.TaskLog
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
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.form.common.FomeTypeTitle;
import com.jiuqi.nr.task.form.controller.dto.DeleteFormParam;
import com.jiuqi.nr.task.form.controller.vo.CopyFormVO;
import com.jiuqi.nr.task.form.controller.vo.FormTreeParam;
import com.jiuqi.nr.task.form.dto.DeleteFormDTO;
import com.jiuqi.nr.task.form.dto.FormExtDTO;
import com.jiuqi.nr.task.form.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormItemDTO;
import com.jiuqi.nr.task.form.form.exception.FormException;
import com.jiuqi.nr.task.form.form.service.IFormDefineService;
import com.jiuqi.nr.task.form.form.vo.FormVO;
import com.jiuqi.nr.task.form.service.IFormService;
import com.jiuqi.nr.task.form.util.ExceptionUtils;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/form-manage"})
@Api(tags={"\u8868\u5355\u7ba1\u7406"})
public class FormManageController {
    @Autowired
    private IFormService formService;
    @Autowired
    private IFormDefineService formDefineService;

    @ApiOperation(value="\u67e5\u8be2\u8868\u5355\u6269\u5c55\u9879")
    @GetMapping(value={"/queryExt/{formSchemeKey}"})
    public List<FormExtDTO> queryExt(@PathVariable String formSchemeKey) {
        return this.formService.queryFormExt(formSchemeKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u8be6\u60c5")
    @GetMapping(value={"/query/task/{formSchemeKey}"})
    public Map<String, String> queryTaskInfo(@PathVariable String formSchemeKey) {
        return this.formService.queryTaskInfo(formSchemeKey);
    }

    @ApiOperation(value="\u65b0\u589e\u62a5\u8868")
    @PostMapping(value={"insert-form"})
    @TaskLog(operation="\u65b0\u589e\u62a5\u8868")
    public String insertForm(@RequestBody @SFDecrypt FormVO formVo) throws JQException {
        try {
            return this.formService.insertForm(formVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_007, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868")
    @PostMapping(value={"delete-form"})
    @TaskLog(operation="\u5220\u9664\u62a5\u8868")
    public DeleteFormDTO deleteForm(@RequestBody DeleteFormParam deleteFormParam) throws JQException {
        try {
            return this.formService.deleteForm(deleteFormParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_004, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u5173\u8054\u7684\u57fa\u7840\u6570\u636e")
    @PostMapping(value={"delete-form-basedata"})
    @TaskLog(operation="\u5220\u9664\u62a5\u8868\u5173\u8054\u7684\u57fa\u7840\u6570\u636e")
    public void deleteFormBaseData(@RequestBody DeleteFormParam deleteFormParam) throws JQException {
        try {
            this.formService.deleteFormAndField(deleteFormParam);
            this.formService.deleteFormBaseData(deleteFormParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_010, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868")
    @GetMapping(value={"get-form/{formKey}"})
    public FormVO getForm(@PathVariable String formKey) throws JQException {
        try {
            FormDTO formDTO = this.formDefineService.getForm(formKey);
            FormVO instance = FormVO.getInstance(formDTO);
            instance.setFormTypeString(FomeTypeTitle.forTitle(instance.getFormType().getValue()));
            return instance;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_003, e.getMessage());
        }
    }

    @ApiOperation(value="\u6a21\u7cca\u641c\u7d22\u62a5\u8868")
    @GetMapping(value={"fuzzy-search"})
    public List<DesignFormDefine> fuzzyForm(@RequestBody FormVO formVo) throws JQException {
        try {
            return this.formDefineService.getFormFuzzy(formVo.getFormScheme(), formVo.getTitle());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_003, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u62a5\u8868")
    @PostMapping(value={"update-form"})
    @TaskLog(operation="\u4fee\u6539\u62a5\u8868")
    public void updateForm(@RequestBody @SFDecrypt FormVO formVo) throws JQException {
        try {
            this.formDefineService.updateForm(formVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_008, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u62a5\u8868")
    @PostMapping(value={"move-form"})
    @TaskLog(operation="\u79fb\u52a8\u62a5\u8868")
    public void moveForm(@RequestBody ItemOrderMoveDTO formVo) throws JQException {
        try {
            this.formDefineService.moveForm(formVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_009, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u62a5\u8868\u5206\u7ec4")
    @PostMapping(value={"change-form-group"})
    @TaskLog(operation="\u79fb\u52a8\u62a5\u8868\u5206\u7ec4")
    public void changeFormGroup(@RequestBody ItemOrderMoveDTO formGroupDTO) throws JQException {
        try {
            this.formDefineService.changeFormGroup(formGroupDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u662f\u5426\u5b58\u5728\u5c01\u9762\u4ee3\u7801")
    @GetMapping(value={"fmdm/exist/{formScheme}"})
    public Boolean existFMDM(@PathVariable String formScheme) throws JQException {
        try {
            return this.formDefineService.existFMDM(formScheme);
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u6a21\u7cca\u641c\u7d22\u62a5\u8868")
    @PostMapping(value={"form/fuzzy/search"})
    public List<FormItemDTO> fuzzySearch(@RequestBody FormTreeParam formTreeParam) throws JQException {
        try {
            return this.formService.fuzzySearch(formTreeParam);
        }
        catch (Exception e) {
            throw new JQException(ExceptionUtils.convert(e));
        }
    }

    @ApiOperation(value="\u6807\u8bc6\u6821\u9a8c")
    @GetMapping(value={"code-check/{formSchemeKey}/{formCode}/{formKey}"})
    public boolean codeCheck(@PathVariable String formSchemeKey, @PathVariable String formCode, @PathVariable String formKey) throws JQException {
        boolean check = this.formService.codeCheck(formSchemeKey, formCode, formKey);
        if (!check) {
            throw new JQException(ExceptionUtils.convert("\u6807\u8bc6\u91cd\u590d"));
        }
        return check;
    }

    @ApiOperation(value="\u62a5\u8868\u540d\u79f0\u6821\u9a8c")
    @PostMapping(value={"title-check"})
    public boolean checkFormTitle(@RequestBody FormVO formVo) throws JQException {
        return this.formService.checkFormTitle(formVo);
    }

    @ApiOperation(value="\u590d\u5236\u62a5\u8868")
    @PostMapping(value={"copyForm"})
    @TaskLog(operation="\u590d\u5236\u62a5\u8868")
    public boolean copyForm(@RequestBody CopyFormVO copyForm) {
        return this.formService.copyForm(copyForm);
    }
}

