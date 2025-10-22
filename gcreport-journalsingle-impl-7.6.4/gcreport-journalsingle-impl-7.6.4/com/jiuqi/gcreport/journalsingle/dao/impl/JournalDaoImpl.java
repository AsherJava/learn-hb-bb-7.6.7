/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.journalsingle.common.SqlUtil
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 */
package com.jiuqi.gcreport.journalsingle.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.journalsingle.common.SqlUtil;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.dao.IJournalDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalEO;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class JournalDaoImpl
extends GcDbSqlGenericDAO<JournalEO, String>
implements IJournalDao {
    private static final String SQL_QUERYJOURNALEOS = "select %1$s \n from GC_JOURNAL  t  \n where t.default_period=?  \n and t.unitid=? \n and (t.md_gcorgtype = ? or t.md_gcorgtype='" + GCOrgTypeEnum.NONE.getCode().toString() + "') \n and t.taskid=? \n and t.schemeid=? \n";

    public JournalDaoImpl() {
        super(JournalEO.class);
    }

    @Override
    public void batchDeleteBySrcid(List<String> mrecidList, int acctYear, int effectType, int acctPeriod) {
        if (mrecidList == null || mrecidList.size() <= 0) {
            return;
        }
        String sql = "delete from gc_journal   where \n" + SqlUtil.buildInSql((String)"srcid", mrecidList) + "and acctYear = " + acctYear + "\n";
        sql = effectType > 0 ? sql + "and acctPeriod >= " + acctPeriod + "\n" : sql + "and acctPeriod = " + acctPeriod + "\n";
        this.execute(sql);
    }

    @Override
    public List<JournalEO> queryJournalByDims(String orgid, String periodValue, String gcorgtype, String taskid, String schemeid) {
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_JOURNAL", (String)"t");
        String formatSQL = String.format(SQL_QUERYJOURNALEOS, columns);
        List journalEOs = this.selectEntity(formatSQL, Arrays.asList(periodValue, orgid, gcorgtype, taskid, schemeid));
        return journalEOs;
    }

    @Override
    public int pushPreDetails(JournalDetailCondition condi) {
        int c;
        int acctPeriod = condi.acctPeriod - 1;
        String querySql = "select t.recid as recid\nfrom gc_journal t\nwhere t.inputunitid= ?  and t.acctYear = ? \n and t.acctPeriod = ? \n and t.adjtype = ? \n";
        if (!StringUtils.isEmpty(condi.adjustTypeCode)) {
            querySql = querySql + "and t.adjustTypeCode = '" + condi.adjustTypeCode + "'\n";
        }
        if ((c = this.count(querySql, new Object[]{condi.inputUnitId, condi.acctYear, acctPeriod, condi.adjType})) == 0) {
            throw new BusinessRuntimeException("\u63d0\u53d6\u5931\u8d25\uff1a\u4e0a\u671f\u6ca1\u6709\u53ef\u63d0\u53d6\u7684\u6570\u636e\u3002");
        }
        String sql = "insert into gc_journaldetail \n(select new_recid() as recid, t.adjusttypeid as adjusttypeid, t.taskId as taskId, t.schemeId as schemeId,t.unitid as unitid,t.unitversionid as unitversionid,t.oppunitid as oppunitid,t.inputunitid as inputunitid,t.acctYear as acctYear," + condi.acctPeriod + "as acctPeriod,t.subjectid as subjectid, t.dc as dc,t.currencyid as currencyid,t.creditcny as creditcny,t.credithkd as credithkd,t.creditusd as creditusd,t.debitcny as debitcny,t.debithkd as debithkd, t.debitusd as debitusd,t.adjtype as adjtype,t.mrecid as mrecid, t.memo as memo, t.sortorder as sortorder, t.createuser as createuser,\nt.createdate as createdate,t.createtime as createtime \nfrom gc_journaldetail t where t.inputunitid=? and t.acctYear = ? and t.acctPeriod = ? and t.adjtype=?\n";
        if (condi.adjustTypeCode != null) {
            sql = sql + "and t.adjusttypeid = '" + condi.adjustTypeCode + "'\n";
        }
        sql = sql + " and not exists(select 1 as c from gc_journaldetail as t1 where t1.inputunitid=@inputUnitID and t1.acctYear = ?\n and t1.acctPeriod =" + condi.acctPeriod + " and t1.adjtype=?\n";
        if (condi.adjustTypeCode != null) {
            sql = sql + "and t1.adjusttypeid = '" + condi.adjustTypeCode + "'\n";
        }
        sql = sql + " and t.mrecid = t1.mrecid))\n";
        return this.execute(sql, new Object[]{condi.inputUnitId, condi.acctYear, acctPeriod, condi.adjType});
    }
}

