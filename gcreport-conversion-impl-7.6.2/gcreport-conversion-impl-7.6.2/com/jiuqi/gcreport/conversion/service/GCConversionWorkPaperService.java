/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.conversion.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public interface GCConversionWorkPaperService {
    public JSONObject conversionWorkPaper(String var1) throws Exception;

    public JSONObject getConversionWorkPaperDatas(GcConversionWorkPaperEnv var1, Map<String, List<ConversionSystemItemEO>> var2) throws Exception;

    public GcConversionWorkPaperEnv getGcConversionWorkPaperEnv(String var1, ExportContext var2);

    public JSONObject getExcelTableData(GcOrgCacheVO var1);

    public Map<String, List<GcConversionWorkPaperEnv>> getGcBatchConversionWorkPaperEnv(String var1, ExportContext var2) throws Exception;
}

