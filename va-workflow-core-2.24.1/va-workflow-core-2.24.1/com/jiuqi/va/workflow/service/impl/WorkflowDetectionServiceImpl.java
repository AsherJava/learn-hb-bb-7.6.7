/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.biz.domain.workflow.detection.WorkflowDetectionFormula;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.dao.WorkflowBusinessDao;
import com.jiuqi.va.workflow.dao.WorkflowDetectionDao;
import com.jiuqi.va.workflow.dao.WorkflowDetectionDataDao;
import com.jiuqi.va.workflow.dao.WorkflowDetectionResultDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDraftDao;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDTO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDataDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionElement;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionResultDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionVO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.service.WorkflowBusinessService;
import com.jiuqi.va.workflow.service.WorkflowDetectionHelperService;
import com.jiuqi.va.workflow.service.WorkflowDetectionService;
import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import com.jiuqi.va.workflow.service.detection.DefaultDetection;
import com.jiuqi.va.workflow.service.detection.DetectionHandler;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkflowThreadUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowDetectionServiceImpl
implements WorkflowDetectionService,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowDetectionServiceImpl.class);
    @Autowired
    private WorkflowDetectionHelperService detectionHelperService;
    @Autowired
    private List<BaseDetectionHandler> detectionHandlerList;
    @Autowired
    private DefaultDetection defaultDetection;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private WorkflowMetaService workflowMetaService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowBusinessDao workflowBusinessDao;
    @Autowired
    private WorkflowDetectionDao workflowDetectionDao;
    @Autowired
    private WorkflowDetectionDataDao workflowDetectionDataDao;
    @Autowired
    private WorkflowDetectionResultDao workflowDetectionResultDao;
    @Autowired
    private WorkflowDetectionService workflowDetectionService;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private AuthUserClient authUserClient;
    private Map<String, BaseDetectionHandler> detectionHandlerMap;
    private List<String> workflowEleDetects;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private WorkflowBusinessService workflowBusinessService;
    @Autowired
    private WorkflowBusinessRelDraftDao workflowBusinessRelDraftDao;
    private static final int MAX_DETECT_NUM = 256;

    @Override
    public WorkflowDetectionVO detectionParamExtract(WorkflowDetectionDTO workflowDetectionDTO) {
        Map workflowInfo;
        Object workflowVariables;
        WorkflowDetectionVO workflowDetectionVO = new WorkflowDetectionVO();
        workflowDetectionVO.setParamInfoList(new ArrayList<Map<String, Object>>());
        String bizDefine = workflowDetectionDTO.getBizdefine();
        String workflowDefineKey = workflowDetectionDTO.getWorkflowdefinekey();
        if (!StringUtils.hasText(bizDefine) || !StringUtils.hasText(workflowDefineKey)) {
            logger.error("\u6d41\u7a0b\u68c0\u6d4b\u63d0\u53d6\u53c2\u6570\u5931\u8d25\uff0c\u4e1a\u52a1\u6807\u8bc6\u548c\u5de5\u4f5c\u6d41\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
            return workflowDetectionVO;
        }
        WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
        workflowBusinessDTO.setBusinesscode(bizDefine);
        workflowBusinessDTO.setWorkflowdefinekey(workflowDefineKey);
        List<WorkflowBusinessDO> runtimeDOList = this.workflowBusinessDao.selectLatest(workflowBusinessDTO);
        List<WorkflowBusinessDO> draftDOList = this.workflowBusinessRelDraftDao.selectLatestList(workflowBusinessDTO);
        WorkflowBusinessDO workflowBusinessDO = null;
        if (CollectionUtils.isEmpty(runtimeDOList)) {
            workflowBusinessDO = draftDOList.get(0);
        } else if (CollectionUtils.isEmpty(draftDOList)) {
            workflowBusinessDO = runtimeDOList.get(0);
        } else {
            Long runtimeDOVersion = runtimeDOList.get(0).getWorkflowdefineversion();
            Long draftDOVersion = draftDOList.get(0).getWorkflowdefineversion();
            WorkflowBusinessDO workflowBusinessDO2 = workflowBusinessDO = runtimeDOVersion > draftDOVersion ? runtimeDOList.get(0) : draftDOList.get(0);
        }
        if (workflowBusinessDO == null) {
            logger.error("\u6d41\u7a0b\u68c0\u6d4b\u63d0\u53d6\u53c2\u6570\u5931\u8d25\uff0c\u83b7\u53d6\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u53c2\u6570\u5931\u8d25");
            return workflowDetectionVO;
        }
        String bizType = workflowBusinessDO.getBusinesstype();
        String designData = workflowBusinessDO.getDesigndata();
        if (!StringUtils.hasText(designData)) {
            designData = workflowBusinessDO.getConfig();
        }
        if (StringUtils.hasText(designData) && (workflowVariables = (workflowInfo = JSONUtil.parseMap((String)designData)).get("workflowvariables")) instanceof List) {
            WorkflowDetectionFormula workflowDetectionFormula = new WorkflowDetectionFormula();
            workflowDetectionFormula.setBizDefine(bizDefine);
            workflowDetectionFormula.setWorkflowDefineKey(workflowDefineKey);
            workflowDetectionFormula.setWorkflowVariables((List)workflowVariables);
            workflowDetectionFormula.setBizType(bizType);
            if (workflowInfo.get("unitcode") != null) {
                workflowDetectionFormula.setUnitCode((String)workflowInfo.get("unitcode"));
            }
            TenantDO tenantDO = new TenantDO();
            tenantDO.setTraceId(Utils.getTraceId());
            tenantDO.setTenantName(ShiroUtil.getTenantName());
            tenantDO.addExtInfo("workflowDetectionFormula", (Object)workflowDetectionFormula);
            BussinessClient bussinessClient = this.getBussinessClient(bizType, bizDefine);
            Map workflowDetectionParams = bussinessClient.getWorkflowDetectionParams(tenantDO);
            if (workflowDetectionParams.get("orgFlag") != null) {
                workflowDetectionVO.setOrgFlag((Boolean)workflowDetectionParams.get("orgFlag"));
            }
            if (workflowDetectionParams.get("paramInfoList") != null) {
                workflowDetectionVO.setParamInfoList((List)workflowDetectionParams.get("paramInfoList"));
            }
            if (workflowDetectionParams.get("orgCodes") != null) {
                workflowDetectionVO.setOrgCodes((List)workflowDetectionParams.get("orgCodes"));
            }
        }
        return workflowDetectionVO;
    }

    private BussinessClient getBussinessClient(String bizType, String bizDefine) {
        BussinessClient bussinessClient;
        if ("BILL".equals(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType("BILL");
            R r = this.metaDataClient.getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
        }
        return bussinessClient;
    }

    @Override
    public WorkflowDetectionVO detectionExecute(WorkflowDetectionDTO detectionDTO) {
        WorkflowDetectionVO detectionVO = new WorkflowDetectionVO();
        List<Map<String, Object>> workflowVariables = this.handleWorkflowVariable(detectionDTO);
        if (workflowVariables.size() > 256) {
            throw new WorkflowException("\u8d85\u51fa\u6700\u5927\u68c0\u6d4b\u6570\u91cf\uff1a256");
        }
        if (workflowVariables.isEmpty()) {
            return detectionVO;
        }
        String workflowDefineKey = detectionDTO.getWorkflowdefinekey();
        List<WorkflowDetectionElement> workFlowElements = this.detectionHelperService.getWorkFlowElements(workflowDefineKey);
        TenantDO param = new TenantDO();
        param.addExtInfo("defineCode", (Object)workflowDefineKey);
        R r = this.metaDataClient.findMetaInfoByDefineCode(param);
        TenantDO tenantDO = new TenantDO();
        Object versionNO = r.get((Object)"versionNO");
        tenantDO.addExtInfo("metaVersion", versionNO);
        tenantDO.addExtInfo("workflowDefineKey", (Object)workflowDefineKey);
        Integer version = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
        HashMap<String, Object> workflowInfo = new HashMap<String, Object>(8);
        workflowInfo.put("workflowDefineKey", workflowDefineKey);
        workflowInfo.put("processDefineVersion", versionNO);
        workflowInfo.put("version", version);
        String bizdefine = detectionDTO.getBizdefine();
        workflowInfo.put("bizDefine", bizdefine);
        List<Map<String, Object>> result = this.detectExecute(workflowInfo, workflowVariables, workFlowElements, detectionDTO.getBizType());
        detectionVO.setDetectionResult(result);
        this.detectionHelperService.handleDetectResult(detectionVO);
        if (detectionDTO.isRetryFlag()) {
            this.handleRetrySave(detectionDTO, workflowDefineKey, bizdefine, result);
        } else {
            this.handleSaveDetectInfo(detectionDTO, detectionVO.getDetectionResult());
        }
        return detectionVO;
    }

    private void handleRetrySave(WorkflowDetectionDTO detectionDTO, String workflowDefineKey, String bizdefine, List<Map<String, Object>> result) {
        List<Map<String, Object>> inputParam = detectionDTO.getInputParam();
        Map<String, Object> inputMap = inputParam.get(0);
        List inputDatas = (List)inputMap.get("datas");
        if (CollectionUtils.isEmpty(inputDatas)) {
            return;
        }
        String rowId = (String)((Map)inputDatas.get(0)).get("rowId");
        if (!StringUtils.hasText(rowId)) {
            return;
        }
        Map unitcode = (Map)inputMap.get("unitcode");
        WorkflowDetectionDTO dto = new WorkflowDetectionDTO();
        dto.setSearchdata(true);
        dto.setBizdefine(bizdefine);
        dto.setWorkflowdefinekey(workflowDefineKey);
        List<WorkflowDetectionDTO> detectList = this.workflowDetectionDao.listDetect(dto);
        if (detectList.isEmpty()) {
            this.handleSaveDetectInfo(detectionDTO, result);
            return;
        }
        WorkflowDetectionDTO detectInfo = detectList.get(0);
        String id = detectInfo.getId();
        String detectiondata = detectInfo.getDetectiondata();
        String detectionresult = detectInfo.getDetectionresult();
        Map detectDataMap = JSONUtil.parseMap((String)detectiondata);
        List dBInputParam = (List)detectDataMap.get("inputParam");
        boolean orgFlag = detectionDTO.isOrgFlag();
        if (!orgFlag) {
            if (!CollectionUtils.isEmpty(dBInputParam)) {
                Map unitInputParam = (Map)dBInputParam.get(0);
                List datas = (List)unitInputParam.get("datas");
                List rows = datas.stream().filter(d -> !Objects.equals(d.get("rowId"), rowId)).collect(Collectors.toList());
                rows.add(inputDatas.get(0));
                unitInputParam.put("datas", rows);
            } else {
                detectDataMap.put("inputParam", inputParam);
            }
        } else {
            String code = (String)unitcode.get("code");
            if (!CollectionUtils.isEmpty(dBInputParam)) {
                for (Map map : dBInputParam) {
                    Map dBunitcode = (Map)map.get("unitcode");
                    if (!Objects.equals(dBunitcode.get("code"), code)) continue;
                    List datas = (List)map.get("datas");
                    datas.removeIf(d -> Objects.equals(d.get("rowId"), rowId));
                    datas.add(inputDatas.get(0));
                    break;
                }
            } else {
                detectDataMap.put("inputParam", inputParam);
            }
        }
        List detectResult = JSONUtil.parseMapArray((String)detectionresult);
        detectResult.removeIf(d -> Objects.equals(d.get("rowId"), rowId));
        detectResult.addAll(result);
        String detectDataMapStr = JSONUtil.toJSONString((Object)detectDataMap);
        String detectResultStr = JSONUtil.toJSONString((Object)detectResult);
        this.workflowDetectionService.saveRetryInfo(id, detectDataMapStr, detectResultStr);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveRetryInfo(String id, String detectDataMapStr, String detectResultStr) {
        WorkflowDetectionDataDO dataDO = new WorkflowDetectionDataDO();
        dataDO.setId(id);
        dataDO.setDetectiondata(detectDataMapStr);
        this.workflowDetectionDataDao.updateByPrimaryKey((Object)dataDO);
        WorkflowDetectionResultDO resultDO = new WorkflowDetectionResultDO();
        resultDO.setId(id);
        resultDO.setDetectionresult(detectResultStr);
        this.workflowDetectionResultDao.updateByPrimaryKey((Object)resultDO);
        WorkflowDetectionDO detectionDO = new WorkflowDetectionDO();
        detectionDO.setId(id);
        detectionDO.setOperatetime(new Date());
        detectionDO.setOperator(ShiroUtil.getUser().getId());
        this.workflowDetectionDao.updateByPrimaryKeySelective((Object)detectionDO);
    }

    private void handleSaveDetectInfo(WorkflowDetectionDTO detectionDTO, List<Map<String, Object>> result) {
        String bizdefine = detectionDTO.getBizdefine();
        String workflowdefinekey = detectionDTO.getWorkflowdefinekey();
        WorkflowDetectionDO record = new WorkflowDetectionDO();
        record.setBizdefine(bizdefine);
        record.setWorkflowdefinekey(workflowdefinekey);
        List detectionDOList = this.workflowDetectionDao.select((Object)record);
        this.workflowDetectionService.saveDetectInfo(detectionDOList, detectionDTO, result);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveDetectInfo(List<WorkflowDetectionDO> detectionDOList, WorkflowDetectionDTO detectionDTO, List<Map<String, Object>> result) {
        if (!detectionDOList.isEmpty()) {
            WorkflowDetectionDO detectionHisDO = detectionDOList.get(0);
            this.workflowDetectionDao.insertDetectHis(detectionHisDO);
            this.workflowDetectionDao.deleteById(detectionHisDO);
        }
        String userId = ShiroUtil.getUser().getId();
        String id = UUID.randomUUID().toString();
        WorkflowDetectionDO detectionDO = new WorkflowDetectionDO();
        detectionDO.setId(id);
        detectionDO.setWorkflowdefinekey(detectionDTO.getWorkflowdefinekey());
        detectionDO.setBizdefine(detectionDTO.getBizdefine());
        detectionDO.setOperator(userId);
        detectionDO.setOperatetime(new Date());
        this.workflowDetectionDao.insert((Object)detectionDO);
        WorkflowDetectionDataDO dataDO = new WorkflowDetectionDataDO();
        dataDO.setId(id);
        ArrayList inputParam = detectionDTO.getInputParam();
        boolean orgFlag = detectionDTO.isOrgFlag();
        HashMap<String, Serializable> detectData = new HashMap<String, Serializable>(4);
        detectData.put("orgFlag", Boolean.valueOf(orgFlag));
        detectData.put("inputParam", inputParam != null ? inputParam : new ArrayList());
        detectData.put("orgCodes", detectionDTO.getOrgCodes() != null ? detectionDTO.getOrgCodes() : new ArrayList());
        dataDO.setDetectiondata(JSONUtil.toJSONString(detectData, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS));
        this.workflowDetectionDataDao.insert((Object)dataDO);
        WorkflowDetectionResultDO resultDO = new WorkflowDetectionResultDO();
        resultDO.setId(id);
        resultDO.setDetectionresult(JSONUtil.toJSONString(result, (String)DATE_FORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS));
        this.workflowDetectionResultDao.insert((Object)resultDO);
    }

    private List<Map<String, Object>> detectExecute(Map<String, Object> workflowInfo, List<Map<String, Object>> wfVariables, List<WorkflowDetectionElement> workFlowElements, String bizType) {
        CopyOnWriteArrayList<Map<String, Object>> resultList = new CopyOnWriteArrayList<Map<String, Object>>();
        HashMap eleMap = new HashMap();
        workFlowElements.forEach(ele -> eleMap.put(ele.getElementId(), ele));
        WorkflowDetectionElement startNode = null;
        for (WorkflowDetectionElement element : workFlowElements) {
            if (!"StartNoneEvent".equals(element.getElementType())) continue;
            startNode = element;
            break;
        }
        if (startNode == null) {
            return resultList;
        }
        WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
        String bizDefine = (String)workflowInfo.get("bizDefine");
        workflowBusinessDTO.setBusinesscode(bizDefine);
        workflowBusinessDTO.setWorkflowdefineversion((Long)workflowInfo.get("processDefineVersion"));
        workflowBusinessDTO.setWorkflowdefinekey((String)workflowInfo.get("workflowDefineKey"));
        workflowBusinessDTO.setDesignFlag(true);
        WorkflowBusinessDTO businessDTO = this.workflowBusinessService.get(workflowBusinessDTO, false);
        Map workflow = (Map)businessDTO.getWorkflows().get(0);
        logger.info("\u6d41\u7a0b\u68c0\u6d4b>>>\u4e1a\u52a1\u7c7b\u578b\u4e3a\uff1a{}", (Object)bizType);
        if (!StringUtils.hasText(bizType)) {
            bizType = "BILL";
        }
        BussinessClient bussinessClient = this.getBussinessClient(bizType, bizDefine);
        WorkflowDetectionElement finalStartNode = startNode;
        int size = wfVariables.size();
        CountDownLatch latch = new CountDownLatch(size);
        ThreadPoolExecutor executor = VaWorkflowThreadUtils.getThreadPoolExecutor();
        UserLoginDTO loginUser = VaWorkflowUtils.getLoginUserWithToken();
        int i = 0;
        while (i < size) {
            int finalI = i++;
            HashMap resultMap = new HashMap();
            executor.execute(() -> {
                try {
                    if (ShiroUtil.getUser() == null) {
                        logger.info("\u6d41\u7a0b\u68c0\u6d4b>>>\u5f53\u524d\u7ebf\u7a0b\u83b7\u53d6\u767b\u5f55\u7528\u6237\u4e3a\u7a7a");
                    }
                    Map variableMap = (Map)wfVariables.get(finalI);
                    resultMap.putAll(variableMap);
                    Map variables = new HashMap();
                    try {
                        TenantDO tenantDO = new TenantDO();
                        ShiroUtil.unbindUser();
                        ShiroUtil.bindUser((UserLoginDTO)loginUser);
                        tenantDO.setTraceId(Utils.getTraceId());
                        tenantDO.addExtInfo("defineCode", (Object)bizDefine);
                        tenantDO.addExtInfo("workflow", (Object)workflow);
                        tenantDO.addExtInfo("bizValues", (Object)variableMap);
                        variables = bussinessClient.getWorkflowParamsValueMap(tenantDO);
                    }
                    catch (Exception e) {
                        logger.error("\u6d41\u7a0b\u68c0\u6d4b>>>\u83b7\u53d6\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u53c2\u6570\u5931\u8d25\uff1a{}", (Object)e.getMessage(), (Object)e);
                    }
                    VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
                    WorkflowDTO workflowDTO = this.packageWfContext(workflowInfo, vaWorkflowContext);
                    Map customParam = vaWorkflowContext.getCustomParam();
                    HashSet uniqueIdSet = new HashSet();
                    customParam.put("uniqueIdSet", uniqueIdSet);
                    VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                    ArrayList<WorkflowDetectResultDO> nodeResult = new ArrayList<WorkflowDetectResultDO>();
                    workflowDTO.setWorkflowVariables(variables);
                    LinkedList<WorkflowDetectionElement> queue = new LinkedList<WorkflowDetectionElement>();
                    queue.add(finalStartNode);
                    this.detectOne(eleMap, nodeResult, queue);
                    boolean resultFlag = nodeResult.stream().anyMatch(r -> Objects.equals(r.getStatus(), 1));
                    resultMap.put("info", nodeResult);
                    resultMap.put("result", resultFlag ? "failure" : "success");
                }
                catch (Exception e) {
                    resultMap.put("result", "failure");
                    resultMap.put("info", "\u68c0\u6d4b\u5931\u8d25\uff1a" + e.getMessage());
                    logger.error(e.getMessage(), e);
                }
                finally {
                    ShiroUtil.unbindUser();
                    resultList.add(resultMap);
                    latch.countDown();
                    VaContext.removeVaWorkflowContext();
                }
            });
        }
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return resultList;
    }

    private WorkflowDTO packageWfContext(Map<String, Object> workflowInfo, VaWorkflowContext vaWorkflowContext) {
        HashMap<String, WorkflowModel> customParam = new HashMap<String, WorkflowModel>();
        WorkflowDTO workflowDTO = new WorkflowDTO();
        String workflowDefineKey = (String)workflowInfo.get("workflowDefineKey");
        Long versionNO = (Long)workflowInfo.get("processDefineVersion");
        workflowDTO.setUniqueCode(workflowDefineKey);
        workflowDTO.setProcessDefineVersion(versionNO);
        workflowDTO.setBizDefine((String)workflowInfo.get("bizDefine"));
        workflowDTO.addExtInfo("version", workflowInfo.get("version"));
        vaWorkflowContext.setWorkflowDTO(workflowDTO);
        WorkflowModel workflowModel = this.workflowParamService.getModel(workflowDefineKey, null);
        customParam.put("workflowModel", workflowModel);
        ProcessDO processDO = new ProcessDO();
        processDO.setDefinekey(workflowDefineKey);
        processDO.setDefineversion(BigDecimal.valueOf(versionNO));
        vaWorkflowContext.setProcessDO(processDO);
        vaWorkflowContext.setCustomParam(customParam);
        return workflowDTO;
    }

    @Override
    public void detectOne(Map<String, WorkflowDetectionElement> eleMap, List<WorkflowDetectResultDO> nodeResult, List<WorkflowDetectionElement> queue) {
        while (!queue.isEmpty()) {
            String elementType;
            WorkflowDetectionElement element = this.getNextElement(queue);
            if (element == null || "SequenceFlow".equals(elementType = element.getElementType())) continue;
            if (this.workflowEleDetects.contains(elementType)) {
                BaseDetectionHandler detectionHandler = this.detectionHandlerMap.get(elementType);
                if (detectionHandler == null) continue;
                detectionHandler.execute(element, queue, nodeResult, eleMap);
                continue;
            }
            this.defaultDetection.execute(element, queue, nodeResult, eleMap);
        }
    }

    private WorkflowDetectionElement getNextElement(List<WorkflowDetectionElement> queue) {
        if (CollectionUtils.isEmpty(queue)) {
            return null;
        }
        Iterator<WorkflowDetectionElement> iterator = queue.iterator();
        WorkflowDetectionElement next = iterator.next();
        String elementType = next.getElementType();
        if ("JoinParallelGateway".equals(elementType)) {
            VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
            Map customParam = vaWorkflowContext.getCustomParam();
            Map pgwMap = (Map)customParam.get("pgwMap");
            Integer count = (Integer)pgwMap.get(next.getPgwCode());
            if (count > 0 && iterator.hasNext()) {
                next = iterator.next();
            }
        } else if ("EndNoneEvent".equals(elementType) && iterator.hasNext()) {
            next = iterator.next();
        }
        queue.remove((Object)next);
        return next;
    }

    private List<Map<String, Object>> handleWorkflowVariable(WorkflowDetectionDTO detectionDTO) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> inputParam = detectionDTO.getInputParam();
        boolean orgFlag = detectionDTO.isOrgFlag();
        if (CollectionUtils.isEmpty(inputParam)) {
            return result;
        }
        List params = null;
        for (Map<String, Object> paramMap : inputParam) {
            HashMap unitcodeMap = new HashMap();
            if (orgFlag) {
                Map unitcode = (Map)paramMap.get("unitcode");
                ArrayList<Map> unitcodes = new ArrayList<Map>();
                unitcodes.add(unitcode);
                unitcodeMap.put("UNITCODE", unitcodes);
            }
            if (params == null && CollectionUtils.isEmpty(params = (List)paramMap.get("param"))) break;
            List datas = (List)paramMap.get("datas");
            if (CollectionUtils.isEmpty(datas)) continue;
            for (Map data : datas) {
                ArrayList<Map> paramList = new ArrayList<Map>();
                HashMap<String, Object> subWorkflowVariableMap = new HashMap<String, Object>();
                HashMap<String, List<Object>> workflowVariableMap = new HashMap<String, List<Object>>(unitcodeMap);
                String rowId = (String)data.get("rowId");
                for (Map param : params) {
                    Map paramCopy = JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)param));
                    String paramName = (String)param.get("paramName");
                    Object paramValueObj = data.get(paramName);
                    paramCopy.put("paramValue", paramValueObj);
                    paramList.add(paramCopy);
                }
                for (Map param : paramList) {
                    String paramName = (String)param.get("paramName");
                    Object paramValueObj = param.get("paramValue");
                    if (paramValueObj == null) continue;
                    Object paramTypeObj = param.get("paramType");
                    String mapping = (String)param.get("mapping");
                    Boolean subParamFlag = (Boolean)param.get("subParamFlag");
                    if (subParamFlag != null && subParamFlag.booleanValue()) {
                        this.handleSubParam(subWorkflowVariableMap, param);
                        continue;
                    }
                    List<Object> paramValueList = this.detectionHelperService.getParamValueList(paramName, paramValueObj, paramTypeObj, mapping);
                    if (paramValueList.isEmpty()) continue;
                    workflowVariableMap.put(paramName, paramValueList);
                }
                List<Map<String, Object>> cartesianProduct = this.detectionHelperService.getCartesianProduct(workflowVariableMap);
                if (cartesianProduct.isEmpty()) {
                    if (!subWorkflowVariableMap.isEmpty()) {
                        subWorkflowVariableMap.put("rowId", rowId);
                        result.add(subWorkflowVariableMap);
                        continue;
                    }
                    HashMap<String, String> defaultMap = new HashMap<String, String>(2);
                    defaultMap.put("rowId", rowId);
                    result.add(defaultMap);
                    continue;
                }
                if (!subWorkflowVariableMap.isEmpty()) {
                    for (Map<String, Object> variable : cartesianProduct) {
                        variable.put("rowId", rowId);
                        variable.putAll(subWorkflowVariableMap);
                    }
                } else {
                    for (Map<String, Object> variable : cartesianProduct) {
                        variable.put("rowId", rowId);
                    }
                }
                result.addAll(cartesianProduct);
            }
        }
        return result;
    }

    private void handleSubParam(Map<String, Object> subWorkflowVariableMap, Map<String, Object> param) {
        String paramName = (String)param.get("paramName");
        Object paramValueObj = param.get("paramValue");
        Object paramTypeObj = param.get("paramType");
        String mapping = (String)param.get("mapping");
        Object rowId = param.get("rowId");
        String subTableName = (String)param.get("subTableName");
        if (paramValueObj instanceof List) {
            boolean multiFlag = mapping != null && mapping.endsWith("[MULTI]");
            List paramValues = (List)paramValueObj;
            if (CollectionUtils.isEmpty(paramValues)) {
                return;
            }
            if (multiFlag) {
                ArrayList subParams = new ArrayList();
                for (Map subParam : paramValues) {
                    HashMap<String, Object> subParamMap = new HashMap<String, Object>();
                    subParamMap.put("title", subParam.get("showTitle"));
                    subParamMap.put("mapping", mapping);
                    subParamMap.put("paramType", paramTypeObj);
                    subParamMap.put("rowId", rowId);
                    subParamMap.put("subParamFlag", true);
                    subParamMap.put("subTableName", subTableName);
                    if (mapping.startsWith("EM_") || mapping.startsWith("MD_ORG")) {
                        subParamMap.put("code", subParam.get("code"));
                    } else {
                        subParamMap.put("code", subParam.get("objectcode"));
                    }
                    subParams.add(subParamMap);
                }
                subWorkflowVariableMap.put(paramName, subParams);
            } else {
                Map subParam = (Map)paramValues.get(0);
                HashMap<String, Object> subParamMap = new HashMap<String, Object>();
                subParamMap.put("title", subParam.get("showTitle"));
                subParamMap.put("mapping", mapping);
                subParamMap.put("paramType", paramTypeObj);
                subParamMap.put("rowId", rowId);
                subParamMap.put("subParamFlag", true);
                subParamMap.put("subTableName", subTableName);
                if (mapping != null && (mapping.startsWith("EM_") || mapping.startsWith("MD_ORG"))) {
                    subParamMap.put("code", subParam.get("code"));
                } else {
                    subParamMap.put("code", subParam.get("objectcode"));
                }
                subWorkflowVariableMap.put(paramName, subParamMap);
            }
        } else if (paramValueObj instanceof String) {
            String value = (String)paramValueObj;
            if (!StringUtils.hasText(value)) {
                return;
            }
            HashMap<String, Object> subParamMap = new HashMap<String, Object>(8);
            subParamMap.put("code", value);
            subParamMap.put("title", value);
            subParamMap.put("paramType", paramTypeObj);
            subParamMap.put("rowId", rowId);
            subParamMap.put("subParamFlag", true);
            subParamMap.put("subTableName", subTableName);
            subWorkflowVariableMap.put(paramName, subParamMap);
        }
    }

    @Override
    public WorkflowDetectionVO getDetectionData(WorkflowDetectionDTO workflowDetectionDTO) {
        WorkflowDetectionVO detectionVO = new WorkflowDetectionVO();
        List<WorkflowDetectionDTO> detectionDTOList = this.workflowDetectionDao.listDetect(workflowDetectionDTO);
        if (detectionDTOList.isEmpty()) {
            return detectionVO;
        }
        WorkflowDetectionDTO detectionDTO = detectionDTOList.get(0);
        String detectionresult = detectionDTO.getDetectionresult();
        String detectiondata = detectionDTO.getDetectiondata();
        detectionVO.setDetectionResult(JSONUtil.parseMapArray((String)detectionresult));
        detectionVO.setDetectionData(JSONUtil.parseMap((String)detectiondata));
        return detectionVO;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(this.detectionHandlerList)) {
            this.detectionHandlerMap = this.detectionHandlerList.stream().collect(Collectors.toMap(DetectionHandler::getName, obj -> obj));
            this.workflowEleDetects = this.detectionHandlerList.stream().map(DetectionHandler::getName).collect(Collectors.toList());
        }
    }
}

