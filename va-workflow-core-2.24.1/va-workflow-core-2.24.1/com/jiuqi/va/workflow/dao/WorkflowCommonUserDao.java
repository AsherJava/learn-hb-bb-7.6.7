/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowCommonUserDao
extends BaseOptMapper<WorkflowCommonUserDO> {
    @SelectProvider(type=WorkflowCommonUserSqlProvider.class, method="list")
    public List<WorkflowCommonUserDO> list(WorkflowCommonUserDTO var1);

    public static class WorkflowCommonUserSqlProvider {
        public String list(WorkflowCommonUserDTO workflowCommonUserDTO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("*")).FROM("WORKFLOW_COMMON_USER");
            if (StringUtils.hasText(workflowCommonUserDTO.getUserId())) {
                sql.WHERE("USERID = #{userId}");
            }
            if (StringUtils.hasText(workflowCommonUserDTO.getWorkflowDefineKey())) {
                sql.WHERE("WORKFLOWDEFINEKEY = #{workflowDefineKey}");
            }
            if (StringUtils.hasText(workflowCommonUserDTO.getNodeCode())) {
                sql.WHERE("NODECODE = #{nodeCode}");
            }
            if (StringUtils.hasText(workflowCommonUserDTO.getCommonUserId())) {
                sql.WHERE("COMMONUSERID = #{commonUserId}");
            }
            sql.ORDER_BY("CREATETIME DESC");
            return sql.toString();
        }
    }
}

