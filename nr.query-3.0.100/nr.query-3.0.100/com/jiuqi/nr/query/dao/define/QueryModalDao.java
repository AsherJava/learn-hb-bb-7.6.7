/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.query.dao.define;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.query.dao.impl.TransUtil;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QueryModalDao
extends BaseDao {
    private static String ATTR_TASKID = "taskId";
    private static String ATTR_ID = "id";
    private static String ATTR_GROUPID = "groupId";
    private static String ATTR_MODALTYPE = "modelType";
    private static String ATTR_MODALTITLE = "title";
    public static final String ATTR_QMD_CREATOR = "creator";
    private Class<QueryModalDefine> implClass = QueryModalDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<QueryModalDefine> list() throws Exception {
        List list = this.list(this.implClass);
        return list;
    }

    public void deleteWithAuth(String[] keys) throws Exception {
        for (int i = 0; i < keys.length; ++i) {
            this.delete(keys);
        }
    }

    public void delete(String[] keys) throws Exception {
        for (int i = 0; i < keys.length; ++i) {
            this.delete(keys);
        }
    }

    public void deleteByModelId(String modelID) throws Exception {
        this.deleteBy(new String[]{ATTR_ID}, new Object[]{modelID});
    }

    public void deleteByModelIdWithAuth(String modelID) throws Exception {
        this.deleteBy(new String[]{ATTR_ID}, new Object[]{modelID});
    }

    public QueryModalDefine queryDefineById(String id) throws Exception {
        return (QueryModalDefine)this.getByKey(id, this.implClass);
    }

    public QueryModalDefine queryDefineByIdWithAuth(String id) throws Exception {
        return (QueryModalDefine)this.getByKey(id, this.implClass);
    }

    public List<QueryModalDefine> getDefinesByTaskId(String taskId) throws Exception {
        List list = this.list(new String[]{ATTR_TASKID}, new Object[]{taskId}, this.implClass);
        return list;
    }

    public List<QueryModalDefine> getDefinesByGroupId(String groupdId) throws Exception {
        List list = this.list(new String[]{ATTR_GROUPID}, new Object[]{groupdId}, this.implClass);
        return list;
    }

    public List<QueryModalDefine> getDefinesByGroupId(String groupdId, QueryModelType type) throws Exception {
        List list = this.list(new String[]{ATTR_GROUPID, ATTR_MODALTYPE}, new Object[]{groupdId, type}, this.implClass);
        return list;
    }

    public List<QueryModalDefine> getDefinesByTitle(String title, String groupId) throws Exception {
        List list = this.list(new String[]{ATTR_MODALTITLE, ATTR_GROUPID}, new Object[]{title, groupId.toString()}, this.implClass);
        return list;
    }

    public int insertDefine(QueryModalDefine model) throws Exception {
        return this.insert(model);
    }

    public int[] insertDefines(List<QueryModalDefine> models) throws Exception {
        return this.insert(models.toArray());
    }

    public int updateDefine(QueryModalDefine model) throws Exception {
        return this.update(model);
    }

    public int updateDefineWithAuth(QueryModalDefine model) throws Exception {
        return this.update(model);
    }

    public List<QueryModalDefine> getDefinesByCondition(String condition, String[] values) throws Exception {
        List list = this.list(condition, values, this.implClass);
        return list;
    }

    public List<QueryModalDefine> getDefinesByGroupId(String groupId, QueryModelType type, String userId) {
        List list = this.list(new String[]{ATTR_GROUPID, ATTR_MODALTYPE, ATTR_QMD_CREATOR}, new Object[]{groupId, type, userId}, this.implClass);
        return list;
    }

    public List<QueryModalDefine> getAllChartModels() {
        String condition = "qmd_id in (select qbd_modelid from SYS_QUERYBLOCKDEFINE t where instr(utl_raw.cast_to_varchar2(DBMS_LOB.SUBSTR(t.qbd_blockinfo,2000,length(t.qbd_blockinfo) - 2000)),'qbt_Chart',1,1) > 0)";
        List list = this.list(condition, null, this.implClass);
        return list;
    }

    public List<QueryModalDefine> getDefinesByModelType(String modelType) throws Exception {
        List list = this.list(new String[]{ATTR_MODALTYPE}, new Object[]{modelType}, this.implClass);
        return list;
    }
}

