/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
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
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.form.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.task.form.form.dto.FormGroupDTO;
import com.jiuqi.nr.task.form.form.exception.FormException;
import com.jiuqi.nr.task.form.form.service.IFormGroupService;
import com.jiuqi.nr.task.form.form.vo.FormGroupVO;
import com.jiuqi.nr.task.form.service.IFormService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/form-group-designer/"})
@Api(tags={"\u62a5\u8868\u5206\u7ec4\u6a21\u5757"})
public class FormGroupController {
    @Autowired
    private IFormService formService;
    @Autowired
    private IFormGroupService formGroupService;

    @ApiOperation(value="\u65b0\u589e\u62a5\u8868\u5206\u7ec4")
    @PostMapping(value={"insert-form-group"})
    @TaskLog(operation="\u65b0\u589e\u62a5\u8868\u5206\u7ec4")
    public String insert(@RequestBody @SFDecrypt FormGroupDTO formGroupDTO) throws JQException {
        try {
            return this.formGroupService.insertGroup(formGroupDTO);
        }
        catch (NrDefinitionRuntimeException e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_001_1);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_001);
        }
    }

    @ApiOperation(value="\u4fee\u6539\u62a5\u8868\u5206\u7ec4")
    @PostMapping(value={"update-form-group"})
    @TaskLog(operation="\u4fee\u6539\u62a5\u8868\u5206\u7ec4")
    public void update(@RequestBody @SFDecrypt FormGroupDTO formGroupDTO) throws JQException {
        try {
            this.formGroupService.updateGroup(formGroupDTO);
        }
        catch (NrDefinitionRuntimeException e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_002_1);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_002);
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u62a5\u8868\u5206\u7ec4\u6807\u9898\u662f\u5426\u91cd\u590d")
    @PostMapping(value={"check-form-group"})
    public boolean checkFormGroup(@RequestBody FormGroupDTO formGroupDTO) throws JQException {
        try {
            return this.formGroupService.checkHasGroup(formGroupDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u5206\u7ec4")
    @PostMapping(value={"delete-form-group"})
    @TaskLog(operation="\u5220\u9664\u62a5\u8868\u5206\u7ec4")
    public void delete(@RequestBody FormGroupDTO formGroupDTO) throws JQException {
        try {
            this.formService.deleteGroup(formGroupDTO.getKey());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_003, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u62a5\u8868\u5206\u7ec4\uff0c0\u662f\u4e0a\u79fb\uff0c1\u662f\u4e0b\u79fb")
    @PostMapping(value={"move-form-group"})
    @TaskLog(operation="\u79fb\u52a8\u62a5\u8868\u5206\u7ec4\uff0c0\u662f\u4e0a\u79fb\uff0c1\u662f\u4e0b\u79fb")
    public void moveFormGroup(@RequestBody ItemOrderMoveDTO formGroupDTO) throws JQException {
        try {
            this.formGroupService.moveGroup(formGroupDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_004, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u62a5\u8868\u5206\u7ec4")
    @GetMapping(value={"query-all-form-group/{formSchemeKey}"})
    public List<FormGroupDTO> queryFormGroupByScheme(@PathVariable String formSchemeKey) throws JQException {
        try {
            return this.formGroupService.queryRoot(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_GROUP_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u5206\u7ec4")
    @GetMapping(value={"query-form-group/{formGroupKey}/{formSchemeKey}"})
    public FormGroupVO queryFormGroup(@PathVariable String formGroupKey, @PathVariable String formSchemeKey) throws JQException {
        FormGroupVO result = new FormGroupVO();
        if (StringUtils.hasText(formSchemeKey) && !formSchemeKey.equals("---")) {
            List<FormGroupDTO> formGroupDTOS = this.formGroupService.queryRoot(formSchemeKey);
            for (int i = 0; i < formGroupDTOS.size(); ++i) {
                if (!formGroupDTOS.get(i).getKey().equals(formGroupKey)) continue;
                result = FormGroupVO.getInstance(formGroupDTOS.get(i));
                result.setFirst(i == 0);
                result.setLast(i == formGroupDTOS.size() - 1);
                break;
            }
        } else {
            FormGroupDTO query = this.formGroupService.query(formGroupKey);
            result = FormGroupVO.getInstance(query);
        }
        return result;
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u5206\u7ec4")
    @GetMapping(value={"query-group/{formKey}"})
    public FormGroupDTO queryFormGroupByForm(@PathVariable String formKey) {
        return this.formGroupService.getFormGroupByFrom(formKey);
    }

    @ApiOperation(value="\u5206\u7ec4\u4e0b\u662f\u5426\u5b58\u5728\u62a5\u8868")
    @GetMapping(value={"exist-form/{formGroupKey}"})
    public boolean existFormInGroup(@PathVariable String formGroupKey) {
        return this.formGroupService.existFormInGroup(formGroupKey);
    }
}

