/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishVO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.domain.BillWorkflowNodeVO;
import com.jiuqi.va.workflow.domain.BusinessWorkflowTreeVO;
import com.jiuqi.va.workflow.domain.workflowbusiness.WorkflowBusinessRelation;
import com.jiuqi.va.workflow.service.WorkflowBusinessService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/business"})
public class WorkflowBusinessController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowBusinessController.class);
    @Autowired
    private WorkflowBusinessService workflowBusinessService;

    @PostMapping(value={"/list"})
    public PageVO<WorkflowBusinessDTO> listBusinessBoundedWorkflow(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        boolean pagination = workflowBusinessDTO.isPagination();
        workflowBusinessDTO.setPagination(false);
        List<WorkflowBusinessDTO> workflowBusinessList = this.workflowBusinessService.list(workflowBusinessDTO);
        workflowBusinessDTO.setPagination(pagination);
        return this.getWorkflowBusinessPageVO(workflowBusinessDTO, workflowBusinessList);
    }

    @PostMapping(value={"/businesslist"})
    public PageVO<WorkflowBusinessDTO> listWorkflowBoundedBusiness(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        boolean pagination = workflowBusinessDTO.isPagination();
        workflowBusinessDTO.setPagination(false);
        List<WorkflowBusinessDTO> workflowBusinessList = this.workflowBusinessService.businesslist(workflowBusinessDTO);
        workflowBusinessDTO.setPagination(pagination);
        return this.getWorkflowBusinessPageVO(workflowBusinessDTO, workflowBusinessList);
    }

    @PostMapping(value={"/get"})
    public R getBusinessBoundedWorkflow(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        boolean showTitle = workflowBusinessDTO.isShowTitle();
        R r = new R();
        r.put("data", (Object)this.workflowBusinessService.get(workflowBusinessDTO, showTitle));
        return r;
    }

    @PostMapping(value={"/batch/get"})
    public R batchGetBusinessWorkflowBindingInfo(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        List businessesWorkflows = workflowBusinessDTO.getBusinessesWorkflows();
        if (CollectionUtils.isEmpty(businessesWorkflows)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        R r = new R();
        r.put("data", this.workflowBusinessService.batchGetBusinessWorkflowBindingInfo(workflowBusinessDTO));
        return r;
    }

    @PostMapping(value={"/getBusiness"})
    public R getWorkflowBoundedBusiness(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        boolean showTitle = workflowBusinessDTO.isShowTitle();
        R r = new R();
        r.put("data", (Object)this.workflowBusinessService.getBusiness(workflowBusinessDTO, showTitle));
        return r;
    }

    @PostMapping(value={"/orgview/list"})
    public PageVO<WorkflowBusinessRelation> getBindingRelationByOrg(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        PageVO pageVO;
        try {
            pageVO = this.workflowBusinessService.getBindingRelationByOrg(workflowBusinessDTO);
        }
        catch (Exception e) {
            log.error("\u7ec4\u7ec7\u673a\u6784\u89c6\u89d2\u83b7\u53d6\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u5173\u7cfb\u5931\u8d25", e);
            pageVO = new PageVO(null, 0, R.error((String)e.getMessage()));
        }
        return pageVO;
    }

    @PostMapping(value={"version/list"})
    public R getBusinessBoundedWorkflowVersions(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        boolean showTitle = true;
        workflowBusinessDTO = this.workflowBusinessService.getVersions(workflowBusinessDTO, showTitle);
        R r = new R();
        r.put("data", (Object)workflowBusinessDTO);
        return r;
    }

    @PostMapping(value={"/save"})
    public R save(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowBusinessService.save(workflowBusinessDTO);
    }

    @PostMapping(value={"/batch/save"})
    public R batchSaveBindingInfo(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (CollectionUtils.isEmpty(workflowBusinessDTO.getWorkflows())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowBusinessService.batchSave(workflowBusinessDTO);
    }

    @PostMapping(value={"/saveBusiness"})
    public R saveBusiness(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowBusinessService.saveBusiness(workflowBusinessDTO);
    }

    @PostMapping(value={"/version/update"})
    public R update(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        R r = R.ok();
        WorkflowBusinessPublishVO publishVO = this.workflowBusinessService.update(workflowBusinessDTO);
        r.put("successData", (Object)publishVO.getSuccessData());
        r.put("failedData", (Object)publishVO.getFailedData());
        return r;
    }

    @PostMapping(value={"/delete"})
    public R delete(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode()) || !StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowBusinessService.delete(workflowBusinessDTO);
            return R.ok();
        }
        catch (Exception e) {
            log.error("\u5220\u9664\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u5173\u7cfb\u5931\u8d25\uff0c", e);
            return R.error((String)e.getMessage());
        }
    }

    private PageVO<WorkflowBusinessDTO> getWorkflowBusinessPageVO(WorkflowBusinessDTO workflowBusinessDTO, List<WorkflowBusinessDTO> workflowBusinessList) {
        PageVO pageVO = new PageVO();
        boolean isPagination = workflowBusinessDTO.isPagination();
        if (CollectionUtils.isEmpty(workflowBusinessList)) {
            pageVO.setRows(Collections.emptyList());
            pageVO.setTotal(0);
            return pageVO;
        }
        if (isPagination) {
            int offset;
            int limit = workflowBusinessDTO.getLimit();
            if (limit + (offset = workflowBusinessDTO.getOffset()) <= workflowBusinessList.size()) {
                pageVO.setRows(workflowBusinessList.subList(offset, offset + limit));
            } else {
                pageVO.setRows(workflowBusinessList.subList(offset, workflowBusinessList.size()));
            }
            pageVO.setTotal(workflowBusinessList.size());
        } else {
            pageVO.setRows(workflowBusinessList);
            pageVO.setTotal(workflowBusinessList.size());
        }
        return pageVO;
    }

    @GetMapping(value={"/getViews"})
    public List<Map<String, String>> getViews() {
        ArrayList<Map<String, String>> viewList = new ArrayList<Map<String, String>>();
        HashMap<String, String> workflowMap = new HashMap<String, String>();
        workflowMap.put("code", "workflowView");
        workflowMap.put("title", VaWorkFlowI18nUtils.getInfo("va.workflow.workflowview"));
        viewList.add(workflowMap);
        HashMap<String, String> billMap = new HashMap<String, String>();
        billMap.put("code", "billView");
        billMap.put("title", VaWorkFlowI18nUtils.getInfo("va.workflow.businesspersctive"));
        viewList.add(billMap);
        HashMap<String, String> orgMap = new HashMap<String, String>();
        orgMap.put("code", "orgView");
        orgMap.put("title", VaWorkFlowI18nUtils.getInfo("va.workflow.orgview"));
        viewList.add(orgMap);
        return viewList;
    }

    @RequestMapping(value={"/tree"})
    public List<BusinessWorkflowTreeVO> getBusinessWorkflowTree(@RequestBody TenantDO tenantDO) {
        return this.workflowBusinessService.getBusinessWorkflowTree(tenantDO);
    }

    @PostMapping(value={"/nodeinfo/list"})
    public PageVO<BillWorkflowNodeVO> listBillWorkflowNode(@RequestBody TenantDO tenantDO) {
        return this.workflowBusinessService.listBillWorkflowNode(tenantDO);
    }

    @PostMapping(value={"/distribute"})
    public R distribute(@RequestBody WorkflowBusinessDistributeDTO workflowBusinessDistributeDTO) {
        return this.workflowBusinessService.distribute(workflowBusinessDistributeDTO);
    }

    @PostMapping(value={"/distribute/result"})
    R getDistributeResult(@RequestBody WorkflowBusinessDistributeDTO distributionDTO) {
        return this.workflowBusinessService.getDistributeResult(distributionDTO);
    }

    @PostMapping(value={"/extractBizFields"})
    public R extractBizFields(@RequestBody TenantDO tenantDO) {
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

    @PostMapping(value={"/judgeSatisfyAdaptCondition"})
    R judgeSatisfyAdaptCondition(@RequestBody TenantDO tenantDO) {
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

    @PostMapping(value={"/publish/get"})
    public WorkflowBusinessPublishVO getWorkflowBusinessPublishData(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        return this.workflowBusinessService.getWorkflowBusinessPublishData(workflowBusinessDTO);
    }

    @PostMapping(value={"/update/get"})
    public WorkflowBusinessDTO getWorkflowBusinessUpdateData(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        return this.workflowBusinessService.getWorkflowBusinessUpdateData(workflowBusinessDTO);
    }

    @PostMapping(value={"/publish/check"})
    public R publishCheck(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        try {
            R r = R.ok();
            r.put("checkResult", (Object)this.workflowBusinessService.publishCheck(workflowBusinessDTO));
            return r;
        }
        catch (Exception e) {
            log.error("\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u53d1\u5e03\u6821\u9a8c\u5931\u8d25", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/publish/dep"})
    public R publish(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        R r = R.ok();
        WorkflowBusinessPublishVO publishVO = this.workflowBusinessService.publish(workflowBusinessDTO);
        r.put("successData", (Object)publishVO.getSuccessData());
        r.put("failedData", (Object)publishVO.getFailedData());
        return r;
    }

    @PostMapping(value={"/lock"})
    public R lock(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        try {
            workflowBusinessDTO.setLockflag(Integer.valueOf(1));
            workflowBusinessDTO.setLockuser(ShiroUtil.getUser().getId());
            this.workflowBusinessService.updateLockFlag(workflowBusinessDTO);
            return R.ok((String)"\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u5173\u7cfb\u5df2\u9501\u5b9a");
        }
        catch (Exception e) {
            log.error("\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u5173\u7cfb\u9501\u5b9a\u5931\u8d25\uff0c", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/unlock"})
    public R unLock(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        try {
            workflowBusinessDTO.setLockflag(Integer.valueOf(0));
            this.workflowBusinessService.updateLockFlag(workflowBusinessDTO);
            return R.ok((String)"\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u5173\u7cfb\u5df2\u89e3\u9501");
        }
        catch (Exception e) {
            log.error("\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u5173\u7cfb\u89e3\u9501\u5931\u8d25\uff0c", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/version/reset"})
    public R reset(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
        try {
            this.workflowBusinessService.reset(workflowBusinessDTO);
            return R.ok((String)"\u7248\u672c\u91cd\u7f6e\u6210\u529f");
        }
        catch (Exception e) {
            log.error("\u7248\u672c\u91cd\u7f6e\u5931\u8d25\uff0c", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/workflow/business/publish-without-design"})
    R publishWithoutDesign(@RequestBody WorkflowBusinessDTO workflowBusinessDTO) {
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
}

