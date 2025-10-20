/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowPublicParamRelationDao
extends BaseOptMapper<WorkflowPublicParamRelationDO> {
    @DeleteProvider(method="deleteRelation", type=WorkflowPublicParamRelationDaoProvide.class)
    public void deleteRelation(WorkflowPublicParamRelationDTO var1);

    @SelectProvider(method="list", type=WorkflowPublicParamRelationDaoProvide.class)
    public List<WorkflowPublicParamRelationDO> list(WorkflowPublicParamRelationDTO var1);

    @Update(value={"update WORKFLOW_PUBPARAM_REL set defineversion = #{defineversion}, username = null where  defineversion = #{extInfo.editVersion} and definekey = #{definekey} and username = #{username}"})
    public void updatePublicParamRel(WorkflowPublicParamRelationDTO var1);

    @Update(value={"update WORKFLOW_PUBPARAM_REL set username = null where defineversion = #{defineversion} and definekey = #{definekey} and username = #{username}"})
    public void updateUsername(WorkflowPublicParamRelationDTO var1);

    public static class WorkflowPublicParamRelationDaoProvide {
        public String list(WorkflowPublicParamRelationDTO dto) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("WORKFLOW_PUBPARAM_REL");
            sql.WHERE("definekey=#{definekey}");
            String username = dto.getUsername();
            if (username == null) {
                sql.WHERE("username is null");
            } else {
                sql.WHERE("username = #{username}");
            }
            Long defineversion = dto.getDefineversion();
            if (defineversion != null) {
                sql.WHERE("defineversion = #{defineversion}");
            }
            if (StringUtils.hasText(dto.getParamname())) {
                sql.WHERE("paramname=#{paramname}");
            }
            return sql.toString();
        }

        public String deleteRelation(WorkflowPublicParamRelationDTO dto) {
            SQL sql = new SQL();
            sql.DELETE_FROM("WORKFLOW_PUBPARAM_REL");
            sql.WHERE("definekey= #{definekey}");
            if (dto.getDefineversion() != null) {
                sql.WHERE("defineversion = #{defineversion}");
            }
            if (StringUtils.hasText(dto.getParamname())) {
                sql.WHERE("paramname = #{paramname}");
            }
            if (dto.getUsername() == null) {
                sql.WHERE("username is null");
            } else {
                sql.WHERE("username = #{username}");
            }
            return sql.toString();
        }
    }
}

