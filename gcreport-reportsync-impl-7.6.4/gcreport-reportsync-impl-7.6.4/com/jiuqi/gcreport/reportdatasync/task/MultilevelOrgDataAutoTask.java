/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDataService
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.bizmeta.service.IMetaInfoService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.reportdatasync.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultilevelOrgDataAutoTask
implements AutoTask {
    @Autowired
    public List<ISyncMethodScheduler> syncMethodSchedulerList;
    @Autowired
    private ReportDataSyncServerListService serverListService;
    @Autowired
    BillDataService billDataService;
    @Autowired
    BillDefineService billDefineService;
    @Autowired
    IMetaInfoService metaInfoService;

    public String getName() {
        return "MultilevelOrgDataAutoTask";
    }

    public String getTitle() {
        return "\u591a\u7ea7\u90e8\u7f72\u7ec4\u7ec7\u673a\u6784\u9000\u56de\u81ea\u52a8\u4efb\u52a1";
    }

    public String getAutoTaskModule() {
        return "bill";
    }

    public Boolean canRetract() {
        return true;
    }

    public R execute(Object params) {
        TenantDO tenantDO = (TenantDO)params;
        String bizCode = tenantDO.getExtInfo("bizCode").toString();
        TenantDO param = new TenantDO();
        HashMap<String, String> extInfo = new HashMap<String, String>();
        param.setExtInfo(extInfo);
        extInfo.put("billCode", bizCode);
        BillDefine billDefine = this.billDataService.getBillDefineByCode(param);
        MetaInfoDTO metaData = this.metaInfoService.getMetaInfoByUniqueCode(billDefine.getName());
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), metaData.getUniqueCode());
        model.getContext().setContextValue("disableVerify", (Object)true);
        model.loadByCode(bizCode);
        String srcBillCode = (String)model.getMaster().getValue("SRCBILLCODE", String.class);
        extInfo.put("billCode", srcBillCode);
        BillDefine srcBillDefine = this.billDataService.getBillDefineByCode(param);
        MetaInfoDTO srcMetaData = this.metaInfoService.getMetaInfoByUniqueCode(srcBillDefine.getName());
        BillModelImpl srcModel = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), srcMetaData.getUniqueCode());
        srcModel.getContext().setContextValue("disableVerify", (Object)true);
        srcModel.loadByCode(srcBillCode);
        String unitcode = (String)srcModel.getMaster().getValue("SYNCPARENTCODE", String.class);
        String orgType = (String)srcModel.getMaster().getValue("SYNCORGTYPE", String.class);
        ReportDataSyncServerInfoVO serverInfoVO = this.serverListService.queryServerInfoByOrgCode(unitcode);
        if (serverInfoVO == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5355\u4f4d\u4ee3\u7801\u4e3a" + unitcode + "\u7684\u76ee\u6807\u670d\u52a1\u5668");
        }
        if (!serverInfoVO.getStartFlag().booleanValue()) {
            throw new BusinessRuntimeException("\u76ee\u6807\u670d\u52a1\u5668\u672a\u542f\u7528");
        }
        String synclogId = (String)srcModel.getMaster().getValue("SYNCLOGID", String.class);
        MultilevelOrgDataSyncLogVO logVO = new MultilevelOrgDataSyncLogVO();
        NpContext npContext = NpContextHolder.getContext();
        logVO.setOrgType(orgType);
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        String orgTypeTitle = iEntityMetaService.queryEntity(orgType + "@ORG").getTitle();
        logVO.setSyncLogId(synclogId);
        logVO.setOrgTypeTitle(orgTypeTitle);
        logVO.setUploadTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        logVO.setUploadUsername(npContext.getUser().getFullname());
        logVO.setUploadUserId(npContext.getUserId());
        logVO.setUploadStatus(UploadStatusEnum.REJECTED.getCode());
        String content = tenantDO.getExtInfo().get("approvalOpinion") == null ? "" : tenantDO.getExtInfo().get("approvalOpinion").toString();
        String uploadMsg = "\u7ec4\u7ec7\u673a\u6784\u53d8\u66f4\u7533\u8bf7\u88ab\u9a73\u56de\uff0c\u9a73\u56de\u4eba\uff1a" + NpContextHolder.getContext().getUser().getName() + "\uff0c\u9a73\u56de\u539f\u56e0\uff1a" + content + "\uff0c\u8bf7\u6c9f\u901a\u540e\u518d\u6b21\u8fdb\u884c\u540c\u6b65\u3002";
        logVO.setUploadMsg(uploadMsg);
        MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(serverInfoVO.getSyncMethod())).findFirst().orElse(null)).getHandler();
        if (null != extendService) {
            extendService.rejectReportData(serverInfoVO, JsonUtils.writeValueAsString((Object)logVO), SyncTypeEnums.ORGDATA);
        }
        return null;
    }

    public R retrack(Object params) {
        return null;
    }
}

