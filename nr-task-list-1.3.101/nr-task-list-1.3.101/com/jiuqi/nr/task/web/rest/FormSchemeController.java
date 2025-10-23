/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IParamNoddlDeployController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.api.IParamNoddlDeployController;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.dto.FormSchemeDTO;
import com.jiuqi.nr.task.exception.FormSchemeException;
import com.jiuqi.nr.task.service.IFormSchemeService;
import com.jiuqi.nr.task.web.param.SchemeQueryParam;
import com.jiuqi.nr.task.web.vo.DefaultSchemeVO;
import com.jiuqi.nr.task.web.vo.FormSchemeItemVO;
import com.jiuqi.nr.task.web.vo.FormSchemeVO;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@ApiOperation(value="\u62a5\u8868\u65b9\u6848\u5b9a\u4e49")
@RequestMapping(value={"api/v1/formScheme"})
public class FormSchemeController {
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IParamNoddlDeployController paramNoddlDeployController;

    @ApiOperation(value="\u65b0\u5efa\u62a5\u8868\u65b9\u6848\u521d\u59cb\u5316")
    @RequestMapping(value={"/init/{taskKey}"}, method={RequestMethod.GET})
    public FormSchemeVO init(@PathVariable String taskKey) {
        return this.formSchemeService.init(taskKey);
    }

    @ApiOperation(value="\u521b\u5efa\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/create"}, method={RequestMethod.POST})
    @TaskLog(operation="\u65b0\u5efa\u62a5\u8868\u65b9\u6848")
    public FormSchemeItemVO create(@RequestBody FormSchemeVO formScheme) throws JQException {
        try {
            return this.formSchemeService.insert(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_INSERT_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/delete/{formSchemeKey}"}, method={RequestMethod.GET})
    @TaskLog(operation="\u5220\u9664\u62a5\u8868\u65b9\u6848")
    public String delete(@PathVariable String formSchemeKey) throws JQException {
        try {
            return this.formSchemeService.delete(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_DELETE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/get/{formSchemeKey}"}, method={RequestMethod.GET})
    public FormSchemeVO get(@PathVariable String formSchemeKey) throws JQException {
        FormSchemeVO formScheme;
        try {
            formScheme = this.formSchemeService.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_UPDATE_FAILED, e.getMessage());
        }
        return formScheme;
    }

    @ApiOperation(value="\u83b7\u53d6\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/getDefault/{taskKey}"}, method={RequestMethod.GET})
    public DefaultSchemeVO getDefaultScheme(@PathVariable String taskKey) throws JQException {
        try {
            return this.formSchemeService.getDefaultFormScheme(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_QUERY_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/update"}, method={RequestMethod.POST})
    @TaskLog(operation="\u66f4\u65b0\u62a5\u8868\u65b9\u6848")
    public FormSchemeItemVO update(@RequestBody FormSchemeVO formScheme) throws JQException {
        try {
            return this.formSchemeService.update(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_UPDATE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u62a5\u8868\u65b9\u6848\u4ee3\u7801")
    @RequestMapping(value={"/checkCode/{formSchemeKey}/{formSchemeCode}"}, method={RequestMethod.GET})
    public void formSchemeCodeCheck(@PathVariable String formSchemeKey, @PathVariable String formSchemeCode) throws JQException {
        try {
            this.formSchemeService.formSchemeCodeCheck(formSchemeKey, formSchemeCode);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_CODE_REPEAT, e.getMessage());
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u62a5\u8868\u65b9\u6848\u6807\u9898")
    @PostMapping(value={"/checkTitle"})
    public boolean formSchemeTitleCheck(@RequestBody FormSchemeVO formSchemeVO) {
        return this.formSchemeService.formSchemeTitleCheck(formSchemeVO);
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/list/{taskKey}"}, method={RequestMethod.GET})
    public List<FormSchemeItemVO> list(@PathVariable String taskKey) throws JQException {
        List<FormSchemeItemVO> formSchemeItems;
        try {
            formSchemeItems = this.formSchemeService.queryByTask(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_QUERY_FAILED, e.getMessage());
        }
        return formSchemeItems;
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/query"}, method={RequestMethod.POST})
    public List<FormSchemeDTO> query(@RequestBody SchemeQueryParam schemeQueryParam) throws JQException {
        List<FormSchemeDTO> formSchemeItems;
        try {
            formSchemeItems = this.formSchemeService.query(schemeQueryParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_QUERY_FAILED, e.getMessage());
        }
        return formSchemeItems;
    }

    @ApiOperation(value="\u67e5\u8be2\u662f\u5426\u8bbe\u7f6e\u65e0DDL\u6743\u9650\u914d\u7f6e")
    @GetMapping(value={"/no-ddl"})
    public Boolean enableNoDDL() throws JQException {
        try {
            return this.paramNoddlDeployController.enableNoDDL();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_SCHEDULED_NO_DDL, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u662f\u5426\u8bbe\u7f6e\u65e0DDL\u6743\u9650\u914d\u7f6e")
    @GetMapping(value={"/no-ddl/status"})
    public int getDDLStatus(@RequestParam String formScheme) throws JQException {
        try {
            return this.paramNoddlDeployController.getDDLStatus(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_SCHEDULED_NO_DDL, e.getMessage());
        }
    }

    @ApiOperation(value="\u65e0DDL\u751f\u6210\u9884\u53d1\u5e03")
    @GetMapping(value={"/no-ddl/pre-deploy"})
    public List<String> noDDLPreDeploy(@RequestParam String formScheme) throws JQException {
        try {
            return this.paramNoddlDeployController.preDeploy(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_SCHEDULED_NO_DDL2, e.getMessage());
        }
    }

    @ApiOperation(value="\u65e0DDL\u53d1\u5e03")
    @GetMapping(value={"/no-ddl/deploy"})
    public void noDDLDeploy(@RequestParam String formScheme) throws JQException {
        try {
            this.paramNoddlDeployController.deploy(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_SCHEDULED_NO_DDL1, e.getMessage());
        }
    }

    @ApiOperation(value="\u64a4\u9500\u53d1\u5e03")
    @GetMapping(value={"/no-ddl/cancel"})
    public void noDDLCancel(@RequestParam String formScheme) throws JQException {
        try {
            this.paramNoddlDeployController.unPreDeploy(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_SCHEDULED_NO_DDL3, e.getMessage());
        }
    }
}

