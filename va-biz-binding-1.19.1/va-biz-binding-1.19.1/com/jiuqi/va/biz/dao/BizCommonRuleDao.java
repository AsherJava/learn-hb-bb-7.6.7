/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.biz.dao;

import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDO;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDTO;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleVO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface BizCommonRuleDao
extends BaseOptMapper<BizCommonRuleDO> {
    @SelectProvider(type=BizCommonRuleDaoProvider.class, method="listByParam")
    public List<BizCommonRuleDTO> listByParam(BizCommonRuleDO var1);

    public static class BizCommonRuleDaoProvider {
        public String listByParam(BizCommonRuleVO bizCommonRuleVO) {
            SQL sql = new SQL();
            ((SQL)sql.SELECT("rule.*, info.RULEINFO")).FROM("BIZ_COMMRULE rule");
            if (bizCommonRuleVO.isCurUser()) {
                sql.WHERE("rule.CREATEUSER = #{createuser}");
            }
            if (bizCommonRuleVO.getBiztype() != null) {
                sql.WHERE("rule.BIZTYPE = #{biztype}");
            }
            sql.JOIN("BIZ_COMMRULE_INFO info ON info.ID = rule.ID");
            sql.ORDER_BY("rule.VER DESC");
            return sql.toString();
        }
    }
}

