/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 *  org.apache.ibatis.annotations.Insert
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.workflow.dao;

import com.jiuqi.va.mapper.common.CustomOptMapper;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WorkflowMetaDao
extends CustomOptMapper {
    @Insert(value={"INSERT INTO WORKFLOW_META_RELATION(ID,WORKFLOWDEFINEKEY,WORKFLOWDEFINEVERSION,METAVERSION) VALUES(#{extInfo.id},#{extInfo.workflowDefineKey},#{extInfo.workflowDefineVersion},#{extInfo.metaVersion})"})
    public void save(TenantDO var1);

    @Select(value={"SELECT METAVERSION FROM WORKFLOW_META_RELATION WHERE WORKFLOWDEFINEKEY = #{extInfo.workflowDefineKey} AND WORKFLOWDEFINEVERSION = #{extInfo.workflowDefineVersion}"})
    public Long getMetaVersion(TenantDO var1);

    @Select(value={"SELECT WORKFLOWDEFINEVERSION FROM WORKFLOW_META_RELATION WHERE WORKFLOWDEFINEKEY = #{extInfo.workflowDefineKey} AND METAVERSION = #{extInfo.metaVersion}"})
    public Integer getworkflowDefineVersion(TenantDO var1);

    @Select(value={"SELECT * from WORKFLOW_META_RELATION WHERE WORKFLOWDEFINEKEY = #{extInfo.defineCode} ORDER BY METAVERSION DESC"})
    public List<UpperKeyMap> getWorkflowMetaRelation(TenantDO var1);

    @Select(value={"SELECT * from WORKFLOW_META_RELATION"})
    public List<UpperKeyMap> getWorkflowMetaRelationAll(TenantDO var1);
}

