/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 *  tk.mybatis.mapper.common.BaseMapper
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface PlusApprovalUserDao
extends BaseMapper<PlusApprovalUserDO> {
    @Select(value={"select * from workflow_plusapproval_user where username = #{username}"})
    public List<PlusApprovalUserDO> selectByUserName(PlusApprovalUserDO var1);
}

