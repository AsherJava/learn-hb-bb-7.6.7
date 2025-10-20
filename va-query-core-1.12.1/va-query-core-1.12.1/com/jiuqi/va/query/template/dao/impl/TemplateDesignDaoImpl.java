/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.QueryTemplateDesignVO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.va.query.template.dao.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.template.dao.TemplateDesignDao;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.QueryTemplateDesignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class TemplateDesignDaoImpl
implements TemplateDesignDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public QueryTemplateDesignVO getTemplateDesignByTemplateId(String templateId) {
        String sql = " select id, designdata from DC_QUERY_TEMPLATE_DESIGN where id = ? ";
        return (QueryTemplateDesignVO)this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                QueryTemplateDesignVO template = new QueryTemplateDesignVO();
                template.setId(rs.getString(1));
                template.setDesignData(rs.getString(2));
                return template;
            }
            return null;
        }, new Object[]{templateId});
    }

    @Override
    public int updateTemplateDesign(QueryTemplate queryTemplate) {
        String sql = " update DC_QUERY_TEMPLATE_DESIGN set designdata = ? where id = ?";
        return this.jdbcTemplate.update(sql, new Object[]{JSONUtil.beanToJson((Object)queryTemplate), queryTemplate.getId()});
    }

    @Override
    public boolean existByID(String id) {
        if (!StringUtils.hasText(id)) {
            throw new DefinedQueryRuntimeException("\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String sql = "select id from DC_QUERY_TEMPLATE_DESIGN where id = ?";
        return !CollectionUtils.isEmpty(this.jdbcTemplate.queryForList(sql, new Object[]{id}));
    }

    @Override
    public int saveTemplateDesign(QueryTemplate queryTemplate) {
        String sql = " insert into DC_QUERY_TEMPLATE_DESIGN (id,designdata) values (?,?)";
        return this.jdbcTemplate.update(sql, new Object[]{queryTemplate.getId(), JSONUtil.beanToJson((Object)queryTemplate)});
    }

    @Override
    public int deleteById(String templateId) {
        if (!StringUtils.hasText(templateId)) {
            throw new DefinedQueryRuntimeException("\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String sql = "delete from DC_QUERY_TEMPLATE_DESIGN where id = ?";
        return this.jdbcTemplate.update(sql, new Object[]{templateId});
    }

    @Override
    public int saveTemplateDesignData(QueryTemplateDesignVO designVO) {
        String sql = " insert into DC_QUERY_TEMPLATE_DESIGN (id,designdata) values (?,?)";
        return this.jdbcTemplate.update(sql, new Object[]{designVO.getId(), designVO.getDesignData()});
    }
}

