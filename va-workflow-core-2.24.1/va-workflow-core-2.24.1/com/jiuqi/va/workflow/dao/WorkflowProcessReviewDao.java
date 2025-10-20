/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.jdbc.SQL
 *  tk.mybatis.mapper.common.BaseMapper
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDO;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface WorkflowProcessReviewDao
extends BaseMapper<WorkflowProcessReviewDO> {
    @SelectProvider(method="queryReviewInfos", type=WorkflowProcessReviewDaoProvider.class)
    public List<WorkflowProcessReviewDTO> queryReviewInfos(WorkflowProcessReviewDO var1);

    @Update(value={"UPDATE WORKFLOW_PROCESS_REVIEW\nSET id = #{id}, biztype = #{biztype}, bizcode = #{bizcode}, username = #{username}, modifytime = #{modifytime}, rejectprocessid = #{rejectprocessid}, rejectnodeid = #{rejectnodeid}, rejectednodeid = #{rejectednodeid}, reviewinfo = #{reviewinfo}\nWHERE id = #{id}"})
    public void update(WorkflowProcessReviewDO var1);

    public static class WorkflowProcessReviewDaoProvider {
        public String queryReviewInfos(WorkflowProcessReviewDO workflowProcessReviewDO) {
            SQL sql = new SQL();
            sql.SELECT("review.*, node.completecomment as rejectcomment, node.completetime as rejecttime, node.completeusername as rejectusername, node.processnodename as rejectnodename");
            sql.FROM("WORKFLOW_PROCESS_REVIEW review");
            sql.LEFT_OUTER_JOIN("WORKFLOW_PROCESS_NODE node on review.rejectnodeid = node.nodeid and review.rejectprocessid = node.processid");
            sql.WHERE("review.bizcode = #{bizcode}");
            sql.WHERE("review.rejectnodeid = #{rejectnodeid}");
            sql.ORDER_BY("review.modifytime desc");
            return sql.toString();
        }
    }
}

