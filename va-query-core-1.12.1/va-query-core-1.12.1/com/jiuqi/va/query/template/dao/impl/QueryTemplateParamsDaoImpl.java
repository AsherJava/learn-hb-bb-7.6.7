/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.template.dao.QueryTemplateParamsDao;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
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
public class QueryTemplateParamsDaoImpl
extends UserDefinedBaseDaoImpl
implements QueryTemplateParamsDao {
    @Override
    public void deleteByTemplateId(String templateId) {
        String sql = "delete from dc_query_template_params where templateId = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{templateId});
    }

    @Override
    public void batchSave(final List<TemplateParamsVO> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        String sql = "insert into dc_query_template_params \n  (id,templateId,name,title,refColumnName,paramType,queryMode,mustInput,refTableName,defaultValue,sortOrder,sequenceSource,minInt,maxInt,enableAuth,visibleFlag,filterCondition,unitCodeFlag,modeOperator,foldFlag) \n  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n";
        this.getJdbcTemplate().batchUpdate(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TemplateParamsVO param = (TemplateParamsVO)params.get(i);
                ps.setString(1, DCQueryStringHandle.isEmpty(param.getId()) ? DCQueryUUIDUtil.getUUIDStr() : param.getId());
                ps.setString(2, param.getTemplateId());
                ps.setString(3, param.getName());
                ps.setString(4, DCQueryStringHandle.isEmpty(param.getTitle()) ? "" : param.getTitle());
                ps.setString(5, DCQueryStringHandle.isEmpty(param.getRefColumnName()) ? " " : param.getRefColumnName());
                ps.setString(6, param.getParamType());
                ps.setString(7, param.getMode());
                ps.setInt(8, param.isMustInput() ? 1 : 0);
                ps.setString(9, param.getRefTableName());
                ps.setString(10, param.getDefaultValue());
                ps.setInt(11, i);
                ps.setString(12, param.getSource());
                if (param.getMinInt() != null) {
                    ps.setInt(13, param.getMinInt());
                } else {
                    ps.setNull(13, 4);
                }
                if (param.getMinInt() != null) {
                    ps.setInt(14, param.getMaxInt());
                } else {
                    ps.setNull(14, 4);
                }
                ps.setInt(15, Boolean.TRUE.equals(param.getEnableAuth()) ? 1 : 0);
                ps.setInt(16, Boolean.TRUE.equals(param.getVisibleFlag()) ? 1 : 0);
                ps.setString(17, StringUtils.hasLength(param.getFilterCondition()) ? param.getFilterCondition() : "");
                ps.setInt(18, Boolean.TRUE.equals(param.getUnitCodeFlag()) ? 1 : 0);
                ps.setString(19, param.getModeOperator());
                ps.setInt(20, Boolean.TRUE.equals(param.getFoldFlag()) ? 1 : 0);
            }

            public int getBatchSize() {
                return params.size();
            }
        });
    }

    @Override
    public void batchUpdate(final List<TemplateParamsVO> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        String sql = "update dc_query_template_params \n   set name = ? , title = ? , refColumnName = ? , paramType = ? , queryMode = ? , \n       mustInput = ? ,  refTableName = ? , defaultValue = ? , sortOrder = ? , \n       sequenceSource = ? ,  maxInt = ? , minInt = ? , enableAuth = ? , visibleFlag = ? , filterCondition = ? , \n        unitCodeFlag = ?, modeOperator = ? , foldFlag = ? \n where id = ? \n";
        this.getJdbcTemplate().batchUpdate(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TemplateParamsVO param = (TemplateParamsVO)params.get(i);
                ps.setString(1, param.getName());
                ps.setString(2, param.getTitle());
                ps.setString(3, param.getRefColumnName());
                ps.setString(4, param.getParamType());
                ps.setString(5, param.getMode());
                ps.setInt(6, param.isMustInput() ? 1 : 0);
                ps.setString(7, param.getRefTableName());
                ps.setString(8, param.getDefaultValue());
                ps.setInt(9, param.getSortOrder());
                ps.setString(10, param.getSource());
                if (param.getMinInt() != null) {
                    ps.setInt(11, param.getMinInt());
                } else {
                    ps.setNull(11, 4);
                }
                if (param.getMinInt() != null) {
                    ps.setInt(12, param.getMaxInt());
                } else {
                    ps.setNull(12, 4);
                }
                ps.setInt(13, Boolean.TRUE.equals(param.getEnableAuth()) ? 1 : 0);
                ps.setInt(14, Boolean.TRUE.equals(param.getVisibleFlag()) ? 1 : 0);
                ps.setString(15, param.getFilterCondition());
                ps.setInt(16, Boolean.TRUE.equals(param.getUnitCodeFlag()) ? 1 : 0);
                ps.setString(17, param.getModeOperator());
                ps.setInt(18, Boolean.TRUE.equals(param.getFoldFlag()) ? 1 : 0);
                ps.setString(19, param.getId());
            }

            public int getBatchSize() {
                return params.size();
            }
        });
    }

    @Override
    public void save(TemplateParamsVO paramsVO) {
        String sql = "insert into dc_query_template_params \n  (id,templateId,name,title,refColumnName,paramType,queryMode,mustInput,refTableName,defaultValue,sortOrder,sequenceSource,minInt,maxInt,enableAuth,visibleFlag,filterCondition,unitCodeFlag,modeOperator,foldFlag) \n  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> {
            ps.setString(1, DCQueryStringHandle.isEmpty(paramsVO.getId()) ? DCQueryUUIDUtil.getUUIDStr() : paramsVO.getId());
            ps.setString(2, paramsVO.getTemplateId());
            ps.setString(3, paramsVO.getName());
            ps.setString(4, DCQueryStringHandle.isEmpty(paramsVO.getTitle()) ? "" : paramsVO.getTitle());
            ps.setString(5, DCQueryStringHandle.isEmpty(paramsVO.getRefColumnName()) ? " " : paramsVO.getRefColumnName());
            ps.setString(6, paramsVO.getParamType());
            ps.setString(7, paramsVO.getMode());
            ps.setInt(8, paramsVO.isMustInput() ? 1 : 0);
            ps.setString(9, paramsVO.getRefTableName());
            ps.setString(10, paramsVO.getDefaultValue());
            ps.setInt(11, paramsVO.getSortOrder());
            ps.setString(12, paramsVO.getSource());
            if (paramsVO.getMinInt() != null) {
                ps.setInt(13, paramsVO.getMinInt());
            } else {
                ps.setNull(13, 4);
            }
            if (paramsVO.getMinInt() != null) {
                ps.setInt(14, paramsVO.getMaxInt());
            } else {
                ps.setNull(14, 4);
            }
            ps.setInt(15, Boolean.TRUE.equals(paramsVO.getEnableAuth()) ? 1 : 0);
            ps.setInt(16, Boolean.TRUE.equals(paramsVO.getVisibleFlag()) ? 1 : 0);
            ps.setString(17, StringUtils.hasLength(paramsVO.getFilterCondition()) ? paramsVO.getFilterCondition() : "");
            ps.setInt(18, Boolean.TRUE.equals(paramsVO.getUnitCodeFlag()) ? 1 : 0);
            ps.setString(19, paramsVO.getModeOperator());
            ps.setInt(20, Boolean.TRUE.equals(paramsVO.getFoldFlag()) ? 1 : 0);
        });
    }

    @Override
    public List<TemplateParamsVO> getByTemplateId(final String templateId) {
        String sql = "select id,name,title,refColumnName,paramType,queryMode,mustInput,refTableName,defaultValue,sortOrder,sequenceSource,maxInt,minInt,enableAuth,visibleFlag,filterCondition,unitCodeFlag,modeOperator,foldFlag from dc_query_template_params where templateId = ? order by sortOrder  ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, templateId), (RowMapper)new RowMapper<TemplateParamsVO>(){

            public TemplateParamsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TemplateParamsVO params = new TemplateParamsVO();
                params.setTemplateId(templateId);
                params.setId(rs.getString(1));
                params.setName(rs.getString(2));
                params.setTitle(rs.getString(3));
                params.setRefColumnName(rs.getString(4));
                params.setParamType(rs.getString(5));
                params.setMode(rs.getString(6));
                params.setMustInput(rs.getInt(7) == 1);
                params.setRefTableName(rs.getString(8));
                params.setDefaultValue(rs.getString(9));
                params.setSortOrder(Integer.valueOf(rs.getInt(10)));
                params.setSource(rs.getString(11));
                params.setMaxInt(rs.getObject(12) != null ? Integer.valueOf(rs.getInt(12)) : null);
                params.setMinInt(rs.getObject(13) != null ? Integer.valueOf(rs.getInt(13)) : null);
                params.setEnableAuth(rs.getObject(14) != null ? Boolean.valueOf(rs.getInt(14) == 1) : null);
                params.setVisibleFlag(rs.getObject(15) != null ? Boolean.valueOf(rs.getInt(15) == 1) : null);
                params.setFilterCondition(rs.getString(16));
                params.setUnitCodeFlag(Boolean.valueOf(rs.getInt(17) == 1));
                params.setModeOperator(rs.getString(18));
                params.setFoldFlag(Boolean.valueOf(rs.getInt(19) == 1));
                return params;
            }
        });
    }

    @Override
    public List<FetchQueryFiledVO> getSimpleTemplateParamsByTemplateCode(String templateCode) {
        String sql = "select name,title,mustInput from dc_query_template_params where templateId = (select id from dc_query_template where code = ?) order by sortOrder  ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, templateCode), (rs, rowNum) -> {
            FetchQueryFiledVO params = new FetchQueryFiledVO();
            params.setName(rs.getString(1));
            params.setTitle(rs.getString(2));
            params.setNeedFlag(rs.getInt(3) == 1);
            return params;
        });
    }
}

