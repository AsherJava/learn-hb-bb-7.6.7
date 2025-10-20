/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO
 *  com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.analysisreport.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportDataEO;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;
import java.util.Set;

public interface AnalysisReportDataDao
extends IDbSqlGenericDAO<AnalysisReportDataEO, String> {
    public boolean isExistsByTemplateIdAndDimValuesAndVersion(String var1, String var2, String var3, String var4);

    public PageInfo<AnalysisReportDataEO> queryByTemplateIdAndLikeDimValues(String var1, List<String> var2, String var3, ReqQueryAnalysisReportDatasDTO var4);

    public List<AnalysisReportDataEO> queryByTemplateIdsAndLikeDimValuesAndVersionState(List<String> var1, List<String> var2, String var3, AnalysisReportVersionState var4);

    public AnalysisReportDataEO queryPreviousItemById(String var1, String var2, String var3);

    public AnalysisReportDataEO queryNextItemById(String var1, String var2, String var3);

    public List<AnalysisReportDataEO> queryByIds(Set<String> var1);

    public Boolean updateAnalysisReportDataById(AnalysisReportDataEO var1);

    public AnalysisReportDataEO queryLatestConfirmedByTemplateId(String var1);
}

