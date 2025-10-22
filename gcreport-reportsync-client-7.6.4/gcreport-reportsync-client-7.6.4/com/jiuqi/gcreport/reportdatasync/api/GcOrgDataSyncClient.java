/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcOrgDataSyncClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/multilevel/orgDataSync/";

    @PostMapping(value={"/api/gcreport/v1/multilevel/orgDataSync/rejectOrgData"})
    public BusinessResponseEntity<Boolean> rejectOrgData(@RequestBody MultilevelOrgDataSyncLogVO var1);

    @GetMapping(value={"/api/gcreport/v1/multilevel/orgDataSync/log/{pageNum}/{pageSize}"})
    public BusinessResponseEntity<PageInfo<MultilevelOrgDataSyncLogVO>> listOrgDataSyncLogs(@PathVariable(value="pageNum") int var1, @PathVariable(value="pageSize") int var2);
}

