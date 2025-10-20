/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.domain.workflow.WorkflowQueryDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.domain.workflow.WorkflowQueryDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.service.WorkflowProcessInstanceService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
@RequestMapping(value={"/workflow/process"})
public class WorkflowProcessInstanceController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowProcessInstanceController.class);
    @Autowired
    private WorkflowProcessInstanceService workflowProcessInstanceService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowMetaService workflowMetaService;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private BizTypeConfig bizTypeConfig;

    @PostMapping(value={"/query"})
    public R listProcess(@RequestBody WorkflowQueryDTO workflowDTO) {
        try {
            ProcessDTO processDTO = this.getProcessDTO(workflowDTO);
            List<Map<String, Object>> processDOList = this.vaWorkflowProcessService.selectProcess(processDTO);
            processDOList = this.filterByAuth(processDOList);
            this.processResult(processDOList);
            processDTO.setPagination(false);
            int count = this.vaWorkflowProcessService.count(processDTO);
            PageVO pageVO = new PageVO(processDOList, count);
            R r = R.ok();
            r.put("processInstances", (Object)pageVO);
            return r;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getprocessfailed"));
        }
    }

    private ProcessDTO getProcessDTO(WorkflowQueryDTO workflowDTO) throws ParseException {
        String dateEndStr;
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setDefinekey(workflowDTO.getUniqueCode());
        if (workflowDTO.getProcessDefineVersion() != null) {
            processDTO.setDefineversion(new BigDecimal(workflowDTO.getProcessDefineVersion()));
        }
        processDTO.setBiztype(workflowDTO.getBizType());
        processDTO.setBizcode(workflowDTO.getBizCode());
        processDTO.setStartuser(workflowDTO.getProcessInstanceStartUser());
        if (workflowDTO.getProcessInstanceStatus() != null) {
            processDTO.setStatus(new BigDecimal(workflowDTO.getProcessInstanceStatus()));
        }
        processDTO.setPagination(workflowDTO.isPagination());
        processDTO.setLimit(workflowDTO.getLimit());
        processDTO.setOffset(workflowDTO.getOffset());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = (String)workflowDTO.getWorkflowVariables().get("submitTime");
        if (StringUtils.hasText(dateStr)) {
            processDTO.setStarttime(format.parse(dateStr));
        }
        if (StringUtils.hasText(dateEndStr = (String)workflowDTO.getWorkflowVariables().get("submitEndTime"))) {
            processDTO.setEndtime(format.parse(dateEndStr));
        }
        processDTO.setCompleteUser(workflowDTO.getCompleteUser());
        processDTO.setNodeName(workflowDTO.getNodeName());
        return processDTO;
    }

    private void processResult(List<Map<String, Object>> processDOList) {
        if (CollectionUtils.isEmpty(processDOList)) {
            return;
        }
        HashMap versionMap = new HashMap();
        R rw = this.workflowMetaService.getWorkflowMetaRelationAll(new TenantDO());
        if (rw.getCode() == 1) {
            throw new RuntimeException(rw.getMsg());
        }
        List relations = (List)rw.get((Object)"relations");
        if (!CollectionUtils.isEmpty(relations)) {
            relations.forEach(map -> versionMap.put(String.valueOf(map.get("WORKFLOWDEFINEKEY")) + map.get("METAVERSION"), map.get("WORKFLOWDEFINEVERSION")));
        }
        HashMap userMap = new HashMap();
        HashMap billDefineMap = new HashMap();
        TenantDO param = new TenantDO();
        param.addExtInfo("metaType", (Object)"bill");
        PageVO metas = this.metaDataClient.getAllMetaInfoByMetaType(param);
        if (!CollectionUtils.isEmpty(metas.getRows())) {
            metas.getRows().forEach(item -> billDefineMap.put(item.getUniqueCode(), item.getTitle()));
        }
        HashMap billDefineWorkFlowMap = new HashMap();
        param.addExtInfo("metaType", (Object)"workflow");
        PageVO metasWorkFlow = this.metaDataClient.getAllMetaInfoByMetaType(param);
        if (!CollectionUtils.isEmpty(metasWorkFlow.getRows())) {
            metasWorkFlow.getRows().forEach(item -> billDefineWorkFlowMap.put(item.getUniqueCode(), item.getTitle()));
        }
        processDOList.forEach(map -> {
            map.put("VERSION", versionMap.get(String.valueOf(map.get("DEFINEKEY")) + map.get("DEFINEVERSION")));
            if (map.get("STARTUSER") != null) {
                String startUser = map.get("STARTUSER").toString();
                if (!userMap.containsKey(startUser)) {
                    this.getUser(userMap, startUser);
                }
                map.put("STARTUSERTITLE", userMap.get(startUser));
            }
            String bizModule = (String)map.get("BIZMODULE");
            String bizType = (String)map.get("BIZTYPE");
            String bizCode = (String)map.get("BIZCODE");
            if ("BILL".equals(bizModule)) {
                map.put("BIZTYPETITLE", billDefineMap.get(bizType));
                map.put("BIZCODETITLE", bizCode);
            } else {
                TenantDO tenantDO = new TenantDO();
                tenantDO.setTraceId(Utils.getTraceId());
                tenantDO.addExtInfo("bizCode", (Object)bizCode);
                tenantDO.addExtInfo("bizDefine", (Object)bizType);
                BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizModule);
                R r = bussinessClient.getBizTitle(tenantDO);
                if (r.getCode() == 0) {
                    map.put("BIZTYPETITLE", r.get((Object)"bizDefineTitle"));
                    map.put("BIZCODETITLE", r.get((Object)"bizcodeTitle"));
                }
            }
            if (map.get("DEFINEKEY") != null) {
                map.put("DEFINEKEYTITLE", billDefineWorkFlowMap.get(map.get("DEFINEKEY").toString()));
            }
        });
    }

    private List<Map<String, Object>> filterByAuth(List<Map<String, Object>> processDOList) {
        OptionItemVO optionItemVO;
        UserLoginDTO user = ShiroUtil.getUser();
        OptionItemDTO optionParam = new OptionItemDTO();
        optionParam.setName("META001");
        List optionItemList = this.metaDataClient.listOption(optionParam);
        if ("normal".equals(user.getMgrFlag()) && !CollectionUtils.isEmpty(optionItemList) && "1".equals((optionItemVO = (OptionItemVO)optionItemList.get(0)).getVal())) {
            TenantDO tenantParam = new TenantDO();
            HashMap<String, Object> exParam = new HashMap<String, Object>();
            exParam.put("TenantName", ShiroUtil.getTenantName());
            exParam.put("Groupflag", 0);
            exParam.put("MetaType", "workflow");
            tenantParam.setExtInfo(exParam);
            Set userAuth = this.metaDataClient.checkUserAuth(tenantParam);
            processDOList = processDOList.stream().filter(map -> map.get("DEFINEKEY") != null && userAuth.contains(map.get("DEFINEKEY").toString())).collect(Collectors.toList());
        }
        return processDOList;
    }

    private void getUser(Map<String, String> userMap, String startUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(startUser);
        PageVO pageVO = this.authUserClient.list(userDTO);
        if (pageVO != null && pageVO.getRows() != null && pageVO.getRows().size() > 0) {
            UserDO userDO = (UserDO)pageVO.getRows().get(0);
            userMap.put(userDO.getId(), userDO.getName());
        }
    }

    @PostMapping(value={"/suspend"})
    public R suspendProcess(@RequestBody TenantDO tenantDO) {
        try {
            return this.workflowProcessInstanceService.changeProcessStatus(tenantDO, WorkflowProcessStatus.PROCESS_UNFINSHED_PAUSE);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.stopprocessfailed"));
        }
    }

    @PostMapping(value={"/resume"})
    public R resumeProcess(@RequestBody TenantDO tenantDO) {
        try {
            return this.workflowProcessInstanceService.changeProcessStatus(tenantDO, WorkflowProcessStatus.PROCESS_UNFINSHED_NORMAL);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.recoveryprocessfailed"));
        }
    }

    @PostMapping(value={"/terminate"})
    public R terminateProcessInstances(@RequestBody TenantDO tenantDO) {
        try {
            return this.workflowProcessInstanceService.changeProcessStatus(tenantDO, WorkflowProcessStatus.PROCESS_FINSHED_RETRACT);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.terminateprocessfailed"));
        }
    }

    @PostMapping(value={"/refresh"})
    public R refreshParticipant(@RequestBody TenantDO tenantDO) {
        String traceId = GUID.newGUID();
        Utils.setTraceId((String)traceId);
        tenantDO.setTraceId(traceId);
        try {
            return this.workflowProcessInstanceService.refreshParticipant(tenantDO);
        }
        catch (Exception e) {
            log.error(VaWorkFlowI18nUtils.convertError(e.getMessage()), e);
            return R.error((String)VaWorkFlowI18nUtils.convertError(e.getMessage()));
        }
    }

    @PostMapping(value={"/bizType/query"})
    public R queryBizType(@RequestBody TenantDO tenantDO) {
        try {
            return this.workflowProcessInstanceService.getProcessBizType(tenantDO);
        }
        catch (Exception e) {
            log.error(VaWorkFlowI18nUtils.getInfo("va.workflow.getbusinesstypefailed"), e);
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.getbusinesstypefailed") + e.getMessage()));
        }
    }

    @PostMapping(value={"/get"})
    public R getProcess(@RequestBody ProcessDTO processDTO) {
        try {
            ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
            R r = R.ok();
            r.put("process", (Object)processDO);
            return r;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u5f53\u524d\u5de5\u4f5c\u6d41\u53d1\u751f\u5f02\u5e38", e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getworkflowerror"));
        }
    }

    @PostMapping(value={"/getBillProcess"})
    public R getBillWorkflowProcess(@RequestBody ProcessDTO processDTO) {
        try {
            ProcessDO processDO = this.vaWorkflowProcessService.getBillProcess(processDTO);
            R r = R.ok();
            r.put("process", (Object)processDO);
            return r;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u5355\u636e\u6267\u884c\u4e2d\u7684\u5de5\u4f5c\u6d41\u53d1\u751f\u5f02\u5e38", e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getworkflowerror"));
        }
    }
}

