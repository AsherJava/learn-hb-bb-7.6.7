/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Options
 *  org.apache.ibatis.annotations.Options$FlushCachePolicy
 *  org.apache.ibatis.annotations.Select
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowProcessNodeDao
extends BaseOptMapper<ProcessNodeDO> {
    @Options(flushCache=Options.FlushCachePolicy.TRUE)
    @SelectProvider(type=ProcessNodeSqlProvider.class, method="listProcessNode")
    public List<ProcessNodeDO> listProcessNode(ProcessNodeDTO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="listProcessNodeByProcessIdList")
    public List<ProcessNodeDO> listProcessNodeByProcessIdList(ProcessNodeDTO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="listProcessNodeByCondition")
    public List<ProcessNodeDO> listProcessNodeByCondition(ProcessNodeDTO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="selectProcessNode")
    public List<UpperKeyMap> selectProcessNode(ProcessDTO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deleteByCondition")
    public int deleteByCondition(ProcessNodeDO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deleteByProcessIdAndNodeId")
    public int deleteByProcessIdAndNodeId(ProcessNodeDO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deleteFinishProcessNode")
    public int deleteFinishProcessNode(ProcessNodeDO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deleteByNodeIdAndUserId")
    public int deleteByNodeIdAndUserId(ProcessNodeDO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deleteByNodeCodeAndUserId")
    public int deleteByNodeCodeAndUserId(ProcessNodeDO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deletePendingNode")
    public void deletePendingNode(ProcessNodeDO var1);

    @UpdateProvider(type=ProcessNodeSqlProvider.class, method="updatePendingNode")
    public void updatePendingNode(ProcessNodeDO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="update")
    public void update(ProcessNodeDO var1);

    @UpdateProvider(type=ProcessNodeSqlProvider.class, method="updateRejectStatus")
    public void updateRejectStatus(ProcessNodeDTO var1);

    @DeleteProvider(type=ProcessNodeSqlProvider.class, method="deleteByPrimaryKeys")
    public void deleteByPrimaryKeys(ProcessNodeDTO var1);

    @UpdateProvider(type=ProcessNodeSqlProvider.class, method="updateRetractRejectNodes")
    public void updateRetractRejectNodes(ProcessNodeDTO var1);

    @UpdateProvider(type=ProcessNodeSqlProvider.class, method="updateRetractNode")
    public void updateRetractNode(ProcessNodeDTO var1);

    @Update(value={"update workflow_process_node set nodeid = #{extInfo.retractRejectNewTaskId} where processid = #{processid} and nodeid = #{nodeid} and (completeresult!='\u53d6\u56de' or completeresult is null)"})
    public void updateNodeId(ProcessNodeDTO var1);

    @Update(value={"update workflow_process_node set completetime = #{completetime}, completeresult = #{completeresult} where id = #{id}"})
    public void updateCompleteTime(ProcessNodeDTO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="getByCondition")
    public List<ProcessNodeDO> getByCondition(ProcessNodeDTO var1);

    @Select(value={"select * from workflow_process_node where processid=#{processid} and nodeid=#{nodeid} and nodecode=#{nodecode}"})
    public List<ProcessNodeDO> listIsExist(ProcessNodeDTO var1);

    @UpdateProvider(type=ProcessNodeSqlProvider.class, method="updateCompleteUserAndReceiveTime")
    public void updateCompleteUserAndReceiveTime(ProcessNodeDO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="listProcessNodeByBizCodeIn")
    public List<ProcessNodeDO> listProcessNodeByBizCodeIn(TenantDO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="queryNodeLastOpt")
    public List<ProcessNodeDO> queryNodeLastOpt(ProcessNodeDTO var1);

    @SelectProvider(type=ProcessNodeSqlProvider.class, method="retractPgwNode")
    public void retractPgwNode(ProcessNodeDO var1);

    @Delete(value={"delete from workflow_process_node where bizcode=#{bizcode} and ignoreflag = 1"})
    public void deleteIgnoreNode(ProcessNodeDTO var1);

    public static class ProcessNodeSqlProvider {
        public String listProcessNodeByProcessIdList(ProcessNodeDTO processNodeDTO) {
            String sort;
            SQL sql = new SQL();
            sql.SELECT("processid, processnodename, nodeid, nodecode, completeuserid, completeusername, completeresult, completecomment, completetime, approvalflag, pgwbranch, pgwnodeid, hiddenflag, subprocessbranch, subprocessnodeid, ordernum, nodemodule, bizdefine, bizcode, completeusertype, countersignflag");
            sql.FROM("workflow_process_node");
            List processIdList = processNodeDTO.getProcessIdList();
            int totalSize = processIdList.size();
            int batchSize = 1000;
            int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterations; ++i) {
                sb.append("processid IN (");
                int start = i * batchSize;
                int end = start + Math.min(batchSize, totalSize - start);
                for (int j = start; j < end; ++j) {
                    sb.append("#{processIdList").append("[").append(j).append("]},");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(") OR ");
            }
            sb.delete(sb.length() - 3, sb.length());
            sql.WHERE(sb.toString());
            if (!processNodeDTO.isSearchIgnore()) {
                sql.WHERE("(ignoreflag !=1 or ignoreflag is null)");
            }
            ((SQL)sql.AND()).WHERE("completeresult != '\u53d6\u56de' or completeresult is null");
            String string = sort = processNodeDTO.getSort() == null ? "ordernum" : processNodeDTO.getSort();
            if (StringUtils.hasText(processNodeDTO.getOrder())) {
                sql.ORDER_BY(sort + " " + processNodeDTO.getOrder());
            } else {
                sql.ORDER_BY(sort);
            }
            return sql.toString();
        }

        public String listProcessNode(ProcessNodeDTO processNodeDTO) {
            String sort;
            List bizCodes;
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_process_node");
            if (StringUtils.hasText(processNodeDTO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDTO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (StringUtils.hasText(processNodeDTO.getNodemodule())) {
                sql.WHERE("nodemodule = #{nodemodule}");
            }
            if (StringUtils.hasText(processNodeDTO.getSyscode())) {
                sql.WHERE("syscode = #{syscode}");
            }
            if (StringUtils.hasText(processNodeDTO.getNodeid())) {
                sql.WHERE("nodeid = #{nodeid}");
            }
            if (StringUtils.hasText(processNodeDTO.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (StringUtils.hasText(processNodeDTO.getCompleteuserid())) {
                sql.WHERE("completeuserid = #{completeuserid}");
            }
            if (StringUtils.hasText(processNodeDTO.getPgwnodeid())) {
                sql.WHERE("pgwnodeid = #{pgwnodeid}");
            }
            if (StringUtils.hasText(processNodeDTO.getPgwbranch())) {
                sql.WHERE("pgwbranch = #{pgwbranch}");
            }
            if (processNodeDTO.isFilteEnd()) {
                sql.WHERE("completeresult is not null and rejectstatus is null");
            }
            if ((bizCodes = processNodeDTO.getBizCodes()) != null && !bizCodes.isEmpty()) {
                HashMap<String, String> param = new HashMap<String, String>(bizCodes.size());
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < bizCodes.size(); ++j) {
                    String bizCode = (String)bizCodes.get(j);
                    param.put("bizCode_" + j, bizCode);
                    if (j % 500 == 0) {
                        if (j > 0) {
                            stringBuilder.append(") or ");
                        }
                        stringBuilder.append("bizcode").append(" in (");
                        stringBuilder.append(" #{").append("extInfo.bizCode_").append(j).append("}");
                        continue;
                    }
                    stringBuilder.append(", #{").append("extInfo.bizCode_").append(j).append("}");
                }
                stringBuilder.append(")");
                processNodeDTO.setExtInfo(param);
                sql.WHERE("(" + stringBuilder + ")");
            }
            if (!processNodeDTO.isSearchIgnore()) {
                sql.WHERE("(ignoreflag !=1 or ignoreflag is null)");
            }
            ((SQL)sql.AND()).WHERE("completeresult != '\u53d6\u56de' or completeresult is null");
            String string = sort = processNodeDTO.getSort() == null ? "ordernum" : processNodeDTO.getSort();
            if (StringUtils.hasText(processNodeDTO.getOrder())) {
                sql.ORDER_BY(sort + " " + processNodeDTO.getOrder());
            } else {
                sql.ORDER_BY(sort);
            }
            return sql.toString();
        }

        public String getByCondition(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_process_node");
            if (StringUtils.hasText(processNodeDTO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDTO.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (StringUtils.hasText(processNodeDTO.getCompleteuserid())) {
                sql.WHERE("completeuserid = #{completeuserid}");
            }
            if (!processNodeDTO.isSearchIgnore()) {
                sql.WHERE("(ignoreflag !=1 or ignoreflag is null)");
            }
            sql.ORDER_BY("receivetime desc");
            return sql.toString();
        }

        public String listProcessNodeByCondition(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_process_node");
            if (StringUtils.hasText(processNodeDTO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDTO.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (processNodeDTO.getCompleteusertype() != null) {
                sql.WHERE("completeusertype = #{completeusertype}");
            }
            if (!processNodeDTO.isSearchIgnore()) {
                sql.WHERE("(ignoreflag !=1 or ignoreflag is null)");
            }
            if (processNodeDTO.isFilteNull()) {
                sql.WHERE("rejectstatus is null and (completeresult is null or completeresult != '\u53d6\u56de' )");
            } else {
                sql.WHERE("completeresult != '\u53d6\u56de'  and completeresult is not null");
            }
            sql.ORDER_BY("ordernum ");
            return sql.toString();
        }

        public String selectProcessNode(ProcessDTO processDTO) {
            SQL sql = new SQL();
            sql.SELECT("n.completeuserid COMPLETEUSERID, n.processnodename PROCESSNODENAME, n.nodecode NODECODE, n.subprocessbranch SUBPROCESSBRANCH");
            sql.FROM("workflow_process_node n");
            sql.WHERE("n.processid = #{id}");
            sql.WHERE("n.syscode = 'WORKFLOW'");
            sql.WHERE("(ignoreflag != 1 or ignoreflag is null)");
            sql.WHERE("n.COMPLETERESULT IS NULL");
            sql.WHERE("(n.pgwnodeid IS NULL or (n.pgwnodeid IS NOT NULL and n.nodeid != n.pgwnodeid))");
            sql.WHERE("(n.subprocessnodeid IS NULL or (n.subprocessnodeid IS NOT NULL and n.nodeid != n.subprocessnodeid))");
            sql.WHERE("(n.hiddenflag != 1 OR n.hiddenflag IS NULL)");
            sql.ORDER_BY("receivetime");
            return sql.toString();
        }

        public String deleteByCondition(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            sql.WHERE("processid = #{processid}");
            sql.WHERE("nodeid = #{nodeid}");
            sql.WHERE("completetime is null");
            return sql.toString();
        }

        public String deleteByProcessIdAndNodeId(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            if (StringUtils.hasText(processNodeDO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            sql.WHERE("nodeid = #{nodeid}");
            return sql.toString();
        }

        public String deleteByNodeIdAndUserId(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            sql.WHERE("processid = #{processid}");
            sql.WHERE("nodeid = #{nodeid}");
            if (StringUtils.hasText(processNodeDO.getCompleteuserid())) {
                sql.WHERE("completeuserid = #{completeuserid}");
            }
            return sql.toString();
        }

        public String deleteByNodeCodeAndUserId(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            sql.WHERE("processid = #{processid}");
            sql.WHERE("nodecode = #{nodecode}");
            if (StringUtils.hasText(processNodeDO.getSubprocessbranch())) {
                sql.WHERE("subprocessbranch = #{subprocessbranch}");
            }
            sql.WHERE("completeuserid = #{completeuserid}");
            return sql.toString();
        }

        public String deleteFinishProcessNode(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            sql.WHERE("nodecode = #{nodecode}");
            sql.WHERE("bizcode = #{bizcode}");
            return sql.toString();
        }

        public String deletePendingNode(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            if (StringUtils.hasText(processNodeDO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (StringUtils.hasText(processNodeDO.getPgwnodeid())) {
                sql.WHERE("pgwnodeid = #{pgwnodeid}");
            }
            if (StringUtils.hasText(processNodeDO.getPgwbranch())) {
                sql.WHERE("pgwbranch = #{pgwbranch}");
                sql.WHERE("nodeid != pgwnodeid");
            }
            if (StringUtils.hasText(processNodeDO.getSubprocessnodeid())) {
                sql.WHERE("subprocessnodeid = #{subprocessnodeid}");
            }
            if (StringUtils.hasText(processNodeDO.getSubprocessbranch())) {
                sql.WHERE("subprocessbranch = #{subprocessbranch}");
                sql.WHERE("nodeid != subprocessnodeid");
            }
            sql.WHERE("completetime is null and (ignoreflag = 0 or ignoreflag is null)");
            if (processNodeDO.getExtInfo("delPgwNodeIdFlag") == null) {
                sql.WHERE("((pgwnodeid is not null and nodeid != pgwnodeid) or (pgwnodeid is null))");
            }
            if (processNodeDO.getExtInfo("delSubProcessNodeIdFlag") == null) {
                sql.WHERE("((subprocessnodeid is not null and nodeid != subprocessnodeid) or (subprocessnodeid is null))");
            }
            return sql.toString();
        }

        public String updatePendingNode(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_process_node");
            sql.SET("ignoreflag = #{ignoreflag}");
            sql.WHERE("processid = #{processid}");
            if (StringUtils.hasText(processNodeDO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (StringUtils.hasText(processNodeDO.getPgwnodeid())) {
                sql.WHERE("pgwnodeid = #{pgwnodeid}");
            }
            if (StringUtils.hasText(processNodeDO.getPgwbranch())) {
                sql.WHERE("pgwbranch = #{pgwbranch}");
                sql.WHERE("nodeid != pgwnodeid");
            }
            if (StringUtils.hasText(processNodeDO.getSubprocessnodeid())) {
                sql.WHERE("subprocessnodeid = #{subprocessnodeid}");
            }
            if (StringUtils.hasText(processNodeDO.getSubprocessbranch())) {
                sql.WHERE("subprocessbranch = #{subprocessbranch}");
                sql.WHERE("nodeid != subprocessnodeid");
            }
            sql.WHERE("completetime is null");
            if (processNodeDO.getExtInfo("delPgwNodeIdFlag") == null) {
                sql.WHERE("((pgwnodeid is not null and nodeid != pgwnodeid) or (pgwnodeid is null))");
            }
            if (processNodeDO.getExtInfo("delSubProcessNodeIdFlag") == null) {
                sql.WHERE("((subprocessnodeid is not null and nodeid != subprocessnodeid) or (subprocessnodeid is null))");
            }
            return sql.toString();
        }

        public String update(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_process_node");
            if (processNodeDO.getCompleteresult() != null) {
                sql.SET("completeresult = #{completeresult}");
            }
            if (processNodeDO.getRejectstatus() != null) {
                sql.SET("rejectstatus = #{rejectstatus}");
            }
            if (StringUtils.hasText(processNodeDO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDO.getNodeid())) {
                sql.WHERE("nodeid = #{nodeid}");
            }
            if (StringUtils.hasText(processNodeDO.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (StringUtils.hasText(processNodeDO.getCompleteuserid())) {
                sql.WHERE("completeuserid = #{completeuserid}");
            }
            return sql.toString();
        }

        public String updateCompleteUserAndReceiveTime(ProcessNodeDO processNode) {
            SQL sql = new SQL();
            sql.UPDATE("WORKFLOW_PROCESS_NODE");
            sql.SET("COMPLETEUSERID =#{completeuserid} ");
            if (processNode.getReceivetime() != null) {
                sql.SET("RECEIVETIME = #{receivetime}");
            }
            if (processNode.getCompleteusername() != null) {
                sql.SET("COMPLETEUSERNAME = #{completeusername}");
            }
            sql.WHERE("COMPLETEUSERID =#{extInfo.transferFrom}");
            sql.WHERE("nodecode = #{nodecode}");
            sql.WHERE("processid = #{processid}");
            sql.WHERE("bizcode = #{bizcode}");
            sql.WHERE("nodeid = #{nodeid}");
            sql.WHERE("DELEGATEUSER is null");
            return sql.toString();
        }

        public String listProcessNodeByBizCodeIn(TenantDO tenantDO) {
            Object bizCode = tenantDO.getExtInfo("bizCode");
            String flag = (String)tenantDO.getExtInfo("flag");
            String sql = "pgw".equalsIgnoreCase(flag) ? "SELECT * FROM WORKFLOW_PROCESS_NODE WHERE BIZCODE IN ('" + bizCode + "') AND (COMPLETETIME is null and PGWNODEID IS NOT NULL and NODEID!=PGWNODEID ) ORDER BY ORDERNUM ASC" : "SELECT * FROM WORKFLOW_PROCESS_NODE WHERE BIZCODE IN ('" + bizCode + "') AND (PGWNODEID IS NULL OR NODEID=PGWNODEID OR NODECODE ='\u6d41\u7a0b\u7ed3\u675f') ORDER BY ORDERNUM ASC";
            return sql;
        }

        public String queryNodeLastOpt(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_process_node");
            if (StringUtils.hasText(processNodeDTO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDTO.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (StringUtils.hasText(processNodeDTO.getBizcode())) {
                sql.WHERE("bizcode = #{bizcode}");
            }
            if (processNodeDTO.getCompleteuserid() != null) {
                sql.WHERE("completeuserid = #{completeuserid}");
            }
            if (!processNodeDTO.isSearchIgnore()) {
                sql.WHERE("(ignoreflag !=1 or ignoreflag is null)");
            }
            sql.WHERE("completetime is not null");
            sql.ORDER_BY("ordernum desc");
            return sql.toString();
        }

        public String retractPgwNode(ProcessNodeDO processNodeDO) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_process_node");
            sql.SET("completeresult = #{completeresult}, completetime = #{completetime}, completecomment = #{completecomment}");
            if (StringUtils.hasText(processNodeDO.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(processNodeDO.getNodeid())) {
                sql.WHERE("nodeid = #{nodeid}");
            }
            return sql.toString();
        }

        public String updateRejectStatus(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_process_node");
            sql.SET("rejectstatus = #{rejectstatus}");
            sql.WHERE("processid = #{processid}");
            List processNodeIds = processNodeDTO.getProcessNodeIds();
            if (processNodeIds != null && !processNodeIds.isEmpty()) {
                VaWorkflowUtils.builderMultiInCondition((TenantDO)processNodeDTO, processNodeIds, "id", "processNodeId_", sql);
            }
            return sql.toString();
        }

        public String deleteByPrimaryKeys(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_process_node");
            sql.WHERE("processid = #{processid}");
            List processNodeIds = processNodeDTO.getProcessNodeIds();
            if (processNodeIds != null && !processNodeIds.isEmpty()) {
                VaWorkflowUtils.builderMultiInCondition((TenantDO)processNodeDTO, processNodeIds, "id", "processNodeId_", sql);
            }
            return sql.toString();
        }

        public String updateRetractRejectNodes(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_process_node");
            sql.SET("ignoreflag = #{ignoreflag}");
            sql.SET("rejectstatus = #{rejectstatus}");
            sql.WHERE("processid = #{processid}");
            List processNodeIds = processNodeDTO.getProcessNodeIds();
            if (processNodeIds != null && !processNodeIds.isEmpty()) {
                VaWorkflowUtils.builderMultiInCondition((TenantDO)processNodeDTO, processNodeIds, "id", "processNodeId_", sql);
            }
            return sql.toString();
        }

        public String updateRetractNode(ProcessNodeDTO processNodeDTO) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_process_node");
            sql.SET("completeresult = #{completeresult}");
            sql.SET("ignoreflag = #{ignoreflag}");
            sql.SET("rejectstatus = #{rejectstatus}");
            sql.SET("completecomment = #{completecomment}");
            sql.WHERE("processid = #{processid}");
            sql.WHERE("nodeid = #{nodeid}");
            sql.WHERE("completeuserid = #{completeuserid}");
            return sql.toString();
        }
    }
}

