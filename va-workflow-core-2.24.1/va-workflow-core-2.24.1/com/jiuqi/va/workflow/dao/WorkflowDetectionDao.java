/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.Insert
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDTO;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowDetectionDao
extends BaseOptMapper<WorkflowDetectionDO> {
    @SelectProvider(method="listDetect", type=WorkflowDetectionDaoProvider.class)
    public List<WorkflowDetectionDTO> listDetect(WorkflowDetectionDTO var1);

    @SelectProvider(method="listDetectHis", type=WorkflowDetectionDaoProvider.class)
    public List<WorkflowDetectionDTO> listDetectHis(WorkflowDetectionDTO var1);

    @Insert(value={"INSERT INTO WORKFLOW_DETECTION_HISTORY(id, workflowdefinekey, bizdefine, operatetime, operator) VALUES(#{id},#{workflowdefinekey},#{bizdefine},#{operatetime},#{operator})"})
    public int insertDetectHis(WorkflowDetectionDO var1);

    @Delete(value={"delete from WORKFLOW_DETECTION where id=#{id}"})
    public int deleteById(WorkflowDetectionDO var1);

    public static class WorkflowDetectionDaoProvider {
        public String listDetect(WorkflowDetectionDTO detectionDTO) {
            SQL sql = new SQL();
            if (detectionDTO.isSearchdata()) {
                sql.SELECT("t1.*,t2.detectiondata,t3.detectionresult");
                sql.FROM("WORKFLOW_DETECTION t1");
                sql.LEFT_OUTER_JOIN("WORKFLOW_DETECTION_DATA t2 on t1.id = t2.id");
                sql.LEFT_OUTER_JOIN("WORKFLOW_DETECTION_RESULT t3 on t1.id = t3.id");
            } else {
                sql.SELECT("t1.*");
                sql.FROM("WORKFLOW_DETECTION t1");
            }
            if (StringUtils.hasText(detectionDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.workflowdefinekey = #{workflowdefinekey}");
            }
            if (StringUtils.hasText(detectionDTO.getBizdefine())) {
                sql.WHERE("t1.bizdefine = #{bizdefine}");
            }
            if (StringUtils.hasText(detectionDTO.getOperator())) {
                sql.WHERE("t1.operator = #{operator}");
            }
            return sql.toString();
        }

        public String listDetectHis(WorkflowDetectionDTO detectionDTO) {
            SQL sql = new SQL();
            if (detectionDTO.isSearchdata()) {
                sql.SELECT("t1.*,t2.detectiondata,t3.detectionresult");
                sql.FROM("WORKFLOW_DETECTION_HISTORY t1");
                sql.LEFT_OUTER_JOIN("WORKFLOW_DETECTION_DATA t2 on t1.id = t2.id");
                sql.LEFT_OUTER_JOIN("WORKFLOW_DETECTION_RESULT t3 on t1.id = t3.id");
            } else {
                sql.SELECT("t1.*");
                sql.FROM("WORKFLOW_DETECTION_HISTORY t1");
            }
            if (StringUtils.hasText(detectionDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.workflowdefinekey = #{workflowdefinekey}");
            }
            if (StringUtils.hasText(detectionDTO.getBizdefine())) {
                sql.WHERE("t1.bizdefine = #{bizdefine}");
            }
            if (StringUtils.hasText(detectionDTO.getOperator())) {
                sql.WHERE("t1.operator = #{operator}");
            }
            return sql.toString();
        }
    }
}

