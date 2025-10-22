/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcReportPickVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.nr.bpm.upload.UploadState
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcReportPickVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.nr.bpm.upload.UploadState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDataPickTaskImpl
implements GcCenterTask {
    private static final Logger logger = LoggerFactory.getLogger(GcDataPickTaskImpl.class);
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO) {
        return this.doReportPick(paramsVO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ReturnObject doReportPick(GcActionParamsVO paramsVO) {
        GcTaskResultEO eo = new GcTaskResultEO();
        ArrayList<GcBaseTaskStateVO> ret = new ArrayList<GcBaseTaskStateVO>();
        TaskLog taskLog = new TaskLog(paramsVO.getOnekeyProgressData());
        TaskStateEnum state = TaskStateEnum.EXECUTING;
        try {
            NpContextUser user = OneKeyMergeUtils.getUser();
            eo.setUserName(user.getName());
            eo.setTaskTime(DateUtils.now());
            eo.setTaskCode(TaskTypeEnum.DATAPICK.getCode());
            ReturnObject returnObject = this.onekeyMergeService.checkUpLoadAndFinishCalState(paramsVO, paramsVO.getOrgId());
            if (returnObject.isSuccess()) {
                throw new BusinessRuntimeException(returnObject.getErrorMessage() + ",\u64cd\u4f5c\u7ec8\u6b62");
            }
            taskLog.writeInfoLog("\u5f00\u59cb\u4e2a\u522b\u62a5\u8868\u91c7\u96c6", Float.valueOf(1.0f));
            taskLog.writeInfoLog("\u5f00\u59cb\u65f6\u95f4" + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss") + "", Float.valueOf(1.0f));
            ArrayList<GcReportPickVO> reportPickResult = new ArrayList<GcReportPickVO>();
            taskLog.writeInfoLog("\u6570\u636e\u521d\u59cb\u5316", Float.valueOf(2.0f));
            String formDefineKeys = String.join((CharSequence)";", OneKeyMergeUtils.getFilterLockedAndHiddenForm(paramsVO.getSchemeId(), paramsVO.getOrgId(), paramsVO));
            List<GcOrgCacheVO> treeByParent = OrgUtils.getChildrenTreeByParentId(paramsVO);
            if (paramsVO.getMergeType().equals((Object)MergeTypeEnum.ALL_LEVEL)) {
                List<GcOrgCacheVO> leafs = OrgUtils.getAllLeafUnitByParent(paramsVO);
                taskLog.setTotalNum(Integer.valueOf(leafs.size()));
            } else {
                int directLeaf = OrgUtils.getDirectLeafUnitByParent(treeByParent).size();
                taskLog.setTotalNum(Integer.valueOf(directLeaf));
            }
            this.executeReportPick(treeByParent.get(0).getChildren(), reportPickResult, paramsVO, formDefineKeys, taskLog);
            Map<String, GcReportPickVO> datas = reportPickResult.stream().collect(Collectors.toMap(GcBaseTaskStateVO::getId, vo -> vo));
            ArrayList<GcReportPickVO> tree = new ArrayList<GcReportPickVO>();
            for (GcReportPickVO vo2 : reportPickResult) {
                GcReportPickVO parentObj = datas.get(vo2.getParentId());
                if (parentObj != null) {
                    parentObj.getChildren().add(datas.get(vo2.getId()));
                    parentObj.setHasChildren(Boolean.valueOf(true));
                    continue;
                }
                tree.add(datas.get(vo2.getId()));
            }
            ret.addAll(tree);
            taskLog.writeInfoLog("\u4e2a\u522b\u62a5\u8868\u91c7\u96c6\u5b8c\u6bd5", Float.valueOf(98.0f));
            taskLog.writeInfoLog("\u7ed3\u675f\u65f6\u95f4" + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss"), Float.valueOf(99.0f));
            taskLog.setFinish(true);
            state = TaskStateEnum.SUCCESS;
        }
        catch (Exception e) {
            taskLog.writeErrorLog("\u4e2a\u522b\u62a5\u8868\u91c7\u96c6\u51fa\u73b0\u9519\u8bef" + e.getMessage(), Float.valueOf(taskLog.getProcessPercent()));
            taskLog.setFinish(true);
            state = TaskStateEnum.ERROR;
            logger.error(e.getMessage(), e);
        }
        finally {
            taskLog.setState(state);
            this.onekeyMergeService.saveTaskResult(paramsVO, eo, ret, state.getCode());
        }
        taskLog.endTask();
        return new ReturnObject(state.equals((Object)TaskStateEnum.SUCCESS), ret);
    }

    private void executeReportPick(List<GcOrgCacheVO> orgTree, List<GcReportPickVO> retList, GcActionParamsVO paramsVO, String formDefineKeys, TaskLog taskLog) {
        for (GcOrgCacheVO org : orgTree) {
            if (this.onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId().toString())) {
                taskLog.writeErrorLog("\u624b\u52a8\u505c\u6b62,\u670d\u52a1\u4e2d\u65ad", null);
                throw new BusinessRuntimeException("\u670d\u52a1\u7ec8\u6b62");
            }
            if (org.getChildren().size() > 0) {
                if (!paramsVO.getMergeType().equals((Object)MergeTypeEnum.ALL_LEVEL)) continue;
                ReturnObject returnObject = this.onekeyMergeService.checkUpLoadAndFinishCalState(paramsVO, org.getId());
                if (!returnObject.isSuccess()) {
                    taskLog.writeInfoLog("\u5408\u5e76\u5355\u4f4d:" + org.getTitle() + "\u4e0b\u7ea7\u5f00\u59cb\u6267\u884c\u63d0\u53d6", null);
                    UploadState uploadSate = this.onekeyMergeService.getUploadSate(paramsVO, org.getId());
                    GcReportPickVO data = new GcReportPickVO();
                    data.setPickWay("");
                    data.setOrgName(org.getTitle());
                    data.setId(org.getId());
                    data.setParentId(org.getParentId());
                    retList.add(data);
                    if (!paramsVO.getMergeType().equals((Object)MergeTypeEnum.ALL_LEVEL)) continue;
                    this.executeReportPick(org.getChildren(), retList, paramsVO, formDefineKeys, taskLog);
                    continue;
                }
                taskLog.writeInfoLog("\u5408\u5e76\u5355\u4f4d:" + org.getTitle() + returnObject.getErrorMessage(), null);
                ArrayList<GcOrgCacheVO> list = new ArrayList<GcOrgCacheVO>();
                list.add(org);
                int allLeaf = OrgUtils.getAllLeafUnitByParent(list).size();
                taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + allLeaf));
                continue;
            }
            taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + 1));
            taskLog.writeInfoLog("\u5f00\u59cb\u6267\u884c: " + org.getTitle(), Float.valueOf(taskLog.getProcessPercent()));
            if (org.getOrgKind().equals((Object)GcOrgKindEnum.DIFFERENCE)) continue;
            UploadState uploadSate = this.onekeyMergeService.getUploadSate(paramsVO, org.getId());
            GcReportPickVO data = this.buildStatesData(org, uploadSate);
            data.setPickWay("\u4e00\u952e\u5408\u5e76\u81ea\u52a8\u63d0\u53d6");
            ReturnObject returnObject = this.onekeyMergeService.checkUploadState(paramsVO, org.getId());
            if (returnObject.isSuccess()) {
                taskLog.writeInfoLog("\u975e\u5408\u5e76\u5355\u4f4d:" + org.getTitle() + returnObject.getErrorMessage(), null);
                retList.add(this.buildPickVO(data, org, "\u8df3\u8fc7", null));
                continue;
            }
            data.setPickWay("\u624b\u5de5\u63d0\u53d6");
            taskLog.writeInfoLog("\u975e\u5408\u5e76\u5355\u4f4d:" + org.getTitle(), null);
        }
    }

    private GcReportPickVO buildStatesData(GcOrgCacheVO org, UploadState uploadState) {
        GcReportPickVO vo = new GcReportPickVO();
        if (uploadState.equals((Object)UploadState.UPLOADED)) {
            vo.setDataReport("\u5df2\u4e0a\u62a5");
        } else if (uploadState.equals((Object)UploadState.CONFIRMED)) {
            vo.setDataReport("\u5df2\u786e\u8ba4");
        } else if (uploadState.equals((Object)UploadState.REJECTED)) {
            vo.setDataReport("\u5df2\u9000\u56de");
        } else {
            vo.setDataReport("\u672a\u4e0a\u62a5");
        }
        vo.setJournalAdjust("\u672a\u8fc7\u8d26");
        return vo;
    }

    private GcReportPickVO buildPickVO(GcReportPickVO vo, GcOrgCacheVO org, String msg, Long start) {
        vo.setId(UUIDUtils.newUUIDStr());
        vo.setOrgName(org.getTitle());
        vo.setParentId(org.getParentId());
        vo.setState(msg);
        if (null != start) {
            String useTime = String.valueOf(System.currentTimeMillis() - start);
            vo.setUseTime(useTime);
        }
        return vo;
    }
}

