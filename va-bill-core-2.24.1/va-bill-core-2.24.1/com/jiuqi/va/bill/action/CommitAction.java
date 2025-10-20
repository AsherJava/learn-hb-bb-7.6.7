/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO
 *  com.jiuqi.va.feign.client.WorkflowProcessReviewClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 *  org.apache.ibatis.jdbc.SQL
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.action.BillWorkflowActionBase;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.domain.option.BillRuleOptionVO;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.BillCoreWorkFlowService;
import com.jiuqi.va.bill.service.BillRuleOptionService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.BillWorkflowUtil;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO;
import com.jiuqi.va.feign.client.WorkflowProcessReviewClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class CommitAction
extends BillWorkflowActionBase {
    private static final Logger log = LoggerFactory.getLogger(CommitAction.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BillCoreWorkFlowService billCoreWorkFlowService;
    @Autowired
    private WorkflowProcessReviewClient workflowProcessReviewClient;
    @Autowired
    private BillRuleOptionService billRuleOptionService;
    @Autowired
    private CommonDao dao;

    public String getName() {
        return "bill-commit";
    }

    public String getTitle() {
        return "\u63d0\u4ea4";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_tijiao";
    }

    public String getActionPriority() {
        return "017";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void invoke(Model model, ActionRequest request, ActionResponse response) {
        block10: {
            BillModel billModel = (BillModel)model;
            VerifyUtils.verifyBill(billModel, 2);
            String billCode = (String)billModel.getMaster().getValue("BILLCODE", String.class);
            LogUtil.add((String)"\u5355\u636e", (String)"\u63d0\u4ea4", (String)billModel.getDefine().getName(), (String)billCode, null);
            if (billModel.editing()) {
                new SaveAction().executeReturn(billModel, (Map<String, Object>)null);
            }
            String tenantName = billModel.getContext().getTenantName();
            String key = tenantName + billCode;
            String value = UUID.randomUUID().toString();
            boolean flag = true;
            try {
                if (Boolean.TRUE.equals(this.stringRedisTemplate.opsForValue().setIfAbsent((Object)key, (Object)value))) {
                    this.stringRedisTemplate.opsForValue().set((Object)key, (Object)value, 10L, TimeUnit.SECONDS);
                    BigDecimal ver = billModel.getMaster().getBigDecimal("VER");
                    SQL sql = new SQL();
                    sql.SELECT("VER");
                    sql.FROM(billModel.getMasterTable().getName());
                    sql.WHERE("BILLCODE = #{param.BILLCODE, jdbcType=VARCHAR}");
                    SqlDTO sqlDTO = new SqlDTO(tenantName, sql.toString());
                    sqlDTO.addParam("BILLCODE", (Object)billCode);
                    List list = this.dao.listUpperKeyMap(sqlDTO);
                    BigDecimal newVer = BigDecimal.ZERO;
                    if (list.size() > 0) {
                        Object val = ((UpperKeyMap)list.get(0)).get((Object)"VER");
                        newVer = val instanceof BigDecimal ? (BigDecimal)val : new BigDecimal(val.toString());
                    }
                    if (ver.compareTo(newVer) != 0) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.datachange"));
                    }
                    List checkMessages = ((RulerImpl)billModel.getRuler()).getRulerExecutor().beforeAction("commit");
                    if (checkMessages != null && checkMessages.size() > 0) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.commitfailed"), checkMessages);
                    }
                    int billState = (Integer)billModel.getMaster().getValue("BILLSTATE", Integer.TYPE);
                    if (BillState.SAVED.getValue() != billState && BillState.SENDBACK.getValue() != billState) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.commitfailed"), checkMessages);
                    }
                    this.checkLock(billCode);
                    this.doBillCommit(billModel, request.getParams(), response);
                    break block10;
                }
                flag = false;
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.getlockfailed"));
            }
            finally {
                if (flag) {
                    this.stringRedisTemplate.delete((Object)key);
                }
            }
        }
    }

    private void doBillCommit(BillModel model, Map<String, Object> params, ActionResponse response) {
        R workFlowR;
        String defineCode = (String)model.getMaster().getValue("DEFINECODE", String.class);
        String billCode = (String)model.getMaster().getValue("BILLCODE", String.class);
        int billState = (Integer)model.getMaster().getValue("BILLSTATE", Integer.TYPE);
        WorkflowDTO workflowDTO = new WorkflowDTO();
        try {
            R r;
            WorkflowDTO newWorkflowDTO;
            Map<String, Object> workflow = BillWorkflowUtil.getSubmittableWorkflows(model);
            String workflowdefinekey = BillUtils.valueToString(workflow.get("workflowdefinekey"));
            Map<String, Object> todoParam = this.billDataEditService.loadTodoParam(model, defineCode);
            workflowDTO.setTodoParamMap(todoParam);
            workflowDTO.setUniqueCode(workflowdefinekey);
            workflowDTO.setBizModule(model.getDefine().getName().split("_")[0]);
            workflowDTO.setBizType("BILL");
            workflowDTO.setBizCode(billCode);
            workflowDTO.setBizDefine(defineCode);
            workflowDTO.addExtInfo("CREATEUSER", model.getMaster().getValue("CREATEUSER", String.class));
            Object workflowVariableObj = params.get("workflowVariables");
            Map<String, Object> workflowVariablesMap = this.billCoreWorkFlowService.getWorkFlowParamsValueMap(model, workflow);
            if (workflowVariableObj != null) {
                workflowVariablesMap.putAll((Map)workflowVariableObj);
            }
            workflowDTO.setWorkflowVariables(workflowVariablesMap);
            Object rejectTodoMap = workflow.get("rejectTodoMap");
            if (rejectTodoMap instanceof Map) {
                Map rejectTodo = (Map)rejectTodoMap;
                if (rejectTodo.get("REJECTSKIPFLAG") != null && Integer.valueOf(String.valueOf(rejectTodo.get("REJECTSKIPFLAG"))) == 1) {
                    workflowDTO.setRejectSkipNode(true);
                    workflowDTO.setProcessDefineVersion(Long.valueOf(String.valueOf(rejectTodo.get("PROCESSDEFINEVERSION"))));
                }
                if (!ObjectUtils.isEmpty(rejectTodo.get("SUBPROCESSBRANCH"))) {
                    workflowDTO.setSubProcessBranch((String)rejectTodo.get("SUBPROCESSBRANCH"));
                }
            }
            if (!workflowDTO.isRejectSkipNode() && params.get("nextNodeCode") == null) {
                newWorkflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)workflowDTO), WorkflowDTO.class);
                newWorkflowDTO.setTraceId(Utils.getTraceId());
                r = this.workflowServerClient.listNextNode(newWorkflowDTO);
                if (r.getCode() != 0) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.getnextnode") + "\uff1a" + r.getMsg());
                }
                boolean chooseApproverNode = (Boolean)r.get((Object)"chooseApproverNode");
                if (chooseApproverNode) {
                    List nextNodeCodeList = BillUtils.getList(r.get((Object)"chooseApproverNodeList"));
                    if (nextNodeCodeList.size() == 0) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va_activiti_nonenextnode_i18n"));
                    }
                    if (nextNodeCodeList.size() > 1) {
                        response.setSuccess(false);
                        r.put("confirms", params.get("confirms"));
                        response.setReturnValue((Object)r);
                        return;
                    }
                    workflowDTO.setNextNodeCode((String)((Map)nextNodeCodeList.get(0)).get("nodeCode"));
                }
            } else if (!workflowDTO.isRejectSkipNode()) {
                workflowDTO.setNextNodeCode((String)params.get("nextNodeCode"));
            }
            if (this.isChooseApprover() && params.get("confirmedAction") == null) {
                newWorkflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)workflowDTO), WorkflowDTO.class);
                newWorkflowDTO.setTraceId(Utils.getTraceId());
                r = this.workflowServerClient.nextnodeApprover(workflowDTO);
                if (r.getCode() != 0) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.getnextapprover"));
                }
                if (!"disable".equals(r.get((Object)"chooseApprover"))) {
                    response.setSuccess(false);
                    r.put("confirms", params.get("confirms"));
                    if (params.get("nextNodeCode") != null) {
                        r.put("nextNodeCode", params.get("nextNodeCode"));
                    }
                    response.setReturnValue((Object)r);
                    return;
                }
            }
            if (params.get("nextNodeCompleteUsers") != null) {
                workflowDTO.setNextNodeCompleteUsers(new HashSet((List)params.get("nextNodeCompleteUsers")));
            }
            if (BillState.SENDBACK.getValue() == billState && params.get("rejectInfos") == null) {
                BillRuleOptionVO billRuleOptionVO;
                OptionItemDTO optionItemDTO = new OptionItemDTO();
                optionItemDTO.setName("BR1001");
                optionItemDTO.setControlModel(Boolean.valueOf(true));
                optionItemDTO.setUnitcode(model.getMaster().getString("UNITCODE"));
                List<BillRuleOptionVO> list = this.billRuleOptionService.list(optionItemDTO);
                if (!CollectionUtils.isEmpty(list) && this.check(billRuleOptionVO = list.get(0), defineCode)) {
                    WorkflowProcessReviewDTO workflowProcessReviewDTO = new WorkflowProcessReviewDTO();
                    workflowProcessReviewDTO.setBizcode(billCode);
                    workflowProcessReviewDTO.setRejectednodeid("submit");
                    workflowProcessReviewDTO.setRequired(Boolean.valueOf(this.required(billRuleOptionVO)));
                    workflowProcessReviewDTO.setTraceId(Utils.getTraceId());
                    R rejectInfos = this.workflowProcessReviewClient.getRejectInfos(workflowProcessReviewDTO);
                    if (rejectInfos.getCode() == 1) {
                        log.error(rejectInfos.getMsg());
                    } else if (rejectInfos.getCode() == 0 && !CollectionUtils.isEmpty((List)rejectInfos.get((Object)"rejectInfos"))) {
                        R r2 = R.ok();
                        r2.put("rejectInfos", rejectInfos.get((Object)"rejectInfos"));
                        if (params.get("nextNodeCode") != null) {
                            r2.put("nextNodeCode", params.get("nextNodeCode"));
                        }
                        if (params.get("nextNodeCompleteUsers") != null) {
                            r2.put("nextNodeCompleteUsers", params.get("nextNodeCompleteUsers"));
                        }
                        r2.put("confirms", params.get("confirms"));
                        response.setReturnValue((Object)r2);
                        response.setSuccess(false);
                        return;
                    }
                }
            }
            if (params.get("rejectInfos") != null) {
                List list = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)params.get("rejectInfos")), WorkflowProcessReviewDTO.class);
                list.forEach(o -> o.setTraceId(Utils.getTraceId()));
                r = this.workflowProcessReviewClient.syncReviews(list);
                if (r.getCode() == 1) {
                    log.error(r.getMsg());
                }
            }
            boolean disableSendMailFlag = false;
            if (model.getMasterTable().getFields().find("DISABLESENDMAILFLAG") != null) {
                disableSendMailFlag = model.getMaster().getBoolean("DISABLESENDMAILFLAG");
            }
            workflowDTO.setBizId((String)model.getMaster().getValue("ID", String.class));
            workflowDTO.setBizUnitcode((String)model.getMaster().getValue("UNITCODE", String.class));
            workflowDTO.setDisableSendMailFlag(disableSendMailFlag);
            workflowDTO.setGenerateEndNode(ObjectUtils.isEmpty(params.get("generateEndNode")) || Boolean.parseBoolean(params.get("generateEndNode").toString()));
            Object token = params.get("JTOKENID");
            if (token != null) {
                workflowDTO.addExtInfo("JTOKENID", token);
            }
            workflowDTO.setTransDefineName("bill-commit");
        }
        catch (Exception e) {
            log.error("\u5355\u636e{}\u63d0\u4ea4\u5de5\u4f5c\u6d41\u5f02\u5e38", (Object)billCode, (Object)e);
            throw e;
        }
        try {
            workflowDTO.setTraceId(Utils.getTraceId());
            workFlowR = this.workflowServerClient.startProcess(workflowDTO);
        }
        catch (Exception e) {
            log.error("\u5355\u636e{}\u63d0\u4ea4\u5de5\u4f5c\u6d41\u5f02\u5e38", (Object)billCode, (Object)e);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.billsubmitexception"));
        }
        if (!"0".equals(workFlowR.get((Object)"code").toString())) {
            if (workFlowR.get((Object)"msg") != null) {
                throw new BillException(workFlowR.get((Object)"msg").toString());
            }
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.commitaction.billsubmitexception"));
        }
        model.getContext().setContextValue("TriggerOrigin", "FRONTEND");
        model.loadByCode(billCode);
        response.setReturnValue((Object)R.ok());
    }

    private boolean check(BillRuleOptionVO billRuleOptionVO, String definecode) {
        if (billRuleOptionVO.getVal() == null || billRuleOptionVO.getVal().equals("{}")) {
            return false;
        }
        Map map = JSONUtil.parseMap((String)billRuleOptionVO.getVal());
        Object fillModify = map.get("fillModify");
        if (fillModify == null || !((Boolean)fillModify).booleanValue()) {
            return false;
        }
        Object allBill = map.get("allBill");
        if (allBill != null && ((Boolean)allBill).booleanValue()) {
            return true;
        }
        String storageValue = billRuleOptionVO.getStorageValue();
        if (storageValue == null) {
            return false;
        }
        for (Map stringObjectMap : JSONUtil.parseMapArray((String)storageValue)) {
            if (!definecode.equals(stringObjectMap.get("uniqueCode"))) continue;
            return true;
        }
        return false;
    }

    private boolean required(BillRuleOptionVO billRuleOptionVO) {
        Map map = JSONUtil.parseMap((String)billRuleOptionVO.getVal());
        if (map.get("required") == null) {
            return false;
        }
        return (Boolean)map.get("required");
    }

    @Override
    public String[] getModelParams() {
        return super.getModelParams();
    }
}

