/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.user.dto.PasswordDTO
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.user.dto.PasswordDTO;
import java.util.List;
import java.util.Map;

public interface ReportDataSyncServerListService {
    public List<ReportDataSyncServerInfoVO> listServerInfos();

    public List<ReportDataSyncServerInfoVO> listServerInfos(SyncTypeEnums var1);

    public PageInfo<ReportDataSyncServerInfoVO> listServerInfosByPage(String var1, Integer var2, Integer var3);

    public ReportDataSyncServerInfoVO queryServerInfoByOrgCode(String var1);

    public List<ReportDataSyncServerInfoVO> queryServerInfoByOrgCodes(List<String> var1);

    public Boolean updateServerInfoState(Map<String, Object> var1);

    public String updateSyncModifyFlag(Map<String, Object> var1);

    public Boolean updateManageUser(Map<String, Object> var1);

    public Boolean registerServerInfo(ReportDataSyncServerInfoVO var1);

    public Boolean updateServerInfo(ReportDataSyncServerInfoVO var1);

    public Boolean deleteServerInfo(List<String> var1);

    public PasswordDTO queryPasswordDTOByUsername(String var1);

    public ReportDataSyncServerInfoVO getServerInfoByOrgCode(String var1, String var2, String var3);
}

