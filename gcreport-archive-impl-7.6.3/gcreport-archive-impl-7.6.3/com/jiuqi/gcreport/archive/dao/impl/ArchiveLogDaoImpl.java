/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.archive.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.dao.ArchiveLogDao;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveLogDaoImpl
extends GcDbSqlGenericDAO<ArchiveLogEO, String>
implements ArchiveLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ArchiveLogDaoImpl() {
        super(ArchiveLogEO.class);
    }

    @Override
    public PageInfo<ArchiveLogEO> querybatchLogByConid(ArchiveQueryParam param, String tempGroupId) {
        List archiveLogEOS;
        List<String> idListByConid = this.getIdListByConid(param, tempGroupId);
        if (CollectionUtils.isEmpty(idListByConid)) {
            return PageInfo.empty();
        }
        String sql = "select %1$s \n  from GC_ARCHIVE_LOG t \n  where  " + SqlUtils.getConditionOfIdsUseOr(idListByConid, (String)"t.id") + "\n  order by t.CREATE_DATE desc \n";
        int count = this.count(sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ARCHIVE_LOG", (String)"t")), new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        if (param.getPageNum() == -1 || param.getPageSize() == -1) {
            archiveLogEOS = this.selectEntity(sql, new Object[0]);
        } else {
            int start = (param.getPageNum() - 1) * param.getPageSize();
            archiveLogEOS = this.selectEntityByPaging(sql, start, param.getPageNum() * param.getPageSize(), new Object[0]);
        }
        return PageInfo.of((List)archiveLogEOS, (int)count);
    }

    @Override
    public List<String> getIdListByConid(ArchiveQueryParam param, String tempGroupId) {
        String sql = " select d.id from GC_ARCHIVE_LOG d \n      left join  GC_ARCHIVE_INFO info \n        on  d.id = info.logId  \n     where 1=1 \n       %1$s \n     group by d.ID  \n";
        StringBuffer whereSql = new StringBuffer();
        ArrayList<String> args = new ArrayList<String>();
        if (param.getQueryConditions() != null) {
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getUserName())) {
                whereSql.append(" and (info.unitId in (SELECT TEMP.TBCODE FROM GC_IDTEMPORARY TEMP WHERE GROUP_ID = ? ) or d.CREATE_USER = ? ) \n");
                args.add(tempGroupId);
                args.add(param.getQueryConditions().getUserName());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getTaskId())) {
                whereSql.append(" and d.taskId = ? \n");
                args.add(param.getQueryConditions().getTaskId());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getSchemeId())) {
                whereSql.append(" and d.schemeId = ? \n");
                args.add(param.getQueryConditions().getSchemeId());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getSchemeId())) {
                whereSql.append(" and d.orgType = ? \n");
                args.add(param.getQueryConditions().getOrgType());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getDefaultPeriod())) {
                whereSql.append(" and d.START_PERIOD <= ?  \n");
                whereSql.append(" and d.END_PERIOD >= ? \n");
                args.add(param.getQueryConditions().getDefaultPeriod());
                args.add(param.getQueryConditions().getDefaultPeriod());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getStartPeriodString())) {
                whereSql.append(" and (d.START_PERIOD >= ? or (d.START_PERIOD <= ? and d.END_PERIOD >= ?))\n");
                args.add(param.getQueryConditions().getStartPeriodString());
                args.add(param.getQueryConditions().getStartPeriodString());
                args.add(param.getQueryConditions().getStartPeriodString());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getEndPeriodString())) {
                whereSql.append(" and (d.END_PERIOD <= ? or (d.END_PERIOD >= ? and  d.START_PERIOD <= ?))\n");
                args.add(param.getQueryConditions().getEndPeriodString());
                args.add(param.getQueryConditions().getEndPeriodString());
                args.add(param.getQueryConditions().getEndPeriodString());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getOrgCode())) {
                whereSql.append(" and info.unitId = ?  \n");
                args.add(param.getQueryConditions().getOrgCode());
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getStatus())) {
                whereSql.append(" and info.status = ?  \n");
                args.add(param.getQueryConditions().getStatus());
            }
        }
        String formatSql = String.format(sql, whereSql);
        return (List)this.jdbcTemplate.query(formatSql, args.toArray(), rs -> {
            ArrayList<String> idList = new ArrayList<String>();
            while (rs.next()) {
                idList.add(rs.getString(1));
            }
            return idList;
        });
    }

    @Override
    public List<ArchiveLogEO> getInExecTaskLogByUnit(String taskKey, String formSchemeKey, String unit, String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select  START_PERIOD,END_PERIOD from GC_ARCHIVE_LOG t");
        sql.append(" where 1=1");
        sql.append("   and t.taskId = ?");
        sql.append("   and t.schemeId = ?");
        sql.append("   and t.id <> ? ");
        sql.append("   and t.status = 0 ");
        sql.append("   and t.orgCodeList like ?");
        return (List)this.jdbcTemplate.query(sql.toString(), new Object[]{taskKey, formSchemeKey, id, "%" + unit + "%"}, rs -> {
            ArrayList<ArchiveLogEO> result = new ArrayList<ArchiveLogEO>();
            while (rs.next()) {
                ArchiveLogEO archiveLogEO = new ArchiveLogEO();
                archiveLogEO.setStartPeriod(rs.getString(1));
                archiveLogEO.setEndPeriod(rs.getString(2));
                result.add(archiveLogEO);
            }
            return result;
        });
    }
}

