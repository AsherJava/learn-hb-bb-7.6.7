/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.process.ProcessForecast
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.action.ProcessForecastAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.BillCoreWorkFlowService;
import com.jiuqi.va.bill.service.ProcessForecastService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.process.ProcessForecast;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ProcessForecastServiceImpl
implements ProcessForecastService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessForecastServiceImpl.class);
    @Autowired(required=false)
    private List<ProcessForecast> processForecasts;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private BillCodeClient billCodeClient;
    @Autowired
    private BillCoreWorkFlowService billCoreWorkFlowService;
    @Autowired
    private WorkflowServerClient workflowServerClient;

    @PostConstruct
    public void init() {
        this.forecastNodeInfoGather();
    }

    private void forecastNodeInfoGather() {
        List processForecastList = this.processForecasts.stream().sorted(Comparator.comparing(ProcessForecast::getForecastOrder)).collect(Collectors.toList());
        List workflowProcessForecastList = processForecastList.stream().filter(processForecast -> "FORECAST_WORKFLOW".equals(processForecast.getForecastCode())).collect(Collectors.toList());
        if (workflowProcessForecastList.size() > 1) {
            processForecastList.remove(0);
        }
        this.processForecasts = processForecastList;
    }

    @Override
    public R getAllNodes(Model model, Map<String, Object> params) {
        ArrayList forecastNodeInfos = new ArrayList();
        HashMap<String, Object> startNode = new HashMap<String, Object>();
        startNode.put("nodeName", BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processforecast.start"));
        HashMap<String, String> startNodeNameMap = new HashMap<String, String>();
        startNodeNameMap.put("title", "\u5f00\u59cb");
        startNodeNameMap.put("value", "\u5f00\u59cb");
        startNode.put("nodeNameMap", startNodeNameMap);
        startNode.put("nodeType", "StartNoneEvent");
        startNode.put("auditState", 1);
        forecastNodeInfos.add(startNode);
        try {
            for (ProcessForecast processForecast : this.processForecasts) {
                List nodes = processForecast.getForecastNodeInfos(model, params);
                forecastNodeInfos.addAll(nodes);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        HashMap<String, Object> endNode = new HashMap<String, Object>();
        endNode.put("nodeName", BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processforecast.end"));
        HashMap<String, String> endNodeNameMap = new HashMap<String, String>();
        endNodeNameMap.put("title", "\u7ed3\u675f");
        endNodeNameMap.put("value", "\u7ed3\u675f");
        Map lastNode = (Map)forecastNodeInfos.get(forecastNodeInfos.size() - 1);
        if (lastNode.get("auditState") != null && (Integer)lastNode.get("auditState") == 1) {
            endNode.put("auditState", 1);
        } else {
            endNode.put("auditState", 3);
        }
        endNode.put("nodeNameMap", endNodeNameMap);
        endNode.put("nodeType", "EndNoneEvent");
        forecastNodeInfos.add(endNode);
        return R.ok(forecastNodeInfos);
    }

    @Override
    public R getForecastNodeInfo(String bizCode, String stencilId) {
        for (ProcessForecast processForecast : this.processForecasts) {
            Map forecastNodeInfo = processForecast.getForecastNodeInfo(bizCode, stencilId);
            if (CollectionUtils.isEmpty(forecastNodeInfo)) continue;
            return R.ok((Object)forecastNodeInfo);
        }
        return R.error((String)BillCoreI18nUtil.getMessage("va.bill.core.process.forecast.error"));
    }

    @Override
    public List<Map<String, Object>> processViewForecast(WorkflowProcessForecastDTO workflowProcessForecastDTO) {
        String bizCode = workflowProcessForecastDTO.getBizCode();
        String workflowDefineKey = workflowProcessForecastDTO.getWorkflowDefineKey();
        Long workflowDefineVersion = workflowProcessForecastDTO.getWorkflowDefineVersion();
        HashMap<String, String> extInfo = new HashMap<String, String>();
        extInfo.put("billCode", bizCode);
        TenantDO tenant = new TenantDO();
        tenant.setExtInfo(extInfo);
        com.jiuqi.va.domain.common.R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
        String billDefine = r.get((Object)"value").toString();
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        context.setTenantName(ShiroUtil.getTenantName());
        BillModelImpl model = (BillModelImpl)this.modelDefineService.createModel((ModelContext)context, billDefine);
        model.loadByCode(bizCode);
        HashMap<String, Object> param = new HashMap<String, Object>();
        WorkflowDTO workflowDTO = new WorkflowDTO();
        Object needWorkflow = workflowProcessForecastDTO.getExtInfo("needWorkflow");
        if (needWorkflow == null || ((Boolean)needWorkflow).booleanValue()) {
            int billState = model.getMaster().getInt("BILLSTATE");
            if (billState < BillState.COMMITTED.getValue() || billState == BillState.SENDBACK.getValue()) {
                workflowDTO.setUniqueCode(workflowDefineKey);
            }
            ProcessForecastAction processForecastAction = (ProcessForecastAction)((Object)ApplicationContextRegister.getBean(ProcessForecastAction.class));
            Map<String, Object> workflow = processForecastAction.findworkflow(model);
            if (StringUtils.hasText(workflowDefineKey) && workflowDefineVersion == null) {
                workflowDefineVersion = (Long)workflow.get("workflowdefineversion");
            }
            workflowDTO.setProcessDefineVersion(workflowDefineVersion);
            Map<String, Object> workflowVariables = this.billCoreWorkFlowService.getWorkFlowParamsValueMap(model, workflow);
            workflowDTO.setWorkflowVariables(workflowVariables);
        }
        workflowDTO.setBizType("BILL");
        workflowDTO.setBizDefine(billDefine);
        workflowDTO.setBizCode(bizCode);
        param.put("bizCode", bizCode);
        param.put("workflowDTO", workflowDTO);
        return (List)this.getAllNodes(model, param).getData();
    }
}

