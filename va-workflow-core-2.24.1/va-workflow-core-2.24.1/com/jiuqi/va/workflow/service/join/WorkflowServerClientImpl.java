/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateService
 *  com.jiuqi.va.domain.delegate.DelegateVO
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.workflow.NodeView
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.domain.workflow.WorkflowQueryDTO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  com.jiuqi.va.domain.workflow.formula.WorkflowFormulaDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.join;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateService;
import com.jiuqi.va.domain.delegate.DelegateVO;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.workflow.NodeView;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.domain.workflow.WorkflowQueryDTO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.domain.workflow.formula.WorkflowFormulaDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.controller.WorkflowController;
import com.jiuqi.va.workflow.controller.WorkflowDefineController;
import com.jiuqi.va.workflow.controller.WorkflowFormulaController;
import com.jiuqi.va.workflow.domain.BillWorkflowNodeVO;
import com.jiuqi.va.workflow.service.WorkflowBusinessService;
import com.jiuqi.va.workflow.service.WorkflowCommentService;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.service.WorkflowProcessInstanceService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class WorkflowServerClientImpl
implements WorkflowServerClient {
    private static final Logger log = LoggerFactory.getLogger(WorkflowServerClientImpl.class);
    @Autowired
    private WorkflowBusinessService workflowBusinessService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowProcessInstanceService workflowProcessInstanceService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowOptionService optionFoService;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private DelegateService delegateService;
    @Autowired
    private WorkflowSevice workflowSevice;
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private WorkflowController workflowController;
    @Autowired
    private WorkflowDefineController workflowDefineController;
    @Autowired
    private WorkflowFormulaController workflowFormulaController;
    @Autowired
    private WorkflowProcessNodeExtendService workflowProcessNodeExtendService;
    @Autowired
    private WorkflowCommentService workflowCommentService;
    @Autowired
    private WorkflowRetractLockService workflowRetractLockService;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    @Autowired
    private WorkflowNextNodeService workflowNextNodeService;

    public R getBusinessBoundedWorkflow(WorkflowBusinessDTO workflowBusinessDTO) {
        Utils.setTraceId((String)workflowBusinessDTO.getTraceId());
        if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        boolean showTitle = workflowBusinessDTO.isShowTitle();
        WorkflowBusinessDTO object = this.workflowBusinessService.get(workflowBusinessDTO, showTitle);
        String jsonString = JSONUtil.toJSONString((Object)object, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        Map map = JSONUtil.parseMap((String)jsonString);
        R r = new R();
        r.put("data", (Object)map);
        return r;
    }

    public R batchGetBusinessWorkflowBindingInfo(WorkflowBusinessDTO workflowBusinessDTO) {
        List businessesWorkflows = workflowBusinessDTO.getBusinessesWorkflows();
        if (CollectionUtils.isEmpty(businessesWorkflows)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List<Map<String, Object>> result = this.workflowBusinessService.batchGetBusinessWorkflowBindingInfo(workflowBusinessDTO);
        String jsonString = JSONUtil.toJSONString(result, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        List data = JSONUtil.parseMapArray((String)jsonString);
        R r = new R();
        r.put("data", (Object)data);
        return r;
    }

    public R getWorkflowBoundedBusiness(WorkflowBusinessDTO workflowBusinessDTO) {
        Utils.setTraceId((String)workflowBusinessDTO.getTraceId());
        if (!StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        boolean showTitle = workflowBusinessDTO.isShowTitle();
        WorkflowBusinessDTO object = this.workflowBusinessService.getBusiness(workflowBusinessDTO, showTitle);
        String jsonString = JSONUtil.toJSONString((Object)object, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        Map map = JSONUtil.parseMap((String)jsonString);
        R r = new R();
        r.put("data", (Object)map);
        return r;
    }

    public R saveBusiness(WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowBusinessService.saveBusiness(workflowBusinessDTO);
    }

    public R saveBusinessWorkflowBindingInfo(WorkflowBusinessDTO workflowBusinessDTO) {
        List workflows = workflowBusinessDTO.getWorkflows();
        if (CollectionUtils.isEmpty(workflows)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowBusinessService.batchSave(workflowBusinessDTO);
    }

    public R listBillWorkflowNode(TenantDO tenantDO) {
        PageVO<BillWorkflowNodeVO> billWorkflowNodeVOPageVO = this.workflowBusinessService.listBillWorkflowNode(tenantDO);
        R r = new R();
        r.put("data", (Object)billWorkflowNodeVOPageVO.getRows());
        return r;
    }

    public R startProcess(WorkflowDTO workflowDTO) {
        return this.workflowController.startProcess(workflowDTO);
    }

    public R completeTask(WorkflowDTO workflowDTO) {
        return this.workflowController.completeTask(workflowDTO);
    }

    public R retractProcess(WorkflowDTO workflowDTO) {
        return this.workflowController.retractProcess(workflowDTO);
    }

    public List<ProcessNodeDO> listProcessNode(ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid()) && !StringUtils.hasText(processNodeDTO.getNodeid()) && CollectionUtils.isEmpty(processNodeDTO.getProcessIdList()) && CollectionUtils.isEmpty(processNodeDTO.getBizCodes())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessNodeService.listProcessNode(processNodeDTO);
    }

    public ProcessNodeDO getProcessNode(ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessNodeService.get(processNodeDTO);
    }

    public R addProcessNode(ProcessNodeDO processNode) {
        if (!StringUtils.hasText(processNode.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        this.workflowProcessNodeService.add(processNode);
        return R.ok();
    }

    public R addBatchProcessNode(TenantDO tenantDO) {
        Object obj = tenantDO.getExtInfo("processNodes");
        if (ObjectUtils.isEmpty(obj)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)obj), ProcessNodeDO.class);
        this.workflowProcessNodeService.addBatch(processNodes, tenantDO.getTenantName());
        return R.ok();
    }

    public R updateProcessNode(ProcessNodeDO processNode) {
        if (!StringUtils.hasText(processNode.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        this.workflowProcessNodeService.update(processNode);
        return R.ok();
    }

    public R completeBatchProcessNode(TenantDO tenantDO) {
        try {
            this.workflowProcessNodeService.completeBatch(tenantDO);
        }
        catch (Exception e) {
            log.error("completeBatchProcessNode\u53d1\u751f\u5f02\u5e38", e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    public R deleteFinishProcessNode(ProcessNodeDO processNode) {
        return this.workflowProcessNodeService.deleteFinishProcessNode(processNode);
    }

    public R getFinalApprover(TenantDO tenantDO) {
        return this.workflowController.getFinalApprover(tenantDO);
    }

    public R getNodeConfig(TenantDO tenantDO) {
        R nodeConfig = this.workflowDefineController.getNodeConfig(tenantDO);
        String jsonString = JSONUtil.toJSONString((Object)nodeConfig, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public R processForecast(WorkflowDTO workflowDTO) {
        R r = this.workflowProcessInstanceService.processForecast(workflowDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public List<NodeView> processView(ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid())) {
            return Collections.emptyList();
        }
        return this.workflowProcessNodeService.processView(processNodeDTO);
    }

    public R getNode(WorkflowDTO workflowDTO) {
        R r = this.workflowDefineController.getNode(workflowDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public R getProcess(ProcessDTO processDTO) {
        try {
            ProcessDO object = this.vaWorkflowProcessService.get(processDTO);
            String jsonString = JSONUtil.toJSONString((Object)object, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
            Map map = JSONUtil.parseMap((String)jsonString);
            R r = R.ok();
            r.put("process", (Object)map);
            return r;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u5f53\u524d\u5de5\u4f5c\u6d41\u53d1\u751f\u5f02\u5e38", e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getworkflowerror"));
        }
    }

    public R processHistory(ProcessDTO processDTO) {
        if (!StringUtils.hasText(processDTO.getBizcode()) && !StringUtils.hasText(processDTO.getId())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processList = this.vaWorkflowProcessService.listHistory(processDTO);
        String jsonString = JSONUtil.toJSONString((Object)processList, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        List list = JSONUtil.parseMapArray((String)jsonString);
        R r = new R();
        r.put("processList", (Object)list);
        return r;
    }

    public R nextnodeApprover(WorkflowDTO workflowDTO) {
        R r = this.workflowNextNodeService.getNextNodeApprover(workflowDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public R execute(WorkflowFormulaDTO workflowFormulaDTO) {
        R r = this.workflowFormulaController.execute(workflowFormulaDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public List<OptionItemVO> listWorkflowOption(OptionItemDTO param) {
        return this.optionFoService.list(param);
    }

    public R currNodeProperties(WorkflowDTO workflowDTO) {
        R r = this.workflowProcessNodeService.currNodeProperties(workflowDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public R updateCompleteUserAndReceiveTime(ProcessNodeDO processNode) {
        return this.workflowProcessNodeService.updateCompleteUserAndReceiveTime(processNode);
    }

    public R deleteBatchProcessNode(TenantDO tenantDO) {
        Object obj = tenantDO.getExtInfo("processNodes");
        if (ObjectUtils.isEmpty(obj)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)obj), ProcessNodeDO.class);
        this.workflowProcessNodeService.deleteBatch(processNodes, tenantDO.getTenantName());
        return R.ok();
    }

    public List<ProcessRejectNodeDO> listProcessRejectNode(ProcessRejectNodeDO processRejectNodeDO) {
        if (!StringUtils.hasText(processRejectNodeDO.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
    }

    public R listNextApproverInfo(TenantDO tenantDO) {
        R r = R.ok();
        Map<String, Map<String, String>> resultMap = this.workflowHelperService.listNextApproveInfo(tenantDO);
        r.put("data", resultMap);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public R getProcessInfo(TenantDO tenantDO) {
        R r = this.workflowProcessInstanceService.getProcessInfo(tenantDO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public R getBillWorkflowProcess(ProcessDTO processDTO) {
        try {
            ProcessDO object = this.vaWorkflowProcessService.getBillProcess(processDTO);
            String jsonString = JSONUtil.toJSONString((Object)object, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
            Map map = JSONUtil.parseMap((String)jsonString);
            R r = R.ok();
            r.put("process", (Object)map);
            return r;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u5355\u636e\u6267\u884c\u4e2d\u7684\u5de5\u4f5c\u6d41\u53d1\u751f\u5f02\u5e38", e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getworkflowerror"));
        }
    }

    public R getNextNodeConfig(WorkflowDTO workflowDTO) {
        R r = this.workflowNextNodeService.getNextNodeConfig(workflowDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }

    public PageVO<DelegateVO> pageDelegateDto(DelegateDTO delegateDto) {
        List list = this.delegateService.query(delegateDto);
        delegateDto.setPagination(false);
        int total = this.delegateService.count(delegateDto);
        return new PageVO(list, total);
    }

    public R getWorkflowDesignImage(TenantDO tenantDO) {
        return this.workflowSevice.getWorkflowDesignImage(tenantDO);
    }

    public List<PlusApprovalInfoDO> listPlusApprovalInfoDto(PlusApprovalInfoDO plusApprovalInfoDO) {
        return this.workflowPlusApprovalService.listPlusApprovalInfoDto(plusApprovalInfoDO);
    }

    public R reopenLastNode(WorkflowDTO workflowDTO) {
        return this.workflowController.reopenLastNode(workflowDTO);
    }

    public R checkRetract(WorkflowDTO workflowDTO) {
        return this.workflowController.checkRetract(workflowDTO);
    }

    public List<PlusApprovalInfoDO> selectByConditionAndNodeIds(PlusApprovalInfoDTO info) {
        return this.workflowPlusApprovalService.selectByConditionAndNodeIds(info);
    }

    public R terminateProcessInstances(TenantDO tenantDO) {
        try {
            R r = this.workflowProcessInstanceService.changeProcessStatus(tenantDO, WorkflowProcessStatus.PROCESS_FINSHED_RETRACT);
            String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
            return (R)JSONUtil.parseObject((String)jsonString, R.class);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.terminateprocessfailed"));
        }
    }

    public R getWorkflowAttrOfTodoColumn(WorkflowQueryDTO workflowQueryDTO) {
        R ok = new R();
        List processNodeList = workflowQueryDTO.getProcessNodeList();
        if (CollectionUtils.isEmpty(processNodeList)) {
            return ok;
        }
        try {
            boolean currNodeFlag;
            Object prevNodeCommentFlagObj = workflowQueryDTO.getExtInfo("prevNodeCommentFlag");
            Object currNodeFlagObj = workflowQueryDTO.getExtInfo("currNodeFlag");
            boolean prevNodeCommentFlag = prevNodeCommentFlagObj != null && (Boolean)prevNodeCommentFlagObj != false;
            boolean bl = currNodeFlag = currNodeFlagObj != null && (Boolean)currNodeFlagObj != false;
            if (!prevNodeCommentFlag && !currNodeFlag) {
                return ok;
            }
            Map workflowAttributes = this.workflowProcessNodeService.getPreNodeCommAndCurrNode(processNodeList, prevNodeCommentFlag, currNodeFlag);
            ok.put("workflowAttributes", (Object)workflowAttributes);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return ok;
    }

    public R extractBizFields(TenantDO tenantDO) {
        R ok = R.ok();
        Object workflowsObj = tenantDO.getExtInfo("workflows");
        if (workflowsObj instanceof List) {
            List workflows = (List)workflowsObj;
            if (CollectionUtils.isEmpty(workflows)) {
                return ok;
            }
            Set<String> bizField = this.workflowBusinessService.extractBizFields(workflows);
            ok = ok.put("bizFields", new ArrayList<String>(bizField));
        }
        return ok;
    }

    public R judgeSatisfyAdaptCondition(TenantDO tenantDO) {
        R ok = R.ok();
        List workflows = (List)tenantDO.getExtInfo("workflows");
        if (CollectionUtils.isEmpty(workflows)) {
            return ok;
        }
        Map bizFieldDataMap = (Map)tenantDO.getExtInfo("bizFieldDataMap");
        Set<String> submittableWorkflowKeys = this.workflowBusinessService.judgeSatisfyAdaptCondition(workflows, bizFieldDataMap);
        HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
        for (Map workflow : workflows) {
            String workflowdefinekey = (String)workflow.get("workflowdefinekey");
            if (submittableWorkflowKeys.contains(workflowdefinekey)) {
                resultMap.put(workflowdefinekey, true);
                continue;
            }
            resultMap.put(workflowdefinekey, false);
        }
        return ok.put("workflows", resultMap);
    }

    public R checkRejectToSubmitter(WorkflowDTO workflowDTO) {
        try {
            boolean result = this.workflowProcessRejectNodeService.checkRejectToSubmitter(workflowDTO);
            R r = new R();
            r.put("rejectToSubmitter", (Object)result);
            return r;
        }
        catch (Exception e) {
            log.error("\u6821\u9a8c\u662f\u5426\u9a73\u56de\u5230\u63d0\u4ea4\u4eba\u5931\u8d25", e);
            return R.error((String)e.getMessage());
        }
    }

    public R plusApprovalUser(TenantDO tenantDO) {
        String traceId = GUID.newGUID();
        Utils.setTraceId((String)traceId);
        tenantDO.setTraceId(traceId);
        R r = R.ok();
        Runnable setPlusApprovalRunnable = () -> {
            try {
                this.workflowPlusApprovalService.setPlusApproval(tenantDO);
            }
            catch (Exception e) {
                log.error("\u52a0\u7b7e\u5f02\u5e38", e);
                r.setMsg(1, e.getMessage());
            }
            finally {
                VaContext.removeVaWorkflowContext();
            }
        };
        R redisResult = R.ok();
        String processInstanceId = (String)tenantDO.getExtInfo("PROCESSID");
        String lockKey = tenantDO.getTenantName() + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)setPlusApprovalRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    public R getNoUser(PlusApprovalInfoDTO info) {
        List list;
        try {
            list = this.workflowPlusApprovalService.getNoUser(info);
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
        R r = new R();
        r.put("data", (Object)list);
        return r;
    }

    public R publishWithoutDesign(WorkflowBusinessDTO workflowBusinessDTO) {
        R r = R.ok();
        try {
            this.workflowBusinessService.publishWithoutDesign(workflowBusinessDTO);
            return r;
        }
        catch (Exception e) {
            log.error("\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u76f4\u63a5\u53d1\u5e03\u5931\u8d25\uff0c", e);
            return R.error((String)e.getMessage());
        }
    }

    public R listSpecifyNodeApprovalInfo(PlusApprovalInfoDTO infoDTO) {
        if (!StringUtils.hasText(infoDTO.getNodecode()) || !StringUtils.hasText(infoDTO.getNodeid())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            List approvalInfos = this.workflowPlusApprovalService.listSpecifyNodeApprovalInfo(infoDTO);
            return R.ok().put("approvalInfos", (Object)approvalInfos);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R batchUpdatePlusApproval(PlusApprovalInfoDTO plusApprovalInfoDTO) {
        List infoDOs = plusApprovalInfoDTO.getPlusApprovalInfoDOs();
        if (CollectionUtils.isEmpty(infoDOs)) {
            return R.ok();
        }
        try {
            this.workflowPlusApprovalService.updatePlusApprovalInfoList(infoDOs);
            return R.ok();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R queryNodeCodeTitleInfo(TenantDO tenantDO) {
        try {
            Object processDefinesObj = tenantDO.getExtInfo("processDefines");
            R ok = R.ok();
            if (processDefinesObj instanceof List) {
                List processDefines = (List)processDefinesObj;
                Map nodeCodeMap = this.workflowProcessNodeExtendService.getNodeCodeMapList(processDefines);
                ok.put("nodeCodeMap", (Object)nodeCodeMap);
            }
            return ok;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R addWorkflowComment(WorkflowCommentDTO workflowCommentDTO) {
        R r;
        try {
            this.workflowCommentService.add(workflowCommentDTO);
            r = R.ok((String)"\u6dfb\u52a0\u5e38\u7528\u610f\u89c1\u6210\u529f");
        }
        catch (Exception e) {
            log.error("\u6dfb\u52a0\u5e38\u7528\u610f\u89c1\u5931\u8d25", e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    public R lockRetract(WorkflowRetractLockDTO retractLockDTO) {
        String bizCode = retractLockDTO.getBizcode();
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        String lockKey = processDO == null ? retractLockDTO.getTenantName() + ":SUBMIT:" + bizCode : retractLockDTO.getTenantName() + "processInstanceId:" + processDO.getId();
        R r = R.ok();
        Runnable lockRetractRunnable = () -> {
            try {
                this.workflowRetractLockService.add(retractLockDTO);
            }
            catch (Exception e) {
                log.error("{}\u6dfb\u52a0\u53d6\u56de\u9501\u5b9a\u5931\u8d25\uff1a{}", bizCode, e.getMessage(), e);
                r.setMsg(1, e.getMessage());
            }
        };
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)lockRetractRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    public R listNextNode(WorkflowDTO workflowDTO) {
        R r = this.workflowNextNodeService.listNextNode(workflowDTO);
        String jsonString = JSONUtil.toJSONString((Object)r, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
        return (R)JSONUtil.parseObject((String)jsonString, R.class);
    }
}

