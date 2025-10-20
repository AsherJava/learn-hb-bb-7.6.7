/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.DistributeItem
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.DistributeItem;
import com.jiuqi.va.domain.workflow.business.TargetObject;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.workflow.constants.WorkflowBusinessRelationConst;
import com.jiuqi.va.workflow.dao.WorkflowBusinessDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDraftDao;
import com.jiuqi.va.workflow.domain.BusinessWorkflowNodesInfoSaveProcess;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowCacheUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class DistributeUtil {
    private DistributeUtil() {
    }

    public static WorkflowBusinessRelDraftDao getWorkflowBusinessRelDraftDao() {
        return (WorkflowBusinessRelDraftDao)ApplicationContextRegister.getBean(WorkflowBusinessRelDraftDao.class);
    }

    public static WorkflowBusinessDao getWorkflowBusinessDao() {
        return (WorkflowBusinessDao)ApplicationContextRegister.getBean(WorkflowBusinessDao.class);
    }

    public static MetaDataClient getMetaDataClient() {
        return (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
    }

    public static List<DistributeItem> getFilteredList(List<DistributeItem> itemList, String key) {
        HashMap<String, DistributeItem> itemNameMap = new HashMap<String, DistributeItem>(itemList.size());
        for (DistributeItem distributeItem : itemList) {
            String uniqueKey = null;
            if ("action".equals(key)) {
                uniqueKey = distributeItem.getName();
            } else if ("editablefield".equals(key)) {
                uniqueKey = distributeItem.getId();
            } else if ("editabletable".equals(key)) {
                uniqueKey = distributeItem.getTableName();
            }
            String type = distributeItem.getType();
            if (itemNameMap.containsKey(uniqueKey) && !type.equals(((DistributeItem)itemNameMap.get(uniqueKey)).getType())) {
                itemNameMap.remove(uniqueKey);
                continue;
            }
            itemNameMap.put(uniqueKey, distributeItem);
        }
        return new ArrayList<DistributeItem>(itemNameMap.values());
    }

    public static List<DistributeItem> getAddItemList(List<DistributeItem> itemList) {
        return itemList.stream().filter(item -> "add".equals(item.getType())).collect(Collectors.toList());
    }

    public static List<DistributeItem> getDeleteItemList(List<DistributeItem> itemList) {
        return itemList.stream().filter(item -> "delete".equals(item.getType())).collect(Collectors.toList());
    }

    public static List<WorkflowBusinessDO> getTargetBindingRelation(List<TargetObject> targetObjectList) {
        List<Map<String, String>> bizWorkflowMapList = DistributeUtil.getBizWorkflowMapList(targetObjectList);
        WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
        workflowBusinessDTO.setBusinessesWorkflows(bizWorkflowMapList);
        List<WorkflowBusinessDO> runtimeList = DistributeUtil.getWorkflowBusinessDao().selectWBListLatest(workflowBusinessDTO);
        List<WorkflowBusinessDO> designList = DistributeUtil.getWorkflowBusinessRelDraftDao().selectWBListLatest(workflowBusinessDTO);
        DistributeUtil.overrideByDesignTimeData(runtimeList, designList);
        return runtimeList;
    }

    private static List<Map<String, String>> getBizWorkflowMapList(List<TargetObject> targetObjectList) {
        ArrayList<Map<String, String>> bizWorkflowMapList = new ArrayList<Map<String, String>>();
        HashSet<String> uniqueKeySet = new HashSet<String>(2);
        for (TargetObject target : targetObjectList) {
            String bizName = target.getBizName();
            String workflowName = target.getWorkflowName();
            String uniqueKey = bizName + "_" + workflowName;
            if (uniqueKeySet.contains(uniqueKey)) continue;
            HashMap<String, String> bizWorkflowMap = new HashMap<String, String>(2);
            bizWorkflowMap.put("bizName", bizName);
            bizWorkflowMap.put("workflowName", workflowName);
            bizWorkflowMapList.add(bizWorkflowMap);
            uniqueKeySet.add(uniqueKey);
        }
        return bizWorkflowMapList;
    }

    private static void overrideByDesignTimeData(List<WorkflowBusinessDO> runtimeDOList, List<WorkflowBusinessDO> draftDOList) {
        String key;
        HashMap<String, WorkflowBusinessDO> draftDOMap = new HashMap<String, WorkflowBusinessDO>();
        for (WorkflowBusinessDO draftDO : draftDOList) {
            key = draftDO.getBusinesscode() + "_" + draftDO.getWorkflowdefinekey();
            draftDOMap.put(key, draftDO);
        }
        for (WorkflowBusinessDO runtimeDO : runtimeDOList) {
            key = runtimeDO.getBusinesscode() + "_" + runtimeDO.getWorkflowdefinekey();
            WorkflowBusinessDO draftDO = (WorkflowBusinessDO)draftDOMap.get(key);
            if (draftDO != null && Objects.equals(draftDO.getWorkflowdefineversion(), runtimeDO.getWorkflowdefineversion()) && draftDO.getRelversion() >= runtimeDO.getRelversion()) {
                runtimeDO.setId(draftDO.getId());
                runtimeDO.setLockflag(draftDO.getLockflag());
                runtimeDO.setLockuser(draftDO.getLockuser());
                runtimeDO.setDesignstate(draftDO.getDesignstate());
                runtimeDO.setDesigndata(draftDO.getDesigndata());
                runtimeDO.setModifytime(draftDO.getModifytime());
            }
            draftDOMap.remove(key);
        }
        runtimeDOList.addAll(draftDOMap.values());
    }

    public static Map<String, List<TargetObject>> getNodeGroupMap(List<TargetObject> targetObjectList) {
        HashMap<String, List<TargetObject>> nodeGroupMap = new HashMap<String, List<TargetObject>>();
        for (TargetObject target : targetObjectList) {
            String bizName = target.getBizName();
            String workflowName = target.getWorkflowName();
            String uniqueKey = bizName + "_" + workflowName;
            if (nodeGroupMap.containsKey(uniqueKey)) {
                ((List)nodeGroupMap.get(uniqueKey)).add(target);
                continue;
            }
            ArrayList<TargetObject> list = new ArrayList<TargetObject>();
            list.add(target);
            nodeGroupMap.put(uniqueKey, list);
        }
        return nodeGroupMap;
    }

    public static Map<String, TargetObject> getTargetObjectMap(List<TargetObject> targetObjectList) {
        HashMap<String, TargetObject> targetObjectMap = new HashMap<String, TargetObject>(targetObjectList.size());
        for (TargetObject target : targetObjectList) {
            String bizName = target.getBizName();
            String workflowName = target.getWorkflowName();
            String uniqueKey = bizName + "_" + workflowName;
            targetObjectMap.put(uniqueKey, target);
        }
        return targetObjectMap;
    }

    public static BusinessWorkflowNodesInfoSaveProcess setProcessInfo(WorkflowBusinessDistributeDTO distributionDTO) {
        BusinessWorkflowNodesInfoSaveProcess processInfo = new BusinessWorkflowNodesInfoSaveProcess();
        String resultKey = distributionDTO.getResultKey();
        processInfo.setRsKey(resultKey);
        processInfo.setTotal(distributionDTO.getTargetObjectList().size() + 30);
        processInfo.setCurrIndex(30);
        VaWorkflowCacheUtils.setImportDataResult(resultKey, JSONUtil.toJSONString((Object)processInfo));
        return processInfo;
    }

    public static boolean shouldSkip(WorkflowBusinessDO workflowBusinessDO, String currentUserId, List<TargetObject> nodeGroup, BusinessWorkflowNodesInfoSaveProcess processInfo) {
        if (workflowBusinessDO.getLockflag() == 1 && !currentUserId.equals(workflowBusinessDO.getLockuser())) {
            nodeGroup.forEach(res -> res.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.failed") + VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked")));
            processInfo.setCurrIndex(processInfo.getCurrIndex() + 1);
            VaWorkflowCacheUtils.setImportDataResult(processInfo.getRsKey(), JSONUtil.toJSONString((Object)processInfo));
            return true;
        }
        return false;
    }

    public static boolean shouldSkip(WorkflowBusinessDO workflowBusinessDO, String currentUserId, TargetObject targetObject, BusinessWorkflowNodesInfoSaveProcess processInfo, WorkflowBusinessDistributeDTO processedParam) {
        if (workflowBusinessDO.getLockflag() == 1 && !currentUserId.equals(workflowBusinessDO.getLockuser())) {
            targetObject.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.failed") + VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
            processInfo.setCurrIndex(processInfo.getCurrIndex() + 1);
            VaWorkflowCacheUtils.setImportDataResult(processInfo.getRsKey(), JSONUtil.toJSONString((Object)processInfo));
            return true;
        }
        if (StringUtils.hasText(processedParam.getFailedMessage())) {
            targetObject.setSaveResult(processedParam.getFailedMessage());
            targetObject.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.FAILED.getValue());
            processInfo.setCurrIndex(processInfo.getCurrIndex() + 1);
            VaWorkflowCacheUtils.setImportDataResult(processInfo.getRsKey(), JSONUtil.toJSONString((Object)processInfo));
            return true;
        }
        return false;
    }

    public static Map<String, Object> getDesignDataMap(WorkflowBusinessDO workflowBusinessDO) {
        HashMap designDataMap;
        String designData = workflowBusinessDO.getDesigndata();
        if (!StringUtils.hasText(designData)) {
            designData = workflowBusinessDO.getConfig();
        }
        if (ObjectUtils.isEmpty(designDataMap = JSONUtil.parseMap((String)designData))) {
            designDataMap = new HashMap();
        }
        return designDataMap;
    }

    public static Map<String, Object> getProcessedParams(WorkflowBusinessDistributeDTO distributeDTO) {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setModuleName(distributeDTO.getSourceBizDefine().split("_")[0]);
        moduleDTO.setFunctionType("BILL");
        R r = DistributeUtil.getMetaDataClient().getModuleByName(moduleDTO);
        String server = String.valueOf(r.get((Object)"server"));
        String path = String.valueOf(r.get((Object)"path"));
        BussinessClient bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        R handleResult = bussinessClient.handleDistributeParam(distributeDTO);
        if (handleResult.getCode() == 0) {
            return (Map)handleResult.get((Object)"data");
        }
        throw new WorkflowException(handleResult.getMsg());
    }
}

