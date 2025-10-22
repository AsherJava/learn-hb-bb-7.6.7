/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.internal.listener.BatchOptEvent
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 */
package com.jiuqi.gcreport.reportdatasync.listen;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.internal.listener.BatchOptEvent;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BatchOptEventListener {
    @Autowired
    ReportDataSyncServerListService serverListService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    List<ISyncMethodScheduler> syncMethodSchedulerList;

    @EventListener
    public Object handleEvent(BatchOptEvent batchOptEvent) {
        String operator = batchOptEvent.getActionCode();
        String orgType = null;
        if (operator.equals(UploadStateEnum.ACTION_REJECT.getCode()) || operator.equals(UploadStateEnum.CUSTOM_REJECT.getCode())) {
            List orgTypeList = this.runTimeViewController.listTaskOrgLinkByTask(batchOptEvent.getTaskKey());
            if (orgTypeList.size() > 1) {
                String entityId;
                DsContext dsContext = DsContextHolder.getDsContext();
                orgType = entityId = dsContext.getContextEntityId();
            } else {
                orgType = ((TaskOrgLinkDefine)orgTypeList.get(0)).getEntity();
            }
            int index = orgType.indexOf("@ORG");
            if (index != -1) {
                orgType = orgType.substring(0, index);
            }
            List<ReportDataSyncServerInfoVO> syncServerInfoVOS = this.serverListService.listServerInfos();
            this.sendBackMsg(orgType, batchOptEvent, batchOptEvent.getUnits(), syncServerInfoVOS);
        }
        return null;
    }

    public void sendBackMsg(String orgType, BatchOptEvent batchOptEvent, List<String> units, List<ReportDataSyncServerInfoVO> syncServerInfoVOS) {
        HashMap serverInfoVoUnitsMap = new HashMap();
        HashMap serverInfoMap = new HashMap();
        YearPeriodObject yp = new YearPeriodObject(null, batchOptEvent.getPeriod());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        for (int i = 0; i < units.size(); ++i) {
            String orgCode = units.get(i);
            GcOrgCacheVO orgCacheVO = instance.getOrgByCode(orgCode);
            List<String> orgParents = Arrays.asList(orgCacheVO.getParents());
            List vos = syncServerInfoVOS.stream().filter(info -> {
                List<String> orgCodeList = ReportDataSyncUtils.getOrgCodesByServerInfo(info);
                List repeatCodes = orgCodeList.stream().filter(orgParents::contains).collect(Collectors.toList());
                return !CollectionUtils.isEmpty(repeatCodes);
            }).collect(Collectors.toList());
            if (vos.isEmpty()) continue;
            if (serverInfoVoUnitsMap.containsKey(((ReportDataSyncServerInfoVO)vos.get(0)).getId())) {
                ((List)serverInfoVoUnitsMap.get(((ReportDataSyncServerInfoVO)vos.get(0)).getId())).add(orgCode);
                continue;
            }
            ArrayList<String> unitLists = new ArrayList<String>();
            unitLists.add(orgCode);
            serverInfoVoUnitsMap.put(((ReportDataSyncServerInfoVO)vos.get(0)).getId(), unitLists);
            serverInfoMap.put(((ReportDataSyncServerInfoVO)vos.get(0)).getId(), vos.get(0));
        }
        String periodStrTitle = ReportDataSyncUtils.getPeriodTitle(batchOptEvent.getPeriod());
        for (String key : serverInfoVoUnitsMap.keySet()) {
            List orgCodes = (List)serverInfoVoUnitsMap.get(key);
            ReportDataSyncServerInfoVO serverInfoVO = (ReportDataSyncServerInfoVO)serverInfoMap.get(key);
            if (orgCodes == null || orgCodes.size() == 0 || serverInfoVO == null) continue;
            TaskDefine taskDefine = this.runTimeViewController.getTask(batchOptEvent.getTaskKey());
            if (Boolean.FALSE.equals(serverInfoVO.getSupportAll())) continue;
            ReportDataSyncUploadDataTaskVO uploadDataTaskVO = new ReportDataSyncUploadDataTaskVO();
            uploadDataTaskVO.setPeriodStr(periodStrTitle);
            uploadDataTaskVO.setPeriodValue(batchOptEvent.getPeriod());
            uploadDataTaskVO.setOrgCode(serverInfoVO.getOrgCode());
            uploadDataTaskVO.setOrgTitle(serverInfoVO.getOrgTitle());
            uploadDataTaskVO.setUploadTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
            ContextUser user = NpContextHolder.getContext().getUser();
            uploadDataTaskVO.setUploadUserId(user.getId());
            uploadDataTaskVO.setUploadUsername(user.getFullname());
            uploadDataTaskVO.setTaskId(taskDefine.getKey());
            uploadDataTaskVO.setTaskCode(taskDefine.getTaskCode());
            uploadDataTaskVO.setTaskTitle(taskDefine.getTitle());
            uploadDataTaskVO.setStatus(UploadStatusEnum.REJECTED.getCode());
            uploadDataTaskVO.setAdjustCode(batchOptEvent.getAdjustPeriod());
            StringBuffer msg = new StringBuffer();
            uploadDataTaskVO.setReturnUnitCodes(orgCodes);
            if (null != batchOptEvent.getContent()) {
                msg.append("\u9000\u56de\u539f\u56e0\uff1a" + batchOptEvent.getContent());
            }
            uploadDataTaskVO.setRejectMsg(msg.toString());
            uploadDataTaskVO.setId(UUIDUtils.newUUIDStr());
            String type = serverInfoVO.getSyncMethod();
            MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
            if (null == extendService) {
                return;
            }
            extendService.rejectReportData(serverInfoVO, JsonUtils.writeValueAsString((Object)uploadDataTaskVO), SyncTypeEnums.REPORTDATA);
        }
    }
}

