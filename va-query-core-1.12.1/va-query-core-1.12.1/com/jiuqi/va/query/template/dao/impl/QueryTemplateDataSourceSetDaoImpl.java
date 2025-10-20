/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.template.dao.QueryTemplateDataSourceSetDao;
import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;
import com.jiuqi.va.query.util.DCQueryClobUtil;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.io.StringReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QueryTemplateDataSourceSetDaoImpl
extends UserDefinedBaseDaoImpl
implements QueryTemplateDataSourceSetDao {
    @Autowired
    private DynamicDataSourceService dataSourceService;

    @Override
    public void deleteByTemplateId(String templateId) {
        String sql = "delete from dc_query_template_sql where templateId = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{templateId});
    }

    @Override
    public void save(TemplateDataSourceSetVO dataSourceSet) {
        String sql = "insert into dc_query_template_sql \n  (id,templateId,defineSql) \n  values (?,?,?) \n";
        Object[] args = new Object[]{DCQueryUUIDUtil.getUUIDStr(), dataSourceSet.getTemplateId(), dataSourceSet.getDefineSql()};
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void update(TemplateDataSourceSetVO dataSourceSet) {
        String sql = "update dc_query_template_sql \n   set defineSql = ? \n where id = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> {
            StringReader reader = new StringReader(dataSourceSet.getDefineSql());
            ps.setClob(1, reader);
            ps.setString(2, dataSourceSet.getId());
        });
    }

    @Override
    public TemplateDataSourceSetVO getByTemplateId(String templateId) {
        String sql = " select id,defineSql from dc_query_template_sql where templateId = ? ";
        return (TemplateDataSourceSetVO)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{templateId}, rs -> {
            if (rs.next()) {
                TemplateDataSourceSetVO dataSourceSetVO = new TemplateDataSourceSetVO();
                dataSourceSetVO.setTemplateId(templateId);
                dataSourceSetVO.setId(rs.getString(1));
                dataSourceSetVO.setDefineSql(DCQueryClobUtil.clobToString(rs.getClob(2)));
                return dataSourceSetVO;
            }
            return null;
        });
    }
}

