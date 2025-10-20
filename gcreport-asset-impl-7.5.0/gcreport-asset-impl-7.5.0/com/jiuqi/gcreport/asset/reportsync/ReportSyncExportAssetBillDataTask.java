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
package com.jiuqi.gcreport.asset.reportsync;

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
public class ReportSyncExportAssetBillDataTask
implements IReportSyncExportTask {
    public boolean match(ReportDataSyncParams dataSyncParam) {
        if (!ReportDataSyncTypeEnum.DATA.getCode().equals(dataSyncParam.getSyncType())) {
            return false;
        }
        return !CollectionUtils.isEmpty((Collection)dataSyncParam.getAssetUnitCodes());
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        String log = String.format("\u8d44\u4ea7\u53f0\u8d26\u6570\u636e\u5bfc\u51fa\uff0c\u5355\u4f4d:%1$s\u5bb6;", dataSyncParam.getAssetUnitCodes().size());
        dataSyncParam.getLogs().add(log);
        CommonBillParamDTO commonBillParamDTO = new CommonBillParamDTO("GC_COMBINEDASSETBILL", dataSyncParam.getAssetUnitCodes(), "\u7ec4\u5408\u8d44\u4ea7\u53f0\u8d26");
        commonBillParamDTO.addItemTable("GC_COMBINEDASSETBILLITEM");
        BillDataSyncTool.exportCommonBillTxt((File)rootFolder, (CommonBillParamDTO)commonBillParamDTO);
        commonBillParamDTO = new CommonBillParamDTO("GC_COMMONASSETBILL", dataSyncParam.getAssetUnitCodes(), "\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26");
        BillDataSyncTool.exportCommonBillTxt((File)rootFolder, (CommonBillParamDTO)commonBillParamDTO);
        return null;
    }

    public String funcTitle() {
        return "\u8d44\u4ea7\u53f0\u8d26";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

