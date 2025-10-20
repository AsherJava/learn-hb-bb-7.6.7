/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.template.dao.QueryTemplateRelateQueryDao;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class QueryTemplateRelateQueryDaoInfo
extends UserDefinedBaseDaoImpl
implements QueryTemplateRelateQueryDao {
    @Override
    public List<TemplateRelateQueryVO> getRelateQueryByTemplateId(final String templateId) {
        String sql = "select id,processorName,processorConfig,triggerField,description,sortOrder,queryParam,openType from dc_query_template_relatequery where templateId = ? order by sortOrder  ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, templateId), (RowMapper)new RowMapper<TemplateRelateQueryVO>(){

            public TemplateRelateQueryVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TemplateRelateQueryVO params = new TemplateRelateQueryVO();
                params.setTemplateId(templateId);
                params.setId(rs.getString(1));
                params.setProcessorName(rs.getString(2));
                params.setProcessorConfig(rs.getString(3));
                params.setTriggerField(rs.getString(4));
                params.setDescription(rs.getString(5));
                params.setSortOrder(Integer.valueOf(rs.getInt(6)));
                params.setQueryParam(rs.getString(7));
                params.setOpenType(rs.getString(8));
                return params;
            }
        });
    }

    @Override
    public void deleteByTemplateId(String templateId) {
        String sql = "delete from dc_query_template_relatequery where templateId = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{templateId});
    }

    @Override
    public void batchSave(final List<TemplateRelateQueryVO> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        String sql = "insert into dc_query_template_relatequery \n  (id,templateId,processorName,processorConfig,triggerField,description,sortOrder,queryParam,openType) \n  values (?,?,?,?,?,?,?,?,?) \n";
        this.getJdbcTemplate().batchUpdate(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TemplateRelateQueryVO param = (TemplateRelateQueryVO)params.get(i);
                ps.setString(1, DCQueryStringHandle.isEmpty(param.getId()) ? DCQueryUUIDUtil.getUUIDStr() : param.getId());
                ps.setString(2, param.getTemplateId());
                ps.setString(3, param.getProcessorName());
                ps.setString(4, param.getProcessorConfig());
                ps.setString(5, param.getTriggerField());
                ps.setString(6, param.getDescription());
                ps.setInt(7, param.getSortOrder());
                ps.setString(8, param.getQueryParam());
                ps.setString(9, param.getOpenType());
            }

            public int getBatchSize() {
                return params.size();
            }
        });
    }
}

