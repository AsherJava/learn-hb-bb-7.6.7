/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.asset.assetbill.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import java.util.List;
import java.util.Map;

public interface CommonAssetBillService {
    public PageInfo<Map<String, Object>> listAssetBills(Map<String, Object> var1);

    public void batchDelete(List<String> var1);

    public void batchUnDisposal(List<String> var1);

    public String getIdsByBillCode(String var1, String var2);

    public void transfer2FixedAsset(String var1);

    public ExportExcelSheet getExcelSheet(ExportContext var1, Map<String, Object> var2);

    public StringBuilder commonAssetBillImport(Map<String, Object> var1, List<Object[]> var2);
}

