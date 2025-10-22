/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO
 *  com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.nr.bpm.upload.UploadState
 */
package com.jiuqi.gcreport.analysisreport.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportDataDao;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqQueryAnalysisReportDatasDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportDataEO;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.nr.bpm.upload.UploadState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisReportDataDaoImpl
extends GcDbSqlGenericDAO<AnalysisReportDataEO, String>
implements AnalysisReportDataDao {
    public AnalysisReportDataDaoImpl() {
        super(AnalysisReportDataEO.class);
    }

    @Override
    public List<AnalysisReportDataEO> queryByIds(Set<String> analysisReportDataIds) {
        if (CollectionUtils.isEmpty(analysisReportDataIds)) {
            return Collections.emptyList();
        }
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_DATA" + " t  \n  where " + SqlUtils.getConditionOfMulStrUseOr(analysisReportDataIds, (String)"t.id");
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public boolean isExistsByTemplateIdAndDimValuesAndVersion(String templateId, String orgDimId, String dimensionsLikeCondition, String versionname) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_DATA" + "   t  \n  where  t.templateId = ? ");
        sqlBuilder.append(" and t.orgId = ?");
        sqlBuilder.append(" and t.dimensions = ?");
        sqlBuilder.append(" and t.versionname = ?");
        String querySql = sqlBuilder.toString();
        int count = this.count(querySql, new Object[]{templateId, orgDimId, dimensionsLikeCondition, versionname});
        return count != 0;
    }

    @Override
    public PageInfo<AnalysisReportDataEO> queryByTemplateIdAndLikeDimValues(String templateId, List<String> orgIds, String dimensionsLikeCondition, ReqQueryAnalysisReportDatasDTO queryAnalysisReportDatasDTO) {
        Integer pageSize = queryAnalysisReportDatasDTO.getPageSize();
        Integer pageNum = queryAnalysisReportDatasDTO.getPageNum();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_DATA" + "   t  \n  where  t.templateId = ? ");
        if (!StringUtils.isEmpty((String)dimensionsLikeCondition)) {
            sqlBuilder.append(" and t.dimensions like '%" + dimensionsLikeCondition + "%'");
        }
        if (Boolean.TRUE.equals(queryAnalysisReportDatasDTO.getOnlyShowConfirmVersion())) {
            sqlBuilder.append(" and t.flowstate = '" + UploadState.CONFIRMED.name() + "'");
        }
        if (!CollectionUtils.isEmpty(orgIds)) {
            sqlBuilder.append("and ").append(SqlUtils.getConditionOfMulStrUseOr(orgIds, (String)"t.orgId")).append("\n");
        }
        sqlBuilder.append("  order by t.sortorder desc\n");
        String querySql = sqlBuilder.toString();
        int count = this.count(querySql, new Object[]{templateId});
        if (count == 0) {
            return PageInfo.empty();
        }
        List dataEOs = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[]{templateId});
        if (CollectionUtils.isEmpty((Collection)dataEOs)) {
            return PageInfo.empty();
        }
        return PageInfo.of((List)dataEOs, (int)count);
    }

    @Override
    public List<AnalysisReportDataEO> queryByTemplateIdsAndLikeDimValuesAndVersionState(List<String> templateIds, List<String> orgIds, String dimensionsLikeCondition, AnalysisReportVersionState versionState) {
        ArrayList<String> groupFields = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(orgIds)) {
            groupFields.add("orgid");
        }
        if (AnalysisReportVersionState.LATEST_CONFIRMED.equals((Object)versionState)) {
            groupFields.add("flowstate");
        }
        String groupFieldsStr = groupFields.stream().map(s -> "," + s).collect(Collectors.joining());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t"));
        sqlBuilder.append("  from  GC_ANALYSISREPORT_DATA   t  \n");
        sqlBuilder.append("  join (select templateid ").append(groupFieldsStr).append(",max(updatetime) as max_updatetime,max(createtime) as max_createtime");
        String subwhereCondition = this.buildWhereCondition("", templateIds, orgIds, dimensionsLikeCondition, versionState);
        sqlBuilder.append(" from  GC_ANALYSISREPORT_DATA").append("  where  1=1 ").append(subwhereCondition).append(" group by templateid ").append(groupFieldsStr).append(")  t1  ");
        sqlBuilder.append("  on t.templateid = t1.templateid ");
        groupFields.stream().forEach(groupField -> sqlBuilder.append(" and t.").append((String)groupField).append(" = t1.").append((String)groupField));
        switch (versionState) {
            case LATEST_CONFIRMED: {
                sqlBuilder.append(" and t.updatetime = t1.max_updatetime ");
                break;
            }
            case LATEST_SAVED: {
                sqlBuilder.append(" and t.createtime = t1.max_createtime ");
                break;
            }
        }
        sqlBuilder.append("  where  1=1 ");
        String whereCondition = this.buildWhereCondition("t", templateIds, orgIds, dimensionsLikeCondition, versionState);
        sqlBuilder.append(whereCondition);
        sqlBuilder.append("  order by t.sortorder desc\n");
        String querySql = sqlBuilder.toString();
        List dataEOs = this.selectEntity(querySql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)dataEOs)) {
            return Collections.emptyList();
        }
        dataEOs.sort(Comparator.comparingInt(o -> templateIds.indexOf(o.getTemplateId())));
        return dataEOs;
    }

    private String buildWhereCondition(String tableAlias, List<String> templateIds, List<String> orgIds, String dimensionsLikeCondition, AnalysisReportVersionState versionState) {
        String tableAliasStr;
        StringBuilder whereSql = new StringBuilder();
        String string = tableAliasStr = StringUtils.isEmpty((String)tableAlias) ? "" : tableAlias + ".";
        if (!StringUtils.isEmpty((String)dimensionsLikeCondition)) {
            whereSql.append(" and " + tableAliasStr + "dimensions like '%" + dimensionsLikeCondition + "%'");
        }
        if (AnalysisReportVersionState.LATEST_CONFIRMED.equals((Object)versionState)) {
            whereSql.append(" and " + tableAliasStr + "flowstate = '" + UploadState.CONFIRMED.name() + "'");
        }
        if (!CollectionUtils.isEmpty(orgIds)) {
            whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(orgIds, (String)(tableAliasStr + "orgId")));
        }
        whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr(templateIds, (String)(tableAliasStr + "templateId")));
        return whereSql.toString();
    }

    @Override
    public AnalysisReportDataEO queryNextItemById(String currentId, String templateId, String dimensionsValue) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_DATA" + "   t  \n  where   t.templateId = ? \n     and sortorder < (select sortorder from " + "GC_ANALYSISREPORT_DATA" + " where id = ?) \n order by sortorder desc ";
        List previousNodes = this.selectEntityByPaging(sql, 0, 1, new Object[]{templateId, currentId});
        if (CollectionUtils.isEmpty((Collection)previousNodes)) {
            return null;
        }
        return (AnalysisReportDataEO)((Object)previousNodes.get(0));
    }

    @Override
    public AnalysisReportDataEO queryPreviousItemById(String currentId, String templateId, String dimensionsValue) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_DATA" + "   t  \n  where   t.templateId = ?\n     and sortorder > (select sortorder from " + "GC_ANALYSISREPORT_DATA" + " where id = ?) \n order by sortorder asc ";
        List nextNodes = this.selectEntityByPaging(sql, 0, 1, new Object[]{templateId, currentId});
        if (CollectionUtils.isEmpty((Collection)nextNodes)) {
            return null;
        }
        return (AnalysisReportDataEO)((Object)nextNodes.get(0));
    }

    @Override
    public Boolean updateAnalysisReportDataById(AnalysisReportDataEO analysisReportDataEO) {
        String sql = " update GC_ANALYSISREPORT_DATA t  set t.flowState = ? , t.updator = ? ,  t.updateTime = ? where t.id = ?";
        int result = this.execute(sql, new Object[]{analysisReportDataEO.getFlowState(), analysisReportDataEO.getUpdator(), analysisReportDataEO.getUpdateTime(), analysisReportDataEO.getId()});
        return result > 0;
    }

    @Override
    public AnalysisReportDataEO queryLatestConfirmedByTemplateId(String templateId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportDataEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT_DATA" + "   t  \n  where  t.templateId = ?      and t.flowstate = '" + UploadState.CONFIRMED.name() + "'     and t.updatetime = (select max(updatetime) as max_updatetime from " + "GC_ANALYSISREPORT_DATA" + " where templateId = ? and flowstate = '" + UploadState.CONFIRMED.name() + "') ";
        List dataEOS = this.selectEntityByPaging(sql, 0, 1, new Object[]{templateId, templateId});
        if (CollectionUtils.isEmpty((Collection)dataEOS)) {
            return null;
        }
        return (AnalysisReportDataEO)((Object)dataEOS.get(0));
    }
}

