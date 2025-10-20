/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.archive.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveInfoDaoImpl
extends GcDbSqlGenericDAO<ArchiveInfoEO, String>
implements ArchiveInfoDao {
    @Autowired
    private ArchiveProperties archiveProperties;

    public ArchiveInfoDaoImpl() {
        super(ArchiveInfoEO.class);
    }

    @Override
    public List<ArchiveInfoEO> queryByUnitAndPeriod(JtableContext context, String unit, String defaultPeriod) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ArchiveInfoEO.class, (String)"e");
        String msql = " select %s from GC_ARCHIVE_INFO e \n  where e.unitId = ? \n   and  e.default_period =  ? \n    and  e.taskId =  ? \n    and  e.schemeid =  ? \n    and  e.retry_count < ? \n ";
        String adjustCode = context.getDimensionSet().get("ADJUST") == null ? "0" : ((DimensionValue)context.getDimensionSet().get("ADJUST")).getValue();
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(unit);
        params.add(defaultPeriod);
        params.add(context.getTaskKey());
        params.add(context.getFormSchemeKey());
        params.add(this.archiveProperties.getRetryCount());
        if (!adjustCode.equals("0")) {
            msql = msql + " and e.adjustCode = ? \n";
            params.add(adjustCode);
        } else {
            msql = msql + " and ( e.adjustCode = '0' or e.adjustCode IS NULL) \n";
        }
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, params);
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return eos;
    }

    @Override
    public List<ArchiveInfoEO> queryByUnitPeriodAndOrgType(JtableContext context, String unit, String orgType, String defaultPeriod) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ArchiveInfoEO.class, (String)"e");
        String msql = " select %s from GC_ARCHIVE_INFO e \n  where e.unitId = ? \n   and  e.default_period =  ? \n    and  e.orgtype =  ? \n    and  e.taskId =  ? \n    and  e.schemeid =  ? \n    and  e.retry_count < ? \n ";
        String adjustCode = context.getDimensionSet().get("ADJUST") == null ? "0" : ((DimensionValue)context.getDimensionSet().get("ADJUST")).getValue();
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(unit);
        params.add(defaultPeriod);
        params.add(orgType);
        params.add(context.getTaskKey());
        params.add(context.getFormSchemeKey());
        params.add(this.archiveProperties.getRetryCount());
        if (!adjustCode.equals("0")) {
            msql = msql + " and e.adjustCode = ? \n";
            params.add(adjustCode);
        } else {
            msql = msql + " and ( e.adjustCode = '0' or e.adjustCode IS NULL) \n";
        }
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, params);
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return eos;
    }

    @Override
    public List<ArchiveInfoEO> getNeedUploadArchive(int retryCount) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ArchiveInfoEO.class, (String)"e");
        String msql = "select %s from GC_ARCHIVE_INFO e \n  where e.status = " + ArchiveStatusEnum.UPLOAD_FAILED.getStatus() + "\n   and  e.retry_count < ? \n ";
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, new Object[]{retryCount});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return eos;
    }

    @Override
    public List<ArchiveInfoEO> getNeedSendArchive(int retryCount) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ArchiveInfoEO.class, (String)"e");
        String msql = "select %s from GC_ARCHIVE_INFO e \n  where (e.status = " + ArchiveStatusEnum.UPLOAD_SUCCESS.getStatus() + " or  e.status = " + ArchiveStatusEnum.SEND_FAILED.getStatus() + " )\n   and  e.retry_count < ? \n ";
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, new Object[]{retryCount});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return eos;
    }

    @Override
    public PageInfo<ArchiveInfoEO> queryArchiveInfoByConid(ArchiveQueryParam param) {
        int count;
        String sql = "select %1$s \n     from GC_ARCHIVE_INFO d \n    where 1=1 \n    %2$s \n    order by d.CREATE_DATE desc \n";
        ArchiveContext queryConditions = param.getQueryConditions();
        ArrayList<String> args = new ArrayList<String>(8);
        StringBuffer whereSql = new StringBuffer();
        if (!StringUtils.isEmpty((String)queryConditions.getLogId())) {
            whereSql.append("    and d.LOGID = ? \n");
            args.add(queryConditions.getLogId());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getTaskId())) {
            whereSql.append("    and d.TASKID = ? \n");
            args.add(queryConditions.getTaskId());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getSchemeId())) {
            whereSql.append("    and d.SCHEMEID = ? \n");
            args.add(queryConditions.getSchemeId());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getStartPeriodString())) {
            whereSql.append("    and d.DEFAULT_PERIOD >= ? \n");
            args.add(queryConditions.getStartPeriodString());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getEndPeriodString())) {
            whereSql.append("    and d.DEFAULT_PERIOD <= ? \n");
            args.add(queryConditions.getEndPeriodString());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getOrgCode())) {
            whereSql.append("    and d.unitId like '%").append(queryConditions.getOrgCode()).append("%' \n");
        }
        if (!StringUtils.isEmpty((String)queryConditions.getStatus())) {
            whereSql.append("    and d.status ='").append(queryConditions.getStatus()).append("' \n");
        }
        if (!CollectionUtils.isEmpty((Collection)queryConditions.getOrgCodeList())) {
            whereSql.append("    and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)param.getQueryConditions().getOrgCodeList(), (String)"d.unitId"));
        }
        if ((count = this.count(sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ARCHIVE_INFO", (String)"d"), whereSql), args)) == 0) {
            return PageInfo.empty();
        }
        int pageNum = param.getPageNum();
        int pageSize = param.getPageSize();
        List ArchiveInfoEOS = this.selectEntityByPaging(sql, (pageNum - 1) * pageSize, pageNum * pageSize, args);
        return PageInfo.of((List)ArchiveInfoEOS, (int)count);
    }

    @Override
    public List<ArchiveInfoEO> queryAllArchiveInfoByConid(ArchiveQueryParam param) {
        String sql = "select %1$s \n     from GC_ARCHIVE_INFO d \n    where 1=1 \n    %2$s \n    order by d.CREATE_DATE desc \n";
        ArchiveContext queryConditions = param.getQueryConditions();
        ArrayList<String> args = new ArrayList<String>(8);
        StringBuffer whereSql = new StringBuffer();
        if (!StringUtils.isEmpty((String)queryConditions.getLogId())) {
            whereSql.append("    and d.LOGID = ? \n");
            args.add(queryConditions.getLogId());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getTaskId())) {
            whereSql.append("    and d.TASKID = ? \n");
            args.add(queryConditions.getTaskId());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getSchemeId())) {
            whereSql.append("    and d.SCHEMEID = ? \n");
            args.add(queryConditions.getSchemeId());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getStartPeriodString())) {
            whereSql.append("    and d.DEFAULT_PERIOD >= ? \n");
            args.add(queryConditions.getStartPeriodString());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getEndPeriodString())) {
            whereSql.append("    and d.DEFAULT_PERIOD <= ? \n");
            args.add(queryConditions.getEndPeriodString());
        }
        if (!StringUtils.isEmpty((String)queryConditions.getOrgCode())) {
            whereSql.append("    and d.unitId like '%").append(queryConditions.getOrgCode()).append("%' \n");
        }
        if (!StringUtils.isEmpty((String)queryConditions.getStatus())) {
            whereSql.append("    and d.status ='").append(queryConditions.getStatus()).append("' \n");
        }
        if (!CollectionUtils.isEmpty((Collection)queryConditions.getOrgCodeList())) {
            whereSql.append("    and ").append(SqlUtils.getConditionOfIdsUseOr((Collection)param.getQueryConditions().getOrgCodeList(), (String)"d.unitId"));
        }
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ARCHIVE_INFO", (String)"d"), whereSql);
        return this.selectEntity(sql, args);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        String sql = "delete from GC_ARCHIVE_INFO  where " + SqlUtils.getConditionOfIdsUseOr(ids, (String)"ID");
        this.execute(sql);
    }

    @Override
    public List<ArchiveInfoEO> queryArchiveInfoByIds(List<String> ids) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ArchiveInfoEO.class, (String)"e");
        String msql = "select %s from GC_ARCHIVE_INFO e  where  " + SqlUtils.getConditionOfIdsUseOr(ids, (String)"e.id");
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, new Object[0]);
        return eos;
    }

    @Override
    public List<ArchiveInfoEO> listInfoByLogIdAndStatus(String logId, ArchiveStatusEnum archiveStatusEnum) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ArchiveInfoEO.class, (String)"e");
        String msql = " select %s from GC_ARCHIVE_INFO e \n  where e.logId = ? \n   and  e.status = ? \n ";
        String sql = String.format(msql, columnSQL);
        List eos = this.selectEntity(sql, new Object[]{logId, archiveStatusEnum.getStatus()});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return eos;
    }
}

