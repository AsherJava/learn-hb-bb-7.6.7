/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 */
package com.jiuqi.gcreport.asset.assetbill.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import java.util.List;
import java.util.Map;

public interface CombinedAssetBillService {
    public String getAllBillListJson(String var1);

    public PageInfo<Map<String, Object>> listAssetBills(Map<String, Object> var1);

    public void batchDelete(List<String> var1);

    public void batchUnDisposal(List<String> var1);

    public String getIdsByBillCode(String var1, String var2);

    public void transfer2FixedAsset(String var1);

    public List<ExportExcelSheet> getExcelSheets(ExportContext var1, Map<String, Object> var2);

    public String commonAssetBillImport(Map<String, Object> var1, List<ImportExcelSheet> var2);
}

