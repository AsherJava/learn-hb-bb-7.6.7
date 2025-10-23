/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.finalaccountsaudit.common.TmpTableUtils
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.singlequeryimport.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.finalaccountsaudit.common.TmpTableUtils;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ModalNodeInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QueryModeleDao
extends BaseDao {
    private Class<QueryModel> implClass = QueryModel.class;
    private static final String TASK_KEY = "taskKey";
    private static final String FORMSCHEME_KEY = "formschemeKey";
    private static final String GROUP = "group";
    private static final String SQ_KEY = "key";
    private static final String SQ_DISUSE = "disUse";
    private static final String SQ_MODALWARNING = "SQ_MODAL_WARNING";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private TmpTableUtils tempDao;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<QueryModel> get() throws Exception {
        List list = this.list(this.implClass);
        return list;
    }

    public List<QueryModel> getBySchemeKey(String key) throws Exception {
        return this.list(new String[]{TASK_KEY}, new Object[]{key}, this.implClass);
    }

    public List<QueryModel> getByTaskKeyAndSchemeKey(String takeKey, String formSchemeKey) throws Exception {
        return this.list(new String[]{TASK_KEY, FORMSCHEME_KEY}, new Object[]{takeKey, formSchemeKey}, this.implClass);
    }

    public List<QueryModel> getGroup(String key) throws Exception {
        return this.list(new String[]{FORMSCHEME_KEY}, new Object[]{key}, this.implClass);
    }

    public List<QueryModel> getModel(String formSchemeKey, String key) throws Exception {
        return this.list(new String[]{FORMSCHEME_KEY, GROUP}, new Object[]{formSchemeKey, key}, this.implClass);
    }

    public List<QueryModel> getDisUseModel(String formSchemeKey, String key) throws Exception {
        return this.list(new String[]{FORMSCHEME_KEY, GROUP, SQ_DISUSE}, new Object[]{formSchemeKey, key, 1}, this.implClass);
    }

    public List<QueryModel> getModelData(String key) throws Exception {
        return this.list(new String[]{SQ_KEY}, new Object[]{key}, this.implClass);
    }

    public List<QueryModel> getCheckModel(String taskKey, String formSchemeKey) throws Exception {
        return this.list(new String[]{TASK_KEY, FORMSCHEME_KEY}, new Object[]{taskKey, formSchemeKey}, this.implClass);
    }

    public QueryModel getData(String key) throws Exception {
        return (QueryModel)this.getBy("sq_key=?", new Object[]{key}, this.implClass);
    }

    public int[] upDateDisUse(final List<QueryModelNode> modleNodes) throws Exception {
        return this.jdbcTemplate.batchUpdate("UPDATE SYS_SINGLE_QUERY SET SQ_DISUSE = ? WHERE SQ_KEY = ?", new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, ((QueryModelNode)modleNodes.get(i)).getDisUse());
                ps.setString(2, ((QueryModelNode)modleNodes.get(i)).getId());
            }

            public int getBatchSize() {
                return modleNodes.size();
            }
        });
    }

    public Integer deleteById(String key) throws Exception {
        return this.deleteBy(new String[]{SQ_KEY}, new Object[]{key});
    }

    public int[] batchDelete(List keys) throws Exception {
        return this.delete(keys.toArray());
    }

    public Integer changeModelName(String name, String key) throws Exception {
        String sql = "UPDATE SYS_SINGLE_QUERY SET SQ_ITEM_TITLE = ? WHERE SQ_KEY = ?";
        return this.jdbcTemplate.update(sql, new Object[]{name, key});
    }

    public Integer changeGroupName(String newName, String taskKey, String fromSchemeKey, String oldName) throws Exception {
        String sql = "UPDATE SYS_SINGLE_QUERY SET SQ_GROUP = ? WHERE SQ_FORMSCHEME_KEY = ? AND SQ_TASK_KEY = ? AND SQ_GROUP = ?";
        return this.jdbcTemplate.update(sql, new Object[]{newName, fromSchemeKey, taskKey, oldName});
    }

    public Integer deleteGroup(String name, String fromSchemeKey, String taskKey) throws Exception {
        String sql = "DELETE FROM SYS_SINGLE_QUERY WHERE SQ_FORMSCHEME_KEY = ? AND SQ_TASK_KEY = ? AND SQ_GROUP = ?";
        return this.jdbcTemplate.update(sql, new Object[]{fromSchemeKey, taskKey, name});
    }

    public Integer deleteTask(String taskKey) throws Exception {
        String sql = "DELETE FROM SYS_SINGLE_QUERY WHERE SQ_TASK_KEY = ?";
        return this.jdbcTemplate.update(sql, new Object[]{taskKey});
    }

    public Integer deleteScheme(String fromSchemeKey) throws Exception {
        String sql = "DELETE FROM SYS_SINGLE_QUERY WHERE SQ_FORMSCHEME_KEY =?";
        return this.jdbcTemplate.update(sql, new Object[]{fromSchemeKey});
    }

    public Integer deleteByLevel(String fromSchemeKey, String taskKey, Set<Integer> levels) throws Exception {
        StringBuilder sqlLevel = new StringBuilder();
        for (Integer level : levels) {
            sqlLevel.append(level).append(",");
        }
        if (sqlLevel.length() > 0) {
            sqlLevel.deleteCharAt(sqlLevel.length() - 1);
        }
        String sql = "DELETE FROM SYS_SINGLE_QUERY WHERE  SQ_FORMSCHEME_KEY = ? AND SQ_TASK_KEY = ? AND  SQ_LEVEL in (" + sqlLevel + ")";
        return this.jdbcTemplate.update(sql, new Object[]{fromSchemeKey, taskKey});
    }

    public int deleteAll(String fromSchemeKey, String taskKey) {
        String sql = "DELETE FROM SYS_SINGLE_QUERY WHERE   SQ_FORMSCHEME_KEY = ? AND SQ_TASK_KEY = ? ";
        return this.jdbcTemplate.update(sql, new Object[]{fromSchemeKey, taskKey});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<QueryModel> getMdoelByKyes(List<String> keys) {
        ITempTable tempTableName = null;
        try {
            ArrayList<String> alist = new ArrayList<String>();
            for (String key : keys) {
                if (alist.contains(key)) continue;
                alist.add(key);
            }
            ArrayList<QueryModel> modelList = new ArrayList<QueryModel>();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT SQ_KEY, SQ_TASK_KEY ,SQ_FORMSCHEME_KEY ,SQ_GROUP,SQ_ITEM_TITLE,SQ_ITEM,SQ_ORDER FROM SYS_SINGLE_QUERY WHERE 1=1");
            sql.append(" and SQ_KEY").append(" in (");
            ArrayList objects = new ArrayList();
            for (int i = 0; i < alist.size(); ++i) {
                sql.append("?");
                if (i < alist.size() - 1) {
                    sql.append(",");
                }
                objects.add(alist.get(i));
            }
            sql.append(")");
            Object[] ov = new Object[]{};
            this.jdbcTemplate.query(sql.toString(), objects.toArray(ov), rs -> {
                QueryModel model = new QueryModel();
                int col = 1;
                model.setKey(rs.getString(col++));
                model.setTaskKey(rs.getString(col++));
                model.setFormschemeKey(rs.getString(col++));
                model.setGroup(rs.getString(col++));
                model.setItemTitle(rs.getString(col++));
                model.setItem(rs.getString(col++));
                model.setOrder(rs.getString(col++));
                modelList.add(model);
            });
            ArrayList<QueryModel> arrayList = modelList;
            return arrayList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (null != tempTableName) {
                try {
                    this.tempDao.dropTempTable(tempTableName);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;
    }

    public String updateOrder(final List<QueryModelNode> modleNodes) throws Exception {
        this.jdbcTemplate.batchUpdate("UPDATE SYS_SINGLE_QUERY SET SQ_ORDER = ? WHERE SQ_KEY = ?", new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((QueryModelNode)modleNodes.get(i)).getOrder());
                ps.setString(2, ((QueryModelNode)modleNodes.get(i)).getId());
            }

            public int getBatchSize() {
                return modleNodes.size();
            }
        });
        return "OK";
    }

    public QueryModel getModelById(QueryModel queryModel) throws Exception {
        return (QueryModel)this.getBy("sq_task_key=? and sq_formscheme_key=? and  sq_group = ? and sq_item_title=?", new Object[]{queryModel.getTaskKey(), queryModel.getFormschemeKey(), queryModel.getGroup(), queryModel.getItemTitle()}, this.implClass);
    }

    public Set<String> getTaskKey() {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select distinct t.sq_task_key from SYS_SINGLE_QUERY t", rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    public Set<String> getFormSchemeKeyByTaskKey(String taskKey) {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select distinct t.sq_formscheme_key from SYS_SINGLE_QUERY t where t.sq_task_key=?", new Object[]{taskKey}, rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    public Set<String> getModalKeyByTaskKey(String taskKey) {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select t.SQ_KEY from SYS_SINGLE_QUERY t where t.sq_task_key=?", new Object[]{taskKey}, rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    public Set<String> getGroupByFormSchemeKey(String formSchemeKey) {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select t.SQ_GROUP from SYS_SINGLE_QUERY t where t.SQ_FORMSCHEME_KEY=?", new Object[]{formSchemeKey}, rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    public Set<String> getModalKeyByFormSchemeKey(String formSchemeKey) {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select t.SQ_KEY from SYS_SINGLE_QUERY t where t.SQ_FORMSCHEME_KEY=?", new Object[]{formSchemeKey}, rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    public Set<String> getModalTitleByFormSchemeKey(String formSchemeKey) {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select t.SQ_ITEM_TITLE from SYS_SINGLE_QUERY t where t.SQ_FORMSCHEME_KEY=?", new Object[]{formSchemeKey}, rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    public Set<String> getModalKeyByGroupKey(String formSchemeKey, String groupKey) {
        HashSet<String> result = new HashSet<String>();
        this.jdbcTemplate.query("select t.SQ_KEY from SYS_SINGLE_QUERY t where t.SQ_FORMSCHEME_KEY=? and t.SQ_GROUP=? and t.SQ_ITEM_TITLE is not null", new Object[]{formSchemeKey, groupKey}, rs -> {
            if (!result.contains(rs.getString(1))) {
                result.add(rs.getString(1));
            }
        });
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<ModalNodeInfo> getModalNodeInfosByKeys(List<String> modalIds) {
        ArrayList<ModalNodeInfo> arrayList;
        ITempTable tempTableName = null;
        ArrayList<ModalNodeInfo> result = new ArrayList<ModalNodeInfo>();
        try {
            boolean useTempTable = false;
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT SQ_KEY, SQ_ITEM_TITLE,SQ_GROUP,SQ_FORMSCHEME_KEY FROM SYS_SINGLE_QUERY WHERE 1=1");
            if (modalIds.size() >= 1000) {
                useTempTable = true;
                tempTableName = this.tempDao.createTempTable();
                this.tempDao.prepareTempTableData(tempTableName, modalIds);
                sql.append(String.format(" and SQ_KEY in (SELECT %s from %s)", this.tempDao.getFName(), tempTableName));
            } else {
                sql.append(" AND SQ_KEY IN (");
                for (int i = 0; i < modalIds.size(); ++i) {
                    sql.append("?");
                    if (i >= modalIds.size() - 1) continue;
                    sql.append(",");
                }
                sql.append(")");
            }
            sql.append(" ORDER BY SQ_ORDER");
            if (useTempTable) {
                this.jdbcTemplate.query(sql.toString(), rs -> {
                    ModalNodeInfo nodeInfo = new ModalNodeInfo();
                    nodeInfo.setModalId(rs.getString(1));
                    nodeInfo.setTitle(rs.getString(2));
                    nodeInfo.setGroupTitle(rs.getString(3));
                    nodeInfo.setFormSchemeKey(rs.getString(4));
                    result.add(nodeInfo);
                });
            } else {
                this.jdbcTemplate.query(sql.toString(), rs -> {
                    ModalNodeInfo nodeInfo = new ModalNodeInfo();
                    nodeInfo.setModalId(rs.getString(1));
                    nodeInfo.setTitle(rs.getString(2));
                    nodeInfo.setGroupTitle(rs.getString(3));
                    nodeInfo.setFormSchemeKey(rs.getString(4));
                    result.add(nodeInfo);
                }, modalIds.toArray());
            }
            arrayList = result;
            if (null == tempTableName) return arrayList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        try {
            this.tempDao.dropTempTable(tempTableName);
            return arrayList;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return arrayList;
        finally {
            if (null != tempTableName) {
                try {
                    this.tempDao.dropTempTable(tempTableName);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<String> sortedModelsByIds(List<String> modelIds) {
        ArrayList<String> result = new ArrayList<String>();
        if (modelIds.size() == 1) {
            result.addAll(modelIds);
            return result;
        }
        ITempTable tempTableName = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT SQ_KEY FROM SYS_SINGLE_QUERY WHERE 1=1");
            if (modelIds.size() >= 1000) {
                tempTableName = this.tempDao.createTempTable();
                this.tempDao.prepareTempTableData(tempTableName, modelIds);
                sql.append(String.format(" and SQ_KEY in (SELECT %s from %s)", this.tempDao.getFName(), tempTableName));
            } else {
                sql.append(" and SQ_KEY").append(" in ('").append(String.join((CharSequence)"','", modelIds)).append("')");
            }
            sql.append(" ORDER BY SQ_ORDER");
            this.jdbcTemplate.query(sql.toString(), rs -> result.add(rs.getString(1)));
            if (null == tempTableName) return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        try {
            this.tempDao.dropTempTable(tempTableName);
            return result;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return result;
        }
        finally {
            if (null != tempTableName) {
                try {
                    this.tempDao.dropTempTable(tempTableName);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}

