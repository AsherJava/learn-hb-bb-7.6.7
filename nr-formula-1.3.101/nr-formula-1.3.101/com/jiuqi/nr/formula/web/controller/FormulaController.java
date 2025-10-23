/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.deploy.common.ParamDeployException
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  com.jiuqi.util.OrderGenerator
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.web.controller;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.formula.dto.FormulaAuditTypeDTO;
import com.jiuqi.nr.formula.dto.FormulaCycleTreeDTO;
import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import com.jiuqi.nr.formula.dto.FormulaFormTreeDTO;
import com.jiuqi.nr.formula.dto.ImportResult;
import com.jiuqi.nr.formula.exception.FormulaResourceErrorEnum;
import com.jiuqi.nr.formula.service.ICustomFormulaService;
import com.jiuqi.nr.formula.service.IFormulaConditionService;
import com.jiuqi.nr.formula.service.IFormulaResourceTreeService;
import com.jiuqi.nr.formula.service.IFormulaService;
import com.jiuqi.nr.formula.web.param.BasePM;
import com.jiuqi.nr.formula.web.param.ExpressionCheckParam;
import com.jiuqi.nr.formula.web.param.FormulaDataExportPM;
import com.jiuqi.nr.formula.web.param.FormulaExtPM;
import com.jiuqi.nr.formula.web.param.FormulaListPM;
import com.jiuqi.nr.formula.web.param.FormulaMovePM;
import com.jiuqi.nr.formula.web.param.FormulaQueryPM;
import com.jiuqi.nr.formula.web.param.FormulaSavePM;
import com.jiuqi.nr.formula.web.param.ImportPM;
import com.jiuqi.nr.formula.web.param.TreeLocationPM;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.formula.web.vo.FormulaDataVO;
import com.jiuqi.nr.formula.web.vo.FormulaEnvVO;
import com.jiuqi.nr.formula.web.vo.FormulaInitParamVO;
import com.jiuqi.nr.formula.web.vo.FormulaSearchParamVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import com.jiuqi.util.OrderGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/"})
@Api(value="\u4efb\u52a1\u8bbe\u8ba12.0\u516c\u5f0f\u7ba1\u7406")
public class FormulaController {
    private static final Logger logger = LoggerFactory.getLogger(FormulaController.class);
    @Autowired
    private IFormulaResourceTreeService formulaResourceTreeService;
    @Autowired
    private IFormulaService formulaService;
    @Autowired
    private IFormulaConditionService formulaConditionService;
    @Autowired
    private ICustomFormulaService customFormulaService;
    @Autowired
    private SystemIdentityService systemIdentityService;

    @ApiOperation(value="\u521d\u59cb\u5316\u516c\u5f0f\u7ba1\u7406\u73af\u5883")
    @GetMapping(value={"init/env/{taskKey}"})
    public FormulaEnvVO initEnv(@PathVariable String taskKey) {
        FormulaEnvVO env = new FormulaEnvVO();
        List<FormulaAuditTypeDTO> auditType = this.formulaService.listAllAuditType();
        boolean conditionExist = this.formulaConditionService.isConditionExist(taskKey);
        Boolean customFormula = this.customFormulaService.existCustomFormula();
        env.setAuditType(auditType);
        env.setEnableCondition(conditionExist);
        env.setEnableCustom(customFormula);
        ContextOrganization organization = NpContextHolder.getContext().getOrganization();
        if (organization != null) {
            env.setOwnUnit(organization.getCode());
        }
        env.setAdmin(this.systemIdentityService.isAdmin());
        return env;
    }

    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\u521d\u59cb\u5316\u62a5\u8868\u6811\u5f62")
    @GetMapping(value={"tree/init/{key}"})
    public List<UITreeNode<FormulaFormTreeDTO>> initTree(@PathVariable String key) throws JQException {
        try {
            return this.formulaResourceTreeService.initFormulaResourceTree(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_01, (Throwable)e);
        }
    }

    @PostMapping(value={"formula/data/list"})
    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\u754c\u9762\u67e5\u8be2\u516c\u5f0f")
    public FormulaDataVO listFormulaData(@RequestBody FormulaListPM param) {
        return this.formulaService.searchFormulaData(param);
    }

    @PostMapping(value={"formula/data/query"})
    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\u754c\u9762\u67e5\u8be2\u516c\u5f0f")
    public FormulaDataVO queryFormulaData(@RequestBody FormulaQueryPM param) {
        return this.formulaService.queryFormulaData(param);
    }

    @PostMapping(value={"formula/data/move"})
    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\u754c\u9762\u67e5\u8be2\u516c\u5f0f")
    public void move(@RequestBody @SFDecrypt FormulaMovePM param) {
        this.formulaService.moveFormulaData(param);
    }

    @PostMapping(value={"formula/data/save"})
    @ApiOperation(value="\u4fdd\u5b58\u516c\u5f0f")
    @TaskLog(operation="formula/data/save")
    public void saveFormulaData(@RequestBody @SFDecrypt FormulaSavePM pm) throws JQException {
        try {
            this.formulaService.saveFormulaData(pm);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_05, e.getMessage());
        }
    }

    @PostMapping(value={"formula/publish"})
    @TaskLog(operation="\u53d1\u5e03\u516c\u5f0f")
    public void publish(@RequestBody BasePM basePM) throws JQException {
        try {
            this.formulaService.publish(basePM.getFormKey(), basePM.getFormulaSchemeKey());
        }
        catch (ParamDeployException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_071, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_06, e.getMessage());
        }
    }

    @PostMapping(value={"formula/check"})
    public FormulaCheckResult formulaCheck(@RequestBody @SFDecrypt FormulaExtPM pm) throws JQException {
        try {
            return this.formulaService.formulaCheck(pm);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_11, (Throwable)e);
        }
    }

    @PostMapping(value={"expression/check"})
    public void expressionCheck(@RequestBody @SFDecrypt ExpressionCheckParam checkParam) throws JQException {
        try {
            this.formulaService.expressionCheck(checkParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_18, e.getMessage());
        }
    }

    @ApiOperation(value="\u6761\u4ef6\u6837\u5f0f\u6821\u9a8c")
    @PostMapping(value={"conditionStyle/check/{formScheme}"})
    public Collection<FormulaExtDTO> conditionStyleCheck(@PathVariable String formScheme, @RequestBody @SFDecrypt List<FormulaExtDTO> list) throws JQException {
        try {
            return this.formulaService.conditionStyleCheck(formScheme, list);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_11, (Throwable)e);
        }
    }

    @Deprecated
    @RequestMapping(value={"formula/init-param/get/{formKey}", "formula/init-param/get"})
    public FormulaInitParamVO formulaParamInit(@PathVariable(value="formKey", required=false) String formKey) {
        FormulaInitParamVO res = new FormulaInitParamVO();
        res.setOrder(OrderGenerator.newOrder());
        if ("BJ".equals(formKey)) {
            res.setFormCode("BJ");
        } else {
            String code = this.formulaService.getFormCodeByForm(formKey);
            res.setFormCode(code);
        }
        return res;
    }

    @PostMapping(value={"formula/data/export"})
    public void export(@RequestBody FormulaDataExportPM formulaDataExportPM, HttpServletResponse response) {
        this.formulaService.exportFormulaInForm(response, formulaDataExportPM);
    }

    @PostMapping(value={"formula/data/all-export"})
    public void allExport(@RequestBody FormulaDataExportPM formulaDataExportPM, HttpServletResponse response) {
        this.formulaService.exportFormulaInFormulaScheme(response, formulaDataExportPM);
    }

    @PostMapping(value={"formula/data/import"})
    public FileInfoDTO uploadFile(@RequestParam(value="file") MultipartFile file) throws JQException {
        try {
            return this.formulaService.uploadFormulaExcel(file);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_10, e.getMessage());
        }
    }

    @PostMapping(value={"formula/data/exe-import"})
    @TaskLog(operation="\u5bfc\u5165\u516c\u5f0f")
    public ImportResult executeImport(@RequestBody ImportPM importPM) throws JQException {
        try {
            return this.formulaService.importFormulaExcel(importPM);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_10, e.getMessage());
        }
    }

    @GetMapping(value={"formula/data/delete-import/{fileKey}"})
    public void deleteImport(@PathVariable String fileKey) throws JQException {
        try {
            this.formulaService.deleteUploadFile(fileKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_10, e.getMessage());
        }
    }

    @PostMapping(value={"formula/data/download-error"})
    public void downloadError(@RequestBody ImportResult result, HttpServletResponse response) throws JQException {
        try {
            this.formulaService.exportErrorExcel(result, response);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_10, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5168\u90e8\u516c\u5f0f\uff0c\u516c\u5f0f\u641c\u7d22")
    @PostMapping(value={"formula/search"})
    public FormulaSearchParamVO formulaSearch(@RequestBody FormulaSearchParamVO searchParamVO) {
        return (FormulaSearchParamVO)this.formulaService.searchFormula(searchParamVO);
    }

    @PostMapping(value={"formula/code/get/{formKey}"})
    public String getFormulaCode(@RequestBody Set<String> codes, @PathVariable String formKey) {
        return this.formulaService.getFormulaCode(codes, formKey);
    }

    @ApiOperation(value="\u751f\u6210\u516c\u5f0f\u8bf4\u660e")
    @PostMapping(value={"formula/description"})
    public List<FormulaExtDTO> getDescription(@RequestBody @SFDecrypt FormulaExtPM pm) throws JQException {
        try {
            return this.formulaService.generateFormulaDescription(pm.getFormulaSchemeKey(), pm.getItemList());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_12, (Throwable)e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u6811\u5b9a\u4f4d")
    @PostMapping(value={"tree/locate"})
    public List<UITreeNode<FormulaFormTreeDTO>> treeLocate(@RequestBody TreeLocationPM pm) throws JQException {
        try {
            return this.formulaResourceTreeService.initTreeAndLocate(pm.getFormulaSchemeKey(), pm.getFormKey());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_01, (Throwable)e);
        }
    }

    @GetMapping(value={"cycle/check/{formulaScheme}"})
    @ApiOperation(value="\u5faa\u73af\u516c\u5f0f\u68c0\u6d4b")
    public List<FormulaCycleTreeDTO> cycleCheck(@PathVariable String formulaScheme) throws JQException {
        try {
            return this.formulaService.checkFormulaCycle(formulaScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_13, (Throwable)e);
        }
    }

    @GetMapping(value={"cycle/export/{formulaScheme}"})
    public void exportCycle(@PathVariable String formulaScheme, HttpServletResponse response) {
        this.formulaService.exportCycle(response, formulaScheme);
    }
}

