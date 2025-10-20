/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelOrgDataSyncLogVO;
import org.springframework.web.multipart.MultipartFile;

public interface GcOrgDataSyncService {
    public Boolean rejectOrgData(MultilevelOrgDataSyncLogVO var1);

    public PageInfo<MultilevelOrgDataSyncLogVO> listOrgDataSyncLogs(int var1, int var2);

    public Boolean importOrgDataFile(MultipartFile var1);
}

