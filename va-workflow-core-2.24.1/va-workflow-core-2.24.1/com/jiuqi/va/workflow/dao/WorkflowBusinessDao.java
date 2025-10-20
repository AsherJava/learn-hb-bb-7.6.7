/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import com.jiuqi.va.workflow.constants.VaWorkflowConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowBusinessDao
extends CustomOptMapper {
    @InsertProvider(method="add", type=WorkflowBusinessDaoProvider.class)
    public void add(WorkflowBusinessDO var1);

    @UpdateProvider(method="update", type=WorkflowBusinessDaoProvider.class)
    public void update(WorkflowBusinessDO var1);

    @DeleteProvider(method="delete", type=WorkflowBusinessDaoProvider.class)
    public void delete(WorkflowBusinessDO var1);

    @UpdateProvider(method="updateByPrimaryKey", type=WorkflowBusinessDaoProvider.class)
    public void updateByPrimaryKey(WorkflowBusinessDO var1);

    @SelectProvider(method="selectOne", type=WorkflowBusinessDaoProvider.class)
    public WorkflowBusinessDO selectOne(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectOneWithDesignData", type=WorkflowBusinessDaoProvider.class)
    public WorkflowBusinessDO selectOneWithDesignData(WorkflowBusinessDTO var1);

    @SelectProvider(method="select", type=WorkflowBusinessDaoProvider.class)
    public List<WorkflowBusinessDO> select(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectLatest", type=WorkflowBusinessDaoProvider.class)
    public List<WorkflowBusinessDO> selectLatest(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectLatestList", type=WorkflowBusinessDaoProvider.class)
    public List<WorkflowBusinessDO> selectLatestList(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectBusinessList", type=WorkflowBusinessDaoProvider.class)
    public List<WorkflowBusinessDO> selectBusinessList(WorkflowBusinessDTO var1);

    @SelectProvider(method="selectWBListLatest", type=WorkflowBusinessDaoProvider.class)
    public List<WorkflowBusinessDO> selectWBListLatest(WorkflowBusinessDTO var1);

    public static class WorkflowBusinessDaoProvider {
        public String add(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.INSERT_INTO("WORKFLOW_BUSINESS_RELATION");
            sql.INTO_COLUMNS(new String[]{"ID, MODULENAME, BUSINESSTYPE, BUSINESSCODE, WORKFLOWDEFINEKEY, WORKFLOWDEFINEVERSION, STOPFLAG, LOCKFLAG, LOCKUSER, RELVERSION"});
            sql.INTO_VALUES(new String[]{"#{id}, #{modulename}, #{businesstype}, #{businesscode}, #{workflowdefinekey}, #{workflowdefineversion}, 0, #{lockflag}, #{lockuser}, #{relversion}"});
            return sql.toString();
        }

        public String delete(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("WORKFLOW_BUSINESS_RELATION");
            sql.WHERE("ID = #{id}");
            return sql.toString();
        }

        public String update(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.UPDATE("WORKFLOW_BUSINESS_RELATION");
            if (workflowBusinessDO.getStopflag() != null) {
                sql.SET("STOPFLAG = #{stopflag}");
            }
            if (workflowBusinessDO.getLockflag() != null) {
                sql.SET("LOCKFLAG = #{lockflag}");
            }
            if (workflowBusinessDO.getLockuser() != null) {
                sql.SET("LOCKUSER = #{lockuser}");
            }
            if (workflowBusinessDO.getRelversion() != null) {
                sql.SET("RELVERSION = #{relversion}");
            }
            sql.WHERE("BUSINESSCODE = #{businesscode}");
            if (StringUtils.hasText(workflowBusinessDO.getWorkflowdefinekey())) {
                sql.WHERE("WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDO.getWorkflowdefineversion() != null) {
                sql.WHERE("WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            return sql.toString();
        }

        public String updateByPrimaryKey(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            sql.UPDATE("WORKFLOW_BUSINESS_RELATION");
            if (workflowBusinessDO.getStopflag() != null) {
                sql.SET("STOPFLAG = #{stopflag}");
            }
            if (workflowBusinessDO.getRelversion() != null) {
                sql.SET("RELVERSION = #{relversion}");
            }
            sql.WHERE("ID = #{id}");
            return sql.toString();
        }

        public String selectOne(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("*")).FROM("WORKFLOW_BUSINESS_RELATION");
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
            sql.WHERE("STOPFLAG = 0");
            return sql.toString();
        }

        public String selectOneWithDesignData(WorkflowBusinessDO workflowBusinessDO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("t1.*, t2.DESIGNDATA")).FROM("WORKFLOW_BUSINESS_RELATION t1");
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
            sql.WHERE("t1.STOPFLAG = 0");
            return sql.toString();
        }

        public String select(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            sql.SELECT("t1.*, t3.DESIGNDATA");
            sql.FROM("WORKFLOW_BUSINESS_RELATION t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t3 ON t1.ID = t3.ID");
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesstype())) {
                sql.WHERE("t1.BUSINESSTYPE  = #{businesstype}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE  = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (workflowBusinessDTO.getWorkflowdefineversion() != null) {
                sql.WHERE("t1.WORKFLOWDEFINEVERSION = #{workflowdefineversion}");
            }
            if (workflowBusinessDTO.getStopflag() == null) {
                sql.WHERE("t1.STOPFLAG = 0");
            } else if (workflowBusinessDTO.getStopflag() != -1) {
                sql.WHERE("t1.STOPFLAG = #{stopflag}");
            }
            sql.ORDER_BY("t1.BUSINESSCODE,t1.WORKFLOWDEFINEKEY,t1.WORKFLOWDEFINEVERSION desc");
            return sql.toString();
        }

        public String selectLatest(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            sql.SELECT("t1.*, t3.DESIGNDATA");
            sql.FROM("WORKFLOW_BUSINESS_RELATION t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t3 ON t1.ID = t3.ID");
            sql.WHERE("NOT EXISTS(SELECT 1 FROM WORKFLOW_BUSINESS_RELATION t2 WHERE t1.BUSINESSCODE = t2.BUSINESSCODE AND t1.WORKFLOWDEFINEKEY = t2.WORKFLOWDEFINEKEY AND t1.WORKFLOWDEFINEVERSION < t2.WORKFLOWDEFINEVERSION)");
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesstype())) {
                sql.WHERE("t1.BUSINESSTYPE  = #{businesstype}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            sql.WHERE("t1.STOPFLAG = 0");
            if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.ORDER_BY("t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY");
            } else {
                sql.ORDER_BY("t1.WORKFLOWDEFINEKEY, t1.BUSINESSCODE");
            }
            return sql.toString();
        }

        public String selectLatestList(WorkflowBusinessDTO workflowBusinessDTO) {
            List businesscodes;
            SQL sql = new SQL();
            sql.SELECT("t1.ID, t1.MODULENAME, t1.BUSINESSTYPE, t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY, t1.WORKFLOWDEFINEVERSION, t1.STOPFLAG, t1.LOCKFLAG, t1.LOCKUSER, t1.RELVERSION");
            sql.FROM("WORKFLOW_BUSINESS_RELATION t1");
            sql.WHERE("NOT EXISTS(SELECT 1 FROM WORKFLOW_BUSINESS_RELATION t2 WHERE t1.BUSINESSCODE = t2.BUSINESSCODE AND t1.WORKFLOWDEFINEKEY = t2.WORKFLOWDEFINEKEY AND t1.WORKFLOWDEFINEVERSION < t2.WORKFLOWDEFINEVERSION)");
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesstype())) {
                sql.WHERE("t1.BUSINESSTYPE  = #{businesstype}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if (!CollectionUtils.isEmpty(businesscodes = workflowBusinessDTO.getBusinesscodes())) {
                int totalSize = businesscodes.size();
                int batchSize = 500;
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
            sql.WHERE("t1.STOPFLAG = 0");
            sql.ORDER_BY("t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY");
            return sql.toString();
        }

        public String selectBusinessList(WorkflowBusinessDTO workflowBusinessDTO) {
            List workflowdefinekeys;
            SQL sql = new SQL();
            sql.SELECT("t1.ID, t1.MODULENAME, t1.BUSINESSTYPE, t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY, t1.WORKFLOWDEFINEVERSION, t1.STOPFLAG, t1.LOCKFLAG, t1.LOCKUSER, t1.RELVERSION");
            sql.FROM("WORKFLOW_BUSINESS_RELATION t1");
            sql.WHERE("NOT EXISTS(SELECT 1 FROM WORKFLOW_BUSINESS_RELATION t2 WHERE t1.BUSINESSCODE = t2.BUSINESSCODE AND t1.WORKFLOWDEFINEKEY = t2.WORKFLOWDEFINEKEY AND t1.WORKFLOWDEFINEVERSION < t2.WORKFLOWDEFINEVERSION)");
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesstype())) {
                sql.WHERE("t1.BUSINESSTYPE  = #{businesstype}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.WHERE("t1.BUSINESSCODE = #{businesscode}");
            }
            if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
                sql.WHERE("t1.WORKFLOWDEFINEKEY = #{workflowdefinekey}");
            }
            if ((workflowdefinekeys = workflowBusinessDTO.getWorkflowdefinekeys()) != null && !workflowdefinekeys.isEmpty()) {
                sql.WHERE("(" + this.setCollectionSql("t1.WORKFLOWDEFINEKEY", workflowBusinessDTO) + ")");
            }
            sql.WHERE("t1.STOPFLAG = 0");
            sql.ORDER_BY("t1.WORKFLOWDEFINEKEY, t1.BUSINESSCODE");
            return sql.toString();
        }

        public String selectWBListLatest(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            sql.SELECT("t1.*, t3.DESIGNDATA");
            sql.FROM("WORKFLOW_BUSINESS_RELATION t1");
            sql.LEFT_OUTER_JOIN("WORKFLOW_BUSINESS_REL_DESIGN t3 ON t1.ID = t3.ID");
            sql.WHERE("NOT EXISTS(SELECT 1 FROM WORKFLOW_BUSINESS_RELATION t2 WHERE t1.BUSINESSCODE = t2.BUSINESSCODE AND t1.WORKFLOWDEFINEKEY = t2.WORKFLOWDEFINEKEY AND t1.WORKFLOWDEFINEVERSION < t2.WORKFLOWDEFINEVERSION)");
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
            sql.WHERE("t1.STOPFLAG = 0");
            if (!StringUtils.hasText(workflowBusinessDTO.getBusinesscode())) {
                sql.ORDER_BY("t1.BUSINESSCODE, t1.WORKFLOWDEFINEKEY");
            } else {
                sql.ORDER_BY("t1.WORKFLOWDEFINEKEY, t1.BUSINESSCODE");
            }
            return sql.toString();
        }

        private String setCollectionSql(String workflowdefineKey, WorkflowBusinessDTO workflowBusinessDTO) {
            List workflowdefinekeys = workflowBusinessDTO.getWorkflowdefinekeys();
            String[] split = workflowdefineKey.split("\\.");
            String WORKFLOWDEFINEKEY = split.length > 1 ? split[1] : workflowdefineKey;
            StringBuilder sb = new StringBuilder();
            int size = workflowdefinekeys.size();
            HashMap<String, String> param = new HashMap<String, String>(workflowdefinekeys.size());
            for (int i = 0; i < size; ++i) {
                String tempKey = (String)workflowdefinekeys.get(i);
                if (Objects.isNull(tempKey)) continue;
                tempKey = VaWorkflowConstants.EMPTY_BLANK_COMPILE.matcher(tempKey).replaceAll(Matcher.quoteReplacement(""));
                param.put(WORKFLOWDEFINEKEY + "_" + i, tempKey);
                if (i % 500 == 0) {
                    if (i > 0) {
                        sb.append(") or ");
                    }
                    sb.append(workflowdefineKey).append(" in(");
                    sb.append("#{extInfo.").append(WORKFLOWDEFINEKEY).append("_").append(i).append("}");
                    continue;
                }
                sb.append(",#{extInfo.").append(WORKFLOWDEFINEKEY).append("_").append(i).append("}");
            }
            workflowBusinessDTO.setExtInfo(param);
            sb.append(")");
            return sb.toString();
        }
    }
}

