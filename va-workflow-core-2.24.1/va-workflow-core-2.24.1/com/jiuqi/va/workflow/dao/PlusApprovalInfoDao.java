/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 *  tk.mybatis.mapper.common.BaseMapper
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface PlusApprovalInfoDao
extends BaseMapper<PlusApprovalInfoDO> {
    @SelectProvider(type=PlusApprovalInfoSqlProvider.class, method="select")
    public List<PlusApprovalInfoDO> select(PlusApprovalInfoDO var1);

    @Select(value={"select * from workflow_plusapproval_info where username = #{username} order by createtime desc"})
    public List<PlusApprovalInfoDO> selectByUserName(PlusApprovalInfoDO var1);

    @SelectProvider(type=PlusApprovalInfoSqlProvider.class, method="selectByConditionAndNodeIds")
    public List<PlusApprovalInfoDO> selectByConditionAndNodeIds(PlusApprovalInfoDTO var1);

    @UpdateProvider(type=PlusApprovalInfoSqlProvider.class, method="update")
    public void update(PlusApprovalInfoDO var1);

    @Update(value={"update workflow_plusapproval_info set nodeid = #{extInfo.retractRejectNewTaskId} where processid = #{processid} and nodeid=#{nodeid}"})
    public void updateNodeId(PlusApprovalInfoDTO var1);

    @DeleteProvider(type=PlusApprovalInfoSqlProvider.class, method="deleteByCondition")
    public int deleteByCondition(PlusApprovalInfoDO var1);

    public static class PlusApprovalInfoSqlProvider {
        public String selectByConditionAndNodeIds(PlusApprovalInfoDTO info) {
            List processIdList;
            List nodeIds;
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_plusapproval_info");
            if (StringUtils.hasText(info.getUsername())) {
                sql.WHERE("username = #{username}");
            }
            if (StringUtils.hasText(info.getApprovaluser())) {
                sql.WHERE("approvaluser = #{approvaluser}");
            }
            if (StringUtils.hasText(info.getApprovalunitcode())) {
                sql.WHERE("approvalunitcode = #{approvalunitcode}");
            }
            if (StringUtils.hasText(info.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(info.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (!CollectionUtils.isEmpty(nodeIds = info.getNodeIds())) {
                HashMap<String, String> paramMap = new HashMap<String, String>();
                StringBuilder stringBuilder = new StringBuilder();
                int size = nodeIds.size();
                for (int i = 0; i < size; ++i) {
                    String nodeId = (String)nodeIds.get(i);
                    paramMap.put("nodeId_" + i, nodeId);
                    stringBuilder.append(", #{").append("extInfo.nodeId_").append(i).append(",jdbcType=VARCHAR}");
                }
                String subSqlStr = stringBuilder.substring(1, stringBuilder.length());
                info.setExtInfo(paramMap);
                sql.WHERE("NODEID in (" + subSqlStr + ")");
            }
            if (!CollectionUtils.isEmpty(processIdList = info.getProcessIdList())) {
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
            }
            sql.ORDER_BY("createtime");
            return sql.toString();
        }

        public String select(PlusApprovalInfoDO info) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_plusapproval_info");
            if (StringUtils.hasText(info.getUsername())) {
                sql.WHERE("username = #{username}");
            }
            if (StringUtils.hasText(info.getApprovaluser())) {
                sql.WHERE("approvaluser = #{approvaluser}");
            }
            if (StringUtils.hasText(info.getApprovalunitcode())) {
                sql.WHERE("approvalunitcode = #{approvalunitcode}");
            }
            if (StringUtils.hasText(info.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(info.getNodeid())) {
                sql.WHERE("nodeid = #{nodeid}");
            }
            if (StringUtils.hasText(info.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            return sql.toString();
        }

        public String update(PlusApprovalInfoDO info) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_plusapproval_info");
            sql.SET("approvalcomment = #{approvalcomment}");
            if (StringUtils.hasText(info.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(info.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            return sql.toString();
        }

        public String deleteByCondition(PlusApprovalInfoDO info) {
            SQL sql = new SQL();
            sql.DELETE_FROM("workflow_plusapproval_info");
            if (StringUtils.hasText(info.getProcessid())) {
                sql.WHERE("processid = #{processid}");
            }
            if (StringUtils.hasText(info.getNodecode())) {
                sql.WHERE("nodecode = #{nodecode}");
            }
            if (StringUtils.hasText(info.getNodeid())) {
                sql.WHERE("nodeid = #{nodeid}");
            }
            if (StringUtils.hasText(info.getApprovaluser())) {
                sql.WHERE("approvaluser = #{approvaluser}");
            }
            if (StringUtils.hasText(info.getApprovalunitcode())) {
                sql.WHERE("approvalunitcode = #{approvalunitcode}");
            }
            return sql.toString();
        }
    }
}

