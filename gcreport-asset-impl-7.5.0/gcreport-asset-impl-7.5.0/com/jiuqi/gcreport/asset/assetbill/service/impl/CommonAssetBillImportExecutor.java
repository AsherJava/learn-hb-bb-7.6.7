/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor;
import com.jiuqi.gcreport.asset.assetbill.service.CommonAssetBillService;
import com.jiuqi.np.log.LogHelper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonAssetBillImportExecutor
extends AbstractImportExcelOneSheetExecutor {
    @Autowired
    private CommonAssetBillService commonAssetBillService;

    public String getName() {
        return "CommonAssetBillImportExecutor";
    }

    protected Object importExcelSheet(ImportContext context, List<Object[]> excelDatas) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String defineCode = (String)params.get("defineCode");
        if (StringUtils.isEmpty((String)defineCode)) {
            return "defineCode(\u5355\u636e\u6807\u8bc6)\u4e0d\u80fd\u4e3a\u7a7a";
        }
        StringBuilder log = new StringBuilder(128);
        log.append((CharSequence)this.commonAssetBillService.commonAssetBillImport(params, excelDatas));
        this.CommonAssetBillImportLog(context.getParam());
        return log;
    }

    private void CommonAssetBillImportLog(String param) {
        Map params = (Map)JsonUtils.readValue((String)param, Map.class);
        String mergeUnit = (String)params.get("mergeUnit");
        String acctYear = ConverterUtils.getAsString(params.get("acctYear"));
        String operateTypeTitle = String.format("\u5bfc\u51fa-\u5e74\u5ea6%1s-\u6267\u884c\u64cd\u4f5c\u5355\u4f4d%2s", acctYear, mergeUnit);
        LogHelper.info((String)"\u5408\u5e76-\u6295\u8d44\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
    }
}

