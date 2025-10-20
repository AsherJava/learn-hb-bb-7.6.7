/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaExecute;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class DesignatedUnitAndRoleStrategy
implements Strategy {
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowMetaService workflowMetaService;

    public String getName() {
        return "designatedUnitAndRole";
    }

    public String getTitle() {
        return "\u6307\u5b9a\u89d2\u8272\u6307\u5b9a\u7ec4\u7ec7\u7684\u7528\u6237\uff08\u4ec5\u9650\u7528\u6237\u6240\u5c5e\u7ec4\u7ec7\uff09";
    }

    public String getOrder() {
        return "003";
    }

    public String getStrategyModule() {
        return "general";
    }

    public Set<String> execute(Object params) {
        LinkedHashSet<String> list = new LinkedHashSet<String>();
        try {
            Map paramsmap = (Map)params;
            ArrayNode paramsList = (ArrayNode)paramsmap.get("assignParam");
            for (JsonNode node : paramsList) {
                UserDTO userDTO = new UserDTO();
                userDTO.setRoleName(node.get("value").asText());
                Object unitCode = this.getUnitCode(node.get("unit"), paramsmap);
                if (unitCode instanceof String) {
                    this.addUser(userDTO, unitCode, list);
                    continue;
                }
                if (unitCode instanceof ArrayData) {
                    List unitCodeList = ((ArrayData)unitCode).toList();
                    for (Object o : unitCodeList) {
                        this.addUser(userDTO, o, list);
                    }
                    continue;
                }
                throw new RuntimeException("\u6267\u884c\u7ec4\u7ec7\u673a\u6784\u516c\u5f0f\u5f02\u5e38");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private void addUser(UserDTO userDTO, Object unitCode, Set<String> list) {
        if (!(unitCode instanceof String) || !StringUtils.hasText(unitCode.toString())) {
            throw new RuntimeException("\u7ec4\u7ec7\u673a\u6784\u53c2\u6570\u4e3a\u7a7a");
        }
        userDTO.addExtInfo("contextOrg", unitCode);
        PageVO pageVO = this.authUserClient.list(userDTO);
        if (pageVO != null && pageVO.getRows() != null) {
            for (UserDO userDO : pageVO.getRows()) {
                list.add(userDO.getId());
            }
        }
    }

    private Object getUnitCode(JsonNode jsonNode, Map<String, Object> paramMap) throws SyntaxException {
        Object unitcode = null;
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
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
        WorkflowContext workflowContext = (WorkflowContext)((Object)customParam.get("workflowFormulaContext"));
        if (workflowContext == null) {
            workflowContext = new WorkflowContext(new HashMap<String, Object>());
            customParam.put("workflowFormulaContext", workflowContext);
        }
        Map variables = (Map)paramMap.get("variables");
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
        JsonNode formula = jsonNode.get("value");
        String expression = formula.get("expression").asText();
        String formulaType = formula.get("formulaType").asText();
        if (jsonNode != null && expression != null) {
            FormulaImpl formulaImpl = new FormulaImpl();
            formulaImpl.setExpression(expression);
            formulaImpl.setFormulaType(FormulaType.valueOf((String)formulaType));
            unitcode = WorkflowFormulaExecute.evaluate(workflowContext, formulaImpl);
        }
        return unitcode;
    }
}

