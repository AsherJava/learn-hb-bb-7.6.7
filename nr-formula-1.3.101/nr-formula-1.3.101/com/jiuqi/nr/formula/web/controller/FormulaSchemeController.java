/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.deploy.common.ParamDeployException
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.formula.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import com.jiuqi.nr.formula.exception.FormulaResourceErrorEnum;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.formula.utils.ErrorWrapper;
import com.jiuqi.nr.formula.web.param.ItemMovePM;
import com.jiuqi.nr.formula.web.vo.FormulaSchemeVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/scheme/"})
@Api(value="\u4efb\u52a1\u8bbe\u8ba12.0\u516c\u5f0f\u65b9\u6848\u7ba1\u7406")
public class FormulaSchemeController {
    @Autowired
    private IFormulaSchemeService formulaSchemeService;

    @GetMapping(value={"/publish/{key}"})
    @TaskLog(operation="\u53d1\u5e03\u516c\u5f0f\u65b9\u6848")
    public void publish(@PathVariable String key) throws JQException {
        try {
            this.formulaSchemeService.publishFormulaScheme(key);
        }
        catch (ParamDeployException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_071, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_07, e.getMessage());
        }
    }

    @PostMapping(value={"/add"})
    @TaskLog(operation="\u65b0\u589e\u516c\u5f0f\u65b9\u6848")
    public void insertFormulaScheme(@RequestBody FormulaSchemeVO formulaSchemeVO) throws JQException {
        try {
            this.formulaSchemeService.insertFormulaScheme(formulaSchemeVO);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @GetMapping(value={"/delete/{formulaSchemeKey}"})
    @TaskLog(operation="\u5220\u9664\u516c\u5f0f\u65b9\u6848")
    public void deleteFormulaScheme(@PathVariable String formulaSchemeKey) throws JQException {
        try {
            this.formulaSchemeService.deleteFormulaScheme(formulaSchemeKey);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @PostMapping(value={"/update"})
    @TaskLog(operation="\u66f4\u65b0\u516c\u5f0f\u65b9\u6848")
    public void updateFormulaScheme(@RequestBody FormulaSchemeVO formulaSchemeVO) throws JQException {
        try {
            this.formulaSchemeService.updateFormulaScheme(formulaSchemeVO);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @PostMapping(value={"/copy"})
    @TaskLog(operation="\u590d\u5236\u516c\u5f0f\u65b9\u6848")
    public void copyFormulaScheme(@RequestBody FormulaSchemeVO formulaScheme) throws JQException {
        try {
            this.formulaSchemeService.copyFormulaScheme(formulaScheme);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @GetMapping(value={"/default/{formulaSchemeKey}"})
    public void defaultFormulaScheme(@PathVariable String formulaSchemeKey) throws JQException {
        try {
            this.formulaSchemeService.defaultFormulaScheme(formulaSchemeKey);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @PostMapping(value={"/move"})
    @TaskLog(operation="\u79fb\u52a8\u516c\u5f0f\u65b9\u6848")
    public void moveScheme(@RequestBody ItemMovePM pm) throws JQException {
        try {
            this.formulaSchemeService.moveFormulaScheme(pm);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @GetMapping(value={"default/insert"})
    public void insertDefaultScheme(@RequestParam String formSchemeKey) throws JQException {
        try {
            this.formulaSchemeService.insertDefaultFormulaScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @PostMapping(value={"scheme-title-check"})
    @ApiOperation(value="\u6821\u9a8c\u516c\u5f0f\u65b9\u6848\u540d\u79f0\u91cd\u590d")
    public boolean checkFormulaSchemeTitle(@RequestBody FormulaSchemeVO formulaSchemeVO) throws JQException {
        try {
            return this.formulaSchemeService.checkFormulaScheme(formulaSchemeVO);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @GetMapping(value={"query/{formulaSchemeKey}"})
    @ApiOperation(value="\u6821\u9a8c\u516c\u5f0f\u65b9\u6848\u540d\u79f0\u91cd\u590d")
    public FormulaSchemeDTO queryFormulaScheme(@PathVariable String formulaSchemeKey) throws JQException {
        try {
            return this.formulaSchemeService.getFormulaScheme(formulaSchemeKey);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @GetMapping(value={"list/report/{formSchemeKey}"})
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u5217\u8868")
    public List<FormulaSchemeDTO> listReportFormulaScheme(@PathVariable String formSchemeKey) throws JQException {
        try {
            return this.formulaSchemeService.listReportFormulaSchemeByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }

    @GetMapping(value={"list/efdc/{formSchemeKey}"})
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u5217\u8868")
    public List<FormulaSchemeDTO> listEfdcFormulaScheme(@PathVariable String formSchemeKey) throws JQException {
        try {
            return this.formulaSchemeService.listEFDCFormulaSchemeByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }
}

