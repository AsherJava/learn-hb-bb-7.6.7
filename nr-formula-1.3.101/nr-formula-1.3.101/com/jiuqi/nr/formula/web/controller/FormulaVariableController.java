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
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.formula.dto.VariableDTO;
import com.jiuqi.nr.formula.exception.FormulaResourceErrorEnum;
import com.jiuqi.nr.formula.service.IFormulaVariableService;
import com.jiuqi.nr.formula.utils.convert.VariableConvert;
import com.jiuqi.nr.formula.web.vo.VariableVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/"})
@Api(value="\u516c\u5f0f\u53d8\u91cf2.0")
public class FormulaVariableController {
    @Autowired
    private IFormulaVariableService formulaVariableService;

    @GetMapping(value={"formula-variable/list/{formSchemeKey}"})
    public List<VariableVO> listFormulaVariables(@PathVariable String formSchemeKey) {
        List<VariableDTO> list = this.formulaVariableService.listVariablesByFormScheme(formSchemeKey);
        list.sort(Comparator.comparing(VariableDTO::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
        return VariableConvert.dtoToVoList(list);
    }

    @PostMapping(value={"formula-variable/add"})
    @TaskLog(operation="\u6dfb\u52a0\u516c\u5f0f\u53d8\u91cf")
    public void addFormulaVariable(@RequestBody @SFDecrypt VariableVO variableVO) throws JQException {
        try {
            this.formulaVariableService.insertVariable(variableVO);
        }
        catch (NrDefinitionRuntimeException e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_15, e.getCause().getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_15, e.getMessage());
        }
    }

    @PostMapping(value={"formula-variable/update"})
    @TaskLog(operation="\u66f4\u65b0\u516c\u5f0f\u53d8\u91cf")
    public void updateFormulaVariable(@RequestBody @SFDecrypt VariableVO variableVO) {
        this.formulaVariableService.updateVariable(variableVO);
    }

    @GetMapping(value={"formula-variable/delete/{varKey}"})
    @TaskLog(operation="\u5220\u9664\u516c\u5f0f\u53d8\u91cf")
    public void delFormulaVariable(@PathVariable String varKey) {
        this.formulaVariableService.deleteVariable(varKey);
    }

    @PostMapping(value={"formula-variable/check"})
    public boolean checkFormulaVariable(@RequestBody @SFDecrypt VariableVO variableVO) {
        return this.formulaVariableService.existVariable(variableVO.getFormSchemeKey(), variableVO.getCode(), variableVO.getKey());
    }
}

