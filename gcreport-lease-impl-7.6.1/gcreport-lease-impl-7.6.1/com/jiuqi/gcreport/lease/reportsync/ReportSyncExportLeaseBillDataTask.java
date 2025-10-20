/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.billcore.dto.CommonBillParamDTO
 *  com.jiuqi.gcreport.billcore.reportsync.BillDataSyncTool
 */
package com.jiuqi.gcreport.lease.reportsync;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.billcore.dto.CommonBillParamDTO;
import com.jiuqi.gcreport.billcore.reportsync.BillDataSyncTool;
import java.io.File;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportLeaseBillDataTask
implements IReportSyncExportTask {
    public boolean match(ReportDataSyncParams dataSyncParam) {
        if (!ReportDataSyncTypeEnum.DATA.getCode().equals(dataSyncParam.getSyncType())) {
            return false;
        }
        return !CollectionUtils.isEmpty((Collection)dataSyncParam.getLessorUnitCodes()) || !CollectionUtils.isEmpty((Collection)dataSyncParam.getTenantryUnitCodes());
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        CommonBillParamDTO commonBillParamDTO;
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        int lessorUnitSize = dataSyncParam.getLessorUnitCodes() == null ? 0 : dataSyncParam.getLessorUnitCodes().size();
        int tenantryUnitSize = dataSyncParam.getTenantryUnitCodes() == null ? 0 : dataSyncParam.getTenantryUnitCodes().size();
        String log = String.format("\u878d\u8d44\u79df\u8d41\u53f0\u8d26\u6570\u636e\u5bfc\u51fa\uff0c\u51fa\u79df\u65b9\u5355\u4f4d:%1$s\u5bb6;\u627f\u79df\u65b9\u5355\u4f4d:%2$s\u5bb6;", lessorUnitSize, tenantryUnitSize);
        dataSyncParam.getLogs().add(log);
        if (lessorUnitSize != 0) {
            commonBillParamDTO = new CommonBillParamDTO("GC_LESSORBILL", dataSyncParam.getLessorUnitCodes(), "\u51fa\u79df\u65b9\u53f0\u8d26");
            commonBillParamDTO.addItemTable("GC_LESSORITEMBILL");
            BillDataSyncTool.exportCommonBillTxt((File)rootFolder, (CommonBillParamDTO)commonBillParamDTO);
        }
        if (tenantryUnitSize != 0) {
            commonBillParamDTO = new CommonBillParamDTO("GC_TENANTRYBILL", dataSyncParam.getTenantryUnitCodes(), "\u627f\u79df\u65b9\u53f0\u8d26");
            commonBillParamDTO.addItemTable("GC_TENANTRYITEMBILL");
            BillDataSyncTool.exportCommonBillTxt((File)rootFolder, (CommonBillParamDTO)commonBillParamDTO);
        }
        return null;
    }

    public String funcTitle() {
        return "\u878d\u8d44\u79df\u8d41\u53f0\u8d26";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

