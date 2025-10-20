/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaWorkflowProcessDao
extends BaseOptMapper<ProcessDO> {
    @SelectProvider(type=ProcessSqlProvider.class, method="getProcess")
    public ProcessDO getProcess(ProcessDTO var1);

    @SelectProvider(type=ProcessSqlProvider.class, method="selectProcess")
    public List<UpperKeyMap> selectProcess(ProcessDTO var1);

    @Select(value={"SELECT distinct BIZTYPE, DEFINEKEY, BIZMODULE FROM WORKFLOW_PROCESS"})
    public List<UpperKeyMap> getProcessBizType(TenantDO var1);

    @SelectProvider(type=ProcessSqlProvider.class, method="countProcess")
    public int countProcess(ProcessDTO var1);

    public static class ProcessSqlProvider {
        public String getProcess(ProcessDTO processDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_process");
            if (StringUtils.hasText(processDTO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (StringUtils.hasText(processDTO.getId())) {
                sql.WHERE("id = #{id}");
            }
            return sql.toString();
        }

        public String selectProcess(ProcessDTO processDTO) {
            SQL sql = new SQL();
            if (StringUtils.hasText(processDTO.getCompleteUser()) || StringUtils.hasText(processDTO.getNodeName())) {
                sql.SELECT("DISTINCT w.*");
                sql.LEFT_OUTER_JOIN("workflow_process_node n ON w.id = n.PROCESSID AND n.PROCESSID IS NOT NULL AND n.COMPLETERESULT IS NULL");
                sql.WHERE("n.syscode = 'WORKFLOW'");
                sql.WHERE("(n.pgwnodeid IS NULL or (n.pgwnodeid IS NOT NULL and n.nodeid != n.pgwnodeid))");
                sql.WHERE("(n.subprocessnodeid IS NULL or (n.subprocessnodeid IS NOT NULL and n.nodeid != n.subprocessnodeid))");
                sql.WHERE("(n.hiddenflag != 1 OR n.hiddenflag IS NULL)");
                if (StringUtils.hasText(processDTO.getCompleteUser())) {
                    sql.WHERE("n.completeuserid = #{completeUser}");
                }
                if (StringUtils.hasText(processDTO.getNodeName())) {
                    sql.WHERE("n.processnodename = #{nodeName}");
                }
            } else {
                sql.SELECT("w.*");
            }
            sql.FROM("workflow_process w");
            sql.ORDER_BY("w.starttime desc");
            this.condition(sql, processDTO);
            return sql.toString();
        }

        public String countProcess(ProcessDTO processDTO) {
            SQL sql = new SQL();
            if (StringUtils.hasText(processDTO.getCompleteUser()) || StringUtils.hasText(processDTO.getNodeName())) {
                sql.SELECT("COUNT(DISTINCT w.ID)");
                sql.LEFT_OUTER_JOIN("workflow_process_node n ON w.id = n.PROCESSID AND n.PROCESSID IS NOT NULL AND n.COMPLETERESULT IS NULL");
                if (StringUtils.hasText(processDTO.getCompleteUser())) {
                    sql.WHERE("n.completeuserid = #{completeUser}");
                }
                if (StringUtils.hasText(processDTO.getNodeName())) {
                    sql.WHERE("n.processnodename = #{nodeName}");
                }
                sql.WHERE("n.syscode = 'WORKFLOW'");
                sql.WHERE("(n.pgwnodeid IS NULL or (n.pgwnodeid IS NOT NULL and n.nodeid != n.pgwnodeid))");
                sql.WHERE("(n.subprocessnodeid IS NULL or (n.subprocessnodeid IS NOT NULL and n.nodeid != n.subprocessnodeid))");
            } else {
                sql.SELECT("COUNT(*)");
            }
            sql.FROM("workflow_process w");
            this.condition(sql, processDTO);
            return sql.toString();
        }

        private void condition(SQL sql, ProcessDTO processDTO) {
            if (StringUtils.hasText(processDTO.getBizcode())) {
                sql.WHERE("w.BIZCODE like '%" + processDTO.getBizcode() + "%'");
            }
            if (StringUtils.hasText(processDTO.getBiztype())) {
                sql.WHERE("w.BIZTYPE = #{biztype}");
            }
            if (StringUtils.hasText(processDTO.getDefinekey())) {
                sql.WHERE("w.DEFINEKEY = #{definekey}");
            }
            if (StringUtils.hasText(processDTO.getStartunitcode())) {
                sql.WHERE("w.STARTUNITCODE = #{startunitcode}");
            }
            if (StringUtils.hasText(processDTO.getStartuser())) {
                sql.WHERE("w.STARTUSER = #{startuser}");
            }
            if (processDTO.getDefineversion() != null) {
                sql.WHERE("w.DEFINEVERSION = #{defineversion}");
            }
            if (processDTO.getStarttime() != null) {
                sql.WHERE("w.STARTTIME >= #{starttime,jdbcType=TIMESTAMP}");
            }
            if (processDTO.getEndtime() != null) {
                sql.WHERE("w.STARTTIME <= #{endtime,jdbcType=TIMESTAMP}");
            }
            if (processDTO.getStatus() != null) {
                sql.WHERE("w.STATUS = #{status}");
            }
        }
    }
}

