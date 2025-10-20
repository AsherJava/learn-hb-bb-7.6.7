/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.controller.WorkflowController;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaExecute;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class FormulaStrategy
implements Strategy {
    private static final Logger log = LoggerFactory.getLogger(WorkflowController.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowMetaService workflowMetaService;

    public String getName() {
        return "formula";
    }

    public String getTitle() {
        return "\u516c\u5f0f";
    }

    public String getOrder() {
        return "000";
    }

    public String getStrategyModule() {
        return "general";
    }

    public Set<String> execute(Object params) {
        LinkedHashSet<String> users = new LinkedHashSet<String>();
        try {
            ArrayNode formulas;
            Map paramMap = (Map)params;
            VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
            Map customParam = vaWorkflowContext.getCustomParam();
            WorkflowContext workflowFormulaContext = (WorkflowContext)((Object)customParam.get("workflowFormulaContext"));
            if (workflowFormulaContext == null) {
                workflowFormulaContext = this.initWorkflowFormulaContext(paramMap, vaWorkflowContext, customParam);
                customParam.put("workflowFormulaContext", workflowFormulaContext);
            }
            if ((formulas = (ArrayNode)paramMap.get("assignParam")) != null && formulas.size() > 0) {
                for (JsonNode jsonNode : formulas) {
                    JsonNode formula = jsonNode.get("value");
                    String expression = formula.findValue("expression").asText();
                    if (!StringUtils.hasText(expression)) continue;
                    String formulaType = formula.findValue("formulaType").asText();
                    FormulaImpl formulaImpl = new FormulaImpl();
                    formulaImpl.setExpression(expression);
                    formulaImpl.setFormulaType(FormulaType.valueOf((String)formulaType));
                    Object user = WorkflowFormulaExecute.evaluate(workflowFormulaContext, formulaImpl);
                    if (user instanceof UUID) {
                        users.add(user.toString());
                    }
                    if (user instanceof String) {
                        users.add((String)user);
                    }
                    if (user instanceof ArrayData) {
                        List userList = ((ArrayData)user).toList();
                        userList.forEach(o -> users.add((String)o));
                    }
                    if (!(user instanceof Collection)) continue;
                    users.addAll((Collection)user);
                }
            }
        }
        catch (Exception e) {
            String currentNodeName;
            StringBuilder errorMsg = new StringBuilder("\u516c\u5f0f\u7b56\u7565\u8ba1\u7b97\u53c2\u4e0e\u8005\u5931\u8d25");
            Map paramMap = (Map)params;
            WorkflowDTO workflowDTO = (WorkflowDTO)paramMap.get("workflowDTO");
            if (workflowDTO != null) {
                errorMsg.append("\uff0c\u4e1a\u52a1\u7f16\u53f7\uff1a").append(workflowDTO.getBizCode());
                errorMsg.append("\uff0c\u6d41\u7a0b\u6807\u8bc6\uff1a").append(workflowDTO.getUniqueCode());
            }
            if (StringUtils.hasText(currentNodeName = (String)paramMap.get("currentNodeName"))) {
                errorMsg.append("\uff0c\u6d41\u7a0b\u8282\u70b9\uff1a").append(currentNodeName);
            }
            ArrayNode formulas = (ArrayNode)paramMap.get("assignParam");
            errorMsg.append("\uff0c\u516c\u5f0f\u914d\u7f6e\uff1a").append(formulas);
            errorMsg.append("\uff0c\u62a5\u9519\u539f\u56e0\uff1a").append(e.getMessage());
            log.error(errorMsg.toString(), e);
            throw new RuntimeException(errorMsg.toString(), e);
        }
        return users;
    }

    private WorkflowContext initWorkflowFormulaContext(Map<String, Object> paramMap, VaWorkflowContext vaWorkflowContext, Map<String, Object> customParam) {
        WorkflowContext workflowContext = new WorkflowContext(new HashMap<String, Object>());
        WorkflowModel workflowModel = (WorkflowModel)customParam.get("workflowModel");
        if (workflowModel == null) {
            ProcessDO processDO = vaWorkflowContext.getProcessDO();
            String workflowDefine = processDO.getDefinekey();
            long workflowDefineVersion = processDO.getDefineversion().longValue();
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("workflowDefineKey", (Object)workflowDefine);
            tenantDO.addExtInfo("workflowDefineVersion", (Object)workflowDefineVersion);
            Long metaVersion = this.workflowMetaService.getWorkflowMetaVersion(tenantDO);
            WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(workflowDefine, metaVersion);
            workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        }
        ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)workflowModel.getPlugins().get(ProcessParamPlugin.class);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
        List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
        Map<String, Object> variables = VaWorkflowUtils.getMap(paramMap.get("variables"));
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
                workflowContext.put(processParam.getParamName(), formulaParamNode);
            }
            catch (Exception e) {
                formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
                workflowContext.put(processParam.getParamName(), formulaParamNode);
            }
        }
        return workflowContext;
    }
}

