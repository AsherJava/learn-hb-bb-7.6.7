/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerListClient
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelTemplateVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.user.dto.PasswordDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerListClient;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelTemplateVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.user.dto.PasswordDTO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ReportDataSyncServerListController
implements ReportDataSyncServerListClient {
    @Autowired
    private ReportDataSyncServerListService reportDataSyncServerListService;

    public BusinessResponseEntity<PageInfo<ReportDataSyncServerInfoVO>> listServerInfos(String keywords, Integer pageSize, Integer pageNum) {
        ReportDataSyncUtil.checkIsSystemIdentity();
        return BusinessResponseEntity.ok(this.reportDataSyncServerListService.listServerInfosByPage(keywords, pageSize, pageNum));
    }

    public BusinessResponseEntity<Boolean> updateServerInfoState(Map<String, Object> params) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.updateServerInfoState(params));
    }

    public BusinessResponseEntity<String> updateSyncModifyFlag(Map<String, Object> params) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.updateSyncModifyFlag(params));
    }

    public BusinessResponseEntity<Boolean> updateManageUser(Map<String, Object> params) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.updateManageUser(params));
    }

    public BusinessResponseEntity<Boolean> registerServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.registerServerInfo(serverInfoVO));
    }

    public BusinessResponseEntity<Boolean> updateServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataSyncUtil.checkIsSystemIdentity();
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.updateServerInfo(serverInfoVO));
    }

    public BusinessResponseEntity<Boolean> deleteServerInfo(List<String> ids) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.deleteServerInfo(ids));
    }

    public BusinessResponseEntity<PasswordDTO> queryPasswordDTOByUsername(String username) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.queryPasswordDTOByUsername(username));
    }

    public BusinessResponseEntity<ReportDataSyncServerInfoVO> getServerInfoByOrgCode(String orgType, String periodStr, String orgCode) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncServerListService.getServerInfoByOrgCode(orgType, periodStr, orgCode));
    }

    public BusinessResponseEntity<List<MultilevelTemplateVO>> getSyncMethod() {
        return BusinessResponseEntity.ok(ReportDataSyncUtils.getSyncMethod());
    }

    public BusinessResponseEntity<List<MultilevelTemplateVO>> getSyncType() {
        return BusinessResponseEntity.ok(ReportDataSyncUtils.getSyncType());
    }

    public BusinessResponseEntity<List<MultilevelTemplateVO>> listAllFileFormat() {
        return BusinessResponseEntity.ok(ReportDataSyncUtils.listAllReportFileFormat());
    }
}

