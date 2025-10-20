/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.template.dao.TemplateInfoDao;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TemplateInfoDaoImpl
implements TemplateInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TemplateInfoVO getTemplateInfo(String templateId) {
        String sql = " select code,title,datasourceCode,queryType,sortOrder,groupId,description,columnLayout,conditionDisplay from DC_QUERY_TEMPLATE where id = ? ";
        return (TemplateInfoVO)this.jdbcTemplate.query(sql, rs -> {
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
                return template;
            }
            return null;
        }, new Object[]{templateId});
    }

    @Override
    public TemplateInfoVO getTemplateInfoByCode(String code) {
        String sql = " select id,title,datasourceCode,queryType,sortOrder,groupId,description,columnLayout,conditionDisplay from DC_QUERY_TEMPLATE where code = ? ";
        return (TemplateInfoVO)this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                TemplateInfoVO template = new TemplateInfoVO();
                template.setId(rs.getString(1));
                template.setCode(code);
                template.setTitle(rs.getString(2));
                template.setDatasourceCode(rs.getString(3));
                template.setType(rs.getString(4));
                template.setSortOrder(Integer.valueOf(rs.getInt(5)));
                template.setGroupId(rs.getString(6));
                template.setDescription(rs.getString(7));
                return template;
            }
            return null;
        }, new Object[]{code});
    }

    @Override
    public int updateTemplateInfo(TemplateInfoVO baseInfo) {
        Object[] args = new Object[]{baseInfo.getCode(), baseInfo.getTitle(), baseInfo.getDatasourceCode(), baseInfo.getType(), DCQueryStringHandle.isEmpty(baseInfo.getDescription()) ? "" : baseInfo.getDescription(), baseInfo.getId()};
        String sql = "update dc_query_template \n   set code = ? , title = ? , datasourceCode = ? , queryType = ? , description = ? \n where id = ? \n";
        return this.jdbcTemplate.update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public int saveTemplateInfo(TemplateInfoVO baseInfo) {
        Object[] args = new Object[]{baseInfo.getId(), baseInfo.getCode(), baseInfo.getTitle(), baseInfo.getDatasourceCode(), baseInfo.getType(), baseInfo.getSortOrder() == null ? Integer.MAX_VALUE : baseInfo.getSortOrder(), baseInfo.getGroupId(), DCQueryStringHandle.isEmpty(baseInfo.getDescription()) ? "" : baseInfo.getDescription()};
        String sql = "insert into dc_query_template \n  (id,code,title,datasourceCode,queryType,sortorder,groupId,description) \n  values (?,?,?,?,?,?,?,?) \n";
        return this.jdbcTemplate.update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public int deleteById(String templateId) {
        if (!StringUtils.hasText(templateId)) {
            throw new DefinedQueryRuntimeException("\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String sql = "delete from dc_query_template where id = ?";
        return this.jdbcTemplate.update(sql, new Object[]{templateId});
    }
}

