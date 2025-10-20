/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.analysisreport.dao.impl;

import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportTemplateRefOrgDao;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportRefOrgEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisReportTemplateItemRefOrgImpl
extends GcDbSqlGenericDAO<AnalysisReportRefOrgEO, String>
implements AnalysisReportTemplateRefOrgDao {
    public AnalysisReportTemplateItemRefOrgImpl() {
        super(AnalysisReportRefOrgEO.class);
    }

    @Override
    public List<AnalysisReportRefOrgEO> queryItemsByMrecid(String mrecid) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportRefOrgEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_REFORG" + "   t  \n  where  t.mrecid = ? order by t.sortorder asc \n";
        List analysisReportItemEOS = this.selectEntity(sql, new Object[]{mrecid});
        if (analysisReportItemEOS == null) {
            return Collections.emptyList();
        }
        return analysisReportItemEOS;
    }

    @Override
    public int deleteItemsByMrecid(String mrecid) {
        String sql = " delete from  GC_ANALYSISREPORT_REFORG where  mrecid = ?  \n";
        int count = this.execute(sql, new Object[]{mrecid});
        return count;
    }
}

