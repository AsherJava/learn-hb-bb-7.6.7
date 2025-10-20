/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerInfoService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ReportDataSyncServerInfoController
implements ReportDataSyncServerInfoClient {
    @Autowired
    private ReportDataSyncServerInfoService reportDataSyncServerInfoService;

    public BusinessResponseEntity<ReportDataSyncServerInfoVO> saveServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncUtil.checkIsSystemIdentity();
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerInfoService.saveServerInfo(serverInfoVO));
    }

    public BusinessResponseEntity<ReportDataSyncServerInfoVO> queryServerInfo() {
        ReportDataSyncUtil.checkIsSystemIdentity();
        ReportDataSyncServerInfoVO serverInfoVO = this.reportDataSyncServerInfoService.queryServerInfo();
        return BusinessResponseEntity.ok((Object)serverInfoVO);
    }

    public BusinessResponseEntity<ReportDataSyncServerInfoVO> register(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncUtil.checkIsSystemIdentity();
        ReportDataSyncServerInfoVO connectionedServerInfoVO = this.reportDataSyncServerInfoService.register(serverInfoVO);
        return BusinessResponseEntity.ok((Object)connectionedServerInfoVO);
    }

    public BusinessResponseEntity<Boolean> connection(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncUtil.checkIsSystemIdentity();
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerInfoService.connection(serverInfoVO));
    }

    public BusinessResponseEntity<ReportDataSyncServerInfoVO> syncModifyFlagUpdate(@PathVariable(value="startFlag") Boolean startFlag) {
        ReportDataSyncServerInfoVO serverInfoVO = this.reportDataSyncServerInfoService.queryServerInfo();
        serverInfoVO.setSyncModifyFlag(startFlag);
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerInfoService.saveServerInfo(serverInfoVO));
    }
}

