/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class BillWorkflowUtil {
    public static final Logger log = LoggerFactory.getLogger(BillWorkflowUtil.class);

    private static WorkflowServerClient getWorkflowServerClient() {
        return (WorkflowServerClient)ApplicationContextRegister.getBean(WorkflowServerClient.class);
    }

    private static TodoClient getTodoClient() {
        return (TodoClient)ApplicationContextRegister.getBean(TodoClient.class);
    }

    private static MetaDataClient getMetaDataClient() {
        return (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
    }

    public static Map<String, Object> getSubmittableWorkflows(BillModel model) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String billCode = (String)model.getMaster().getValue("BILLCODE", String.class);
        int billState = (Integer)model.getMaster().getValue("BILLSTATE", Integer.TYPE);
        Map<String, Object> rejectTodoMap = null;
        if (BillState.SENDBACK.getValue() == billState) {
            rejectTodoMap = BillWorkflowUtil.getRejectTodoMap(billCode);
        }
        WorkflowBusinessDTO business = new WorkflowBusinessDTO();
        String bizDefine = model.getDefine().getName();
        business.setBusinesscode(bizDefine);
        boolean rejectSkipFlag = false;
        if (!CollectionUtils.isEmpty(rejectTodoMap)) {
            Object rejectSkipFlagObj = rejectTodoMap.get("REJECTSKIPFLAG");
            if (rejectSkipFlagObj != null && Integer.parseInt(String.valueOf(rejectSkipFlagObj)) == 1) {
                business.setWorkflowdefinekey(String.valueOf(rejectTodoMap.get("PROCESSDEFINEKEY")));
                if (rejectTodoMap.get("PROCESSDEFINEVERSION") != null) {
                    business.setWorkflowdefineversion(Long.valueOf(String.valueOf(rejectTodoMap.get("PROCESSDEFINEVERSION"))));
                    rejectSkipFlag = true;
                } else {
                    log.info("\u5355\u636e\u63d0\u4ea4\uff0c\u9a73\u56de\u4e0d\u91cd\u8d70\u7248\u672c\u4e3a\u7a7a");
                }
            }
            resultMap.put("rejectTodoMap", rejectTodoMap);
        }
        business.setShowTitle(false);
        business.setTraceId(Utils.getTraceId());
        R relation = BillWorkflowUtil.getWorkflowServerClient().getBusinessBoundedWorkflow(business);
        if (relation.getCode() == 1) {
            log.error(relation.getMsg());
        }
        if (relation.get((Object)"data") == null || relation.get((Object)"data").toString().length() <= 2) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.billunbindworkflow"));
        }
        Map mapdata = (Map)relation.get((Object)"data");
        List workflows = (List)mapdata.get("workflows");
        if (rejectSkipFlag && !CollectionUtils.isEmpty(workflows)) {
            resultMap.putAll((Map)workflows.get(0));
            return resultMap;
        }
        List<Map<String, Object>> submittableWorkflows = new ArrayList<Map<String, Object>>();
        for (Map workflow : workflows) {
            Map adaptJsonObject;
            String adaptExpression;
            List unitcodes = (List)workflow.get("unitcode");
            if (unitcodes != null && !unitcodes.isEmpty() && !unitcodes.contains(model.getMaster().getString("UNITCODE"))) continue;
            if (workflow.get("adaptcondition") != null && (adaptExpression = BillUtils.valueToString((adaptJsonObject = (Map)workflow.get("adaptcondition")).get("expression"))) != null && !adaptExpression.isEmpty()) {
                try {
                    FormulaImpl formulaImpl = new FormulaImpl();
                    formulaImpl.setExpression(adaptExpression);
                    formulaImpl.setFormulaType(FormulaType.valueOf((String)BillUtils.valueToString(adaptJsonObject.get("formulaType"))));
                    Object formulaResult = FormulaUtils.executeFormula((FormulaImpl)formulaImpl, (Model)model);
                    if (!((Boolean)formulaResult).booleanValue()) {
                        continue;
                    }
                }
                catch (Exception e) {
                    String workflowdefinekey = String.valueOf(workflow.get("workflowdefinekey"));
                    log.error("\u9002\u5e94\u6761\u4ef6\u516c\u5f0f\u6267\u884c\u5f02\u5e38\uff0c\u4e1a\u52a1\u5b9a\u4e49\uff1a{}\uff0c\u5de5\u4f5c\u6d41\u5b9a\u4e49\uff1a{}", bizDefine, workflowdefinekey, e);
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.formulaexecutefailed"));
                }
            }
            submittableWorkflows.add(workflow);
        }
        if (submittableWorkflows.isEmpty()) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.unfindworkflow"));
        }
        int submittableCount = (submittableWorkflows = BillWorkflowUtil.handleWorkflowBusinessGroup(model, submittableWorkflows)).size();
        if (submittableCount == 1) {
            Map<String, Object> map = submittableWorkflows.get(0);
            resultMap.putAll(map);
            return resultMap;
        }
        if (submittableCount > 1) {
            TenantDO param = new TenantDO();
            StringBuilder builder = new StringBuilder();
            for (Map<String, Object> workflowMap : submittableWorkflows) {
                String workflowDefineKey = (String)workflowMap.get("workflowdefinekey");
                if (workflowDefineKey == null || workflowDefineKey.isEmpty()) continue;
                param.setTraceId(Utils.getTraceId());
                param.addExtInfo("defineCode", (Object)workflowDefineKey);
                R workflowMetaDataR = BillWorkflowUtil.getMetaDataClient().findMetaInfoByDefineCode(param);
                if (workflowMetaDataR == null || workflowMetaDataR.getCode() == 1) continue;
                String title = (String)workflowMetaDataR.get((Object)"title");
                if (builder.length() == 0) {
                    builder.append(title);
                    continue;
                }
                builder.append("\u3001").append(title);
            }
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.findmultiworkflow") + " (" + builder + ")");
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.unfindworkflow"));
    }

    private static Map<String, Object> getRejectTodoMap(String billCode) {
        HashMap<String, Object> rejectTodoMap = null;
        ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
        processRejectNodeDO.setBizcode(billCode);
        processRejectNodeDO.setTraceId(Utils.getTraceId());
        List rejectNodeDOS = BillWorkflowUtil.getWorkflowServerClient().listProcessRejectNode(processRejectNodeDO);
        if (!CollectionUtils.isEmpty(rejectNodeDOS)) {
            rejectTodoMap = new HashMap<String, Object>();
            ProcessRejectNodeDO rejectNodeDO = (ProcessRejectNodeDO)rejectNodeDOS.get(0);
            rejectTodoMap.put("PROCESSDEFINEKEY", rejectNodeDO.getProcessdefinekey());
            rejectTodoMap.put("PROCESSDEFINEVERSION", rejectNodeDO.getProcessdefineversion());
            rejectTodoMap.put("REJECTSKIPFLAG", 1);
            rejectTodoMap.put("SUBPROCESSBRANCH", rejectNodeDO.getSubprocessbranch());
        }
        return rejectTodoMap;
    }

    private static List<Map<String, Object>> handleWorkflowBusinessGroup(BillModel model, List<Map<String, Object>> submittableWorkflows) {
        R checkConditionGroupR;
        R r;
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("workflows", submittableWorkflows);
        tenantDO.setTraceId(Utils.getTraceId());
        try {
            r = BillWorkflowUtil.getWorkflowServerClient().extractBizFields(tenantDO);
            if (r.getCode() == 1) {
                throw new BillException(r.getMsg());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.conditiongroup.obtainbillfieldfailed") + BillCoreI18nUtil.getMessage("va.billcore.colon") + e.getMessage());
        }
        ArrayList bizFields = (ArrayList)r.get((Object)"bizFields");
        DataTable masterTable = model.getMasterTable();
        NamedContainer fields = masterTable.getDefine().getFields();
        DataRowImpl dataRow = (DataRowImpl)model.getMaster();
        HashMap<String, Object> bizFieldDataMap = new HashMap<String, Object>();
        if (bizFields == null || bizFields.isEmpty()) {
            bizFields = new ArrayList();
        }
        for (String bizField : bizFields) {
            if (!StringUtils.hasText(bizField)) continue;
            try {
                boolean multiChoice = ((DataFieldDefine)fields.get(bizField)).isMultiChoice();
                Object value = multiChoice ? dataRow.getMultiValue(bizField) : dataRow.getValue(bizField);
                bizFieldDataMap.put(bizField, value);
            }
            catch (Exception e) {
                log.error("\u5355\u636e\u83b7\u53d6\uff1a{}\u5c5e\u6027\u5931\u8d25\uff1a{}", bizField, e.getMessage(), e);
            }
        }
        tenantDO = new TenantDO();
        tenantDO.addExtInfo("workflows", submittableWorkflows);
        tenantDO.addExtInfo("bizFieldDataMap", bizFieldDataMap);
        tenantDO.setTraceId(Utils.getTraceId());
        try {
            checkConditionGroupR = BillWorkflowUtil.getWorkflowServerClient().judgeSatisfyAdaptCondition(tenantDO);
            if (checkConditionGroupR.getCode() == 1) {
                throw new BillException(checkConditionGroupR.getMsg());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.conditiongroup.obtainwfbycondtionviewfailed") + BillCoreI18nUtil.getMessage("va.billcore.colon") + e.getMessage());
        }
        Map workflows = (Map)checkConditionGroupR.get((Object)"workflows");
        if (workflows == null || workflows.isEmpty()) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.conditiongroup.obtainwfbycondtionviewfailed"));
        }
        List workflowDefineKeys = workflows.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), true)).map(Map.Entry::getKey).collect(Collectors.toList());
        return submittableWorkflows.stream().filter(wf -> workflowDefineKeys.contains((String)wf.get("workflowdefinekey"))).collect(Collectors.toList());
    }
}

