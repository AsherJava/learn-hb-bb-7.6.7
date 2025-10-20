/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.analysisreport.dao;

import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportRefOrgEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface AnalysisReportTemplateRefOrgDao
extends IDbSqlGenericDAO<AnalysisReportRefOrgEO, String> {
    public List<AnalysisReportRefOrgEO> queryItemsByMrecid(String var1);

    public int deleteItemsByMrecid(String var1);
}

