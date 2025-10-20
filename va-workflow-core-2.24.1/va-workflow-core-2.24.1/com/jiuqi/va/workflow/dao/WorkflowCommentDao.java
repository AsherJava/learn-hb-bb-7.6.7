/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowCommentDao
extends BaseOptMapper<WorkflowCommentDO> {
    @SelectProvider(method="getComments", type=WorkflowCommentDaoProvider.class)
    public List<String> getComments(WorkflowCommentDTO var1);

    @SelectProvider(method="getCommentsByUsername", type=WorkflowCommentDaoProvider.class)
    public List<WorkflowCommentDO> getCommentsByUsername(WorkflowCommentDTO var1);

    @Delete(value={"delete from workflow_comment where username = #{username} and commoncomment = #{comment}"})
    public int deleteComment(WorkflowCommentDTO var1);

    @Delete(value={"delete from workflow_comment where id = #{id}"})
    public int deleteCommentById(WorkflowCommentDTO var1);

    public static class WorkflowCommentDaoProvider {
        public String getComments(WorkflowCommentDTO wc) {
            SQL sql = new SQL();
            sql.SELECT("commoncomment");
            sql.FROM("WORKFLOW_COMMENT");
            if (StringUtils.hasText(wc.getUsername())) {
                sql.WHERE("username = #{username}");
            }
            if (StringUtils.hasText(wc.getComment())) {
                sql.WHERE("commoncomment = #{comment}");
            }
            sql.ORDER_BY("createtime desc");
            return sql.toString();
        }

        public String getCommentsByUsername(WorkflowCommentDTO wc) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("WORKFLOW_COMMENT");
            if (StringUtils.hasText(wc.getUsername())) {
                sql.WHERE("username = #{username}");
            }
            sql.ORDER_BY("createtime desc");
            return sql.toString();
        }
    }
}

