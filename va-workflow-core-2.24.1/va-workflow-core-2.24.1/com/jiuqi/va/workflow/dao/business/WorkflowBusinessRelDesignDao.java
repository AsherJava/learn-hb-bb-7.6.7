/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao.business;

import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;

@Mapper
public interface WorkflowBusinessRelDesignDao
extends BaseOptMapper<WorkflowBusinessRelDesignDO> {
    @SelectProvider(method="queryByIdList", type=WorkflowBusinessRelDesignDaoProvider.class)
    public List<WorkflowBusinessRelDesignDO> queryByIdList(WorkflowBusinessDTO var1);

    @DeleteProvider(method="deleteByIdList", type=WorkflowBusinessRelDesignDaoProvider.class)
    public void deleteByIdList(WorkflowBusinessDTO var1);

    public static class WorkflowBusinessRelDesignDaoProvider {
        public String queryByIdList(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("ID, DESIGNDATA")).FROM("WORKFLOW_BUSINESS_REL_DESIGN");
            List deployIds = workflowBusinessDTO.getDeployIds();
            if (!CollectionUtils.isEmpty(deployIds)) {
                int totalSize = deployIds.size();
                int batchSize = 1000;
                int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < iterations; ++i) {
                    sb.append("ID IN (");
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
            return sql.toString();
        }

        public String deleteByIdList(WorkflowBusinessDTO workflowBusinessDTO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("WORKFLOW_BUSINESS_REL_DESIGN");
            List deployIds = workflowBusinessDTO.getDeployIds();
            int totalSize = deployIds.size();
            int batchSize = 1000;
            int iterations = (int)Math.ceil((double)totalSize / (double)batchSize);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterations; ++i) {
                sb.append("ID IN (");
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
            return sql.toString();
        }
    }
}

