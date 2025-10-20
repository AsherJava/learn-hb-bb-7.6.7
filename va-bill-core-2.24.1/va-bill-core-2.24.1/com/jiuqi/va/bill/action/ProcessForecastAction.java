/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.intf.action.ActionReturnModalTemplate
 *  com.jiuqi.va.biz.intf.action.ActionReturnUtil
 *  com.jiuqi.va.biz.intf.action.ModalWidthEnum
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.BillCoreWorkFlowService;
import com.jiuqi.va.bill.service.ProcessForecastService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.BillWorkflowUtil;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.intf.action.ActionReturnModalTemplate;
import com.jiuqi.va.biz.intf.action.ActionReturnUtil;
import com.jiuqi.va.biz.intf.action.ModalWidthEnum;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ProcessForecastAction
extends BillActionBase {
    private static final Logger logger = LoggerFactory.getLogger(ProcessForecastAction.class);
    @Autowired
    private WorkflowServerClient workflowServerClient;
    @Autowired
    protected MetaDataClient metaDataClient;
    @Autowired
    private BillCoreWorkFlowService billCoreWorkFlowService;
    @Autowired
    private ProcessForecastService processForecastService;

    public String getName() {
        return "bill-workflow-forecast";
    }

    public String getTitle() {
        return "\u9884\u6d4b\u6d41\u7a0b";
    }

    public String getActionPriority() {
        return "022";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_yuceliucheng";
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        WorkflowDTO workflowDTO = new WorkflowDTO();
        Long defineVersion = null;
        String defineKey = null;
        String bizCode = (String)model.getMaster().getValue("BILLCODE", String.class);
        Object needWorkflow = model.getContext().getContextValue("needWorkflow");
        if (needWorkflow == null || ((Boolean)needWorkflow).booleanValue()) {
            int billState = model.getMaster().getInt("BILLSTATE");
            if (model.editing() || billState <= BillState.COMMITTED.getValue() || billState == BillState.SENDBACK.getValue()) {
                Map<String, Object> workflow = BillWorkflowUtil.getSubmittableWorkflows(model);
                Map<String, Object> workflowparamMap = this.billCoreWorkFlowService.getWorkFlowParamsValueMap(model, workflow);
                String workflowDefineKey = BillUtils.valueToString(workflow.get("workflowdefinekey"));
                String workflowDefineVersion = BillUtils.valueToString(workflow.get("workflowdefineversion"));
                defineKey = workflowDefineKey;
                if (BillState.COMMITTED.getValue() != billState) {
                    workflowDTO.setUniqueCode(workflowDefineKey);
                    ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
                    processRejectNodeDO.setBizcode(bizCode);
                    processRejectNodeDO.setTraceId(Utils.getTraceId());
                    List processRejectNodeDOS = this.workflowServerClient.listProcessRejectNode(processRejectNodeDO);
                    defineVersion = CollectionUtils.isEmpty(processRejectNodeDOS) ? ((Long)workflow.get("workflowdefineversion")).longValue() : ((ProcessRejectNodeDO)processRejectNodeDOS.get(0)).getProcessdefineversion().longValue();
                } else {
                    ProcessDTO processDTO = new ProcessDTO();
                    processDTO.setBizcode(bizCode);
                    try {
                        R r = this.workflowServerClient.getProcess(processDTO);
                        if (r.getCode() == 1) {
                            logger.error("\u83b7\u53d6\u5de5\u4f5c\u6d41\u5f02\u5e38\uff0c\u4e1a\u52a1\u7f16\u53f7\uff1a{}", (Object)bizCode);
                        } else {
                            Object process = r.get((Object)"process");
                            Map processMap = (Map)process;
                            defineVersion = processMap != null ? (Long)processMap.get("defineversion") : null;
                        }
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                workflowDTO.setWorkflowVariables(workflowparamMap);
                workflowDTO.setProcessDefineVersion(Long.valueOf(workflowDefineVersion));
            } else {
                ProcessDTO processDTO = new ProcessDTO();
                processDTO.setBizcode(bizCode);
                try {
                    R r1 = this.workflowServerClient.processHistory(processDTO);
                    Object processHistory = r1.get((Object)"processList");
                    if (r1.getCode() == 1) {
                        logger.error("\u83b7\u53d6\u6d41\u7a0b\u5386\u53f2\u5f02\u5e38\uff0c\u4e1a\u52a1\u7f16\u53f7\uff1a{}", (Object)bizCode);
                    } else {
                        List processHistoryList = (List)processHistory;
                        if (!CollectionUtils.isEmpty(processHistoryList)) {
                            Map processHistoryMap = (Map)processHistoryList.get(processHistoryList.size() - 1);
                            defineVersion = (Long)processHistoryMap.get("defineversion");
                            defineKey = (String)processHistoryMap.get("definekey");
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        String bizDefine = (String)model.getMaster().getValue("DEFINECODE", String.class);
        workflowDTO.setBizType("BILL");
        workflowDTO.setBizDefine(bizDefine);
        workflowDTO.setBizCode(bizCode);
        HashMap<String, Object> bizcode = new HashMap<String, Object>();
        bizcode.put("bizCode", bizCode);
        bizcode.put("workflowDTO", workflowDTO);
        com.jiuqi.va.biz.utils.R r = this.processForecastService.getAllNodes(model, bizcode);
        if (0 != r.getCode()) {
            ArrayList<CheckResult> results = new ArrayList<CheckResult>();
            CheckResultImpl result = new CheckResultImpl();
            result.setFormulaName("\u63d0\u793a");
            result.setTargetList(new ArrayList());
            if (r.getMsg() != null) {
                result.setCheckMessage(r.getMsg());
            } else {
                result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.forecastaction.forecastexception"));
            }
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
        }
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("forecastData", r.getData());
        props.put("defineVersion", defineVersion);
        props.put("defineKey", defineKey);
        HashMap<String, HashMap<String, Object>> data = new HashMap<String, HashMap<String, Object>>();
        data.put("props", props);
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("props", data);
        param.put("type", "WorkflowForecastModal");
        ActionReturnModalTemplate template = ActionReturnUtil.returnModalTemplate((String)JSONUtil.toJSONString(param), (String)"vue");
        template.setWidth(ModalWidthEnum.FULL);
        template.setHideFooter(true);
        template.setTitle(BillCoreI18nUtil.getMessage("va.billcore.forecastaction.forecastprocess"));
        return template;
    }

    public Map<String, Object> findworkflow(BillModel model) {
        ArrayList<CheckResult> results = new ArrayList<CheckResult>();
        CheckResultImpl result = new CheckResultImpl();
        result.setFormulaName("\u63d0\u793a");
        result.setTargetList(new ArrayList());
        WorkflowBusinessDTO business = new WorkflowBusinessDTO();
        business.setBusinesscode(model.getDefine().getName());
        business.setTraceId(Utils.getTraceId());
        R relation = this.workflowServerClient.getBusinessBoundedWorkflow(business);
        if (relation.getCode() == 1) {
            logger.error(relation.getMsg());
        }
        if (relation.get((Object)"data") == null || relation.get((Object)"data").toString().length() <= 2) {
            result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.commitaction.unfindworkflow"));
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
        }
        Map mapdata = (Map)relation.get((Object)"data");
        List workflows = (List)mapdata.get("workflows");
        List<Map<String, Object>> submittableWorkflows = new ArrayList<Map<String, Object>>();
        for (Map workflow : workflows) {
            Map adaptJsonObject;
            String adaptExpression;
            List unicodes = (List)workflow.get("unitcode");
            if (unicodes != null && unicodes.size() > 0 && !unicodes.contains(model.getMaster().getString("UNITCODE"))) continue;
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
                    result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.commitaction.formulaexecutefailed"));
                    results.add((CheckResult)result);
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
                }
            }
            submittableWorkflows.add(workflow);
        }
        if (submittableWorkflows.isEmpty()) {
            result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.commitaction.unfindworkflow"));
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
        }
        int submittableCount = (submittableWorkflows = this.handleWorkflowBusinessGroup(model, submittableWorkflows, result, results)).size();
        if (submittableCount == 1) {
            return submittableWorkflows.get(0);
        }
        if (submittableCount > 1) {
            TenantDO param = new TenantDO();
            StringBuilder builder = new StringBuilder();
            for (Map<String, Object> workflowMap : submittableWorkflows) {
                String workflowDefineKey = (String)workflowMap.get("workflowdefinekey");
                if (workflowDefineKey == null || workflowDefineKey.isEmpty()) continue;
                param.setTraceId(Utils.getTraceId());
                param.addExtInfo("defineCode", (Object)workflowDefineKey);
                R workflowMetaDataR = this.metaDataClient.findMetaInfoByDefineCode(param);
                if (workflowMetaDataR == null || workflowMetaDataR.getCode() == 1) continue;
                String title = (String)workflowMetaDataR.get((Object)"title");
                if (builder.length() == 0) {
                    builder.append(title);
                    continue;
                }
                builder.append("\u3001").append(title);
            }
            result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.commitaction.findmultiworkflow") + " (" + builder + ")");
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
        }
        result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.commitaction.unfindworkflow"));
        results.add((CheckResult)result);
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
    }

    private List<Map<String, Object>> handleWorkflowBusinessGroup(BillModel model, List<Map<String, Object>> submittableWorkflows, CheckResultImpl result, List<CheckResult> results) {
        R checkConditionGroupR;
        R r;
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("workflows", submittableWorkflows);
        tenantDO.setTraceId(Utils.getTraceId());
        try {
            r = this.workflowServerClient.extractBizFields(tenantDO);
            if (r.getCode() == 1) {
                throw new BillException(r.getMsg());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.conditiongroup.obtainbillfieldfailed") + BillCoreI18nUtil.getMessage("va.billcore.colon") + e.getMessage());
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
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
                logger.error("\u5355\u636e\u83b7\u53d6\uff1a{}\u5c5e\u6027\u5931\u8d25\uff1a{}", bizField, e.getMessage(), e);
            }
        }
        tenantDO = new TenantDO();
        tenantDO.addExtInfo("workflows", submittableWorkflows);
        tenantDO.addExtInfo("bizFieldDataMap", bizFieldDataMap);
        tenantDO.setTraceId(Utils.getTraceId());
        try {
            checkConditionGroupR = this.workflowServerClient.judgeSatisfyAdaptCondition(tenantDO);
            if (checkConditionGroupR.getCode() == 1) {
                throw new BillException(checkConditionGroupR.getMsg());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.conditiongroup.obtainwfbycondtionviewfailed") + BillCoreI18nUtil.getMessage("va.billcore.colon") + e.getMessage());
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
        }
        Map workflows = (Map)checkConditionGroupR.get((Object)"workflows");
        if (workflows == null || workflows.isEmpty()) {
            result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.conditiongroup.obtainwfbycondtionviewfailed"));
            results.add((CheckResult)result);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
        }
        List workflowDefineKeys = workflows.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), true)).map(Map.Entry::getKey).collect(Collectors.toList());
        return submittableWorkflows.stream().filter(wf -> workflowDefineKeys.contains((String)wf.get("workflowdefinekey"))).collect(Collectors.toList());
    }
}

