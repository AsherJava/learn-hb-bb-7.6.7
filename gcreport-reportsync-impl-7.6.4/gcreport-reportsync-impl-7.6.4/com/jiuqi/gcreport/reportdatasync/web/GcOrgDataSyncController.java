/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.reportdatasync.api.GcOrgDataSyncClient
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.api.GcOrgDataSyncClient;
import com.jiuqi.gcreport.reportdatasync.service.GcOrgDataSyncService;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcOrgDataSyncController
implements GcOrgDataSyncClient {
    @Autowired
    private GcOrgDataSyncService gcOrgDataSyncService;

    public BusinessResponseEntity<Boolean> rejectOrgData(MultilevelOrgDataSyncLogVO vo) {
        return BusinessResponseEntity.ok((Object)this.gcOrgDataSyncService.rejectOrgData(vo));
    }

    public BusinessResponseEntity<PageInfo<MultilevelOrgDataSyncLogVO>> listOrgDataSyncLogs(int pageNum, int pageSize) {
        return BusinessResponseEntity.ok(this.gcOrgDataSyncService.listOrgDataSyncLogs(pageNum, pageSize));
    }
}

