/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
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
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.reportdatasync.action;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.reportdatasync.action.ReportDataSyncUploadDataRejectActionParam;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncFlowUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReportDataSyncUploadDataRejectAction
extends AbstractGcActionItem {
    @Autowired
    private ReportDataSyncServerListService serverListService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    List<ISyncMethodScheduler> syncMethodSchedulerList;

    protected ReportDataSyncUploadDataRejectAction() {
        super("ReportDataSyncUploadDataRejectAction", "\u6570\u636e\u540c\u6b65\u9000\u56de", "gcreportdatasync-\u6570\u636e\u540c\u6b65\u9000\u56de", "#icon-16_DH_A_GC_wbzs");
    }

    @Transactional(rollbackFor={Exception.class})
    public Object execute(GcActionItemEnv actionItemEnv) {
        ReportDataSyncServerInfoVO serverInfoVO;
        String orgCode;
        ReportDataSyncUploadDataRejectActionParam rejectActionParam = (ReportDataSyncUploadDataRejectActionParam)JsonUtils.readValue((String)actionItemEnv.getActionParam(), ReportDataSyncUploadDataRejectActionParam.class);
        String parentOrgCode = orgCode = rejectActionParam.getOrgCode();
        YearPeriodObject yp = new YearPeriodObject(null, rejectActionParam.getPeriodStr());
        GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)rejectActionParam.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO currentUnit = noAuthzOrgTool.getOrgByID(orgCode);
        if (currentUnit != null) {
            List orgCodes;
            List parentCodeList;
            List<String> unitParents = Arrays.asList(currentUnit.getParents());
            List<ReportDataSyncServerInfoVO> serverInfoVOS = this.serverListService.listServerInfos();
            if (!CollectionUtils.isEmpty(serverInfoVOS) && !CollectionUtils.isEmpty(parentCodeList = (orgCodes = serverInfoVOS.stream().map(ReportDataSyncServerInfoVO::getOrgCode).collect(Collectors.toList())).stream().filter(code -> unitParents.contains(code)).collect(Collectors.toList()))) {
                parentOrgCode = (String)parentCodeList.get(0);
            }
        }
        if ((serverInfoVO = this.serverListService.queryServerInfoByOrgCode(parentOrgCode)) == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + orgCode + "]\u672a\u5728\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u529f\u80fd\u70b9\u542f\u7528\uff0c\u4e0d\u652f\u6301\u6570\u636e\u9000\u56de\u3002");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(rejectActionParam.getTaskId());
        ReportDataSyncFlowUtils.executeDataFlow(taskDefine.getTaskCode(), orgCode, rejectActionParam.getPeriodStr(), UploadStateEnum.ACTION_REJECT, rejectActionParam.getRejectMsg(), rejectActionParam.getSelectAdjust());
        if (Boolean.FALSE.equals(serverInfoVO.getSupportAll())) {
            return null;
        }
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = new ReportDataSyncUploadDataTaskVO();
        uploadDataTaskVO.setPeriodStr(ReportDataSyncUtils.getPeriodTitle(rejectActionParam.getPeriodStr()));
        uploadDataTaskVO.setPeriodValue(rejectActionParam.getPeriodStr());
        uploadDataTaskVO.setOrgCode(serverInfoVO.getOrgCode());
        uploadDataTaskVO.setOrgTitle(serverInfoVO.getOrgTitle());
        uploadDataTaskVO.setUploadTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        ContextUser user = NpContextHolder.getContext().getUser();
        uploadDataTaskVO.setUploadUserId(user.getId());
        uploadDataTaskVO.setUploadUsername(user.getFullname());
        uploadDataTaskVO.setTaskId(taskDefine.getKey());
        uploadDataTaskVO.setTaskCode(taskDefine.getTaskCode());
        uploadDataTaskVO.setTaskTitle(rejectActionParam.getTaskTitle());
        uploadDataTaskVO.setStatus(UploadStatusEnum.REJECTED.getCode());
        StringBuilder msg = new StringBuilder();
        msg.append("\u9000\u56de\u5355\u4f4d\uff1a").append(currentUnit.getCode()).append("|").append(currentUnit.getTitle()).append(";\n");
        if (null != rejectActionParam.getRejectMsg()) {
            msg.append("\u9000\u56de\u539f\u56e0\uff1a").append(rejectActionParam.getRejectMsg());
        }
        uploadDataTaskVO.setRejectMsg(msg.toString());
        uploadDataTaskVO.setId(UUIDUtils.newUUIDStr());
        uploadDataTaskVO.setAdjustCode(rejectActionParam.getSelectAdjust());
        ArrayList<String> unitCodes = new ArrayList<String>();
        unitCodes.add(orgCode);
        uploadDataTaskVO.setReturnUnitCodes(unitCodes);
        String type = serverInfoVO.getSyncMethod();
        MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
        if (null != extendService) {
            extendService.rejectReportData(serverInfoVO, JsonUtils.writeValueAsString((Object)uploadDataTaskVO), SyncTypeEnums.REPORTDATA);
        }
        return null;
    }

    public boolean isEnable(String enableContextJson) {
        String selectAdjust;
        if (StringUtils.isEmpty((String)enableContextJson)) {
            return false;
        }
        DataEntryContext dataEntryContext = (DataEntryContext)JsonUtils.readValue((String)enableContextJson, DataEntryContext.class);
        DimensionValue orgDimensionValue = (DimensionValue)dataEntryContext.getDimensionSet().get("MD_ORG");
        DimensionValue dataTimeValue = (DimensionValue)dataEntryContext.getDimensionSet().get("DATATIME");
        if (orgDimensionValue == null || dataTimeValue == null) {
            return false;
        }
        String orgCode = orgDimensionValue.getValue();
        String periodStr = dataTimeValue.getValue();
        DimensionValue adjustDim = (DimensionValue)dataEntryContext.getDimensionSet().get("ADJUST");
        String string = selectAdjust = adjustDim == null ? "0" : adjustDim.getValue();
        if (StringUtils.isEmpty((String)orgCode) || StringUtils.isEmpty((String)periodStr)) {
            return false;
        }
        ReportDataSyncServerInfoVO serverInfoVO = this.serverListService.queryServerInfoByOrgCode(orgCode);
        if (serverInfoVO == null) {
            return false;
        }
        ActionStateBean actionState = ReportDataSyncFlowUtils.queryUnitState(dataEntryContext.getFormSchemeKey(), orgCode, periodStr, selectAdjust);
        return UploadState.UPLOADED.name().equals(actionState.getCode());
    }
}

