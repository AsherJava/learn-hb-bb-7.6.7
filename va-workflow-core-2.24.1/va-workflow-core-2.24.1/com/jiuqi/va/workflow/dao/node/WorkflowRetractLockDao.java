/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDO
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao.node;

import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDO;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowRetractLockDao
extends BaseOptMapper<WorkflowRetractLockDO> {
    @UpdateProvider(type=WorkflowRetractLockSqlProvider.class, method="updateCount")
    public void updateCount(WorkflowRetractLockDTO var1);

    public static class WorkflowRetractLockSqlProvider {
        public String updateCount(WorkflowRetractLockDTO dto) {
            SQL sql = new SQL();
            sql.UPDATE("WORKFLOW_RETRACT_LOCK");
            sql.SET("lockcount = #{lockcount}");
            sql.WHERE("bizcode = #{bizcode}");
            sql.WHERE("locknode = #{locknode}");
            if (StringUtils.hasText(dto.getSubprocessbranch())) {
                sql.WHERE("subprocessbranch = #{subprocessbranch}");
            }
            return sql.toString();
        }
    }
}

