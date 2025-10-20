/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.role.RoleDTO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  org.springframework.jdbc.core.JdbcOperations
 */
package com.jiuqi.va.workflow.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.role.RoleDTO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaExecute;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class DesignatedRoleAndStaffUnitStrategy
implements Strategy {
    @Autowired
    private AuthRoleClient authRoleClient;
    @Autowired
    private JdbcOperations jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DesignatedRoleAndStaffUnitStrategy.class);
    private static final String SQL_QUERY_USERID_BY_ROLEANDUNIT = "select u.ID,u.LOCKED,u.ENABLED from NP_USER u inner join NP_AUTHZ_IDENTITY_ROLE r  on r.IDENTITY_ID_ = u.ID inner join MD_STAFF s on s.LINKUSER = u.ID  where r.ROLE_ID_ =? and s.UNITCODE=? and s.STOPFLAG=? and s.RECOVERYFLAG=? ";
    private static final String SQL_QUERY_USERID_BY_UNITIDENTITY = "select u.ID,u.LOCKED,u.ENABLED from NP_USER u  inner join NP_AUTHZ_USER_IDENTITY i on u.ID=i.USER_ID_  inner join NP_AUTHZ_IDENTITY_ROLE r on i.IDENTITY_ID_=r.IDENTITY_ID_  inner join MD_STAFF s on s.LINKUSER = u.ID  where r.ROLE_ID_ =? and s.UNITCODE=? and s.STOPFLAG=? and s.RECOVERYFLAG=? ";

    public Set<String> execute(Object params) {
        LinkedHashSet<String> list = new LinkedHashSet<String>();
        try {
            Map paramsmap = (Map)params;
            ArrayNode paramsList = (ArrayNode)paramsmap.get("assignParam");
            for (JsonNode node : paramsList) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setName(node.get("value").asText());
                RoleDO role = this.authRoleClient.get(roleDTO);
                String unitCode = this.getUnitCode(node.get("unit"), paramsmap);
                List tempList = null;
                List userList = this.jdbcTemplate.queryForList(SQL_QUERY_USERID_BY_ROLEANDUNIT, new Object[]{role.getId(), unitCode, 0, 0});
                tempList = this.jdbcTemplate.queryForList(SQL_QUERY_USERID_BY_UNITIDENTITY, new Object[]{role.getId(), unitCode, 0, 0});
                if (!tempList.isEmpty()) {
                    userList.addAll(tempList);
                }
                if (userList.isEmpty()) {
                    return list;
                }
                userList.stream().forEach(m -> {
                    if (Integer.valueOf(m.get("LOCKED").toString()) == 0 && Integer.valueOf(m.get("ENABLED").toString()) == 1) {
                        list.add(m.get("ID").toString());
                    }
                });
            }
        }
        catch (Exception e) {
            logger.error("\u804c\u5458\u5728\u6307\u5b9a\u7ec4\u7ec7\u4e0b\u5bf9\u5e94\u89d2\u8272\u7684\u7528\u6237", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    private String getUnitCode(JsonNode jsonNode, Map<String, Object> paramMap) throws SyntaxException {
        String unitcode = null;
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        WorkflowModel workflowModel = (WorkflowModel)customParam.get("workflowModel");
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
            unitcode = (String)WorkflowFormulaExecute.evaluate(workflowContext, formulaImpl);
        }
        return unitcode;
    }

    public String getName() {
        return "designatedRoleAndStaffUnit";
    }

    public String getStrategyModule() {
        return "general";
    }

    public String getTitle() {
        return "\u6307\u5b9a\u89d2\u8272\u6307\u5b9a\u804c\u5458\u7ec4\u7ec7\u7684\u7528\u6237";
    }

    public String getOrder() {
        return "005";
    }
}

