/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.deploy.DeployPrivateFormulaService
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.formula.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.deploy.DeployPrivateFormulaService;
import com.jiuqi.nr.formula.dto.ImportResult;
import com.jiuqi.nr.formula.exception.CustomFormulaErrorEnum;
import com.jiuqi.nr.formula.exception.FormulaResourceErrorEnum;
import com.jiuqi.nr.formula.service.ICustomFormulaService;
import com.jiuqi.nr.formula.web.param.BasePM;
import com.jiuqi.nr.formula.web.param.FormulaDataExportPM;
import com.jiuqi.nr.formula.web.param.FormulaExtPM;
import com.jiuqi.nr.formula.web.param.FormulaListPM;
import com.jiuqi.nr.formula.web.param.FormulaMovePM;
import com.jiuqi.nr.formula.web.param.FormulaQueryPM;
import com.jiuqi.nr.formula.web.param.FormulaSavePM;
import com.jiuqi.nr.formula.web.param.ImportPM;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.formula.web.vo.FormulaDataVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/custom"})
@Api(value="\u4efb\u52a1\u8bbe\u8ba12.0\u516c\u5f0f\u7ba1\u7406")
public class CustomFormulaController {
    @Autowired
    private ICustomFormulaService customFormulaService;
    @Autowired
    private DeployPrivateFormulaService deployPrivateFormulaService;

    @PostMapping(value={"unit/tree/init"})
    public List<UITreeNode<EntityDataDTO>> initUnitTree(@RequestBody BasePM param) throws JQException {
        try {
            return this.customFormulaService.initUnitTree(param.getFormulaSchemeKey(), param.getFormKey());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CustomFormulaErrorEnum.QUERY_UNIT, e.getMessage());
        }
    }

    @GetMapping(value={"unit/tree/children/{taskKey}/{entityKey}"})
    public List<UITreeNode<EntityDataDTO>> loadChildren(@PathVariable String taskKey, @PathVariable String entityKey) throws JQException {
        try {
            return this.customFormulaService.loadChildren(taskKey, entityKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CustomFormulaErrorEnum.QUERY_UNIT, e.getMessage());
        }
    }

    @PostMapping(value={"data/list"})
    public FormulaDataVO listFormulaData(@RequestBody FormulaListPM param) throws JQException {
        if (StringUtils.hasLength(param.getUnit())) {
            return this.customFormulaService.searchFormulaData(param);
        }
        throw new JQException((ErrorEnum)CustomFormulaErrorEnum.DATA_LIST, "\u6ca1\u6709\u5f53\u524d\u7684\u5355\u4f4d\u4fe1\u606f");
    }

    @PostMapping(value={"data/query"})
    public FormulaDataVO queryFormulaData(@RequestBody FormulaQueryPM param) throws JQException {
        if (StringUtils.hasLength(param.getUnit())) {
            return this.customFormulaService.queryFormulaData(param);
        }
        throw new JQException((ErrorEnum)CustomFormulaErrorEnum.DATA_LIST, "\u6ca1\u6709\u5f53\u524d\u7684\u5355\u4f4d\u4fe1\u606f");
    }

    @PostMapping(value={"data/move"})
    public void moveFormulaData(@RequestBody FormulaMovePM param) throws JQException {
        if (!StringUtils.hasLength(param.getUnit())) {
            throw new JQException((ErrorEnum)CustomFormulaErrorEnum.DATA_LIST, "\u6ca1\u6709\u5f53\u524d\u7684\u5355\u4f4d\u4fe1\u606f");
        }
        this.customFormulaService.moveFormulaData(param);
    }

    @PostMapping(value={"data/save"})
    @ApiOperation(value="\u4fdd\u5b58\u79c1\u6709\u516c\u5f0f")
    @TaskLog(operation="data/save")
    public void saveFormulaData(@RequestBody FormulaSavePM pm) throws JQException {
        if (StringUtils.hasText(pm.getUnit())) {
            try {
                this.customFormulaService.saveFormulaData(pm);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_05, e.getMessage());
            }
            try {
                this.deployPrivateFormulaService.deploy(pm.getFormulaSchemeKey(), pm.getFormKey(), pm.getUnit());
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_06, e.getMessage());
            }
        }
        throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_05, "\u5355\u4f4d\u6807\u8bc6\u4e3a\u7a7a");
    }

    @ApiOperation(value="\u68c0\u67e5\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"data/check"})
    public FormulaCheckResult formulaCheck(@RequestBody FormulaExtPM pm) throws JQException {
        if (StringUtils.hasText(pm.getUnit())) {
            return this.customFormulaService.formulaCheck(pm);
        }
        throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_11, "\u5355\u4f4d\u6807\u8bc6\u4e3a\u7a7a");
    }

    @ApiOperation(value="\u68c0\u67e5\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"data/export"})
    public void export(@RequestBody FormulaDataExportPM formulaDataExportPM, HttpServletResponse response) {
        this.customFormulaService.exportFormulaInForm(response, formulaDataExportPM);
    }

    @ApiOperation(value="\u68c0\u67e5\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"data/exportAll"})
    public void allExport(@RequestBody FormulaDataExportPM formulaDataExportPM, HttpServletResponse response) {
        this.customFormulaService.exportFormulaInFormulaScheme(response, formulaDataExportPM);
    }

    @ApiOperation(value="\u68c0\u67e5\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"data/import"})
    @TaskLog(operation="\u68c0\u67e5\u79c1\u6709\u516c\u5f0f")
    public ImportResult executeImport(@RequestBody ImportPM importPM) throws JQException {
        try {
            return this.customFormulaService.importFormulaExcel(importPM);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_10, e.getMessage());
        }
    }
}

