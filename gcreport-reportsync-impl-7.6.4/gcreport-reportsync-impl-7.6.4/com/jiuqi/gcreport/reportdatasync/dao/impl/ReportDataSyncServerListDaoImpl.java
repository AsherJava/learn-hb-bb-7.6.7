/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncServerListDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerListEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncServerListDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncServerListEO, String>
implements ReportDataSyncServerListDao {
    public ReportDataSyncServerListDaoImpl() {
        super(ReportDataSyncServerListEO.class);
    }

    @Override
    public List<ReportDataSyncServerListEO> listServerInfos() {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_SERVERLIST", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_DATASYNC_SERVERLIST" + "  t order by CREATETIME desc";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public PageInfo<ReportDataSyncServerListEO> listServerInfosByPage(String keywords, Integer pageSize, Integer pageNum) {
        int count;
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_SERVERLIST", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_DATASYNC_SERVERLIST" + "  t";
        if (!StringUtils.isEmpty((String)keywords)) {
            String keywordsWhereSql = " where 1 = 1 " + (" and (t.url like '%" + keywords + "%'") + (" or t.orgTitle like '%" + keywords + "%'") + (" or t.contacts like '%" + keywords + "%'") + (" or t.contactInfos like '%" + keywords + "%'") + (" or t.manageUsers like '%" + keywords + "%')");
            querySql = querySql + keywordsWhereSql;
        }
        if ((count = this.count(querySql = querySql + " order by CREATETIME desc", new Object[0])) == 0) {
            return PageInfo.empty();
        }
        List serverListEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)serverListEOS, (int)count);
    }

    @Override
    public ReportDataSyncServerListEO queryServerInfoByOrgCode(String orgCode) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_SERVERLIST", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_SERVERLIST").append(" t \n");
        sql.append(" where orgcode = '").append(orgCode).append("' and startFlag = 1");
        List serverListEOS = this.selectEntity(sql.toString(), new Object[0]);
        if (CollectionUtils.isEmpty((Collection)serverListEOS)) {
            return null;
        }
        ReportDataSyncServerListEO reportDataSyncServerListEO = (ReportDataSyncServerListEO)((Object)serverListEOS.get(0));
        return reportDataSyncServerListEO;
    }

    @Override
    public List<ReportDataSyncServerListEO> queryServerInfoByOrgCodes(List<String> orgCodes) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_SERVERLIST", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_SERVERLIST").append(" t \n");
        sql.append(" where startFlag = 1 and ").append(SqlBuildUtil.getStrInCondi((String)"orgcode", orgCodes));
        List serverListEOS = this.selectEntity(sql.toString(), new Object[0]);
        return serverListEOS;
    }

    @Override
    public List<ReportDataSyncServerListEO> listServerInfoStateByIds(List<String> serverInfoIdList) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_SERVERLIST", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_SERVERLIST").append(" t \n");
        sql.append(" where ");
        sql.append(SqlUtils.getConditionOfIdsUseOr(serverInfoIdList, (String)"id"));
        return this.selectEntity(sql.toString(), new Object[0]);
    }
}

