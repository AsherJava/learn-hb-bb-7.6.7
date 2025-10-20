/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.user.UserDO
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.workflow.domain.WorkflowExtendQueryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowExtendQueryDao {
    @SelectProvider(type=WorkflowExtendQueryProvider.class, method="countUser")
    public int countUser(WorkflowExtendQueryDTO var1);

    @SelectProvider(type=WorkflowExtendQueryProvider.class, method="queryUser")
    public List<UserDO> queryUser(WorkflowExtendQueryDTO var1);

    @SelectProvider(type=WorkflowExtendQueryProvider.class, method="listByRelParam")
    public List<UserDO> listByRelParam(WorkflowExtendQueryDTO var1);

    @SelectProvider(type=WorkflowExtendQueryProvider.class, method="countByRelParam")
    public int countByRelParam(WorkflowExtendQueryDTO var1);

    public static class WorkflowExtendQueryProvider {
        public String countUser(WorkflowExtendQueryDTO workflowExtendQueryDTO) {
            List<String> unitCodeList;
            SQL sql = new SQL();
            ((SQL)((SQL)sql.SELECT("COUNT(ID)")).FROM("NP_USER")).WHERE("ENABLED = 1");
            if (StringUtils.hasText(workflowExtendQueryDTO.getSearchKey())) {
                sql.WHERE("(NAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%') or FULLNAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%'))");
            }
            if (!CollectionUtils.isEmpty(unitCodeList = workflowExtendQueryDTO.getUnitCodeList())) {
                sql.WHERE(this.buildInCondition(unitCodeList));
            } else if (StringUtils.hasText(workflowExtendQueryDTO.getUnitcode())) {
                sql.WHERE("ORG_CODE = #{unitcode}");
            }
            return sql.toString();
        }

        public String queryUser(WorkflowExtendQueryDTO workflowExtendQueryDTO) {
            List<String> unitCodeList;
            SQL sql = new SQL();
            ((SQL)((SQL)sql.SELECT("ID AS id,NAME AS username,FULLNAME AS name, ORG_CODE AS unitcode")).FROM("NP_USER")).WHERE("ENABLED = 1");
            if (StringUtils.hasText(workflowExtendQueryDTO.getSearchKey())) {
                sql.WHERE("(NAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%') or FULLNAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%'))");
            }
            if (!CollectionUtils.isEmpty(unitCodeList = workflowExtendQueryDTO.getUnitCodeList())) {
                sql.WHERE(this.buildInCondition(unitCodeList));
            } else if (StringUtils.hasText(workflowExtendQueryDTO.getUnitcode())) {
                sql.WHERE("ORG_CODE = #{unitcode}");
            }
            return sql.toString();
        }

        public String listByRelParam(WorkflowExtendQueryDTO workflowExtendQueryDTO) {
            SQL sql = new SQL();
            ((SQL)((SQL)((SQL)sql.SELECT("ID AS id,NAME AS username,FULLNAME AS name, ORG_CODE AS unitcode")).FROM("NP_USER t1 ")).INNER_JOIN(workflowExtendQueryDTO.getMappingTable() + " t2 on t1.ORG_CODE = t2.c ")).WHERE(" t1.ENABLED = 1 and t2.h=#{hashKey}");
            if (StringUtils.hasText(workflowExtendQueryDTO.getSearchKey())) {
                sql.WHERE("(NAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%') or FULLNAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%'))");
            }
            return sql.toString();
        }

        public String countByRelParam(WorkflowExtendQueryDTO workflowExtendQueryDTO) {
            SQL sql = new SQL();
            ((SQL)((SQL)((SQL)sql.SELECT("count(*)")).FROM("NP_USER t1 ")).INNER_JOIN(workflowExtendQueryDTO.getMappingTable() + " t2 on t1.ORG_CODE = t2.c ")).WHERE(" t1.ENABLED = 1  and t2.h=#{hashKey}");
            if (StringUtils.hasText(workflowExtendQueryDTO.getSearchKey())) {
                sql.WHERE("(NAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%') or FULLNAME LIKE CONCAT(CONCAT('%',#{searchKey}),'%'))");
            }
            return sql.toString();
        }

        private String buildInCondition(List<String> unitCodeList) {
            int batchSize = 500;
            int totalSize = unitCodeList.size();
            int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterations; ++i) {
                sb.append("ORG_CODE IN (");
                int start = i * batchSize;
                int end = start + Math.min(batchSize, totalSize - start);
                for (int j = start; j < end; ++j) {
                    sb.append("#{unitCodeList").append("[").append(j).append("]},");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(") OR ");
            }
            sb.delete(sb.length() - 3, sb.length());
            return sb.toString();
        }
    }
}

