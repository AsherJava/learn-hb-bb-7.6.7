/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 *  tk.mybatis.mapper.common.BaseMapper
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface WorkflowProcessRejectNodeDao
extends BaseMapper<ProcessRejectNodeDO> {
    @UpdateProvider(method="logicalDelete", type=WorkflowProcessRejectNodeDaoProvider.class)
    public void logicalDelete(ProcessRejectNodeDO var1);

    @UpdateProvider(method="restore", type=WorkflowProcessRejectNodeDaoProvider.class)
    public void restore(ProcessRejectNodeDO var1);

    public static class WorkflowProcessRejectNodeDaoProvider {
        public String logicalDelete(ProcessRejectNodeDO processNode) {
            SQL sql = new SQL();
            ((SQL)sql.UPDATE("WORKFLOW_PROCESS_REJECT_NODE")).SET("DELETEDFLAG = 1");
            if (StringUtils.hasText(processNode.getAgreenodeid())) {
                sql.SET("AGREENODEID = #{agreenodeid}");
            }
            this.setConditions(processNode, sql);
            return sql.toString();
        }

        public String restore(ProcessRejectNodeDO processNode) {
            SQL sql = new SQL();
            ((SQL)sql.UPDATE("WORKFLOW_PROCESS_REJECT_NODE")).SET("DELETEDFLAG = 0, AGREENODEID = NULL");
            this.setConditions(processNode, sql);
            sql.WHERE("AGREENODEID = #{agreenodeid}");
            return sql.toString();
        }

        private void setConditions(ProcessRejectNodeDO processNode, SQL sql) {
            if (StringUtils.hasText(processNode.getId())) {
                sql.WHERE("id = #{id}");
            }
            if (StringUtils.hasText(processNode.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (StringUtils.hasText(processNode.getProcessdefinekey())) {
                sql.WHERE("processdefinekey = #{processdefinekey}");
            }
            if (processNode.getProcessdefineversion() != null) {
                sql.WHERE("processdefineversion = #{processdefineversion}");
            }
            if (StringUtils.hasText(processNode.getNodeid())) {
                sql.WHERE("nodeid = #{nodeid}");
            }
            if (StringUtils.hasText(processNode.getSubprocessbranch())) {
                sql.WHERE("subprocessbranch = #{subprocessbranch}");
            }
            if (StringUtils.hasText(processNode.getRejectnodecode())) {
                sql.WHERE("rejectnodecode = #{rejectnodecode}");
            }
            if (StringUtils.hasText(processNode.getBerejectednodecode())) {
                sql.WHERE("berejectednodecode = #{berejectednodecode}");
            }
            if (processNode.getAgreenotreapprove() != null) {
                sql.WHERE("agreenotreapprove = #{agreenotreapprove}");
            }
            if (processNode.getDeletedflag() == null) {
                sql.WHERE("deletedflag is null or deletedflag = 0");
            } else {
                sql.WHERE("deletedflag = 1");
            }
        }
    }
}

