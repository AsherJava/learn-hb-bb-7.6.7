/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.billcore.common.AbstractExportExcelAmtMultiSheetExecutor
 *  com.jiuqi.gcreport.billcore.util.BillParseTool
 *  com.jiuqi.gcreport.billcore.vo.BillInfoVo
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.asset.assetbill.service.CommonAssetBillService;
import com.jiuqi.gcreport.asset.assetbill.service.impl.CommonAssetBillItemExportTask;
import com.jiuqi.gcreport.billcore.common.AbstractExportExcelAmtMultiSheetExecutor;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonAssetBillExportExecutor
extends AbstractExportExcelAmtMultiSheetExecutor {
    @Autowired
    private CommonAssetBillService commonAssetBillService;
    @Autowired
    private CommonAssetBillItemExportTask commonAssetBillItemExportTask;

    public String getName() {
        return "CommonAssetBillExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheetsNew(ExportContext context, Workbook workbook) {
        context.getVarMap().put("headAmt", this.getHeadAmtStyle(workbook));
        context.getVarMap().put("contentAmt", this.getContentAmtStyle(workbook));
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        exportExcelSheets.add(this.commonAssetBillService.getExcelSheet(context, params));
        BillInfoVo defineCode = BillParseTool.parseBillInfo((String)params.get("defineCode").toString());
        if (!CollectionUtils.isEmpty((Collection)defineCode.getSubTableNames())) {
            exportExcelSheets.add(this.commonAssetBillItemExportTask.exportExcelSheet(context));
        }
        this.commonAssetBillExportLog(context.getParam());
        return exportExcelSheets;
    }

    private void commonAssetBillExportLog(String param) {
        Map params = (Map)JsonUtils.readValue((String)param, Map.class);
        String mergeUnit = (String)params.get("mergeUnit");
        String acctYear = ConverterUtils.getAsString(params.get("acctYear"));
        String operateTypeTitle = String.format("\u5bfc\u51fa-\u5e74\u5ea6%1s-\u6267\u884c\u64cd\u4f5c\u5355\u4f4d%2s", acctYear, mergeUnit);
        LogHelper.info((String)"\u5408\u5e76-\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
    }

    private CellStyle getHeadAmtStyle(Workbook workbook) {
        CellStyle headAmtStyle = this.buildDefaultHeadCellStyle(workbook);
        headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        return headAmtStyle;
    }

    private CellStyle getContentAmtStyle(Workbook workbook) {
        CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
        contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        return contentAmtStyle;
    }
}

