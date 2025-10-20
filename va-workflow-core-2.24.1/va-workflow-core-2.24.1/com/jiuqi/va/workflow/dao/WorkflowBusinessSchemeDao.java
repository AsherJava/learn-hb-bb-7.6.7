/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDO
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDO;
import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.util.StringUtils;

@Mapper
public interface WorkflowBusinessSchemeDao
extends BaseOptMapper<WorkflowBusinessSchemeDO> {
    @SelectProvider(method="list", type=WorkflowBusinessSchemeDaoProvider.class)
    public List<WorkflowBusinessSchemeDTO> list(WorkflowBusinessSchemeDTO var1);

    public static class WorkflowBusinessSchemeDaoProvider {
        public String list(WorkflowBusinessSchemeDTO schemeDTO) {
            String orderfield;
            SQL sql = new SQL();
            boolean searchdata = schemeDTO.isSearchdata();
            if (searchdata) {
                sql.SELECT("t1.*, t2.schemedata");
                sql.FROM("workflow_business_scheme t1");
                sql.LEFT_OUTER_JOIN("workflow_business_scheme_data t2 on t1.id = t2.id");
            } else {
                sql.SELECT("t1.*");
                sql.FROM("workflow_business_scheme t1");
            }
            if (StringUtils.hasText((String)schemeDTO.getId())) {
                sql.WHERE("t1.id = #{id}");
            }
            if (StringUtils.hasText((String)schemeDTO.getSchemetype())) {
                sql.WHERE("t1.schemetype = #{schemetype}");
            }
            if (StringUtils.hasText((String)schemeDTO.getBusinesscode())) {
                sql.WHERE("t1.businesscode = #{businesscode}");
            }
            if (StringUtils.hasText((String)schemeDTO.getName())) {
                sql.WHERE("t1.name = #{name}");
            }
            if (StringUtils.hasText((String)(orderfield = schemeDTO.getOrderfield()))) {
                sql.ORDER_BY("t1." + orderfield);
            } else {
                sql.ORDER_BY("t1.modifytime");
            }
            String sqlStr = sql.toString();
            String ordertype = schemeDTO.getOrdertype();
            if (WorkflowOption.OrderType.DESC.name().equals(ordertype)) {
                sqlStr = sqlStr + " desc";
            }
            return sqlStr;
        }
    }
}

