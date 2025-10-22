/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.journalsingle.common.SqlUtil
 *  com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum
 */
package com.jiuqi.gcreport.journalsingle.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.journalsingle.common.SqlUtil;
import com.jiuqi.gcreport.journalsingle.dao.IJournalRelateSchemeDao;
import com.jiuqi.gcreport.journalsingle.dao.IJournalSingleDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleEO;
import com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class JournalSingleDaoImpl
extends GcDbSqlGenericDAO<JournalSingleEO, String>
implements IJournalSingleDao {
    public JournalSingleDaoImpl() {
        super(JournalSingleEO.class);
    }

    @Override
    public void batchDeleteBySrcid(List<String> mrecidList, int acctYear, int effectType, int acctPeriod) {
        if (CollectionUtils.isEmpty(mrecidList)) {
            return;
        }
        String sql = "delete from gc_journal_single   where \n" + SqlUtil.buildInSql((String)"srcid", mrecidList) + "and acctYear = " + acctYear + "\n";
        sql = effectType > 0 ? sql + "and acctPeriod >= " + acctPeriod + "\n" : sql + "and acctPeriod = " + acctPeriod + "\n";
        this.execute(sql);
    }

    @Override
    public List<JournalSingleEO> findJournalSingleBySrcid(List<String> mrecidList, int acctYear, int effectType, int acctPeriod) {
        if (CollectionUtils.isEmpty(mrecidList)) {
            return null;
        }
        String sql = "select %1$s from gc_journal_single  t where \n" + SqlUtil.buildInSql((String)"t.srcid", mrecidList) + "and t.acctYear = " + acctYear + "\n";
        sql = effectType > 0 ? sql + "and t.acctPeriod >= " + acctPeriod + "\n" : sql + "and t.acctPeriod = " + acctPeriod + "\n";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_JOURNAL_SINGLE", (String)"t");
        String formatSQL = String.format(sql, columns);
        return this.selectEntity(formatSQL, new Object[0]);
    }

    @Override
    public List<JournalSingleEO> listJournalSingleByDims(String taskId, String schemeId, String periodStr, String orgId, String orgTypeId, String adjust) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(taskId);
        params.add(schemeId);
        params.add(periodStr);
        params.add(orgId);
        params.add(orgTypeId);
        String sql = "select %1$s \n from gc_journal_single  t  \n where t.taskId=? \n and t.schemeId=?\n and t.default_period=? \n and t.inputUnitId=? \n and t.md_gcOrgType in (?,'" + GCOrgTypeEnum.NONE.getCode().toString() + "') \n";
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            sql = sql + " and t.adjust=? \n";
            params.add(adjust);
        }
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_JOURNAL_SINGLE", (String)"t");
        String formatSQL = String.format(sql, columns);
        return this.selectEntity(formatSQL, params);
    }

    @Override
    public Integer batchUpdatePostFlag(String taskId, String schemeId, String periodStr, String orgId, String orgTypeId) {
        String sql = "  update GC_JOURNAL_SINGLE  t \n  set postFlag = 1 \n  where t.taskId=? \n  and t.schemeId=? \n  and t.default_period=?  \n  and t.inputUnitId=? \n  and t.md_gcOrgType=? \n";
        return this.execute(sql, new Object[]{taskId, schemeId, periodStr, orgId, orgTypeId});
    }

    @Override
    public Integer batchUpdatePostFlag(String taskId, String schemeId, String periodStr, String orgId, String orgTypeId, String afterZbTableName) {
        if (StringUtils.isNull((String)afterZbTableName)) {
            return 0;
        }
        IJournalRelateSchemeDao relateSchemeDao = (IJournalRelateSchemeDao)SpringContextUtils.getBean(IJournalRelateSchemeDao.class);
        String jRelateSchemeId = relateSchemeDao.getRelateSchemeId(taskId, schemeId, AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        if (null == jRelateSchemeId) {
            return 0;
        }
        String sql = "  update GC_JOURNAL_SINGLE  t \n  set postFlag = 1 \n  where t.taskId=? \n  and t.schemeId=? \n  and t.default_period=?  \n  and t.inputUnitId=? \n  and t.md_gcOrgType=? \n  and t.subjectCode in (select s.code from gc_journal_subject  s where s.afterZbCode like ? and s.jRelateSchemeId=?)\n";
        return this.execute(sql, new Object[]{taskId, schemeId, periodStr, orgId, orgTypeId, afterZbTableName + "%", jRelateSchemeId});
    }
}

