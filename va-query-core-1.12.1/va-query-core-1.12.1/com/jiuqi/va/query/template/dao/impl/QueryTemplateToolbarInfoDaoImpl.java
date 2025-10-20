/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.template.dao.QueryTemplateToolbarInfoDao;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.util.DCQueryClobUtil;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@Deprecated
public class QueryTemplateToolbarInfoDaoImpl
extends UserDefinedBaseDaoImpl
implements QueryTemplateToolbarInfoDao {
    @Override
    public void save(TemplateToolbarInfoVO toolbarInfoVO) {
        String sql = "insert into dc_query_template_toolbar \n  (id,templateid,operation,title,sortorder,config) \n  values (?,?,?,?,?,?) \n";
        Object[] args = new Object[]{toolbarInfoVO.getId(), toolbarInfoVO.getTemplateId(), toolbarInfoVO.getAction(), toolbarInfoVO.getTitle(), toolbarInfoVO.getSortOrder(), toolbarInfoVO.getConfig()};
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void deleteByTemplateId(String templateId) {
        String sql = "delete from dc_query_template_toolbar where templateId = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{templateId});
    }

    @Override
    public List<TemplateToolbarInfoVO> getByTemplateId(final String templateId) {
        String sql = " select id,operation,title,sortorder,config from dc_query_template_toolbar where templateId = ? order by sortorder";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, templateId), (RowMapper)new RowMapper<TemplateToolbarInfoVO>(){

            public TemplateToolbarInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TemplateToolbarInfoVO toolbarInfo = new TemplateToolbarInfoVO();
                toolbarInfo.setTemplateId(templateId);
                toolbarInfo.setId(rs.getString(1));
                toolbarInfo.setAction(rs.getString(2));
                toolbarInfo.setTitle(rs.getString(3));
                toolbarInfo.setSortOrder(Integer.valueOf(rs.getInt(4)));
                toolbarInfo.setConfig(DCQueryClobUtil.clobToString(rs.getClob(5)));
                return toolbarInfo;
            }
        });
    }

    @Override
    public void batchSave(final List<TemplateToolbarInfoVO> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        String sql = "insert into dc_query_template_toolbar \n  (id,templateid,operation,title,sortorder,config) \n  values (?,?,?,?,?,?) \n";
        this.getJdbcTemplate().batchUpdate(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TemplateToolbarInfoVO param = (TemplateToolbarInfoVO)params.get(i);
                ps.setString(1, DCQueryStringHandle.isEmpty(param.getId()) ? DCQueryUUIDUtil.getUUIDStr() : param.getId());
                ps.setString(2, param.getTemplateId());
                ps.setString(3, param.getAction());
                ps.setString(4, param.getTitle());
                ps.setInt(5, param.getSortOrder());
                String config = StringUtils.hasText(param.getConfig()) ? param.getConfig() : "";
                StringReader reader = new StringReader(config);
                ps.setClob(6, reader);
            }

            public int getBatchSize() {
                return params.size();
            }
        });
    }
}

