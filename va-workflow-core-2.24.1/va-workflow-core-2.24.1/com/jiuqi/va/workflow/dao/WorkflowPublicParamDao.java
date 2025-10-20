/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface WorkflowPublicParamDao
extends BaseOptMapper<WorkflowPublicParamDO> {
    @SelectProvider(method="list", type=WorkflowPublicParamDaoProvide.class)
    public List<WorkflowPublicParamDTO> list(WorkflowPublicParamDTO var1);

    @Update(value={"update WORKFLOW_PUBPARAM set paramtitle=#{paramtitle}, paramtype=#{paramtype}, mappingtype=#{mappingtype}, mapping=#{mapping}, modifytime=#{modifytime}, fixedflag=#{fixedflag},removeflag=#{removeflag} where id = #{id}"})
    public int update(WorkflowPublicParamDTO var1);

    public static class WorkflowPublicParamDaoProvide {
        public String list(WorkflowPublicParamDTO publicParamDTO) {
            SQL sql = new SQL();
            boolean searchdata = publicParamDTO.isSearchdata();
            if (searchdata) {
                sql.SELECT("t1.*,t2.paramdata");
                sql.FROM("WORKFLOW_PUBPARAM t1");
                sql.LEFT_OUTER_JOIN("WORKFLOW_PUBPARAM_DATA t2 on t1.id = t2.id ");
            } else {
                sql.SELECT("t1.*");
                sql.FROM("WORKFLOW_PUBPARAM t1");
            }
            String searchkey = publicParamDTO.getSearchkey();
            if (StringUtils.hasText(searchkey)) {
                publicParamDTO.setSearchkey(searchkey.toUpperCase());
                sql.WHERE("( t1.paramname like concat(concat('%', #{searchkey}), '%') or t1.paramtitle like concat(concat('%', #{searchkey}), '%') )");
            }
            if (StringUtils.hasText(publicParamDTO.getParamname())) {
                sql.WHERE("t1.paramname = #{paramname}");
            }
            if (publicParamDTO.getFixedflag() != null) {
                sql.WHERE("t1.fixedflag = #{fixedflag}");
            }
            if (publicParamDTO.getRemoveflag() != null) {
                sql.WHERE("t1.removeflag = #{removeflag}");
            } else {
                sql.WHERE("t1.removeflag != 1");
            }
            String orderfield = publicParamDTO.getOrderfield();
            if (StringUtils.hasText(orderfield)) {
                sql.ORDER_BY("t1." + orderfield);
            } else {
                sql.ORDER_BY("t1.ordernum");
            }
            String sqlStr = sql.toString();
            String ordertype = publicParamDTO.getOrdertype();
            if (WorkflowOption.OrderType.DESC.name().equals(ordertype)) {
                sqlStr = sqlStr + " desc ";
            }
            return sqlStr;
        }
    }
}

