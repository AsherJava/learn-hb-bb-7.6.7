/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.billcore.common.AbstractExportExcelAmtMultiSheetExecutor
 *  com.jiuqi.np.log.LogHelper
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.lease.leasebill.service.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.common.AbstractExportExcelAmtMultiSheetExecutor;
import com.jiuqi.gcreport.lease.leasebill.service.impl.TenantryBillExportTask;
import com.jiuqi.gcreport.lease.leasebill.service.impl.TenantryBillItemExportTask;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TenantryBillExportExecutor
extends AbstractExportExcelAmtMultiSheetExecutor {
    @Autowired
    private TenantryBillExportTask tenantryBillExportTask;
    @Autowired
    private TenantryBillItemExportTask tenantryBillItemExportTask;

    public String getName() {
        return "TenantryBillExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheetsNew(ExportContext context, Workbook workbook) {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        exportExcelSheets.add(this.tenantryBillExportTask.exportExcelSheet(context));
        exportExcelSheets.add(this.tenantryBillItemExportTask.exportExcelSheet(context));
        this.addTenantryBillExportLog(context.getParam());
        return exportExcelSheets;
    }

    private void addTenantryBillExportLog(String param) {
        Map params = (Map)JsonUtils.readValue((String)param, Map.class);
        String mergeUnit = (String)params.get("mergeUnit");
        String acctYear = ConverterUtils.getAsString(params.get("acctYear"));
        String operateTypeTitle = String.format("\u5bfc\u51fa-\u5e74\u5ea6%1s-\u6267\u884c\u64cd\u4f5c\u5355\u4f4d%2s", acctYear, mergeUnit);
        LogHelper.info((String)"\u5408\u5e76-\u627f\u79df\u65b9\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
    }
}

