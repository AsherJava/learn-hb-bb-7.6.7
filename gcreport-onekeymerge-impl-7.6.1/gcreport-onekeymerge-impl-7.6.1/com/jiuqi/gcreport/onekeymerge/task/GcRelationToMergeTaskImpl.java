/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcRelationToMergeVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyProcessService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcRelationToMergeVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcRelationToMergeTaskImpl
implements GcCenterTask {
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    private GcOnekeyProcessService onekeyProcessService;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO) {
        return this.doRelationToMerge(paramsVO);
    }

    private ReturnObject doRelationToMerge(GcActionParamsVO paramsVO) {
        List<GcOrgCacheVO> orgs = OrgUtils.getAllHbUnitParent(paramsVO);
        Map<String, GcOrgCacheVO> voMap = OrgUtils.getUnUploadUnit(paramsVO).stream().collect(Collectors.toMap(GcOrgCacheVO::getId, o -> o));
        TaskLog taskLog = new TaskLog(paramsVO.getOnekeyProgressData());
        TaskStateEnum state = TaskStateEnum.EXECUTING;
        GcTaskResultEO eo = new GcTaskResultEO();
        eo.setUserName(OneKeyMergeUtils.getUser().getName());
        eo.setTaskTime(DateUtils.now());
        eo.setTaskCode(TaskTypeEnum.RELATIONTOMERGE.getCode());
        ArrayList<GcBaseTaskStateVO> retListVO = new ArrayList<GcBaseTaskStateVO>();
        for (GcOrgCacheVO org : orgs) {
            if (this.onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId().toString())) {
                taskLog.writeErrorLog("\u624b\u52a8\u505c\u6b62,\u670d\u52a1\u4e2d\u65ad", null);
                throw new RuntimeException("\u670d\u52a1\u7ec8\u6b62");
            }
            GcRelationToMergeVO vo = new GcRelationToMergeVO();
            vo.setOrgName(org.getTitle());
            boolean b = voMap.containsKey(org.getId());
            if (b) {
                taskLog.writeInfoLog(org.getTitle() + "\u5df2\u4e0a\u62a5\u6216\u9001\u5ba1", null);
                vo.setResult(org.getTitle() + "\u5df2\u4e0a\u62a5\u6216\u9001\u5ba1");
                continue;
            }
            long start = System.currentTimeMillis();
            taskLog.writeInfoLog(org.getTitle(), null);
            vo.setResult(org.getTitle());
            vo.setUseTime(String.valueOf(System.currentTimeMillis() - start));
        }
        taskLog.endTask();
        taskLog.setState(TaskStateEnum.SUCCESS);
        this.onekeyMergeService.saveTaskResult(paramsVO, eo, retListVO, TaskStateEnum.SUCCESS.getCode());
        return ReturnObject.ofSuccess(retListVO);
    }
}

