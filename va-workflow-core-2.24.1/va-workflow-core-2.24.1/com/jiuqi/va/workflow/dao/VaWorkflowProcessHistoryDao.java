/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaWorkflowProcessHistoryDao
extends BaseOptMapper<ProcessHistoryDO> {
    @SelectProvider(type=ProcessHistorySqlProvider.class, method="listHistory")
    public List<ProcessHistoryDO> listHistory(ProcessDTO var1);

    public static class ProcessHistorySqlProvider {
        public String listHistory(ProcessDTO processDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_process_history");
            if (StringUtils.hasText(processDTO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (StringUtils.hasText(processDTO.getId())) {
                sql.WHERE("id = #{id}");
            }
            ((SQL)sql.AND()).WHERE("endstatus != 4 or endstatus is null");
            sql.ORDER_BY("starttime");
            return sql.toString();
        }
    }
}

