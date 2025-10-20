/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.ValueType
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.formula.WorkflowFormulaDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.ValueType;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.formula.WorkflowFormulaDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaExecute;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class WorkflowFormulaServiceImpl
implements WorkflowFormulaSevice {
    private static final Logger log = LoggerFactory.getLogger(WorkflowFormulaServiceImpl.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowMetaService workflowMetaService;

    public boolean judge(String workflowDefine, Integer workflowDefineVersion, Map<String, Object> variables, String condition) {
        try {
            Map customParam = VaContext.getVaWorkflowContext().getCustomParam();
            WorkflowModel workflowModel = (WorkflowModel)customParam.get("workflowModel");
            if (workflowModel == null) {
                workflowModel = this.createModel(workflowDefine, workflowDefineVersion);
            }
            WorkflowContext workflowContext = null;
            workflowContext = (WorkflowContext)((Object)customParam.get("workflowFormulaContext"));
            if (workflowContext == null) {
                workflowContext = new WorkflowContext(new HashMap<String, Object>());
                customParam.put("workflowFormulaContext", workflowContext);
                if (variables != null) {
                    ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)workflowModel.getPlugins().get(ProcessParamPlugin.class);
                    ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
                    List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
                    this.initWorkflowFormulaContext(workflowContext, processParams, variables);
                }
            }
            FormulaImpl formulaImpl = this.createFormulaImpl(condition);
            return WorkflowFormulaExecute.judge(workflowContext, formulaImpl);
        }
        catch (Exception e) {
            log.warn("\u516c\u5f0f\u6267\u884c\u5931\u8d25", e);
            return false;
        }
    }

    public List<Object> execute(WorkflowFormulaDTO workflowFormulaDTO) {
        try {
            WorkflowContext workflowContext = new WorkflowContext(new HashMap<String, Object>());
            Map variables = workflowFormulaDTO.getVariables();
            List processParams = workflowFormulaDTO.getProcessParams();
            List formulas = workflowFormulaDTO.getFormulas();
            if (variables != null) {
                this.initWorkflowFormulaContext(workflowContext, processParams, variables);
            }
            ArrayList<Object> resultList = new ArrayList<Object>();
            for (String formula : formulas) {
                FormulaImpl formulaImpl = this.createFormulaImpl(formula);
                Object result = WorkflowFormulaExecute.evaluate(workflowContext, formulaImpl);
                resultList.add(result);
            }
            return resultList;
        }
        catch (Exception e) {
            log.error("\u516c\u5f0f\u6267\u884c\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    private WorkflowModel createModel(String workflowDefine, Integer workflowDefineVersion) {
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("workflowDefineKey", (Object)workflowDefine);
        tenantDO.addExtInfo("workflowDefineVersion", (Object)workflowDefineVersion);
        Long metaVersion = this.workflowMetaService.getWorkflowMetaVersion(tenantDO);
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(workflowDefine, metaVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        return workflowModel;
    }

    private void initWorkflowFormulaContext(WorkflowContext workflowContext, List<ProcessParam> processParams, Map<String, Object> variables) {
        for (ProcessParam processParam : processParams) {
            FormulaParam formulaParamNode;
            Object paramValue = variables.get(processParam.getParamName());
            try {
                if (!ObjectUtils.isEmpty(paramValue) && paramValue instanceof String) {
                    String paramValueStr = (String)paramValue;
                    if (paramValueStr.startsWith("{")) {
                        Map jSONObject = JSONUtil.parseMap((String)paramValueStr);
                        int baseType = (Integer)jSONObject.get("baseType");
                        List data = (List)jSONObject.get("data");
                        formulaParamNode = new FormulaParam(processParam.getParamType(), new ArrayData(baseType, (Collection)data));
                    } else {
                        formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
                    }
                } else {
                    formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
                }
            }
            catch (Exception e) {
                formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
            }
            if (ValueType.DECIMAL.equals((Object)formulaParamNode.getValueType()) && formulaParamNode.getValue() == null) {
                formulaParamNode = new FormulaParam(formulaParamNode.getValueType(), 0);
            }
            workflowContext.put(processParam.getParamName(), formulaParamNode);
        }
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        if (!ObjectUtils.isEmpty(vaWorkflowContext)) {
            WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
            workflowContext.put("REVIEWMODE", workflowDTO.getReviewMode());
        }
    }

    private FormulaImpl createFormulaImpl(String formula) throws JsonProcessingException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode formulaJson = mapper.readTree(formula);
        String expression = formulaJson.findValue("expression").asText();
        String formulaType = formulaJson.findValue("formulaType").asText();
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setExpression(expression);
        formulaImpl.setFormulaType(FormulaType.valueOf((String)formulaType));
        return formulaImpl;
    }
}

