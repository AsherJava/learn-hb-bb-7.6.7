/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.delegate.DelegateDO
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateVO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.Update
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.delegate.DelegateDO;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateVO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface DelegateDao
extends BaseOptMapper<DelegateDO> {
    @SelectProvider(type=DelegateSqlProvider.class, method="queryList")
    public List<DelegateVO> queryList(DelegateDTO var1);

    @SelectProvider(type=DelegateSqlProvider.class, method="count")
    public int count(DelegateDTO var1);

    @Select(value={"select * from workflow_delegate_info where id = #{id} "})
    public DelegateDO selectByPk(DelegateDTO var1);

    @Update(value={"update workflow_delegate_info set enableflag = #{enableflag},valdate = #{valdate},invaldate = #{invaldate} where id = #{id} "})
    public int enableByPk(DelegateDTO var1);

    @Update(value={"update workflow_delegate_info set enableflag = #{enableflag} where id = #{id} "})
    public int disenableByPk(DelegateDTO var1);

    @Delete(value={"delete from workflow_delegate_info where id = #{id} "})
    public int deleteByPk(DelegateDTO var1);

    @UpdateProvider(type=DelegateSqlProvider.class, method="updateByPk")
    public int updateByPk(DelegateDTO var1);

    public static class DelegateSqlProvider {
        public String queryList(DelegateDTO delegatedto) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("workflow_delegate_info");
            this.condition(sql, delegatedto);
            sql.ORDER_BY("createtime desc ");
            return sql.toString();
        }

        public String count(DelegateDTO delegatedto) {
            SQL sql = new SQL();
            sql.SELECT("count(*)");
            sql.FROM("workflow_delegate_info");
            this.condition(sql, delegatedto);
            return sql.toString();
        }

        private void condition(SQL sql, DelegateDTO delegatedto) {
            if (delegatedto.isFilterbyself()) {
                String userid = ShiroUtil.getUser().getId().toString();
                sql.WHERE("(creater = '" + userid + "' or delegateuser = '" + userid + "' )");
            }
            if (StringUtils.hasText(delegatedto.getDelegatetitle())) {
                delegatedto.setDelegatetitle("%" + delegatedto.getDelegatetitle() + "%");
                sql.WHERE("delegatetitle like #{delegatetitle}");
            }
            delegatedto.setNowdate(new Date());
            switch (delegatedto.getQuerytype()) {
                case 1: {
                    sql.WHERE("((#{nowdate} > valdate and invaldate is null) or #{nowdate} between valdate and invaldate)");
                    sql.WHERE("(enableflag = 0 or enableflag is null )");
                    break;
                }
                case 2: {
                    sql.WHERE("valdate > #{nowdate}");
                    sql.WHERE("(enableflag = 0 or enableflag is null )");
                    break;
                }
                case 3: {
                    sql.WHERE("(invaldate < #{nowdate} or enableflag = 1)");
                    break;
                }
            }
            if (StringUtils.hasText(delegatedto.getCreater())) {
                sql.WHERE("creater = #{creater}");
            }
            if (StringUtils.hasText(delegatedto.getAgentuser())) {
                sql.WHERE("agentuser = #{agentuser}");
            }
            if (StringUtils.hasText(delegatedto.getDelegateuser())) {
                sql.WHERE("delegateuser = #{delegateuser}");
            }
            if (StringUtils.hasText(delegatedto.getId())) {
                sql.WHERE("id = #{id}");
            }
            if (delegatedto.getHisdeleflag() != null) {
                sql.WHERE("hisdeleflag = #{hisdeleflag}");
            }
        }

        public String updateByPk(DelegateDTO delegatedto) {
            SQL sql = new SQL();
            sql.UPDATE("workflow_delegate_info");
            if (StringUtils.hasText(delegatedto.getDelegatetitle())) {
                sql.SET("delegatetitle = #{delegatetitle}");
            }
            if (StringUtils.hasText(delegatedto.getDelegateuser())) {
                sql.SET("delegateuser = #{delegateuser}");
            }
            if (StringUtils.hasText(delegatedto.getAgentuser())) {
                sql.SET("agentuser = #{agentuser}");
            }
            if (delegatedto.getValdate() != null) {
                sql.SET("valdate = #{valdate}");
            }
            if (delegatedto.getInvaldate() != null) {
                sql.SET("invaldate = #{invaldate}");
            }
            if (delegatedto.getHisdeleflag() != null) {
                sql.SET("hisdeleflag = #{hisdeleflag}");
            }
            if (StringUtils.hasText(delegatedto.getDelecomment())) {
                sql.SET("delecomment = #{delecomment}");
            }
            if (StringUtils.hasText(delegatedto.getDelecondition())) {
                sql.SET("delecondition = #{delecondition}");
            }
            if (StringUtils.hasText(delegatedto.getStaffcode())) {
                sql.SET("staffcode = #{staffcode}");
            }
            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}

