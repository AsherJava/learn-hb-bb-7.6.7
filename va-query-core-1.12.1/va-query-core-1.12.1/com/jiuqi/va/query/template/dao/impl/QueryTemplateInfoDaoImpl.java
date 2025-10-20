/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.util.DCQuerySqlBuildUtil;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class QueryTemplateInfoDaoImpl
extends UserDefinedBaseDaoImpl
implements QueryTemplateInfoDao {
    @Autowired
    private DynamicDataSourceService dataSourceService;

    @Override
    public List<TemplateInfoVO> getTemplatesByGroupId(String groupId) {
        String sql = " select id,code,title,datasourceCode,queryType,sortOrder,description,columnLayout,conditionDisplay from DC_QUERY_TEMPLATE where groupId = ? order by sortOrder  ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, groupId), (rs, rowNum) -> {
            TemplateInfoVO template = new TemplateInfoVO();
            template.setGroupId(groupId);
            template.setId(rs.getString(1));
            template.setCode(rs.getString(2));
            template.setTitle(rs.getString(3));
            template.setDatasourceCode(rs.getString(4));
            template.setType(rs.getString(5));
            template.setSortOrder(Integer.valueOf(rs.getInt(6)));
            template.setDescription(rs.getString(7));
            template.setColumnLayout(Integer.valueOf(rs.getInt(8)));
            template.setConditionDisplay(rs.getString(9));
            return template;
        });
    }

    @Override
    public Boolean hasTemplatesByGroupId(String groupId) {
        String sql = " Select * from DC_QUERY_TEMPLATE where groupId = ? ";
        return (Boolean)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{groupId}, ResultSet::next);
    }

    @Override
    public void save(TemplateInfoVO templateInfo) {
        Object[] args = new Object[]{templateInfo.getId(), templateInfo.getCode(), templateInfo.getTitle(), templateInfo.getDatasourceCode(), templateInfo.getType(), templateInfo.getSortOrder(), templateInfo.getGroupId(), DCQueryStringHandle.isEmpty(templateInfo.getDescription()) ? "" : templateInfo.getDescription(), templateInfo.getColumnLayout(), templateInfo.getConditionDisplay()};
        String sql = "insert into dc_query_template \n  (id,code,title,datasourceCode,queryType,sortorder,groupId,description,columnLayout,conditionDisplay) \n  values (?,?,?,?,?,?,?,?,?,?) \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void deleteById(String templateId) {
        String sql = "delete from dc_query_template where id = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{templateId});
    }

    @Override
    public void update(TemplateInfoVO templateInfo) {
        Object[] args = new Object[]{templateInfo.getCode(), templateInfo.getTitle(), templateInfo.getDatasourceCode(), templateInfo.getType(), DCQueryStringHandle.isEmpty(templateInfo.getDescription()) ? "" : templateInfo.getDescription(), templateInfo.getColumnLayout(), templateInfo.getConditionDisplay(), templateInfo.getId()};
        String sql = "update dc_query_template \n   set code = ? , title = ? , datasourceCode = ? , queryType = ? , description = ? ,columnLayout = ? , conditionDisplay = ? \n where id = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void updateByCode(TemplateInfoVO templateInfo) {
        Object[] args = new Object[]{templateInfo.getTitle(), templateInfo.getDatasourceCode(), templateInfo.getType(), DCQueryStringHandle.isEmpty(templateInfo.getDescription()) ? "" : templateInfo.getDescription(), templateInfo.getColumnLayout(), templateInfo.getConditionDisplay(), templateInfo.getCode()};
        String sql = "update dc_query_template \n   set title = ? , datasourceCode = ? , queryType = ? , description = ? ,columnLayout = ? , conditionDisplay = ? \n where code = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public TemplateInfoVO getTemplatesById(String templateId) {
        String sql = " select code,title,datasourceCode,queryType,sortOrder,groupId,description,columnLayout,conditionDisplay from DC_QUERY_TEMPLATE where id = ? ";
        return (TemplateInfoVO)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{templateId}, rs -> {
            if (rs.next()) {
                TemplateInfoVO template = new TemplateInfoVO();
                template.setId(templateId);
                template.setCode(rs.getString(1));
                template.setTitle(rs.getString(2));
                template.setDatasourceCode(rs.getString(3));
                template.setType(rs.getString(4));
                template.setSortOrder(Integer.valueOf(rs.getInt(5)));
                template.setGroupId(rs.getString(6));
                template.setDescription(rs.getString(7));
                template.setColumnLayout(Integer.valueOf(rs.getInt(8)));
                template.setConditionDisplay(rs.getString(9));
                return template;
            }
            return null;
        });
    }

    @Override
    public Boolean hasTemplatesById(String id) {
        String sql = " Select * from DC_QUERY_TEMPLATE where id = ? ";
        return (Boolean)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{id}, ResultSet::next);
    }

    @Override
    public List<TemplateInfoVO> getAllTemplates() {
        String sql = "select id,code,title,datasourceCode,querytype,sortOrder,description,groupId,columnLayout,conditionDisplay from DC_QUERY_TEMPLATE order by sortOrder ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), (rs, rowNum) -> {
            TemplateInfoVO template = new TemplateInfoVO();
            template.setId(rs.getString(1));
            template.setCode(rs.getString(2));
            template.setTitle(rs.getString(3));
            template.setDatasourceCode(rs.getString(4));
            template.setType(rs.getString(5));
            template.setSortOrder(Integer.valueOf(rs.getInt(6)));
            template.setDescription(rs.getString(7));
            template.setGroupId(rs.getString(8));
            template.setColumnLayout(Integer.valueOf(rs.getInt(9)));
            template.setConditionDisplay(rs.getString(10));
            return template;
        });
    }

    @Override
    public TemplateInfoVO getTemplatesByCode(String code) {
        String sql = " select id,title,datasourceCode,queryType,sortOrder,groupId,description from DC_QUERY_TEMPLATE where code = ? ";
        return (TemplateInfoVO)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{code}, rs -> {
            if (rs.next()) {
                TemplateInfoVO template = new TemplateInfoVO();
                template.setCode(code);
                template.setId(rs.getString(1));
                template.setTitle(rs.getString(2));
                template.setDatasourceCode(rs.getString(3));
                template.setType(rs.getString(4));
                template.setSortOrder(Integer.valueOf(rs.getInt(5)));
                template.setGroupId(rs.getString(6));
                template.setDescription(rs.getString(7));
                return template;
            }
            return null;
        });
    }

    @Override
    public List<TemplateInfoVO> getTemplatesByDataSourceCode(List<String> codes) {
        String sql = "select id,code,title,datasourceCode,querytype,sortOrder,description,groupId from DC_QUERY_TEMPLATE where 1=1 %1$s order by sortOrder ";
        String whereSql = " and " + DCQuerySqlBuildUtil.getStrInCondi("datasourceCode", codes.size());
        String formatSql = String.format(sql, whereSql);
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(formatSql), ps -> {
            int count = codes.size();
            for (int i = 1; i <= count; ++i) {
                ps.setString(i, (String)codes.get(i - 1));
            }
        }, (rs, rowNum) -> {
            TemplateInfoVO template = new TemplateInfoVO();
            template.setId(rs.getString(1));
            template.setCode(rs.getString(2));
            template.setTitle(rs.getString(3));
            template.setDatasourceCode(rs.getString(4));
            template.setType(rs.getString(5));
            template.setSortOrder(Integer.valueOf(rs.getInt(6)));
            template.setDescription(rs.getString(7));
            template.setGroupId(rs.getString(8));
            return template;
        });
    }

    @Override
    public void updateGroupIdAndSortOrderByTemplateId(String templateId, String groupId, int sortOrder) {
        Object[] args = new Object[]{groupId, sortOrder, templateId};
        String sql = "update dc_query_template  set groupId = ? , sortOrder = ?  where id = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public int getMaxSortOrderByGroupId(String groupId) {
        String sql = " select sortOrder from dc_query_template where groupId = ? order by sortOrder desc ";
        return (Integer)this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, groupId), rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }
}

