/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.archive.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface GcArchiveService {
    public FormTree queryFormTree(String var1, String var2);

    public ArchiveInfoEO getArchiveByUnitAndPeriod(JtableContext var1);

    public List<ArchiveInfoEO> getNeedUploadArchive();

    public List<ArchiveInfoEO> getNeedSendArchive();

    public List<ArchiveConfigVO> getArchiveConfig(String var1);

    public void saveArchiveConfig(String var1, List<ArchiveConfigVO> var2);

    public String batchDoActionSave(ArchiveContext var1);

    public void batchDoActionStart(String var1);

    public PageInfo<ArchiveLogVO> batchLogQuery(ArchiveQueryParam var1);

    public PageInfo<ArchiveInfoVO> detailsLogQuery(ArchiveQueryParam var1);

    public List<ArchiveInfoVO> detailsAllLogQuery(ArchiveQueryParam var1);

    public void detailsLogDelete(List<String> var1);

    public List<EFSResponseData> cancelArchive(SendArchiveVO var1);

    default public void beforeSingleArchive(PeriodWrapper startPeriodWrapper) {
    }

    public List<ArchiveConfigVO> getArchiveConfigWithOrgType(String var1, String var2);
}

