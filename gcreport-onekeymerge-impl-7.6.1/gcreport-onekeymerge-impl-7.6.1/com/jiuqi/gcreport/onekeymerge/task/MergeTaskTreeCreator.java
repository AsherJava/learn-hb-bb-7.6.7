/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskDao;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskProcessDao;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskProcessEO;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskOrderProvider;
import com.jiuqi.gcreport.onekeymerge.task.factory.MergeTaskExecutorFactory;
import com.jiuqi.gcreport.onekeymerge.util.OrgKindEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nr.bpm.upload.UploadState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MergeTaskTreeCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeTaskTreeCreator.class);
    @Autowired
    private MergeTaskOrderProvider mergeTaskOrderProvider;
    @Autowired
    private MergeTaskDao mergeTaskDao;
    @Autowired
    private MergeTaskExecutorFactory mergeTaskExecutorFactory;
    @Autowired
    private MergeTaskProcessDao mergeTaskProcessDao;
    @Autowired
    private GcOnekeyMergeService gcOnekeyMergeService;

    @Transactional(rollbackFor={Exception.class})
    public String createTaskTree(List<GcOrgCacheVO> orgs, GcActionParamsVO param) {
        List taskTypes = param.getTaskCodes();
        String taskGroupId = param.getTaskLogId();
        ArrayList<MergeTaskEO> mergeTaskEOList = new ArrayList<MergeTaskEO>();
        List<GcOrgCacheVO> curOrgs = orgs;
        HashSet<String> allOrgIds = new HashSet<String>();
        HashMap<String, String> orgCode2TaskIdMap = new HashMap<String, String>();
        Map<String, List<String>> orgBblx2TasksMap = this.buildTasks(taskTypes);
        GcActionParamsVO gcActionParamsTemp = new GcActionParamsVO();
        BeanUtils.copyProperties(param, gcActionParamsTemp);
        Map<String, UploadState> uploadSates = this.getAllOrgUploadStates(gcActionParamsTemp, orgs, allOrgIds);
        int hierarchy = 0;
        while (!CollectionUtils.isEmpty(curOrgs)) {
            ArrayList<GcOrgCacheVO> nextOrgs = new ArrayList<GcOrgCacheVO>();
            for (GcOrgCacheVO org : curOrgs) {
                gcActionParamsTemp.setOrgId(org.getId());
                GcOrgCacheVO parentOrg = OrgUtils.getParentOrg(gcActionParamsTemp, org.getId());
                if ((MergeTypeEnum.CUR_LEVEL.equals((Object)param.getMergeType()) || MergeTypeEnum.CUSTOM_LEVEL.equals((Object)param.getMergeType())) && hierarchy > 1) continue;
                UploadState uploadState = uploadSates.get(org.getCode());
                if (UploadState.UPLOADED.equals((Object)uploadState) || UploadState.CONFIRMED.equals((Object)uploadState) || UploadState.SUBMITED.equals((Object)uploadState)) {
                    LOGGER.info("\u5355\u4f4d {}-{} \u5df2\u4e0a\u62a5\uff0c\u4e0d\u521b\u5efa\u4efb\u52a1\u3002", (Object)org.getCode(), (Object)org.getTitle());
                    continue;
                }
                if (GcOrgKindEnum.DIFFERENCE.equals((Object)org.getOrgKind())) {
                    LOGGER.info("\u5355\u4f4d {}-{} \u4e3a\u5dee\u989d\u5355\u4f4d\uff0c\u4e0d\u521b\u5efa\u4efb\u52a1\u3002", (Object)org.getCode(), (Object)org.getTitle());
                    continue;
                }
                List<String> orderTasks = CollectionUtils.isEmpty((Collection)org.getChildren()) ? orgBblx2TasksMap.get(OrgKindEnum.SINGLE.getCode()) : orgBblx2TasksMap.get(OrgKindEnum.MERGE.getCode());
                String lastTaskId = Objects.isNull(parentOrg) ? null : (String)orgCode2TaskIdMap.get(parentOrg.getId());
                for (int i = 0; i < orderTasks.size(); ++i) {
                    String taskType = orderTasks.get(i);
                    if (!(!MergeTypeEnum.CUR_LEVEL.equals((Object)param.getMergeType()) && !MergeTypeEnum.CUSTOM_LEVEL.equals((Object)param.getMergeType()) || hierarchy != 1 || !TaskTypeEnum.CALC.getCode().equals(taskType) && !TaskTypeEnum.FINISHCALC.getCode().equals(taskType))) continue;
                    MergeTaskExecutor mergeTaskExecutor = this.mergeTaskExecutorFactory.create(taskType);
                    MergeTaskEO task = mergeTaskExecutor.createTask(gcActionParamsTemp);
                    task.setOrdinal(new Double(OrderGenerator.newOrderID()));
                    task.setTaskCode(taskType);
                    task.setGroupId(taskGroupId);
                    task.setOrgId(org.getId());
                    task.setOrgTitle(org.getTitle());
                    if (!TaskStateEnum.SKIP.getCode().equals(task.getTaskState())) {
                        orgCode2TaskIdMap.put(org.getCode(), task.getId());
                        task.setAfterTaskId(lastTaskId);
                        lastTaskId = task.getId();
                    }
                    mergeTaskEOList.add(task);
                }
                List childrens = org.getChildren();
                if (CollectionUtils.isEmpty((Collection)childrens)) continue;
                nextOrgs.addAll(childrens);
            }
            ++hierarchy;
            curOrgs = nextOrgs;
        }
        this.mergeTaskDao.addBatch(mergeTaskEOList);
        MergeTaskProcessEO mergeTaskProcessEO = new MergeTaskProcessEO();
        mergeTaskProcessEO.setId(param.getTaskLogId());
        mergeTaskProcessEO.setProcess(0.1);
        mergeTaskProcessEO.setTotalTaskCount(Long.valueOf(mergeTaskEOList.size()));
        mergeTaskProcessEO.setFinishedTaskCount(0L);
        this.mergeTaskProcessDao.updateSelective((BaseEntity)mergeTaskProcessEO);
        return taskGroupId;
    }

    private Map<String, List<String>> buildTasks(List<String> taskTypes) {
        List<String> mergeTasks = this.mergeTaskOrderProvider.orderTasks(taskTypes, OrgKindEnum.MERGE.getCode());
        List<String> singleTasks = this.mergeTaskOrderProvider.orderTasks(taskTypes, OrgKindEnum.SINGLE.getCode());
        HashMap<String, List<String>> orgBblx2TasksMap = new HashMap<String, List<String>>();
        orgBblx2TasksMap.put(OrgKindEnum.MERGE.getCode(), mergeTasks);
        orgBblx2TasksMap.put(OrgKindEnum.SINGLE.getCode(), singleTasks);
        return orgBblx2TasksMap;
    }

    private Map<String, UploadState> getAllOrgUploadStates(GcActionParamsVO gcActionParamsTemp, List<GcOrgCacheVO> orgs, Set<String> allOrgIds) {
        for (GcOrgCacheVO org : orgs) {
            gcActionParamsTemp.setOrgId(org.getId());
            List<GcOrgCacheVO> gcOrgCacheVOS = OrgUtils.listAllOrgByParentIdContainsSelf(gcActionParamsTemp);
            allOrgIds.addAll(gcOrgCacheVOS.stream().map(GcOrgCacheVO::getId).collect(Collectors.toSet()));
        }
        return UploadStateTool.getInstance().getUploadSates((Object)gcActionParamsTemp, new ArrayList<String>(allOrgIds));
    }
}

