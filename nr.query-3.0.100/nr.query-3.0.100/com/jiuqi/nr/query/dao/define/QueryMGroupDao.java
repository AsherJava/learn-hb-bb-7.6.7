/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.query.dao.define;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.query.dao.impl.TransUtil;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QueryMGroupDao
extends BaseDao {
    private static String ATTR_ParentId = "ParentGroupId";
    private static String ATTR_Title = "GroupName";
    private Class<QueryModalGroup> implClass = QueryModalGroup.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<QueryModalGroup> list() throws Exception {
        List list = this.list(this.implClass);
        return list;
    }

    public List<QueryModalGroup> list(QueryModelType type) throws Exception {
        List list = this.list(this.implClass);
        ArrayList<QueryModalGroup> queryModalGroups = new ArrayList<QueryModalGroup>();
        for (QueryModalGroup queryModalGroup : list) {
            if (queryModalGroup.getModelType() != type) continue;
            queryModalGroups.add(queryModalGroup);
        }
        return queryModalGroups;
    }

    public void delete(String[] keys) throws Exception {
        this.delete(keys);
    }

    public int deleteById(String id) throws Exception {
        return this.delete(id);
    }

    public int deleteByIdWithAuth(String id) throws Exception {
        return this.delete(id);
    }

    public QueryModalGroup queryGroupByIdWithAuth(String id) throws Exception {
        return (QueryModalGroup)this.getByKey(id, this.implClass);
    }

    public QueryModalGroup queryGroupById(String id) throws Exception {
        return (QueryModalGroup)this.getByKey(id, this.implClass);
    }

    public List<QueryModalGroup> getMGroupByParentId(String parentId) throws Exception {
        List list = this.list(new String[]{ATTR_ParentId}, new Object[]{parentId}, this.implClass);
        return list;
    }

    public List<QueryModalGroup> getMGroupByParentId(String parentId, String userId) throws Exception {
        List list = this.list(new String[]{ATTR_ParentId, "QMG_CREATOR"}, new Object[]{parentId, userId}, this.implClass);
        return list;
    }

    public List<QueryModalGroup> getMGroupByParentIdWithType(String parentId, QueryModelType type) throws Exception {
        List list = this.list(new String[]{ATTR_ParentId, "QMG_TYPE"}, new Object[]{parentId, type}, this.implClass);
        return list;
    }

    public int insertMGroup(QueryModalGroup group) throws Exception {
        return this.insert(group);
    }

    public int[] insertDefines(List<QueryModalGroup> groups) throws Exception {
        return this.insert(groups.toArray());
    }

    public int updateMGroup(QueryModalGroup group) throws Exception {
        return this.update(group);
    }

    public List<QueryModalGroup> getGroupsByCondition(String condition, String[] values) throws Exception {
        List list = this.list(condition, values, this.implClass);
        return list;
    }

    public List<QueryModalGroup> getMGroupByTitle(String title, String parentId) throws Exception {
        List list = this.list(new String[]{ATTR_Title, ATTR_ParentId}, new Object[]{title, parentId}, this.implClass);
        return list;
    }

    public List<QueryModalGroup> getMGroupByType(QueryModelType type) throws Exception {
        List list = this.list(new String[]{"QMG_TYPE"}, new Object[]{type}, this.implClass);
        return list;
    }

    public List<QueryModalGroup> getMGroupByParentIdWithType(String parentId, QueryModelType type, String userId) {
        List list = null;
        list = userId == null ? this.list(new String[]{ATTR_ParentId, "QMG_TYPE"}, new Object[]{parentId, type}, this.implClass) : this.list(new String[]{ATTR_ParentId, "QMG_TYPE", "QMG_CREATOR"}, new Object[]{parentId, type, userId}, this.implClass);
        return list;
    }

    public List<QueryModalGroup> getAllChartModelGroups() throws Exception {
        String condition = "qmg_id in (select distinct m.qmd_groupid from sys_querymodeldefine m where m.qmd_id in (select qbd_modelid from SYS_QUERYBLOCKDEFINE t where instr(utl_raw.cast_to_varchar2(DBMS_LOB.SUBSTR(t.qbd_blockinfo,2000,length(t.qbd_blockinfo) - 2000)), 'qbt_Chart',1,1) > 0))";
        List list = this.list(condition, null, this.implClass);
        return list;
    }
}

