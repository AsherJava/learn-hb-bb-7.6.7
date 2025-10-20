/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula
 *  com.jiuqi.va.biz.intf.workflow.detection.ParamExtract
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.node.WorkflowQueryRes
 *  com.jiuqi.va.feign.client.BillBusinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.va.bill.service.BillBusinessService;
import com.jiuqi.va.bill.service.BillDataEditService;
import com.jiuqi.va.bill.service.BillParamExtractService;
import com.jiuqi.va.bill.service.ProcessForecastService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula;
import com.jiuqi.va.biz.intf.workflow.detection.ParamExtract;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.node.WorkflowQueryRes;
import com.jiuqi.va.feign.client.BillBusinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillBusinessController {
    private static final Logger log = LoggerFactory.getLogger(BillBusinessController.class);
    @Autowired
    private BillDataEditService billDataEditService;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private BillParamExtractService billParamExtractService;
    @Autowired
    private ProcessForecastService processForecastService;
    @Autowired
    private BillBusinessService billBusinessService;

    @PostMapping(value={"bill/bussiness/state/update"})
    public R updateBussinessState(@RequestBody TenantDO tenantDO) {
        BillBusinessController.setTraceId(tenantDO);
        try {
            return this.billDataEditService.changeBillState(tenantDO);
        }
        catch (Exception e) {
            return R.error((String)(BillCoreI18nUtil.getMessage("va.billcore.billdataeditcontroller.modifybillstatusfailed") + e.getMessage()));
        }
    }

    @PostMapping(value={"bill/bussiness/param/variables/get"})
    public Map<String, Object> getBussinessParamVariables(@RequestBody TenantDO tenantDO) {
        BillBusinessController.setTraceId(tenantDO);
        return this.billDataEditService.getBussinessParamVariables(tenantDO);
    }

    @PostMapping(value={"/bill/todo/param/get"})
    public Map<String, Object> getBillTodoParam(@RequestBody TenantDO tenantDO) {
        BillBusinessController.setTraceId(tenantDO);
        return this.billDataEditService.getTodoParam(tenantDO);
    }

    @PostMapping(value={"bill/biz/todo/param/get"})
    public Map<String, Object> getBussinessTodoParam(@RequestBody TenantDO tenantDO) {
        BillBusinessController.setTraceId(tenantDO);
        String bizDefine = (String)tenantDO.getExtInfo("bizDefine");
        BillBusinessClient billBusinessClient = this.billUtils.getBillBusinessClientByDefineCode(bizDefine);
        return billBusinessClient.getBillTodoParam(tenantDO);
    }

    @PostMapping(value={"bill/bussiness/message/template/param/get/map"})
    public Map<String, Object> getMessageTemplateParam(@RequestBody TenantDO tenantDO) {
        BillBusinessController.setTraceId(tenantDO);
        return this.billDataEditService.getMessageTemplateParam(tenantDO);
    }

    @PostMapping(value={"/bill/biz/metas/tree"})
    public PageVO<MetaTreeInfoDTO> getAllMetas(@RequestBody(required=false) TenantDO tenantDO) {
        return this.metaDataClient.getAllMetas(tenantDO);
    }

    @PostMapping(value={"/bill/biz/title/get"})
    public R getBizTitle(@RequestBody TenantDO tenantDO) {
        String title;
        BillBusinessController.setTraceId(tenantDO);
        String bizDefine = (String)tenantDO.getExtInfo("bizDefine");
        tenantDO.addExtInfo("defineCode", (Object)bizDefine);
        R r = this.metaDataClient.findMetaInfoByDefineCode(tenantDO);
        if (r != null && r.getCode() == 0 && StringUtils.hasText(title = (String)r.get((Object)"title"))) {
            r.put("bizDefineTitle", (Object)title);
        }
        return r;
    }

    @RequestMapping(value={"/bill/bussiness/process/detection/param/extract"})
    public Map<String, Object> getWorkflowDetectionParam(@RequestBody TenantDO tenantDO) {
        WorkflowDetectionFormula workflowDetectionFormula;
        String bizType;
        List<ParamExtract> paramExtractBeanList;
        BillBusinessController.setTraceId(tenantDO);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Object object = tenantDO.getExtInfo("workflowDetectionFormula");
        if (object instanceof Map && !CollectionUtils.isEmpty(paramExtractBeanList = this.billParamExtractService.getParamExtractBeanList(bizType = (workflowDetectionFormula = (WorkflowDetectionFormula)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)object), WorkflowDetectionFormula.class)).getBizType()))) {
            for (ParamExtract paramExtract : paramExtractBeanList) {
                resultMap.putAll(paramExtract.getWorkflowDetectionParam(workflowDetectionFormula));
            }
        }
        return resultMap;
    }

    @PostMapping(value={"/bill/business/process/detection/paramsValueMap/get"})
    Map<String, Object> getWorkflowParamsValueMap(@RequestBody TenantDO tenantDO) {
        BillBusinessController.setTraceId(tenantDO);
        Map billWorkflowRelation = (Map)tenantDO.getExtInfo("workflow");
        Map bizValues = (Map)tenantDO.getExtInfo("bizValues");
        String defineCode = (String)tenantDO.getExtInfo("defineCode");
        return this.billParamExtractService.getWorkflowBusinessParam(bizValues, defineCode, billWorkflowRelation);
    }

    @PostMapping(value={"/bill/business/process/view/forecast"})
    public R getProcessForecastInfo(@RequestBody WorkflowProcessForecastDTO workflowProcessForecastDTO) {
        R r;
        try {
            r = R.ok();
            r.put("data", this.processForecastService.processViewForecast(workflowProcessForecastDTO));
        }
        catch (Exception e) {
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/bill/business/distribute/param/handle"})
    public R handleDistributeParam(@RequestBody WorkflowBusinessDistributeDTO distributeDTO) {
        R r = R.ok();
        try {
            r.put("data", this.billBusinessService.handleDistributeParams(distributeDTO));
        }
        catch (Exception e) {
            log.error("\u5904\u7406\u4e0b\u53d1\u53c2\u6570\u5931\u8d25\uff0c", e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/bill/business/process/node/processed"})
    public WorkflowQueryRes businessProcessNodeProcessed(@RequestBody ProcessNodeDTO processNodeDTO) {
        List processNodes = processNodeDTO.getProcessNodes();
        if (CollectionUtils.isEmpty(processNodes)) {
            return WorkflowQueryRes.ok();
        }
        try {
            WorkflowQueryRes res = new WorkflowQueryRes();
            List<ProcessNodeDO> result = this.billBusinessService.businessProcessNodeProcessed(processNodes);
            res.setProcessNodes(result);
            return res;
        }
        catch (Exception e) {
            return WorkflowQueryRes.error((String)e.getMessage());
        }
    }

    private static void setTraceId(TenantDO tenantDO) {
        String traceId = tenantDO.getTraceId();
        if (StringUtils.hasText(traceId)) {
            Utils.setTraceId((String)traceId);
        } else {
            Utils.setTraceId((String)GUID.newGUID());
        }
    }
}

