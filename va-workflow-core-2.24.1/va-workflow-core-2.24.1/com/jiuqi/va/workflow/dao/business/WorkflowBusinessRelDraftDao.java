/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao.business;

import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowBusinessRelDraftDao
extends CustomOptMapper {
    @SelectProvider(method="insert", type=WorkflowBusinessRelDraftDaoProvider.class)
    public void insert(WorkflowBusinessDO var1);

    @SelectProvider(method="selectOne", type=WorkflowBusinessRelDraftDaoProvider.class)
    public WorkflowBusinessDO selectOne(WorkflowBusinessDO var1);

    @SelectProvider(method="selectOneWithDesignData", type=WorkflowBusinessRelDraftDaoProvider.class)
    public WorkflowBusinessDO selectOneWithDesignData(WorkflowBusinessDO var1);

    @SelectProvider(method="select", type=WorkflowBusinessRelDraftDaoProvider.class)
    public List<WorkflowBusinessDO> select(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectLatestList", type=WorkflowBusinessRelDraftDaoProvider.class)
    public List<WorkflowBusinessDO> selectLatestList(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectWBListLatest", type=WorkflowBusinessRelDraftDaoProvider.class)
    public List<WorkflowBusinessDO> selectWBListLatest(WorkflowBusinessDTO var1);

    @DeleteProvider(method="delete", type=WorkflowBusinessRelDraftDaoProvider.class)
    public int delete(WorkflowBusinessDO var1);

    @UpdateProvider(method="update", type=WorkflowBusinessRelDraftDaoProvider.class)
    public void update(WorkflowBusinessDO var1);

    public static class WorkflowBusinessRelDraftDaoProvider {
        public String insert(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.INSERT_INTO("WORKFLOW_BUSINESS_REL_DRAFT");
            sql.INTO_COLUMNS(new String[]{"ID, MODULENAME, BUSINESSTYPE, BUSINESSCODE, WORKFLOWDEFINEKEY, WORKFLOWDEFINEVERSION, DESIGNSTATE, LOCKFLAG, LOCKUSER, RELVERSION, MODIFYTIME"});
            sql.INTO_VALUES(new String[]{"#{id}, #{modulename}, #{businesstype}, #{businesscode}, #{workflowdefinekey}, #{workflowdefineversion}, #{designstate}, #{lockflag}, #{lockuser}, #{relversion}, #{modifytime}"});
            return sql.toString();
        }

        public String selectOne(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("*")).FROM("WORKFLOW_BUSINESS_REL_DRAFT");
            if (workflowBusinessDO.getId() != null) {
                sql.WHERE("ID = #{id}");
            }
            if (StringUtils.hasText(workflowBusinessDO.getBusinesscode())) {
                sql.WHERE("BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDO.getWorkflowdefinekey())) {
                sql.WHERE("WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDO.getWorkflowdefineversion() != null) {
                sql.WHERE("WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            return sql.toString();
        }

        public String selectOneWithDesignData(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("t1.*, t2.DESIGNDATA")).FROM("WORKFLOW_BUSINESS_REL_DRAFT t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t2 ON t1.ID = t2.ID");
            if (workflowBusinessDO.getId() != null) {
                sql.WHERE("t1.ID = #{id}");
            }
            if (StringUtils.hasText(workflowBusinessDO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDO.getWorkflowdefineversion() != null) {
                sql.WHERE("t1.WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            return sql.toString();
        }

        public String select(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("t1.*, t2.DESIGNDATA")).FROM("WORKFLOW_BUSINESS_REL_DRAFT t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t2 ON t1.ID = t2.ID");
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesstype())) {
                sql.WHERE("t1.BUSINESSTYPE  = #{businesstype}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDTO.getWorkflowdefineversion() != null) {
                sql.WHERE("t1.WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            this.buildInCondition(workflowBusinessDTO, sql);
            List deployIds = workflowBusinessDTO.getDeployIds();
            if (!CollectionUtils.isEmpty(deployIds)) {
                int totalSize = deployIds.size();
                int batchSize = 1000;
                int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < iterations; ++i) {
                    sb.append("t1.ID IN (");
                    int start = i * batchSize;
                    int end = start + Math.min(batchSize, totalSize - start);
                    for (int j = start; j < end; ++j) {
                        sb.append("#{deployIds").append("[").append(j).append("]},");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(") OR ");
                }
                sb.delete(sb.length() - 3, sb.length());
                sql.WHERE(sb.toString());
            }
            sql.ORDER_BY("BUSINESSCODE,WORKFLOWDEFINEKEY,WORKFLOWDEFINEVERSION desc");
            return sql.toString();
        }

        public String selectLatestList(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            sql.SELECT("t1.*, t3.DESIGNDATA");
            sql.FROM("WORKFLOW_BUSINESS_REL_DRAFT t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t3 ON t1.ID = t3.ID");
            sql.WHERE("NOT EXISTS(SELECT 1 FROM WORKFLOW_BUSINESS_REL_DRAFT t2 WHERE t1.BUSINESSCODE = t2.BUSINESSCODE AND t1.WORKFLOWDEFINEKEY = t2.WORKFLOWDEFINEKEY AND t1.WORKFLOWDEFINEVERSION < t2.WORKFLOWDEFINEVERSION)");
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesstype())) {
                sql.WHERE("t1.BUSINESSTYPE  = #{businesstype}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDTO.getDesignstate() != null) {
                sql.WHERE("t1.DESIGNSTATE = #{designstate}");
            }
            this.buildInCondition(workflowBusinessDTO, sql);
            sql.ORDER_BY("t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY");
            return sql.toString();
        }

        private void buildInCondition(WorkflowBusinessDTO workflowBusinessDTO, SQL sql) {
            List workflowdefinekeys;
            List businesscodes = workflowBusinessDTO.getBusinesscodes();
            if (!CollectionUtils.isEmpty(businesscodes)) {
                int batchSize = 500;
                int totalSize = businesscodes.size();
                int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < iterations; ++i) {
                    sb.append("t1.BUSINESSCODE in (");
                    int start = i * batchSize;
                    int end = start + Math.min(batchSize, totalSize - start);
                    for (int j = start; j < end; ++j) {
                        sb.append("#{businesscodes").append("[").append(j).append("]},");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(") OR ");
                }
                sb.delete(sb.length() - 3, sb.length());
                sql.WHERE(sb.toString());
            }
            if (!CollectionUtils.isEmpty(workflowdefinekeys = workflowBusinessDTO.getWorkflowdefinekeys())) {
                int batchSize = 500;
                int totalSize = workflowdefinekeys.size();
                int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < iterations; ++i) {
                    sb.append("t1.WORKFLOWDEFINEKEY in (");
                    int start = i * batchSize;
                    int end = start + Math.min(batchSize, totalSize - start);
                    for (int j = start; j < end; ++j) {
                        sb.append("#{workflowdefinekeys").append("[").append(j).append("]},");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(") OR ");
                }
                sb.delete(sb.length() - 3, sb.length());
                sql.WHERE(sb.toString());
            }
        }

        public String selectWBListLatest(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            sql.SELECT("t1.*, t3.DESIGNDATA");
            sql.FROM("WORKFLOW_BUSINESS_REL_DRAFT t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t3 ON t1.ID = t3.ID");
            sql.WHERE("NOT EXISTS(SELECT 1 FROM WORKFLOW_BUSINESS_REL_DRAFT t2 WHERE t1.BUSINESSCODE = t2.BUSINESSCODE AND t1.WORKFLOWDEFINEKEY = t2.WORKFLOWDEFINEKEY AND t1.WORKFLOWDEFINEVERSION < t2.WORKFLOWDEFINEVERSION)");
            List businessesWorkflows = workflowBusinessDTO.getBusinessesWorkflows();
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < businessesWorkflows.size(); ++i) {
                sb.append("(t1.BUSINESSCODE = #{businessesWorkflows").append("[").append(i).append("]").append(".bizName}");
                sb.append(" AND ");
                sb.append("t1.WORKFLOWDEFINEKEY = #{businessesWorkflows").append("[").append(i).append("]").append(".workflowName})");
                sb.append(" OR ");
            }
            sb.setLength(sb.length() - 4);
            sb.append(")");
            sql.WHERE(sb.toString());
            if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.ORDER_BY("t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY");
            } else {
                sql.ORDER_BY("t1.WORKFLOWDEFINEKEY, t1.BUSINESSCODE");
            }
            return sql.toString();
        }

        public String delete(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("WORKFLOW_BUSINESS_REL_DRAFT");
            if (workflowBusinessDO.getId() != null) {
                sql.WHERE("ID = #{id}");
            }
            if (StringUtils.hasText(workflowBusinessDO.getBusinesscode())) {
                sql.WHERE("BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDO.getWorkflowdefinekey())) {
                sql.WHERE("WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDO.getWorkflowdefineversion() != null) {
                sql.WHERE("WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            if (workflowBusinessDO.getDesignstate() != null) {
                sql.WHERE("DESIGNSTATE = #{designstate}");
            }
            return sql.toString();
        }

        public String update(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.UPDATE("WORKFLOW_BUSINESS_REL_DRAFT");
            if (workflowBusinessDO.getLockflag() != null) {
                sql.SET("LOCKFLAG = #{lockflag}");
            }
            if (workflowBusinessDO.getLockuser() != null) {
                sql.SET("LOCKUSER = #{lockuser}");
            }
            if (workflowBusinessDO.getModifytime() != null) {
                sql.SET("MODIFYTIME = #{modifytime}");
            }
            if (workflowBusinessDO.getDesignstate() != null) {
                sql.SET("DESIGNSTATE = #{designstate}");
            }
            sql.WHERE("BUSINESSCODE = #{businesscode}");
            if (StringUtils.hasText(workflowBusinessDO.getWorkflowdefinekey())) {
                sql.WHERE("WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDO.getWorkflowdefineversion() != null) {
                sql.WHERE("WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            if (workflowBusinessDO.getId() != null) {
                sql.WHERE("ID = #{id}");
            }
            return sql.toString();
        }
    }
}

