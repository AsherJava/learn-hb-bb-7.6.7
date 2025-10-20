/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.domain.GrammarTreeVO
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.formula.WorkflowFormulaDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulasVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.domain.GrammarTreeVO;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.formula.WorkflowFormulaDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulasVO;
import com.jiuqi.va.workflow.domain.WorkflowFormulasCheckDTO;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaHandle;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow"})
public class WorkflowFormulaController {
    @Autowired
    private WorkflowFormulaSevice workflowFormulaSevice;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowSevice workflowSevice;

    @GetMapping(value={"/formulas"})
    public R<Map<String, List<FormulasVO>>> gatherFormulas() {
        return R.ok((Object)FunctionUtils.gatherFormulas());
    }

    @GetMapping(value={"/formulasFilter"})
    public R<String> gatherFormulasFilter() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("formulaMap", FunctionUtils.gatherFormulas());
        map.put("isPrivateCategory", FunctionUtils.getFilterCategories());
        return R.ok((Object)JSONUtil.toJSONString(map));
    }

    @PostMapping(value={"/formulas/check"})
    public R<List<GrammarTreeVO>> checkFormula(@RequestBody WorkflowFormulasCheckDTO paramDTO) {
        HashMap<String, Object> variableMap = new HashMap<String, Object>();
        WorkflowContext context = new WorkflowContext(variableMap);
        if (paramDTO.getProcessParams() != null) {
            paramDTO.getProcessParams().forEach(param -> context.put(param.getParamName(), new FormulaParam(param.getParamType(), null)));
        }
        ArrayList grammarTreeVOs = new ArrayList();
        StringBuffer msgs = new StringBuffer();
        paramDTO.getFormulas().stream().forEach(formula -> {
            try {
                String expression = formula.getExpression();
                IExpression iExpression = new WorkflowFormulaHandle().parse(context, expression, formula.getFormulaType());
                grammarTreeVOs.add(FormulaUtils.createGrammarTree((IASTNode)iExpression.getChild(0)));
            }
            catch (Exception e) {
                msgs.append(String.format(VaWorkFlowI18nUtils.getInfo("va.workflow.formulacheckerror"), formula.getExpression(), e.getMessage())).append("##");
            }
        });
        if (StringUtils.hasText(msgs.toString())) {
            return new R(1, msgs.substring(0, msgs.length() - 2), "", null);
        }
        return R.ok(grammarTreeVOs);
    }

    @PostMapping(value={"/formula/execute"})
    public com.jiuqi.va.domain.common.R execute(@RequestBody WorkflowFormulaDTO workflowFormulaDTO) {
        com.jiuqi.va.domain.common.R r = com.jiuqi.va.domain.common.R.ok();
        try {
            Map variables;
            List<ProcessParam> processParams;
            String bizCode = workflowFormulaDTO.getBizCode();
            ProcessDO processDO = null;
            if (StringUtils.hasText(bizCode)) {
                ProcessDTO processDTO = new ProcessDTO();
                processDTO.setBizcode(bizCode);
                processDO = this.vaWorkflowProcessService.get(processDTO);
                if (processDO == null) {
                    return com.jiuqi.va.domain.common.R.error();
                }
            }
            if ((processParams = workflowFormulaDTO.getProcessParams()) == null) {
                WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(processDO.getDefinekey(), Long.valueOf(processDO.getDefineversion().longValue()));
                WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
                ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)workflowModel.getPlugins().get(ProcessParamPlugin.class);
                ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
                processParams = processParamPluginDefine.getProcessParam();
                workflowFormulaDTO.setProcessParams(processParams);
            }
            if ((variables = workflowFormulaDTO.getVariables()) == null) {
                variables = this.workflowSevice.getVariables(processDO.getId());
                workflowFormulaDTO.setVariables(variables);
            }
            List resultList = this.workflowFormulaSevice.execute(workflowFormulaDTO);
            r.put("data", (Object)resultList);
        }
        catch (Exception e) {
            r = com.jiuqi.va.domain.common.R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.performformulafaulure"));
        }
        return r;
    }
}

