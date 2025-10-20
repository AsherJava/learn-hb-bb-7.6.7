/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.archive.export;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.service.GcArchiveService;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcArchiveLogExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private GcArchiveService archiveService;

    public String getName() {
        return "GcArchiveLogExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        try {
            ArchiveQueryParam archiveQueryParam = (ArchiveQueryParam)JsonUtils.readValue((String)context.getParam(), ArchiveQueryParam.class);
            List<ArchiveInfoVO> archiveInfoList = this.archiveService.detailsAllLogQuery(archiveQueryParam);
            ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(0), "\u5f52\u6863\u8be6\u60c5");
            String[] headers = new String[]{"\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0", "\u65f6\u671f", "\u6587\u4ef6\u540d", "\u5f52\u6863\u72b6\u6001", "\u91cd\u8bd5\u6b21\u6570", "\u521b\u5efa\u65f6\u95f4", "\u65e5\u5fd7"};
            sheet.getRowDatas().add(headers);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (ArchiveInfoVO archiveInfo : archiveInfoList) {
                Object[] row = new Object[]{archiveInfo.getUnitId(), archiveInfo.getUnitName(), archiveInfo.getDefaultPeriod(), archiveInfo.getFileName(), this.getStatusText(archiveInfo.getStatus()), archiveInfo.getRetryCount(), archiveInfo.getCreateDate() != null ? dateFormat.format(archiveInfo.getCreateDate()) : "", archiveInfo.getErrorInfo()};
                sheet.getRowDatas().add(row);
            }
            context.getProgressData().setProgressValueAndRefresh(0.8);
            return Arrays.asList(sheet);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5f52\u6863\u65e5\u5fd7\u5bfc\u51fa\u5931\u8d25: " + e.getMessage(), (Throwable)e);
        }
    }

    private String getStatusText(int status) {
        switch (status) {
            case 1: {
                return "\u4e0a\u4f20\u6210\u529f";
            }
            case -1: {
                return "\u4e0a\u4f20\u5931\u8d25";
            }
            case 2: {
                return "\u53d1\u9001\u540c\u6b65\u4fe1\u606f\u5230\u6863\u6848\u7cfb\u7edf\u6210\u529f";
            }
            case -2: {
                return "\u53d1\u9001\u540c\u6b65\u4fe1\u606f\u5230\u6863\u6848\u7cfb\u7edf\u5931\u8d25";
            }
            case -3: {
                return "\u53d6\u6d88\u5f52\u6863";
            }
        }
        return String.valueOf(status);
    }
}

