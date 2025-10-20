/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.asset.assetbill.service.CombinedAssetBillService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CombinedAssetBillImportExecutor
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private CombinedAssetBillService combinedAssetBillService;

    public String getName() {
        return "CombinedAssetBillImportExecutor";
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String defineCode = (String)params.get("defineCode");
        if (StringUtils.isEmpty((String)defineCode)) {
            return "defineCode(\u5355\u636e\u6807\u8bc6)\u4e0d\u80fd\u4e3a\u7a7a";
        }
        StringBuilder log = new StringBuilder(128);
        log.append(this.combinedAssetBillService.commonAssetBillImport(params, excelSheets));
        return log;
    }

    protected int[] getReadSheetNos() {
        return new int[]{0, 1};
    }
}

