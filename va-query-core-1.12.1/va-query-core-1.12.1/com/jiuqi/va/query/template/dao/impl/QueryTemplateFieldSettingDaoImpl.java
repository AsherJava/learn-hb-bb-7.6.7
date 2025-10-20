/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.template.dao.QueryTemplateFieldSettingDao;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class QueryTemplateFieldSettingDaoImpl
extends UserDefinedBaseDaoImpl
implements QueryTemplateFieldSettingDao {
    public static final String ADAPT = "adapt";

    @Override
    public void deleteByTemplateId(String templateId) {
        String sql = "delete from dc_query_template_fieldsetting where templateId = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{templateId});
    }

    @Override
    public void batchSave(final List<TemplateFieldSettingVO> fields) {
        if (fields == null || fields.isEmpty()) {
            return;
        }
        String sql = "insert into dc_query_template_fieldsetting \n  (id,templateId,name,title,dataType,width,autoWidth,gatherType,visibleFlag,SORTORDER,align,decimalLength,displayFormat,baseDataTable) \n  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n";
        this.getJdbcTemplate().batchUpdate(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TemplateFieldSettingVO field = (TemplateFieldSettingVO)fields.get(i);
                ps.setString(1, DCQueryStringHandle.isEmpty(field.getId()) ? DCQueryUUIDUtil.getUUIDStr() : field.getId());
                ps.setString(2, field.getTemplateId());
                ps.setString(3, field.getName());
                ps.setString(4, DCQueryStringHandle.isEmpty(field.getTitle()) ? "" : field.getTitle());
                ps.setString(5, field.getDataType());
                ps.setString(6, field.getWidth());
                ps.setInt(7, QueryTemplateFieldSettingDaoImpl.ADAPT.equals(field.getAutoWidth()) ? 1 : 0);
                ps.setString(8, field.getGatherType());
                ps.setInt(9, field.isVisibleFlag() ? 1 : 0);
                ps.setInt(10, field.getSortOrder());
                ps.setString(11, field.getAlign());
                ps.setObject(12, field.getDecimalLength());
                ps.setString(13, field.getDisplayFormat());
                ps.setString(14, field.getBaseDataTable());
            }

            public int getBatchSize() {
                return fields.size();
            }
        });
    }

    @Override
    public void batchUpdate(final List<TemplateFieldSettingVO> fields) {
        if (fields == null || fields.isEmpty()) {
            return;
        }
        String sql = "update dc_query_template_fieldsetting \n   set name = ? , title = ?, dataType = ?, width = ? , autoWidth = ? \n       gatherType = ? , visibleFlag = ?, SORTORDER = ?, align = ?, \n       decimalLength = ? , displayFormat = ? , baseDataTable = ? \n where id = ? \n";
        this.getJdbcTemplate().batchUpdate(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TemplateFieldSettingVO field = (TemplateFieldSettingVO)fields.get(i);
                ps.setString(1, field.getName());
                ps.setString(2, field.getTitle());
                ps.setString(3, field.getDataType());
                ps.setString(4, field.getWidth());
                ps.setInt(5, QueryTemplateFieldSettingDaoImpl.ADAPT.equals(field.getAutoWidth()) ? 1 : 0);
                ps.setString(6, field.getGatherType());
                ps.setInt(7, field.isVisibleFlag() ? 1 : 0);
                ps.setInt(8, field.getSortOrder());
                ps.setString(9, field.getId());
                ps.setString(10, field.getAlign());
                ps.setObject(11, field.getDecimalLength());
                ps.setString(12, field.getDisplayFormat());
                ps.setString(13, field.getBaseDataTable());
            }

            public int getBatchSize() {
                return fields.size();
            }
        });
    }

    @Override
    public List<TemplateFieldSettingVO> getByTemplateId(String templateId) {
        String sql = "select id,name,title,dataType,width,autoWidth,gatherType,visibleFlag,sortOrder,align,decimalLength,displayFormat,baseDataTable from dc_query_template_fieldsetting where templateId = ? order by sortOrder  ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, templateId), (rs, rowNum) -> {
            TemplateFieldSettingVO field = new TemplateFieldSettingVO();
            field.setTemplateId(templateId);
            field.setId(rs.getString(1));
            field.setName(rs.getString(2));
            field.setTitle(rs.getString(3));
            field.setDataType(rs.getString(4));
            field.setWidth(rs.getString(5));
            field.setAutoWidth(rs.getInt(6) == 1 ? ADAPT : "regular");
            field.setGatherType(rs.getString(7));
            field.setVisibleFlag(rs.getInt(8) == 1);
            field.setSortOrder(rs.getInt(9));
            field.setAlign(rs.getString(10));
            field.setDecimalLength(rs.getObject(11) == null ? null : Integer.valueOf(rs.getInt(11)));
            field.setDisplayFormat(rs.getString(12));
            field.setBaseDataTable(rs.getString(13));
            return field;
        });
    }

    @Override
    public List<FetchQueryFiledVO> getSimpleTemplateFieldsByTemplateCode(String templateCode) {
        String sql = "select name,title,visibleFlag from dc_query_template_fieldsetting where templateId = (select id from dc_query_template where code = ?)  order by sortOrder  ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, templateCode), (rs, rowNum) -> {
            FetchQueryFiledVO field = new FetchQueryFiledVO();
            field.setName(rs.getString(1));
            field.setTitle(rs.getString(2));
            field.setNeedFlag(rs.getInt(3) == 1);
            return field;
        });
    }
}

