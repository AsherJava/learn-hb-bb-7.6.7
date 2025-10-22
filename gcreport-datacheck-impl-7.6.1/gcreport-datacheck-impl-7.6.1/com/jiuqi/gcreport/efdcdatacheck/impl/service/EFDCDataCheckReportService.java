/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.pdf.DownloadZipDTO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.pdf.DownloadZipDTO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

public interface EFDCDataCheckReportService {
    public List<EfdcCheckReportLogVO> queryRecordsByCondition(Map<String, Object> var1);

    public List<EfdcCheckReportLogVO> queryRecordsByPageCondition(Integer var1, Integer var2, Map<String, Object> var3);

    public File viewPdf(@NotNull String var1) throws IOException;

    public DownloadZipDTO batchDownload(Set<String> var1, String var2);

    public String batchDeleteByRecids(Set<String> var1);

    public void clearMongoFiles(String var1) throws Exception;

    public BusinessResponseEntity<String> createEfdcDataCheckReport(EfdcCheckCreateReportVO var1);

    public GcBatchEfdcCheckInfo buildBatchCheckInfo(EfdcCheckCreateReportVO var1, boolean var2) throws Exception;

    public void downloadExcel(String var1, HttpServletResponse var2);

    public String efdcCheckUser(EfdcCheckUserVo var1);

    public String shareFile(GcEfdcShareFileVO var1);
}

