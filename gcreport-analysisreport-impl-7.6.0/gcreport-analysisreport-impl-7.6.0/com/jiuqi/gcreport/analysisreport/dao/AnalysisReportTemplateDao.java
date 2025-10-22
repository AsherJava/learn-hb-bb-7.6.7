/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.analysisreport.dao;

import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;
import java.util.Map;

public interface AnalysisReportTemplateDao
extends IDbSqlGenericDAO<AnalysisReportEO, String> {
    public List<AnalysisReportEO> queryAuthItemsByParentId(boolean var1, String var2);

    public List<AnalysisReportEO> queryAuthItemsByParentIdContainSelf(boolean var1, String var2);

    public AnalysisReportEO queryAuthPreviousItemById(boolean var1, String var2);

    public AnalysisReportEO queryAuthNextItemById(boolean var1, String var2);

    public List<AnalysisReportEO> queryAuthItemsByIds(boolean var1, List<String> var2);

    public List<AnalysisReportEO> queryAuthAll(boolean var1);

    public List<AnalysisReportEO> queryByTitle(boolean var1, String var2);

    public List<Map<String, Object>> queryAnalysisReportLeafTemplates();
}

